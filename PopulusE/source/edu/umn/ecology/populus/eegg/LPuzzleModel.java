/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.eegg;

import java.awt.*;

/**
 * Basic model for the puzzle.  Inherit from this to create
 * a new rule system for the base puzzle.
 */
public abstract class LPuzzleModel implements LPuzzleDirections {
	/** 
	 * List of colors.  This are currently four colors, but more can be
	 * added.  Be sure to use dark colors (Color.darker()) so that when
	 * we hover over the color, we can make it lighter.  Normal colors
	 * are already light, so lighter() does nothing.
	 */
	public static final Color[] kColorList = new Color[] {
			  Color.red.darker(), Color.blue.darker(), Color.yellow.darker(), Color.green.darker()};

	//
	// Public methods not intended to be overridden
	//
	public final int getScore() {
		return score;
	}
	public final Color getColor(int where) {
		return colors[where];
	}
	/** Make a guess.  This changes state. */
	public final int guess(int where) {
		int winner = getWinningPiece();
		if (winner == where)
			score++;
		else
			score = 0;
		setup();
		return winner;
	}

	//
	// Methods for subclasses to inherit
	//
	protected LPuzzleModel() {
		init();
	}

	/**
	 * Override to generate possible colors.
	 * Default is randDoubleQuadPerm.
	 */
	protected int[] generate() {
		return randDoubleQuadPerm();
	}
	/**
	 * Return the winning piece.
	 */
	abstract protected int calculateWinningPiece(Color[] colors);
	
	/** Generates a random permutation of length 4 */
	protected static int[] randQuadPerm() {
		int[] quad = new int[] {0, 1, 2, 3};
		for (int i=0; i<4; i++) {
			int idx = ((int) (Math.random() * (4.0 - i))) + i;
			int tmp = quad[idx];
			quad[idx] = quad[i];
			quad[i] = tmp;
		}
		return quad;
	}
	/** Generates two random permutations of length 4
	 * then adjoins them to make one of length 8 in total.
	 */
	protected static int[] randDoubleQuadPerm() {
		int[] perm = new int[8];
		int[] quad1 = randQuadPerm(), quad2 = randQuadPerm();
		System.arraycopy(quad1, 0, perm, 0, 4);
		System.arraycopy(quad2, 0, perm, 4, 4);
		return perm;
	}

	//
	// Private parts
	// 
	private Color[] colors;
	private int score;
	private int winningPiece;

	private void init() {
		//Initialize score
		score = 0;
		setup();
	}
	private void setup() {
		//initialize colors
		int[] pieces = generate();
		colors = new Color[8];
		for (int i=0; i<8; i++) {
			colors[i] = kColorList[pieces[i]];
		}

		//compute winning piece
		winningPiece = calculateWinningPiece(colors);
	}
	private int getWinningPiece() {
		return winningPiece;
	}
}
