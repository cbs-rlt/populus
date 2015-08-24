/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdfr;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;

public class APPDFRModel extends BasicPlotModel {
   static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdfr.Res" );

   public String getThisModelInputName() {
      return res.getString( "Discrete_Predator" );
   }

   public Object getModelHelpText() {
      return "APPDFRHELP";
   }

   public APPDFRModel() {
      this.setModelInput( new APPDFRPanel() );
   }

   public static String getModelName() {
      return res.getString( "Functional_Responses" );
   }

   protected String getHelpId() {
      return "appdfr.overview";
   }
}
