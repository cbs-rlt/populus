/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.math;

abstract public class DiscreteProc {
	protected int numVariables = -1;

	/**
	 * gen is current generation to get  <BR>
	 * y is both input and output
	 */

	public abstract void v( long gen, double[] y );

	/********************/

	/* ACCESSOR METHODS */

	/********************/

	public int getNumVariables() {
		return numVariables;
	}

	public DiscreteProc() {

	}
}
