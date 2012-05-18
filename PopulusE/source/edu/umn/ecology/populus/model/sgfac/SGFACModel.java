package edu.umn.ecology.populus.model.sgfac;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class SGFACModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "SGFACHELP";
   }

   public SGFACModel() {
      this.setModelInput( new SGFACPanel() );
   }

   public static String getModelName() {
      return "Selection on Clinal Stepping Stones";
   }

   protected String getHelpId() {
      return "SGFAC.overview";
   }

   protected boolean isRepeatable() {
      return true;
   }
}
