package edu.umn.ecology.populus.fileio;
import edu.umn.ecology.populus.core.*;
import java.awt.*;
import java.io.*;
import javax.swing.*;

public class IOUtility {
   public static Exception exception = null;

   static class ExtensionFileFilter implements java.io.FileFilter {
      private String ext;

      ExtensionFileFilter(String str) {
         this.ext = str;
      }

      public boolean accept(File f) {
         String end = f.getName();
         int idx = end.length() - ext.length();
         String suffix = end.substring(idx);
         if (suffix.equals(ext))
            return true;
         else
            return false;
      }
   }

   /**
     * returns a String with the file name, or null if cancelled
     * @param type Use either FileDialog.SAVE, or FileDialog.LOAD
     */

    //Lars - Need to verify

   public static String getFileName( String baseName, String extension, String title, int type ) {
      //Find first name of form  baseName + int + extension, where int starts with 1 and goes until
      // we don't have a file like that.
      String fullName = baseName + "1" + extension;
      {
         File dir = new File(PopPreferences.getDirectory());
         if (dir.isDirectory()) {
            File[] files = dir.listFiles(new ExtensionFileFilter(extension));
            int index = 0;
            boolean fileExists = true;
            do {
               index++;
               fileExists = false;
               for (int i = 0; i < files.length; i++) {
                  if (files[i].getName().equals(baseName + index + extension)) {
                      fileExists = true;
                      break;
                  }
               }
            } while (fileExists);
            fullName = baseName + index + extension;
         }
      }
      return getFileName(fullName, title, type);
   }
   /**
    * returns a String with the file name, or null if cancelled
    * @param type Use either FileDialog.SAVE, or FileDialog.LOAD
    */

   public static String getFileName( String defaultName, String title, int type ) {
      String filename = null;
      if( PopPreferences.isUseAWTFileDialog() ) {
         FileDialog fd;

         //get filename
         fd = new FileDialog( DesktopWindow.defaultWindow, title, type );
         fd.setDirectory( PopPreferences.getDirectory() );
         fd.setFile( defaultName );

         //get user input
         Point pos = DesktopWindow.defaultWindow.getLocationOnScreen();

         /*
         Dimension d0 = DesktopWindow.defaultWindow.getSize();
         fd.validate();
         Dimension d1 = fd.getPreferredSize();
         edu.umn.ecology.populus.fileio.Logging.log(pos);
         edu.umn.ecology.populus.fileio.Logging.log(d0);
         edu.umn.ecology.populus.fileio.Logging.log(d1);
         */
         fd.setVisible( true );
         fd.setLocation( pos.x + 20, pos.y + 40 );
         fd.setResizable( true );
         filename = fd.getFile();
      }
      else {
         JFileChooser fc;
         int state;

         //get filename
         fc = new JFileChooser( PopPreferences.getDirectory() );
         fc.setSelectedFile( new File( fc.getCurrentDirectory(), defaultName ) );
         fc.setFileFilter( new BasicFilenameFilter( defaultName.substring( 1 + defaultName.indexOf( '.' ) ) ) );

         //get user input
         if( type == FileDialog.SAVE ) {
            state = fc.showSaveDialog( DesktopWindow.defaultWindow );
         }
         else {
            state = fc.showOpenDialog( DesktopWindow.defaultWindow );
         }
         if( state == JFileChooser.APPROVE_OPTION ) {
            filename = fc.getSelectedFile().toString();
            PopPreferences.setDirectory( fc.getCurrentDirectory().toString() );
         }
      }
      return filename;
   }

/*
public HTMLDocument getHTMLFromString(String source) {
HTMLDocument doc = new HTMLDocument();
JEditorPane pane = new JEditorPane(
AbstractDocument.Content = new AbstractDocument.Content
HTMLDocument specificDocument = new HTMLDocument(Contentc, StyleSheetstyles);
Content c = new Content(
}
*/
}
