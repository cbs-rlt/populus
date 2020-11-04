/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.fdsdm;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

public class FDSDMParamInfo implements BasicPlot {
    public static final int PvsT = 0;
    public static final int WBARvsT = 1;

    final double[] w;
    final int gens, plotType;
    final double freq;

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bpi = null;
        double[][][] points = new double[1][2][gens + 1];
        double p = freq, q;
        double wtot1, wtot2, wtot3, wbar;

        for (int i = 0; i <= gens; i++) {
            q = 1.0 - p;
            points[0][0][i] = i;
            if (plotType == PvsT)
                points[0][1][i] = p;

            wtot1 = p * p * w[0] + 2.0 * p * q * w[1] + q * q * w[2];
            wtot2 = p * p * w[3] + 2.0 * p * q * w[4] + q * q * w[5];
            wtot3 = p * p * w[6] + 2.0 * p * q * w[7] + q * q * w[8];

            wbar = p * p * wtot1 + 2.0 * p * q * wtot2 + q * q * wtot3;

            if (plotType == WBARvsT)
                points[0][1][i] = wbar;

            if (wbar != 0.0)
                p = (p * p * wtot1 + p * q * wtot2) / wbar;
            else p = 0.0;
        }

        String ycap = (plotType == PvsT) ? "Frequency of the \"A\" allele (  " + ColorScheme.getColorString(0) + "<b><i>p</i></b></font> )" :
                "Mean Fitness ( " + ColorScheme.getColorString(0) + "<b><i>w\u0305</i></b></font> )";
        String mcap = (plotType == PvsT) ? "Gene Frequency vs Time" : "<b><i>w\u0305</i></b> vs Time";
        bpi = new BasicPlotInfo(points, mcap, "Generations ( <b><i>t</i></b> )", ycap);
        return bpi;
    }

    public FDSDMParamInfo(double[] w, double freq, int gens, int plotType) {
        this.w = w;
        this.gens = gens;
        this.freq = freq;
        this.plotType = plotType;
    }
}
