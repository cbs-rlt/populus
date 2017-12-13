/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;
import java.lang.Exception;

/** An exception especially "designed" for use with the EquationCalculator
  class.*/

public class IEException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 3719610138118809274L;

	public IEException( String message ) {
		super( message );
	}
}
