/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dig;
import java.io.Serializable;

class DIGData extends Object implements Serializable {
	private static final long serialVersionUID = 2469088803649153178L;

	//TODO - why are these static??
	public static int selection;
	public static double gensPF;
	public static boolean isContinuous;
	double rPF;
	double nOPF;
	double lambdaPF;

	DIGData(boolean a, int b, double c, double d, double e, double f) {
		isContinuous = a;
		selection = b;
		gensPF = c;
		lambdaPF = d;
		nOPF = e;
		rPF = f;
	}
}
