package edu.umn.ecology.populus.model.fdsdm;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class FDSDMModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "FDSDMHELP";
   }

   public FDSDMModel() {
      this.setModelInput( new FDSDMPanel() );
   }

   public static String getModelName() {
      return "Frequency-Dependent Selection (Diploid Model)";
   }

   protected String getHelpId() {
      return "FDSDM.overview";
   }
}
