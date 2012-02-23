package edu.umn.ecology.populus.model.tp;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class TPModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.tp.Res" );

   public Object getModelHelpText() {
      return "TPHELP";
   }

   public TPModel() {
      this.setModelInput( new TPPanel() );
   }

   public static String getModelName() {
      return res.getString( "Bacterial" );
   }

   protected String getHelpId() {
      return "TP.overview";
   }
}
