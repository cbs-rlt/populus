/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.bp;

import edu.umn.ecology.populus.math.Derivative;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class BPParamInfo implements BasicPlot {
    public static final int seasonal = 9;
    public static final int equable = 10;
    public static final int constant = 11;
    public static final int variable = 12;
    public static final int vsT = 0;//N+, N* , N , Ntotal vs t
    public static final int nplusvsn = 1;//N+ vs N
    public static final int nstarvsn = 2;//N* vs N
    public static final int fvst = 3;//F vs t
    final int numVars;
    final String mCapNvsT = res.getString("Bacterial");
    final int plotType;
    final int modelType;
    final double x;
    final double y;
    final double z;
    final double w;
    final double d; // w is r in the equations
    final double time;
    int tlast;
    final int tI;
    final Integrator ig;
    final Derivative der;
    final String mCapN2vsN1 = mCapNvsT;
    final String xCap = res.getString("Time_b_i_t_");
    final String yCap2 = res.getString("Plasmid-Bearing");
    final String yCap3 = res.getString("nstar");
    final String Caption = res.getString("N_t_");
    final String totCaption = res.getString("plasmid-free");
    final String fCaption = res.getString("frequency");
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.bp.Res");


    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        double[] totalPop;
        double[] f1;
        double[] f2;
        double[][] ylists;
        double[] initialConditions = new double[4];
        double[] seasonalCond = new double[4];
        int size;
        initialConditions[0] = x;
        initialConditions[1] = y;
        initialConditions[2] = z;
        initialConditions[3] = w;

        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }

        if (modelType == BPParamInfo.equable) {
            ig.doIntegration(initialConditions, 0.0, time);
            xlist = ig.getX();
            ylists = ig.getY();
        } else {
            seasonalCond = initialConditions;
            double count = time / tI;
            ArrayList<Double> newlist = new ArrayList<>();
            ArrayList<ArrayList<Double>> newYlist = new ArrayList<>();
            newYlist.add(new ArrayList<>()); //0
            newYlist.add(new ArrayList<>()); //1
            newYlist.add(new ArrayList<>()); //2
            newYlist.add(new ArrayList<>()); //3

            tlast = 0;
            for (int num = 0; num < count; num++) {
                ig.doIntegration(seasonalCond, 0, tI);
                ig.getDerivative().reset();
                xlist = ig.getX();
                ylists = ig.getY();
                // ig.reset();
                seasonalCond[0] = ylists[0][ylists[0].length - 1] * d;
                seasonalCond[1] = ylists[1][ylists[1].length - 1] * d;
                seasonalCond[2] = ylists[2][ylists[2].length - 1] * d;
                seasonalCond[3] = w;
                for (int j = 0; j < xlist.length; j++) {
                    newlist.add(Double.valueOf(xlist[j] + num * tI));
                    for (int i = 0; i < 4; i++)
                        newYlist.get(i).add(Double.valueOf(ylists[i][j]));
                }
                tlast = tlast + tI;
            }
            xlist = new double[newlist.size()];
            ylists = new double[4][xlist.length];
            for (int j = 0; j < newlist.size(); j++) {
                xlist[j] = newlist.get(j).doubleValue();
                for (int i = 0; i < 4; i++)
                    ylists[i][j] = newYlist.get(i).get(j).doubleValue();
            }

        }
        size = xlist.length;
        totalPop = new double[size];
        f1 = new double[size];
        f2 = new double[size];
        for (int i = 0; i < totalPop.length; i++) {
            for (int j = 0; j < 3; j++)
                totalPop[i] += ylists[j][i];
            f1[i] += ylists[0][i] / totalPop[i];
            f2[i] += ylists[1][i] / totalPop[i];
        }
        //PlotArrow.addFletching( bp, 0 );
        switch (plotType) {
            case vsT -> {
                points = new double[4][2][];
                points[0][0] = xlist;
                points[0][1] = ylists[0];//N+
                points[1][0] = xlist;
                points[1][1] = ylists[1];//N*
                points[2][0] = xlist;
                points[2][1] = ylists[2];//N
                points[3][0] = xlist;
                points[3][1] = totalPop;//Ntotal
                bp = new BasicPlotInfo(points, mCapNvsT, xCap, Caption);
                bp.setYMin(0.0);
                bp.vsTimeChars = new String[]{
                        "n+", "n*", "n", "n+ + n* + n"
                };
            }
            case nplusvsn -> {
                points = new double[1][2][];
                points[0][1] = ylists[0];//n+
                points[0][0] = ylists[2];//n
                bp = new BasicPlotInfo(points, mCapN2vsN1, totCaption, yCap2);
                bp.setYMin(0.0);
                PlotArrow.addArrow(bp, 0);
            }
            case nstarvsn -> {
                points = new double[1][2][];
                points[0][1] = ylists[1];//n*
                points[0][0] = ylists[2];//n
                bp = new BasicPlotInfo(points, mCapN2vsN1, totCaption, yCap3);
                bp.setYMin(0.0);
                PlotArrow.addArrow(bp, 0);
            }
            case fvst -> {
                points = new double[2][2][];
                points[0][1] = f1;// N+/N
                points[0][0] = xlist;//t
                points[1][1] = f2;// N+/N
                points[1][0] = xlist;//t
                bp = new BasicPlotInfo(points, mCapN2vsN1, xCap, fCaption);
            }
        }
        return bp;
    }

    public BPParamInfo(int modelType, int plotType, int varType, double time, /*time < 0 for steady state*/
                       double X, double Y, double Z, double r, double alpha, double gamma, double tau, double rho,
                       double c, double p, double q, double e, double d, int tI) {
        der = new BPDeriv(modelType, varType, time, alpha, gamma, tau, rho, c, r, p, q, e, d, tI);
        ig = new Integrator(der);
        this.x = X;
        this.y = Y;
        this.z = Z;
        this.w = r;
        this.time = time;
        this.tI = tI;
        this.d = d;
        this.modelType = modelType;
        this.plotType = plotType;
        numVars = 4;
    }
}