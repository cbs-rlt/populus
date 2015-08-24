/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;
import edu.umn.ecology.populus.plot.*;

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
