package edu.umn.ecology.populus.model.aids;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class AIDSModel extends BasicPlotModel {
   public String getThisModelInputName() {
      return "AIDS: Threshold Model";
   }

   public Object getModelHelpText() {
      return "AIDSHELP";
   }

   public AIDSModel() {
      this.setModelInput( new AIDSPanel() );
   }

   public static String getModelName() {
      return "AIDS: Threshold Model";
   }

   protected String getHelpId() {
      return "aids.overview";
   }
}
