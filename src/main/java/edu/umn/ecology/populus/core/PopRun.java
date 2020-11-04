/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.fileio.Logging;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.ResourceBundle;

public class PopRun {
    ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.core.Res");
    boolean packFrame = false;

    /**
     * Theoretically, we could add a way to change the log file here.  Then we'd hook up getopt or something.
     */
    public static void main(String[] argv) {
        Logging.addFileLog(Logging.getDefaultLogFile());
        //Allow users to specify the Preferences File on the command line
        if ((argv.length > 1) && argv[0].equals("-p")) {
            Logging.log("Using preferences file at " + argv[1]);
            PopPreferencesStorage.setPreferencesFile(argv[1]);
        }
        new PopRun();
    }

    PopRun() {
        edu.umn.ecology.populus.fileio.Logging.log(res.getString("Populus_Starting_") + " on " + System.getProperty("os.name"));
        String buildTime = "<Unable to find build time>";
        try {
            //Read timestamp.txt from current directory.
            InputStream is = PopRun.class.getResourceAsStream("timestamp.txt");
            InputStreamReader isr = new InputStreamReader(is);
            java.io.BufferedReader ri = new java.io.BufferedReader(isr);
            buildTime = ri.readLine();
        } catch (Exception whateverHappensInMinnesotaStaysInMinnesota) {
        }
        Logging.log("Populus built " + buildTime + "\n");
        try {
            Logging.log("Using " + UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Object[] localUIDefaults = new String[]{
                    "StyledRadioButtonUI", "edu.umn.ecology.populus.visual.StyledRadioButtonUI",
                    "BracketUI", "edu.umn.ecology.populus.visual.BracketUI"
            };
            UIManager.getLookAndFeelDefaults().putDefaults(localUIDefaults);
            System.out.print(".");
            new DesktopWindow();
        } catch (Exception e) {
            e.printStackTrace();
            //Try to log to file
            try {
                String fileName = System.getProperty("user.home", ".") + System.getProperty("file.separator")
                        + "PopulusErrorLog" + System.currentTimeMillis() + ".txt";
                java.io.PrintWriter w = new java.io.PrintWriter(new java.io.FileOutputStream(fileName));
                w.write("!Fatal error in Populus!\n");
                w.write("Populus built " + buildTime + "\n");
                w.write("Date: " + new Date() + "\n\n");
                e.printStackTrace(w);
            } catch (Exception e2) {
                edu.umn.ecology.populus.fileio.Logging.log("Couldn't write error to log file");
            }
        }
    }
}

