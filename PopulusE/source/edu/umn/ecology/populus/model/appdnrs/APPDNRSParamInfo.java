/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnrs;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class APPDNRSParamInfo extends APPDProtoParamInfo {
   public static final int DDGrowth = 1;
   public static final int DIGrowth = 1; //one of these is supposed to be 0?
   protected int modelType = 0;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnrs.Res" );

   public APPDNRSParamInfo( double n0, double p0, double lambda, double a, double k, double K_, int gens, boolean independent, boolean vsTime ) {
      discProc = new APPDNRSProc( independent, lambda, a, k, K_ );
      this.gens = gens;
      plotType = vsTime ? NvsT : NvsN;
      modelType = independent ? DIGrowth : DDGrowth;
      initialConditions = new double[] {
         n0, p0
      };
      mainCaption = res.getString( "Discrete_Predator" );
      xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : "<i> N </i>";
      yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + " N</>, " + ColorScheme.getColorString( 1 ) + "<i>P</>" : ColorScheme.getColorString( 1 ) + "<i>P</>";
   }
}
