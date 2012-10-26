package edu.umn.ecology.populus.model.das;

import edu.umn.ecology.populus.plot.*;

public class DASModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "DASHELP";
   }

   public DASModel() {
      this.setModelInput( new DASPanel() );
   }

   public static String getModelName() {
      return "Drift and Selection";
   }

   protected String getHelpId() {
      return "das.overview";
   }
}