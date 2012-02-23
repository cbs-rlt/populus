package edu.umn.ecology.populus.model.paqg;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class PAQGModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "PAQGHELP";
   }

   public PAQGModel() {
      this.setModelInput( new PAQGPanel() );
   }

   public static String getModelName() {
      return "Population & Quantitative Genetics";
   }

   protected String getHelpId() {
      return "PAQG.overview";
   }
}
