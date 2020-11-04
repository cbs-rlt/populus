/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Lars Roe
//Company:      University of Minnesota
//Description:  Populus

package edu.umn.ecology.populus.visual;

import java.awt.*;

public class ImagePanel extends Canvas {

    /**
     *
     */
    private static final long serialVersionUID = 7735576242903205864L;
    Container pappy;
    Image image;
    Dimension size;
    int w, h;
    boolean trueSizeKnown;
    MediaTracker tracker;

    public ImagePanel(Image image, Container highestContainer,
                      int initialWidth, int initialHeight) {
        if (image == null) {
            System.err.println("Canvas got invalid image object!");
            return;
        }
        this.image = image;
        this.pappy = highestContainer;
        w = initialWidth;
        h = initialHeight;
        tracker = new MediaTracker(this);
        tracker.addImage(image, 0);
        size = new Dimension(w, h);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public void paint(Graphics g) {
        if (image != null) {
            if (!trueSizeKnown) {
                int imageWidth = image.getWidth(this);
                int imageHeight = image.getHeight(this);
                if (tracker.checkAll(true)) {
                    trueSizeKnown = true;
                    if (tracker.isErrorAny()) {
                        System.err.println("Error loading image: "
                                + image);
                    }
                }
                //Component-initiated resizing.
                if (((imageWidth > 0) && (w != imageWidth)) ||
                        ((imageHeight > 0) && (h != imageHeight))) {
                    w = imageWidth;
                    h = imageHeight;
                    size = new Dimension(w, h);
                    setSize(w, h);
                    pappy.validate();
                }
            }
        }
        g.drawImage(image, 0, 0, this);
    }
}

