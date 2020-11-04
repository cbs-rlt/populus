/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.imd;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;

import java.util.ResourceBundle;

public class IMDParamInfo implements BasicPlot {
    public static final int SIDD = 9;
    public static final int SIFD = 10;
    public static final int SIRDD = 11;
    public static final int SIRFD = 12;
    public static final int vsT = 0;//S,I,R,N vs T or S,I,N vs T
    public static final int ivsn = 1;//I vs N
    public static final int phase = 2;//S vs I vs R or I vs S
    final String mCapNvsT = res.getString("Infectious1");
    final int plotType;
    final int numVars;

    final Integrator ig;
    final String mCapN2vsN1 = mCapNvsT;
    final String xCap = res.getString("Time_b_i_t_");
    final double x;
    final double y;
    final double z;
    final double time;
    final String yCap2 = res.getString("Host_Population") + "<b><i>" + ColorScheme.getColorString(0) + "N </font></i></b>, <b><i>" + ColorScheme.getColorString(1) + "S </font></i></b>, <b><i>" + ColorScheme.getColorString(2) + "I </font>" + "</i></b> )";
    final String yCap3 = res.getString("Host_Population") + "<b><i>" + ColorScheme.getColorString(0) + "N </font></i></b>, <b><i>" + ColorScheme.getColorString(1) + "S </font></i></b>, <b><i>" + ColorScheme.getColorString(2) + "I </font>" + "</i></b>, <b><i>" + ColorScheme.getColorString(3) + "R </font></i></b> )";
    final String sCaption = res.getString("Susceptable_Host_S_R");
    final String iCaption = res.getString("Infected_Host_I_R");
    final String nCaption = res.getString("totHostDens");
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.imd.Res");


    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        double[] totalPop;
        double[][] ylists;
        double[] initialConditions = new double[numVars];
        int size;
        initialConditions[0] = x;
        initialConditions[1] = y;
        if (numVars == 3) {
            initialConditions[2] = z;
        }
        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        ig.doIntegration(initialConditions, 0.0, time);
        xlist = ig.getX();
        ylists = ig.getY();
        size = xlist.length;
        totalPop = new double[size];
        for (int i = 0; i < totalPop.length; i++)
            for (double[] ylist : ylists) totalPop[i] += ylist[i];

        //PlotArrow.addFletching( bp, 0 );
        //PlotArrow.addFletching( bp, 0 );
        switch (plotType) {
            case vsT -> {
                points = new double[numVars + 1][2][];
                points[0][0] = xlist;
                points[0][1] = totalPop;
                points[1][0] = xlist;
                points[1][1] = ylists[0];
                points[2][0] = xlist;
                points[2][1] = ylists[1];
                if (numVars == 3) {
                    points[3][0] = xlist;
                    points[3][1] = ylists[2];
                }
                String caption = numVars == 3 ? yCap3 : yCap2;
                bp = new BasicPlotInfo(points, mCapNvsT, xCap, caption);
                bp.setYMin(0.0);
                bp.vsTimeChars = new String[]{
                        "S", "I", "R"
                };
            }
            case ivsn -> {
                points = new double[1][2][];
                points[0][1] = ylists[1];
                points[0][0] = totalPop;
                bp = new BasicPlotInfo(points, mCapN2vsN1, nCaption, iCaption);
                PlotArrow.addArrow(bp, 0);
            }
            case phase -> {
                points = new double[1][][];
                points[0] = ylists;
                bp = new BasicPlotInfo(points, mCapN2vsN1, sCaption, iCaption);
                PlotArrow.addArrow(bp, 0);
            }
        }
        return bp;
    }

    public IMDParamInfo(int modelType, int plotType, double time, /*time < 0 for steady state*/ double X, double Y, double Z, double a, double b, double alpha, double beta, double nu, double gamma) {
        ig = new Integrator(new IMDDeriv(modelType, a, b, alpha, beta, nu, gamma));
        this.x = X;
        this.y = Y;
        this.z = Z;
        this.time = time;
        this.plotType = plotType;
        numVars = ((modelType == SIRDD) || (modelType == SIRFD)) ? 3 : 2;
    }
}
