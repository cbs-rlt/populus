/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;
import java.io.Serializable;

/**
 * Base class for all messages between model input and output.
 */
public class ParamInfo extends Object implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4846503651986984099L;

	/**
	 * Dumps all of the information. Includes date, data, and other information. For
	 *  a less descriptive dump, and only the data, use dumpDataOnly()
	 */

	public void dump() {

	}

	/**
	 * Only dumps the data. For use with MSExcel
	 */

	public void dumpDataOnly() {

	}

	public ParamInfo() {

	}
}
