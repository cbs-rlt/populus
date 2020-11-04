/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.LabelUI;
import java.awt.*;

public class BracketUI extends LabelUI {
    private static final int kHEIGHT = 50;

    // Shared UI object
    private static final BracketUI bracketUI = new BracketUI();
    private static final int kWIDTH = 15;

    public BracketUI() {

    }

    // ********************************

    //          Paint Methods

    // ********************************

    /**
     * paint the bracket
     */

    @Override
    public synchronized void paint(Graphics g, JComponent c) {
        Bracket bracket = (Bracket) c;
        int bottom, top, left, right, w;
        Dimension size = bracket.getSize();
        left = 0;
        right = size.width - 1;
        top = 0;
        bottom = size.height - 1;
        w = size.height / 7;
        if (w < size.width) {
            w = (size.width - w) / 2;
            left += w;
            right -= w;
        }

        // fill background
        if (c.isOpaque()) {
            g.setColor(bracket.getBackground());
            g.fillRect(left, top, right, bottom);
        }
        g.setColor(bracket.getForeground());
        if (bracket.getSide() == SwingConstants.LEFT) {
            g.drawLine(left, top, left, bottom);
        } else {
            g.drawLine(right, top, right, bottom);
        }
        g.drawLine(left, top, right, top);
        g.drawLine(left, bottom, right, bottom);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(kWIDTH, kHEIGHT);
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    // ********************************

    //          Create PLAF

    // ********************************

    public static ComponentUI createUI(JComponent c) {
        return bracketUI;
    }
}
