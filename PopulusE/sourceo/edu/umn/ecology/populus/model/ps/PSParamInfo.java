package edu.umn.ecology.populus.model.ps;

import java.awt.Color;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.poproutines.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;
import java.lang.Math;
import javax.swing.JOptionPane;
import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.constants.ColorScheme;

public class PSParamInfo implements BasicPlot {
   final int loci;
   int gen = 0;
   final double migrationRate;
   Population[] pops;
   double freqAve;
   double[][] data;
   double[][] statistics;
   boolean isP = true;
   boolean fixed = false;
   boolean fixedMessaged = false;
   public static final String maincap1 = "Population Structure: Demic Allele Frequencies";
   public static final String maincap2 = "Population Structure: Wright's <i>F</i>-Statistics";
   public static final String ycap1 = "Allelic Frequency (  <b><i>p</i> )";
   public String ycap2 = ColorScheme.getColorString(0)+"F<sub><i>is</>, "+ColorScheme.getColorString(1)+"F<sub><i>st</>, and "+ColorScheme.getColorString(2)+"F<sub><i>it</>";
   public static final String xcap  = "Generations (<b><i>t</>)";

   public void switchOutputs(){
      isP = !isP;
   }

   double[] getStatistics(double[] p, double hetAve){
      double Hi=0, Hs=0, Ht=0, newAve=0;
      double[] s = new double[3];

      Hi = hetAve;
      for(int i=0; i<p.length; i++){
         newAve += p[i];
         Hs += 2.0*p[i]*(1.0 - p[i]);
      }
      newAve /= loci;
      Ht = 2.0*newAve*(1.0 - newAve);
      Hs /= loci;

      if(Hs>1e-4) s[0] = (Hs - Hi)/Hs;
      else        s[0] = 0;
      if(Ht>1e-4){
         s[1] = (Ht - Hs)/Ht;
         s[2] = (Ht - Hi)/Ht;
      } else {
         s[1] = 0;
         s[2] = 0;
      }
      freqAve = newAve;
      return s;
   }

   void doGeneration(){
      double[] p = new double[loci];
      double hetAve = 0;
      int fix = 2;

      //if(fixed) return;
      fixed = true;

      for(int i=0; i<loci; i++){
         pops[i] = pops[i].breed(migrationRate,freqAve);
         p[i] = pops[i].getPFreq();
         hetAve += pops[i].getHetFreq();
         if( pops[i].isFixed() >= 0 ){
            if(fix != pops[i].isFixed() && i != 0){
               fixed = false;
            } else {
               fix = pops[i].isFixed();
            }
         } else {
            fixed = false;
         }
      }
      if(fixed && !fixedMessaged){
         String message = "All demes have fixed at p = "+(fix+1)%2;
         JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
         fixedMessaged = true;
      }
      hetAve /= loci;
      gen++;
      data = Routines.addArray(data,p,gen);
      statistics = Routines.addArray(statistics,getStatistics(p,hetAve),gen);
   }

   public BasicPlotInfo getBasicPlotInfo() {
      double[][][] points = new double[isP?loci:4][2][gen+1];
      for(int i=0; i<points.length; i++)
         for(int j=0; j<=gen; j++){
            if(!isP && i >= 3) break;
            points[i][0][j] = j;
            points[i][1][j] = isP?data[j][i]:statistics[j][i];
         }

      if(!isP){
         points[3] = new double[][]{ {0,gen+1},{0,0}};
      }

      BasicPlotInfo bpi = new BasicPlotInfo( points, isP?maincap1:maincap2, xcap, isP?ycap1:ycap2 );

      if(!isP){
         bpi.setLineColor(3,Color.black);
         bpi.setLineStyle(3,BasicPlotInfo.DASHED);
      }

      bpi.setDefaultAxis(false);
      if(isP) bpi.setIsFrequencies(true);
      return bpi;
   }

   public PSParamInfo( double[] freqs, double migrationRate, int demeSize, boolean selfs) {
      this.migrationRate = migrationRate;
      loci = freqs.length;
      pops = new Population[loci];
      for(int i=0; i<pops.length; i++){
         pops[i] = new Population(demeSize,freqs[i],selfs);
         freqAve += freqs[i];
      }
      freqAve /= loci;
      data = new double[20][loci];
      data[0] = freqs;
      statistics = new double[20][3];
      statistics[0] = getStatistics(freqs,2.0*freqAve*(1.0-freqAve));
   }
}