package edu.umn.ecology.populus.plot.plotshapes;

import java.awt.Graphics;


/**
 * Circle that goes at the beginning or end of a line.
 *
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class CircleTerminus extends PlotTerminus {

	public CircleTerminus(boolean isStart) {
		super(isStart);
	}
	public CircleTerminus() {
		this(false);
	}

	@Override
	public boolean isOpaque() {
		return !this.isStart();
	}

	@Override
	protected void resize(int size) {
		this.size = size;
		x = new int[72];
		y = new int[72];
		int index = 0;
		double temp;
		for( double i = 0;i < 360;i += 5 ) {
			temp = Math.cos( degToRad( i ) );
			temp *= size;
			x[index] = (int)temp;
			temp = Math.sin( degToRad( i ) );
			temp *= size;
			y[index] = (int)temp;
			index++;
		}
	}

	double degToRad( double degrees ) {
		return Math.PI / 180 * degrees;
	}


	@Override
	public void draw(Graphics gc,
			int xorg,
			int yorg) {
		int xi, yi, d;
		xi = xorg - size;
		yi = yorg - size;
		d = size * 2;
		if (this.isStart())
			gc.drawOval(xi, yi, d, d);
		else
			gc.fillOval(xi, yi, d, d);
	}

}

