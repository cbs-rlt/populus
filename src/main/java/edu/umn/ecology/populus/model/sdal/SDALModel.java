/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sdal;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class SDALModel extends BasicPlotModel {
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.sdal.Res");

    @Override
    public Object getModelHelpText() {
        return "SDALHELP";
    }

    public SDALModel() {
        this.setModelInput(new SDALPanel());
    }

    public static String getModelName() {
        return res.getString("Autosomal_Selection");
    }

    @Override
    protected String getHelpId() {
        return "sdal.overview";
    }
}
