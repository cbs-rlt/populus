/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.help;

import edu.umn.ecology.populus.core.AboutPopulusDialog;
import edu.umn.ecology.populus.core.PopPreferencesStorage;
import edu.umn.ecology.populus.fileio.Logging;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Vector;


/**
 * Help utilities - now uses PDF
 */

/**
 * @author Lars
 */
public class HelpUtilities {
    public static HelpUtilities hu;

    public static synchronized HelpUtilities createHelp() {
        if (hu == null) {
            hu = new HelpUtilities();
        }
        return hu;
    }


    private HelpUtilities() {
    }


    /**
     * Register a component's Help button with an ID.
     *
     * @param c  - the component that can trigger help (e.g., a Help Button within a model)
     * @param id - the help topic id to trigger.
     */
    public void installComponentHelp(Component c, final String id) {
        Logging.log("Registering for help, id=" + id);
        if (c instanceof JButton) {
            ((JButton) c).addActionListener(ae -> {
                Logging.log("Got event from " + ae.getSource().getClass().getName());
                Logging.log("Id of source is " + ae.getSource().hashCode());
                HelpUtilities.this.displayHelp(id);
            });
        }
    }

    /* Open up help for a given ID.
     * This is the primary method that always gets called, can do switching
     * on behavior here.
     */
    private void displayHelp(String id) {
        Logging.log("Help requested for id " + id);

        //execute(getOpenCommand(true,true,true),null,false);
        try {
            displayHelp(id, PopPreferencesStorage.getOpenPDFObject(), null);
        } catch (Exception e) {
            Logging.log("Unable to open Java help");
            Logging.log(e);
        }
    }

    public static boolean displayHelpTrial(OpenPDFMethod opm, Vector<String> results) {
        return displayHelp("dipg.overview", opm, results);
    }

    private static boolean displayHelp(String id, OpenPDFMethod opm, Vector<String> results) {
        String baseLoc = PDFHelpFileMgr.getHelpFileLocationURI();
        String location = baseLoc + "#" + id;
        Logging.log("About to displayHelp using method " + opm.getOpenMethod());
        if (OpenPDFMethod.DESKTOP == opm.getOpenMethod()) {
            return AboutPopulusDialog.openURIDesktop(location);
        } else if (OpenPDFMethod.JNLP == opm.getOpenMethod()) {
            return AboutPopulusDialog.openURIJNLP(location);
        } else { //if (OpenPDFMethod.DESKTOP == opm.getOpenMethod()) {
            String command = opm.getExecStr();
            String filename = PDFHelpFileMgr.getHelpFileAsFileName(true);
            command = command.replaceAll("%1", baseLoc);
            command = command.replaceAll("%2", id);
            command = command.replaceAll("%3", filename);
            if (null == results) {
                results = new Vector<>();
            }
            boolean retVal = execute(new String[]{command}, results, true);
            Logging.log("execute results (" + retVal + "): ");
            for (String s : results) {
                Logging.log(s);
            }
            return retVal;
        }
    }

    /* Stateless method to call a command
     *
     * Note: this appears in Core Web Programming from
     * Prentice Hall Publishers, and may be freely used
     * or adapted. 1997 Marty Hall, hall@apl.jhu.edu.
     * was modified though to store the result in the Vector.
     *
     */
    private static boolean execute(String[] command, Vector<String> result, boolean printResults) {
        try {
            // Print the output. Since we read until
            // there is no more input, this causes us
            // to wait until the process is completed
            if (printResults) {
                Logging.log("About to execute: " + Arrays.toString(command));
                Process p;
                if (1 == command.length) {
                    p = Runtime.getRuntime().exec(command[0]);
                } else {
                    p = Runtime.getRuntime().exec(command);
                }
                BufferedInputStream buffer =
                        new BufferedInputStream(p.getInputStream());
                BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));
                //DataInputStream commandResult = new DataInputStream(buffer);
                String s = null;
                try {
                    while ((s = commandResult.readLine()) != null) {
                        if (result == null)
                            edu.umn.ecology.populus.fileio.Logging.log("Output: " + s);
                        else
                            result.addElement(s);
                    }
                    commandResult.close();
                    if (p.exitValue() != 0) {
                        return (false);
                    }
                    // Ignore read errors; they mean process is done
                } catch (Exception e) {
                    Logging.log(e, "Error in reading command line results");
                }

                // If you don't print the results, then you
                // need to call waitFor to stop until the process
                // is completed
            }
        } catch (Exception e) {
            Logging.log(e, "Error executing command");
            result.addElement(e.toString());
            return (false);
        }
        return (true);
    }
}
