package edu.umn.ecology.populus.model.sdal;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import java.awt.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class SDALParamInfo implements BasicPlot {
   protected static final int SIX_FREQ = 6;
   protected static final int SINGLE_FREQ = 1;
   protected static final int WBARvP = 3;
   protected static final int FREQvT = 1;
   protected static final int PvT = 0;
   protected static final int FITNESS = 1;
   protected static final int SELECTION = 0;
   protected static final int DPvP = 2;
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.sdal.Res" );
   private double[][] points;
   private int coeffType;
   private double min, max;
   private int freqNumber;
   private int plotType;
   private double[][][] points2;
   private double freq0, generations, waa, wAa, wAA, s, h;
   private String yCap3 = ColorScheme.getColorString(0)+" <b><i>w\u0305</> ";
   private String pCap = " Allelic Frequency (  "+ColorScheme.getColorString(0)+"<i><b>p</> )  ";
   private static final String xpCap = " Allelic Frequency ( <i><b>p</> )  ";
   private String yCap4 = ColorScheme.getColorString( 0 ) + "p<sup>2</>,   " + ColorScheme.getColorString( 1 ) + "2pq</>,   " + ColorScheme.getColorString( 2 ) + "q<sup>2</>";
   private static final String xCap1 = res.getString( "Generations_b_i_t_" );
   private String yCap2 = ColorScheme.getColorString(0)+" <b><i>\u0394p</> ";

   //private static final String mCap1 = "<b><i>p</> vs <b><i>t</>";
   //private static final String mCap2 = res.getString("Genotypic_Frequencies");
   //private static final String mCap3 = "<b><i>\u0394p</> vs <b><i>p</>"; //no delta that big? //is mac os x having problems with *italic* symbols?
   //private static final String mCap4 = "<b><i>w\u0305</> vs <b><i>p</>";

   private static final String mCap1 = "Autosomal Selection";
   private static final String mCap4 = "Adaptive Topography";
   private static final String mCap3 = "Allelic Frequency Change"; //no delta that big? //is mac os x having problems with *italic* symbols?
   private static final String mCap2 = "Genotypic Frequency Trajectory";

   public BasicPlotInfo getBasicPlotInfoK() {
      double[][][] temp = new double[1][][];
      BasicPlotInfo bpi;
      if( plotType == PvT ) {
         if( freqNumber == SINGLE_FREQ ) {
            doPvT();
            temp[0] = points;
            bpi = new BasicPlotInfo( temp, mCap1, xCap1, pCap );
            bpi.setIsFrequencies(true);
            return bpi;
         } else {
            doPvT6();
            bpi = new BasicPlotInfo( points2, mCap1, xCap1, pCap );
            bpi.setIsFrequencies(true);
            return bpi;
         }
      } else {
         if( plotType == DPvP ) {
            doDPvP();
            temp[0] = points;
            return new BasicPlotInfo( temp, mCap3, xpCap, yCap2 );
         } else {
            if( plotType == WBARvP ) {
               doWBARvP();
               temp[0] = points;
               return new BasicPlotInfo( temp, mCap4, xpCap, yCap3 );
            } else { //plotType == FREQvT
               doFREQvT();
               bpi = new BasicPlotInfo( points2, mCap2, xCap1, yCap4 );
               bpi.setIsFrequencies(true);
               return bpi;
            }
         }
      }
   }

   //Must be sure to keep time consuming area in getBasicPlotInto!!
   //What about six-frequency?? Do we need to have it, since we now have multiple graphs?
   //Should be able to do it easily.

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = getBasicPlotInfoK();
      bpi.setSymbolSize(4);
      bpi.setIsDiscrete(true);
      return bpi;
   }

   public SDALParamInfo( int plotType, int coeffType, double wAA, double wAa, double waa, double s, double h, int freq, double initFreq, double gens ) {
      super();
      this.plotType = plotType;
      this.coeffType = coeffType;
      this.freqNumber = freq;
      this.freq0 = initFreq;
      this.generations = gens;
      this.waa = waa;
      this.wAA = wAA;
      this.wAa = wAa;
      this.h = h;
      this.s = s;
   }

   void doPvT6() {
      points2 = new double[6][][];
      double[] frequencies =  {
         0.02, 0.212, 0.404, 0.596, 0.788, 0.98
      };
      for( int j = 0;j < 6;j++ ) {
         freq0 = frequencies[j];
         doPvT();
         points2[j] = points;
      }
   }

   void doWBARvP() {
      points = new double[2][101];
      double tempP = 0.0;
      double temp = wBar( tempP );
      min = temp;
      max = temp;
      points[0][0] = 0.0;
      points[1][0] = temp;
      temp = 0.01;
      for( int i = 1;i < 101;tempP += 0.01,i++ ) {
         temp = wBar( tempP );
         points[0][i] = tempP;
         points[1][i] = temp;
         max = Math.max( max, temp );
         min = Math.min( min, temp );
      }
   }

   /**
     * p^2 is red, 2pq is green, q^2 is blue
     */

   void doFREQvT() {
      int i;
      double[] xArray;
      points2 = new double[3][2][];
      xArray = new double[(int)generations + 1];
      for( i = 0;i < 3;i++ ) {
         points2[i][1] = new double[(int)generations + 1];
         points2[i][0] = xArray;
      }
      double tempP = freq0, tempQ;
      for( i = 0;i <= generations;i++,tempP = next( tempP ) ) {
         tempQ = 1.0 - tempP;
         xArray[i] = (double)i;
         points2[0][1][i] = tempP * tempP;
         points2[1][1][i] = 2 * tempP * tempQ;
         points2[2][1][i] = tempQ * tempQ;
         for( int j = 0;j < 3;j++ ) {
            max = Math.max( points2[j][1][i], max );
         }
      }
   }

   void doDPvP() {
      points = new double[2][101];
      double tempP = 0.0, deltaP;
      min = 0.0;
      max = 0.0;
      for( int i = 0;i < 101;tempP += 0.01,i++ ) {
         deltaP = next( tempP ) - tempP;
         points[0][i] = tempP;
         points[1][i] = deltaP;
         max = Math.max( max, deltaP );
         min = Math.min( min, deltaP );
      }
   }

   /** Go forward one generation */

   double next( double p ) {
      double q = 1 - p;
      double num, den;
      if( coeffType == SELECTION ) {
         double qomhs = q * ( 1 - h * s );
         num = p * ( p + qomhs );
         den = p * ( p + 2 * qomhs ) + q * q * ( 1 - s );
      }
      else {
         double p2wAA = p * p * wAA;
         double pqwAa = p * q * wAa;
         num = p2wAA + pqwAa;
         den = p2wAA + 2 * pqwAa + q * q * waa;
      }
      if( den > 0 ) {
         return num / den;
      }
      if( p == 1 ) {
         return 1; //{ when wAA=0,wBB=0,p=0, pval already set to 0, so set f=0}
      }
      return 0;
   }

   /** wbar is the success rate of the entire population, for a given p */

   double wBar( double p ) {
      double q = 1 - p;
      if( coeffType == SELECTION ) {
         return p * ( p + 2 * q * ( 1 - h * s ) ) + q * q * ( 1 - s );
      }
      else {
         return p * ( p * wAA + 2 * q * wAa ) + q * q * waa;
      }
   }

   /** The reason I use 'do' instead of 'get' is to return points, min, and max */

   void doPvT() {
      points = new double[2][(int)generations + 1];
      double tempP = freq0;
      min = 0.0;
      max = 1.0;
      for( int i = 0;i <= generations;i++,tempP = next( tempP ) ) {
         points[0][i] = (double)i;
         points[1][i] = tempP;
      }
   }
}
