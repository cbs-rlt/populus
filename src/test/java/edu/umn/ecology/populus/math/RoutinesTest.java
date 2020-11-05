package edu.umn.ecology.populus.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Routines.java.
 * TODO: There are still many methods without tests
 */
class RoutinesTest {
    private static final double DELTA = 1.0e-7;

    @Test
    void quickSort() {
        int[] nums1to9 = {3, 1, 9, 6, 8, 2, 7, 5, 4};
        Routines.quickSort(nums1to9, 0, nums1to9.length - 1);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9}, nums1to9);
    }

    @Test
    void minMaxArray() {
        double[] arr = {5.0, -77.0, 10.0, 8.2, -23.0, 0.0};
        assertEquals(10.0, Routines.getMaxValue(arr));
        assertEquals(-77.0, Routines.getMinValue(arr));
        assertEquals(2, Routines.getMaxIndex(arr));
    }

    @Test
    void intMaxIndex() {
        int[] arr = {4, 5, -3, 2, 8, 0, -1};
        assertEquals(4, Routines.getMaxIndex(arr));
    }

    @Test
    void LogGamma() {
        assertEquals(Math.log(120.0), Routines.gammln(6.0), DELTA); //Gamma(n) = (n-1)!
        assertEquals(Math.log(15.0*Math.sqrt(Math.PI)/8.0), Routines.gammln(3.5), DELTA);
    }

    @Test
    void lnArray() {
        double[] arr = new double[] {5.0, 72.1};
        Routines.lnArray(arr); //Runs inplace
        assertArrayEquals(new double[] {Math.log(5.0), Math.log(72.1)}, arr, DELTA);
    }

    @Test
    void process1() {
        double[] arr = new double[] {1.0, 2.0, 4.0, 8.0, -3.5};
        double[][] out = new double[2][arr.length-1];
        double[][] expected = {{1.0, 2.0, 4.0, 8.0}, {1.0, 2.0, 4.0, -11.5}};
        Routines.process1(arr, out);
        assertArrayEquals(expected, out);
    }

    @Test
    void process3() {
        double[] arr = new double[] {1.0, 2.0, 4.0, 8.0, 3.5};
        double[][] out = new double[2][arr.length-1];
        double log2 = Math.log(2.0);
        double[][] expected = {{1.0, 2.0, 4.0, 8.0},
                {log2, log2, log2, Math.log(3.5/8.0)}};
        Routines.process3(arr, out);
        assertArrayEquals(expected, out);
    }
}