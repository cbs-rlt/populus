/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ppplot;
import edu.umn.ecology.populus.plot.*;

//import edu.umn.ecology.populus.math.*;

public abstract class PPPlotInfo extends BasicPlotInfo {

	/**
	 *
	 */
	private static final long serialVersionUID = 4236948640811328005L;

	public BasicPlotInfo getBasicPlotInfo() {
		BasicPlotInfo bp = null;
		return bp;
	}

	public PPPlotInfo() {

	}

	/**
	 * Returns the lines that make up the isoclines.
	 * Usually should be double[2][2][2], but the first
	 * 2 may vary
	 */

	protected abstract double[][][] getIsoclines();
}
