package net.keeratipong.arachne.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Telneter {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;

	private String host;
	private int port;

	public Telneter(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	}

	public String write(String input) throws UnsupportedEncodingException, IOException {
		dos.write(input.getBytes("UTF-8"));
		dos.flush();
		return dis.readLine();
	}
	
	public void close() throws IOException {
		dos.close();
		dis.close();
		socket.close();
	}

}
