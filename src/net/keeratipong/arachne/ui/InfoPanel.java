package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class InfoPanel extends JPanel{

	private String message;
	private JLabel label;
	
	public InfoPanel(String message) {
		this.message = message;
		setPreferredSize(new Dimension(800, 50));
		Border margin = new EmptyBorder(10,10,10,10);
		setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(), margin));
		initComponents();
	}
	
	public void showInfoMessage(String message) {
		this.message = message;
		label.setText(message);
		label.setForeground(Color.green.darker());
	}
	
	public void showErrorMessage(String message) {
		this.message = message;
		label.setText(message);
		label.setForeground(Color.red.darker());
	}
	
	private void initComponents() {
		setLayout(new BorderLayout());
		label = new JLabel(message);
		label.setForeground(Color.green.darker());
		add(label, BorderLayout.CENTER);
	}
	
}
