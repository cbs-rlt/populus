/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aidsbasic;

import edu.umn.ecology.populus.math.Derivative;

public class AIDSBASICDeriv extends Derivative {
    public static final int kY = 1;
    public static final int kV = 2;
    public static final int kX = 0;
    private final double lambda;
    private final double d;
    private final double beta;
    private final double a;
    private final double k;
    private final double u;

    @Override
    public void doDerivative(double t, double[] N, double[] dN) {
        double x = N[kX];
        double y = N[kY];
        double v = N[kV];

        dN[kX] = lambda - d * x - beta * x * v;
        dN[kY] = beta * x * v - a * y;
        dN[kV] = k * y - u * v;

    }

    public AIDSBASICDeriv(double lambda, double d, double k, double a, double beta, double u) {
        this.lambda = lambda;
        this.d = d;
        this.k = k;
        this.beta = beta;
        this.a = a;
        this.u = u;
        this.numVariables = 3;
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

