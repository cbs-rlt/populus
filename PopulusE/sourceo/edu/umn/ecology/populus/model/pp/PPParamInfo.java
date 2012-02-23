package edu.umn.ecology.populus.model.pp;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class PPParamInfo extends APPDProtoParamInfo {
   public static final int DDGrowth = 1; //one of these supposed to be 0?
   public static final int DIGrowth = 1;
   protected int modelType = 0;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.pp.Res" );

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = super.getBasicPlotInfo();
      bpi.vsTimeChars = new String[] {
         "X", "Y", "P"
      };
      return bpi;
   }

   public PPParamInfo( double X0, double Y0, double P0, boolean switching, double[] l, double[] aX, double[] g, double[] a, double m, double s, int gens, boolean vsTime ) {
      discProc = new PPProc( switching, l, aX, g, a, m, s );
      this.gens = gens;
      plotType = vsTime ? NvsT : NvsN;

      //modelType = linear ? DIGrowth : DDGrowth;
      initialConditions = new double[] {
         X0, Y0, P0
      };
      mainCaption = res.getString( "Discrete_Predator" );
      xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : "<i> N </i>";
      yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + " X</>, " + ColorScheme.getColorString( 1 ) + "<i>Y</>, " + ColorScheme.getColorString( 2 ) + "<i>P</>" : ColorScheme.getColorString( 1 ) + "<i>P</>";
   }
}
