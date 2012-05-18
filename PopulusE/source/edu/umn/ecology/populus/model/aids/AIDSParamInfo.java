package edu.umn.ecology.populus.model.aids;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.plot.plotshapes.EventLine;
import edu.umn.ecology.populus.model.appd.APPDProtoParamInfo;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;
import java.awt.Color;

public class AIDSParamInfo implements BasicPlot {
   public static final int YVvsT      = 1;
   public static final int RvsT       = 2;
   public static final int DvsT       = 3;
   public static final int VIvsT      = 4;
   public static final int ZXIvsT     = 5;
   public static final int VvsT       = 6;

   protected String mainCaption = null;
   protected String xCaption = null;
   protected String yCaption = null;
   protected String zCaption = null;
   protected double[] initialConditions = null;
   protected Derivative der;
   protected double runTime;
   protected int plotType = YVvsT;
   protected double dt;
   protected String[] vsTimeChars;
   protected Vector VvsTSaved;
   protected double[] xlist;
   protected double[][] ylists;
   protected double[] vt, xt, r, R, D;
   private final String xCapTime = "Time";
   private final String yCapPopDensity = "Population Density   ";
   private final String yCapAveR = "Average Virus Replication Rate   ";
   private final String yCapViralDiversityIdx = "Viral Diversity Index (1/<i>D</i>)   ";
   private final String yCapViralStrainAbundance = "Viral Strain Abundance   ";
   //private final String yCap5 = ColorScheme.getColorString(0)+ "Cross-Reactive</>"+" and"+ColorScheme.getColorString(1)+" S</>"+ColorScheme.getColorString(2)+"t</>"+ColorScheme.getColorString(3)+"r</>"+ColorScheme.getColorString(4)+"a</>"+ColorScheme.getColorString(5)+"i</>"+ColorScheme.getColorString(6)+"n</>"+ColorScheme.getColorString(7)+"-</>"+ColorScheme.getColorString(8)+"S</>"+ColorScheme.getColorString(9)+"p</>"+ColorScheme.getColorString(10)+"e</>"+ColorScheme.getColorString(11)+"c</>"+ColorScheme.getColorString(12)+"i</>"+ColorScheme.getColorString(13)+"f</>"+ColorScheme.getColorString(14)+"i</>"+ColorScheme.getColorString(15)+"c</>"+ " Cell Densities";
   //private final String yCap5= ColorScheme.getColorString(0)+ "Cross-Reactive</>"+" and Strain-Specific Cell Densities    ";
   private final String yCap5= ColorScheme.getColorString(0)+ "<i><b>z</>"+" and <i><b>x<sub>i</> Cell Densities    ";
   private final String yCapRun = "Run";
   private final String capTotalVirusDensity = "Total Virus Density   ";

