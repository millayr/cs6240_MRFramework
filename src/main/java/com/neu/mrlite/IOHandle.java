package com.neu.mrlite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Input and output handle for reading and writing from parallel workers.
 * This will also abstract the in-memory executions from mapper and reducer.
 * Map and reduce will perform as they read and wrote to HDFS, but it may
 * not be the case always and it completely depends on the implementations
 * of this class.
 *
 * @author nikit
 *
 */
public class IOHandle {
	/**
	 * I/O Handles
	 */
	private static String inFile;
	private static String outDir;
	private BufferedReader fr;
	private PrintStream fw;
	
	/**
	 * Input file path and output directory are both HDFS path in future scope 
	 * @param inFile
	 * @param outDir
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	public IOHandle(String inFile, String outDir) throws FileNotFoundException, UnsupportedEncodingException {
		IOHandle.inFile = inFile;
		IOHandle.outDir = outDir;
		getIOHandle();
	}
	
	public IOHandle(IOHandle io) throws FileNotFoundException, UnsupportedEncodingException {
		getIOHandle();
	}
	
	private void getIOHandle() throws FileNotFoundException {
		fr = new BufferedReader(new FileReader(inFile));
		File dir = new File(outDir);
		if(!dir.isDirectory()) {
			dir.mkdirs();
		}
		fw = new PrintStream(new FileOutputStream(outDir+"/part-0", true));
	}
	/**
	 * Close the I/O Handles safely
	 */
	public void close() {
		try {
			fw.flush();
			fr.close();
			fw.close();
		} catch(Exception e) {
			System.out.println("error @ ioHandle.close()");
		}
	}
	
	/**
	 * Read the file line by line, It is just a wrapper function
	 * @return
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return fr.readLine();
	}
	
	/**
	 * write the output object to a file
	 * @param o
	 */
	public void write(Object o) {
		fw.println(o);
	}
}