/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.resultwindow;
import java.awt.*;
import javax.swing.*;

/** This class extends OutputPanel to put an error message on the output
  screen instead of the usual graph when there is an error in the output.*/

public class BadUserOutputPanel extends OutputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -3164580227786601659L;
	JLabel errorMessage;
	BadUserException bue = null;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JLabel mainMessage;

	public BadUserOutputPanel( BadUserException bue ) {
		this.bue = bue;
		try {
			jbInit();
		}
		catch( Exception ex ) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		errorMessage = new JLabel();
		mainMessage = new JLabel();
		this.setLayout( gridBagLayout1 );
		errorMessage.setForeground( Color.red );
		errorMessage.setHorizontalAlignment( SwingConstants.CENTER );
		errorMessage.setHorizontalTextPosition( SwingConstants.CENTER );
		errorMessage.setText( bue.getMessage() );
		this.setBackground( Color.yellow );
		this.setForeground( Color.red );
		mainMessage.setForeground( Color.red );
		mainMessage.setHorizontalAlignment( SwingConstants.CENTER );
		mainMessage.setText( "Cannot create output:" );
		this.add( mainMessage, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		this.add( errorMessage, new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
	}
}
