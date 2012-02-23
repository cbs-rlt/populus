package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.math.*;

public class NBSSProc extends DiscreteProc
				implements edu.umn.ecology.populus.model.appd.Constants {
	private double a, l, q;


	public NBSSProc(double a, double l, double q) {
		this.a = a;
		this.l = l;
		this.q = q;
		this.numVariables = 2;
	}
	
	public void v(long t, double[] y) {
		double P, N, f;

		N = y[Ny];
		P = y[Py];

		f = -a * P;
		if (f < 500)
			f = Math.exp(f);
		else
			f = 0;
		y[Ny] = l*N*f;
		y[Py] = q*N*(1-f);
	}
}