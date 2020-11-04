/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.ParamInfo;

public class NBSSGraphParamInfo extends ParamInfo implements BasicPlot {
    /**
     *
     */
    private static final long serialVersionUID = -6979872204518008120L;
    final NBSSCellParamInfo cpi;
    final boolean avg;
    final boolean isup;
    public static final String kMainCap = "Spatial-Structure Populations";
    public static final String kXCap = "Generation";
    String kYCap = "Population ( " + ColorScheme.getColorString(0) + "<i>N</>, "
            + ColorScheme.getColorString(1) + "<i>P</> ) ";

    public NBSSGraphParamInfo(NBSSCellParamInfo pi, boolean plotAverage, boolean isUpdate) {
        cpi = pi;
        avg = plotAverage;
        isup = isUpdate;
        if (avg) kYCap = "Average " + kYCap;
        else kYCap = "Total " + kYCap;
    }

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        int gens = cpi.runInterval;
        if (!isup) {
            cpi.initialF();
            for (int i = 0; i < gens; i++)
                cpi.f();
        }
        return new BasicPlotInfo(cpi.getTotals(avg), kMainCap, kXCap, kYCap);
    }
}
