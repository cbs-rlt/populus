/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.help;

import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.fileio.Logging;

/**
 * Use the Adobe PDF Reader Bean.  Currently disabled to use what user plugs in.
 * To enable:
 * 1) add the AdobeBean JAR file to the classpath (in several places, including the installer),
 * 2) uncomment the code in here
 * 3) fix the file name for the pdf file
 * @author Lars Roe
 * @version 5.4
 */

public class OpenPDFWithAdobeBean {

	/**
	 * Opens PDF with named destination
	 */
	public OpenPDFWithAdobeBean(String filename, String namedDest) {
		try {
			JFrame frame = new JFrame("Test Viewer"); //Todo - must make sure we have a singleton JFrame
			frame.setLayout(new BorderLayout());

			/*
         com.adobe.acrobat.Viewer viewer = new com.adobe.acrobat.Viewer();
         frame.add(viewer, BorderLayout.CENTER);
         java.io.InputStream input = new java.io.FileInputStream (new java.io.File(filename));
         viewer.setDocumentInputStream(input);
         viewer.activate();
         if (namedDest != null)
            viewer.gotoNamedDest(namedDest);
			 */

			frame.setSize(400, 500);
			frame.setVisible(true);
		} catch (Exception e) {
			Logging.log("Couldn't open PDF");
		} catch (Error e) {
		}
	}
	public static void open(String filename, String dest) {
		new OpenPDFWithAdobeBean(filename, dest);
	}
}