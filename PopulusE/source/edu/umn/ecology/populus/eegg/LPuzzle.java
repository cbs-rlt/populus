package edu.umn.ecology.populus.eegg;

import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.fileio.Logging;

public class LPuzzle extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2340957324111912141L;
	private boolean isApplet=true;

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
