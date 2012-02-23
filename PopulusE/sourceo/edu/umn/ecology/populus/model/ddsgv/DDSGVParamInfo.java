package edu.umn.ecology.populus.model.ddsgv;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import java.awt.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;
import javax.swing.JOptionPane;
import edu.umn.ecology.populus.core.DesktopWindow;

public class DDSGVParamInfo implements BasicPlot {
   final double[] r, K, freqs;
   final double iN;
   final int gens;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = null;
      double[][][] points = new double[freqs.length][2][gens+1];
      double[] w = new double[3];
      double p,q,t1,t2,wbar,N;

      for(int i=0; i<freqs.length; i++){
         p = freqs[i];
         q = 1.0 - p;
         N = iN;
         for(int j=0; j<=gens; j++){
            points[i][0][j] = p;
            points[i][1][j] = N;

            for(int k=0; k<3; k++){
               if(K[k] != 0.0) w[k] = 1.0 + r[k]*(1.0 - N/K[k]);
               else            w[k] = 0.0;
               if(w[k] < 0.0)  w[k] = 0.0;
            }

            t1 = p*p*w[0];
            t2 = p*q*w[1];

            wbar = t1 + 2.0*t2 + q*q*w[2];

            if(wbar > 0.0){
               p = (t1 + t2)/wbar;
               q = 1.0 - p;
               N = (int)(N*wbar + 0.5);
            } else
               N = 0.0;
         }
      }
      String mcap = "Density-Dependent Selection w/ Genetic Variation";
      String ycap = "Population Size ( "+ColorScheme.getColorString(0)+"<b><i>N</> )";
      String xcap = "Allelic Frequency ( p )";
      bpi = new BasicPlotInfo(points,mcap,xcap,ycap);
      bpi.setColors(new Color[] {ColorScheme.colors[0]} );
      bpi.setIsDiscrete(true);
      return bpi;
   }

   public DDSGVParamInfo(double[] r, double[] K, double[] freqs, int N, int gens) {
      this.r = r;
      this.K = K;
      this.gens = gens;
      this.freqs = freqs;
      this.iN = N;
   }
}