/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rct;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.math.RungeKuttaRec;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;

import java.util.ResourceBundle;

public class RCTParamInfo implements BasicPlot {
    //public static final int SINGLE = 0;//Single model, just a special case of essential and switching
    public static final int ESSENTIAL = 1;//Essential model
    public static final int SWITCHING = 2;//Switching
    public static final int nvst = 3;//N vs t
    public static final int rvst = 4;//R vs t
    public static final int nvsn = 5;//N vs N
    public static final int rvsr = 6;//R vs R
    static ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.rct.Res");

    RCTVariableIndex vars = null;
    int plotType, modelType;
    //initial conditions
    double[] initN, initR;
    double time;
    Integrator ig;
    double[] m;
    double[] r;
    double[][] k;

    String mCapNvsT = res.getString("Resource_competition");
    String mCapNvsN = res.getString("Resource_competition");
    String mCapRvsR = res.getString("Resource_competition");
    String xCap = res.getString("Time_b_i_t_");
    String yCap1 = "<b><i>" + ColorScheme.getColorString(0) + "N<sub>1</>";
    String yCap2 = "<b><i>" + ColorScheme.getColorString(0) + "N<sub>1</>, <b><i>" + ColorScheme.getColorString(1) + "N<sub>2</>";
    String yCap3 = "<b><i>" + ColorScheme.getColorString(0) + "N<sub>1</>, <b><i>" + ColorScheme.getColorString(1) + "N<sub>2</>, <b><i>" + ColorScheme.getColorString(2) + "N<sub>3</>";
    String yCap4 = "<b><i>" + ColorScheme.getColorString(0) + "R<sub>1</>";
    String yCap5 = "<b><i>" + ColorScheme.getColorString(0) + "R<sub>1</>, <b><i>" + ColorScheme.getColorString(1) + "R<sub>2</>";
    String yCap6 = "<b><i>" + ColorScheme.getColorString(0) + "R<sub>1</>, <b><i>" + ColorScheme.getColorString(1) + "R<sub>2</>, <b><i>" + ColorScheme.getColorString(2) + "R<sub>3</>";

    String n1Caption = res.getString("CapSpecies1");
    String n2Caption = res.getString("CapSpecies2");
    String n3Caption = res.getString("CapSpecies3");
    String r1Caption = res.getString("CapResource1");
    String r2Caption = res.getString("CapResource2");
    String r3Caption = res.getString("CapResource3");

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        double[] totalPop;
        double[][] ylists;
        double[] initialConditions = new double[vars.getTotalNum()];
        int size;
        String caption;
        for (int i = 0; i < vars.getResNum(); i++) {
            initialConditions[vars.getResIdx(i)] = initR[i];
        }
        for (int i = 0; i < vars.getSpecNum(); i++) {
            initialConditions[vars.getSpecIdx(i)] = initN[i];
        }
        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        //Lars - Reduce precision so that we don't take forever
        // This is ugly, but otherwise this model hits max iterations at t~10
        ig.record.eps = 5e-5;
        ig.doIntegration(initialConditions, 0.0, time);
        xlist = ig.getX();
        ylists = ig.getY();
        size = xlist.length;
        totalPop = new double[size];
        for (int i = 0; i < totalPop.length; i++)
            for (double[] ylist : ylists) totalPop[i] += ylist[i];

