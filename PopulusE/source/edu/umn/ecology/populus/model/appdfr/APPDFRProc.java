/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdfr;
import edu.umn.ecology.populus.math.*;

public class APPDFRProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
   private double ap, lambda, c, t, th, b;
   private int type; //Type 1-3
   
   public void v( long time, double[] y ) {
      double f, N, P;
      N = y[Ny];
      P = y[Py];
      switch( type ) {
         case 1:
             f = Math.exp( -ap * t * P );
             break;
         
         case 2:
             f = 1 + ap * th * N;
             if( f != 0 ) {
                f = Math.exp( -ap * t * P / f );
             }
             else {
                f = Double.POSITIVE_INFINITY;
             }
             break;
         
         case 3:
             f = 1 + c * N + b * th * N * N;
             if( f != 0 ) {
                f = Math.exp( -b * t * N * P / f );
             }
             else {
                f = Double.POSITIVE_INFINITY;
             }
             break;
         
         default:
             
             //ERROR!
             f = 0;
      }
      y[Ny] = lambda * N * f;
      y[Py] = c * N * ( 1 - f );
   }
   
   public APPDFRProc( int type, double lambda, double ap, double c, double t, double th, double b ) {
      this.type = type;
      this.lambda = lambda;
      this.ap = ap;
      this.c = c;
      this.t = t;
      this.th = th;
      this.b = b;
      this.numVariables = 2;
   }
}
