package net.keeratipong.arachne.crawler;

import java.util.Observable;

public class CrawlerState extends Observable {

	private static CrawlerState instance;

	private String domain;
	private String currentUrl;
	private String lastEmailFound;
	
	public static CrawlerState getInstance() {
		if (instance == null) {
			instance = new CrawlerState();
		}
		return instance;
	}

	private CrawlerState() {
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}
	
	public String getCurrentUrl() {
		return currentUrl;
	}
	
	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}
	
	public void setLastEmailFound(String lastEmailFound) {
		this.lastEmailFound = lastEmailFound;
	}
	
	public String getLastEmailFound() {
		return lastEmailFound;
	}
	
}
