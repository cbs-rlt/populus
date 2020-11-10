/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import edu.umn.ecology.populus.constants.ColorScheme;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * Used to get Colors from Strings
 * Either a common color name, or a rgb value works
 * Default is black
 *
 * @author Lars Roe
 */

public class Utilities {

    /**
     * Gets a color from an int.
     */

    public static Color getColor(int colorNumber) {
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
     * Gets a color from string. use either name (case-insensitive) or 6 digit rgb hex value
     */

    public static Color getColor(String colorName) {
        if (colorName.equalsIgnoreCase("BLUE")) {
            return Color.blue;
        }
        if (colorName.equalsIgnoreCase("RED")) {
            return Color.red;
        }
        if (colorName.equalsIgnoreCase("GREEN")) {
            //return Color.green;
            return new Color(0, 153, 0);
        }
        if (colorName.equalsIgnoreCase("CYAN")) {
            return Color.cyan;
        }
        if (colorName.equalsIgnoreCase("ORANGE")) {
            return Color.orange;
        }
        if (colorName.equalsIgnoreCase("YELLOW")) {
            return Color.yellow;
        }
        if (colorName.equalsIgnoreCase("PURPLE")) {
            return new Color(75, 0, 150);
        }
        if (colorName.equalsIgnoreCase("MAGENTA")) {
            return Color.magenta;
        }
        if (colorName.equalsIgnoreCase("GRAY")) {
            return Color.gray;
        }
        if (colorName.equalsIgnoreCase("LIGHTGRAY")) {
            return Color.lightGray;
        }
        if (colorName.equalsIgnoreCase("DARKGRAY")) {
            return Color.darkGray;
        }
        if (colorName.equalsIgnoreCase("BLACK")) {
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
                rgb = Integer.parseInt(colorName);
            }
            return new Color(rgb);
        } catch (NumberFormatException e) {
            return Color.black;
        }
    }

    /**
     * e = Enumeration of String
     * <BR>NOTE: does not do Mnemonics yet, but shouldn't be too hard to add.
     * Mnemonics? what does that mean? ButtonModel.getMnemonics()...
     * <BR> Please improve on this. Especially sub/super-scripts
     */

    public static final void drawHTML(Graphics g, Enumeration<String> e, int c, int x, int y) {

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
        if (f == null) {
            f = (Font) UIManager.get("Label.font");
        }
        if (defaultColor == null) {
            defaultColor = (Color) UIManager.get("Label.foreground");
        }
        regHeight = fm.getAscent();

        //defaultFont = f;

        //Go through e, writing text and applying tags
        while (e.hasMoreElements()) {
            tag = !tag;
            s = (e.nextElement());

            //If it is a tag, do appropriate work
            if (tag) {
                //f = defaultFont;
                switch (HTMLTag.getTag(s).type) {
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
                    g.setColor((Color) tempTag.extra);
                    break;
                case HTMLConstants.COLOR_END:
                    g.setColor(defaultColor);
                    break;
                case HTMLConstants.LESS_THAN:
                    g.drawString("<", x, y + dy);
                    x += fm.stringWidth("<");
                    break;
                case HTMLConstants.GREATER_THAN:
                    g.drawString(">", x, y + dy);
                    x += fm.stringWidth(">");
                    break;
                case HTMLConstants.ALL_END:
                    g.setColor(defaultColor);
                    style = Font.PLAIN;
                    supLevel = 0;
                    dy = 0;
                    topBar = false;
                    g.setFont(new Font(f.getFontName(), style, f.getSize()));
                    break;
                default:
                    System.err.println("Unknown Tag #" + tempTag.type + " = " + tempTag.extra);
                }
            } else {
                if (s.length() == 0) {
                    continue;
                }
                dy = 0;
                if (supLevel != 0) {
                    tempSize = (int) (0.5f + HTMLConstants.SCRIPT_SHRINKAGE * ((float) f.getSize() * Math.abs(supLevel)));
                    g.setFont(new Font(f.getFontName(), style, tempSize));
                } else {
                    if (updateFont) {
                        g.setFont(new Font(f.getFontName(), style, f.getSize()));
                        //f = new Font(f.getFontName(), style, f.getSize());
                        //g.setFont(f);
                    }
                }
                updateFont = false;
                fm = g.getFontMetrics();

                //edu.umn.ecology.populus.fileio.Logging.log("supLevel: " + supLevel + ", ht: " + fm.getAscent());
                if (supLevel != 0) { //subscript or subscript
                    //dy = regHeight - fm.getHeight();
                    dy = regHeight - f.getSize() + 1;
                    if (supLevel > 0) { //subscript
                        dy *= -1;
                    }
                }

                //edu.umn.ecology.populus.fileio.Logging.log(dy);
                g.drawString(s, x, y + dy);
                dx = fm.stringWidth(s);

                //draw top bar
                if (topBar) {
                    barHeight = fm.getAscent() - 1;
                    g.drawLine(x, y - barHeight, x + dx, y - barHeight);
                }

                //BasicGraphicsUtils.drawString(g, s, c /* 0 */, x, y);
                x += dx;
            }
        }
    }


    /**
     * Java has added HTML formatting after I hacked in HTML-like strings.
     * For now, this code simply surrounds with html tags.
     */
    public static String PopHTMLToSwingHTML(String s) {
        return "<html>" + s + "</html>";
    }

    static class HTMLTag implements Cloneable {
        Object extra = null;
        int type;

        static HTMLTag getTag(String str) {
            HTMLTag tag = new HTMLTag();
            if (str.equalsIgnoreCase("b")) {
                tag.type = HTMLConstants.BOLD_BEGIN;
            } else if (str.equalsIgnoreCase("/b")) {
                tag.type = HTMLConstants.BOLD_END;
            } else if (str.equalsIgnoreCase("i")) {
                tag.type = HTMLConstants.ITALIC_BEGIN;
            } else if (str.equalsIgnoreCase("/i")) {
                tag.type = HTMLConstants.ITALIC_END;
            } else if (str.equalsIgnoreCase("bar")) {
                tag.type = HTMLConstants.BAR_BEGIN;
            } else if (str.equalsIgnoreCase("/bar")) {
                tag.type = HTMLConstants.BAR_END;
            } else if (str.equalsIgnoreCase("/font")) {
                tag.type = HTMLConstants.COLOR_END;
            } else if ((str.length() > 11) &&
                    str.substring(0, 11).equalsIgnoreCase("font color=")) {
                tag.type = HTMLConstants.COLOR_BEGIN;
                tag.extra = Utilities.getColor(str.substring(11));
            } else if (str.equalsIgnoreCase("sup")) {
                tag.type = HTMLConstants.SUP_BEGIN;
            } else if (str.equalsIgnoreCase("sub")) {
                tag.type = HTMLConstants.SUB_BEGIN;
            } else if (str.equalsIgnoreCase("/sup")) {
                tag.type = HTMLConstants.SUP_END;
            } else if (str.equalsIgnoreCase("/sub")) {
                tag.type = HTMLConstants.SUB_END;
            } else if (str.equalsIgnoreCase("/")) {
                tag.type = HTMLConstants.ALL_END;
            } else if (str.equalsIgnoreCase("lt")) {
                tag.type = HTMLConstants.LESS_THAN;
            } else if (str.equalsIgnoreCase("gt")) {
                tag.type = HTMLConstants.GREATER_THAN;
            } else {
                tag.type = HTMLConstants.INVALID;
                tag.extra = str;
            }
            return tag;
        }
    }
}
