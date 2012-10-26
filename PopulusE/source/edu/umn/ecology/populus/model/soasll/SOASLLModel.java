package edu.umn.ecology.populus.model.soasll;

import edu.umn.ecology.populus.plot.*;

public class SOASLLModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "SOASLLHELP";
   }

   public SOASLLModel() {
      this.setModelInput( new SOASLLPanel() );
   }

   public static String getModelName() {
      return "Selection on a Sex-Linked Locus";
   }

   protected String getHelpId() {
      return "SOASLL.overview";
   }
}
