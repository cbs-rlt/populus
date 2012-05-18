package edu.umn.ecology.populus.model.intradgs;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class INTRADGSModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "INTRADGSHELP";
   }

   public INTRADGSModel() {
      this.setModelInput( new INTRADGSPanel() );
   }

   public static String getModelName() {
      return "Intrademic Group Selection";
   }

   protected String getHelpId() {
      return "INTRADGS.overview";
   }
}
