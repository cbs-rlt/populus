/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.resultwindow;
import java.util.*;
import javax.swing.*;

/**
 *  ModelPanel serves as a root for all other models in the 'edwin' directory
 *  Changes to this will affect all descendants.
 *
 */
public class OutputPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 5766971437163627959L;
	protected transient Vector<OutputPanelListener> listeners = new Vector<OutputPanelListener>( 1, 1 );
	int type = 0;

	/** @see edu.umn.ecology.populus.constants.OutputTypes
	 *
	 * Lars - this doesn't seem like a good idea for clean software.  Will look into if/when I have time.
	 */
	public int getType() {
		return type;
	}

	/** @see edu.umn.ecology.populus.constants.OutputTypes */
	public void setType( int nType ) {
		type = nType;
	}

	public OutputPanel() {
		super();
	}

	public void showOptions( int option ) {


		//Do nothing if not a plot model.
	}

	public synchronized void addOutputPanelListener( OutputPanelListener listener ) {
		listeners.addElement( listener );
	}

	public synchronized void removeOutputPanelListener( OutputPanelListener listener ) {
		listeners.removeElement( listener );
	}

	public void destroy() {

		//remove listeners
		listeners.removeAllElements();

		//give panel a chance to clean up

		//Make sure all paramfields have lost their connections to any frames

		//so that they can be lost, too
	}

	//////////

	// EVENT

	// METHODS

	//////////

	/**
	 * Call this to easily notify an event
	 */

	protected void fireOutputPanelEvent( int type ) {
		OutputPanelEvent event = new OutputPanelEvent( this, type );
		Enumeration<OutputPanelListener> e = listeners.elements();
		while( e.hasMoreElements() ) {
			e.nextElement().outputChangeRequested( event );
		}
	}

}
