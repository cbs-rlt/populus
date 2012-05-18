package edu.umn.ecology.populus.model.rc;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class RCModel extends BasicPlotModel {
   static ResourceBundle res = null;
   private static synchronized void initRes() {
      if (res == null)
         res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.rc.Res" );
   }

   public Object getModelHelpText() {
      return "RCHELP";
   }

   public RCModel() {
      this.setModelInput( new RCPanel() );
   }
   public String getThisModelInputName() {
      initRes();
      return res.getString( "Resource_competition" );
   }
   public static String getModelName() {
      initRes();
      return res.getString( "Resource" );
   }

   protected String getHelpId() {
      return "rc.overview";
   }
}
