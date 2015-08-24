/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1999
//Author:       Lars Roe, under Don Alstad
//Company:      University of Minnesota
//Description:  Container that rotates its component

package edu.umn.ecology.populus.visual;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

/**
  * A panel that simply holds another component that can be rotated.
  * Currently we rotate by 90-degree turns, counterclockwise
  */
public class RotatablePanel extends JComponent {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1818310346143966701L;
private int turns = 0; //90 degree turns counterclockwise
   private Component content = null;

   public RotatablePanel( Component c ) {
      this();
      setContent( c );
   }

   /////////////////////////////////////////////
   //////  ROTATION STUFF       ////////////////
   /////////////////////////////////////////////

   public Dimension getMinimumSize() {
      return possiblyRotate( getContent().getMinimumSize() );
   }

   public Dimension getPreferredSize() {
      return possiblyRotate( getContent().getPreferredSize() );
   }

   /**
     * Rotates the bounds for the function if necessary. <br>
     * I am not so sure about this function
     */

   public void setBounds( int x, int y, int width, int height ) {
      super.setBounds( x, y, width, height );
      if( turns % 2 != 0 ) {
         getContent().setBounds( x, y, height, width );
      }
      else {
         getContent().setBounds( x, y, width, height );
      }
   }

   public void setTurns( int newTurns ) {
      turns = newTurns;
      repaint();
   }

   public Component getContent() {
      return content;
   }

   public RotatablePanel() {
      try {
         jbInit();
      }
      catch( Exception ex ) {
         ex.printStackTrace();
      }
   }

   public void paint(Graphics g) {
       if (isRotate()) {
           g = rotateGraphics(g);
       }
       getContent().paint(g);
       return;
   }

/*
   static boolean doP = true;
   public void paint(Graphics g) {
       if (!doP) return;
       if (isRotate()) {
           g = rotateGraphics(g);
       }
       getContent().paint(g);
   }

   static boolean doPC = true;
   protected void paintChildren(Graphics g) {
       if (doPC) super.paintChildren(g);
       return;
   }
   */

   /**
     * Returns true if it is not in the default state
     * (i.e. <b>any</b> rotation)
     */
   public boolean isRotate() {
      return ( turns != 0 );
   }

   public int getTurns() {
      return turns;
   }

   /**
     * <code>true</code> sets to one turn;
     * <code>false</code> sets to no turns;
     */

   public void setRotate( boolean newRotate ) {
      if( newRotate ) {
         setTurns( 1 );
      }
      else {
         setTurns( 0 );
      }
   }

   public void setContent( Component newContent ) {
      content = newContent;
   }

   public Dimension getMaximumSize() {
      return possiblyRotate( getContent().getMaximumSize() );
   }

   //*/

   public static Dimension invertDimension( Dimension d ) {
      return new Dimension( d.height, d.width );
   }

   protected void addImpl( Component c, Object constraints, int index ) {
      //super.addImpl(new RotatedComponent(c), constraints, index);
      //throw new Error("Can't add to this rotatable panel");
      //edu.umn.ecology.populus.fileio.Logging.log("addImpl called in RotPanel");
      setContent( c );
   }

   /**
     * Returns a Dimension object equivalent to rotating the input
     * Dimension by the number of turns
     */

   protected Dimension possiblyRotate( Dimension d ) {
      if( turns % 2 != 0 ) {
         d = invertDimension( d );
      }
      return d;
   }

   /** This function here rotates the HTMLLabels. For some reason, this rotation causes
     * the label to become invisible on Mac OS X, for whatever reason.
     * In the process of debugging this problem, it was discovered that the commented code
     * at the bottom of the function works well.
     * 	g2.setColor(Color.blue);
     * 	g2.fillRect(0,0,200,200);
     * 	g2.setColor(Color.white);
     * 	g2.fillOval(0,0,10,40);
     *	The ovals show up exactly as they would be expected to, but the text is not there.
     * This problem is yet to be solved.
     */

   protected Graphics rotateGraphics( Graphics g ) {
      Graphics2D g2;
      g2 = (Graphics2D)g;
      AffineTransform xform = new AffineTransform();
      switch( turns ) {
         case 0:
             break;

         case 1:
             xform.setToRotation( -Math.PI / 2 );
             g2.transform( xform );
             xform.setToTranslation( (double)-getSize().height, 0.0 );
             g2.transform( xform );
             break;

         case 2:
             xform.setToRotation( -Math.PI );
             g2.transform( xform );
             xform.setToTranslation( (double)-getSize().width, (double)-getSize().height );
             g2.transform( xform );
             break;

         case 3:
             xform.setToRotation( -Math.PI * 3 / 2 );
             g2.transform( xform );
             xform.setToTranslation( 0.0, (double)-getSize().width);
             g2.transform( xform );
             break;
      }

      return g2;
   }

   private void jbInit() throws Exception {

   }

/**
  * Big time kludge. Before, printing rotated components came out VERY small.
  *
  public void print(Graphics g) {
  if (!isRotate()) {
  super.print(g);
  } else {
  //create image and paint from it.
  Dimension d = getSize();
  Image image = this.createImage(d.width, d.height);
  Graphics gr = image.getGraphics();
  gr.setColor(Color.white);
  gr.fillRect(0, 0, image.getWidth(this), image.getHeight(this));
  paintAll(gr);
  g.drawImage(image, 0, 0, this);
  }
  } //*/
}
