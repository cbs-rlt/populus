/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvc;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;
import java.util.*;

public class LVCParamInfo implements BasicPlot {
	String xCap = res.getString( "Time_b_i_t_" );
	String yCap = res.getString( "Population_" ) + ColorScheme.getColorString( 0 ) + "<b><i>N<sub></i>1</sub></font> , " + ColorScheme.getColorString( 1 ) + "<i>N<sub></i>2</sub></font> )";
	double[][] isocline1, isocline2;
	String mCapNvsT = res.getString( "Lotka_Volterra1" );
	Integrator ig;
	boolean vsTime;
	double n1, n2, time;
	String n1Cap = res.getString( "Population_b_i_" ) /*+ ColorScheme.getColorString( 0 )*/ + "N<SUB></i>1</> )";
	String n2Cap = res.getString( "Population_b_i_" ) /*+ ColorScheme.getColorString( 1 )*/ + "N<SUB></i>2</> )";
	String mCapN2vsN1 = res.getString( "Lotka_Volterra2" );
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvc.Res" );

	@Override
	public BasicPlotInfo getBasicPlotInfo() {
		BasicPlotInfo bp;
		double[][][] points;
		double[] xlist;
		double[][] ylists;
		double[] initialConditions = new double[2];
		initialConditions[0] = n1;
		initialConditions[1] = n2;
		if( time < 0 ) {
			ig.record.ss = true;
			ig.record.interval = false;
		}
		ig.doIntegration( initialConditions, 0.0, time );
		xlist = ig.getX();
		ylists = ig.getY();
		if( vsTime ) {
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[0];
			points[1][0] = xlist;
			points[1][1] = ylists[1];
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, yCap );
			bp.setYMin( 0.0 );
			bp.vsTimeChars = new String[] {
					"N1", "N2"
			};
		} else {
			points = new double[3][][];
			points[0] = isocline1;
			points[1] = isocline2;
			points[2] = ylists;
			bp = new BasicPlotInfo( points, mCapN2vsN1, n1Cap, n2Cap );
			PlotArrow.addFletching( bp, 2 );
			PlotArrow.addArrow( bp, 2 );
		}
		return bp;
	}

	public LVCParamInfo( boolean vsTime, double time, /*time < 0 for steady state*/ double N01, double r1, double K1, double alpha, double N02, double r2, double K2, double beta ) {
		ig = new Integrator( new LVCDeriv( r1, K1, alpha, r2, K2, beta ) );
		this.n1 = N01;
		this.n2 = N02;
		this.time = time;
		this.vsTime = vsTime;
		isocline1 = new double[][] {
			{
				0, K1
			},  {
				K1 / alpha, 0
			}
		}; //N1 = K2 - beta*N1
		isocline2 = new double[][] {
			{
				0, K2 / beta
			},  {
				K2, 0
			}
		}; //N2 = K1 - alpha*N2
	}
}
