/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.intradgs;

import edu.umn.ecology.populus.plot.*;

public class INTRADGSModel extends BasicPlotModel {

	@Override
	public Object getModelHelpText() {
		return "INTRADGSHELP";
	}

	public INTRADGSModel() {
		this.setModelInput( new INTRADGSPanel() );
	}

	public static String getModelName() {
		return "Intrademic Group Selection";
	}

	@Override
	protected String getHelpId() {
		return "INTRADGS.overview";
	}
}
