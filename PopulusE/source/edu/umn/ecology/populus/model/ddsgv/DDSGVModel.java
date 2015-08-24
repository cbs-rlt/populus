/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ddsgv;

import edu.umn.ecology.populus.plot.*;

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
