package edu.umn.ecology.populus.visual.stagegraph;

import java.awt.*;
import java.awt.geom.*;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.visual.HTMLFreeLabel;
import java.io.*;

/**
 * this class is for the labels used with the stages and the transitions
 * it may not be necessary to separate it all out like this, but oh well.
 *
 * some of the code was pulled directly from Rectangle2D or somewhere like that
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */
public class Label extends Rectangle2D implements StageShape, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6111331350917097525L;
	Color c = Color.black;
	Color ac = Color.green;
	public double x;
	public double y;
	public double width;
	public double height;
	public double w1,w2;
	double v = 0;
	StageShape owner;
	boolean isActive = false;

	public Label(StageShape owner, double x, double y, double value) {
		setRect(x, y, 10, 10);
		this.v = value;
		this.owner = owner;
	}

	/**
	 * This method takes into account the width of the string when drawing it.
	 * Basically, it attempts to center the text around the x and y values it was
	 * given.
	 * @param g
	 */
	@Override
	public void paint(Graphics g){
		double shift;
		String name = owner.getName(true);
		String value = getNamePrefix()+NumberMath.roundSig(v,4,NumberMath.NORMAL);
		g.setFont(new Font("Dialog",Font.BOLD,(int)(15*StageStructuredPane.scale)));
		if(isActive)
			g.setColor(ac);
		else
			g.setColor(c);
		int h = g.getFontMetrics().getHeight();
		if(name != null){
			height = 2*h + 5;
			width = Math.max(w1,w2);
			w1 = g.getFontMetrics().stringWidth(name);
			w2 = g.getFontMetrics().stringWidth(HTMLFreeLabel.getPlainText(value,false));
			g.drawString(name,(int)(getX()-w1/2.0),(int)(getY()));
			shift = w1 == 0? h/4.0 : h;
			HTMLFreeLabel.paintHTML(g,value,(int)(getX()-w2/2.0),(int)(getY() + shift ));
		} else {
			height = h;
			width = g.getFontMetrics().stringWidth(value);
			g.drawString(value,(int)(getX()-width/2),(int)(getY()+4));
		}
		//g.drawOval((int)x,(int)y,2,2);
		//Rectangle2D r = getBounds2D();
		//g.drawRect((int)r.getX(),(int)r.getY(),(int)r.getWidth(),(int)r.getHeight());
	}

	@Override
	public void setActive(boolean b){
		isActive = b;
	}

	public StageShape getOwner(){
		return owner;
	}

	@Override
	public Label getLabel(){
		return this;
	}

	@Override
	public String getName(boolean b){
		return owner.getName(b);
	}
	@Override
	public void setName(String s){
		owner.setName(s);
	}

	@Override
	public String getNamePrefix(){
		return owner.getNamePrefix();
	}

	@Override
	public void setNamePrefix(String s){
		owner.setNamePrefix(s);
	}

	public void setPosition(PointD p){
		this.x = p.x;
		this.y = p.y;
	}

	public PointD getPosition(){
		return new PointD(x,y);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
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
		return (width <= 0.0) || (height <= 0.0);
	}

	@Override
	public void setRect(double x, double y, double w, double h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	@Override
	public void setRect(Rectangle2D r) {
		this.x = r.getX();
		this.y = r.getY();
		this.width = r.getWidth();
		this.height = r.getHeight();
	}

	@Override
	public int outcode(double x, double y) {
		int out = 0;
		if (this.width <= 0) {
			out |= OUT_LEFT | OUT_RIGHT;
		} else if (x < this.x) {
			out |= OUT_LEFT;
		} else if (x > this.x + this.width) {
			out |= OUT_RIGHT;
		}
		if (this.height <= 0) {
			out |= OUT_TOP | OUT_BOTTOM;
		} else if (y < this.y) {
			out |= OUT_TOP;
		} else if (y > this.y + this.height) {
			out |= OUT_BOTTOM;
		}
		return out;
	}

	@Override
	public Rectangle2D getBounds2D() {
		return new Double(x-width/2, y-height/2, width, height);
	}

	@Override
	public boolean contains(double x, double y) {
		Rectangle2D r = getBounds2D();
		double x0 = r.getX();
		double y0 = r.getY();
		return (x >= x0 &&
				y >= y0 &&
				x < x0 + r.getWidth() &&
				y < y0 + r.getHeight());
	}

	@Override
	public Rectangle2D createIntersection(Rectangle2D r) {
		Rectangle2D dest = new Rectangle2D.Double();
		Rectangle2D.intersect(this, r, dest);
		return dest;
	}

	@Override
	public Rectangle2D createUnion(Rectangle2D r) {
		Rectangle2D dest = new Rectangle2D.Double();
		Rectangle2D.union(this, r, dest);
		return dest;
	}

}


