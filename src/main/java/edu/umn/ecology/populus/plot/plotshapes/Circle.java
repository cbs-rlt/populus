package edu.umn.ecology.populus.plot.plotshapes;

/**
 * this is used for a better discrete plot -- the dots are bigger
 * than jcchart usually does
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class Circle extends PlotTerminus {

	@Override
	protected void resize( int size ) {
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
}
