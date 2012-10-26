package edu.umn.ecology.populus.model.soamal;

import edu.umn.ecology.populus.plot.*;

public class SOAMALModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "SOAMALHELP";
   }

   public SOAMALModel() {
      this.setModelInput( new SOAMALPanel() );
   }

   public static String getModelName() {
      return "Selection on a Multi-Allelic Locus";
   }

   protected String getHelpId() {
      return "SOAMAL.overview";
   }
}
