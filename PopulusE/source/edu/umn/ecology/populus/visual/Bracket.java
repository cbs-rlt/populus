
//Title:        Populus
//Version:      
//Copyright:    Copyright (c) 1999
//Author:       Lars Roe, under Don Alstad
//Company:      University of Minnesota
//Description:  First Attempt at using Java 1.2
//with Populus
package edu.umn.ecology.populus.visual;

import javax.swing.*;
public class Bracket extends JLabel {
	private int side;

	private final String uiClassID = "BracketUI";


	/**
	 * 'side' may be either SwingConstants.LEFT or SwingConstants.RIGHT
	 */
	public Bracket(int side) {
		this.side = side;
	}

	public String getUIClassID() {
		return uiClassID;
	}

	public boolean isOpaque() {
		return true;
	}


	public int getSide() {
		return side;
	}
}
