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

import javax.swing.*;
import java.util.ResourceBundle;

public class PopulusToolButton extends JButton {

    /**
     *
     */
    private static final long serialVersionUID = -8546615415111250769L;
    static ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");
    //Button Strings
    public static final String CLOSE = res.getString("Close");
    public static final String SAVE = res.getString("Save");
    public static final String HELP = res.getString("Help");
    public static final String NEW = res.getString("Model");
    public static final String PRINT = res.getString("Print");
    public static final String OPEN = res.getString("Load");
    public static final String QUIT = res.getString("Quit");
    public static final String OPTIONS = res.getString("Options");
    public static final String OUTPUTO = res.getString("Repeat");
    public static final String OUTPUTI = res.getString("View");
    public static final String PREFERENCES = res.getString("Preferences");
    public static final String PICTURE = res.getString("Picture");
    public static final String OUTPUTSWITCH = "Switch";
    public static final String RESTOREDEFAULT = "Default";
    public static final String ABOUT = "About";

    //TTT = ToolTipText
    public static final String SAVE_TTT = res.getString("Save_To_Disk");
    public static final String PRINT_TTT = res.getString("Print");
    public static final String CLOSE_TTT = res.getString("Close_This_Window");
    public static final String MAIN_HELP_TTT = res.getString("How_to_run_the");
    public static final String OPEN_TTT = res.getString("Reload_a_Previously");
    public static final String PICTURE_TTT = res.getString("Save_to_Picture");
    public static final String OUTPUT_TTT = res.getString("Update_the_Output");
    public static final String HELP_TTT = res.getString("Text_introduction_to");
    public static final String NEW_TTT = res.getString("Choose_a_Model");
    public static final String OPTIONS_TTT = res.getString("Options");
    public static final String OUTPUTSWITCH_TTT = "Switch the Output Type";
    public static final String PREFERENCES_TTT = res.getString("See_and_Edit_Your");
    public static final String QUIT_TTT = res.getString("Quit_Populus");
    public static final String ITERATE_TTT = "Advance the model one step";
    public static final String RESTOREDEFAULT_TTT = "Restore Default Values";
    public static final String ABOUT_TTT = "About Populus";


    private static final String HELP_IMAGE_FILE = "Bulb.gif";
    private static final String NEW_IMAGE_FILE = "new.gif";
    private static final String SAVE_IMAGE_FILE = "Save.gif";
    private static final String CLOSE_IMAGE_FILE = "Delete.gif";
    private static final String OUTPUT_IMAGE_FILE = "Enter.gif";
    private static final String PICTURE_IMAGE_FILE = "CameraFlash.gif";
    private static final String PREFERENCES_IMAGE_FILE = "Palette.gif";
    private static final String PRINT_IMAGE_FILE = "Print.gif";
    private static final String OPTIONS_IMAGE_FILE = "Hammer.gif";
    private static final String OPEN_IMAGE_FILE = "Open.gif";
    private static final String QUIT_IMAGE_FILE = "Delete.gif";
    private static final String SWITCH_IMAGE_FILE = "Switch.gif";
    private static final String ITERATE_IMAGE_FILE = "Iterate.gif";
    private static final String RESTOREDEFAULT_IMAGE_FILE = "new.gif";
    private static final String ABOUT_IMAGE_FILE = "P.20.20.gif";

    //Non-static items.  So small and few.
    private ImageIcon image;
    private String text;

    public PopulusToolButton() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        PopPreferencesStorage.addButton(this);
    }

    /**
     * Call this when you're done with it.
     * Lars - Garbage collection doesn't work automatically
     * since the listeners don't go away.  We should consider looking at the Swing UI architecture
     * to fix this the right way.
     */
    public void dispose() {
        PopPreferencesStorage.removeButton(this);
    }

    /**
     * this function is for the preference of whether or not to display icons with the
     * text on the buttons
     */
    void setLook() {
        int type = PopPreferencesStorage.getButtonType();

        //set icon
        if ((type & PopPreferencesStorage.IMAGES) != 0) {
            this.setIcon(image);
        } else {
            this.setIcon(null);
        }

        //set text
        if ((type & PopPreferencesStorage.TEXT) != 0) {
            this.setText(text);
        } else {
            this.setText(null);
        }
    }

    private PopulusToolButton(String imageFile, String text, String toolTipText) {
        this();
        this.setValues(imageFile, text, toolTipText);
    }

    public void setValues(String imageFile, String text, String toolTipText) {
        this.image = new ImageIcon(this.getClass().getResource(imageFile));
        this.text = text;
        this.setToolTipText(toolTipText);
        setLook();
    }

    public static JButton createPrintButton() {
        return new PopulusToolButton(PRINT_IMAGE_FILE, PRINT, PRINT_TTT);
    }

    public static JButton createCloseButton() {
        return new PopulusToolButton(CLOSE_IMAGE_FILE, CLOSE, CLOSE_TTT);
    }

    public static JButton createRestoreDefaultButton() {
        return new PopulusToolButton(RESTOREDEFAULT_IMAGE_FILE, RESTOREDEFAULT, RESTOREDEFAULT_TTT);
    }

    public static JButton createAboutButton() {
        return new PopulusToolButton(ABOUT_IMAGE_FILE, ABOUT, ABOUT_TTT);
    }

    public static JButton createHelpButton() {
        return new PopulusToolButton(HELP_IMAGE_FILE, HELP, HELP_TTT);
    }

    public static JButton createIterateButton() {
        return new PopulusToolButton(ITERATE_IMAGE_FILE, OUTPUTO, ITERATE_TTT);
    }

    public static JButton createSwitchButton() {
        return new PopulusToolButton(SWITCH_IMAGE_FILE, OUTPUTSWITCH, OUTPUTSWITCH_TTT);
    }

    public static JButton createPreferencesButton() {
        return new PopulusToolButton(PREFERENCES_IMAGE_FILE, PREFERENCES, PREFERENCES_TTT);
    }

    public static JButton createOptionButton() {
        return new PopulusToolButton(OPTIONS_IMAGE_FILE, OPTIONS, OPTIONS_TTT);
    }

    public static JButton createOutputIButton() {
        return new PopulusToolButton(OUTPUT_IMAGE_FILE, OUTPUTI, OUTPUT_TTT);
    }

    public static JButton createMainHelpButton() {
        return new PopulusToolButton(HELP_IMAGE_FILE, HELP, MAIN_HELP_TTT);
    }

    public static JButton createPictureButton() {
        return new PopulusToolButton(PICTURE_IMAGE_FILE, PICTURE, PICTURE_TTT);
    }

    public static JButton createNewButton() {
        return new PopulusToolButton(NEW_IMAGE_FILE, NEW, NEW_TTT);
    }

    public static JButton createSaveButton() {
        return new PopulusToolButton(SAVE_IMAGE_FILE, SAVE, SAVE_TTT);
    }

    public static JButton createOpenButton() {
        return new PopulusToolButton(OPEN_IMAGE_FILE, OPEN, OPEN_TTT);
    }

    public static JButton createQuitButton() {
        return new PopulusToolButton(QUIT_IMAGE_FILE, QUIT, QUIT_TTT);
    }


}
