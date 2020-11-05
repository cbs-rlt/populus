package edu.umn.ecology.populus.model.ie;

import org.junit.jupiter.api.Test;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

class EquationCalculatorTest {

    @Test
    void testEquationConstants() throws IEException {
        String[] equations = new String[] {"5*1.2 + k", "E^2+7.0"};
        Hashtable<String, Double> consts = new Hashtable<>();
        consts.put("k", -6.0);
        double[] initState = new double[] {0.0, 0.0};

        EquationCalculator ec = new EquationCalculator(equations, consts, false);
        double[] sols = ec.calculateValues(initState, 0.0);
        assertEquals(2, sols.length);
        assertEquals(0.0, sols[0], 0.001);
        assertEquals(Math.E*Math.E + 7.0, sols[1], 0.001);
    }
}