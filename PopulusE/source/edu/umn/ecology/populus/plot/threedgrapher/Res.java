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

	public Object[][] getContents() {
		return contents;
	}
}