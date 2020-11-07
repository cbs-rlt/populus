/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.math;

import edu.umn.ecology.populus.constants.RungeKuttaDefaults;

/**
 * This is used to all of the math stuff for Populus. Most is taken from Chris's old code, as well as
 * 'Numerical Recipes' chapter 15. I would possibly consider using native methods if it is needed. <BR>
 * Call:
 * <UL><LI>integrate(double x1, double y1) to integrate.
 * <LI>RKQC and RK4 - single step functions
 * <BR> Should we make methods final?
 */

public class Integrator {

    /**
     * record
     */
    public RungeKuttaRec record;
    public static final int MAX_STORED_STEPS = 200;
    public static final double TINY = 1E-30;
    public static final int MAX_STEPS = 10000;
    public static final int MAX_VARIABLES = 150;

    /**
     * Used to find derivative (like derivative, but discrete)
     */
    DiscreteProc discProc = null;
    double[][] yout = null;

    /**
     * Temporary array for holding y values. Used in multiple steps.
     */
    double[] yt;

    //For efficiency, these (dym,dyt,yt) are global. However, they belong in doRK4.

    /**
     * Temporary array for dy at the middle
     */
    double[] dym;

    /**
     * Temporary array for dy. Used in multiple steps
     */
    double[] dyt;

    /**
     * Used to find derivative
     */
    Derivative derivative = null;
    double[] xout = null;

    /**
     * Number of variables (<code>y</code>s) to integrate. This does not include x.
     * It can also be retrieved through either record or discProc, but it is used too
     * often in this program, and needs speedy access.
     */
    int numVariables;

    //These 5 variables are used in doRKQC
    //Numerical Recipies doesn't say much about what these are
    final double pgrow = -0.20;
    final double pshrnk = -0.25;
    final double safety = 0.9;
    final double errcon = 5.76650390618e-4; //6.0e-4;
    final double fcor = 1.0 / 15.0;

    /**
     * Integrator constructor. Uses the default RungeKuttaRec and all the default
     * settings (some set in RungeKuttaDefaults by end-user)
     */
    public Integrator(Derivative der) {
        this(der, new RungeKuttaRec(der.getNumVariables()));
    }

    /**
     * Integrator constructor
     */
    public Integrator(Derivative der, RungeKuttaRec rkr) {
        super();
        this.derivative = der;
        numVariables = der.getNumVariables();
        record = rkr;
        dym = new double[numVariables];
        init();
    }

    /**
     * Integrator constructor for discrete processes. Uses the default RungeKuttaRec
     * and all the default settings (some set in RungeKuttaDefaults by end-user)
     */
    public Integrator(DiscreteProc dp) {
        super();
        this.discProc = dp;
        numVariables = dp.getNumVariables();
        record = new RungeKuttaRec(numVariables);
        record.mode = RungeKuttaRec.DISCRETE;
        record.h = record.hlast = 1.0; //that is what discrete means...
        init();
    }

    public Derivative getDerivative() {
        return derivative;
    }

    public double[][] getY() {
        return this.yout;
    }

    public double[] getX() {
        return this.xout;
    }

    /**
     * Main function call for integration. It will generally
     * call integrateRK4, but it may also run a discrete process
     * depending on the RungeKuttaRec.
     */
    public void integrate(double[] vstart, double x1, double x2) {
        doIntegration(vstart, x1, x2);
    }

