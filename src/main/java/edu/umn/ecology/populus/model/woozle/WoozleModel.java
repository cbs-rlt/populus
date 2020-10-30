/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.woozle;

import edu.umn.ecology.populus.core.*;
import edu.umn.ecology.populus.plot.*;
import java.util.*;

public class WoozleModel extends Model {
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.woozle.Res" );

	@Override
	public Object getModelHelpText() {

		//Just testing with this

		//return getStringResource(edu.umn.ecology.populus.model.woozle.WoozleModel.class.getResource("WoozleHelp.poht"));;
		return "WOOZLEHELP";
	}

	public WoozleModel() {
		this.setModelInput( new WoozlePanel() );
	}

	public static String getModelName() {
		return res.getString( "Woozleology" );
	}

	@Override
	protected boolean isRepeatable() {
		return true;
	}

	@Override
	protected String getHelpId() {
		return "woozle.overview";
	}

	@Override
	protected ParamInfo getData() {
		outputFrame.setVisible( true );
		return ( (WoozlePanel)getModelInput() ).getWoozleParamInfo();
	}

	@Override
	protected void simpleUpdateOutput() {
		WoozleParamInfo data = (WoozleParamInfo)getData();
		if( getModelOutput() == null ) {
			setModelOutput( new WoozleWindow( data ) );
		}
		else {
			( (WoozleWindow)getModelOutput() ).updateWoozle( data );
		}
	}
}
