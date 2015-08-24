/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hhap;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;

public class HHAPParamInfo implements BasicPlot {
   public static final int Allele2  = 0;
   public static final int Allele3  = 1;
   public static final int DeFin    = 2;

   double[] h, p;
   final double s,t,mh,mp,ph;
   final int gens,genInt,plotType;

   BasicPlotInfo twoAllele(double h1, double p1){
      double h2, p2, p10, w, denom;
      int i=0,k=0;
      double[][] data = new double[2][10];

      while(i<=gens){
         data[0] = Routines.addDouble(data[0],p1,k);
         data[1] = Routines.addDouble(data[1],h1,k);

         h2 = 1.0 - h1;
         h1 -= mh*(h1 - h2);
         h2 = 1.0 - h1;

         p10 = p1;

         for(int j=0; j<ph; j++){
            p2 = 1.0 - p1;
            p1 -= mp*(p1 - p2);
            p2 = 1.0 - p1;
            w = p1*(h1 + (1.0 - t)*h2);

            denom = w + p2*(h2 + (1.0 - t)*h1);

            if(denom != 0.0) p1 = w/denom;
            else             p1 = 0.0;
            i++;
         }

         denom = s*(h1 + p10 - 2.0*h1*p10 - 1) + 1;
         if(denom != 0.0) h1 *= (1.0 - s*p10)/denom;
         else             h1 =  0.0;

         k++;
      }

      double[][][] points = new double[1][2][k];
      for(int j=0; j<k; j++){
         points[0][0][j] = data[0][j];
         points[0][1][j] = data[1][j];
      }
      String xcap = "Frequency of Parasite Allele \"a\"";
      String ycap = "Frequency of Host Allele \"a\" ";
      String mcap = "Seger/Hamilton Host-Parasite Model";
      BasicPlotInfo bpi = new BasicPlotInfo(points,mcap,xcap,ycap);
      bpi.setIsDiscrete(true);
      //bpi.setLineStyle(0,BasicPlotInfo.NO_LINE);
      bpi.setLineWidth(0,1);
      bpi.setSymbolSize(0,4);
      return bpi;
   }

   void migration(double m, double[] a){
      double t1,t2;
      double[] hp = (double[])a.clone();

      t1 = 1.0 - m;
      t2 = 0.5*m;

      a[0] = t1*hp[0] + t2*(hp[1] + hp[2]);
      a[1] = t1*hp[1] + t2*(hp[0] + hp[2]);
      a[2] = t1*hp[2] + t2*(hp[0] + hp[1]);
   }

   void hostFreqs(double[] hp, double[] pp){
      double s1 = 1.0 - s, whbar;
      double[] hterm = new double[3];

      hterm[0] = hp[0]*(s1*pp[0] + pp[1] + pp[2]);
      hterm[1] = hp[1]*(pp[0] + s1*pp[1] + pp[2]);
      hterm[2] = hp[2]*(pp[0] + pp[1] + s1*pp[2]);

      whbar = hterm[0] + hterm[1] + hterm[2];

      if(whbar == 0){
         hp[0] = 0;
         hp[1] = 0;
         hp[2] = 0;
      } else {
         hp[0] = hterm[0]/whbar;
         hp[1] = hterm[1]/whbar;
         hp[2] = hterm[2]/whbar;
      }
   }

   void paraFreqs(double[] hp, double[] pp){
      double t1 = 1.0 - t, wpbar;
      double[] pterm = new double[3];

      pterm[0] = pp[0]*(hp[0] + t1*(hp[1] + hp[2]));
      pterm[1] = pp[1]*(hp[1] + t1*(hp[0] + hp[2]));
      pterm[2] = pp[2]*(hp[2] + t1*(hp[0] + hp[1]));

      wpbar = pterm[0] + pterm[1] + pterm[2];

      if(wpbar == 0){
         pp[0] = 0;
         pp[1] = 0;
         pp[2] = 0;
      } else {
         pp[0] = pterm[0]/wpbar;
         pp[1] = pterm[1]/wpbar;
         pp[2] = pterm[2]/wpbar;
      }

   }

   BasicPlotInfo threeAllele(){
      double[][][] data = new double[2][3][10];
      double[] p0;
      int i=0;

      for(; i<=gens; i++){
         for(int j=0; j<3; j++){
            data[0][j] = Routines.addDouble(data[0][j],p[j],i);
            data[1][j] = Routines.addDouble(data[1][j],h[j],i);
         }

         if(mh > 0) migration(mh,h);
         p0 = (double[])p.clone();
         for(int j=0; j<ph; j++){
            if(mp > 0.0) migration(mp,p);
            paraFreqs(h,p);
         }
         hostFreqs(h,p0);
      }

      double[][][] points = new double[2][3][i];
      for(int j=0; j<i; j++){
         for(int k=0; k<3; k++){
            points[0][k][j] = data[0][k][j];
            points[1][k][j] = data[1][k][j];
         }
      }
      return new BasicPlotInfo(points,"Seger/Hamilton Host-Parasite Model","<i>h</><sub>1</sub>, <i>p</><sub>1</sub>",
                               "<i>h</><sub>2</sub>, <i>p</><sub>2</sub>","<i>h</><sub>3</sub>, <i>p</><sub>3</sub>");
   }

   public BasicPlotInfo getBasicPlotInfo() {
      if(plotType == Allele2)
         return twoAllele(h[0],p[0]);
      else{
         BasicPlotInfo bpi = threeAllele();
         if(plotType == DeFin){
            bpi.setGraphType(BasicPlotInfo.kDeFinetti);
         } else {
         }
         bpi.setIsLive(true);
         return bpi;
      }
   }

   public HHAPParamInfo(double[] h, double[] p, double s, double t, double mh, double mp, double ph,
                        int gens, int genInt, int plotType) {
      this.h = h;
      this.p = p;
      this.s = s;
      this.t = t;
      this.mh = mh;
      this.mp = mp;
      this.ph = ph;
      this.gens = gens;
      this.genInt = genInt;
      this.plotType = plotType;
   }
}