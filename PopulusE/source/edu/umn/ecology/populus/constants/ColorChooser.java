/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.constants;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.core.Model;

/**	This class accesses ColorScheme to allow the user to modify the colors used
  in the graphs.

  a LOT of stuff in this class could be replaced with for loops and arrays...
  but that would take more work...
  besides, unraveled loops are faster :)*/

public class ColorChooser extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = 5763100029469797725L;
	JPanel jPanel3 = new JPanel();
   JPanel panel1 = new JPanel();
   JComboBox jComboBox1 = new JComboBox();
   FlowLayout flowLayout1 = new FlowLayout();
   JPanel mainpanel = new JPanel();
   JLabel jLabel11 = new JLabel();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   JButton jButton3 = new JButton();
   JComboBox jComboBox2 = new JComboBox();
   JLabel jLabel12 = new JLabel();
   JComboBox jComboBox3 = new JComboBox();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   JComboBox jComboBox4 = new JComboBox();
   JButton jButton1 = new JButton();
   JComboBox jComboBox5 = new JComboBox();
   JButton jButton5 = new JButton();
   JComboBox jComboBox6 = new JComboBox();
   Color[] currentColor = (Color[])ColorScheme.colors.clone();
   JComboBox jComboBox7 = new JComboBox();
   Color currentBackground = ColorScheme.bG;
   JComboBox jComboBox8 = new JComboBox();
   JLabel jLabel10 = new JLabel();
   JComboBox jComboBox9 = new JComboBox();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   JComboBox jComboBox10 = new JComboBox();
   JLabel jTextArea1 = new JLabel();
   JComboBox jComboBox11 = new JComboBox();
   JPanel jPanel1 = new JPanel();
   JLabel jLabel1 = new JLabel();
   JButton jButton2 = new JButton();
   JLabel jLabel2 = new JLabel();
   JButton jButton4 = new JButton();
   JLabel jLabel3 = new JLabel();
   JPanel jPanel2 = new JPanel();
   JLabel jLabel4 = new JLabel();
   String[] keepColorStrings;
   JLabel jLabel5 = new JLabel();
   Color[] keepColor = (Color[])ColorScheme.colors.clone();
   JLabel jLabel6 = new JLabel();
   Color keepBack = ColorScheme.bG;
   JLabel jLabel7 = new JLabel();
   JComboBox jComboBox12 = new JComboBox();
   JLabel jLabel8 = new JLabel();
   JLabel jLabel9 = new JLabel();
   int[] keepIndex = (int[])ColorScheme.currentIndex.clone();
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.core.Res" );

   public void setSchemeIndex( int i ) {
      if( i <= jComboBox12.getItemCount() ) {
         ;
      }
      jComboBox12.setSelectedIndex( i );
   }

   public ColorChooser( Frame frame, String title, boolean modal ) {
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
   }

   public ColorChooser( Frame frame, String title ) {
      this( frame, title, false );
   }

   public ColorChooser( Frame frame ) {
      this( frame, res.getString( "Color_Scheme" ), false );
   }

   public int getSchemeIndex() {
      return jComboBox12.getSelectedIndex();
   }

   public static void bringUpColorDialog() {
      if( ColorScheme.keepOnlyOneOpen == null ) {
         Frame coolthing = new Frame();
         coolthing.setIconImage( Toolkit.getDefaultToolkit().getImage( DesktopWindow.class.getResource( "picon.gif" ) ) );
         ColorScheme.keepOnlyOneOpen = new ColorChooser( coolthing );
      }
      else {
         ColorScheme.keepOnlyOneOpen.reinit();
      }
      ColorScheme.keepOnlyOneOpen.setVisible( true );
   }

   void updateGraphs() {
      Vector<Model> v = ColorScheme.theseModels;
      Model m = null;
      for( int i = 0;i < v.size();i++ ) {
         m = (Model)v.get( i );
         m.updateOutput();
      }
   }

   void jComboBox9_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox9.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[8] );
         if( temp != null ) {
            currentColor[8] = temp;
         }
         else {
            jComboBox9.setSelectedIndex( ColorScheme.currentIndex[8] );
         }
      }
      else {
         currentColor[8] = ColorScheme.defaultColors[jComboBox9.getSelectedIndex()];
         ColorScheme.currentIndex[8] = jComboBox9.getSelectedIndex();
      }
      updateColors();
   }

   void reinit() {
      setKeepVariables();
      getCurrentIndex();
      updateColors();
   }

   /*
   i know that the following is VERY repetative, and probably shouldn't be so
   (for elegance sake) but it is here and works... so, if it ain't broke...
   */

   void jComboBox1_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox1.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[0] );
         if( temp != null ) {
            currentColor[0] = temp;
         }
         else {
            jComboBox1.setSelectedIndex( ColorScheme.currentIndex[0] );
         }
      }
      else {
         currentColor[0] = ColorScheme.defaultColors[jComboBox1.getSelectedIndex()];
         ColorScheme.currentIndex[0] = jComboBox1.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox3_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox3.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[2] );
         if( temp != null ) {
            currentColor[2] = temp;
         }
         else {
            jComboBox3.setSelectedIndex( ColorScheme.currentIndex[2] );
         }
      }
      else {
         currentColor[2] = ColorScheme.defaultColors[jComboBox3.getSelectedIndex()];
         ColorScheme.currentIndex[2] = jComboBox3.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox5_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox5.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[4] );
         if( temp != null ) {
            currentColor[4] = temp;
         }
         else {
            jComboBox5.setSelectedIndex( ColorScheme.currentIndex[4] );
         }
      }
      else {
         currentColor[4] = ColorScheme.defaultColors[jComboBox5.getSelectedIndex()];
         ColorScheme.currentIndex[4] = jComboBox5.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox7_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox7.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[6] );
         if( temp != null ) {
            currentColor[6] = temp;
         }
         else {
            jComboBox7.setSelectedIndex( ColorScheme.currentIndex[6] );
         }
      }
      else {
         currentColor[6] = ColorScheme.defaultColors[jComboBox7.getSelectedIndex()];
         ColorScheme.currentIndex[6] = jComboBox7.getSelectedIndex();
      }
      updateColors();
   }

   /**	"apply" button pressed*/

   void jButton4_actionPerformed( ActionEvent e ) {
      setColorScheme();
      updateGraphs();
   }

   /**	"ok" button pressed*/

   void jButton1_actionPerformed( ActionEvent e ) {
      setColorScheme();
      setKeepVariables();
      updateGraphs();
      setCurrentIndex();
      this.setVisible( false );
   }

   void makeLists() {
      for( int i = 0;i < ColorScheme.colorStrings2.length;i++ ) {
         jComboBox1.addItem( ColorScheme.colorStrings2[i] );
         jComboBox2.addItem( ColorScheme.colorStrings2[i] );
         jComboBox3.addItem( ColorScheme.colorStrings2[i] );
         jComboBox4.addItem( ColorScheme.colorStrings2[i] );
         jComboBox5.addItem( ColorScheme.colorStrings2[i] );
         jComboBox6.addItem( ColorScheme.colorStrings2[i] );
         jComboBox7.addItem( ColorScheme.colorStrings2[i] );
         jComboBox8.addItem( ColorScheme.colorStrings2[i] );
         jComboBox9.addItem( ColorScheme.colorStrings2[i] );
         jComboBox10.addItem( ColorScheme.colorStrings2[i] );
         jComboBox11.addItem( ColorScheme.colorStrings2[i] );
      }
   }

   /**	"set to default" button pressed*/

   void jButton3_actionPerformed( ActionEvent e ) {
      setListsToDefault();
      updateColors();
      jComboBox12.setSelectedIndex( 0 );
   }

   void setListsToDefault() {
      jComboBox1.setSelectedItem( ColorScheme.colorStrings2[0] );
      jComboBox2.setSelectedItem( ColorScheme.colorStrings2[1] );
      jComboBox3.setSelectedItem( ColorScheme.colorStrings2[2] );
      jComboBox4.setSelectedItem( ColorScheme.colorStrings2[3] );
      jComboBox5.setSelectedItem( ColorScheme.colorStrings2[4] );
      jComboBox6.setSelectedItem( ColorScheme.colorStrings2[5] );
      jComboBox7.setSelectedItem( ColorScheme.colorStrings2[6] );
      jComboBox8.setSelectedItem( ColorScheme.colorStrings2[7] );
      jComboBox9.setSelectedItem( ColorScheme.colorStrings2[8] );
      jComboBox10.setSelectedItem( ColorScheme.colorStrings2[9] );
      jComboBox11.setSelectedItem( ColorScheme.colorStrings2[ColorScheme.colorStrings2.length - 2] );
      for( int i = 0;i < currentColor.length - 1;i++ ) {
         currentColor[i] = ColorScheme.defaultColors[i];
      }
      currentColor[10] = ColorScheme.defaultColors[ColorScheme.defaultColors.length - 1];
   }

   /** "revert" button pressed*/

   void jButton5_actionPerformed( ActionEvent e ) {
      getKeepVariables();
      getCurrentIndex();
      updateColors();
   }

   void getCurrentIndex() {
      jComboBox1.setSelectedIndex( ColorScheme.currentIndex[0] );
      jComboBox2.setSelectedIndex( ColorScheme.currentIndex[1] );
      jComboBox3.setSelectedIndex( ColorScheme.currentIndex[2] );
      jComboBox4.setSelectedIndex( ColorScheme.currentIndex[3] );
      jComboBox5.setSelectedIndex( ColorScheme.currentIndex[4] );
      jComboBox6.setSelectedIndex( ColorScheme.currentIndex[5] );
      jComboBox7.setSelectedIndex( ColorScheme.currentIndex[6] );
      jComboBox8.setSelectedIndex( ColorScheme.currentIndex[7] );
      jComboBox9.setSelectedIndex( ColorScheme.currentIndex[8] );
      jComboBox10.setSelectedIndex( ColorScheme.currentIndex[9] );
      jComboBox11.setSelectedIndex( ColorScheme.currentIndex[10] );
   }

   void jComboBox2_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox2.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[1] );
         if( temp != null ) {
            currentColor[1] = temp;
         }
         else {
            jComboBox2.setSelectedIndex( ColorScheme.currentIndex[1] );
         }
      }
      else {
         currentColor[1] = ColorScheme.defaultColors[jComboBox2.getSelectedIndex()];
         ColorScheme.currentIndex[1] = jComboBox2.getSelectedIndex();
      }
      updateColors();
   }

   void setCurrentIndex() {
      ColorScheme.currentIndex[0] = jComboBox1.getSelectedIndex();
      ColorScheme.currentIndex[1] = jComboBox2.getSelectedIndex();
      ColorScheme.currentIndex[2] = jComboBox3.getSelectedIndex();
      ColorScheme.currentIndex[3] = jComboBox4.getSelectedIndex();
      ColorScheme.currentIndex[4] = jComboBox5.getSelectedIndex();
      ColorScheme.currentIndex[5] = jComboBox6.getSelectedIndex();
      ColorScheme.currentIndex[6] = jComboBox7.getSelectedIndex();
      ColorScheme.currentIndex[7] = jComboBox8.getSelectedIndex();
      ColorScheme.currentIndex[8] = jComboBox9.getSelectedIndex();
      ColorScheme.currentIndex[9] = jComboBox10.getSelectedIndex();
      ColorScheme.currentIndex[10] = jComboBox11.getSelectedIndex();
   }

   void jComboBox4_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox4.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[3] );
         if( temp != null ) {
            currentColor[3] = temp;
         }
         else {
            jComboBox4.setSelectedIndex( ColorScheme.currentIndex[3] );
         }
      }
      else {
         currentColor[3] = ColorScheme.defaultColors[jComboBox4.getSelectedIndex()];
         ColorScheme.currentIndex[3] = jComboBox4.getSelectedIndex();
      }
      updateColors();
   }

   void setColorScheme() {
      ColorScheme.colors = (Color[])currentColor.clone();
      ColorScheme.bG = currentBackground;
      setColorStrings();
   }

   void jComboBox6_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox6.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[5] );
         if( temp != null ) {
            currentColor[5] = temp;
         }
         else {
            jComboBox6.setSelectedIndex( ColorScheme.currentIndex[5] );
         }
      }
      else {
         currentColor[5] = ColorScheme.defaultColors[jComboBox6.getSelectedIndex()];
         ColorScheme.currentIndex[5] = jComboBox6.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox12_actionPerformed( ActionEvent e ) {
      int selection = jComboBox12.getSelectedIndex();
      switch( selection ) {
         case 0: //default
             setListsToDefault();
             jComboBox12.setSelectedIndex( 0 );
             break;

         case 2:
             final int start = 12; //where the old populus colors start
             jComboBox1.setSelectedIndex( start );
             jComboBox2.setSelectedIndex( start + 1 );
             jComboBox3.setSelectedIndex( start + 2 );
             jComboBox4.setSelectedIndex( start + 3 );
             jComboBox5.setSelectedIndex( start + 4 );
             jComboBox6.setSelectedIndex( start + 5 );
             jComboBox11.setSelectedIndex( start + 6 );
             for( int i = 0;i < 6;i++ ) {
                currentColor[i] = ColorScheme.colors[i + start];
             }
             currentColor[10] = ColorScheme.colors[start + 6];
             updateColors();
             jComboBox12.setSelectedIndex( 2 );
             break;
      }
   }

   void jComboBox8_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox8.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[7] );
         if( temp != null ) {
            currentColor[7] = temp;
         }
         else {
            jComboBox8.setSelectedIndex( ColorScheme.currentIndex[7] );
         }
      }
      else {
         currentColor[7] = ColorScheme.defaultColors[jComboBox8.getSelectedIndex()];
         ColorScheme.currentIndex[7] = jComboBox8.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox11_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox11.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom" ), currentBackground );
         if( temp != null ) {
            currentBackground = temp;
         }
         else {
            jComboBox11.setSelectedIndex( ColorScheme.currentIndex[10] );
         }
      }
      else {
         currentBackground = ColorScheme.defaultColors[jComboBox11.getSelectedIndex()];
         ColorScheme.currentIndex[10] = jComboBox11.getSelectedIndex();
      }
      updateColors();
   }

   void jComboBox10_actionPerformed( ItemEvent e ) {
      jComboBox12.setSelectedIndex( 1 );
      if( jComboBox10.getSelectedIndex() == ColorScheme.colorStrings2.length - 1 ) {
         JDialog what = new JDialog( this );
         Color temp;
         temp = JColorChooser.showDialog( what, res.getString( "Choose_a_custom_color" ), currentColor[9] );
         if( temp != null ) {
            currentColor[9] = temp;
         }
         else {
            jComboBox10.setSelectedIndex( ColorScheme.currentIndex[9] );
         }
      }
      else {
         currentColor[9] = ColorScheme.defaultColors[jComboBox10.getSelectedIndex()];
         ColorScheme.currentIndex[9] = jComboBox10.getSelectedIndex();
      }
      updateColors();
   }

   /**	"cancel" button pressed*/

   void jButton2_actionPerformed( ActionEvent e ) {
      getKeepVariables();
      getCurrentIndex();
      updateGraphs();
      this.setVisible( false );
   }

   private void getKeepVariables() {
      ColorScheme.currentIndex = (int[])keepIndex.clone();
      ColorScheme.colors = (Color[])keepColor.clone();
      ColorScheme.bG = keepBack;
      setColorStrings();
   }

   private void setKeepVariables() {
      keepIndex = (int[])ColorScheme.currentIndex.clone();
      keepColor = (Color[])ColorScheme.colors.clone();
      keepBack = ColorScheme.bG;
      setColorStrings();
   }

   private void jbInit() throws Exception {
      panel1.setLayout( gridBagLayout1 );
      jLabel1.setText( res.getString( "Color_1" ) );
      jLabel2.setText( res.getString( "Color_2" ) );
      jLabel3.setText( res.getString( "Color_3" ) );
      jLabel4.setText( res.getString( "Color_4" ) );
      jLabel5.setText( res.getString( "Color_5" ) );
      jLabel6.setText( res.getString( "Color_6" ) );
      jLabel7.setText( res.getString( "Color_7" ) );
      jLabel8.setText( res.getString( "Color_8" ) );
      jLabel9.setText( res.getString( "Color_9" ) );
      jLabel10.setText( res.getString( "Color_10" ) );
      jLabel11.setText( res.getString( "Background" ) );
      panel1.setBorder( BorderFactory.createLoweredBevelBorder() );
      jTextArea1.setBackground( Color.lightGray );
      jTextArea1.setText( res.getString( "Edit_the_color_scheme" ) );
      mainpanel.setLayout( gridBagLayout3 );
      jButton1.setText( res.getString( "OK" ) );
      jButton1.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jButton1_actionPerformed( e );
         }
      } );
      jButton2.setText( res.getString( "Cancel" ) );
      jButton2.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jButton2_actionPerformed( e );
         }
      } );
      jButton3.setText( res.getString( "Set_Defaults" ) );
      jButton3.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jButton3_actionPerformed( e );
         }
      } );
      jButton4.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jButton4_actionPerformed( e );
         }
      } );
      jButton4.setText( res.getString( "Apply" ) );
      jButton5.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jButton5_actionPerformed( e );
         }
      } );
      jButton5.setText( res.getString( "Revert" ) );
      makeLists();
      getCurrentIndex();
      updateColors();
      jComboBox1.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox1_actionPerformed( e );
            }
         }
      } );
      jComboBox2.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox2_actionPerformed( e );
            }
         }
      } );
      jComboBox3.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox3_actionPerformed( e );
            }
         }
      } );
      jComboBox4.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox4_actionPerformed( e );
            }
         }
      } );
      jComboBox5.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox5_actionPerformed( e );
            }
         }
      } );
      jComboBox6.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox6_actionPerformed( e );
            }
         }
      } );
      jComboBox7.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox7_actionPerformed( e );
            }
         }
      } );
      jComboBox8.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox8_actionPerformed( e );
            }
         }
      } );
      jComboBox9.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox9_actionPerformed( e );
            }
         }
      } );
      jComboBox10.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox10_actionPerformed( e );
            }
         }
      } );
      jComboBox11.addItemListener( new java.awt.event.ItemListener()  {

         public void itemStateChanged( ItemEvent e ) {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
               jComboBox11_actionPerformed( e );
            }
         }
      } );
      jPanel1.setLayout( flowLayout1 );
      jLabel12.setText( res.getString( "Color_plan_" ) );
      jComboBox12.addItem( res.getString( "Default" ) );
      jComboBox12.addItem( res.getString( "Custom" ) );
      jComboBox12.addItem( res.getString( "Old_Populus" ) );
      jComboBox12.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            jComboBox12_actionPerformed( e );
         }
      } );
      jComboBox12.setSelectedIndex( ColorScheme.schemeIndex );
      panel1.add( jComboBox1, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel1, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 5, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox2, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel2, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox3, new GridBagConstraints( 1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel3, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox4, new GridBagConstraints( 1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel4, new GridBagConstraints( 0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox5, new GridBagConstraints( 1, 4, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel5, new GridBagConstraints( 0, 4, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox6, new GridBagConstraints( 1, 5, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel6, new GridBagConstraints( 0, 5, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox7, new GridBagConstraints( 1, 6, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel7, new GridBagConstraints( 0, 6, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox8, new GridBagConstraints( 1, 7, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel8, new GridBagConstraints( 0, 7, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox9, new GridBagConstraints( 1, 8, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel9, new GridBagConstraints( 0, 8, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jComboBox10, new GridBagConstraints( 1, 9, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel10, new GridBagConstraints( 0, 9, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel11, new GridBagConstraints( 0, 10, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 5, 0, 2 ), 0, 0 ) );
      panel1.add( jComboBox11, new GridBagConstraints( 1, 10, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 5 ), 0, 0 ) );
      mainpanel.add( jPanel3, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      jPanel3.add( jLabel12, null );
      jPanel3.add( jComboBox12, null );
      jPanel1.add( jButton4, null );
      jPanel1.add( jButton5, null );
      jPanel1.add( jButton3, null );
      jPanel2.add( jButton1, null );
      jPanel2.add( jButton2, null );
      mainpanel.add( jPanel1, new GridBagConstraints( 0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      mainpanel.add( jPanel2, new GridBagConstraints( 0, 4, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      mainpanel.add( jTextArea1, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      mainpanel.add( panel1, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      getContentPane().add( mainpanel );
   }

   private void updateColors() {
      if( currentColor[0].getRGB() != Color.white.getRGB() ) {
         jComboBox1.setForeground( currentColor[0] );
      }
      else {
         jComboBox1.setForeground( Color.black );
      }
      if( currentColor[1].getRGB() != Color.white.getRGB() ) {
         jComboBox2.setForeground( currentColor[1] );
      }
      else {
         jComboBox2.setForeground( Color.black );
      }
      if( currentColor[2].getRGB() != Color.white.getRGB() ) {
         jComboBox3.setForeground( currentColor[2] );
      }
      else {
         jComboBox3.setForeground( Color.black );
      }
      if( currentColor[3].getRGB() != Color.white.getRGB() ) {
         jComboBox4.setForeground( currentColor[3] );
      }
      else {
         jComboBox4.setForeground( Color.black );
      }
      if( currentColor[4].getRGB() != Color.white.getRGB() ) {
         jComboBox5.setForeground( currentColor[4] );
      }
      else {
         jComboBox5.setForeground( Color.black );
      }
      if( currentColor[5].getRGB() != Color.white.getRGB() ) {
         jComboBox6.setForeground( currentColor[5] );
      }
      else {
         jComboBox6.setForeground( Color.black );
      }
      if( currentColor[6].getRGB() != Color.white.getRGB() ) {
         jComboBox7.setForeground( currentColor[6] );
      }
      else {
         jComboBox7.setForeground( Color.black );
      }
      if( currentColor[7].getRGB() != Color.white.getRGB() ) {
         jComboBox8.setForeground( currentColor[7] );
      }
      else {
         jComboBox8.setForeground( Color.black );
      }
      if( currentColor[8].getRGB() != Color.white.getRGB() ) {
         jComboBox9.setForeground( currentColor[8] );
      }
      else {
         jComboBox9.setForeground( Color.black );
      }
      if( currentColor[9].getRGB() != Color.white.getRGB() ) {
         jComboBox10.setForeground( currentColor[9] );
      }
      else {
         jComboBox10.setForeground( Color.black );
      }
      if( currentBackground.getRed() < 80 && currentBackground.getBlue() < 80 && currentBackground.getGreen() < 80 ) {
         jComboBox11.setBackground( Color.white );
      }
      else {
         jComboBox11.setBackground( currentBackground );
      }
      panel1.add( jLabel3, new GridBagConstraints( 0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel2, new GridBagConstraints( 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel1, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel4, new GridBagConstraints( 0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel5, new GridBagConstraints( 0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel6, new GridBagConstraints( 0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel7, new GridBagConstraints( 0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel8, new GridBagConstraints( 0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel9, new GridBagConstraints( 0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel10, new GridBagConstraints( 0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      panel1.add( jLabel11, new GridBagConstraints( 0, 10, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 5, 0, 5 ), 0, 0 ) );
   }

   private void setColorStrings() {
      for( int i = 0;i < ColorScheme.colorStrings.length;i++ ) {

         //ColorScheme.colorStrings[i] = "<font color=" + currentColor[i].getRGB() + ">";
         ColorScheme.colorStrings[i] = "<font color=" + ColorScheme.colors[i].getRGB() + ">";
      }
   }
}
