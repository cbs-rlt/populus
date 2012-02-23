package edu.umn.ecology.populus.model.appdfr;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;
import java.util.*;

public class APPDFRModel extends BasicPlotModel {
   static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdfr.Res" );

   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }

   public Object getModelHelpText() {
      return "APPDFRHELP";
   }

   public APPDFRModel() {
      this.setModelInput( new APPDFRPanel() );
   }

   public static String getModelName() {
      return res.getString( "Functional_Responses" );
   }

   protected String getHelpId() {
      return "appdfr.overview";
   }
}
