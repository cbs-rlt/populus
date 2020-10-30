/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.kham;
import java.awt.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.plot.*;
import javax.swing.*;
import javax.swing.border.*;

public class KHAMPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -9042335499740405956L;
	JPanel modParamP = new JPanel();
	PopulusParameterField p2PPF = new PopulusParameterField();
	private JPanel plotTypeP = new JPanel();
	private JRadioButton Dvst2p2RB = new StyledRadioButton();
	private JRadioButton MVvsTRB = new StyledRadioButton();
	private PopulusParameterField sPPF = new PopulusParameterField();
	private PopulusParameterField rPPF = new PopulusParameterField();
	private TitledBorder titledBorder2;
	private TitledBorder titledBorder3;
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private GridBagLayout gridBagLayout3 = new GridBagLayout();
	private GridBagLayout gridBagLayout4 = new GridBagLayout();
	private ButtonGroup plotTypeBG = new ButtonGroup();
	private JRadioButton DvsTRB = new StyledRadioButton();
	private JRadioButton t2vsp2RB = new StyledRadioButton();
	private PopulusParameterField a1PPF = new PopulusParameterField();
	private PopulusParameterField D0PPF = new PopulusParameterField();
	private PopulusParameterField gensPPF = new PopulusParameterField();
	private PopulusParameterField a2PPF = new PopulusParameterField();
	private PopulusParameterField t2PPF = new PopulusParameterField();

	@Override
	public BasicPlot getPlotParamInfo() {
		int plotType=0;

		if(t2vsp2RB.isSelected())
			plotType = KHAMParamInfo.t2vsp2;
		else if(DvsTRB.isSelected())
			plotType = KHAMParamInfo.DvsT;
		else if(MVvsTRB.isSelected())
			plotType = KHAMParamInfo.MVvsT;
		else
			plotType = KHAMParamInfo.DvsT2P2;

		return new KHAMParamInfo(t2PPF.getDouble(),p2PPF.getDouble(),a1PPF.getDouble(),a2PPF.getDouble(),
				D0PPF.getDouble(),sPPF.getDouble(),rPPF.getDouble(),gensPPF.getInt(), plotType);
	}

	public KHAMPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		titledBorder2 = new TitledBorder("Individual Coefficients");
		titledBorder3 = new TitledBorder("Plot Type");
		modParamP.setBorder(titledBorder2);
		plotTypeP.setBorder(titledBorder3);
		modParamP.setLayout(gridBagLayout3 );
		p2PPF.setCurrentValue(0.7);
		p2PPF.setDefaultValue(0.7);
		p2PPF.setIncrementAmount( 0.05 );
		p2PPF.setMaxValue( 1.0 );
		p2PPF.setParameterName("<i>p<sub>2</>" );
		p2PPF.setHelpText("Initial Allelic frequency for Female Preference");
		Dvst2p2RB.setText("<i>D</i> vs <i>t<sub>2</>, <i>p<sub>2</>");
		MVvsTRB.setText("Male Viability vs time");
		sPPF.setParameterName("<i>s</i>");
		sPPF.setHelpText("Selection Coefficient");
		sPPF.setMaxValue(1.0);
		sPPF.setCurrentValue(0.2);
		sPPF.setDefaultValue(0.2);
		sPPF.setIncrementAmount(0.05);
		rPPF.setParameterName("<i>r</i>");
		rPPF.setHelpText("Recombination Fraction");
		rPPF.setMaxValue(0.5);
		rPPF.setIncrementAmount(0.05);
		rPPF.setDefaultValue(0.5);
		rPPF.setCurrentValue(0.5);
		plotTypeP.setLayout(gridBagLayout4);
		this.setLayout(gridBagLayout1);
		DvsTRB.setText("<i>D</i> vs time");
		t2vsp2RB.setText("<i>t<sub>2</> vs <i>p<sub>2</>");
		t2vsp2RB.setSelected(true);
		a1PPF.setCurrentValue(2.5);
		a1PPF.setDefaultValue(0.1);
		a1PPF.setIncrementAmount(0.05);
		a1PPF.setMaxValue(20.0);
		a1PPF.setParameterName("<i>a<sub>1</>");
		a1PPF.setHelpText("Mating Preference Strength");
		D0PPF.setIncrementAmount(0.05);
		D0PPF.setMaxValue(0.25);
		D0PPF.setParameterName("<i>D<sub>0</>");
		D0PPF.setHelpText("Initial Linkage Disequilibrium");
		gensPPF.setCurrentValue(400.0);
		gensPPF.setDefaultValue(400.0);
		gensPPF.setIncrementAmount(20.0);
		gensPPF.setIntegersOnly(true);
		gensPPF.setMaxValue(2000.0);
		gensPPF.setParameterName("Generations");
		gensPPF.setHelpText("This is the number of generations to be simulated before plotting a point.");
		a2PPF.setCurrentValue(2.0);
		a2PPF.setDefaultValue(2.0);
		a2PPF.setIncrementAmount(0.05);
		a2PPF.setMaxValue(20.0);
		a2PPF.setParameterName("<i>a<sub>2</>");
		a2PPF.setHelpText("Mating Preference Strength");
		t2PPF.setCurrentValue(0.01);
		t2PPF.setDefaultValue(0.01);
		t2PPF.setIncrementAmount(0.05);
		t2PPF.setMaxValue(1.0);
		t2PPF.setParameterName("<i>t<sub>2</>");
		t2PPF.setHelpText("Initial Allelic Frequency for Male Trait");
		titledBorder2.setTitle("Model Parameters");
		this.add( modParamP,        new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0) );
		modParamP.add(rPPF,                    new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add(sPPF,                   new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add( p2PPF,                   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0) );
		modParamP.add(a1PPF,                  new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add(D0PPF,              new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add(gensPPF,           new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add(a2PPF,        new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		modParamP.add(t2PPF,    new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
		this.add(plotTypeP,        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		plotTypeP.add(MVvsTRB,          new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
		plotTypeP.add(Dvst2p2RB,           new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeP.add(DvsTRB,          new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeP.add(t2vsp2RB,      new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
		plotTypeBG.add(MVvsTRB);
		plotTypeBG.add(Dvst2p2RB);
		plotTypeBG.add(t2vsp2RB);
		plotTypeBG.add(DvsTRB);
		this.registerChildren( this );
	}
}
