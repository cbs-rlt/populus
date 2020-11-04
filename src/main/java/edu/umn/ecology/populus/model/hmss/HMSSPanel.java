/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hmss;

import edu.umn.ecology.populus.math.Routines;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class HMSSPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -865593510989217294L;
    JPanel modParamP = new JPanel();
    PopulusParameterField bPPF = new PopulusParameterField();
    private JPanel plotTypeP = new JPanel();
    private JRadioButton rqDBCRB = new StyledRadioButton();
    private JRadioButton pqDABRB = new StyledRadioButton();
    private PopulusParameterField ePPF = new PopulusParameterField();
    private PopulusParameterField mPPF = new PopulusParameterField();
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private ButtonGroup plotTypeBG = new ButtonGroup();
    private JRadioButton VvstRB = new StyledRadioButton();
    private JRadioButton DvstRB = new StyledRadioButton();
    private PopulusParameterField p0PPF = new PopulusParameterField();
    private PopulusParameterField r0PPF = new PopulusParameterField();
    private PopulusParameterField gensPPF = new PopulusParameterField();
    private PopulusParameterField q0PPF = new PopulusParameterField();
    private PopulusParameterField aPPF = new PopulusParameterField();
    private JRadioButton pqrvstRB = new StyledRadioButton();
    private JPanel modelTypeP = new JPanel();
    private JRadioButton monoRB = new JRadioButton();
    private JRadioButton polyRB = new JRadioButton();
    private JRadioButton andersonRB = new JRadioButton();
    private JRadioButton bellRB = new JRadioButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private ButtonGroup baBG = new ButtonGroup();
    private ButtonGroup mpBG = new ButtonGroup();
    private TitledBorder titledBorder1;

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType;

        balanceAlphaBetaEpsilon(0);

        if (DvstRB.isSelected())
            plotType = HMSSParamInfo.DvsT;
        else if (VvstRB.isSelected())
            plotType = HMSSParamInfo.VvsT;
        else if (pqDABRB.isSelected())
            plotType = HMSSParamInfo.pqDAB;
        else if (rqDBCRB.isSelected())
            plotType = HMSSParamInfo.rqDBC;
        else
            plotType = HMSSParamInfo.pqrvsT;

        return new HMSSParamInfo(aPPF.getDouble(), bPPF.getDouble(), ePPF.getDouble(), p0PPF.getDouble(), q0PPF.getDouble(),
                r0PPF.getDouble(), mPPF.getDouble(), gensPPF.getInt(), plotType, bellRB.isSelected(),
                monoRB.isSelected());
    }

    public HMSSPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder2 = new TitledBorder("Model Parameters");
        titledBorder3 = new TitledBorder("Plot Type");
        titledBorder1 = new TitledBorder("Model Type");
        modParamP.setBorder(titledBorder2);
        plotTypeP.setBorder(titledBorder3);
        modParamP.setLayout(gridBagLayout3);
        bPPF.setCurrentValue(5.0E-4);
        bPPF.setDefaultValue(0.7);
        bPPF.setIncrementAmount(0.05);
        bPPF.setMaxValue(1.0);
        bPPF.setParameterName("<i>\u03B2</i>");
        bPPF.addParameterFieldListener(e -> bPPF_parameterFieldChanged(e));
        bPPF.setHelpText("Added fitness for being cryptic");
        rqDBCRB.setText("<i>r</> vs <i>q</>, <i>D<sub>BC</>");
        pqDABRB.setText("<i>p</> vs <i>q</>, <i>D<sub>AB</>");
        ePPF.setParameterName("<i>\u03B5</i>");
        ePPF.addParameterFieldListener(e -> ePPF_parameterFieldChanged(e));
        ePPF.setMaxValue(1.0);
        ePPF.setCurrentValue(0.0020);
        ePPF.setDefaultValue(0.2);
        ePPF.setIncrementAmount(0.05);
        ePPF.setHelpText("Added fitness for \"B\" allele");
        mPPF.setParameterName("<i>\u03BC</i>");
        mPPF.setMaxValue(1.0);
        mPPF.setIncrementAmount(0.05);
        mPPF.setHelpText("Mortality factor for \"C\" females");
        plotTypeP.setLayout(gridBagLayout4);
        this.setLayout(gridBagLayout1);
        VvstRB.setText("Viability vs <i>t</>");
        DvstRB.setText("<i>D</> vs <i>t</>");
        p0PPF.setCurrentValue(0.0010);
        p0PPF.setDefaultValue(0.0010);
        p0PPF.setIncrementAmount(0.05);
        p0PPF.setMaxValue(1.0);
        p0PPF.setParameterName("<i>p<sub>0</>");
        p0PPF.setHelpText("Initial frequency for ornamented alleles");
        r0PPF.setCurrentValue(1.0E-4);
        r0PPF.setDefaultValue(1.0E-4);
        r0PPF.setIncrementAmount(0.05);
        r0PPF.setMaxValue(1.0);
        r0PPF.setParameterName("<i>r<sub>0</>");
        r0PPF.setHelpText("Initial frequency for high-viability alleles");
        gensPPF.setCurrentValue(540.0);
        gensPPF.setDefaultValue(540.0);
        gensPPF.setIncrementAmount(20.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setMaxValue(2000.0);
        gensPPF.setParameterName("Generations");
        q0PPF.setCurrentValue(0.0010);
        q0PPF.setDefaultValue(0.0010);
        q0PPF.setIncrementAmount(0.05);
        q0PPF.setMaxValue(1.0);
        q0PPF.setParameterName("<i>q<sub>0</>");
        q0PPF.setHelpText("Initial frequency for choosy alleles");
        aPPF.setCurrentValue(0.1);
        aPPF.setDefaultValue(0.1);
        aPPF.setIncrementAmount(0.05);
        aPPF.setMaxValue(1.0);
        aPPF.setParameterName("<i>\u03B1</i>");
        aPPF.setHelpText("Baseline fitness");
        aPPF.addParameterFieldListener(e -> aPPF_parameterFieldChanged(e));
        pqrvstRB.setSelected(true);
        pqrvstRB.setText("<i>p</>, <i>q</>, <i>r</> vs <i>t</>");
        monoRB.setSelected(true);
        monoRB.setText("Monogamous");
        polyRB.setEnabled(false);
        polyRB.setText("Polygamous");
        andersonRB.setSelected(true);
        andersonRB.setText("Andersson");
        andersonRB.addChangeListener(e -> andersonRB_stateChanged(e));
        bellRB.setText("Maynard Smith/Bell");
        modelTypeP.setLayout(gridBagLayout2);
        modelTypeP.setBorder(titledBorder1);
        this.add(modParamP, new GridBagConstraints(1, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        modParamP.add(mPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(ePPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(bPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(p0PPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(r0PPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(gensPPF, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(q0PPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        modParamP.add(aPPF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(pqDABRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
        plotTypeP.add(rqDBCRB, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(VvstRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(DvstRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
        plotTypeP.add(pqrvstRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        plotTypeBG.add(pqDABRB);
        plotTypeBG.add(rqDBCRB);
        plotTypeBG.add(DvstRB);
        plotTypeBG.add(VvstRB);
        plotTypeBG.add(pqrvstRB);
        this.add(modelTypeP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        modelTypeP.add(bellRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        modelTypeP.add(andersonRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
        modelTypeP.add(monoRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 5), 0, 0));
        modelTypeP.add(polyRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 5, 5), 0, 0));
        this.registerChildren(this);
        mpBG.add(monoRB);
        mpBG.add(polyRB);
        baBG.add(bellRB);
        baBG.add(andersonRB);
    }


    void andersonRB_stateChanged(ChangeEvent e) {
        polyRB.setEnabled(!andersonRB.isSelected());
        mPPF.setEnabled(andersonRB.isSelected());
        if (!polyRB.isEnabled()) monoRB.setSelected(true);
    }

    void balanceAlphaBetaEpsilon(int index) {
        if (aPPF.getDouble() + bPPF.getDouble() + ePPF.getDouble() > 1.0) {
            double[] values = new double[]{aPPF.getDouble(), bPPF.getDouble(), ePPF.getDouble()};
            values = Routines.balanceArray(values, 1.0, index);
            aPPF.setCurrentValue(values[0]);
            bPPF.setCurrentValue(values[1]);
            ePPF.setCurrentValue(values[2]);
        }
    }

    void aPPF_parameterFieldChanged(ParameterFieldEvent e) {
        balanceAlphaBetaEpsilon(0);
    }

    void bPPF_parameterFieldChanged(ParameterFieldEvent e) {
        balanceAlphaBetaEpsilon(1);
    }

    void ePPF_parameterFieldChanged(ParameterFieldEvent e) {
        balanceAlphaBetaEpsilon(2);
    }
}
