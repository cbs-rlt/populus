/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvc;
import edu.umn.ecology.populus.math.*;

public class LVCDeriv extends Derivative {
   private final double r2, K2, beta;
   private final double r1, K1, alpha;

   public void doDerivative( double t, double[] N, double[] dN ) {
      dN[0] = r1 * N[0] * ( K1 - N[0] - alpha * N[1] ) / K1;
      dN[1] = r2 * N[1] * ( K2 - N[1] - beta * N[0] ) / K2;
      return ;
   }

   public LVCDeriv( double r1, double K1, double alpha, double r2, double K2, double beta ) {
      this.r1 = r1;
      this.K1 = K1;
      this.alpha = alpha;
      this.r2 = r2;
      this.K2 = K2;
      this.beta = beta;
      this.numVariables = 2;
   }
}
