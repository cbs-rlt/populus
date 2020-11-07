/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ihpi;

import edu.umn.ecology.populus.math.DiscreteProc;

public class IHPIProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
    private final double F_;
    private final double I;
    private final double a;
    private final double k;
    private final double theta;
    private final double Th;
    private final double Ip_;
    private final int type; //Type 1-4

    @Override
    public void v(long time, double[] y) {
        double N, P, den, f;
        N = y[Ny];
        P = y[Py];
        switch (type) {
            case 1 -> {
                den = k * (1 + a * N * I * theta); //assume that 'Theta' is the same as 'theta'
                if (den != 0) {
                    f = func(1 + a * P / den);
                } else {
                    f = 0;
                }
                y[Ny] = F_ * N * I * f;
                y[Py] = N * I * (1 - f);
            }
            case 2, 3 -> {
                den = k * (1 + a * N * Th);
                if (den != 0) {
                    f = func(1 + a * P / den);
                } else {
                    f = 0;
                }
                y[Ny] = F_ * N * I * f;
                if (type == 2) {
                    y[Py] = N * (1 - f);
                } else {
                    y[Py] = N * (1 - f) * I;
                }
            }
            case 4 -> {
                den = k * (1 + a * N * I);
                if (den != 0) {
                    f = func(1 + a * P * Ip_ / den);
                } else {
                    f = 0;
                }
                y[Ny] = F_ * N * I * f;
                y[Py] = N * I * (1 - f);
            }
        }
    }

    public IHPIProc(int type, double F_, double I, double a, double k, double theta, double Th, double Ip_) {
        this.type = type;
        this.F_ = F_;
        this.I = I;
        this.a = a;
        this.k = k;
        this.theta = theta;
        this.Th = Th;
        this.Ip_ = Ip_;
        this.numVariables = 2;
    }

    private double func(double temp) {
        if (temp > 0) {
            temp = Math.log(temp) * (-k);
            if (temp < 500) {
                return Math.exp(temp);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
} /*pascal code for Insecticides in Host-Parasitoid Interactions
 procedure V(var Pm:ParamArray;t:longint;var y_); far;
 var
 temp,f,
 P,N,F_,I,a,den,
 k,Theta,Th,Ip_ : extended;
 y              : extendedArray absolute y_;

 function func(temp:extended):extended;
 begin
 if temp>0 then
 begin
 temp := ln(temp)*(-k);
 if temp<500 then
 func := exp(temp)
 else
 func := 0;
 end
 else
 func := 0;
 end;

 begin
 N := y[Ny];
 P := y[Py];

 F_:= Pm[Fp];
 I := Pm[Ip];
 a := Pm[ap];
 k := Pm[kp];

 case TheModel of
 Model1 : begin
 Theta := Pm[Thetap];

 den := k*(1+a*N*I*theta);
 if den<>0 then
 f := func(1+a*P/den)
 else
 f := 0;

 y[Ny] := F_*N*I*f;
 y[Py] := N*I*(1-f);
 end;
 Model2,
 Model3 : begin
 Th := Pm[Thp];

 den := k*(1+a*N*Th);
 if den<>0 then
 f := func(1+a*P/den)
 else
 f := 0;

 y[Ny] := F_*N*I*f;
 if TheModel=Model2 then
 y[Py] := N*(1-f)
 else
 y[Py] := N*(1-f)*I;
 end;
 Model4 : begin
 Ip_:= Pm[Ipp];

 den := k*(1+a*N*I);
 if den<>0 then
 f := func(1+a*P*Ip_/den)
 else
 f := 0;

 y[Ny] := F_*N*I*f;
 y[Py] := N*I*(1-f);
 end;
 end;
 end;
 */

