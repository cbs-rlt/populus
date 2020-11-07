package edu.umn.ecology.populus.model.hmss;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

/**
 * The references for this model are:<br>
 * (1) Malte Andersson, EVOLUTION (1986)40(4)804-16 (This has the model)<br>
 * (2) Graham Bell,     EVOLUTION (1978)32(4)872-85 (This is a simpler version of (1))<br>
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author Amos Anderson
 * @version 5.2
 */
public class HMSSParamInfo implements BasicPlot {
    public static final int pqrvsT = 0;
    public static final int DvsT = 1;
    public static final int VvsT = 2;
    public static final int pqDAB = 3;
    public static final int rqDBC = 4;

    /**
     * these are for accessing the allelic frequency arrays <code>f</code>
     */
    private static final short p = 0;
    private static final short q = 1;
    private static final short r = 2;

    /**
     * these are the alleles, in binary form<br>
     * e.g.<br>
     * ABC = A|B|C<br>
     * AbC = A|C
     */
    private static final byte A = 1;
    private static final byte B = 2;
    private static final byte C = 4;

    /**
     * for referencing the linkage disequilibrium
     */
    private static final int dAB = 0;
    private static final int dBC = 1;
    private static final int dAC = 2;

    private static final byte maleBit = 3;

    /**
     * these are for accessing arrays
     */
    private static final int female = 0;
    private static final int male = 1;

    final double a, b, e, m;
    final boolean isBell, isMono;
    final double[][] f = new double[2][3];
    double[][] genotypicFrequencies = new double[2][(A | B | C) + 1];
    final int plotType, gens;

    /**
     * abc,Abc,aBc,ABc,abC,AbC,aBC,ABC
     */
    final int numGenotypes = 8;


    /**
     * this returns the linkage disequilibrium between<br>
     * 0: A and B<br>
     * 1: B and C<br>
     * 2: A and C<br>
     *
     * @param sex
     * @param i
     * @return
     */
    private double getLinkageDisequilibrium(int sex, int i) {
        return switch (i) {
            case dAB -> (genotypicFrequencies[sex][A | B | C] + genotypicFrequencies[sex][A | C]) *
                    (genotypicFrequencies[sex][0] + genotypicFrequencies[sex][B]) -
                    (genotypicFrequencies[sex][A | B] + genotypicFrequencies[sex][A]) *
                            (genotypicFrequencies[sex][B | C] + genotypicFrequencies[sex][C]);
            case dBC -> (genotypicFrequencies[sex][A | B | C] + genotypicFrequencies[sex][B | C]) *
                    (genotypicFrequencies[sex][0] + genotypicFrequencies[sex][A]) -
                    (genotypicFrequencies[sex][A | B] + genotypicFrequencies[sex][B]) *
                            (genotypicFrequencies[sex][A | C] + genotypicFrequencies[sex][C]);
            case dAC -> (genotypicFrequencies[sex][A | B | C] + genotypicFrequencies[sex][A | B]) *
                    (genotypicFrequencies[sex][0] + genotypicFrequencies[sex][C]) -
                    (genotypicFrequencies[sex][A | C] + genotypicFrequencies[sex][A]) *
                            (genotypicFrequencies[sex][B | C] + genotypicFrequencies[sex][B]);
            default -> -1;
        };
    }

    /**
     * this method determines if <code>first</code> and <code>second</code> belong in the
     * same "class" as defined in the references. these classes are defined differently
     * by gender.
     *
     * @param first
     * @param second
     * @param matchFemale if we are looking to match a female or a male
     */
    boolean inSameClass(int first, int second, boolean matchFemale) {
        if (isBell) {
            if (matchFemale) {
                if ((first & C) == (second & C)) return true;
                return false;
            } else {
                if ((first & A) == (second & A)) return true;
                return false;
            }
        } else {
            if (matchFemale) {
                if ((first & C) == (second & C)) return true;
                return false;
            } else {
                if ((first & (A | B)) == (second & (A | B))) return true;
                if (((first & A) == 0) && ((second & A) == 0)) return true;
                return false;
            }
        }
    }

