package edu.umn.ecology.populus.model.appdnb;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDNBModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnb.Res" );
   
   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }
   
   public Object getModelHelpText() {
      return "APPDNBHELP";
   }
   
   public APPDNBModel() {
      this.setModelInput( new APPDNBPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Nicholson_Bailey" );
   }
   
   protected String getHelpId() {
      return "appdnb.overview";
   }
}
