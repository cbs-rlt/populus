package edu.umn.ecology.populus.fileio;

import java.io.PrintStream;
import java.util.Vector;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006, 2015</p>
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
	public static final int kWarn = 10;
	/* Potentially very serious */
	public static final int kErr = 20;

	private static Logging singleLog;

	private Vector<PrintStream> ps;
	private static PrintStream getLogFileAsStream(String filename) {
		try {
			return new PrintStream(filename);
		} catch (Exception e) {
			Logging.log(e, "Could not get log file:");
		}
		return null;
	}

	private Logging() {
		ps = new Vector<PrintStream>();
		ps.add(System.err);
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

	/** Returns true if able to add to it */
	public static synchronized boolean addFileLog(String filename) {
		PrintStream fstrm = getLogFileAsStream(filename);
		if (fstrm != null) {
			singleLog.ps.add(fstrm);
			return true;
		}
		return false;
	}

	public static String getDefaultLogFile() {
		String fileout = "";
		fileout = System.getProperty("user.home") + System.getProperty("file.separator");
		fileout += "populus.log.txt";
		return fileout;
	}
	static {
		if (null == singleLog) {
			singleLog = new Logging();
		} else {
			Logging.log(new Exception("Stack trace"), "Why init logging twice?");
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
