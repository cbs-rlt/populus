/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sam;
import edu.umn.ecology.populus.constants.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.math.NumberMath;

public class SAMParamInfo implements BasicPlot {
   public static final int numFreqs  = 100;
   public static final int PvsT      = 0;
   public static final int GenovsT   = 1;
   public static final int dPvsP     = 2;
   public static final int WBar      = 3;
   private double s, h, mu, qhat;
   private int gens, plotType;
   private double[] freqs;

   public BasicPlotInfo getBasicPlotInfo() {
      String mCap = "Selection and Mutation";
      String yCap ="";
      String xCap ="";
      double[][][] points = null;
      double[][] p;
      if(plotType == PvsT || plotType == GenovsT){
       p = new double[gens+1][freqs.length];
         for(int i=0; i<=gens; i++){
            System.arraycopy(freqs,0,p[i],0,freqs.length);
            for(int j=0; j<freqs.length; j++)
               freqs[j] = pNext(freqs[j]);
         }
      } else {
         p = new double[1][numFreqs];
         for(int i=0; i<numFreqs; i++)
            p[0][i] = i*1.0d/numFreqs;
      }

      switch (plotType){
         case PvsT:
            points = new double[freqs.length][2][p.length];
            xCap = "Generations ( <b><i>t</> )";
            yCap = "Gene Frequency ( "+ColorScheme.getColorString( 0 )+"<b><i>p</> )    ";
            for(int i=0; i<p.length; i++)
               for(int j=0; j<p[i].length; j++){
                  points[j][0][i] = i;
                  points[j][1][i] = p[i][j];
               }
            break;
         case GenovsT:
            points = new double[3][2][p.length];
            xCap = "Generations ( <b><i>t</> )";
            yCap = ColorScheme.getColorString( 0 ) + "p<sup>2</>,   " + ColorScheme.getColorString( 1 ) + "2pq</>,   " + ColorScheme.getColorString( 2 ) + "q<sup>2</>    ";
            for(int i=0; i<p.length; i++){
               points[0][0][i] = i;
               points[1][0][i] = i;
               points[2][0][i] = i;
               points[0][1][i] = p[i][0]*p[i][0];//p^2
               points[1][1][i] = 2.0d*p[i][0]*(1.0d - p[i][0]);//2pq
               points[2][1][i] = (1.0d - p[i][0])*(1.0d - p[i][0]);//q^2
            }
            break;
         case dPvsP:
            points = new double[1][2][p[0].length];
            xCap = "Gene Frequency ( <b><i>p</> )";
            yCap = ColorScheme.getColorString(0)+" <b><i>\u0394p</>     ";
            points[0][0] = p[0];
            for(int i=0; i<p[0].length; i++)
               points[0][1][i] = pNext(p[0][i]) - p[0][i];
            break;
         case WBar:
            points = new double[1][2][p[0].length];
            xCap = "Gene Frequency ( <b><i>p</> )";
            yCap = ColorScheme.getColorString(0)+"<b><i>w\u0305</>    ";
            points[0][0] = p[0];
            for(int i=0; i<p[0].length; i++)
               points[0][1][i] = wBar(p[0][i]);
            break;
      }

      if(s == 0.0d)
         qhat = 0.0d;
      else if(h == 0.0d)
         qhat = Math.sqrt(mu/s);
      else
         qhat = mu/(h*s);
      qhat = NumberMath.roundSig(qhat,5,0);

      //BasicPlotInfo bpi = new BasicPlotInfo(points,mCap+", <sp=qhat> = "+qhat,xCap,yCap);
      BasicPlotInfo bpi = new BasicPlotInfo(points,mCap+", q\u0302 = "+qhat,xCap,yCap);
      if(plotType == GenovsT || plotType == PvsT) bpi.setIsFrequencies(true);
      bpi.setIsDiscrete(true);
      bpi.setColors( ColorScheme.colors );
      return bpi;
   }

   double pNext(double p){
      double q = 1.0d - p;
      return (p*(1.0d-q*h*s)*(1.0d - mu)/wBar(p));
   }

   double wBar(double p){
      double q = 1.0d - p;
      return (1.0d - s*q*(q + 2.0d*p*h));
   }

   public SAMParamInfo(double s, double h, double mu, int gens, int plotType, double[] freqs) {
      this.s = s;
      this.h = h;
      this.mu = mu;
      this.gens = gens;
      this.plotType = plotType;
      this.freqs = freqs;
   }

}
