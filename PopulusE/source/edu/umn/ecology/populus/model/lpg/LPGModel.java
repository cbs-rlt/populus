package edu.umn.ecology.populus.model.lpg;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class LPGModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lpg.Res" );
   
   public Object getModelHelpText() {
      return "LPGHELP";
   }
   
   public LPGModel() {
      this.setModelInput( new LPGPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Density_Dependent" );
   }
   
   protected String getHelpId() {
      return "ddpg.overview";
   }
}