    /**
     * this method returns the mating preference factor. see reference (2) for
     * a description.
     *
     * @param r
     * @param p
     * @param mate
     * @return
     */
    double bellMatingFreq(double r, double p, int mate) {
        if (isMono) {
            if (r > p)
                switch (mate & (C | A << maleBit)) {
                    case /*CxA*/ C | A << maleBit:
                        return p;
                    case /*Cxa*/ C:
                        return (r - p);
                    case /*cxA*/ A << maleBit:
                        return 0.0;
                    case /*cxa*/ 0:
                        return (1.0 - r);
                }
            else
                switch (mate & (C | A << maleBit)) {
                    case /*CxA*/ C | A << maleBit:
                        return r;
                    case /*Cxa*/ C:
                        return 0.0;
                    case /*cxA*/ A << maleBit:
                        return (p - r);
                    case /*cxa*/ 0:
                        return (1.0 - p);
                }
        } else {
            switch (mate & (C | A << maleBit)) {
                case /*CxA*/ C | A << maleBit:
                    return r;
                case /*Cxa*/ C:
                    return 0.0;
                case /*cxA*/ A << maleBit:
                    return p * (1.0 - r);
                case /*cxa*/ 0:
                    return (1.0 - p) * (1.0 - r);
            }
        }
        return -1;
    }

    /**
     * this method returns the mating preference factor. see reference (1) for
     * a description.
     *
     * @param r
     * @param pB
     * @param pb
     * @param mate
     * @return
     */
    double anderssonMatingFreq(double r, double pB, double pb, int mate) {
        if (r > pB)
            switch (mate & (C | (A | B) << maleBit)) {//001 110
                case /*CxAB*/ C | (A | B) << maleBit:
                    return pB;
                case /*CxAb*/ C | (A) << maleBit:
                    return (r - pB) * pb / (1.0 - pB);
                case /*Cxab*/ C:
                case /*CxaB*/ C | (B) << maleBit:
                    return (r - pB) * (1.0 - pB - pb) / (1.0 - pB);
                case /*cxAB*/ (A | B) << maleBit:
                    return 0.0;
                case /*cxAb*/ A << maleBit:
                    return (1.0 - r) * pb / (1.0 - pB);
                case /*cxab*/ 0:
                case /*cxaB*/ B << maleBit:
                    return (1.0 - r) * (1.0 - pB - pb) / (1.0 - pB);
            }
        else
            switch (mate & (C | (A | B) << maleBit)) {//001 110
                case /*CxAB*/ C | (A | B) << maleBit:
                    return r;
                case /*CxAb*/ C | (A) << maleBit:
                    return 0.0;
                case /*Cxab*/ C:
                case /*CxaB*/ C | (B) << maleBit:
                    return 0.0;
                case /*cxAB*/ (A | B) << maleBit:
                    return (pB - r);
                case /*cxAb*/ A << maleBit:
                    return pb;
                case /*cxab*/ 0:
                case /*cxaB*/ B << maleBit:
                    return (1.0 - pB - pb);
            }
        return -1;
    }

    /**
     * this method determines the viability of the creature is based on its
     * genotype <code>genotype</code>. <code>sex</code> corresponds to the parameters
     * <code>male</code> and <code>female</code> defined in the class.
     *
     * @param sex
     * @param genotype
     * @return
     */
    private double getViability(int sex, byte genotype) {
        if (sex == female) {
            switch (genotype & (A | B)) {
                case A | B:
                    return a + b + e;
                case A:
                    return a + b;
                case B:
                    return a + b + e;
                case 0:
                    return a + b;
            }
        } else {
            switch (genotype & (A | B)) {
                case A | B:
                    return a + e;
                case A:
                    if (isBell) return a;
                    else return a + b;
                case B:
                    return a + b + e;
                case 0:
                    return a + b;
            }
        }
        return -1;
    }

    /**
     * this method determines the number of different genotypes possible in progeny
     * based on the genotypes of the parents, <code>first</code> and <code>second</code>
     *
     * @param first
     * @param second
     * @return
     */
    private double getNumProgeny(int first, int second) {
        int dif = first ^ second;
        int numMatches = 0;
        if ((dif & A) != 0) numMatches++;
        if ((dif & B) != 0) numMatches++;
        if ((dif & C) != 0) numMatches++;
        return Math.pow(2, numMatches);
    }

    /**
     * this method determines if a progeny with genotype <code>child</code> is possible
     * from parents with genotypes <code>first</code> and <code>second</code>
     *
     * @param first
     * @param second
     * @param child
     * @return
     */
    private boolean isProgenyPossible(int first, int second, int child) {
        int mate = first ^ second;
        if ((child | mate) == (first | mate)) return true;
        return false;
    }

