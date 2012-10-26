package edu.umn.ecology.populus.model.mnp;

import edu.umn.ecology.populus.plot.*;

public class MNPModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "MNPHELP";
   }

   public MNPModel() {
      this.setModelInput( new MNPPanel() );
   }

   public static String getModelName() {
      return "Multiple Niche Polymorphism";
   }

   protected String getHelpId() {
      return "MNP.overview";
   }
}
