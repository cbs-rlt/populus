/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.cp;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import javax.swing.border.*;
import java.util.*;

public class CPPanel extends BasicPlotInputPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -2681157339951149355L;
	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.cp.Res" );
	Border border1;
	TitledBorder titledBorder1;
	JPanel initialConditionsPanel = new JPanel();
	JPanel jPanel1 = new JPanel();
	JRadioButton vsButton = new StyledRadioButton();
	PopulusParameterField sPF = new PopulusParameterField();
	PopulusParameterField q0PF = new PopulusParameterField();
	Border border2;
	JPanel plotTypePanel = new JPanel();
	TitledBorder titledBorder2;
	GridBagLayout gridBagLayout3 = new GridBagLayout();
	Border border3;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	TitledBorder titledBorder3;
	PopulusParameterField a1PF = new PopulusParameterField();
	Border border4;
	PopulusParameterField gensPF = new PopulusParameterField();
	TitledBorder titledBorder4;
	GridBagLayout gridBagLayout2 = new GridBagLayout();
	PopulusParameterField p0PF = new PopulusParameterField();
	JRadioButton npqvstButton = new StyledRadioButton();
	PopulusParameterField k1PF = new PopulusParameterField();
	PopulusParameterField k2PF = new PopulusParameterField();
	PopulusParameterField lambdaPF = new PopulusParameterField();
	PopulusParameterField n0PF = new PopulusParameterField();
	ButtonGroup bg = new ButtonGroup();
	SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	ButtonGroup vs = new ButtonGroup();
	PopulusParameterField a2PF = new PopulusParameterField();

	@Override
	public BasicPlot getPlotParamInfo() {
		if( vsButton.isSelected() ) {
			return new CP3DParamInfo( n0PF.getDouble(), p0PF.getDouble(), q0PF.getDouble(), lambdaPF.getDouble(), a1PF.getDouble(), a2PF.getDouble(), k1PF.getDouble(), k2PF.getDouble(), gensPF.getInt(), npqvstButton.isSelected() );
		}
		return new CPParamInfo( n0PF.getDouble(), p0PF.getDouble(), q0PF.getDouble(), lambdaPF.getDouble(), a1PF.getDouble(), a2PF.getDouble(), k1PF.getDouble(), k2PF.getDouble(), gensPF.getInt(), npqvstButton.isSelected() );
	}

	public CPPanel() {
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

		}
		this.fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
	}

	protected boolean isContinuous() {
		return true;
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
		initialConditionsPanel.setBorder( titledBorder3 );
		initialConditionsPanel.setLayout( gridBagLayout2 );
		p0PF.setCurrentValue( 10.0 );
		p0PF.setDefaultValue( 10.0 );
		p0PF.setHelpText( res.getString( "Initial_Population" ) );
		p0PF.setIncrementAmount( 1.0 );
		p0PF.setMaxValue( 999.0 );
		p0PF.setMinValue( 1.0 );
		p0PF.setParameterName( res.getString( "Predator_1_Size_i_P_i" ) );
		lambdaPF.setCurrentValue( 2.0 );
		lambdaPF.setDefaultValue( 2.0 );
		lambdaPF.setIncrementAmount( 0.1 );
		lambdaPF.setMaxValue( 5.0 );
		lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.
		lambdaPF.setHelpText("The discrete growth factor per time step for the prey.");
		k1PF.setCurrentValue( 0.4 );
		k1PF.setDefaultValue( 0.4 );
		k1PF.setIncrementAmount( 0.1 );
		k1PF.setMaxValue( 999.0 );
		k1PF.setMinValue( 0 );
		k1PF.setParameterName("<i>k</i><sub>1</sub>" );
		k1PF.setHelpText("The clumping parameter of the negative binomial parasitoid distributions.");
		this.setLayout( gridBagLayout1 );
		q0PF.setParameterName( res.getString( "Predator_2_Size_i_Q_i" ) );
		q0PF.setMinValue( 1.0 );
		q0PF.setMaxValue( 999.0 );
		q0PF.setIncrementAmount( 1.0 );
		q0PF.setHelpText( res.getString( "Initial_Population" ) );
		q0PF.setDefaultValue( 10.0 );
		q0PF.setCurrentValue( 10.0 );
		a1PF.setCurrentValue( 0.07 );
		a1PF.setDefaultValue( 0.07 );
		a1PF.setIncrementAmount( 0.1 );
		a1PF.setMaxValue( 1.0 );
		a1PF.setParameterName("<i>a</i><sub>1</sub>" );
		a1PF.setHelpText("The \"area of discovery\" of the parasitoid. Values can range from 0 to 1.");
		a2PF.setCurrentValue( 0.12 );
		a2PF.setDefaultValue( 0.12 );
		a2PF.setIncrementAmount( 0.1 );
		a2PF.setMaxValue( 1.0 );
		a2PF.setParameterName("<i>a</i><sub>2</sub>" );
		a2PF.setHelpText("The \"area of discovery\" of the parasitoid. Values can range from 0 to 1.");
		gensPF.setCurrentValue( 200.0 );
		gensPF.setDefaultValue( 200.0 );
		gensPF.setHelpText( res.getString( "Number_of_generations" ) );
		gensPF.setIncrementAmount( 5.0 );
		gensPF.setMaxValue( 999.0 );
		gensPF.setMinValue( 1.0 );
		gensPF.setParameterName( res.getString( "Generations_" ) );
		jPanel1.setLayout( gridBagLayout3 );
		vsButton.setToolTipText( "" );
		vsButton.setText( "<i>N</i>  vs <i>P</i>  vs <i>Q</i>" );
		vsButton.setFocusPainted( false );
		npqvstButton.setSelected( true );
		npqvstButton.setText( "<i>N</i>, <i>P</i>, <i>Q</i>  vs <i>t</i>" );
		npqvstButton.setFocusPainted( false );
		plotTypePanel.setBorder( titledBorder4 );
		plotTypePanel.setLayout( simpleVFlowLayout1 );
		k2PF.setParameterName("<i>k</i><sub>2</sub>" );
		k2PF.setMinValue( 0.0 );
		k2PF.setMaxValue( 999.0 );
		k2PF.setIncrementAmount( 0.1 );
		k2PF.setDefaultValue( 0.6 );
		k2PF.setCurrentValue( 0.6 );
		k2PF.setHelpText("The clumping parameter of the negative binomial parasitoid distributions.");
		n0PF.setParameterName( res.getString( "Prey_Size_i_N_i_sub_0" ) );
		n0PF.setMinValue( 1.0 );
		n0PF.setMaxValue( 999.0 );
		n0PF.setIncrementAmount( 1.0 );
		n0PF.setHelpText( res.getString( "Initial_Population" ) );
		n0PF.setDefaultValue( 25.0 );
		n0PF.setCurrentValue( 25.0 );
		sPF.setCurrentValue( 1.0 );
		sPF.setDefaultValue( 1.0 );
		sPF.setIncrementAmount( 0.1 );
		sPF.setMaxValue( 1.0 );
		sPF.setMinValue( 0.0 );
		sPF.setParameterName( "<i>s</i>" );
		sPF.setVisible( false );
		this.add( initialConditionsPanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 0, 0 ), 0, 0 ) );
		initialConditionsPanel.add( p0PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		initialConditionsPanel.add( q0PF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 5, 2, 0 ), 0, 0 ) );
		initialConditionsPanel.add( n0PF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		this.add( gensPF, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		this.add( jPanel1, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 10, 0, 0, 0 ), 0, 0 ) );
		jPanel1.add( lambdaPF, new GridBagConstraints( 0, 0, 2, 1, 2.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 10, 0 ), 0, 0 ) );
		jPanel1.add( a1PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		jPanel1.add( a2PF, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 5, 5, 0 ), 0, 0 ) );
		jPanel1.add( k1PF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		jPanel1.add( k2PF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
		jPanel1.add( sPF, new GridBagConstraints( 1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 20, 0, 0, 0 ), 0, 0 ) );
		this.add( plotTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
		plotTypePanel.add( npqvstButton, null );
		plotTypePanel.add( vsButton, null );
		vs.add( npqvstButton );
		vs.add( vsButton );
		this.registerChildren( this );
	}

	/*
public int getTriggers() {
return 0;
}
//*/
}
