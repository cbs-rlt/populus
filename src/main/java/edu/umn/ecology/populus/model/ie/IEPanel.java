/******************************************************************************
 Copyright (c) 2015 Regents of the University of Minnesota.

 This software is released under GNU General Public License 2.0
 http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 */
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.RunningTimePanel;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import edu.umn.ecology.populus.visual.StyledRadioButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;

public class IEPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 2656336723261477979L;
    EquationCalculator ss;
    EquationPanel ep;
    final JRadioButton textB = new JRadioButton();
    final JPanel integrationType = new JPanel();
    final JRadioButton discreteB = new JRadioButton();
    TitledBorder titledBorder1;
    final ButtonGroup bgPlot = new ButtonGroup();
    final ConstantPanel cp = new ConstantPanel(3);
    final JRadioButton continuousB = new JRadioButton();
    final RunningTimePanel runTime = new RunningTimePanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JPanel plotType = new JPanel();
    TitledBorder titledBorder2;
    final StyledRadioButton nvsnB = new StyledRadioButton();
    final ButtonGroup bgInte = new ButtonGroup();
    final StyledRadioButton nvstB = new StyledRadioButton();
    Border border1, border2;
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    final SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();

    @Override
    public BasicPlot getPlotParamInfo() {
        double[] initial = new double[0];
        initial = ep.getInitialValues();
        double run = (int) runTime.getTime();
        try {
            ss = new EquationCalculator(ep.getStrings(true), ep.getUsed(), cp.getConstantHashTable(), discreteB.isSelected());
            return new IEParamInfo(ss, initial, ep.getUsed(), ep.getPlotted(), run,
                    discreteB.isSelected(), nvstB.isSelected(), textB.isSelected(), ep.getStrings(true),
                    ep.getParamNames(false));
        } catch (IEException iee) {
            JOptionPane.showMessageDialog(this, iee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public String getOutputGraphName() {
        return "Interaction Engine Results";
    }

    public IEPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void doTextOutput(EquationCalculator ss, double[] init, double rt) {
        edu.umn.ecology.populus.fileio.Logging.log("\n\nInteraction Engine Console Output");
        edu.umn.ecology.populus.fileio.Logging.log("Discrete Calculations:");
        for (int i = 0; i <= rt; i++) {
            edu.umn.ecology.populus.fileio.Logging.log("\nt = " + i);
            for (int j = 0; j < init.length; j++) {
                edu.umn.ecology.populus.fileio.Logging.log("  N" + j + ":\t" + init[j]);
            }
            init = ss.calculateValues(init, rt);
            edu.umn.ecology.populus.fileio.Logging.log();
        }
        edu.umn.ecology.populus.fileio.Logging.log();
    }

    void discreteB_itemStateChanged(ItemEvent e) {
        ep.setType(discreteB.isSelected());
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, "Output Type");
        titledBorder2 = new TitledBorder(border2, "Plot Type");
        this.setLayout(gridBagLayout1);
        nvsnB.setText("<i><b>N</> vs <i><b>N</>");
        nvsnB.setSelected(true);
        nvstB.setText("<i><b>N</> vs <i>t</i>");
        plotType.setLayout(simpleVFlowLayout2);
        plotType.setBorder(titledBorder1);
        continuousB.setSelected(true);
        continuousB.setText("Continuous");
        continuousB.setFocusPainted(false);
        discreteB.setText("Discrete");
        discreteB.setFocusPainted(false);
        discreteB.addItemListener(e -> discreteB_itemStateChanged(e));
        ep = new EquationPanel(3, discreteB.isSelected());
        integrationType.setLayout(simpleVFlowLayout3);
        integrationType.setBorder(titledBorder2);
        textB.setToolTipText("Send output a file.");
        textB.setText("File");
        textB.setFocusPainted(false);
        ep.setPreferredSize(new Dimension(440, 136));
        cp.setBackground(Color.gray);
        bgInte.add(discreteB);
        bgInte.add(continuousB);
        bgInte.add(discreteB);
        bgInte.add(continuousB);
        plotType.add(nvstB, null);
        plotType.add(nvsnB, null);
        plotType.add(textB, null);
        bgPlot.add(nvstB);
        bgPlot.add(nvsnB);
        //bgPlot.add( textB );
        this.add(ep, new GridBagConstraints(0, 1, 3, 1, 1.0, 4.0, GridBagConstraints.SOUTHWEST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
        this.add(runTime, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(plotType, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));
        this.add(integrationType, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        integrationType.add(discreteB, null);
        integrationType.add(continuousB, null);
        this.add(cp, new GridBagConstraints(3, 0, 1, 2, 1.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 3, 0, 5), 40, 0));
        registerChildren(plotType);
        registerChildren(integrationType);
        registerChildren(runTime);
    }
}
