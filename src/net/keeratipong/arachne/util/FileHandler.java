package net.keeratipong.arachne.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileHandler {

	public static List<String> readFile(String path) throws FileNotFoundException, IOException {
		return IOUtils.readLines(new FileInputStream(path), "UTF-8");
	}

	public static void writeFile(String s, String path) throws IOException {
		FileUtils.writeStringToFile(new File(path), s, true);
	}
	
}
