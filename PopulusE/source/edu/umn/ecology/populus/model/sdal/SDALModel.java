package edu.umn.ecology.populus.model.sdal;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class SDALModel extends BasicPlotModel {
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.sdal.Res" );
   
   public Object getModelHelpText() {
      return "SDALHELP";
   }
   
   public SDALModel() {
      this.setModelInput( new SDALPanel() );
   }
   
   public static String getModelName() {
      return res.getString( "Autosomal_Selection" );
   }
   
   protected String getHelpId() {
      return "sdal.overview";
   }
}
