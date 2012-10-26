package edu.umn.ecology.populus.model.interdgs;

import edu.umn.ecology.populus.plot.*;

public class INTERDGSModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "INTERDGSHELP";
   }

   public INTERDGSModel() {
      this.setModelInput( new INTERDGSPanel() );
   }

   public static String getModelName() {
      return "Interdemic Group Selection";
   }

   protected String getHelpId() {
      return "INTERDGS.overview";
   }
}
