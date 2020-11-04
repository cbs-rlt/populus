/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
/* This file is based on:
 * ThreeD.java      1.8 98/06/29
 * and modified by Amos Anderson for simple 3D graphing capabilities.
 */
package edu.umn.ecology.populus.plot.threedgrapher;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.visual.HTMLFreeLabel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * The representation of a 3D model. This class will collect the points in the 3D
 * graph, and add an axis and grid lines to them for a complete 3D graph. This implementation
 * will only graph lines that move in 3 directions, not 3D surfaces or volumes, though it could
 * be relatively easily modified to do these.
 */
class Model3D {
    ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.plot.threedgrapher.Res");
    Matrix3D mat;

    /**
     * this Vector will be used to hold all the 2D surfaces that get put on the plot -- including
     * the griding panes
     */
    Vector<Plane3D> planes = new Vector<>(0);

    /**
     * this Vector will be used to hold all the Lines that get put on the plot. however,
     * because it holds Line3D objects, it could hold more than just "lines"...
     */
    Vector<Line3D> lines = new Vector<>(0);

    int numLines;
    float xmin, xmax, ymin, ymax, zmin, zmax;
    float xMaxEnforced, yMaxEnforced, zMaxEnforced;
    String[] graphLabels = {"X-axis label", "Y-axis label", "Z-axis label", "Graph Title"};
    Color[] graphColors;
    Color[] plotColors = new Color[]{Color.green, Color.cyan};
    Color backColor;
    Color textColor;
    boolean gridVisible = true;
    boolean zIsDiscrete = false;
    boolean xIsDiscrete = false;
    /*if we want the different lines to be different colors...*/
    boolean multiColored = false;
    BufferedImage bi;

    /**
     * this is the factor by which the axes will be longer than the value longest vector
     * component found in the points.
     */
    float fudgeFactor = 1.1f;

    /**
     * this stores the length of the message for the previous query about a data point so that
     * it can be erased effectively
     */
    int oldMessageLength = 0;

    /**
     * encompassingGrid is for when the grid is ensured to surround all points when grid is changed by having the
     * drawGrid method add one more grid length to each plane. This would be false when one of the axis lengths is
     * fixed so that the drawGrid won't draw further than the maximum value.
     */
    boolean encompassingGrid = true;

    //BufferedImage bi;
    //boolean imageStarted;
    //boolean liveDone;
    //int numPoints, counter, increment;

    Model3D() {
        mat = new Matrix3D();
        mat.xrot(30);
        mat.yrot(20);
    }

    /**
     * This constuctor takes in a triple array. The first array is for multiple lines, the
     * second array is for the 3 (x,y,z) collections of points, and the last are the points themselves.
     */
    Model3D(double[][][] points) {
        this();
        if (points[0].length != 3)
            return;

        Line3D line;
        for (double[][] point : points) {
            line = new Line3D(point);
            lines.add(line);
        }

        createGrid(10);
    }

    float getAxisLength() {
        float l = 0;
        findBB();
        l = Math.max(xmax, ymax);
        l = Math.max(l, zmax);
        if (encompassingGrid) l *= fudgeFactor;
        if (l == 0) l = 100;
        return l;
    }

    /**
     * The grid works like this. There are 9 boundaries for the grid
     * as defined by the axis lines and the edges of the 3 planes.
     * This function plots points along all of those boundaries.
     * The grid is then drawn simply by connecting these points.
     */
    void createGrid(int numLines) {
        findBB();
        createGrid(numLines, xmax, ymax, zmax);
    }

