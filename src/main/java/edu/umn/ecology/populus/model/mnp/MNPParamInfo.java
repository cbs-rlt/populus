/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.mnp;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

public class MNPParamInfo implements BasicPlot {
    public static final int PvsT = 0;
    public static final int GFvsT = 1;
    public static final int dPvsP = 2;
    public static final int WBARvsP = 3;

    final int gens, plotType;
    final double[] c, freqs;
    final double[][] w;
    double wbar;
    final boolean isDemp;

    double fDempster(double p) {
        double q = 1.0 - p;
        double[] aveW = new double[3];
        double np;

        for (int i = 0; i < w.length; i++)
            for (int j = 0; j < w[i].length; j++)
                aveW[j] += c[i] * w[i][j];

        np = (p * aveW[0] + q * aveW[1]) * p;
        wbar = p * p * aveW[0] + 2.0 * p * q * aveW[1] + q * q * aveW[2];
        if (wbar == 0) return 1;
        else return np / wbar;
    }

    double fLevene(double p) {
        double q = 1.0 - p;
        double wbari, sum = 0;
        wbar = 0;

        for (int i = 0; i < w.length; i++) {
            wbari = p * p * w[i][0] + 2 * p * q * w[i][1] + q * q * w[i][2];

            if (wbari != 0)
                sum += c[i] * p * (p * w[i][0] + q * w[i][1]) / wbari;

            wbar += c[i] * wbari;
        }
        return sum;
    }

    double getNextP(double p) {
        if (isDemp)
            return fDempster(p);
        else
            return fLevene(p);
    }

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        int numPoints = 100;
        double[][][] points = null;
        String mc = "", xc = "", yc = "";
        switch (plotType) {
            case GFvsT, PvsT -> {
                points = new double[(plotType == PvsT) ? freqs.length : 3][2][gens + 1];
                for (int i = 0; i <= gens; i++) {
                    for (int j = 0; j < freqs.length; j++) {
                        points[j][0][i] = i;
                        points[j][1][i] = freqs[j];
                        freqs[j] = getNextP(freqs[j]);
                    }
                }
                xc = "Generations ( <b><i>t</> )";
                yc = "Allelic Frequency (  " + ColorScheme.getColorString(0) + "<i><b>p</> )";
                if (plotType == PvsT)
                    break;
                for (int i = 0; i <= gens; i++) {
                    points[1][0][i] = i;
                    points[2][0][i] = i;
                    points[1][1][i] = 2.0 * points[0][1][i] * (1.0 - points[0][1][i]);
                    points[2][1][i] = (1.0 - points[0][1][i]) * (1.0 - points[0][1][i]);
                }
                xc = "Generations ( <b><i>t</> )";
                yc = "Genotypic Frequencies (  " + ColorScheme.getColorString(0) + "p<sup>2</>, " + ColorScheme.getColorString(1) + "2pq</>, " + ColorScheme.getColorString(2) + "q<sup>2</> )";
            }
            case WBARvsP -> {
                points = new double[1][2][numPoints + 1];
                for (double i = 0; i <= numPoints; i++) {
                    points[0][0][(int) i] = i / numPoints;
                    getNextP(i / numPoints);
                    points[0][1][(int) i] = wbar;
                }
                xc = "Allelic Frequency ( <i><b>p</> )";
                yc = "Mean Fitness ( " + ColorScheme.getColorString(0) + "<b><i>w\u0305</i></b> )";
            }
            case dPvsP -> {
                points = new double[1][2][numPoints];
                for (double i = 0; i < numPoints; i++) {
                    points[0][0][(int) i] = i / numPoints;
                    if (i > 0)
                        points[0][1][(int) i] = getNextP(i / numPoints) - i / numPoints;
                }
                xc = "Allelic Frequency ( <i><b>p</> )";
                yc = ColorScheme.getColorString(0) + "<i><b>\u0394p</> per Generation";
            }
        }
        if (isDemp) {
            mc = "Multiple-Niche Polymorphism: Dempster";
        } else {
            mc = "Multiple-Niche Polymorphism: Levene";
        }
        return new BasicPlotInfo(points, mc, xc, yc);
    }

    public MNPParamInfo(double[][] matrix, double[] freqs, boolean isDemp, int gens, int plotType) {
        this.gens = gens;
        this.plotType = plotType;
        this.freqs = freqs;
        this.isDemp = isDemp;
        w = new double[matrix.length][3];
        c = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (j == 0) c[i] = matrix[i][0];
                else w[i][j - 1] = matrix[i][j];
    }
}
