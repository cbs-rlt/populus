/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hhap;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class HHAPPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 6203698711272625912L;
    final JPanel modParamP = new JPanel();
    final PopulusParameterField tPPF = new PopulusParameterField();
    private final JPanel plotTypeP = new JPanel();
    private final JRadioButton DeFinettiRB = new JRadioButton();
    private final PopulusParameterField gensPPF = new PopulusParameterField();
    private final PopulusParameterField genIntPPF = new PopulusParameterField();
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private final ButtonGroup plotTypeBG = new ButtonGroup();
    private final JRadioButton ThreeAlleleRB = new JRadioButton();
    private final JRadioButton TwoAlleleRB = new JRadioButton();
    private final PopulusParameterField mhPPF = new PopulusParameterField();
    private final PopulusParameterField phRPPF = new PopulusParameterField();
    private final PopulusParameterField mpPPF = new PopulusParameterField();
    private final PopulusParameterField sPPF = new PopulusParameterField();
    private final JPanel hostFreqsP = new JPanel();
    private final JPanel parasideFreqsP = new JPanel();
    private final TitledBorder titledBorder1 = new TitledBorder("");
    private final TitledBorder titledBorder4 = new TitledBorder("");
    ;
    private final PopulusParameterField h3PPF = new PopulusParameterField();
    private final PopulusParameterField h2PPF = new PopulusParameterField();
    private final PopulusParameterField h1PPF = new PopulusParameterField();
    private final PopulusParameterField p3PPF = new PopulusParameterField();
    private final PopulusParameterField p2PPF = new PopulusParameterField();
    private final PopulusParameterField p1PPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final GridBagLayout gridBagLayout5 = new GridBagLayout();

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType;

        if (TwoAlleleRB.isSelected())
            plotType = HHAPParamInfo.Allele2;
        else if (ThreeAlleleRB.isSelected())
            plotType = HHAPParamInfo.Allele3;
        else
            plotType = HHAPParamInfo.DeFin;

        double[] hFreqs = new double[]{h1PPF.getDouble(), h2PPF.getDouble(), h3PPF.getDouble()};
        double[] pFreqs = new double[]{p1PPF.getDouble(), p2PPF.getDouble(), p3PPF.getDouble()};

        return new HHAPParamInfo(hFreqs, pFreqs, sPPF.getDouble(), tPPF.getDouble(), mhPPF.getDouble(), mpPPF.getDouble(),
                phRPPF.getDouble(), gensPPF.getInt(), genIntPPF.getInt(), plotType);
    }

    public HHAPPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder2 = new TitledBorder("Individual Coefficients");
        titledBorder3 = new TitledBorder("Plot Type");
        modParamP.setBorder(titledBorder2);
        plotTypeP.setBorder(titledBorder3);
        modParamP.setLayout(gridBagLayout3);
        tPPF.setCurrentValue(0.15);
        tPPF.setDefaultValue(0.15);
        tPPF.setIncrementAmount(0.05);
        tPPF.setMaxValue(1.0);
        tPPF.setParameterName("<i>t</i>");
        tPPF.setHelpText("Penalty for thwarted parasites");
        DeFinettiRB.setText("3-Allele De Finetti");
        gensPPF.setParameterName("Generations");
        gensPPF.setHelpText("Selection Coefficient");
        gensPPF.setMaxValue(30000.0);
        gensPPF.setMinValue(1.0);
        gensPPF.setCurrentValue(5000.0);
        gensPPF.setDefaultValue(5000.0);
        gensPPF.setIncrementAmount(0.05);
        gensPPF.setIntegersOnly(true);
        genIntPPF.setParameterName("Generation Interval");
        genIntPPF.setHelpText("Recombination Fraction");
        genIntPPF.setMaxValue(500.0);
        genIntPPF.setMinValue(1.0);
        genIntPPF.setIntegersOnly(true);
        genIntPPF.setDefaultValue(5.0);
        genIntPPF.setCurrentValue(5.0);
        plotTypeP.setLayout(gridBagLayout4);
        this.setLayout(gridBagLayout1);
        ThreeAlleleRB.setSelected(true);
        ThreeAlleleRB.setText("3-Allele");
        TwoAlleleRB.setText("2-Allele");
        TwoAlleleRB.addChangeListener(e -> TwoAlleleRB_stateChanged(e));
        mhPPF.setIncrementAmount(0.05);
        mhPPF.setMaxValue(1.0);
        mhPPF.setParameterName("<i>m<sub>h</sub></i>");
        mhPPF.setHelpText("Mutation-Migration rate for hosts");
        phRPPF.setMaxValue(100.0);
        phRPPF.setParameterName("p/h Ratio");
        phRPPF.setCurrentValue(1.0);
        phRPPF.setDefaultValue(1.0);
        phRPPF.setHelpText("Ratio of parasite generations to host generations");
        phRPPF.setIntegersOnly(true);
        mpPPF.setIncrementAmount(0.05);
        mpPPF.setMaxValue(1.0);
        mpPPF.setParameterName("<i>m<sub>p</sub></i>");
        mpPPF.setHelpText("Mutation-Migration rate for parasites");
        sPPF.setCurrentValue(0.05);
        sPPF.setDefaultValue(0.05);
        sPPF.setIncrementAmount(0.05);
        sPPF.setMaxValue(1.0);
        sPPF.setParameterName("<i>s</i>");
        sPPF.setHelpText("Penalty for parasitized hosts");
        titledBorder2.setTitle("Parameters");
        hostFreqsP.setBorder(titledBorder1);
        hostFreqsP.setLayout(gridBagLayout2);
        titledBorder1.setTitle("Host Initial Frequencies");
        parasideFreqsP.setBorder(titledBorder4);
        parasideFreqsP.setLayout(gridBagLayout5);
        titledBorder4.setTitle("Parasite Initial Frequencies");
        h3PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        h3PPF.setParameterName("<i>h<sub>3</sub></i>");
        h3PPF.setMaxValue(1.0);
        h3PPF.setIncrementAmount(0.05);
        h3PPF.setDefaultValue(0.3);
        h3PPF.setCurrentValue(0.3);
        h2PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        h2PPF.setParameterName("<i>h<sub>2</sub></i>");
        h2PPF.setMaxValue(1.0);
        h2PPF.setIncrementAmount(0.05);
        h2PPF.setDefaultValue(0.3);
        h2PPF.setCurrentValue(0.3);
        h1PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        h1PPF.setParameterName("<i>h<sub>1</sub></i>");
        h1PPF.setMaxValue(1.0);
        h1PPF.setIncrementAmount(0.05);
        h1PPF.setDefaultValue(0.4);
        h1PPF.setCurrentValue(0.4);
        p3PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        p3PPF.setParameterName("<i>p<sub>3</sub></i>");
        p3PPF.setMaxValue(1.0);
        p3PPF.setIncrementAmount(0.05);
        p3PPF.setDefaultValue(0.4);
        p3PPF.setCurrentValue(0.4);
        p2PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        p2PPF.setParameterName("<i>p<sub>2</sub></i>");
        p2PPF.setMaxValue(1.0);
        p2PPF.setIncrementAmount(0.05);
        p2PPF.setDefaultValue(0.32);
        p2PPF.setCurrentValue(0.32);
        p1PPF.setHelpText("Initial Allelic Frequency for Male Trait");
        p1PPF.setParameterName("<i>p<sub>1</sub></i>");
        p1PPF.setMaxValue(1.0);
        p1PPF.setIncrementAmount(0.05);
        p1PPF.setDefaultValue(0.28);
        p1PPF.setCurrentValue(0.28);
        this.add(modParamP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        modParamP.add(genIntPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        modParamP.add(gensPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(tPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(mhPPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(phRPPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(mpPPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(sPPF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        plotTypeP.add(DeFinettiRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
        plotTypeP.add(ThreeAlleleRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(TwoAlleleRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        this.add(parasideFreqsP, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        parasideFreqsP.add(p1PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        parasideFreqsP.add(p2PPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        parasideFreqsP.add(p3PPF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        this.add(hostFreqsP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        hostFreqsP.add(h1PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        hostFreqsP.add(h2PPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        hostFreqsP.add(h3PPF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeBG.add(DeFinettiRB);
        plotTypeBG.add(TwoAlleleRB);
        plotTypeBG.add(ThreeAlleleRB);
        this.registerChildren(this);
    }


    void TwoAlleleRB_stateChanged(ChangeEvent e) {
        h2PPF.setEnabled(!TwoAlleleRB.isSelected());
        h3PPF.setEnabled(!TwoAlleleRB.isSelected());
        p2PPF.setEnabled(!TwoAlleleRB.isSelected());
        p3PPF.setEnabled(!TwoAlleleRB.isSelected());
        genIntPPF.setEnabled(!TwoAlleleRB.isSelected());
    }
}
