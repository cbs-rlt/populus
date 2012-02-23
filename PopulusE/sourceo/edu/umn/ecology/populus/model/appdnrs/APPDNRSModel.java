package edu.umn.ecology.populus.model.appdnrs;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDNRSModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnrs.Res" );
   
   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }
   
   public Object getModelHelpText() {
      return "APPDNRSHELP";
   }
   
   public APPDNRSModel() {
      this.setModelInput( new APPDNRSPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Non_Random_Searching" );
   }
   
   protected String getHelpId() {
      return "appdnrs.overview";
   }
}
