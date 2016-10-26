package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import net.keeratipong.arachne.core.Arachne;
import net.keeratipong.arachne.crawler.Crawler;
import net.keeratipong.arachne.crawler.CrawlerState;

public class Window extends JFrame {

	private JPanel topPanel;
	private JButton startButton;
	private JButton stopButton;
	private ListPanel unprocessedInputPanel;
	private ListPanel processedInputPanel;
	private ListPanel outputPanel;
	private InfoPanel infoPanel;

	private Arachne arachne;
	private Worker worker;

	public Window() {
		super("Arachne Project");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1000, 600);
		initComponents();
	}

	public void recenter() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public void activate() {
		setVisible(true);
		recenter();
		if (arachne == null) {
			arachne = new Arachne();
		}
	}

	private void start() {
		worker = new Worker();
		CrawlerState.getInstance().addObserver(worker);
		worker.execute();
	}

	private void stop() {
		if (worker != null) {
			worker.cancel(true);
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		// Top Panel
		topPanel = new JPanel(new FlowLayout());
		topPanel.setPreferredSize(new Dimension(800, 100));
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(topPanel, BorderLayout.NORTH);

		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start();
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
			}
		});
		topPanel.add(startButton);

		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
				stopButton.setEnabled(false);
				startButton.setEnabled(true);
			}
		});
		topPanel.add(stopButton);

		// Center
		unprocessedInputPanel = new ListPanel("Unprocessed Input");
		processedInputPanel = new ListPanel("Processed Input");
		add(new JPanel() {

			{
				setLayout(new BorderLayout());
				add(unprocessedInputPanel, BorderLayout.NORTH);
				add(processedInputPanel, BorderLayout.SOUTH);
			}

		}, BorderLayout.WEST);

		outputPanel = new ListPanel("Output (Write to output.txt)");
		outputPanel.setPreferredSize(new Dimension(600,220));
		add(outputPanel, BorderLayout.EAST);

		// Bottom Panel
		infoPanel = new InfoPanel("Arachne is idling");
		add(infoPanel, BorderLayout.SOUTH);
	}

	private void reloadInput() {
		infoPanel.showInfoMessage("Loading input...");
		try {
			arachne.reloadInputList();
		} catch (IOException e) {
			infoPanel.showErrorMessage("Failed to load input on input.txt.");
		}
		unprocessedInputPanel.setList(arachne.getInputList());
		infoPanel.showInfoMessage("Input data has been loaded.");
	}

	private boolean hookBrowser() {
		try {
			arachne.getBrowserHook().start();
		} catch (IOException e) {
			infoPanel.showErrorMessage("Fail to connect to Firefox.");
			return false;
		}
		infoPanel.showInfoMessage("Firefox is successfully connected.");
		return true;
	}

	private boolean goToUrl(String url) {
		try {
			arachne.getBrowserHook().setUrlAndWaitForChange(url);
		} catch (IOException e) {
			infoPanel.showErrorMessage(url);
			return false;
		}
		infoPanel.showInfoMessage("Arachene arrived on " + url);
		return true;
	}

	private List<String> getAllWhitelistedLinks() {
		try {
			return arachne.getBrowserHook().getAllAbsoluteWhiteListLinks();
		} catch (IOException e) {
			infoPanel.showErrorMessage("Could not get links on the page.");
			return null;
		}
	}

	class Worker extends SwingWorker<String, String> implements Observer {

		@Override
		protected String doInBackground() throws Exception {
			// Reload the input
			if (arachne.getInputList() == null || arachne.getInputList().isEmpty()) {
				reloadInput();
			}
			// Start to hook with browser
			if (!hookBrowser()) {
				return null;
			}

			// Go to google
			if (!goToUrl("http://www.google.com")) {
				return null;
			}

			String googleUrl = arachne.getBrowserHook().getCurrentUrl();

			int count = 0;
			while (arachne.hasMoreInput()) {
				count ++;
				String keyword = arachne.googleQuery(arachne.getNextInput());
				if (!goToUrl(googleUrl + "#q=" + keyword)) {
					break;
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					return null;
				}
				List<String> links = getAllWhitelistedLinks();
				if(links == null || links.isEmpty()) {
					break;
				}
				String target = links.get(0);
				
				outputPanel.append("# #####################");
				outputPanel.append("# Key: " + keyword);
				outputPanel.append("# Root: " + target);
				
				crawl(target);
				
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					return null;
				}
				arachne.getInputList().remove(0);
			}
			return null;
		}

		private void crawl(String root) throws Exception {
			CrawlerState.getInstance().setDomain(root);
			
			String crawlStorageFolder = "./log";
	        int numberOfCrawlers = 1;
	        
	        CrawlConfig config = new CrawlConfig();
	        config.setCrawlStorageFolder(crawlStorageFolder);

	        PageFetcher pageFetcher = new PageFetcher(config);
	        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
	        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
	        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

	        controller.addSeed(CrawlerState.getInstance().getDomain());
	        controller.start(Crawler.class, numberOfCrawlers);
		}

		@Override
		public void update(Observable o, Object arg) {
			if(arg.toString().equals(CrawlerState.UPDATE_EMAIL)) {
				infoPanel.showInfoMessage("Email Found: " + CrawlerState.getInstance().getLastEmailFound());
				outputPanel.append("# Email Found: " + CrawlerState.getInstance().getLastEmailFound());
			} else if(arg.toString().equals(CrawlerState.UPDATE_URL)) {
				infoPanel.showInfoMessage("Scraping : " + CrawlerState.getInstance().getCurrentUrl());
				outputPanel.append("Scraping : " + CrawlerState.getInstance().getCurrentUrl());
			}
		}
		
	}

}
