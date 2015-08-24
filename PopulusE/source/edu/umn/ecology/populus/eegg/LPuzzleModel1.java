/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.eegg;

import java.awt.*;

/** Add up positions of yellow pieces for winning piece */
public class LPuzzleModel1 extends LPuzzleModel {
	
	public static LPuzzleModel createNewModel() {
		return new LPuzzleModel1();
	}
	protected int calculateWinningPiece(Color[] colors) {
		int innerYellow=-1, outerYellow=-1;
		for (int i=0; i<4; i++) {
			if (colors[i] == kColorList[2])
				innerYellow = i;
		}
		for (int i=4; i<8; i++) {
			if (colors[i] == kColorList[2])
				outerYellow = i;
		}
		int winningPiece = (outerYellow+innerYellow)%8;
		return winningPiece;
	}
}
