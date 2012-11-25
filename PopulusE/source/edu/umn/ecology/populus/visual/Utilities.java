package edu.umn.ecology.populus.visual;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import edu.umn.ecology.populus.constants.ColorScheme;

/**
  * Used to get Colors from Strings
  * Either a common color name, or a rgb value works
  * Default is black
  * @author Lars Roe
  */

public class Utilities extends java.lang.Object {

   /**
     *Gets a color from an int.
     */

   public static Color getColor( int colorNumber ) {
      return ColorScheme.colors[colorNumber % ColorScheme.colors.length];
   }

   public static int getColorCount() {
      return ColorScheme.colors.length;
   }

   public static String getColorString(Color c) {
      int rgb = c.getRGB() & 0xFFFFFF;
      String hexStr = Integer.toHexString(rgb);
      while (hexStr.length() < 6) hexStr = "0" + hexStr;
      return "#" + hexStr;
   }

   /*
   protected static Color[] colors = {
   Color.red, Color.green, Color.gray, Color.black,
   Color.blue, Color.magenta, Color.yellow, Color.cyan,
   Color.darkGray, Color.white, new Color(75, 0, 150), //(purple)
   Color.orange, Color.lightGray, Color.pink};
   */

   /**
     *Gets a color from string. use either name (case-insensitive) or 6 digit rgb hex value
     *Just look at the code itself for further
     */

   public static Color getColor( String colorName ) {
      if( colorName.equalsIgnoreCase( "BLUE" ) ) {
         return Color.blue;
      }
      if( colorName.equalsIgnoreCase( "RED" ) ) {
         return Color.red;
      }
      if( colorName.equalsIgnoreCase( "GREEN" ) ) {
         //return Color.green;
         return new Color( 0, 153, 0 );
      }
      if( colorName.equalsIgnoreCase( "CYAN" ) ) {
         return Color.cyan;
      }
      if( colorName.equalsIgnoreCase( "ORANGE" ) ) {
         return Color.orange;
      }
      if( colorName.equalsIgnoreCase( "YELLOW" ) ) {
         return Color.yellow;
      }
      if( colorName.equalsIgnoreCase( "PURPLE" ) ) {
         return new Color( 75, 0, 150 );
      }
      if( colorName.equalsIgnoreCase( "MAGENTA" ) ) {
         return Color.magenta;
      }
      if( colorName.equalsIgnoreCase( "GRAY" ) ) {
         return Color.gray;
      }
      if( colorName.equalsIgnoreCase( "LIGHTGRAY" ) ) {
         return Color.lightGray;
      }
      if( colorName.equalsIgnoreCase( "DARKGRAY" ) ) {
         return Color.darkGray;
      }
      if( colorName.equalsIgnoreCase( "BLACK" ) ) {
         return Color.black;
      }
      try {
         int rgb;
         //Remove initial white space
         while (colorName.startsWith(" ")) {
            colorName = colorName.substring(1);
         }
         if (colorName.startsWith("#")) {
            rgb = Integer.parseInt(colorName.substring(1), 16);
         } else {
            rgb = Integer.parseInt( colorName );
         }
         return new Color( rgb );
      }
      catch( NumberFormatException e ) {
         return Color.black;
      }
   }

   /**
     * e = Enumeration of String
     * <BR>NOTE: does not do Mnemonics yet, but shouldn't be too hard to add.
     * Mnemonics? what does that mean? ButtonModel.getMnemonics()...
     * <BR> Please improve on this. Especially sub/super-scripts
     */

