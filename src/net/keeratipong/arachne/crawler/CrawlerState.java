package net.keeratipong.arachne.crawler;

import java.util.Observable;

public class CrawlerState extends Observable {

	public static final String UPDATE_URL = "UPDATE_URL";
	public static final String UPDATE_EMAIL = "UPDATE_EMAIL";
	
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
		System.out.println("Domain = " + domain);
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
		setChanged();
		notifyObservers(UPDATE_URL);
	}
	
	public void setLastEmailFound(String lastEmailFound) {
		this.lastEmailFound = lastEmailFound;
		setChanged();
		notifyObservers(UPDATE_EMAIL);
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getCurrentUrl() {
		return currentUrl;
	}
	
	public String getLastEmailFound() {
		return lastEmailFound;
	}
	
}
