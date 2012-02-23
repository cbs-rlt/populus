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