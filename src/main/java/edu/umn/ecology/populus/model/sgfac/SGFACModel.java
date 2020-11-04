/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sgfac;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class SGFACModel extends BasicPlotModel {

    @Override
    public Object getModelHelpText() {
        return "SGFACHELP";
    }

    public SGFACModel() {
        this.setModelInput(new SGFACPanel());
    }

    public static String getModelName() {
        return "Selection on Clinal Stepping Stones";
    }

    @Override
    protected String getHelpId() {
        return "SGFAC.overview";
    }

    @Override
    protected boolean isRepeatable() {
        return true;
    }
}
