/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdtpr;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDTPRModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdtpr.Res" );

	@Override
	public String getThisModelInputName() {
		return res.getString( "Discrete_Predator" );
	}

	@Override
	public Object getModelHelpText() {
		return "APPDTPRHELP";
	}

	public APPDTPRModel() {
		this.setModelInput( new APPDTPRPanel() );
	}

	public static String getModelName() {
		return res.getString( "Threshold_Predator" );
	}

	@Override
	protected String getHelpId() {
		return "appdtpr.overview";
	}
}
