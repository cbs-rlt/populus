package edu.umn.ecology.populus.plot.definetti;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.Stepper;
import edu.umn.ecology.populus.visual.HTMLFreeLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * this class is for drawing de finetti graphs
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author Amos Anderson
 * @version 5.2
 */
public class DeFinettiGraph extends JPanel implements Stepper, MouseListener, MouseMotionListener, KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 5692947888221877327L;
    /**
     * change this angle for changing the angles of the base of the triangle
     */
    double sqrt3div2 = Math.sin(Math.PI * 40.0d / 180.0d);
    private int[][][] data;
    private double[][][] points;
    double scale;
    final double minorLength = 0.02;
    final double majorLength = 0.1;
    private final int bottomSpace = 50;
    private final int topSpace = 60;
    double xstart, ystart;
    String mainCap = "", firstCap = "", secondCap = "", thirdCap = "";
    Font mainFont = new Font("Dialog", Font.PLAIN, 18);
    int topX, topY, leftY, leftX, rightX, rightY;
    int xshift = 0, yshift = 0;
    int prevy, prevx;
    int numberPoints, counter, increment;
    int lastCounter = 1;
    double gridFactor = 1.0, zoomFactor = 1.0;
    BufferedImage bi;
    Graphics2D big;
    boolean imageStarted = false;
    boolean drawGrid = true;
    boolean liveDone = true;
    boolean chartChanged = true;

    public DeFinettiGraph() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    public DeFinettiGraph(double[][][] points) {
        this();
        setData(points);
    }

    public DeFinettiGraph(BasicPlotInfo bpi) {
        this();
        mainCap = bpi.getMainCaption();
        firstCap = bpi.getXCaption();
        secondCap = bpi.getYCaption();
        thirdCap = bpi.getZCaption();
        points = bpi.getData();
        setData(points);
    }

    public void destroy() {
        removeMouseListener(this);
        removeMouseMotionListener(this);
        removeKeyListener(this);
    }

    public void setCaptions(String m, String x, String y, String z) {
        mainCap = m;
        firstCap = x;
        secondCap = y;
        thirdCap = z;
    }

    public void setData(double[][][] points) {
        this.points = points;
        numberPoints = points[0][0].length;
        imageStarted = false;
        counter = numberPoints;
    }

    public void resetGraph() {
        imageStarted = false;
        zoomFactor = 1.0;
        gridFactor = 1.0;
        xshift = 0;
        yshift = 0;
        sqrt3div2 = Math.sin(Math.PI * 40.0d / 180.0d);
        if (!liveDone) counter = 0;
        repaint();
    }

    /**
     * this method will transform the data into "de finetti space".
     * in definetti space:
     * x = x + y/2
     * y = sqrt(3)/2*y
     *
     * @param lhs
     * @return
     */
    public int[][][] transformData(double[][][] lhs) {
        int[][][] rhs = new int[lhs.length][2][];
        for (int i = 0; i < rhs.length; i++) {
            rhs[i][0] = new int[lhs[i][0].length];
            rhs[i][1] = new int[lhs[i][1].length];
            for (int j = 0; j < rhs[i][0].length; j++) {
                rhs[i][0][j] = (int) (scale * (lhs[i][0][j] + lhs[i][1][j] / 2.0d) + 0.5d);
                rhs[i][1][j] = (int) (scale * sqrt3div2 * lhs[i][1][j] + 0.5d);
            }
        }
        data = rhs;
        return rhs;
    }

    @Override
    public void setUpLive(int inc) {
        increment = inc;
        liveDone = false;
        counter = 0;
        System.gc();
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
        }
        return !liveDone;
    }

    /**
     * will prepare an image, and then blit it onto the <code>g</code> passed in
     * as a parameter.
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        prepNextImage();
        g.setColor(ColorScheme.bG);
        Rectangle r = getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        g.drawImage(bi, 0, 0, this);
        drawLeadingMarks(g);
    }

    /**
     * this method will prepare the next image on a BufferedImage object.
     */
    public void prepNextImage() {
		/*we only need to create the BufferedImage object once, after that the image
      is painted over, and then redrawn*/
        if (!imageStarted) {
            requestFocus();
            bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            big = bi.createGraphics();
            imageStarted = true;
            chartChanged = true;
        }

		/*This segment is for when a drawing parameter like zoom or scale or sqrt3div2 is changed
      the entire drawing (if live, then entire drawing so far) needs to be redrawn*/
        if (chartChanged) {
            big.setColor(ColorScheme.bG);
            big.fillRect(0, 0, getSize().width, getSize().height);

            scale = zoomFactor * (getSize().height - bottomSpace - topSpace) / sqrt3div2;
            xstart = getSize().width / 2 - (int) (scale / 2.0) + xshift;
            ystart = getSize().height - bottomSpace + yshift;
            data = transformData(points);

            setMarkers();
            drawLabels(big);
            drawAxes(big);
            big.setColor(Color.darkGray);
            if (drawGrid) drawGrid(big);
            drawData(big, false);
            chartChanged = false;
        }

		/*draw the data. this actually only draws the next data point, if we are at the end of
      the drawing, calling this method should change nothing*/
        drawData(big, true);
    }

    /**
     * isIncrement refers to whether we need to draw the entire data set over
     * or just the next bit.
     *
     * @param g
     * @param isIncrement
     */
    void drawData(Graphics g, boolean isIncrement) {
        /*apparently, it is possible for this to happen, so take steps to prevent a crash*/
        if (lastCounter == 0 && isIncrement) {
            lastCounter = counter;
            return;
        }
        for (int j = isIncrement ? lastCounter : 1; j < counter; j++)
            for (int i = 0; i < data.length; i++) {
                g.setColor(ColorScheme.colors[i % ColorScheme.colors.length]);
                g.drawLine((int) xstart + data[i][0][j - 1], (int) ystart - data[i][1][j - 1],
                        (int) xstart + data[i][0][j], (int) ystart - data[i][1][j]);
            }
        lastCounter = counter;
    }

    /**
     * this draws the little circles that lead the data "trail" as it is plotted live. if the graph isn't live,
     * then a circle will appear at the last point.
     *
     * @param g
     */
    void drawLeadingMarks(Graphics g) {
        int[] d = new int[]{8, 6, 4};
        int index;
        for (int i = 0; i < data.length; i++) {
            g.setColor(ColorScheme.colors[i % ColorScheme.colors.length]);
            index = 1;
            for (int j = 0; j < 3; j++) {
                if (data[i][0].length > (counter - index) && (counter - index) >= 0)
                    g.fillOval((int) (xstart + data[i][0][counter - index] - d[j] / 2.0), (int) (ystart - data[i][1][counter - index] - d[j] / 2.0), d[j], d[j]);
                index += 10;
            }
        }
    }

    /**
     * these data are considered to be worth saving...
     */
    void setMarkers() {
        topY = (int) (ystart - sqrt3div2 * scale);
        topX = (int) (xstart + scale / 2.0d);
        rightY = (int) ystart;
        rightX = (int) (xstart + scale);
        leftY = (int) ystart;
        leftX = (int) xstart;
    }

    void drawGrid(Graphics g) {
        int numMajor = (int) (1.0 / majorLength + 0.5);
        double spacing = majorLength / gridFactor;
        int numLines = (int) (numMajor * gridFactor);
        for (double i = 1; i < numLines; i++) {
            g.drawLine((int) (xstart + i * spacing * scale), (int) ystart,
                    (int) (xstart + i * spacing * scale / 2.0d), (int) (ystart - sqrt3div2 * i * scale * spacing));
            g.drawLine((int) (xstart + i * spacing * scale / 2.0d), (int) (ystart - sqrt3div2 * i * scale * spacing),
                    (int) (rightX - i * spacing * scale / 2.0d + 1), (int) (ystart - sqrt3div2 * i * scale * spacing));
            g.drawLine((int) (rightX - i * spacing * scale / 2.0d + 1), (int) (ystart - sqrt3div2 * i * scale * spacing),
                    (int) (xstart + (numLines - i) * spacing * scale), (int) ystart);
        }
    }

    /**
     * draws the edge of the graph, but also the major and minor tic marks with their labels
     *
     * @param g
     */
    void drawAxes(Graphics g) {
        int lSize = 10;
        String mark;
        g.setColor(Color.black);
        drawThickLine(g, leftX, leftY, topX, topY);//left line
        drawThickLine(g, leftX, leftY, rightX, rightY);//bottom line
        drawThickLine(g, topX, topY, rightX, rightY);//right line

        int numMajor = (int) (1.0 / majorLength + 0.5);
        int numMinor = (int) (1.0 / minorLength + 0.5);
        int minorSize = 3;
        int majorSize = 7;

        /*minor tic marks*/
        for (double i = 0; i <= numMinor; i++) {
            g.drawLine((int) (xstart + i * minorLength * scale), (int) ystart, (int) (xstart + i * minorLength * scale), (int) ystart + minorSize);
            g.drawLine((int) (xstart + i * minorLength * scale / 2.0d), (int) (ystart - sqrt3div2 * i * scale * minorLength),
                    (int) (xstart + i * minorLength * scale / 2.0d - minorSize * sqrt3div2), (int) (ystart - sqrt3div2 * i * scale * minorLength - minorSize / 2.0d));
            g.drawLine((int) (rightX - i * minorLength * scale / 2.0d + 1), (int) (ystart - sqrt3div2 * i * scale * minorLength),
                    (int) (rightX - i * minorLength * scale / 2.0d + minorSize * sqrt3div2 + 1), (int) (ystart - sqrt3div2 * i * scale * minorLength - minorSize / 2.0d));
        }

        /*major tic marks with their labels*/
        g.setFont(new Font("Dialog", Font.PLAIN, lSize));
        for (double i = 0; i <= numMajor; i++) {
            mark = "" + NumberMath.roundSig(majorLength * i, 4, 0);

            g.drawLine((int) (xstart + i * majorLength * scale), (int) ystart, (int) (xstart + i * majorLength * scale), (int) ystart + majorSize);
            g.drawString(mark, (int) (xstart + i * majorLength * scale), (int) ystart + majorSize + lSize);

            g.drawLine((int) (xstart + i * majorLength * scale / 2.0d), (int) (ystart - sqrt3div2 * i * scale * majorLength),
                    (int) (xstart + i * majorLength * scale / 2.0d - majorSize * sqrt3div2), (int) (ystart - sqrt3div2 * i * scale * majorLength - majorSize / 2.0d));
            g.drawString(mark, (int) (topX - i * majorLength * scale / 2.0d - majorSize * sqrt3div2) - lSize - 5, (int) (topY + sqrt3div2 * i * scale * majorLength - majorSize / 2.0d));

            g.drawLine((int) (rightX - i * majorLength * scale / 2.0d + 1), (int) (ystart - sqrt3div2 * i * scale * majorLength),
                    (int) (rightX - i * majorLength * scale / 2.0d + majorSize * sqrt3div2 + 1), (int) (ystart - sqrt3div2 * i * scale * majorLength - majorSize / 2.0d));
            g.drawString(mark, (int) (rightX - i * majorLength * scale / 2.0d + majorSize * sqrt3div2 + 1) + 2, (int) (ystart - sqrt3div2 * i * scale * majorLength - majorSize / 2.0d));
        }
    }

    /**
     * draws the captions
     *
     * @param g
     */
    void drawLabels(Graphics g) {
        int bigSize = (int) (getWidth() / 25.0d + 0.5);
        int smallSize = (int) (getWidth() / 35.0d + 0.5);
        g.setColor(Color.black);

        g.setFont(new Font("Dialog", Font.PLAIN, bigSize));
        HTMLFreeLabel.paintHTML(g, mainCap, 25, bigSize + 5);

        g.setFont(new Font("Dialog", Font.PLAIN, smallSize));
        int fLen = g.getFontMetrics().stringWidth(HTMLFreeLabel.getPlainText(firstCap, false)) + 5;
        int tLen = g.getFontMetrics().stringWidth(HTMLFreeLabel.getPlainText(thirdCap, false)) + 5;
        HTMLFreeLabel.paintHTML(g, firstCap, rightX - fLen, rightY + smallSize + 15);
        HTMLFreeLabel.paintHTML(g, secondCap, topX + bigSize + 15, topY + smallSize - 5);
        HTMLFreeLabel.paintHTML(g, thirdCap, leftX - tLen - 10, leftY - smallSize);
    }

    /**
     * Doubles the thickness of a drawn line. probably isn't really needed because
     * Graphics2D can do it, but who cares?
     */
    void drawThickLine(Graphics g, int x1, int y1, int x2, int y2) {
        if (y1 != y2) {
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x1 + 1, y1, x2 + 1, y2);
        } else {
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x1, y1 + 1, x2, y2 + 1);
        }
    }

    private void jbInit() throws Exception {
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                this_componentResized(e);
            }
        });
    }

    void this_componentResized(ComponentEvent e) {
        resetGraph();
    }

    /***************
     * Accessors   *
     ***************/

    public void setGridVisible(boolean b) {
        drawGrid = b;
        chartChanged = true;
    }

    public boolean getGridVisible() {
        return drawGrid;
    }

    public void setZoomFactor(double f) {
        zoomFactor = f;
        chartChanged = true;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public void setGridFactor(double f) {
        if (f > 4.0 || f < 0.5) return;
        gridFactor = f;
        chartChanged = true;
    }

    public double getGridFactor() {
        return gridFactor;
    }

    /*******************
     * listener methods *
     *******************/
    /**
     * Will draw a string with the value of the model at the point clicked.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if ((e.getModifiersEx() & InputEvent.META_DOWN_MASK) != 0)
            resetGraph();
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

        if (e.isShiftDown()) {
            sqrt3div2 += (prevx - x) / scale;
        } else {
            yshift += (y - prevy);
            xshift += (x - prevx);
        }
        prevx = x;
        prevy = y;

        chartChanged = true;
        repaint();
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
		switch(i){
		case 37:/*left*/
            xshift -= factor;
            repaint();
            break;
		case 39:/*right*/
			xshift += factor;
			repaint();
			break;
		case 38:/*up*/
			yshift -= factor;
			repaint();
			break;
		case 40:/*down*/
			yshift += factor;
			repaint();
			break;
		}
        chartChanged = true;
        repaint();
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

		switch(c){
		case '=':
		case '+':
			setZoomFactor(getZoomFactor()*factor);
			break;
		case '-':
		case '_':
			setZoomFactor(getZoomFactor()/factor);
			break;
		case 'r':
		case 'R':
			resetGraph();
			break;
		}
        chartChanged = true;
        repaint();
        ke.consume();
    }
}