        //PlotArrow.addArrow( bp, 0 );
        //PlotArrow.addFletching( bp, 0 );
        //PlotArrow.addFletching( bp, 0 );
        //PlotArrow.addFletching( bp, 0 );
        switch (plotType) {
            case nvst -> {
                points = new double[vars.getSpecNum()][2][];
                for (int i = 0; i < vars.getSpecNum(); i++) {
                    points[i][0] = xlist;
                    points[i][1] = ylists[vars.getSpecIdx(i)];
                }
                if (vars.getSpecNum() == 1)
                    caption = yCap1;
                else if (vars.getSpecNum() == 2)
                    caption = yCap2;
                else
                    caption = yCap3;
                bp = new BasicPlotInfo(points, mCapNvsT, xCap, caption);
                bp.setYMin(0.0);
            }
            case rvst -> {
                points = new double[vars.getResNum()][2][];
                for (int i = 0; i < vars.getResNum(); i++) {
                    points[i][0] = xlist;
                    points[i][1] = ylists[vars.getResIdx(i)];
                }
                if (vars.getResNum() == 1)
                    caption = yCap4;
                else if (vars.getResNum() == 2)
                    caption = yCap5;
                else
                    caption = yCap6;
                bp = new BasicPlotInfo(points, mCapNvsT, xCap, caption);
                bp.setYMin(0.0);
            }
            case nvsn -> {
                points = new double[1][vars.getSpecNum()][];
                points[0][0] = ylists[vars.getSpecIdx(0)];
                points[0][1] = ylists[vars.getSpecIdx(1)];
                if (vars.getSpecNum() == 3) {
                    points[0][2] = ylists[vars.getSpecIdx(2)];
                    bp = new BasicPlotInfo(points, mCapNvsN, n1Caption, n2Caption, n3Caption);
                } else {
                    bp = new BasicPlotInfo(points, mCapNvsN, n1Caption, n2Caption);
                    PlotArrow.addArrow(bp, 0);
                }
            }
            case rvsr -> {
                points = new double[1][vars.getResNum()][];
                points[0][0] = ylists[vars.getResIdx(0)];
                points[0][1] = ylists[vars.getResIdx(1)];
                if (vars.getResNum() == 3) {
                    points[0][2] = ylists[vars.getResIdx(2)];
                    bp = new BasicPlotInfo(points, mCapRvsR, r1Caption, r2Caption, r3Caption);
                    bp.setZMin(0.0);
                } else {
                    bp = new BasicPlotInfo(points, mCapRvsR, r1Caption, r2Caption);
                    if (modelType == ESSENTIAL) {
                        //Add isoclines
                        bp.findBounds();
                        double xmax = bp.getMaxXVal();
                        double ymax = bp.getMaxYVal();
                        for (int i = 0; i < vars.getSpecNum(); i++) {
                            double r1star = (m[i] * k[i][0]) / (r[i] - m[i]); //x
                            double r2star = (m[i] * k[i][1]) / (r[i] - m[i]); //y
                            double[][] isocline = new double[][]{{r1star, r1star, xmax}, {ymax, r2star, r2star}};
                            bp.addData(isocline);
                        }
                    } else {
                        //SWITCHING
                        for (int i = 0; i < vars.getSpecNum(); i++) {
                            double r1star = (m[i] * k[i][0]) / (r[i] - m[i]); //x
                            double r2star = (m[i] * k[i][1]) / (r[i] - m[i]); //y
                            double[][] isocline = new double[][]{{0.0, r1star, r1star}, {r2star, r2star, 0.0}};
                            bp.addData(isocline);
                        }
                    }
                    //Move the isoclines to the front
                    bp.moveDataToBack(0);

                    PlotArrow.addArrow(bp, vars.getSpecNum());
                }
                bp.setYMin(0.0);
                bp.setXMin(0.0);
            }
        }
        return bp;
    }

    public RCTParamInfo(int modelType, int plotType, double time, /*time < 0 for steady state*/int speciesNum, int resourceNum, double N01, double N02, double N03, double[] a, double[] r, double R1, double R2, double R3, double[] m, double[][] k, double[][] c, double[] s) {
        ig = new Integrator(new RCTDeriv(modelType, speciesNum, resourceNum, a, r, m, k, c, s, false));

		/*Placing these here means that users will not be able to change
      these through the preferences panel*/
        ig.record.h = 0.1;
        ig.record.mode = RungeKuttaRec.RK4;
        //ig.record.ssErr = 10;

        this.modelType = modelType;
        this.m = m;
        this.r = r;
        this.k = k;
        this.initN = new double[3];
        this.initR = new double[3];
        this.initN[0] = N01;
        this.initN[1] = N02;
        this.initN[2] = N03;
        this.initR[0] = R1;
        this.initR[1] = R2;
        this.initR[2] = R3;
        this.time = time;
        this.vars = new RCTVariableIndex(speciesNum, resourceNum);
        this.plotType = plotType;
    }
}