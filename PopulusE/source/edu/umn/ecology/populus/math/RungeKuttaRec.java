package edu.umn.ecology.populus.math;

//Title:        Populus
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Lars Roe
//Company:      University of Minnesota
//Description:  Populus

import edu.umn.ecology.populus.constants.RungeKuttaDefaults;

/**
  * RungeKutta Record <BR>
  * Describes the way to do the integration
  *
  * it isn't really documented which ones here aren't implemented. check the
  * options you want to use to be sure they are implemented
  */

public class RungeKuttaRec {
   /** when this is set to true, then the integration has finished and no more
       steps will be taken */
   public boolean done = false;

   /** set this to true if you plan to make use of the postDerivative method
       that is in the derivative class. this is useful if there are some calculations
       that need to happen inbetween steps.*/
   public boolean usePostDerivative = false;

   public double ssMinDur; //min time SS test must hold  ??????????

   //public int eq;
   //private boolean[] eqSet;
   //public double[] c = new double[3];
   //public double[] param;

   //not implemented?
   public int theOneAbove;

   //////////////////////////////////////////
   //VARIABLES TO FINE-TUNE THE INTEGRATION//
   //////////////////////////////////////////

   /** If true, negative values will be set to zero */
   public boolean nonNegOnly;

   //what is serialStep? not implemented...
   public double serialStep;

   /** %err/100 that stepsize h can have (see Numerical Recipes p609) */
   public double eps; //"eps"ilon

   /** not sure what scale is supposed to do for me */
   public double[] scale;

   /////////////////////////////////////
   //STOPPING CONDITIONS AND VARIABLES//
   /////////////////////////////////////
   public boolean ss, thr, interval; //how it should stop

   /** Precision used in SS (steady-state) */
   public double ssErr;

   /* should we check if values are above or below the
      max values? Java can handle big and small numbers,
      but it is likely that some numbers aren't physical...

      should we have another control that would set a calculation
      to zero if it is too small?*/
   public double min = 1e-15;
   /**stop if all values reach this*/
   public boolean minExit = false;
   public double max = 1e15;
   /**stop if one value exceeds this*/
   public boolean maxExit = false;

   /** Precision used in threshold */
   public double thrErr;
   public int maxiter; //automatically stop after max iterations
   public double tf; //final time (independent) value if stopped at thr or ss.

   //public double hnext; //predicted next h is simply stored in h
   public double h;//this is the integration step size
   public double hlast;

   /////////////////////////////
   //TYPE OF INTEGRATION TO DO//
   /////////////////////////////

   //Runge-Kutta 4th order with Quality Control will be the default for Populus
   public int mode;

   public static final int EULER       = 0;
   public static final int MIDPOINT    = 1;
   public static final int RK4         = 2;
   public static final int RK4QC       = 3;
   public static final int DISCRETE    = 4;


   /**
     * Imitating Chris' DefaultRKRec
     *
     * the setting of some of these variables is now allowed by the
     * end user through the Integrator Preferences
     */
   public RungeKuttaRec( int numVariables ) {
      int i;
      this.ss = false;
      this.interval = true;
      this.minExit = true;
      this.thr = false;
      this.thrErr = 2.0;

      /* these are the defaults, but not set here
      this.eps = 1e-4;
      this.ssErr = 1e-5;
      this.maxiter = 500;
      this.ssMinDur = 10.0;
      */
      this.eps = RungeKuttaDefaults.dEPS;
      this.ssErr = RungeKuttaDefaults.dSSERR;
      this.maxiter = RungeKuttaDefaults.dMaxiter;
      this.ssMinDur = RungeKuttaDefaults.dSSMinDur;
      this.h = RungeKuttaDefaults.h;
      this.mode = RungeKuttaDefaults.mode;
      this.hlast = h;
      if(numVariables < 1)
         System.err.println("numVariables not set");
      this.scale = new double[numVariables];
      for( i = 0;i < numVariables;i++ ) {
         this.scale[i] = 1.0;
      }
   }

   /**
     * Imitating Chris' SetUpRKRec
     * what's up with 2 letter variable names and then no comments? hmm...
     */

   public RungeKuttaRec( double tf, double sw, double sh, boolean SS, double sserr, double ssDur, double stoptime ) {
      int i;
      if( this.mode == this.RK4QC ) {
         this.scale[0] = sw / 1000.0;
         this.scale[1] = sh / 1000.0;

         //this.eps = 1e-6;
         this.eps = RungeKuttaDefaults.dEPS;
         sw = Math.max( sw, sh );

         /*
         if (sw != 0)
         for (i = 2; i < this.eq; i++)
         this.scale[i] = sw;
         */
         for( i = 0;i <= 1;i++ ) {
            if( this.scale[i] == 0 ) {
               scale[i] = 1;
            }
         }
         this.ss = SS;

         //this.param = param;
         this.h = 0.2;

         //this.setUserFlags = false
         if( this.ss ) {
            this.ssErr = sserr;
            this.ssMinDur = ssDur;
            tf = 1e4;
         }
         else {
            tf = stoptime;
         }
      }
   }
}
