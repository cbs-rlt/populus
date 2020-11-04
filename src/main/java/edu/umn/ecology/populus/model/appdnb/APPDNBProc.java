/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnb;

import edu.umn.ecology.populus.math.DiscreteProc;

public class APPDNBProc extends DiscreteProc implements edu.umn.ecology.populus.model.appd.Constants {
    private final double aDI;
    private final double l;
    private final double K;
    private final double r;
    private final double aDD;
    private final boolean indep;

    @Override
    public void v(long t, double[] y) {
        double P, N, q, q1, q2;
        N = y[Ny];
        P = y[Py];
        if (indep) {
            q = -aDI * P;
            if (q < 500) {
                q = Math.exp(q);
            } else {
                q = 0;
            }
            y[Ny] = l * N * q;
            y[Py] = N * (1 - q);
        } else {
            q1 = -aDD * P;
            if (K != 0) {
                q2 = r * (1 - N / K) + q1;
            } else {
                q2 = 0;
            }
            if (q2 < 87.0) /*87.0 < ln (MAX_FLOAT)*/ {
                y[Ny] = N * Math.exp(q2);
            } else {
                y[Ny] = 1e30;
            }
            if (q1 < 87.0) {
                y[Py] = N * (1 - Math.exp(q1));
            } else {
                y[Py] = 0;
            }
        }
    }

    public APPDNBProc(boolean indep, double aDI, double l, double K, double r, double aDD) {
        this.indep = indep;
        this.aDI = aDI;
        this.aDD = aDD;
        this.l = l;
        this.K = K;
        this.r = r;
        this.numVariables = 2;
    }
}
