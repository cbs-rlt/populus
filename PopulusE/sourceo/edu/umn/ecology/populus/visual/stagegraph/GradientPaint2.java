package edu.umn.ecology.populus.visual.stagegraph;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.awt.*;

/**
the whole point of this file is so that i can have a new GradientPaintContext class i can monkey with.
this file itself only needed to be changed to return a GradientPaintContext2 object

*/
public class GradientPaint2 implements Paint {
    Point2D.Float p1;
    Point2D.Float p2;
    Color color1;
    Color color2;
    boolean cyclic;
    int shift = 0;

    public GradientPaint2(float x1,
                         float y1,
                         Color color1,
                         float x2,
                         float y2,
                         Color color2) {
        p1 = new Point2D.Float(x1, y1);
        p2 = new Point2D.Float(x2, y2);
        this.color1 = color1;
        this.color2 = color2;
    }

    public GradientPaint2(Point2D pt1,
                         Color color1,
                         Point2D pt2,
                         Color color2) {
        p1 = new Point2D.Float((float)pt1.getX(), (float)pt1.getY());
        p2 = new Point2D.Float((float)pt2.getX(), (float)pt2.getY());
        this.color1 = color1;
        this.color2 = color2;
    }

    public GradientPaint2(float x1,
                         float y1,
                         Color color1,
                         float x2,
                         float y2,
                         Color color2,
                         boolean cyclic, int shift) {
        this (x1, y1, color1, x2, y2, color2);
        this.cyclic = cyclic;
        this.shift = shift;
    }

    public GradientPaint2(Point2D pt1,
                         Color color1,
                         Point2D pt2,
                         Color color2,
                         boolean cyclic) {
        this (pt1, color1, pt2, color2);
        this.cyclic = cyclic;
    }

    public Point2D getPoint1() {
        return new Point2D.Float(p1.x, p1.y);
    }

    public Color getColor1() {
        return color1;
    }

    public Point2D getPoint2() {
        return new Point2D.Float(p2.x, p2.y);
    }

    public Color getColor2() {
        return color2;
    }

    public boolean isCyclic() {
        return cyclic;
    }

    public PaintContext createContext(ColorModel cm,
                                      Rectangle deviceBounds,
                                      Rectangle2D userBounds,
                                      AffineTransform xform,
                                      RenderingHints hints) {

        return new GradientPaintContext2(p1, p2, xform, color1, color2, cyclic, shift);
    }

    public int getTransparency() {
        int a1 = color1.getAlpha();
        int a2 = color2.getAlpha();
        return (((a1 & a2) == 0xff) ? OPAQUE : TRANSLUCENT);
    }

}
