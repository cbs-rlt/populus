
// This snippet creates a new about box.

//Title:

//Version:

//Copyright:

//Author:

//Company:

//Description:

//
package edu.umn.ecology.populus.core;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;

public class AboutPopulusDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3075187335844374818L;
   JButton button1 = new JButton();
   Border border1;
   JPanel jPanel1 = new JPanel();
   JLabel line3 = new JLabel();
   JLabel line1 = new JLabel();
   XYLayout xYLayout1 = new XYLayout();
   JLabel line7 = new JLabel();
   JPanel panel1 = new JPanel();
   VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
   JPanel jPanel3 = new JPanel();
   JPanel jPanel2 = new JPanel();
   JLabel line8 = new JLabel();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JLabel line6 = new JLabel();
   ImageIcon populusImage;
   JLabel line4 = new JLabel();
   JLabel line13 = new JLabel();
   JLabel line2 = new JLabel();
   JLabel line12 = new JLabel();
   TitledBorder titledBorder1;
   JLabel line11 = new JLabel();
   JButton goB = new JButton();
   JLabel line10 = new JLabel();
   JLabel line9 = new JLabel();
   JLabel line5 = new JLabel();
    public AboutPopulusDialog( JFrame frame, String title ) {
      this( frame, title, false );
   }

   public AboutPopulusDialog( JFrame frame ) {
      this( frame, "", false );
   }

   public AboutPopulusDialog( JFrame frame, String title, boolean modal ) {
      super( frame, title, modal );
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
      pack();

      //Center the window
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = getSize();
      if (frameSize.height > screenSize.height) {
         frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
         frameSize.width = screenSize.width;
      }
      setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      setVisible( true );
   }

   //Close the dialog

   void button1_actionPerformed( ActionEvent e ) {
      dispose();
   }

   void this_windowClosing( WindowEvent e ) {
      dispose();
   }
   
   //TODO: fix up error handling on this.
   void openURI( String uriString ) {
	   //TODO: Make work for earlier JDK versions.
	   
	   //With JNLP 1.5, need JNLP libraries
	   /*
	   javax.jnlp.BasicService.showDocument(uriString);
	   
	   //For 1.6 and above
       if( !java.awt.Desktop.isDesktopSupported() ) {
           System.err.println( "Desktop is not supported (fatal)" );
           return; //ERROR
       }

       java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

       if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {

           System.err.println( "Desktop doesn't support the browse action (fatal)" );
           return; //ERROR
       }
       try {
           java.net.URI uri = new java.net.URI( uriString );
           desktop.browse( uri );
       }
       catch ( Exception e ) {
           System.err.println( e.getMessage() );
       }
       */
   }

   void goB_actionPerformed( ActionEvent e ) {
	   openURI( "http://www.cbs.umn.edu/populus/" );
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLoweredBevelBorder();
      titledBorder1 = new TitledBorder( "" );
      button1.setActionCommand( "" );
      button1.setFocusPainted( false );
      button1.setRolloverEnabled( true );
      jPanel1.setLayout( verticalFlowLayout1 );
      button1.setText( "OK" );
      button1.addActionListener( new AboutPopulusDialog_button1_actionAdapter( this ) );
      this.addWindowListener( new AboutPopulusDialog_this_windowAdapter( this ) );
      panel1.setLayout( gridBagLayout1 );
      line1.setText("Populus, Java Version 5.4, Octember 2005" );
      line2.setText( "url: http://www.cbs.umn.edu/populus/" );
      line3.setText( "email: populus@ecology.umn.edu" );
      line4.setText( "  " );
      line5.setText( "Populus Programmers:" );
      line6.setText( "Amos Anderson (2001-2003, Java Version)" );
      line13.setText( "Sharareh Noorbaloochi (2001-present, Java Version)" );
      line7.setText( "Lars Roe (1997-2001, Java Version)" );
      line8.setText( "Chris Bratteli (1989-1993, Pascal Version)" );
      line9.setText( "  " );
      line10.setText( "Don Alstad" );
      line11.setText( "Department of Ecology, Evolution & Behavior" );
      line12.setText( "University of Minnesota" );
      Color a = new Color( 103, 1, 0 );

      //Color b = new Color(236,196,62);
      Color b = new Color( 236, 210, 62 );
      jPanel1.setBackground( a );
      jPanel2.setBackground( a );
      jPanel3.setBackground( a );
      jPanel3.setLayout( xYLayout1 );
      goB.setBackground( b );
      goB.setBorder( border1 );
      goB.setFocusPainted( false );
      button1.setBackground( b );
      button1.setBorder( border1 );
      button1.setPreferredSize( new Dimension( 50, 25 ) );
      line1.setForeground( b );
      line2.setForeground( b );
      line3.setForeground( b );
      line4.setForeground( b );
      line5.setForeground( b );
      line6.setForeground( b );
      line7.setForeground( b );
      line8.setForeground( b );
      line9.setForeground( b );
      line10.setForeground( b );
      line11.setForeground( b );
      line12.setForeground( b );
      line13.setForeground( b );
      goB.setText( "go!" );
      goB.addActionListener(new AboutPopulusDialog_goB_actionAdapter(this));
      panel1.add(jPanel1,
                 new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 0, 0, 0), 0, 0));
      jPanel1.add(line1, null);
      jPanel3.add(goB, new XYConstraints(265, 5, -1, -1));
      jPanel3.add( line2, new XYConstraints( 0, 7, -1, -1 ) );
      jPanel1.add( line10, null );
      jPanel1.add( line11, null );
      jPanel1.add( line12, null );
      jPanel1.add( line4, null );
      jPanel1.add( jPanel3, null );
      jPanel1.add( line3, null );
      jPanel1.add( line9, null );
      jPanel1.add( line5, null );
      jPanel1.add( line6, null );
      jPanel1.add( line13, null );
      jPanel1.add( line7, null );
      jPanel1.add( line8, null );
      panel1.add( jPanel2, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      jPanel2.add( button1, null );
      getContentPane().add( panel1 );
    }
}

class AboutPopulusDialog_button1_actionAdapter implements ActionListener {
   AboutPopulusDialog adaptee;

   public void actionPerformed( ActionEvent e ) {
      adaptee.button1_actionPerformed( e );
   }

   AboutPopulusDialog_button1_actionAdapter( AboutPopulusDialog adaptee ) {
      this.adaptee = adaptee;
   }
}

class AboutPopulusDialog_this_windowAdapter extends WindowAdapter {
   AboutPopulusDialog adaptee;

   public void windowClosing( WindowEvent e ) {
      adaptee.this_windowClosing( e );
   }

   AboutPopulusDialog_this_windowAdapter( AboutPopulusDialog adaptee ) {
      this.adaptee = adaptee;
   }
}

class AboutPopulusDialog_goB_actionAdapter implements java.awt.event.ActionListener {
   AboutPopulusDialog adaptee;

   public void actionPerformed( ActionEvent e ) {
      adaptee.goB_actionPerformed( e );
   }

   AboutPopulusDialog_goB_actionAdapter( AboutPopulusDialog adaptee ) {
      this.adaptee = adaptee;
   }
}
