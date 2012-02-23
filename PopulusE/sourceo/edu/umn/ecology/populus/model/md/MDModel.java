package edu.umn.ecology.populus.model.md;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class MDModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.md.Res" );

   public Object getModelHelpText() {
      return "MDHELP";
   }

   public MDModel() {
      this.setModelInput( new MDPanel() );
   }

   public static String getModelName() {
      return res.getString( "Infectious" );
   }

   protected String getHelpId() {
      return "md.overview";
   }
}
