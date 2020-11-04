package edu.umn.ecology.populus.visual.stagegraph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Vector;

/**
 * this class controls the properties of the stages in StageStructuredGrowth. Essentially, all it does
 * is control the painting, hold references to the Transitions who connect to it, control the position of it's Label
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author unascribed
 * @version 1.0
 */
public class Stage extends Ellipse2D implements StageShape, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 5077369746978999456L;
    //Color c = new Color(40,84,40);
    final Color c = Color.black;
    //Color ac = new Color(161,210,88);
    final Color ac = Color.green;
    public double x;
    public double y;
    public double width = 50 * StageStructuredPane.scale;
    public double height = 50 * StageStructuredPane.scale;
    boolean filled = false;
    boolean isActive = false;
    final Label l;
    final AffineTransform at = new AffineTransform();
    final Vector<Transition> starts = new Vector<>(0);
    final Vector<Transition> stops = new Vector<>(0);
    final Vector<Stage> allStages;
    final Vector<Stage> queue;
    String name = "Stage #%n";
    final Component comp;
    int indexshift = 0;
    final int type;
    final String picture_name = "growtrans.gif";

    /**
     * constructor
     *
     * @param x
     * @param y
     * @param pop       value held by the stage
     * @param type      whether it is for a kSSPG or a kASPG graph
     * @param allStages the vector storing all stages already added to the viewing plane
     * @param queue     the vector for stages who haven't been located yet
     * @param comp      the ImageObserver object for drawing the stages images
     */
    Stage(int x, int y, double pop, int type, Vector<Stage> allStages, Vector<Stage> queue, Component comp) {
        this.allStages = allStages;
        this.queue = queue;
        this.type = type;
        this.comp = comp;
        setFrame(x, y, width, height);
        l = new Label(this, (int) (getX() - 20), (int) (getY() - 20), pop);
    }

    /**
     * if the object isn't drawable (i.e. not located yet), we want to know
     *
     * @return
     */
    public boolean isDrawable() {
        return (getX() > -9999 || getY() > -9999);
    }

    @Override
    public void paint(Graphics g) {
        if (!isDrawable()) return;
        Graphics2D g2d = (Graphics2D) g;
        ImageIcon ii = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Stage.class.getResource(picture_name)));
        g2d.drawImage(ii.getImage(), (int) (getX()), (int) (getY()), (int) getWidth(), (int) getHeight(), comp);
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (isActive) {
            g2d.setColor(ac);
        } else {
            g2d.setColor(c);
        }

        if (filled) {
            g2d.fill(this);
        } else {
            g2d.draw(this);
        }

        g2d.setColor(Color.white);
        int index = getShiftedIndex();
        String indexL = "" + index;
        g.setFont(new Font("Serif", Font.BOLD, 30));
        double w = g.getFontMetrics().stringWidth(indexL);
        double h = g.getFontMetrics().getAscent();
        g2d.drawString(indexL, (int) (getCenter().x - w / 2), (int) (getCenter().y + h / 2 - 5));
    }

    public void paintLabel(Graphics g) {
        l.paint(g);
    }

    @Override
    public Label getLabel() {
        return l;
    }

    public double getValue() {
        return l.v;
    }

    /**
     * turn all the %n's in the label into stage indicies
     *
     * @param s
     * @return
     */
    String transformName(String s) {
        int i = s.indexOf("%n", 0);
        while (i != -1) {
            s = s.substring(0, i) + getShiftedIndex() + s.substring(i + 2, s.length());
            i = s.indexOf("%n", 0);
        }
        return s;
    }

    @Override
    public String getName(boolean wantMapped) {
        if (wantMapped) return transformName(name);
        else return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getNamePrefix() {
        return "<i>N</i><sub>" + getShiftedIndex() + "</sub>(0) = ";
    }

    @Override
    public void setNamePrefix(String s) {

    }

    /**
     * we can only reindex Stages who have been located.
     *
     * @param i
     * @return
     */
    public boolean setIndex(int i) {
        if (i >= allStages.size() || i < 0) return false;
        allStages.remove(this);
        allStages.insertElementAt(this, i);
        return true;
    }

    @Override
    public void setActive(boolean b) {
        isActive = b;
    }

    public void setFilled(boolean b) {
        filled = b;
    }

    public boolean getFilled() {
        return filled;
    }

    /**
     * this is to let the Transitions know if the Stage was moved
     *
     * @param p
     * @param bounds
     */
    public void setPosition(Point p, Rectangle bounds) {
        this.x = p.x - width / 2.0;
        this.y = p.y - height / 2.0;
        for (Transition start : starts) start.setStart(getCenter());
        for (Transition stop : stops) stop.setStop(getCenter());
        setLabelPosition(bounds);
    }

    /**
     * we want the label to be a specified distance from the center of the stage.
     * the default position for kSSPG is that the vector formed by the center of the Stage
     * to the center of the Label points to the nearest corner. if kASPG, then the first stage points
     * toward the upper left corner, all the others to the lower right.
     *
     * @param r
     */
    public void setLabelPosition(Rectangle r) {
        double distance = 75;
        if (type == StageStructuredPane.kASPG) {
            distance = 65;
        }
        distance *= StageStructuredPane.scale;
        double xdis = -distance;
        double ydis = -distance;
        double xcoor = getCenter().x, ycoor = getCenter().y;
        boolean topRight = (type == StageStructuredPane.kASPG) && (getIndex() != 1);

        if (xcoor > r.width / 2 && !topRight) {
            xdis *= -1;
            xcoor = r.width - xcoor;
        }
        if (ycoor > r.height / 2 || topRight) {
            ydis *= -1;
            ycoor = r.height - ycoor;
        }
        double angle = Math.atan(ycoor / xcoor);
        l.setPosition(new PointD(getCenter().x + xdis * Math.cos(angle), getCenter().y + ydis * Math.sin(angle)));
    }

    public void rotateLabel(Point p) {
        PointD c = getCenter();//center of stage
        //c.y -= height/2;//not sure why i have to do this...
        PointD lp = l.getPosition();//position of label
        PointD r = new PointD(lp.x - c.x, lp.y - c.y);//position of label relative to stage

        double dtheta = arcTan(p.y - c.y, p.x - c.x) - arcTan(r.y, r.x);//angle of drag relative to label

        at.setToIdentity();//clear the transform
        at.translate(c.x, c.y);//translate to center of circle (cause that's what we're rotating around
        at.rotate(dtheta);//rotate
        double[] t = new double[2];
        at.transform(new double[]{r.x, r.y}, 0, t, 0, 1);
        l.setPosition(new PointD(t));//set new label position
    }

    public double arcTan(double dy, double dx) {
        double angle;
        if (dx != 0) {
            angle = Math.atan(dy / dx);
        } else {
            if (dy < 0)
                angle = 3.0 * Math.PI / 2.0;
            else
                angle = Math.PI / 2.0;
        }

        if (dy < 0 && dx > 0) angle += 2 * Math.PI;
        if (dx < 0) angle += Math.PI;

        return angle;
    }

    public void registerTransition(Transition trans, boolean isStart) {
        if (isStart) {
            starts.add(trans);
            trans.setStart(getCenter());
        } else {
            stops.add(trans);
            trans.setStop(getCenter());
        }
    }


    public void removeStarts(Vector<Transition> trans) {
        for (Transition start : starts) trans.remove(start);
        starts.clear();
    }

    public void removeStops(Vector<Transition> trans) {
        for (Transition stop : stops) trans.remove(stop);
        stops.clear();
    }

    /**
     * when the stage is removed, we need to also remove all the transitions connected to it
     * the reason the starts are separated from the stops is because the ASPG rules sometimes dictate
     * that certain stages can't have starts
     *
     * @param trans
     */
    public void removeStage(Vector<Transition> trans) {
        removeStarts(trans);
        removeStops(trans);
    }

    /**
     * when a Transition is removed, we want to remove all reference to it. so this removes the reference
     * the stage maintains
     *
     * @param t
     * @return
     */
    public boolean removeTransition(Transition t) {
        for (int i = 0; i < starts.size(); i++)
            if (t.equals(starts.get(i))) {
                starts.remove(i);
                return true;
            }
        for (int i = 0; i < stops.size(); i++)
            if (t.equals(stops.get(i))) {
                stops.remove(i);
                return true;
            }
        return false;
    }

    public boolean isStart(Transition trans) {
        for (Transition start : starts)
            if (trans.equals(start))
                return true;
        return false;
    }

    public boolean isStop(Transition trans) {
        for (Transition stop : stops)
            if (trans.equals(stop))
                return true;
        return false;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    /**
     * if we want to know what the index of the stage is, whether it has been located or not
     *
     * @return
     */
    public int getIndex() {
        if (allStages.contains(this)) return allStages.indexOf(this) + 1;
        if (queue.contains(this)) return queue.indexOf(this) + allStages.size() + 1;
        return -1;
        //return allStages.indexOf(this)+1;
    }

    /**
     * used mostly for drawing purposes so the stage index can be correctly displayed
     *
     * @return
     */
    public int getShiftedIndex() {
        return getIndex() + indexshift;
    }

    /**
     * the indexshift is in case we want the numbering to start somewhere other than 1
     *
     * @param indexshift
     */
    public void setIndexShift(int indexshift) {
        this.indexshift = indexshift;
    }

    public PointD getCenter() {
        //return new PointD(getX()+width/2,getY());
        return new PointD(getX() + width / 2, getY() + height / 2);
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public boolean isEmpty() {
        return (width <= 0.0 || height <= 0.0);
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return new Rectangle2D.Double(x, y, width, height);
    }


}

