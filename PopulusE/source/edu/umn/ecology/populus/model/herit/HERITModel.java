package edu.umn.ecology.populus.model.herit;

import edu.umn.ecology.populus.plot.*;

public class HERITModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "HERITHELP";
   }

   public HERITModel() {
      this.setModelInput( new HERITPanel() );
   }

   public static String getModelName() {
      return "Heritability";
   }

   protected String getHelpId() {
      return "HERIT.overview";
   }
}
