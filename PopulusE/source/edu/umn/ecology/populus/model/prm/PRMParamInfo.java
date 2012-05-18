package edu.umn.ecology.populus.model.prm;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import java.awt.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class PRMParamInfo implements BasicPlot {
   protected static final int onevst = 1;
   protected static final int twovst = 2;
   protected static final int threevst = 5;
   protected static final int fourvst = 6;
   protected static final int COMINS  = 3;
   protected static final int ALSTAD = 4;


   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.prm.Res" );
   private double[][] points;
   private int plotType, modelType;
   private double[][][] points2;
   private double [][][] points1;
   private double[][][] points2temp;
   private double [][][] points3;
   private double[][][] points4;
   private double [][][] points1tempx;
   private static final String mCap = res.getString("PRM");
   private static final String xCap1 = res.getString( "Generations_b_i_t_" );
   private static final String yCap1 = res.getString( "One" );
   private static final String yCap2 = res.getString( "Two" );
   private static final String yCap3 = res.getString( "Three" );
   private static final String yCap4 = res.getString( "Four" );
   //PRM
   private double  x, y, p, w, b1, b2, b3, g, r, L, k, h, mu, s1, s2, s3, f, a, time;
   private int genNum;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = null;
      if( plotType == onevst ) {
         doFREQvT();
         bpi = new BasicPlotInfo( points2, mCap, xCap1, yCap1 );
         //bpi.setYMax(2.0);
         //bpi.setIsFrequencies(true);
         //bpi.setIsDiscrete(true);

         return bpi;
      }
      else{
         if(plotType == twovst){
            doFREQvT();
            bpi = new BasicPlotInfo( points1, mCap, xCap1, yCap2);
            //bpi.setIsFrequencies(true);
            return bpi;
         }
         else{
            if( plotType == threevst){
               doFREQvT();
               bpi = new BasicPlotInfo( points3, mCap, xCap1, yCap3);
               //  bpi.setIsFrequencies(true);
               return bpi;
            }
            else{
               doFREQvT();
               bpi = new BasicPlotInfo( points4, mCap, xCap1, yCap4);
               //bpi.setIsFrequencies(true);
               return bpi;
            }
         }
      }
   }
   public PRMParamInfo( int modelType, int plotType, double time, int genNum, double x, double y, double p, double w, double b1, double b2, double b3, double g, double r, double l, double k, double h, double mu, double s1, double s2, double s3, double f, double a) {
      super();
      this.plotType = plotType;
      this.modelType = modelType;
      this.time = time;
      this.genNum = genNum;
      this.x = x;
      this.y = y;
      this.p = p;
      this.w = w;
      this.b1 = b1;
      this.b2 = b2;
      this.b3 = b3;
      this.g= g;
      this.r = r;
      this.L = l;
      this.k = k;
      this.h = h;
      this.a = a;
      this.s1 = s1;
      this.s2 = s2;
      this.s3 = s3;
      this.f = f;
      this.mu = mu;
   }



   void doFREQvT() {
      int i,j;
      double[] xArray;
      points2 = new double[4][2][];
      points1 = new double[2][2][];
      points3 = new double[1][2][];
      points4 = new double[1][2][];
      xArray = new double[(int)time + 1];


      for( i = 0;i < 4;i++ ) {
         points2[i][1] = new double[(int)time + 1];
         points2[i][0] = xArray;
      }

      for( j = 0;j < 2;j++ ) {
         points1[j][1] = new double[(int)time + 1];
         points1[j][0] = xArray;
      }

      points3[0][1] = new double[(int)time + 1];
      points3[0][0] = xArray;


      points4[0][1] = new double[(int)time + 1];
      points4[0][0] = xArray;

      double[] temp = new double[4];
      double[] temp1 = new double[4];
      double[] tempxp = new double[2];

      double tempX = x, tempY=y, tempP = p, tempW = w;

      if (modelType == PRMParamInfo.COMINS){
         for( i = 0;i <= time;i++) {

            tempxp = kill (tempX,tempP, b1);
            temp = migrate(tempxp[0], tempY, tempxp[1], tempW, b1);
            xArray[i] = (double)i;
            points2[0][1][i] = temp[0];//x
            points2[1][1][i] = temp[1]/g;//y/G
            points2[2][1][i] = temp[2];//p
            points2[3][1][i] = temp[3];//w

            points1[0][1][i] = temp[0] * temp [2];//x * p
            points1[1][1][i] = temp[1]/g * temp[3];//y/G * w

            points3[0][1][i] = temp[0] + temp [1];//x +y
            points4[0][1][i] = temp[0] * temp[2] + temp[1] * temp[3];//x*p + y * w

            tempX = temp[0];
            tempY = temp[1];
            tempP = temp[2];
            tempW = temp[3];

         }
      }
      else{
         for ( i=0; i<= time; i++)
            xArray[i] = (double)i;
         //Integer i = new Integer();
         if (genNum ==1){
            for( i = 1;i <= time;i++){
               tempxp = overwinter (tempX,tempY);
               temp1 = migrate2(tempxp[0], tempxp[1], tempP, tempW, s1, b1);
               temp = migrate2(temp1[0], temp1[1], temp1[2], temp1[3], s1, b1);
               points2[0][1][i] = temp[0];//x
               points2[1][1][i] = temp[1]/g;//y/G
               points2[2][1][i] = temp[2];//p
               points2[3][1][i] = temp[3];//w
               points1[0][1][i] = temp[0] * temp [2];//x * p
               points1[1][1][i] = temp[1]/g * temp[3];
               points3[0][1][i] = temp[0] + temp [1];//x +y
               points4[0][1][i] = temp[0] * temp[2] + temp[1] * temp[3];//x*p + y * w
               tempX = temp[0];
               tempY = temp[1];
               tempP = temp[2];
               tempW = temp[3];
            }
         }
         else if (genNum == 2){
            for( i = 1;i <= time;i++) {
               if ((i % 2) != 0){
                  tempxp = overwinter (tempX,tempY);
                  temp1 = migrate2(tempxp[0], tempxp[1], tempP, tempW, s1, b1);
                  points2[0][1][i] = temp1[0];//x
                  points2[1][1][i] = temp1[1]/g;//y/G
                  points2[2][1][i] = temp1[2];//p
                  points2[3][1][i] = temp1[3];//w
                  points1[0][1][i] = temp1[0] * temp1 [2];//x * p
                  points1[1][1][i] = temp1[1]/g * temp1[3];
                  points3[0][1][i] = temp1[0] + temp1 [1];//x +y
                  points4[0][1][i] = temp1[0] * temp1[2] + temp1[1] * temp1[3];//x*p + y * w
               }
               else{
                  temp = migrate2(temp1[0], temp1[1], temp1[2], temp1[3], s2, b2);
                  points2[0][1][i] = temp[0];//x
                  points2[1][1][i] = temp[1]/g;//y/G
                  points2[2][1][i] = temp[2];//p
                  points2[3][1][i] = temp[3];//w
                  points1[0][1][i] = temp[0] * temp [2];//x * p
                  points1[1][1][i] = temp[1]/g * temp[3];
                  points3[0][1][i] = temp[0] + temp [1];//x +y
                  points4[0][1][i] = temp[0] * temp[2] + temp[1] * temp[3];//x*p + y * w
                  tempX = temp[0];
                  tempY = temp[1];
                  tempP = temp[2];
                  tempW = temp[3];
               }
            }
         }
         else{
            for( i = 1;i <= time;i++) {
               //1st generation of year
               if (i%3 == 1){
                  tempxp = overwinter (tempX,tempY);
                  temp1 = migrate2(tempxp[0], tempxp[1], tempP, tempW, s1, b1);
                  points2[0][1][i] = temp1[0];//x
                  points2[1][1][i] = temp1[1]/g;//y/G
                  points2[2][1][i] = temp1[2];//p
                  points2[3][1][i] = temp1[3];//w
                  points1[0][1][i] = temp1[0] * temp1 [2];//x * p
                  points1[1][1][i] = temp1[1]/g * temp1[3];
                  points3[0][1][i] = temp1[0] + temp1 [1];//x +y
                  points4[0][1][i] = temp1[0] * temp1[2] + temp1[1] * temp1[3];//x*p + y * w
               }
               //second generation of year
               else if ((i % 3) == 2){
                  temp = migrate2(temp1[0], temp1[1], temp1[2], temp1[3], s2, b2);
                  points2[0][1][i] = temp[0];//x
                  points2[1][1][i] = temp[1]/g;//y/G
                  points2[2][1][i] = temp[2];//p
                  points2[3][1][i] = temp[3];//w
                  points1[0][1][i] = temp[0] * temp [2];//x * p
                  points1[1][1][i] = temp[1]/g * temp[3];
                  points3[0][1][i] = temp[0] + temp [1];//x +y
                  points4[0][1][i] = temp[0] * temp[2] + temp[1] * temp[3];//x*p + y * w
               }

               else{
                  tempxp = overwinter (temp[0],temp[1]);
                  temp = migrate2(tempxp[0],tempxp[1], temp[2], temp[3], s3, b3);
                  points2[0][1][i] = temp[0];//x
                  points2[1][1][i] = temp[1]/g;//y/G
                  points2[2][1][i] = temp[2];//p
                  points2[3][1][i] = temp[3];//w
                  points1[0][1][i] = temp[0] * temp [2];//x * p
                  points1[1][1][i] = temp[1]/g * temp[3];
                  points3[0][1][i] = temp[0] + temp [1];//x +y
                  points4[0][1][i] = temp[0] * temp[2] + temp[1] * temp[3];//x*p + y * w
                  tempX = temp[0];
                  tempY = temp[1];
                  tempP = temp[2];
                  tempW = temp[3];
               }
            }
         }
      }
   }
   double[] kill(double x,double p, double b) {
      double[] xp = new double[2];
      if (x > 0)
         x = Math.pow(x, 1-b);
      double q, temp, temp2, lp2;
      q = 1 - p;
      lp2   = L*p*p;
      temp  = (L * h + k * (1-h) ) * p * q;
      temp2 = lp2+temp*2+k*q*q;

      x = x * temp2;
      if (temp2 > 0)
         p = ( lp2 + temp ) / temp2;
      else
         p = 0;
      xp[0] = x;
      xp[1] = p;
      return xp;
   }


   double[] migrate(double x, double y, double p, double w, double b) {
      double p0, w0, x0, y0,x1mr, y1mrdG, rydG, rx;

      double[] output = new double[4];
      if (( g > 0) && ( y > 0 ))
         y = g * Math.pow(y/g, 1-b);
      else
         y = 0;
      p0 = p;
      x0 = x;
      y0 = y;
      w0 = w;
      x1mr = ( 1- r) * x0;
      if ( g != 0 ){
         y1mrdG = (1 - r/g) * y0;
         rydG = r * y0/g;
      }
      else{
         y1mrdG = 0;
         rydG = 0;
      }
      rx = r * x0;

      x = x1mr + rydG;
      y = rx + y1mrdG;

      if (x != 0)
         p = ( x1mr * p + rydG * w ) / x;
      else
         p = 0;
      if ( y != 0)
         w = ( rx * p0 + y1mrdG * w ) / y;
      else
         w = 0;
      if ( x < 0) x=0;
      if (y < 0) y=0;
      if (p < 0)
         p=0;
      else if ( p > 1)
         p = 1;
      if ( w < 0)
         w=0;
      else if (w > 1)
         w = 1;
      output[0] = x;
      output[1] = y;
      output[2] = p;
      output[3] = w;
      return output;
   }
   double[] overwinter(double xo, double yo){
      double[] out = new double[2];
      x = mu * xo;
      y = mu * yo;
      out[0] = x;
      out[1] = y;
      return out;
   }

   double[] migrate2(double x0, double y0, double p0, double w0, double s, double b) {
      double[] output2 = new double[4];
      double x1, p1, y1, w1, x2, x3, x4, p2, y2, y3, q;

      if ((s+g) != 0){
         x1 = (1 - r) * x0 +  s * r * (x0 + y0) / (s + g) ;
         y1 = (1 - r) * y0 +  g * r * (x0 + y0) / (s + g) ;
         if (x1 != 0)
            p1 = ((1 - r) * p0 * x0 +  s * r * p0 * x0 / (s + g) + s * r * w0 * y0 / (s + g)) / x1;
         else
            p1 = 0;
         if (y1 != 0)
            w1 = ((1 - r) * w0 * y0 +  g * r * w0 * y0 / (s + g) + g * r * p0 * x0 / (s + g)) / y1;
         else
            w1 = 0;
      }
      else {
         x1 = 0;
         y1 = 0;
         p1 = 0;
         w1 = 0;
      }
      x2 = x1 * f;
      y2 = y1 * f;
      q = 1 - p1;
      x3 = ( L * p1 * p1+ ( L*h + k * ( 1 - h)) * 2 * p1 * q + k * q * q) * x2;
      if (x3 != 0)
         p2 = (( L * p1 * p1 + (L * h + k * (1 - h )) * p1 * q ) * x2 ) / x3;
      else p2 = 0;
      if ( x3 > 0 )
         x4 = x3 * Math.pow((1 + a * x3), -b);
      else
         x4 = 0;
      if (( g > 0) && ( y2 > 0 ))
         y3 = y2 * Math.pow(( 1 + a * y2 / g), -b);
      else
         y3 = 0;

      output2[0] = x4 ;//after reproduction
      output2[1] = y3; //after reproduction
      output2[2] = p2;
      output2[3] = w1;
      return output2;
   }

}
