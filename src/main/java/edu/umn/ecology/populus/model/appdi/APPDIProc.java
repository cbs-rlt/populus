/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdi;

import edu.umn.ecology.populus.math.DiscreteProc;

public class APPDIProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
    private double l, a, b, c, T_, Tw, m, Q;
    private boolean linear;

    @Override
    public void v(long t, double[] y) {
        double P, N, f;
        N = y[Ny];
        P = y[Py];
        if (linear) {
            c = 1;
            if (P > 0) {
                f = -Q * Math.exp(Math.log(P) * (1 - m));
                if (f < 87.0) {
                    f = Math.exp(f);
                } else {
                    f = Double.POSITIVE_INFINITY;
                }
            } else {
                f = 0;
            }
        } else {
            f = 1 + b * Tw * (P - 1);
            if (f != 0) {
                f = -a * T_ * P / f;
                if (f < 87.0) {
                    f = Math.exp(f);
                } else {
                    f = Double.POSITIVE_INFINITY;
                }
            } else {
                f = 0;
            }
        }
        y[Ny] = l * N * f;
        y[Py] = c * N * (1 - f);
    }

    public APPDIProc(boolean linear, double l, double a, double b, double c, double T_, double Tw, double m, double Q) {
        this.linear = linear;
        this.l = l;
        this.a = a;
        this.b = b;
        this.c = c;
        this.T_ = T_;
        this.Tw = Tw;
        this.m = m;
        this.Q = Q;
        this.numVariables = 2;
    }
} /* Old Pascal Code for APPDI
 procedure V(var Pm:ParamArray;t:longint;var y_); far;
 var
 l,P,N,f,
 a,b,c,T_,Tw,
 m,Q         : extended;
 y           : extendedArray absolute y_;
 begin
 l := Pm[lp];

 N := y[Ny];
 P := y[Py];

 case TheModel of
 Linear      : begin
 c := 1;
 m := Pm[mp];
 Q := Pm[Qp];

 if P>0 then
 begin
 f := -Q*exp(ln(P)*(1-m));
 if f<ln(MaxSingle) then
 f := exp(f)
 else
 f := MaxSingle;
 end
 else
 f := 0;
 end;
 Curvilinear : begin
 a := Pm[ap];
 b := Pm[bp];
 c := Pm[cp];
 T_:= Pm[Tp];
 Tw:= Pm[Twp];

 f := 1+b*Tw*(P-1);
 if f<>0 then
 begin
 f := -a*T_*P/f;
 if f<ln(MaxSingle) then
 f := exp(f)
 else
 f := MaxSingle;
 end
 else
 f := 0;
 end;
 end;

 y[Ny] := l*N*f;
 y[Py] := c*N*(1-f);
 end;
 */

