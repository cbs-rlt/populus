package edu.umn.ecology.populus.fileio;
import edu.umn.ecology.populus.core.*;
import java.awt.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GraphicsSaver {
   public static Exception exception = null;
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.fileio.Res" );

   public GraphicsSaver() {

   }

   /**
     * Saves a component to a jpeg file
     *
     *
     * @param c component to save as a picture
     * @param defaultName default file name for saving picture
     * @param parent a frame that the FileDialog can be modal to
     * @return true if successful
     * @author originally written by K.Kal,
     *  but significant changes and this version by Lars Roe roex0029@tc.umn.edu,
     */

   public static boolean saveJPEG( Component c, String defaultName, Frame parent ) {
      boolean worked = false;

      //Use appropriate initializer for directory here, if needed
      String defaultDirectory = PopPreferences.getDirectory();
      String filename;
      FileDialog fd;
      Component frame = null;
      java.io.FileOutputStream out = null;
      java.awt.Graphics g = null;
      java.awt.image.BufferedImage bi = null;
      try {

         //Get filename
         filename = IOUtility.getFileName( res.getString( "picture_jpg" ), res.getString( "Save_picture_in_JPEG" ), FileDialog.SAVE );
         if( filename == null ) {
            return false;
         }

         //Save it to the file
         //For some reason, I can't get Swing components
         // (descendants of JComponent, not JFrame/JWindow/JDialog)
         // to get the graphics correctly, so I use a component which
         // is not to get the graphics (frame). Note: frame need not
         // be a Frame, but in many cases it is.
         frame = c;
         while( frame instanceof JComponent ) {
            frame = frame.getParent();
         }
         java.awt.Dimension area = c.getSize();
         Point p1 = frame.getLocationOnScreen();
         Point p2 = c.getLocationOnScreen();
         bi = (java.awt.image.BufferedImage)c.createImage( area.width, area.height );
         g = bi.getGraphics();
         g.translate( (int)( p1.getX() - p2.getX() ), (int)( p1.getY() - p2.getY() ) );
         frame.paintAll( g );

         ImageIO.write(bi,"jpg", new java.io.File(filename));

         /* Old java, replaced with ImageIO (above)
         out = new java.io.FileOutputStream( filename );
         com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder( out );
         encoder.encode( bi );
         out.flush();
         out.close();
         */

         //successful if this far
         worked = true;
      }
      catch( Exception e ) {
         JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, res.getString( "Error_Saving_File" ), res.getString( "Could_Not_Save_File" ), JOptionPane.ERROR_MESSAGE );
         exception = e;
      }
      if( g != null ) {
         g.dispose();
      }
      if( bi != null ) {
         bi.flush();
      }
      return worked;
   }
}
