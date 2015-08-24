/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.intradgs;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class INTRADGSParamInfo implements BasicPlot {
   double[] freqs;
   final double b, s;
   final int N, gens, G, numGroups;
   double n2, popFreq;
   Random rand = new Random(System.currentTimeMillis());



   public BasicPlotInfo getBasicPlotInfo() {
      double[][][] points = new double[1][2][gens+1];
      int[] groupSize = new int[numGroups];
      int gen=0,k;
      double wAA, wAE, wEE, t1, t2, wbar, sum=popFreq, wsum, PopSize;

//      points[0][0][0] = 0.0;
//      points[0][1][0] = sum;
      while(gen<=gens){
         points[0][0][gen] = gen;
         points[0][1][gen] = popFreq;
         for(int i=0; i<numGroups; i++){
            k = 0;
            for(int j=0; j<n2; j++)
               if(Math.random() < popFreq) k++;
            freqs[i] = k/n2;
         }
         for(int i=0; i<numGroups; i++)
            groupSize[i] = N;


         for(int i=0; i<G; i++){
            gen++;
            sum = 0.0;

            for(int j=0; j<numGroups; j++){
            // calc freqs[i] and groupSize[i] in terms of their previous values
               wEE = 1.0 + b*freqs[j];
               wAA = wEE*(1.0 - 2.0*s);
               wAE = wEE*(1.0 - s);

               t1 = freqs[j]*freqs[j]*wAA;
               t2 = freqs[j]*(1.0 - freqs[j])*wAE;
               wbar = t1 + 2.0*t2 + (1.0 - freqs[j])*(1.0 - freqs[j])*wEE;
               if(wbar != 0.0)
                  freqs[j] = (t1+t2)/wbar;
               else
                  freqs[j] = 0.0;

               groupSize[j] = (int)(groupSize[j]*wbar+0.5);

               //for unweighted average
               sum += freqs[j];
            }
            sum /= numGroups;

            if(points[0][0].length <= gen) break;
            if(i+1 != G){
               points[0][0][gen] = gen;
               points[0][1][gen] = sum;
            }
         }

         //calculate dispersal
         wsum = 0.0;
         PopSize = 0.0;

         for(int i=0; i<numGroups; i++){
            PopSize += groupSize[i];
            wsum += freqs[i]*groupSize[i];
         }

         if(PopSize > 0) popFreq = wsum/PopSize;
         else popFreq = 0.0;
      }

      BasicPlotInfo bpi = new BasicPlotInfo(points, "Intrademic Selection: Altruistic Frequency", "Generations<b> (<i>t</i>)</>", "Mean Frequency of the \"<i>A</i>\" allele (  <b><i>p</> )       ");
      bpi.setIsDiscrete(true);
      bpi.setIsFrequencies(true);
      return bpi;
   }

   public INTRADGSParamInfo(int N, int G, double b, double s, double iFreq, int numGroups, int gens) {
      this.popFreq = iFreq;
      this.b = b;
      this.s = s;
      this.G = G;
      this.gens = gens;
      this.N = N;
      this.n2 = 2*N;
      this.numGroups = numGroups;
      freqs = new double[numGroups];
   }
}