/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.pp;
import edu.umn.ecology.populus.math.*;

public class PPProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
   private double[] l, aX, g, a;
   private double m, s;
   private boolean switching;
   final private double[] kaX;
   
   public void v( long t, double[] y ) {
      final short Xy = 0, Yy = 1, Py = 2;
      double[] X =  {
         y[Xy], y[Yy]
      };
      double P = y[Py], P1m, E;
      this.aX = (double[])kaX.clone();
      if( P > 0 ) {
         P1m = Math.log( P ) * ( 1 - m );
         if( P1m < 87.0 ) {
            P1m = Math.exp( P1m );
         }
         else {
            P1m = Double.POSITIVE_INFINITY;
         }
      }
      else {
         P1m = 0;
      }
      if( switching ) {
         if( X[0] + X[1] != 0 ) {
            E = s * ( X[0] - X[1] ) / ( X[0] + X[1] );
         }
         else {
            E = 0;
         }
         aX[0] *= ( 1 + E );
         aX[1] *= ( 1 - E );
      }
      aX[0] *= P1m;
      aX[1] *= P1m;
      y[Xy] = -g[0] * ( X[0] + a[0] * X[1] ) - aX[0];
      if( y[Xy] > -1e5 ) {
         y[Xy] = l[0] * X[0] * Math.exp( y[Xy] );
      }
      else {
         y[Xy] = 0;
      }
      y[Yy] = -g[1] * ( X[1] + a[1] * X[0] ) - aX[1];
      if( y[Yy] > -1e5 ) {
         y[Yy] = l[1] * X[1] * Math.exp( y[Yy] );
      }
      else {
         y[Yy] = 0;
      }
      if( -aX[0] > -1e5 ) {
         aX[0] = Math.exp( -aX[0] );
      }
      else {
         aX[0] = 0;
      }
      if( -aX[1] > -1e5 ) {
         aX[1] = Math.exp( -aX[1] );
      }
      else {
         aX[1] = 0;
      }
      y[Py] = X[0] * ( 1 - aX[0] ) + X[1] * ( 1 - aX[1] );
   }
   
   public PPProc( boolean switching, double[] l, double[] aX, double[] g, double[] a, double m, double s ) {
      this.l = l;
      this.kaX = aX;
      this.g = g;
      this.a = a;
      this.m = m;
      this.s = s;
      this.switching = switching;
      this.numVariables = 3;
   }
} /* pascal code for "Polyphagous Predators"
 procedure V(var Pm:ParamArray;t:longint;var y_); far;
 var
 X,g,l,aX,a  : array[1..2] of extended;
 P,P1m,m,s,E : extended;
 y           : extendedArray absolute y_;
 begin
 l[1] := Pm[lp];  {lambda}
 l[2] := Pm[lpp]; {lambda prime}
 aX[1]:= Pm[aXp]; {aX}
 aX[2]:= Pm[aYp]; {aY}
 g[1] := Pm[gp];  {g}
 g[2] := Pm[gpp]; {g prime}
 a[1] := Pm[ap];
 a[2] := Pm[bp];
 m    := Pm[mp];
 X[1] := y[Xy];   {X}
 X[2] := y[Yy];   {Y}
 P    := y[Py];
 
 if P>0 then
 begin
 P1m := ln(P)*(1-m);
 if P1m<ln(MaxSingle) then
 P1m := exp(P1m)
 else
 P1m := MaxSingle;
 end
 else
 P1m := 0;
 
 case TheModel of
 Switching : begin
 s := Pm[sp];
 
 if X[1]+X[2]<>0 then
 E := s*(X[1]-X[2])/(X[1]+X[2])
 else
 E := 0;
 
 aX[1] := aX[1]*(1+E);
 aX[2] := aX[2]*(1-E);
 end;
 end;
 
 aX[1] := aX[1]*P1m;
 aX[2] := aX[2]*P1m;
 
 
 y[Xy] := -g[1]*(X[1]+a[1]*X[2])-aX[1];
 if y[Xy]>-1e5 then y[Xy] := l[1]*X[1]*exp(y[Xy])
 else               y[Xy] := 0;
 
 y[Yy] := -g[2]*(X[2]+a[2]*X[1])-aX[2];
 if y[Yy]>-1e5 then y[Yy] := l[2]*X[2]*exp(y[Yy])
 else               y[Yy] := 0;
 
 if -aX[1]>-1e5 then aX[1] := exp(-aX[1])
 else                aX[1] := 0;
 
 if -aX[2]>-1e5 then aX[2] := exp(-aX[2])
 else                aX[2] := 0;
 
 y[Py] := X[1]*(1-aX[1])+X[2]*(1-aX[2]);
 
 (*
 y[Xy] := l[1]*X[1]*exp(-g[1]*(X[1]+a[1]*X[2])-aX[1]);
 y[Yy] := l[2]*X[2]*exp(-g[2]*(X[2]+a[2]*X[1])-aX[2]);
 y[Py] := X[1]*(1-exp(-aX[1])) + X[2]*(1-exp(-aX[2]));
 *)
 end;
 */
 
