/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.install;

import java.lang.reflect.Method;

import edu.umn.ecology.populus.fileio.Logging;
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
		try {
			Method hasMenuShortMeth = Class.forName("javax.jnlp.IntegrationService").getMethod("hasMenuShortcut");
			Method hasDesktopShortMeth = Class.forName("javax.jnlp.IntegrationService").getMethod("hasDesktopShortcut");

			boolean result = true;
			// javax.jnlp.IntegrationService is = javax.jnlp.ServiceManager.lookup("javax.jnlp.IntegrationService");
			Method findISMeth = Class.forName("javax.jnlp.ServiceManager").getMethod("lookup", String.class);
			Object is = findISMeth.invoke(null, "javax.jnlp.IntegrationService");
			if (install) {
				// creates a desktop and system menu shortcut; returns true if the shortcuts
				// were created successfully
				Method reqShortMeth = Class.forName("javax.jnlp.IntegrationService")
						.getMethod("requestShortcut", boolean.class, boolean.class, String.class);
				result = (Boolean) reqShortMeth.invoke(is, true, true, null);

				// checks to see if there are shortcuts for the application
				//result = result && is.hasMenuShortcut() && is.hasDesktopShortcut();
				result = result && (Boolean) hasMenuShortMeth.invoke(is)
						&& (Boolean) hasDesktopShortMeth.invoke(is);
			} else {
				//removes all shortcuts for application
				Method remShortMeth = Class.forName("javax.jnlp.IntegrationService").getMethod("removeShortcuts");
				result = (Boolean) remShortMeth.invoke(is);

				// checks to see if there are shortcuts for the application
				result = result && ! (Boolean) hasMenuShortMeth.invoke(is)
						&& ! (Boolean) hasDesktopShortMeth.invoke(is);
			}
		} catch(Exception unavailableServiceException){
			Logging.log("Unable to get IntegrationService to install:");
			Logging.log(unavailableServiceException);
			return false;
		}
		return true;


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

	}

}



