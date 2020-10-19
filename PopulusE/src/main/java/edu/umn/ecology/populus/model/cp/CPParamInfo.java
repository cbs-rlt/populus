/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.cp;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class CPParamInfo extends APPDProtoParamInfo {
	public static final int DDGrowth = 1; //one of these supposed to be 0?
	public static final int DIGrowth = 1;
	protected int modelType = 0;
	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.cp.Res" );

	@Override
	public BasicPlotInfo getBasicPlotInfo() {
		BasicPlotInfo bpi = super.getBasicPlotInfo();
		bpi.vsTimeChars = new String[] {
				"N", "P", "Q"
		};
		return bpi;
	}

	public CPParamInfo( double N0, double P0, double Q0, double l, double a1, double a2, double k1, double k2, int gens, boolean vsTime ) {
		discProc = new CPProc( l, a1, a2, k1, k2 );
		this.gens = gens;
		plotType = vsTime ? NvsT : NvsN;

		//modelType = linear ? DIGrowth : DDGrowth;
		initialConditions = new double[] {
				N0, P0, Q0
		};
		mainCaption = res.getString( "Discrete_Predator" );
		xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : "<i> N </i>";
		yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + " N</>, " + ColorScheme.getColorString( 1 ) + "<i>P</>, " + ColorScheme.getColorString( 2 ) + "<i>Q</>" : ColorScheme.getColorString( 1 ) + "<i>P</>";
	}
}
