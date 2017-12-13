/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnrs;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class APPDNRSModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnrs.Res" );

	@Override
	public String getThisModelInputName() {
		return res.getString( "Discrete_Predator" );
	}

	@Override
	public Object getModelHelpText() {
		return "APPDNRSHELP";
	}

	public APPDNRSModel() {
		this.setModelInput( new APPDNRSPanel() );
	}

	public static String getModelName() {
		return res.getString( "Non_Random_Searching" );
	}

	@Override
	protected String getHelpId() {
		return "appdnrs.overview";
	}
}
