package edu.umn.ecology.populus.model.ihpi;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class IHPIParamInfo extends APPDProtoParamInfo {
   protected int modelType = 0;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.ihpi.Res" );

   public IHPIParamInfo( double n0, double p0, double F_, double I, double a, double k, double theta, double Th, double Ip_, int gens, int type, boolean vsTime ) {
      discProc = new IHPIProc( type, F_, I, a, k, theta, Th, Ip_ );
      this.gens = gens;
      plotType = vsTime ? NvsT : NvsN;
      modelType = type;
      initialConditions = new double[] {
         n0, p0
      };

      //mainCaption = "Insecticides in Host-Parasitoid Interactions";
      mainCaption = res.getString( "Discrete_Predator" );
      xCaption = vsTime ? res.getString( "Time_i_t_i_" ) : "<i> N </i>";
      yCaption = vsTime ? "<i>" + ColorScheme.getColorString( 0 ) + " N</>, " + ColorScheme.getColorString( 1 ) + "<i>P</>" : ColorScheme.getColorString( 1 ) + "<i>P</>";
   }
}
