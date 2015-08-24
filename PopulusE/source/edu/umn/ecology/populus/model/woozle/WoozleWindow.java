/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.woozle;
import edu.umn.ecology.populus.resultwindow.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/**
  * Where Woozle Results are displayed
  *
  *
  */

public class WoozleWindow extends OutputPanel implements Runnable,ActionListener {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5036237593784372454L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.woozle.Res" );
   JToggleButton pauseButton = new JToggleButton();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JLabel generationLabel = new JLabel();
   GridLayout gridLayout1 = new GridLayout();
   JLabel currentPhraseLabel = new JLabel();
   GridLayout gridLayout2 = new GridLayout();
   WoozleParamInfo info;
   WoozleAGPanel aGPanel = new WoozleAGPanel();
   JPanel currentPhrasePanel = new JPanel();
   GridLayout gridLayout3 = new GridLayout();
   JLabel currentPhrase = new JLabel();
   JLabel targetPhrase = new JLabel();
   JPanel targetPhrasePanel = new JPanel();
   JPanel generationPanel = new JPanel();
   JLabel generations = new JLabel();
   JLabel targetPhraseLabel = new JLabel();
   private int phase = RUNNING;
   private Thread runner;
   private boolean isSuspended = false;

   /* This Button is separate from the dataPanel */
   private static final int RUNNING = 0;
   private static final int DONE = 2;
   private static final int PAUSED = 1;

   public WoozleWindow( WoozleParamInfo params ) {
      info = params;
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
      init();
   }

   public void updateWoozle( WoozleParamInfo params ) {
      info = params;
      remove( aGPanel );
      aGPanel = new WoozleAGPanel();
      this.add( aGPanel, new GridBagConstraints( 0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      init();
   }

   /** The window must finish up: it needs to stop */

   public void destroy() {
      super.destroy();

   //runner.stop();
   }

   public WoozleWindow() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   /** This is the method that takes on the long process of evaluation */

   //Be sure that stopping, starting, resuming, etc. change button to pause, resume, etc.

   public void run() {
      while( !info.isDone() ) {

         //Pause
         try {
            Thread.sleep( edu.umn.ecology.populus.core.PopPreferencesStorage.getDelayTime() );
         }
         catch( InterruptedException e ) {

         }
         if( !isSuspended ) {
            info.doNextGeneration();
            updateComponents();
         }
      }
      phase = DONE;
      updateComponents();
      pauseButton.setText( res.getString( "Run_Again" ) );
      pauseButton.getModel().setSelected( false );
   }

   public void actionPerformed( ActionEvent e ) {
      switch( phase ) {
         case RUNNING:
             isSuspended = true;
             pauseButton.setText( res.getString( "Resume" ) );
             phase = PAUSED;
             break;

         case PAUSED:
             pauseButton.setText( res.getString( "Pause" ) );
             phase = RUNNING;
             isSuspended = false;
             break;

         case DONE:
             pauseButton.setText( res.getString( "Pause" ) );
             pauseButton.getModel().setSelected( false );
             phase = RUNNING;
             aGPanel.clear();
             info.startOver();
             updateComponents();
             runner = new Thread( this );
             runner.start();
             break;default:
      }
   }

   protected void init() {
      if( info.getBroodSize() <= info.getNumParents() ) {
         throw new BadUserException( res.getString( "Number_of_parents" ) );
      }
      targetPhrase.setText( info.getTargetPhrase() );
      if( info.getShowEvolve() ) {
         ; //dataPanel.addThis(currentPhrasePanel);
      }
      pauseButton.addActionListener( this );
      runner = new Thread( this );
      runner.start();

   //stopThinkingSigns();
   }

   /** Updates all the components */

   void updateComponents() {
      currentPhrase.setText( info.getCurrentPhrase() );
      generations.setText( Integer.toString( info.getGeneration() ) );
      aGPanel.addAG( info.getBestMatch(), info.getGeneration() );
   }

   private void jbInit() throws Exception {
      generations.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
      generations.setText( "0" );
      generationLabel.setHorizontalAlignment( SwingConstants.RIGHT );
      generationLabel.setText( res.getString( "Generations_" ) );
      generationPanel.setLayout( gridLayout1 );
      currentPhrasePanel.setLayout( gridLayout2 );
      currentPhraseLabel.setHorizontalAlignment( SwingConstants.RIGHT );
      currentPhraseLabel.setText( res.getString( "Current_Phrase_" ) );
      currentPhrase.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
      currentPhrase.setText( res.getString( "methinks_it_is_like_a" ) );
      targetPhrasePanel.setLayout( gridLayout3 );
      targetPhraseLabel.setHorizontalAlignment( SwingConstants.RIGHT );
      targetPhraseLabel.setText( res.getString( "Target_Phrase_" ) );
      targetPhrase.setFont( new java.awt.Font( "Monospaced", 0, 12 ) );
      targetPhrase.setText( res.getString( "methinks_it_is_like_a" ) );
      this.setLayout( gridBagLayout1 );
      generationPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
      currentPhrasePanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
      targetPhrasePanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
      gridLayout1.setHgap( 5 );
      gridLayout2.setHgap( 5 );
      gridLayout3.setHgap( 5 );
      pauseButton.setText( res.getString( "Pause" ) );
      aGPanel.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
      this.add( generationPanel, new GridBagConstraints( 0, 2, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 1, 1, 1 ), 0, 0 ) );
      generationPanel.add( generationLabel, null );
      generationPanel.add( generations, null );
      this.add( aGPanel, new GridBagConstraints( 0, 3, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      if( !( info != null && !info.getShowEvolve() ) ) {
         this.add( currentPhrasePanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 1, 1, 1, 1 ), 0, 0 ) );
      }
      currentPhrasePanel.add( currentPhraseLabel, null );
      currentPhrasePanel.add( currentPhrase, null );
      this.add( targetPhrasePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 1, 1, 0, 1 ), 0, 0 ) );
      targetPhrasePanel.add( targetPhraseLabel, null );
      targetPhrasePanel.add( targetPhrase, null );
      this.add( pauseButton, new GridBagConstraints( 0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
   }
}
