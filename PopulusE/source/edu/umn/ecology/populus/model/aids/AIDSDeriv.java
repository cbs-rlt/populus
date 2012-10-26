package edu.umn.ecology.populus.model.aids;

import edu.umn.ecology.populus.math.*;
import java.util.Random;

/**
 * the unique thing about this model is that the number of variables to be integrated
 * can change with each step. the Integrator class has been modified to handle this. the
 * postDerivative method will be called after each integration step to modify the
 * number of variables if necessary.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class AIDSDeriv extends Derivative {
   private int kMaxStrains;
   public static final double kTooSmall = 1e-15;

   public static final int kY = 0;
   public static final int kZ = 1;
   public static final int kVi= 2;
   public static final int kXi= 3;
   //after 1, evens will be for viruses and odds will be for strain-specific CD4+

   private double vi0, u, aveR, rp, Q, K, d, kp, k, s, p, dt;
   private double v;
   double[] r = new double[1];
   private Random rand;
   //this is so that the "max strains" is detected once
   public boolean maxStrains = false;

   /** Time when virus is eliminated */
   private double virusElimTime = -1.0;
   private double virusElimVal = -1.0;
   /** Time when CD4+ cells are dead */
   private double cd4ElimTime = -1.0;
   private double cd4ElimVal = -1.0;
   /** Time when max strains occurs, and we don't generate new strains */
   private double maxStrainsTime = -1.0;
   private double maxStrainsVal = -1.0;

   public void doDerivative( double t, double[] N, double[] dN ) {
      double y = N[kY];
      double z = N[kZ];
      double si, pi, rip;

    //dy/dt  = K - y*(d+u*v)
      dN[kY] = K - y*(d+u*v);
    //dz/dt  = v*(k'*y - u*z)
      dN[kZ] = v*(kp*y - u*z);

      for(int i=2; i<N.length; i++){
         if(i%2 == 0){
            si = s*r[(i-kVi)/2];
            pi = p*r[(i-kVi)/2];
            rip = rp*r[(i-kVi)/2];
          //dvi/dt= vi  *(ri*y   + ri' - si*z + pi*xi)
            dN[i] = N[i]*(r[(i-kVi)/2]*y + rip - si*z - pi*N[i+1]);
         } else {
          //dxi/dt= k*vi*y     - u*v*xi
            dN[i] = k*N[i-1]*y - u*v*N[i];
         }
      }
      return;
   }

   public double[] postDerivative(double[] N, double t){
      int l = N.length;
      int n = (l-2)/2;
      String message;
      t = NumberMath.roundSig(t,10,0);
      v = 0;
      for(int i=2; i<N.length; i += 2){
         v += N[i];
      }

      if(v <= kTooSmall) {
         message = "Virus eliminated (v = "+v+") when t = "+t;
         //JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
         edu.umn.ecology.populus.fileio.Logging.log(message+"\n");
         virusElimTime = t;
         virusElimVal = v;
         return null;
      }
      if(N[kY] <= kTooSmall) {
         message = "CD4+ cells are dead (y = "+N[kY]+") when t = "+t;
         //JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
         edu.umn.ecology.populus.fileio.Logging.log(message+"\n");
         cd4ElimTime = t;
         cd4ElimVal = N[kY];
         return null;
      }
      if(!maxStrains && rand.nextDouble()<Q*v*dt){
         if(n >= kMaxStrains){
            message = "Max number of strains, "+kMaxStrains+" when t = "+t;
            //JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Message", JOptionPane.PLAIN_MESSAGE);
            edu.umn.ecology.populus.fileio.Logging.log(message);
            maxStrains = true;
            maxStrainsTime = t;
            maxStrainsVal = kMaxStrains;
            return N;
         }
         //we have a new virus mutant
         N = addElement(N,vi0);
         N = addElement(N,0.0d);
         r = addElement(r,getReplicateRate());
         numVariables += 2;
      }
      return N;
   }

   public double getVirusEliminatedTime() {
      return virusElimTime;
   }
   public double getCD4EliminatedTime() {
      return cd4ElimTime;
   }
   public double getMaxStrainsTime() {
      return maxStrainsTime;
   }
   public double getVirusEliminatedVal() {
      return virusElimVal;
   }
   public double getCD4EliminatedVal() {
      return cd4ElimVal;
   }
   public double getMaxStrainsVal() {
      return maxStrainsVal;
   }

   /**
    * the lecture notes say "exponential distribution." does this do the same thing?
    * @return
    */
   double getReplicateRate(){
      double d = 2*aveR*rand.nextDouble();
//      edu.umn.ecology.populus.fileio.Logging.log("d is "+d);
      return d;
   }

   double[] addElement(double[] a, double e){
      double[] newA = new double[a.length+1];
      System.arraycopy(a,0,newA,0,a.length);
      newA[a.length] = e;
      return newA;
   }

   public AIDSDeriv(double v0, double vi0, double u, double aveR, double rp, double Q,
          double K, double d, double kp, double k, double s, double p, double dt, long rS,
          int numStrains) {
      this.vi0 = vi0;
      this.u = u;
      this.aveR = aveR;
      this.rp = rp;
      this.Q = Q;
      this.K = K;
      this.d = d;
      this.kp = kp;
      this.k = k;
      this.s = s;
      this.p = p;
      this.dt = dt;
      this.v = v0;
      rand = new Random(rS);
      numVariables = 4;
      maxStrains = false;
      kMaxStrains = numStrains;
      r[0] = getReplicateRate();
   }
}