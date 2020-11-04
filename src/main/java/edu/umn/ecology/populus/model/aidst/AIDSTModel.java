/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aidst;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class AIDSTModel extends BasicPlotModel {
    // static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.aidsbasic.Res" );

    @Override
    public Object getModelHelpText() {
        return "AIDSTHELP";
    }

    public AIDSTModel() {
        this.setModelInput(new AIDSTPanel());
    }

    public static String getModelName() {
        return "AIDS: Therapy";
    }

    @Override
    protected String getHelpId() {
        return "aidst.overview";
    }
}
