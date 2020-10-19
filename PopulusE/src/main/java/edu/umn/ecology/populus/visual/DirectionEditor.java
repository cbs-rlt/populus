/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;

/**
 * Use to define the direction the HTMLLabel will point.
 * This was made for the editor, and isn't exposed.  To be honest, I don't know if it's still used...
 * If it <em>doesn't</em> work, the worst thing that will happen will only affect developers.
 */
public class DirectionEditor extends PropertyEditorSupport {
	private ArrayList<Integer> values;
	private ArrayList<String> names;

	@SuppressWarnings("serial")
	public DirectionEditor() {
		values = new ArrayList<Integer>() {{
			add(HTMLConstants.NORMAL);
			add(HTMLConstants.DOWN_TO_UP);
			add(HTMLConstants.UPSIDEDOWN);
			add(HTMLConstants.UP_TO_DOWN);
		}};
		names = new ArrayList<String>() {{
			add("Normal");
			add("Down to Up (90deg CC)");
			add("Upside-Down");
			add("Up to Down (270deg CC)");
		}};
	}

	@Override
	public String getAsText() {
		int idx = values.indexOf(getValue());
		if (idx >= 0)
			return names.get(idx);
		return null;
	}

	public void SetAsText(String text) throws java.lang.IllegalArgumentException {
		int idx = names.indexOf(text);
		if (idx < 0)
			throw new java.lang.IllegalArgumentException(text);
		setValue(values.get(idx));
	}

	@Override
	public String[] getTags() {
		String s[] = new String[] {};
		return names.toArray(s);
	}
}
