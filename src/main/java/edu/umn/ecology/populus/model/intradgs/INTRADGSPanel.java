/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.intradgs;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class INTRADGSPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7758086403816220837L;
    JPanel paramsP = new JPanel();
    PopulusParameterField sPPF = new PopulusParameterField();
    private PopulusParameterField GPPF = new PopulusParameterField();
    private PopulusParameterField bPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private PopulusParameterField NPPF = new PopulusParameterField();
    private PopulusParameterField gensPPF = new PopulusParameterField();
    private PopulusParameterField AfreqPPF = new PopulusParameterField();
    private JCheckBox singleMutationCB = new JCheckBox();
    private PopulusParameterField numGroupsPPF = new PopulusParameterField();

    @Override
    public BasicPlot getPlotParamInfo() {
        double Afreq = AfreqPPF.getDouble();
        if (singleMutationCB.isSelected())
            Afreq = (0.5 / NPPF.getInt()) / numGroupsPPF.getInt();

        return new INTRADGSParamInfo(NPPF.getInt(), GPPF.getInt(), bPPF.getDouble(), sPPF.getDouble(),
                Afreq, numGroupsPPF.getInt(), gensPPF.getInt());
    }

    public INTRADGSPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        paramsP.setLayout(gridBagLayout3);
        sPPF.setCurrentValue(0.05);
        sPPF.setDefaultValue(0.05);
        sPPF.setIncrementAmount(0.05);
        sPPF.setMaxValue(0.5);
        sPPF.setParameterName("<i>s</i>");
        sPPF.setHelpText("Altruistic's Cost");
        GPPF.setParameterName("<i>G</i>");
        GPPF.setHelpText("Number of generations between dispersals");
        GPPF.setMaxValue(50.0);
        GPPF.setMinValue(1.0);
        GPPF.setCurrentValue(3.0);
        GPPF.setDefaultValue(3.0);
        GPPF.setIntegersOnly(true);
        bPPF.setParameterName("<i>b</i>");
        bPPF.setHelpText("Recipient's Benefit");
        bPPF.setMaxValue(20.0);
        bPPF.setIncrementAmount(0.05);
        bPPF.setDefaultValue(2.0);
        bPPF.setCurrentValue(2.0);
        this.setLayout(gridBagLayout1);
        NPPF.setParameterName("<i>N</i>");
        NPPF.setHelpText("Group size after dispersal.");
        NPPF.setMaxValue(100.0);
        NPPF.setMinValue(1.0);
        NPPF.setCurrentValue(5.0);
        NPPF.setDefaultValue(5.0);
        NPPF.setIntegersOnly(true);
        gensPPF.setCurrentValue(50.0);
        gensPPF.setDefaultValue(50.0);
        gensPPF.setIncrementAmount(10.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setMaxValue(1000.0);
        gensPPF.setMinValue(1.0);
        gensPPF.setParameterName("Generations");
        gensPPF.setHelpText("Number of generations to run.");
        AfreqPPF.setCurrentValue(0.5);
        AfreqPPF.setDefaultValue(0.5);
        AfreqPPF.setIncrementAmount(0.05);
        AfreqPPF.setMaxValue(1.0);
        AfreqPPF.setParameterName("\"<i>A</i>\" starting frequency");
        AfreqPPF.setHelpText("Starting frequency of \"A\" genes.");
        singleMutationCB.setText("Single Mutation?");
        singleMutationCB.addActionListener(e -> singleMutationCB_actionPerformed(e));
        numGroupsPPF.setIntegersOnly(true);
        numGroupsPPF.setDefaultValue(100.0);
        numGroupsPPF.setIncrementAmount(5.0);
        numGroupsPPF.setCurrentValue(100.0);
        numGroupsPPF.setMaxValue(1000.0);
        numGroupsPPF.setMinValue(1.0);
        numGroupsPPF.setParameterName("Number of Groups");
        numGroupsPPF.setHelpText("Number of groups to be simulated.");
        this.add(paramsP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        paramsP.add(bPPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(GPPF, new GridBagConstraints(1, 0, 2, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
        paramsP.add(NPPF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(gensPPF, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(AfreqPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(numGroupsPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(singleMutationCB, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        paramsP.add(sPPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        this.registerChildren(this);
    }

    void singleMutationCB_actionPerformed(ActionEvent e) {
        AfreqPPF.setEnabled(!singleMutationCB.isSelected());
    }
}
