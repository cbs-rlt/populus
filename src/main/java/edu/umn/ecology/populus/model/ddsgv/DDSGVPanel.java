/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ddsgv;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class DDSGVPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5726820884292076983L;
    JPanel modParamP = new JPanel();
    PopulusParameterField rAaPPF = new PopulusParameterField();
    private PopulusParameterField raaPPF = new PopulusParameterField();
    private PopulusParameterField NPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private ButtonGroup freqRB = new ButtonGroup();
    private PopulusParameterField KAAPPF = new PopulusParameterField();
    private PopulusParameterField KaaPPF = new PopulusParameterField();
    private PopulusParameterField gensPPF = new PopulusParameterField();
    private PopulusParameterField KAaPPF = new PopulusParameterField();
    private PopulusParameterField rAAPPF = new PopulusParameterField();
    private JPanel freqsP = new JPanel();
    private JPanel paramsP = new JPanel();
    private JRadioButton singleRB = new JRadioButton();
    private JRadioButton nineRB = new JRadioButton();
    private PopulusParameterField freqPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();

    @Override
    public BasicPlot getPlotParamInfo() {
        double[] freqs;
        if (singleRB.isSelected())
            freqs = new double[]{freqPPF.getDouble()};
        else
            freqs = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};


        return new DDSGVParamInfo(new double[]{rAAPPF.getDouble(), rAaPPF.getDouble(), raaPPF.getDouble()},
                new double[]{KAAPPF.getDouble(), KAaPPF.getDouble(), KaaPPF.getDouble()},
                freqs, NPPF.getInt(), gensPPF.getInt());
    }

    public DDSGVPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        modParamP.setLayout(gridBagLayout3);
        rAaPPF.setCurrentValue(0.7);
        rAaPPF.setDefaultValue(0.7);
        rAaPPF.setIncrementAmount(0.1);
        rAaPPF.setMaxValue(5.0);
        rAaPPF.setParameterName("<i>r<sub>Aa</>");
        rAaPPF.setHelpText("Intrinsic rate of increase for the \"Aa\" genotype");
        raaPPF.setParameterName("<i>r<sub>aa</>");
        raaPPF.setMaxValue(5.0);
        raaPPF.setCurrentValue(0.6);
        raaPPF.setDefaultValue(0.6);
        raaPPF.setIncrementAmount(0.1);
        raaPPF.setHelpText("Intrinsic rate of increase for the \"aa\" genotype");
        NPPF.setParameterName("<i>N</i>");
        NPPF.setMaxValue(10000.0);
        NPPF.setIncrementAmount(2.0);
        NPPF.setIntegersOnly(true);
        NPPF.setDefaultValue(5.0);
        NPPF.setCurrentValue(5.0);
        NPPF.setHelpText("Initial population size");
        this.setLayout(gridBagLayout1);
        KAAPPF.setCurrentValue(800.0);
        KAAPPF.setDefaultValue(800.0);
        KAAPPF.setIncrementAmount(50.0);
        KAAPPF.setMaxValue(10000.0);
        KAAPPF.setParameterName("<i>K<sub>AA</>");
        KAAPPF.setHelpText("Carrying capacity for the \"AA\" genotype");
        KaaPPF.setCurrentValue(900.0);
        KaaPPF.setDefaultValue(900.0);
        KaaPPF.setIncrementAmount(50.0);
        KaaPPF.setMaxValue(10000.0);
        KaaPPF.setParameterName("<i>K<sub>aa</>");
        KaaPPF.setHelpText("Carrying capacity for the \"aa\" genotype");
        gensPPF.setCurrentValue(50.0);
        gensPPF.setDefaultValue(400.0);
        gensPPF.setIncrementAmount(20.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setMaxValue(5000.0);
        gensPPF.setParameterName("Generations");
        KAaPPF.setCurrentValue(1000.0);
        KAaPPF.setDefaultValue(1000.0);
        KAaPPF.setIncrementAmount(50.0);
        KAaPPF.setMaxValue(10000.0);
        KAaPPF.setParameterName("<i>K<sub>Aa</>");
        KAaPPF.setHelpText("Carrying capacity for the \"Aa\" genotype");
        rAAPPF.setCurrentValue(0.8);
        rAAPPF.setDefaultValue(0.8);
        rAAPPF.setIncrementAmount(0.1);
        rAAPPF.setMaxValue(5.0);
        rAAPPF.setParameterName("<i>r<sub>AA</>");
        rAAPPF.setHelpText("Intrinsic rate of increase for the \"AA\" genotype");
        singleRB.setSelected(true);
        singleRB.setText("Single Frequency");
        singleRB.addChangeListener(e -> singleRB_stateChanged(e));
        nineRB.setText("Nine-Frequency");
        freqPPF.setParameterName("Frequency");
        freqPPF.setMaxValue(1.0);
        freqPPF.setCurrentValue(0.1);
        freqPPF.setDefaultValue(0.1);
        freqPPF.setIncrementAmount(0.05);
        freqPPF.setHelpText("Initial frequency of the \"A\" allele");
        freqsP.setLayout(gridBagLayout2);
        paramsP.setLayout(gridBagLayout4);
        this.add(modParamP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        modParamP.add(NPPF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
        paramsP.add(raaPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        paramsP.add(rAaPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        paramsP.add(KAAPPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        paramsP.add(KaaPPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        modParamP.add(gensPPF, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramsP.add(KAaPPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        paramsP.add(rAAPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        modParamP.add(freqsP, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        freqsP.add(freqPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        freqsP.add(singleRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        freqsP.add(nineRB, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modParamP.add(paramsP, new GridBagConstraints(0, 0, 1, 3, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        freqRB.add(singleRB);
        freqRB.add(nineRB);
        this.registerChildren(this);
    }

    void singleRB_stateChanged(ChangeEvent e) {
        freqPPF.setEnabled(singleRB.isSelected());
    }
}
