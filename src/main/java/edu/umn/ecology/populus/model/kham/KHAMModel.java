/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.kham;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class KHAMModel extends BasicPlotModel {

    @Override
    public Object getModelHelpText() {
        return "KHAMHELP";
    }

    public KHAMModel() {
        this.setModelInput(new KHAMPanel());
    }

    public static String getModelName() {
        return "Arbitrary Sexual Selection";
    }

    @Override
    protected String getHelpId() {
        return "KHAM.overview";
    }
}
