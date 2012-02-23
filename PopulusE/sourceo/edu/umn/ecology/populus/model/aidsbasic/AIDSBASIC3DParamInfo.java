package edu.umn.ecology.populus.model.aidsbasic;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.awt.Color;
import java.util.*;

public class AIDSBASIC3DParamInfo extends APPD3DProtoParamInfo {
   public static final int NvsT = 1;
   public static final int NvsN = 2;
   protected double time;
   protected double x, y, v;
   protected DiscreteProc discProc = null;
   protected String xCaption = null;
   protected String yCaption = null;
   protected int numVars;
   protected int plotType = 0;
   protected double[] initialConditions = null;
   protected String mainCaption = null;
   protected String vCaption = null;
   Integrator ig = null;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      initialConditions = new double[3];
      initialConditions[0] = x;
      initialConditions[1] = y;
      initialConditions[2] = v;
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
      bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption, vCaption );
      return bp;
     }

   public AIDSBASIC3DParamInfo( double time, /*time < 0 for steady state*/ double X, double Y, double V, double lambda, double d, double k, double a, double beta, double u  ) {
      ig = new Integrator( new AIDSBASICDeriv( lambda, d, k, a,beta,u) );
      this.x = X;
      this.y = Y;
      this.v = V;
      this.time = time;
      numVars = 3;
      mainCaption = "Viral Dynamics" ;
      xCaption = "Uninfected Cells (<i>x</i>)" ;
      yCaption = "Infected Cells (<i>y</i>)" ;
      vCaption = "Free Viral Particles (<i>v</i>)" ;
   }
}