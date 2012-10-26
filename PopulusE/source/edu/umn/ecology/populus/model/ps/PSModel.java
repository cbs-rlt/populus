package edu.umn.ecology.populus.model.ps;
import edu.umn.ecology.populus.plot.*;

public class PSModel extends BasicPlotModel {
   PSPanel psp;

   public Object getModelHelpText() {
      return "PSHELP";
   }

   public PSModel() {
      psp = new PSPanel();
      setModelInput( psp );
   }

   public static String getModelName() {
      return "Population Structure";
   }

   protected String getHelpId() {
      return "ps.overview";
   }

   protected boolean isRepeatable() {
      return true;
   }
   protected boolean isSwitchable(){
      return true;
   }
   protected void switchOutput(){
      psp.switchOutputType();
      updateOutput();
   }
}
