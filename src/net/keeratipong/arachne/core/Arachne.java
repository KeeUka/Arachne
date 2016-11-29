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
	private List<String> inputList;
	private List<Result> results;

	public Arachne() {
		browserHook = new BrowserHook(HOST, PORT);
		inputList = new ArrayList<String>();
		results = new ArrayList<Result>();
	}

	public void reloadInputList() throws FileNotFoundException, IOException {
		inputList = FileHandler.readFile(INPUT_PATH);
	}

	public List<String> getInputList() {
		return inputList;
	}
	
	public String getNextInput() {
		return inputList.get(0);
	}
	
	public boolean hasMoreInput() {
		return inputList != null && !inputList.isEmpty();
	}
	
	public List<Result> getResults() {
		return results;
	}

	public void processNextInput() {
		if(inputList.isEmpty()) {
			return;
		}
		// Do something
		String key = inputList.remove(0);
		Result result = new Result(key);
		results.add(result);
	}
	
	public BrowserHook getBrowserHook() {
		return browserHook;
	}

	public String googleQuery(String searchTerm) {
		return searchTerm.replaceAll("\\s+", "+");
	}
}