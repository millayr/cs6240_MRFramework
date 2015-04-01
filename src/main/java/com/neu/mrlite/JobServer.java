package com.neu.mrlite;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class JobServer implements Runnable {
	private static final int SIGTERM = 30;
	int intr = 0;
	IOHandle io;
	List<JobServlet> servlets;
	private static JobServer jobServer = null;
	
	private JobServer(IOHandle io) {
		this.io = io;
		servlets = new ArrayList<JobServlet>();
	}
	
	public void run() {
		try (ServerSocket socket = new ServerSocket(2120))
		{
			System.out.println("Opened server connection:"+socket.getLocalPort());
			while(!isInterrupted()) {
				if(servlets.size() == Constants.NODES)
				{
					boolean fin = true;
					for(JobServlet job: servlets) {
						fin = fin && job.isInterrupted();
					}
					if(fin)
						this.interrupt(SIGTERM);
				} else {
					JobServlet servlet = new JobServlet(socket.accept(), io);
					servlets.add(servlet);
					new Thread(servlet).start();
				}
			}
			System.out.println("Closed server connection:"+socket.getLocalPort());
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isInterrupted() {
		return (intr == 30);
	}
	
	public void interrupt(int SIGNAL) {
		intr = SIGNAL;
	}
	
	public static void startJobServer(IOHandle io) {
		if(jobServer == null) {
			jobServer = new JobServer(io); 
			new Thread(jobServer).start();
		}
	}
}
