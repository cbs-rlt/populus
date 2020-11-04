/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sotl;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SOTLPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -3563042864719360505L;
    final JPanel wPanel = new JPanel();
    private final JPanel plotTypeP = new JPanel();
    private final JRadioButton WBARvstRB = new StyledRadioButton();
    private final JRadioButton DvstRB = new StyledRadioButton();
    private final JRadioButton pvstRB = new StyledRadioButton();
    private final JRadioButton PvstRB = new StyledRadioButton();
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final JPanel paramP = new JPanel();
    private final PopulusParameterField RPPF = new PopulusParameterField();
    private final PopulusParameterField PabPPF = new PopulusParameterField();
    private final PopulusParameterField PaBPPF = new PopulusParameterField();
    private final PopulusParameterField PAbPPF = new PopulusParameterField();
    private final PopulusParameterField PABPPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final PopulusParameterField gensPPF = new PopulusParameterField();
    private final PopulusParameterField waabbPPF = new PopulusParameterField();
    private final PopulusParameterField waaBbPPF = new PopulusParameterField();
    private final PopulusParameterField waaBBPPF = new PopulusParameterField();
    private final PopulusParameterField wAabbPPF = new PopulusParameterField();
    private final PopulusParameterField wAaBbPPF = new PopulusParameterField();
    private final PopulusParameterField wAaBBPPF = new PopulusParameterField();
    private final PopulusParameterField wAAbbPPF = new PopulusParameterField();
    private final PopulusParameterField wAABbPPF = new PopulusParameterField();
    private final PopulusParameterField wAABBPPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private final ButtonGroup plotTypeBG = new ButtonGroup();

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType;
        double[][] fitnesses = new double[3][3];
        double[] freqs = new double[4];
        fitnesses[0][0] = wAABBPPF.getDouble();
        fitnesses[0][1] = wAABbPPF.getDouble();
        fitnesses[0][2] = wAAbbPPF.getDouble();
        fitnesses[1][0] = wAaBBPPF.getDouble();
        fitnesses[1][1] = wAaBbPPF.getDouble();
        fitnesses[1][2] = wAabbPPF.getDouble();
        fitnesses[2][0] = waaBBPPF.getDouble();
        fitnesses[2][1] = waaBbPPF.getDouble();
        fitnesses[2][2] = waabbPPF.getDouble();

        freqs[0] = PABPPF.getDouble();
        freqs[1] = PAbPPF.getDouble();
        freqs[2] = PaBPPF.getDouble();
        freqs[3] = PabPPF.getDouble();

        if (PvstRB.isSelected())
            plotType = SOTLParamInfo.Pvst;
        else if (pvstRB.isSelected())
            plotType = SOTLParamInfo.pvst;
        else if (DvstRB.isSelected())
            plotType = SOTLParamInfo.Dvst;
        else
            plotType = SOTLParamInfo.WBARvst;

        return new SOTLParamInfo(fitnesses, freqs, RPPF.getDouble(), plotType, gensPPF.getInt());
    }

    public SOTLPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {//this is needed because w/o the title doesn't fit
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        titledBorder3 = new TitledBorder("");
        wPanel.setLayout(gridBagLayout4);
        WBARvstRB.setText("<i>w\u0305</i> vs <i>t</i>");
        DvstRB.setText("<i>D</i> vs <i>t</i>");
        pvstRB.setText("<i>p</i> vs <i>t</i>");
        PvstRB.setSelected(true);
        PvstRB.setText("<i>P</i> vs <i>t</i>");
        plotTypeP.setLayout(gridBagLayout1);
        RPPF.setCurrentValue(0.1);
        RPPF.setDefaultValue(0.1);
        RPPF.setIncrementAmount(0.05);
        RPPF.setMaxValue(1.0);
        RPPF.setParameterName("<i>R</i>");
        RPPF.setHelpText("This is the recombination fraction. R = 0 means complete linkage; R = 0.5 means unlinked loci. Enter a number between 0 and 0.5.");
        PabPPF.setParameterName("<i>P<sub>ab</>");
        PabPPF.setHelpText("Frequency of gamete type ab");
        PabPPF.setMaxValue(1.0);
        PabPPF.setIncrementAmount(0.05);
        PabPPF.setDefaultValue(0.2);
        PabPPF.setCurrentValue(0.2);
        PaBPPF.setParameterName("<i>P<sub>aB</>");
        PaBPPF.setHelpText("Frequency of gamete type aB");
        PaBPPF.setMaxValue(1.0);
        PaBPPF.setIncrementAmount(0.05);
        PaBPPF.setDefaultValue(0.2);
        PaBPPF.setCurrentValue(0.3);
        PAbPPF.setParameterName("<i>P<sub>Ab</>");
        PAbPPF.setHelpText("Frequency of gamete type Ab");
        PAbPPF.setMaxValue(1.0);
        PAbPPF.setIncrementAmount(0.05);
        PAbPPF.setDefaultValue(0.1);
        PAbPPF.setCurrentValue(0.1);
        PABPPF.setParameterName("<i>P<sub>AB</>");
        PABPPF.setHelpText("Frequency of gamete type AB");
        PABPPF.setMaxValue(1.0);
        PABPPF.setIncrementAmount(0.05);
        PABPPF.setDefaultValue(0.4);
        PABPPF.setCurrentValue(0.4);
        paramP.setLayout(gridBagLayout2);
        this.setLayout(gridBagLayout3);
        gensPPF.setCurrentValue(30.0);
        gensPPF.setDefaultValue(30.0);
        gensPPF.setIncrementAmount(10.0);
        gensPPF.setMaxValue(1000.0);
        gensPPF.setMinValue(1.0);
        gensPPF.setParameterName("Generations");
        gensPPF.setHelpText("The number of generations to run to equilibrium will generally depend on the strength of selection. Usually 100 generations or less will suffice.");
        waabbPPF.setCurrentValue(1.0);
        waabbPPF.setDefaultValue(1.0);
        waabbPPF.setIncrementAmount(0.1);
        waabbPPF.setMaxValue(5.0);
        waabbPPF.setParameterName("<i>w<sub>aabb</>");
        waabbPPF.setHelpText("Fitness of aabb genotypes (any positive number)");
        waaBbPPF.setParameterName("<i>w<sub>aaBb</>");
        waaBbPPF.setHelpText("Fitness of aaBb genotypes (any positive number)");
        waaBbPPF.setMaxValue(5.0);
        waaBbPPF.setIncrementAmount(0.1);
        waaBbPPF.setDefaultValue(1.0);
        waaBbPPF.setCurrentValue(1.0);
        waaBBPPF.setParameterName("<i>w<sub>aaBB</>");
        waaBBPPF.setHelpText("Fitness of aaBB genotypes (any positive number)");
        waaBBPPF.setMaxValue(5.0);
        waaBBPPF.setIncrementAmount(0.1);
        waaBBPPF.setDefaultValue(1.0);
        waaBBPPF.setCurrentValue(1.0);
        wAabbPPF.setParameterName("<i>w<sub>Aabb</>");
        wAabbPPF.setHelpText("Fitness of Aabb genotypes (any positive number)");
        wAabbPPF.setMaxValue(5.0);
        wAabbPPF.setIncrementAmount(0.1);
        wAabbPPF.setDefaultValue(1.0);
        wAabbPPF.setCurrentValue(1.0);
        wAaBbPPF.setParameterName("<i>w<sub>AaBb</>");
        wAaBbPPF.setHelpText("Fitness of AaBb genotypes (any positive number)");
        wAaBbPPF.setMaxValue(5.0);
        wAaBbPPF.setIncrementAmount(0.1);
        wAaBbPPF.setDefaultValue(1.0);
        wAaBbPPF.setCurrentValue(1.0);
        wAaBBPPF.setParameterName("<i>w<sub>AaBB</>");
        wAaBBPPF.setHelpText("Fitness of AaBB genotypes (any positive number)");
        wAaBBPPF.setMaxValue(5.0);
        wAaBBPPF.setIncrementAmount(0.1);
        wAaBBPPF.setDefaultValue(1.0);
        wAaBBPPF.setCurrentValue(1.0);
        wAAbbPPF.setParameterName("<i>w<sub>AAbb</>");
        wAAbbPPF.setHelpText("Fitness of AAbb genotypes (any positive number)");
        wAAbbPPF.setMaxValue(5.0);
        wAAbbPPF.setIncrementAmount(0.1);
        wAAbbPPF.setDefaultValue(1.0);
        wAAbbPPF.setCurrentValue(1.0);
        wAABbPPF.setParameterName("<i>w<sub>AABb</>");
        wAABbPPF.setHelpText("Fitness of AABb genotypes (any positive number)");
        wAABbPPF.setMaxValue(5.0);
        wAABbPPF.setIncrementAmount(0.1);
        wAABbPPF.setDefaultValue(1.0);
        wAABbPPF.setCurrentValue(1.0);
        wAABBPPF.setParameterName("<i>w<sub>AABB</>");
        wAABBPPF.setHelpText("Fitness of AABB genotypes (any positive number)");
        wAABBPPF.setMaxValue(5.0);
        wAABBPPF.setIncrementAmount(0.1);
        wAABBPPF.setDefaultValue(1.0);
        wAABBPPF.setCurrentValue(1.0);
        paramP.setBorder(titledBorder1);
        titledBorder1.setTitle("Parameters");
        plotTypeP.setBorder(titledBorder2);
        titledBorder2.setTitle("Plot Type");
        wPanel.setBorder(titledBorder3);
        titledBorder3.setTitle("Fitnesses");
        this.add(wPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAABBPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAABbPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAAbbPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAaBBPPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAaBbPPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(wAabbPPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(waaBBPPF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(waaBbPPF, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        wPanel.add(waabbPPF, new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(PvstRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(pvstRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(DvstRB, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(WBARvstRB, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        this.add(paramP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        paramP.add(PABPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(PAbPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(PaBPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(PabPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(RPPF, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(gensPPF, new GridBagConstraints(1, 2, 1, 2, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        plotTypeBG.add(PvstRB);
        plotTypeBG.add(pvstRB);
        plotTypeBG.add(DvstRB);
        plotTypeBG.add(WBARvstRB);
        this.registerChildren(this);
    }
}
