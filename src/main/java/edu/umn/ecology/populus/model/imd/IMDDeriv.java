/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.imd;

import edu.umn.ecology.populus.math.Derivative;

public class IMDDeriv extends Derivative {
    public static final int kY = 1;
    public static final int kZ = 2;
    public static final int kX = 0;
    private final int type;
    private final double a;
    private final double b;
    private final double alpha;
    private final double beta;
    private final double nu;
    private final double gamma;

    @Override
    public void doDerivative(double t, double[] N, double[] dN) {
        double x = N[kX];
        double y = N[kY];
        double z;
        double n;
        double bsi;
        if ((type == IMDParamInfo.SIRDD) || (type == IMDParamInfo.SIRFD)) {
            z = N[kZ];
            n = x + y + z;
        } else {
            z = 0; //Just initializing it for compile errors - it is never used
            n = x + y;
        }
        if ((type == IMDParamInfo.SIDD) || (type == IMDParamInfo.SIRDD)) {
            bsi = beta * x * y;
        } else {

			//Worry about |n| < 10^-6 ?
			if( Math.abs( n ) < 1e-6 ) {
				bsi = 0;
			}
			else {
				bsi = beta * x * y / n;
			}
		}
		switch( type ) {
		case IMDParamInfo.SIRDD:
		case IMDParamInfo.SIRFD:
			dN[kX] = a * ( x + y + z ) - b * x - bsi + gamma * z;
			dN[kY] = bsi - ( alpha + b + nu ) * y;
			dN[kZ] = nu * y - ( b + gamma ) * z;
			break;

		case IMDParamInfo.SIDD:
		case IMDParamInfo.SIFD:
			dN[kX] = a * ( x + y ) + nu * y - b * x - bsi;
			dN[kY] = bsi - ( alpha + b + nu ) * y;
			break;
		}
		return ;
	}

    public IMDDeriv(int modelType, double a, double b, double alpha, double beta, double nu, double gamma) {
        this.type = modelType;
        if ((modelType == IMDParamInfo.SIRDD) || (modelType == IMDParamInfo.SIRFD)) {
            this.numVariables = 3;
        } else {
            this.numVariables = 2;
        }
        this.a = a;
        this.b = b;
        this.alpha = alpha;
        this.beta = beta;
        this.nu = nu;
        this.gamma = gamma;
    }
} /*
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

