/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.soasll;

import edu.umn.ecology.populus.plot.*;

public class SOASLLModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "SOASLLHELP";
	}

	public SOASLLModel() {
		this.setModelInput( new SOASLLPanel() );
	}

	public static String getModelName() {
		return "Selection on a Sex-Linked Locus";
	}

	@Override
	protected String getHelpId() {
		return "SOASLL.overview";
	}
}
