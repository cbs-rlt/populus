package edu.umn.ecology.populus.model.imd;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;

public class IMDModel extends BasicPlotModel {
   static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.imd.Res" );

   public Object getModelHelpText() {
      return "IMDHELP";
   }

   public IMDModel() {
      this.setModelInput( new IMDPanel() );
   }

   public static String getModelName() {
      return res.getString( "Infectious" );
   }

   protected String getHelpId() {
      return "imd.overview";
   }
}
