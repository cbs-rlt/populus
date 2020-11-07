/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import java.awt.*;

/**
 * Will soon be a more robust Text Component
 * designed especially for numbers.
 */

public class NumberComponent extends TextField {
    /**
     *
     */
    private static final long serialVersionUID = -1818193465390545826L;
    double maxVal = Double.POSITIVE_INFINITY;
    double minVal = 0.0;

    public NumberComponent(String def, int columns, double min, double max) {
        this(def, columns);
        minVal = min;
        maxVal = max;
    }

    /**
     * Parses a double from a TextComponent
     * Later, I may want to add something to throw...
     * There are seven phases of parsing the number here:
     * 1) 0 or 1  [+-]
     * 2) 0+      [0123456789]
     * 3) 0 or 1  [.] if 1:
     * 4) 0+     [0123456789]
     * 5) 0 or 1  [eE] if 1:
     * 6)
     * 7) 123   [0123456789]
     * oh wait, there already is a canned routine. But I might just leave this here.
     */

    public double getDouble() {
        String s = getText().trim();
        return Double.valueOf(s);
    }

    public NumberComponent(String def, int columns) {
        super(def, columns);
    }
}
