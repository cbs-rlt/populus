/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aidst;
import edu.umn.ecology.populus.math.*;

public class AIDSTDeriv extends Derivative {
   public static final int kX = 0;
   public static final int kY = 1;
   //public static final int kV = 2;
   public static final int kW = 2;
   public static final int kZ = 3;
   private double lambda,d,beta,a,c,q,h,b,s, p;
   private double[][] intervals;
   private double s_new;


   public void doDerivative( double t, double[] N, double[] dN ) {
      double x = N[kX];
      double y = N[kY];
      //double v = N[kV];
      double w = N[kW];
      double z = N[kZ];
      //s_new is 1 outside of an interval, and s within an interval
      s_new = 1;
      for (int cnt = 0; cnt < intervals.length; cnt++)
         if ((intervals[cnt][0] <= t) && (t <= intervals[cnt][1]))
            s_new = s;

      dN[kX] = lambda- d*x - s_new*beta*x*y;
      dN[kY] = s_new*beta*x*y-a*y-p*y*z;
      //dN[kV] = k*y-u*v;
      dN[kW] = c*x*y*w - c*q*y*w - b*w;
      dN[kZ] = c*q*y*w - h*z;

   }
   public AIDSTDeriv( double lambda, double d, double a, double beta, double b, double c, double q,
                      double h, double s,double p, double[][] intervals) {
      this.lambda = lambda;
      this.d = d;
      this.beta = beta;
      this.a = a;
      this.b=b;
      this.c=c;
      this.q=q;
      this.h=h;
      this.p=p;
      this.s=s;

      this.intervals = intervals;
      this.numVariables = 4;
   }
}


