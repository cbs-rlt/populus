/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.fdsdm;

import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.SpecialLineBorder;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FDSDMPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -3378363432434260696L;
    private TitledBorder titledBorder2;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final ButtonGroup plotTypeBG = new ButtonGroup();
    private final JLabel AAL = new JLabel("A");
    private final PopulusParameterField w8PPF = new PopulusParameterField();
    private final JPanel AAP = new JPanel();
    private final JLabel AaVL = new JLabel("Strategy A");
    private final GridBagLayout gridBagLayout5 = new GridBagLayout();
    private final PopulusParameterField w7PPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout4 = new GridBagLayout();
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final JPanel AaP = new JPanel();
    private final JPanel labelP = new JPanel();
    private final JPanel matrixP = new JPanel();
    private final PopulusParameterField w5PPF = new PopulusParameterField();
    private final JLabel AaL = new JLabel("B");
    private final JLabel aaVL = new JLabel("Strategy B");
    private final PopulusParameterField w4PPF = new PopulusParameterField();
    private final JLabel aaL = new JLabel("B");
    private final GridBagLayout gridBagLayout6 = new GridBagLayout();
    private final PopulusParameterField w9PPF = new PopulusParameterField();
    private final JPanel aaP = new JPanel();
    private final PopulusParameterField w6PPF = new PopulusParameterField();
    private final PopulusParameterField w1PPF = new PopulusParameterField();
    private final PopulusParameterField w2PPF = new PopulusParameterField();
    private final PopulusParameterField w3PPF = new PopulusParameterField();
    private final JLabel AAVL = new JLabel("Strategy B");
    private final PopulusParameterField gensPPF = new PopulusParameterField();
    private final PopulusParameterField freqPPF = new PopulusParameterField();
    private final JRadioButton pvstRB = new StyledRadioButton();
    private final JRadioButton wbarvstRB = new StyledRadioButton();
    private final JPanel fillerP = new JPanel();

    @Override
    public BasicPlot getPlotParamInfo() {
        double[] w = new double[]{
                w1PPF.getDouble(), w2PPF.getDouble(), w3PPF.getDouble(),
                w4PPF.getDouble(), w5PPF.getDouble(), w6PPF.getDouble(),
                w7PPF.getDouble(), w8PPF.getDouble(), w9PPF.getDouble()};
        int plotType = 0;
        if (wbarvstRB.isSelected())
            plotType = FDSDMParamInfo.WBARvsT;

        return new FDSDMParamInfo(w, freqPPF.getDouble(), gensPPF.getInt(), plotType);
    }

    public FDSDMPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        titledBorder2 = new TitledBorder("Individual Coefficients");
        w4PPF.setParameterName("<i>w<sub>4</>");
        w4PPF.setIncrementAmount(0.1);
        w4PPF.setDefaultValue(1.0);
        w4PPF.setCurrentValue(1.0);
        w4PPF.setMaxValue(1000.0);
        AaL.setHorizontalTextPosition(SwingConstants.CENTER);
        AaL.setText("Aa");
        AaL.setHorizontalAlignment(SwingConstants.CENTER);
        AaL.setBorder(BorderFactory.createLineBorder(Color.black));
        w5PPF.setParameterName("<i>w<sub>5</>");
        w5PPF.setMaxValue(1000.0);
        w5PPF.setIncrementAmount(0.1);
        w5PPF.setDefaultValue(1.0);
        w5PPF.setCurrentValue(1.0);
        matrixP.setLayout(gridBagLayout2);
        labelP.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        labelP.setLayout(gridBagLayout3);
        AaP.setBorder(new SpecialLineBorder(Color.black, false, true, true, true));
        AaP.setLayout(gridBagLayout5);
        w7PPF.setParameterName("<i>w<sub>7</>");
        w7PPF.setMaxValue(1000.0);
        w7PPF.setIncrementAmount(0.1);
        AAP.setBorder(new SpecialLineBorder(Color.black, false, true, true, false));
        AAP.setLayout(gridBagLayout4);
        w8PPF.setParameterName("<i>w<sub>8</>");
        w8PPF.setMaxValue(1000.0);
        w8PPF.setIncrementAmount(0.1);
        AAL.setHorizontalTextPosition(SwingConstants.CENTER);
        AAL.setText("AA");
        AAL.setHorizontalAlignment(SwingConstants.CENTER);
        AAL.setBorder(new SpecialLineBorder(Color.black, true, true, true, false));
        this.setLayout(gridBagLayout1);
        titledBorder2.setTitle("Model Parameters");
        aaL.setBorder(new SpecialLineBorder(Color.black, true, true, false, true));
        aaL.setHorizontalAlignment(SwingConstants.CENTER);
        aaL.setHorizontalTextPosition(SwingConstants.CENTER);
        aaL.setText("aa");
        w9PPF.setCurrentValue(1.0);
        w9PPF.setDefaultValue(1.0);
        w9PPF.setIncrementAmount(0.1);
        w9PPF.setMaxValue(1000.0);
        w9PPF.setParameterName("<i>w<sub>9</>");
        aaP.setLayout(gridBagLayout6);
        aaP.setBorder(new SpecialLineBorder(Color.black, false, true, false, true));
        w6PPF.setParameterName("<i>w<sub>6</>");
        w6PPF.setMaxValue(1000.0);
        w6PPF.setIncrementAmount(0.1);
        w6PPF.setDefaultValue(1.0);
        w6PPF.setCurrentValue(1.0);
        w1PPF.setCurrentValue(1.0);
        w1PPF.setDefaultValue(1.0);
        w1PPF.setIncrementAmount(0.1);
        w1PPF.setMaxValue(1000.0);
        w1PPF.setParameterName("<i>w<sub>1</>");
        w2PPF.setIncrementAmount(0.1);
        w2PPF.setMaxValue(1000.0);
        w2PPF.setParameterName("<i>w<sub>2</>");
        w3PPF.setIncrementAmount(0.1);
        w3PPF.setMaxValue(1000.0);
        w3PPF.setParameterName("<i>w<sub>3</>");
        AAVL.setToolTipText("");
        AAVL.setText("AA");
        AaVL.setText("Aa");
        aaVL.setText("aa");
        gensPPF.setCurrentValue(200.0);
        gensPPF.setDefaultValue(200.0);
        gensPPF.setIncrementAmount(20.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setMaxValue(5000.0);
        gensPPF.setParameterName("Generations");
        freqPPF.setCurrentValue(0.01);
        freqPPF.setDefaultValue(0.01);
        freqPPF.setIncrementAmount(0.05);
        freqPPF.setMaxValue(1.0);
        freqPPF.setParameterName("Starting Frequency");
        pvstRB.setSelected(true);
        pvstRB.setText("<i>p</> vs <i>t</>");
        wbarvstRB.setText("<i>w\u0305</i> vs <i>t</i>");
        fillerP.setBorder(new SpecialLineBorder(Color.black, false, true, false, false));
        labelP.add(AaVL, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        labelP.add(aaVL, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        labelP.add(AAVL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        matrixP.add(AAP, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        matrixP.add(labelP, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        AAP.add(w4PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        AAP.add(w7PPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        AAP.add(w1PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        matrixP.add(AaP, new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        AaP.add(w8PPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        AaP.add(w2PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        AaP.add(w5PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        matrixP.add(AAL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        matrixP.add(AaL, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        matrixP.add(aaL, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        matrixP.add(aaP, new GridBagConstraints(3, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        aaP.add(w6PPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        aaP.add(w9PPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        aaP.add(w3PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        matrixP.add(fillerP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(matrixP, new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
        this.add(gensPPF, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 10), 0, 0));
        this.add(freqPPF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 10), 0, 0));
        this.add(pvstRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 0, 0), 0, 0));
        this.add(wbarvstRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 10, 5, 0), 0, 0));
        plotTypeBG.add(pvstRB);
        plotTypeBG.add(wbarvstRB);
        this.registerChildren(this);
    }
}
