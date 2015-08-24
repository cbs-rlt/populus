/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.das;

import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class DASParamInfo implements BasicPlot {
   static String xCap = "Generations ( <i>t</i> )";
   static String yCap = "Allelic Frequency (  "+ColorScheme.getColorString(0)+"<i>p</> )";
   static String mainCap = "Drift and Selection";
   double time, ifreq, wAA, wAa, waa;
   int N;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      double[][][] points = new double[1][][];

      points[0] = getP(N, ifreq, (int)time, wAA, wAa, waa);

      //bp = new BasicPlotInfo(points,0.0,time,0.0,1.0,mainCap,xCap,yCap);
      bp = new BasicPlotInfo(points,mainCap,xCap,yCap);
      bp.setIsFrequencies(true);
      return bp;
   }

   private double[][] getP(int N, double p, int gens, double wAA, double wAa, double waa){
      double[][] plot = new double[2][gens+1];
      int[][] parent = new int[2][N];
      int[][] offspring = new int[2][N];
      double n = 2*N;
      int rand, temp, curGen = 0, numA = (int)(n*p);
      boolean allDead = true;

      //use the initial frequency to set the genotype
      for(int i=0; i<(int)(p*n); i++)
         if(i<N) parent[0][i] = 1;
      else parent[1][i-N] = 1;

      //shuffle the genotype
      for(int i=0; i<n; i++){
         rand = Routines.getRandomInt((int)n);
         temp = parent[i/N][i%N];
         parent[i/N][i%N] = parent[rand/N][rand%N];
         parent[rand/N][rand%N] = temp;
      }

      while(curGen <= gens && numA != 0 && numA != n){
         plot[0][curGen] = curGen;
         plot[1][curGen] = p;

         allDead = true;
         for(int i=0; i<N; i++)
            switch(parent[0][i] + parent[1][i]){
               case 0: if(Math.random() > waa) parent[0][i] = -1;
                  else allDead = false;
                  break;
               case 1: if(Math.random() > wAa) parent[0][i] = -1;
                  else allDead = false;
                  break;
               case 2: if(Math.random() > wAA) parent[0][i] = -1;
                  else allDead = false;
                  break;
         }

         numA = 0;
         if(!allDead){
            for(int i=0; i<n; i++){
               do
                  rand = Routines.getRandomInt(N);
               while(parent[0][rand] < 0);
               offspring[i/N][i%N] = parent[Routines.getRandomInt(2)][rand];
               if(offspring[i/N][i%N] == 1) numA++;
            }

            System.arraycopy(offspring[0],0,parent[0],0,N);
            System.arraycopy(offspring[1],0,parent[1],0,N);

            p = (double)numA/(double)n;

            curGen++;
         }
      }

      //if either fixed or dead before the runtime is over
      if(curGen-1 < gens){
         plot[0] = Routines.shrinkDoubleArray(plot[0],curGen+1);
         plot[1] = Routines.shrinkDoubleArray(plot[1],curGen+1);
         plot[0][curGen] = curGen;

         //this is to make sure that the last data point is correct
         if(numA != n)
            plot[1][curGen] = 0.0;
         else
            plot[1][curGen] = 1.0;
      }
      return plot;
   }


   public DASParamInfo( int N, double ifreq, double time, double wAA, double wAa, double waa) {
      this.N = N;
      this.time = time;
      this.ifreq = ifreq;
      this.wAA = wAA;
      this.wAa = wAa;
      this.waa = waa;
   }
}
