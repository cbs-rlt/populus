/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.md;
import edu.umn.ecology.populus.math.*;
/*
H = Number of Hosts, P = Number of parasites, W = Number of free-living infective stages
a = birth rate, b = death rate, beta = transmission parameter; alpha = parasite induced death rate
mu = natural mortality rate of adult parasites, lambda = rate of production of infective stages by adult parasite
c =  death rate of infective stage;

*/
public class MDDeriv extends Derivative {
   public static final int kH = 0;
   public static final int kP = 1;
   public static final int kW = 2;
   private final int type;
   private final double b, d, gamma, muA, alpha, beta, lambda, k;
   private final double theta, muP, sigma;

   public void doDerivative( double t, double[] N, double[] dN ) {
      double H = N[kH];
      double P = N[kP];
      double W, BWH;

      if(type != MDParamInfo.HPDD) W = N[kW];
      else                         W = 0;

      BWH = beta*W*H;

      switch( type ) {
         case MDParamInfo.HPWDD:
            dN[kH] = (b - d)*H - alpha*P;
            dN[kP] = BWH - (alpha + d + muP)*P - alpha*(k + 1)*P*P/(k*H);
            dN[kW] = lambda*P - gamma*W - BWH;
            break;

         case MDParamInfo.HPDD:
            dN[kH] = (b - d)*H - alpha*P;
            dN[kP] = lambda*H*P/(gamma/beta + H) - (muP + d + alpha)*P - alpha*(k + 1)*P*P/(k*H);
            break;

         case MDParamInfo.HPADD:
            if(H <= 1e-100 || W <= 1e-100 || P <= 1e-100) {
               dN[kH] = Double.NaN;
               return;
            }

            dN[kH]/*H*/ = (b - d)*H - (alpha + sigma)*P;
            dN[kW]/*A*/ = lambda*H*P/(gamma/beta + H) - (muA + d + theta)*W - alpha*P*W/H;
            dN[kP]/*P*/ = theta*W - (muP + d + alpha)*P - alpha*(k + 1)*P*P/(k*H);
            break;
      }
      return ;
   }

   public MDDeriv( int modelType, double a, double b, double alpha, double beta, double muA,
                   double muP, double lambda, double k, double gamma, double sigma, double theta) {
      this.type = modelType;
      if ( (modelType == MDParamInfo.HPWDD) || (type == MDParamInfo.HPADD) )  {
         this.numVariables = 3;
      } else {
         this.numVariables = 2;
      }
      this.b = a;
      this.d = b;
      this.gamma = gamma;
      this.k = k;
      this.alpha = alpha;
      this.beta = beta;
      this.muA = muA;
      this.lambda = lambda;
      this.theta = theta;
      this.sigma = sigma;
      this.muP = muP;
   }
}


/*
 densdep : begin
 case j of
 1: df := N_*a-N[1]*(b+beta*N[2])+gamma*N[3];
 2: df := N[2]*(beta*N[1]-(b+alpha+v));
 3: df := v*N[2]-(b+gamma)*N[3];
 end;
 end;
 freqdep : begin
 case j of
 1: if abs(N_) < 1e-6
 then df := 0
 else df := N_*a-N[1]*(b+beta*N[2]/N_)+gamma*N[3];
 2: if abs(N_) < 1e-6
 then df := 0
 else df := N[2]*((beta*N[1]/N_)-(b+alpha+v));
 3: df := v*N[2]-(b+gamma)*N[3];
 end;
 end;
 end {case tranmission}
 end
 else {Q^.Immunity = without  so model represented by 2 equations }
 begin {note that the equations switch to N and Y, so N[1]=N,N[2]=Y,N_=X}
 N_ := N[1]-N[2];
 case Q^.Transmission of
 densdep : begin
 case j of
 1: df := N[1]*(a-b)-alpha*N[2];
 2: df := N[2]*(beta*N_-(b+alpha+v));
 end;
 end;
 freqdep : begin
 case j of
 1: df := N[1]*(a-b)-alpha*N[2];
 2: if abs(N[1]) < 1e-6
 then df := 0
 else df := N[2]*((beta*N_/N[1])-(b+alpha+v));
 end;
 end;
 end {case tranmission}
 end;

 */
