package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.keeratipong.arachne.core.Arachne;

public class Window extends JFrame {

	private JPanel topPanel;
	private ListPanel panelInput;
	private ListPanel panelOutput;
	private InfoPanel panelInfo;

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
		if(arachne == null) {
			arachne = new Arachne();
		}
	}
	
	public void start() {
		reloadInput();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		// Top Panel
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(800, 100));
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(topPanel, BorderLayout.NORTH);

		panelInput = new ListPanel("Input Keywords (Read from input.txt)");
		add(panelInput, BorderLayout.WEST);

		panelOutput = new ListPanel("Output (Write to output.txt)");
		add(panelOutput, BorderLayout.EAST);

		// Bottom Panel
		panelInfo = new InfoPanel("Arachne is idling");
		add(panelInfo, BorderLayout.SOUTH);
	}

	private void reloadInput() {
		try {
			arachne.reloadInput();
		} catch (IOException e) {
			panelInfo.showErrorMessage("Failed to load input on input.txt.");
			e.printStackTrace();
		}
		panelInput.setList(arachne.getInput());
		panelInfo.showInfoMessage("Input data has been loaded.");
	}
	
}
