/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.fileio;
import java.io.*;

/**
  * This type was created in VisualAge.
  * TODO: Is this used? Looks worthless...
  */

public class OutputFilter implements java.io.FilenameFilter {

   /**
     * accept method comment.
     */

   public boolean accept( File dir, String name ) {
      return true;
   }

   /**
     * OutputFilter constructor comment.
     */

   public OutputFilter() {
      super();
   }
}
