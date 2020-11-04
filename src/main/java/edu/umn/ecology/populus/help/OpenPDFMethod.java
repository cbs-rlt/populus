/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.help;

import java.io.Serializable;

public class OpenPDFMethod implements Serializable {
    private static final long serialVersionUID = -4040941158718934928L;

    public static final int DESKTOP = 0;
    public static final int JNLP = 1;
    public static final int CUSTOM_EXEC = 2;

    public static final OpenPDFMethod DEFAULT_METHOD = new OpenPDFMethod(DESKTOP, "\"C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe\" \"%1#%2\"");

    private int openMethod;
    private String execStr;

    public OpenPDFMethod() {
        this(DESKTOP, "");
    }

    public OpenPDFMethod(int method, String cmd) {
        this.openMethod = method;
        this.execStr = cmd;
    }

    public int getOpenMethod() {
        return openMethod;
    }

    public void setOpenMethod(int openMethod) {
        this.openMethod = openMethod;
    }

    public String getExecStr() {
        return execStr;
    }

    public void setExecStr(String execStr) {
        this.execStr = execStr;
    }
}
