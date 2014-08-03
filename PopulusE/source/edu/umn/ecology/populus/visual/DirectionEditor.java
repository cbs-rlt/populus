package edu.umn.ecology.populus.visual;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;

/** Use to define the direction the HTMLLabel will point. */

public class DirectionEditor extends PropertyEditorSupport {
	private ArrayList<Integer> values;
	private ArrayList<String> names;
	
	@SuppressWarnings("serial")
	public DirectionEditor() {
		values = new ArrayList<Integer>() {{
			add(HTMLLabel.NORMAL);
			add(HTMLLabel.DOWN_TO_UP);
			add(HTMLLabel.UPSIDEDOWN);
			add(HTMLLabel.UP_TO_DOWN);
		}};
		names = new ArrayList<String>() {{
			add("Normal");
			add("Down to Up (90deg CC)");
			add("Upside-Down");
			add("Up to Down (270deg CC)");
		}};
	}
	
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
	
	public String[] getTags() {
		String s[] = new String[] {};
		return names.toArray(s);
	}
}