   protected void generateNewValues() {
      Integrator ig;
      int equations = initialConditions.length;
      int size, last;

      ig = new Integrator( der );
      ig.record.usePostDerivative = true;
      ig.record.h = ig.record.hlast = dt;
      ig.record.mode = RungeKuttaRec.EULER;
      if( runTime < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.record.nonNegOnly = true;
      ig.integrate( initialConditions, 0.0, runTime );
      xlist = ig.getX();
      ylists = ig.getY();

      size = xlist.length;
      xt = new double[size];
      vt = new double[size];
      R = new double[size];
      D = new double[size];
      r = ((AIDSDeriv)der).r;

      for(int i=2; i<ylists.length; i++){
         for(int j=0; j<ylists[i].length; j++)
            if(i%2 == 0){
               vt[j] += ylists[i][j];
               D[j] += ylists[i][j]*ylists[i][j];
               R[j] += ylists[i][j]*r[(i-2)/2];
            } else
               xt[j] += ylists[i][j];

      }

      for(int i=0; i<R.length; i++){
         R[i] /= vt[i];
         D[i] = vt[i]*vt[i]/D[i];
      }

      //Save VvsTSaved
      if (VvsTSaved != null) {
         double[][] currentRun = new double[3][];
         currentRun[0] = xlist;
         currentRun[1] = vt;
         currentRun[2] = new double[xt.length];
         VvsTSaved.add(currentRun);
      }
   }

   public BasicPlotInfo getBasicPlotInfo() {
      double[][][] points = null;
      BasicPlotInfo bp;
      int size, factor;

      size = xlist.length;
      factor = (int)(xlist.length/xlist[size-1]);
      bp = new BasicPlotInfo();
      switch (plotType) {
         case YVvsT:
            points = new double[2][2][];
            points[0][0] = xlist;
            points[1][0] = xlist;
            points[0][1] = ylists[AIDSDeriv.kY];
            points[1][1] = vt;
            vsTimeChars = new String[] {"Y","V"};
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,yCapPopDensity);
            break;
         case RvsT:
            points = new double[1][2][];
            points[0][0] = xlist;
            points[0][1] = R;
            vsTimeChars = new String[] {"R"};
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,yCapAveR);
            break;
         case DvsT:
            points = new double[1][2][];
            points[0][0] = xlist;
            points[0][1] = D;
            vsTimeChars = new String[] {"D"};
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,yCapViralDiversityIdx);
            break;
         case VIvsT:
            //ylists[population][time]
            points = new double[(ylists.length-2)/2][2][];
            for(int i=2; i<ylists.length; i+=2){
               points[(i-2)/2][0] = (double[])xlist.clone();
               points[(i-2)/2][1] = (double[])ylists[i].clone();
               if(i>40){
                  points[(i-2)/2] = Routines.pruneArray(points[(i-2)/2], factor/2,false);
               }else if(i>10){
                  points[(i-2)/2] = Routines.pruneArray(points[(i-2)/2], factor/3,false);
               } else {
                  points[(i-2)/2] = Routines.pruneArray(points[(i-2)/2], 3, false);
               }
            }
            vsTimeChars = new String[points.length];
            for(int i=0; i<vsTimeChars.length; i++)
               vsTimeChars[i] = "v"+i;
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,yCapViralStrainAbundance);
            break;
         case ZXIvsT:
            /*the difficulty here is that there are so many points to graph
            that the graphing utility slows down substantially. to avoid this,
            i've added a pruning utility that will only send the BasicPlotInfo
            some of the calculated points. it is assumed that later strains don't
            look as interesting, so they don't get as many points*/
            points = new double[ylists.length/2][2][];
            for(int i=1; i<ylists.length; i+=2){
               points[i/2][0] = (double[])xlist.clone();
               points[i/2][1] = (double[])ylists[i].clone();
               if(i>40){
                  points[i/2] = Routines.pruneArray(points[i/2], factor/2, true);
               }else if(i>10){
                  points[i/2] = Routines.pruneArray(points[i/2], factor/3, true);
               } else {
                  points[i/2] = Routines.pruneArray(points[i/2], 3, true);
               }
            }
            vsTimeChars = new String[points.length];
            vsTimeChars[0] = "Z";
            for(int i=1; i<vsTimeChars.length; i++)
               vsTimeChars[i] = "x"+i;
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,yCap5);
            break;
         case VvsT:
            points = new double[1][2][];
            points[0][0] = xlist;
            points[0][1] = vt;
            bp = new BasicPlotInfo(points,mainCaption,xCapTime,capTotalVirusDensity);
            break;
         default:
            edu.umn.ecology.populus.fileio.Logging.log("Invalid plot option: " + plotType);
            break;
      };

      bp.clearInnerCaptions();
      AIDSDeriv d = (AIDSDeriv) der;
      if (d.getCD4EliminatedTime() > 0.0) {
         //EventLine.addEventMark(bp, 0, d.getCD4EliminatedTime(), d.getCD4EliminatedVal());
         bp.addInnerCaption("CD4+ eliminated", d.getCD4EliminatedTime(), d.getCD4EliminatedVal());
      }
      if (d.getVirusEliminatedTime() > 0.0) {
         //EventLine.addEventMark(bp, 0, d.getVirusEliminatedTime(), d.getVirusEliminatedVal());
         bp.addInnerCaption("Virus eliminated", d.getVirusEliminatedTime(), d.getVirusEliminatedVal());
      }
      if (d.getMaxStrainsTime() > 0.0) {
         //EventLine.addEventLine(bp, d.getMaxStrainsTime());
         bp.addInnerCaption("Max strains (" + d.getMaxStrainsVal() + ")",
                            d.getMaxStrainsTime(), 0.0 /* ??? */);
      }
      bp.vsTimeChars = vsTimeChars;
      if(plotType != ZXIvsT)
         bp.setColors(ColorScheme.colors);
      else{
         Color[] colors = new Color[ColorScheme.colors.length-1];
         System.arraycopy(ColorScheme.colors,1,colors,0,colors.length);
         for(int i=1; i<bp.getNumSeries(); i++)
            bp.setLineColor(i,colors[(i-1)%colors.length]);
         bp.setLineColor(0,ColorScheme.colors[0]);
      }

      /*this model potentially has a large data set, so after we've collected all we need,
      let's ensure the rubbish is taken care of*/
      System.gc();

      return bp;
   }

   public AIDSParamInfo(double v0, double vi0, double u, double ri, double rip, double Q, double y0,
          double K, double d, double kp, double k, double si, double pi, double dt, double runtime,
          int maxStrains, int plotType, long randSeed, Vector VvsTSaved) {
      der = new AIDSDeriv(v0, vi0,u,ri,rip,Q,K,d,kp,k,si,pi,dt,randSeed,maxStrains);
      initialConditions = new double[4];
      initialConditions[AIDSDeriv.kY] = y0;
      initialConditions[AIDSDeriv.kZ] = 0.0d;
      initialConditions[2] = v0;
      initialConditions[3] = 0.0d;
      runTime = runtime;
      this.plotType = plotType;
      this.dt = dt;
      this.VvsTSaved = VvsTSaved;

      mainCaption = "AIDS: Threshold Model";

      this.generateNewValues();
   }

   void setPlotType(int newPlotType) {
      this.plotType = newPlotType;
   }
}





