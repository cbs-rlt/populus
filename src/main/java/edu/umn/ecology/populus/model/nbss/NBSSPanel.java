/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.edwin.ModelPanel;
import edu.umn.ecology.populus.plot.ParamInfo;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ItemEvent;


public class NBSSPanel extends ModelPanel {
    /**
     *
     */
    private static final long serialVersionUID = 6744116625324757882L;
    public static final int kPeriodic = 0;
    public static final int kAbsorbing = 1;
    public static final int kReflexive = 2;

    public static final int kN = 0;
    public static final int kP = 1;
    public static final int kNdivP = 2;

    final JPanel bordertypeP = new JPanel();
    final JRadioButton reflexiveRB = new JRadioButton();
    final JRadioButton periodicRB = new JRadioButton();
    final JRadioButton absorbingRB = new JRadioButton();
    final SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
    TitledBorder titledBorder1;
    Border border1;
    TitledBorder titledBorder2;
    Border border2;
    TitledBorder titledBorder3;
    Border border3;
    TitledBorder titledBorder4;
    Border border4;
    final JRadioButton nRB = new StyledRadioButton();
    final JRadioButton ndivpRB = new StyledRadioButton();
    final JRadioButton npvstRB = new StyledRadioButton();
    final JRadioButton pRB = new StyledRadioButton();
    final JPanel outputtypeP = new JPanel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JPanel specsP = new JPanel();
    final JRadioButton startmRB = new JRadioButton();
    final JRadioButton starteRB = new JRadioButton();
    final JCheckBox displayeachgenCB = new JCheckBox();
    final JCheckBox intpopsCB = new JCheckBox();
    final ButtonGroup outBG = new ButtonGroup();
    final ButtonGroup borderBG = new ButtonGroup();
    final ButtonGroup startBG = new ButtonGroup();
    final ButtonGroup cartBG = new ButtonGroup();
    final JPanel initcondP = new JPanel();
    final PopulusParameterField p0PPF = new PopulusParameterField();
    final PopulusParameterField n0PPF = new PopulusParameterField();
    final JPanel paramP = new JPanel();
    final PopulusParameterField lambdaPPF = new PopulusParameterField();
    final PopulusParameterField areadisPPF = new PopulusParameterField();
    final PopulusParameterField munPPF = new PopulusParameterField();
    final PopulusParameterField mupPPF = new PopulusParameterField();
    final PopulusParameterField qPPF = new PopulusParameterField();
    final PopulusParameterField nPPF = new PopulusParameterField();
    final PopulusParameterField runIntervalPPF = new PopulusParameterField();
    final GridBagLayout gridBagLayout4 = new GridBagLayout();
    final SimpleVFlowLayout simpleVFlowLayout4 = new SimpleVFlowLayout();
    final GridBagLayout gridBagLayout2 = new GridBagLayout();
    final JRadioButton avgB = new JRadioButton();
    final JRadioButton totB = new JRadioButton();
    final GridBagLayout gridBagLayout3 = new GridBagLayout();

    NBSSCellParamInfo pi;
    boolean switchTrigger = false;

