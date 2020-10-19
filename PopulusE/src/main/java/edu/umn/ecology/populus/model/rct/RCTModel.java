/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rct;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class RCTModel extends BasicPlotModel {
	static ResourceBundle res = null;
	private static synchronized void initRes() {
		if (res == null)
			res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.rct.Res" );
	}

	@Override
	public Object getModelHelpText() {
		return "RCTHELP";
	}

	public RCTModel() {
		this.setModelInput( new RCTPanel() );
	}
	@Override
	public String getThisModelInputName() {
		initRes();
		return res.getString( "Resource_competition" );
	}
	public static String getModelName() {
		initRes();
		return res.getString( "Resource" );
	}

	@Override
	protected String getHelpId() {
		return "rct.overview";
	}
}
