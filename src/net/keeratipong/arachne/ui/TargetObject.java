package net.keeratipong.arachne.ui;

import java.util.ArrayList;
import java.util.List;

public class TargetObject {

	private String target;
	private String keyword;
	private List<String> linksCrawled;
	private List<String> emails;
	
	public TargetObject() {
		linksCrawled = new ArrayList<String>();
		emails = new ArrayList<String>();
	}
	
	public void refresh(String target, String keyword) {
		this.target = target;
		this.keyword = keyword;
		linksCrawled.clear();
		emails.clear();
	}
	
	public void addLink(String link) {
		linksCrawled.add(link);
	}
	
	public void addEmail(String email) {
		if(emails.contains(email)) {
			return;
		}
		emails.add(email);
	}
	
	public String toString() {
		String output = "#############################\n";
		output += ("Key: " + keyword + "\n");
		output += ("Root: " + target + "\n");
		output += ("Links Crawled Count: " + linksCrawled.size() + "\n");
		output += ("Emails Found Count: " + emails.size() + "\n");
		output += "Emails:\n";
		for(String email: emails) {
			output += (email + "\n");
		}
		return output;
	}
	
}
