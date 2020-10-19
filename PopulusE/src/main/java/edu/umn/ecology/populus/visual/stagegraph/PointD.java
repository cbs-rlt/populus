package edu.umn.ecology.populus.visual.stagegraph;

/**
 * a double type implementation of a coordinate point. i guess i could have used
 * a class already written, but the deed is done
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */
public class PointD {
	public double x;
	public double y;

	public PointD(double x, double y){
		this.x = x;
		this.y = y;
	}
	public PointD(double[] p){
		this.x = p[0];
		this.y = p[1];
	}
}
