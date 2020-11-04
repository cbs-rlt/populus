/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ps;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class PSModel extends BasicPlotModel {
    final PSPanel psp;

    @Override
    public Object getModelHelpText() {
        return "PSHELP";
    }

    public PSModel() {
        psp = new PSPanel();
        setModelInput(psp);
    }

    public static String getModelName() {
        return "Population Structure";
    }

    @Override
    protected String getHelpId() {
        return "ps.overview";
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
        psp.switchOutputType();
        updateOutput();
    }
}
