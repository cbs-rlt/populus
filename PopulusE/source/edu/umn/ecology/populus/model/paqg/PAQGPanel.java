/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.paqg;
import java.awt.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.plot.*;
import javax.swing.*;
import javax.swing.border.*;

public class PAQGPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 293207087767048504L;
	JPanel wPanel = new JPanel();
	private JPanel plotTypeP = new JPanel();
	private JRadioButton WBARvstRB = new StyledRadioButton();
	private JRadioButton WBARvsPRB = new StyledRadioButton();
	private JRadioButton h2vstRB = new StyledRadioButton();
	private JRadioButton PvstRB = new StyledRadioButton();
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private JPanel paramP = new JPanel();
	private PopulusParameterField vePPF = new PopulusParameterField();
	private PopulusParameterField pPPF = new PopulusParameterField();
	private GridBagLayout gridBagLayout2 = new GridBagLayout();
	private GridBagLayout gridBagLayout3 = new GridBagLayout();
	private PopulusParameterField gensPPF = new PopulusParameterField();
	private PopulusParameterField waaPPF = new PopulusParameterField();
	private PopulusParameterField wAaPPF = new PopulusParameterField();
	private PopulusParameterField wAAPPF = new PopulusParameterField();
	private GridBagLayout gridBagLayout4 = new GridBagLayout();
	private TitledBorder titledBorder1;
	private TitledBorder titledBorder2;
	private TitledBorder titledBorder3;
	private ButtonGroup plotTypeBG = new ButtonGroup();

	@Override
	public BasicPlot getPlotParamInfo() {
		int plotType;
		double[] w = new double[3];
		w[0] = wAAPPF.getDouble();
		w[1] = wAaPPF.getDouble();
		w[2] = waaPPF.getDouble();

		if(PvstRB.isSelected())
			plotType = PAQGParamInfo.Pvst;
		else if(h2vstRB.isSelected())
			plotType = PAQGParamInfo.h2vst;
		else if(WBARvsPRB.isSelected())
			plotType = PAQGParamInfo.WBARvsP;
		else
			plotType = PAQGParamInfo.WBARvst;

		return new PAQGParamInfo(w,pPPF.getDouble(),vePPF.getDouble(),gensPPF.getInt(),plotType);
	}

	public PAQGPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {//this is needed because w/o the title doesn't fit
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		titledBorder3 = new TitledBorder("");
		wPanel.setLayout(gridBagLayout4 );
		WBARvstRB.setText("<i>w\u0305</i> vs <i>t</i>");
		WBARvsPRB.setText("<i>w\u0305</i> vs <i>p</i>");
		h2vstRB.setText("<i>h</i><sup>2</sup> vs <i>t</i>");
		PvstRB.setSelected(true);
		PvstRB.setText("<i>p</i> vs <i>t</i>");
		plotTypeP.setLayout(gridBagLayout1);
		vePPF.setParameterName("<i>V,/i><sub>e</sub>");
		vePPF.setMaxValue(1.0);
		vePPF.setIncrementAmount(0.05);
		vePPF.setDefaultValue(0.01);
		vePPF.setCurrentValue(0.01);
		vePPF.setHelpText("Environmental variance");
		pPPF.setParameterName("<i>p</i>");
		pPPF.setMaxValue(1.0);
		pPPF.setIncrementAmount(0.05);
		pPPF.setDefaultValue(0.4);
		pPPF.setCurrentValue(0.01);
		pPPF.setHelpText("Initial gene frequency");
		paramP.setLayout(gridBagLayout2);
		this.setLayout(gridBagLayout3);
		gensPPF.setCurrentValue(50.0);
		gensPPF.setDefaultValue(50.0);
		gensPPF.setIncrementAmount(10.0);
		gensPPF.setMaxValue(1000.0);
		gensPPF.setMinValue(1.0);
		gensPPF.setParameterName("Generations");
		waaPPF.setParameterName("w<i><sub>aa</sub></i>");
		waaPPF.setHelpText("Fitness for the \"aa\" genotype");
		waaPPF.setMaxValue(5.0);
		waaPPF.setIncrementAmount(0.1);
		waaPPF.setDefaultValue(0.3);
		waaPPF.setCurrentValue(0.3);
		wAaPPF.setParameterName("<i>w<sub>Aa</>");
		wAaPPF.setHelpText("Fitness for the \"Aa\" genotype");
		wAaPPF.setMaxValue(5.0);
		wAaPPF.setIncrementAmount(0.1);
		wAaPPF.setDefaultValue(0.6);
		wAaPPF.setCurrentValue(0.6);
		wAAPPF.setParameterName("<i>w<sub>AA</sub></i>");
		wAAPPF.setHelpText("Fitness for the \"AA\" genotype");
		wAAPPF.setMaxValue(5.0);
		wAAPPF.setIncrementAmount(0.1);
		wAAPPF.setDefaultValue(1.0);
		wAAPPF.setCurrentValue(1.0);
		paramP.setBorder(titledBorder1);
		titledBorder1.setTitle("Parameters");
		plotTypeP.setBorder(titledBorder2);
		titledBorder2.setTitle("Plot Type");
		wPanel.setBorder(titledBorder3);
		titledBorder3.setTitle("Fitnesses");
		this.add( wPanel,   new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0) );
		wPanel.add(wAAPPF,       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
		wPanel.add(wAaPPF,      new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
		wPanel.add(waaPPF,      new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 0), 0, 0));
		this.add(plotTypeP,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		plotTypeP.add(PvstRB,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeP.add(h2vstRB,     new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeP.add(WBARvsPRB,      new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeP.add(WBARvstRB,       new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		this.add(paramP,   new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		paramP.add(pPPF,       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		paramP.add(vePPF,       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		paramP.add(gensPPF,          new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		plotTypeBG.add(PvstRB);
		plotTypeBG.add(h2vstRB);
		plotTypeBG.add(WBARvsPRB);
		plotTypeBG.add(WBARvstRB);
		this.registerChildren( this );
	}
}
