/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
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
