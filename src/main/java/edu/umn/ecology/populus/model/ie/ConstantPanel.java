/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.util.Hashtable;

public class ConstantPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5089773599005699384L;
    final JPanel conHolder = new JPanel();
    final JButton addConstant = new JButton();
    final JPanel addConstantPanel = new JPanel();
    final JTextField newConstantName = new JTextField();
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    JScrollBar jsb;
    final JScrollPane scroller = new JScrollPane();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();

    public ConstantPanel(int numConstants) {
        this();

		/*
      for(int i=0; i<numConstants; i++)
      addConstant(""+(char)('a' + i), 1.0+i);
		 */

        //* these are the constants from the old ie default
        addConstant("r1", 1);
        addConstant("r2", 1);
        addConstant("a11", 0.001);
        addConstant("a12", 0.001);
        addConstant("a21", 0.0015);
        addConstant("a22", 0.001);
        addConstant("c1", 0.01);
        addConstant("c2", 0.001);
        addConstant("b", 0.5);
        addConstant("d", 1);

		/*/  //from cpp dd-prey
      addConstant("d2",0.6);
      addConstant("g",0.5);
      addConstant("r1",0.9);
      addConstant("C",0.1);
      addConstant("K",100);
      //*/
    }

    public Hashtable<String, Double> getConstantHashTable() {
        Hashtable<String, Double> h = new Hashtable<>();
        Constant c;
        for (int i = conHolder.getComponentCount() - 1; i >= 0; i--) {
            c = (Constant) conHolder.getComponent(i);
            h.put(c.getName(), c.getValue());
        }
        return h;
    }

    public ConstantPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addConstant_actionPerformed(ActionEvent e) {

        //maybe substitute symbols in?

		/*
      tau -------		\u03c4
      lambda ---- 	\u03bb
      sigma ----- 	\u03a3 (capital)
      beta ------		\u03b2
      alpha -----		\u03b1
      gamma ----- 	\u03b3
      nu --------		\u03bd
      theta -----		\u0398
		 */
        String s = newConstantName.getText();
        if (s.length() == 0) {
            return;
        }
        if (Character.isLetter(s.charAt(0))) {
            addConstant(s, 1.234);
        }
        newConstantName.setText("");
    }

    void jsb_adjustmentValueChanged(AdjustmentEvent e) {
        scroller.revalidate();
        scroller.repaint();
    }

    void addConstant(String name, double value) {
        conHolder.add(new Constant(name, value));
        conHolder.revalidate();
        conHolder.repaint();
    }

    private void jbInit() throws Exception {
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollBar jsb = scroller.getVerticalScrollBar();
        this.setLayout(gridBagLayout1);
        addConstant.setToolTipText("Add the constant to the table");
        addConstant.setMargin(new Insets(2, 2, 2, 2));
        addConstant.setText("Add Constant");
        addConstant.addActionListener(this::addConstant_actionPerformed);
        newConstantName.setPreferredSize(new Dimension(30, 21));
        newConstantName.setToolTipText("Name for new constant");
        conHolder.setLayout(simpleVFlowLayout2);
        jsb.addAdjustmentListener(this::jsb_adjustmentValueChanged);
        this.add(addConstantPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        addConstantPanel.add(newConstantName, null);
        addConstantPanel.add(addConstant, null);
        this.add(scroller, new GridBagConstraints(0, 1, 1, 1, 1.0, 10.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        scroller.setPreferredSize(new Dimension(100, 300));
        scroller.getViewport().add(conHolder, null);
    }
}
