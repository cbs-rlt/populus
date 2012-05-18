package edu.umn.ecology.populus.model.dsoqc;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class DSOQCModel extends BasicPlotModel {
   DSOQCPanel dp;

   public Object getModelHelpText() {
      return "DSOQCHELP";
   }

   public DSOQCModel() {
      dp = new DSOQCPanel();
      this.setModelInput( dp );
   }

   public static String getModelName() {
      return "Directional Selection on a Quantitative Trait";
   }

   protected String getHelpId() {
      return "DSOQC.overview";
   }

   protected boolean isRepeatable() {
      return true;
   }

   protected boolean isSwitchable(){
      return true;
   }

   protected void switchOutput(){
      dp.switchOutputType();
      updateOutput();
   }
}
