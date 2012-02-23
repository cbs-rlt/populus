package edu.umn.ecology.populus.model.sd;

import java.awt.Color;
import edu.umn.ecology.populus.plot.coloredcells.CellPalette;

/** The array of colors that the cells will use. These are an approximation to the
		visible spectrum*/
class SDColorPalette extends CellPalette {
	SDColorPalette(){
		colors = new Color[] {
			Color.blue,
			Color.yellow,
			Color.red,
			Color.green
		};
	}
}

