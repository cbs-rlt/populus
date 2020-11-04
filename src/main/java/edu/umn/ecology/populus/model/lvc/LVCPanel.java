/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvc;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.RunningTimePanel;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LVCPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -2873141916877977257L;
    Border border1;
    TitledBorder titledBorder1;
    final StyledRadioButton nvstButton = new StyledRadioButton();
    final PopulusParameterField paramr1 = new PopulusParameterField();
    final PopulusParameterField paramalpha = new PopulusParameterField();
    TitledBorder titledBorder4;
    TitledBorder titledBorder5;
    final PopulusParameterField parambeta = new PopulusParameterField();
    final StyledRadioButton n1vsn2Button = new StyledRadioButton();
    final SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
    final SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
    final RunningTimePanel runningTimePanel1 = new RunningTimePanel();
    final JPanel modelParametersPanel = new JPanel();
    final PopulusParameterField paramK2 = new PopulusParameterField();
    Border border2;
    final JPanel plotTypePanel = new JPanel();
    TitledBorder titledBorder2;
    final PopulusParameterField paramK1 = new PopulusParameterField();
    final JPanel species2Panel = new JPanel();
    final JPanel species1Panel = new JPanel();
    Border border3;
    Border border4;
    TitledBorder titledBorder3;
    ButtonGroup bg1 = new ButtonGroup();
    final ButtonGroup bg2 = new ButtonGroup();
    final PopulusParameterField paramN02 = new PopulusParameterField();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final PopulusParameterField paramr2 = new PopulusParameterField();
    Border border5;
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    final PopulusParameterField paramN01 = new PopulusParameterField();

    public LVCPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLabels() {
        titledBorder3 = new TitledBorder(border3, "Species #2", TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[1]);
        titledBorder5 = new TitledBorder(border5, "Species #1", TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[0]);
        species2Panel.setBorder(titledBorder3);
        species1Panel.setBorder(titledBorder5);
    }

    @Override
    public BasicPlot getPlotParamInfo() {
        boolean vsTime = nvstButton.isSelected();

        //boolean vsTime = true;
        return new LVCParamInfo(vsTime, runningTimePanel1.getTime(), paramN01.getDouble(), paramr1.getDouble(), paramK1.getDouble(), paramalpha.getDouble(), paramN02.getDouble(), paramr2.getDouble(), paramK2.getDouble(), parambeta.getDouble());
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, "Plot Type");
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border2, "Model Parameters");
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);

        //titledBorder3 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Species #1");
        titledBorder3 = new TitledBorder(border3, "Species #2", TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[1]);
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder4 = new TitledBorder(border4, "Termination Conditions");
        border5 = BorderFactory.createLineBorder(SystemColor.controlText, 1);

        //titledBorder5 = new TitledBorder(border5,"Species #1");
        titledBorder5 = new TitledBorder(border5, "Species #1", TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[0]);
        plotTypePanel.setBorder(titledBorder1);
        plotTypePanel.setLayout(simpleVFlowLayout1);
        nvstButton.setSelected(true);
        nvstButton.setText("<i>N</i> vs <i>t</i>");
        n1vsn2Button.setText("<i>N</i><sub>2</sub> vs <i>N</i><sub>1</sub>");
        modelParametersPanel.setBorder(titledBorder2);
        species2Panel.setBorder(titledBorder3);
        species2Panel.setLayout(simpleVFlowLayout2);
        paramN02.setCurrentValue(20.0);
        paramN02.setDefaultValue(20.0);
        paramN02.setHelpText("Initial population size of species #2");
        paramN02.setIncrementAmount(5.0);
        paramN02.setMaxValue(100000.0);
        paramN02.setParameterName("<i>N</i><sub>2</sub>(0)");
        paramr2.setCurrentValue(0.5);
        paramr2.setDefaultValue(0.5);
        paramr2.setHelpText("Intrinsic rate of increase for species #2");
        paramr2.setIncrementAmount(0.05);
        paramr2.setMaxValue(5.0);
        paramr2.setMinValue(-1.0);
        paramr2.setParameterName("<i>r </i>");
        paramK2.setCurrentValue(700.0);
        paramK2.setDefaultValue(700.0);
        paramK2.setHelpText("Environmental carrying capacity for species #2");
        paramK2.setIncrementAmount(20.0);
        paramK2.setMaxValue(1000.0);
        paramK2.setParameterName("<i>K</i><sub>2</sub>");
        parambeta.setCurrentValue(0.7);
        parambeta.setDefaultValue(0.7);
        parambeta.setHelpText("Competition coefficient for species #2");
        parambeta.setIncrementAmount(0.1);
        parambeta.setMaxValue(100.0);
        parambeta.setParameterName("\u03b2");
        paramN01.setParameterName("<i>N</i><sub>1</sub>(0)");
        paramN01.setMaxValue(100000.0);
        paramN01.setIncrementAmount(5.0);
        paramN01.setDefaultValue(10.0);
        paramN01.setHelpText("Initial population size of species #1");
        paramN01.setCurrentValue(10.0);
        paramr1.setParameterName("<i>r </i>");
        paramr1.setMinValue(-1.0);
        paramr1.setMaxValue(5.0);
        paramr1.setIncrementAmount(0.05);
        paramr1.setDefaultValue(0.9);
        paramr1.setHelpText("Intrinsic rate of increase for species #1");
        paramr1.setCurrentValue(0.9);
        paramK1.setParameterName("<i>K</i><sub>1</sub>");
        paramK1.setMaxValue(1000.0);
        paramK1.setIncrementAmount(20.0);
        paramK1.setDefaultValue(500.0);
        paramK1.setHelpText("Environmental carrying capacity for species #1");
        paramK1.setCurrentValue(500.0);
        paramalpha.setParameterName("\u03b1");
        paramalpha.setMaxValue(100.0);
        paramalpha.setIncrementAmount(0.1);
        paramalpha.setDefaultValue(0.6);
        paramalpha.setHelpText("Competition coefficient for species #1");
        paramalpha.setCurrentValue(0.6);
        species1Panel.setLayout(simpleVFlowLayout3);
        species1Panel.setBorder(titledBorder5);

        //titledBorder3.setTitle("Species #2");
        this.setLayout(gridBagLayout1);
        runningTimePanel1.setDefaultTime(25.0);
        runningTimePanel1.setIncrementTime(10.0);
        this.add(plotTypePanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        plotTypePanel.add(nvstButton, null);
        plotTypePanel.add(n1vsn2Button, null);
        this.add(modelParametersPanel, new GridBagConstraints(0, 0, 1, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelParametersPanel.add(species1Panel, null);
        species1Panel.add(paramN01, null);
        species1Panel.add(paramr1, null);
        species1Panel.add(paramK1, null);
        species1Panel.add(paramalpha, null);
        modelParametersPanel.add(species2Panel, null);
        species2Panel.add(paramN02, null);
        species2Panel.add(paramr2, null);
        species2Panel.add(paramK2, null);
        species2Panel.add(parambeta, null);
        this.add(runningTimePanel1, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        bg2.add(nvstButton);
        bg2.add(n1vsn2Button);
        registerChildren(this);
    }
}
