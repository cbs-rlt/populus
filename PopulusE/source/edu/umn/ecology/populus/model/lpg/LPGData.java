package edu.umn.ecology.populus.model.lpg;
import java.io.Serializable;

class LPGData extends Object implements Serializable {
	private static final long serialVersionUID = 8248674235327098587L;
	public double r;
	public double K;
	public double N;
	public double T;   

	LPGData( double paramNO, double paramK, double paramR, double paramT) {
		this.N = paramNO;
		this.K = paramK;
		this.r = paramR;
		this.T = paramT;
	}
}
