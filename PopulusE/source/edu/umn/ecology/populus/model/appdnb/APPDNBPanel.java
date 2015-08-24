/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnb;
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

public class APPDNBPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5423494175501386639L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnb.Res" );
   JPanel modelDIParametersPanel = new JPanel();
   JPanel initialConditionsPanel = new JPanel();
   JRadioButton densityIndependentButton = new JRadioButton();
   ButtonGroup vsT = new ButtonGroup();
   PopulusParameterField rPF = new PopulusParameterField();
   PopulusParameterField kPF = new PopulusParameterField();
   StyledRadioButton pvsnButton = new StyledRadioButton();
   Border border1;
   JPanel modelTypePanel = new JPanel();
   TitledBorder titledBorder1;
   SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   CardLayout cardLayout1 = new CardLayout();
   SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
   StyledRadioButton npvstButton = new StyledRadioButton();
   Border border2;
   ButtonGroup bg = new ButtonGroup();
   TitledBorder titledBorder2;
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   Border border3;
   JPanel modeDDParametersPanel = new JPanel();
   TitledBorder titledBorder3;
   PopulusParameterField aDDPF = new PopulusParameterField();
   Border border4;
   PopulusParameterField gensPF = new PopulusParameterField();
   TitledBorder titledBorder4;
   JPanel jPanel1 = new JPanel();
   PopulusParameterField n0PF = new PopulusParameterField();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   PopulusParameterField aDIPF = new PopulusParameterField();
   JPanel plotTypePanel = new JPanel();
   PopulusParameterField lambdaPF = new PopulusParameterField();
   JRadioButton densityDependentButton = new JRadioButton();
   PopulusParameterField p0PF = new PopulusParameterField();

   public BasicPlot getPlotParamInfo() {
      boolean ind = this.densityIndependentButton.isSelected();
      return new APPDNBParamInfo( n0PF.getDouble(), p0PF.getDouble(), lambdaPF.getDouble(), ind ? aDIPF.getDouble() : aDDPF.getDouble(), rPF.getDouble(), kPF.getDouble(), gensPF.getInt(), ind, npvstButton.isSelected() );
   }

   public APPDNBPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public String getOutputGraphName() {

      /*
      if (densityDependentButton.isSelected()) {
      return "Continuous Exponential Growth r=" + aDIPF.getDouble();
      } else {
      return "Discrete Geometric Growth \u03BB=" + lambdaPF.getDouble();
      }
      */
      return res.getString( "Discrete_Predator" );
   }

   /** Changes enablement of components */

   public void actionPerformed( ActionEvent e ) {
      if( e.getSource() instanceof JRadioButton ) {
         boolean cE = densityDependentButton.isSelected();
         aDIPF.setEnabled( !cE );
         lambdaPF.setEnabled( !cE );

      /*
      if (!cE)
      aDIPF.setCurrentValue(Math.log(lambdaPF.getCurrentValue()));
      else
      lambdaPF.setCurrentValue(Math.exp(aDIPF.getCurrentValue()));

      */
      }
      this.fireModelPanelEvent( ModelPanelEvent.CHANGE_PLOT );
   }

   protected boolean isContinuous() {
      return densityDependentButton.isSelected();
   }

   void modeDDParametersPanel_componentShown( ComponentEvent e ) {
      densityDependentButton.setSelected( true );
   }

   void modelDIParametersPanel_componentShown( ComponentEvent e ) {
      densityIndependentButton.setSelected( true );
   }

   void densityIndependentButton_actionPerformed( ActionEvent e ) {
      cardLayout1.first( jPanel1 );
   }

   void densityDependentButton_actionPerformed( ActionEvent e ) {
      cardLayout1.last( jPanel1 );
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
      modelDIParametersPanel.setLayout( simpleVFlowLayout2 );
      initialConditionsPanel.setBorder( titledBorder3 );
      initialConditionsPanel.setLayout( gridBagLayout2 );
      n0PF.setCurrentValue( 25.0 );
      n0PF.setDefaultValue( 25.0 );
      n0PF.setHelpText( res.getString( "Initial_Population" ) );
      n0PF.setIncrementAmount( 1.0 );
      n0PF.setMaxValue( 999.0 );
      n0PF.setMinValue( 1.0 );
      n0PF.setParameterName( res.getString( "Prey_Size_i_N_i_sub_0" ) );
      densityDependentButton.setText( res.getString( "Density_Dependent" ) );
      densityDependentButton.setFocusPainted( false );
      densityDependentButton.setActionCommand( res.getString( "continuous" ) );
      densityDependentButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            densityDependentButton_actionPerformed( e );
         }
      } );
      densityIndependentButton.setSelected( true );
      densityIndependentButton.setText( res.getString( "Density_Independent" ) );
      densityIndependentButton.setFocusPainted( false );
      densityIndependentButton.setActionCommand( res.getString( "discrete" ) );
      densityIndependentButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            densityIndependentButton_actionPerformed( e );
         }
      } );
      lambdaPF.setCurrentValue( 2.0 );
      lambdaPF.setDefaultValue( 2.0 );
      lambdaPF.setIncrementAmount( 0.1 );
      lambdaPF.setMaxValue( 10.0 );
      lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.
      lambdaPF.setHelpText("The discrete growth factor per time step for the prey");
      aDIPF.setCurrentValue( 0.068 );
      aDIPF.setDefaultValue( 0.068 );
      aDIPF.setHelpText("Nicholson called \"a\" the parasitoid's \"area of discovery.\"");
      aDIPF.setIncrementAmount( 0.05 );
      aDIPF.setMaxValue( 1.0 );
      aDIPF.setParameterName( "<i>a</i>" );
      this.setLayout( gridBagLayout1 );
      p0PF.setParameterName( res.getString( "Predator_Size_i_P_i" ) );
      p0PF.setMinValue( 1.0 );
      p0PF.setMaxValue( 999.0 );
      p0PF.setIncrementAmount( 1.0 );
      p0PF.setHelpText( res.getString( "Initial_Population" ) );
      p0PF.setDefaultValue( 10.0 );
      p0PF.setCurrentValue( 10.0 );
      modeDDParametersPanel.setLayout( simpleVFlowLayout3 );
      rPF.setCurrentValue( 0.693 );
      rPF.setDefaultValue( 0.693 );
      rPF.setHelpText("The intrinsic growth rate of the prey.");
      rPF.setIncrementAmount( 0.1 );
      rPF.setMaxValue( 10.0 );
      rPF.setParameterName( "<i>r</i>" );
      aDDPF.setCurrentValue( 0.068 );
      aDDPF.setDefaultValue( 0.068 );
      aDDPF.setHelpText("Nicholson called \"a\" the parasitoid's \"area of discovery.\" ");
      aDDPF.setIncrementAmount( 0.05 );
      aDDPF.setMaxValue( 1.0 );
      aDDPF.setParameterName( "<i>a</i>" );
      gensPF.setCurrentValue( 25.0 );
      gensPF.setDefaultValue( 25.0 );
      gensPF.setHelpText( res.getString( "Number_of_generations" ) );
      gensPF.setIncrementAmount( 5.0 );
      gensPF.setMaxValue( 999.0 );
      gensPF.setMinValue( 1.0 );
      gensPF.setParameterName( res.getString( "Run_Time_" ) );
      kPF.setCurrentValue( 50.0 );
      kPF.setDefaultValue( 50.0 );
      kPF.setMaxValue( 999.0 );
      kPF.setParameterName( "<i>K</i>" );
      kPF.setHelpText("The prey carrying capacity.");
      modeDDParametersPanel.addComponentListener( new java.awt.event.ComponentAdapter()  {

         public void componentShown( ComponentEvent e ) {
            modeDDParametersPanel_componentShown( e );
         }
      } );
      modelDIParametersPanel.addComponentListener( new java.awt.event.ComponentAdapter()  {

         public void componentShown( ComponentEvent e ) {
            modelDIParametersPanel_componentShown( e );
         }
      } );
      jPanel1.setLayout( cardLayout1 );
      pvsnButton.setText( "<i>P</i>  vs <i>N</i>" );
      pvsnButton.setFocusPainted( false );
      npvstButton.setSelected( true );
      npvstButton.setText( "<i>N</i>, <i>P</i>  vs <i>t</i>" );
      npvstButton.setFocusPainted( false );
      plotTypePanel.setBorder( titledBorder4 );
      this.add( modelTypePanel, new GridBagConstraints( 0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      modelTypePanel.add( densityIndependentButton, null );
      modelTypePanel.add( densityDependentButton, null );
      this.add( initialConditionsPanel, new GridBagConstraints( 0, 2, 2, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 0, 0 ), 0, 0 ) );
      initialConditionsPanel.add( n0PF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 5, 0 ), 25, 0 ) );
      initialConditionsPanel.add( p0PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 2, 0 ), 0, 0 ) );
      this.add( gensPF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      this.add( jPanel1, new GridBagConstraints( 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 0, 0, 0 ), 0, 0 ) );
      jPanel1.add( modelDIParametersPanel, "modelDIParametersPanel" );
      modelDIParametersPanel.add( lambdaPF, null );
      modelDIParametersPanel.add( aDIPF, null );
      jPanel1.add( modeDDParametersPanel, "modeDDParametersPanel" );
      modeDDParametersPanel.add( kPF, null );
      modeDDParametersPanel.add( rPF, null );
      modeDDParametersPanel.add( aDDPF, null );
      this.add( plotTypePanel, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      plotTypePanel.add( npvstButton, null );
      plotTypePanel.add( pvsnButton, null );
      vsT.add( pvsnButton );
      vsT.add( npvstButton );
      bg.add( densityIndependentButton );
      bg.add( densityDependentButton );
      this.registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
//*/
}
