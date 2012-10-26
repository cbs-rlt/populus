package edu.umn.ecology.populus.model.bp;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;

public class BPModel extends BasicPlotModel {
   static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.bp.Res" );

   public Object getModelHelpText() {
      return "BPHELP";
   }

   public BPModel() {
      this.setModelInput( new BPPanel() );
   }

   public static String getModelName() {
      return res.getString( "Bacterial" );
   }

   protected String getHelpId() {
      return "bp.overview";
   }
}
