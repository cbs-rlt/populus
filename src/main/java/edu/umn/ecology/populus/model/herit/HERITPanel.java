/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.herit;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class HERITPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -1308377477081368096L;
    private final JPanel plotTypeP = new JPanel();
    private final JRadioButton mcRB = new JRadioButton();
    private final JRadioButton theoreticalRB = new JRadioButton();
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final JPanel paramP = new JPanel();
    private final PopulusParameterField vePPF = new PopulusParameterField();
    private final PopulusParameterField G3PPF = new PopulusParameterField();
    private final PopulusParameterField G2PPF = new PopulusParameterField();
    private final PopulusParameterField G1PPF = new PopulusParameterField();
    private final PopulusParameterField pPPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final PopulusParameterField popSizePPF = new PopulusParameterField();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private final ButtonGroup plotTypeBG = new ButtonGroup();
    long seed = System.currentTimeMillis();
    private final JCheckBox keepSeedCB = new JCheckBox();

    @Override
    public BasicPlot getPlotParamInfo() {
        if (!keepSeedCB.isSelected())
            seed = System.currentTimeMillis();

        return new HERITParamInfo(G1PPF.getDouble(), G2PPF.getDouble(), G3PPF.getDouble(), pPPF.getDouble(), vePPF.getDouble(),
                popSizePPF.getInt(), theoreticalRB.isSelected(), seed);
    }

    public HERITPanel() {
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
        mcRB.setText("Monte Carlo");
        mcRB.addChangeListener(e -> mcRB_stateChanged(e));
        theoreticalRB.setSelected(true);
        theoreticalRB.setText("Theoretical");
        plotTypeP.setLayout(gridBagLayout1);
        vePPF.setCurrentValue(5.0);
        vePPF.setDefaultValue(5.0);
        vePPF.setMaxValue(10.0);
        vePPF.setParameterName("<i>V<sub>e</>");
        vePPF.setHelpText("Environmental variance");
        G3PPF.setParameterName("<i>G<sub>3</>");
        G3PPF.setHelpText("Average phenotypic measurement for the third genotype");
        G3PPF.setMaxValue(100.0);
        G3PPF.setDefaultValue(15.0);
        G3PPF.setCurrentValue(15.0);
        G2PPF.setParameterName("<i>G<sub>2</>");
        G2PPF.setHelpText("Average phenotypic measurement for the second genotype");
        G2PPF.setMaxValue(100.0);
        G2PPF.setDefaultValue(10.0);
        G2PPF.setCurrentValue(10.0);
        G1PPF.setParameterName("<i>G<sub>1</>");
        G1PPF.setHelpText("Average phenotypic measurement for the first genotype");
        G1PPF.setMaxValue(100.0);
        G1PPF.setDefaultValue(5.0);
        G1PPF.setCurrentValue(5.0);
        pPPF.setParameterName("<i>p</i>");
        pPPF.setHelpText("Allele frequency");
        pPPF.setMaxValue(1.0);
        pPPF.setIncrementAmount(0.05);
        pPPF.setDefaultValue(0.3);
        pPPF.setCurrentValue(0.3);
        paramP.setLayout(gridBagLayout2);
        this.setLayout(gridBagLayout3);
        popSizePPF.setCurrentValue(250.0);
        popSizePPF.setDefaultValue(250.0);
        popSizePPF.setEnabled(false);
        popSizePPF.setIncrementAmount(10.0);
        popSizePPF.setIntegersOnly(true);
        popSizePPF.setMaxValue(1000.0);
        popSizePPF.setMinValue(1.0);
        popSizePPF.setParameterName("Population Size");
        popSizePPF.setHelpText("The number of generations to run to equilibrium will generally depend on the strength of selection. Usually 100 generations or less will suffice.");
        paramP.setBorder(titledBorder1);
        titledBorder1.setTitle("Parameters");
        plotTypeP.setBorder(titledBorder2);
        titledBorder2.setTitle("Plot Type");
        titledBorder3.setTitle("Increments");
        keepSeedCB.setEnabled(false);
        keepSeedCB.setText("Keep Seed");
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(keepSeedCB, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(theoreticalRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(mcRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        this.add(paramP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        paramP.add(popSizePPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(vePPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(pPPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(G1PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(G2PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        paramP.add(G3PPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        plotTypeBG.add(theoreticalRB);
        plotTypeBG.add(mcRB);
        this.registerChildren(this);
    }

    void mcRB_stateChanged(ChangeEvent e) {
        popSizePPF.setEnabled(mcRB.isSelected());
        keepSeedCB.setEnabled(mcRB.isSelected());
    }
}
