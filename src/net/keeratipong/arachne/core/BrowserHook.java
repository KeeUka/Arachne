package net.keeratipong.arachne.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class BrowserHook {

	public static final int LOOP_DELAY = 200;
	
	private Telneter telneter;
	private String host;
	private int port;

	public BrowserHook(String host, int port) {
		this.host = host;
		this.port = port;
		telneter = new Telneter(host, port);
	}

	public void start() throws UnknownHostException, IOException {
		telneter.connect();
	}

	public void setUrl(String url) throws UnsupportedEncodingException, IOException {
		telneter.write("window.location.href = \""  + url +"\"");
	}
	
	public void setUrlAndWaitForChange(String url) throws UnsupportedEncodingException, IOException {
		String currentUrl = getCurrentUrl();
		telneter.write("window.location.href = \""  + url +"\"");
		while(true) {
			sleep();
			String newUrl = getCurrentUrl();
			if(!newUrl.equals(currentUrl)) {
				break;
			}
		}
	}
	
	public String getCurrentUrl() throws UnsupportedEncodingException, IOException {
		String s = telneter.write("window.location.href");
		String result = "";
		try {
			result = new JSONObject(s).get("result").toString();
		} catch (JSONException e) {
			return "";
		}
		return result;
	}

	public String getPageHtml() throws UnsupportedEncodingException, IOException {
		String s = telneter.write("document.documentElement.innerHTML");
		return s;
	}

	public List<String> getAllLinks() throws UnsupportedEncodingException, IOException {
		String page = getPageHtml();
		Pattern p = Pattern.compile("href=\\\\\"(.*?)\\\\\"");
		List<String> links = new ArrayList<String>();
		Matcher m = p.matcher(page);
		while (m.find()) {
			String trimmedFront = m.group().substring(7);
			String trimmedEnd = trimmedFront.substring(0, trimmedFront.length() - 2);
			links.add(trimmedEnd);
		}
		return links;
	}

	public List<String> getAllAbsoluteLinks() throws UnsupportedEncodingException, IOException {
		List<String> links = getAllLinks();
		List<String> absLinks = new ArrayList<String>(); 
		for(String l : links) {
			if(l.startsWith("http")) {
				absLinks.add(l);
			}
		}
		return absLinks;
	}
	
	public List<String> getAllAbsoluteWhiteListLinks() throws UnsupportedEncodingException, IOException {
		List<String> links = getAllAbsoluteLinks();
		List<String> whitelistedLinks = new ArrayList<String>(); 
		for(String l : links) {
			if(l.contains("webcache.googleusercontent") || !l.contains("google") && !l.contains("youtube")) {
				whitelistedLinks.add(l);
			}
		}
		return whitelistedLinks;
	}
	
	private void sleep() {
		try {
			Thread.sleep(LOOP_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}
