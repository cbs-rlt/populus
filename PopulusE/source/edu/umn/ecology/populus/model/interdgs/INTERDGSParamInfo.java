/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.interdgs;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;
import javax.swing.JOptionPane;
import edu.umn.ecology.populus.core.DesktopWindow;

public class INTERDGSParamInfo implements BasicPlot {
   public static final int AvsT = 0;
   public static final int SvsT = 1;

   final double[] iFreqs;
   final double a, b, c, wAA, wAE, wEE, m;
   final int runs, gens, plotType;
   final double numDemes;
   final double n2;
   Random rand = new Random(System.currentTimeMillis());
   private static final String xCap = "Generations<b>(<i>t</i>)</>";
   private static final String mCap1 = "Interdemic Selection: Altruistic Frequency";
   private static final String mCap2 = "Interdemic Selection: Variance";
   private String yCap1 = "Mean Frequency of \"<i>A</i>\" Allele (  "+ColorScheme.getColorString(0)+"<b><i>p</> )";
   private String yCap2 = "Interdemic Variance ( "+ColorScheme.getColorString(0)+"<b>\u03c3<sup>2</>) ";

   private double[] doRun(double[] variance){
      double[][] data = new double[gens+1][(int)numDemes];
      double[] freqs = (double[])iFreqs.clone();
      double[] totFreq = new double[gens+1];
      double[] ps = new double[(int)numDemes];//probabilities of survival
      double t1, t2, wbar, li, ei, popFreq;
      double x;

      for(int i=0; i<=gens; i++){
         data[i] = (double[])freqs.clone();
         popFreq = 0.0;

         for(int j=0; j<numDemes; j++){
            /*selection*/
            t1 = freqs[j]*freqs[j]*wAA;
            t2 = freqs[j]*(1.0d-freqs[j])*wAE;
            wbar = t1 + 2*t2 + (1.0d-freqs[j])*(1.0d-freqs[j])*wEE;
            if(wbar != 0.0d)
               freqs[j] = (t1+t2)/wbar;
            else
               freqs[j] = 0.0d;
            popFreq += freqs[j];

            /*genetic drift*/
            x=0;
            for(int k=0; k<n2; k++)
               if(rand.nextDouble()<freqs[j])
                  x++;
            freqs[j] = x/n2;
         }
         popFreq /= numDemes;

         for(int j=0; j<numDemes; j++){
            /*gene flow migration*/
            x=0; //number of individuals that migrate from the deme
            for(int k=0; k<(n2/2); k++)
               if(rand.nextDouble()<m)
                  x++;
            li=0; //number A alleles leaving deme i
            ei=0; //number A alleles entering deme i
            for(int k=0; k<2*x; k++){
               if(rand.nextDouble()<freqs[j]) li++;
               if(rand.nextDouble()<popFreq) ei++;
            }
            freqs[j] += (ei - li)/n2;
            if(freqs[j] > 1.0) freqs[j] = 1.0;
            else if(freqs[j] < 0.0) freqs[j] = 0.0;

            /*demic extinction and replacement*/
            ps[j] = Math.min(a + b*Math.pow(freqs[j],c), 1.0);
            if(rand.nextDouble() > ps[j]){
            //the deme didn't survive, replace it from the population at large
               x=0;
               for(int k=0; k<n2; k++)
                  if(rand.nextDouble()<popFreq)
                     x++;
               freqs[j] = x/n2;
            }

            totFreq[i] += freqs[j];
         }
         totFreq[i] /= numDemes;
         if(totFreq[i] > 1.0)
            edu.umn.ecology.populus.fileio.Logging.log("FINAL SCREAM!");
         variance[i] += Routines.getVariance(freqs);
      }

      return totFreq;
   }

   public BasicPlotInfo getBasicPlotInfo() {
      double[][][] points = new double[1][2][gens+1];
      double[][] data = new double[runs][];
      double[] variance = new double[gens+1];
      int fix=0, die=0;
      BasicPlotInfo bpi = new BasicPlotInfo();

      for(int i=0; i<runs; i++){
         data[i] = doRun(variance);
         if(data[i][data[i].length-1] + 1e-6 >= 1.0) fix++;
         if(data[i][data[i].length-1] - 1e-6 <= 0.0) die++;
      }

      if(fix > 0){
         String message = "In "+fix+" of "+runs+" runs, the altruistic allele was fixed population-wide";
         JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
      }
      if(die > 0){
         String message = "In "+die+" of "+runs+" runs, the altruistic allele died population-wide";
         JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
      }

      switch (plotType){
         case AvsT:
            for(int i=0; i<=gens; i++){
               points[0][0][i] = i;
               for(int j=0; j<runs; j++)
                  points[0][1][i] += data[j][i];
               points[0][1][i] /= (double)runs;
            }
            bpi = new BasicPlotInfo(points,mCap1,xCap,yCap1);
            bpi.setIsFrequencies(true);

            break;
         case SvsT:
            for(int i=0; i<=gens; i++){
               points[0][0][i] = i;
               points[0][1][i] = variance[i]/(double)runs;
            }
            bpi = new BasicPlotInfo(points,mCap2,xCap,yCap2);
            break;
      }
      return bpi;
   }

   public INTERDGSParamInfo(double a, double b, double c, double sAA, double sAE, double sEE,
                            int demeN, double m, double[] freqs, int runs, int gens, int plotType) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.wAA = 1.0d - sAA;
      this.wAE = 1.0d - sAE;
      this.wEE = 1.0d - sEE;
      this.m = m;
      this.runs = runs;
      this.gens = gens;
      this.n2 = 2*demeN;
      this.iFreqs = freqs;
      this.plotType = plotType;
      numDemes = iFreqs.length;
   }
}