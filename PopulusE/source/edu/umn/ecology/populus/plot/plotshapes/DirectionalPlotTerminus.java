package edu.umn.ecology.populus.plot.plotshapes;

import java.awt.geom.AffineTransform;


/**
 * Directional means that it needs to know the direction of the line, such as an arrow.
 *
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

abstract public class DirectionalPlotTerminus extends PlotTerminus {
	private double dy, dx;

	public DirectionalPlotTerminus() {
	}
	public DirectionalPlotTerminus(boolean isStart) {
		super(isStart);
	}

	/**
	 *  So we know the angle from the last two points
	 */
	public void setDeltas(double dy, double dx) {
		this.dy = dy;
		this.dx = dx;
	}

	/**
	 * @param size
	 * @return an array of points in the form (x0,y0, x1,y1, x2,y2, ...) for the shape
	 *         assuming a given size, and directed to the right.
	 */
	protected abstract float[] getArray(int size);

	/**
	 *  From JCShape, recompute x and y arrays based on the new size and direction
	 */
	@Override
	protected void resize( int size ) {
		AffineTransform at = new AffineTransform();
		at.rotate( getAngle() );
		float[] pc;

		pc = getArray(size);

		x = new int[pc.length/2];
		y = new int[pc.length/2];
		float[] pf = new float[pc.length];
		at.transform( pc, 0, pf, 0, pc.length / 2 );
		for( int j = 0;j < x.length;j++ ) {
			x[j] = (int)pf[2 * j];
			y[j] = (int)pf[2 * j + 1];
		}
	}

	/**
	 * i thought for certain i'd played with Math.atan2 before writing this... but this seems to work
	 * so why change it now?
	 * @return
	 */
	protected double getAngle(){
		double angle;
		if( dx != 0 ) {
			angle = Math.atan( adjustment*dy/dx );
		} else {
			angle = 3.0*Math.PI / 2.0;
		}
		if( dx > 0 ) {
			if( dy < 0 )
				angle += 2.0 * Math.PI;
		} else {
			angle += Math.PI;
		}
		angle *= -1.0;
		return angle;
	}
}