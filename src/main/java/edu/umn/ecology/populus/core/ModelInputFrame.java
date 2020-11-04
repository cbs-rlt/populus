/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:

//Version:

//Copyright:

//Author:

//Company:

//Description:
package edu.umn.ecology.populus.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class ModelInputFrame extends PopInternalFrame {
    /**
     *
     */
    private static final long serialVersionUID = 8093208225694956317L;
    ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");
    BorderLayout mainBorderLayout = new BorderLayout();
    transient Model model;
    int whichSelected = 1;
    JButton outputButton = PopulusToolButton.createOutputIButton();
    JButton closeButton = PopulusToolButton.createCloseButton();
    JButton printButton = PopulusToolButton.createPrintButton();
    JButton helpButton = PopulusToolButton.createHelpButton();
    JButton saveButton = PopulusToolButton.createSaveButton();
    JPanel jPanel1 = new JPanel();

    public void jbInit() throws Exception {
        this.setResizable(true);
        this.getContentPane().setLayout(mainBorderLayout);
        saveButton.addActionListener(e -> save());
        outputButton.addActionListener(e -> outputButton_actionPerformed(e));
        closeButton.addActionListener(e -> closeButton_actionPerformed(e));
        printButton.addActionListener(e -> printButton_actionPerformed(e));
        saveButton.setText(res.getString("File"));
        toolBar.setFloatable(false);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        toolBar.add(outputButton, null);
        toolBar.add(saveButton, null);
        toolBar.add(helpButton, null);
        toolBar.add(printButton, null);
        toolBar.add(closeButton, null);
        this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
        this.setTitle(model.getThisModelInputName() + res.getString("_Input"));
    }

    public ModelInputFrame(Model model) {
        super();
        setModelColor(model.getModelColor());
        this.model = model;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void outputButton_actionPerformed(ActionEvent e) {
        model.updateOutput();
    }

    void closeButton_actionPerformed(ActionEvent e) {
        model.closeInput();
    }

    @Override
    Component getHelpComponent() {
        return helpButton;
    }

    void printButton_actionPerformed(ActionEvent e) {
        model.printInputGraphics();
    }

    void save() {
        model.save();
    }
}
