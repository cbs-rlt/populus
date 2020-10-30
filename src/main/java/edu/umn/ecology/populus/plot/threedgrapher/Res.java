/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.threedgrapher;

import java.io.Serializable;

public class Res extends java.util.ListResourceBundle
implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -8435556359193477054L;
	static final Object[][] contents = new String[][]{
		{ "Last_Selected_Data_", "Last Selected Data: " }};

		@Override
		public Object[][] getContents() {
			return contents;
		}
}