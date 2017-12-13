/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.bp;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.PopResourceBundle;

public class BPModel extends BasicPlotModel {
	static PopResourceBundle res = PopResourceBundle.getBundle( "edu.umn.ecology.populus.model.bp.Res" );

	@Override
	public Object getModelHelpText() {
		return "BPHELP";
	}

	public BPModel() {
		this.setModelInput( new BPPanel() );
	}

	public static String getModelName() {
		return res.getString( "Bacterial" );
	}

	@Override
	protected String getHelpId() {
		return "bp.overview";
	}
}
