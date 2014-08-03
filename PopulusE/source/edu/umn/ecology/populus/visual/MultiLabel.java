package edu.umn.ecology.populus.visual;
import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Lars Roe
//Company:      University of Minnesota
//Description:  Populus

/** Lars - We should combine this with HTMLMultiLabel.  They're really the same thing. */
public class MultiLabel extends JPanel {

   /**
	 * 
	 */
	private static final long serialVersionUID = 6019089739089743371L;

/**
     * lines is an array where each element is either a Component, or a String
     * (in which case it will be converted into a JLabel). If the element is neither,
     * nothing is added, and it continues to try to add successive elements (no error).
     */

   public MultiLabel( Object[] lines, Font f, int horizSpace, int vertSpace, int alignment ) {
      Component label;
      this.setLayout( new SimpleVFlowLayout() );
      for( int i = 0;i < lines.length;i++ ) {
         if( lines[i] instanceof String ) {
            label = new JLabel( (String)lines[i], alignment );
            if( f != null ) {
               label.setFont( f );
            }
         }
         else {
            if( lines[i] instanceof Component ) {
               label = (Component)lines[i];
            }
            else {
               continue;
            }
         }
         add( label );
      }
   }

   /**
     * lines is an array where each element is either a Component, or a String
     * (in which case it will be converted into a JLabel)
     */

   public MultiLabel( Object[] lines ) {
      this( lines, null, 5, 5, JLabel.CENTER ); //used to pass 'new Font("Serif", Font.PLAIN, 12)' for font
   }
}