    void createGrid(int numLines, float xlength, float ylength, float zlength) {
        xMaxEnforced = xlength;
        yMaxEnforced = ylength;
        zMaxEnforced = zlength;
        this.numLines = numLines;

        float xwidth, ywidth, zwidth, scaleFactor;
        float dzwidth, dxwidth;//discrete widths

        if (encompassingGrid) scaleFactor = fudgeFactor;
        else scaleFactor = 1.0f;

        xwidth = scaleFactor * xlength / numLines;
        ywidth = scaleFactor * ylength / numLines;
        zwidth = scaleFactor * zlength / numLines;

        Point3D[][] xy = new Point3D[numLines + 1][numLines + 1];
        Point3D[][] xz = new Point3D[numLines + 1][numLines + 1];
        Point3D[][] yz = new Point3D[numLines + 1][numLines + 1];

        for (int i = 0; i <= numLines; i++) {

            for (int j = 0; j <= numLines; j++) {
                if (zIsDiscrete) dzwidth = Math.round(j * zwidth);
                else dzwidth = j * zwidth;

                if (xIsDiscrete) dxwidth = Math.round(i * xwidth);
                else dxwidth = i * xwidth;

                xy[i][j] = new Point3D(dxwidth, j * ywidth, 0);
                xz[i][j] = new Point3D(dxwidth, 0, dzwidth);
                yz[i][j] = new Point3D(0, i * ywidth, dzwidth);
            }

        }
        if (planes.size() < 3) planes.setSize(3);
        planes.set(0, new Plane3D(xy, Plane3D.isXY));
        planes.set(1, new Plane3D(xz, Plane3D.isXZ));//this needs to remain at pos 1
        planes.set(2, new Plane3D(yz, Plane3D.isYZ));
        planes.get(1).setZIsDiscrete(zIsDiscrete);
        planes.get(0).setXIsDiscrete(xIsDiscrete);
    }

    /**
     * Method to intercept the output of a double to the screen. This formats it
     * for a better fit or for more information accordingly.
     */
    String dToS(double v) {
        int temp;
        if (v < 0.01) {
            return NumberMath.roundSigScientific(v, 3, 0);
        } else if (v < 10d) {
            temp = (int) (v * 100d + 0.5d);
            v = temp;
            v /= 100;
            return Double.toString(v);
        } else {
            return Integer.toString((int) (v + 0.5d));
        }
    }

    /**
     * Draws the 3 axis lines and the labels associated with each.
     */
    void drawAxis(Graphics g) {
        Plane3D xyp = planes.get(0);
        Plane3D xzp = planes.get(1);
        Plane3D yzp = planes.get(2);
        xyp.transform(mat);
        xzp.transform(mat);
        yzp.transform(mat);

        int last = xyp.last;

        g.setColor(graphColors[4]);
        drawThickLine(g, xyp.getPoint(0, 0).tx(), xyp.getPoint(0, 0).ty(),
                xyp.getPoint(0, last).tx(), xyp.getPoint(0, last).ty());
        drawThickLine(g, xyp.getPoint(0, 0).tx(), xyp.getPoint(0, 0).ty(),
                xyp.getPoint(last, 0).tx(), xyp.getPoint(last, 0).ty());
        drawThickLine(g, xyp.getPoint(0, 0).tx(), xyp.getPoint(0, 0).ty(),
                xzp.getPoint(0, last).tx(), xzp.getPoint(0, last).ty());

        g.setFont(new Font("Dialog", Font.BOLD, 14));
        g.setColor(textColor);
        HTMLFreeLabel.paintHTML(g, graphLabels[0], (xyp.getPoint(0, last).tx() + xyp.getPoint(last, last).tx()) / 2,
                (xyp.getPoint(0, last).ty() + xyp.getPoint(last, last).ty()) / 2 - 20);//xlabel on top right
        HTMLFreeLabel.paintHTML(g, graphLabels[1], yzp.getPoint(last, last).tx() - 70, yzp.getPoint(last, last).ty() - 20); //ylabel on top
        HTMLFreeLabel.paintHTML(g, graphLabels[2], (xzp.getPoint(last, last).tx() + xyp.getPoint(last, 0).tx()) / 2 + 30,
                (xzp.getPoint(last, last).ty() + xyp.getPoint(last, 0).ty()) / 2 + 30);//zlabel on bottom right
    }

    public void drawBackground(Graphics2D g) {
        for (Plane3D plane : planes) plane.paint(g, mat);
        drawAxis(g);
    }

    public void drawData(Graphics2D g, int numPointsToDraw) {
        for (Line3D line : lines) {
            if (line.isDrawable())
                line.paint(g, mat, numPointsToDraw);
        }
    }

    public void paint(Graphics g, int numPointsToDraw, boolean redraw) {
        Rectangle r = g.getClipBounds();
        boolean sizeChanged = bi == null || r.width != bi.getWidth() || r.height != bi.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        if (redraw || sizeChanged) {
            if (sizeChanged)
                bi = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D big = (Graphics2D) bi.getGraphics();
            big.setColor(ColorScheme.bG);
            big.fillRect(0, 0, r.width, r.height);
            drawBackground(big);
        }
        g2d.drawImage(bi, 0, 0, null);
        drawData(g2d, numPointsToDraw);
    }

