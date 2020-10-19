/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.fdsess;

import edu.umn.ecology.populus.plot.*;

public class FDSESSModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "FDSESSHELP";
	}

	public FDSESSModel() {
		this.setModelInput( new FDSESSPanel() );
	}

	public static String getModelName() {
		return "Frequency-Dependent Selection (ESS Model)";
	}

	@Override
	protected String getHelpId() {
		return "FDSESS.overview";
	}
}
