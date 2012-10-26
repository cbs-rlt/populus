package edu.umn.ecology.populus.model.sotl;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class SOTLParamInfo implements BasicPlot {
   protected static final int Pvst    = 0;
   protected static final int pvst    = 1;
   protected static final int Dvst    = 2;
   protected static final int WBARvst = 3;
   private String yCap1 = "("+ColorScheme.getColorString( 0 ) + " <i>P<sub>AB</>, " + ColorScheme.getColorString(1) + "<i>P<sub>Ab</>, "+ ColorScheme.getColorString( 2 ) + "<i>P<sub>aB</>, " + ColorScheme.getColorString(3) + "<i>P<sub>ab </>) " ;
   private String yCap2 = ColorScheme.getColorString( 0 ) + " <i>p<sub>A</>, " + ColorScheme.getColorString( 1 ) + "<i>p<sub>B</>, " + ColorScheme.getColorString( 2 ) + "<i>p<sub>a</>, " + ColorScheme.getColorString(3) + "<i>p<sub>b </>) ";
   final double[][] wij;
   final double r;
   int plotType, gens;
   double[] x;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = null;
      double[][][] points;
      double[][] data = new double[gens+1][];
      double[] wbar = new double[gens+1];
      double[] D = new double[gens+1];
      double t12, t13, t14, t23, t24, t34;

      for(int i=0; i<=gens; i++){
         data[i] = (double[])x.clone();
         D[i] = x[0]*x[3]-x[1]*x[2];

         t12 = x[0]*x[1]*wij[0][1];
         t13 = x[0]*x[2]*wij[1][0];
         t14 = x[0]*x[3]*wij[1][1];
         t23 = x[1]*x[2]*wij[1][1];
         t24 = x[1]*x[3]*wij[1][2];
         t34 = x[2]*x[3]*wij[2][1];

         x[0] = t12 + t13 + t14*(1.0-r) + t23*r                   + x[0]*x[0]*wij[0][0];
         x[1] = t12       + t14*r       + t23*(1.0-r) + t24       + x[1]*x[1]*wij[0][2];
         x[2] =       t13 + t14*r       + t23*(1.0-r)       + t34 + x[2]*x[2]*wij[2][0];
         x[3] =             t14*(1.0-r) + t23*r       + t24 + t34 + x[3]*x[3]*wij[2][2];

         wbar[i] = x[0] + x[1] + x[2] + x[3];

         for(int j=0; j<x.length; j++)
            x[j] /= wbar[i];
      }

      switch (plotType) {
         case Pvst:
            points = new double[4][2][gens+1];
            for(int i=0; i<=gens; i++)
               for(int j=0; j<4; j++){
                  points[j][0][i] = i;
                  points[j][1][i] = data[i][j];
               }

            bpi = new BasicPlotInfo(points,"Two-Locus Selection: Gametic Frequencies","Generations<b>( <i>t</i> )</b>","Gamete Frequency "+ yCap1);
            bpi.setIsFrequencies(true);
            break;
         case pvst:
            points = new double[4][2][gens+1];
            for(int i=0; i<=gens; i++){
               for(int j=0; j<4; j++)
                  points[j][0][i] = i;
               points[0][1][i] = data[i][0]+data[i][1];//A
               points[1][1][i] = data[i][0]+data[i][2];//B
               points[2][1][i] = data[i][2]+data[i][3];//a
               points[3][1][i] = data[i][1]+data[i][3];//b
            }
            bpi = new BasicPlotInfo(points,"Two-Locus Selection: Allelic Frequencies","Generations<b>( <i>t</i> )</>","Allelic Frequency "+ " ( "+ yCap2);
            break;
         case Dvst:
            points = new double[1][2][gens+1];
            for(int i=0; i<=gens; i++){
               points[0][0][i] = i;
               points[0][1][i] = D[i];
            }
            String ycap = " Gametic Disequilibium  (  "+ColorScheme.getColorString(0)+"<i><b>D</> ) ";
            bpi = new BasicPlotInfo(points,"Two-Locus Selection: Gametic Disequilibria","Generations<b>( <i>t</i> )</>", ycap);
            break;
         case WBARvst:
            points = new double[1][2][gens+1];
            for(int i=0; i<=gens; i++){
               points[0][0][i] = i;
               points[0][1][i] = wbar[i];
            }
            String ycap2 = "Mean Fitness ( "+ColorScheme.getColorString(0)+"<i><b>w\u0305</b></i></font> )";
            bpi = new BasicPlotInfo(points,"Two-Locus Selection: Mean Fitness","Generations<b>( <i>t</i> )</>",ycap2);
            bpi.setYMin(0.0d);
            break;
      }
      return bpi;
   }

   public SOTLParamInfo(double[][] fitnesses, double[] freqs, double R, int plotType, int gens) {
      this.wij = fitnesses;
      this.r = R;
      this.x = freqs;
      this.plotType = plotType;
      this.gens = gens;
   }
}
