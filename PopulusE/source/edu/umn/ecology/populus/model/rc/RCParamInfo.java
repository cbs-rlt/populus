package edu.umn.ecology.populus.model.rc;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class RCParamInfo implements BasicPlot {
   //Model Types
   public static final int seasonal_11 = 4;
   public static final int seasonal_21 = 10;
   public static final int seasonal_22 = 11;
   public static final int equable_11 = 5;
   public static final int equable_21 = 6;
   public static final int equable_22 = 7;

   //Plot types
   public static final int nvst= 1;//n vs t
   public static final int phivsR = 2;//phi vs r1
   public static final int phivsR2= 4;//phi vs r2
   public static final int rvst= 8;//r vs t
   public static final int nandrvst= 16;//r,n vs t
   public static final int n2vsn1 = 32;//n2 vs n1

   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.rc.Res" );

   //public static final int freqvsS = 1;//Freq vs Season
   //public static final int freqvsTime = 2;//Freq vs time
   //public static final int r1vst= 13;//r1 vs t
   //public static final int r2vst= 14;//r2 vs t

   int numVars;

   int plotType, modelType;
   double n1, n2, r1, r2, d;
   double a11, a12, a21, a22, b11, b12, b21, b22;
   double time;
   int tlast, tI;
   int endindex;
   Integrator ig;
   Derivative der;

   String mCap;
   String capR1, capR2, capR1P, capR2P, capRBoth,
          capTime, capN1, capN2, capNBoth,
          capConc2, capConc3, capConc4;

   public BasicPlotInfo getBasicPlotInfo() {
      String yCaption;
      BasicPlotInfo bp = null;
      double[][][] points;
      double[] xlist;
      double[] rlist, r2list;
      double[] phi11, phi12, phi21, phi22;
      double[][] ylists;
      double[] initialConditions = new double[numVars];
      int sizeR1,sizeR2;
      initialConditions[0] = n1;
      initialConditions[1] = r1;
      if (numVars >= 3) {
         initialConditions[2] = n2;
         if (numVars == 4)
            initialConditions[3] = r2;
      }


      if( time < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }

      if ((modelType == RCParamInfo.equable_11) || (modelType == RCParamInfo.equable_21) || (modelType == RCParamInfo.equable_22)){
         ig.doIntegration( initialConditions, 0.0, time );
         xlist = ig.getX();
         ylists = ig.getY();
      }
      else{ //seasonal
         double curX = 0.0;
         Vector<double[]> vecX = new Vector<double[]>();
         Vector<double[][]> vecY = new Vector<double[][]>();

         do {
            //Use steady state
            ig.reset();
            ig.record.ss = true;
            ig.record.interval = false;
            ig.record.ssMinDur = 0.1; //Lars - This min dur is somewhat of a hack.
                                      //If we started at 0, the log graph wouldn't work.

            ig.doIntegration( initialConditions, curX, RunningTimePanel.STEADYSTATE);
            xlist = ig.getX();
            ylists = ig.getY();

            vecX.add(xlist);
            vecY.add(ylists);

            //set the new initial conditions
            initialConditions[0] = ylists[0][ylists[0].length - 1]*d;
            initialConditions[1] = r1;
            if (numVars > 2){
               initialConditions[2]=ylists[2][ylists[2].length - 1]*d;
               if (numVars == 4)
                  initialConditions[3]= r2;
            }
            curX = xlist[xlist.length - 1];
         } while (curX < time);

         //Add up all elements into a single array;
         int totalElts, cumIdx, idx, idx2, arrElts, cutOffIdx;
         arrElts = vecX.size();
         idx = arrElts - 1;
         cutOffIdx = Routines.binarySearchD(vecX.elementAt(idx), time);
         totalElts = 1 + cutOffIdx;
         for (idx--; idx >= 0 ; idx--) {
            totalElts += vecX.elementAt(idx).length;
         }

         xlist = new double[totalElts];
         ylists = new double[numVars][totalElts];
         double[] curArr;

         cumIdx = 0;
         for (idx = 0; idx < arrElts; idx++) {
            for (idx2 = 0; idx2 < numVars; idx2++) {
               curArr = vecY.elementAt(idx)[idx2];
               if (idx == arrElts - 1)
                  System.arraycopy(curArr, 0, ylists[idx2], cumIdx, cutOffIdx + 1);
               else
                  System.arraycopy(curArr, 0, ylists[idx2], cumIdx, curArr.length);
            }
            curArr = vecX.elementAt(idx);
            if (idx == arrElts - 1)
               System.arraycopy(curArr, 0, xlist, cumIdx, cutOffIdx + 1);
            else
               System.arraycopy(curArr, 0, xlist, cumIdx, curArr.length);
            cumIdx += curArr.length;
         }
      }

      switch(plotType){
         case phivsR:
         {
            // phi vs r1
            int sizish = (int) (r1 * 100) +1;

            rlist = new double[sizish];

            for (int i=0; i<= sizish - 1; i++){
               rlist[i] = (double)i/100;
            }

            sizeR1 = rlist.length;

            phi11 = new double[sizeR1];
            phi21 = new double[sizeR1];
            for (int i = 0; i<sizeR1; i++) {
               phi11[i] = rlist[i] * a11 / (b11 + rlist[i]);
               phi21[i] = rlist[i] * a21 / (b21 + rlist[i]);
            }


            if ((modelType ==RCParamInfo.equable_11) || (modelType == RCParamInfo.seasonal_11)){
               points = new double[1][2][];
               yCaption = res.getString( "Rate_of_Uptake1" );
            }
            else {
               points = new double[2][2][];
               yCaption = res.getString( "Rate_of_Uptake2" );
            }

            points[0][0] = rlist;
            points[0][1] = phi11;
            if (numVars >= 3){
               points[1][0] = rlist;
               points[1][1] = phi21;

            }
            bp = new BasicPlotInfo( points, mCap, capR1P, yCaption);
            bp.setYMin( 0.0 );
         }
            break;
         case phivsR2:
         {
            points = new double[2][2][];
            int sizish2 = (int) r2 * 100 +1;
            r2list = new double[sizish2];
            for (int i=0; i<=r2*100; i++){
               r2list[i] = (double)i/100;
            }
            sizeR2 = r2list.length;
            phi12 = new double [sizeR2];
            phi22 = new double [sizeR2];
            for (int i = 0; i<sizeR2; i++) {
               phi12[i] = r2list[i] * a12 / (b12 + r2list[i]);
               phi22[i] = r2list[i] * a22 / (b22 + r2list[i]);
            }

            points[0][0] = r2list;
            points[0][1] = phi12;
            points[1][0] = r2list;
            points[1][1] = phi22;
            yCaption = res.getString("Rate_of_Uptake3");
            bp = new BasicPlotInfo( points, mCap, capR2P, yCaption);
            bp.setYMin( 0.0 );
         }
            break;
         case nvst:
         {
            int i, n;
            n = (numVars<3) ? 1 : 2;
            points = new double[n][2][];
            for (i = 0; i < n; i++) {
               points[i][0] = xlist;
               points[i][1] = ylists[2*i];
            }
            bp = new BasicPlotInfo( points, mCap, capTime,
                                    (n==1)?capN1:capNBoth);
            bp.setYMin(0.0);
         }
            break;
         case rvst:
         {
            int i, n;
            n = (numVars<4) ? 1 : 2;
            points = new double[n][2][];
            for (i = 0; i < n; i++) {
               points[i][0] = xlist;
               points[i][1] = ylists[2*i+1];
            }
            points[0][0] = xlist;
            points[0][1] = ylists[1];
            bp = new BasicPlotInfo( points, mCap, capTime,
                                    (n==1)?capR1:capRBoth);
            bp.setYMin( 0.0 );
         }
            break;
         case nandrvst:
         {
            int i;
            points = new double[numVars][2][];
            for (i = 0; i < numVars; i++) {
               points[i][0] = xlist;
               points[i][1] = ylists[i];
            }
            switch (numVars) {
            default:
            edu.umn.ecology.populus.fileio.Logging.log("Bad numvars");
            case 2: yCaption = capConc2; break;
            case 3: yCaption = capConc3; break;
            case 4: yCaption = capConc4; break;
            }
            bp = new BasicPlotInfo( points, mCap, capTime, yCaption);
            bp.setYMin(0.0);
         }
            break;
         case n2vsn1:
         {
            points = new double[1][2][];
            points[0][0] = ylists[0];
            points[0][1] = ylists[2];
            bp = new BasicPlotInfo( points, mCap, capN1, capN2);
            bp.setYMin(0.0);
         }
            break;
      }
      return bp;
   }

   public RCParamInfo( int modelType, int plotType,  double time, /*time < 0 for steady state*/ double N1, double N2, double v, double w, double c1, double c2, double e11, double e12, double e21, double e22, double a11, double a12, double a21, double a22, double b11, double b12, double b21, double b22, double d, int tI ) {
      this.n1 = N1;
      this.n2 = N2;
      this.r1 = c1;
      this.r2 = c2;
      this.a11 = a11;
      this.a12 = a12;
      this.a22 = a22;
      this.a21 = a21;
      this.b11 = b11;
      this.b12 = b12;
      this.b22 = b22;
      this.b21 = b21;
      this.time = time;
      this.tI = tI;
      this.d = d;
      this.modelType = modelType;
      this.plotType = plotType;
      if ((modelType == RCParamInfo.equable_11) || (modelType == RCParamInfo.seasonal_11))
         numVars = 2;
      else{
         if ((modelType == RCParamInfo.equable_21) || (modelType == RCParamInfo.seasonal_21))
            numVars = 3;
         else
            numVars = 4;
      }
      der = new RCDeriv(modelType, time, v, w, c1, c2, e11, e12, e21, e22, a11, a12, a21, a22, b11, b12, b21, b22, d, tI);
      ig = new Integrator( der );
      mCap = res.getString( "Resource_competition" );
      String n1 = ColorScheme.getColorString(0) + "<b><i>n</i></b><sub>1</sub></font>";
      String r1P = "<b><i>r</i></b><sub>1</sub>";
      String r1 = ColorScheme.getColorString(0) + r1P + "</font>";
      String r1T = ColorScheme.getColorString(1) + "<b><i>r</i></b><sub>1</sub></font>";
      String n2 = ColorScheme.getColorString(1) + "<b><i>n</i></b><sub>2</sub></font>";
      String n2T = ColorScheme.getColorString(2) + "<b><i>n</i></b><sub>2</sub></font>";
      String r2P = "<b><i>r</i></b><sub>2</sub>";
      String r2 = ColorScheme.getColorString(1) + r2P + "</font>";
      String r2T = ColorScheme.getColorString(3) + "<b><i>r</i></b><sub>2</sub></font>";
      String resource = res.getString("ResourceS");
      String consumer = res.getString("ConsumerS");
      String conc = res.getString("Concentration");
      capR1 = resource + " ( " + r1 + " )";
      capR2 = resource + " ( " + r2 + " )";
      capR1P = resource + " ( " + r1P + " )";
      capR2P = resource + " ( " + r2P + " )";
      capRBoth = resource + " ( " + r1 + ", " + r2 + " )";
      capN1 = consumer + " ( " + n1 +  " )";
      capN2 = consumer + " ( " + n2 +  " )";
      capNBoth = consumer + " ( " + n1 + ", " + n2 + " )";
      capTime = res.getString( "Time_b_i_t_" );
      capConc2 = conc + " ( " + n1 + ", " + r1T + " )";
      capConc3 = conc + " ( " + n1 + ", " + r1T + ", " + n2T + " )";
      capConc4 = conc + " ( " + n1 + ", " + r1T + ", " + n2T + ", " + r2T + " )";
   }
}
