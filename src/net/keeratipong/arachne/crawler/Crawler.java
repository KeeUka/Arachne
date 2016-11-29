package net.keeratipong.arachne.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {

	public static final String EMAIL_PATTERN = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return whitelisted(href) && href.startsWith(CrawlerState.getInstance().getDomain());
	}
	
	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		CrawlerState.getInstance().setCurrentUrl(url);
		CrawlerState.getInstance().increaseUrlCount();
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			List<String> emails = extractEmails(html.toString());
			for (int i = 0; i < emails.size(); i++) {
				CrawlerState.getInstance().setLastEmailFound(emails.get(i));
			}
		}
	}
	
	private boolean whitelisted(String href) {
		String sPattern = ".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$";
		Pattern pattern = Pattern.compile(sPattern);
		return !pattern.matcher(href).matches();
	}

	public static List<String> extractEmails(String source) {
		List<String> emails = new ArrayList<String>();
		Matcher m = Pattern.compile(EMAIL_PATTERN).matcher(source);
		while (m.find()) {
			emails.add(m.group());
		}
		return emails;
	}
	
}
