package edu.umn.ecology.populus.model.appdi;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDIModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdi.Res" );
   
   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }
   
   public Object getModelHelpText() {
      return "APPDIHELP";
   }
   
   public APPDIModel() {
      this.setModelInput( new APPDIPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Predator_Interference" );
   }
   
   protected String getHelpId() {
      return "appdpi.overview";
   }
}
