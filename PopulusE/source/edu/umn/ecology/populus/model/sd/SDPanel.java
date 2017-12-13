/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.ModelPanel;
import javax.swing.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.event.*;


public class SDPanel extends ModelPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 16352175806661664L;
	public static final int kPD = 0;
	public static final int kHD = 1;

	public static final int kRandom = 0;
	public static final int kOneCD = 1;
	public static final int kOneDH = 2;

	public static final int kPeriodic = 0;
	public static final int kAbsorbing = 1;
	public static final int kReflexive = 2;

	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JRadioButton periodicRB = new JRadioButton();
	JRadioButton reflexiveRB = new JRadioButton();
	JRadioButton absorbingRB = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	JPanel bordertypeP = new JPanel();
	TitledBorder titledBorder1;
	Border border1;
	TitledBorder titledBorder2;
	Border border2;
	TitledBorder titledBorder3;
	Border border3;
	TitledBorder titledBorder4;
	Border border4;
	TitledBorder titledBorder5;
	Border border5;
	JPanel gametypeP = new JPanel();
	JRadioButton hdRB = new JRadioButton();
	JRadioButton pdRB = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
	JPanel outputP = new JPanel();
	JRadioButton freqRB = new JRadioButton();
	JRadioButton latticeRB = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
	JPanel layoutP = new JPanel();
	JRadioButton onedRB = new JRadioButton();
	JRadioButton onecRB = new JRadioButton();
	JRadioButton randomRB = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout4 = new SimpleVFlowLayout();
	PopulusParameterField patchsizePPF = new PopulusParameterField();
	JPanel miscP = new JPanel();
	JRadioButton middleRB = new JRadioButton();
	JRadioButton edgeRB = new JRadioButton();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	JRadioButton fourRB = new JRadioButton();
	JRadioButton eightRB = new JRadioButton();
	JCheckBox eachGenCB = new JCheckBox();
	JCheckBox selfiCB = new JCheckBox();
	PopulusParameterField runIntervalPPF = new PopulusParameterField();
	JPanel pdparamP = new JPanel();
	PopulusParameterField initfreqPPF = new PopulusParameterField();
	PopulusParameterField dpayoffddPPF = new PopulusParameterField();
	PopulusParameterField dpayoffcdPPF = new PopulusParameterField();
	PopulusParameterField cpayoffcdPPF = new PopulusParameterField();
	SimpleVFlowLayout simpleVFlowLayout5 = new SimpleVFlowLayout();
	PopulusParameterField cpayoffccPPF = new PopulusParameterField();
	PopulusParameterField laticesizePPF = new PopulusParameterField();
	ButtonGroup initBG = new ButtonGroup();
	ButtonGroup outputBG = new ButtonGroup();
	ButtonGroup gameBG = new ButtonGroup();
	ButtonGroup wstartBG = new ButtonGroup();
	ButtonGroup numneighborsBG = new ButtonGroup();
	ButtonGroup borderBG = new ButtonGroup();
	JPanel dhparamP = new JPanel();
	JLabel hhhvL = new JLabel();
	JLabel hhdvL = new JLabel();
	JLabel dhdvL = new JLabel();
	JLabel dddvL = new JLabel();
	JLabel hhhL = new JLabel();
	JLabel hhdL = new JLabel();
	JLabel dhdL = new JLabel();
	JLabel dddL = new JLabel();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	PopulusParameterField winnerPPF = new PopulusParameterField();
	PopulusParameterField injuryPPF = new PopulusParameterField();

	SDCellParamInfo pi;
	boolean switchTrigger=false;

	public SDPanel() {
		try  {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
		titledBorder1 = new TitledBorder(border1,"Border Type");
		border2 = BorderFactory.createLineBorder(SystemColor.controlText,1);
		titledBorder2 = new TitledBorder(border1,"Game Type");
		border3 = BorderFactory.createLineBorder(SystemColor.controlText,1);
		titledBorder3 = new TitledBorder(border1,"Output Type");
		border4 = BorderFactory.createLineBorder(SystemColor.controlText,1);
		titledBorder4 = new TitledBorder(border1,"Initial Layout");
		border5 = BorderFactory.createLineBorder(SystemColor.controlText,1);
		titledBorder5 = new TitledBorder(border1,"Specifications");
		bordertypeP.setLayout(simpleVFlowLayout1);
		absorbingRB.setText("Absorbing");
		absorbingRB.setSelected(true);
		absorbingRB.setToolTipText("Acts like borders aren\'t there");
		reflexiveRB.setText("Reflexive");
		reflexiveRB.setToolTipText("Populations bounce into closest tile");
		periodicRB.setText("Periodic");
		periodicRB.setToolTipText("Populations wrap to other side");
		pdRB.setSelected(true);
		pdRB.addChangeListener(new javax.swing.event.ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				pdRB_stateChanged(e);
			}
		});
		latticeRB.setSelected(true);
		onedRB.setSelected(true);
		middleRB.setSelected(true);
		eightRB.setSelected(true);
		runIntervalPPF.setCurrentValue(10.0);
		runIntervalPPF.setDefaultValue(10.0);
		runIntervalPPF.setMaxValue(100000.0);
		runIntervalPPF.setParameterName("Run Interval");
		initfreqPPF.setCurrentValue(0.9);
		initfreqPPF.setEnabled(false);
		initfreqPPF.setIncrementAmount(0.1);
		initfreqPPF.setMaxValue(1.0);
		randomRB.addChangeListener(new javax.swing.event.ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				randomRB_stateChanged(e);
			}
		});
		hhhL.setToolTipText("Hawk payoff in a Hawk-Hawk Encounter");
		hhhL.setText("(V-C)/2 = ");
		hhdL.setToolTipText("Hawk payoff in a Dove-Hawk Encounter");
		hhdL.setText("V = ");
		dhdL.setToolTipText("Dove payoff in a Dove-Hawk Encounter");
		dhdL.setText("S = ");
		dddL.setToolTipText("Dove payoff in a Dove-Dove Encounter");
		dddL.setText("V/2 = ");
		dhparamP.setLayout(gridBagLayout3);
		injuryPPF.addParameterFieldListener(new edu.umn.ecology.populus.visual.ppfield.ParameterFieldListener() {

			@Override
			public void parameterFieldChanged(ParameterFieldEvent e) {
				injuryPPF_parameterFieldChanged(e);
			}
		});
		injuryPPF.setCurrentValue(1.0);
		injuryPPF.setDefaultValue(1.0);
		injuryPPF.setMaxValue(10000.0);
		injuryPPF.setParameterName("C");
		injuryPPF.setHelpText("Loss of Injury");
		winnerPPF.addParameterFieldListener(new edu.umn.ecology.populus.visual.ppfield.ParameterFieldListener() {

			@Override
			public void parameterFieldChanged(ParameterFieldEvent e) {
				winnerPPF_parameterFieldChanged(e);
			}
		});
		winnerPPF.setCurrentValue(2.0);
		winnerPPF.setDefaultValue(2.0);
		winnerPPF.setMaxValue(10000.0);
		winnerPPF.setParameterName("V");
		winnerPPF.setHelpText("Gain to Winner");
		setDNLabels();
		eachGenCB.setSelected(false);
		selfiCB.setSelected(true);
		cpayoffccPPF.setCurrentValue(1.0);
		cpayoffccPPF.setDefaultValue(1.0);
		cpayoffccPPF.setMaxValue(10000.0);
		cpayoffcdPPF.setMaxValue(10000.0);
		dpayoffcdPPF.setCurrentValue(1.81);
		dpayoffcdPPF.setDefaultValue(1.81);
		dpayoffcdPPF.setMaxValue(10000.0);
		dpayoffddPPF.setCurrentValue(0.0010);
		dpayoffddPPF.setDefaultValue(0.0010);
		dpayoffddPPF.setMaxValue(10000.0);
		patchsizePPF.setCurrentValue(3.0);
		patchsizePPF.setDefaultValue(3.0);
		patchsizePPF.setMaxValue(200.0);
		borderBG.add(absorbingRB);
		borderBG.add(periodicRB);
		borderBG.add(reflexiveRB);
		this.setLayout(gridBagLayout1);
		bordertypeP.setBorder(titledBorder1);
		hdRB.setText("Hawk-Dove");
		pdRB.setText("Prisoner\'s Dilemma");
		gameBG.add(hdRB);
		gameBG.add(pdRB);
		gametypeP.setLayout(simpleVFlowLayout2);
		gametypeP.setBorder(titledBorder2);
		freqRB.setText("Frequencies vs t");
		latticeRB.setText("Lattice");
		outputBG.add(freqRB);
		outputBG.add(latticeRB);
		outputP.setBorder(titledBorder3);
		outputP.setLayout(simpleVFlowLayout3);
		onedRB.setText("One D Patch");
		onecRB.setText("One C Patch");
		randomRB.setText("Random");
		initBG.add(onedRB);
		initBG.add(onecRB);
		initBG.add(randomRB);
		layoutP.setLayout(simpleVFlowLayout4);
		layoutP.setBorder(titledBorder4);
		patchsizePPF.setParameterName("Patch Size (m*m)");
		middleRB.setText("Middle");
		edgeRB.setText("Edge");
		wstartBG.add(middleRB);
		wstartBG.add(edgeRB);
		miscP.setBorder(titledBorder5);
		miscP.setLayout(gridBagLayout2);
		fourRB.setText("4 Neighbors");
		eightRB.setText("8 Neighbors");
		numneighborsBG.add(fourRB);
		numneighborsBG.add(eightRB);
		eachGenCB.setText("Each Gen");
		selfiCB.setText("Interact with Self");
		pdparamP.setLayout(simpleVFlowLayout5);
		laticesizePPF.setCurrentValue(45.0);
		laticesizePPF.setDefaultValue(99.0);
		laticesizePPF.setMaxValue(120.0);
		laticesizePPF.setParameterName("Latice Size (n*n)");
		cpayoffccPPF.setParameterName("R");
		cpayoffccPPF.setHelpText("C Payoff in C-C Encounter");
		cpayoffcdPPF.setParameterName("S");
		cpayoffcdPPF.setHelpText("C Payoff in C-D Encounter");
		dpayoffcdPPF.setParameterName("T");
		dpayoffcdPPF.setHelpText("D Payoff in C-D Encounter");
		dpayoffddPPF.setParameterName("P");
		dpayoffddPPF.setHelpText("D Payoff in D-D Encounter");
		initfreqPPF.setParameterName("Initial Frequency of C");
		this.add(bordertypeP, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		bordertypeP.add(absorbingRB, null);
		bordertypeP.add(periodicRB, null);
		bordertypeP.add(reflexiveRB, null);
		this.add(gametypeP, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		gametypeP.add(pdRB, null);
		gametypeP.add(hdRB, null);
		this.add(outputP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		outputP.add(latticeRB, null);
		outputP.add(freqRB, null);
		this.add(layoutP, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		layoutP.add(randomRB, null);
		layoutP.add(initfreqPPF, null);
		layoutP.add(onecRB, null);
		layoutP.add(onedRB, null);
		layoutP.add(patchsizePPF, null);
		this.add(miscP, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(edgeRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(fourRB, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(eachGenCB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(runIntervalPPF, new GridBagConstraints(0, 3, 2, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(middleRB, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(eightRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		miscP.add(selfiCB, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(pdparamP, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		pdparamP.add(laticesizePPF, null);
		pdparamP.add(cpayoffccPPF, null);
		pdparamP.add(cpayoffcdPPF, null);
		pdparamP.add(dpayoffcdPPF, null);
		pdparamP.add(dpayoffddPPF, null);
		this.add(dhparamP, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(hhhvL, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(winnerPPF, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(injuryPPF, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(dddL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(dhdL, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(hhdL, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(hhhL, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(dddvL, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(dhdvL, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.add(hhdvL, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		dhparamP.setVisible(hdRB.isSelected());
		pdparamP.setVisible(pdRB.isSelected());
	}

	@Override
	public ParamInfo getParamInfo() {
		int border,type,inittype;
		double ccc,cdc,ddc,ddd;

		if(absorbingRB.isSelected())
			border = kAbsorbing;
		else
			if(reflexiveRB.isSelected())
				border = kReflexive;
			else
				border = kPeriodic;

		if(pdRB.isSelected()){
			type = kPD;
			ccc = cpayoffccPPF.getDouble();
			cdc = cpayoffcdPPF.getDouble();
			ddc = dpayoffcdPPF.getDouble();
			ddd = dpayoffddPPF.getDouble();
		} else {
			type = kHD;
			ccc = Double.parseDouble(dddvL.getText());
			cdc = Double.parseDouble(dhdvL.getText());
			ddc = Double.parseDouble(hhdvL.getText());
			ddd = Double.parseDouble(hhhvL.getText());
		}

		if(randomRB.isSelected())
			inittype = kRandom;
		else
			if(onecRB.isSelected())
				inittype = kOneCD;
			else
				inittype = kOneDH;

		if(!switchTrigger)
			pi = new SDCellParamInfo(type,border,inittype,initfreqPPF.getDouble(),
					patchsizePPF.getInt(),edgeRB.isSelected(),eightRB.isSelected(),eachGenCB.isSelected(),
					selfiCB.isSelected(),runIntervalPPF.getInt(),laticesizePPF.getInt(),ddd,ddc,cdc,ccc);

		if(!freqRB.isSelected()){
			switchTrigger = false;
			return pi;
		}	else {
			SDGraphParamInfo sdgpi = new SDGraphParamInfo(pi,switchTrigger);
			switchTrigger = false;
			return sdgpi;
		}
	}

	void randomRB_stateChanged(ChangeEvent e) {
		initfreqPPF.setEnabled(randomRB.isSelected());
		patchsizePPF.setEnabled(!randomRB.isSelected());
		edgeRB.setEnabled(!randomRB.isSelected());
		middleRB.setEnabled(!randomRB.isSelected());
	}

	void pdRB_stateChanged(ChangeEvent e) {
		if(pdRB.isSelected())
			initfreqPPF.setParameterName("Initial Frequency of C");
		else
			initfreqPPF.setParameterName("Initial Frequency of Doves");
		dhparamP.setVisible(hdRB.isSelected());
		pdparamP.setVisible(pdRB.isSelected());
	}

	void winnerPPF_parameterFieldChanged(ParameterFieldEvent e) {
		setDNLabels();
	}

	void injuryPPF_parameterFieldChanged(ParameterFieldEvent e) {
		setDNLabels();
	}

	void setDNLabels(){
		dddvL.setText(""+winnerPPF.getDouble()/2);
		dhdvL.setText(""+0);
		hhdvL.setText(""+winnerPPF.getDouble());
		hhhvL.setText(""+(winnerPPF.getDouble()+injuryPPF.getDouble())/2);
	}
	void switchOutputType(){
		boolean isfreq = freqRB.isSelected();
		switchTrigger = true;
		freqRB.setSelected(!isfreq);
		latticeRB.setSelected(isfreq);
	}
}