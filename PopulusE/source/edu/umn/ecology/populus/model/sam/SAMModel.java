package edu.umn.ecology.populus.model.sam;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class SAMModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "SAMHELP";
   }

   public SAMModel() {
      this.setModelInput( new SAMPanel() );
   }

   public static String getModelName() {
      return "Selection & Mutation";
   }

   protected String getHelpId() {
      return "SAM.overview";
   }
}
