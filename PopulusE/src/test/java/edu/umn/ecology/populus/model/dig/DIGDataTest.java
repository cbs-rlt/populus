package edu.umn.ecology.populus.model.dig;

import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.poproutines.Population;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DIGDataTest {
    @Test
    void testContinuous() {
        double initialPop = 5.0;
        double nGens = 100.0;
        DIGData d = new DIGData(true, DIGPanel.kNVST, nGens, 1.1, initialPop, 1.1);
        //TODO: Why do we need to pass in numGraphs?
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(false, bpi.isDiscrete());
        double[][] points = bpi.getPoints(0);
        double[] xVals = points[0];
        double[] yVals = points[1];
        assertEquals(0.0, xVals[0], "X should start at 0.0");
        assertEquals(initialPop, yVals[0], "Y should start at n0");
        assertEquals(nGens, xVals[xVals.length-1], "X should end with number of generations.");
    }

    @Test
    void testDiscrete() {
        double initialPop = 12.0;
        DIGData d = new DIGData(false, DIGPanel.kNVST, 100.0, 1.1, initialPop, 1.1);
        DIGParamInfo dpi = new DIGParamInfo(new DIGData[] {d}, 1);
        BasicPlotInfo bpi = dpi.getBasicPlotInfo();
        assertEquals(true, bpi.isDiscrete());
        assertEquals(0.0, bpi.getPoints(0)[0][0], "X should start at 0.0");
        assertEquals(initialPop, bpi.getPoints(0)[1][0], "Y should start at n0");
    }

}