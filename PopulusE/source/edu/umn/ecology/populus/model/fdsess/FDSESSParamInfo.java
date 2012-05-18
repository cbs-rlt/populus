package edu.umn.ecology.populus.model.fdsess;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import java.awt.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;
import javax.swing.JOptionPane;
import edu.umn.ecology.populus.core.DesktopWindow;

public class FDSESSParamInfo implements BasicPlot {
   public static final int A   = 0;
   public static final int B   = 1;
   public static final int C   = 2;
   public static final int D   = 3;

   final double[] e;
   final int invader,resident,gens;
   double[] p = new double[4];

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bpi = null;
      double[][][] points = new double[2][2][gens+1];
      double[] w = new double[4];
      double wbar;

      for(int i=0; i<=gens; i++){
         points[0][0][i] = i;
         points[1][0][i] = i;
         points[0][1][i] = p[invader];
         points[1][1][i] = p[resident];

         w[0] = 10.0 + p[0]*e[0] + p[1]*e[1] + p[2]*e[4]*e[0] + p[2]*e[5]*e[1] + p[3]*e[6]*e[0] + p[3]*e[7]*e[1];
         w[1] = 10.0 + p[0]*e[2] + p[1]*e[3] + p[2]*e[4]*e[2] + p[2]*e[5]*e[3] + p[3]*e[6]*e[2] + p[3]*e[7]*e[3];
         w[2] = e[4]*w[0] + e[5]*w[1];
         w[3] = e[6]*w[0] + e[7]*w[1];

         wbar = p[0]*w[0] + p[1]*w[1] + p[2]*w[2] + p[3]*w[3];

         if(wbar != 0.0){
            p[invader]  *= w[invader]/wbar;
            p[resident] *= w[resident]/wbar;
         } else {
            p[invader] = 0.0;
            p[resident] = 0.0;
         }
      }
      String a = ColorScheme.getColorString(0)+"<b><i>"+(char)('A'+invader)+"</>, ";
      String b = ColorScheme.getColorString(1)+"<b><i>"+(char)('A'+resident)+"</>";
      String ycap = "Frequency ( "+a+b+" )";
      bpi = new BasicPlotInfo(points,"Frequency vs Time","Generations ( <b><i>t</> )",ycap);
      bpi.setIsFrequencies(true);
      return bpi;
   }

   public FDSESSParamInfo(double[] e, int i, int r, double pi, int gens) {
      this.e = e;
      this.invader = i;
      this.resident = r;
      this.gens = gens;
      p[invader]  = pi;
      p[resident] = 1.0 - pi;
   }
}