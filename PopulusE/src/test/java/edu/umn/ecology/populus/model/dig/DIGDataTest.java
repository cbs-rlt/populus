/*******************************************************************************
 * Copyright (c) 2020 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.dig;

import edu.umn.ecology.populus.plot.BasicPlotInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DIGDataTest {
    @Test
    void testContinuous() {
        double n0 = 5.0;
        double nGens = 100.0;
        double r = 0.1;
        DIGData d = new DIGData(true, DIGPanel.kNVST, nGens, 0.7384, n0, r);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot);
        double[][] points = bpi.getPoints(0);
        assertEquals(2, points.length, "Expecting X and Y series");
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(0.0, xVals[0], "X should start at 0.0");
        assertEquals(n0, yVals[0], "Y should start at n0");
        assertEquals(nGens, xVals[xVals.length-1], "X should end with number of generations.");
        assertEquals(n0 * Math.exp(r*nGens), yVals[yVals.length-1], 0.001);
    }

    @Test
    void testDiscrete() {
        double n0 = 12.0;
        double nGens = 100.0;
        double lambda = 1.1;
        DIGData d = new DIGData(false, DIGPanel.kNVST, nGens, lambda, n0, 1.434);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(true, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot);
        assertEquals(1, bpi.getNumSeries());
        double[][] points = bpi.getPoints(0);
        assertEquals(2, points.length, "Expecting X and Y series");
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(0.0, xVals[0], "X should start at 0.0");
        assertEquals(n0, yVals[0], "Y should start at n0");
        assertEquals(nGens, xVals[xVals.length-1], "X should end with number of generations.");
        assertEquals(n0 * Math.exp(nGens*Math.log(lambda)), yVals[yVals.length-1], 0.001);
    }

    @Test
    void testLogGraph() {
        double n0 = 3.0;
        double nGens = 100.0;
        double r = 0.17;
        DIGData d = new DIGData(true, DIGPanel.kLNNVST, nGens, 3.9143, n0, r);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot); //The PLOT is not, but the values are
        assertEquals(1, bpi.getNumSeries());
        double[][] points = bpi.getPoints(0);
        assertEquals(2, points.length, "Expecting X and Y series");
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(0.0, xVals[0], "X should start at 0.0");
        assertEquals(Math.log(n0), yVals[0]);
        assertEquals(nGens, xVals[xVals.length-1], "X should end with number of generations.");
        assertEquals(Math.log(n0) + (r*nGens), yVals[yVals.length-1], 0.1);
    }

    @Test
    void testDNDTvsN() {
        double n0 = 7.0;
        double nGens = 100.0;
        double r = 0.17;
        DIGData d = new DIGData(true, DIGPanel.kDNDTVSN, nGens, 77.24, n0, r);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot);
        assertEquals(1, bpi.getNumSeries());
        double[][] points = bpi.getPoints(0);
        assertEquals(2, points.length, "Expecting X and Y series");
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(n0, xVals[0]);
        assertEquals(n0*r, yVals[0], 0.001);
        assertEquals(n0 * Math.exp(r*nGens), xVals[xVals.length-1], 0.001);
        assertEquals(r * xVals[xVals.length-1], yVals[yVals.length-1], 0.001);
    }

    @Test
    void testDNNDTvsN() {
        double n0 = 5.0;
        double nGens = 100.0;
        double r = -0.14;
        DIGData d = new DIGData(true, DIGPanel.kDNNDTVSN, nGens, -143.2, n0, r);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot);
        assertEquals(1, bpi.getNumSeries());
        double[][] points = bpi.getPoints(0);
        assertEquals(2, points.length, "Expecting X and Y series");
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(n0, xVals[0]);
        assertEquals(r, yVals[0]);
        assertEquals(n0 * Math.exp(r*nGens), xVals[xVals.length-1], 0.001);
        assertEquals(r, yVals[yVals.length-1], 0.001);
    }

    @Test
    void testMultiPlots() {
        double n01 = 4.0;
        double n03 = 3.0;
        double nGens = 60.0;
        double r1 = -0.04;
        double r3 = 0.17;
        DIGData d1 = new DIGData(true, DIGPanel.kNVST, nGens, -7.3, n01, r1);
        DIGData d3 = new DIGData(true, DIGPanel.kNVST, nGens, 4.4, n03, r3);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d1, null, d3, null}, 2);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        assertEquals(false, bpi.isLogPlot);
        assertEquals(2, bpi.getNumSeries());

        //Test first series
        {
            double[][] points1 = bpi.getPoints(0);
            assertEquals(2, points1.length, "Expecting X and Y series");
            double[] xVals1 = points1[0];
            double[] yVals1 = points1[1];
            assertEquals(0.0, xVals1[0]);
            assertEquals(n01, yVals1[0]);
            assertEquals(nGens, xVals1[xVals1.length - 1], 0.001);
            assertEquals(n01 * Math.exp(r1*nGens), yVals1[yVals1.length - 1], 0.001);
        }

        //Test second series
        {
            double[][] points3 = bpi.getPoints(1);
            assertEquals(2, points3.length, "Expecting X and Y series");
            double[] xVals3 = points3[0];
            double[] yVals3 = points3[1];
            assertEquals(0.0, xVals3[0]);
            assertEquals(n03, yVals3[0]);
            assertEquals(nGens, xVals3[xVals3.length - 1], 0.001);
            assertEquals(n03 * Math.exp(r3*nGens), yVals3[yVals3.length - 1], 0.001);
        }
    }
}