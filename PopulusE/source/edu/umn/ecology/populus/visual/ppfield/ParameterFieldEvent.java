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
package edu.umn.ecology.populus.visual.ppfield;
import java.util.*;

/**
 *
 * @author Lars Roe
 */

public class ParameterFieldEvent extends EventObject {
	/**
	 *
	 */
	private static final long serialVersionUID = -8285642595516017902L;
	/** Increment */
	public static final int INC = 1;
	/** Decrement */
	public static final int DEC = 2;
	/** [Enter] was pressed to confirm the value */
	public static final int ENTER = 4;
	/** Text was typed in, but did not use [Enter] */
	public static final int TEXT = 8;
	/** Value was set using a menu popup */
	public static final int MENU = 16;
	/** Programmatically, i.e. explicitly called through a function */
	public static final int PROG = 32;
	private int type;

	/**
	 * Returns the type. Other methods are preferred, so that
	 * actual event types are not as hard-coded
	 */
	public int getType() {
		return type;
	}

	/**
	 * Is it enter? (currently equivalent to isMandatory())
	 */

	public boolean isEnter() {
		return ( ENTER & type ) != 0;
	}

	/**
	 * Is it an increment/decrement button?
	 */

	public boolean isIncDec() {
		return ( ( INC & type ) | ( DEC & type ) ) != 0;
	}

	/**
	 * Should this always trigger an update?
	 * Currently true when user presses "Enter"
	 */

	public boolean isMandatory() {
		return ( ENTER & type ) != 0;
	}

	/**
	 * Called from a Menu
	 */

	public boolean isMenu() {
		return ( MENU & type ) != 0;
	}

	/**
	 * User typed text
	 */

	public boolean isText() {
		return ( TEXT & type ) != 0;
	}

	/**
	 * Plain Constructor
	 */

	public ParameterFieldEvent( Object source, int type ) {
		super( source );
		this.type = type;
	}
}
