/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lpg;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class LPGModel extends BasicPlotModel {
    static ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.lpg.Res");

    @Override
    public Object getModelHelpText() {
        return "LPGHELP";
    }

    public LPGModel() {
        this.setModelInput(new LPGPanel());
    }

    public static String getModelName() {
        return res.getString("Density_Dependent");
    }

    @Override
    protected String getHelpId() {
        return "ddpg.overview";
    }
}
