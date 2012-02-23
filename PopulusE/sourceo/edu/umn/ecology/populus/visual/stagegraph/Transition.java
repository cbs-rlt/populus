package edu.umn.ecology.populus.visual.stagegraph;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.*;

/**
 * this class is all about the transitions between the stages. this class is a CubicCurve2D, which is defined by 4 points:
 * the 2 centers, and 2 control points. this transition positions the 2 control points such that the line connecting them is
 * parallel to the line connecting the 2 centers. this causes the curve to look like a parabola, except that is is better
 * shaped.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */
public class Transition extends CubicCurve2D implements StageShape, Serializable {
   Color ac = Color.blue;
   double x1=0;
   double y1=0;
   double ctrlx1=0;
   double ctrly1=0;
   double ctrlx2=0;
   double ctrly2=0;
   double x2=0;
   double y2=0;
   double labelx = 0;
   double labely = 0;
   boolean filled = false;
   boolean isActive = false;
   String name;
   String value;
   Label l;
   AffineTransform at = new AffineTransform();
   int shift = 30;
   final int type;
   boolean isStraight = true;
   boolean isCircular = false;
   Stage[] connection = new Stage[2];
   final int circle = 100;
   int timeIndex = 0;

   /**
    * the transition wants to have references to the two stages it connects, as well as know
    * whether it is for a kSSPG or kASPG graph
    * @param value
    * @param start
    * @param stop
    * @param type
    */
   public Transition(double value, Stage start, Stage stop, int type){
      l = new Label(this,0,0,value);
      connection[0] = start;
      connection[1] = stop;
      this.type = type;
      if(start == stop) isCircular = true;
   }

   public void setTimeIndex(int i){
     timeIndex = i;
   }

   public double getValue(){
      return l.v;
   }

   /**
    * if the graph is kASPG, then we always want one of the transitions to be straight.
    * also, it turns out that for a circular transition, we don't want pulses.
    * Also, GradientPaint2 is used here so that the gradient can move.
    * @param g
    */
   public void paint(Graphics g){
      if(!connection[0].isDrawable() || !connection[1].isDrawable()) return;
      Graphics2D g2d = (Graphics2D)g;
      g2d.setStroke(new BasicStroke(7.0f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND));
      Paint p;

      if(type == StageStructuredPane.kASPG){
         if(connection[0].getIndex() < connection[1].getIndex()){
            isStraight = true;
         } else if(connection[0].getIndex() > connection[1].getIndex()){
            isStraight = false;
            shift = 100;
         }
         setCtrlPositions();
      }

      if(connection[0] == connection[1]) {
         timeIndex = 128;
      }

      if(isActive){
         g2d.setColor(ac);
         g2d.draw(this);
         return;
      }

      double divisions = 6;
      //set number of pulses
      double cw = (x2-x1)/divisions;
      double ch = (y2-y1)/divisions;

      int period = 511-timeIndex%512;
      Color[] colors = new Color[] {Color.black, Color.green};
      p = new GradientPaint2((int)getX1(),(int)getY1(),colors[0],
                             (int)(getX1()+cw),(int)(getY1()+ch),colors[1],true,period);
      g2d.setPaint(p);
      g2d.draw(this);
   }

   public void paintLabel(Graphics g){
      l.paint(g);
   }

   void setLabelPosition(){
      l.setPosition(new PointD(labelx,labely));
   }

   void setCtrlPositions(){
      double[] t = new double[4];
      double angle = Math.atan2(y1-y2,x1-x2);

      if(isCircular){
         t[0] = x1 - circle;
         t[1] = y1 + circle;
         t[2] = x1 + circle;
         t[3] = y1 + circle;
      } else {
         at.setToIdentity();
         at.rotate(angle);
         if(!isStraight)
            at.translate(0,shift);
         at.rotate(-angle);
         at.transform(new double[]{x1,y1,x2,y2},0,t,0,2);
      }
      ctrlx1 = t[0];
      ctrly1 = t[1];
      ctrlx2 = t[2];
      ctrly2 = t[3];

      if(isCircular){
         labelx = x1;
         labely = y1+50;
         return;
      }

      //move is how far away from the center's line the control's line is moved
      double move;
      if(type == StageStructuredPane.kASPG){
         move = 100;
      } else {
         move = 50;
      }

      if(isStraight) move /= 2;

      double midx = (x1 - x2)/2.0 + x2;
      double midy = (y1 - y2)/2.0 + y2;
      at.setToIdentity();
      at.rotate(angle);
      at.translate(0,move);
      at.rotate(-angle);
      at.transform(new double[]{midx,midy},0,t,0,1);

      labelx = t[0];
      labely = t[1];
   }

   public void setStart(PointD p){
      this.x1 = p.x;
      this.y1 = p.y;
      setCtrlPositions();
      setLabelPosition();
   }

   public void setStop(PointD p){
      this.x2 = p.x;
      this.y2 = p.y;
      setCtrlPositions();
      setLabelPosition();
   }

