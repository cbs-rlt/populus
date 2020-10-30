/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lpg;

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1998

//Author:       Lars Roe

//Company:      University of Minnesota

//Description:  Populus
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

/**
 * Logistic Population Growth panel (aka Density Dependent)
 *
 *
 *
 */

public class LPGPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -2691060861159865609L;
	public static final int kDNNDTVSN = 3;
	public static final int kDNDTVSN = 2;
	public static final int kLNNVST = 1;
	public static final int kNVST = 0;
	public static final int kLNNTP1VSLNNT = 4;
	JPanel parametersPanel3 = new JPanel();
	PopulusParameterField paramN3 = new PopulusParameterField();
	PopulusParameterField paramr2 = new PopulusParameterField();
	StyledRadioButton nvstButton = new StyledRadioButton();
	StyledRadioButton lnNtp1vslnNtButton = new StyledRadioButton();
	SimpleVFlowLayout simpleVFlowLayout8 = new SimpleVFlowLayout();
	JPanel plotTypePanel = new JPanel();
	SimpleVFlowLayout simpleVFlowLayout4 = new SimpleVFlowLayout();
	PopulusParameterField paramT2 = new PopulusParameterField();
	JPanel graph2 = new JPanel();
	PopulusParameterField paramK3 = new PopulusParameterField();
	JPanel parametersPanel2 = new JPanel();
	PopulusParameterField paramT3 = new PopulusParameterField();
	JPanel leftPanel = new JPanel();
	PopulusParameterField paramr3 = new PopulusParameterField();
	PopulusParameterField paramK2 = new PopulusParameterField();
	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lpg.Res" );
	StyledRadioButton dNdtButton = new StyledRadioButton();
	JCheckBox two = new JCheckBox();
	JTabbedPane graphs = new JTabbedPane();
	JCheckBox four = new JCheckBox();
	JPanel graph4 = new JPanel();
	SimpleVFlowLayout simpleVFlowLayout6 = new SimpleVFlowLayout();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	PopulusParameterField paramN1 = new PopulusParameterField();
	LPGData[] myData;
	PopulusParameterField paramT1 = new PopulusParameterField();
	JPanel modelTypePanel = new JPanel();
	GridBagLayout gridBagLayout5 = new GridBagLayout();
	JPanel parametersPanel = new JPanel();
	TitledBorder titledBorder3;
	JRadioButton discrete = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
	JRadioButton laggedContinuous = new JRadioButton();
	JPanel rightPanel = new JPanel();
	JRadioButton continuous = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout5 = new SimpleVFlowLayout();
	int lastButtonSelected = LPGParamInfo.CONTINUOUS;
	TitledBorder titledBorder4;
	ButtonGroup bg = new ButtonGroup();
	StyledRadioButton lnnvstButton = new StyledRadioButton();
	SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	StyledRadioButton dNNdtButton = new StyledRadioButton();
	PopulusParameterField paramT = new PopulusParameterField();
	ButtonGroup bgPlotType = new ButtonGroup();
	PopulusParameterField paramr = new PopulusParameterField();
	JPanel graph1 = new JPanel();
	PopulusParameterField paramK = new PopulusParameterField();
	JPanel graph3 = new JPanel();
	PopulusParameterField paramN0 = new PopulusParameterField();
	JCheckBox one = new JCheckBox();
	Border border1;
	JCheckBox three = new JCheckBox();
	TitledBorder titledBorder1;
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
	JPanel parametersPanel1 = new JPanel();
	Border border2;
	PopulusParameterField paramK1 = new PopulusParameterField();
	TitledBorder titledBorder2;
	PopulusParameterField paramr1 = new PopulusParameterField();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	GridBagLayout gridBagLayout4 = new GridBagLayout();
	JPanel endtimePanel = new JPanel();
	PopulusParameterField paramN2 = new PopulusParameterField();
	PopulusParameterField paramTime = new PopulusParameterField();
	SimpleVFlowLayout simpleVFlowLayout7 = new SimpleVFlowLayout();
	Border border3;
	Border border4;
	static final String strnof0 = "<i>N</i>(0)";
	static final String strnsub0 = "<i>N</i><sub>0</sub>"; //"<i>N</i>\u2070";
	static final String str5 = "ln <i>N</i><sub><i>t</i>+1</sub> vs ln <i>N<sub>t</>";
	static final String str24 = "d<i>N</i>/<i>N</i>d<i>t</i> vs <i>N</i>";
	static final String str23 = "d<i>N</i>/d<i>t</i> vs <i>N</i>";
	//static final String str14 = "(<i>N<sub>t</i>+1</sub>-<i>N<sub>t</>)/<i>N<sub>t</> vs <i>N<sub>t</>";
	static final String str14 = "ln (<i>N</i><sub><i>t</i>+1</sub> / <i>N<sub>t</>) vs <i>N<sub>t</>";
	static final String str1 = "<i>N</i> vs <i>t</i>";
	static final String str2 = "ln (<i>N</i>) vs <i>t</i>";
	static final String str13 = "<i>N</i><sub><i>t</i>+1</>-<i>N<sub>t</> vs <i>N<sub>t</>";

	/** Changes enablement and states of components dependent on type */

	@Override
	public void actionPerformed( ActionEvent e ) {
		super.actionPerformed( e );
		if( ( e.getSource() instanceof JRadioButton ) && ( ( e.getSource() == continuous ) || ( e.getSource() == discrete ) || ( e.getSource() == laggedContinuous ) ) ) {
			if( !( (JRadioButton)e.getSource() ).isSelected() ) {
				return ;
			}
			int newButtonSelected = ( continuous.isSelected() ) ? LPGParamInfo.CONTINUOUS : ( ( discrete.isSelected() ) ? LPGParamInfo.DISCRETE : LPGParamInfo.LAGGED );
			if( newButtonSelected != lastButtonSelected ) {
				lastButtonSelected = newButtonSelected;
				paramT.setEnabled( newButtonSelected == LPGParamInfo.LAGGED );
				paramT1.setEnabled( newButtonSelected == LPGParamInfo.LAGGED );
				paramT2.setEnabled( newButtonSelected == LPGParamInfo.LAGGED );
				paramT3.setEnabled( newButtonSelected == LPGParamInfo.LAGGED );
				if( newButtonSelected == LPGParamInfo.DISCRETE ) {
					dNdtButton.setText( str13 );
					dNNdtButton.setText( str14 );
					paramN0.setParameterName( strnsub0 );
					paramN1.setParameterName( strnsub0 );
					paramN2.setParameterName( strnsub0 );
					paramN3.setParameterName( strnsub0 );
					dNdtButton.setEnabled( true );
					dNNdtButton.setEnabled( true );
					lnNtp1vslnNtButton.setEnabled( true );
				}
				else {
					dNdtButton.setText( str23 );
					dNNdtButton.setText( str24 );
					paramN0.setParameterName( strnof0 );
					paramN1.setParameterName( strnof0 );
					paramN2.setParameterName( strnof0 );
					paramN3.setParameterName( strnof0 );
					lnNtp1vslnNtButton.setEnabled( false );
					if( lnNtp1vslnNtButton.isSelected() ) {
						nvstButton.setSelected( true );
					}
					if( newButtonSelected == LPGParamInfo.LAGGED ) {
						dNdtButton.setEnabled( false );
						dNNdtButton.setEnabled( false );
						if( ( dNdtButton.isSelected() ) || ( dNNdtButton.isSelected() ) ) {
							nvstButton.setSelected( true );
						}
					}
					else {
						dNdtButton.setEnabled( true );
						dNNdtButton.setEnabled( true );
					}
				}
				repack();
			}
		}

		//this must be here or else the graph labels get messed up
		this.fireModelPanelEvent( CHANGE_PLOT );
	}

	public LPGPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateLabels() {
		if( !discrete.isSelected() ) {
			graphs.setForegroundAt( 0, ColorScheme.colors[0] );
			graphs.setForegroundAt( 1, ColorScheme.colors[1] );
			graphs.setForegroundAt( 2, ColorScheme.colors[2] );
			graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		}
		else {
			graphs.setForegroundAt( 0, ColorScheme.colors[0] );
			graphs.setForegroundAt( 1, ColorScheme.colors[1] );
			graphs.setForegroundAt( 2, ColorScheme.colors[2] );
			graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		}
	}

	@Override
	public String getOutputGraphName() {
		return res.getString( "Density_Dependent" );

		/*
   if (discrete.isSelected()) {
   return "Discrete Logistic Population Growth";
   } else if (continuous.isSelected()) {
   return "Continuous Logistic Population Growth";
   } else {
   return "Lagged Logistic Population Growth";
   }
		 */
	}

	/** Returns Paraminfo */

	@Override
	public BasicPlot getPlotParamInfo() {
		int plotType, modelType;
		modelType = continuous.isSelected() ? LPGParamInfo.CONTINUOUS : discrete.isSelected() ? LPGParamInfo.DISCRETE : LPGParamInfo.LAGGED;
		plotType = nvstButton.isSelected() ? kNVST : lnnvstButton.isSelected() ? kLNNVST : dNdtButton.isSelected() ? kDNDTVSN : dNNdtButton.isSelected() ? kDNNDTVSN : kLNNTP1VSLNNT;

		myData = new LPGData[4];
		int numToGraph = 0;
		if( one.isSelected() ) {
			myData[0] = new LPGData(paramN0.getCurrentValue(), paramK.getCurrentValue(), paramr.getCurrentValue(), paramT.getCurrentValue());
			numToGraph++;
		}
		else {
			myData[0] = null;
		}
		if( two.isSelected() ) {
			myData[1] = new LPGData(paramN1.getCurrentValue(), paramK1.getCurrentValue(), paramr1.getCurrentValue(), paramT1.getCurrentValue());
			numToGraph++;
		}
		else {
			myData[1] = null;
		}
		if( three.isSelected() ) {
			myData[2] = new LPGData(paramN2.getCurrentValue(), paramK2.getCurrentValue(), paramr2.getCurrentValue(), paramT2.getCurrentValue());
			numToGraph++;
		}
		else {
			myData[2] = null;
		}
		if( four.isSelected() ) {
			myData[3] = new LPGData(paramN3.getCurrentValue(), paramK3.getCurrentValue(), paramr3.getCurrentValue(), paramT3.getCurrentValue());
			numToGraph++;
		}
		else {
			myData[3] = null;
		}
		return new LPGParamInfo( myData, numToGraph, modelType, plotType, paramTime.getInt());
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder1 = new TitledBorder( border1, res.getString( "Parameters" ) );
		border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder2 = new TitledBorder( border2, res.getString( "Model_Type" ) );
		border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder3 = new TitledBorder( border3, res.getString( "Terminating_Time" ) );
		border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder4 = new TitledBorder( border4, res.getString( "Plot_Type" ) );
		discrete.setText( res.getString( "Discrete_Logistic" ) );
		discrete.setFocusPainted( false );
		laggedContinuous.setText( res.getString( "Lagged_Logistic" ) );
		laggedContinuous.setFocusPainted( false );
		continuous.setSelected( true );
		continuous.setText( res.getString( "Continuous_Logistic" ) );
		continuous.setFocusPainted( false );
		modelTypePanel.setLayout( simpleVFlowLayout1 );
		parametersPanel.setBorder( titledBorder1 );
		parametersPanel.setLayout( simpleVFlowLayout2 );
		modelTypePanel.setBorder( titledBorder2 );
		paramN0.setCurrentValue( 5.0 );
		paramN0.setDefaultValue( 5.0 );
		paramN0.setHelpText( res.getString( "Initial_Population" ) );
		paramN0.setIncrementAmount( 1.0 );
		paramN0.setMaxValue( 999.0 );
		paramN0.setMinValue( 1.0 );
		paramN0.setParameterName("<i>N</i>(0)" );
		paramK.setCurrentValue( 500.0 );
		paramK.setDefaultValue( 500.0 );
		paramK.setHelpText( res.getString( "Maximum_sustainable" ) + res.getString( "capacity" ) );
		paramK.setIncrementAmount( 10.0 );
		paramK.setMaxValue(99999.0 );
		paramK.setMinValue( 1.0 );
		paramK.setParameterName( "<i>K</i>" );
		paramr.setCurrentValue( 0.2 );
		paramr.setDefaultValue( 0.2 );
		paramr.setHelpText( res.getString( "Intrinsic_growth_rate" ) );
		paramr.setIncrementAmount( 0.05 );
		paramr.setMaxValue( 5.0 );
		paramr.setParameterName( "<i>r </i>" );
		paramT.setCurrentValue( 2.0 );
		paramT.setDefaultValue( 2.0 );
		paramT.setEnabled( false );
		paramT.setHelpText( res.getString( "Feedback_lag" ) );
		paramT.setMaxValue( 10.0 );
		paramT.setParameterName( "\u03c4" );
		this.setLayout( gridBagLayout1 );
		paramTime.setCurrentValue( 50.0 );
		paramTime.setDefaultValue( 50.0 );
		paramTime.setHelpText( res.getString( "Number_of_generations" ) );
		paramTime.setIncrementAmount( 20.0 );
		paramTime.setMaxValue( 500.0 );
		paramTime.setMinValue( 1.0 );
		paramTime.setParameterName( res.getString( "Run_Time" ) );
		endtimePanel.setBorder( titledBorder3 );
		titledBorder3.setTitle( res.getString( "Termination" ) );
		nvstButton.setSelected( true );
		nvstButton.setText( str1 );
		plotTypePanel.setLayout( simpleVFlowLayout3 );
		leftPanel.setLayout( simpleVFlowLayout4 );
		rightPanel.setLayout( simpleVFlowLayout5 );
		lnnvstButton.setText( str2 );
		plotTypePanel.setBorder( titledBorder4 );
		dNdtButton.setText( str23 );
		dNNdtButton.setText( str24 );
		lnNtp1vslnNtButton.setText( str5 );
		lnNtp1vslnNtButton.setEnabled( false );

		//discrete.addActionListener(this);

		//laggedContinuous.addActionListener(this);

		//continuous.addActionListener(this);
		graph1.setLayout( gridBagLayout2 );
		one.setToolTipText( res.getString( "Graph_Model_A" ) );
		one.setSelected( true );
		two.setToolTipText( res.getString( "Overlay_Model_B" ) );
		three.setToolTipText( res.getString( "Overlay_Model_C" ) );
		four.setToolTipText( res.getString( "Overlay_Model_D" ) );
		paramN1.setParameterName("<i>N</i>(0)" );
		paramN1.setMinValue( 1.0 );
		paramN1.setMaxValue( 999.0 );
		paramN1.setIncrementAmount( 1.0 );
		paramN1.setHelpText( res.getString( "Initial_Population" ) );
		paramN1.setDefaultValue( 5.0 );
		paramN1.setCurrentValue( 5.0 );
		parametersPanel1.setLayout( simpleVFlowLayout6 );
		parametersPanel1.setBorder( titledBorder1 );
		paramK1.setCurrentValue( 500.0 );
		paramK1.setDefaultValue( 500.0 );
		paramK1.setHelpText( res.getString( "Maximum_sustainable1" ) + res.getString( "capacity" ) );
		paramK1.setIncrementAmount( 10.0 );
		paramK1.setMaxValue(99999.0 );
		paramK1.setMinValue( 1.0 );
		paramK1.setParameterName( "<i>K</i>" );
		paramT1.setCurrentValue( 2.0 );
		paramT1.setDefaultValue( 2.0 );
		paramT1.setEnabled( false );
		paramT1.setHelpText( res.getString( "Feedback_lag" ) );
		paramT1.setIncrementAmount( 1.0 );
		paramT1.setMaxValue( 10.0 );
		paramT1.setParameterName( "\u03c4" );
		paramr1.setCurrentValue( 0.2 );
		paramr1.setDefaultValue( 0.2 );
		paramr1.setHelpText( res.getString( "Intrinsic_growth_rate" ) );
		paramr1.setIncrementAmount( 0.05 );
		paramr1.setMaxValue( 5.0 );
		paramr1.setParameterName( "<i>r </i>" );
		graph2.setLayout( gridBagLayout3 );
		graph3.setLayout( gridBagLayout4 );
		graph4.setLayout( gridBagLayout5 );
		paramN2.setParameterName("<i>N</i>(0)" );
		paramN2.setMinValue( 1.0 );
		paramN2.setMaxValue( 999.0 );
		paramN2.setIncrementAmount( 1.0 );
		paramN2.setHelpText( res.getString( "Initial_Population" ) );
		paramN2.setDefaultValue( 5.0 );
		paramN2.setCurrentValue( 5.0 );
		parametersPanel2.setLayout( simpleVFlowLayout7 );
		parametersPanel2.setBorder( titledBorder1 );
		paramK2.setCurrentValue( 500.0 );
		paramK2.setDefaultValue( 500.0 );
		paramK2.setHelpText( res.getString( "Maximum_sustainable2" ) + res.getString( "capacity" ) );
		paramK2.setIncrementAmount( 10.0 );
		paramK2.setMaxValue(99999.0 );
		paramK2.setMinValue( 1.0 );
		paramK2.setParameterName( "<i>K</i>" );
		paramT2.setCurrentValue( 2.0 );
		paramT2.setDefaultValue( 2.0 );
		paramT2.setEnabled( false );
		paramT2.setHelpText( res.getString( "Feedback_lag" ) );
		paramT2.setIncrementAmount( 1.0 );
		paramT2.setMaxValue( 10.0 );
		paramT2.setParameterName( "\u03c4" );
		paramr2.setCurrentValue( 0.2 );
		paramr2.setDefaultValue( 0.2 );
		paramr2.setHelpText( res.getString( "Intrinsic_growth_rate" ) );
		paramr2.setIncrementAmount( 0.05 );
		paramr2.setMaxValue( 5.0 );
		paramr2.setParameterName( "<i>r </i>" );
		paramN3.setParameterName("<i>N</i>(0)" );
		paramN3.setMinValue( 1.0 );
		paramN3.setMaxValue( 999.0 );
		paramN3.setIncrementAmount( 1.0 );
		paramN3.setHelpText( res.getString( "Initial_Population" ) );
		paramN3.setDefaultValue( 5.0 );
		paramN3.setCurrentValue( 5.0 );
		parametersPanel3.setLayout( simpleVFlowLayout8 );
		parametersPanel3.setBorder( titledBorder1 );
		paramK3.setCurrentValue( 500.0 );
		paramK3.setDefaultValue( 500.0 );
		paramK3.setHelpText( res.getString( "Maximum_sustainable3" ) + res.getString( "capacity" ) );
		paramK3.setIncrementAmount( 10.0 );
		paramK3.setMaxValue( 9999.0 );
		paramK3.setMinValue( 1.0 );
		paramK3.setParameterName( "<i>K</i>" );
		paramT3.setCurrentValue( 2.0 );
		paramT3.setDefaultValue( 2.0 );
		paramT3.setEnabled( false );
		paramT3.setHelpText( res.getString( "Feedback_lag" ) );
		paramT3.setIncrementAmount( 1.0 );
		paramT3.setMaxValue( 10.0 );
		paramT3.setParameterName( "\u03c4" );
		paramr3.setCurrentValue( 0.2 );
		paramr3.setDefaultValue( 0.2 );
		paramr3.setHelpText( res.getString( "Intrinsic_growth_rate" ) );
		paramr3.setIncrementAmount( 0.05 );
		paramr3.setMaxValue( 5.0 );
		paramr3.setParameterName( "<i>r </i>" );
		this.add( leftPanel, new GridBagConstraints( 0, 0, 1, 2, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		leftPanel.add( modelTypePanel, null );
		modelTypePanel.add( continuous, null );
		modelTypePanel.add( laggedContinuous, null );
		modelTypePanel.add( discrete, null );
		leftPanel.add( plotTypePanel, null );
		plotTypePanel.add( nvstButton, null );
		plotTypePanel.add( lnnvstButton, null );
		plotTypePanel.add( dNdtButton, null );
		plotTypePanel.add( dNNdtButton, null );
		plotTypePanel.add( lnNtp1vslnNtButton, null );
		this.add( rightPanel, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		rightPanel.add( endtimePanel, null );
		endtimePanel.add( paramTime, null );
		this.add( graphs, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 30, 0, 30 ), 0, 0 ) );
		graphs.add( graph1, "  A  " );
		graph1.add( one, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph1.add( parametersPanel, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		parametersPanel.add( paramN0, null );
		parametersPanel.add( paramK, null );
		parametersPanel.add( paramr, null );
		parametersPanel.add( paramT, null );
		graphs.add( graph2, "  B  " );
		graph2.add( parametersPanel1, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		parametersPanel1.add( paramN1, null );
		parametersPanel1.add( paramK1, null );
		parametersPanel1.add( paramr1, null );
		parametersPanel1.add( paramT1, null );
		graph2.add( two, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graphs.add( graph3, "  C  " );
		graph3.add( three, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph3.add( parametersPanel2, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		parametersPanel2.add( paramN2, null );
		parametersPanel2.add( paramK2, null );
		parametersPanel2.add( paramr2, null );
		parametersPanel2.add( paramT2, null );
		graphs.add( graph4, "  D  " );
		graph4.add( four, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph4.add( parametersPanel3, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graphs.setForegroundAt( 0, ColorScheme.colors[0] );
		graphs.setForegroundAt( 1, ColorScheme.colors[1] );
		graphs.setForegroundAt( 2, ColorScheme.colors[2] );
		graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		parametersPanel3.add( paramN3, null );
		parametersPanel3.add( paramK3, null );
		parametersPanel3.add( paramr3, null );
		parametersPanel3.add( paramT3, null );
		bg.add( continuous );
		bg.add( discrete );
		bg.add( laggedContinuous );
		bgPlotType.add( nvstButton );
		bgPlotType.add( lnnvstButton );
		bgPlotType.add( dNdtButton );
		bgPlotType.add( dNNdtButton );
		bgPlotType.add( lnNtp1vslnNtButton );
		registerChildren( this );
	}
}
