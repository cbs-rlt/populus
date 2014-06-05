package edu.umn.ecology.populus.model.ps;

import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import edu.umn.ecology.populus.constants.ColorScheme;
import javax.swing.event.*;

/**
  *Population Structure panel
  *
  */
public class PSPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 3520322824910734120L;

public static final int MAX_LOCI = 10;

   Border border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
   TitledBorder titledBorder1 = new TitledBorder("Model Parameters");
   Border border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   TitledBorder titledBorder2 = new TitledBorder("Loci Parameters");
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   ButtonGroup goBG = new ButtonGroup();
   private JPanel demicPanel = new JPanel();
   private JScrollPane lociScroller = new JScrollPane();
   private VerticalFlowLayout verticalFlowLayout7 = new VerticalFlowLayout();
   private VerticalFlowLayout verticalFlowLayout5 = new VerticalFlowLayout();
   private VerticalFlowLayout verticalFlowLayout4 = new VerticalFlowLayout();
   private JPanel individualFreqPanel = new JPanel();
   private PopulusParameterField paramInitFreq = new PopulusParameterField();
   private JRadioButton individualFrequencyButton = new JRadioButton();
   private JRadioButton singleFrequencyButton = new JRadioButton();
   private PopulusParameterField paramNumDemes = new PopulusParameterField();
   private JPanel lociFreqP = new JPanel();
   private JPanel singleFreqPanel = new JPanel();
   private JCheckBox selfingCB = new JCheckBox();
   private GridBagLayout gridBagLayout2 = new GridBagLayout();
   private JPanel modelP = new JPanel();
   private PopulusParameterField gensPPF = new PopulusParameterField();
   private PopulusParameterField migrationPPF = new PopulusParameterField();
   private PopulusParameterField demeSizePPF = new PopulusParameterField();
   PopulusParameterField[] demicPPFArray = new PopulusParameterField[MAX_LOCI];
   private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

   //these are saved so we can use the same last calculated frequencies
   //for the next iteration
   PSParamInfo pspi=null;
   boolean isIterate = false;
   boolean doSwitch = false;

   public void updateLabels() {
      for( int i = 0;i < MAX_LOCI;i++ ) {
         demicPPFArray[i].getLabel().setDefaultColor( ColorScheme.colors[i] );
      }
   }

   public BasicPlot getPlotParamInfo() {
      if(!isIterate && !doSwitch) {
         double[] freqs = new double[paramNumDemes.getInt()];
         for(int i=0; i<freqs.length;i++){
            if(!singleFrequencyButton.isSelected())
               freqs[i] = demicPPFArray[i].getDouble();
            else
               freqs[i] = paramInitFreq.getDouble();
         }


         pspi = new PSParamInfo(freqs,migrationPPF.getDouble(),demeSizePPF.getInt(),selfingCB.isSelected());
      }

      if(doSwitch){
         pspi.switchOutputs();
         doSwitch = false;
      } else {
         for(int i=0; i<gensPPF.getInt(); i++)
            pspi.doGeneration();
      }
      isIterate = false;
      return pspi;
   }

   public void willIterate(boolean b){
      isIterate = b;
   }

   public void switchOutputType(){
      doSwitch = true;
   }

   public PSPanel() {

      for( int i = 0;i < MAX_LOCI;i++ ) {
         demicPPFArray[i] = new PopulusParameterField();
      }
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public int getDefaultTriggers() {
      return ModelPanelEventTypes.ENTER;
   }

   void setPFGEnabled( boolean b, boolean overwriteMultiPF ) {
      int numLoci = paramNumDemes.getInt();
      for( int i = 0;i < MAX_LOCI;i++ ) {
         if( b && ( numLoci > i ) && overwriteMultiPF ) {
            demicPPFArray[i].setCurrentValue( this.paramInitFreq.getDouble() );
         }
         demicPPFArray[i].setEnabled( !b && ( numLoci > i ) );
      }
      lociScroller.repaint();
   }

   void setPFGEnabled( boolean b ) {
      setPFGEnabled( b, true );
   }

   private void jbInit() throws Exception {
      modelP.setLayout(gridBagLayout2);
      selfingCB.setFocusPainted(false);
      selfingCB.setText("Permit selfing?");
      demicPanel.setLayout(verticalFlowLayout7);
      lociScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      lociScroller.setPreferredSize(new Dimension( 150, 100 ));
      individualFreqPanel.setBorder(BorderFactory.createLineBorder( Color.black ));
      individualFreqPanel.setLayout(verticalFlowLayout4);
      paramInitFreq.setCurrentValue(0.5);
      paramInitFreq.setDefaultValue(0.5);
      paramInitFreq.setIncrementAmount(0.1);
      paramInitFreq.setMaxValue(1.0);
      paramInitFreq.setParameterName("Initial Frequency");
      individualFrequencyButton.setFocusPainted(false);
      individualFrequencyButton.setText("Set Frequencies Individually");
      singleFrequencyButton.setFocusPainted(false);
      singleFrequencyButton.setSelected(true);
      singleFrequencyButton.setText("Set Frequencies Collectively");
      singleFrequencyButton.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            singleFrequencyButton_stateChanged(e);
         }
      });
      paramNumDemes.setCurrentValue(6.0);
      paramNumDemes.setDefaultValue(6.0);
      paramNumDemes.setMaxValue(10.0);
      paramNumDemes.setMinValue(1.0);
      paramNumDemes.setParameterName("Number of Demes");
      paramNumDemes.setHelpText("Number of subpopulations in the total population");
      lociFreqP.setLayout(verticalFlowLayout1);
      singleFreqPanel.setBorder(BorderFactory.createLineBorder( Color.black ));
      singleFreqPanel.setLayout(verticalFlowLayout5);
      this.setLayout( gridBagLayout1 );//won't auto update after 30
      for( int i = 0;i < MAX_LOCI;i++ ) {
         demicPPFArray[i].setMaxValue( 1.0 );
         demicPPFArray[i].setIncrementAmount(0.1);
         demicPPFArray[i].setDefaultValue( 0.5 );
         demicPPFArray[i].setCurrentValue( 0.5 );
         demicPPFArray[i].setUseHTML( true );
         demicPPFArray[i].getLabel().setDefaultColor( ColorScheme.colors[i] );
         demicPPFArray[i].setParameterName("Deme #" + Integer.toString( i + 1 ) );
         demicPanel.add( demicPPFArray[i], null );
      }
      setPFGEnabled( true );//this number can't be too big b/c 10*this will be the number of grid lines

      JScrollBar myBar = lociScroller.getVerticalScrollBar();
      myBar.setUnitIncrement( 37 );
      myBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
         public void adjustmentValueChanged(AdjustmentEvent e) {
            lociScroller.getViewport().repaint();
         }
      });

      paramNumDemes.addParameterFieldListener(new NumLociListener());
      gensPPF.setCurrentValue(5.0);
      gensPPF.setDefaultValue(5.0);
      gensPPF.setIncrementAmount(10.0);
      gensPPF.setIntegersOnly(true);
      gensPPF.setMaxValue(100.0);
      gensPPF.setMinValue(1.0);
      gensPPF.setParameterName("Iteration Interval");
      gensPPF.setHelpText("Number of generations to advance the model per iteration");
      migrationPPF.setCurrentValue(0.01);
      migrationPPF.setDefaultValue(0.01);
      migrationPPF.setMaxValue(1.0);
      migrationPPF.setParameterName("Migration Rate");
      migrationPPF.setHelpText("Proportion of deme members which are exchanged by migration in each generation");
      demeSizePPF.setParameterName("Deme Size (<i>N</>)");
      demeSizePPF.setMinValue(1.0);
      demeSizePPF.setMaxValue(500.0);
      demeSizePPF.setDefaultValue(12.0);
      demeSizePPF.setCurrentValue(12.0);
      demeSizePPF.setIntegersOnly(true);
      demeSizePPF.setHelpText("Number of individuals in each of the subpopulations");
      lociFreqP.add(paramNumDemes, null);
      lociFreqP.add(singleFreqPanel, null);
      singleFreqPanel.add(singleFrequencyButton, null);
      singleFreqPanel.add(paramInitFreq, null);
      lociFreqP.add(individualFreqPanel, null);
      individualFreqPanel.add(individualFrequencyButton, null);
      individualFreqPanel.add(lociScroller);
      lociScroller.getViewport().add(demicPanel, null);
      modelP.add(demeSizePPF,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
      modelP.add(selfingCB,  new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
      modelP.add(migrationPPF,  new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
      modelP.add(gensPPF,  new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
      this.add(modelP,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      this.add(lociFreqP,    new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      goBG.add(singleFrequencyButton);
      goBG.add(individualFrequencyButton);
      /*if i just say registerChildren(this) then the scroll bar will reset the graph too...
      and this right here works...*/
      registerChildren(modelP);
      registerChildren(demicPanel);
      registerChildren(paramNumDemes);
   }

   class NumLociListener implements ParameterFieldListener {

      public void parameterFieldChanged( ParameterFieldEvent e ) {
         setPFGEnabled( singleFrequencyButton.isSelected(), false );
      }
   }

   void singleFrequencyButton_stateChanged(ChangeEvent e) {
      setPFGEnabled( singleFrequencyButton.isSelected(), false );
      paramInitFreq.setEnabled( singleFrequencyButton.isSelected() );
   }
}
