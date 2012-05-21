package edu.umn.ecology.populus.install;

import edu.umn.ecology.populus.fileio.Logging;
import javax.jnlp.*;  //From javaws.jar
import javax.swing.JOptionPane;

//TODO: License agreement
public class Installer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Logging.log("In Installer main");
		if (args.length > 0) {
			if (args[0].equals("install")) {
				boolean success = install(true);
				if (success) {
					JOptionPane.showMessageDialog(null,"Successfully installed Populus");
				} else {
					JOptionPane.showMessageDialog(null, "Installed Populus, but missing some features because of security restrictions");
				}
			} else {
				install(false);
				JOptionPane.showMessageDialog(null, "Populus uninstalled");
			}
		} else {
			Logging.log("No arguments to main, will not run installer");
		}
	}

	/* Tries to install shortcuts and associations  on local machine
	 * @param install: true to install, false to remove.
	 * @return true if successful
	 */
	public static synchronized boolean install(boolean install) {
		IntegrationService is = null;
		try {
			is = (IntegrationService) ServiceManager.lookup("javax.jnlp.IntegrationService");
		} catch(UnavailableServiceException use){
			Logging.log("Unable to get IntegrationService to install:");
			Logging.log(use);
			return false;
		}
		boolean result = true;

		if (install) {
			// creates a desktop and system menu shortcut; returns true if the shortcuts 
			// were created successfully
			result = is.requestShortcut(true, true, null);

			// checks to see if there are shortcuts for the application
			result = is.hasMenuShortcut() && is.hasDesktopShortcut();
		} else {	
			//removes all shortcuts for application
			result = is.removeShortcuts();

			// checks to see if there are shortcuts for the application
			result = ! is.hasMenuShortcut() && ! is.hasDesktopShortcut();
		}

		//Mime association with .po files?
		/*
		String mime = "x-application/Populus";
		String [] exts = {"po"};
		if (install) {
			// associates the application with the specified mime-type and file extensions
			result = is.requestAssociation(mime, exts);
			// checks if the application is associated with the specified mime-type and file extensions
			result = is.hasAssociation(mime, exts);
		} else {
			// removes association between the application and the specified mime-type and file extensions
			is.removeAssociation(mime, exts);
			result = ! is.hasAssociation(mime, exts);
		}
		*/


		return result;
	}

}



