package edu.umn.ecology.populus.model.gdamcm;

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1998

//Author:       Lars Roe

//Company:      University of Minnesota

//Description:  Populus
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

/**
  * Genetic Drift panel
  *
  *
  *
  */

public class GDPanel extends BasicPlotInputPanel {
   public static final int MAX_LOCI = 10;
   Border border1;
   TitledBorder titledBorder1;
   Border border2;
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   JRadioButton singleFrequencyButton = new JRadioButton();
   JCheckBox selfingBox = new JCheckBox();
   JRadioButton otherButton = new JRadioButton();
   TitledBorder titledBorder2;
   Border border3;
   JPanel initialConditionsPanel = new JPanel();
   TitledBorder titledBorder3;
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   JPanel breedingPanel = new JPanel();
   PopulusParameterField[] lociFrequencies = new PopulusParameterField[MAX_LOCI];
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JRadioButton viewGens3DCB = new JRadioButton();
   JTabbedPane switcherTP = new JTabbedPane();
   PopulusParameterField paramNumLoci = new PopulusParameterField();
   JPanel montecarloP = new JPanel();
   JPanel singleFreqPanel = new JPanel();
   JPanel markovP = new JPanel();
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.gdamcm.Res" );
   PopulusParameterField paramGens = new PopulusParameterField();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   JPanel runtimePanel = new JPanel();
   JPanel scrollPaneInside = new JPanel();
   VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
   JScrollPane jScrollPane1 = new JScrollPane();
   PopulusParameterField paramN = new PopulusParameterField();
   VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
   PopulusParameterField numAGenesPPF = new PopulusParameterField();
   VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
   PopulusParameterField popSizePPF = new PopulusParameterField();
   JPanel individualFreqPanel = new JPanel();
   PopulusParameterField paramInitFreq = new PopulusParameterField();
   PopulusParameterField gensToViewPPF = new PopulusParameterField();
   VerticalFlowLayout verticalFlowLayout3 = new VerticalFlowLayout();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   JRadioButton individualFrequencyButton = new JRadioButton();
   StyledRadioButton gensButton = new StyledRadioButton();
   ButtonGroup goBG = new ButtonGroup();
   ButtonGroup siBG = new ButtonGroup();
   VerticalFlowLayout verticalFlowLayout7 = new VerticalFlowLayout();

   //these are saved so we can use the same last calculated frequencies
   //for the next iteration
   GDParamInfo gdpi=null;
   boolean isIteration = false;

   public void updateLabels() {
      for( int i = 0;i < MAX_LOCI;i++ ) {
         lociFrequencies[i].getLabel().setDefaultColor( ColorScheme.colors[i] );
      }
   }

   /** Changes enablement of components */

   public void actionPerformed( ActionEvent e ) {
      JRadioButton src;
      if( e.getSource() instanceof JRadioButton ) {
         src = (JRadioButton)e.getSource();
         if( src == gensButton || src == otherButton ) {
            paramGens.setEnabled( otherButton.isSelected() );
         }
         else { //if (src == cbAll || src == cbIndividual)
            boolean mult = individualFrequencyButton.isSelected();
            setPFGEnabled( !mult );
            paramInitFreq.setEnabled( !mult );
         }
      }
   }

   public BasicPlot getPlotParamInfo() {
      if( switcherTP.getSelectedIndex() == 0 ) {
         int gens;
         int N = paramN.getInt();

         //Set lociFreqs
         int numLoci = paramNumLoci.getInt();
         double[] lociFreqs = new double[numLoci];
         if( singleFrequencyButton.isSelected() ) {
            double val = paramInitFreq.getDouble();
            for( int i = 0;i < numLoci;i++ ) {
               lociFreqs[i] = val;
            }
         }
         else {
            for( int i = 0;i < numLoci;i++ ) {
               lociFreqs[i] = this.lociFrequencies[i].getDouble();
            }
         }

         //Set N
         if( otherButton.isSelected() ) {
            gens = paramGens.getInt();
         }
         else {
            gens = 3 * N;
         }
         return new GDParamInfo( gens, selfingBox.isSelected(), N, lociFreqs );
      }
      else {
         if( isIteration && gdpi != null ){
            isIteration = false;
            if(viewGens3DCB.isSelected()) gdpi.incGenerations();
            return gdpi;
         } else{
            gdpi = new GDParamInfo( numAGenesPPF.getInt(), popSizePPF.getInt(), viewGens3DCB.isSelected(), gensToViewPPF.getInt() );
            return gdpi;
         }
      }
   }

   public void willIterate(boolean b){
      isIteration = b;
   }