   public void setStraight(boolean b){
      isStraight = b;
      setCtrlPositions();
   }

   public void setShift(Point p){
      //this doesn't work, but i'm not sure that i want a drag to change the shift...
      /*
      double angle = Math.atan2(y1-y2,x1-x2);
      //double x = p.x - x1;
      double midy = (y1 - y2)/2.0 + y2;
      double y = p.y - midy;
      shift = (int)y;
      setCtrlPositions();
      */
   }

   public void setActive(boolean b){
      isActive = b;
   }

   public Stage[] getConnectionStages(){
      return connection;
   }

   public int[] getConnectionIndicies(){
      return new int[] {connection[0].getIndex(),connection[1].getIndex()};
   }

   public Label getLabel(){
      return l;
   }

   public String getName(boolean b){
      return ""+connection[0].getShiftedIndex()+" to "+connection[1].getShiftedIndex();
   }

   public void setName(String s){

   }

   /**
    * the transitions between the different stages are divided into classes
    * @return
    */
   public String getNamePrefix(){
      int[] c = getConnectionIndicies();
      String letter;
      //c[0] is source or column, c[1] is destination or row
      if(c[0] == c[1]){
         letter = "s";
      } else if(c[1]-1 == c[0]){
         letter = "p";
      } else if(c[1] == 1){
         letter = "f";
      } else if(c[1]+1 == c[0]){
         letter = "r";
      } else if(c[1] > c[0]){
         letter = "j";
      } else if(c[0] > c[1]){
         letter = "v";
      } else {
         letter = "";
         edu.umn.ecology.populus.fileio.Logging.log("no letter assignable to transition...");
      }

      return "<i>"+letter+"</i> = ";
   }

   public void setNamePrefix(String s){

   }

   public double getX1() {
      return x1;
   }

   public double getY1() {
      return y1;
   }

   public Point2D getP1() {
      return new Point2D.Double(x1, y1);
   }

   public double getCtrlX1() {
      return ctrlx1;
   }

   public double getCtrlY1() {
      return ctrly1;
   }

   public Point2D getCtrlP1() {
      return new Point2D.Double(ctrlx1, ctrly1);
   }

   public double getCtrlX2() {
      return ctrlx2;
   }

   public double getCtrlY2() {
      return ctrly2;
   }

   public Point2D getCtrlP2() {
      return new Point2D.Double(ctrlx2, ctrly2);
   }

   public double getX2() {
      return x2;
   }

   public double getY2() {
      return y2;
   }

   public Point2D getP2() {
      return new Point2D.Double(x2, y2);
   }

   public void setCurve(double x1, double y1,
                        double ctrlx1, double ctrly1,
                        double ctrlx2, double ctrly2,
                        double x2, double y2) {
      this.x1     = x1;
      this.y1     = y1;
      this.ctrlx1 = ctrlx1;
      this.ctrly1 = ctrly1;
      this.ctrlx2 = ctrlx2;
      this.ctrly2 = ctrly2;
      this.x2     = x2;
      this.y2     = y2;
   }

   public Rectangle2D getBounds2D() {
      double left   = Math.min(Math.min(x1, x2),
                               Math.min(ctrlx1, ctrlx2));
      double top    = Math.min(Math.min(y1, y2),
                               Math.min(ctrly1, ctrly2));
      double right  = Math.max(Math.max(x1, x2),
                               Math.max(ctrlx1, ctrlx2));
      double bottom = Math.max(Math.max(y1, y2),
                               Math.max(ctrly1, ctrly2));
      return new Rectangle2D.Double(left, top,
                                    right - left, bottom - top);
   }

   public boolean contains(Point2D p){
      if(!isStraight && !isCircular) return super.contains(p);
      if(isCircular){
        double centerX = getX1();
        double centerY = getY1() + circle/3;
        double distance = Math.sqrt(Math.pow((p.getX()-centerX),2)+Math.pow((p.getY()-centerY),2));
        if(distance < (circle/3)) return true;
      }
      double[] t = new double[6];
      double angle = Math.atan2(y1-y2,x1-x2);
      at.setToIdentity();
      at.rotate(-angle);
      at.transform(new double[]{p.getX(),p.getY(),x1,y1,x2,y2},0,t,0,3);
      if(t[0] < t[2] && t[0] > t[4] && Math.abs(t[3]-t[1]) < 7) return true;
      return false;
   }
}








class NullStroke implements Stroke {
    public Shape createStrokedShape(Shape s) { return s; }
}

/**
 * This Stroke implementation applies a BasicStroke to a shape twice.
 * If you draw with this Stroke, then instead of outlining the shape,
 * you're outlining the outline of the shape.
 **/
class DoubleStroke implements Stroke {
    BasicStroke stroke1, stroke2;   // the two strokes to use
    public DoubleStroke(float width1, float width2) {
        stroke1 = new BasicStroke(width1);  // Constructor arguments specify
        stroke2 = new BasicStroke(width2);  // the line widths for the strokes
    }

