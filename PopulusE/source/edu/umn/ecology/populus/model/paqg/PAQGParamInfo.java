package edu.umn.ecology.populus.model.paqg;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class PAQGParamInfo implements BasicPlot {
   protected static final int Pvst     = 0;
   protected static final int h2vst    = 1;
   protected static final int WBARvsP  = 2;
   protected static final int WBARvst  = 3;
   int plotType, gens;
   final double[] w;
   double p, ve;

   public BasicPlotInfo getBasicPlotInfo() {
      double a = (w[0] - w[2])/2;
      double d = -(w[0] - 2.0*w[1] + w[2])/2;
      double[] freqs = new double[gens+1];
      double[] h2 = new double[gens+1];
      double[] wbarA = new double[gens+1];
      double[] tArray = new double[gens+1];
      double q,t,wbar,va,vp;

      for(int i=0; i<=gens; i++){
         freqs[i] = p;
         tArray[i] = i;
         q = 1.0 - p;
         t = 2.0*p*q;
         wbar = p*p*w[0] + t*w[1] + q*q*w[2];
         va = t*(a+d*(q-p))*(a+d*(q-p));
         vp = va + ve + t*t*d*d;

         if((vp==0 || ve==0) && (p==0 || p==1))
            h2[i] = 0;
         else
            h2[i] = va/vp;

         if(wbar == 0.0)
            p = 0.0;
         else
            p = (p*p*w[0] + p*q*w[1])/wbar;
         wbarA[i] = wbar;
      }

      String mcap = "Population and Quantitative Genetics";
      String xcap = "Generations ( <b><i>t</> )";
      String ycap = "Mean Fitness ( "+ColorScheme.getColorString(0)+"<b><i>w\u0305</> )";
      double[][][] points = null;
      switch(plotType){
         case Pvst:
            points = new double[1][2][gens+1];
            points[0][0] = tArray;
            points[0][1] = freqs;
            ycap = "Allelic Frequency (  "+ColorScheme.getColorString(0)+"<b><i>p</> )";
            break;
         case h2vst:
            points = new double[1][2][gens+1];
            points[0][0] = tArray;
            points[0][1] = h2;
            ycap = "Heritability ( "+ColorScheme.getColorString(0)+"<b><i>h<sup>2</> )";
            break;
         case WBARvsP:
            points = new double[1][2][gens+1];
            points[0][0] = freqs;
            points[0][1] = wbarA;
            xcap = "Allelic Frequency (<b><i>p</> )";
            break;
         case WBARvst:
            points = new double[1][2][gens+1];
            points[0][0] = tArray;
            points[0][1] = wbarA;
            break;
      }
      BasicPlotInfo bpi = new BasicPlotInfo(points,mcap,xcap,ycap);
      bpi.setIsDiscrete(true);
      if(plotType == Pvst || plotType == h2vst)
         bpi.setIsFrequencies(true);
      return bpi;
   }

   public PAQGParamInfo(double[] w, double p, double ve, int gens, int plotType) {
      this.w = w;
      this.p = p;
      this.ve = ve;
      this.plotType = plotType;
      this.gens = gens;
   }
}
