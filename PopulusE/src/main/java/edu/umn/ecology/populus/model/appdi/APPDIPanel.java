/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdi;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class APPDIPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 8408782555799642209L;
	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdi.Res" );
	JPanel initialConditionsPanel = new JPanel();
	Border border1;
	ButtonGroup vs = new ButtonGroup();
	JPanel modelTypePanel = new JPanel();
	PopulusParameterField QPF = new PopulusParameterField();
	JRadioButton npvstButton = new StyledRadioButton();
	PopulusParameterField T_PF = new PopulusParameterField();
	TitledBorder titledBorder1;
	PopulusParameterField aPF = new PopulusParameterField();
	SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	Border border2;
	PopulusParameterField cPF = new PopulusParameterField();
	TitledBorder titledBorder2;
	ButtonGroup bg = new ButtonGroup();
	Border border3;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	TitledBorder titledBorder3;
	PopulusParameterField mPF = new PopulusParameterField();
	Border border4;
	PopulusParameterField gensPF = new PopulusParameterField();
	TitledBorder titledBorder4;
	JPanel jPanel1 = new JPanel();
	PopulusParameterField n0PF = new PopulusParameterField();
	JPanel plotTypePanel = new JPanel();
	PopulusParameterField bPF = new PopulusParameterField();
	JRadioButton pvsnButton = new StyledRadioButton();
	PopulusParameterField lambdaPF = new PopulusParameterField();
	PopulusParameterField TwPF = new PopulusParameterField();
	JRadioButton clinearButton = new JRadioButton();
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	JRadioButton linearButton = new JRadioButton();
	PopulusParameterField p0PF = new PopulusParameterField();

	@Override
	public BasicPlot getPlotParamInfo() {
		return new APPDIParamInfo( n0PF.getDouble(), p0PF.getDouble(), lambdaPF.getDouble(), aPF.getDouble(), bPF.getDouble(), cPF.getDouble(), T_PF.getDouble(), TwPF.getDouble(), mPF.getDouble(), QPF.getDouble(), gensPF.getInt(), linearButton.isSelected(), npvstButton.isSelected() );
	}

	public APPDIPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public String getOutputGraphName() {

		/*
      if (clinearButton.isSelected()) {
      return "Continuous Exponential Growth r=" + bPF.getDouble();
      } else {
      return "Discrete Geometric Growth \u03BB=" + lambdaPF.getDouble();
      }
		 */
		return res.getString( "Discrete_Predator" );
	}

	/** Changes enablement of components */

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() instanceof JRadioButton ) {
			boolean cL = clinearButton.isSelected();
			QPF.setVisible( !cL );
			mPF.setVisible( !cL );
			aPF.setVisible( cL );
			bPF.setVisible( cL );
			cPF.setVisible( cL );
			T_PF.setVisible( cL );
			TwPF.setVisible( cL );
		}
		this.fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	protected boolean isContinuous() {
		return clinearButton.isSelected();
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
		border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
		border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
		border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder4 = new TitledBorder( border4, res.getString( "Plot_Type" ) );
		modelTypePanel.setBorder( titledBorder1 );
		modelTypePanel.setLayout( simpleVFlowLayout1 );
		initialConditionsPanel.setBorder( titledBorder3 );
		initialConditionsPanel.setLayout( gridBagLayout2 );
		n0PF.setCurrentValue( 25.0 );
		n0PF.setDefaultValue( 25.0 );
		n0PF.setHelpText( res.getString( "Initial_Population" ) );
		n0PF.setIncrementAmount( 1.0 );
		n0PF.setMaxValue( 999.0 );
		n0PF.setMinValue( 1.0 );
		n0PF.setParameterName( res.getString( "Prey_Size_i_N_i_sub_0" ) );
		clinearButton.setText( res.getString( "Curvilinear" ) );
		clinearButton.setFocusPainted( false );
		clinearButton.setActionCommand( res.getString( "continuous" ) );
		linearButton.setSelected( true );
		linearButton.setText( res.getString( "Linear" ) );
		linearButton.setFocusPainted( false );
		linearButton.setActionCommand( res.getString( "discrete" ) );
		lambdaPF.setCurrentValue( 2.0 );
		lambdaPF.setDefaultValue( 2.0 );
		lambdaPF.setIncrementAmount( 0.1 );
		lambdaPF.setMaxValue( 5.0 );
		lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.
		lambdaPF.setHelpText("The discrete growth factor per time step for the prey.");
		bPF.setCurrentValue( 0.1 );
		bPF.setDefaultValue( 0.1 );
		bPF.setIncrementAmount( 0.05 );
		bPF.setMaxValue( 1.0 );
		bPF.setMinValue( 0 );
		bPF.setParameterName( "<i>b</i>" );
		bPF.setHelpText("The instantaneous encounter rate between parasitoids. Values range from 0 to 1.");
		this.setLayout( gridBagLayout1 );
		p0PF.setParameterName( res.getString( "Predator_Size_i_P_i" ) );
		p0PF.setMinValue( 1.0 );
		p0PF.setMaxValue( 999.0 );
		p0PF.setIncrementAmount( 1.0 );
		p0PF.setHelpText( res.getString( "Initial_Population" ) );
		p0PF.setDefaultValue( 10.0 );
		p0PF.setCurrentValue( 10.0 );
		mPF.setCurrentValue( 0.4 );
		mPF.setDefaultValue( 0.4 );
		mPF.setIncrementAmount( 0.1 );
		mPF.setMaxValue( 1.0 );
		mPF.setParameterName( "<i>m</i>" );
		mPF.setHelpText("The interference constant, interpretable as the slope of the log-linear decline in search efficiency with parasitoid density.");
		aPF.setCurrentValue( 0.068 );
		aPF.setDefaultValue( 0.068 );
		aPF.setIncrementAmount( 0.05 );
		aPF.setMaxValue( 1.0 );
		aPF.setParameterName( "<i>a\'</i>" );
		aPF.setHelpText("The instantaneous search rate of the parasitoid during te available search time, T.");
		gensPF.setCurrentValue( 25.0 );
		gensPF.setDefaultValue( 25.0 );
		gensPF.setHelpText( res.getString( "Number_of_generations" ) );
		gensPF.setIncrementAmount( 5.0 );
		gensPF.setMaxValue( 999.0 );
		gensPF.setMinValue( 1.0 );
		gensPF.setParameterName( res.getString( "Generations_" ) );
		QPF.setCurrentValue( 0.2 );
		QPF.setDefaultValue( 0.2 );
		QPF.setMaxValue( 1.0 );
		QPF.setParameterName( "<i>Q</i>" );
		QPF.setHelpText("Scaling constant used in the linear model to specify the decline of search efficiency with increasing parasitoid density.");
		jPanel1.setLayout( gridBagLayout3 );
		pvsnButton.setText( "<i>P</i>  vs <i>N</i>" );
		pvsnButton.setFocusPainted( false );
		npvstButton.setSelected( true );
		npvstButton.setText( "<i>N</i>, <i>P</i>  vs <i>t</i>" );
		npvstButton.setFocusPainted( false );
		plotTypePanel.setBorder( titledBorder4 );
		cPF.setParameterName( "<i>c</i>" );
		cPF.setHelpText("The parasitoid's \"numerical respones.\" This is a constant indicating the parasitoid's efficiency in converting prey into offspring.");
		cPF.setMinValue( 0.0 );
		cPF.setMaxValue( 999.0 );
		cPF.setIncrementAmount( 0.05 );
		cPF.setDefaultValue( 1.0 );
		cPF.setCurrentValue( 1.0 );
		TwPF.setParameterName( "<i>T<sub>w</sub> </i>" );
		TwPF.setMinValue( 0.0 );
		TwPF.setMaxValue( 1.0 );
		TwPF.setIncrementAmount( 0.05 );
		TwPF.setDefaultValue( 0.4 );
		TwPF.setCurrentValue( 0.4 );
		TwPF.setHelpText("The potential search time wasted at each encounter between parasitoids.");
		T_PF.setParameterName( "<i>T</i>" );
		T_PF.setHelpText("The total search time available to an individual, which may take values from 0 to 1 full generation.");
		T_PF.setMinValue( 0.0 );
		T_PF.setMaxValue( 1.0 );
		T_PF.setIncrementAmount( 0.1 );
		T_PF.setDefaultValue( 1.0 );
		T_PF.setCurrentValue( 1.0 );
		this.add( modelTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
		modelTypePanel.add( linearButton, null );
		modelTypePanel.add( clinearButton, null );
		this.add( initialConditionsPanel, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 0, 0 ), 0, 0 ) );
		initialConditionsPanel.add( n0PF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 5, 0 ), 25, 0 ) );
		initialConditionsPanel.add( p0PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 2, 0 ), 0, 0 ) );
		this.add( gensPF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		this.add( jPanel1, new GridBagConstraints( 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 10, 0, 0, 0 ), 0, 0 ) );
		jPanel1.add( lambdaPF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		jPanel1.add( QPF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		jPanel1.add( mPF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		jPanel1.add( aPF, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 10, 5, 0 ), 0, 0 ) );
		jPanel1.add( bPF, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 10, 5, 0 ), 0, 0 ) );
		jPanel1.add( cPF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 10, 0, 0 ), 0, 0 ) );
		jPanel1.add( T_PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		jPanel1.add( TwPF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		aPF.setVisible( false );
		bPF.setVisible( false );
		cPF.setVisible( false );
		T_PF.setVisible( false );
		TwPF.setVisible( false );
		this.add( plotTypePanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
		plotTypePanel.add( npvstButton, null );
		plotTypePanel.add( pvsnButton, null );
		bg.add( linearButton );
		bg.add( clinearButton );
		vs.add( npvstButton );
		vs.add( pvsnButton );
		this.registerChildren( this );
	}

	/*
public int getTriggers() {
return 0;
}
//*/
}
