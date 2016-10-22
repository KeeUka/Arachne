package net.keeratipong.arachne.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileHandler {

	public static List<String> readFile(String name) throws FileNotFoundException, IOException {
		return IOUtils.readLines(new FileInputStream(name), "UTF-8");
	}

}
