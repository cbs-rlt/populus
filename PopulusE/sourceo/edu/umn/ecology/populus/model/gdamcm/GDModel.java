package edu.umn.ecology.populus.model.gdamcm;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class GDModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.gdamcm.Res" );
   GDPanel gdp;

   public Object getModelHelpText() {
      return "GDHELP";
   }

   public GDModel() {
      gdp = new GDPanel();
      setModelInput( gdp );
   }

   public static String getModelName() {
      return "Genetic Drift";
   }

   protected String getHelpId() {
      if(gdp == null || gdp.switcherTP.getSelectedIndex()==0)
         return "gdrift.overview";
      else
         return "gdriftm.overview";
   }

   protected boolean isRepeatable() {
      return true;
   }
}
