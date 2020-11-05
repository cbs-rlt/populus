/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aidst;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.awt.*;

public class AIDSTParamInfo implements BasicPlot {

    public static final int vsT = 0;//x, y, v vs t
    public static final int xyvsT = 1;//x, y vs t
    public static final int vvsT = 2;//v vs t
    public static final int xvsYvsV = 3; //x vs y vs v
    final String mCapNvsT = "AIDS Therapy";
    final int plotType;
    final int numVars;

    final Integrator ig;
    String mCapN2vsN1 = mCapNvsT;
    final String xCap = "Time (<b><i>t</i></b>)";
    final double x;
    final double y;
    final double w;
    final double z;
    final double time;
    double s;
    double t1, t2, t3, t4, t5, t6;

    private static final int kVarX = 0, kVarY = 1, kVarW = 2, kVarZ = 3;

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        double[][] ylists;
        double[] initialConditions = new double[4];
        initialConditions[0] = x;
        initialConditions[1] = y;
        initialConditions[2] = w;
        initialConditions[3] = z;
        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }

		ig.doIntegration( initialConditions, 0.0, time );
		xlist = ig.getX();
		ylists = ig.getY();
		String[] yCaptions;
		switch(plotType){
		case vsT:
			points = new double[4][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[0];
			points[1][0] = xlist;
			points[1][1] = ylists[1];
			points[2][0] = xlist;
			points[2][1] = ylists[2];
			points[3][0] = xlist;
			points[3][1] = ylists[3];

			yCaptions = new String[] {getVarString(kVarX), getVarString(kVarY), getVarString(kVarW), getVarString(kVarZ)};

			bp = new BasicPlotInfo( points, mCapNvsT, xCap, "" );
			bp.setYCaptions(yCaptions);
			bp.vsTimeChars = new String[] {
					"x", "y", "w", "z"
			};
			bp.isLogPlot =true;
			break;
		case xyvsT:
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[0];
			points[1][0] = xlist;
			points[1][1] = ylists[1];
			yCaptions = new String[] {getVarString(kVarX), getVarString(kVarY)};
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, "" );
			bp.setYCaptions(yCaptions);
			bp.isLogPlot =true;
			break;
		case vvsT:
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[1];
			points[1][0] = xlist;
			points[1][1] = ylists[2];
			yCaptions = new String[] {getVarString(kVarY), getVarString(kVarW)};
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, "" );
			bp.setYCaptions(yCaptions);
			bp.setColors(new Color[] {ColorScheme.defaultColors[kVarY],  ColorScheme.defaultColors[kVarW]});
			bp.isLogPlot =true;
			break;
		}
		return bp;
	}

	private String getVarString(int i) {
		String var, desc;
		switch (i) {
		case kVarX:
			var = "x";
			desc = "Uninfected host cell density";
			break;
		case kVarY:
			var = "y";
			desc = "Infected host cell density";
			break;
		case kVarW:
			var = "w";
			desc = "CTL precursors, CTLp, density";
			break;
		case kVarZ:
			var = "z";
			desc = "CTL effector density";
			break;
		default:
			var = "?";
			desc = "?";
			break;
		}
		//This is a hack since the multilabel sucks.
		return desc + " ( <b><i>" + ColorScheme.getColorString( i ) + var + "</font></i></b> )";
	}


    public AIDSTParamInfo(int plotType, double time, /*time < 0 for steady state*/ double X, double Y,
            /* double V*/double W, double Z, double lambda, double d, double a, double beta,
                          double b, double c, double q, double h, double s, double p, double[][] intervals) {
        ig = new Integrator(new AIDSTDeriv(lambda, d, a, beta, b, c, q, h, s, p, intervals));
        this.x = X;
        this.y = Y;
        //this.v=V;
        this.w = W;
        this.z = Z;
        this.time = time;
        this.plotType = plotType;
        numVars = 4;
    }
}
