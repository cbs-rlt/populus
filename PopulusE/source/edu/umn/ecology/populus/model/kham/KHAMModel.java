package edu.umn.ecology.populus.model.kham;

import edu.umn.ecology.populus.plot.*;

public class KHAMModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "KHAMHELP";
   }

   public KHAMModel() {
      this.setModelInput( new KHAMPanel() );
   }

   public static String getModelName() {
      return "Arbitrary Sexual Selection";
   }

   protected String getHelpId() {
      return "KHAM.overview";
   }
}
