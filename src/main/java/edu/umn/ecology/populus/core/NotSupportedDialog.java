/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

// This snippet creates a new dialog box

// with buttons on the bottom.

//Title:

//Version:

//Copyright:

//Author:

//Company:

//Description:
package edu.umn.ecology.populus.core;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NotSupportedDialog extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 9143334206721753109L;
    final JPanel panel2 = new JPanel();
    final JButton button1 = new JButton();
    final JButton button2 = new JButton();
    final BorderLayout borderLayout1 = new BorderLayout();
    final JPanel jPanel1 = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final GridLayout gridLayout1 = new GridLayout();
    final JPanel panel1 = new JPanel();
    Border border1;
    final JLabel jLabel1 = new JLabel();

    public NotSupportedDialog(Frame frame, String title) {
        this(frame, title, false);
    }

    public NotSupportedDialog(Frame frame) {
        this(frame, "", false);
    }

    public NotSupportedDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
    }

    // OK

    void button1_actionPerformed(ActionEvent e) {
        dispose();
    }

    // Cancel

    void button2_actionPerformed(ActionEvent e) {
        dispose();
    }

    void this_windowClosing(WindowEvent e) {
        dispose();
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createRaisedBevelBorder();
        jPanel1.setLayout(gridLayout1);
        panel2.setBorder(border1);
        panel2.setLayout(borderLayout1);
        button1.setText("OK");
        button1.addActionListener(new NotSupportedDialog_button1_actionAdapter(this));
        button2.setText("Cancel");
        gridLayout1.setHgap(4);
        button2.addActionListener(new NotSupportedDialog_button2_actionAdapter(this));
        this.addWindowListener(new NotSupportedDialog_this_windowAdapter(this));
        panel1.setLayout(gridBagLayout1);
        jLabel1.setForeground(Color.red);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Feature Not Yet Supported");
        panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        panel2.add(jLabel1, BorderLayout.CENTER);
        panel1.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(4, 8, 4, 8), 0, 0));
        jPanel1.add(button1, null);
        jPanel1.add(button2, null);
        getContentPane().add(panel1);
    }
}

class NotSupportedDialog_button1_actionAdapter implements ActionListener {
    final NotSupportedDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.button1_actionPerformed(e);
    }

    NotSupportedDialog_button1_actionAdapter(NotSupportedDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class NotSupportedDialog_button2_actionAdapter implements ActionListener {
    final NotSupportedDialog adaptee;

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.button2_actionPerformed(e);
    }

    NotSupportedDialog_button2_actionAdapter(NotSupportedDialog adaptee) {
        this.adaptee = adaptee;
    }
}

class NotSupportedDialog_this_windowAdapter extends WindowAdapter {
    final NotSupportedDialog adaptee;

    @Override
    public void windowClosing(WindowEvent e) {
        adaptee.this_windowClosing(e);
    }

    NotSupportedDialog_this_windowAdapter(NotSupportedDialog adaptee) {
        this.adaptee = adaptee;
    }
}
