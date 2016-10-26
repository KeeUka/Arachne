package net.keeratipong.arachne.crawler;

public class CrawlerState {

	private static CrawlerState instance;

	private String domain;

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

}
