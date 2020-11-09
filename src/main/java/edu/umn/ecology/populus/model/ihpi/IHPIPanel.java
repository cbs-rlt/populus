/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ihpi;

import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;
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

public class IHPIPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -104726416224982985L;
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.ihpi.Res");
    final JPanel initialConditionsPanel = new JPanel();
    Border border1;
    final JPanel paramsPanel = new JPanel();
    final PopulusParameterField thetaPF = new PopulusParameterField();
    final JRadioButton type2Button = new JRadioButton();
    final JRadioButton pvsnButton = new StyledRadioButton();
    final JRadioButton type4Button = new JRadioButton();
    TitledBorder titledBorder1;
    final JPanel modelTypePanel = new JPanel();
    Border border2;
    Border border4;
    TitledBorder titledBorder2;
    final SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
    Border border3;
    final PopulusParameterField IPPF = new PopulusParameterField();
    TitledBorder titledBorder3;
    final PopulusParameterField ThPF = new PopulusParameterField();
    final PopulusParameterField n0PF = new PopulusParameterField();
    final PopulusParameterField kPF = new PopulusParameterField();
    final PopulusParameterField IPF = new PopulusParameterField();
    final PopulusParameterField FPF = new PopulusParameterField();
    final JRadioButton type3Button = new JRadioButton();
    final JPanel plotTypePanel = new JPanel();
    final JRadioButton type1Button = new JRadioButton();
    TitledBorder titledBorder4;
    final ButtonGroup bg = new ButtonGroup();
    final JRadioButton npvstButton = new StyledRadioButton();
    final ButtonGroup plotGroup = new ButtonGroup();
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final GridBagLayout gridBagLayout2 = new GridBagLayout();
    final PopulusParameterField p0PF = new PopulusParameterField();
    final PopulusParameterField gensPF = new PopulusParameterField();
    final PopulusParameterField aPF = new PopulusParameterField();

    @Override
    public BasicPlot getPlotParamInfo() {
        int type = 0;
        if (type1Button.isSelected()) {
            type = 1;
        } else {
            if (type2Button.isSelected()) {
                type = 2;
            } else {
                if (type3Button.isSelected()) {
                    type = 3;
                } else {
                    type = 4;
                }
            }
        }
        return new IHPIParamInfo(n0PF.getDouble(), p0PF.getDouble(), FPF.getDouble(), IPF.getDouble(), aPF.getDouble(), kPF.getDouble(), thetaPF.getDouble(), ThPF.getDouble(), IPPF.getDouble(), gensPF.getInt(), type, npvstButton.isSelected());

		/*
   double n0, double p0, double F_, double I, double a, double k,
   double theta,	double Th, double Ip_, int gens, int type, boolean vsTime)
		 */
    }

    public IHPIPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOutputGraphName() {

        //return "Insecticides in Host-Parasitoid Interactions";

        //return "Host-Parasitoid with Insecticide";
        return res.getString("Discrete_Predator");
    }

    /**
     * Changes enablement of components
     */

    @Override
    public void actionPerformed(ActionEvent e) {

        //if (e.getSource() instanceof JRadioButton) {
        this.fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    void type4Button_stateChanged(ChangeEvent e) {
        if (type4Button.isSelected()) {
            thetaPF.setVisible(false);
            ThPF.setVisible(false);
            IPPF.setVisible(true);
        }
    }

    void type3Button_stateChanged(ChangeEvent e) {
        if (type3Button.isSelected()) {
            thetaPF.setVisible(false);
            ThPF.setVisible(true);
            IPPF.setVisible(false);
        }
    }

    void type2Button_stateChanged(ChangeEvent e) {
        if (type2Button.isSelected()) {
            thetaPF.setVisible(false);
            ThPF.setVisible(true);
            IPPF.setVisible(false);
        }
    }

    void type1Button_stateChanged(ChangeEvent e) {
        if (type1Button.isSelected()) {
            thetaPF.setVisible(true);
            ThPF.setVisible(false);
            IPPF.setVisible(false);
        }
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, res.getString("Model_Type"));
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border2, res.getString("Model_Parameters"));
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border3, res.getString("Initial_Conditions"));
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder4 = new TitledBorder(border4, res.getString("Axes"));
        modelTypePanel.setBorder(titledBorder1);
        modelTypePanel.setLayout(gridBagLayout2);
        initialConditionsPanel.setBorder(titledBorder3);
        initialConditionsPanel.setLayout(simpleVFlowLayout2);
        n0PF.setCurrentValue(25.0);
        n0PF.setDefaultValue(25.0);
        n0PF.setHelpText(res.getString("Initial_Population"));
        n0PF.setIncrementAmount(1.0);
        n0PF.setMaxValue(999.0);
        n0PF.setMinValue(1.0);
        n0PF.setParameterName(res.getString("Prey_Size_i_N_i_sub_0"));
        type3Button.setText(res.getString("Type_III"));
        type3Button.setFocusPainted(false);
        type3Button.addChangeListener(this::type3Button_stateChanged);

        //type3Button.setActionCommand("continuous");
        type1Button.setSelected(true);
        type1Button.setText(res.getString("Type_I"));
        type1Button.setFocusPainted(false);
        type1Button.addChangeListener(this::type1Button_stateChanged);

        //type1Button.setActionCommand("discrete");
        IPF.setCurrentValue(0.8);
        IPF.setDefaultValue(0.8);
        IPF.setIncrementAmount(0.1);
        IPF.setMaxValue(1.0);
        IPF.setParameterName("<i>I</i>");
        IPF.setHelpText("The probability that hosts will survive the insecticide. It takes values from 0 to 1.");
        this.setLayout(gridBagLayout1);
        p0PF.setParameterName(res.getString("Predator_Size_i_P_i"));
        p0PF.setMinValue(1.0);
        p0PF.setMaxValue(999.0);
        p0PF.setIncrementAmount(1.0);
        p0PF.setHelpText(res.getString("Initial_Population"));
        p0PF.setDefaultValue(10.0);
        p0PF.setCurrentValue(10.0);
        gensPF.setCurrentValue(25.0);
        gensPF.setDefaultValue(25.0);
        gensPF.setHelpText(res.getString("Number_of_generations"));
        gensPF.setIncrementAmount(5.0);
        gensPF.setMaxValue(999.0);
        gensPF.setMinValue(1.0);
        gensPF.setParameterName(res.getString("Generations_"));
        paramsPanel.setLayout(simpleVFlowLayout1);
        kPF.setCurrentValue(0.6);
        kPF.setDefaultValue(0.6);
        kPF.setMaxValue(999.0);
        kPF.setParameterName("<i>k</i>");
        kPF.setHelpText("The negative binomial dispersion parameter.");
        thetaPF.setCurrentValue(1.0);
        thetaPF.setDefaultValue(1.0);
        thetaPF.setMaxValue(1.0);
        thetaPF.setParameterName("\u0398");
        thetaPF.setHelpText("The patch-specific giving-up time.");
        ThPF.setCurrentValue(0.05);
        ThPF.setDefaultValue(0.05);
        ThPF.setIncrementAmount(0.1);
        ThPF.setMaxValue(1.0);
        ThPF.setParameterName("<i>T<sub>h</sub></i>");
        ThPF.setHelpText("The handling time required for the parasitoid to oviposit on each host individual after finding it.");
        FPF.setCurrentValue(2.0);
        FPF.setDefaultValue(2.0);
        FPF.setIncrementAmount(0.2);
        FPF.setMaxValue(5.0);
        FPF.setParameterName("<i>F</i>");
        FPF.setHelpText("The intrinsic rate of increase of the host population.");
        aPF.setCurrentValue(1.0);
        aPF.setDefaultValue(1.0);
        aPF.setIncrementAmount(0.1);
        aPF.setMaxValue(1.0);
        aPF.setParameterName("<i>a</i>");
        aPF.setHelpText("The search efficiency or \"area of discovery\" of the parasitoid, taking values from 0 to 1.");
        //type2Button.setActionCommand("continuous");
        type2Button.setText(res.getString("Type_II"));
        type2Button.setFocusPainted(false);
        type2Button.addChangeListener(this::type2Button_stateChanged);
        plotTypePanel.setBorder(titledBorder4);
        pvsnButton.setText("<i>P</i>  vs <i>N</i>");
        pvsnButton.setFocusPainted(false);
        npvstButton.setSelected(true);
        npvstButton.setText("<i>N</i>, <i>P</i>  vs <i>t</i>");
        npvstButton.setFocusPainted(false);
        titledBorder4.setTitle(res.getString("Plot_Type"));
        this.setMinimumSize(new Dimension(360, 250));

        //type4Button.setActionCommand("continuous");
        type4Button.setText("Type IV");
        type4Button.setFocusPainted(false);
        type4Button.addChangeListener(this::type4Button_stateChanged);
        IPPF.setParameterName("<i>I</i>'");
        IPPF.setHelpText("The probability that adult parasitoids will survive the insecticide, taking values from 0 to 1.");
        IPPF.setMaxValue(1.0);
        IPPF.setIncrementAmount(0.1);
        IPPF.setDefaultValue(1.0);
        IPPF.setCurrentValue(1.0);
        ThPF.setVisible(false);
        IPPF.setVisible(false);
        this.add(modelTypePanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 10, 0), 0, 0));
        modelTypePanel.add(type1Button, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(type2Button, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(type3Button, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        modelTypePanel.add(type4Button, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.add(initialConditionsPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 20, 0), 0, 0));
        initialConditionsPanel.add(n0PF, null);
        initialConditionsPanel.add(p0PF, null);
        this.add(paramsPanel, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        paramsPanel.add(IPF, null);
        paramsPanel.add(FPF, null);
        paramsPanel.add(aPF, null);
        paramsPanel.add(kPF, null);
        paramsPanel.add(thetaPF, null);
        paramsPanel.add(ThPF, null);
        paramsPanel.add(IPPF, null);
        this.add(plotTypePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 20, 0), 0, 0));
        plotTypePanel.add(npvstButton, null);
        plotTypePanel.add(pvsnButton, null);
        this.add(gensPF, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        bg.add(type1Button);
        bg.add(type2Button);
        bg.add(type3Button);
        bg.add(type4Button);
        plotGroup.add(this.npvstButton);
        plotGroup.add(this.pvsnButton);
        this.registerChildren(this);
    }

	/*
public int getTriggers() {
return 0;
}
	 */
}
