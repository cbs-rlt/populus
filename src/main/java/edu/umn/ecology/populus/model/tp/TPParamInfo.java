/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.tp;

import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.util.ResourceBundle;

public class TPParamInfo implements BasicPlot {
    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;
    public static final int vsT = 0; //S,I,R,N vs T or S,I,N vs T
    final String mCapNvsT = res.getString("Bacterial");
    final int modelType;
    final int numVars;
    static final ResourceBundle res = ResourceBundle.getBundle(
            "edu.umn.ecology.populus.model.tp.Res");
    final Integrator ig;
    String mCapN2vsN1 = mCapNvsT;
    String caption;
    final String xCap = res.getString("Time_b_i_t_");
    final double temp;
    final double v;
    final double s;
    final double l;
    final double resistant;
    final double r;
    final double time;
    final String yCap1 = res.getString("One");
    final String yCap2 = res.getString("Two");
    final String yCap3 = res.getString("Three");
    final String yCap4 = res.getString("Four");
    final String yCap5 = res.getString("Five");


    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        //double[] totalPop;
        double[][] ylists;
        double[] initialConditions = new double[numVars + 1];
        initialConditions[0] = r;
        initialConditions[1] = l;
        initialConditions[2] = temp;
        if (numVars >= 5) {
            initialConditions[3] = s;
            if (numVars == 5) {
                if (modelType == THREE)
                    initialConditions[4] = resistant;
                else
                    initialConditions[4] = v;
            } else {
                initialConditions[4] = resistant;
                initialConditions[5] = v;
            }
        }

        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        ig.doIntegration(initialConditions, 0.0, time);
        xlist = ig.getX();
        ylists = ig.getY();
        points = new double[numVars - 1][2][];




		switch( modelType ) {
		case ONE:
			points[0][0] = xlist;
			points[0][1] = ylists[1];//L
			points[1][0] = xlist;
			points[1][1] = ylists[2];//T
			bp = new BasicPlotInfo(points, mCapNvsT, xCap, yCap1);
			bp.setYMin(0.0);
			/*bp.vsTimeChars = new String[] {
              "L", "T"
            };*/
			break;
		case TWO:
			points[0][0] = xlist;
			points[0][1] = ylists[1];//L
			points[1][0] = xlist;
			points[1][1] = ylists[2];//T
			points[2][0] = xlist;
			points[2][1] = ylists[3];//S
			bp = new BasicPlotInfo(points, mCapNvsT, xCap, yCap2);
			bp.setYMin(0.0);
			bp.vsTimeChars = new String[] {
					"T", "S", "L"
			};
			break;
		case THREE:
			points[0][0] = xlist;
			points[0][1] = ylists[1];//L
			points[1][0] = xlist;
			points[1][1] = ylists[2];//T
			points[2][0] = xlist;
			points[2][1] = ylists[3];//S
			points[3][0] = xlist;
			points[3][1] = ylists[4]; //R
			bp = new BasicPlotInfo(points, mCapNvsT, xCap, yCap3);
			bp.setYMin(0.0);
			bp.vsTimeChars = new String[] {
					"T", "S", "L", "R"
			};
			break;
		case TPParamInfo.FOUR:
			points[0][0] = xlist;
			points[0][1] = ylists[1];//L
			points[1][0] = xlist;
			points[1][1] = ylists[2];//T

			points[2][0] = xlist;
			points[2][1] = ylists[3];//S
			points[3][0] = xlist;
			points[3][1] = ylists[4]; //
			bp = new BasicPlotInfo(points, mCapNvsT, xCap, yCap4);
			bp.setYMin(0.0);
			bp.vsTimeChars = new String[] {
					"T", "V", "S", "L"
			};
			break;
		case FIVE:
			points[0][0] = xlist;
			points[0][1] = ylists[1];//L
			points[1][0] = xlist;
			points[1][1] = ylists[2];//T
			points[2][0] = xlist;
			points[2][1] = ylists[3];//S
			points[3][0] = xlist;
			points[3][1] = ylists[4]; //R
			points[4][0] = xlist;
			points[4][1] = ylists[5]; //V
			bp = new BasicPlotInfo(points, mCapNvsT, xCap, yCap5);
			bp.setYMin(0.0);
			bp.vsTimeChars = new String[] {
					"T", "V", "S", "L", "R"
			};
			break;
		}
		return bp;
	}

    public TPParamInfo(int modelType, double time, /*time < 0 for steady state*/
                       double T, double V, double S, double L, double R, double r,
                       double e, double sigmaV, double sigmaT, double betaV,
                       double betaT, double lambda, double induction, double tau,
                       double c, double rho, double p, double q, double alphaS, double alphaR) {
        ig = new Integrator(new TPDeriv(modelType, e, sigmaV, sigmaT, betaV, betaT,
                lambda, induction, tau, c, rho, p, q, alphaS, alphaR));
        this.temp = T;
        this.v = V;
        this.s = S;
        this.resistant = R;
        this.l = L;
        this.r = r;
        this.time = time;
        this.modelType = modelType;
        if (modelType == ONE)
            numVars = 3;
        else {
            if (modelType == TWO)
                numVars = 4;
            else {
                if (modelType == FIVE)
                    numVars = 6;
                else
                    numVars = 5;
            }
        }
    }
}
