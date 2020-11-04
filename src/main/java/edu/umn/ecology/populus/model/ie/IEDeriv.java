/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.math.Derivative;

public class IEDeriv extends Derivative {
    final boolean[] plotted;
    private final EquationCalculator calc;

    @Override
    public void doDerivative(double time, double[] y, double[] dN) {
        double[] a = calc.calculateValues(y, time);
        for (int i = 0; i < dN.length; i++) {
            dN[i] = a[i];
        }
    }

    public int getPlotCount() {
        int count = 0;
        for (boolean b : plotted) {
            if (b) {
                count++;
            }
        }
        if (count > 3) {
            return 3;
        }
        return count;
    }

    public int[] getPlottedList() {
        int[] list = new int[getPlotCount()];
        int ind = 0;
        for (int i = 0; i < plotted.length; i++) {
            if (plotted[i]) {
                list[ind++] = i;
            }
        }
        return list;
    }

    public IEDeriv(EquationCalculator ss, boolean[] plot) {
        plotted = plot;
        calc = ss;
        this.numVariables = calc.numVariables(); //doesn't take unused equations into account
    }
}
