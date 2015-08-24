/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import java.awt.event.*;
import java.io.*;

/** Panel for use with models that require a run-time parameter.*/

public class RunningTimePanel extends JPanel implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -4226071707631879578L;
public static final double STEADYSTATE = -1.3;
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   PopulusParameterField paramTime = new PopulusParameterField();
   JRadioButton steadyStateButton = new JRadioButton();
   Border border1;
   TitledBorder titledBorder1;
   JRadioButton fixedTimeButton = new JRadioButton();
   ButtonGroup bg = new ButtonGroup();
   private double incrementTime = 20.0;
   private double maxTime = 10000.0;
   private double minTime = 1.0;
   private double defaultTime = 100.0;

   public RunningTimePanel( double defaultTime, double maxTime, double incrementTime ) {
      this.defaultTime = defaultTime;
      this.maxTime = maxTime;
      this.incrementTime = incrementTime;
      try {
         jbInit();
      }
      catch( Exception ex ) {
         ex.printStackTrace();
      }
   }

   public void setAutoIncrement( boolean yesAutoIncrement, boolean yesKeepInteger ) {
      paramTime.setAutoIncrement( yesAutoIncrement, yesKeepInteger );
   }

   public double getMaxTime() {
      return paramTime.getMaxValue();
   }

   public void setMaxTime( double newMaxTime ) {
      paramTime.setMaxValue( newMaxTime );
   }

   public void setMinTime( double newMinTime ) {
      paramTime.setMinValue( newMinTime );
   }

   public double getTime() {
      if( steadyStateButton.isSelected() ) {
         return STEADYSTATE;
      }
      else {
         return paramTime.getDouble();
      }
   }

   public double getDefaultTime() {
      return paramTime.getDefaultValue();
   }

   public void setDefaultTime( double newDefaultTime ) {
      paramTime.setDefaultValue( newDefaultTime );
      paramTime.setCurrentValue( newDefaultTime );
   }

   public void setFixedTime(boolean expl) {
      steadyStateButton.setSelected(!expl);
      fixedTimeButton.setSelected(expl);
      paramTime.setEnabled(expl);
   }

   public double getIncrementTime() {
      return paramTime.getIncrementAmount();
   }

   public double getMinTime() {
      return paramTime.getMinValue();
   }

   public void setIncrementTime( double newIncrementTime ) {
      paramTime.setIncrementAmount( newIncrementTime );
   }

   public RunningTimePanel( double defaultTime ) {
      this.defaultTime = defaultTime;
      try {
         jbInit();
      }
      catch( Exception ex ) {
         ex.printStackTrace();
      }
   }

   public RunningTimePanel() {
      try {
         jbInit();
      }
      catch( Exception ex ) {
         ex.printStackTrace();
      }
   }

   void writeObject( ObjectOutputStream oos ) throws IOException {
      oos.defaultWriteObject();
   }

   void steadyStateButton_actionPerformed( ActionEvent e ) {
      this.paramTime.setEnabled( false );
   }

   void fixedTimeButton_actionPerformed( ActionEvent e ) {
      this.paramTime.setEnabled( true );
   }

   void readObject( ObjectInputStream ois ) throws ClassNotFoundException,IOException {
      ois.defaultReadObject();
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, "Termination Conditions" );
      fixedTimeButton.setSelected( true );
      fixedTimeButton.setText( "Run until time:" );
      fixedTimeButton.setFocusPainted( false );
      fixedTimeButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            fixedTimeButton_actionPerformed( e );
         }
      } );
      titledBorder1.setBorder( BorderFactory.createLineBorder( Color.black ) );
      this.setBorder( titledBorder1 );
      this.setLayout( simpleVFlowLayout1 );
      steadyStateButton.setText( "Run until steady state" );
      steadyStateButton.setFocusPainted( false );
      steadyStateButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            steadyStateButton_actionPerformed( e );
         }
      } );
      paramTime.setColumns( 8 );
      paramTime.setMinValue( minTime );
      paramTime.setMaxValue( maxTime );
      paramTime.setCurrentValue( defaultTime );
      paramTime.setDefaultValue( defaultTime );
      paramTime.setHelpText( "Length of time to run simulation" );
      paramTime.setIncrementAmount( incrementTime );
      paramTime.setParameterName( "Time" );
      this.add( steadyStateButton, null );
      this.add( fixedTimeButton, null );
      this.add( paramTime, null );
      bg.add( fixedTimeButton );
      bg.add( steadyStateButton );
   }
}
