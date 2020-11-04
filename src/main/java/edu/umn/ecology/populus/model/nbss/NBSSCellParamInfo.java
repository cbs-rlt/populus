/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.plot.ParamInfo;
import edu.umn.ecology.populus.plot.coloredcells.CellFunction;
import edu.umn.ecology.populus.plot.coloredcells.CellPalette;

import java.util.Random;

class NBSSCellParamInfo extends ParamInfo implements CellFunction {
    /**
     *
     */
    private static final long serialVersionUID = 8413398310438128499L;
    public Random myRand = new Random(System.currentTimeMillis());
    NBSSProc v;
    int s, type, border, runInterval, currentGeneration = 0;
    double[][][] oldData, popTotals, values;
    int[] init = new int[2];
    double mun, mup;
    boolean intpops, diseach, se;

    String[] strings = {"0", "5", "10", "20", "40", "80", "160", "320", "640", "1280"};

    NBSSCellParamInfo(int n0, int p0, double l, double a, double mun, double mup, double q,
                      int size, int border, int type, boolean intpops, boolean diseach,
                      boolean startedge, int runi) {
        v = new NBSSProc(a, l, q);
        this.mun = mun;
        this.mup = mup;
        this.border = border;
        this.type = type;
        this.intpops = intpops;
        this.diseach = diseach;
        this.runInterval = runi;
        this.s = size;
        init[0] = n0;
        init[1] = p0;
        se = startedge;
        initialize();
    }

    void initialize() {
        values = new double[2][s][s];
        int half = s / 2;
        if (se) {
            values[0][0][0] = init[0];
            values[1][0][0] = init[1];
        } else {
            values[0][half][half] = init[0];
            values[1][half][half] = init[1];
        }
        oldData = values.clone();
    }

    @Override
    public double[][] initialF() {
        updateTotals();
        return sendValues(oldData);
    }

    @Override
    public double[][] f() {
        increment();
        currentGeneration++;
        updateTotals();
        return sendValues(oldData);
    }

    void increment() {
        if (values[0].length != s) {
            //Logging.log("ta created ");
            values = new double[2][s][s];
        }
        double[] t;
        double fP, fN;
        final int h1 = values[0].length;
        final int h2 = values[0][0].length;

        for (int i = 0; i < h1; i++) {
            for (int j = 0; j < h2; j++) {
                t = new double[]{oldData[0][i][j], oldData[1][i][j]};
                v.v(0, t);
                if (!intpops) {
                    values[0][i][j] = t[0];
                    values[1][i][j] = t[1];
                } else {
                    values[0][i][j] = (int) t[0];
                    values[1][i][j] = (int) t[1];
                }
            }
        }
        oldData = cloneArray(values);
        for (int i = 0; i < h1; i++) {
            for (int j = 0; j < h2; j++) {
                fN = mun * oldData[0][i][j];
                fP = mup * oldData[1][i][j];
                if (fN < 1e-10 && fP < 1e-10)
                    continue;
                fN /= 8;
                fP /= 8;

                if (intpops) {
                    fN = (int) fN;
                    fP = (int) fP;
                }

                for (int k = i - 1; k <= i + 1; k++) {
                    for (int l = j - 1; l <= j + 1; l++) {
                        if (l == j && k == i)
                            continue;
                        int tK = k, tL = l;
                        switch (border) {
                            case NBSSPanel.kAbsorbing -> {
                                if (l < 0 || k < 0 || l >= h1 || k >= h2) break;
                                values[0][tK][tL] += fN;
                                values[1][tK][tL] += fP;
                            }
                            case NBSSPanel.kPeriodic -> {
                                if (l < 0) tL = h1 - 1;
                                if (l >= h1) tL = 0;
                                if (k < 0) tK = h2 - 1;
                                if (k >= h2) tK = 0;
                                values[0][tK][tL] += fN;
                                values[1][tK][tL] += fP;
                            }
                            case NBSSPanel.kReflexive -> {
                                if (l < 0) tL = 0;
                                if (l >= h1) tL = h1 - 1;
                                if (k < 0) tK = 0;
                                if (k >= h2) tK = h2 - 1;
                                values[0][tK][tL] += fN;
                                values[1][tK][tL] += fP;
                            }
                        }
                    }
                }
            }
        }
        oldData = cloneArray(values);
    }

