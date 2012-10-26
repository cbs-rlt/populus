
//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

/**
  * Homemade class to imitate html-like formatting commands. The following tags
  *  are acceptable (case insensitive): <P>
  * private static String SUP_BEGIN = "<SUP>"; <br>
  * private static String SUP_END = "</SUP>"; <br>
  * private static String SUB_BEGIN = "<SUB>"; <br>
  * private static String SUB_END = "</SUB>"; <br>
  * private static String COLOR_BEGIN = "<FONT COLOR=...>"; <br>
  * &nbsp;&nbsp; Note: '...' is either name of color of 6*hex rgb number <br>
  * &nbsp;&nbsp; @see edu.umn.ecology.populus.visual.Utilities.getColor() <br>
  * private static String COLOR_END = "</FONT>"; <br>
  * private static String ITALICS_BEGIN = "<i>"; <br>
  * private static String ITALICS_END = "</i>"; <br>
  * private static String BOLD_BEGIN = "<b>"; <br>
  * private static String BOLD_END = "</b>"; <br>
  * Deprecate BAR tag:  Use \u0305 instead of bar!!
  * private static String ALL_END = "</>"; <p>
  * KNOWN ISSUES:
  *  <OL><LI>subscript bars appear as high as other bars
  *  </OL>
  * @author Lars Roe
  **/

public class HTMLLabel extends JPanel implements Serializable,HTMLConstants {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3706000911036148584L;
private Color defaultColor;
   private Color currentColor;
   private int supLevel = 0; //1 == superscript, -1 == subscript
   private int nextChar;
   private Object tagExtra;
   private Font currentFont;
   private int currentRow = 0;
   private final static int kMaxRows = 10;
   private JPanel currentRowPanel = null;

   /** If it needs to be parsed or not */
   private boolean formatted;

   /** Elements will be HTMLSubLabels */
   private int lastTag;
   private boolean topBar = false;
   private int direction = NORMAL;
   private String text = "";
   private Font defaultFont;
   private Stack componentStack = new Stack();
   private boolean hasHTMLLabels = true;

   public void setHasHTMLLabels(boolean newB){
      hasHTMLLabels = newB;
   }
   public boolean getHasHTMLLabels(){
      return hasHTMLLabels;
   }

   public void setDirection( int newDirection ) {
      direction = newDirection;
      objectNeedsUpdating();
   }

   public void setDefaultFont( Font newDefaultFont ) {

      //super.setFont(newDefaultFont);
      defaultFont = newDefaultFont;
      objectNeedsUpdating();
   }

   public void setText( String newText ) {
      if( newText == null ) {
         newText = "";
      }
      else {
         if( newText == text ) {
            return ;
         }
      }
      text = newText;
      objectNeedsUpdating();
   }

   public Font getDefaultFont() {
      return defaultFont;
   }

   public int getDirection() {
      return direction;
   }

   public String getText() {
      return text;
   }

   public void setEnabled( boolean b ) {
      super.setEnabled( b );
      objectNeedsUpdating();
   }

   // GETTERS
   //  AND
   // SETTERS

   public Color getDefaultColor() {
      return defaultColor;
   }

   public String getPlainText() {
      /*
      char tempChar = 0;
      StringBuffer tempBuffer = new StringBuffer();
      String tempString;
      if ((tempChar = text.charAt(nextChar++)) != TAG_BEGIN) {
      //ERROR!
      System.err.println("Bad char " + tempChar + ". Looking for '<'");
      }
      while ((tempChar = text.charAt(nextChar++)) != TAG_END) {
      tempBuffer.append(tempChar);
      }
      tempString = tempBuffer.toString();
      if (tempString.equalsIgnoreCase("b")) {
      lastTag = BOLD_BEGIN;
      } else if (tempString.equalsIgnoreCase("/b")) {
      lastTag = BOLD_END;
      } else if (tempString.equalsIgnoreCase("i")) {
      lastTag = ITALIC_BEGIN;
      } else if (tempString.equalsIgnoreCase("/i")) {
      lastTag = ITALIC_END;
      } else if (tempString.equalsIgnoreCase("bar")) {
      lastTag = BAR_BEGIN;
      } else if (tempString.equalsIgnoreCase("/bar")) {
      lastTag = BAR_END;
      } else if (tempString.equalsIgnoreCase("/font")) {
      lastTag = COLOR_END;
      } else if ((tempString.length() > 11) && tempString.substring(0, 11).equalsIgnoreCase("font color=")) {
      lastTag = COLOR_BEGIN;
      String tempColorName = tempString.substring(11);
      tagExtra = Utilities.getColor(tempColorName);
      } else if (tempString.equalsIgnoreCase("sup")) {
      lastTag = SUP_BEGIN;
      } else if (tempString.equalsIgnoreCase("sub")) {
      lastTag = SUB_BEGIN;
      } else if (tempString.equalsIgnoreCase("/sup")) {
      lastTag = SUP_END;
      } else if (tempString.equalsIgnoreCase("/sub")) {
      lastTag = SUB_END;
      } else if (tempString.equalsIgnoreCase("/")) {
      lastTag = ALL_END;
      } else {
      lastTag = INVALID;
      tagExtra = tempString;
      }
      */
      return null;
   }

