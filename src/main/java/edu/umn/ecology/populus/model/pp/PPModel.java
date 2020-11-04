/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.pp;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class PPModel extends BasicPlotModel {
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.pp.Res");

    @Override
    public String getThisModelInputName() {
        return res.getString("Discrete_Predator");
    }

    @Override
    public Object getModelHelpText() {
        return "PPHELP";
    }

    public PPModel() {
        this.setModelInput(new PPPanel());
    }

    public static String getModelName() {
        return res.getString("Polyphagous_Predators");
    }

    @Override
    protected String getHelpId() {
        return "appdpp.overview";
    }
}
