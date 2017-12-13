/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.eov;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;

public class EOVPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -1741663304487845921L;
	Border border1;
	TitledBorder titledBorder1;
	Border border8;
	ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
	JPanel plotTypePanel = new JPanel();
	RunningTimePanel runningTimePanel = new RunningTimePanel();
	Border border9;
	GridBagLayout gridBagLayout7= new GridBagLayout();
	PopulusParameterField paramE = new PopulusParameterField();
	JPanel modelParametersPanel = new JPanel();
	Border border6;
	Border border2;
	Border border7;
	TitledBorder titledBorder2;
	JPanel hostRatesPanel = new JPanel();
	GridBagLayout gridBagLayout5 = new GridBagLayout();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	PopulusParameterField paramb = new PopulusParameterField();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	PopulusParameterField paramH = new PopulusParameterField();
	TitledBorder titledBorder6;
	PopulusParameterField paramI = new PopulusParameterField();
	TitledBorder titledBorder7;
	JPanel hostDensitiesPanel = new JPanel();
	Border border3;
	TitledBorder titledBorder3;
	TitledBorder titledBorder8;
	Border border4;
	TitledBorder titledBorder4;
	TitledBorder titledBorder9;
	Border border10;
	TitledBorder titledBorder10;
	Border border5;
	TitledBorder titledBorder5;
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	private PopulusParameterField paramBirth1 = new PopulusParameterField();
	private PopulusParameterField paramDeath = new PopulusParameterField();
	private PopulusParameterField paramBirth0 = new PopulusParameterField();
	private PopulusParameterField paramP = new PopulusParameterField();
	private JRadioButton ebvstButton = new StyledRadioButton();
	private JRadioButton HIStarButton = new StyledRadioButton();
	private JRadioButton hivstButton = new StyledRadioButton();
	private JPanel constantsPanel1 = new JPanel();
	private PopulusParameterField paramC3 = new PopulusParameterField();
	private PopulusParameterField paramC2 = new PopulusParameterField();
	private GridBagLayout gridBagLayout6 = new GridBagLayout();
	private PopulusParameterField paramC1 = new PopulusParameterField();
	private GridBagLayout gridBagLayout4 = new GridBagLayout();
	private JRadioButton coupledModelButton = new JRadioButton();
	private JRadioButton alternatingModelButton = new JRadioButton();
	private JPanel modelTypePanel = new JPanel();

	@Override
	public BasicPlot getPlotParamInfo() {
		double time = runningTimePanel.getTime();
		int plotType;
		if( hivstButton.isSelected())
			plotType = EOVParamInfo.hivsT;
		else if( ebvstButton.isSelected() )
			plotType = EOVParamInfo.ebvsT;
		else
			plotType = EOVParamInfo.hiStar;

		//boolean vsTime = true;
		int modelType;
		if( alternatingModelButton.isSelected() ) {
			modelType = EOVParamInfo.ALTER;
		}
		else {
			if( coupledModelButton.isSelected() ) {
				modelType = EOVParamInfo.COUPLED;
			}
			else {
				System.err.println( "Error in EOVPanel: Unknown button selected" );
				modelType = -1;
			}
		}

		/*if( plotType==EOVParamInfo.phase && modelType > 10 ) {
         return new EOV3DParamInfo( modelType, time, paramH.getDouble(), paramI.getDouble(), paramR.getDouble(), paramBirth1.getDouble(), paramDeath.getDouble(), paramE.getDouble(), paramb.getDouble(), paramnu.getDouble(), paramgamma.getDouble() );
      }*/
		if (modelType == EOVParamInfo.COUPLED)
			return new EOVParamInfo( modelType, plotType, time, paramH.getDouble(), paramI.getDouble(), paramBirth0.getDouble(), paramBirth1.getDouble(),  paramb.getDouble(), paramDeath.getDouble(), paramP.getDouble(),paramE.getDouble(), paramC1.getDouble(), paramC2.getDouble(), paramC3.getDouble() );
		else
			return new EOVParamInfo( modelType, plotType, time, paramH.getDouble(), paramI.getDouble(), paramBirth0.getDouble(), paramBirth1.getDouble(),  paramb.getDouble(), paramDeath.getDouble(), paramP.getDouble(),paramE.getDouble(), paramC1.getDouble(), paramC2.getDouble(), paramC3.getDouble() );
	}

	public EOVPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	void modelTypeChanged( boolean alternate ) {
		paramC1.setEnabled(!alternate  );
		paramC2.setEnabled(!alternate );
		paramC3.setEnabled(!alternate );
		ebvstButton.setEnabled(!alternate);
		paramb.setEnabled(alternate);
		paramE.setEnabled(alternate);
	}



	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder1 = new TitledBorder( border1, "Plot Type" );
		border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder2 = new TitledBorder( border2, "Model Parameters" );
		border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder3 = new TitledBorder( border3, "Host Rates" );
		border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder4 = new TitledBorder( border4, "Termination Conditions" );
		border5 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder5 = new TitledBorder( border5, "Host Densities" );
		border6 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder6 = new TitledBorder( border6, "Include R" );
		border7 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder7 = new TitledBorder( border7, "Host Rates" );
		border8 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder8 = new TitledBorder( border8, "Model Version" );
		border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder9 = new TitledBorder( border9, "Initial Host Densities" );
		border10 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder10 = new TitledBorder( border10, "Constants" );
		plotTypePanel.setBorder( titledBorder1 );
		plotTypePanel.setLayout( gridBagLayout7 );
		modelParametersPanel.setBorder( titledBorder2 );
		modelParametersPanel.setLayout( gridBagLayout5 );
		hostRatesPanel.setBorder( titledBorder3 );
		hostRatesPanel.setLayout( gridBagLayout3 );
		paramb.setCurrentValue(1.225 );
		paramb.setDefaultValue(1.225 );
		paramb.setHelpText( "Between-host transmission rate" );
		paramb.setIncrementAmount(0.25 );
		paramb.setMaxValue(1000.0 );
		paramb.setParameterName("<i>b</i>" );
		paramH.setParameterName("<i>H</i>(0)" );
		paramH.setMaxValue( 100000.0 );
		paramH.setIncrementAmount( 5.0 );
		paramH.setDefaultValue(1000.0 );
		paramH.setHelpText( "Susceptible host density" );
		paramH.setCurrentValue(1000.0 );
		paramI.setParameterName( "<i>I</i>(0)" );
		paramI.setMaxValue( 100000.0 );
		paramI.setIncrementAmount( 5.0 );
		paramI.setDefaultValue(1.0 );
		paramI.setHelpText( "Infected host density" );
		paramI.setCurrentValue(1.0 );
		hostDensitiesPanel.setLayout( gridBagLayout2 );
		hostDensitiesPanel.setBorder( titledBorder9 );
		hostDensitiesPanel.setToolTipText( "Initial Host Densities" );
		this.setLayout( gridBagLayout1 );
		paramE.setParameterName("<i>e</i>" );
		paramE.setMaxValue(10000.0 );
		paramE.setIncrementAmount(5.0 );
		paramE.setDefaultValue(626.0 );
		paramE.setHelpText("Disease-induced mortality rate (virulence)" );
		paramE.setCurrentValue(625.75 );
		titledBorder9.setTitle( "Host Densities" );
		runningTimePanel.setDefaultTime(4.0 );
		paramBirth1.setCurrentValue(0.0080);
		paramBirth1.setDefaultValue(0.0080);
		paramBirth1.setHelpText("Birth Rate, density dependent component");
		paramBirth1.setIncrementAmount(0.1);
		paramBirth1.setMaxValue(1000.0);
		paramBirth1.setParameterName("<i>a<sub>1</sub></i>");
		paramDeath.setCurrentValue(2.0);
		paramDeath.setDefaultValue(2.0);
		paramDeath.setHelpText("Natural mortality rate");
		paramDeath.setIncrementAmount(0.1);
		paramDeath.setMaxValue(5.0);
		paramDeath.setParameterName("<i>d </i>");
		paramBirth0.setParameterName("<i>a<sub>0</sub></i>");
		paramBirth0.setMaxValue(1000.0);
		paramBirth0.setIncrementAmount(0.1);
		paramBirth0.setHelpText("Birth Rate, density independent component");
		paramBirth0.setDefaultValue(10.0);
		paramBirth0.setCurrentValue(10.0);
		paramP.setCurrentValue(0.8);
		paramP.setDefaultValue(0.8);
		paramP.setHelpText("Probabilty of disease");
		paramP.setIncrementAmount(0.1);
		paramP.setMaxValue(1.0);
		paramP.setParameterName("<i>p</i>");
		ebvstButton.setText("<i>e, b vs t</i>");
		ebvstButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ebvstButton_actionPerformed(e);
			}
		});
		ebvstButton.setEnabled(false);
		ebvstButton.setActionCommand("<i>e, b vs t</i>");
		ebvstButton.setFocusPainted(false);
		HIStarButton.setFocusPainted(false);
		HIStarButton.setActionCommand("<i>H*, I*, H*+I* vs b</i>");
		HIStarButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HIStarButton_actionPerformed(e);
			}
		});
		HIStarButton.setText("<i>H*, I*, H*+I* vs b</i>");
		hivstButton.setActionCommand("<i>H, I vs t </i>");
		hivstButton.setFocusPainted(false);
		hivstButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hivstButton_actionPerformed(e);
			}
		});
		hivstButton.setSelected(true);
		hivstButton.setText("<i>H, I vs t </i>");
		constantsPanel1.setToolTipText("Constants");
		constantsPanel1.setBorder(titledBorder10);
		constantsPanel1.setLayout(gridBagLayout6);
		paramC3.setParameterName("<i>C<sub>3</sub></i>");
		paramC3.setMaxValue(100000.0);
		paramC3.setMinValue(0.0010);
		paramC3.setIncrementAmount(5.0);
		paramC3.setDefaultValue(400.0);
		paramC3.setEnabled(false);
		paramC3.setHelpText("Constant C3");
		paramC3.setCurrentValue(400.0);
		paramC2.setParameterName("<i>C<sub>2</sub></i>");
		paramC2.setMaxValue(100000.0);
		paramC2.setIncrementAmount(5.0);
		paramC2.setDefaultValue(20.0);
		paramC2.setEnabled(false);
		paramC2.setHelpText("constant c2");
		paramC2.setCurrentValue(20.0);
		paramC1.setCurrentValue(4.0);
		paramC1.setHelpText("constant c1");
		paramC1.setDefaultValue(4.0);
		paramC1.setEnabled(false);
		paramC1.setIncrementAmount(5.0);
		paramC1.setMaxValue(100000.0);
		paramC1.setMinValue(4.0);
		paramC1.setParameterName("<i>C<sub>1</sub></i>");
		coupledModelButton.setToolTipText("Coupled");
		coupledModelButton.setText("Coupled");
		coupledModelButton.setFocusPainted(false);
		coupledModelButton.addActionListener(new java.awt.event.ActionListener()  {

			@Override
			public void actionPerformed( ActionEvent e ) {
				coupledModelButton_actionPerformed( e );
			}
		});
		alternatingModelButton.setToolTipText("Alternating");
		alternatingModelButton.setSelected(true);
		alternatingModelButton.setText("Alternating");
		alternatingModelButton.setFocusPainted(false);
		alternatingModelButton.addActionListener(new java.awt.event.ActionListener()  {

			@Override
			public void actionPerformed( ActionEvent e ) {
				alternatingModelButton_actionPerformed( e );
			}
		});
		modelTypePanel.setLayout(gridBagLayout4);
		modelTypePanel.setBorder(titledBorder8);
		hostDensitiesPanel.add( paramH,              new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
		hostDensitiesPanel.add( paramI,                           new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
		modelParametersPanel.add(hostRatesPanel,    new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		modelTypePanel.add(alternatingModelButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		modelTypePanel.add(coupledModelButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		this.add(runningTimePanel,             new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(plotTypePanel,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		constantsPanel1.add(paramC3,                                     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		constantsPanel1.add(paramC1,                                     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		constantsPanel1.add(paramC2,               new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		this.add(modelParametersPanel,                   new GridBagConstraints(1, 0, 2, 3, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		modelParametersPanel.add(hostDensitiesPanel,       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		hostRatesPanel.add( paramE,                            new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
		hostRatesPanel.add( paramb,                           new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
		hostRatesPanel.add(paramDeath,                    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		hostRatesPanel.add(paramBirth1,                 new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 0, 3, 0), 0, 0));
		hostRatesPanel.add(paramP,       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
		hostRatesPanel.add(paramBirth0,                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 0, 3, 0), 0, 0));
		modelParametersPanel.add(constantsPanel1,                                   new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		plotTypePanel.add(hivstButton,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		plotTypePanel.add(ebvstButton,   new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		plotTypePanel.add(HIStarButton,  new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.add(modelTypePanel,                                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		registerChildren( this );
		bg2.add(hivstButton);
		bg2.add(ebvstButton);
		bg2.add(HIStarButton);
		bg1.add(alternatingModelButton);
		bg1.add(coupledModelButton);
	}
	void alternatingModelButton_actionPerformed( ActionEvent e ) {
		modelTypeChanged( true );
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	void coupledModelButton_actionPerformed( ActionEvent e ) {
		modelTypeChanged( false );
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}
	void ebvstButton_actionPerformed(ActionEvent e) {
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}
	void hivstButton_actionPerformed(ActionEvent e) {
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}
	void HIStarButton_actionPerformed(ActionEvent e) {
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	/*
public int getTriggers() {
return 0;
}
	 */
}
