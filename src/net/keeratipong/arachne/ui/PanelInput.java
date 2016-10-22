package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class PanelInput extends JPanel {

	private JLabel label;
	private JTextArea inputArea;

	public PanelInput() {
		setPreferredSize(new Dimension(400, 450));
		Border margin = new EmptyBorder(10,10,10,10);
		setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(), margin));
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		
		label = new JLabel("Input Keywords (Read from input.txt)");
		label.setPreferredSize(new Dimension(300, 30));
		add(label, BorderLayout.NORTH);
		
		inputArea = new JTextArea();
		inputArea.setEditable(false);
		JScrollPane sp = new JScrollPane(inputArea);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp, BorderLayout.CENTER);
	}

}