   public GDPanel() {
      for( int i = 0;i < MAX_LOCI;i++ ) {
         lociFrequencies[i] = new PopulusParameterField();
      }
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   /** Adding loci will not set all of the pf's to the value of all */


   public int getDefaultTriggers() {
      return ModelPanelEventTypes.ENTER;
   }

   /** This class makes sure frequencies occur at 1/2N marks */



   void setPFGEnabled( boolean b, boolean overwriteMultiPF ) {
      int numLoci = paramNumLoci.getInt();
      for( int i = 0;i < MAX_LOCI;i++ ) {
         if( b && ( numLoci > i ) && overwriteMultiPF ) {
            lociFrequencies[i].setCurrentValue( this.paramInitFreq.getDouble() );
         }
         lociFrequencies[i].setEnabled( !b && ( numLoci > i ) );
      }
      jScrollPane1.repaint();
   }

   void setPFGEnabled( boolean b ) {
      setPFGEnabled( b, true );
   }

   void popSizePPF_parameterFieldChanged( ParameterFieldEvent e ) {
      numAGenesPPF.setMaxValue(popSizePPF.getInt()*2);
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Breeding_Parameters" ) );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, res.getString( "Initial_Conditions" ) );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, res.getString( "Runtime" ) );
      paramN.addParameterFieldListener( new IncrementChecker() );
      paramN.setParameterName( res.getString( "Population_Size_i_N_i" ) );
      paramN.setMinValue( 1.0 );
      paramN.setMaxValue( 500.0 );
      paramN.setDefaultValue( 8.0 );
      paramN.setCurrentValue( 8.0 );
      paramN.setHelpText( res.getString( "Number_of_individuals" ) );
      breedingPanel.setLayout( gridBagLayout2 );
      breedingPanel.setBorder( titledBorder1 );
      popSizePPF.setDefaultValue(6.0);
      viewGens3DCB.setToolTipText("Several generations plotted on a 3D graph.");
      viewGens3DCB.setActionCommand("View Generations 3D");
      viewGens3DCB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            viewGens3DCB_actionPerformed(e);
         }
      });
      gensToViewPPF.setEnabled(false);
      gensToViewPPF.setIncrementAmount(5.0);
      goBG.add(otherButton);
      goBG.add(gensButton);
      otherButton.setFocusPainted( false );
      otherButton.setText( res.getString( "Other_" ) );
      runtimePanel.setLayout( verticalFlowLayout1 );
      runtimePanel.setBorder( titledBorder3 );
      gensButton.setText( res.getString( "3i_N_i_Generations" ) );
      gensButton.setSelected( true );
      paramGens.setParameterName( res.getString( "Generations" ) );
      paramGens.setMinValue( 1.0 );
      paramGens.setMaxValue( 10000.0 );
      paramGens.setIncrementAmount( 10.0 );
      paramGens.setDefaultValue( 30.0 );
      paramGens.setCurrentValue( 30.0 );
      paramGens.setHelpText( res.getString( "Number_of_generations" ) );
      paramGens.setEnabled( false );
      selfingBox.setText( res.getString( "Permit_Selfing_" ) );
      selfingBox.setFocusPainted( false );
      this.setLayout( gridBagLayout1 );
      montecarloP.setLayout( gridBagLayout3 );
      scrollPaneInside.setLayout(verticalFlowLayout7 );
      jScrollPane1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
      jScrollPane1.setPreferredSize( new Dimension( 150, 100 ) );
      individualFreqPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
      individualFreqPanel.setLayout( verticalFlowLayout4 );
      paramInitFreq.setHelpText( res.getString( "Initial_frequency_of" ) );
      paramInitFreq.setCurrentValue( 0.5 );
      paramInitFreq.setDefaultValue( 0.5 );
      paramInitFreq.setIncrementAmount( 1 / 16 );
      paramInitFreq.setMaxValue( 1.0 );
      paramInitFreq.setParameterName( res.getString( "Initial_Frequency" ) );
      paramInitFreq.addParameterFieldListener( new IncrementChecker() );
      siBG.add(individualFrequencyButton);
      siBG.add(singleFrequencyButton);
      individualFrequencyButton.setText( res.getString( "set_frequencies" ) );
      singleFrequencyButton.setSelected( true );
      singleFrequencyButton.setText( res.getString( "set_frequencies_all" ) );
      paramNumLoci.setHelpText( res.getString( "Number_of_gene_loci" ) );
      paramNumLoci.setCurrentValue( 6.0 );
      paramNumLoci.setDefaultValue( 6.0 );
      paramNumLoci.setMaxValue( 10.0 );
      paramNumLoci.setMinValue( 1.0 );
      paramNumLoci.setParameterName( res.getString( "Number_of_Loci" ) );
      paramNumLoci.addParameterFieldListener( new NumLociListener() );
      initialConditionsPanel.setBorder( titledBorder2 );
      initialConditionsPanel.setLayout( verticalFlowLayout3 );
      singleFreqPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
      singleFreqPanel.setLayout( verticalFlowLayout5 ); //won't auto update after 30
      for( int i = 0;i < MAX_LOCI;i++ ) {
         lociFrequencies[i].setMaxValue( 1.0 );
         lociFrequencies[i].setIncrementAmount( 1 / 16 );
         lociFrequencies[i].setDefaultValue( 0.5 );
         lociFrequencies[i].setCurrentValue( 0.5 );
         lociFrequencies[i].setUseHTML( true );
         lociFrequencies[i].getLabel().setDefaultColor( ColorScheme.colors[i] );
         lociFrequencies[i].setParameterName( res.getString( "Locus_" ) + Integer.toString( i + 1 ) );
         lociFrequencies[i].addParameterFieldListener( new IncrementChecker() );
      }
      setPFGEnabled( true );
      markovP.setToolTipText( "" );
      markovP.setLayout( gridBagLayout5 );
      numAGenesPPF.setToolTipText( "" );
      numAGenesPPF.setParameterName( "Number of \"A\" genes per population:" );
      numAGenesPPF.setMaxValue( 12.0 );
      numAGenesPPF.setCurrentValue(6.0 );
      popSizePPF.setCurrentValue(6.0 );
      popSizePPF.setMaxValue( 30.0 );
      popSizePPF.setMinValue( 1.0 );
      popSizePPF.setParameterName( "Population Size:" );
      popSizePPF.setToolTipText( "" );
      popSizePPF.addParameterFieldListener( new edu.umn.ecology.populus.visual.ppfield.ParameterFieldListener()  {

         public void parameterFieldChanged( ParameterFieldEvent e ) {
            popSizePPF_parameterFieldChanged( e );
         }
      } );
      gensToViewPPF.setParameterName("Generations to View in 3D" );
      gensToViewPPF.setMaxValue(100.0 );//this number can't be too big b/c 10*this will be the number of grid lines
      gensToViewPPF.setDefaultValue(10.0 );
      gensToViewPPF.setCurrentValue(10.0 );
      viewGens3DCB.setText("View Generations 3D" );

      JScrollBar myBar = jScrollPane1.getVerticalScrollBar();
      myBar.setUnitIncrement( 32 );
      myBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent e) {
            jScrollPane1.getViewport().repaint();
         }
      });

      singleFreqPanel.add( singleFrequencyButton, null );
      singleFreqPanel.add( paramInitFreq, null );

      for( int i = 0;i < MAX_LOCI;i++ ) {
         scrollPaneInside.add( lociFrequencies[i], null );
      }
      jScrollPane1.getViewport().add( scrollPaneInside, null );


      //myBar.addAdjustmentListener(); is it possible to cause the field to be moved by the wheel?
      paramN.setAutoUpdateStopValue( 30.0 );

      individualFreqPanel.add( individualFrequencyButton, null );
      individualFreqPanel.add( jScrollPane1, null );

      runtimePanel.add( gensButton, null );
      runtimePanel.add( otherButton, null );
      runtimePanel.add( paramGens, null );
      breedingPanel.add( selfingBox, new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      breedingPanel.add( paramN, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      breedingPanel.add( runtimePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 5, 5, 0, 5 ), 17, 0 ) );
      initialConditionsPanel.add( paramNumLoci, null );
      initialConditionsPanel.add( singleFreqPanel, null );
      initialConditionsPanel.add( individualFreqPanel, null );

      montecarloP.add( breedingPanel, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      montecarloP.add( initialConditionsPanel, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );

      markovP.add( numAGenesPPF,        new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0) );
      markovP.add( popSizePPF,       new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0) );
      markovP.add(gensToViewPPF,       new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      markovP.add(viewGens3DCB,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

      switcherTP.add( montecarloP, "Monte Carlo" );
      switcherTP.add( markovP, "Markov" );
      this.add( switcherTP, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      registerChildren( this );
   }

   class NumLociListener implements ParameterFieldListener {

      public void parameterFieldChanged( ParameterFieldEvent e ) {
         setPFGEnabled( singleFrequencyButton.isSelected(), false );
      }
   }
   class IncrementChecker implements ParameterFieldListener {

      public void parameterFieldChanged( ParameterFieldEvent e ) {
         double increment = 0.5 / paramN.getDouble();
         paramInitFreq.setIncrementAmount( increment );
         paramInitFreq.setCurrentValue( increment * ( (double)Math.round( paramInitFreq.getDouble() / increment ) ) );
         for( int i = 0;i < lociFrequencies.length;i++ ) {
            lociFrequencies[i].setIncrement( increment );
            lociFrequencies[i].setCurrentValue( increment * ( (double)Math.round( lociFrequencies[i].getDouble() / increment ) ) );
         }
      }
   }

  void viewGens3DCB_actionPerformed(ActionEvent e) {
     boolean is3D = viewGens3DCB.isSelected();
     gensToViewPPF.setEnabled(is3D);
  }
}
