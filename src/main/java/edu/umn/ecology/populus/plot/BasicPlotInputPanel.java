/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.edwin.*;

/**
 * Every model extends this class to implement their personalized input screen.
 * if more flexibility is required, then the model could extend ModelPanel
 * instead...
 *
 * Of course, getPlotParamInfo is to be overridden with something that looks
 * like (from APPD Functional Responses):
 *
 * public BasicPlot getPlotParamInfo() { int type = type1Button.isSelected() ? 1
 * : (type2Button.isSelected() ? 2 : 3);
 *
 * return new APPDFRParamInfo(n0PF.getDouble(), p0PF.getDouble(),
 * lambdaPF.getDouble(), apPF.getDouble(), cPF.getDouble(), tPF.getDouble(),
 * thPF.getDouble(), bPF.getDouble(), gensPF.getInt(), type,
 * npvstButton.isSelected()); }
 *
 * which returns the class who knows how to calculate the results from the input
 * screen.
 */

public class BasicPlotInputPanel extends ModelPanel {
	private static final long serialVersionUID = -3085139371708191553L;

	/**
	 * The only reason this is not abstract is to use the visual designer
	 */
	public BasicPlot getPlotParamInfo() {
		return null;
	}

	public void updateLabels() {

	}

	public BasicPlotInputPanel() {

	}
}
