/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.prm;

import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldListener;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

public class PRMPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 8558460283140767054L;
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.prm.Res");
    Border border5;
    Border border7;
    Border border8;
    final ButtonGroup bg1 = new ButtonGroup();
    TitledBorder titledBorder6;
    final ButtonGroup bg2 = new ButtonGroup();
    ButtonGroup bg3 = new ButtonGroup();
    final JPanel graphTypePanel = new JPanel();
    final PopulusParameterField paramh = new PopulusParameterField();
    final GridLayout gridLayout1 = new GridLayout();
    Border border1;
    TitledBorder titledBorder1;
    final JPanel cominsParams = new JPanel();
    final GridBagLayout gridBagLayout3 = new GridBagLayout();
    TitledBorder titledBorder9;
    Border border2;
    TitledBorder titledBorder5;
    TitledBorder titledBorder2;
    final PopulusParameterField paramK = new PopulusParameterField();
    final PopulusParameterField paramL = new PopulusParameterField();
    Border border6;
    Border border3;
    TitledBorder titledBorder3;
    TitledBorder titledBorder7;
    final JPanel modelType = new JPanel();
    Border border4;
    TitledBorder titledBorder4;
    Border border11;
    TitledBorder titledBorder11;
    private final JRadioButton AlstadAndowButton = new StyledRadioButton();
    TitledBorder titledBorder8;
    private final JRadioButton CominsButton = new StyledRadioButton();
    final JPanel cominsParamsPanel = new JPanel();
    final GridLayout gridLayout3 = new GridLayout();
    Border border9;
    final JTabbedPane modelSpecificParamsTabbedPane = new JTabbedPane();
    Border border10;
    final JPanel alstadAndowParams = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final PopulusParameterField paramR = new PopulusParameterField();
    private final PopulusParameterField paramG = new PopulusParameterField();
    private final PopulusParameterField paramb = new PopulusParameterField();
    private final PopulusParameterField paramp0 = new PopulusParameterField();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final PopulusParameterField paramy0 = new PopulusParameterField();
    private final PopulusParameterField paramw0 = new PopulusParameterField();
    private final JPanel hostDensitiesPanel = new JPanel();
    private final JRadioButton fourvstButton = new StyledRadioButton();
    private final JRadioButton threevstButton = new StyledRadioButton();
    private final PopulusParameterField paramx0 = new PopulusParameterField();
    private final PopulusParameterField paramp1 = new PopulusParameterField();
    private final PopulusParameterField paramw1 = new PopulusParameterField();
    private final JPanel hostDensitiesPanel1 = new JPanel();
    private final PopulusParameterField paramx1 = new PopulusParameterField();
    private final GridBagLayout gridBagLayout6 = new GridBagLayout();
    private final PopulusParameterField paramy1 = new PopulusParameterField();
    private final JPanel alstadParamsPanel = new JPanel();
    private final PopulusParameterField paramL1 = new PopulusParameterField();
    private final PopulusParameterField paramb1 = new PopulusParameterField();
    private final PopulusParameterField paramb2 = new PopulusParameterField();
    private final PopulusParameterField paramb3 = new PopulusParameterField();

    private final PopulusParameterField paramK1 = new PopulusParameterField();
    private final PopulusParameterField paramh1 = new PopulusParameterField();
    private final PopulusParameterField paramR1 = new PopulusParameterField();
    private final PopulusParameterField paramG1 = new PopulusParameterField();
    private final PopulusParameterField parama = new PopulusParameterField();
    private final PopulusParameterField parammu = new PopulusParameterField();
    private final PopulusParameterField paramF = new PopulusParameterField();
    private final PopulusParameterField paramS1 = new PopulusParameterField();
    private final PopulusParameterField paramS2 = new PopulusParameterField();
    private final PopulusParameterField paramS3 = new PopulusParameterField();
    private final JRadioButton twovstButton = new StyledRadioButton();
    private final JRadioButton onevstButton = new StyledRadioButton();
    private final PopulusParameterField paramTime1 = new PopulusParameterField();
    private final PopulusParameterField paramTime2 = new PopulusParameterField();
    private TitledBorder titledBorder10;
    private TitledBorder titledBorder14;
    private final GridBagLayout gridBagLayout5 = new GridBagLayout();
    private final GridBagLayout gridBagLayout7 = new GridBagLayout();
    private final PopulusParameterField paramGenperYr = new PopulusParameterField();

    @Override
    public BasicPlot getPlotParamInfo() {


        int plotType;
        if (onevstButton.isSelected())
            plotType = PRMParamInfo.onevst;
        else {
            if (twovstButton.isSelected())
                plotType = PRMParamInfo.twovst;
            else {
                if (threevstButton.isSelected())
                    plotType = PRMParamInfo.threevst;
                else
                    plotType = PRMParamInfo.fourvst;
            }
        }

        int modelType;
        if (CominsButton.isSelected()) {
            modelType = PRMParamInfo.COMINS;
        } else if (AlstadAndowButton.isSelected()) {
            modelType = PRMParamInfo.ALSTAD;
        } else {
            System.err.println("Error in IPRMPanel: Unknown button selected");
            modelType = -1;
        }
        if (modelType == PRMParamInfo.COMINS)
            return new PRMParamInfo(modelType, plotType, paramTime1.getDouble(), 0, paramx0.getDouble(), paramy0.getDouble(), paramp0.getDouble(), paramw0.getDouble(), paramb.getDouble(), 0, 0, paramG.getDouble(), paramR.getDouble(), paramL.getDouble(), paramK.getDouble(), paramh.getDouble(), 0, 0, 0, 0, 0, 0);
        else
            return new PRMParamInfo(modelType, plotType, paramTime2.getDouble(), paramGenperYr.getInt(), paramx1.getDouble(), paramy1.getDouble(), paramp1.getDouble(), paramw1.getDouble(), paramb1.getDouble(), paramb2.getDouble(), paramb3.getDouble(), paramG1.getDouble(), paramR1.getDouble(), paramL1.getDouble(), paramK1.getDouble(), paramh1.getDouble(), parammu.getDouble(), paramS1.getDouble(), paramS2.getDouble(), paramS3.getDouble(), paramF.getDouble(), parama.getDouble());

    }


    public PRMPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void AlstadAndowButton_actionPerformed(ActionEvent e) {
        this.modelSpecificParamsTabbedPane.setSelectedComponent(alstadAndowParams);

        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void CominsButton_actionPerformed(ActionEvent e) {
        this.modelSpecificParamsTabbedPane.setSelectedComponent(cominsParams);

        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void cominsParams_componentShown(ComponentEvent e) {
        this.CominsButton.setSelected(true);

        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);

    }

    void alstadAndowParams_componentShown(ComponentEvent e) {
        this.AlstadAndowButton.setSelected(true);
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);

    }


    private void jbInit() throws Exception {
        border1 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText, 1), "");
        titledBorder1 = new TitledBorder(border1, res.getString("Model_Type"));
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText, 1), "Model Parameters");
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border3, res.getString("Initial_Conditions"));
        border4 = BorderFactory.createLineBorder(Color.black, 1);
        titledBorder4 = new TitledBorder(border4, res.getString("Model_Type"));
        border6 = BorderFactory.createLineBorder(Color.black, 1);
        titledBorder6 = new TitledBorder(border6, "Generations/Year");


        titledBorder10 = new TitledBorder(BorderFactory.createLineBorder(Color.darkGray, 1), "Initial Densities");
        titledBorder14 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText, 1), "Termination Condition");
        graphTypePanel.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(4);
        graphTypePanel.setBorder(titledBorder1);
        cominsParams.setLayout(gridBagLayout3);
        cominsParams.setBorder(titledBorder2);
        cominsParams.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {
                cominsParams_componentShown(e);
            }
        });
        paramL.setCurrentValue(1.0);
        paramL.setDefaultValue(1.0);
        paramL.setIncrementAmount(0.05);
        paramL.setMaxValue(5.0);
        paramL.setParameterName("<i>L</i>");
        paramK.setCurrentValue(0.3);
        paramK.setDefaultValue(0.3);
        paramK.setIncrementAmount(0.05);
        paramK.setMaxValue(999.0);
        paramK.setParameterName("<i>K</i>");
        paramK.setHelpText("Genotype-specific survival rate for susceptible homozygotes exposed " +
                "to the treatment");
        titledBorder1.setTitle(res.getString("Graph_Type"));
        modelType.setBorder(titledBorder4);
        modelType.setLayout(gridLayout3);
        gridLayout3.setColumns(1);
        gridLayout3.setRows(0);
        this.setLayout(gridBagLayout1);
        CominsButton.setSelected(true);
        CominsButton.setText("Comins two-patch pesticide model");
        CominsButton.setActionCommand("Comins two-patch pesticide model");
        CominsButton.setFocusPainted(false);
        CominsButton.addActionListener(e -> CominsButton_actionPerformed(e));
        AlstadAndowButton.setText("Alstad & Andow <i>Bt</i> maize model");
        AlstadAndowButton.setFocusPainted(false);
        AlstadAndowButton.addActionListener(e -> AlstadAndowButton_actionPerformed(e));

        alstadAndowParams.setLayout(gridBagLayout2);
        alstadAndowParams.addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {
                alstadAndowParams_componentShown(e);
            }
        });
        cominsParamsPanel.setLayout(gridBagLayout5);
        paramh.setMaxValue(10000.0);
        paramh.setParameterName("<i>h</i>");
        paramh.setHelpText("Gene-expression parameter");
        paramR.setCurrentValue(0.2);
        paramR.setDefaultValue(0.2);
        paramR.setIncrementAmount(0.1);
        paramR.setMaxValue(100000.0);
        paramR.setParameterName("<i>r</i>");
        paramG.setCurrentValue(10.0);
        paramG.setDefaultValue(10.0);
        paramG.setMaxValue(10000.0);
        paramG.setParameterName("<i>G</i>");
        paramG.setHelpText("Relative size of the treated and untreated units ( untreated is G " +
                "times as large as treated)");
        paramb.setCurrentValue(1.0);
        paramb.setDefaultValue(1.0);
        paramb.setIncrementAmount(0.1);
        paramb.setMaxValue(2.0);
        paramb.setParameterName("<i>b</i>");
        paramb.setHelpText("Intensity of denisty-dependent mortality");
        paramp0.setCurrentValue(0.03);
        paramp0.setHelpText("Initial frequency of the resistant allele in the treated region");
        paramp0.setDefaultValue(0.03);
        paramp0.setIncrementAmount(0.01);
        paramp0.setMaxValue(1.0);
        paramp0.setParameterName("<i>p</i><sub>0</sub>");
        paramy0.setCurrentValue(10.0);
        paramy0.setHelpText("Initial population size of insects in the untreated unit");
        paramy0.setDefaultValue(10.0);
        paramy0.setIncrementAmount(5.0);
        paramy0.setMaxValue(100000.0);
        paramy0.setParameterName("<i>y</i><sub>0</sub>");
        paramw0.setCurrentValue(0.03);
        paramw0.setHelpText("Initial frequency of resistant allele in the untreated region");
        paramw0.setDefaultValue(0.03);
        paramw0.setIncrementAmount(0.01);
        paramw0.setMaxValue(1.0);
        paramw0.setParameterName("<i>w</i><sub>0</sub>");
        hostDensitiesPanel.setBorder(titledBorder10);
        hostDensitiesPanel.setToolTipText("Initial Host Densities");
        hostDensitiesPanel.setLayout(gridBagLayout4);

        threevstButton.setActionCommand("<i>x, y/G, p, w vs t</i>");
        threevstButton.setFocusPainted(false);
        threevstButton.addActionListener(e -> threevstButton_actionPerformed(e));
        threevstButton.setText("<i>(x + y ) vs t</i>");
        fourvstButton.setActionCommand("<i>( x * p ) + ( y * w ) vs t</i>");
        fourvstButton.setFocusPainted(false);
        fourvstButton.addActionListener(e -> fourvstButton_actionPerformed(e));
        fourvstButton.setText("<i>( x*p + y* w ) vs t</i>");

        alstadAndowParams.setBorder(titledBorder2);
        paramx0.setParameterName("<i>x</i><sub>0</sub>");
        paramx0.setMaxValue(100000.0);
        paramx0.setIncrementAmount(0.5);
        paramx0.setDefaultValue(1.0);
        paramx0.setHelpText("Initial population density of insects in the treated unit");
        paramx0.setCurrentValue(1.0);
        paramp1.setParameterName("<i>p</i><sub>0</sub>");
        paramp1.setMaxValue(1.0);
        paramp1.setIncrementAmount(0.05);
        paramp1.setDefaultValue(0.0030);
        paramp1.setHelpText("Initial frequency of the resistant allele in the toxic region");
        paramp1.setCurrentValue(0.0030);
        paramw1.setParameterName("<i>w</i><sub>0</sub>");
        paramw1.setMaxValue(1.0);
        paramw1.setIncrementAmount(0.05);
        paramw1.setDefaultValue(0.0030);
        paramw1.setHelpText("Initial frequency of resistant allele in the nontoxic region");
        paramw1.setCurrentValue(0.0030);
        hostDensitiesPanel1.setLayout(gridBagLayout6);
        hostDensitiesPanel1.setBorder(titledBorder10);
        hostDensitiesPanel1.setToolTipText("Initial Host Densities");
        paramx1.setParameterName("<i>x</i><sub>0</sub>");
        paramx1.setMaxValue(100000.0);
        paramx1.setIncrementAmount(0.5);
        paramx1.setDefaultValue(1.0);
        paramx1.setHelpText("Initial population density of insects in the treated unit");
        paramx1.setCurrentValue(1.0);
        paramy1.setCurrentValue(1.0);
        paramy1.setHelpText("Initial population size of insects in the untreated unit");
        paramy1.setDefaultValue(1.0);
        paramy1.setIncrementAmount(0.5);
        paramy1.setMaxValue(100000.0);
        paramy1.setParameterName("<i>y</i><sub>0</sub>");
        alstadParamsPanel.setLayout(gridBagLayout7);
        paramL1.setCurrentValue(1.0);
        paramL1.setDefaultValue(1.0);
        paramL1.setIncrementAmount(0.05);
        paramL1.setMaxValue(5.0);
        paramL1.setParameterName("<i>L</i>");
        paramb1.setCurrentValue(0.7);
        paramb1.setDefaultValue(0.7);
        paramb1.setIncrementAmount(0.1);
        paramb1.setMaxValue(2.0);
        paramb1.setParameterName("<i>b</i><sub>1</sub>");
        paramb1.setHelpText("Intensity of denisty-dependent mortality for first generation");

        paramb2.setCurrentValue(0.7);
        paramb2.setDefaultValue(0.7);
        paramb2.setIncrementAmount(0.1);
        paramb2.setMaxValue(2.0);
        paramb2.setParameterName("<i>b</i><sub>2</sub>");
        paramb2.setHelpText("Intensity of denisty-dependent mortality for second generation");

        paramb3.setCurrentValue(0.7);
        paramb3.setDefaultValue(0.7);
        paramb3.setIncrementAmount(0.1);
        paramb3.setMaxValue(2.0);
        paramb3.setParameterName("<i>b</i><sub>3</sub>");
        paramb3.setHelpText("Intensity of denisty-dependent mortality for third generation");

        paramK1.setCurrentValue(0.0010);
        paramK1.setDefaultValue(0.0010);
        paramK1.setIncrementAmount(0.0050);
        paramK1.setMaxValue(999.0);
        paramK1.setParameterName("<i>K</i>");
        paramK1.setHelpText("Genotype-specific survival rate for susceptible homozygotes exposed " +
                "to the treatment");
        paramh1.setIncrementAmount(0.01);
        paramh1.setMaxValue(10000.0);
        paramh1.setParameterName("<i>h</i>");
        paramh1.setCurrentValue(0.025);
        paramh1.setDefaultValue(0.025);
        paramh1.setHelpText("Gene-expression parameter");
        paramR1.setCurrentValue(0.95);
        paramR1.setDefaultValue(0.95);
        paramR1.setHelpText(res.getString("Init_Population"));
        paramR1.setIncrementAmount(0.1);
        paramR1.setMaxValue(100000.0);
        paramR1.setParameterName("<i>r</i>");
        paramG1.setCurrentValue(1.0);
        paramG1.setDefaultValue(1.0);
        paramG1.setMaxValue(10000.0);
        paramG1.setParameterName("<i>G</i>");
        paramG1.setHelpText("Relative size of the treated and untreated units ( untreated is G " +
                "times as large as treated)");
        parama.setHelpText("Reciprocal of the threshold density below which density-dependent " +
                "larval mortality disappears");
        parama.setParameterName("<i>a</i>");
        parama.setMaxValue(50.0);
        parama.setIncrementAmount(0.1);
        parama.setDefaultValue(4.0);
        parama.setCurrentValue(4.0);
        parammu.setCurrentValue(0.01);
        parammu.setDefaultValue(0.01);
        parammu.setHelpText("Density-independent over-winter survival rate");
        parammu.setParameterName("\u03BC");
        parammu.setMaxValue(10000.0);
        parammu.setIncrementAmount(0.05);
        paramF.setIncrementAmount(10.0);
        paramF.setMaxValue(10000.0);
        paramF.setParameterName("<i>F</i>");
        paramF.setCurrentValue(100.0);
        paramF.setDefaultValue(100.0);
        paramF.setHelpText("Fecundity factor, the average number of daughters produced per female");
        paramS1.setCurrentValue(1.0);
        paramS1.setDefaultValue(1.0);
        paramS1.setHelpText("Preference factor for first generation every year");
        paramS1.setIncrementAmount(0.1);
        paramS1.setParameterName("<i>s<sub>1</sub></i>");
        paramS1.setMaxValue(10000.0);

        paramS2.setCurrentValue(1.0);
        paramS2.setDefaultValue(1.0);
        paramS2.setHelpText("Preference factor for second generation every year");
        paramS2.setIncrementAmount(0.1);
        paramS2.setParameterName("<i>s<sub>2</sub></i>");
        paramS2.setMaxValue(10000.0);

        paramS3.setCurrentValue(1.0);
        paramS3.setDefaultValue(1.0);
        paramS3.setEnabled(false);
        paramS3.setHelpText("Preference factor for third generation every year");
        paramS3.setIncrementAmount(0.1);
        paramS3.setParameterName("<i>s<sub>3</sub></i>");
        paramS3.setMaxValue(10000.0);

        twovstButton.setText("<i>x * p, w * y/G vs t</i>");
        twovstButton.addActionListener(e -> twovstButton_actionPerformed(e));
        twovstButton.setActionCommand("<i>x * p, y/G * w </i> vs <i>t</i>");
        twovstButton.setFocusPainted(false);
        onevstButton.setText("<i>x, y/G, p, w vs t</i>");
        onevstButton.setSelected(true);
        onevstButton.addActionListener(e -> onevstButton_actionPerformed(e));
        onevstButton.setFocusPainted(false);
        onevstButton.setActionCommand("<i>x, y/G, p, w vs t</i>");
        paramTime1.setParameterName("Generations");
        paramTime1.setBorder(titledBorder14);
        paramTime1.setMaxValue(100000.0);
        paramTime1.setIncrementAmount(100.0);
        paramTime1.setDefaultValue(1000.0);
        paramTime1.setHelpText("Generations");
        paramTime1.setCurrentValue(1000.0);
        paramTime2.setCurrentValue(30.0);
        paramTime2.setHelpText("Generations");
        paramTime2.setDefaultValue(30.0);
        paramTime2.setMaxValue(1000.0);
        paramTime2.setMinValue(1.0);
        paramTime2.setParameterName("Generations");
        paramTime2.setBorder(titledBorder14);
        paramGenperYr.setParameterName("# of Gens/Yr");
        paramGenperYr.setMaxValue(3.0);
        paramGenperYr.setMinValue(1.0);
        paramGenperYr.setDefaultValue(2.0);
        paramGenperYr.setHelpText("Number of generations per year");
        paramGenperYr.setCurrentValue(2.0);
        paramGenperYr.addParameterFieldListener(new IncrementChecker());

		/*if ((paramGenperYr.getInt()) == 1) {
        paramS2.setEnabled(false);
        paramS3.setEnabled(false);
        paramb2.setEnabled(false);
        paramb3.setEnabled(false);
     }
     else{
        if((paramGenperYr.getCurrentValue()) == 2){
           paramS2.setEnabled(true);
           paramS3.setEnabled(false);
           paramb2.setEnabled(true);
           paramb3.setEnabled(false);
        }
        else if((paramGenperYr.getCurrentValue()) == 3){
           paramS2.setEnabled(true);
           paramS3.setEnabled(true);
           paramb2.setEnabled(true);
           paramb3.setEnabled(true);
        }
      }
		 */

        alstadParamsPanel.add(paramS1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramS2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramS3, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(parammu, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel.add(paramy0, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel.add(paramp0, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel.add(paramw0, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 1, 0));
        hostDensitiesPanel.add(paramx0, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 1, 1));

        alstadAndowParams.add(alstadParamsPanel, new GridBagConstraints(1, 0, 1, 3, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        alstadParamsPanel.add(parama, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramb1, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramb2, new GridBagConstraints(1, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramb3, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramG1, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramR1, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramL1, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramK1, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadParamsPanel.add(paramh1, new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel1.add(paramy1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel1.add(paramp1, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel1.add(paramw1, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        hostDensitiesPanel1.add(paramx1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        alstadAndowParams.add(paramGenperYr, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.add(graphTypePanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        cominsParamsPanel.add(paramb, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParamsPanel.add(paramG, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParamsPanel.add(paramR, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParamsPanel.add(paramL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParamsPanel.add(paramK, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParamsPanel.add(paramh, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        cominsParams.add(paramTime1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        cominsParams.add(hostDensitiesPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        graphTypePanel.add(onevstButton, null);
        graphTypePanel.add(twovstButton, null);
        graphTypePanel.add(threevstButton, null);
        graphTypePanel.add(fourvstButton, null);
        this.add(modelType, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelType.add(CominsButton, null);
        modelType.add(AlstadAndowButton, null);
        this.add(modelSpecificParamsTabbedPane, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelSpecificParamsTabbedPane.add(cominsParams, "Comins");
        cominsParams.add(cominsParamsPanel, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        bg1.add(fourvstButton);
        bg1.add(threevstButton);
        bg1.add(onevstButton);
        bg1.add(twovstButton);
        bg2.add(CominsButton);
        bg2.add(AlstadAndowButton);
        modelSpecificParamsTabbedPane.add(alstadAndowParams, "Alstad & Andow");
        alstadAndowParams.add(paramTime2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        alstadAndowParams.add(hostDensitiesPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.registerChildren(this);

    }

    class IncrementChecker implements ParameterFieldListener {

        @Override
        public void parameterFieldChanged(ParameterFieldEvent e) {
            if ((paramGenperYr.getInt()) == 1) {
                paramS2.setEnabled(false);
                paramS3.setEnabled(false);
                paramb2.setEnabled(false);
                paramb3.setEnabled(false);
            } else {
                if ((paramGenperYr.getCurrentValue()) == 2) {
                    paramS2.setEnabled(true);
                    paramS3.setEnabled(false);
                    paramb2.setEnabled(true);
                    paramb3.setEnabled(false);
                } else if ((paramGenperYr.getCurrentValue()) == 3) {
                    paramS2.setEnabled(true);
                    paramS3.setEnabled(true);
                    paramb2.setEnabled(true);
                    paramb3.setEnabled(true);
                }
            }
        }
    }

    void fourvstButton_actionPerformed(ActionEvent e) {

    }

    void threevstButton_actionPerformed(ActionEvent e) {

    }

    void twovstButton_actionPerformed(ActionEvent e) {

    }

    void onevstButton_actionPerformed(ActionEvent e) {

    }
}
