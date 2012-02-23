package edu.umn.ecology.populus.model.appdtpr;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDTPRModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdtpr.Res" );
   
   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }
   
   public Object getModelHelpText() {
      return "APPDTPRHELP";
   }
   
   public APPDTPRModel() {
      this.setModelInput( new APPDTPRPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Threshold_Predator" );
   }
   
   protected String getHelpId() {
      return "appdtpr.overview";
   }
}
