package edu.umn.ecology.populus.poproutines;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    /**
     * Test that selfing is correctly set.
     */
    @Test
    void testSelfing() {
        Population ps = new Population(5, 0.5, true);
        Population pn = new Population(4, 0.3, false);
        assertEquals(true, ps.getSelfing());
        assertEquals(false, pn.getSelfing());
    }

    /**
     * The PFreq should be roughly equal to the p value of the population.
     * Hetero frequency should be 2*p*(1-p)
     * isFixed should return 1 for recessive, 0 for dominant, and -1 for not fixed.
     * Allow for precision errors of truncating to a finite population.
     */
    @Test
    void testFrequencies() {
        Population p1 = new Population(100, 1.0, true);
        assertEquals(p1.getPFreq(), 1.0, 0.02);
        assertEquals(p1.getHetFreq(), 0.0, 0.02);
        assertEquals(p1.isFixed(), 0);

        Population p50 = new Population(100, 0.50, true);
        assertEquals(p50.getPFreq(), 0.5, 0.02);
        assertEquals(p50.getHetFreq(), 0.5, 0.02);
        assertEquals(p50.isFixed(), -1);

        Population p37 = new Population(100, 0.37, true);
        assertEquals(p37.getPFreq(), 0.37, 0.02);
        assertEquals(p37.getHetFreq(), 0.4662, 0.02);
        assertEquals(p37.isFixed(), -1);

        Population p0 = new Population(100, 0.0, true);
        assertEquals(p0.getPFreq(), 0.0, 0.02);
        assertEquals(p0.getHetFreq(), 0.0, 0.02);
        assertEquals(p0.isFixed(), 1);
    }

    /**
     * The PFreq should be roughly equal to the p value of the population.
     * Allow for precision errors of truncating to a finite population.
     */
    @Test
    void testHetFreq() {
        Population p50 = new Population(100, 0.37, true);
        assertEquals(p50.getHetFreq(), 0.5, 0.02);
        Population p37 = new Population(100, 0.15, true);
        assertEquals(p37.getPFreq(), 0.37, 0.02);
    }

    /**
     * The PFreq should be roughly equal to the p value of the population.
     * Allow for precision errors of truncating to a finite population.
     */
    @Test
    void testPFreq() {
        Population p50 = new Population(100, 0.50, true);
        assertEquals(p50.getPFreq(), 0.5, 0.02);
        Population p37 = new Population(100, 0.37, true);
        assertEquals(p37.getPFreq(), 0.37, 0.02);
    }
}