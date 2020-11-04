/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.tp;

import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.RunningTimePanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TPPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -6244011476895184844L;
    Border border1;
    TitledBorder titledBorder1;
    Border border2;
    TitledBorder titledBorder2;
    Border border3;
    TitledBorder titledBorder3;
    Border border4;
    TitledBorder titledBorder4;
    Border border5;
    TitledBorder titledBorder5;
    Border border6;
    TitledBorder titledBorder6;
    Border border7;
    TitledBorder titledBorder7;
    Border border8;
    TitledBorder titledBorder8;
    Border border9;
    TitledBorder titledBorder9;
    Border border10;

    TitledBorder titledBorder10;
    ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
    JPanel plotTypePanel = new JPanel();
    RunningTimePanel runningTimePanel = new RunningTimePanel();
    GridBagLayout gridBagLayout7 = new GridBagLayout();
    PopulusParameterField paramE = new PopulusParameterField();
    JPanel modelParametersPanel = new JPanel();
    JPanel ratesPanel = new JPanel();
    GridBagLayout gridBagLayout5 = new GridBagLayout();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    PopulusParameterField paramAlphas = new PopulusParameterField();
    GridBagLayout gridBagLayout3 = new GridBagLayout();
    PopulusParameterField paramL0 = new PopulusParameterField();
    PopulusParameterField paramR0 = new PopulusParameterField();
    JPanel populationPanel = new JPanel();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    private PopulusParameterField paramInduction = new PopulusParameterField();
    private PopulusParameterField paramQ = new PopulusParameterField();
    private PopulusParameterField paramRho = new PopulusParameterField();
    private PopulusParameterField paramP = new PopulusParameterField();
    private JRadioButton vstButton = new StyledRadioButton();
    private JPanel phageratePanel = new JPanel();
    private PopulusParameterField paramSigmat = new PopulusParameterField();
    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private PopulusParameterField paramSigmav = new PopulusParameterField();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JRadioButton fiveButton = new JRadioButton();
    private JRadioButton fourButton = new JRadioButton();
    private JPanel modelTypePanel = new JPanel();
    JRadioButton threeButton = new JRadioButton();
    JRadioButton twoButton = new JRadioButton();
    JRadioButton oneButton = new JRadioButton();
    PopulusParameterField paramS0 = new PopulusParameterField();
    JPanel phagePanel = new JPanel();
    PopulusParameterField paramV0 = new PopulusParameterField();
    PopulusParameterField paramT0 = new PopulusParameterField();
    GridBagLayout gridBagLayout8 = new GridBagLayout();
    PopulusParameterField paramBetat = new PopulusParameterField();
    PopulusParameterField paramBetav = new PopulusParameterField();
    PopulusParameterField paramTau = new PopulusParameterField();
    PopulusParameterField paramLambda = new PopulusParameterField();
    PopulusParameterField paramC = new PopulusParameterField();
    PopulusParameterField paramAlphar = new PopulusParameterField();
    PopulusParameterField paramResource = new PopulusParameterField();

    @Override
    public BasicPlot getPlotParamInfo() {
        double time = runningTimePanel.getTime();
        if (vstButton.isSelected()) {
        }
        int modelType;
        if (oneButton.isSelected()) {
            modelType = TPParamInfo.ONE;
        } else {
            if (twoButton.isSelected()) {
                modelType = TPParamInfo.TWO;
            } else {
                if (threeButton.isSelected()) {
                    modelType = TPParamInfo.THREE;
                } else {
                    if (fourButton.isSelected()) {
                        modelType = TPParamInfo.FOUR;
                    } else {
                        if (fiveButton.isSelected()) {
                            modelType = TPParamInfo.FIVE;
                        } else {
                            System.err.println("Error inTPPanel: Unknown button selected");
                            modelType = -1;
                        }
                    }
                }
            }
        }

        return new TPParamInfo(modelType, time, paramT0.getDouble(), paramV0.getDouble(), paramS0.getDouble(), paramL0.getDouble(), paramR0.getDouble(), paramResource.getDouble(), paramE.getDouble(), paramSigmav.getDouble(), paramSigmat.getDouble(), paramBetav.getDouble(), paramBetat.getDouble(), paramLambda.getDouble(), paramInduction.getDouble(), paramTau.getDouble(), paramC.getDouble(), paramRho.getDouble(), paramP.getDouble(), paramQ.getDouble(), paramAlphas.getDouble(), paramAlphar.getDouble());
    }

    public TPPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        paramBetat.setCurrentValue(80.0);
        paramBetat.setHelpText("Number of free viral particles produced per infection of termperate " +
                "type");
        paramBetat.setDefaultValue(80.0);
        paramBetat.setEnabled(true);
        paramBetat.setIncrementAmount(10.0);
        paramBetat.setMaxValue(10000.0);
        paramBetat.setParameterName("<i>\u03B2<sub>T</sub></i>");
        paramBetav.setCurrentValue(100.0);
        paramBetav.setHelpText("Number of free viral particles produced per infection of virulent " +
                "type");
        paramBetav.setDefaultValue(100.0);
        paramBetav.setEnabled(true);
        paramBetav.setIncrementAmount(10.0);
        paramBetav.setMaxValue(10000.0);
        paramBetav.setParameterName("<i>\u03B2<sub>V</sub></i>");
        paramBetav.setFont(new java.awt.Font("Dialog", 0, 11));
        twoButton.setSelected(false);
        paramTau.setCurrentValue(0.0010);
        paramTau.setDefaultValue(0.0010);
        paramTau.setHelpText("Rate of prophage loss through vegetative segregation, per cell, per " +
                "hr");
        paramTau.setIncrementAmount(0.0010);
        paramTau.setMaxValue(1000.0);
        paramTau.setParameterName("\u03C4");
        paramLambda.setCurrentValue(0.0010);
        paramLambda.setDefaultValue(0.0010);
        paramLambda.setHelpText("Proportion of temperate phage adsorptions onto sensitive hosts that " +
                "produce lysogens");
        paramLambda.setIncrementAmount(0.0010);
        paramLambda.setMaxValue(1.0);
        paramLambda.setParameterName("\u03BB");
        paramC.setCurrentValue(1.6);
        paramC.setDefaultValue(1.6);
        paramC.setHelpText("Concentration of limiting resources in the input stock solution ");
        paramC.setIncrementAmount(0.1);
        paramC.setMaxValue(500.0);
        paramC.setParameterName("<i>C</i>");
        paramSigmav.setEnabled(true);
        paramSigmav.setDoubleBuffered(false);
        paramAlphar.setParameterName("<i>\u03B1<sub>R</sub></i>");
        paramAlphar.setMaxValue(50.0);
        paramAlphar.setMinValue(-50.0);
        paramAlphar.setIncrementAmount(0.1);
        paramAlphar.setHelpText("Selection coefficient defining the proportional difference in growth " +
                "rates between the different host-cell types");
        paramAlphar.setDefaultValue(-0.3);
        paramAlphar.setCurrentValue(-0.3);
        paramResource.setCurrentValue(1.225);
        paramResource.setDefaultValue(1.225);
        paramResource.setHelpText("Concentration of a unique limiting resource in the culture vessel");
        paramResource.setIncrementAmount(0.25);
        paramResource.setMaxValue(1000.0);
        paramResource.setParameterName("<i>r</i>");
        paramAlphas.setMinValue(-50.0);
        vstButton.setEnabled(false);
        populationPanel.add(paramL0, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        populationPanel.add(paramR0, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        populationPanel.add(paramS0, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, "Plot Type");
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border2, "Model Parameters");
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border3, "Host Rates");
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder4 = new TitledBorder(border4, "Termination Conditions");
        border5 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder5 = new TitledBorder(border5, "Host Densities");
        border6 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder6 = new TitledBorder(border6, "Include R");
        border7 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder7 = new TitledBorder(border7, "Host Rates");
        border8 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder8 = new TitledBorder(border8, "Model Version");
        border9 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder9 = new TitledBorder(border9, "Initial Host Densities");
        border10 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder10 = new TitledBorder(border10, "Constants");
        plotTypePanel.setBorder(titledBorder1);
        plotTypePanel.setLayout(gridBagLayout7);
        modelParametersPanel.setBorder(titledBorder2);
        modelParametersPanel.setLayout(gridBagLayout5);
        ratesPanel.setBorder(titledBorder3);
        ratesPanel.setLayout(gridBagLayout3);
        paramAlphas.setCurrentValue(-0.02);
        paramAlphas.setDefaultValue(-0.02);
        paramAlphas.setHelpText("Selection coefficient defining the proportional difference in growth " +
                "rates between the different host-cell types");
        paramAlphas.setIncrementAmount(0.01);
        paramAlphas.setMaxValue(50.0);
        paramAlphas.setParameterName("<i>\u03B1<sub>S</sub></i>");
        paramL0.setParameterName("<i>L</i>(0)");
        paramL0.setMaxValue(100000.0);
        paramL0.setIncrementAmount(20.0);
        paramL0.setDefaultValue(1000.0);
        paramL0.setHelpText("Initial population density of Lysogenic host bacteria");
        paramL0.setCurrentValue(1000.0);
        paramR0.setParameterName("<i>R</i>(0)");
        paramR0.setMaxValue(100000.0);
        paramR0.setIncrementAmount(5.0);
        paramR0.setDefaultValue(1.0);
        paramR0.setHelpText("Initial population density of Resistant host bacteria");
        paramR0.setCurrentValue(1.0);
        populationPanel.setLayout(gridBagLayout2);
        populationPanel.setBorder(titledBorder9);
        populationPanel.setToolTipText("Initial Densities");
        this.setLayout(gridBagLayout1);
        paramE.setParameterName("<i>e</i>");
        paramE.setMaxValue(1.0);
        paramE.setIncrementAmount(1.0E-6);
        paramE.setDefaultValue(5.0E-7);
        paramE.setHelpText("Conversion efficiency, a constant defining the rate of resource uptake, " +
                "which increases proportionally with the bacterial growth rate");
        paramE.setCurrentValue(5.0E-7);
        titledBorder9.setTitle("Host Densities");
        runningTimePanel.setDefaultTime(90.0);
        paramInduction.setCurrentValue(0.0010);
        paramInduction.setDefaultValue(0.0010);
        paramInduction.setHelpText("Lysogen induction rate, per cell per hr");
        paramInduction.setIncrementAmount(0.0010);
        paramInduction.setMaxValue(1000.0);
        paramInduction.setParameterName("<i>j</i>");
        paramQ.setCurrentValue(4.0);
        paramQ.setDefaultValue(4.0);
        paramQ.setHelpText("half-saturation constant, the concentration of resources at which " +
                "the bacteria grow at half their maximal rate");
        paramQ.setIncrementAmount(0.1);
        paramQ.setMaxValue(100.0);
        paramQ.setParameterName("<i>Q</i>");
        paramRho.setParameterName("<i>\u03C1</i>");
        paramRho.setMaxValue(100.0);
        paramRho.setIncrementAmount(0.1);
        paramRho.setHelpText("Flow/dilution rate of the culture chemostat, per hour");
        paramRho.setDefaultValue(0.2);
        paramRho.setCurrentValue(0.2);
        paramP.setCurrentValue(0.7);
        paramP.setDefaultValue(0.7);
        paramP.setHelpText("Maximum bacterial growth rate on ulimited resources");
        paramP.setIncrementAmount(0.1);
        paramP.setMaxValue(100.0);
        paramP.setParameterName("P");
        vstButton.setActionCommand("<i>H, I vs t </i>");
        vstButton.setFocusPainted(false);
        vstButton.addActionListener(e -> vstButton_actionPerformed(e));
        vstButton.setSelected(true);
        vstButton.setText("<i>vs t</i> ");
        phageratePanel.setToolTipText("Constants");
        phageratePanel.setBorder(titledBorder10);
        phageratePanel.setLayout(gridBagLayout6);
        paramSigmat.setParameterName("<i>\u03B4<sub>T</sub></i>");
        paramSigmat.setMaxValue(1.0);
        paramSigmat.setIncrementAmount(1.0E-9);
        paramSigmat.setDefaultValue(8.0E-10);
        paramSigmat.setHelpText("Constant defining the rate of phage adsorption in proportion to the " +
                "joint densities of sensitive hosts and temperate phage particles");
        paramSigmat.setCurrentValue(8.0E-10);
        paramSigmav.setCurrentValue(1.0E-9);
        paramSigmav.setHelpText("Constant defining the rate of phage adsorption in proportion to the " +
                "joint densities of sensitive hosts and virulent phage particles");
        paramSigmav.setDefaultValue(1.0E-9);
        paramSigmav.setIncrementAmount(1.0E-9);
        paramSigmav.setMaxValue(1.0);
        paramSigmav.setParameterName("<i>\u03B4<sub>V</sub></i>");
        fiveButton.setToolTipText("Case V");
        fiveButton.setActionCommand("Coupled");
        fiveButton.setText("Case V");
        fiveButton.setFocusPainted(false);
        fiveButton.addActionListener(e -> fiveButton_actionPerformed(e));
        fourButton.setToolTipText("Case IV");
        fourButton.setText("Case IV");
        fourButton.setFocusPainted(false);
        fourButton.setSelected(false);
        fourButton.addActionListener(e -> fourButton_actionPerformed(e));
        modelTypePanel.setLayout(gridBagLayout4);
        modelTypePanel.setBorder(titledBorder8);
        threeButton.addActionListener(e -> threeButton_actionPerformed(e));
        threeButton.setFocusPainted(false);
        threeButton.setText("Case III");
        threeButton.setToolTipText("Case III");
        twoButton.addActionListener(e -> twoButton_actionPerformed(e));
        twoButton.setFocusPainted(false);
        twoButton.setText("Case II");
        twoButton.setToolTipText("Case II");
        oneButton.addActionListener(e -> oneButton_actionPerformed(e));
        oneButton.setFocusPainted(false);
        oneButton.setText("Case I");
        oneButton.setSelected(true);
        oneButton.setToolTipText("Case I");
        oneButton.setActionCommand("Case I");
        paramS0.setCurrentValue(1.0);
        paramS0.setHelpText("Initial population density of Sensitive host bacteria");
        paramS0.setDefaultValue(1.0);
        paramS0.setIncrementAmount(5.0);
        paramS0.setMaxValue(100000.0);
        paramS0.setParameterName("<i>S</i>(0)");
        phagePanel.setToolTipText("Initial Densities");
        phagePanel.setBorder(titledBorder9);
        phagePanel.setLayout(gridBagLayout8);
        paramV0.setParameterName("<i>V</i>(0)");
        paramV0.setMaxValue(100000.0);
        paramV0.setIncrementAmount(20.0);
        paramV0.setDefaultValue(1000.0);
        paramV0.setHelpText("Initial population density of Virulent phage");
        paramV0.setCurrentValue(1000.0);
        paramT0.setCurrentValue(1.0);
        paramT0.setHelpText("Initial population density of Temperate phage");
        paramT0.setDefaultValue(1.0);
        paramT0.setIncrementAmount(5.0);
        paramT0.setMaxValue(100000.0);
        paramT0.setParameterName("<i>T</i>(0)");
        modelTypePanel.add(fourButton, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(fiveButton, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(threeButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(twoButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(oneButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(modelParametersPanel, new GridBagConstraints(1, 0, 2, 3, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(runningTimePanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(plotTypePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        ratesPanel.add(paramE, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramAlphas, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramQ, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramInduction, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramP, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramLambda, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramC, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramTau, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramRho, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramAlphar, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        ratesPanel.add(paramResource, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        modelParametersPanel.add(phageratePanel, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelParametersPanel.add(populationPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        plotTypePanel.add(vstButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(modelTypePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        bg2.add(vstButton);
        bg1.add(fourButton);
        bg1.add(fiveButton);
        bg1.add(oneButton);
        bg1.add(twoButton);
        bg1.add(threeButton);
        phagePanel.add(paramV0, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        phagePanel.add(paramT0, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        modelParametersPanel.add(ratesPanel, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        phageratePanel.add(paramBetat, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        phageratePanel.add(paramBetav, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        phageratePanel.add(paramSigmat, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        phageratePanel.add(paramSigmav, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        modelParametersPanel.add(phagePanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        registerChildren(this);
    }

    void oneButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void twoButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void threeButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void fourButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void fiveButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void ebvstButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void vstButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void HIStarButton_actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }




	/*
public int getTriggers() {
return 0;
}
	 */
}