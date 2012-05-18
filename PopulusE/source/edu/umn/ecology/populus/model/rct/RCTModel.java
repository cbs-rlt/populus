package edu.umn.ecology.populus.model.rct;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class RCTModel extends BasicPlotModel {
   static ResourceBundle res = null;
   private static synchronized void initRes() {
      if (res == null)
         res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.rct.Res" );
   }

   public Object getModelHelpText() {
      return "RCTHELP";
   }

   public RCTModel() {
      this.setModelInput( new RCTPanel() );
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
      return "rct.overview";
   }
}
