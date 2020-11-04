/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.poproutines;

import edu.umn.ecology.populus.math.Routines;

import java.io.Serializable;

/**
 * Defaults:
 * selfing is true
 * this class is used in Genetic Drift and Population Structure
 */

public class Population implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -6282214892616261592L;
    public static final int a = 1;
    public static final int A = 0;
    private int popSize;
    private int[][] popArray;
    private boolean selfing = true;

    /**
     * Assumes each person has two alleles.
     * <p>
     * Here is Chris's code (lines 883-898 of SIMEXTRA.PAS): <CODE><BR>
     * Dom := trunc(DemeSize*FreqA*FreqA);<BR>
     * Rec := trunc(DemeSize*sqr(1-FreqA));<BR>
     * Het := DemeSize-Dom-Rec;<BR>
     * <p>
     * {Indicate whether an allele is present (1) or not (0) in a parent}<BR>
     * FillChar(Parent,ParentiSize,0);<BR>
     * For i := 1 to Demesize do<BR>
     * if i <= Dom then<BR>
     * begin<BR>
     * Parent[i,1] := 1;<BR>
     * Parent[i,2] := 1<BR>
     * end<BR>
     * else<BR>
     * if i<=(Dom+Het) then Parent[i,1] := 1;<BR>
     * <BR>
     * FreqA := (Dom + Het/2) / DemeSize;<BR>
     * </CODE>
     *
     * @param popSize Population size
     * @param p       Proportion of dominant allele, in range [0.0, 1.0]
     */
    public Population(int popSize, double p, boolean selfing) {
        int dom, het, rec;
        this.popArray = new int[popSize][2];
        this.popSize = popSize;
        dom = (int) (p * p * (popSize) + 0.01);
        rec = (int) ((1 - p) * (1 - p) * (popSize) + 0.01);
        het = popSize - dom - rec;
        init(dom, het);
        this.selfing = selfing;
    }

    /**
     * Assumes each person has two alleles.
     * This constructor is generally used for making children
     *
     * @param popSize int
     */
    private Population(int popSize) {
        this.popArray = new int[popSize][2];
        this.popSize = popSize;
    }

    /**
     * @return Population
     */
    public Population breed() {
        return breed(popSize, 0, 0);
    }

    /**
     * @return Population
     */
    public Population breed(double m, double freqAve) {
        return breed(popSize, m, freqAve);
    }

    /**
     * Creates one generation of children from the parent
     * popArray .
     *
     * @return Population
     */
    public Population breed(int newPop) {
        return breed(newPop, 0, 0);
    }

    /**
     * @param newPop
     * @param m       mutation rate
     * @param freqAve average frequency among all the demes
     * @return
     */
    public Population breed(int newPop, double m, double freqAve) {
        Population child = new Population(newPop);
        int parent1, parent2;
        for (int i = 0; i < newPop; i++) {
            if (m > 0.0 && Math.random() < m) {
                child.popArray[i][0] = Math.random() < freqAve ? A : a;
                child.popArray[i][1] = Math.random() < freqAve ? A : a;
            } else {
                do {
                    parent1 = Routines.getRandomInt(popSize);
                    parent2 = Routines.getRandomInt(popSize);
                } while (!getSelfing() && parent1 == parent2);
                child.popArray[i][0] = this.popArray[parent1][Routines.getRandomInt(0, 1)];
                child.popArray[i][1] = this.popArray[parent2][Routines.getRandomInt(0, 1)];
            }
        }
        return child;
    }

    /**
     * @return double frequency of p, or what proportion of the gene pool is dominant.
     */
    public double getPFreq() {
        int tally = 0;
        for (int i = 0; i < popSize; i++)
            for (int j = 0; j < 2; j++)
                tally += (popArray[i][j] == A) ? 1 : 0;
        return ((double) tally) / ((double) (popSize * 2));
    }

    /**
     * @return double
     */
    public double getHetFreq() {
        int tally = 0;
        for (int i = 0; i < popSize; i++)
            if ((popArray[i][0] + popArray[i][1]) == 1)
                tally++;
        return (double) tally / (double) popSize;
    }

    /**
     * @return boolean
     */
    public boolean getSelfing() {
        return selfing;
    }

    /**
     * @return int: -1 for not fixed; otherwise the fixed value.
     * Namely 0 for dominant A, and 1 for recessive a.
     */
    public int isFixed() {
        int last = popArray[0][0];
        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < 2; j++) {
                if (popArray[i][j] != last) {
                    return -1;
                }
            }
        }
        return last;
    }

    /**
     * @param dom how many dominant genes
     * @param het how many heterozygous
     */
    private void init(int dom, int het) {
        for (int i = 0; i < popSize; i++) {
            if (i + 1 <= dom) {
                popArray[i][0] = A;
                popArray[i][1] = A;
            } else if (i + 1 <= dom + het) {
                popArray[i][0] = A;
                popArray[i][1] = a;
            } else {
                popArray[i][0] = a;
                popArray[i][1] = a;
            }
        }
    }
}
