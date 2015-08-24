/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.inbreeding;

import edu.umn.ecology.populus.plot.*;

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