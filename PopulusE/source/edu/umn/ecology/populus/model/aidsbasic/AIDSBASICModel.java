package edu.umn.ecology.populus.model.aidsbasic;
import edu.umn.ecology.populus.plot.*;

public class AIDSBASICModel extends BasicPlotModel {
  // static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.aidsbasic.Res" );

   public Object getModelHelpText() {
      return "AIDSBASICHELP";
   }

   public AIDSBASICModel() {
      this.setModelInput( new AIDSBASICPanel() );
   }

   public static String getModelName() {
      return "AIDS: Viral Dynamics";
   }

   protected String getHelpId() {
      return "aidsbasic.overview";
   }
}
