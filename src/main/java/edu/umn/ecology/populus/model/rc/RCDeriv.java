/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rc;

import edu.umn.ecology.populus.math.Derivative;

public class RCDeriv extends Derivative {
    public static final int kR1 = 1;//r1
    public static final int kR2 = 3;//r2
    public static final int kN1 = 0;//n1
    public static final int kN2 = 2;//n2
    final private int modeltype;
    final private double v, w, c1, c2, e11, e22, e21, e12;
    final private double a11, a21, a12, a22, b11, b22, b12, b21;

    @Override
    public void doDerivative(double t, double[] N, double[] dN) {
        double phi11, phi21, phi12, rho, phi22;
        double r1 = N[kR1];
        double n1 = N[kN1];
        double n2;
        double r2;
        if ((r1 + b11) != 0) {
            phi11 = a11 * r1 / (r1 + b11);
        } else {
            phi11 = 0;
        }

        // i = 2, j = 1
        if ((r1 + b21) != 0) {
            phi21 = a21 * r1 / (r1 + b21);
        } else {
            phi21 = 0;
        }


		rho = w/ v;
		switch( modeltype ) {
		case RCParamInfo.equable_11:

			dN[kR1] = rho * (c1- r1) - n1* phi11;
			dN[kN1] = n1 * ( phi11/e11 -rho);
			break;

		case RCParamInfo.equable_21:
			n2 =N[kN2];
			//i=2, j=1
					dN[kR1] = rho * (c1- r1) - n1* phi11 - n2 * phi21;
					dN[kN1] = n1 * ( phi11/e11 -rho);
					dN[kN2] = n2 * ( phi21/e21 -rho);
					break;

		case RCParamInfo.equable_22:
			r2 = N[kR2];
			n2 =N[kN2];
			//i = 1, j = 2
			if ((r2+ b12) != 0) {
				phi12= a12 * r2/(r2 + b12);
			} else {
				phi12 = 0;
			}
			// i = 2, j=2
			if ((r2+ b22) != 0) {
				phi22= a22 * r2/(r2 + b22);
			} else {
				phi22 = 0;
			}
			//i=2, j=2
			dN[kR1] = rho * (c1- r1) - n1* phi11 - n2 * phi21;
			dN[kR2] = rho * (c2- r2) - n1* phi12 - n2 * phi22;
			dN[kN1] = n1 * ( phi11/e11 + phi12/e12 -rho);
			dN[kN2] = n2 * ( phi21/e21+ phi22/e22 -rho);
			break;

		case RCParamInfo.seasonal_11:
			dN[kR1] = - n1* phi11;
			dN[kN1] = n1 * ( phi11/e11);
			break;
		case RCParamInfo.seasonal_21:
			//i=2, j=1
			n2 =N[kN2];
			dN[kR1] = - n1* phi11 - n2 * phi21;
			dN[kN1] = n1 * phi11/e11;
			dN[kN2] = n2 * phi21/e21;
			break;
		case RCParamInfo.seasonal_22:
			//i=2, j=2
			r2 = N[kR2];
			n2 =N[kN2];
			//i = 1, j = 2
			if ((r2+ b12) != 0) {
				phi12= a12 * r2/(r2 + b12);
			} else {
				phi12 = 0;
			}
			// i = 2, j=2
			if ((r2+ b22) != 0) {
				phi22= a22 * r2/(r2 + b22);
			} else {
				phi22 = 0;
			}
			dN[kR1] = - n1* phi11 - n2 * phi21;
			dN[kR2] = - n1* phi12 - n2 * phi22;
			dN[kN1] = n1 * ( phi11/e11 + phi12/e12);
			dN[kN2] = n2 * ( phi21/e21+ phi22/e22);
			break;

		}
    }


    public RCDeriv(int modelType, double time, double v, double w, double c1, double c2, double e11, double e12, double e21, double e22, double a11, double a12, double a21, double a22, double b11, double b12, double b21, double b22, double d, int tI) {
        this.modeltype = modelType;
        this.v = v;
        this.w = w;
        this.c1 = c1;
        this.c2 = c2;
        this.e11 = e11;
        this.e12 = e12;
        this.e22 = e22;
        this.e21 = e21;
        this.a11 = a11;
        this.a22 = a22;
        this.a21 = a21;
        this.a12 = a12;
        this.b11 = b11;
        this.b22 = b22;
        this.b21 = b21;
        this.b12 = b12;
        if ((modelType == RCParamInfo.equable_11) || (modelType == RCParamInfo.seasonal_11))
            this.numVariables = 2;
        else {
            if ((modelType == RCParamInfo.equable_21) || (modelType == RCParamInfo.seasonal_21))
                this.numVariables = 3;
            else
                this.numVariables = 4;
        }

    }
}

