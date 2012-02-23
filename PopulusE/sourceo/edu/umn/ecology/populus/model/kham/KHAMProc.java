package edu.umn.ecology.populus.model.kham;
import edu.umn.ecology.populus.math.*;

public class KHAMProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
   private double s, r, a1, a2;
   public static final int p2y = 0;
   public static final int t2y = 1;
   public static final int Dy  = 2;

   public void v( long time, double[] y ) {
      double oms, omt2, omp2, q1, q2, z1, z2, Iz1, Iz2;
      double G, H, S, q3, dt2;
      double p2, t2, D;
      p2 = y[p2y];
      t2 = y[t2y];
      D  = y[Dy];

      /*oms means "one minus s"*/
      oms  = 1.0 - s;
      omt2 = 1.0 - t2;
      omp2 = 1.0 - p2;

      if(1.0-s*t2 != 0.0)  q1 = 1.0/(1.0 - s*t2);
      else                 q1 = 0.0;
      q2 = t2*oms*q1;

      z1 = a1 - q2*(a1 - 1.0);
      z2 = 1.0 + q2*(a2 - 1.0);

      /*Iz1 means "inverse of z1"*/
      if(z1 != 0.0) Iz1 = 1.0/z1;
      else          Iz1 = 0.0;
      if(z2 != 0.0) Iz2 = 1.0/z2;
      else          Iz2 = 0.0;

      /*q3 is the large term that occurs twice in the formation of S*/
      q3 = (oms - a1)*Iz1 - (a2*oms - 1.0)*Iz2;

      G  = q1*(a1*omp2*Iz1 + p2*Iz2);
      H  = q1*oms*(omp2*Iz1 + a2*p2*Iz2);
      S  = q1*r*(D*D*  q3 +
                 D*    ((omt2*omp2 + t2*p2)*(oms*Iz1 + Iz2) + (omt2*p2 + t2*omp2)*(a1*Iz1 + a2*oms*Iz2)) +
                       t2*omt2*p2*omp2*q3);

      dt2 = 0.5*t2*(H - 1.0);

      if(t2*omt2 != 0.0){
         y[p2y] = p2 + dt2*D/(t2*omt2);
         if(y[p2y] > 1)      y[p2y] = 1.0;
         else if(y[p2y] < 0) y[p2y] = 0.0;
      } else
         y[p2y] = 0.0;

      y[Dy]  = D + 0.25*D*(G + H + G*H - 3.0) - 0.25*S*(1.0 + G*omt2 + H*t2);

      y[t2y] = t2 + dt2;
      if(y[t2y] > 1.0)       y[t2y] = 1.0;
      else if(y[t2y] < 0.0)  y[t2y] = 0.0;
   }

   public KHAMProc(double s, double r, double a1, double a2 ) {
      this.s = s;
      this.r = r;
      this.a1 = a1;
      this.a2 = a2;
      this.numVariables = 3;
   }
}