/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;
import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.visual.*;
import javax.swing.border.Border;

public class PopInternalFrame extends JInternalFrame {
	/**
	 *
	 */
	private static final long serialVersionUID = 608794057839649107L;
	protected JToolBar toolBar = new JToolBar();

	@Override
	public void setVisible( boolean vis ) {
		super.setVisible( vis );
		this.toFront();
	}

	public void setModelColor( Color c ) {
		Border b = new OwnershipBorder(c);
		((JComponent) this.getContentPane()).setBorder(b);
	}

	/**
	 * This is meant to be the superclass of the ModelInputFrame
	 * and ModelOutputFrame. This and all of its subclasses should
	 * be <b>strictly</b> GUI, and no actual processes. All events
	 * should be passed to the Model object appropriately.
	 * This is because in future versions, a splitpane could be used instead
	 * of two internal frames, and it should have the same exact functionality.
	 */

	public PopInternalFrame() {
		try {
			this.setFrameIcon( new ImageIcon( PopInternalFrame.class.getResource( "picon.gif" ) ) );
		}
		catch( Exception e ) {

			edu.umn.ecology.populus.fileio.Logging.log("Could not find the icon image file");
		}
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	Component getHelpComponent() {
		return null;
	}

	private void jbInit() throws Exception {

	}
	@Override
	public void dispose() {
		Component[] comps = toolBar.getComponents();
		for (int i = comps.length - 1; i >= 0; i--) {
			if (comps[i] instanceof PopulusToolButton) {
				((PopulusToolButton) comps[i]).dispose();
			}
		}
		super.dispose();
	}
}