    public NBSSPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder1 = new TitledBorder(border1, "Output Type");
        border2 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder2 = new TitledBorder(border1, "Border Method");
        border3 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder3 = new TitledBorder(border1, "Specifications");
        border4 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
        titledBorder4 = new TitledBorder(border1, "Initial Conditions");
        absorbingRB.setToolTipText("Acts like borders aren\'t there");
        absorbingRB.setSelected(true);
        reflexiveRB.setToolTipText("Populations bounce into closest tile");
        reflexiveRB.setText("Reflexive");
        periodicRB.setToolTipText("Populations wrap to other side");
        periodicRB.setText("Periodic");
        absorbingRB.setText("Absorbing");
        initcondP.setLayout(gridBagLayout2);
        p0PPF.setCurrentValue(10.0);
        p0PPF.setDefaultValue(10.0);
        p0PPF.setMaxValue(1000.0);
        p0PPF.setParameterName("<i>P</i><sub>0</sub>");
        p0PPF.setHelpText("The initial parasitoid population size in the starting patch.");
        n0PPF.setCurrentValue(25.0);
        n0PPF.setDefaultValue(25.0);
        n0PPF.setMaxValue(1000.0);
        n0PPF.setParameterName("<i>N</i><sub>0</sub>");
        n0PPF.setHelpText("The initial host population size in the starting patch.");
        initcondP.setBorder(titledBorder4);
        paramP.setLayout(simpleVFlowLayout4);
        areadisPPF.setCurrentValue(0.068);
        areadisPPF.setDefaultValue(0.068);
        areadisPPF.setIncrementAmount(0.1);
        areadisPPF.setMaxValue(1.0);
        areadisPPF.setParameterName("<i>a</i>");
        areadisPPF.setHelpText("The search efficiency of the parasitoid (the Nicholson-Bailey \"area of discovery\".)");
        lambdaPPF.setCurrentValue(2.0);
        lambdaPPF.setDefaultValue(2.0);
        lambdaPPF.setMaxValue(1000.0);
        lambdaPPF.setParameterName("\u03bb");
        lambdaPPF.setHelpText("The host growth rate.");
        munPPF.setCurrentValue(0.4);
        munPPF.setDefaultValue(0.4);
        munPPF.setIncrementAmount(0.1);
        munPPF.setMaxValue(1.0);
        munPPF.setParameterName("\u03BC<i>N </i>");
        munPPF.setHelpText("The host migration fraction leaving the patch in each generation.");
        mupPPF.setCurrentValue(0.89);
        mupPPF.setDefaultValue(0.89);
        mupPPF.setIncrementAmount(0.1);
        mupPPF.setMaxValue(1.0);
        mupPPF.setParameterName("\u03BC<i>P </i>");
        mupPPF.setHelpText("The parasitoid migration fraction leaving the patch in each generation.");
        qPPF.setCurrentValue(1.0);
        qPPF.setDefaultValue(1.0);
        qPPF.setIncrementAmount(0.1);
        qPPF.setMaxValue(10000.0);
        qPPF.setParameterName("<i>q</i>");
        qPPF.setHelpText("q sets the number of parasitoids emerging from each parasitized host individual.");
        nPPF.setCurrentValue(45.0);
        nPPF.setDefaultValue(30.0);
        nPPF.setIncrementAmount(5.0);
        nPPF.setMaxValue(200.0);
        nPPF.setMinValue(1.0);
        nPPF.setParameterName("n");
        nPPF.setHelpText("n sets the size of the spatial array.");
        runIntervalPPF.setCurrentValue(200.0);
        runIntervalPPF.setDefaultValue(200.0);
        runIntervalPPF.setIncrementAmount(500.0);
        runIntervalPPF.setMaxValue(10000.0);
        runIntervalPPF.setParameterName("Run interval between steps");
        nRB.setSelected(true);
        starteRB.setSelected(true);
        avgB.setSelected(true);
        avgB.setEnabled(true);
        avgB.setText("Average");
        avgB.addItemListener(e -> avgB_itemStateChanged(e));
        totB.setEnabled(true);
        totB.setText("Total");
        npvstRB.addChangeListener(e -> npvstRB_stateChanged(e));
        cartBG.add(avgB);
        cartBG.add(totB);
        borderBG.add(reflexiveRB);
        borderBG.add(periodicRB);
        borderBG.add(absorbingRB);
        bordertypeP.setLayout(simpleVFlowLayout1);
        bordertypeP.setBorder(titledBorder2);
        nRB.setText("<i>N</i>");
        ndivpRB.setText("<i>N</i>/<i>P</i>");
        npvstRB.setText("<i>N</i>, <i>P</i> vs <i>t</i>");
        pRB.setText("<i>P</i>");
        outBG.add(ndivpRB);
        outBG.add(nRB);
        outBG.add(pRB);
        outBG.add(npvstRB);
        outputtypeP.setBorder(titledBorder1);
        outputtypeP.setLayout(gridBagLayout3);
        this.setLayout(gridBagLayout1);
        startmRB.setText("Start in Middle");
        starteRB.setText("Start on Edge");
        startBG.add(startmRB);
        startBG.add(starteRB);
        specsP.setLayout(gridBagLayout4);
        displayeachgenCB.setText("Display Each Generation");
        intpopsCB.setText("Use Integer Populations");
        specsP.setBorder(titledBorder3);
        this.add(outputtypeP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        outputtypeP.add(ndivpRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
        outputtypeP.add(nRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        outputtypeP.add(pRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        outputtypeP.add(npvstRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
        outputtypeP.add(avgB, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 5), 0, 0));
        outputtypeP.add(totB, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 20, 0, 5), 0, 0));
        this.add(bordertypeP, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        bordertypeP.add(absorbingRB, null);
        bordertypeP.add(periodicRB, null);
        bordertypeP.add(reflexiveRB, null);
        this.add(specsP, new GridBagConstraints(0, 2, 3, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        specsP.add(starteRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        specsP.add(startmRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
        specsP.add(intpopsCB, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        specsP.add(displayeachgenCB, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        specsP.add(runIntervalPPF, new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
        this.add(initcondP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 20, 0));
        initcondP.add(n0PPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        initcondP.add(p0PPF, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        this.add(paramP, new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        paramP.add(lambdaPPF, null);
        paramP.add(areadisPPF, null);
        paramP.add(munPPF, null);
        paramP.add(mupPPF, null);
        paramP.add(qPPF, null);
        paramP.add(nPPF, null);
    }

    @Override
    public ParamInfo getParamInfo() {
        int border;
        int type;
        if (absorbingRB.isSelected())
            border = kAbsorbing;
        else if (reflexiveRB.isSelected())
            border = kReflexive;
        else
            border = kPeriodic;

        if (nRB.isSelected())
            type = kN;
        else if (pRB.isSelected())
            type = kP;
        else
            type = kNdivP;

        if (!switchTrigger)
            pi = new NBSSCellParamInfo(n0PPF.getInt(), p0PPF.getInt(), lambdaPPF.getDouble(),
                    areadisPPF.getDouble(), munPPF.getDouble(), mupPPF.getDouble(), qPPF.getDouble(), nPPF.getInt(),
                    border, type, intpopsCB.isSelected(), displayeachgenCB.isSelected(), starteRB.isSelected(),
                    runIntervalPPF.getInt());

        if (!npvstRB.isSelected()) {
            switchTrigger = false;
            return pi;
        } else {
            NBSSGraphParamInfo gpi = new NBSSGraphParamInfo(pi, avgB.isSelected(), switchTrigger);
            switchTrigger = false;
            return gpi;
        }
    }

    void npvstRB_stateChanged(ChangeEvent e) {
        //boolean b = npvstRB.isSelected();
        //avgB.setEnabled(b);
        //totB.setEnabled(b);
    }

	void switchOutputType(){
		boolean isfreq = npvstRB.isSelected();
		switchTrigger = true;
		npvstRB.setSelected(!isfreq);
		switch(pi.getType()){
		case kN:			nRB.setSelected(isfreq);			break;
		case kP:			pRB.setSelected(isfreq);			break;
		case kNdivP:	ndivpRB.setSelected(isfreq);	break;
		}
	}

    void avgB_itemStateChanged(ItemEvent e) {

    }
}
