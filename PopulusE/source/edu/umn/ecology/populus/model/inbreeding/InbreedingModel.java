package edu.umn.ecology.populus.model.inbreeding;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class InbreedingModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "INBREEDINGHELP";
   }

   public InbreedingModel() {
      this.setModelInput( new InbreedingPanel() );
   }

   public static String getModelName() {
      return "Inbreeding";
   }

   protected String getHelpId() {
      return "inbreeding.overview";
   }
}