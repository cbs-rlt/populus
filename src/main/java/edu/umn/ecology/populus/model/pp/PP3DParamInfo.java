/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.pp;

import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;

import java.util.ResourceBundle;

public class PP3DParamInfo extends APPD3DProtoParamInfo {
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.pp.Res");

    public PP3DParamInfo(double X0, double Y0, double P0, boolean switching, double[] l, double[] aX, double[] g, double[] a, double m, double s, int gens, boolean vsTime) {
        discProc = new PPProc(switching, l, aX, g, a, m, s);
        this.gens = gens;

        //plotType = vsTime ? NvsT : NvsN;
        initialConditions = new double[]{
                X0, Y0, P0
        };
        mainCaption = res.getString("Discrete_Predator");
        xCaption = "Predator (<i>P</i>)";
        yCaption = "Prey 1 (<i>X</i>)";
        zCaption = "Prey 2 (<i>Y</i>)";
    }
}
