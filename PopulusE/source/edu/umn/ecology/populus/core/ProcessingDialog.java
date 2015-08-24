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
package edu.umn.ecology.populus.core;
import java.awt.*;
import javax.swing.*;

public class ProcessingDialog extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = -2891643239222105852L;
JButton cancelButton = new JButton();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JLabel thinkingLabel = new JLabel();
   JProgressBar jProgressBar1 = new JProgressBar();
   JPanel panel1 = new JPanel();
   
   public ProcessingDialog() {
      this( null, "", false );
   }
   
   public ProcessingDialog( Frame frame, String title, boolean modal ) {
      super( frame, title, modal );
      try {
         jbInit();
         pack();
      }
      catch( Exception ex ) {
         ex.printStackTrace();
      }
   }
   
   void jbInit() throws Exception {
      panel1.setLayout( gridBagLayout1 );
      cancelButton.setText( "Cancel" );
      thinkingLabel.setText( "Thinking..." );
      getContentPane().add( panel1 );
      panel1.add( cancelButton, new GridBagConstraints( 0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      panel1.add( thinkingLabel, new GridBagConstraints( 0, 0, 1, 1, 0.0, 1.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jProgressBar1, new GridBagConstraints( 0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
   }
}
