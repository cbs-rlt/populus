package edu.umn.ecology.populus.model.imd;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import edu.umn.ecology.populus.math.*;
import java.util.*;

public class IMD3DParamInfo extends APPD3DProtoParamInfo {
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
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.imd.Res" );
   Integrator ig = null;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      initialConditions = new double[3];
      initialConditions[0] = x;
      initialConditions[1] = y;
      initialConditions[2] = z;
      double[][][] points = new double[1][3][];
      double[] xlist;
      double[][] ylists;
      if( gens < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.integrate( initialConditions, 0.0, time );
      xlist = ig.getX();
      ylists = ig.getY();
      points[0][0] = ylists[2];
      points[0][1] = ylists[0];
      points[0][2] = ylists[1];
      bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption, zCaption );
      return bp;
   }

   public IMD3DParamInfo( int modelType, double time, /*time < 0 for steady state*/ double X, double Y, double Z, double a, double b, double alpha, double beta, double nu, double gamma ) {
      ig = new Integrator( new IMDDeriv( modelType, a, b, alpha, beta, nu, gamma ) );
      this.x = X;
      this.y = Y;
      this.z = Z;
      this.time = time;
      numVars = 3;
      mainCaption = res.getString( "Infectious" );
      xCaption = res.getString( "Immune_Host_R_" );
      yCaption = res.getString( "Susceptable_Host_S_" );
      zCaption = res.getString( "Infected_Host_I_" );
   }
}