package edu.umn.ecology.populus.model.fdsess;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class FDSESSModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "FDSESSHELP";
   }

   public FDSESSModel() {
      this.setModelInput( new FDSESSPanel() );
   }

   public static String getModelName() {
      return "Frequency-Dependent Selection (ESS Model)";
   }

   protected String getHelpId() {
      return "FDSESS.overview";
   }
}
