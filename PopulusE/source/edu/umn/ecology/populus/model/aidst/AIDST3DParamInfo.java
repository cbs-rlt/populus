package edu.umn.ecology.populus.model.aidst;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import edu.umn.ecology.populus.math.*;

public class AIDST3DParamInfo extends APPD3DProtoParamInfo {
   public static final int NvsT = 1;
   public static final int NvsN = 2;
   protected double time;
   protected double x, y, w, z;
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
      initialConditions[2] = z;
      double[][][] points = new double[1][3][];
      double[][] ylists;
      if( gens < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.integrate( initialConditions, 0.0, time );
      ylists = ig.getY();
      points[0][0] = ylists[2];
      points[0][1] = ylists[0];
      points[0][2] = ylists[1];
      bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption, vCaption );
      return bp;
     }

     public AIDST3DParamInfo( double time, /*time < 0 for steady state*/ double X, double Y, double W, double Z,
                              double lambda, double d, double a, double beta, double b, double c, double q,
                              double h, double s, double p, double[][] intervals) {
        ig = new Integrator( new AIDSTDeriv( lambda, d, a,beta, b, c, q, h, s, p,intervals) );
      this.x = X;
      this.y = Y;

      this.w=W;
      this.z=Z;
      this.time = time;
      numVars = 3;
      mainCaption = "Viral Dynamics" ;
      xCaption = "Uninfected Cells (<i>x</i>)" ;
      yCaption = "Infected Cells (<i>y</i>)" ;
      vCaption = "Free Viral Particles (<i>v</i>)" ;
   }
}