    public Shape createStrokedShape(Shape s) {
        // Use the first stroke to create an outline of the shape
        Shape outline = stroke1.createStrokedShape(s);
        // Use the second stroke to create an outline of that outline.
        // It is this outline of the outline that will be filled in
        return stroke2.createStrokedShape(outline);
    }
}

/**
 * This Stroke implementation strokes the shape using a thin line, and
 * also displays the end points and Bezier curve control points of all
 * the line and curve segments that make up the shape.  The radius
 * argument to the constructor specifies the size of the control point
 * markers. Note the use of PathIterator to break the shape down into
 * its segments, and of GeneralPath to build up the stroked shape.
 **/
class ControlPointsStroke implements Stroke {
    float radius;  // how big the control point markers should be
    public ControlPointsStroke(float radius) { this.radius = radius; }

    public Shape createStrokedShape(Shape shape) {
        // Start off by stroking the shape with a thin line.  Store the
        // resulting shape in a GeneralPath object so we can add to it.
        GeneralPath strokedShape =
            new GeneralPath(new BasicStroke(1.0f).createStrokedShape(shape));

        // Use a PathIterator object to iterate through each of the line and
        // curve segments of the shape.  For each one, mark the endpoint and
        // control points (if any) by adding a rectangle to the GeneralPath
        float[] coords = new float[6];
        for(PathIterator i=shape.getPathIterator(null); !i.isDone();i.next()) {
            int type = i.currentSegment(coords);
            Shape s = null, s2 = null, s3 = null;
            switch(type) {
            case PathIterator.SEG_CUBICTO:
                markPoint(strokedShape, coords[4], coords[5]); // falls through
                edu.umn.ecology.populus.fileio.Logging.log("cubicto "+coords[4]+", "+coords[5]);
            case PathIterator.SEG_QUADTO:
                markPoint(strokedShape, coords[2], coords[3]); // falls through
            case PathIterator.SEG_MOVETO:
            case PathIterator.SEG_LINETO:
                markPoint(strokedShape, coords[0], coords[1]); // falls through
            case PathIterator.SEG_CLOSE:
                break;
            }
        }

        return strokedShape;
    }

    /** Add a small square centered at (x,y) to the specified path */
    void markPoint(GeneralPath path, float x, float y) {
        path.moveTo(x-radius, y-radius);  // Begin a new sub-path
        path.lineTo(x+radius, y-radius);  // Add a line segment to it
        path.lineTo(x+radius, y+radius);  // Add a second line segment
        path.lineTo(x-radius, y+radius);  // And a third
        path.closePath();                 // Go back to last moveTo position
    }
}

/**
 * This Stroke implementation randomly perturbs the line and curve segments
 * that make up a Shape, and then strokes that perturbed shape.  It uses
 * PathIterator to loop through the Shape and GeneralPath to build up the
 * modified shape.  Finally, it uses a BasicStroke to stroke the modified
 * shape.  The result is a "sloppy" looking shape.
 **/
class SloppyStroke implements Stroke {
    BasicStroke stroke;
    float sloppiness;
    public SloppyStroke(float width, float sloppiness) {
        this.stroke = new BasicStroke(width); // Used to stroke modified shape
        this.sloppiness = sloppiness;         // How sloppy should we be?
    }

    public Shape createStrokedShape(Shape shape) {
        GeneralPath newshape = new GeneralPath();  // Start with an empty shape

        // Iterate through the specified shape, perturb its coordinates, and
        // use them to build up the new shape.
        float[] coords = new float[6];
        for(PathIterator i=shape.getPathIterator(null); !i.isDone();i.next()) {
            int type = i.currentSegment(coords);
            switch(type) {
            case PathIterator.SEG_MOVETO:
                perturb(coords, 2);
                newshape.moveTo(coords[0], coords[1]);
                break;
            case PathIterator.SEG_LINETO:
                perturb(coords, 2);
                newshape.lineTo(coords[0], coords[1]);
                break;
            case PathIterator.SEG_QUADTO:
                perturb(coords, 4);
                newshape.quadTo(coords[0], coords[1], coords[2], coords[3]);
                break;
            case PathIterator.SEG_CUBICTO:
                perturb(coords, 6);
                newshape.curveTo(coords[0], coords[1], coords[2], coords[3],
                                 coords[4], coords[5]);
                break;
            case PathIterator.SEG_CLOSE:
                newshape.closePath();
                break;
            }
        }

        // Finally, stroke the perturbed shape and return the result
        return stroke.createStrokedShape(newshape);
    }

    // Randomly modify the specified number of coordinates, by an amount
    // specified by the sloppiness field.
    void perturb(float[] coords, int numCoords) {
        for(int i = 0; i < numCoords; i++)
            coords[i] += (float)((Math.random()*2-1.0)*sloppiness);
    }

}

