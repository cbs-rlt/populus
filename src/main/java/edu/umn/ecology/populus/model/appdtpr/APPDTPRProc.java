/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdtpr;

import edu.umn.ecology.populus.math.DiscreteProc;

public class APPDTPRProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
    private double K, r, a, c, b;

    @Override
    public void v(long t, double[] y) {
        double P, N, aP_, temp;
        N = y[Ny];
        P = y[Py];
        aP_ = a * P;
        if (K != 0) {
            temp = r * (1 - N / K) - aP_;
            if (temp < 87.0) {
                y[Ny] = N * Math.exp(temp);
                if (y[Ny] < 0) {
                    y[Ny] = 0;
                }
            } else {
                y[Ny] = Double.POSITIVE_INFINITY;
            }
        } else {
            y[Ny] = 0;
        }
        if (-aP_ < 87.0) {
            y[Py] = c * (N * (1 - Math.exp(-aP_)) - b * P);
            if (y[Py] < 0) {
                y[Py] = 0;
            }
        } else {
            y[Py] = 0;
        }
    }

    public APPDTPRProc(double K, double r, double a, double c, double b) {
        this.K = K;
        this.a = a;
        this.r = r;
        this.c = c;
        this.b = b;
        this.numVariables = 2;
    }
} /* pascal source for Threshold Predator Reproduction
 procedure V(var Pm:ParamArray;t:longint;var y_); far;
 var
 K,r,a,c,b,P,N,
 aP_,temp      : extended;
 y             : extendedArray absolute y_;
 begin
 K := Pm[Kp];
 r := Pm[rp];
 a := Pm[ap];
 c := Pm[cp];
 b := Pm[bp];

 N := y[Ny];
 P := y[Py];

 aP_:= a*P;

 if K<>0 then
 begin
 temp := r*(1-N/K)-aP_;
 if temp<ln(MaxSingle) then
 begin
 y[Ny] := N*exp(temp);
 if y[Ny]<0 then y[Ny]:=0;
 end
 else
 y[Ny] := MaxSingle;
 end
 else
 y[Ny] := 0;

 if -aP_<ln(MaxSingle) then
 begin
 y[Py] := c*(N*(1-exp(-aP_))-b*P);
 if y[Py]<0 then y[Py]:=0;
 end
 else
 y[Py] := 0;
 end;
 */

