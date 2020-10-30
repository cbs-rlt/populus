/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.coloredcells;

import java.awt.Color;

public class Cell{
	Color cellColor;
	double value;

	Cell(double fValue, Color c){
		value = fValue;
		cellColor = c;
	}

	public Color getColor(){
		return cellColor;
	}
	public double getValue() {
		return value;
	}
	public void setCell(double newValue, Color newColor){
		value = newValue;
		cellColor = newColor;
	}
}