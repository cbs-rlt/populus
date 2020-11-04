/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.prm;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class PRMModel extends BasicPlotModel {
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.prm.Res");

    @Override
    public Object getModelHelpText() {
        return "PRMHELP";
    }

    public PRMModel() {
        this.setModelInput(new PRMPanel());
    }

    public static String getModelName() {
        return res.getString("PRM");
    }

    @Override
    protected String getHelpId() {
        return "prm.overview";
    }
}
