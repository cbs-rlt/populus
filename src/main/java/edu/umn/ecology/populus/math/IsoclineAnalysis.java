/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.math;

import edu.umn.ecology.populus.model.ie.IEDeriv;

import java.util.Vector;

class IsoPoint {
    double[] points;

    public double[] getPoint() {
        return points;
    }

    public double getPoint(int which) {
        return points[which];
    }

    public void setPoint(int whichLine, double value) {
        points[whichLine] = value;
    }

    IsoPoint(int numVars) {
        points = new double[numVars];
        for (int i = 0; i < numVars; i++) {
            points[i] = 0;
        }
    }
}

public class IsoclineAnalysis {

    /**
     * How accurate is the answer. 1E-15 is the number of decimals
     * a double holds, but the last decimal is always inaccurate,
     * so a setting of 1E-14 is best.
     */
    public static double acceptableError = 1E-14;

    /**
     * How many times should the method iterate to get to the answer to the
     * desired accuracy. With Populus isoclines, it will usually get the answer
     * in 1 or 2, so this setting doesn't really matter.
     */
    public static int maxTries = 30;

    /**
     * How many points will be in the isocline line.
     */
    public static int numPointsToAdd = 50;

    /**
     * How far beyond the data the isocline will extend.
     */
    public static double extendFactor = 1.2;

    /**
     * This method will add isocline lines to the data.
     * Preconditions:
     * the points array has at least one graph in it
     * Postconditions:
     * the points array will now have all the isocline lines added to it.
     * <p>
     * variables passed in:
     * time-this is the array that contains the time values for the same indices in the
     * points array
     * d-this is the Derivative object that the method can use to solve for 0.
     * useSpread-should be set to true, otherwise, it will just contain the "corrected"
     * critical points of the data. if true, it will add numPointsToAdd isocline points
     */

    public static final double[][][] addIsoclines(double[][][] points, double[] time, Derivative d, boolean useSpread) {
        int numEq = points.length;
        int numVars = points[0].length;
        if (useSpread) {
            points = addPointSpread(points);
        } else {
            points = addCriticalPoints(points);
        }
        double[] n = new double[numVars];
        int analyte, dataToModify;
        for (int i = 0; i < numVars * numEq; i++) {
            for (int p = 0; p < points[i][0].length; p++) {
                dataToModify = i % numVars;
                if (dataToModify < 2) {
                    analyte = (dataToModify + 1) % 2;
                } else {
                    analyte = dataToModify;
                }
                for (int j = 0; j < numVars; j++) {
                    n[j] = points[i][j][p];
                }
                double x1 = Routines.getMinValue(points[points.length - 1][dataToModify]);
                double x2 = Routines.getMaxValue(points[points.length - 1][dataToModify]);
                n = findRootBySecant(d, n, 0, analyte, dataToModify, x1, x2);
                for (int j = 0; j < numVars; j++) {
                    points[i][j][p] = n[j];
                }
            }
        }
        return points;
    }

    /**
     * If you happen to desire to have calculated critical points,  a method to add
     * them is provided.
     */

    public static final double[][][] addCriticalPoints(double[][][] points) {
        IsoPoint is;
        int numEqs = points.length;
        int numVars = points[0].length;
        Vector[][] v = new Vector[numEqs][numVars];
        for (int i = 0; i < numEqs; i++) {
            if (points[i][i].length < 2) {
                edu.umn.ecology.populus.fileio.Logging.log("Not enough points to find isocline");
                break;
            }
            for (int l = 0; l < numVars; l++) {
                v[i][l] = new Vector();
                for (int j = 1; j < points[i][l].length - 1; j++) {
                    if ((points[i][l][j] > points[i][l][j - 1] && points[i][l][j] > points[i][l][j + 1]) || (points[i][l][j] < points[i][l][j - 1] && points[i][l][j] < points[i][l][j + 1])) {
                        is = new IsoPoint(numVars);
                        for (int k = 0; k < numVars; k++) {
                            is.setPoint(k, points[i][k][j]);
                        }
                        v[i][l].addElement(is);
                    }
                }
            }
        }

        //invariant:

        //v[0][0] holds all the critical points (x,y,..) from dx/dt for line 1

        //v[0][1] holds all the critical points (x,y,..) from dy/dt for line 1

        //v[1][1] holds all the critical points (x,y,..) from dy/dt for line 2

        //so forth...

        //critical points refer to the data in the array, which is likely to be

        //poor for finding these data.

        //the points in each of these isoclines need to find the line, plane, whichever

        //that intersect all the points
        double[][][] newPoints = new double[numVars * numEqs + numEqs][][];
        double[][] isocline;

        //move the points from the vectors into the arrays
        for (int i = 0; i < numEqs; i++) {
            for (int j = 0; j < numVars; j++) {
                isocline = new double[numVars][v[i][j].size()];
                for (int k = 0; k < v[i][j].size(); k++) {
                    for (int l = 0; l < numVars; l++) {
                        isocline[l][k] = ((IsoPoint) v[i][j].elementAt(k)).getPoint(l);
                    }
                }
                newPoints[i + j] = isocline;
            }
        }

        //move the original data to the array in the last spot

        //so that on the graph, it will get the last color.
        for (int i = numVars * numEqs; i < numVars * numEqs + numEqs; i++) {
            newPoints[i] = points[i - numVars * numEqs];
        }

        //sort the data points in the isocline so the graph doesn't

        //zig-zag back and forth across the graph
        for (int i = 0; i < numVars; i++) {
            Routines.quickSort(newPoints[i], 0, newPoints[i][0].length - 1, i);
        }
        return newPoints;
    }

