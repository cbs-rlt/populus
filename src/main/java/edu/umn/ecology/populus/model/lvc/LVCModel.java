/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvc;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class LVCModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvc.Res" );

	@Override
	public Object getModelHelpText() {
		return "LVCHELP";
	}

	public LVCModel() {
		this.setModelInput( new LVCPanel() );
	}

	public static String getModelName() {
		return res.getString( "Lotka_Volterra" );
	}

	@Override
	protected String getHelpId() {
		return "lvc.overview";
	}
}
