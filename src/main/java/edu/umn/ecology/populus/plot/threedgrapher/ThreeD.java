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
import edu.umn.ecology.populus.plot.Stepper;
import edu.umn.ecology.populus.visual.HTMLFreeLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to handle the drawing and user interface of Populus 3D models.
 * There is a lot more functionality that probably should be a part of 3D graphing models,
 * but because i'm lazy, additional features will pretty much only be implemented when there is a need
 * for me to do so.
 * some ideas might include:
 * - a function grapher, i.e. the constructor takes a string equation or provides a
 * function interface to implement instead of just a data set.
 * - capability to graph surfaces instead of just lines
 * - a dialog window that pops up (like the one JClass has) for users to change the settings
 * manually
 */
public class ThreeD extends JPanel implements Stepper, MouseListener, MouseMotionListener, KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 768912425772549700L;
    public Model3D md = null;
    boolean painted = true;
    boolean needsRefresh = true;
    float scalefudge = 1.3f;
    float xtranslate = 0, ytranslate = 0;
    double[][][] pointArray;
    Color backGround;

    /**
     * used to determine the size of the graph
     */
    float xfac;

    /**
     * used to determine how far the mouse was dragged
     */
    int prevx, prevy;

    /**
     * these are the matricies that control the tranformations
     * of the 3D graph.
     */
    final Matrix3D amat = new Matrix3D();
    final Matrix3D tmat = new Matrix3D();

    /**
     * this float contains the information on which axis is currently the longest
     */
    float longestAxis = 0;

    /*
     */
    int increment, counter, numberPoints;
    boolean liveDone = true;


    /***************
     * constructors *
     ***************/
    public ThreeD() {
        super();
        amat.yrot(-46.6);
        amat.xrot(-21.8);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setVisible(true);
    }

    /**
     * Constructor that takes in data in the same format as setData.
     */
    public ThreeD(double[][][] points) {
        this();
        pointArray = points;
        md = new Model3D(pointArray);
        setBackground(md.backColor);
        run();
        xtranslate = getSize().width / 2;
        ytranslate = getSize().height / 2;
        counter = numberPoints = points[0][0].length;
    }

    public void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeKeyListener(this);
    }

    /**
     * Scales the model to the size of the panel.
     */
    public void run() {
        if (!hasFocus()) requestFocus();
        if (md == null) return;
        setColors(ColorScheme.colors, ColorScheme.bG);
        try {
            if (needsRefresh) {
                md.findBB();
            }
            float xw = md.xmax - md.xmin;
            float yw = md.ymax - md.ymin;
            float zw = md.zmax - md.zmin;
            if (yw > xw)
                xw = yw;
            if (zw > xw)
                xw = zw;
            longestAxis = xw;
            float f1 = getSize().width / xw;
            float f2 = getSize().height / xw;
            xfac = 0.4f * (f1 < f2 ? f1 : f2) * scalefudge;
        } catch (Exception e) {
            md = null;
            edu.umn.ecology.populus.fileio.Logging.log("Error in the model.");
        }
    }

    /*******************
     * listener methods *
     *******************/
    /**
     * Will draw a string with the value of the model at the point clicked.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if ((e.getModifiersEx() & InputEvent.META_DOWN_MASK) != 0)
            reset();
        else md.drawValueAt(getGraphics(), x, y);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocus();
        prevx = e.getX();
        prevy = e.getY();
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Mouse dragged will modify the model.
     * if:
     * <ul>
     *    <li>shift is held, it will translate the graph
     *    <li>control is held, it will rotate around the z-axis
     *    <li>no modifiers, it will rotate around the x and y axes
     * </ul>
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        tmat.unit();
        float xtheta = (prevy - y);
        float ytheta = (x - prevx);

        if (e.isShiftDown()) {
            //tmat.translate(ytheta/xfac,xtheta/xfac,0);

			/* We don't want to do transformations on the kind of translations we want here,
            so this data is saved for use after the transformations have been applied. */
            xtranslate += ytheta;
            ytranslate -= xtheta;
        } else if (e.isControlDown()) {
            if (e.getX() > (getSize().width / 2))
                tmat.zrot(xtheta / scalefudge / 4);
            else
                tmat.zrot(-xtheta / scalefudge / 4);
            if (e.getY() > (getSize().height / 2))
                tmat.zrot(ytheta / scalefudge / 4);
            else
                tmat.zrot(-ytheta / scalefudge / 4);
        } else {
            tmat.xrot(xtheta / scalefudge / getSize().width * 360);
            tmat.yrot(ytheta / scalefudge / getSize().height * 360);
        }

        amat.mult(tmat);
        needsRefresh = true;
        if (painted) {
            painted = false;
            repaint();
        }
        prevx = x;
        prevy = y;
        e.consume();
    }

    /**
     * the help file accessed through the KeyListener javadoc says that the isFocusTraversable
     * method should be overridden from the parent component and set to return true every time.
     *
     * @return always will be true
     */
    @Override
    public boolean isFocusable() {
        return true;
    }

    /**
     * not useful right now, all my needs are covered with the other two methods from KeyListener
     *
     * @param ke
     */
    @Override
    public void keyReleased(KeyEvent ke) {
    }

    /**
     * this method is implemented because it is only in the keyPressed and keyReleased methods that the
     * arrow keys can be picked up. if the shift key is held down while pressing one of the arrow keys, then
     * it will be a finer adjustment.
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        int factor;
        int i = ke.getKeyCode();
        if (ke.isShiftDown())
            factor = 1;
        else
            factor = 10;

        switch (i) {
            /*left*/
            case 37 -> {
                xtranslate -= factor;
                repaint();
            }
            /*right*/
            case 39 -> {
                xtranslate += factor;
                repaint();
            }
            /*up*/
            case 38 -> {
                ytranslate -= factor;
                repaint();
            }
            /*down*/
            case 40 -> {
                ytranslate += factor;
                repaint();
            }
        }
        needsRefresh = true;
        ke.consume();
    }

    /**
     * this method picks up the zoom keys, which are set to be the buttons with + or - on them.
     * holding shift will give a finer adjustment.
     *
     * @param ke
     */
    @Override
    public void keyTyped(KeyEvent ke) {
        float factor;
        char c = ke.getKeyChar();
        if (ke.isShiftDown())
            factor = 1.01f;
        else
            factor = 1.1f;

        switch (c) {
            case '=', '+' -> setScaleFudge(getScaleFudge() * factor);
            case '-', '_' -> setScaleFudge(getScaleFudge() / factor);
        }
        needsRefresh = true;
        ke.consume();
    }


    @Override
    public void setUpLive(int inc) {
        increment = inc;
        liveDone = false;
        counter = 0;
        //System.gc();
    }

    /**
     * inherited from Stepper, call this method to add another data
     * point to the drawing
     *
     * @return true means more iterations possible
     */
    @Override
    public boolean stepGraph() {
        if (counter + increment < numberPoints) {
            counter += increment;
            repaint();
        } else {
            liveDone = true;
            counter = numberPoints;
        }
        return !liveDone;
    }

    /**
     * Paint the model to the screen.
     */
    @Override
    public void paint(Graphics g) {
        run();
        g.setColor(md.backColor);
        g.fillRect(0, 0, getSize().width, getSize().height);

        if (md != null) {
            md.mat.unit();

            md.mat.translate(-longestAxis / 2, -longestAxis / 2, -longestAxis / 2);

			/* This is a pivot point. If translations happen before this line, then the transformations
            will act on those translations. If you don't want the transformation to act on the translation,
            then do it after... i.e. these operations are not commutative.*/
            md.mat.mult(amat);

            md.mat.scale(xfac, -xfac, 16 * xfac / getSize().width);
            md.mat.scaleGraph(longestAxis / md.xMaxEnforced, longestAxis / md.yMaxEnforced, longestAxis / md.zMaxEnforced);
            md.mat.translate(xtranslate + getSize().width / 2, ytranslate + (int) (getSize().height / 1.85), 8);
            md.setTransformed(false);
            md.paint(g, counter, needsRefresh);
            needsRefresh = false;
            g.setFont(new Font("Dialog", Font.BOLD, 20));
            g.setColor(getTextColor());
            HTMLFreeLabel.paintHTML(g, md.graphLabels[3], 10, 30);
            painted = true;
        }
    }

    /**
     * Set all of the labels at once. Format of the array is x label, y,z main.
     */
    public void setGraphLabels(String[] labels) {
        if (labels.length < 4)
            return;
        md.graphLabels[0] = labels[0];
        md.graphLabels[1] = labels[1];
        md.graphLabels[2] = labels[2];
        md.graphLabels[3] = labels[3];
    }

    /**
     * Label for the x axis.
     */
    public void setXLabel(String xLabel) {
        md.graphLabels[0] = xLabel;
    }

    /**
     * Label for the y axis.
     */
    public void setYLabel(String yLabel) {
        md.graphLabels[1] = yLabel;
    }

    /**
     * Label for the z axis.
     */
    public void setZLabel(String zLabel) {
        md.graphLabels[2] = zLabel;
    }

    /**
     * If encompassingGrid is not set false, then the graph will have one extra grid width
     * added to the axis above the max value.
     */
    public void setXMax(float newXMax) {
        md.encompassingGrid = false;
        md.createGrid(md.numLines, newXMax, md.ymax, md.zmax);
        repaint();
    }

    public void setYMax(float newYMax) {
        md.encompassingGrid = false;
        md.createGrid(md.numLines, md.xmax, newYMax, md.zmax);
        repaint();
    }

    public void setZMax(float newZMax) {
        md.encompassingGrid = false;
        md.createGrid(md.numLines, md.xmax, md.ymax, newZMax);
        repaint();
    }

    /**
     * The label that goes at the top of the panel.
     */
    public void setMainLabel(String mainLabel) {
        md.graphLabels[3] = mainLabel;
    }

    /**
     * Put the model back in it's original position, and original grid setting.
     */
    public void reset() {
        md.mat.unit();
        //md.encompassingGrid = true;
        md.createGrid(10);
        md.mat.unitScale();
        amat.unit();
        needsRefresh = true;
        amat.yrot(-46.6);
        amat.xrot(-21.8);
        scalefudge = 1.3f;
        xtranslate = 0;
        ytranslate = 0;
        repaint();
    }

    /**
     * Modify the number of gridlines.
     */
    public void setNumGridLines(int newNumGridLines) {
        md.createGrid(newNumGridLines);
        repaint();
    }

    /**
     * Returns the number of gridlines.
     */
    public int getNumGridLines() {
        return md.numLines;
    }

    /**
     * sets whether the grid label text on the axes are allowed to over lap.
     * if b is true, then labels that would overlap are simply not drawn. this is useful because
     * if they were to overlap, then they would be unreadable. i'm not sure
     * if this is a "feature" that should be accessable to the user. maybe i should just say
     * "true" for all time...
     */
    public void setSqueezeLabels(boolean b) {
        md.setSqueezeLabels(b);
    }

    /**
     * get's whether the labels are allowed to overlap or not.
     */
    public boolean getSqueezeLabels() {
        return md.getSqueezeLabels();
    }

    /**
     * Make the grids on the 3 planes visible.
     */
    public void setGridVisible(boolean isVisible) {
        md.gridVisible = isVisible;
        repaint();
    }

    /**
     * the range of values permitted here is [0.05,250]. any attempt
     * to go outside this region will be ignored.
     *
     * @param ns
     */
    public void setScaleFudge(float ns) {
        if (ns > 250 || ns < 0.05) return;
        scalefudge = ns;
        repaint();
    }

    /**
     * gets the scale factor used in sizing the graph
     */
    public float getScaleFudge() {
        return scalefudge;
    }

    /**
     * Set the data model will use. The format of the array is that the first array
     * contains the x-coordinates, the second array the y, the third, z.
     */
    public void setData(double[][][] newArray) {
        pointArray = newArray;
        md = new Model3D(pointArray);
        counter = numberPoints = pointArray[0][0].length;
        repaint();
    }

    /**
     * Set the colors the graph will use. The array are the colors used in the
     * picture, the color is for the background.
     */
    public void setColors(Color[] graphColors, Color backGround) {
        this.backGround = backGround;
        setBackground(backGround);
        super.setBackground(backGround);
        md.setBackGroundColors(backGround, getTextColor());
        md.setColors(graphColors);
    }

    /**
     * This function will calculate a color for the text based on the
     * color of the background it will be placed on. i.e. if the background
     * is too dark, then it will return a lighter gray instead of black.
     */
    private Color getTextColor() {
        int trans = backGround.getRGB();
        int r, b, g;
        r = backGround.getRed();
        b = backGround.getBlue();
        g = backGround.getGreen();
        if (r < 80 && b < 80 && g < 80) {
            trans += 256 * 256 * 256 + 1;
            trans *= -1;
        } else trans = -256 * 256 * 256;
        return new Color(trans);
    }

    /**
     * This method sets confines the z-axis to integers
     */
    public void setZIsDiscrete(boolean b) {
        md.setZIsDiscrete(b);
    }

    /**
     * This method sets confines the x-axis to integers
     */
    public void setXIsDiscrete(boolean b) {
        md.setXIsDiscrete(b);
    }

    /**
     * The label "t=0" will be placed on the first data point.
     */
    public void setLabelT(boolean b) {
        md.setLabelT(b);
    }

    /**
     * The dots will be placed at the data points if discrete, and when implemented,
     * there will only be integers on the whichAxis axis, because it is discrete.
     */
    public void setIsDiscrete(boolean b, int whichAxis) {
        md.setIsDiscrete(b);
        //then we need to tell the model to only show integers on whichAxis
    }

    public void setMultiColored(boolean b) {
        md.setMultiColored(b);
    }
}