   public static final void drawHTML( Graphics g, Enumeration e, int c, int x, int y ) {

      //Enumeration e = v.elements();

      //BasicGraphicsUtils.drawString(g,s,c, x, y);
      boolean tag = true;
      String s;
      int style = Font.PLAIN;
      int supLevel = 0;
      int tempSize;
      int dy = 0, regHeight = -1, dx;
      int barHeight;
      boolean topBar = false;

      //Font defaultFont;
      FontMetrics fm = g.getFontMetrics();
      Font f = g.getFont();
      Color defaultColor = g.getColor();
      HTMLTag tempTag = new HTMLTag();
      boolean updateFont = false;

      //Ensure c and f are not null
      if( f == null ) {
         f = (Font)UIManager.get( "Label.font" );
      }
      if( defaultColor == null ) {
         defaultColor = (Color)UIManager.get( "Label.foreground" );
      }
      regHeight = fm.getAscent();

      //defaultFont = f;

      //Go through e, writing text and applying tags
      while( e.hasMoreElements() ) {
         tag = !tag;
         s = ( (String)e.nextElement() );

         //If it is a tag, do appropriate work
         if( tag ) {
            switch( HTMLTag.getTag( s ).type ) {
               case HTMLConstants.BOLD_BEGIN:
                   style |= Font.BOLD;
                   updateFont = true;
                   break;

               case HTMLConstants.BOLD_END:
                   style &= Font.ITALIC;
                   updateFont = true;
                   break;

               case HTMLConstants.ITALIC_BEGIN:
                   style |= Font.ITALIC;
                   updateFont = true;
                   break;

               case HTMLConstants.ITALIC_END:
                   style &= Font.BOLD;
                   updateFont = true;
                   break;

               case HTMLConstants.SUP_BEGIN:
               case HTMLConstants.SUB_END:
                   supLevel++;
                   updateFont = true;
                   break;

               case HTMLConstants.SUP_END:
               case HTMLConstants.SUB_BEGIN:
                   supLevel--;
                   updateFont = true;
                   break;

               case HTMLConstants.BAR_BEGIN:
                   topBar = true;
                   break;

               case HTMLConstants.BAR_END:
                   topBar = false;
                   break;

               case HTMLConstants.COLOR_BEGIN:
                   g.setColor( (Color)tempTag.extra );
                   break;

               case HTMLConstants.COLOR_END:
                   g.setColor( defaultColor );
                   break;

               case HTMLConstants.LESS_THAN:
                   g.drawString( "<", x, y + dy );
                   x += fm.stringWidth( "<" );
                   break;

               case HTMLConstants.GREATER_THAN:
                   g.drawString( ">", x, y + dy );
                   x += fm.stringWidth( ">" );
                   break;

               case HTMLConstants.ALL_END:
                   g.setColor( defaultColor );

                   //f = defaultFont;
                   style = Font.PLAIN;
                   supLevel = 0;
                   dy = 0;
                   topBar = false;
                   g.setFont( new Font( f.getFontName(), style, f.getSize() ) );
                   break;

               default:
                   System.err.println( "Unknown Tag #" + tempTag.type + " = " + tempTag.extra );
            }
         } else {
            if( s.length() == 0 ) {
               continue;
            }
            dy = 0;
            if( supLevel != 0 ) {
               tempSize = (int)( 0.5f + HTMLConstants.SCRIPT_SHRINKAGE * ( (float)f.getSize() * Math.abs( supLevel ) ) );
               g.setFont( new Font( f.getFontName(), style, tempSize ) );
            } else {
               if( updateFont ) {
                  g.setFont( new Font( f.getFontName(), style, f.getSize() ) );
                  //f = new Font(f.getFontName(), style, f.getSize());
                  //g.setFont(f);
               }
            }
            updateFont = false;
            fm = g.getFontMetrics();

            //edu.umn.ecology.populus.fileio.Logging.log("supLevel: " + supLevel + ", ht: " + fm.getAscent());
            if( supLevel != 0 ) { //subscript or subscript
               //dy = regHeight - fm.getHeight();
               dy = regHeight - f.getSize()+1;
               if( supLevel > 0 ) { //subscript
                  dy *= -1;
               }
            }

            //edu.umn.ecology.populus.fileio.Logging.log(dy);
            g.drawString( s, x, y + dy );
            dx = fm.stringWidth( s );

            //draw top bar
            if( topBar ) {
               barHeight = fm.getAscent() - 1;
               g.drawLine( x, y - barHeight, x + dx, y - barHeight );
            }

            //BasicGraphicsUtils.drawString(g, s, c /* 0 */, x, y);
            x += dx;
         }
      }
   }

   /**
     * Very much like a StringTokenizer.
     * returnValue[0] is first text,
     * returnValue[1] is first tag,
     * returnValue[2] is second text,
     * returnValue[3] is second tag,
     * returnValue[4] is third text,
     * etc.
     */

   public static final Vector parseHTML( StringBuffer buf ) {
      Vector v = new Vector();
      StringBuffer currBuff = new StringBuffer();

      while (buf.length() > 0) {
          int ind;
          String text;

          //Add text
          if ((ind = buf.toString().indexOf("<")) != -1) {
              text = buf.substring(0, ind);
              buf.delete(0, ind);
          } else {
              text = buf.toString();
              buf.setLength(0);
          }
          v.add(text);

          //Add tag
          if ((buf.length() > 0) && (buf.charAt(0) == '<')) {
              ind = buf.toString().indexOf(">");
              if (ind == -1) {
                  edu.umn.ecology.populus.fileio.Logging.log("Tag not closed!  Assuming goes to end");
                  ind = buf.length();
              }
              HTMLTag h = HTMLTag.getTag(buf.substring(1, ind));
              v.add(h);
              buf.delete(0, ind+1);
          }
      }
      return v;
   }

