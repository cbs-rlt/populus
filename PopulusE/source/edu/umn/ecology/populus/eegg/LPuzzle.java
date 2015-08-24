/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.eegg;

import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.fileio.Logging;

public class LPuzzle extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2340957324111912141L;
	public LPuzzle(Frame owner) {
      super(owner, "puzzle", true);
      try {
         setDefaultCloseOperation(DISPOSE_ON_CLOSE);
         init();
         setSize(500, 500);
         setVisible(true);
      } catch (Exception exception) {
         Logging.log(exception);
      }
   }

	private void init() {
		LPuzzlePanel pp = new LPuzzlePanel();
		getContentPane().add(pp, BorderLayout.CENTER);
	}
}