    double[][][] cloneArray(double[][][] d) {
        double[][][] r = new double[d.length][d[0].length][d[0][0].length];
        for (int i = 0; i < r.length; i++)
            for (int j = 0; j < r[0].length; j++)
                for (int k = 0; k < r[0][0].length; k++)
                    r[i][j][k] = d[i][j][k];
        return r;
    }

    double[][] sendValues(double[][][] v) {
        if (type == NBSSPanel.kN)
            return v[0];
        if (type == NBSSPanel.kP)
            return v[1];
        double[][] r = new double[v[0].length][v[0][0].length];
        for (int i = 0; i < r.length; i++)
            for (int j = 0; j < r[0].length; j++) {
                if (v[0][i][j] == 0) {
                    r[i][j] = 0;
                    continue;
                } else if (v[1][i][j] == 0) {
                    r[i][j] = Double.MAX_VALUE;
                    continue;
                }
                r[i][j] = v[0][i][j] / v[1][i][j];
            }
        return r;
    }

    void updateTotals() {
        if (popTotals == null) popTotals = new double[2][2][currentGeneration + 1];
        if (popTotals[0][0].length >= currentGeneration) {
            double[][][] points = new double[2][2][popTotals[0][0].length + 50];
            for (int i = 0; i < popTotals.length; i++)
                for (int j = 0; j < popTotals[0].length; j++)
                    for (int k = 0; k < popTotals[0][0].length; k++)
                        points[i][j][k] = popTotals[i][j][k];
            popTotals = points;
        }

        double[] sums = new double[2];
        for (int k = 0; k < 2; k++)
            for (int i = 0; i < oldData[0].length; i++)
                for (int j = 0; j < oldData[0][0].length; j++)
                    sums[k] += oldData[k][i][j];

        popTotals[0][0][currentGeneration] = currentGeneration;
        popTotals[1][0][currentGeneration] = currentGeneration;
        popTotals[0][1][currentGeneration] = sums[0];
        popTotals[1][1][currentGeneration] = sums[1];
    }

    public double[][][] getTotals(boolean isAverage) {
        double[][][] v = new double[2][2][currentGeneration + 1];
        final int numSquares = oldData[0].length * oldData[0][0].length;

        for (int i = 0; i < popTotals.length; i++)
            for (int j = 0; j < popTotals[0].length; j++)
                for (int k = 0; k < v[0][0].length; k++) {
                    v[i][j][k] = popTotals[i][j][k];
                    if (isAverage)
                        v[i][1][k] /= numSquares;
                }
        //if the generation of the last data point is zero, then that means the
        //the generations got out of sync.
        if (v[0][0][v[0][0].length - 1] == 0) {
            double[][][] temp = new double[2][2][v[0][0].length - 1];
            for (int i = 0; i < temp.length; i++)
                for (int j = 0; j < temp[0].length; j++)
                    for (int k = 0; k < temp[0][0].length; k++)
                        temp[i][j][k] = v[i][j][k];
            v = temp;
        }
        return v;
    }

    @Override
    public double[][] changeType(String s) {
        String[] a = getOutputTypes();
        if (s.equalsIgnoreCase(a[0])) {
            type = NBSSPanel.kN;
        } else if (s.equalsIgnoreCase(a[1])) {
            type = NBSSPanel.kP;
        } else type = NBSSPanel.kNdivP;
        return sendValues(oldData);
    }

    @Override
    public int getDimension() {
        return s;
    }

    @Override
    public String[] getStrings() {
        return strings;
    }

    @Override
    public double[] getDemarcations() {
        return new double[]{5d, 10d, 20d, 40d, 80d, 160d, 320d, 640d, 1280d};
    }

    @Override
    public int getBreakInterval() {
        if (diseach) return 1;
        return runInterval;
    }

    @Override
    public void setValue(int species, int r, int c, double newValue) {
        if (species <= 1)
            oldData[species][r][c] = newValue;
    }

    @Override
    public String[] getOutputTypes() {
        return new String[]{"N", "P", "N/P"};
    }

    @Override
    public String getCurrentType() {
        String[] a = getOutputTypes();
        if (type == NBSSPanel.kN) return a[0];
        if (type == NBSSPanel.kP) return a[1];
        if (type == NBSSPanel.kNdivP) return a[2];
        return null;
    }

    protected int getType() {
        return type;
    }

    @Override
    public CellPalette getColorPalette() {
        return new NBSSColorPalette();
    }

    @Override
    public int getGeneration() {
        return currentGeneration;
    }
}

