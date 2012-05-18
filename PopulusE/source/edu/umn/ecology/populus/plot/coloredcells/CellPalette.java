package edu.umn.ecology.populus.plot.coloredcells;

import java.awt.Color;

public abstract class CellPalette{
	public static final int kCenter=0;
	public static final int kLeft=1;
	public int textFormat = kCenter;
	public Color[] colors;
	public Color textColor = Color.black;
	public Color background = Color.black;
	public Color outline = Color.white;
}

