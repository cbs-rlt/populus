/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
// This snippet creates a new dialog box
// with buttons on the bottom.
//Title:
//Version:
//Copyright:
//Author:
//Company:
//Description:
package edu.umn.ecology.populus.core;
import javax.swing.*;

public class SaveDialog extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 3434462412812793131L;
public static final int MODEL_SETTINGS = 0;
   public static final int OUTPUT_TEXT = 1;
   public static final int OUTPUT_JPEG = 2;
   public static final int INPUT_JPEG = 3;
   public static final int CANCELLED = -1;
   public static final int OUTPUT_EXCEL = 4;

   //Be sure to keep these in the same order as the integers above!
   private static final String MODEL_SETTINGS_STR = "Save Inputs to File";
   private static final String OUTPUT_TEXT_STR = "Save Output Data to Text File";
   private static final String OUTPUT_JPEG_STR = "Save Output Graph to File";
   private static final String OUTPUT_EXCEL_STR = "Save Output in Excel Format";
   private static final String INPUT_JPEG_STR = "Save Input as a Picture";

   private static final String[] ALL_STR =  {
      MODEL_SETTINGS_STR, OUTPUT_TEXT_STR, OUTPUT_JPEG_STR,
      INPUT_JPEG_STR, OUTPUT_EXCEL_STR
   };

   //Be sure to keep these in the same order as the integers above!
   private static final String[] ALL_STR_EXCEPT_EXCEL =  {
      MODEL_SETTINGS_STR, OUTPUT_TEXT_STR, OUTPUT_JPEG_STR,
      INPUT_JPEG_STR
   };

   public static int getSaveChoice( boolean b ) {
      int j;
      if( b ) {
         j = JOptionPane.showOptionDialog( DesktopWindow.defaultWindow, "Choose which type of save to do", "Save Model", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, ALL_STR, MODEL_SETTINGS_STR );
      }
      else {
         j = JOptionPane.showOptionDialog( DesktopWindow.defaultWindow, "Choose which type of save to do", "Save Model", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, ALL_STR_EXCEPT_EXCEL, MODEL_SETTINGS_STR );
      }
      return j;
   }
}


   /*
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   JRadioButton modelSettingsButton = new JRadioButton();
   JRadioButton outputTextButton = new JRadioButton();
   JRadioButton outputJPEGButton = new JRadioButton();
   JRadioButton saveOutputExcelButton = new JRadioButton();
   Object[] buttons = {modelSettingsButton, outputTextButton,
   outputJPEGButton, saveOutputExcelButton};


   public SaveDialog() {
   try {
   jbInit();
   } catch (Exception e) {
   }
   }

   private void jbInit() throws Exception {
   this.setLayout(gridBagLayout2);
   modelSettingsButton.setSelected(true);
   modelSettingsButton.setText("Save Current Model Settings");
   outputTextButton.setText("Save Output to Text File");
   outputJPEGButton.setText("Save Output as Picture");
   saveOutputExcelButton.setText("Save Output in Excel Format");
   this.add(modelSettingsButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
   ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
   this.add(outputTextButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
   ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
   this.add(outputJPEGButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
   ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
   this.add(saveOutputExcelButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
   ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
   }
   */