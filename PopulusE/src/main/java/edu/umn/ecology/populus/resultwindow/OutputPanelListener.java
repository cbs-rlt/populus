/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.resultwindow;

/**
 * Used to communicate between an output panel and its creator(s)
 *
 * @author Lars Roe
 */

public interface OutputPanelListener {

	public void outputChangeRequested( OutputPanelEvent e );
}
