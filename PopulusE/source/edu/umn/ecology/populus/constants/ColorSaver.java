/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.constants;
import java.awt.Color;
import java.io.Serializable;

/**
  This class enables the capability of saving the user's color choices.
  ColorScheme isn't serializable because the all relevant data are static,
  */

public class ColorSaver implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -2730125349195960295L;
	Color bGToSave;
   int[] currentIndexToSave;
   int scheme = 0;
   Color[] colorsToSave;

   /**	Restore the colors in the ColorChooser to the saved values.*/

   public void setColors() {
      ColorScheme.colors = (Color[])colorsToSave.clone();
      ColorScheme.bG = bGToSave;
      ColorScheme.currentIndex = (int[])currentIndexToSave.clone();
      ColorScheme.schemeIndex = scheme;
   }

   public ColorSaver() {
      colorsToSave = (Color[])ColorScheme.colors.clone();
      bGToSave = ColorScheme.bG;
      currentIndexToSave = (int[])ColorScheme.currentIndex.clone();
      if( ColorScheme.keepOnlyOneOpen != null ) {
         scheme = ColorScheme.keepOnlyOneOpen.getSchemeIndex();
      }
   }
   public static void setDefaults(){
      if(ColorScheme.keepOnlyOneOpen != null){
         ColorScheme.keepOnlyOneOpen.reinit();
      }
   }
}