    /**
     * Integrates with initial values vstart, going from x1 to x2.
     * This is intended to be called from outside - unlike doRKQC or doRK4,
     * which are strictly used within this package only.
     */
    public void doIntegration(double[] v, double x1, double x2) {
        int k, maxVars = 0;
        double t;
        double[] xtemp = new double[record.maxiter + 2];
        //ytemp has an unspecified 2nd length because i want the number of
        //variables to be able to change from step to step
        double[][] ytemp = new double[record.maxiter + 2][];
        double[] yss = new double[numVariables];//y steady-state
        double xss;//x steady-state

        //Set the finishing time so that other methods can see it
        record.tf = x2;

        //When this becomes true, the integration stops. It can be set in other methods!
        record.done = false;
        ytemp[0] = new double[numVariables];
        for (int i = 0; i < numVariables; i++) {
            ytemp[0][i] = v[i];
            yss[i] = v[i];
        }
        xtemp[0] = x1;
        t = x1;
        xss = 0.0;

		/*
      switch (record.mode) {
         case RungeKuttaRec.EULER: edu.umn.ecology.populus.fileio.Logging.log("\nEuler Method"); break;
         case RungeKuttaRec.MIDPOINT: edu.umn.ecology.populus.fileio.Logging.log("\nMidpoint Method"); break;
         case RungeKuttaRec.RK4: edu.umn.ecology.populus.fileio.Logging.log("\nRK4 Method"); break;
         case RungeKuttaRec.RK4QC: edu.umn.ecology.populus.fileio.Logging.log("\nRK4QC Method"); break;
         case RungeKuttaRec.DISCRETE: edu.umn.ecology.populus.fileio.Logging.log("\nDiscrete"); break;
      }
		 */
        intLabel:
        for (k = 0; k < record.maxiter; k++) {
            switch (record.mode) {
                case RungeKuttaRec.EULER -> ytemp[k + 1] = doEulerStep(v, t);
                case RungeKuttaRec.MIDPOINT -> ytemp[k + 1] = doMidpointStep(v, t);
                case RungeKuttaRec.RK4 -> ytemp[k + 1] = doRK4Step(v, t);
                case RungeKuttaRec.RK4QC -> ytemp[k + 1] = doRKQCStep(v, t);
                case RungeKuttaRec.DISCRETE -> {
                    discProc.v((long) (t + 0.5), v);
                    ytemp[k + 1] = v;
                }
            }
            for (double value : v)
                if (Double.isInfinite(value)) {
                    k--;
                    break intLabel;
                }

            t += record.hlast;
            xtemp[k + 1] = t;

            //check flags
            {
                double n, nss, tmp;
                n = nss = 0.0;
                boolean notMin = false;

                for (int i = 0; i < numVariables; i++) {
                    if (record.nonNegOnly && ytemp[k + 1][i] < 0.0d)
                        ytemp[k + 1][i] = 0.0d;

                    if (record.maxExit && ytemp[k + 1][i] > record.max)
                        record.done = true;
                    if (record.minExit && ytemp[k + 1][i] > record.min)
                        notMin = true;

                    tmp = (ytemp[k + 1][i] - ytemp[k][i]) / record.scale[i];
                    n += tmp * tmp;
                    tmp = (ytemp[k + 1][i] - yss[i]) / record.scale[i];
                    nss += tmp * tmp;
                }
                if (!notMin)
                    record.done = true;

                if (record.ss && (Math.abs(xss - t) >= record.ssMinDur)) {
                    if (nss > record.ssErr) {
                        xss = t;
                        for (int i = 0; i < numVariables; i++) {
                            yss[i] = ytemp[k + 1][i];
                        }
                        //System.arraycopy(ytemp, k+1, yss, 0, numVariables);
                    } else {
                        record.done = true;
                    }
                }
                if (record.thr && (n > record.thrErr)) {
                    record.done = true;
                }
            }

            //Break if done
            if ((record.mode != RungeKuttaRec.RK4QC) &&
                    !record.ss &&
                    (t + record.h > record.tf))
                record.done = true;

            if (record.done)
                break intLabel;

            if (record.usePostDerivative) {
                double[] temp = ytemp[k + 1].clone();
                ytemp[k + 1] = derivative.postDerivative(ytemp[k + 1], t);
                if (ytemp[k + 1] == null) {
                    //k--;
                    ytemp[k + 1] = temp;
                    break intLabel;
                }
                if (numVariables != derivative.getNumVariables()) {
                    numVariables = derivative.getNumVariables();
                    if (numVariables != ytemp[k + 1].length) {
                        System.err.println("Error: You didn't change the array!");
                        break intLabel;
                    }
                    changeNumVariables();
                    double[] yssNew = new double[numVariables];
                    System.arraycopy(yss, 0, yssNew, 0, yss.length);
                    yss = yssNew;
                }
            }
            v = ytemp[k + 1].clone();
        }

        if (k == record.maxiter) {
            //System.err.println("Max number of integration iterations ("+record.maxiter+") reached.");
            k--;
        }

        for (int i = 0; i < ytemp[k + 1].length; i++) {
            if (Double.isNaN(ytemp[k + 1][i]) || Double.isInfinite(ytemp[k + 1][i])) {
                k--;
                break;
            }
        }

        for (int i = 0; i < k + 2; i++)
            maxVars = Math.max(maxVars, ytemp[i].length);

        yout = new double[maxVars][k + 2];
        xout = new double[k + 2];
        System.arraycopy(xtemp, 0, xout, 0, k + 2);
        for (int i = 0; i < maxVars; i++)
            for (int j = 0; j < yout[0].length; j++)
                if (ytemp[j].length > i) {
                    yout[i][j] = ytemp[j][i];
                } else {
                    yout[i][j] = 0.0d;
                }
    }

