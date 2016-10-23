package net.keeratipong.arachne.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

	public static final int LINE_LIMIT = 20;

	private String title;
	private JLabel label;
	private JTextArea listText;

	public ListPanel(String title) {
		this.title = title;
		setPreferredSize(new Dimension(400, 220));
		Border margin = new EmptyBorder(10, 10, 10, 10);
		setBorder(new CompoundBorder(BorderFactory.createRaisedBevelBorder(), margin));
		initComponents();
	}

	public void setList(List<String> list) {
		listText.setText("");
		for (int i = 0; i < list.size(); i++) {
			listText.append(list.get(i));
			listText.append("\n");
			if (i > LINE_LIMIT) {
				listText.append("\n... " + (list.size() - LINE_LIMIT) + " More Items ...");
				break;
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
		listText.setFont(new Font("Courier New", Font.ITALIC, 14));
		JScrollPane sp = new JScrollPane(listText);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(sp, BorderLayout.CENTER);
	}

}
