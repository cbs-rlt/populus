/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dsoqc;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class DSOQCPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 2759769583551935323L;
    JPanel wPanel = new JPanel();
    private JPanel plotTypeP = new JPanel();
    private JRadioButton MPvsTRB = new StyledRadioButton();
    private JRadioButton IvsPRB = new JRadioButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel paramP = new JPanel();
    private PopulusParameterField repopsPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private PopulusParameterField gensIntervalPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private ButtonGroup plotTypeBG = new ButtonGroup();
    private PopulusParameterField pPPF = new PopulusParameterField();
    private PopulusParameterField VePPF = new PopulusParameterField();
    private PopulusParameterField popsPPF = new PopulusParameterField();
    DSOQCParamInfo dPI = null;
    boolean isIterate = false;
    boolean doSwitch = false;
    private PopulusParameterField GaaPPF = new PopulusParameterField();
    private PopulusParameterField GAAPPF = new PopulusParameterField();
    private JPanel genoValsP = new JPanel();
    private PopulusParameterField GAaPPF = new PopulusParameterField();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    @Override
    public BasicPlot getPlotParamInfo() {
        if (!isIterate && !doSwitch) {
            dPI = new DSOQCParamInfo(GAAPPF.getDouble(), GAaPPF.getDouble(), GaaPPF.getDouble(), pPPF.getDouble(), VePPF.getDouble(),
                    popsPPF.getInt(), repopsPPF.getInt(), MPvsTRB.isSelected());
        }

        if (doSwitch) {
            dPI.switchOutputs();
            doSwitch = false;
        } else {
            for (int i = 0; i < gensIntervalPPF.getInt(); i++)
                dPI.doGeneration();
        }
        isIterate = false;
        return dPI;
    }

    @Override
    public String getOutputGraphName() {
        if (!MPvsTRB.isSelected())
            return "Selection on a Quantitative Trait (Individual Values)";
        else
            return "Selection on a Quantitative Trait (Phenotypic Trajectory)";
    }

    @Override
    public void willIterate(boolean b) {
        isIterate = b;
    }

    public void switchOutputType() {
        doSwitch = true;
    }

    public DSOQCPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder1 = new TitledBorder("");
        titledBorder2 = new TitledBorder("");
        titledBorder3 = new TitledBorder("");
        wPanel.setLayout(gridBagLayout4);
        MPvsTRB.setText("Mean Phenotype vs <i>t</>");
        IvsPRB.setSelected(true);
        IvsPRB.setText("Individuals vs Phenotype");
        plotTypeP.setLayout(gridBagLayout1);
        repopsPPF.setParameterName("<i>N</> Selected");
        repopsPPF.setMaxValue(10000.0);
        repopsPPF.setMinValue(1.0);
        repopsPPF.setIncrementAmount(5.0);
        repopsPPF.setIntegersOnly(true);
        repopsPPF.setDefaultValue(25.0);
        repopsPPF.setCurrentValue(25.0);
        repopsPPF.setHelpText("This is the number of individuals with the highest phenotype used to repopulate the next generation");
        paramP.setLayout(gridBagLayout2);
        this.setLayout(gridBagLayout3);
        gensIntervalPPF.setCurrentValue(1.0);
        gensIntervalPPF.setDefaultValue(1.0);
        gensIntervalPPF.setIncrementAmount(10.0);
        gensIntervalPPF.setIntegersOnly(true);
        gensIntervalPPF.setMaxValue(1000.0);
        gensIntervalPPF.setMinValue(1.0);
        gensIntervalPPF.setParameterName("Iteration Interval");
        gensIntervalPPF.setHelpText("Number of generations to advance the model per iteration");
        titledBorder1.setTitle("Genotypic Values");
        plotTypeP.setBorder(titledBorder2);
        titledBorder2.setTitle("Plot Type");
        titledBorder3.setTitle("Fitnesses");
        pPPF.setCurrentValue(0.05);
        pPPF.setDefaultValue(0.05);
        pPPF.setIncrementAmount(0.05);
        pPPF.setMaxValue(1.0);
        pPPF.setParameterName("<i>p</i>");
        pPPF.setHelpText("Allele frequency");
        VePPF.setCurrentValue(10.0);
        VePPF.setDefaultValue(10.0);
        VePPF.setIncrementAmount(10.0);
        VePPF.setMaxValue(1000.0);
        VePPF.setMinValue(1.0);
        VePPF.setParameterName("<i>V<sub>e</>");
        VePPF.setHelpText("Environmental variance");
        popsPPF.setParameterName("<i>N</>");
        popsPPF.setMinValue(1.0);
        popsPPF.setMaxValue(10000.0);
        popsPPF.setIncrementAmount(10.0);
        popsPPF.setIntegersOnly(true);
        popsPPF.setDefaultValue(100.0);
        popsPPF.setCurrentValue(100.0);
        popsPPF.setHelpText("Population Size");
        GaaPPF.setParameterName("<i>G<sub>aa</>");
        GaaPPF.setMaxValue(100.0);
        GaaPPF.setIncrementAmount(0.1);
        GaaPPF.setDefaultValue(15.0);
        GaaPPF.setCurrentValue(15.0);
        GaaPPF.setHelpText("Average phenotype for the \"aa\" genotype in the absence of environmental effects");
        GAAPPF.setParameterName("<i>G<sub>AA</>");
        GAAPPF.setMaxValue(100.0);
        GAAPPF.setIncrementAmount(0.1);
        GAAPPF.setDefaultValue(25.0);
        GAAPPF.setCurrentValue(25.0);
        GAAPPF.setHelpText("Average phenotype for the \"AA\" genotype in the absence of environmental effects");
        GAaPPF.setParameterName("<i>G<sub>Aa</>");
        GAaPPF.setMaxValue(100.0);
        GAaPPF.setIncrementAmount(0.1);
        GAaPPF.setDefaultValue(20.0);
        GAaPPF.setCurrentValue(20.0);
        GAaPPF.setHelpText("Average phenotype for the \"Aa\" genotype in the absence of environmental effects");
        genoValsP.setLayout(gridBagLayout5);
        genoValsP.setBorder(titledBorder1);
        this.add(wPanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(IvsPRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(MPvsTRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        this.add(paramP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        paramP.add(gensIntervalPPF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
        paramP.add(repopsPPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
        paramP.add(popsPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
        this.add(genoValsP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        genoValsP.add(GAaPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 15), 0, 0));
        genoValsP.add(GaaPPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 15), 0, 0));
        genoValsP.add(GAAPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 15), 0, 0));
        plotTypeBG.add(IvsPRB);
        plotTypeBG.add(MPvsTRB);
        wPanel.add(pPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
        wPanel.add(VePPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
        this.registerChildren(this);
    }
}
