/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.paqg;

import edu.umn.ecology.populus.plot.*;

public class PAQGModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "PAQGHELP";
	}

	public PAQGModel() {
		this.setModelInput( new PAQGPanel() );
	}

	public static String getModelName() {
		return "Population & Quantitative Genetics";
	}

	@Override
	protected String getHelpId() {
		return "PAQG.overview";
	}
}
