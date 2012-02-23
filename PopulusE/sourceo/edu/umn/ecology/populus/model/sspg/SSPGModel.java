package edu.umn.ecology.populus.model.sspg;

import edu.umn.ecology.populus.core.*;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class SSPGModel extends BasicPlotModel {
   public SSPGModel() {
      this.setModelInput(new SSPGPanel());
   }

   public static String getModelName() {
      return "Stage-Structured Population Growth";
   }

   public Object getModelHelpText() {
      return "SSPGHELP";
   }

   protected String getHelpId() {
      return "SSPG.overview";
   }

}
