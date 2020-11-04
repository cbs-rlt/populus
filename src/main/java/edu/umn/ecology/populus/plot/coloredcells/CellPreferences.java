/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.coloredcells;

import edu.umn.ecology.populus.core.DesktopWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CellPreferences extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 6730692691382426455L;
    static CellPreferences keepOnlyOneOpen;
    final JPanel mainPanel = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JTextField wTF = new JTextField();
    final JTextField hTF = new JTextField();
    final JLabel wL = new JLabel();
    final JLabel hL = new JLabel();
    final JButton setB = new JButton();
    final JButton cancelB = new JButton();

    public static void bringUpCellPreferences() {
        if (CellPreferences.keepOnlyOneOpen == null) {
            Frame coolthing = new Frame();
            coolthing.setIconImage(Toolkit.getDefaultToolkit()
                    .getImage(DesktopWindow.class.getResource("picon.gif")));
            CellPreferences.keepOnlyOneOpen = new CellPreferences(coolthing, "Cell Preferences", false);
        }
        CellPreferences.keepOnlyOneOpen.init();
        CellPreferences.keepOnlyOneOpen.setVisible(true);
    }

    void init() {
        wTF.setText(String.valueOf(CellDefaults.kWidth));
        hTF.setText(String.valueOf(CellDefaults.kHeight));
    }

    public CellPreferences(Frame frame, String title, boolean b) {
        super(frame, title, b);
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();

        //Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }

    void jbInit() {
        mainPanel.setLayout(gridBagLayout1);
        wL.setText("Cell Width:");
        hL.setText("Cell Height:");
        wTF.setPreferredSize(new Dimension(50, 21));
        wTF.setToolTipText("In Pixels");
        hTF.setPreferredSize(new Dimension(50, 21));
        hTF.setToolTipText("In Pixels");
        setB.setText("Set");
        setB.addActionListener(new CellPreferences_setB_actionAdapter(this));
        cancelB.setText("Cancel");
        cancelB.addActionListener(new CellPreferences_cancelB_actionAdapter(this));

        mainPanel.add(wTF, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 5, 10), 0, 0));
        mainPanel.add(hTF, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 10), 0, 0));
        mainPanel.add(wL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(hL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.add(cancelB, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        mainPanel.add(setB, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
        this.getContentPane().add(mainPanel, null);
    }

    void setB_actionPerformed(ActionEvent e) {
        try {
            CellDefaults.kWidth = Integer.parseInt(wTF.getText());
            CellDefaults.kHeight = Integer.parseInt(hTF.getText());
        } catch (NumberFormatException nfe) {
            edu.umn.ecology.populus.fileio.Logging.log("Cell preference setting not an integer");
        }
        this.setVisible(false);
    }

    void cancelB_actionPerformed(ActionEvent e) {
        this.setVisible(false);
    }
}

class CellPreferences_setB_actionAdapter implements java.awt.event.ActionListener {
    final CellPreferences adaptee;

    CellPreferences_setB_actionAdapter(CellPreferences adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.setB_actionPerformed(e);
    }
}

class CellPreferences_cancelB_actionAdapter implements java.awt.event.ActionListener {
    final CellPreferences adaptee;

    CellPreferences_cancelB_actionAdapter(CellPreferences adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.cancelB_actionPerformed(e);
    }
}