   public boolean isRotate() {
      return ( ( direction & SIDEWAYS ) != 0 );
   }

   public void setDefaultColor( Color c ) {
      defaultColor = c;
      objectNeedsUpdating();
   }

   ///////////////////////////////
   //////   Constructors    //////
   ///////////////////////////////

   public HTMLLabel() {
      text = "";
      this.setLayout( new GridBagLayout() );
   //objectNeedsUpdating();
   }

   public void setRotate( boolean newRotate ) {
      if( newRotate ) {
         direction |= SIDEWAYS;
      }
      else {
         direction &= STACKED;
      }
      objectNeedsUpdating();
   }

   public HTMLLabel( String text ) {
      this.setText( text );
      this.setLayout( new GridBagLayout() );
      objectNeedsUpdating();
   }

   protected boolean isStacked() {

      //return ((direction & STACKED) != 0);
      return ( ( direction == UPSIDEDOWN ) || ( direction == DOWN_TO_UP ) );
   }

   protected void setStacked( boolean newStacked ) {
      if( newStacked ) {
         direction |= STACKED;
      }
      else {
         direction &= SIDEWAYS;
      }
      objectNeedsUpdating();
   }

   //
   // Visual stuff
   //

   protected void addImpl( Component c, Object constraints, int index ) {
      RotatablePanel actualComp = null;
      if( c instanceof RotatablePanel ) {
         actualComp = (RotatablePanel)c;
      } else {
         actualComp = new RotatablePanel( c );
      }
      actualComp.setTurns( getTurns() );
      if( isStacked() ) {
         componentStack.push( new StackContainer( actualComp, constraints, index ) );
      } else {
         super.addImpl( actualComp, constraints, index );
      }
   }

   protected int getTurns() {
      int turns = 0;
      switch( direction ) {
         case NORMAL:
             turns = 0;
             break;

         case DOWN_TO_UP:
             turns = 1;
             break;

         case UPSIDEDOWN:
             turns = 2;
             break;

         case UP_TO_DOWN:
             turns = 3;
             break;

         default:
             //ERROR!!!
             turns = -1;
             break;
      }
      return turns;
   }

   protected void unstack() {
      StackContainer comp;
      if( !isStacked() ) {
         return ;
      }
      while( !componentStack.isEmpty() ) {
         comp = (StackContainer)componentStack.pop();
         super.addImpl( comp.c, comp.cts, comp.index );
      }
   }

   void readObject( ObjectInputStream ois ) throws ClassNotFoundException,IOException {
      ois.defaultReadObject();
   }

   /*
   public void setBackground(java.awt.Color c) {
   super.setBackground(c);
   if (componentStack != null) {
   Enumeration e = componentStack.elements();
   Component temp;
   while (e.hasMoreElements()) {
   temp = (Component) e.nextElement();
   temp.setBackground(c);
   }
   }
   }
   */
   //
   // Serialization
   //

   void writeObject( ObjectOutputStream oos ) throws IOException {
      oos.defaultWriteObject();
   }

   synchronized void parse() {
      if( formatted ) {
         return ;
      }
      int len = text.length();
      int index = 0;
      StringBuffer tempText = new StringBuffer();
      this.removeAll();
      supLevel = 0;
      currentRow = 0;
      currentRowPanel = null;

      /*
      if (isRotate()) {
      this.setLayout(new VerticalFlowLayout(VerticalFlowLayout.MIDDLE, 0, 0, false, false));
      } else {
      this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
      }
      */
      if( defaultFont == null ) {
         defaultFont = (Font)UIManager.get( "Label.font" );
      }
      if( defaultColor == null ) {
         defaultColor = (Color)UIManager.get( "Label.foreground" );
      }
      currentFont = defaultFont;
      if( this.isEnabled() ) {
         currentColor = defaultColor;
      }
      else {
         currentColor = Color.gray;
      }
      nextChar = 0;
      while( nextChar < len ) {
         if( text.charAt( nextChar ) == TAG_BEGIN && hasHTMLLabels) {
            if( tempText.length() > 0 ) {
               addSubLabel( new HTMLSubLabel( tempText.toString(), currentFont, currentColor, supLevel, topBar ));
               tempText = new StringBuffer();
            }
            getNextTag();
            switch( lastTag ) {
               case BOLD_BEGIN:
                  currentFont = new Font( currentFont.getName(), currentFont.getStyle() | Font.BOLD, currentFont.getSize() );
                  break;

               case BOLD_END:
                  currentFont = new Font( currentFont.getName(), currentFont.getStyle() & Font.ITALIC, currentFont.getSize() );
                  break;

               case ITALIC_BEGIN:
                  currentFont = new Font( currentFont.getName(), currentFont.getStyle() | Font.ITALIC, currentFont.getSize() );
                  break;

               case ITALIC_END:
                  currentFont = new Font( currentFont.getName(), currentFont.getStyle() & Font.BOLD, currentFont.getSize() );
                  break;

               case SUP_BEGIN:
               case SUB_END:
                  supLevel++;
                  break;

               case SUP_END:
               case SUB_BEGIN:
                  supLevel--;
                  break;

               case COLOR_BEGIN:
                  currentColor = (Color)tagExtra;
                  break;

               case COLOR_END:
                  currentColor = defaultColor;
                  break;

                  /*
               case NEW_LINE:
                  currentRowPanel = null; //Lars - untested
                  break;
                  */

               case ALL_END:
                  currentColor = defaultColor;
                  currentFont = defaultFont;
                  supLevel = 0;
                  topBar = false;
                  break;

               default:
                  System.err.println( "Unknown Tag #" + lastTag + " = " + tagExtra );
            }
         } else {
            if( text.charAt( nextChar ) == '\\' ) {
               nextChar++;
            }
            tempText.append( "" + text.charAt( nextChar++ ) ); //Lars - Adding "" because JDK 1.5 is stupid.
         }
      }
      if( tempText.length() > 0 ) {
         addSubLabel( new HTMLSubLabel( tempText.toString(), currentFont, currentColor, supLevel, topBar ));
      }

      //setBackground(this.getBackground());
      formatted = true;
      unstack();
      revalidate();
   }

