package edu.umn.ecology.populus.model.lvpptl;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class LVPPTLModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvpptl.Res" );
   
   public Object getModelHelpText() {
      return "CPPTLHELP";
   }
   
   public LVPPTLModel() {
      this.setModelInput( new LVPPTLPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Continuous_Predator" );
   }
   
   protected String getHelpId() {
      return "cpp.overview";
   }
}
