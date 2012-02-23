package edu.umn.ecology.populus.model.dig;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class DIGModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.dig.Res" );
   
   public Object getModelHelpText() {
      return "DIGHELP";
   }
   
   public DIGModel() {
      this.setModelInput( new DIGPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Density_Independent" );
   }
   
   protected String getHelpId() {
      return "dipg.overview";
   }
}
