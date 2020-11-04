/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.plot.coloredcells.CellPalette;

import java.awt.*;

/**
 * The array of colors that the cells will use. These are an approximation to the
 * visible spectrum
 */
class SDColorPalette extends CellPalette {
    SDColorPalette() {
        colors = new Color[]{
                Color.blue,
                Color.yellow,
                Color.red,
                Color.green
        };
    }
}