    /**
     * The core procedure was found in
     * <p>
     * Numerical Recipes in Pascal, The Art of Scientific Computing
     * Press, William H, et al, Press Syndicate of the University of Cambridge c1989
     * <p>
     * on page 282-3
     * <p>
     * This method was modifed for use in Populus.
     * d becomes fx()
     * n is the critical point data.
     * t is the time at that critical point
     * a is the analyte, or which dN to look at
     * cd is the index in n of the data to be modified
     * x1,x2 are the bounds the critical point is assumed to be inside.
     */

    static final double[] findRootBySecant(Derivative d, double[] n, double t, int a, int cd, double x1, double x2) {
        double dx, f, fl, swap, xl, rts;
        double[] dN = new double[n.length];
        int as = a, cds = cd;
        if (d instanceof IEDeriv) {
            int[] list = ((IEDeriv) d).getPlottedList();
            int count = d.getNumVariables();
            dN = new double[count];
            double[] temp = new double[n.length];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = n[i];
            }
            n = new double[count];
            cd = list[cd];
            a = list[a];
            n[cd] = temp[cds];
            n[a] = temp[as];
        }
        n[cd] = x2;
        d.doDerivative(t, n, dN);
        f = dN[a];
        n[cd] = x1;
        d.doDerivative(t, n, dN);
        fl = dN[a];
        if (f == fl) {
            return n;
        }
        if (Math.abs(fl) < Math.abs(f)) { //Pick the bound with the smaller function value as the most recent guess
            rts = x1;
            xl = x2;
            swap = fl;
            fl = f;
            f = swap;
        } else {
            xl = x1;
            rts = x2;
        }
        for (int j = 1; j < maxTries; j++) { //Secant loop
            if ((f - fl) == 0) {
                break;
            }
            dx = (xl - rts) * f / (f - fl); //Increment with respect to the latest value
            xl = rts;
            fl = f;
            rts += dx;
            n[cd] = rts;
            d.doDerivative(t, n, dN);
            f = dN[a];
            if (Math.abs(dx) < acceptableError || f == 0d) {
                break; //Convergence
            }
        }
        if (d instanceof IEDeriv) {
            double[] temp = new double[n.length];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = n[i];
            }
            n = new double[2];
            n[cds] = temp[cd];
            n[as] = temp[a];
        }
        return n;
    }

    static final double[][][] addPointSpread(double[][][] points) {

        //add points across the graph

        //because the number of data points is now known for sure, we could

        //get rid of the entire vector business.
        IsoPoint is;
        int numEqs = points.length;
        int numVars = points[0].length;
        int numPoints3D = (int) (Math.sqrt(numPointsToAdd) * 1.5);
        double maxValue1, maxValue2;
        Vector[][] v = new Vector[numEqs][numVars];
        if (numVars == 2) {
            for (int i = 0; i < numEqs; i++) {
                for (int l = 0; l < numVars; l++) {
                    v[i][l] = new Vector();
                    maxValue1 = Routines.getMaxValue(points[i][(l + 1) % 2]) * extendFactor;
                    for (double j = 0.001; j <= numPointsToAdd; j += 1.0d) {
                        is = new IsoPoint(numVars);
                        is.setPoint((l + 1) % numVars, j * maxValue1 / (numPointsToAdd));
                        v[i][l].addElement(is);
                    }
                }
            }
        } else {
            for (int i = 0; i < numEqs; i++) {
                for (int l = 0; l < 3; l++) {
                    v[i][l] = new Vector();
                    maxValue1 = Routines.getMaxValue(points[i][(l + 1) % 3]) * extendFactor;
                    maxValue2 = Routines.getMaxValue(points[i][(l + 2) % 3]) * extendFactor;
                    for (double j = 0.1; j <= numPoints3D; j += 1.0d) {
                        for (double k = 0.1; k <= numPoints3D; k += 1.0d) {
                            is = new IsoPoint(numVars);
                            is.setPoint((l + 1) % 3, j * maxValue1 / numPoints3D);
                            is.setPoint((l + 2) % 3, k * maxValue2 / numPoints3D);
                            v[i][l].addElement(is);
                        }
                    }
                }
            }
        }
        double[][][] newPoints = new double[numVars * numEqs + numEqs][][];
        double[][] isocline;
        for (int i = 0; i < numEqs; i++) {
            for (int j = 0; j < numVars; j++) {
                isocline = new double[numVars][v[i][j].size()];
                for (int k = 0; k < v[i][j].size(); k++) {
                    for (int l = 0; l < numVars; l++) {
                        isocline[l][k] = ((IsoPoint) v[i][j].elementAt(k)).getPoint(l);
                    }
                }
                newPoints[i + j] = isocline;
            }
        }
        for (int i = numVars * numEqs; i < numVars * numEqs + numEqs; i++) {
            newPoints[i] = points[i - numVars * numEqs];
        }
        return newPoints;
    }
} /* evaluation code stored for possible future use
 static int numTests=0;
 double[] dn = new double[numVars];
 d.doDerivative(time[p],n,dn);
 edu.umn.ecology.populus.fileio.Logging.log(""+p+": "+n[dataToModify]+",\tdN = "+dn[analyte]+"\n");

 long timeTaken = System.currentTimeMillis();
 timeTaken = System.currentTimeMillis()-timeTaken;
 System.out.print("We did about "+numTests+" ("+((float)numTests)/numPoints+" per point) derivatives in "+((double)timeTaken)/1000+" seconds.");
 */

