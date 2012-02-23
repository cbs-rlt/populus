package edu.umn.ecology.populus.model.lvc;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class LVCModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvc.Res" );
   
   public Object getModelHelpText() {
      return "LVCHELP";
   }
   
   public LVCModel() {
      this.setModelInput( new LVCPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Lotka_Volterra" );
   }
   
   protected String getHelpId() {
      return "lvc.overview";
   }
}
