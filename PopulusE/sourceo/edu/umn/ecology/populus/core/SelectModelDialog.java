
// This snippet creates a new dialog box

// with buttons on the bottom.

//Title:

//Version:

//Copyright:

//Author:

//Company:

//Description:
package edu.umn.ecology.populus.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import edu.umn.ecology.populus.visual.ppfield.*;

public class SelectModelDialog extends JDialog {
   JPanel panel2 = new JPanel();
   JButton button1 = new JButton();
   JButton button2 = new JButton();
   Border border1;
   JPanel jPanel1 = new JPanel();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   GridLayout gridLayout1 = new GridLayout();
   JPanel panel1 = new JPanel();
   JList jList1 = new JList();
   
   public SelectModelDialog( Frame frame, String title ) {
      this( frame, title, false );
   }
   
   public SelectModelDialog( Frame frame ) {
      this( frame, "", false );
   }
   
   public SelectModelDialog( Frame frame, String title, boolean modal ) {
      super( frame, title, modal );
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
      pack();
   }
   
   // OK
   
   void button1_actionPerformed( ActionEvent e ) {
      dispose();
   }
   
   // Cancel
   
   void button2_actionPerformed( ActionEvent e ) {
      dispose();
   }
   
   void this_windowClosing( WindowEvent e ) {
      dispose();
   }
   
   private void jbInit() throws Exception {
      border1 = BorderFactory.createRaisedBevelBorder();
      jPanel1.setLayout( gridLayout1 );
      panel2.setBorder( border1 );
      button1.setText( "OK" );
      button1.addActionListener( new SelectModelDialog_button1_actionAdapter( this ) );
      button2.setText( "Cancel" );
      gridLayout1.setHgap( 4 );
      button2.addActionListener( new SelectModelDialog_button2_actionAdapter( this ) );
      this.addWindowListener( new SelectModelDialog_this_windowAdapter( this ) );
      panel1.setLayout( gridBagLayout1 );
      panel1.add( panel2, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel2.add( jList1, null );
      panel1.add( jPanel1, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 4, 8, 4, 8 ), 0, 0 ) );
      jPanel1.add( button1, null );
      jPanel1.add( button2, null );
      getContentPane().add( panel1 );
   }
}

class SelectModelDialog_button1_actionAdapter implements ActionListener {
   SelectModelDialog adaptee;
   
   public void actionPerformed( ActionEvent e ) {
      adaptee.button1_actionPerformed( e );
   }
   
   SelectModelDialog_button1_actionAdapter( SelectModelDialog adaptee ) {
      this.adaptee = adaptee;
   }
}

class SelectModelDialog_button2_actionAdapter implements ActionListener {
   SelectModelDialog adaptee;
   
   public void actionPerformed( ActionEvent e ) {
      adaptee.button2_actionPerformed( e );
   }
   
   SelectModelDialog_button2_actionAdapter( SelectModelDialog adaptee ) {
      this.adaptee = adaptee;
   }
}

class SelectModelDialog_this_windowAdapter extends WindowAdapter {
   SelectModelDialog adaptee;
   
   public void windowClosing( WindowEvent e ) {
      adaptee.this_windowClosing( e );
   }
   
   SelectModelDialog_this_windowAdapter( SelectModelDialog adaptee ) {
      this.adaptee = adaptee;
   }
}
