/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.fdsdm;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class FDSDMModel extends BasicPlotModel {

    @Override
    public Object getModelHelpText() {
        return "FDSDMHELP";
    }

    public FDSDMModel() {
        this.setModelInput(new FDSDMPanel());
    }

    public static String getModelName() {
        return "Frequency-Dependent Selection (Diploid Model)";
    }

    @Override
    protected String getHelpId() {
        return "FDSDM.overview";
    }
}
