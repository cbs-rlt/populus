
//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1998

//Author:       Lars Roe

//Company:      University of Minnesota

//Description:  Populus
package edu.umn.ecology.populus.math;

public abstract class Derivative {
   protected int numVariables = -1;

   /**
     *  x is the x-coord (usually x=time) <br>
     *  y is the y-coords, <br>
     *  and dydx is to be modified by putting dy/dx into it
     */

   public abstract void doDerivative( double x, double[] y, double[] dydx );

   /**
      the use of this method is controled by usePostDerivative in RungeKuttaRec
      the default is false, so by default all that is needed in Derivative classes is a place
      holder that will never get called.
      a double array is returned so that the length of v can be modified.
   */
   public abstract double[] postDerivative( double[] v, double t);

   /********************/

   /* ACCESSOR METHODS */

   /********************/

   public int getNumVariables() {
      return numVariables;
   }

   public void reset(){

   }

   public Derivative() {

   }
}
