/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual.ppfield;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Vector;

public class UpDownArrowSet extends JPanel implements Serializable, ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = 7060966603979549125L;
    public static final String DOWN = "Down";
    public static final String UP = "Up";
    final BasicArrowButton downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
    final BasicArrowButton upButton = new BasicArrowButton(BasicArrowButton.NORTH);
    private transient Vector<ActionListener> actionListeners;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();

    @Override
    public void actionPerformed(ActionEvent e) {
        fireActionPerformed(e);
    }

    @Override
    public void setEnabled(boolean newState) {
        super.setEnabled(newState);
        upButton.setEnabled(newState);
        downButton.setEnabled(newState);
    }

    public synchronized void removeActionListener(ActionListener l) {
        if (actionListeners != null && actionListeners.contains(l)) {
            Vector<ActionListener> v = new Vector<>(actionListeners);
            v.removeElement(l);
            actionListeners = v;
        }
    }

    public synchronized void addActionListener(ActionListener l) {
        Vector<ActionListener> v = actionListeners == null ? new Vector<>(2) : new Vector<>(actionListeners);
        if (!v.contains(l)) {
            v.addElement(l);
            actionListeners = v;
        }
    }

    public UpDownArrowSet() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void fireActionPerformed(ActionEvent e) {
        if (actionListeners != null) {
            Vector<ActionListener> listeners = actionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                listeners.elementAt(i).actionPerformed(e);
            }
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(16, 32);
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        upButton.setActionCommand(UP);
        downButton.setActionCommand(DOWN);
        upButton.addActionListener(this);
        downButton.addActionListener(this);
        setPreferredSize(new Dimension(16, 32));

        this.add(upButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(downButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
}