    /**
     * Doubles the thickness of a drawn line.
     */
    void drawThickLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
        g.drawLine(x1 + 1, y1, x2 + 1, y2);
    }

    /**
     * Retrieves the value of the transformed point at the highest z
     * at the mouse click point and draws it.
     */
    void drawValueAt(Graphics g, int x, int y) {
        float[] highestZ = null;
        String message;

        for (Line3D line : lines) {
            highestZ = line.matchPoint(x, y);
            if (highestZ != null)
                break;
        }

        if (highestZ != null) {
            g.setFont(new Font("Dialog", Font.BOLD, 14));
            g.setColor(backColor);
            if (oldMessageLength != 0)
                g.fillRect(10, 40, oldMessageLength, g.getFontMetrics().getAscent());
            //is there a way to repaint just that section?
            g.setColor(textColor);
            g.drawString(highestZ[0] + ", " + highestZ[1] + ", " + highestZ[2], (int) highestZ[3], (int) highestZ[4]);
            message = res.getString("Last_Selected_Data_") + highestZ[0] + ", " + highestZ[1] + ", " + highestZ[2];
            oldMessageLength = g.getFontMetrics().stringWidth(message);
            g.drawString(message, 10, 53);
        }
    }

    /**
     * Find the bounding box of this model.
     */
    void findBB() {
        float[] max;
        for (Line3D line : lines) {
            max = line.findBB();
            if (max != null) {
                xmax = Math.max(xmax, max[0]);
                xmin = Math.max(xmin, max[1]);
                ymax = Math.max(ymax, max[2]);
                ymin = Math.max(ymin, max[3]);
                zmax = Math.max(zmax, max[4]);
                zmin = Math.max(zmin, max[5]);
            }
        }
    }

    /**
     * This method returns the 3 values of the axes furthest away from the center
     */
    int[] getEdges() {
        int last = planes.get(0).last;
        int[] e = new int[3];
        e[0] = (int) (planes.get(0).getPoint(last, 0)).x();
        e[1] = (int) (planes.get(2).getPoint(last, 0)).y();
        e[2] = (int) (planes.get(1).getPoint(0, last)).z();
        return e;
    }

    /**
     * Set the colors of the model.
     */
    public void setColors(Color[] newColors) {
        graphColors = newColors;
        for (int i = 0; i < lines.size(); i++) {
            if (multiColored)
                lines.get(i).setColors(plotColors[i % plotColors.length], graphColors[8]);
            else
                lines.get(i).setColors(graphColors[4], graphColors[8]);
        }
        planes.get(0).setPlaneColor(graphColors[0]);
        planes.get(1).setPlaneColor(graphColors[5]);
        planes.get(2).setPlaneColor(graphColors[1]);
    }

    /**
     * Set the background color.
     */
    public void setBackGroundColors(Color graphBack, Color textColor) {
        backColor = graphBack;
        this.textColor = textColor;
        for (Plane3D plane : planes) plane.setBackground(backColor);
    }

    public void setLabelT(boolean b) {
        for (Line3D line : lines) {
            line.setLabelT(b);
        }
    }

    public void setIsDiscrete(boolean b) {
        for (Line3D line : lines) {
            line.setIsDiscrete(b);
        }
    }

    public void setZIsDiscrete(boolean b) {
        zIsDiscrete = b;
    }

    public void setXIsDiscrete(boolean b) {
        xIsDiscrete = b;
    }

    public void setSqueezeLabels(boolean b) {
        for (Plane3D plane : planes) plane.setDontSqueeze(b);
    }

    public boolean getSqueezeLabels() {
        return planes.get(0).dontSqueezeNumbers;
    }

    public void setTransformed(boolean b) {
        for (Line3D line : lines) {
            line.transformed = b;
        }
    }

    public void setMultiColored(boolean b) {
        if (b == multiColored) return;
        multiColored = b;
        for (int i = 0; i < lines.size(); i++) {
            if (multiColored) {
                lines.get(i).setColors(plotColors[i % plotColors.length], graphColors[8]);
                lines.get(i).setDrawLeading(true);
            } else
                lines.get(i).setColors(graphColors[4], graphColors[8]);
        }
    }
}