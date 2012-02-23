package edu.umn.ecology.populus.model.eov;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;
import java.util.*;

public class EOVModel extends BasicPlotModel {
   static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.eov.Res" );

   public Object getModelHelpText() {
      return "EOVHELP";
   }

   public EOVModel() {
      this.setModelInput( new EOVPanel() );
   }

   public static String getModelName() {
      return res.getString( "Infectious" );
   }

   protected String getHelpId() {
      return "eov.overview";
   }
}