   /**
     * Very much like a StringTokenizer.
     * returnValue[0] is first text,
     * returnValue[1] is first tag,
     * returnValue[2] is second text,
     * returnValue[3] is second tag,
     * returnValue[4] is third text,
     * etc.
     */

   public static final Vector parseHTML( String str ) {
      return parseHTML( new StringBuffer( str ) );
   }


   /**
     * e = Enumeration of String
     * <BR>NOTE: does not do Mnemonics yet, but shouldn't be too hard to add.
     */

   public static final Dimension getSize( Graphics g, Enumeration e ) {

      //Enumeration e = v.elements();

      //BasicGraphicsUtils.drawString(g,s,c, x, y);
      int x = 0, y = 0;
      boolean tag = true;
      String s;
      int style = Font.PLAIN;
      int supLevel = 0;
      int tempSize;
      int dy = 0, regHeight = -1;
      boolean topBar = false;

      //Font defaultFont;
      FontMetrics fm;
      Font f = g.getFont();
      HTMLTag tempTag = new HTMLTag();
      boolean updateFont = false;

      //Ensure c and f are not null
      if( f == null ) {
         f = (Font)UIManager.get( "Label.font" );
      }
      regHeight = g.getFontMetrics().getHeight();

      //defaultFont = f;
      //Go through e, writing text and applying tags
      while( e.hasMoreElements() ) {
         tag = !tag;
         s = ( (String)e.nextElement() );

         //If it is a tag, do appropriate work
         if( tag ) {
            switch( HTMLTag.getTag( s ).type ) {
               case HTMLConstants.BOLD_BEGIN:
                   style |= Font.BOLD;
                   updateFont = true;
                   break;

               case HTMLConstants.BOLD_END:
                   style &= Font.ITALIC;
                   updateFont = true;
                   break;

               case HTMLConstants.ITALIC_BEGIN:
                   style |= Font.ITALIC;
                   updateFont = true;
                   break;

               case HTMLConstants.ITALIC_END:
                   style &= Font.BOLD;
                   updateFont = true;
                   break;

               case HTMLConstants.SUP_BEGIN:
               case HTMLConstants.SUB_END:
                   supLevel++;
                   updateFont = true;
                   break;

               case HTMLConstants.SUP_END:
               case HTMLConstants.SUB_BEGIN:
                   supLevel--;
                   updateFont = true;
                   break;

               case HTMLConstants.ALL_END:

                   //f = defaultFont;
                   style = Font.PLAIN;
                   supLevel = 0;
                   topBar = false;
                   updateFont = true;
                   break;

               case HTMLConstants.BAR_BEGIN:
               case HTMLConstants.BAR_END:
               case HTMLConstants.COLOR_BEGIN:
               case HTMLConstants.COLOR_END:
               case HTMLConstants.LESS_THAN:
               case HTMLConstants.GREATER_THAN:
                   break;

               default:
                   System.err.println( "Unknown Tag #" + tempTag.type + " = " + tempTag.extra );
            }
         }
         else {
            if( s.length() == 0 ) {
               continue;
            }
            dy = 0;
            if( supLevel != 0 ) {
               g.setFont( new Font( f.getFontName(), style, ( (int)( 0.5f + HTMLConstants.SCRIPT_SHRINKAGE * ( (float)f.getSize() * Math.abs( supLevel ) ) ) ) ) );
            }
            else {
               if( updateFont ) {
                  g.setFont( new Font( f.getFontName(), style, f.getSize() ) );

               //f = new Font(f.getFontName(), style, f.getSize());
               //g.setFont(f);
               }
            }
            updateFont = false;
            fm = g.getFontMetrics();
            if( supLevel != 0 ) { //subscript or subscript
               dy = regHeight - fm.getHeight() - 1;
               if( supLevel > 0 ) { //superscript
                  dy *= -1;
               }
            }
            x += fm.stringWidth( s );
         }
      }
      return null;
   }


   /**
    * Java has added HTML formatting after I hacked in HTML-like strings.
    * For now, just surround with "<html>" and "</html>"
    */
   public static String PopHTMLToSwingHTML(String s) {
       StringBuffer ns = new StringBuffer("<html>");
       ns.append(s);
       ns.append("</html>");
       return ns.toString();
   }

