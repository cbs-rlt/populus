/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.fdsess;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.SpecialLineBorder;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;

public class FDSESSPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -1770251003333213328L;
    private TitledBorder titledBorder2;
    private TitledBorder titledBorder3;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final ButtonGroup invaderBG = new ButtonGroup();
    private final JPanel payoffMatrixP = new JPanel();
    private final JPanel stratToViewP = new JPanel();
    private final PopulusParameterField gensPPF = new PopulusParameterField();
    private final JPanel e1e3P = new JPanel();
    private final JPanel e2e4P = new JPanel();
    private final JPanel pmLP = new JPanel();
    private final JLabel stratBL = new JLabel("Strategy B");
    private final JLabel stratAL = new JLabel("Strategy A");
    private final PopulusParameterField e3PPF = new PopulusParameterField();
    private final PopulusParameterField e1PPF = new PopulusParameterField();
    private final PopulusParameterField e4PPF = new PopulusParameterField();
    private final PopulusParameterField e2PPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final JLabel AL = new JLabel("A");
    private final JLabel BL = new JLabel("B");
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private final GridBagLayout gridBagLayout5 = new GridBagLayout();
    private final JPanel mixedStratsP = new JPanel();
    private final GridBagLayout gridBagLayout6 = new GridBagLayout();
    private final GridBagLayout gridBagLayout7 = new GridBagLayout();
    private final GridBagLayout gridBagLayout8 = new GridBagLayout();
    private final GridBagLayout gridBagLayout9 = new GridBagLayout();
    private final JLabel pBL = new JLabel("%B");
    private final JPanel msLP = new JPanel();
    private final JLabel pAL = new JLabel("%A");
    private final PopulusParameterField e6PPF = new PopulusParameterField();
    private final JPanel e6e8P = new JPanel();
    private final PopulusParameterField e8PPF = new PopulusParameterField();
    private final JLabel stratCL = new JLabel("Strategy C");
    private final JPanel e5e7P = new JPanel();
    private final PopulusParameterField e5PPF = new PopulusParameterField();
    private final JLabel stratDL = new JLabel("Strategy D");
    private final PopulusParameterField e7PPF = new PopulusParameterField();
    private final JRadioButton RDRB = new JRadioButton();
    private final JRadioButton RCRB = new JRadioButton();
    private final JRadioButton RBRB = new JRadioButton();
    private final JRadioButton RARB = new JRadioButton();
    private final JRadioButton IDRB = new JRadioButton();
    private final JRadioButton ICRB = new JRadioButton();
    private final JRadioButton IBRB = new JRadioButton();
    private final JRadioButton IARB = new JRadioButton();
    private final GridBagLayout gridBagLayout10 = new GridBagLayout();
    private final JLabel invaderL = new JLabel("Invader:");
    private final JLabel residentL = new JLabel("Resident:");
    private TitledBorder titledBorder1;
    private final ButtonGroup residentBG = new ButtonGroup();
    private final PopulusParameterField piPPF = new PopulusParameterField();
    private final JPanel payoffFillerP = new JPanel();
    private final JPanel mixedFillerP = new JPanel();

    @Override
    public BasicPlot getPlotParamInfo() {
        double[] e = new double[]{e1PPF.getDouble(), e2PPF.getDouble(), e3PPF.getDouble(), e4PPF.getDouble(),
                e5PPF.getDouble(), e6PPF.getDouble(), e7PPF.getDouble(), e8PPF.getDouble()};
        int i, r;

        if (IARB.isSelected())
            i = FDSESSParamInfo.A;
        else if (IBRB.isSelected())
            i = FDSESSParamInfo.B;
        else if (ICRB.isSelected())
            i = FDSESSParamInfo.C;
        else
            i = FDSESSParamInfo.D;

        if (RARB.isSelected())
            r = FDSESSParamInfo.A;
        else if (RBRB.isSelected())
            r = FDSESSParamInfo.B;
        else if (RCRB.isSelected())
            r = FDSESSParamInfo.C;
        else
            r = FDSESSParamInfo.D;

        return new FDSESSParamInfo(e, i, r, piPPF.getDouble(), gensPPF.getInt());
    }

    public FDSESSPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder2 = new TitledBorder("Strategy to View");
        titledBorder3 = new TitledBorder("Mixed Strategies");
        titledBorder1 = new TitledBorder("Payoff Matrix");
        gensPPF.setParameterName("Generations");
        gensPPF.setMaxValue(5000.0);
        gensPPF.setIncrementAmount(20.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setDefaultValue(200.0);
        gensPPF.setCurrentValue(200.0);
        this.setLayout(gridBagLayout1);
        payoffMatrixP.setLayout(gridBagLayout2);
        pmLP.setLayout(gridBagLayout3);
        e3PPF.setIncrementAmount(0.5);
        e3PPF.setMaxValue(1000.0);
        e3PPF.setMinValue(-10.0);
        e3PPF.setParameterName("<i>E<sub>3</>");
        e1PPF.setMaxValue(1000.0);
        e1PPF.setMinValue(-10.0);
        e1PPF.setCurrentValue(-2.0);
        e1PPF.setDefaultValue(-2.0);
        e1PPF.setIncrementAmount(0.5);
        e1PPF.setParameterName("<i>E<sub>1</>");
        e4PPF.setCurrentValue(1.0);
        e4PPF.setDefaultValue(1.0);
        e4PPF.setIncrementAmount(0.5);
        e4PPF.setMaxValue(1000.0);
        e4PPF.setMinValue(-10.0);
        e4PPF.setParameterName("<i>E<sub>4</>");
        e2PPF.setCurrentValue(2.0);
        e2PPF.setDefaultValue(2.0);
        e2PPF.setIncrementAmount(0.5);
        e2PPF.setMaxValue(1000.0);
        e2PPF.setMinValue(-10.0);
        e2PPF.setParameterName("<i>E<sub>2</>");
        e1e3P.setLayout(gridBagLayout4);
        e2e4P.setLayout(gridBagLayout5);
        pmLP.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        e2e4P.setBorder(new SpecialLineBorder(Color.black, false, true, true, true));
        e1e3P.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        AL.setBorder(new SpecialLineBorder(Color.black, true, true, true, false));
        AL.setHorizontalAlignment(SwingConstants.CENTER);
        AL.setHorizontalTextPosition(SwingConstants.CENTER);
        BL.setBorder(BorderFactory.createLineBorder(Color.black));
        BL.setHorizontalAlignment(SwingConstants.CENTER);
        BL.setHorizontalTextPosition(SwingConstants.CENTER);
        mixedStratsP.setLayout(gridBagLayout9);
        pBL.setBorder(BorderFactory.createLineBorder(Color.black));
        pBL.setHorizontalAlignment(SwingConstants.CENTER);
        pBL.setHorizontalTextPosition(SwingConstants.CENTER);
        msLP.setLayout(gridBagLayout8);
        msLP.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        pAL.setBorder(new SpecialLineBorder(Color.black, true, true, true, false));
        pAL.setHorizontalAlignment(SwingConstants.CENTER);
        pAL.setHorizontalTextPosition(SwingConstants.CENTER);
        e6PPF.setCurrentValue(0.667);
        e6PPF.setDefaultValue(0.667);
        e6PPF.setIncrementAmount(0.05);
        e6PPF.setMaxValue(1.0);
        e6PPF.setParameterName("<i>E<sub>6</>");
        e6e8P.setLayout(gridBagLayout6);
        e6e8P.setBorder(new SpecialLineBorder(Color.black, false, true, true, true));
        e8PPF.setCurrentValue(0.3);
        e8PPF.setDefaultValue(0.3);
        e8PPF.setIncrementAmount(0.05);
        e8PPF.setMaxValue(1.0);
        e8PPF.setParameterName("<i>E<sub>8</>");
        e5e7P.setLayout(gridBagLayout7);
        e5e7P.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        e5PPF.setCurrentValue(0.333);
        e5PPF.setDefaultValue(0.333);
        e5PPF.setIncrementAmount(0.05);
        e5PPF.setMaxValue(1.0);
        e5PPF.setParameterName("<i>E<sub>5</>");
        e7PPF.setCurrentValue(0.7);
        e7PPF.setDefaultValue(0.7);
        e7PPF.setIncrementAmount(0.05);
        e7PPF.setMaxValue(1.0);
        e7PPF.setParameterName("<i>E<sub>7</>");
        RDRB.setText("D");
        RDRB.addChangeListener(e -> RDRB_stateChanged(e));
        RCRB.setText("C");
        RCRB.addChangeListener(e -> RCRB_stateChanged(e));
        RBRB.setSelected(true);
        RBRB.setText("B");
        RBRB.addChangeListener(e -> RBRB_stateChanged(e));
        RARB.setEnabled(false);
        RARB.setText("A");
        RARB.addChangeListener(e -> RARB_stateChanged(e));
        IDRB.setText("D");
        IDRB.addChangeListener(e -> IDRB_stateChanged(e));
        ICRB.setText("C");
        ICRB.addChangeListener(e -> ICRB_stateChanged(e));
        IBRB.setEnabled(false);
        IBRB.setText("B");
        IBRB.addChangeListener(e -> IBRB_stateChanged(e));
        IARB.setSelected(true);
        IARB.setText("A");
        IARB.addChangeListener(e -> IARB_stateChanged(e));
        stratToViewP.setLayout(gridBagLayout10);
        stratToViewP.setBorder(titledBorder2);
        mixedStratsP.setBorder(titledBorder3);
        payoffMatrixP.setBorder(titledBorder1);
        piPPF.setParameterName("<i>p<sub>invader</>");
        piPPF.setMaxValue(1.0);
        piPPF.setIncrementAmount(0.05);
        piPPF.setDefaultValue(0.01);
        piPPF.setCurrentValue(0.01);
        payoffFillerP.setBorder(new SpecialLineBorder(Color.black, false, true, false, false));
        mixedFillerP.setBorder(new SpecialLineBorder(Color.black, false, true, false, false));
        invaderL.setForeground(Color.red);
        residentL.setForeground(Color.blue);
        this.add(payoffMatrixP, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(stratToViewP, new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
        this.add(gensPPF, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        payoffMatrixP.add(pmLP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        pmLP.add(stratAL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        pmLP.add(stratBL, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        payoffMatrixP.add(e1e3P, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        e1e3P.add(e1PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        e1e3P.add(e3PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        payoffMatrixP.add(e2e4P, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        e2e4P.add(e2PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        e2e4P.add(e4PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        payoffMatrixP.add(AL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        payoffMatrixP.add(BL, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        payoffMatrixP.add(payoffFillerP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        msLP.add(stratCL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        msLP.add(stratDL, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(e5e7P, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(msLP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        e5e7P.add(e5PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        e5e7P.add(e7PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(e6e8P, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        e6e8P.add(e6PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        e6e8P.add(e8PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(pAL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(pBL, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mixedStratsP.add(mixedFillerP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(piPPF, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
        this.add(mixedStratsP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 0, 0, 0), 0, 0));
        stratToViewP.add(IARB, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(IBRB, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(ICRB, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(IDRB, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(RARB, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(RBRB, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(RCRB, new GridBagConstraints(3, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(RDRB, new GridBagConstraints(4, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(invaderL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        stratToViewP.add(residentL, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        invaderBG.add(IARB);
        invaderBG.add(IBRB);
        invaderBG.add(ICRB);
        invaderBG.add(IDRB);
        residentBG.add(RARB);
        residentBG.add(RBRB);
        residentBG.add(RCRB);
        residentBG.add(RDRB);
        this.registerChildren(this);
    }

    void IARB_stateChanged(ChangeEvent e) {
        RARB.setEnabled(!IARB.isSelected());
    }

    void IBRB_stateChanged(ChangeEvent e) {
        RBRB.setEnabled(!IBRB.isSelected());
    }

    void ICRB_stateChanged(ChangeEvent e) {
        RCRB.setEnabled(!ICRB.isSelected());
    }

    void IDRB_stateChanged(ChangeEvent e) {
        RDRB.setEnabled(!IDRB.isSelected());
    }

    void RARB_stateChanged(ChangeEvent e) {
        IARB.setEnabled(!RARB.isSelected());
    }

    void RBRB_stateChanged(ChangeEvent e) {
        IBRB.setEnabled(!RBRB.isSelected());
    }

    void RCRB_stateChanged(ChangeEvent e) {
        ICRB.setEnabled(!RCRB.isSelected());
    }

    void RDRB_stateChanged(ChangeEvent e) {
        IDRB.setEnabled(!RDRB.isSelected());
    }
}