    /**
     * Performs fifth-order Runge-Kutta with quality control step<BR>
     * I basically copied from numerical recipes page 611, with Java <br>
     * It will attempt a step size of record.h, but adjusting the step size
     * accordingly in case it has too much error or exceeds the stopping point.
     * For "output," record.hlast will store the h value actually used. record.h
     * will have the next suggested step size.
     * <p>
     * y = initial variable values (at x), and also an output value after step. <br>
     * dydx = derivative of y's at x
     */
    double[] doRKQCStep(double[] y, double x) {
        int i;
        double xInitial, hh, h, temp, errmax;
        double[] yInitial, ytemp;

        /** Original y values */
        yInitial = new double[numVariables];

        /** Temporary array for y values */
        ytemp = new double[numVariables];

        //Ensure integration time is large enough
        if (record.tf < 1.0) {
            record.tf = 1.0;
        }
        xInitial = x;
        System.arraycopy(y, 0, yInitial, 0, numVariables);
        h = record.h;
        while (true) {

            //If it is about to go past the end, stop it right at the end.
            if (record.interval) {
                if ((record.tf >= xInitial) ? (xInitial + h >= record.tf) : (xInitial + h <= record.tf)) {
                    h = record.tf - xInitial;
                    record.done = true;
                }
            }
            hh = h / 2.0;
            record.h = hh;
            ytemp = doRK4Step(yInitial, xInitial);
            x = xInitial + hh;
            y = doRK4Step(ytemp, x);
            x = xInitial + h;

            //Make sure step size isn't too small to the point that x is not
            //increasing. There is still the possibility that y is only increasing
            //by (effectively) 0.0
            if (x == xInitial) {

                //edu.umn.ecology.populus.fileio.Logging.log("Stepsize too small! Adjusting...");
                xInitial = (x <= record.tf) ? 1.0e-4 : -1.0e-4;
                x = xInitial + h;
                while (x == xInitial) {
                    h *= 1.2;
                    x = xInitial + h;
                }
            }

            //Do the full-sized step.
            record.h = h;
            ytemp = doRK4Step(yInitial, xInitial);

            //Now y is the result of 2 half steps, and ytemp is the result of one full step
            errmax = 0.0;
            for (i = 0; i < numVariables; i++) {
                ytemp[i] = y[i] - ytemp[i]; //ytemp now has error estimate
                temp = Math.abs(ytemp[i] / ((y[i] != 0.0) ? y[i] : 1e-6)); //record.scale[i]);
                if (errmax < temp) {
                    errmax = temp;
                }
            }
            errmax /= record.eps;
            if (errmax <= 1.0) {

                //Successfully made reasonable step
                record.hlast = h;
                if (errmax > errcon) {

                    //Make next step sized according to amount of error
                    //if h is unreasonably too small, then reset it to something more reasonable, and hope
                    //the glitch is passed
                    if (record.h > 1e-10)
                        record.h = safety * h * Math.pow(errmax, pgrow);
                    else
                        record.h = RungeKuttaDefaults.h;

                } else {

                    //Make next step larger (current error is very small)
                    record.h = 4.0 * h;
                }
                break;
            } else {

                //FAILURE: Error is too high. Reset step size and try again.
                h *= safety * Math.pow(errmax, pshrnk);
            }
        }

        //Get rid of as much fifth order error as possible
        for (i = 0; i < numVariables; i++) {
            ytemp[i] = y[i] + ytemp[i] * fcor;
        }

        return ytemp;
    }

