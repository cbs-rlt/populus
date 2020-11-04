/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sspg;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class SSPGModel extends BasicPlotModel {
    public SSPGModel() {
        this.setModelInput(new SSPGPanel());
    }

    public static String getModelName() {
        return "Stage-Structured Population Growth";
    }

    @Override
    public Object getModelHelpText() {
        return "SSPGHELP";
    }

    @Override
    protected String getHelpId() {
        return "SSPG.overview";
    }

}
