package edu.umn.ecology.populus.visual;
import java.awt.*;
import java.util.StringTokenizer;

/**
  The static method <code>paintHTML</code> in this class will draw formatted text
  to the screen.
  Simply give it a string with html-style tags. e.g.
  "<i>is</i> italisized"
  "<b>is</b> bold"

  currently, the types available are:
  i,b,p (plain),font color, font size, sup (superscript),
  sub (subscript),/ (end all formatting)

  Use this code to draw a nicely formatted string on any component.
*/

public class HTMLFreeLabel {
   public static Color defaultColor = Color.black;
   public static int defaultSize = 12;
   private static final String BOLD_END = "/b";
   private static final String PLAIN_BEGIN = "p";
   private static final String PLAIN_END = "/p";
   private static final String SUP_END = "/sup";
   private static final String SUB_BEGIN = "sub";
   private static final String BOLD_BEGIN = "b";
   private static final String ITALIC_BEGIN = "i";
   private static final String SUP_BEGIN = "sup";
   private static final String COLOR_BEGIN = "font color";
   private static final String SUB_END = "/sub";
   private static final String ALL_END = "/";
   private static final String ITALIC_END = "/i";
   private static final String COLOR_END = "/color";
   private static final String SIZE_BEGIN = "font size";
   private static final String SIZE_END = "/size"; //Lars - ?And how does color end?

   public static void paintHTML( Graphics g, String s, int x, int y ) {
      StringTokenizer st;
      String temp; //to store the next token in
      st = new StringTokenizer( s, "<>", true );
      int type = g.getFont().getStyle();
      int size = g.getFont().getSize();
      final int ftype = type;
      final int fsize = size;
      final int fy = y;
      boolean inTag = false;
      while( st.hasMoreTokens() ) {
         temp = st.nextToken();
         if( temp.equalsIgnoreCase( "<" ) ) {
            inTag = true;
            continue;
         }
         else {
            if( temp.equalsIgnoreCase( ">" ) ) {
               inTag = false;
               continue;
            }
         }
         if( inTag ) {
            if( temp.equalsIgnoreCase( ITALIC_BEGIN ) ) {
               type |= Font.ITALIC;
            }
            else {
               if( temp.equalsIgnoreCase( ITALIC_END ) ) {
                  type ^= Font.ITALIC;
               }
               else {
                  if( temp.equalsIgnoreCase( BOLD_BEGIN ) ) {
                     type |= Font.BOLD;
                  }
                  else {
                     if( temp.equalsIgnoreCase( BOLD_END ) ) {
                        type ^= Font.BOLD;
                     }
                     else {
                        if( temp.equalsIgnoreCase( PLAIN_BEGIN ) ) {
                           type = Font.PLAIN;
                        }
                        else {
                           if( temp.equalsIgnoreCase( PLAIN_END ) ) {
                              type = ftype;
                           }
                           else {
                              if( temp.startsWith( COLOR_BEGIN ) ) {
                                 g.setColor( new Color( getValue( temp ) ) );
                              }
                              else {
                                 if( temp.startsWith( COLOR_END ) ) {
                                    g.setColor( defaultColor );
                                 }
                                 else {
                                    if( temp.startsWith( SIZE_BEGIN ) ) {
                                       size = getValue( temp );
                                    }
                                    else {
                                       if( temp.startsWith( SIZE_END ) ) {
                                          size = defaultSize;
                                       }
                                       else {
                                          if( temp.equalsIgnoreCase( SUP_BEGIN ) ) {
                                             y -= (int)( size / 3 );
                                          }
                                          else {
                                             if( temp.equalsIgnoreCase( SUP_END ) ) {
                                                y = fy;
                                             }
                                             else {
                                                if( temp.equalsIgnoreCase( SUB_BEGIN ) ) {
                                                   y += (int)( size / 4 );
                                                }
                                                else {
                                                   if( temp.equalsIgnoreCase( SUB_END ) ) {
                                                      y = fy;
                                                   }
                                                   else {
                                                      if( temp.equalsIgnoreCase( ALL_END ) ) {
                                                         type = ftype;
                                                         size = fsize;
                                                         g.setColor( defaultColor );
                                                      }
                                                      else {
                                                         continue;
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
            g.setFont( new Font( "Dialog", type, size ) );
         } else {
            g.drawString( temp, x, y );
            x += g.getFontMetrics().charsWidth( temp.toCharArray(), 0, temp.length() );
         }
      }
      g.setFont( new Font( "Dialog", ftype, fsize ) );
   }

   static int getValue( String s ) {
      s = s.substring( s.indexOf( "=" ) + 1 );
      int r = 0;
      try {
         r = Integer.parseInt( s );
      }
      catch( NumberFormatException nfe ) {

      }
      return r;
   }

   static public String getPlainText( String a, boolean stripSpaces ) {

      //strip off the extraneous data
      StringBuffer sb = new StringBuffer( a );
      int i = 0;
      while( i < sb.length() ) {
         if( stripSpaces && sb.charAt( i ) == ' ' ) {
            sb.deleteCharAt( i );
         }
         if( sb.charAt( i ) == '<' ) {
            while( sb.charAt( i ) != '>' ) {
               sb.deleteCharAt( i );
            }
            sb.deleteCharAt( i );
         }
         else {
            i++;
         }
      }
      sb.setLength( i );
      if( sb.charAt( 0 ) == ' ' ) {
         sb.deleteCharAt( 0 );
      }
      return sb.toString();
   }
}
