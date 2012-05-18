package edu.umn.ecology.populus.help;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import edu.umn.ecology.populus.fileio.Logging;

//import com.adobe.acrobat.*;

/**
 * Use the Adobe PDF Reader Bean.  Currently disabled to use what user plugs in.
 * To enable:
 * 1) add the AdobeBean JAR file to the classpath (in several places, including the installer),
 * 2) uncomment the code in here
 * 3) fix the file name for the pdf file
 * @author Lars Roe
 * @version 5.4
 */

public class OpenPDF {
	//TODO - This is bad...
   String kFilename = "C:\\dev\\doc\\developers\\PDFOpenParameters.pdf";

   /**
    * Opens PDF with named destination
    */
   public OpenPDF(String namedDest) {
      try {
         JFrame frame = new JFrame("Test Viewer");
         frame.setLayout(new BorderLayout());
         /*
         Viewer viewer = new Viewer();
         frame.add(viewer, BorderLayout.CENTER);
         InputStream input = new FileInputStream (new File(kFilename));
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
   public static void open(String dest) {
      new OpenPDF(dest);
   }
}