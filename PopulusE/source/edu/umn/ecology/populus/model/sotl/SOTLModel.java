package edu.umn.ecology.populus.model.sotl;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class SOTLModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "SOTLHELP";
   }

   public SOTLModel() {
      this.setModelInput( new SOTLPanel() );
   }

   public static String getModelName() {
      return "Two-Locus Selection";
   }

   protected String getHelpId() {
      return "SOTL.overview";
   }
}
