package edu.umn.ecology.populus.help;

import java.io.File;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Vector;
import edu.umn.ecology.populus.core.DesktopWindow;


/**
 * Help utilities - now uses PDF
*/

public class HelpUtilities implements ActionListener {
   public static HelpUtilities hu;
   private static String errorMessage = "";
   private static File f = null;
   private static String helpFileName = "PopulusHelp.pdf";
   private static String cmd;
   private static String app;

   /*in this static block, several things need to be determined. first, we need to make sure the
   help file exists and is in the right place.
   the second thing that needs to be determined is whether the command prompt is cmd or command.
   then, because we can't be too sure about whether
   adobe acrobat or adobe reader are installed, we need to check and find out. I assume that the windows
   command "ftype AcroExch.Document" will always determine this... the whole point here is so that we can pass the
   executable the "don't show the splash screen" parameters... otherwise, we'd only have to use "start thing.pdf"

   */
   private static synchronized void staticInit() {
      if (f != null)
         return;
      //find help file
      String path = System.getProperty("user.dir"); //Lars ???
      String separator = System.getProperty("file.separator");
      //TODO -- can we do something like this to look into JAR?  getClass().getResource("res" + separator + helpFileName)
      f = new File(path+separator+helpFileName);
      if(!f.canRead()){
         errorMessage = "Can't find the help file at \n"+f.getAbsolutePath();
         //TODO  do we want this?? JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, errorMessage, "Error", JOptionPane.PLAIN_MESSAGE);
         edu.umn.ecology.populus.fileio.Logging.log();
         edu.umn.ecology.populus.fileio.Logging.log("Can't seem to find the help file...");
         edu.umn.ecology.populus.fileio.Logging.log("Supposed to be at "+f.getAbsolutePath());
      }

      //find which windows os
      String os = System.getProperty("os.name");
      if(os.startsWith("Windows")){
         if(os.endsWith("NT") || os.endsWith("2000") || os.endsWith("XP")){
            cmd = "cmd.exe";
         } else {
            cmd = "command.com";
         }

         //find Adobe Reader or Adobe Acrobat
         Vector result = new Vector();
         execute(new String[]{cmd,"/c","ftype","AcroExch.Document"},result,true);
         if(result.size() > 0){
            if(((String)result.get(0)).toLowerCase().indexOf("acrord32") >= 0){
               app = "acrord32";
            } else if(((String)result.get(0)).toLowerCase().indexOf("acrobat") >= 0){
               app = "acrobat";
            } else {
               String message = "You don't seem to have Adobe Reader installed.";
               app = "";
               JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, message, "Error", JOptionPane.PLAIN_MESSAGE);
            }
         } else {
            app = "";
            //not sure what do to here... might mean that Adobe Reader is not installed
         }
      }
   }

   private HelpUtilities() {
      staticInit();
      Exec e = new Exec();
      /* Why is this code here?
      if(System.getProperty("os.name").startsWith("Windows")){
         execute(getOpenCommand(true,true,false),null,false);
      }
      */
   }

   /**
    * the interesting problem here is getting acrobat to open just as we please. i guess i'm not sure,
    * but i had thought it was a good idea to look for the filetype "AcroExch.Document", but apparently about
    * .3% of populus users reported an error with that. the error was found in that the ftype command (above) would
    * return nothing. so, now it checks for a return of nothing, and if it does that, then we just stick with
    * the start command. but getting the start command to work is a little tricky. the command form is:
    * start /D"directory path" filename and this seems to be working. i discovered later that maybe i should have gone with
    * using the "assoc" command to find the parameter to use with "ftype", but i'm just guessing here, so
    * i don't really know what i'm "supposed to do". i think this should work fine though.
    * @param isNoSplash
    * @param isBackground
    * @param isOpenFile
    * @return
    */
   private static String[] getOpenCommand(boolean isNoSplash, boolean isBackground, boolean isOpenFile){
      String os = System.getProperty("os.name");
      Vector command = new Vector();
      if(os.startsWith("Windows")){
         boolean canHaveSpecial = app.length() != 0;
         command.add(cmd);
         command.add("/c");
         command.add("start");
         command.add(app);
         if(canHaveSpecial){
            if(isNoSplash) command.add("/s");
            if(isBackground) command.add("/h");
         } else {
            if(isOpenFile) command.add("/D");
            else command.clear();
         }
      } else if(os.startsWith("Mac")){
         //i can't figure out how to get the same options using adobe reader on osx. they may not exist...
         //i can't even find a command line executable. i can use 'open /Applications/"Adobe Reader 6.0.app"', but nowhere
         //in that line are arguments allowed for adobe...
         command.add("open");
      } else {
         //based on my research, this should work. it seems that *NIX don't need path names to run acroread...
         command.add("acroread");
      }
      if(isOpenFile)
         command.add(f.getAbsolutePath());
      String[] a = new String[] {};
      return (String[]) command.toArray(a);
   }

   public void actionPerformed(ActionEvent ae){
      updateHelp();
   }

   public java.awt.event.ActionListener getMenuActionListener() {
      return this;
   }

   public void installComponentHelp( Component c, String id ) {
      //right now, the id tag is ignored. maybe eventually we'll figure out how to make it useful again
      if(c instanceof JButton){
         ((JButton)c).addActionListener(this);
      }
   }

   public static synchronized HelpUtilities createHelp() {
      if( hu == null ) {
         hu = new HelpUtilities();
      }
      return hu;
   }

   public static void updateHelp() {
      if(f.canRead()){
         execute(getOpenCommand(true,true,true),null,false);
      } else {
         JOptionPane.showMessageDialog( DesktopWindow.defaultWindow, errorMessage, "Error", JOptionPane.PLAIN_MESSAGE);
      }
   }

   // This appears in Core Web Programming from
   // Prentice Hall Publishers, and may be freely used
   // or adapted. 1997 Marty Hall, hall@apl.jhu.edu.
   // was modified though to store the result in the Vector.
   private static boolean execute(String[] command, Vector result, boolean printResults) {
      try {
         // Start running command, returning immediately.
         Process p = Runtime.getRuntime().exec(command);
         // Print the output. Since we read until
         // there is no more input, this causes us
         // to wait until the process is completed
         if(printResults) {
            BufferedInputStream buffer =
                  new BufferedInputStream(p.getInputStream());
            BufferedReader commandResult = new BufferedReader(new InputStreamReader(buffer));
            //DataInputStream commandResult = new DataInputStream(buffer);
            String s = null;
            try {
               while ((s = commandResult.readLine()) != null){
                  if(result == null)
                     edu.umn.ecology.populus.fileio.Logging.log("Output: " + s);
                  else
                     result.addElement(s);
               }
               commandResult.close();
               if (p.exitValue() != 0) {
                  return(false);
               }
               // Ignore read errors; they mean process is done
               } catch (Exception e) {}

               // If you don't print the results, then you
               // need to call waitFor to stop until the process
               // is completed
         }
      } catch (Exception e) {
         return(false);
      }
      return(true);
   }
}
