package edu.umn.ecology.populus.visual;

import edu.umn.ecology.populus.fileio.Logging;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.Font;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class TrueHTMLLabel extends JLabel implements HTMLConstants {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5293871811495683773L;
private int turns = 0;

   public TrueHTMLLabel() {
   }

   public void setText( String text ) {
      if( text == null ) {
         text = "";
      }
      else {
         text = Utilities.PopHTMLToSwingHTML(text);
      }
      super.setText(text);
   }

   public String getText() {
      String text = super.getText();
      if (text.startsWith("<html>")) {
         text = text.substring(6);
         if (text.endsWith("</html>"))
            text = text.substring(0, text.length() - 7);
      }
      return text;
   }

   public boolean getHasHTMLLabels() { return true; }
   public void setHasHTMLLabels(boolean b) {
      if (!b) {
         edu.umn.ecology.populus.fileio.Logging.log("We shouldn't set hasHTMLLabels to false here!");
      }
   }

   public void setDefaultFont(Font newDefaultFont) {
      setFont(newDefaultFont);
   }

   public void setDefaultColor(Color c) {
      setForeground(c);
   }

   // ROTATION STUFF
   public void setTurns(int newTurns) {
      this.turns = newTurns;
      AffineTransform rot = AffineTransform.getRotateInstance(Math.PI * turns / 2.0);
      Font f = this.getFont().deriveFont(rot);
      this.setFont(f);
   }
   public int getTurns() {
      return turns;
   }
   public void setDirection( int newDirection ) {
      int turns = dirToTurns(newDirection);
      setTurns(turns);
   }
   public int getDirection() {
      return turnsToDir(getTurns());
   }

   public boolean isRotate() {
      return ( getDirection() != NORMAL );
   }

   public void setRotate( boolean newRotate ) {
      int direction;
      if( newRotate ) {
         direction = DOWN_TO_UP;
      }
      else {
         direction = NORMAL;
      }
      setDirection(direction);
   }

   private static final int dirToTurns(int dir) {
      switch (dir) {
         default:
            Logging.log("Invalid case", Logging.kErr);
         case NORMAL:
            return 0;
         case DOWN_TO_UP:
            return 1;
         case UPSIDEDOWN:
            return 2;
         case UP_TO_DOWN:
            return 3;
      }
   }
   private static final int turnsToDir(int turns) {
      switch (turns) {
         default:
            Logging.log("Invalid case", Logging.kErr);
         case 0:
            return NORMAL;
         case 1:
            return DOWN_TO_UP;
         case 2:
            return UPSIDEDOWN;
         case 3:
            return UP_TO_DOWN;
      }
   }
}
