/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.plot.coloredcells.CellPalette;

import java.awt.*;

/**
 * The array of colors that the cells will use. These are an approximation to the
 * visible light spectrum
 */
class NBSSColorPalette extends CellPalette {
    NBSSColorPalette() {
        colors = new Color[]{
                Color.black,
                new Color(108, 0, 0),
                Color.red,
                Color.orange,
                Color.yellow,
                new Color(157, 252, 0),
                Color.green,
                Color.cyan,
                Color.blue,
                new Color(104, 0, 110)
        };
        textColor = Color.gray;
        textFormat = CellPalette.kCenter;
    }
}