   private void getNextTag() {
      char tempChar = 0;
      StringBuffer tempBuffer = new StringBuffer();
      String tempString;
      if( ( tempChar = text.charAt( nextChar++ ) ) != TAG_BEGIN ) {
         //ERROR!
         System.err.println( "Bad char " + tempChar + ". Looking for '<'" );
      }
      while( ( tempChar = text.charAt( nextChar++ ) ) != TAG_END ) {
         tempBuffer.append( "" + tempChar ); //Lars - Adding "" because JDK 1.5 is stupid.
      }
      tempString = tempBuffer.toString();
      if( tempString.equalsIgnoreCase( "b" ) ) {
         lastTag = BOLD_BEGIN;
      } else if( tempString.equalsIgnoreCase( "/b" ) ) {
         lastTag = BOLD_END;
      } else if( tempString.equalsIgnoreCase( "i" ) ) {
         lastTag = ITALIC_BEGIN;
      } else if( tempString.equalsIgnoreCase( "/i" ) ) {
         lastTag = ITALIC_END;
      } else if( tempString.equalsIgnoreCase( "bar" ) ) {
         lastTag = BAR_BEGIN;
      } else if( tempString.equalsIgnoreCase( "/bar" ) ) {
         lastTag = BAR_END;
      } else if( tempString.equalsIgnoreCase( "/font" ) ) {
         lastTag = COLOR_END;
      } else if( ( tempString.length() > 11 ) && tempString.substring( 0, 11 ).equalsIgnoreCase( "font color=" ) ) {
         lastTag = COLOR_BEGIN;
         String tempColorName = tempString.substring( 11 );
         tagExtra = Utilities.getColor( tempColorName );
      } else if( tempString.equalsIgnoreCase( "sup" ) ) {
         lastTag = SUP_BEGIN;
      } else if( tempString.equalsIgnoreCase( "sub" ) ) {
         lastTag = SUB_BEGIN;
      } else if( tempString.equalsIgnoreCase( "/sup" ) ) {
         lastTag = SUP_END;
      } else if( tempString.equalsIgnoreCase( "/sub" ) ) {
         lastTag = SUB_END;
      } else if( tempString.equalsIgnoreCase( "/" ) ) {
         lastTag = ALL_END;
      } else if( tempString.equalsIgnoreCase( "lt" ) ) {
         lastTag = LESS_THAN;
      } else if( tempString.equalsIgnoreCase( "gt" ) ) {
         lastTag = GREATER_THAN;
      } else if( ( tempString.length() >= 3 ) && tempString.substring( 0, 3 ).equalsIgnoreCase( "sp=" ) ) {
         lastTag = SPECIAL_CHAR;
         tagExtra = tempString.substring(3);
         /*
      } else if (tempString.equalsIgnoreCase("br")) {
         lastTag = NEW_LINE;
         */
      } else {
         lastTag = INVALID;
         tagExtra = tempString;
      }
   }

   protected GridBagConstraints getGBC() {
      /** these 2 GridBagConstraints control how each HTMLSubLabel looks
          gbcH has been modified so that there is a little bit of padding on it's left side
          which makes subscripts look much better*/
      GridBagConstraints gbcV = new GridBagConstraints( 0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 );
      GridBagConstraints gbcH = new GridBagConstraints( GridBagConstraints.RELATIVE, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 2, 0 );
      return isRotate() ? gbcV : gbcH;
   }

   protected void addSubLabel(HTMLSubLabel l) {
      this.add(l, getGBC());
   }

   private void objectNeedsUpdating() {
      formatted = false;
      parse();
      repaint();
   }
}

class StackContainer {
   Component c;
   Object cts;
   int index;

   StackContainer( Component c, Object cts, int index ) {
      this.c = c;
      this.cts = cts;
      this.index = index;
   }
}

