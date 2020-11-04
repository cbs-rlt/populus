/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdi;

import edu.umn.ecology.populus.plot.BasicPlotModel;

import java.util.ResourceBundle;

public class APPDIModel extends BasicPlotModel {
    static final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.appdi.Res");

    @Override
    public String getThisModelInputName() {
        return res.getString("Discrete_Predator");
    }

    @Override
    public Object getModelHelpText() {
        return "APPDIHELP";
    }

    public APPDIModel() {
        this.setModelInput(new APPDIPanel());
    }

    public static String getModelName() {
        return res.getString("Predator_Interference");
    }

    @Override
    protected String getHelpId() {
        return "appdpi.overview";
    }
}
