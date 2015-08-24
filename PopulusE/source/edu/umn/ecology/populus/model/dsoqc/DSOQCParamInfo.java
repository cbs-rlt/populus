/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dsoqc;
import edu.umn.ecology.populus.plot.*;
import java.util.*;
import edu.umn.ecology.populus.math.*;

public class DSOQCParamInfo implements BasicPlot {
   boolean isVsTime;
   double gAA, gAa, gaa, p, ve;
   int popSize, repopSize;
   int[][] parent;
   Random rand;
   double[][] line;
   double[] averages = new double[10];
   int generation = 0;

   public void switchOutputs(){
      isVsTime = !isVsTime;
   }

   public void doGeneration(){
      int[][] selected = new int[2][repopSize];
      double[][] pheno = new double[2][popSize];
      double avg = 0;

      for(int i=0; i<popSize; i++){
         switch(parent[0][i] + parent[1][i]){
            case 0: pheno[1][i] = Routines.nextGaussian(gaa,ve,rand);            break;
            case 1: pheno[1][i] = Routines.nextGaussian(gAa,ve,rand); break;
            case 2: pheno[1][i] = Routines.nextGaussian(gAA,ve,rand); break;
         }
         pheno[0][i] = i;
      }

      line = Routines.buildDistributionCurve(pheno[1],10);

      for(int i=0; i<pheno[1].length; i++){
         avg += pheno[1][i];
      }
      avg /= pheno[1].length;
      averages = Routines.addDouble(averages,avg,generation);

      Routines.quickSort(pheno,0,pheno[1].length-1,1);
      for(int i=0; i<repopSize; i++){
         selected[0][i] = parent[0][(int)pheno[0][pheno[0].length - i - 1]];
         selected[1][i] = parent[1][(int)pheno[0][pheno[0].length - i - 1]];
      }

      for(int i=0; i<popSize; i++){
         parent[0][i] = selected[rand.nextInt(2)][rand.nextInt(repopSize)];
         parent[1][i] = selected[rand.nextInt(2)][rand.nextInt(repopSize)];
      }
      generation++;
   }

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi;
      if(isVsTime){
         double[][][] points = new double[1][2][generation];
         for(int i=0; i<generation; i++){
            points[0][0][i] = i;
            points[0][1][i] = averages[i];
         }
         bpi = new BasicPlotInfo(points,"Phenotypic Trajectory","Generations ( <i><b>t</> )","Mean Phenotype ");
         bpi.setIsDiscrete(true);
         if(generation<=10) bpi.setXMax(10);
         return bpi;
      } else {
         double[][][] points = new double[1][][];
         points[0] = line;
         bpi =  new BasicPlotInfo(points,"Individual Values","Phenotypic Value","Number of Individuals ");
         bpi.setXMin(0.0);
         return bpi;
      }
   }

   public DSOQCParamInfo(double gAA, double gAa, double gaa, double p, double Ve, int popSize,
                         int repopSize, boolean isVsTime) {
      this.gAA = gAA;
      this.gAa = gAa;
      this.gaa = gaa;
      this.p = p;
      this.ve = Ve;
      this.popSize = popSize;
      this.repopSize = repopSize;
      this.isVsTime = isVsTime;
      rand = new Random(System.currentTimeMillis());
      parent = new int[2][];
      parent[0] = Routines.getFrequencyArray(popSize,p,rand);
      parent[1] = Routines.getFrequencyArray(popSize,p,rand);
      doGeneration();
   }
}