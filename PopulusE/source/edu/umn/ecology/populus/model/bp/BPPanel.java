/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.bp;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.edwin.ModelPanelEventTypes;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;

public class BPPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 6869900954843752994L;
Border border1;
   TitledBorder titledBorder1;
   Border border8;
   ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
   JPanel plotTypePanel = new JPanel();
   RunningTimePanel runningTimePanel = new RunningTimePanel();
   Border border9;
   GridBagLayout gridBagLayout7= new GridBagLayout();
   PopulusParameterField paramTau = new PopulusParameterField();
   JPanel modelParametersPanel = new JPanel();
   Border border6;
   Border border2;
   Border border7;
   TitledBorder titledBorder2;
   JPanel hostRatesPanel = new JPanel();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   PopulusParameterField paramnp = new PopulusParameterField();
   TitledBorder titledBorder6;
   PopulusParameterField paramnstar = new PopulusParameterField();
   TitledBorder titledBorder7;
   JPanel initialDensitiesPanel = new JPanel();
   Border border3;
   TitledBorder titledBorder3;
   TitledBorder titledBorder8;
   Border border4;
   TitledBorder titledBorder4;
   TitledBorder titledBorder9;
   Border border10;
   TitledBorder titledBorder10;
   Border border11;
   TitledBorder titledBorder11;
   Border border12;
   TitledBorder titledBorder12;
   Border border5;
   TitledBorder titledBorder5;
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   private PopulusParameterField paramTI = new PopulusParameterField();
   private PopulusParameterField paramD = new PopulusParameterField();
   private PopulusParameterField paramalpha = new PopulusParameterField();
   private PopulusParameterField paramGamma = new PopulusParameterField();
   private JRadioButton nstarvsnButton = new StyledRadioButton();
   private JRadioButton fvstButton = new StyledRadioButton();
   private JRadioButton npvsnButton = new StyledRadioButton();
   private JPanel plasmidPanel = new JPanel();
   private PopulusParameterField paramq = new PopulusParameterField();
   private PopulusParameterField paramp = new PopulusParameterField();
   private GridBagLayout gridBagLayout6 = new GridBagLayout();
   private PopulusParameterField parame = new PopulusParameterField();
   private GridBagLayout gridBagLayout4 = new GridBagLayout();
   private JRadioButton variabletaugammaButton = new JRadioButton();
   private JRadioButton constantaugammaButton = new JRadioButton();
   private JPanel varTypePanel = new JPanel();
   private JRadioButton NvstButton = new StyledRadioButton();
   private PopulusParameterField paramn0 = new PopulusParameterField();
   private GridBagLayout gridBagLayout11 = new GridBagLayout();
   private PopulusParameterField paramrho = new PopulusParameterField();
   private PopulusParameterField paramC = new PopulusParameterField();
   private PopulusParameterField paramR0 = new PopulusParameterField();
   private JPanel resourcePanel = new JPanel();
   private GridBagLayout gridBagLayout12 = new GridBagLayout();
   private JRadioButton seasonalModelButton = new JRadioButton();
   private JPanel modelTypePanel = new JPanel();
   private JRadioButton equableModelButton = new JRadioButton();

   public BasicPlot getPlotParamInfo() {
      double time = runningTimePanel.getTime();
      int plotType;
      if( NvstButton.isSelected())
         plotType = BPParamInfo.vsT;
      else {
         if( nstarvsnButton.isSelected() )
            plotType = BPParamInfo.nstarvsn;
         else {
            if (npvsnButton.isSelected())
               plotType = BPParamInfo.nplusvsn;
            else
               plotType = BPParamInfo.fvst;
         }
      }

      //boolean vsTime = true;
      int modelType;
      if( equableModelButton.isSelected() ) {
         modelType = BPParamInfo.equable;
         paramrho.setEnabled(true);
         paramTI.setEnabled(false);
         paramD.setEnabled(false);
      }
      else {
         if( seasonalModelButton.isSelected() ) {
            modelType = BPParamInfo.seasonal;
            paramrho.setEnabled(false);
            paramTI.setEnabled(true);
            paramD.setEnabled(true);
         }
            else {
               System.err.println( "Error in BPPanel: Unknown button selected" );
               modelType = -1;
            }
      }
      int varType;
      if( constantaugammaButton.isSelected() ) {
         varType = BPParamInfo.constant;
      }
      else {
         if( variabletaugammaButton.isSelected() ) {
            varType = BPParamInfo.variable;
         }
         else {
            System.err.println( "Error in BPPanel: Unknown button selected" );
            varType = -1;
         }
      }

      return new BPParamInfo( modelType, plotType, varType, time, paramnp.getDouble(), paramnstar.getDouble(), paramn0.getDouble(), paramR0.getDouble(), paramalpha.getDouble(), paramGamma.getDouble(), paramTau.getDouble(), paramrho.getDouble(), paramC.getDouble(), paramp.getDouble(), paramq.getDouble(), parame.getDouble(), paramD.getDouble(), paramTI.getInt());
   }

   public BPPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
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
      border12 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder12 = new TitledBorder( border12, "Tau & Gamma" );
      border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder9 = new TitledBorder( border9, "Initial Population Sizes" );
      border10 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder10 = new TitledBorder( border10, "Resources" );
      border11 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder11 = new TitledBorder( border11, "Plasmid-Free" );
      plotTypePanel.setBorder( titledBorder1 );
      plotTypePanel.setLayout( gridBagLayout7 );
      modelParametersPanel.setBorder( titledBorder2 );
      modelParametersPanel.setLayout( gridBagLayout5 );
      hostRatesPanel.setBorder( titledBorder3 );
      hostRatesPanel.setLayout( gridBagLayout3 );
      paramnp.setParameterName("<i>N<sup>+</sup></i>(0)" );
      paramnp.setMaxValue(1.0E10 );
      paramnp.setIncrementAmount( 5.0 );
      paramnp.setDefaultValue(400000.0 );
      paramnp.setHelpText("Initial density of plasmid-bearing cells" );
      paramnp.setCurrentValue(400000.0 );
      paramnstar.setParameterName("<i>N*</i>(0)" );
      paramnstar.setMaxValue(1.0E10 );
      paramnstar.setIncrementAmount( 5.0 );
      paramnstar.setDefaultValue(400000.0 );
      paramnstar.setHelpText("Initial population of new transconjugants" );
      paramnstar.setCurrentValue(400000.0 );
      initialDensitiesPanel.setLayout( gridBagLayout2 );
      initialDensitiesPanel.setBorder( titledBorder9 );
      initialDensitiesPanel.setToolTipText("Initial Population Sizes" );
      this.setLayout( gridBagLayout1 );
      paramTau.setParameterName("<i>\u03C4</i>" );
      paramTau.setMaxValue(0.01 );
      paramTau.setMinValue(1.0E-7);
      paramTau.setIncrementAmount(0.01 );
      paramTau.setDefaultValue(0.0010 );
      paramTau.setHelpText("Vegetative segregation rate" );
      paramTau.setCurrentValue(0.0010 );
      titledBorder9.setTitle( "Initial Densities" );
      runningTimePanel.setDefaultTime(160.0 );
      paramTI.setCurrentValue(8.0);
      paramTI.setDefaultValue(8.0);
      paramTI.setEnabled(false);
      paramTI.setHelpText("Time interval between transfers");
      paramTI.setMaxValue(1000.0);
      paramTI.setParameterName("<i>T</i>");
      paramD.setCurrentValue(0.01);
      paramD.setDefaultValue(0.01);
      paramD.setEnabled(false);
      paramD.setHelpText("Dilution factor");
      paramD.setIncrementAmount(0.05);
      paramD.setMaxValue(5.0);
      paramD.setParameterName("<i>d </i>");
      paramalpha.setParameterName("<i>\u03B1</i>");
      paramalpha.setMaxValue(1.0);
      paramalpha.setIncrementAmount(0.1);
      paramalpha.setHelpText("Fitness advantage of plasmid-free cells");
      paramalpha.setDefaultValue(0.1);
      paramalpha.setCurrentValue(0.1);
      paramGamma.setCurrentValue(1.0E-9);
      paramGamma.setDefaultValue(1.0E-9);
      paramGamma.setHelpText("Conjugational transfer rate");
      paramGamma.setIncrementAmount(10.0);
      paramGamma.setMaxValue(1.0E-7);
      paramGamma.setMinValue(1.0E-15);
      paramGamma.setParameterName("<i>\u03B3</i>");
      nstarvsnButton.setText("<i>N* vs N</i>");
      nstarvsnButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            nstarvsnButton_actionPerformed(e);
         }
      });
      nstarvsnButton.setActionCommand("<i>e, b vs t</i>");
      nstarvsnButton.setFocusPainted(false);
      fvstButton.setFocusPainted(false);
      fvstButton.setActionCommand("<i>H*, I*, H*+I* vs b</i>");
      fvstButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            fvstButton_actionPerformed(e);
         }
      });
      fvstButton.setText("<i>F vs t</i>");
      npvsnButton.setToolTipText("");
      npvsnButton.setActionCommand("<i>H, I vs t </i>");
      npvsnButton.setFocusPainted(false);
      npvsnButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            npvsnButton_actionPerformed(e);
         }
      });
      npvsnButton.setText("<i>N<sup>+</sup> vs N </i>");
      plasmidPanel.setToolTipText("Plasmid_Free Cells");
      plasmidPanel.setBorder(titledBorder11);

      plasmidPanel.setLayout(gridBagLayout6);
      paramq.setParameterName("<i>Q</i>");
      paramq.setMaxValue(100.0);
      paramq.setMinValue(0.01);
      paramq.setIncrementAmount(5.0);
      paramq.setDefaultValue(4.0);
      paramq.setHelpText("Constant C3");
      paramq.setCurrentValue(4.0);
      paramp.setParameterName("<i>P</i>");
      paramp.setMaxValue(2.5);
      paramp.setMinValue(0.01);
      paramp.setIncrementAmount(0.1);
      paramp.setDefaultValue(0.738);
      paramp.setHelpText("Growth rate");
      paramp.setCurrentValue(0.738);
      parame.setCurrentValue(2.6E-6);
      parame.setHelpText("resource needed for one cell division");
      parame.setDefaultValue(2.6E-7);
      parame.setIncrementAmount(100.0);
      parame.setMaxValue(100000.0);
      parame.setMinValue(5.0E-7);
      parame.setParameterName("<i>e</i>");
      variabletaugammaButton.setToolTipText("Variable");
      variabletaugammaButton.setText("Variable");
      variabletaugammaButton.setFocusPainted(false);
      variabletaugammaButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            variabletaugammaButton_actionPerformed( e );
         }
      });
      constantaugammaButton.setToolTipText("Constant");
      constantaugammaButton.setSelected(true);
      constantaugammaButton.setText("Constant");
      constantaugammaButton.setFocusPainted(false);
      constantaugammaButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            constantaugammaButton_actionPerformed( e );
         }
      });
      varTypePanel.setLayout(gridBagLayout4);
      varTypePanel.setBorder(titledBorder12);
      NvstButton.setText("<i>N vs t </i>");
      NvstButton.setSelected(true);
      NvstButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            NvstButton_actionPerformed(e);
         }
      });
      NvstButton.setFocusPainted(false);
      NvstButton.setActionCommand("<i>H, I vs t </i>");
      NvstButton.setToolTipText("");
      paramn0.setCurrentValue(100000.0);
      paramn0.setHelpText("Initial density of plasmid-free cells");
      paramn0.setDefaultValue(100000.0);
      paramn0.setIncrementAmount(5.0);
      paramn0.setMaxValue(1.0E10);
      paramn0.setParameterName("<i>N</i>(0)");
      paramrho.setHelpText("Rate of turnover");
      paramrho.setDefaultValue(0.2);
      paramrho.setIncrementAmount(0.04);
      paramrho.setMinValue(0.01);
      paramrho.setCurrentValue(0.2);
      paramrho.setMaxValue(2.5);
      paramrho.setParameterName("<i>\u03C1</i>");
      paramC.setHelpText("Concentration of the limiting resource in the inflowing solution");
      paramC.setIncrementAmount(5.0);
      paramC.setMaxValue(100000.0);
      paramC.setParameterName("<i>c</i>");
      paramR0.setParameterName("<i>R<sub>0</sub></i>");
      paramR0.setMinValue(4.0);
      paramR0.setMaxValue(100000.0);
      paramR0.setIncrementAmount(10.0);
      paramR0.setDefaultValue(100.0);
      paramR0.setHelpText("Initial concentration of a limiting resource in the \"seasonal model\"");
      paramR0.setCurrentValue(100.0);
      resourcePanel.setLayout(gridBagLayout11);
      resourcePanel.setBorder(titledBorder10);
      resourcePanel.setToolTipText("Constants");
      seasonalModelButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            seasonalModelButton_actionPerformed( e );
         }
      });
      seasonalModelButton.setFocusPainted(false);
      seasonalModelButton.setText("Seasonal");
      seasonalModelButton.setToolTipText("Seasonal");
      modelTypePanel.setBorder(titledBorder8);
      modelTypePanel.setLayout(gridBagLayout12);
      equableModelButton.setToolTipText("Equable");
      equableModelButton.setSelected(true);
      equableModelButton.setText("Equable");
      equableModelButton.setFocusPainted(false);
      equableModelButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            equableModelButton_actionPerformed( e );
         }
      });
      initialDensitiesPanel.add( paramnp,                  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
      initialDensitiesPanel.add( paramnstar,                               new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
      initialDensitiesPanel.add(paramn0,         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      modelParametersPanel.add(hostRatesPanel,        new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      varTypePanel.add(constantaugammaButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      varTypePanel.add(variabletaugammaButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      this.add(modelTypePanel,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(runningTimePanel,                  new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      this.add(plotTypePanel,             new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
      plasmidPanel.add(paramq,                                     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      plasmidPanel.add(parame,                                     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      plasmidPanel.add(paramp,               new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      modelParametersPanel.add(resourcePanel,                 new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      resourcePanel.add(paramrho,  new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      resourcePanel.add(paramR0,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      resourcePanel.add(paramC,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      this.add(modelParametersPanel,                       new GridBagConstraints(2, 0, 1, 3, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      modelParametersPanel.add(initialDensitiesPanel,                new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      hostRatesPanel.add( paramTau,                            new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0) );
      hostRatesPanel.add(paramD,                    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramTI,              new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramGamma,       new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramalpha,             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      modelParametersPanel.add(plasmidPanel,                                             new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(npvsnButton,             new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(nstarvsnButton,              new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(fvstButton,             new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      registerChildren( this );
      bg2.add(constantaugammaButton);
      bg2.add(variabletaugammaButton);
      bg1.add(equableModelButton);
      bg1.add(seasonalModelButton);
      plotTypePanel.add(NvstButton,       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      modelTypePanel.add(equableModelButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      modelTypePanel.add(seasonalModelButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      this.add(varTypePanel,  new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bg3.add(NvstButton);
      bg3.add(npvsnButton);
      bg3.add(nstarvsnButton);
      bg3.add(fvstButton);
   }
   void constantaugammaButton_actionPerformed( ActionEvent e ) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }

   void variabletaugammaButton_actionPerformed( ActionEvent e ) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void nstarvsnButton_actionPerformed(ActionEvent e) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void npvsnButton_actionPerformed(ActionEvent e) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void fvstButton_actionPerformed(ActionEvent e) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void NvstButton_actionPerformed(ActionEvent e) {
       fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void seasonalModelButton_actionPerformed(ActionEvent e) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }
   void equableModelButton_actionPerformed(ActionEvent e) {
      fireModelPanelEvent( ModelPanelEventTypes.CHANGE_PLOT );
   }

/*
public int getTriggers() {
return 0;
}
*/
}
