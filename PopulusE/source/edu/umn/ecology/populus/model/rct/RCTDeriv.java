/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rct;
import edu.umn.ecology.populus.math.*;

public class RCTDeriv extends Derivative {
   final private RCTVariableIndex vars;
   final private int modeltype;
   final private double[] a;
   final private double[] m, s, r;
   final private double[][] k, c;
   public void doDerivative( double t, double[] N, double[] dN ) {
      double e, curre;
      int jex = 0;

      for (int j=vars.getResNum()-1; j>=0; j--) {
         int jidx = vars.getResIdx(j);
         dN[jidx] = 0.0;
      }

      for (int i=vars.getSpecNum()-1; i>=0; i--) {
         //Calculate dN for species
         if (modeltype == RCTParamInfo.SWITCHING)
            e = Double.MIN_VALUE;
         else
            e = Double.MAX_VALUE;
         for (int j=vars.getResNum()-1; j>=0; j--) {
            double Rj = N[vars.getResIdx(j)];
            double denom = Rj + k[i][j];
            if (denom == 0.0)
               curre = 0.0;
            else
               curre = N[vars.getSpecIdx(i)] * (r[i]*Rj/denom - m[i]);

            //We record which resource we want to eat (jex)
            //and what our growth is for that resource
            if (modeltype == RCTParamInfo.SWITCHING) {
               if (curre > e) {
                  e = curre;
                  jex = j;
               }
            } else {
               if (curre < e) {
                  e = curre;
                  jex = j;
               }
            }
         }

         int iidx = vars.getSpecIdx(i);
         //Overwrite species growth
         dN[iidx] = e;

         if (modeltype == RCTParamInfo.SWITCHING) {
            //Record a loss for the one resource eaten
            int jidx = vars.getResIdx(jex);
            dN[jidx] -= c[i][jex] * (dN[iidx] + m[i] * N[iidx]);
         } else {
            //Nibble off of all resources
            for (int j=vars.getResNum()-1; j>=0; j--) {
               int jidx = vars.getResIdx(j);
               dN[jidx] -= c[i][j] * (dN[iidx] + m[i] * N[iidx]);
            }
         }
      }

      //These are the new resources into the system
      for (int j=vars.getResNum()-1; j>=0; j--) {
         int jidx = vars.getResIdx(j);
         dN[jidx] += a[j] * (s[j] - N[jidx]);
      }
   }

   public RCTDeriv(int modelType, int speciesNum, int resourceNum, double[] a, double[] r,
                   double[] m, double[][] k, double[][] c, double[] s, boolean is3D) {
      this.modeltype = modelType;
      this.a = a;
      this.s = s;
      this.r = r;
      this.m = m;
      this.c = c;
      this.k = k;
      this.vars = new RCTVariableIndex(speciesNum, resourceNum);
      this.numVariables = vars.getTotalNum();
   }
}