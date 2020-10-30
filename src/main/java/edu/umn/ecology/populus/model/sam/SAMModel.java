/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sam;

import edu.umn.ecology.populus.plot.*;

public class SAMModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "SAMHELP";
	}

	public SAMModel() {
		this.setModelInput( new SAMPanel() );
	}

	public static String getModelName() {
		return "Selection & Mutation";
	}

	@Override
	protected String getHelpId() {
		return "SAM.overview";
	}
}
