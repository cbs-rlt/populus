/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.md;

import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7152285069030704938L;
    static final Object[][] contents = new String[][]{
            {"Infectious", "Macroparasitic Infections"},
            {"Infective_Stage_W_", "Free-living Infective Stages (<i>W</i>)"},
            {"Arrested_Parasite_", "Arrested Parasites (<i>A</i>)"},
            {"Host", "Host"},
            {"Parasite", "Parasite"},
            {"Host_N_", "Host Density (<i>H</>)"},
            {"Parasite_P_", "Parasite Population (<i>P</>)"},
            {"Host_N_W", "Host Density (<b><i>H</>)"},
            {"Parasite_P_W", "Parasite Population (<b><i>P</>)"},
            {"Time_b_i_t_", "Time ( <b><i>t</> )"},
            {"Host_Population", "Population (   "},
            {"totHostDens", "Host Density ( <b><i>N</> )"},
            {"Infectious1", "Macroparasitic Infections"},
            {"Rate_of_population", "Disease-induced mortality rate"},
            {"Anderson_May", "Anderson & May"},
            {"Parasite_Rate", "Parasite Rates"},
            {"Host_Rate", "Host Rates"},
            {"Model_Type", "Model Type"},
            {"Model_Parameters", "Model Parameters"},
            {"Initial_Conditions", "Initial Conditions"},
            {"Initial_Densities", "Initial Densities"},
            {"Graph_Type", "Graph Type"},
            {"Init_Population", "Initial Populations"},
            {"Parasite_Growth", "Parasite Growth"},
            {"Host_Growth", "Host Growth"},
            {"DAM", "Decoupled Anderson & May"},
            {"DAM_parasite", "Decoupled A & M"}};


    @Override
    public Object[][] getContents() {
        return contents;
    }
}
