package edu.umn.ecology.populus.model.nbss;

import java.awt.Color;
import edu.umn.ecology.populus.plot.coloredcells.CellPalette;

/** The array of colors that the cells will use. These are an approximation to the
		visible light spectrum*/
class NBSSColorPalette extends CellPalette {
	NBSSColorPalette(){
		colors = new Color[] {
			Color.black,
			new Color(108,0,0),
			Color.red,
			Color.orange,
			Color.yellow,
			new Color(157,252,0),
			Color.green,
			Color.cyan,
			Color.blue,
			new Color(104,0,110)
		};
		textColor = Color.gray;
		textFormat = CellPalette.kCenter;
	}
}
