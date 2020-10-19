/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import edu.umn.ecology.populus.math.*;

public class TLDeriv extends Derivative {
	private final double r, k, theta, c, h, d, s;
	private final int type;

	@Override
	public void doDerivative( double t, double[] N, double[] dN ) {
		dN[0] = r * N[0] * ( 1 - Math.pow( ( N[0] / k ), theta ) ) - getfn( N[0] ) * N[1];
		dN[1] = s * N[1] * ( getfn( N[0] ) - d );
		return ;
	}

	public TLDeriv( int type, double r, double k, double theta, double c, double h, double d, double s, double n, double p ) {
		this.type = type;
		this.r = r;
		this.k = k;
		this.theta = theta;
		this.c = c;
		this.h = h;
		this.d = d;
		this.s = s;
		this.numVariables = 2;
	}

	protected double getfn( double N ) {
		switch( type ) {
		case 1:
			return c * N;

		case 2:
			return ( c * N ) / ( 1 + h * c * N );

		case 3:
			return ( c * N * N ) / ( 1 + h * c * N * N );

		default:
			System.err.println( "Invalid type " + type + " in TLDeriv" );
			return 0.0;
		}
	}
}
