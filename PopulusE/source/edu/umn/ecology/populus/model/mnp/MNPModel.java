/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.mnp;

import edu.umn.ecology.populus.plot.*;

public class MNPModel extends BasicPlotModel {

   public Object getModelHelpText() {
      return "MNPHELP";
   }

   public MNPModel() {
      this.setModelInput( new MNPPanel() );
   }

   public static String getModelName() {
      return "Multiple Niche Polymorphism";
   }

   protected String getHelpId() {
      return "MNP.overview";
   }
}
