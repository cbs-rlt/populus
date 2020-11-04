/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.fileio;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ResourceBundle;

public class BasicFilenameFilter extends FileFilter {
    ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.fileio.Res");
    String description = res.getString("No_description");
    String[] s;

    public BasicFilenameFilter(String suffix) {
        this(new String[]{
                suffix
        });
    }

    //return true if should accept a given file

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        String path = f.getPath().toLowerCase();
        for (String value : s) {
            if (path.endsWith("." + value)) {
                return true;
            }
        }
        return false;
    }

    public void setDefaultDescription() {
        String temp = res.getString("Files_of_type_");
        for (String value : s) {
            temp += " *." + value;
        }
        setDescription(temp);
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public BasicFilenameFilter(String[] suffixes) {
        this.s = suffixes;
        setDefaultDescription();
    }
}
