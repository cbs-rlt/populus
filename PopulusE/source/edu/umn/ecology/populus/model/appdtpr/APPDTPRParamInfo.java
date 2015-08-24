/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdtpr;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class APPDTPRParamInfo extends APPDProtoParamInfo {
   protected int modelType = 1;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdtpr.Res" );

   public APPDTPRParamInfo( double n0, double p0, double K, double r, double a, double c, double b, int gens, boolean vsTime ) {
      discProc = new APPDTPRProc( K, r, a, c, b );
      this.gens = gens;
      plotType = vsTime ? NvsT : NvsN;
      initialConditions = new double[] {
         n0, p0
      };
      mainCaption = res.getString( "Discrete_Predator" );
      xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : "<i> N </i>";
      yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + " N</>, " + ColorScheme.getColorString( 1 ) + "<i>P</>" : ColorScheme.getColorString( 1 ) + "<i>P</>";
   }
}
