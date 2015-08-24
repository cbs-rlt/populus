/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.woozle;
import edu.umn.ecology.populus.edwin.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
  * Woozleology panel
  *
  *
  * @author Lars Roe
  */

public class WoozlePanel extends ModelPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8112260482356402009L;
    ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.woozle.Res" );
   PopulusParameterField broodSize = new PopulusParameterField();
   JPanel targetPhrasePanel = new JPanel();
   JCheckBox showEvolveBox = new JCheckBox();
   PopulusParameterField mutationRate = new PopulusParameterField();
   FlowLayout flowLayout1 = new FlowLayout();
   JCheckBox diploidBox = new JCheckBox();
   JCheckBox keepSeed = new JCheckBox();
   JTextField targetPhraseField = new WoozleField();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   Border border1;
   TitledBorder titledBorder1;
   PopulusParameterField crossoverRate = new PopulusParameterField();
   private long seed;

   public WoozlePanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public int getTriggers() {
      return 0;
   }

   /** Returns Paraminfo */

   public WoozleParamInfo getWoozleParamInfo() {
      if( !keepSeed.isSelected() ) {
         getNewSeed();
      }

      //to get rid of characters not letters
      String text = targetPhraseField.getText();
      char[] temp = text.toCharArray();
      for( int i = 0;i < text.length();i++ ) {
         if( !( text.charAt( i ) >= 65 && text.charAt( i ) <= 90 ) && !( text.charAt( i ) >= 97 && text.charAt( i ) <= 122 ) ) {
            temp[i] = ' ';
         }
      }

      //to force the phrase to be 28 characters long
      //Lars - do we always want this?
      char[] finl = new char[28];
      for( int i = 0;i < 28;i++ ) {
         if( i < temp.length ) {
            finl[i] = temp[i];
         }
         else {
            finl[i] = ' ';
         }
      }

      //to capitalize all of the characters
      text = new String( finl );
      text = text.toUpperCase();
      if( diploidBox.isSelected() ) {
         return new WoozleParamInfo( broodSize.getInt(), mutationRate.getDouble(), crossoverRate.getDouble(),
                                     text, showEvolveBox.isSelected(), true, seed );
      }
      else {
         return new WoozleParamInfo( broodSize.getInt(), mutationRate.getDouble(), text, showEvolveBox.isSelected(), seed );
      }
   }

   /**
     */

   public int getDefaultTriggers() {
      return ModelPanelEventTypes.ENTER;
   }

   void diploidBox_actionPerformed( ActionEvent e ) {
      this.crossoverRate.setEnabled( diploidBox.isSelected() );
   }

   private void getNewSeed() {
      seed = System.currentTimeMillis();
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Target_Phrase" ) );
      this.setLayout( gridBagLayout1 );
      targetPhraseField.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
      targetPhraseField.setText( res.getString( "METHINKS_IT_IS_LIKE_A" ) );
      targetPhraseField.setColumns( 40 );
      targetPhrasePanel.setBorder( titledBorder1 );
      targetPhrasePanel.setLayout( flowLayout1 );
      broodSize.setCurrentValue( 50.0 );
      broodSize.setDefaultValue( 50.0 );
      broodSize.setIncrementAmount( 2.0 );
      broodSize.setMaxValue( 1000.0 );
      broodSize.setMinValue( 2.0 );
      broodSize.setParameterName( res.getString( "Brood_Size" ) );
      broodSize.setHelpText("Number of offspring phrases screened in each generation");
      mutationRate.setCurrentValue( 0.05 );
      mutationRate.setDefaultValue( 0.05 );
      mutationRate.setIncrementAmount( 0.01 );
      mutationRate.setMaxValue( 0.5 );
      mutationRate.setParameterName( res.getString( "Mutation_Rate" ) );
      mutationRate.setHelpText("Determines whether the child recieves a new, randomly chosen letter, or a faithful copy from the parental phrase");
      diploidBox.setText( res.getString( "Model_a_diploid" ) );
      diploidBox.setHorizontalAlignment( SwingConstants.CENTER );
      diploidBox.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            diploidBox_actionPerformed( e );
         }
      } );
      showEvolveBox.setSelected( true );
      showEvolveBox.setText( res.getString( "Show_phrase_evolving" ) );
      showEvolveBox.setHorizontalAlignment( SwingConstants.CENTER );
      keepSeed.setText( res.getString( "Same_seed_for_random" ) );
      keepSeed.setSelected( false );
      keepSeed.setHorizontalAlignment( SwingConstants.CENTER );
      crossoverRate.setCurrentValue( 0.1 );
      crossoverRate.setDefaultValue( 0.1 );
      crossoverRate.setEnabled( false );
      crossoverRate.setIncrementAmount( 0.05 );
      crossoverRate.setMaxValue( 0.5 );
      crossoverRate.setParameterName( res.getString( "Crossover_Rate" ) );
      crossoverRate.setHelpText("Determines whether transcription shifts from one parental phrase to the other, and is tested for each position in the offspring phrases");
      titledBorder1.setTitleJustification( 2 );
      this.add( broodSize, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 10 ), 0, 0 ) );
      this.add( mutationRate, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 10 ), 0, 0 ) );
      this.add( targetPhrasePanel, new GridBagConstraints( 0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 15, 0, 1, 0 ), 0, 0 ) );

      /*
      FlowLayout myLay = new FlowLayout();
      myLay.setVgap(5);
      SimpleVFlowLayout myVLay = new SimpleVFlowLayout();

      myLay.setAlignment(SimpleVFlowLayout.MIDDLE);
      this.setLayout(myLay);
      this.add(broodSize,null);
      this.add(mutationRate,null);
      this.add(diploidBox,null);
      this.add(crossoverRate,null);
      this.add(showEvolveBox,null);
      this.add(targetPhrasePanel,null);
      this.add(keepSeed, null);
      */
      targetPhrasePanel.add( targetPhraseField, null );
      this.add( crossoverRate, new GridBagConstraints( 0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 5, 0, 5, 10 ), 0, 0 ) );
      this.add( keepSeed, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 10, 0, 0 ), 0, 0 ) );
      this.add( diploidBox, new GridBagConstraints( 1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 10, 0, 0 ), 0, 0 ) );
      this.add( showEvolveBox, new GridBagConstraints( 1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 10, 0, 10 ), 0, 0 ) );
      registerChildren( this );
      getNewSeed();
   }

/** Changes enablement of components */

/*
public void itemStateChanged(ItemEvent e) {
Object src = e.getSource();
if (src instanceof Checkbox) {
if (src == crossoverEnable) {
boolean state = crossoverEnable.getState();
//sPanelCrossover.setEnabled(state);
showCrossover.setEnabled(state);
crossoverRate.setEnabled(state);
//sPanelCrossover.invalidate();
//sPanelCrossover.validate();
}
//cw.plot(ChooseWindow.UPDATE);
}
}
*/
}
