/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rc;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class RCModel extends BasicPlotModel {
    static ResourceBundle res = null;

    private static synchronized void initRes() {
        if (res == null)
            res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.rc.Res");
    }

    @Override
    public Object getModelHelpText() {
        return "RCHELP";
    }

    public RCModel() {
        this.setModelInput(new RCPanel());
    }

    @Override
    public String getThisModelInputName() {
        initRes();
        return res.getString("Resource_competition");
    }

    public static String getModelName() {
        initRes();
        return res.getString("Resource");
    }

    @Override
    protected String getHelpId() {
        return "rc.overview";
    }
}
