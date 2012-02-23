package edu.umn.ecology.populus.model.prm;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class PRMModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.prm.Res" );

   public Object getModelHelpText() {
      return "PRMHELP";
   }

   public PRMModel() {
      this.setModelInput( new PRMPanel() );
   }

   public static String getModelName() {
      return res.getString( "PRM" );
   }

   protected String getHelpId() {
      return "prm.overview";
   }
}
