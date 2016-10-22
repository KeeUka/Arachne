package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

	private JPanel topPanel;
	private PanelInput panelInput;
	private JPanel rightPanel;
	private JPanel bottomPanel;
	
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

	public void start() {
		setVisible(true);
		recenter();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		// Top Panel
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(800, 100));
		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(topPanel, BorderLayout.NORTH);
		
		// Left Panel
		panelInput = new PanelInput();
		add(panelInput, BorderLayout.WEST);
		
		// Right Panel
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(400, 450));
		rightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(rightPanel, BorderLayout.EAST);
		
		// Bottom Panel
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(800, 50));
		bottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
}
