package com.neu.mrlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class JobServlet implements Runnable{
	private static final String TERMINATE = "30";
	private static final int SIGTERM = 30;
	private Socket socket = null;
	private IOHandle io;
	private int intr = 0;
	public JobServlet (Socket socket, IOHandle io) {
		this.socket = socket;
		this.io = io;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try (
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(
	                    socket.getInputStream()));
	        ) {
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	            	if(inputLine.equals(TERMINATE)) {
	            		this.intr = SIGTERM;
	            		break;
	            	}
	            	System.out.println(inputLine);
	            }
	            System.out.println("Closed connection from:"+socket.getPort());
	            socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public boolean isInterrupted() {
		return intr == 30;
	}
}
