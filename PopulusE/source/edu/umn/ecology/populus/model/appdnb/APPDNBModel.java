/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnb;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDNBModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnb.Res" );

	@Override
	public String getThisModelInputName() {
		return res.getString( "Discrete_Predator" );
	}

	@Override
	public Object getModelHelpText() {
		return "APPDNBHELP";
	}

	public APPDNBModel() {
		this.setModelInput( new APPDNBPanel() );
	}

	public static String getModelName() {
		return res.getString( "Nicholson_Bailey" );
	}

	@Override
	protected String getHelpId() {
		return "appdnb.overview";
	}
}
