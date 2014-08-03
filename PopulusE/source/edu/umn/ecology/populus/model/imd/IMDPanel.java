package edu.umn.ecology.populus.model.imd;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

public class IMDPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4857405508180557746L;
Border border1;
   TitledBorder titledBorder1;
   StyledRadioButton nvstButton = new StyledRadioButton();
   JPanel modelTypePanel = new JPanel();
   Border border8;
   ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
   JPanel plotTypePanel = new JPanel();
   RunningTimePanel runningTimePanel1 = new RunningTimePanel();
   StyledRadioButton phaseButton = new StyledRadioButton();
   Border border9;
   StyledRadioButton ivsnButton = new StyledRadioButton();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   PopulusParameterField paramalpha = new PopulusParameterField();
   JPanel modelParametersPanel = new JPanel();
   Border border6;
   Border border2;
   Border border7;
   TitledBorder titledBorder2;
   JRadioButton siFDModelButton = new JRadioButton();
   JPanel hostRatesPanel = new JPanel();
   JRadioButton siDDModelButton = new JRadioButton();
   PopulusParameterField paramBirth = new PopulusParameterField();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   PopulusParameterField paramDeath = new PopulusParameterField();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField paramnu = new PopulusParameterField();
   PopulusParameterField paramgamma = new PopulusParameterField();
   PopulusParameterField parambeta = new PopulusParameterField();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   PopulusParameterField paramS = new PopulusParameterField();
   TitledBorder titledBorder6;
   PopulusParameterField paramI = new PopulusParameterField();
   JPanel parasiteRatesPanel = new JPanel();
   PopulusParameterField paramR = new PopulusParameterField();
   TitledBorder titledBorder7;
   JPanel hostDensitiesPanel = new JPanel();
   JRadioButton sirFDModelButton = new JRadioButton();
   Border border3;
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   TitledBorder titledBorder3;
   TitledBorder titledBorder8;
   Border border4;
   JRadioButton sirDDModelButton = new JRadioButton();
   TitledBorder titledBorder4;
   TitledBorder titledBorder9;
   Border border5;
   GridBagLayout gridBagLayout6 = new GridBagLayout();
   TitledBorder titledBorder5;
   GridBagLayout gridBagLayout2 = new GridBagLayout();

   public BasicPlot getPlotParamInfo() {
      double time = runningTimePanel1.getTime();
      int plotType;
      if( nvstButton.isSelected())
         plotType = IMDParamInfo.vsT;
      else if( ivsnButton.isSelected() )
         plotType = IMDParamInfo.ivsn;
      else
         plotType = IMDParamInfo.phase;

      //boolean vsTime = true;
      int modelType;
      if( siDDModelButton.isSelected() ) {
         modelType = IMDParamInfo.SIDD;
      }
      else {
         if( sirDDModelButton.isSelected() ) {
            modelType = IMDParamInfo.SIRDD;
         }
         else {
            if( siFDModelButton.isSelected() ) {
               modelType = IMDParamInfo.SIFD;
            }
            else {
               if( sirFDModelButton.isSelected() ) {
                  modelType = IMDParamInfo.SIRFD;
               }
               else {
                  System.err.println( "Error in IMDPanel: Unknown button selected" );
                  modelType = -1;
               }
            }
         }
      }
      if( plotType==IMDParamInfo.phase && modelType > 10 ) {
         return new IMD3DParamInfo( modelType, time, paramS.getDouble(), paramI.getDouble(), paramR.getDouble(), paramBirth.getDouble(), paramDeath.getDouble(), paramalpha.getDouble(), parambeta.getDouble(), paramnu.getDouble(), paramgamma.getDouble() );
      }
      return new IMDParamInfo( modelType, plotType, time, paramS.getDouble(), paramI.getDouble(), paramR.getDouble(), paramBirth.getDouble(), paramDeath.getDouble(), paramalpha.getDouble(), parambeta.getDouble(), paramnu.getDouble(), paramgamma.getDouble() );
   }

   public IMDPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   void modelTypeChanged( boolean siOnly ) {
      paramR.setEnabled( !siOnly );
      paramgamma.setEnabled( !siOnly );
      if(siOnly){
         nvstButton.setText( "<i>S, I, N</i>  vs <i>t</i>" );
         phaseButton.setText( "<i>I</i>  vs <i>S</i>" );
      } else {
        nvstButton.setText( "<i>S, I, R, N</i>  vs <i>t</i>" );
        phaseButton.setText( "<i>S</i>  vs <i>I</i> vs <i>R</i>" );
      }
   }

   void siDDModelButton_actionPerformed( ActionEvent e ) {
      modelTypeChanged( true );
   }

   void siFDModelButton_actionPerformed( ActionEvent e ) {
      modelTypeChanged( true );
   }

   void sirDDModelButton_actionPerformed( ActionEvent e ) {
      modelTypeChanged( false );
   }

   void sirFDModelButton_actionPerformed( ActionEvent e ) {
      modelTypeChanged( false );
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, "Plot Type" );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, "Model Parameters" );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, "Parasite Rates" );
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
      plotTypePanel.setBorder( titledBorder1 );
      plotTypePanel.setLayout( simpleVFlowLayout1 );
      nvstButton.setSelected( true );
      nvstButton.setText( "<i>S, I, N</i>  vs <i>t</i>" );
      phaseButton.setText( "<i>I</i>  vs <i>S</i>" );
      ivsnButton.setText("<i>I</i>  vs <i>N</i>");
      modelParametersPanel.setBorder( titledBorder2 );
      modelParametersPanel.setLayout( gridBagLayout5 );
      hostRatesPanel.setBorder( titledBorder3 );
      hostRatesPanel.setLayout( gridBagLayout3 );
      parambeta.setCurrentValue(0.2 );
      parambeta.setDefaultValue(0.2 );
      parambeta.setHelpText( "Between-host transmission rate" );
      parambeta.setIncrementAmount( 0.1 );
      parambeta.setMaxValue( 1.0 );
      parambeta.setParameterName( "\u03b2" );
      paramS.setParameterName( "<i>S</i>(0)" );
      paramS.setMaxValue( 100000.0 );
      paramS.setIncrementAmount( 5.0 );
      paramS.setDefaultValue(5.0 );
      paramS.setHelpText( "Susceptible host density" );
      paramS.setCurrentValue(5.0 );
      paramI.setParameterName( "<i>I</i>(0)" );
      paramI.setMaxValue( 100000.0 );
      paramI.setIncrementAmount( 5.0 );
      paramI.setDefaultValue(10.0 );
      paramI.setHelpText( "Infected host density" );
      paramI.setCurrentValue(10.0 );
      paramR.setParameterName( "<i>R</i>(0)" );
      paramR.setMaxValue( 100000.0 );
      paramR.setIncrementAmount( 2.0 );
      paramR.setDefaultValue( 6.0 );
      paramR.setEnabled( false );
      paramR.setHelpText( "Immune host density" );
      paramR.setCurrentValue( 6.0 );
      hostDensitiesPanel.setLayout( gridBagLayout2 );
      hostDensitiesPanel.setBorder( titledBorder9 );
      hostDensitiesPanel.setToolTipText( "Initial Host Densities" );
      this.setLayout( gridBagLayout1 );
      paramalpha.setParameterName( "\u03b1" );
      paramalpha.setMaxValue( 1.0 );
      paramalpha.setIncrementAmount( 0.1 );
      paramalpha.setDefaultValue(0.4 );
      paramalpha.setHelpText( "Disease-induced mortality rate" );
      paramalpha.setCurrentValue(0.4 );
      paramgamma.setParameterName( "\u03b3" );
      paramgamma.setMaxValue( 1.0 );
      paramgamma.setIncrementAmount( 0.1 );
      paramgamma.setHelpText( "Rate of immunity loss" );
      paramgamma.setDefaultValue(0.1 );
      paramgamma.setEnabled( false );
      paramgamma.setCurrentValue(0.1 );
      paramDeath.setCurrentValue(0.4 );
      paramDeath.setDefaultValue(0.4 );
      paramDeath.setHelpText( "Natural mortality rate" );
      paramDeath.setIncrementAmount( 0.1 );
      paramDeath.setMaxValue( 1.0 );
      paramDeath.setParameterName( "<i>d </i>" );
      paramBirth.setCurrentValue(0.5 );
      paramBirth.setDefaultValue(0.5 );
      paramBirth.setHelpText( "Birth Rate" );
      paramBirth.setIncrementAmount( 0.1 );
      paramBirth.setMaxValue( 1000.0 );
      paramBirth.setParameterName( "<i>b</i>" );
      paramnu.setCurrentValue(0.1 );
      paramnu.setDefaultValue(0.1 );
      paramnu.setHelpText( "Recovery rate of infected hosts" );
      paramnu.setIncrementAmount( 0.1 );
      paramnu.setMaxValue( 1.0 );
      paramnu.setParameterName( "\u03bd" );
      parasiteRatesPanel.setBorder( titledBorder7 );
      parasiteRatesPanel.setLayout( gridBagLayout6 );
      sirFDModelButton.setToolTipText( "SIR Model with Frequency-Dependent Transmission" );
      sirFDModelButton.setText( "SIR Model, FD Transmission" );
      sirFDModelButton.setFocusPainted( false );
      sirFDModelButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            sirFDModelButton_actionPerformed( e );
         }
      } );
      siFDModelButton.setToolTipText( "SI Model with Frequency-Dependent Transmission" );
      siFDModelButton.setText( "SI Model, FD Transmission" );
      siFDModelButton.setFocusPainted( false );
      siFDModelButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            siFDModelButton_actionPerformed( e );
         }
      } );
      modelTypePanel.setLayout( gridBagLayout4 );
      modelTypePanel.setBorder( titledBorder8 );
      siDDModelButton.setToolTipText( "SI Model with Density-Dependent Transmission" );
      siDDModelButton.setSelected( true );
      siDDModelButton.setText( "SI Model, DD Transmission" );
      siDDModelButton.setFocusPainted( false );
      siDDModelButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            siDDModelButton_actionPerformed( e );
         }
      } );
      sirDDModelButton.setToolTipText( "SIR Model with Density-Dependent Transmission" );
      sirDDModelButton.setText( "SIR Model, DD Transmission" );
      sirDDModelButton.setFocusPainted( false );
      sirDDModelButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            sirDDModelButton_actionPerformed( e );
         }
      } );
      titledBorder9.setTitle( "Host Densities" );
      runningTimePanel1.setDefaultTime(20.0 );
      this.add( modelParametersPanel,  new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 50, 0) );
      modelParametersPanel.add( hostDensitiesPanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostDensitiesPanel.add( paramS, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostDensitiesPanel.add( paramI, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostDensitiesPanel.add( paramR, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 1, 0 ) );
      modelParametersPanel.add( parasiteRatesPanel, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      parasiteRatesPanel.add( paramBirth, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      parasiteRatesPanel.add( paramDeath, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      modelParametersPanel.add( hostRatesPanel, new GridBagConstraints( 2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostRatesPanel.add( paramalpha, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostRatesPanel.add( parambeta, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 1, 0 ) );
      hostRatesPanel.add( paramgamma, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 3, 0 ) );
      hostRatesPanel.add( paramnu, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 1, 0 ) );
      this.add( plotTypePanel,    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0) );
      plotTypePanel.add( nvstButton, null );
      plotTypePanel.add( phaseButton, null );
      plotTypePanel.add( ivsnButton, null );
      this.add( runningTimePanel1,   new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      this.add( modelTypePanel,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      modelTypePanel.add( siDDModelButton, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      modelTypePanel.add( siFDModelButton, new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      modelTypePanel.add( sirDDModelButton, new GridBagConstraints( 0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      modelTypePanel.add( sirFDModelButton, new GridBagConstraints( 0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      this.add( modelTypePanel, new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      bg2.add( nvstButton );
      bg2.add( phaseButton );
      bg2.add( ivsnButton );
      bg3.add( siDDModelButton );
      bg3.add( siFDModelButton );
      bg3.add( sirDDModelButton );
      bg3.add( sirFDModelButton );
      registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
*/
}
