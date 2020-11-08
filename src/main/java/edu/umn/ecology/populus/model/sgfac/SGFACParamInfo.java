/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sgfac;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.util.Arrays;

public class SGFACParamInfo implements BasicPlot {
    public static final int GRAD = 0;
    public static final int HETA = 1;
    public static final int LOCH = 2;
    public static final int FREQ = 3;

    private final double s, g, h1, h2, p0 = 0.5;
    private final int plotType;
    final int numDemes = 50;
    int gen = 0;
    final double[][] w = new double[numDemes][3];
    ;
    final double[][] f = new double[numDemes][3];
    final double[] p;

    String mcap1 = "Selection: Gradient";
    String mcap2 = "Selection: Heterozygote Advantage";
    String mcap3 = "Selection: Local Heterozygote";
    String mcap4 = "Selection: Frequency Dependent";


    void setUpW() {
        double temp;

		for(int i=0; i<numDemes; i++){
			temp = s*i/(numDemes-1);
			switch(plotType){
			case GRAD:
				w[i][0] = 1.0 - temp;
				w[i][1] = 1.0 - s/2.0;
				w[i][2] = 1.0 - s + temp;
				break;
			case HETA:
				w[i][0] = 1.0 - h1 - temp;
				w[i][1] = 1.0;
				w[i][2] = 1.0 - h1 - s + temp;
				break;
			case LOCH:
				w[i][0] = 1.0 - h2 - temp;
				w[i][2] = 1.0 - h2 - s + temp;
				w[i][1] = Math.max(w[i][0],w[i][2]) + h2;
				break;
			case FREQ:
				temp = 1.0 - i/(numDemes-1.0);
				f[i][0] = temp*temp;
				f[i][1] = 2.0*temp*(1.0 - temp);
				f[i][2] = (1.0 - temp)*(1.0 - temp);

				w[i][0] = 1.0 - s*(p[i]*p[i]                 - f[i][0]);
				w[i][1] = 1.0 - s*(2.0*p[i]*(1.0 - p[i])     - f[i][1]);
				w[i][2] = 1.0 - s*((1.0 - p[i])*(1.0 - p[i]) - f[i][2]);
				break;
			}
		}
	}

    double getNextP(double[] wa, double p) {
        double q = 1.0 - p, denom;

        denom = p * p * wa[0] + 2.0 * p * q * wa[1] + q * q * wa[2];

        if (denom > 0) return p * (p * wa[0] + q * wa[1]) / denom;
        else return 0.5;
    }

    public void doGeneration() {
        for (int i = 0; i < numDemes; i++) {
            p[i] = getNextP(w[i], p[i]);

            if (i == (numDemes - 1)) {
                p[i] = (1.0 - g) * p[i] + 0.5 * g * (p[i] + p[i - 1]);
            } else if (i == 0) {
                p[i] = (1.0 - g) * p[i] + 0.5 * g * (p[i] + p[i + 1]);
            } else {
                p[i] = (1.0 - g) * p[i] + 0.5 * g * (p[i + 1] + p[i - 1]);
            }

            if (plotType == FREQ) {
                w[i][0] = 1.0 - s * (p[i] * p[i] - f[i][0]);
                w[i][1] = 1.0 - s * (2.0 * p[i] * (1.0 - p[i]) - f[i][1]);
                w[i][2] = 1.0 - s * ((1.0 - p[i]) * (1.0 - p[i]) - f[i][2]);
            }
        }
        gen++;
    }

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        double[][][] points = new double[4][2][numDemes];

        /*we may not actually want the fitnesses (or frequencies) graphed...*/
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < numDemes; j++) {
                points[i][0][j] = j + 1;
                if (plotType == FREQ)
                    points[i][1][j] = f[j][i];
                else
                    points[i][1][j] = w[j][i];
            }

        for (int i = 0; i < numDemes; i++) {
            points[3][0][i] = i + 1;
            points[3][1][i] = p[i];
        }
        String[] ycaps = new String[2];
        if (plotType == FREQ)
            ycaps[0] = "Local Allelic Frequency (  " + ColorScheme.getColorString(3) + "<i><b>p</> )";
        else
            ycaps[0] = "Local Allelic Fitness (  " + ColorScheme.getColorString(3) + "<i><b>w\u0305</> )";
        ycaps[1] = "Local Genotypic Fitness ( "
                + ColorScheme.getColorString(0) + "<i><b>w</b></i><sub>AA</> )"
                + ColorScheme.getColorString(1) + "<i><b>w</b></i><sub>Aa</> )"
                + ColorScheme.getColorString(2) + "<i><b>w</b></i><sub>aa</> )";
        String mcap = "Endler Cline Model, <i>t</i> = " + gen;
		/*
      switch(plotType){
         case FREQ: mcap = mcap1;
            break;
         case GRAD: mcap = mcap2;
            break;
         case HETA: mcap = mcap3;
            break;
         case LOCH: mcap = mcap4;
            break;
      }
		 */
        BasicPlotInfo bpi = new BasicPlotInfo(points, mcap, "Deme Number", "");
        bpi.setYCaptions(ycaps);
        bpi.setIsFrequencies(true);
        for (int i = 0; i < 3; i++) bpi.setLineStyle(i, BasicPlotInfo.DASHED);
        bpi.setLineWidth(3, 3);
        return bpi;
    }

    public SGFACParamInfo(double s, double g, double h1, double h2, int plotType) {
        this.s = s;
        this.g = g;
        this.h1 = h1;
        this.h2 = h2;
        this.plotType = plotType;
        p = new double[numDemes];
        Arrays.fill(p, p0);
        setUpW();
    }
}
