package edu.umn.ecology.populus.model.soasll;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class SOASLLParamInfo implements BasicPlot {
   private double wXX,wXx,wxx,wXY,wxY,pF,pM;
   private int gens;
   private static final String mCap = "Selection on a Sex-Linked Locus";
   private static final String xCap = "Generations (<b><i>t</>) ";
   private String yCap = "Allelic Frequency (  "+ColorScheme.getColorString(0)+"<i>p<sub>m</>, "+ColorScheme.getColorString(1)+"<i>p<sub>f</> )    ";

   public BasicPlotInfo getBasicPlotInfo() {
      double U,V,W,X,Y,qF,qM;
      boolean mDied = false, fDied = false;
      double[][][] points = new double[2][2][gens+1];

      for(int i=0; i<=gens && !mDied && !fDied; i++){
         points[0][0][i] = i;
         points[0][1][i] = pM;
         points[1][0][i] = i;
         points[1][1][i] = pF;

         qF = 1.0d - pF;
         qM = 1.0d - pM;

         U = wXX*pF*pM;
         V = wXx*(pF*qM + pM*qF);
         W = wxx*qF*qM;
         X = wXY*pF;
         Y = wxY*qF;

         if(U+V+W>0.0d)
            pF = (U+V/2.0d)/(U+V+W);
         else
            fDied = true;

         if(X+Y>0)
            pM = X/(X+Y);
         else
            mDied = true;
      }

      BasicPlotInfo bpi = new BasicPlotInfo(points,mCap,xCap,yCap);
      bpi.setIsDiscrete(true);
      bpi.setIsFrequencies(true);
      return bpi;
   }

   public SOASLLParamInfo(double wXX, double wXx, double wxx, double wXY, double wxY, double pM, double pF, int gens) {
      this.wXX = wXX;
      this.wXx = wXx;
      this.wxx = wxx;
      this.wXY = wXY;
      this.wxY = wxY;
      this.pF = pF;
      this.pM = pM;
      this.gens = gens;
   }
}
