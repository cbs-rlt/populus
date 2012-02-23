package edu.umn.ecology.populus.model.inbreeding;

import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import java.awt.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class InbreedingParamInfo implements BasicPlot {
   static String xCap = "Generations";
   static String yCap = "Inbreeding Coefficient ( "+ColorScheme.getColorString(0)+"F<sub>t</>, "+ColorScheme.getColorString(1) +
      "F<sub>a</>, "+ColorScheme.getColorString(2) + "F<sub>f</> )";
   static String mainCap = "Inbreeding Coefficient vs Time";
   double time, ifreq;
   int N;

   /**
    * this seed needs to be used in the random number generator for both getFa and getFf methods
    */
   long seed;

   /**
    * both the getFa and getFf methods use the random number generator, and they need to produce
    * the same set of random numbers each. so the seed is set to be the same in each method.
    */
   Random rand = new Random();

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      seed = System.currentTimeMillis();
      double[][][] points = new double[3][][];

      points[0] = getFt(N, ifreq, (int)time);
      points[1] = getFa(N, ifreq, (int)time);
      points[2] = getFf(N, ifreq, (int)time);

      bp = new BasicPlotInfo(points,mainCap,xCap,yCap);
      return bp;
   }

   /**
      This is the calculation for the theoretical frequency (Ft) of the inbreeding
      coefficient. The continuous equation is Ft = 1-(1-1/(2N))^t, but because the
      calculations here are concerned with the discrete model -- the discrete for of the
      equation is used. F0 is assumed to be 0. Maybe F0 could be a parameter instead of
      an assumption...
   */
   private double[][] getFt(int N, double ifreq, int gens){
      double[][] plot = new double[2][gens+1];
      double Ft = ifreq, n = 2*N;

      for(int i=0; i<=gens; i++){
         plot[0][i] = i;
         plot[1][i] = Ft;
         Ft = 1.0/n + (1.0-1.0/n)*Ft;
      }

      return plot;
   }

   /**
      This is the Fa, or the inbreeding frequency as calculated via the individual autozygosity
      method.

      maybe we could change the model to allow F0 to be a parameter passed in.
      the parent array would only be filled up to a certain percent as it is
      values, then the remaining with values representing inbreeding....
   */
   private double[][] getFa(int N, double ifreq, int gens){
      double[][] plot = new double[2][gens+1];
      double[][] parents = new double[2][N];
      double[][] offspring = new double[2][N];
      double identities = 0.0;

      rand.setSeed(seed);

      plot[0][0] = 0;
      plot[1][0] = ifreq;

      //just fill up the array with all different values
      if(ifreq < 1.0/(2.0*N)) ifreq = 1.0/(2.0*N);
      for(double i=0; i<N; i++)
         for(double j=0; j<2; j++)
            parents[(int)j][(int)i] = (int)((j*N+i)/(2.0*N*ifreq));


      for(int i=1; i<=gens; i++){
         identities = 0.0;
         for(int j=0; j<N; j++){
            //simulate the next generation
            offspring[0][j] = parents[rand.nextInt(2)][rand.nextInt(N)];
            offspring[1][j] = parents[rand.nextInt(2)][rand.nextInt(N)];
            //count the inbred offspring
            if(offspring[0][j] == offspring[1][j]) identities++;
         }
         System.arraycopy(offspring[0],0,parents[0],0,N);
         System.arraycopy(offspring[1],0,parents[1],0,N);
         plot[0][i] = i;
         plot[1][i] = identities/(double)(N);
      }

      return plot;
   }

   /**
      This finds the frequency of inbreeding as calculated by the Monte Carlo Method.

      The idea of the algorithm is create an array of parents, and then simulate them
      reproducing for the number of generations specified by the model.
      Then put them into a LinkedList (so that elements can be easily removed). Count the
      number of elements that match the first element, removing each as they are counted.
      The variable sumofsqrs is incremented by [count/(2*N)]^2 after each element is examined.
      {the final value for the array is set to the final value of sumofsqrs}
      Then remove the top element, and do the same thing over again until the whole LinkedList
      is empty.
   */
   private double[][] getFf(int N, double ifreq, int gens){
      LinkedList population = new LinkedList();
      double[][] plot = new double[2][gens+1];
      double[][] parents = new double[2][N];
      double[][] offspring = new double[2][N];
      double count = 0.0, sumofsqrs, n = 2.0*N;
      int index = 1;

      rand.setSeed(seed);

      plot[0][0] = 0;
      plot[1][0] = ifreq;

      //just fill up the array with all different values
      if(ifreq < 1.0/(2.0*N)) ifreq = 1.0/(2.0*N);
      for(double i=0; i<N; i++)
         for(double j=0; j<2; j++)
            parents[(int)j][(int)i] = (int)((j*N+i)/(2.0*N*ifreq));

      for(int i=1; i<=gens; i++){
         for(int j=0; j<N; j++){
            //simulate the next generation
            offspring[0][j] = parents[rand.nextInt(2)][rand.nextInt(N)];
            offspring[1][j] = parents[rand.nextInt(2)][rand.nextInt(N)];
         }
         //children become the parents
         System.arraycopy(offspring[0],0,parents[0],0,N);
         System.arraycopy(offspring[1],0,parents[1],0,N);

         //initiate the LinkedList by filling it with the children
         for(int j=0; j<N; j++)
            for(int k=0; k<2; k++)
                  population.add(new Double(offspring[k][j]));

         //count the number of repeats of the first in the population
         //to count and remove them.
         sumofsqrs = 0.0;
         while(!population.isEmpty()){
            count = 1;
            while(index < population.size()){
               if( ((Double)population.getFirst()).equals(population.get(index)) ){
                  count++;
                  population.remove(index);
               } else {
                  index++;
               }
            }
            sumofsqrs += count*count/(n*n);
            population.removeFirst();
            index = 1;
         }
         plot[0][i] = i;
         plot[1][i] = sumofsqrs;
      }

      return plot;
   }

   public InbreedingParamInfo( int N, double ifreq, double time) {
      this.N = N;
      this.time = time;
      this.ifreq = ifreq;
   }
}