    private void addData(double[][][] points, int gen) {
        switch (plotType) {
            case pqrvsT:
                for (int j = 0; j < 3; j++) {
                    points[j][0][gen] = gen;
                    points[j][1][gen] = (f[female][j] + f[male][j]) / 2.0;
                }
                break;
            case DvsT:
                for (int j = 0; j < 3; j++) {
                    points[j][0][gen] = gen;
                    points[j][1][gen] = (getLinkageDisequilibrium(female, j) + getLinkageDisequilibrium(male, j)) / 2.0;
                }
                break;
            case pqDAB:
                points[0][0][gen] = (f[female][p] + f[male][p]) / 2.0;
                points[0][1][gen] = (f[female][q] + f[male][q]) / 2.0;
                points[0][2][gen] = (getLinkageDisequilibrium(female, dAB) + getLinkageDisequilibrium(male, dAB)) / 2.0;
                break;
            case rqDBC:
                points[0][0][gen] = (f[female][r] + f[male][r]) / 2.0;
                points[0][1][gen] = (f[female][q] + f[male][q]) / 2.0;
                points[0][2][gen] = (getLinkageDisequilibrium(female, dBC) + getLinkageDisequilibrium(male, dBC)) / 2.0;
                break;
        }
    }

    @Override
    public BasicPlotInfo getBasicPlotInfo() {
        BasicPlotInfo bpi = null;
        double sum, pB = 0, pb = 0, x, y;
        double[][] freqs = new double[numGenotypes][numGenotypes];
        double[][][] points = switch (plotType) {
            case pqrvsT, DvsT -> new double[3][2][gens + 1];
            case VvsT -> new double[2][2][gens + 1];
            case pqDAB, rqDBC -> new double[1][3][gens + 1];
            default -> null;
        };

        for (int i = 0; i <= gens; i++) {
            /*male and female genotypic frequencies are the same at the beginning of each generation*/
            genotypicFrequencies[male] = genotypicFrequencies[female].clone();

            for (int j = female; j <= male; j++) {
                /* calculate genotypic frequencies weighted by the viability*/
                sum = 0.0;
                for (byte k = 0; k < numGenotypes; k++) {
                    genotypicFrequencies[j][k] *= getViability(j, k);
                    sum += genotypicFrequencies[j][k];
                }

                /*normalize the genotypic frequencies*/
                for (int k = 0; k < numGenotypes; k++)
                    genotypicFrequencies[j][k] /= sum;

                if (plotType == VvsT) {
                    points[j][0][i] = i;
                    points[j][1][i] = sum;
                }

                /*calculate the allelic frequencies*/
                f[j][p] = genotypicFrequencies[j][A | B | C] + genotypicFrequencies[j][A | B] + genotypicFrequencies[j][A | C] + genotypicFrequencies[j][A];
                f[j][q] = genotypicFrequencies[j][A | B | C] + genotypicFrequencies[j][A | B] + genotypicFrequencies[j][B | C] + genotypicFrequencies[j][B];
                f[j][r] = genotypicFrequencies[j][A | B | C] + genotypicFrequencies[j][A | C] + genotypicFrequencies[j][B | C] + genotypicFrequencies[j][C];
            }

            addData(points, i);

			/*andersson models have a mutation modification to make on the female's r allelic frequency.
         also, pB and pb need to be separated for the preference determination.*/
            if (!isBell) {
                pB = genotypicFrequencies[male][A | B | C] + genotypicFrequencies[male][A | B];
                pb = genotypicFrequencies[male][A | C] + genotypicFrequencies[male][A];

                if (pB > 0)
                    f[female][r] = Math.max(0.0, f[female][r] * (1.0 - m / pB));
                else f[female][r] = 0.0;
            }

			/*this section of code determines the relative mating frequency.
         an 8x8 matrix is filled with terms representing the mating probability out of the entire population, each row
         and column representing a genotype; columns for males, rows for females.
         these terms are formed by multiplying the mating preference by a female genotypic frequency and a male genotypic frequency
         multiplied together. this represents the encounter probability of those two genotypes.
         finally, each term then needs to be scaled by the term:
         sum(xi)*sum(yi) where xi and yi are the frequencies of the genotypes sharing the mating preference classes of the female
         and male classes respectively represented by the frequencies in the numerator.*/
            for (byte j = 0; j < numGenotypes; j++)
                for (byte k = 0; k < numGenotypes; k++) {
                    if (isBell) freqs[j][k] = bellMatingFreq(f[female][r], f[male][p], j | (k << maleBit));
                    else freqs[j][k] = anderssonMatingFreq(f[female][r], pB, pb, j | (k << maleBit));
                    x = 0;
                    y = 0;
                    for (int l = 0; l < numGenotypes; l++) {
                        if (inSameClass(j, l, true))
                            x += genotypicFrequencies[female][l];
                        if (inSameClass(k, l, false))
                            y += genotypicFrequencies[male][l];
                    }
                    freqs[j][k] *= genotypicFrequencies[female][j] * genotypicFrequencies[male][k] / (x * y);
                }

			/*this section of code then determines the genotypic frequencies of the progeny. each mating is assumed to
         have produced the same number of offspring, and that the genotypic frequency of the male and female progeny
         is the same. the genotypic frequency is determined by tallying the progeny that result from each mating weighted
         by the mating frequency. for example, an abC-ABC mating will have 4 different types of progeny, so each of those
         resulting genotypic frequencies in the new generation will get 1/4 of the offspring from that mating.*/
            genotypicFrequencies = new double[2][numGenotypes];
            for (int j = 0; j < freqs.length; j++)
                for (int k = 0; k < freqs[j].length; k++)
                    for (int l = 0; l < numGenotypes; l++)
                        if (isProgenyPossible(j, k, l))
                            genotypicFrequencies[female][l] += freqs[j][k] / getNumProgeny(j, k);
        }

        String mcap = isBell ? "Maynard Smith/Bell Handicap Model" : "Andersson Handicap Model";
        String xcap = "Generations ( <i><b>t</> )";
        String ycap = "";
        String zcap = "";
        switch (plotType) {
            case pqrvsT -> ycap = "Allelic Frequency (  " + ColorScheme.getColorString(0) + "<i><b>p</>, " + ColorScheme.getColorString(1) + "<i><b>q</>, " + ColorScheme.getColorString(2) + "<i><b>r</> )";
            case DvsT -> ycap = "Disequilibria (  " + ColorScheme.getColorString(0) + "<b>D<sub>AB</>, " + ColorScheme.getColorString(1) + " <b>D<sub>BC</>, " +
                    ColorScheme.getColorString(2) + " <b>D<sub>AC</> )";
            case VvsT -> ycap = ColorScheme.getColorString(0) + "Male</> and " + ColorScheme.getColorString(1) + "Female</> Viability ";
            case pqDAB -> {
                xcap = "<i>p</>";
                ycap = "<i>q</>";
                zcap = "<i>D</><sub>AB</>";
            }
            case rqDBC -> {
                xcap = "<i>r</>";
                ycap = "<i>q</>";
                zcap = "<i>D</><sub>BC</>";
            }
        }

        bpi = new BasicPlotInfo(points, mcap, xcap, ycap);
        if (plotType == pqrvsT)
            bpi.isLogPlot = true;
        if (plotType == pqDAB || plotType == rqDBC) {
            bpi.setGraphType(BasicPlotInfo.k3D);
            bpi.setZCaption(zcap);
        }
        return bpi;
    }

    public HMSSParamInfo(double a, double b, double e, double p0, double q0, double r0, double m, int gens,
                         int plotType, boolean isBell, boolean isMono) {
        this.a = a;
        this.b = b;
        this.e = e;
        this.m = m;
        this.isBell = isBell;
        this.isMono = isMono;
        this.gens = gens;
        this.plotType = plotType;
        f[0][p] = p0;
        f[0][q] = q0;
        f[0][r] = r0;
        f[1] = f[0].clone();
        genotypicFrequencies[0] = new double[]{
                /*abc*/   (1.0 - p0) * (1.0 - q0) * (1.0 - r0),
                /*Abc*/   p0 * (1.0 - q0) * (1.0 - r0),
                /*aBc*/   (1.0 - p0) * q0 * (1.0 - r0),
                /*ABc*/   p0 * q0 * (1.0 - r0),
                /*abC*/   (1.0 - p0) * (1.0 - q0) * r0,
                /*AbC*/   p0 * (1.0 - q0) * r0,
                /*aBC*/   (1.0 - p0) * q0 * r0,
                /*ABC*/   p0 * q0 * r0
        };
    }
}