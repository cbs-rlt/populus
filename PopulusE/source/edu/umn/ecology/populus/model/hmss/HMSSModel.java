package edu.umn.ecology.populus.model.hmss;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class HMSSModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "HMSSHELP";
   }

   public HMSSModel() {
      this.setModelInput( new HMSSPanel() );
   }

   public static String getModelName() {
      return "Handicap Sexual Selection";
   }

   protected String getHelpId() {
      return "HMSS.overview";
   }
}
