/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import edu.umn.ecology.populus.core.PopPreferencesStorage;

import javax.swing.border.Border;
import java.awt.*;


public class OwnershipBorder implements Border { //, java.io.Serializable {
    private Color color = Color.black;
    private int thickness;

    public OwnershipBorder(Color borderColor) {
        this.color = borderColor;
        thickness = PopPreferencesStorage.getOwnershipBorderThickness();
    }

    public OwnershipBorder(Color borderColor, int thickness) {
        this.color = borderColor;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Color oldColor = g.getColor();
        g.translate(x, y);
        g.setColor(color);
        for (int i = 0; i < thickness; i++) {
            g.drawLine(0, i, w - 1, i);
        }
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }


    public int getThickness() {

        return thickness;

    }

}
