/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.imd;

import edu.umn.ecology.populus.constants.ColorScheme;

import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3468201954476115932L;
    static final Object[][] contents = new String[][]{
            {
                    "Infectious", "Infectious Microparasitic Diseases"
            }, {
            "Immune_Host_R_", "Immune Host (<i>R</i>)"
    }, {
            "Susceptable_Host_S_", "Susceptable Host (<i>S</>)"
    }, {
            "Infected_Host_I_", "Infected Host (<i>I</> )"
    }, {
            "Susceptable_Host_S_R", "Susceptable Host ( <b><i>" + ColorScheme.getColorString(0) + "S</> )"
    }, {
            "Infected_Host_I_R", "Infected Host ( <b><i>" + ColorScheme.getColorString(0) + "I</> )"
    }, {
            "Time_b_i_t_", "Time ( <b><i>t</> )"
    }, {
            "Host_Population", "Host Population Density (   "
    }, {
            "totHostDens", "Total Host Density ( <b><i>" + ColorScheme.getColorString(0) + "N</> )"
    }, {
            "Infectious1", "Infectious Microparasitic Diseases"
    }
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
