/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;

/**
 * Listens to the Models. Created in 1.1 version
 */

public interface ModelListener {

    public void modelChanged(ModelEvent e) throws CannotChangeModelException;
}
