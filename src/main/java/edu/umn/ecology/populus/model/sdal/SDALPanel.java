/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sdal;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

/**
 * selection on a diallelic autosomal locus panel
 */

public class SDALPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 104781528256778392L;
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.sdal.Res");
    final JPanel coefficientsPanel = new JPanel();
    final JPanel initialConditionsPanel = new JPanel();
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    final PopulusParameterField paramwAa = new PopulusParameterField();
    final SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
    final JRadioButton sixInitialFrequenciesButton = new JRadioButton();
    final Border border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
    ;
    final PopulusParameterField paramh = new PopulusParameterField();
    final TitledBorder titledBorder1 = new TitledBorder(border1);
    final PopulusParameterField paramInitialFrequency = new PopulusParameterField();
    final Border border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
    final ButtonGroup bg1 = new ButtonGroup();
    final TitledBorder titledBorder2 = new TitledBorder(border2);
    final JPanel selectionPanel = new JPanel();
    final Border border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
    final JPanel plotOptionsPanel = new JPanel();
    final TitledBorder titledBorder3 = new TitledBorder(border3);
    final PopulusParameterField paramwAA = new PopulusParameterField();
    final JRadioButton pvstButton = new StyledRadioButton();
    final PopulusParameterField params = new PopulusParameterField();
    final SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
    final JRadioButton oneInitialFrequencyButton = new JRadioButton();
    final JRadioButton genotypicFrequencyButton = new StyledRadioButton();
    final SimpleVFlowLayout simpleVFlowLayout4 = new SimpleVFlowLayout();
    final JRadioButton deltapvspButton = new StyledRadioButton();
    final PopulusParameterField paramGens = new PopulusParameterField();
    final JRadioButton wbarvspButton = new StyledRadioButton();
    final ButtonGroup bg2 = new ButtonGroup();
    final JTabbedPane jTabbedPane1 = new JTabbedPane();
    final JScrollPane fitnessScrollPanel = new JScrollPane();
    final JPanel fitnessPanel = new JPanel();
    final PopulusParameterField paramwaa = new PopulusParameterField();
    final GridBagLayout gridBagLayout = new GridBagLayout();

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType, freqs, coeffType;

        //get plot type
        if (pvstButton.isSelected()) {
            plotType = SDALParamInfo.PvT;
        } else {
            if (genotypicFrequencyButton.isSelected()) {
                plotType = SDALParamInfo.FREQvT;
            } else {
                if (this.wbarvspButton.isSelected()) {
                    plotType = SDALParamInfo.WBARvP;
                } else {
                    plotType = SDALParamInfo.DPvP;
                }
            }
        }

        //get freq type
        if (oneInitialFrequencyButton.isSelected()) {
            freqs = SDALParamInfo.SINGLE_FREQ;
        } else {
            freqs = SDALParamInfo.SIX_FREQ;
        }

        //get coeff type
        if (jTabbedPane1.getSelectedComponent() == fitnessScrollPanel) {
            coeffType = SDALParamInfo.FITNESS;
        } else {
            coeffType = SDALParamInfo.SELECTION;
        }
        return new SDALParamInfo(plotType, coeffType, paramwAA.getDouble(), paramwAa.getDouble(), paramwaa.getDouble(), params.getDouble(), paramh.getDouble(), freqs, paramInitialFrequency.getDouble(), paramGens.getDouble());
    }

    public SDALPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void jTabbedPane1_stateChanged(ChangeEvent e) {
        boolean fE = jTabbedPane1.getSelectedComponent() == fitnessScrollPanel;
        paramwAA.setEnabled(fE);
        paramwAa.setEnabled(fE);
        paramwaa.setEnabled(fE);
        paramh.setEnabled(!fE);
        params.setEnabled(!fE);
        if (fE) {
            double sDouble = params.getDouble();
            paramwAA.setCurrentValue(1.0);
            paramwAa.setCurrentValue(1 - sDouble * paramh.getDouble());
            paramwaa.setCurrentValue(1 - sDouble);
        } else {
            double sDouble = 1 - paramwaa.getDouble() / paramwAA.getDouble();
            paramh.setCurrentValue((1 - paramwAa.getDouble()) / sDouble);
            params.setCurrentValue(sDouble);
        }
    }

    void oneInitialFrequencyButton_actionPerformed(ActionEvent e) {
        paramInitialFrequency.setEnabled(true);
        genotypicFrequencyButton.setEnabled(true);
    }

    void sixInitialFrequenciesButton_actionPerformed(ActionEvent e) {
        paramInitialFrequency.setEnabled(false);
        if (genotypicFrequencyButton.isSelected()) {
            pvstButton.setSelected(true);
        }
        genotypicFrequencyButton.setEnabled(false);
    }

    private void jbInit() throws Exception {
        titledBorder1.setTitle(res.getString("Plot_Options"));
        titledBorder2.setTitle(res.getString("Fitness_Selection"));
        titledBorder3.setTitle(res.getString("Initial_Conditions"));
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0};
        setLayout(gridBagLayout);
        initialConditionsPanel.setBorder(titledBorder3);
        initialConditionsPanel.setLayout(simpleVFlowLayout4);
        oneInitialFrequencyButton.setSelected(true);
        oneInitialFrequencyButton.setText(res.getString("One_Initial_Frequency"));
        oneInitialFrequencyButton.addActionListener(this::oneInitialFrequencyButton_actionPerformed);
        paramInitialFrequency.setCurrentValue(0.1);
        paramInitialFrequency.setDefaultValue(0.1);
        paramInitialFrequency.setIncrementAmount(0.05);
        paramInitialFrequency.setMaxValue(1.0);
        paramInitialFrequency.setParameterName(res.getString("Initial_Frequency"));
        paramInitialFrequency.setHelpText("Starting Frequency");
        sixInitialFrequenciesButton.setText(res.getString("Six_Initial"));
        sixInitialFrequenciesButton.addActionListener(this::sixInitialFrequenciesButton_actionPerformed);
        plotOptionsPanel.setBorder(titledBorder1);
        plotOptionsPanel.setLayout(simpleVFlowLayout1);
        pvstButton.setSelected(true);
        pvstButton.setText("<i>p</i> vs <i>t</>");
        pvstButton.setFocusPainted(false);
        genotypicFrequencyButton.setText(res.getString("Genotypic_frequency"));
        genotypicFrequencyButton.setFocusPainted(false);
        deltapvspButton.setText("<i>\u0394p</> vs <i>p</i>");
        deltapvspButton.setFocusPainted(false);
        //Unicode 0305 is the combining overline character.
        //For some reason, Mac and Linux like to add a newline
        //when we use it.  I can't find anything I'm doing wrong.
        wbarvspButton.setText("<i>w\u0305</i> vs <i>p</i>");
        wbarvspButton.setFocusPainted(false);
        GridBagConstraints gbc_plotOptionsPanel = new GridBagConstraints();
        gbc_plotOptionsPanel.fill = GridBagConstraints.VERTICAL;
        gbc_plotOptionsPanel.weighty = 1.0;
        gbc_plotOptionsPanel.anchor = GridBagConstraints.WEST;
        gbc_plotOptionsPanel.insets = new Insets(0, 0, 0, 5);
        gbc_plotOptionsPanel.gridx = 0;
        gbc_plotOptionsPanel.gridy = 0;
        this.add(plotOptionsPanel, gbc_plotOptionsPanel);
        plotOptionsPanel.add(pvstButton, null);
        plotOptionsPanel.add(genotypicFrequencyButton, null);
        plotOptionsPanel.add(deltapvspButton, null);
        plotOptionsPanel.add(wbarvspButton, null);
        bg1.add(this.deltapvspButton);
        bg1.add(this.wbarvspButton);
        bg1.add(this.pvstButton);
        bg1.add(this.genotypicFrequencyButton);
        coefficientsPanel.setBorder(titledBorder2);
        fitnessPanel.setLayout(simpleVFlowLayout2);
        paramwAA.setCurrentValue(1.0);
        paramwAA.setDefaultValue(1.0);
        paramwAA.setIncrementAmount(0.05);
        paramwAA.setMaxValue(1.0);
        paramwAA.setParameterName("<i>w<sub>AA</sub></i>");
        paramwAA.setHelpText("Relative Fitness of AA Genotypes");
        paramwAa.setCurrentValue(0.95);
        paramwAa.setDefaultValue(0.95);
        paramwAa.setIncrementAmount(0.05);
        paramwAa.setMaxValue(1.0);
        paramwAa.setParameterName("<i>w<sub>Aa</sub></i>");
        paramwAa.setHelpText("Relative Fitness of Aa Genotypes");
        paramwaa.setCurrentValue(0.9);
        paramwaa.setDefaultValue(0.9);
        paramwaa.setIncrementAmount(0.05);
        paramwaa.setMaxValue(1.0);
        paramwaa.setParameterName("<i>w<sub>aa</sub></i>");
        paramwaa.setHelpText("Relative Fitness of aa Genotypes");
        selectionPanel.setLayout(simpleVFlowLayout3);
        paramh.setCurrentValue(0.5);
        paramh.setDefaultValue(0.5);
        paramh.setIncrementAmount(0.05);
        paramh.setMaxValue(1.0);
        paramh.setParameterName("<i>h</i>");
        paramh.setHelpText("h represents variations in dominance.");
        params.setCurrentValue(0.1);
        params.setDefaultValue(0.1);
        params.setIncrementAmount(0.05);
        params.setMaxValue(1.0);
        params.setParameterName("<i>s</i>");
        params.setHelpText("Selection Coefficient");
        GridBagConstraints gbc_coefficientsPanel = new GridBagConstraints();
        gbc_coefficientsPanel.weighty = 1.0;
        gbc_coefficientsPanel.weightx = 1.0;
        gbc_coefficientsPanel.fill = GridBagConstraints.BOTH;
        gbc_coefficientsPanel.anchor = GridBagConstraints.NORTHWEST;
        gbc_coefficientsPanel.insets = new Insets(0, 0, 0, 5);
        gbc_coefficientsPanel.gridx = 1;
        gbc_coefficientsPanel.gridy = 0;
        this.add(coefficientsPanel, gbc_coefficientsPanel);
        coefficientsPanel.setLayout(new BorderLayout(0, 0));
        coefficientsPanel.add(jTabbedPane1);
        fitnessPanel.add(paramwAA, null);
        fitnessPanel.add(paramwAa, null);
        fitnessPanel.add(paramwaa, null);
        fitnessScrollPanel.setViewportView(fitnessPanel);

        jTabbedPane1.add(fitnessScrollPanel, res.getString("Fitness"));

        selectionPanel.add(paramh, null);
        selectionPanel.add(params, null);
        jTabbedPane1.add(selectionPanel, res.getString("Selection"));
        jTabbedPane1.addChangeListener(this::jTabbedPane1_stateChanged);
        paramGens.setCurrentValue(130.0);
        paramGens.setDefaultValue(130.0);
        paramGens.setIncrementAmount(10.0);
        paramGens.setIntegersOnly(true);
        paramGens.setMaxValue(1000.0);
        paramGens.setMinValue(1.0);
        paramGens.setParameterName(res.getString("Generations"));
        paramGens.setHelpText("Number of Generations to be Simulated");
        GridBagConstraints gbc_initialConditionsPanel = new GridBagConstraints();
        gbc_initialConditionsPanel.fill = GridBagConstraints.BOTH;
        gbc_initialConditionsPanel.weighty = 1.0;
        gbc_initialConditionsPanel.weightx = 1.0;
        gbc_initialConditionsPanel.anchor = GridBagConstraints.WEST;
        gbc_initialConditionsPanel.gridx = 2;
        gbc_initialConditionsPanel.gridy = 0;
        this.add(initialConditionsPanel, gbc_initialConditionsPanel);
        initialConditionsPanel.add(oneInitialFrequencyButton, null);
        initialConditionsPanel.add(paramInitialFrequency, null);
        initialConditionsPanel.add(sixInitialFrequenciesButton, null);
        initialConditionsPanel.add(paramGens, null);
        bg2.add(this.oneInitialFrequencyButton);
        bg2.add(this.sixInitialFrequenciesButton);
        this.registerChildren(this);
    }
}
