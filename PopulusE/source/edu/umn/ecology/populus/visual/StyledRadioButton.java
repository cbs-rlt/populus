//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Lars Roe, under Don Alstad
//Company:      University of Minnesota
//Description:  First Attempt at using Java 1.2
//with Populus
package edu.umn.ecology.populus.visual;

import java.awt.Color;
import javax.swing.*;

/** This class implements the HTMLLabel utility for formatting the text
 * for radio buttons. */

public class StyledRadioButton extends JRadioButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4655637662372692469L;
	//Vector v;
    private String formattedText = "";

    public StyledRadioButton() {
        /*
               if (edu.umn.ecology.populus.core.Version.isDev()) {
          Object[] localUIDefaults1 = new String[]{"StyledRadioButtonUI",
             "edu.umn.ecology.populus.visual.StyledRadioButtonUI"};
          UIManager.getLookAndFeelDefaults().putDefaults(localUIDefaults1);
               }
         */
    }

    //Try using a clear method instead of 'new'ing the StringBuffer
    public void setText(String text) {
        super.setText(Utilities.PopHTMLToSwingHTML(text));
    }

    public String getFormattedText() {
        return formattedText;
    }
    
    public void setEnabled(boolean b) {
    	super.setEnabled(b);
    	//This should make the font look disabled, similar to HTMLLabel
    	Color currentColor = Color.gray;
    	if( b ) {
    		currentColor = (Color)UIManager.get( "Label.foreground" );
    	}
    	this.setForeground(currentColor);
    }

    /**
     * This variable is true when used in an actual application instead of an
     * IDE.
      private static boolean inited = false;

      static {
      inited = true;
      }
      static {
      Object[] localUIDefaults1 = new String[]{"StyledRadioButtonUI",
      "edu.umn.ecology.populus.visual.StyledRadioButtonUI"};
      UIManager.getLookAndFeelDefaults().putDefaults(localUIDefaults1);
      }
     */

    //If we wanted to use Swing's HTML functionality, we need to uncomment this line
    // and add code to parse into true HTML code.
    /*
    private final String uiClassID = "StyledRadioButtonUI";
    public String getUIClassID() {
        return uiClassID;
    }
     */

}
