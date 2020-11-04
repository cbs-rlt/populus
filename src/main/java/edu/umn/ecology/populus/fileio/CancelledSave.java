/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.fileio;

public class CancelledSave extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 6223522664382154521L;

    public CancelledSave(String s) {
        super("Cancelled Save: " + s);
    }

    public CancelledSave() {
        super("Cancelled Save");
    }
}
