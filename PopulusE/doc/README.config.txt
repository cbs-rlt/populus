
****************************
* Configuration and Setup  *
****************************


* USER PREFERENCES *

A file used to keep configuration information for Populus is loaded at startup.  If not found, 
Populus will use the default configuration.

By default, the file is named userpref.po in the user's home directory.  To use a different file, 
specify the command-line argument "-p" followed by the file name to be used.

Populus will attempt to update the preferences file when setting the preferences from the menu or 
when closing the application.

The file itself is now stored in XML, but users should avoid editing the file directory.  Instead, 
use the Preferences icon (with the paint palette) in Populus.


* HELP *

When a user first launches help from Populus, Populus will request the user to set up the help.  (To
be specific, Populus prompts a user to set up help when the help file is missing.)  After the user
selects a language, Populus will copy the help version for that language to local disk.

The Open tab of the Help Configuration screen allows a user to customize how the help file is launched.
The Desktop method should work on all platforms.  The JNLP method works when run as a browser-based JNLP
application.  The custom command is a catch-all to call a custom system command.  For custom commands,
the %1, %2 and %3 are substituted as follows:
    %1 : URI of the help file  (e.g., with "file://" prefix)
    %2 : named destination for the location in the PDF to use.  This is ignored by many viewers.
    %3 : Absolute file name of the help file

The help configuration can be changed at a later time in the "More" tab of the Preferences.

Help configuration is stored in the User Preferences file, keys 111-114.

