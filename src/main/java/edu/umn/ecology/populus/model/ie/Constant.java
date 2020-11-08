/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Constant extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7232261105799052347L;
    final JButton closeButton = new JButton();
    String name;
    final PopulusParameterField constantPPF = new PopulusParameterField();

    public Constant() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public double getValue() {
        return constantPPF.getDouble();
    }

    public Constant(String name, double value) {
        this();
        this.name = name;
        constantPPF.setParameterName("<i>" + name + "</i>");
        constantPPF.setCurrentValue(value);
    }

    void closeButton_actionPerformed(ActionEvent e) {
        Container c = getParent();
        c.remove(this);
        c.validate();
        c.repaint();
    }

    private void jbInit() throws Exception {
        constantPPF.setIncrementAmount(0.1);
        constantPPF.setMaxValue(1000.0);
        constantPPF.setMinValue(-1000.0);
        constantPPF.setParameterName("a");
        closeButton.setFont(new java.awt.Font("Dialog", Font.BOLD, 12));
        closeButton.setPreferredSize(new Dimension(14, 15));
        closeButton.setToolTipText("Remove this constant");
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setText("X");
        closeButton.addActionListener(this::closeButton_actionPerformed);
        this.add(constantPPF, null);
        this.add(closeButton, null);
    }
}
