/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.cp;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class CPModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.cp.Res" );

   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }

   public Object getModelHelpText() {
      return "CPHELP";
   }

   public CPModel() {
      this.setModelInput( new CPPanel() );
   }

   public static String getModelName() {
      return res.getString( "Competing_Predators" );
   }

   protected String getHelpId() {
      return "appdcp.overview";
   }
}
