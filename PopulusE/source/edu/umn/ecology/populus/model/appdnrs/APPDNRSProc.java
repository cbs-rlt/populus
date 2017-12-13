/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnrs;
import edu.umn.ecology.populus.math.*;

public class APPDNRSProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
	private double l, a, k, K_;
	private boolean indep;

	@Override
	public void v( long t, double[] y ) {
		double g, P, N, q;
		N = y[Ny];
		P = y[Py];
		if( k != 0 ) {
			q = 1 + a * P / k;
			if( q > 0 ) {
				q = Math.log( q ) * -k; //assuming log is the same as ln (i think it is..)
				if( q <= 87.0 ) { //ln(MaxSingle) ~= 87.0? so MaxSingle ~= 6E37?
					q = Math.exp( q );
				}
				else {
					q = Double.POSITIVE_INFINITY;
				}
			}
			else {
				q = 0;
			}
		}
		else {
			q = 1;
		}
		if( !indep ) {
			if( ( K_ != 0 ) && ( l > 0 ) ) {
				g = Math.log( l ) / K_;
			}
			else {
				g = 0;
			}
			y[Ny] = Math.exp( -g * N );
		}
		else {
			y[Ny] = 1;
		}
		y[Ny] = l * N * y[Ny] * q;
		y[Py] = N * ( 1 - q );
	}

	public APPDNRSProc( boolean indep, double l, double a, double k, double K_ ) {
		this.indep = indep;
		this.l = l;
		this.a = a;
		this.k = k;
		this.K_ = K_;
		this.numVariables = 2;
	}

	/* Pascal Code for NRS
procedure V(var Pm:ParamArray;t:longint;var y_); far;
var
a,l,k,K_,g,P,N,q : extended;
y                : extendedArray absolute y_;
begin
l := Pm[lp];
a := Pm[ap];
k := Pm[kp];
K_:= Pm[Kp_];

N := y[Ny];
P := y[Py];

if k<>0 then
begin
q := 1+a*P/k;
if q>0 then
begin
q := ln(q)*-k;
if q<=ln(MaxSingle) then
q := exp(q)
else
q := MaxSingle
end
else
q := 0;
end
else
q := 1;


if TheModel=DDGrowth then
begin
if (K_<>0) and (l>0) then
g := ln(l)/K_
else
g := 0;

y[Ny] := exp(-g*N);
end
else
y[Ny] := 1;

y[Ny] := l*N*y[Ny]*q;
y[Py] := N*(1-q);
end;

	 */

	/* nb v()
if (indep) {
q = -aDI * P;
if (q < 500)
q = Math.exp(q);
else
q = 0;
y[Ny] = l*N*q;
y[Py] = N*(1-q);
} else {
q1 = -aDD*P;
if (K != 0)
q2 = r*(1-N/K)+q1;
else
q2 = 0;

if (q2 < 87.0) //87.0 < ln (MAX_FLOAT)
y[Ny] = N*Math.exp(q2);
else
y[Ny] = 1e30;

if (q1 < 87.0)
y[Py] = N*(1-Math.exp(q1));
else
y[Py] = 0;
}
	 */
}
