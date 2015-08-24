/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnrs;
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

public class APPDNRSPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1613010581333897609L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdnrs.Res" );
   JPanel modelTypePanel = new JPanel();
   JPanel initialConditionsPanel = new JPanel();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   ButtonGroup plotGroup = new ButtonGroup();
   PopulusParameterField K_PF = new PopulusParameterField();
   JPanel plotTypePanel = new JPanel();
   JRadioButton densityIndependentButton = new JRadioButton();
   Border border1;
   SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
   TitledBorder titledBorder1;
   PopulusParameterField kPF = new PopulusParameterField();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   JRadioButton densityDependentButton = new JRadioButton();
   Border border2;
   ButtonGroup bg = new ButtonGroup();
   TitledBorder titledBorder2;
   PopulusParameterField p0PF = new PopulusParameterField();
   Border border3;
   PopulusParameterField aPF = new PopulusParameterField();
   TitledBorder titledBorder3;
   PopulusParameterField gensPF = new PopulusParameterField();
   Border border4;
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   TitledBorder titledBorder4;
   JRadioButton pvsnButton = new StyledRadioButton();
   PopulusParameterField n0PF = new PopulusParameterField();
   JRadioButton npvstButton = new StyledRadioButton();
   PopulusParameterField lambdaPF = new PopulusParameterField();
   JPanel modeDDParametersPanel = new JPanel();

   public BasicPlot getPlotParamInfo() {
      boolean ind = this.densityIndependentButton.isSelected();
      return new APPDNRSParamInfo( n0PF.getDouble(), p0PF.getDouble(), lambdaPF.getDouble(), aPF.getDouble(), kPF.getDouble(), K_PF.getDouble(), gensPF.getInt(), ind, npvstButton.isSelected() );
   }

   public APPDNRSPanel() {
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
      return "Continuous Exponential Growth r=" + aPF.getDouble();
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
         K_PF.setEnabled( cE );
         K_PF.setVisible( cE );
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


   //cardLayout1.first(jPanel1);
   }

   void densityDependentButton_actionPerformed( ActionEvent e ) {


   //cardLayout1.last(jPanel1);
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border3, res.getString( "Plot_Type" ) );
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
      lambdaPF.setHelpText("The discrete growth factor per time step for the prey.");
      lambdaPF.setIncrementAmount( 0.1 );
      lambdaPF.setMaxValue( 5.0 );
      lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.

      //aDIPF.setHelpText("");
      this.setLayout( gridBagLayout1 );
      p0PF.setParameterName( res.getString( "Predator_Size_i_P_i" ) );
      p0PF.setMinValue( 1.0 );
      p0PF.setMaxValue( 999.0 );
      p0PF.setIncrementAmount( 1.0 );
      p0PF.setHelpText( res.getString( "Initial_Population" ) );
      p0PF.setDefaultValue( 10.0 );
      p0PF.setCurrentValue( 10.0 );
      modeDDParametersPanel.setLayout( simpleVFlowLayout3 );

      //rPF.setHelpText("Rate of population growth.\nPopulation at time n + 1 is equal to " +

      //"the\npopulation at time n times this parameter");
      aPF.setCurrentValue( 0.068 );
      aPF.setDefaultValue( 0.068 );

      //aDDPF.setHelpText("");
      aPF.setIncrementAmount( 0.05 );
      aPF.setMaxValue( 1.0 );
      aPF.setParameterName( "<i>a\'</i>" );
      aPF.setHelpText("The \"area of discovery\" of the parasitoids.");
      gensPF.setCurrentValue( 25.0 );
      gensPF.setDefaultValue( 50.0 );
      gensPF.setHelpText( res.getString( "Number_of_generations" ) );
      gensPF.setIncrementAmount( 5.0 );
      gensPF.setMaxValue( 999.0 );
      gensPF.setMinValue( 1.0 );
      gensPF.setParameterName( res.getString( "Generations_" ) );
      K_PF.setCurrentValue( 200.0 );
      K_PF.setDefaultValue( 200.0 );
      K_PF.setEnabled( false );
      K_PF.setVisible( false );
      K_PF.setMaxValue( 999.0 );
      K_PF.setParameterName( "<i>K</i>" );
      K_PF.setHelpText("The prey carrying capacity.");
      modeDDParametersPanel.addComponentListener( new java.awt.event.ComponentAdapter()  {

         public void componentShown( ComponentEvent e ) {
            modeDDParametersPanel_componentShown( e );
         }
      } );
      kPF.setParameterName( "<i>k</i>" );
      kPF.setHelpText("The dumping parameter of the negative binomial distribution.");
      kPF.setMaxValue( 999.0 );
      kPF.setDefaultValue( 0.3 );
      kPF.setCurrentValue( 0.3 );
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
      this.add( gensPF, new GridBagConstraints( 2, 2, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      this.add( modeDDParametersPanel, new GridBagConstraints( 2, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 27, 0 ) );
      modeDDParametersPanel.add( lambdaPF, null );
      modeDDParametersPanel.add( aPF, null );
      modeDDParametersPanel.add( kPF, null );
      modeDDParametersPanel.add( K_PF, null );
      this.add( plotTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      plotTypePanel.add( npvstButton, null );
      plotTypePanel.add( pvsnButton, null );
      bg.add( densityIndependentButton );
      bg.add( densityDependentButton );
      plotGroup.add( npvstButton );
      plotGroup.add( pvsnButton );
      this.registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
//*/
}
