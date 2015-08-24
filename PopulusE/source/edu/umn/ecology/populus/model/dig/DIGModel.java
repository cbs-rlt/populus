/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dig;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class DIGModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.dig.Res" );
   
   public Object getModelHelpText() {
      return "DIGHELP";
   }
   
   public DIGModel() {
      this.setModelInput( new DIGPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Density_Independent" );
   }
   
   protected String getHelpId() {
      return "dipg.overview";
   }
}
