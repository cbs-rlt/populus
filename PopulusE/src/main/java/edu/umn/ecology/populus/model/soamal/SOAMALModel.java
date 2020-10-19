/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.soamal;

import edu.umn.ecology.populus.plot.*;

public class SOAMALModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "SOAMALHELP";
	}

	public SOAMALModel() {
		this.setModelInput( new SOAMALPanel() );
	}

	public static String getModelName() {
		return "Selection on a Multi-Allelic Locus";
	}

	@Override
	protected String getHelpId() {
		return "SOAMAL.overview";
	}
}
