package edu.umn.ecology.populus.model.ie;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class IEModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "IEHELP";
   }

   public String getThisModelInputName() {
      return "Interaction Engine";
   }

   public IEModel() {
      this.setModelInput( new IEPanel() );
   }

   public static String getModelName() {
      return "Interaction Engine";
   }

   protected String getHelpId() {
      return "ie.overview";
   }
}
