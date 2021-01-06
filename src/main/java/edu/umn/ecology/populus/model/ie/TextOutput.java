/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.math.NumberMath;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class TextOutput {
    /**
     * This method handles the writing of the model's data to a text file.
     */
    public static void saveInteractionEngineText(double[][] data, String[] headers, String[] labels, boolean isDiscrete) {
        final int columnWidth = 20;
        try {
            if (data == null)
                throw new IOException("Data is null.");

            if (labels.length != data.length)
                throw new IOException("Arrays don't match.");

            OutputStreamWriter osw;
            String filename = edu.umn.ecology.populus.fileio.IOUtility.getFileName("IEData.dat", "Save Data", FileDialog.SAVE);
            if (filename != null) {
                osw = new OutputStreamWriter(new FileOutputStream(filename));
            } else return;


            osw.write(headers[0] + "\n\n");
            osw.write("The differential equations are:\n");
            for (int i = 1; i < headers.length; i++)
                if (isDiscrete)
                    osw.write("N" + i + "(t+1) = " + headers[i] + "\n");
                else
                    osw.write("dN" + i + "/dt = " + headers[i] + "\n");
            osw.write("\n\n\n");
            for (String label : labels) osw.write(LabToStr(label, columnWidth, false));
            osw.write("\n");

            for (int j = 0; j < data[0].length; j++) {
                for (double[] datum : data) osw.write(NumToStr(datum[j], columnWidth, 10, false));
                osw.write("\n");
            }

            osw.flush();
            osw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(DesktopWindow.defaultWindow, "Could not write file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * HTMLLabels have a lot of extraneous data for text output. This
     * method strips that data off. Perhaps an output option could be made for
     * creating an html file to keep the "decorations."
     * a -- the string to modify
     * len -- the length of the formatted string (0 for no change)
     * stripSpaces -- if we want to strip all spaces out of the string
     */

    public static String LabToStr(String a, int len, boolean stripSpaces) {
        //strip off the extraneous data
        StringBuilder sb = new StringBuilder(a);
        int i = 0;
        while (i < sb.length()) {
            if (stripSpaces && sb.charAt(i) == ' ') {
                sb.deleteCharAt(i);
            }
            if (sb.charAt(i) == '<') {
                while (sb.charAt(i) != '>') {
                    sb.deleteCharAt(i);
                }
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }
        sb.setLength(i);
        if (sb.charAt(0) == ' ') {
            sb.deleteCharAt(0);
        }
        if (len == 0) {
            len = sb.length();
        }
        return NewStringLength(sb.toString(), len, ' ', false);
    }

    /**
     * Doubles are different lengths when printed to the screen. This function
     * forces them to be a uniform length so that the columns of numbers look good.
     */

    public static String NewStringLength(String a, int len, char replaceWith, boolean addComma) {

        //Preconditions: replaceWith is the character that will be used to fill up length added
        //		to the string.
        StringBuilder sb = new StringBuilder(a);
        if (len == 0) {
            len = sb.length();
        }
        int dif = sb.length() - len;
        if (dif > 0) {
            if (addComma) {
                sb.setLength(len - 1);
                sb.append(",");
            } else {
                sb.setLength(len);
            }
        } else {
            if (dif < 0) {
                if (addComma) {
                    sb.append(",");
                }
                while (sb.length() - len < 0) {
                    sb.append("" + replaceWith); //Lars - Adding "" because JDK 1.5 is stupid.
                }
            } else {
                if (addComma) {
                    sb.setCharAt(sb.length() - 1, ',');
                }
            }
        }
        return sb.toString();
    }

    public static String NumToStr(double num, int len, int sigfig, boolean addComma) {
        //num = NumberMath.roundSig( num, 5, 0 );
        //return NewStringLength( String.valueOf( num ), len, ' ', addComma );
        return NewStringLength(NumberMath.roundSigScientific(num, sigfig, 0), len, ' ', addComma);
    }

    void doTextOutput(EquationCalculator ss, double[] init, double rt) {
        edu.umn.ecology.populus.fileio.Logging.log("\n\nInteraction Engine Console Output");
        edu.umn.ecology.populus.fileio.Logging.log("Discrete Calculations:");
        for (int i = 0; i <= rt; i++) {
            edu.umn.ecology.populus.fileio.Logging.log("\nt = " + i);
            for (int j = 0; j < init.length; j++) {
                edu.umn.ecology.populus.fileio.Logging.log("  N" + j + ":\t" + init[j]);
            }
            init = ss.calculateValues(init, rt);
            edu.umn.ecology.populus.fileio.Logging.log("");
        }
        edu.umn.ecology.populus.fileio.Logging.log("");
    }
}