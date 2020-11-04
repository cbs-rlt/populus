/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.math.DiscreteProc;

public class NBSSProc extends DiscreteProc
        implements edu.umn.ecology.populus.model.appd.Constants {
    private final double a;
    private final double l;
    private final double q;


    public NBSSProc(double a, double l, double q) {
        this.a = a;
        this.l = l;
        this.q = q;
        this.numVariables = 2;
    }

    @Override
    public void v(long t, double[] y) {
        double P, N, f;

        N = y[Ny];
        P = y[Py];

        f = -a * P;
        if (f < 500)
            f = Math.exp(f);
        else
            f = 0;
        y[Ny] = l * N * f;
        y[Py] = q * N * (1 - f);
    }
}