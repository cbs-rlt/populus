/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdfr;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class APPDFRParamInfo extends APPDProtoParamInfo {
	protected int modelType = 0;
	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdfr.Res" );

	public APPDFRParamInfo( double n0, double p0, double lambda, double ap, double c, double t, double th, double b, int gens, int type, boolean vsTime ) {
		discProc = new APPDFRProc( type, lambda, ap, c, t, th, b );
		this.gens = gens;
		plotType = vsTime ? NvsT : NvsN;
		modelType = type;
		initialConditions = new double[] {
				n0, p0
		};
		mainCaption = res.getString( "Discrete_Predator" );
		xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : res.getString( "_i_N_i_" );
		yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + res.getString( "N" ) + ColorScheme.getColorString( 1 ) + res.getString( "_i_P_" ) : ColorScheme.getColorString( 1 ) + res.getString( "_i_P_" );
	}
}
