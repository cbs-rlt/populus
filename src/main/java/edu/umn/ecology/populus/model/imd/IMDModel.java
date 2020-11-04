/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.imd;

import edu.umn.ecology.populus.core.PopResourceBundle;
import edu.umn.ecology.populus.plot.BasicPlotModel;

public class IMDModel extends BasicPlotModel {
    static PopResourceBundle res = PopResourceBundle.getBundle("edu.umn.ecology.populus.model.imd.Res");

    @Override
    public Object getModelHelpText() {
        return "IMDHELP";
    }

    public IMDModel() {
        this.setModelInput(new IMDPanel());
    }

    public static String getModelName() {
        return res.getString("Infectious");
    }

    @Override
    protected String getHelpId() {
        return "imd.overview";
    }
}