   static class HTMLState {
       //int bold=0, ital=0, bar=0, font=0, sup=0, sub=0;
       Stack v;

       HTMLState() {
           v = new Stack();
       }

       private String verifyPop(String s) {
           String inStack;
           if (v.isEmpty()) {
               edu.umn.ecology.populus.fileio.Logging.log("Trying to close a tag that was never opened");
           }
           inStack = v.pop().toString();
           if (s != inStack)
               edu.umn.ecology.populus.fileio.Logging.log("Invalid tag nesting!");
           return s;
       }
       String updateState(HTMLTag tag) {
           String htmlString = "";
           switch (tag.type) {
           case HTMLConstants.BOLD_BEGIN:
               v.push("</b>");
               htmlString = "<b>";
               break;
           case HTMLConstants.BOLD_END:
               htmlString = verifyPop("</b>");
               break;
           case HTMLConstants.ITALIC_BEGIN:
               v.push("</i>");
               htmlString = "<i>";
               break;
           case HTMLConstants.ITALIC_END:
               htmlString = verifyPop("</i>");
               break;
           case HTMLConstants.COLOR_BEGIN:
               v.push("</font>");
               htmlString = "<font color=" + getColorString((Color) tag.extra) + ">";
               break;
           case HTMLConstants.COLOR_END:
               htmlString = verifyPop("</font>");
               break;
           case HTMLConstants.SUB_BEGIN:
               v.push("</sub>");
               htmlString = "<sub>";
               break;
           case HTMLConstants.SUB_END:
               htmlString = verifyPop("</sub>");
               break;
           case HTMLConstants.SUP_BEGIN:
               v.push("</sup>");
               htmlString = "<sup>";
               break;
           case HTMLConstants.SUP_END:
               htmlString = verifyPop("</sup>");
               break;
           case HTMLConstants.ALL_END:
               while (!v.isEmpty()) {
                   htmlString += v.pop();
               }
               break;
           default:
               edu.umn.ecology.populus.fileio.Logging.log("Bad HTML Tag: " + tag.toString());
               htmlString = "** BAD STRING **";
               break;
           }
           return htmlString;
       }


   }

   static class HTMLTag implements Cloneable {
       Object extra = null;
       int type;

       static HTMLTag getTag(String str) {
           HTMLTag tag = new HTMLTag();
           if (str.equalsIgnoreCase("b")) {
               tag.type = HTMLConstants.BOLD_BEGIN;
           } else
           if (str.equalsIgnoreCase("/b")) {
               tag.type = HTMLConstants.BOLD_END;
           } else
           if (str.equalsIgnoreCase("i")) {
               tag.type = HTMLConstants.ITALIC_BEGIN;
           } else
           if (str.equalsIgnoreCase("/i")) {
               tag.type = HTMLConstants.ITALIC_END;
           } else
           if (str.equalsIgnoreCase("bar")) {
               tag.type = HTMLConstants.BAR_BEGIN;
           } else
           if (str.equalsIgnoreCase("/bar")) {
               tag.type = HTMLConstants.BAR_END;
           } else
           if (str.equalsIgnoreCase("/font")) {
               tag.type = HTMLConstants.COLOR_END;
           } else
           if ((str.length() > 11) &&
               str.substring(0, 11).equalsIgnoreCase("font color=")) {
               tag.type = HTMLConstants.COLOR_BEGIN;
               tag.extra = Utilities.getColor(str.substring(11));
           } else
           if (str.equalsIgnoreCase("sup")) {
               tag.type = HTMLConstants.SUP_BEGIN;
           } else
           if (str.equalsIgnoreCase("sub")) {
               tag.type = HTMLConstants.SUB_BEGIN;
           } else
           if (str.equalsIgnoreCase("/sup")) {
               tag.type = HTMLConstants.SUP_END;
           } else
           if (str.equalsIgnoreCase("/sub")) {
               tag.type = HTMLConstants.SUB_END;
           } else
           if (str.equalsIgnoreCase("/")) {
               tag.type = HTMLConstants.ALL_END;
           } else
           if (str.equalsIgnoreCase("lt")) {
               tag.type = HTMLConstants.LESS_THAN;
           } else
           if (str.equalsIgnoreCase("gt")) {
               tag.type = HTMLConstants.GREATER_THAN;
           } else {
               tag.type = HTMLConstants.INVALID;
               tag.extra = str;
           }
           return tag;
       }
   }
}
