package edu.umn.ecology.populus.help;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.core.PopPreferences;
import edu.umn.ecology.populus.fileio.Logging;

public class PDFHelpFileMgr {
	/** Return location of help file outside of JAR, and copy the file out if needed
	 * 
	 * @return name of file
	 */
	private static void setupHelpFileFirst(boolean forceAlwaysUpdate) {
		boolean shouldRun = forceAlwaysUpdate;
		if (!shouldRun && isLocalFile()) {
			File f = new File(getHelpFileAsFileName());
			if(null != f && (!f.canRead() || f.length() < 100)){
				Logging.log("Can't read file " + f + ", will attempt to set up.");
				shouldRun = true;
			}
		}
		
		if (shouldRun) {
			HelpConfigPanel.launchWindow();
		}
	}


	private static String getHelpFileLocation() {
		setupHelpFileFirst(false);
		return PopPreferences.getHelpFileLocation();
	}
	public static String getHelpFileLocationURI() {
		return PDFHelpFileMgr.getHelpFileLocation();
	}

	public static String getHelpFileAsFileName() {
		String str = PDFHelpFileMgr.getHelpFileLocation();
		try {
			return new File(new URI(str)).getAbsolutePath();
		} catch (Exception e) {
			Logging.log("File name not a file");
		}
		return "";
	}
	private static boolean isLocalFile() {
		String str = PopPreferences.getHelpFileLocation();
		return (str.startsWith("file:///"));
	}
	public static String getHelpLang() {
		return PopPreferences.getHelpLang();
	}
	public static String getHelpLangSourceFile() {
		String lang = getHelpLang();
		if (lang.equalsIgnoreCase("Portuguese")) {
			return "PopulusHelpPortuguese5.5.pdf";
		} else if (lang.equalsIgnoreCase("Spanish")) {
			return "PopulusHelpEspagnol5.5.pdf";		   
		} else {
			return "PopulusHelpEnglish5.5.pdf";		   
		}
	}
	
}
