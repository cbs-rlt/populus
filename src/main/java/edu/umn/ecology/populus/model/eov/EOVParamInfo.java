/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.eov;

import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.util.ResourceBundle;

public class EOVParamInfo implements BasicPlot {
    public static final int ALTER = 9;
    public static final int COUPLED = 10;
    public static final int hivsT = 0;//H, I vs t
    public static final int ebvsT = 1;//e, b vs t
    public static final int hiStar = 2;//H*, I*, H* + I* vs b
    final String mCapNvsT = res.getString("Infectious1");
    final int plotType;

    final Integrator ig;
    String mCapN2vsN1 = mCapNvsT;
    final String xCap = res.getString("Time_b_i_t_");
    final double x;
    final double y;
    double z;
    final double time;
    final double c1;
    final double c2;
    final double c3;
    final double d;
    final double a0;
    final double a1;
    final double p;
    final String xCap2 = " Transmission Rate ( <b><i>b</i></b> )";
    final String yCap4 = res.getString("hstar_");
    final String yCap2 = res.getString("Susceptable_Host_S_") + ", " + res.getString("Infected_Host_I_");
    final String yCap3 = res.getString("Virulence_");
    String nCaption = res.getString("totHostDens");
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.eov.Res");


    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp = null;
        double[][][] points;
        double[] xlist;
        double[] eopt, bopt;
        double[] hstar, istar, histar, bstar;
        double[][] ylists;
        double[] initialConditions = new double[2];
        int size;

        if (time < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }

        initialConditions[0] = x;
        initialConditions[1] = y;
        ig.doIntegration(initialConditions, 0.0, time);
        xlist = ig.getX();
        ylists = ig.getY();
        size = xlist.length;

		switch(plotType){
		case hivsT:
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[0];
			points[1][0] = xlist;
			points[1][1] = ylists[1];
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, yCap2 );
			//bp.setYMin( 0.0 );
			/*bp.vsTimeChars = new String[] {
               "H", "I"
            };*/
			break;
		case ebvsT:
			eopt = new double [size];
			bopt = new double [size];
			for(int i=0; i<size; i++){
				bopt[i] = (ylists[1][i] - c2)/ (2*c3);
				eopt[i] = c1 + c2 * bopt[i] + c3 * bopt[i] * bopt[i];
			}
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = eopt;
			points[1][0] = xlist;
			points[1][1] = bopt;
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, yCap3 );
			//bp.setYMin( 0.0 );
			bp.vsTimeChars = new String[] {
					"e", "b"
			};

			break;
		case hiStar:
			double es, hs, is, AA, BB, CC;
			double bs = 0.005;
			int j = 0;
			bstar = new double [1246];
			hstar = new double [1246];
			istar = new double [1246];
			histar = new double [1246];
			while ( bs < 1.25 ){
				bstar[j] = bs;
				es = c1 + c2 * bs + c3 * bs * bs;
				hs = (d+es)/bs;
				hstar[j] = hs;
				AA = hs * (a0 - a1*hs - d);
				BB = hs * ( a1 * (1 + p)+bs) - p * a0;
				CC = a1 * p;
				if ( CC == 0)
					is = 0;
				else
					is = (Math.sqrt(BB * BB + 4 * AA * CC)- BB) / (2 * CC);
				istar[ j ] = is;
				histar[j] = is + hs ;
				bs = bs + 0.001;
				j ++;
			}
			points = new double[3][2][];
			points[0][0] = bstar;
			points[0][1] = hstar;
			points[1][0] = bstar;
			points[1][1] = istar;
			points[2][0] = bstar;
			points[2][1] = histar;
			bp = new BasicPlotInfo( points, mCapNvsT, xCap2, yCap4 );
			//bp.setYMin( 0.0 );
			bp.vsTimeChars = new String[] {
					"e", "b"
			};

			break;
		}
		return bp;
	}

    public EOVParamInfo(int modelType, int plotType, double time, /*time < 0 for steady state*/ double X, double Y, double a0, double a1, double b, double d, double p, double e, double c1, double c2, double c3) {
        ig = new Integrator(new EOVDeriv(modelType, a0, a1, b, d, p, e, c1, c2, c3));

        this.x = X;
        this.y = Y;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.d = d;
        this.a0 = a0;
        this.a1 = a1;
        this.p = p;
        this.time = time;
        this.plotType = plotType;

    }
}
