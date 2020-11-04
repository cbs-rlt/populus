/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hhap;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class HHAPModel extends BasicPlotModel {
    @Override
    public Object getModelHelpText() {
        return "HHAPHELP";
    }

    public HHAPModel() {
        this.setModelInput(new HHAPPanel());
    }

    public static String getModelName() {
        return "Haploid Hosts and Parasites";
    }

    @Override
    protected String getHelpId() {
        return "HHAP.overview";
    }
}
