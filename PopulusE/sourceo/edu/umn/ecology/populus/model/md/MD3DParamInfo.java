package edu.umn.ecology.populus.model.md;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.awt.Color;
import java.util.*;

public class MD3DParamInfo extends APPD3DProtoParamInfo {
   public static final int NvsT = 1;
   public static final int NvsN = 2;
   protected double time;
   protected double x, y, z;
   protected DiscreteProc discProc = null;
   protected String xCaption = null;
   protected String yCaption = null;
   protected int numVars;
   protected int plotType = 0;
   protected double[] initialConditions = null;
   protected String mainCaption = null;
   protected String zCaption = null;
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.md.Res" );
   Integrator ig = null;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      initialConditions = new double[3];
      initialConditions[0] = x;
      initialConditions[1] = y;
      initialConditions[2] = z;
      int equations = initialConditions.length;
      double[][][] points = new double[1][3][];
      double[] xlist;
      double[][] ylists;
      int size, i;
      if( gens < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.integrate( initialConditions, 0.0, time );
      xlist = ig.getX();
      ylists = ig.getY();
      size = xlist.length;
      points[0][0] = ylists[2];
      points[0][1] = ylists[0];
      points[0][2] = ylists[1];
      bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption, zCaption );
      return bp;
   }

   public MD3DParamInfo( int modelType, double time, double X, double Y, double Z, double a, double b,
                         double alpha, double beta, double muA, double muP, double lambda, double k,
                         double gamma, double sigma, double teta ) {
      ig = new Integrator( new MDDeriv( modelType, a, b, alpha, beta, muA, muP, lambda, k, gamma, sigma, teta) );
      this.x = X;
      this.y = Y;
      this.z = Z;
      this.time = time;
      numVars = 3;
      mainCaption = res.getString( "Infectious" );
      //xCaption = res.getString( "Infective_Stage_W_" );
      yCaption = res.getString( "Host_N_" );
      xCaption = (modelType == MDParamInfo.HPWDD) ? res.getString("Infective_Stage_W_"):res.getString("Arrested_Parasite_");
      zCaption = res.getString( "Parasite_P_" );
   }
}