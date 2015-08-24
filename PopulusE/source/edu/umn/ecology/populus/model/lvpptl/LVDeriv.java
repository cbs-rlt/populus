/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import edu.umn.ecology.populus.math.*;

public class LVDeriv extends Derivative {
   private final double d2, g;
   private final double k;
   private final double r1, C;
   private final double a, Th;
   private boolean isType2;
   private boolean isDD;

   public LVDeriv( double r1, double c1, double r2, double c2,
                   boolean isDD, double k,
                   boolean isType2, double a, double Th ) {
      this.r1 = r1;
      this.d2 = r2;
      this.C = c1;
      this.g = c2;
      this.isDD = isDD;
      this.k = k;
      this.isType2 = isType2;
      this.a = a;
      this.Th = Th;
      this.numVariables = 2;
   }

   public void doDerivative( double t, double[] N, double[] dN ) {
      double ddCorrection = isDD ? ((k - N[0]) / k) : 1;
      double type2Correction;
      type2Correction = isType2 ? (a / (1 + a*N[0]*Th)) : C;
      //dN[0] is N'
      dN[0] = r1 * N[0] * ddCorrection - type2Correction * N[0] * N[1];
      //dN[1] is P'
      dN[1] = -d2 * N[1] + g * type2Correction * N[0] * N[1];
      return;
   }

}
