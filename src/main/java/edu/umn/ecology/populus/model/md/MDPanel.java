/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.md;

import edu.umn.ecology.populus.constants.ColorScheme;
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
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

public class MDPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 938030400034384251L;
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.md.Res");
    Border border5;
    Border border7;
    Border border8;
    TitledBorder titledBorder10;
    final ButtonGroup bg1 = new ButtonGroup();
    TitledBorder titledBorder6;
    final ButtonGroup bg2 = new ButtonGroup();
    final GridBagLayout gridBagLayout2 = new GridBagLayout();
    ButtonGroup bg3 = new ButtonGroup();
    final JPanel hostPanel = new JPanel();
    final JPanel graphTypePanel = new JPanel();
    final PopulusParameterField paramlambda = new PopulusParameterField();
    final JRadioButton HvsPvsWButton = new StyledRadioButton();
    final PopulusParameterField parammuA = new PopulusParameterField();
    final JPanel hostGrowthPanel = new JPanel();
    final GridLayout gridLayout1 = new GridLayout();
    final PopulusParameterField paramb1 = new PopulusParameterField();
    Border border1;
    TitledBorder titledBorder1;
    final JPanel andersonMayParams = new JPanel();
    final GridBagLayout gridBagLayout3 = new GridBagLayout();
    TitledBorder titledBorder9;
    final PopulusParameterField paramd = new PopulusParameterField();
    final PopulusParameterField paramb = new PopulusParameterField();
    Border border2;
    TitledBorder titledBorder5;
    TitledBorder titledBorder2;
    final PopulusParameterField paramTheta = new PopulusParameterField();
    final PopulusParameterField parambeta = new PopulusParameterField();
    final PopulusParameterField paramalpha = new PopulusParameterField();
    Border border6;
    final PopulusParameterField paramd1 = new PopulusParameterField();
    Border border3;
    TitledBorder titledBorder3;
    TitledBorder titledBorder7;
    final JPanel modelType = new JPanel();
    Border border4;
    TitledBorder titledBorder4;
    Border border11;
    TitledBorder titledBorder11;
    final JRadioButton DobsonHudsonButton = new JRadioButton();
    TitledBorder titledBorder8;
    final JRadioButton AndersonMayButton = new JRadioButton();
    final JPanel parasitePanel = new JPanel();
    final GridLayout gridLayout3 = new GridLayout();
    Border border9;
    final JTabbedPane modelSpecificParamsTabbedPane = new JTabbedPane();
    Border border10;
    final JPanel dobsonHudsonParams = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JCheckBox dAMBox = new JCheckBox();
    final JPanel parasiteGrowthPanel = new JPanel();
    private final PopulusParameterField parammuP = new PopulusParameterField();
    private final PopulusParameterField paramgamma = new PopulusParameterField();
    private final PopulusParameterField paramk = new PopulusParameterField();
    private final RunningTimePanel runningTimePanel = new RunningTimePanel();
    private final PopulusParameterField paramP = new PopulusParameterField();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private final PopulusParameterField paramH = new PopulusParameterField();
    private final PopulusParameterField paramW = new PopulusParameterField();
    private final JPanel hostDensitiesPanel = new JPanel();
    private final PopulusParameterField paramH1 = new PopulusParameterField();
    private final GridBagLayout gridBagLayout5 = new GridBagLayout();
    private final PopulusParameterField paramA = new PopulusParameterField();
    private final PopulusParameterField paramP1 = new PopulusParameterField();
    private final JPanel DensitiesPanel = new JPanel();
    private final PopulusParameterField paramlambda1 = new PopulusParameterField();
    private final PopulusParameterField paramk1 = new PopulusParameterField();
    private final PopulusParameterField paramgamma1 = new PopulusParameterField();
    private final PopulusParameterField parambeta1 = new PopulusParameterField();
    private final PopulusParameterField paramalpha1 = new PopulusParameterField();
    private final PopulusParameterField paramsigma = new PopulusParameterField();
    private final JRadioButton PvsHButton = new StyledRadioButton();
    private final JRadioButton HPWvstButton = new StyledRadioButton();
    private final PopulusParameterField parammup1 = new PopulusParameterField();
    private final GridBagLayout gridBagLayout6 = new GridBagLayout();
    private final GridBagLayout gridBagLayout7 = new GridBagLayout();
    private final GridBagLayout gridBagLayout8 = new GridBagLayout();
    private final GridBagLayout gridBagLayout9 = new GridBagLayout();

    @Override
    public void updateLabels() {
        titledBorder9 = new TitledBorder(border9, res.getString("Host"), TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[0]);
        titledBorder10 = new TitledBorder(BorderFactory.createLineBorder(Color.black, 1), res.getString("Parasite"), TitledBorder.LEFT, TitledBorder.TOP, new Font("Dialog", Font.PLAIN, 12), ColorScheme.colors[1]);
        titledBorder6.setTitleColor(ColorScheme.colors[0]);
        titledBorder5.setTitleColor(ColorScheme.colors[1]);
        hostPanel.setBorder(titledBorder9);
        parasitePanel.setBorder(titledBorder10);
    }

    @Override
    public BasicPlot getPlotParamInfo() {
        double time = runningTimePanel.getTime();
        int plotType;
        if (HPWvstButton.isSelected())
            plotType = MDParamInfo.vsT;
        else if (PvsHButton.isSelected())
            plotType = MDParamInfo.nvsp;
        else
            plotType = MDParamInfo.phase;

        int modelType;
        if (AndersonMayButton.isSelected()) {
            if (dAMBox.isSelected()) {
                modelType = MDParamInfo.HPDD;
                HPWvstButton.setText("<i>H, P</i> vs <i>t</i>");
                HvsPvsWButton.setEnabled(false);
            } else {
                modelType = MDParamInfo.HPWDD;
                HPWvstButton.setText("<i>H, P, W</i> vs <i>t</i>");
                HvsPvsWButton.setText("<i> H vs P vs W</i>");
                HvsPvsWButton.setEnabled(true);

            }
        } else {
            if (DobsonHudsonButton.isSelected()) {
                modelType = MDParamInfo.HPADD;
                HPWvstButton.setText("<i>H, P, A</i> vs <i>t</i>");
                HvsPvsWButton.setText("<i> H vs P vs A</i>");
                HvsPvsWButton.setEnabled(true);
            } else {
                System.err.println("Error in IMDPanel: Unknown button selected");
                modelType = -1;
            }
        }
        if (plotType == MDParamInfo.phase) {
            if (modelType == 10) {
                return new MD3DParamInfo(modelType, time, paramH.getDouble(), paramP.getDouble(), paramW.getDouble(), paramb.getDouble(), paramd.getDouble(), paramalpha.getDouble(), parambeta.getDouble(), parammuA.getDouble(), parammuP.getDouble(), paramlambda.getDouble(), paramk.getDouble(), paramgamma.getDouble(), paramsigma.getDouble(), paramTheta.getDouble());
            } else {
                if (modelType == 11) {
                    return new MD3DParamInfo(modelType, time, paramH1.getDouble(), paramP1.getDouble(), paramA.getDouble(), paramb1.getDouble(), paramd1.getDouble(), paramalpha1.getDouble(), parambeta1.getDouble(), parammuA.getDouble(), parammup1.getDouble(), paramlambda1.getDouble(), paramk1.getDouble(), paramgamma1.getDouble(), paramsigma.getDouble(), paramTheta.getDouble());
                }
            }
        }
        if ((plotType == MDParamInfo.vsT || plotType == MDParamInfo.nvsp) && modelType == 11) {
            return new MDParamInfo(modelType, plotType, time, paramH1.getDouble(), paramP1.getDouble(), paramA.getDouble(), paramb1.getDouble(), paramd1.getDouble(), paramalpha1.getDouble(), parambeta1.getDouble(), parammuA.getDouble(), parammup1.getDouble(), paramlambda1.getDouble(), paramk1.getDouble(), paramgamma1.getDouble(), paramsigma.getDouble(), paramTheta.getDouble());
        }

        return new MDParamInfo(modelType, plotType, time, paramH.getDouble(), paramP.getDouble(), paramW.getDouble(), paramb.getDouble(), paramd.getDouble(), paramalpha.getDouble(), parambeta.getDouble(), parammuA.getDouble(), parammuP.getDouble(), paramlambda.getDouble(), paramk.getDouble(), paramgamma.getDouble(), paramsigma.getDouble(), paramTheta.getDouble());

    }


    public MDPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void dAMBox_actionPerformed(ActionEvent e) {
        paramW.setEnabled(false);
    }


    void DobsonHudsonButton_actionPerformed(ActionEvent e) {
        this.modelSpecificParamsTabbedPane.setSelectedComponent(dobsonHudsonParams);
        dAMBox.setEnabled(false);
    }

    void andersonMayParams_componentShown(ComponentEvent e) {
        this.AndersonMayButton.setSelected(true);
        dAMBox.setEnabled(true);
    }

    void dobsonHudsonParams_componentShown(ComponentEvent e) {
        this.DobsonHudsonButton.setSelected(true);
        dAMBox.setEnabled(false);
    }

    void AndersonMayButton_actionPerformed(ActionEvent e) {
        this.modelSpecificParamsTabbedPane.setSelectedComponent(andersonMayParams);
        dAMBox.setEnabled(true);
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, res.getString("Model_Type"));
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border2, res.getString("Model_Parameters"));
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border3, res.getString("Initial_Conditions"));
        border4 = BorderFactory.createLineBorder(Color.black, 1);
        titledBorder4 = new TitledBorder(border4, res.getString("Model_Type"));
        border5 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder5 = new TitledBorder(border5, res.getString("Parasite_Rate"));
        border6 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder6 = new TitledBorder(border6, res.getString("Host_Rate"));
        border7 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder7 = new TitledBorder(border7, res.getString("Initial_Densities"));
        border11 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder11 = new TitledBorder(border11, res.getString("Init_Population"));
        HvsPvsWButton.setText("<i>H vs P vs W </i>");
        HvsPvsWButton.addActionListener(this::HvsPvsWButton_actionPerformed);
        HvsPvsWButton.setActionCommand("<i>H vs P vs W </i>");
        HvsPvsWButton.setFocusPainted(false);//<i>P</i>, <i>N</i> vs <i>T</i>
        graphTypePanel.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(3);
        graphTypePanel.setBorder(titledBorder1);
        andersonMayParams.setLayout(gridBagLayout3);
        andersonMayParams.setBorder(titledBorder2);
        andersonMayParams.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {
                andersonMayParams_componentShown(e);
            }
        });
        paramalpha.setCurrentValue(0.04);
        paramalpha.setDefaultValue(0.04);
        paramalpha.setHelpText(res.getString("Rate_of_population"));
        paramalpha.setIncrementAmount(0.01);
        paramalpha.setMaxValue(5.0);
        paramalpha.setParameterName("\u03B1");
        parambeta.setCurrentValue(0.4);
        parambeta.setDefaultValue(0.4);
        parambeta.setIncrementAmount(0.1);
        parambeta.setMaxValue(100.0);
        parambeta.setParameterName("\u03B2");
        parambeta.setHelpText("Transmission rate per host contact (/host/time)");
        paramb.setCurrentValue(1.1);
        paramb.setDefaultValue(1.1);
        paramb.setHelpText(res.getString("Init_Population"));
        paramb.setIncrementAmount(0.1);
        paramb.setMaxValue(50.0);
        paramb.setParameterName("<i>b</i>");
        paramd.setCurrentValue(0.75);
        paramd.setDefaultValue(0.75);
        paramd.setHelpText("Host natural mortality rate (/time)");
        paramd.setIncrementAmount(0.05);
        paramd.setMaxValue(50.0);
        paramd.setParameterName("<i>d</i>");
        titledBorder1.setTitle(res.getString("Graph_Type"));
        modelType.setBorder(titledBorder4);
        modelType.setLayout(gridLayout3);
        gridLayout3.setColumns(1);
        gridLayout3.setRows(0);
        this.setLayout(gridBagLayout1);
        AndersonMayButton.setSelected(true);
        AndersonMayButton.setText(res.getString("Anderson_May"));
        AndersonMayButton.setActionCommand("Anderson & May");
        AndersonMayButton.setFocusPainted(false);
        AndersonMayButton.addActionListener(this::AndersonMayButton_actionPerformed);
        DobsonHudsonButton.setText("Dobson & Hudson");
        DobsonHudsonButton.setFocusPainted(false);
        DobsonHudsonButton.addActionListener(this::DobsonHudsonButton_actionPerformed);
        parasiteGrowthPanel.setBorder(titledBorder5);
        parasiteGrowthPanel.setToolTipText(res.getString("Parasite_Growth"));
        parasiteGrowthPanel.setLayout(gridBagLayout8);
        parammuA.setCurrentValue(0.5);
        parammuA.setDefaultValue(0.5);
        parammuA.setIncrementAmount(0.1);
        parammuA.setMaxValue(1000.0);
        parammuA.setParameterName("<i>\u03BC</i><sub>A</sub>");
        parammuA.setHelpText("Natural mortality rate of arrested parasites (/time)");
        paramTheta.setParameterName("\u0398");
        paramTheta.setMaxValue(1000.0);
        paramTheta.setDefaultValue(3.0);
        paramTheta.setHelpText("Rate at which arrested parasites develop into adult parasites");
        paramTheta.setIncrementAmount(0.1);
        paramTheta.setCurrentValue(3.0);
        hostGrowthPanel.setBorder(titledBorder6);
        hostGrowthPanel.setToolTipText(res.getString("Host_Growth"));
        hostGrowthPanel.setLayout(gridBagLayout9);
        paramd1.setCurrentValue(0.75);
        paramd1.setDefaultValue(0.75);
        paramd1.setIncrementAmount(0.1);
        paramd1.setMaxValue(1000.0);
        paramd1.setParameterName("<i>d</i>");
        paramd1.setHelpText("Host natural mortality rate");
        paramb1.setCurrentValue(1.1);
        paramb1.setDefaultValue(1.1);
        paramb1.setIncrementAmount(0.1);
        paramb1.setMaxValue(1000.0);
        paramb1.setParameterName("<i>b </i>");
        paramb1.setHelpText("Host birth rate");
        dobsonHudsonParams.setLayout(gridBagLayout2);
        dobsonHudsonParams.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {
                dobsonHudsonParams_componentShown(e);
            }
        });
        hostPanel.setBorder(titledBorder6);
        hostPanel.setLayout(gridBagLayout7);
        parasitePanel.setBorder(titledBorder5);
        parasitePanel.setLayout(gridBagLayout6);
        dAMBox.setToolTipText(res.getString("DAM"));
        dAMBox.setText(res.getString("DAM_parasite"));
        dAMBox.setFocusPainted(false);
        dAMBox.setHorizontalAlignment(SwingConstants.CENTER);
        dAMBox.addActionListener(this::dAMBox_actionPerformed);
        paramlambda.setCurrentValue(11.0);
        paramlambda.setDefaultValue(11.0);
        paramlambda.setMaxValue(10000.0);
        paramlambda.setParameterName("\u03BB");
        paramlambda.setHelpText("Birth rate parasite eggs or larvae");
        titledBorder7.setTitle(res.getString("Initial_Densities"));
        titledBorder6.setTitle(res.getString("Host"));
        titledBorder6.setTitleColor(ColorScheme.colors[0]);
        titledBorder5.setTitle(res.getString("Parasite"));
        titledBorder5.setTitleColor(ColorScheme.colors[1]);
        runningTimePanel.setDefaultTime(60.0);
        runningTimePanel.setMaxTime(1000.0); //the graph only graphs to about 97 or 98, why is this?
        runningTimePanel.setAutoIncrement(true, true);
        parammuP.setCurrentValue(1.0);
        parammuP.setDefaultValue(1.0);
        parammuP.setHelpText(res.getString("Init_Population"));
        parammuP.setIncrementAmount(0.1);
        parammuP.setMaxValue(100000.0);
        parammuP.setParameterName("<i>\u03BC</i><sub>P</sub>");
        paramgamma.setCurrentValue(6.0);
        paramgamma.setDefaultValue(6.0);
        paramgamma.setMaxValue(10000.0);
        paramgamma.setParameterName("\u03B3");
        paramgamma.setHelpText("Infective stage mortality rate");
        paramk.setCurrentValue(0.6);
        paramk.setDefaultValue(0.6);
        paramk.setIncrementAmount(0.1);
        paramk.setMaxValue(100.0);
        paramk.setParameterName("<i>k</i>");
        paramk.setHelpText("Negative binomial aggregation parameter");
        paramP.setCurrentValue(200.0);
        paramP.setHelpText("Initial parasite density");
        paramP.setDefaultValue(200.0);
        paramP.setIncrementAmount(20.0);
        paramP.setMaxValue(100000.0);
        paramP.setParameterName("<i>P</i>(0)");
        paramH.setCurrentValue(50.0);
        paramH.setHelpText("Initial host density");
        paramH.setDefaultValue(50.0);
        paramH.setIncrementAmount(20.0);
        paramH.setMaxValue(100000.0);
        paramH.setParameterName("<i>H</i>(0)");
        paramW.setCurrentValue(200.0);
        paramW.setHelpText("Initial free_living infective stage density");
        paramW.setDefaultValue(200.0);
        paramW.setIncrementAmount(20.0);
        paramW.setMaxValue(100000.0);
        paramW.setParameterName("<i>W</i>(0)");
        hostDensitiesPanel.setToolTipText("Initial Host Densities");
        hostDensitiesPanel.setBorder(titledBorder11);
        hostDensitiesPanel.setLayout(gridBagLayout4);
        paramH1.setParameterName("<i>H</i>(0)");
        paramH1.setMaxValue(100000.0);
        paramH1.setIncrementAmount(5.0);
        paramH1.setDefaultValue(20.0);
        paramH1.setHelpText("Initial host density");
        paramH1.setCurrentValue(50.0);
        paramA.setParameterName("<i>A</i>(0)");
        paramA.setMaxValue(100000.0);
        paramA.setIncrementAmount(2.0);
        paramA.setDefaultValue(6.0);
        paramA.setHelpText("Initial arrested parasite density");
        paramA.setCurrentValue(200.0);
        paramP1.setParameterName("<i>P</i>(0)");
        paramP1.setMaxValue(100000.0);
        paramP1.setIncrementAmount(5.0);
        paramP1.setDefaultValue(6.0);
        paramP1.setHelpText("Initial parasite density");
        paramP1.setCurrentValue(200.0);
        DensitiesPanel.setLayout(gridBagLayout5);
        DensitiesPanel.setBorder(titledBorder11);
        DensitiesPanel.setToolTipText("Initial Host Densities");
        paramlambda1.setHelpText("Birth rate of parasite eggs or larvae");
        paramlambda1.setParameterName("\u03BB");
        paramlambda1.setMaxValue(1000.0);
        paramlambda1.setDefaultValue(11.0);
        paramlambda1.setCurrentValue(11.0);
        paramk1.setHelpText("Negative binomial aggregation of parasites within the host population");
        paramk1.setParameterName("<i>k</i>");
        paramk1.setMaxValue(1000.0);
        paramk1.setIncrementAmount(0.1);
        paramk1.setDefaultValue(0.315);
        paramk1.setCurrentValue(0.315);
        paramgamma1.setHelpText("Infective stage mortality rate");
        paramgamma1.setParameterName("\u03B3");
        paramgamma1.setMaxValue(1000.0);
        paramgamma1.setDefaultValue(10.0);
        paramgamma1.setCurrentValue(10.0);
        parambeta1.setHelpText("transmission rate per host contact");
        parambeta1.setParameterName("\u03B2");
        parambeta1.setMaxValue(10.0);
        parambeta1.setIncrementAmount(0.1);
        parambeta1.setDefaultValue(0.1);
        parambeta1.setCurrentValue(0.1);
        paramalpha1.setParameterName("\u03B1");
        paramalpha1.setMaxValue(5.0);
        paramalpha1.setIncrementAmount(0.01);
        paramalpha1.setHelpText(res.getString("Rate_of_population"));
        paramalpha1.setDefaultValue(0.03);
        paramalpha1.setCurrentValue(0.03);
        paramsigma.setCurrentValue(0.05);
        paramsigma.setDefaultValue(0.05);
        paramsigma.setHelpText(res.getString("Rate_of_population"));
        paramsigma.setIncrementAmount(0.01);
        paramsigma.setMaxValue(5.0);
        paramsigma.setParameterName("\u03B4");
        paramsigma.setUnicodeName("");

        HPWvstButton.setActionCommand("<i>H, P, W vs t</i>");
        HPWvstButton.setFocusPainted(false);
        HPWvstButton.addActionListener(this::HPWvstButton_actionPerformed);
        HPWvstButton.setSelected(true);
        HPWvstButton.setText("<i>H, P, W vs t</i>");
        PvsHButton.setFocusPainted(false);
        PvsHButton.setActionCommand("<i>P </i> vs <i>H</i>");
        PvsHButton.addActionListener(this::PvsHButton_actionPerformed);
        PvsHButton.setText("<i>P vs H</i>");

        dobsonHudsonParams.setBorder(titledBorder2);
        parammup1.setHelpText("Natural mortality rate of adult parasites (/time)");
        parammup1.setParameterName("<i>\u03BC</i><sub>P</sub>");
        parammup1.setMaxValue(10.0);
        parammup1.setIncrementAmount(0.1);
        parammup1.setDefaultValue(1.0);
        parammup1.setCurrentValue(1.0);
        hostGrowthPanel.add(paramb1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        hostGrowthPanel.add(paramd1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        DensitiesPanel.add(paramH1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        DensitiesPanel.add(paramP1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        DensitiesPanel.add(paramA, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        dobsonHudsonParams.add(hostGrowthPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        parasiteGrowthPanel.add(parammup1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramsigma, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramalpha1, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(parambeta1, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramk1, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramgamma1, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(parammuA, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramTheta, new GridBagConstraints(0, 7, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasiteGrowthPanel.add(paramlambda1, new GridBagConstraints(0, 8, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        dobsonHudsonParams.add(DensitiesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        dobsonHudsonParams.add(parasiteGrowthPanel, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        parasitePanel.add(paramk, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasitePanel.add(paramgamma, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasitePanel.add(parammuP, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasitePanel.add(paramalpha, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasitePanel.add(parambeta, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        parasitePanel.add(paramlambda, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        this.add(graphTypePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        graphTypePanel.add(HPWvstButton, null);
        graphTypePanel.add(PvsHButton, null);
        graphTypePanel.add(HvsPvsWButton, null);
        this.add(modelType, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelType.add(AndersonMayButton, null);
        modelType.add(dAMBox, null);
        modelType.add(DobsonHudsonButton, null);
        this.add(modelSpecificParamsTabbedPane, new GridBagConstraints(1, 0, 1, 3, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelSpecificParamsTabbedPane.add(andersonMayParams, "Anderson & May");
        hostPanel.add(paramb, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        hostPanel.add(paramd, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        andersonMayParams.add(parasitePanel, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        andersonMayParams.add(hostDensitiesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        hostDensitiesPanel.add(paramH, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        hostDensitiesPanel.add(paramP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        hostDensitiesPanel.add(paramW, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
        andersonMayParams.add(hostPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(runningTimePanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        bg1.add(HvsPvsWButton);
        bg1.add(PvsHButton);
        bg1.add(HPWvstButton);
        bg2.add(AndersonMayButton);
        bg2.add(DobsonHudsonButton);
        modelSpecificParamsTabbedPane.add(dobsonHudsonParams, "Dobson & Hudson");
        runningTimePanel.setAutoIncrement(true, true);
        registerChildren(this);
    }

    void HvsPvsWButton_actionPerformed(ActionEvent e) {

    }

    void PvsHButton_actionPerformed(ActionEvent e) {

    }

    void HPWvstButton_actionPerformed(ActionEvent e) {

    }
}
