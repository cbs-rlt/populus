package edu.umn.ecology.populus.fileio;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class Logging {
	/* Normal log */
	static final int kInfo = 0;
	/* Not clear why this happened, but should be recoverable */
	public static final int kHuh = 10;
	/* Potentially very serious */
	public static final int kErr = 20;

	private Logging() {
	}

   public static void log() {
      log("");
   }
   public static void log(String msg) {
      log(msg, kInfo);
   }
   public static void log(String msg, int severity) {
		System.out.println(msg);
	}
   public static void log(Exception e) {
      e.printStackTrace();
   }
}
