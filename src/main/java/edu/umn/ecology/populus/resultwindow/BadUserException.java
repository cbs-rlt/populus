/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.resultwindow;

/** This is the exception class for sending an error to the output screen.*/

public class BadUserException extends Error {

	/**
	 *
	 */
	private static final long serialVersionUID = -5974327217039117069L;

	/**
	 * BadUserException constructor comment.
	 * @param s java.lang.String
	 */

	public BadUserException( String s ) {
		super( s );
	}

	/**
	 * BadUserException constructor comment.
	 */

	public BadUserException() {
		super();
	}
}
