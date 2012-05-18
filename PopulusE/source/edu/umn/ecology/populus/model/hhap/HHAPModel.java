package edu.umn.ecology.populus.model.hhap;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class HHAPModel extends BasicPlotModel {
   public Object getModelHelpText() {
      return "HHAPHELP";
   }

   public HHAPModel() {
      this.setModelInput( new HHAPPanel() );
   }

   public static String getModelName() {
      return "Haploid Hosts and Parasites";
   }

   protected String getHelpId() {
      return "HHAP.overview";
   }
}
