/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.interdgs;

import edu.umn.ecology.populus.plot.BasicPlotModel;

public class INTERDGSModel extends BasicPlotModel {

    @Override
    public Object getModelHelpText() {
        return "INTERDGSHELP";
    }

    public INTERDGSModel() {
        this.setModelInput(new INTERDGSPanel());
    }

    public static String getModelName() {
        return "Interdemic Group Selection";
    }

    @Override
    protected String getHelpId() {
        return "INTERDGS.overview";
    }
}
