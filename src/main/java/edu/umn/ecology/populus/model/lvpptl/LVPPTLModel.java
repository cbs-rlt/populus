/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class LVPPTLModel extends BasicPlotModel {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvpptl.Res" );

	@Override
	public Object getModelHelpText() {
		return "CPPTLHELP";
	}

	public LVPPTLModel() {
		this.setModelInput( new LVPPTLPanel() );
	}

	public static String getModelName() {
		return res.getString( "Continuous_Predator" );
	}

	@Override
	protected String getHelpId() {
		return "cpp.overview";
	}
}
