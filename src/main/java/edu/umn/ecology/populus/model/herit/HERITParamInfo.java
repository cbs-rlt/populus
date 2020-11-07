/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.herit;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.Routines;
import edu.umn.ecology.populus.model.ie.TextOutput;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.awt.*;
import java.util.Random;

public class HERITParamInfo implements BasicPlot {
    final boolean isTheoretical;
    final double g1, g2, g3, p, ve;
    final int popSize;
    double max;
    final double scale = 1.0 / 6.0;
    final Random rand;

    BasicPlotInfo getTheoreticalData() {
        double a = (g1 - g3) / 2.0;
        double d = a - g1 + g2;
        double q = 1.0 - p;
        double alpha = a + d * (q - p);
        double va = 2.0 * p * q * alpha * alpha;
        double vd = (2.0 * p * d * d) * (2.0 * p * d * d);
        double ave = p * p * g1 + 2.0 * p * q * g2 + q * q * g3;
        double h2;
        String extra = "";

        double[][][] points = new double[3][2][2];
        if (va + vd + ve != 0.0) {
            h2 = va / (va + vd + ve);
            points[0] = Routines.buildLinearRegression(new double[]{ave, ave + 1}, new double[]{ave, ave + h2}, 0.0, max);
            extra = ", <i>h</i><sup>2</> = " + TextOutput.NumToStr(h2, 5, 5, false) + " ";
        } else {
            h2 = 0.0;
            points[0] = Routines.buildLinearRegression(new double[]{ave, ave}, new double[]{ave, ave + 1}, 0.0, max);
        }

        points[1] = Routines.buildGaussianCurve(ave, ve, max * scale, max);
        points[2][0] = points[1][1];
        points[2][1] = points[1][0];
        return new BasicPlotInfo(points, "Theoretical Heritability" + extra, " Parental Phenotype ", " Offspring Phenotype ");
    }

    BasicPlotInfo getMonteCarloData() {
        double[][][] points = new double[1][2][popSize];
        int[] geno1, geno2;
        double[] pPheno = new double[popSize];
        double[] oPheno = new double[popSize];
        double[] mPheno = new double[popSize];
        double mPhenoAvg = 0, oPhenoAvg = 0;
        int parent1, parent2;
        int geno1t, geno2t;

        geno1 = Routines.getFrequencyArray(popSize, p, rand);
        geno2 = Routines.getFrequencyArray(popSize, p, rand);

		for(int i=0; i<popSize; i++){
			switch(geno1[i] + geno2[i]){
			case 0: pPheno[i] = Routines.nextGaussian(g3, ve, rand); break;
			case 1: pPheno[i] = Routines.nextGaussian(g2, ve, rand); break;
			case 2: pPheno[i] = Routines.nextGaussian(g1, ve, rand); break;
			}
		}

        for (int i = 0; i < popSize; i++) {
            do {
                parent1 = rand.nextInt(popSize);
                parent2 = rand.nextInt(popSize);
            } while (parent1 == parent2);

            if (rand.nextInt(2) == 0) {
                geno1t = geno1[parent1];
                geno2t = geno2[parent2];
            } else {
                geno1t = geno2[parent1];
                geno2t = geno1[parent2];
            }

			switch(geno1t + geno2t){
			case 0: oPheno[i] = Routines.nextGaussian(g3, ve, rand); break;
			case 1: oPheno[i] = Routines.nextGaussian(g2, ve, rand); break;
			case 2: oPheno[i] = Routines.nextGaussian(g1, ve, rand); break;
			}

            mPheno[i] = (pPheno[parent1] + pPheno[parent2]) / 2.0;
            mPhenoAvg += mPheno[i];
            oPhenoAvg += oPheno[i];
            points[0][0][i] = mPheno[i];
            points[0][1][i] = oPheno[i];
        }

        /*these averages need to be put in the main caption (or axis captions?)*/
        mPhenoAvg /= popSize;
        oPhenoAvg /= popSize;
        String extra = ", <i>F\u0305</i><sub>0</sub> = " + TextOutput.NumToStr(mPhenoAvg, 5, 5, false) + ", <i>F\u0305</i><sub>1</sub> = " + TextOutput.NumToStr(oPhenoAvg, 5, 5, false);

        /*the dots*/
        BasicPlotInfo bpi = new BasicPlotInfo(points, "Monte Carlo Heritability" + extra, "Mid-Parent Phenotype", " Offspring Phenotype ");
        bpi.setLineStyle(BasicPlotInfo.NO_LINE);
        bpi.setSymbolStyle(BasicPlotInfo.DOTS);
        bpi.setColors(new Color[]{ColorScheme.colors[7]});

        /*the linear regression of the dots*/
        bpi.addData(Routines.buildLinearRegression(mPheno, oPheno, Math.min(Routines.getMinValue(mPheno) - 0.5 * ve, 0.0), 0.5 * ve + Routines.getMaxValue(mPheno)));
        bpi.setLineColor(bpi.getNumSeries() - 1, ColorScheme.colors[0]);

        /*the distribution on the x-axis*/
        bpi.addData(Routines.buildDistributionCurve(mPheno, max * scale * Math.sqrt(2.0 * Math.PI * ve)));
        bpi.setLineColor(bpi.getNumSeries() - 1, ColorScheme.colors[1]);

        /*the distribution on the y-axis*/
        double[][] ydist = Routines.buildDistributionCurve(oPheno, max * scale * Math.sqrt(2.0 * Math.PI * ve));
        double[] temp = ydist[0].clone();
        ydist[0] = ydist[1];
        ydist[1] = temp;
        bpi.addData(ydist);
        bpi.setLineColor(bpi.getNumSeries() - 1, ColorScheme.colors[2]);

        return bpi;
    }


    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        if (isTheoretical)
            return getTheoreticalData();
        else
            return getMonteCarloData();
    }

    public HERITParamInfo(double g1, double g2, double g3, double p, double ve, int popSize,
                          boolean isTheoretical, long randSeed) {
        this.isTheoretical = isTheoretical;
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
        this.p = p;
        this.ve = ve;
        this.popSize = popSize;
        rand = new Random(randSeed);
        max = Math.max(g1, g2);
        max = Math.max(max, g3);
        max += ve;
    }
}
