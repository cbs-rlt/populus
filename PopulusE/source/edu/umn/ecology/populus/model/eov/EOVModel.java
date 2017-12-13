/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.eov;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;

public class EOVModel extends BasicPlotModel {
	static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.eov.Res" );

	@Override
	public Object getModelHelpText() {
		return "EOVHELP";
	}

	public EOVModel() {
		this.setModelInput( new EOVPanel() );
	}

	public static String getModelName() {
		return res.getString( "Infectious" );
	}

	@Override
	protected String getHelpId() {
		return "eov.overview";
	}
}
