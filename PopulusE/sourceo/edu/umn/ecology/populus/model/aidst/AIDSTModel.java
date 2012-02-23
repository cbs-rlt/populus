package edu.umn.ecology.populus.model.aidst;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class AIDSTModel extends BasicPlotModel {
  // static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.aidsbasic.Res" );

   public Object getModelHelpText() {
      return "AIDSTHELP";
   }

   public AIDSTModel() {
      this.setModelInput( new AIDSTPanel() );
   }

   public static String getModelName() {
      return "AIDS: Therapy";
   }

   protected String getHelpId() {
      return "aidst.overview";
   }
}
