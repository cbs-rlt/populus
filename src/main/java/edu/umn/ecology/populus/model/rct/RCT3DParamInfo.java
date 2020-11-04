/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rct;

import edu.umn.ecology.populus.math.DiscreteProc;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.model.appd.APPD3DProtoParamInfo;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

import java.util.ResourceBundle;


/**
 * Possibly not needed....
 */
public class RCT3DParamInfo extends APPD3DProtoParamInfo {
    public static final int NvsT = 1;
    public static final int NvsN = 2;
    protected double time;
    protected double n1, n2, n3, R1, R2, R3;
    protected int speciesNum = 3;
    protected int resourceNum;
    protected DiscreteProc discProc = null;
    protected String xCaption = null;
    protected String yCaption = null;
    protected int numVars;
    protected int plotType = 0;
    protected double[] initialConditions = null;
    protected String mainCaption = null;
    protected String zCaption = null;
    ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.rct.Res");
    Integrator ig = null;

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp;
        initialConditions = new double[3];
        initialConditions[0] = n1;
        initialConditions[1] = n2;
        initialConditions[2] = n3;
        double[][][] points = new double[1][3][];
        double[][] ylists;
        if (gens < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        ig.integrate(initialConditions, 0.0, time);
        ylists = ig.getY();
        points[0][0] = ylists[0];
        points[0][1] = ylists[1];
        points[0][2] = ylists[2];
        bp = new BasicPlotInfo(points, mainCaption, xCaption, yCaption, zCaption);
        return bp;
    }

}