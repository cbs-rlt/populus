package edu.umn.ecology.populus.plot.threedgrapher;

import java.util.*;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle
			implements Serializable {
	static final Object[][] contents = new String[][]{
	{ "Last_Selected_Data_", "Last Selected Data: " }};

	public Object[][] getContents() {
		return contents;
	}
}