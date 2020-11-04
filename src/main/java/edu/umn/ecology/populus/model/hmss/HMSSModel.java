/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.hmss;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class HMSSModel extends BasicPlotModel {

    @Override
    public Object getModelHelpText() {
        return "HMSSHELP";
    }

    public HMSSModel() {
        this.setModelInput(new HMSSPanel());
    }

    public static String getModelName() {
        return "Handicap Sexual Selection";
    }

    @Override
    protected String getHelpId() {
        return "HMSS.overview";
    }
}
