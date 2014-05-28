package edu.umn.ecology.populus.visual;
import javax.swing.*;
import java.io.*;
import java.awt.*;

/** A HTMLLabel is parsed into several HTMLSubLabels.*/

public class HTMLSubLabel extends JLabel implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -8870358049640670979L;
   public static final float SCRIPT_SHRINKAGE = 0.8f;
   private int supLevel = 0;
   private Font defaultFont;
   private boolean bar = false;
   private boolean isSpecial = false;

   public HTMLSubLabel() {
      this( "BLANK", new Font( "Serif", Font.PLAIN, 12 ), Color.red, 0, false );
   }

   public HTMLSubLabel( String text, Font font, Color color, int supLevel, boolean topBar ) {
      super( text, JLabel.CENTER );
      this.setFont( font );
      this.defaultFont = font;
      this.setForeground( color );
      this.bar = topBar;
      this.setSupLevel( supLevel );
   }

   public HTMLSubLabel( String specialChar, Font f, Color c ){
      super("q",JLabel.CENTER);
      isSpecial = true;
      this.setFont( f );
      defaultFont = f;
      this.setForeground( c );
   }

   public int getSupLevel() {
      return supLevel;
   }

   public int getRegularHeight() {
      return getFontMetrics( defaultFont ).getHeight();
   }

   public boolean isBar() {
      return bar;
   }

   public void setSupLevel( int newSupLevel ) {
      if( supLevel == newSupLevel ) {
         return ;
      }
      supLevel = newSupLevel;
      switch( supLevel ) {
         case -1:
             this.setFont( shrinkFont( getFont() ) );
             //this.setVerticalAlignment( JLabel.BOTTOM );
             //this.setVerticalTextPosition( JLabel.BOTTOM );
             break;

         case 1:
             this.setFont( shrinkFont( getFont() ) );
             //this.setVerticalAlignment( JLabel.TOP );
             break;

         case 0:
             this.setFont( defaultFont );
             //this.setVerticalAlignment( JLabel.CENTER );
             break;

         default:
             //ERROR
             System.err.println( "Unknown supLevel: " + newSupLevel );
      }
      repaint();
   }

   /**This method paints the HTMLSubLabel
    * according to the specifications originally in the text string.
    * this paints the whole thing "manually" meaning it relies on nothing else to do
    * it. i don't think i regret adding this method anymore... (as opposed to letting
    * the label draw itself)
    */
   public void paint( Graphics g ) {
      g.setFont( getFont() );
      g.setColor( getForeground() );
      Rectangle r = getBounds();

      /*shift is the distance the text will shift if it is super- or sub-script*/
      double shift = 0.0d;
      /*a is the standard scale for how much things are shifted in q-hat, super/sub scripts etc
        maybe this should be determined by the height of the string...*/
      double a = r.getHeight()/4;

      /*so far, only q-hat is implemented. q-hat is used in Selection and Mutation only*/
      if(isSpecial){
         g.drawString( "q", 0, (int)(getHeight()-a) );
         g.drawLine(0, (int)a, (int)getWidth()/2, (int)(a/2) );
         g.drawLine((int)getWidth()/2, (int)(a/2), (int)r.getWidth(), (int)a );
         /*and none of the rest of this method is relavent*/
         return;
      }
      /*setting the amount of vertical shift super and sup scripts are given*/
      switch (supLevel){
         case -1: shift =  a/2.0d; break;
         case  1: shift = -a/2.0d; break;
      }

      /*drawing the text proper*/
      g.drawString( getText(), 0, (int)(getHeight() - a + shift) );

      /*drawing a bar over the text. e.g. w-bar*/
      if( bar ) g.drawLine( 0, (int)(a/2), (int)r.getWidth(), (int)(a/2) );
   }

   //may return negative #'s! (but rare, and only in very strange situations)
   public int getDistanceFromTop() {
      if( getSupLevel() > 0 ) {
         return 0;
      }
      else {
         int a, b;
         a = this.getRegularHeight();
         b = this.getFontMetrics( getFont() ).getHeight();
         return a - b;
      }
   }

   void readObject( ObjectInputStream ois ) throws ClassNotFoundException,IOException {
      ois.defaultReadObject();
   }

   void writeObject( ObjectOutputStream oos ) throws IOException {
      oos.defaultWriteObject();
   }

   private Font shrinkFont( Font oldFont ) {
      return new Font( oldFont.getName(), oldFont.getStyle(), (int)( ( (float)oldFont.getSize() ) * SCRIPT_SHRINKAGE ) );
   }
}