    /**
     * Performs fourth-order Runge-Kutta step<BR>
     * I basically copied from Numerical Recipes (Pascal) page 605, with Java
     * y = initial variable values (at x)
     * dydx = derivative of y's at x (passed in to avoid a redundant calculation)
     * x = starting x value
     * yout = final y values
     * record.h should have stepsize
     * <p>
     * The Runge-Kutta fourth order formula is:
     * k1 = h*f( x0, y0 )
     * k2 = h*f( x0 + 0.5*h, y0 + 0.5*k1 )
     * k3 = h*f( x0 + 0.5*h, y0 + 0.5*k2 )
     * k4 = h*f( x0 + h, y0 + k3)
     * y1 = y0 + (1/6)*k1 + (1/3)*k2 + (1/3)*k3 + (1/6)*k4 + O(h^5)
     */
    double[] doRK4Step(double[] y, double x) {
        int i;
        final double xh, hh, h6;
        final double h = this.record.h;
        double[] dydx = new double[numVariables];
        double[] yout = new double[numVariables];

        //Half h
        hh = h * 0.5;

        //One-sixth of h
        h6 = h / 6.0;

        //midpoint between starting x and final-step x
        xh = x + hh;

        derivative.doDerivative(x, y, dydx);

        //FIRST STEP
        for (i = 0; i < numVariables; i++) {
            // k1 = y0 + 0.5*h*f(x,y)
            yt[i] = y[i] + hh * dydx[i];
        }

        //SECOND STEP
        derivative.doDerivative(xh, yt, dyt);
        for (i = 0; i < numVariables; i++) {
            // k2 = y0 + 0.5*h*f(x+0.5*h,k1)
            yt[i] = y[i] + hh * dyt[i];
        }

        //THIRD STEP
        derivative.doDerivative(xh, yt, dym);
        for (i = 0; i < numVariables; i++) {
            // k3 = y0 + h*f(x+0.5*h,k2)
            yt[i] = y[i] + h * dym[i];
            dym[i] = dyt[i] + dym[i];
        }

        //FOURTH STEP
        derivative.doDerivative(x + h, yt, dyt);
        for (i = 0; i < numVariables; i++) {
            yout[i] = y[i] + h6 * (dydx[i] + dyt[i] + 2.0 * dym[i]);
        }

		/*
      edu.umn.ecology.populus.fileio.Logging.log("x= " + x);
      for (i = 0; i < numVariables; i++)
      edu.umn.ecology.populus.fileio.Logging.log(yout[i]);
		 */
        return yout;
    }

    /**
     * Preconditions:
     * x and y specify the initial conditions
     * dydx are the derivatives at the initial conditions
     * Postconditions:
     * yout is to hold the approximated y values at x + h
     * <p>
     * This method uses the Euler formula:
     * y1 = y0 + h*f( x0, y0 )
     */
    double[] doEulerStep(double[] y, double x) {
        final double h = this.record.h;
        double[] dydx = new double[numVariables];
        double[] yout = new double[numVariables];

        derivative.doDerivative(x, y, dydx);

        for (int i = 0; i < numVariables; i++) {
            yout[i] = y[i] + h * dydx[i];
        }

        return yout;
    }

    /**
     * Preconditions:
     * x and y specify the initial conditions
     * dydx are the derivatives at the initial conditions
     * Postconditions:
     * yout is to hold the approximated y values at x + h
     * <p>
     * This method uses the Midpoint formula:
     * k1 = h*f( x0, y0 )
     * k2 = h*f( x0 + 0.5*h, y0 + 0.5*k1 )
     * y1 = y0 + k2 + O(h^3)
     */
    double[] doMidpointStep(double[] y, double x) {
        final double h = this.record.h;
        double[] dydx = new double[numVariables];
        double[] yout = new double[numVariables];

        derivative.doDerivative(x, y, dydx);

        for (int i = 0; i < numVariables; i++) {
            yt[i] = y[i] + 0.5 * h * dydx[i];
        }

        derivative.doDerivative(x + 0.5 * h, yt, dym);//dym = k2
        for (int i = 0; i < numVariables; i++) {
            yout[i] = y[i] + dym[i];
        }

        return yout;
    }

    public void reset() {
        record = new RungeKuttaRec(numVariables);
        init();
    }

    private void init() {
        dym = new double[numVariables];
        dyt = new double[numVariables];
        yt = new double[numVariables];
    }

    private void changeNumVariables() {
        dym = new double[numVariables];
        dyt = new double[numVariables];
        yt = new double[numVariables];
        record.scale = new double[numVariables];
        for (int i = 0; i < numVariables; i++) {
            record.scale[i] = 1.0;
        }
    }
}
