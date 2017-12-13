/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.herit;

import edu.umn.ecology.populus.plot.*;

public class HERITModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "HERITHELP";
	}

	public HERITModel() {
		this.setModelInput( new HERITPanel() );
	}

	public static String getModelName() {
		return "Heritability";
	}

	@Override
	protected String getHelpId() {
		return "HERIT.overview";
	}
}
