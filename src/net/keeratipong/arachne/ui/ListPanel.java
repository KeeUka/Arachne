package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class ListPanel extends JPanel {

	private String title;
	private JLabel label;
	private JTextArea listText;

	public ListPanel(String title) {
		this.title = title;
		setPreferredSize(new Dimension(400, 450));
		Border margin = new EmptyBorder(10,10,10,10);
		setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(), margin));
		initComponents();
	}
	
	public void setList(List<String> list, InfoPanel infoPanel) {
		for(int i = 0; i < list.size(); i++) {
			listText.append(list.get(i));
			listText.append("\n");
			if(infoPanel != null) {
				String msg = String.format("%d/%d input has been loaded.", i, list.size());
				infoPanel.showInfoMessage(msg);
			}
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		
		label = new JLabel(title);
		label.setPreferredSize(new Dimension(300, 30));
		add(label, BorderLayout.NORTH);
		
		listText = new JTextArea();
		listText.setEditable(false);
		JScrollPane sp = new JScrollPane(listText);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp, BorderLayout.CENTER);
	}

}
