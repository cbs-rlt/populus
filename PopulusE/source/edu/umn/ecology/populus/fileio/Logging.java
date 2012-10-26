package edu.umn.ecology.populus.fileio;

import java.io.PrintStream;
import java.util.Vector;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 * 
 * This is not a very clever class, but was meant to be extensible in case
 * we wanted to redirect the output elsewhere.
 */

public class Logging {
	/* Normal log */
	public static final int kInfo = 0;
	/* Not clear why this happened, but should be recoverable */
	public static final int kHuh = 10;
	/* Potentially very serious */
	public static final int kErr = 20;
	
	private static Logging singleLog;

	private Vector<PrintStream> ps;
	private static PrintStream getLogFile() {
		try {
			String fileout = "";
			fileout = System.getProperty("user.home") + System.getProperty("file.separator");
			fileout += "populus.log.txt";
			return new PrintStream(fileout);
		} catch (Exception e) {
			System.err.println("Could not get log file:");
			System.err.println(e);
		}
		return null;
	}
	
	private Logging() {
		ps = new Vector<PrintStream>();
		ps.add(System.err);
		PrintStream fstrm = getLogFile();
		if (fstrm != null) {
			ps.add(fstrm);
		}
	}
	private void log1(String msg, int severity) {
		for (PrintStream s : ps) {
			s.println(msg);
		}		
	}
	private void log1(Exception e) {
		for (PrintStream s : ps) {
			e.printStackTrace(s);
		}
	}	
	
	
	public static void init() {
		if (null == singleLog) {
			singleLog = new Logging();
		}
	}
	public static void cleanup() {
		//We really should do this explicitly...
	}

	public static void log() {
		log("");
	}
	public static void log(String msg) {
		log(msg, kInfo);
	}
	public static void log(String msg, int severity) {
		singleLog.log1(msg, severity);
	}
	public static void log(Exception e) {
		singleLog.log1(e);
	}
	public static void log(Exception e, String msg) {
		singleLog.log1("Got exception: " + msg, kErr);
		singleLog.log1(e);
	}
}
