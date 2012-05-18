
//Title:

//Version:

//Copyright:

//Author:

//Company:

//Description:
package edu.umn.ecology.populus.core;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import java.util.*;

public class ModelInputFrame extends PopInternalFrame {
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.core.Res" );
   BorderLayout mainBorderLayout = new BorderLayout();
   transient Model model;
   int whichSelected = 1;
   JButton outputButton = PopulusToolButton.createOutputIButton();
   JButton closeButton = PopulusToolButton.createCloseButton();
   JButton printButton = PopulusToolButton.createPrintButton();
   JButton helpButton = PopulusToolButton.createHelpButton();
   JButton saveButton = PopulusToolButton.createSaveButton();
   JPanel jPanel1 = new JPanel();

   public void jbInit() throws Exception {
      this.setResizable( true );
      this.getContentPane().setLayout( mainBorderLayout );
      saveButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            save();
         }
      } );
      outputButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            outputButton_actionPerformed( e );
         }
      } );
      closeButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            closeButton_actionPerformed( e );
         }
      } );
      printButton.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            printButton_actionPerformed( e );
         }
      } );
      saveButton.setText( res.getString( "File" ) );
      toolBar.setFloatable( false );
      this.getContentPane().add( toolBar, BorderLayout.NORTH );
      toolBar.add( outputButton, null );
      toolBar.add( saveButton, null );
      toolBar.add( helpButton, null );
      toolBar.add( printButton, null );
      toolBar.add( closeButton, null );
      this.getContentPane().add( jPanel1, BorderLayout.SOUTH );
      this.setTitle( model.getThisModelInputName() + res.getString( "_Input" ) );
   }

   public ModelInputFrame( Model model ) {
      super();
      setModelColor( model.getModelColor() );
      this.model = model;
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   void outputButton_actionPerformed( ActionEvent e ) {
      model.updateOutput();
   }

   void closeButton_actionPerformed( ActionEvent e ) {
      model.closeInput();
   }

   Component getHelpComponent() {
      return helpButton;
   }

   void printButton_actionPerformed( ActionEvent e ) {
      model.printInputGraphics();
   }

   void save() {
      model.save();
   }
}
