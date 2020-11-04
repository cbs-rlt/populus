/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dsoqc;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class DSOQCModel extends BasicPlotModel {
    DSOQCPanel dp;

    @Override
    public Object getModelHelpText() {
        return "DSOQCHELP";
    }

    public DSOQCModel() {
        dp = new DSOQCPanel();
        this.setModelInput(dp);
    }

    public static String getModelName() {
        return "Directional Selection on a Quantitative Trait";
    }

    @Override
    protected String getHelpId() {
        return "DSOQC.overview";
    }

    @Override
    protected boolean isRepeatable() {
        return true;
    }

    @Override
    protected boolean isSwitchable() {
        return true;
    }

    @Override
    protected void switchOutput() {
        dp.switchOutputType();
        updateOutput();
    }
}
