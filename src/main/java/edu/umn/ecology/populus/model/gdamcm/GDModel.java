/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.gdamcm;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class GDModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.gdamcm.Res" );
	GDPanel gdp;

	@Override
	public Object getModelHelpText() {
		return "GDHELP";
	}

	public GDModel() {
		gdp = new GDPanel();
		setModelInput( gdp );
	}

	public static String getModelName() {
		return "Genetic Drift";
	}

	@Override
	protected String getHelpId() {
		if(gdp == null || gdp.switcherTP.getSelectedIndex()==0)
			return "gdrift.overview";
		else
			return "gdriftm.overview";
	}

	@Override
	protected boolean isRepeatable() {
		return true;
	}
}
