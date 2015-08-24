/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdfr;
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

public class APPDFRPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4146293368343423055L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdfr.Res" );
   JPanel initialConditionsPanel = new JPanel();
   Border border1;
   JPanel paramsPanel = new JPanel();
   JPanel modelTypePanel = new JPanel();
   JRadioButton type2Button = new JRadioButton();
   TitledBorder titledBorder4;
   SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
   TitledBorder titledBorder1;
   PopulusParameterField bPF = new PopulusParameterField();
   Border border2;
   JPanel plotTypePanel = new JPanel();
   TitledBorder titledBorder2;
   StyledRadioButton npvstButton = new StyledRadioButton();
   Border border3;
   PopulusParameterField gensPF = new PopulusParameterField();
   TitledBorder titledBorder3;
   PopulusParameterField tPF = new PopulusParameterField();
   PopulusParameterField n0PF = new PopulusParameterField();
   PopulusParameterField apPF = new PopulusParameterField();
   PopulusParameterField lambdaPF = new PopulusParameterField();
   PopulusParameterField thPF = new PopulusParameterField();
   JRadioButton type3Button = new JRadioButton();
   FlowLayout flowLayout1 = new FlowLayout();
   JRadioButton type1Button = new JRadioButton();
   Border border4;
   ButtonGroup bg = new ButtonGroup();
   StyledRadioButton pvsnButton = new StyledRadioButton();
   ButtonGroup plotGroup = new ButtonGroup();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField p0PF = new PopulusParameterField();
   PopulusParameterField cPF = new PopulusParameterField();

   public BasicPlot getPlotParamInfo() {
      int type = type1Button.isSelected() ? 1 : ( type2Button.isSelected() ? 2 : 3 );
      return new APPDFRParamInfo( n0PF.getDouble(), p0PF.getDouble(), lambdaPF.getDouble(), apPF.getDouble(), cPF.getDouble(), tPF.getDouble(), thPF.getDouble(), bPF.getDouble(), gensPF.getInt(), type, npvstButton.isSelected() );
   }

   public APPDFRPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public String getOutputGraphName() {

      //return "Functional Responses";
      return res.getString( "Discrete_Predator" );
   }

   /** Changes enablement of components */

   public void actionPerformed( ActionEvent e ) {

      //if (e.getSource() instanceof JRadioButton) {
      this.fireModelPanelEvent( ModelPanelEvent.CHANGE_PLOT );
   }

   void type1Button_actionPerformed( ActionEvent e ) {
      apPF.setEnabled( true );
      thPF.setEnabled( false );
      bPF.setEnabled( false );
      apPF.setVisible( true );
      thPF.setVisible( false );
      bPF.setVisible( false );
   }

   void type2Button_actionPerformed( ActionEvent e ) {
      apPF.setEnabled( true );
      thPF.setEnabled( true );
      bPF.setEnabled( false );
      apPF.setVisible( true );
      thPF.setVisible( true );
      bPF.setVisible( false );
   }

   void type3Button_actionPerformed( ActionEvent e ) {
      apPF.setEnabled( false );
      thPF.setEnabled( true );
      bPF.setEnabled( true );
      apPF.setVisible( false );
      thPF.setVisible( true );
      bPF.setVisible( true );
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border4, res.getString( "Axes" ) );
      modelTypePanel.setBorder( titledBorder1 );
      modelTypePanel.setLayout( flowLayout1 );
      initialConditionsPanel.setBorder( titledBorder3 );
      initialConditionsPanel.setLayout( simpleVFlowLayout2 );
      n0PF.setCurrentValue( 25.0 );
      n0PF.setDefaultValue( 25.0 );
      n0PF.setHelpText( res.getString( "Initial_Population" ) );
      n0PF.setIncrementAmount( 1.0 );
      n0PF.setMaxValue( 999.0 );
      n0PF.setMinValue( 1.0 );
      n0PF.setParameterName( res.getString( "Prey_Size_i_N_i_sub_0" ) );
      type3Button.setText( res.getString( "Type_III" ) );
      type3Button.setFocusPainted( false );
      type3Button.setActionCommand( res.getString( "continuous" ) );
      type3Button.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            type3Button_actionPerformed( e );
         }
      } );
      type1Button.setSelected( true );
      type1Button.setText( res.getString( "Type_I" ) );
      type1Button.setFocusPainted( false );
      type1Button.setActionCommand( res.getString( "discrete" ) );
      type1Button.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            type1Button_actionPerformed( e );
         }
      } );
      lambdaPF.setCurrentValue( 2.0 );
      lambdaPF.setDefaultValue( 2.0 );

      lambdaPF.setHelpText("The discrete growth factor per time step for the prey.");
      lambdaPF.setIncrementAmount( 0.1 );
      lambdaPF.setMaxValue( 5.0 );
      lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.

      //aDIPF.setHelpText("The parasitoid's instantaneous search rate, with permissible values ranging from 0 to 1.");
      this.setLayout( gridBagLayout1 );
      p0PF.setParameterName( res.getString( "Predator_Size_i_P_i" ) );
      p0PF.setMinValue( 1.0 );
      p0PF.setMaxValue( 999.0 );
      p0PF.setIncrementAmount( 1.0 );
      p0PF.setHelpText( res.getString( "Initial_Population" ) );
      p0PF.setDefaultValue( 10.0 );
      p0PF.setCurrentValue( 10.0 );

      //rPF.setHelpText("Rate of population growth.\nPopulation at time n + 1 is equal to " +

      //"the\npopulation at time n times this parameter");

      //aDDPF.setHelpText("");
      gensPF.setCurrentValue( 25.0 );
      gensPF.setDefaultValue( 25.0 );
      gensPF.setHelpText( res.getString( "Number_of_generations" ) );
      gensPF.setIncrementAmount( 5.0 );
      gensPF.setMaxValue( 999.0 );
      gensPF.setMinValue( 1.0 );
      gensPF.setParameterName( res.getString( "Generations_" ) );
      paramsPanel.setLayout( simpleVFlowLayout1 );
      apPF.setCurrentValue( 0.1 );
      apPF.setDefaultValue( 0.1 );
      apPF.setMaxValue( 1.0 );
      apPF.setParameterName( "<i>a\' </i>" );
      apPF.setHelpText("The parasitoid's instantaneous search rate, with permissible values ranging from 0 to 1.");
      cPF.setCurrentValue( 1.0 );
      cPF.setDefaultValue( 1.0 );
      cPF.setMaxValue( 1000.0 );
      cPF.setParameterName( "<i>c</i>" );
      cPF.setHelpText("The parasitoid numerical response with a maximum value of 103.");
      tPF.setCurrentValue( 1.0 );
      tPF.setDefaultValue( 1.0 );
      tPF.setIncrementAmount( 0.1 );
      tPF.setMaxValue( 1.0 );
      tPF.setParameterName( "<i>T</i>" );
      tPF.setHelpText("T is the amount of foraging time available.");
      thPF.setCurrentValue( 0.1 );
      thPF.setDefaultValue( 0.1 );
      thPF.setEnabled( false );
      thPF.setIncrementAmount( 0.1 );
      thPF.setMaxValue( 1.0 );
      thPF.setParameterName( "<i>T<sub>h</>" );
      thPF.setHelpText("The time required to \"handle\" (presumably subdue and consume or parasitize) each prey individual.");
      bPF.setCurrentValue( 0.1 );
      bPF.setDefaultValue( 0.1 );
      bPF.setEnabled( false );
      bPF.setIncrementAmount( 0.1 );
      bPF.setMaxValue( 1.0E10 );
      bPF.setMinValue( -1.0E10 );
      bPF.setParameterName( "<i>b</i>" );
      bPF.setHelpText("A constant used to scale the change of a' with prey population size in Type III model.");
      apPF.setVisible( true );
      thPF.setVisible( false );
      bPF.setVisible( false );
      type2Button.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            type2Button_actionPerformed( e );
         }
      } );
      type2Button.setActionCommand( res.getString( "continuous" ) );
      type2Button.setText( res.getString( "Type_II" ) );
      type2Button.setFocusPainted( false );
      plotTypePanel.setBorder( titledBorder4 );
      pvsnButton.setText( res.getString( "Pvs_N" ) );
      pvsnButton.setFocusPainted( false );
      npvstButton.setSelected( true );
      npvstButton.setText( res.getString( "NP_vs_t" ) );
      npvstButton.setFocusPainted( false );
      titledBorder4.setTitle( res.getString( "Plot_Type" ) );
      this.setMinimumSize( new Dimension( 360, 250 ) );
      this.add( modelTypePanel, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      modelTypePanel.add( type1Button, null );
      modelTypePanel.add( type2Button, null );
      modelTypePanel.add( type3Button, null );
      this.add( initialConditionsPanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 20, 0 ), 0, 0 ) );
      initialConditionsPanel.add( n0PF, null );
      initialConditionsPanel.add( p0PF, null );
      this.add( paramsPanel, new GridBagConstraints( 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      paramsPanel.add( lambdaPF, null );
      paramsPanel.add( thPF, null );
      paramsPanel.add( bPF, null );
      paramsPanel.add( apPF, null );
      paramsPanel.add( cPF, null );
      paramsPanel.add( tPF, null );
      this.add( plotTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 20, 0 ), 0, 0 ) );
      plotTypePanel.add( npvstButton, null );
      plotTypePanel.add( pvsnButton, null );
      this.add( gensPF, new GridBagConstraints( 1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      bg.add( type1Button );
      bg.add( type2Button );
      bg.add( type3Button );
      plotGroup.add( this.npvstButton );
      plotGroup.add( this.pvsnButton );
      this.registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
*/
}
