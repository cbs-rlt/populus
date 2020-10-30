/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.plot.*;
import javax.swing.*;
import javax.swing.border.*;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;

import java.util.*;

public class LVPPTLPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1619767776006811454L;
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvpptl.Res" );
	public static final String TYPE1 = res.getString( "Type_1" );
	public static final String TYPE2 = res.getString( "Type_2" );
	public static final String TYPE3 = res.getString( "Type_3" );
	Border border5;
	RunningTimePanel runningTimePanel1 = new RunningTimePanel();
	Border border7;
	Border border8;
	TitledBorder titledBorder10;
	ButtonGroup bg1 = new ButtonGroup();
	TitledBorder titledBorder6;
	ButtonGroup bg2 = new ButtonGroup();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	ButtonGroup bg3 = new ButtonGroup();
	JPanel predatorPanel = new JPanel();
	JPanel graphTypePanel = new JPanel();
	PopulusParameterField paramPreyK = new PopulusParameterField();
	JRadioButton buttonPvN = new StyledRadioButton();
	PopulusParameterField paramR = new PopulusParameterField();
	JRadioButton buttonPNvT = new StyledRadioButton();
	JPanel predatorGrowthPanel = new JPanel();
	GridLayout gridLayout1 = new GridLayout();
	PopulusParameterField paramD = new PopulusParameterField();
	Border border1;
	PopulusParameterField paramTLN0 = new PopulusParameterField();
	TitledBorder titledBorder1;
	PopulusParameterField paramh = new PopulusParameterField();
	JPanel lotkaVolterraParams = new JPanel();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	PopulusParameterField paramC2 = new PopulusParameterField();
	TitledBorder titledBorder9;
	PopulusParameterField paramr2 = new PopulusParameterField();
	GridLayout gridLayout7 = new GridLayout();
	PopulusParameterField paramLVP0 = new PopulusParameterField();
	ComboBoxModel<String> typeComboBoxModel = new DefaultComboBoxModel<String>( new String[] {
			TYPE1, TYPE2, TYPE3
	} );
	Border border2;
	TitledBorder titledBorder5;
	TitledBorder titledBorder2;
	PopulusParameterField paramTheta = new PopulusParameterField();
	PopulusParameterField paramC1 = new PopulusParameterField();
	GridLayout gridLayout4 = new GridLayout();
	PopulusParameterField paramr1 = new PopulusParameterField();
	Border border6;
	PopulusParameterField paramLVN0 = new PopulusParameterField();
	PopulusParameterField paramS = new PopulusParameterField();
	Border border3;
	GridLayout gridLayout5 = new GridLayout();
	TitledBorder titledBorder3;
	TitledBorder titledBorder7;
	JPanel modelType = new JPanel();
	PopulusParameterField paramTLP0 = new PopulusParameterField();
	Border border4;
	JPanel frParamsPanel = new JPanel();
	TitledBorder titledBorder4;
	PopulusParameterField paramC = new PopulusParameterField();
	JRadioButton thetaLogisticButton = new JRadioButton();
	TitledBorder titledBorder8;
	JRadioButton lotkaVolterraButton = new JRadioButton();
	JPanel preyPanel = new JPanel();
	Border border9;
	JTabbedPane modelSpecificParamsTabbedPane = new JTabbedPane();
	Border border10;
	JPanel thetaLogisticParams = new JPanel();
	GridLayout gridLayout2 = new GridLayout();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JComboBox<String> typeComboBox = new JComboBox<String>();
	GridBagLayout gridBagLayout5 = new GridBagLayout();
	JPanel preyGrowthPanel = new JPanel();
	private PopulusParameterField paramK = new PopulusParameterField();
	private PopulusParameterField paramTh = new PopulusParameterField();
	private PopulusParameterField paramA = new PopulusParameterField();
	private JPanel lvParamsPanel = new JPanel();
	private SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	private GridLayout gridLayout3 = new GridLayout();
	private JCheckBox ddPreyBox = new JCheckBox();
	private JCheckBox type2Box = new JCheckBox();

	@Override
	public void updateLabels() {
		titledBorder9 = new TitledBorder( border9, res.getString( "Predator" ), TitledBorder.LEFT, TitledBorder.TOP, new Font( "Dialog", Font.PLAIN, 12 ), ColorScheme.colors[0] );
		titledBorder10 = new TitledBorder( BorderFactory.createLineBorder( Color.black, 1 ), res.getString( "Prey" ), TitledBorder.LEFT, TitledBorder.TOP, new Font( "Dialog", Font.PLAIN, 12 ), ColorScheme.colors[1] );
		titledBorder6.setTitleColor( ColorScheme.colors[0] );
		titledBorder5.setTitleColor( ColorScheme.colors[1] );
		predatorPanel.setBorder( titledBorder9 );
		preyPanel.setBorder( titledBorder10 );
	}

	@Override
	public BasicPlot getPlotParamInfo() {
		if( this.lotkaVolterraButton.isSelected() ) {
			return new LVPPTLParamInfo( this.buttonPvN.isSelected(), this.runningTimePanel1.getTime(), this.ddPreyBox.isSelected(),
					this.type2Box.isSelected(), this.paramPreyK.getDouble(), this.paramLVN0.getDouble(),
					this.paramr1.getDouble(), this.paramC1.getDouble(), this.paramLVP0.getDouble(), this.paramr2.getDouble(),
					this.paramC2.getDouble(), this.paramA.getDouble(), this.paramTh.getDouble() );
		}
		else {
			return new LVPPTLParamInfo( this.buttonPvN.isSelected(), this.runningTimePanel1.getTime(), this.typeComboBox.getSelectedIndex() + 1,
					this.paramR.getDouble(), this.paramK.getDouble(), this.paramTheta.getDouble(), this.paramC.getDouble(),
					this.paramh.getDouble(), this.paramD.getDouble(), this.paramS.getDouble(), this.paramTLN0.getDouble(),
					this.paramTLP0.getDouble() );
		}
	}

	public LVPPTLPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	void type2Box_actionPerformed(ActionEvent e) {
		//enable/disable Th and a parameters
		if(type2Box.isSelected() && !ddPreyBox.isSelected()){
			runningTimePanel1.setDefaultTime( 12.0 );
		} else {
			runningTimePanel1.setDefaultTime( 60.0 );
		}

		paramA.setEnabled( this.type2Box.isSelected() );
		paramTh.setEnabled( this.type2Box.isSelected() );
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	void ddPreyBox_actionPerformed( ActionEvent e ) {
		if(type2Box.isSelected() && !ddPreyBox.isSelected()){
			runningTimePanel1.setDefaultTime( 12.0 );
		} else {
			runningTimePanel1.setDefaultTime( 60.0 );
		}

		paramPreyK.setEnabled( ddPreyBox.isSelected() );
		fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	void setLVParamsEnabled( boolean b) {
		this.lvParamsPanel.setEnabled( b );
		this.ddPreyBox.setEnabled( b );
		this.type2Box.setEnabled( b );
	}

	void thetaLogisticButton_actionPerformed( ActionEvent e ) {
		this.modelSpecificParamsTabbedPane.setSelectedComponent( thetaLogisticParams );
		setLVParamsEnabled( false );
	}

	void lotkaVolterraParams_componentShown( ComponentEvent e ) {
		if(type2Box.isSelected() && !ddPreyBox.isSelected()){
			runningTimePanel1.setDefaultTime( 12.0 );
		} else {
			runningTimePanel1.setDefaultTime( 60.0 );
		}
		this.lotkaVolterraButton.setSelected( true );
		setLVParamsEnabled( true );
	}

	void thetaLogisticParams_componentShown( ComponentEvent e ) {
		runningTimePanel1.setDefaultTime( 60.0 );
		this.thetaLogisticButton.setSelected( true );
		setLVParamsEnabled( false );
	}

	void lotkaVolterraButton_actionPerformed( ActionEvent e ) {
		this.modelSpecificParamsTabbedPane.setSelectedComponent( lotkaVolterraParams );
		setLVParamsEnabled( true );
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
		border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
		border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
		border4 = BorderFactory.createLineBorder( Color.black, 1 );
		titledBorder4 = new TitledBorder( border4, res.getString( "Model_Type" ) );
		border5 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder5 = new TitledBorder( border5, res.getString( "Prey_Growth" ) );
		border6 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder6 = new TitledBorder( border6, res.getString( "Predator_Growth" ) );
		border7 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder7 = new TitledBorder( border7, res.getString( "Initial_Densities" ) );
		border8 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder8 = new TitledBorder( border8, res.getString( "Func_Response_Params" ) );
		border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder9 = new TitledBorder( border9, res.getString( "Predator" ), TitledBorder.LEFT, TitledBorder.TOP, new Font( "Dialog", Font.PLAIN, 12 ), ColorScheme.colors[0] );
		border10 = BorderFactory.createEmptyBorder();
		titledBorder10 = new TitledBorder( BorderFactory.createLineBorder( Color.black, 1 ), res.getString( "Prey" ), TitledBorder.LEFT, TitledBorder.TOP, new Font( "Dialog", Font.PLAIN, 12 ), ColorScheme.colors[1] );
		buttonPvN.setText( "<i>P </i> vs <i>N</i>" );
		buttonPvN.setFocusPainted( false );
		buttonPNvT.setSelected( true );
		buttonPNvT.setText( "<i>P</i>, <i>N</i> vs <i>t</i>" );
		buttonPNvT.setFocusPainted( false ); //<i>P</i>, <i>N</i> vs <i>T</i>
		graphTypePanel.setLayout( gridLayout1 );
		gridLayout1.setColumns( 1 );
		gridLayout1.setRows( 2 );
		graphTypePanel.setBorder( titledBorder1 );
		lotkaVolterraParams.setLayout( gridBagLayout3 );
		lotkaVolterraParams.setBorder( titledBorder2 );
		lotkaVolterraParams.addComponentListener( new java.awt.event.ComponentAdapter()  {

			@Override
			public void componentShown( ComponentEvent e ) {
				lotkaVolterraParams_componentShown( e );
			}
		} );
		paramLVN0.setCurrentValue( 10.0 );
		paramLVN0.setDefaultValue( 10.0 );
		paramLVN0.setHelpText( res.getString( "Initial_Population" ) );
		paramLVN0.setIncrementAmount( 10.0 );
		paramLVN0.setMaxValue( 1000000.0 );
		paramLVN0.setParameterName("<i>N</i><sub>0</sub>" );
		paramr1.setCurrentValue( 0.9 );
		paramr1.setDefaultValue( 0.9 );
		paramr1.setHelpText( res.getString( "Rate_of_population" ) );
		paramr1.setIncrementAmount( 0.05 );
		paramr1.setMaxValue( 5.0 );
		paramr1.setParameterName("<i>r</i><sub>1</sub>" );
		paramC1.setCurrentValue( 0.1 );
		paramC1.setDefaultValue( 0.1 );
		paramC1.setIncrementAmount( 0.0050 );
		paramC1.setMaxValue( 999.0 );
		paramC1.setParameterName( "<i>C</i>" );
		paramC1.setHelpText("Constant related to prey escape ability and the number of prey a predator takes per unit time");
		paramLVP0.setCurrentValue( 10.0 );
		paramLVP0.setDefaultValue( 10.0 );
		paramLVP0.setHelpText( res.getString( "Initial_Population" ) );
		paramLVP0.setIncrementAmount( 10.0 );
		paramLVP0.setMaxValue( 1000000.0 );
		paramLVP0.setParameterName("<i>P</i><sub>0</sub>" );
		paramr2.setCurrentValue( 0.6 );
		paramr2.setDefaultValue( 0.6 );
		paramr2.setHelpText( "Measure of the predator's starvation rate" );
		paramr2.setIncrementAmount( 0.05 );
		paramr2.setMaxValue( 5.0 );
		paramr2.setParameterName("<i>d</i><sub>2</sub>" );
		paramC2.setCurrentValue( 0.5 );
		paramC2.setDefaultValue( 0.5 );
		paramC2.setIncrementAmount( 0.050 );
		paramC2.setMaxValue( 999.0 );
		paramC2.setParameterName( "<i>g</i>" );
		paramC2.setHelpText("Constant defining the conversion efficiency of prey into predators");
		titledBorder1.setTitle( res.getString( "Graph_Type" ) );
		modelType.setBorder( titledBorder4 );
		modelType.setLayout(simpleVFlowLayout1 );
		this.setLayout( gridBagLayout1 );
		lotkaVolterraButton.setSelected( true );
		lotkaVolterraButton.setText( res.getString( "Lotka_Volterra" ) );
		lotkaVolterraButton.setFocusPainted( false );
		lotkaVolterraButton.addActionListener( new java.awt.event.ActionListener()  {

			@Override
			public void actionPerformed( ActionEvent e ) {
				lotkaVolterraButton_actionPerformed( e );
			}
		} );
		thetaLogisticButton.setText( "\u0398 - Logistic" );
		thetaLogisticButton.setFocusPainted( false );
		thetaLogisticButton.addActionListener( new java.awt.event.ActionListener()  {

			@Override
			public void actionPerformed( ActionEvent e ) {
				thetaLogisticButton_actionPerformed( e );
			}
		} );
		typeComboBox.setModel( typeComboBoxModel );
		preyGrowthPanel.setBorder( titledBorder5 );
		preyGrowthPanel.setToolTipText( res.getString( "Prey_Growth" ) );
		preyGrowthPanel.setLayout( gridLayout4 );
		paramR.setCurrentValue( 0.9 );
		paramR.setDefaultValue( 0.9 );
		paramR.setIncrementAmount( 0.1 );
		paramR.setMaxValue( 10.0 );
		paramR.setParameterName("<i>r</i><sub>1</sub>" );
		paramR.setHelpText("Prey intrinsic growth rate");
		paramTheta.setParameterName( "\u0398" );
		paramTheta.setMaxValue( 10.0 );
		paramTheta.setIncrementAmount( 0.1 );
		paramTheta.setDefaultValue( 1.0 );
		paramTheta.setCurrentValue( 1.0 );
		paramTheta.setHelpText( res.getString( "ThetaDescription" ) );
		paramK.setParameterName( "<i>K </i>" );
		paramK.setMaxValue( 1000000.0 );
		paramK.setIncrementAmount( 10.0 );
		paramK.setDefaultValue( 25.0 );
		paramK.setCurrentValue( 25.0 );
		paramK.setHelpText("Prey carrying capacity");
		gridLayout4.setColumns( 1 );
		gridLayout4.setRows( 0 );
		predatorGrowthPanel.setBorder( titledBorder6 );
		predatorGrowthPanel.setToolTipText( res.getString( "Predator_Growth" ) );
		predatorGrowthPanel.setLayout( gridLayout5 );
		paramS.setCurrentValue( 0.6 );
		paramS.setDefaultValue( 0.6 );
		paramS.setIncrementAmount( 0.1 );
		paramS.setMaxValue( 10.0 );
		paramS.setParameterName( "<i>g </i>" );
		paramS.setHelpText("Constant defining the conversion efficiency of prey into predators");
		paramD.setCurrentValue( 0.6 );
		paramD.setDefaultValue( 0.6 );
		paramD.setIncrementAmount( 0.1 );
		paramD.setMaxValue( 100.0 );
		paramD.setParameterName( "<i>D </i>" );
		paramD.setHelpText("Intake rate of prey required for a predator to just replace itself in the next generation");
		gridLayout5.setColumns( 1 );
		gridLayout5.setRows( 0 );
		paramTLN0.setCurrentValue( 10.0 );
		paramTLN0.setDefaultValue( 10.0 );
		paramTLN0.setHelpText( res.getString( "Initial_Prey_Density" ) );
		paramTLN0.setIncrementAmount( 10.0 );
		paramTLN0.setMaxValue( 100000.0 );
		paramTLN0.setParameterName("<i>N</i><sub>0</sub>" );
		paramTLP0.setCurrentValue( 10.0 );
		paramTLP0.setDefaultValue( 10.0 );
		paramTLP0.setHelpText( res.getString( "Initial_Predator_Density" ) );
		paramTLP0.setIncrementAmount( 10.0 );
		paramTLP0.setMaxValue( 10000.0 );
		paramTLP0.setParameterName("<i>P</i><sub>0</sub>" );
		thetaLogisticParams.setLayout( gridBagLayout2 );
		thetaLogisticParams.addComponentListener( new java.awt.event.ComponentAdapter()  {

			@Override
			public void componentShown( ComponentEvent e ) {
				thetaLogisticParams_componentShown( e );
			}
		} );
		paramC.setCurrentValue( 0.05 );
		paramC.setDefaultValue( 0.05 );
		paramC.setIncrementAmount( 0.01 );
		paramC.setMaxValue( 100.0 );
		paramC.setParameterName( "<i>C </i>" );
		paramC.setHelpText( res.getString( "DetailedCDescription" ));
		paramh.setCurrentValue( 1.0 );
		paramh.setDefaultValue( 1.0 );
		paramh.setIncrementAmount( 0.2 );
		paramh.setMaxValue( 1000.0 );
		paramh.setParameterName( "<i>h </i>" );
		paramh.setHelpText( res.getString( "DetailedhDescription" ));
		frParamsPanel.setBorder( titledBorder8 );
		frParamsPanel.setToolTipText( res.getString( "Functional_Response" ) );
		frParamsPanel.setLayout( gridBagLayout5 );
		predatorPanel.setBorder( titledBorder9 );
		predatorPanel.setLayout( gridLayout7 );
		preyPanel.setBorder( titledBorder10 );
		preyPanel.setLayout( gridLayout2 );
		gridLayout2.setColumns( 1 );
		gridLayout2.setRows( 0 );
		gridLayout7.setColumns( 1 );
		gridLayout7.setRows( 0 );
		paramPreyK.setCurrentValue( 25.0 );
		paramPreyK.setDefaultValue( 25.0 );
		paramPreyK.setEnabled( false );
		paramPreyK.setIncrementAmount( 10.0 );
		paramPreyK.setMaxValue( 10000.0 );
		paramPreyK.setParameterName( "<i>K</i>" );
		//paramPreyK.setHelpText("Prey carrying capacity");
		typeComboBoxModel.setSelectedItem( TYPE2 );
		titledBorder7.setTitle( res.getString( "Init_Densities" ) );
		titledBorder6.setTitle( res.getString( "Predator" ) );
		titledBorder6.setTitleColor( ColorScheme.colors[0] );
		titledBorder5.setTitle( res.getString( "Prey" ) );
		titledBorder5.setTitleColor( ColorScheme.colors[1] );
		titledBorder8.setTitle( res.getString( "Functional_Response1" ) );
		titledBorder3.setTitle( res.getString( "Running_Conditions" ) );
		runningTimePanel1.setDefaultTime( 60.0 );
		runningTimePanel1.setMaxTime( 500 ); //the graph only graphs to about 97 or 98, why is this?
		runningTimePanel1.setAutoIncrement( true, true );
		paramTh.setCurrentValue(0.5);
		paramTh.setDefaultValue(0.5);
		paramTh.setEnabled(false);
		paramTh.setIncrementAmount(0.050);
		paramTh.setMaxValue(999.0);
		paramTh.setParameterName("<i>g</i>");
		paramTh.setHelpText("Constant defining the conversion efficiency of prey into predators");
		paramTh.setParameterName("<i>T<sub>h</sub></i>");
		paramA.setCurrentValue(0.5);
		paramA.setDefaultValue(0.5);
		paramA.setEnabled(false);
		paramA.setIncrementAmount(0.050);
		paramA.setMaxValue(999.0);
		paramA.setParameterName("<i>g</i>");
		paramA.setHelpText("Area an individual predator can search per unit time");
		paramA.setParameterName("<i>a</i>");
		lvParamsPanel.setLayout(gridLayout3);
		ddPreyBox.setToolTipText(res.getString( "Density_Dependent" ));
		ddPreyBox.setText(res.getString( "DD_Prey" ));
		ddPreyBox.setFocusPainted(false);
		ddPreyBox.addActionListener(new java.awt.event.ActionListener()  {

			@Override
			public void actionPerformed( ActionEvent e ) {
				ddPreyBox_actionPerformed( e );
			}
		});
		type2Box.setToolTipText("Type II");
		type2Box.setText("Type II");
		type2Box.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				type2Box_actionPerformed(e);
			}
		});
		gridLayout3.setColumns(1);
		gridLayout3.setRows(2);
		lvParamsPanel.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
		this.add( graphTypePanel, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graphTypePanel.add( buttonPNvT, null );
		graphTypePanel.add( buttonPvN, null );
		this.add( modelType, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		modelType.add( lotkaVolterraButton, null );
		modelType.add(lvParamsPanel, null);
		lvParamsPanel.add(ddPreyBox, null);
		lvParamsPanel.add(type2Box, null);
		modelType.add( thetaLogisticButton, null );
		this.add( modelSpecificParamsTabbedPane, new GridBagConstraints( 2, 0, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		modelSpecificParamsTabbedPane.add( lotkaVolterraParams, res.getString( "Lotka_Volterra" ) );
		lotkaVolterraParams.add( preyPanel, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		preyPanel.add( paramLVN0, null );
		preyPanel.add( paramr1, null );
		preyPanel.add( paramC1, null );
		preyPanel.add( paramPreyK, null );
		lotkaVolterraParams.add( predatorPanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		predatorPanel.add( paramLVP0, null );
		predatorPanel.add( paramr2, null );
		predatorPanel.add( paramC2, null );
		predatorPanel.add(paramA, null);
		predatorPanel.add(paramTh, null);
		modelSpecificParamsTabbedPane.add( thetaLogisticParams, res.getString( "_u0398_Logistic" ) );
		thetaLogisticParams.add( predatorGrowthPanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		predatorGrowthPanel.add( paramTLP0, null );
		predatorGrowthPanel.add( paramD, null );
		predatorGrowthPanel.add( paramS, null );
		thetaLogisticParams.add( preyGrowthPanel, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		preyGrowthPanel.add( paramTLN0, null );
		preyGrowthPanel.add( paramR, null );
		preyGrowthPanel.add( paramK, null );
		preyGrowthPanel.add( paramTheta, null );
		thetaLogisticParams.add( frParamsPanel, new GridBagConstraints( 0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		frParamsPanel.add( typeComboBox, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		frParamsPanel.add( paramC, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
		frParamsPanel.add( paramh, new GridBagConstraints( 2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
		this.add( runningTimePanel1, new GridBagConstraints( 0, 1, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		bg1.add( buttonPNvT );
		bg1.add( buttonPvN );
		bg2.add( lotkaVolterraButton );
		bg2.add( thetaLogisticButton );
		this.registerChildren( this );
	}
}
