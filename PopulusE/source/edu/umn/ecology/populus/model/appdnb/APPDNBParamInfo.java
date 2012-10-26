package edu.umn.ecology.populus.model.appdnb;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class APPDNBParamInfo extends APPDProtoParamInfo {
   public static final int DDGrowth = 1;
   public static final int DIGrowth = 1;
   protected int modelType = 0;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnb.Res" );

   public APPDNBParamInfo( double n0, double p0, double lambda, double a, double r, double K, int gens, boolean independent, boolean vsTime ) {
      discProc = new APPDNBProc( independent, a, lambda, K, r, a );
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
