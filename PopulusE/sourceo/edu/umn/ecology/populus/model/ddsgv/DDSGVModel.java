package edu.umn.ecology.populus.model.ddsgv;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class DDSGVModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "DDSGVHELP";
   }

   public DDSGVModel() {
      this.setModelInput( new DDSGVPanel() );
   }

   public static String getModelName() {
      return "Density-Dependent Selection w/ Genetic Variation";
   }

   protected String getHelpId() {
      return "DDSGV.overview";
   }
}
