/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sam;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class SAMPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -684318254985115235L;
    JPanel initialConditionsPanel = new JPanel();
    PopulusParameterField muPPF = new PopulusParameterField();
    PopulusParameterField gensPPF = new PopulusParameterField();
    private JPanel plotTypeP = new JPanel();
    private JRadioButton WBARvsPRB = new StyledRadioButton();
    private JRadioButton dPvsPRB = new StyledRadioButton();
    private JRadioButton GenoFvsTRB = new StyledRadioButton();
    private JRadioButton PvsTRB = new StyledRadioButton();
    private PopulusParameterField hPPF = new PopulusParameterField();
    private PopulusParameterField sPPF = new PopulusParameterField();
    private JPanel freqP = new JPanel();
    private PopulusParameterField initialFreqPPF = new PopulusParameterField();
    private JRadioButton sixFreqRB = new JRadioButton();
    private JRadioButton singFreqRB = new JRadioButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private TitledBorder titledBorder1 = new TitledBorder("Parameters");
    private TitledBorder titledBorder2 = new TitledBorder("Plot Type");
    private TitledBorder titledBorder3 = new TitledBorder("Initial Conditions");
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private ButtonGroup plotBG = new ButtonGroup();
    private ButtonGroup freqBG = new ButtonGroup();

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType;
        double[] freqs;

        if (this.PvsTRB.isSelected())
            plotType = SAMParamInfo.PvsT;
        else if (this.GenoFvsTRB.isSelected())
            plotType = SAMParamInfo.GenovsT;
        else if (this.dPvsPRB.isSelected())
            plotType = SAMParamInfo.dPvsP;
        else
            plotType = SAMParamInfo.WBar;

        if (this.singFreqRB.isSelected())
            freqs = new double[]{this.initialFreqPPF.getDouble()};
        else
            freqs = new double[]{0.02, 0.212, 0.404, 0.596, 0.788, 0.98};

        return new SAMParamInfo(sPPF.getDouble(), hPPF.getDouble(), muPPF.getDouble(), gensPPF.getInt(), plotType, freqs);
    }

    public SAMPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        initialConditionsPanel.setBorder(titledBorder1);
        plotTypeP.setBorder(titledBorder2);
        freqP.setBorder(titledBorder3);
        initialConditionsPanel.setLayout(gridBagLayout4);
        muPPF.setCurrentValue(1.0E-4);
        muPPF.setDefaultValue(1.0E-4);
        muPPF.setIncrementAmount(0.05);
        muPPF.setMaxValue(1.0);
        muPPF.setParameterName("<i>\u03bc</i>");
        muPPF.setHelpText("Mutation Rate of \"A\" Genes to \"a\" Genes");
        gensPPF.setCurrentValue(60.0);
        gensPPF.setDefaultValue(200.0);
        gensPPF.setIncrementAmount(10.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setMaxValue(1000.0);
        gensPPF.setMinValue(1.0);
        gensPPF.setParameterName("Generations");
        gensPPF.setHelpText("Number of Generation to be Simulated");
        WBARvsPRB.setText("<i>w\u0305</> vs <i>p</i>");
        dPvsPRB.setText("<i>\u0394p</i> vs <i>p</i>");
        GenoFvsTRB.setText("Genotypic Freqs vs <i>t</i>");
        PvsTRB.setSelected(true);
        PvsTRB.setText("<i>p</i> vs <i>t</i>");
        plotTypeP.setLayout(gridBagLayout3);
        hPPF.setParameterName("<i>h</i>");
        hPPF.setMaxValue(1.0);
        hPPF.setIncrementAmount(0.05);
        hPPF.setHelpText("Relative Dominance of \"A\" Alleles");
        sPPF.setParameterName("<i>s</i>");
        sPPF.setMaxValue(1.0);
        sPPF.setIncrementAmount(0.05);
        sPPF.setDefaultValue(0.6);
        sPPF.setCurrentValue(0.6);
        sPPF.setHelpText("Selection Coefficient");
        initialFreqPPF.setCurrentValue(0.1);
        initialFreqPPF.setDefaultValue(0.1);
        initialFreqPPF.setIncrementAmount(0.05);
        initialFreqPPF.setMaxValue(1.0);
        initialFreqPPF.setParameterName("Starting Frequency");
        initialFreqPPF.setHelpText("Initial Frequency");
        sixFreqRB.setText("Six Frequency");
        singFreqRB.setSelected(true);
        singFreqRB.setText("Single Frequency");
        singFreqRB.addChangeListener(e -> singFreqRB_stateChanged(e));
        freqP.setLayout(gridBagLayout2);
        this.setLayout(gridBagLayout1);
        this.add(initialConditionsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        initialConditionsPanel.add(sPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        initialConditionsPanel.add(hPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        initialConditionsPanel.add(muPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        initialConditionsPanel.add(gensPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
        this.add(plotTypeP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(PvsTRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        plotTypeP.add(GenoFvsTRB, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        plotTypeP.add(dPvsPRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        plotTypeP.add(WBARvsPRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        this.add(freqP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        freqP.add(singFreqRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        freqP.add(sixFreqRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 0), 0, 0));
        freqP.add(initialFreqPPF, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        plotBG.add(PvsTRB);
        plotBG.add(GenoFvsTRB);
        plotBG.add(dPvsPRB);
        plotBG.add(WBARvsPRB);
        freqBG.add(singFreqRB);
        freqBG.add(sixFreqRB);
        this.registerChildren(this);
    }

    void singFreqRB_stateChanged(ChangeEvent e) {
        if (!singFreqRB.isSelected() && GenoFvsTRB.isSelected())
            PvsTRB.setSelected(true);
        GenoFvsTRB.setEnabled(singFreqRB.isSelected());
        initialFreqPPF.setEnabled(singFreqRB.isSelected());
    }
}
