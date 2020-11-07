/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.kham;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.math.DiscreteProc;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;

public class KHAMParamInfo implements BasicPlot {
    public static final int t2vsp2 = 0;
    public static final int DvsT = 1;
    public static final int MVvsT = 2;
    public static final int DvsT2P2 = 3;

    final double a1, a2, s, r;
    final double[] ic = new double[3];
    final int plotType, gens;
    final DiscreteProc dp;

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bpi = null;
        Integrator ig = new Integrator(dp);
        double[][][] points;
        double[][] ylists;
        double[] xlist;

        if (gens < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        ig.integrate(ic, 0.0, gens);
        xlist = ig.getX();
        ylists = ig.getY();
        int size = xlist.length;

		switch(plotType){
		case t2vsp2:
			double p2e1, p2e2, q;
			q = a1*a2 - 1.0;
			if(q != 0.0)     q = (a1 + s - 1.0)/q;
			else             q = 0.0;
			p2e2 = q*a2;
			if(1.0-s != 0.0) p2e1 = q/(1.0 - s);
			else             p2e1 = 0.0;

			points = new double[2][2][size];
			points[0][0] = ylists[KHAMProc.p2y];
			points[0][1] = ylists[KHAMProc.t2y];
			points[1] = new double[][] {{0,p2e1,p2e2,1.0},{0.0,0.0,1.0,1.0}};
			String ycap1 = "Trait Frequency ( "+ColorScheme.getColorString(0)+"<i><b>t<sub>2</> )";
			bpi = new BasicPlotInfo(points,"Arbitrary Sexual Selection: Frequencies","Preference Frequency ( <b><i>p<sub>2</> )",ycap1);
			bpi.setIsFrequencies(true);
			PlotArrow.addArrow(bpi,0);
			//PlotArrow.addFletching(bpi,0);
			//bpi.setDiscrete(0);
			bpi.setLineWidth( 1, 4 );
			break;
		case DvsT:
			points = new double[1][2][size];
			points[0][0] = xlist;
			points[0][1] = ylists[KHAMProc.Dy];
			String ycap2 = ColorScheme.getColorString(0)+"<i><b>D</>";
			bpi = new BasicPlotInfo(points,"Arbitrary Sexual Selection","Generations<b>(<i>t</i>)</>",ycap2);
			break;
		case MVvsT:
			points = new double[1][2][xlist.length];
			for(int i=0; i<points[0][0].length; i++){
				points[0][0][i] = i;
				points[0][1][i] = 1.0 - s*ylists[KHAMProc.t2y][i];
			}
			bpi = new BasicPlotInfo(points,"Arbitrary Sexual Selection","Generations<b>(<i>t</i>)</>","Male Viability  ");
			break;
		case DvsT2P2:
			points = new double[1][3][size];
			points[0][0] = ylists[KHAMProc.t2y];
			points[0][1] = ylists[KHAMProc.Dy];
			points[0][2] = ylists[KHAMProc.p2y];
			bpi = new BasicPlotInfo(points,"Arbitrary Sexual Selection","<i>t<sub>2</>","<i>D</i>");
			bpi.setGraphType(BasicPlotInfo.k3D);
			bpi.setZCaption("<i>p<sub>2</>");
			break;
		}
		return bpi;
	}

    public KHAMParamInfo(double t2, double p2, double a1, double a2, double D0, double s, double r,
                         int gens, int plotType) {
        this.a1 = a1;
        this.a2 = a2;
        this.s = s;
        this.r = r;
        this.gens = gens;
        this.plotType = plotType;
        ic[KHAMProc.t2y] = t2;
        ic[KHAMProc.p2y] = p2;
        ic[KHAMProc.Dy] = D0;
        dp = new KHAMProc(s, r, a1, a2);
    }
}
