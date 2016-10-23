package net.keeratipong.arachne.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.keeratipong.arachne.util.FileHandler;

public class Arachne {

	public static final String INPUT_PATH = "./input.txt";
	public static final String HOST = "localhost";
	public static final int PORT = 32000;
	public static final int ACTION_DELAY = 1000;

	private BrowserHook browserHook;
	private List<String> input;
	private List<Result> results;

	public Arachne() {
		browserHook = new BrowserHook(HOST, PORT);
		input = new ArrayList<String>();
		results = new ArrayList<Result>();
	}

	public void reloadInput() throws FileNotFoundException, IOException {
		input = FileHandler.readFile(INPUT_PATH);
	}

	public List<String> getInput() {
		return input;
	}
	
	public boolean hasMoreInput() {
		return input != null && !input.isEmpty();
	}
	
	public List<Result> getResults() {
		return results;
	}

	public void processNextInput() {
		if(input.isEmpty()) {
			return;
		}
		// Do something
		String key = input.remove(0);
		Result result = new Result(key);
		results.add(result);
	}
	
	public BrowserHook getBrowserHook() {
		return browserHook;
	}

	public String googleQuery(String searchTerm) {
		return searchTerm.replaceAll("\\s+", "+");
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Arachne a = new Arachne();
		a.reloadInput();
		
		// System.out.println(a.getInput().size());
		// a.getBrowserHook().start();
		// a.getBrowserHook().setUrlAndWaitForChange("http://www.google.com");
		// String googleUrl = a.getBrowserHook().getCurrentUrl();
		// String searchUrl = googleUrl + "#q=keeratipong";
		// a.getBrowserHook().setUrlAndWaitForChange(searchUrl);
		// System.out.println(a.getBrowserHook().getCurrentUrl());

		// // System.out.println(a.getBrowserHook().getCurrentUrl());
		// a.getBrowserHook().setUrl("https://www.google.co.th/search?q=test&ie=utf-8&oe=utf-8&client=firefox-b-ab&gws_rd=cr&ei=ZmcKWLegAsX-vgTZkYWYDw#q=keeratipong");
		//
		// System.out.println(a.getBrowserHook().getPageHtml());
		// List<String> links =
		// a.getBrowserHook().getAllAbsoluteWhiteListLinks();
		// String firstLink = links.get(0);
		// System.out.println("First link = " + firstLink);
		// // a.getBrowserHook().setUrl(firstLink);
		// for(String s : links) {
		// System.out.println(s);
		// }
	}

}