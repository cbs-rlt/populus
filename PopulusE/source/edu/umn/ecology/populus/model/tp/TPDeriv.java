/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.tp;
import edu.umn.ecology.populus.math.*;

public class TPDeriv extends Derivative {
	public static final int kr = 0;
	public static final int kL = 1;
	public static final int kT = 2;
	public static final int kS = 3;
	public static final int kR = 4;
	public static final int kV = 5;
	private final int type;
	private final double e, sigmaV, sigmaT, betaV, betaT, lambda, induction, tau, c, rho, p, q, alphaS, alphaR;

	@Override
	public void doDerivative( double time, double[] N, double[] dN ) {
		double T = N[kT];
		double L = N[kL];
		double r = N[kr];
		double V, S, R;
		double psiL, psiR, psiS;
		psiL = p* r /( q + r);
		psiS = psiL * ( 1 - alphaS);
		psiR = psiL * ( 1 - alphaR);
		switch( type ) {
		case TPParamInfo.ONE:
			dN[kr] = rho*( c - r ) - e*psiL*L;
			dN[kL] = psiL*L - induction*L - rho*L;
			dN[kT] = induction*betaT*L - sigmaT*L*T - rho*T;
			break;

		case TPParamInfo.TWO:
			S = N[kS];
			dN[kr] = rho * ( c - r)- e * psiL * (L + (1 - alphaS)* S);
			dN[kL] = psiL * L + lambda* sigmaT * S * T - ( induction + rho+ tau) * L;
			dN[kS] = (1-alphaS) * psiL * S - sigmaT * S * T + tau * L - rho * S;
			dN[kT] = induction * betaT * L + betaT * ( 1 - lambda) * sigmaT * S* T - sigmaT * L * T - rho * T;
			break;

		case TPParamInfo.THREE:
			S = N[kS];
			R = N[kR];
			dN[kr] = rho * ( c - r)- e * psiL * (L + (1 - alphaS)* S + (1 - alphaR)* R);
			dN[kL] = psiL * L + lambda * sigmaT * S * T - (induction + rho + tau) * L;
			dN[kS] = ( 1 - alphaS) * psiL * S - sigmaT * S * T + tau * L - rho * S;
			dN[kR] = ( 1 - alphaR) * psiL * R - rho * R;
			dN[kT] = induction*betaT*L + betaT*(1 - lambda)*sigmaT*T*S - sigmaT*T*L - rho*T;
			break;

		case TPParamInfo.FOUR:
			S = N[kS];
			V = N[4];//index four is used for V instead of index 5
			dN[kr] = rho * (c -r) - e * ( psiL * L + psiS * S);
					dN[kL] = psiL * L + lambda * sigmaT * S * T - sigmaV* L * V - (induction + rho + tau) * L;
					dN[kS] = psiS * S- sigmaT * S * T - sigmaV * S * V + tau * L - rho * S;
					dN[kT] = induction * betaT * L + betaT * (1-lambda) * sigmaT * S * T - sigmaT * L * T - rho * T;
					dN[4] = betaV * sigmaV * (S + L) * V - rho * V;
					break;

		case TPParamInfo.FIVE:
			S = N[kS];
			V = N[kV];
			R = N[kR];
			dN[kr] = rho*(c - r) - e*(psiL*L + psiS*S + psiR*R);
			dN[kL] = L*(psiL - sigmaV*V - induction - rho - tau) + lambda*S*sigmaT*T;
			dN[kT] = T*((1 - lambda)*betaT*S*sigmaT - L*sigmaT - rho) + induction*betaT*L;
			dN[kS] = S*(psiS - sigmaT*T - sigmaV*V - rho) + tau*L;
			dN[kR] = R*(psiR - rho);
			dN[kV] = V*((S + L)*betaV*sigmaV - rho);
			break;
		}
		return ;
	}

	public TPDeriv(int modelType, double e, double sigmaV, double sigmaT, double betaV, double betaT, double lambda,
			double induction, double tau, double c, double rho, double p, double q, double alphaS, double alphaR){

		if (modelType == TPParamInfo.ONE)
			this.numVariables = 3;
		else{
			if ( (modelType == TPParamInfo.THREE) || (modelType == TPParamInfo.FOUR)) {
				this.numVariables = 5;
			}
			else {
				if (modelType == TPParamInfo.TWO)
					this.numVariables = 4;
				else{
					if(modelType == TPParamInfo.FIVE)
						this.numVariables = 6;
				}
			}
		}
		this.type = modelType;
		this.e = e;
		this.sigmaV = sigmaV;
		this.sigmaT = sigmaT;
		this.betaT = betaT;
		this.betaV = betaV;
		this.c = c;
		this.alphaS = alphaS;
		this.alphaR = alphaR;
		this.q = q;
		this.rho = rho;
		this.induction = induction;
		this.p = p;
		this.lambda = lambda;
		this.tau = tau;
	}
}

