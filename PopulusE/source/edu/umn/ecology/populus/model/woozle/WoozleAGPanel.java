/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.woozle;

//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Lars Roe
//Company:      University of Minnesota
//Description:  Populus
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/** A panel showing agreements/generations matches */

public class WoozleAGPanel extends JScrollPane {
   /**
	 * 
	 */
	private static final long serialVersionUID = -4036338922218170237L;
public static final int DEFAULT_INCREMENT = 1;
   int bestMatch = 0;
   int mod;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.woozle.Res" );
   MainPanel mainPanel;
   static LineThroughMiddleBorder lineBorder = new LineThroughMiddleBorder();


   public WoozleAGPanel() {
      this( DEFAULT_INCREMENT );
   }

   public WoozleAGPanel( int mod ) {
      this.mod = mod;
      mainPanel = new MainPanel();
      this.getViewport().add( mainPanel, null );
      this.setHorizontalScrollBarPolicy( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
      this.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );
   //this.setLayout(new FlowLayout());
   //Necessary?
   //this.repaint();
   }


   /**
     * Adds Agreement/Generation Pair
     * Will ignore it if it is not needed
     */

   void addAG( int matches, int gen ) {

      //ROUND DOWN to nearest mod:
      matches = mod * ( matches / mod );

      //Ignore worthless values
      if( matches <= bestMatch ) {
         return ;
      }
      bestMatch = matches;
      mainPanel.add( new AGPair( matches, gen ) );
      //Adjustable adj = getHAdjustable();
      //adj.setUnitIncrement(adj.getVisibleAmount() / 4);
      this.invalidate();
      this.validate();
   }


   void clear() {
      this.getViewport().removeAll();
      this.bestMatch = 0;
      this.mainPanel = new MainPanel();
      this.getViewport().add( mainPanel, null );
   }
   private class AGPair extends WSGPanel {

      /**
	 * 
	 */
	private static final long serialVersionUID = -1139102889901023831L;

	AGPair( int m, int g ) {
         super();
         JLabel matchNum = new JLabel( String.valueOf( m ) );
         JLabel genNum = new JLabel( String.valueOf( g ) );
         add( matchNum );
         add( genNum );
      }
   }
   private class AGLabels extends WSGPanel {
      /**
	 * 
	 */
	private static final long serialVersionUID = 5134266153816859136L;
	JLabel genLabel = new JLabel( res.getString( "Generation_" ) );
      JLabel matchLabel = new JLabel( res.getString( "Agreements_" ) );

      AGLabels() {
         super();
         add( matchLabel );
         add( genLabel );
      }
   }
   private class MainPanel extends JPanel {

      /**
	 * 
	 */
	private static final long serialVersionUID = 969713433316174784L;

	MainPanel() {
         super();
         setLayout( new FlowLayout() );
         add( new AGLabels() );
      }
   }
   private class WSGPanel extends JPanel {

      /**
	 * 
	 */
	private static final long serialVersionUID = -3705059437821772649L;

	WSGPanel() {
         setLayout( new GridLayout( 2, 1 ) );
         this.setBorder( lineBorder );
      }
   }
}

class LineThroughMiddleBorder extends LineBorder {

   /**
	 * 
	 */
	private static final long serialVersionUID = 7029930001956337257L;

public void paintBorder( Component c, Graphics g, int x, int y, int width, int height ) {
      super.paintBorder( c, g, x, y, width, height );
      g.drawLine( x, y + height / 2, x + width - 1, y + height / 2 );
   }

   public LineThroughMiddleBorder() {
      super( Color.black );
   }
}
