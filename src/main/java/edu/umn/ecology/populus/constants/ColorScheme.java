/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.constants;

import edu.umn.ecology.populus.core.Model;
import edu.umn.ecology.populus.visual.Utilities;

import java.awt.*;
import java.util.Vector;


/**
 * This class is used to store all of the data in the program relating to the colors
 * of the graphs, and a few functions to aid the processing of these colors and getting
 * them on the graphs.
 * <p>
 * <p>
 * this is the area for the colors used in all the graphs
 * <p>
 * if you want more default colors, just add to the defaultColors and colorStrings2
 * arrays (in the same position, of course)
 * <p>
 * remember that the colors in the strings aren't affected by changing these colors:
 * to do a complete change, the html tags in the strings will need to be modified.
 * <p>
 * possible colors to add:
 * from the pascal version we had the colors:
 * bg 			=		60,60,64
 * red 		=		252,84,84
 * green 	= 	0,168,0
 * blue 		=		84,84,252
 * yellow 	= 	252,252,84
 * bgreen 	= 	0,168,168
 * pink 		=		252,84,252
 * other possiblilties:
 * bg =
 * 0,213,255
 * 50,255,204
 */

public class ColorScheme {
    /**
     * The defaults are hard coded so that they can always be
     * retrieved.
     */
    public static final Color[] defaultColors = {
            new Color(255, 0, 75), Color.blue, new Color(0, 223, 0),
            Color.magenta, /*new Color(255,0,255),*/ Color.black, Color.yellow, Color.cyan,
            Color.gray,  /*Color.pink,*/Color.white, new Color(255, 101, 50),
            Color.darkGray, Color.lightGray, new Color(252, 84, 84),
            new Color(0, 168, 0), new Color(84, 84, 252), new Color(252, 252, 84),
            new Color(0, 168, 168), new Color(252, 84, 252),
            new Color(60, 60, 64),  /*/Color.white,*/new Color(170, 170, 170)
    };

    /**
     * The working list of colors for the graphs.
     */
    public static Color[] colors = defaultColors.clone();

    /**
     * this is the background color for the graphs. the color here must not be one of
     * the ones used above because the line with that color would be hidden.
     */
    public static Color bG = defaultColors[defaultColors.length - 1];

    /**
     * This is stored so that, as the name suggests, only one color chooser will be open,
     * whether or not it is visible.
     */
    public static ColorChooser keepOnlyOneOpen = null;

    /**
     * these are strings for use in HTMLLabel objects
     * not really important what goes in here because it will be changed, just as long as
     * there are 10 elements in it (the background color is never used in text)
     * the text here doesn't matter, ColorChooser modifies these strings before
     * they are used.
     */
    public static String[] colorStrings = {
            "<font color=red>", "<font color=blue>", "<font color=green>",
            "<font color=magenta>", "<font color=black>", "<font color=yellow>",
            "<font color=cyan>", "<font color=gray>", "<font color=pink>",
            "<font color=orange>"
    };

    /**
     * This vector holds all of the open models.
     */
    public static Vector<Model> theseModels = new Vector<>();
    public static int schemeIndex = 0;

    /**
     * the last position must be reserved for the custom string
     * essentially, just give the color a name. This name is used in the
     * JComboBoxes in ColorChooser.
     */
    public static final String[] colorStrings2 = {
            "Red", "Blue", "Green", "Magenta", "Black", "Yellow",
            "Cyan", "Gray",  /*"Pink",*/"White", "Orange", "Dark Gray", "Light Gray",
            "Firebrick", /*"Red Coral"*/ "Forest Green", "Periwinkle", "Blonde",
            "Sea Green", "Hot Pink", "Silver Gray",  /*/"White",*/"Silver", "Custom"
    };

    /**
     * This array holds the index of the color currently in use. This is used primarily
     * in ColorChooser so it knows what index to set the JComboBoxes at.
     */
    public static int[] currentIndex = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, colorStrings2.length - 2
    };



	/*
   public static final Color[] oldColors = {
   new Color(252,84,84),
   new Color(0,168,0),
   new Color(84,84,252),
   new Color(252,252,84),
   new Color(0,168,168),
   new Color(252,84,252),
   new Color(60,60,64),
   };

   public static final	Color[] defaultColors = {
   Color.red,
   new Color(0,153,0),
   Color.blue,
   Color.magenta,
   Color.black,
   Color.yellow,
   Color.cyan,
   Color.gray,
   Color.pink,
   Color.orange,
   Color.darkGray,
   Color.lightGray,

   new Color(252,84,84),
   new Color(0,168,0),
   new Color(84,84,252),
   new Color(252,252,84),
   new Color(0,168,168),
   new Color(252,84,252),
   new Color(60,60,64),

   Color.white
   };
	 */

    /**
     * Call this function for the list of open models. This list is useful for
     * doing the same thing to several models. For example, if you want to change
     * the graph colors for all the open models, get this list and call the same method
     * on all of them.
     */

    public Vector<Model> getModelList() {
        return theseModels;
    }

    /**
     * Used to remove a model from the list of opened models.
     */

    public static void removeModel(Model removeThis) {
        for (int i = 0; i < theseModels.size(); i++) {
            if (removeThis.equals(theseModels.get(i))) {
                theseModels.remove(i);
                return;
            }
        }
    }

    /**
     * Used to add a model to the vector of open models.
     */

    public static void addModel(Model addThis) {
        theseModels.addElement(addThis);
    }

    /**
     * i think this function here will eventually replace colorStrings... but i'm lazy...
     * This function will return a valid HTMLLabel label for any color. Use this in creation
     * of labels to be used in the text.
     */

    public static String getColorString(int forWhich) {
        if (forWhich < colors.length && forWhich >= 0) {
            return "<font color=" + Utilities.getColorString(colors[forWhich]) + ">";
        } else {
            return null;
        }
    }
}
