/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appd;

import edu.umn.ecology.populus.math.DiscreteProc;
import edu.umn.ecology.populus.math.Integrator;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

/**
 * Subclasses must do the following in initializing:
 * Set the following variables: <UL>
 * <LI> deriv </LI>
 * <LI> gens </LI>
 * <LI> plotType </LI>
 * <LI> modelType </LI>
 * <LI> initialConditions </LI>
 * <LI> mainCaption </LI>
 * <LI> xCaption </LI>
 * <LI> yCaption </LI>
 * </UL>
 * <p>
 * This is a generic class for 3D discrete-pp models to
 * extend.
 */

public class APPD3DProtoParamInfo implements BasicPlot {
    public static final int NvsT = 1;
    public static final int NvsN = 2;
    protected DiscreteProc discProc = null;
    protected String zCaption = null;
    protected int gens;
    protected int plotType = 0;
    protected String xCaption = null;
    protected String yCaption = null;
    protected double[] initialConditions = null;
    protected String mainCaption = null;

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bp;
        Integrator ig;
        double[][][] points = new double[1][3][];
        double[][] ylists;
        ig = new Integrator(discProc);
        if (gens < 0) {
            ig.record.ss = true;
            ig.record.interval = false;
        }
        ig.integrate(initialConditions, 0.0, gens);
        ylists = ig.getY();
        points[0][0] = ylists[2]; //predator 2
        points[0][1] = ylists[0]; //prey
        points[0][2] = ylists[1]; //predator 1
        bp = new BasicPlotInfo(points, mainCaption, xCaption, yCaption, zCaption);
        bp.setIsDiscrete(true);
        return bp;
    }

    public APPD3DProtoParamInfo() {

    }
}
