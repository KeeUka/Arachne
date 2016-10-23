package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.keeratipong.arachne.core.Arachne;
import net.keeratipong.arachne.core.Result;

public class Window extends JFrame {

	private JPanel topPanel;
	private ListPanel unprocessedInputPanel;
	private ListPanel processedInputPanel;
	private ListPanel outputPanel;
	private InfoPanel infoPanel;

	private Arachne arachne;

	public Window() {
		super("Arachne Project");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		setSize(800, 600);
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

	public void start() {
		reloadInput();
		while(arachne.hasMoreInput()) {
			arachne.processNextInput();
			unprocessedInputPanel.setList(arachne.getInputList());
			processedInputPanel.setList(resultKeys(arachne.getResults()));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		// Top Panel
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(800, 100));
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(topPanel, BorderLayout.NORTH);

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
			e.printStackTrace();
		}
		unprocessedInputPanel.setList(arachne.getInputList());
		infoPanel.showInfoMessage("Input data has been loaded.");
	}
	
	private List<String> resultKeys(List<Result> results) {
		List<String> keys = new ArrayList<String>();
		for(Result r : results) {
			keys.add(r.getKey());
		}
		return keys;
	}

}
