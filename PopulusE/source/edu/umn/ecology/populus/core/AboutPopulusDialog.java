/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

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
import java.lang.reflect.Method;

import javax.swing.*;
import javax.swing.border.*;

import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

public class AboutPopulusDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3075187335844374818L;
   JButton button1 = new JButton();
   Border border1;
   JPanel jPanel1 = new JPanel();
   JTextPane line3 = new JTextPane();
   JLabel line1 = new JLabel();
   JLabel line7 = new JLabel();
   JPanel panel1 = new JPanel();
   SimpleVFlowLayout verticalFlowLayout1 = new SimpleVFlowLayout();
   JPanel urlPanel = new JPanel();
   JPanel jPanel2 = new JPanel();
   JLabel line8 = new JLabel();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JLabel line6 = new JLabel();
   ImageIcon populusImage;
   JLabel line4 = new JLabel();
   JLabel line13 = new JLabel();
   JTextPane line2 = new JTextPane();
   JLabel line12 = new JLabel();
   TitledBorder titledBorder1;
   JLabel line11 = new JLabel();
   JButton goB = new JButton();
   JLabel line10 = new JLabel();
   JLabel line9 = new JLabel();
   JLabel line5 = new JLabel();
   private final JPanel panel3rdParty = new JPanel();
   private final JTextPane txtpnDependsOnThe = new JTextPane();
   private final JPanel emailPanel = new JPanel();
   private JButton emailButton = new JButton("Email");
    public AboutPopulusDialog( JFrame frame, String title ) {
      this( frame, title, false );
   }

   /**
    * @wbp.parser.constructor
    */
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
   //Returns True if successful
   //Note: this isn't used directly by Help, only when user clicks on a URI.
   static public boolean openURI( String uriString ) {
	   //TODO: Should switch on PopPreferences, like context help does.
	   return openURIDesktop( uriString );
	   //return openURIJNLP( uriString );
   }

   /** Use java.awt.Desktop to open uriString.
    * Only supports Java 1.6 and above.
    * @param uriString - URI to open, may include #name
    * @return true if successful.
    */
   static public boolean openURIDesktop( String uriString ) {
	   //Use java.awt.Desktop, new in Java 1.6
	   Logging.log("Trying to open with Desktop: " + uriString);
	   try {
		   Class<?> desktopClass = Class.forName("java.awt.Desktop");
		   Method getDeskMeth = desktopClass.getMethod("getDesktop");
		   Object desktop = getDeskMeth.invoke(null);
		   Method openURI = desktopClass.getMethod("browse", java.net.URI.class);
		   java.net.URI uri = new java.net.URI(uriString);
		   openURI.invoke(desktop, uri);
		   return true;

	   } catch (Exception e) {
		   Logging.log("Could not open URI " + uriString);
		   Logging.log(e);
		   return false;
	   }
   }
   
   /** Use java.awt.Desktop to open uriString.
    * Only supports Java 1.6 and above.
    * @param uriString - URI to open, may include #name
    * @return true if successful.
    */
   static public boolean openURIJNLP( String uriString ) {
	   //With JNLP 1.5, need JNLP libraries
	   //javax.jnlp.BasicService.showDocument(uriString);
	   //Use java.awt.Desktop, new in Java 1.6
	   Logging.log("Trying to open with JNLP's BasicService: " + uriString);
	   try {
		   java.net.URL url = new java.net.URL(uriString);
		   Method lookupMeth = Class.forName("javax.jnlp.ServiceManager").getMethod("lookup", String.class);
           Object basicService = lookupMeth.invoke(null, "javax.jnlp.BasicService");
		   Method showDocMeth = Class.forName("javax.jnlp.BasicService").getMethod("showDocument", java.net.URL.class);
		   showDocMeth.invoke(basicService, url);
		   return true;
	   } catch (Exception e) {
		   Logging.log(e, "Could not use JNLP to open URI " + uriString);
		   return false;
	   }
   }

   void goB_actionPerformed( ActionEvent e ) {
	   openURI( "http://www.cbs.umn.edu/populus/" );
   }
   boolean sendEmail(String emailAddress) {
	   try {
		   Class<?> desktopClass = Class.forName("java.awt.Desktop");
		   Method getDeskMeth = desktopClass.getMethod("getDesktop");
		   Object desktop = getDeskMeth.invoke(null);
		   Method openURI = desktopClass.getMethod("mail", java.net.URI.class);
		   java.net.URI uri = new java.net.URI("mailto:" + emailAddress);
		   openURI.invoke(desktop, uri);
		   return true;
	   } catch (Exception e) {
		   Logging.log("Could not open email to " + emailAddress);
		   Logging.log(e);
		   return false;
	   }
 
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
      gridBagLayout1.rowWeights = new double[]{0.0, 1.0};
      gridBagLayout1.columnWeights = new double[]{0.0};
      panel1.setLayout( gridBagLayout1 );
      line1.setText("Populus, Java Version 5.5.2, Aug 2014" );
      line4.setText( "  " );
      line5.setText( "Populus Programmers:" );
      line7.setText( "Lars Roe (1997-present, Java Version)" );
      line6.setText( "Amos Anderson (2001-2003, Java Version)" );
      line13.setText( "Sharareh Noorbaloochi (2001-2002, Java Version)" );
      line8.setText( "Chris Bratteli (1989-1993, Pascal Version)" );
      line9.setText( "  " );
      line10.setText( "Don Alstad" );
      line11.setText( "Department of Ecology, Evolution & Behavior" );
      line12.setText( "University of Minnesota" );
      Color maroon = new Color( 103, 1, 0 );

      Color gold = new Color( 236, 210, 62 );
      jPanel1.setBackground( maroon );
      jPanel2.setBackground( maroon );
      urlPanel.setForeground(gold);
      urlPanel.setBackground( maroon );
      button1.setBackground( gold );
      button1.setBorder( border1 );
      button1.setPreferredSize( new Dimension( 50, 25 ) );
      panel1.setBackground(maroon);
      panel1.setForeground(gold);
      line1.setForeground( gold );
      line4.setForeground( gold );
      line5.setForeground( gold );
      line6.setForeground( gold );
      line7.setForeground( gold );
      line8.setForeground( gold );
      line9.setForeground( gold );
      line10.setForeground( gold );
      line11.setForeground( gold );
      line12.setForeground( gold );
      line13.setForeground( gold );
      panel1.add(jPanel1,
                 new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                                        GridBagConstraints.CENTER,
                                        GridBagConstraints.BOTH,
                                        new Insets(0, 0, 5, 0), 0, 0));
      jPanel1.add(line1, null);
      GridBagLayout gbl_urlPanel = new GridBagLayout();
      gbl_urlPanel.columnWeights = new double[]{1.0, 0.0};
      gbl_urlPanel.rowWeights = new double[]{0.0};
      urlPanel.setLayout(gbl_urlPanel);
      goB.setAlignmentX(Component.RIGHT_ALIGNMENT);
      goB.setHorizontalAlignment(SwingConstants.RIGHT);
      goB.setToolTipText("Go to Web site");
      goB.setBackground( gold );
      goB.setBorder( border1 );
      goB.setFocusPainted( false );
      goB.setText( "Go" );
      goB.addActionListener(new AboutPopulusDialog_goB_actionAdapter(this));
      line2.setBackground(maroon);
      line2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
      line2.setText( " url: http://www.cbs.umn.edu/populus/" );
      line2.setForeground( gold );
      GridBagConstraints gbc_line2 = new GridBagConstraints();
      gbc_line2.anchor = GridBagConstraints.WEST;
      gbc_line2.insets = new Insets(0, 5, 0, 5);
      gbc_line2.gridx = 0;
      gbc_line2.gridy = 0;
      urlPanel.add( line2, gbc_line2 );
      GridBagConstraints gbc_goB = new GridBagConstraints();
      gbc_goB.insets = new Insets(0, 0, 5, 0);
      gbc_goB.anchor = GridBagConstraints.EAST;
      gbc_goB.gridx = 1;
      gbc_goB.gridy = 0;
      urlPanel.add(goB, gbc_goB);
      jPanel1.add( line10, null );
      jPanel1.add( line11, null );
      jPanel1.add( line12, null );
      jPanel1.add( line4, null );
      jPanel1.add( urlPanel, null );
      emailPanel.setForeground(gold);
      emailPanel.setBackground(maroon);
      
      jPanel1.add(emailPanel);
      GridBagLayout gbl_emailPanel = new GridBagLayout();
      gbl_emailPanel.columnWeights = new double[]{1.0, 0.0};
      gbl_emailPanel.rowWeights = new double[]{0.0};
      emailPanel.setLayout(gbl_emailPanel);
      GridBagConstraints gbc_line3 = new GridBagConstraints();
      gbc_line3.insets = new Insets(0, 5, 0, 5);
      gbc_line3.anchor = GridBagConstraints.WEST;
      gbc_line3.gridx = 0;
      gbc_line3.gridy = 0;
      line3.setBackground(maroon);
      line3.setForeground( gold );
      line3.setBorder(javax.swing.BorderFactory.createEmptyBorder());
      emailPanel.add(line3, gbc_line3);
      line3.setText( " email: populus@umn.edu" );
      emailButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		sendEmail("populus@umn.edu");
      	}
      });
      
      emailButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
      emailButton.setHorizontalAlignment(SwingConstants.RIGHT);
      emailButton.setToolTipText("Send Email");
      emailButton.setBackground( gold );
      emailButton.setBorder( border1 );
      emailButton.setFocusPainted( false );
      emailButton.setText( "Email" );      
      
      emailPanel.add(emailButton);
      jPanel1.add( line9, null );
      jPanel1.add( line5, null );
      jPanel1.add( line7, null );
      jPanel1.add( line6, null );
      jPanel1.add( line13, null );
      jPanel1.add( line8, null );
      panel1.add( jPanel2, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0 ) );
      jPanel2.add( button1, null );
      getContentPane().add( panel1 );
      
      GridBagConstraints gbc_panel3rdParty = new GridBagConstraints();
      gbc_panel3rdParty.fill = GridBagConstraints.BOTH;
      gbc_panel3rdParty.gridx = 0;
      gbc_panel3rdParty.gridy = 1;
      panel3rdParty.setBackground(maroon);
      panel3rdParty.setForeground(gold);
      panel1.add(panel3rdParty, gbc_panel3rdParty);
      panel3rdParty.setLayout(new BorderLayout(0, 0));
      txtpnDependsOnThe.setText("Depends on the distributable libraries:\r\n* Jama Javanumerics (http://math.nist.gov/javanumerics/jama/)\r\n* Borland's JBCL\r\n* KLGroup's JClass charts\r\n* JFreeChart (www.jfree.org/jfreechart/)");
      txtpnDependsOnThe.setBackground(maroon);
      txtpnDependsOnThe.setForeground(gold);
      
      panel3rdParty.add(txtpnDependsOnThe);
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
