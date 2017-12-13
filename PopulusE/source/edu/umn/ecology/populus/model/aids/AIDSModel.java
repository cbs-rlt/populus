/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aids;

import edu.umn.ecology.populus.plot.*;

public class AIDSModel extends BasicPlotModel {
	@Override
	public String getThisModelInputName() {
		return "AIDS: Threshold Model";
	}

	@Override
	public Object getModelHelpText() {
		return "AIDSHELP";
	}

	public AIDSModel() {
		this.setModelInput( new AIDSPanel() );
	}

	public static String getModelName() {
		return "AIDS: Threshold Model";
	}

	@Override
	protected String getHelpId() {
		return "aids.overview";
	}
}
