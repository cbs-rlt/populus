/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.woozle;

import edu.umn.ecology.populus.math.Routines;
import edu.umn.ecology.populus.plot.ParamInfo;

import java.util.Random;
import java.util.ResourceBundle;

/**
 * First generation is actually the first kids. Generation 0 succeeding to make the phrase is technically possible (27^28)
 * What I think would be nice is for result window to be completely GUI, asking this class for what the next state is.
 * <P> <B>Special Programmer's note:</B> if brood size is not evenly divisible by number of parents, the results will be slightly
 * skewed to the slow side. This is because the groups of children to pick parents from will be uneven. The parents that come from
 * the biggest groups will not be the starting parent in the crossover for the kids. 03.09.98 LR
 */

public class WoozleParamInfo extends ParamInfo {
    /**
     *
     */
    private static final long serialVersionUID = -476360965163357223L;
    final ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.woozle.Res");
    private final int phraseLength;
    private char[] targetPhrase = {
            'M', 'E', 'T', 'H', 'I', 'N', 'K', 'S', ' ', 'I', 'T',
            ' ', 'I', 'S', ' ', 'L', 'I', 'K', 'E', ' ', 'A', ' ',
            'W', 'O', 'O', 'Z', 'L', 'E'
    };
    private double averageGeneration;
    private double mutationRate;
    private char[][] currentPhrases; //or parents
    private boolean showCrossover;

    /**
     * How many times it has finished
     * Value of x indicates that it has finished the xth run and is not finished with the (x+1)th run
     */
    private int iterationCount = 0;

    //For the children & mutations

    /**
     * which generation is happening
     */
    private int generation = 0;

    //For multiple parents:

    /**
     * number of parents
     */
    private int numParents = 1;

    /**
     * For each member of progress,
     * index refers to generation,
     * value refers to matches.
     */
    private final int[] progress;
    private boolean showEvolve;
    private int bestMatch = 0; //the best number of agreements so far
    private boolean crossoverEnabled = false;
    private boolean completed = false; //have we found the correct phrase yet?
    private int totalGenerations = 0;

    /**
     * Only for 1 parent
     */
    private char[] currentPhrase;
    private int broodSize;
    private final char[][] children;
    private final Random myRand = new Random();
    private final int[] childMatches; //An array of the children's "successes", or number of correct matches
    private double crossoverRate;
    private static final char[] possibleChars = {
            ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * For two parents
     */

    public WoozleParamInfo(int broodSize, double mutationRate, double crossoverRate, String targetPhrase,
                           boolean showCross, boolean showEvolve, long seed) {
        this(broodSize, mutationRate, crossoverRate, targetPhrase, showCross, showEvolve, 2, seed);
    }

    /**
     * For multiple (>2) parents -- this is never used!
     */

    public WoozleParamInfo(int broodSize, double mutationRate, double crossoverRate, String targetPhrase, boolean showCross,
                           boolean showEvolve, int parents, long seed) {
        super();
        myRand.setSeed(seed);
        this.broodSize = broodSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.targetPhrase = targetPhrase.toCharArray();
        this.numParents = parents;
        this.crossoverEnabled = true;
        setShowEvolve(showEvolve);
        phraseLength = this.targetPhrase.length;
        children = new char[broodSize][phraseLength];
        childMatches = new int[broodSize]; //Is a + 1 necessary?
        progress = new int[phraseLength];
        currentPhrases = new char[numParents][phraseLength];
        startOver();
    }

    /**
     * For single parent
     */

    public WoozleParamInfo(int broodSize, double mutationRate, String targetPhrase, boolean showEvolving, long seed) {
        super();
        myRand.setSeed(seed);
        this.broodSize = broodSize;
        this.mutationRate = mutationRate;
        this.targetPhrase = targetPhrase.toCharArray();
        setShowEvolve(showEvolving);
        phraseLength = this.targetPhrase.length;
        children = new char[broodSize][phraseLength];
        childMatches = new int[broodSize]; //Is a + 1 necessary?
        progress = new int[phraseLength];
        currentPhrase = new char[phraseLength];
        startOver();
    }

    public void startOver() {
        completed = false;
        generation = 0;
        bestMatch = 0;
        if (getCrossoverEnabled()) {
            for (int j = 0; j < numParents; j++) {
                for (int i = 0; i < phraseLength; i++) {
                    currentPhrases[j][i] = getRandomChar();
                }
                updateMatches(currentPhrases[j], targetPhrase);
            }
        } else {
            for (int i = 0; i < phraseLength; i++) {
                currentPhrase[i] = getRandomChar();
            }
            updateMatches(currentPhrase, targetPhrase);
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @return boolean
     */

    public boolean getShowEvolve() {
        return showEvolve;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return double
     */

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public int getGeneration() {
        return generation;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return int
     */

    public int getTotalGenerations() {
        return totalGenerations;
    }

    /**
     * Returns true if it is at end, i.e. if it has found the correct phrase already
     */

    public boolean isDone() {
        return completed;
    }

    public String getCurrentPhrase() {
        if (!getCrossoverEnabled()) {
            return new String(currentPhrase);
        }
        int[] parentMatches = new int[numParents];
        for (int i = 0; i < numParents; i++) {
            parentMatches[i] = countMatches(currentPhrases[i], targetPhrase);
        }
        return new String(currentPhrases[Routines.getMaxIndex(parentMatches)]);
    }

    /**
     * Does the necessary process to create the next generation
     */

    public void doNextGeneration() {
        generation++;

        //Multiple parents (2 or more)
        if (getCrossoverEnabled()) {

            //Make the children
            for (int i = 0; i < broodSize; i++) {
                children[i] = makeChild(currentPhrases); //note: currentPhrases, not currentPhrase!
                childMatches[i] = countMatches(children[i], targetPhrase);
            }

            //Take most successful for next parents
            for (int j = 0; j < numParents; j++) {
                int best = Routines.getMaxIndex(childMatches, (j * getBroodSize()) / numParents, (((j + 1) * getBroodSize()) / numParents) - 1);
                currentPhrases[j] = children[best];
                updateMatches(currentPhrases[j], targetPhrase);
            }
        }

        //One parent
        else {

            //Make the children
            for (int i = 0; i < broodSize; i++) {
                children[i] = makeChild(currentPhrase);
                childMatches[i] = countMatches(children[i], targetPhrase);
            }

            //Take most successful for next parent
            int best = Routines.getMaxIndex(childMatches);
            currentPhrase = children[best];
            updateMatches(currentPhrase, targetPhrase);
        }
    }

    /**
     * This method was created in VisualAge.
     *
     * @return int
     */

    public int getIterationCount() {
        return iterationCount;
    }

    public String getMainCaption() {
        return res.getString("Woozleology");
    }

    /**
     * This method was created in VisualAge.
     *
     * @return double
     */

    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return int
     */

    public int getNumParents() {
        return numParents;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return int
     */

    public int getBestMatch() {
        return bestMatch;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return boolean
     */

    public boolean getShowCrossover() {
        return showCrossover;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return int
     */

    public int getBroodSize() {
        return broodSize;
    }

    public String getTargetPhrase() {
        return new String(targetPhrase);
    }

    /**
     * This method was created in VisualAge.
     *
     * @return boolean
     */

    public boolean getCrossoverEnabled() {
        return crossoverEnabled;
    }

    /**
     * This method was created in VisualAge.
     *
     * @return double
     */

    public double getAverageGeneration() {
        return averageGeneration;
    }

    /**
     * This method was created in VisualAge.
     *
     * @param newValue double
     */

    void setMutationRate(double newValue) {
        this.mutationRate = newValue;
    }

    /**
     * This method was created in VisualAge.
     *
     * @param newValue double
     */

    void setCrossoverRate(double newValue) {
        this.crossoverRate = newValue;
    }

    /**
     * This method was created in VisualAge.
     *
     * @param newValue int
     */

    void setAverageGeneration(int newValue) {
        this.averageGeneration = newValue;
    }

    /**
     * This method was created in VisualAge.
     *
     * @param newValue int
     */

    void setBroodSize(int newValue) {
        this.broodSize = newValue;
    }

    /**
     * For multiple parents
     * When changing parents, I simply increment by one
     */

    private char[] makeChild(char[][] parents) {
        int number = parents.length;
        char[] result = new char[phraseLength];
        int currentParent = 0;
        for (int i = 0; i < phraseLength; i++) {

            //Possibly do crossover
            if (changeParent()) {
                currentParent++;
                if (currentParent + 1 >= number) {
                    currentParent = 0;
                }
            }
            result[i] = isMutate() ? getRandomChar() : parents[currentParent][i];
        }
        return result;
    }

    /**
     * does not return number of matches
     */

    private void updateMatches(char[] array1, char[] array2) {
        int matches = countMatches(array1, array2);

        //This line below may be optional
        if (matches <= bestMatch) {
            return;
        }

        //else

        //    currentPhrase = array1;
        while (bestMatch < matches) {
            progress[bestMatch++] = generation;
        }
        if (bestMatch == phraseLength) {
            this.completed = true;
            this.iterationCount++;
            this.totalGenerations += generation;
            this.averageGeneration = ((double) totalGenerations) / ((double) iterationCount);
        }
        return;
    }

    private char getRandomChar() {
        int index = (int) (myRand.nextFloat() * possibleChars.length);
        try {
            return possibleChars[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return getRandomChar();
        }
    }

    /**
     * For a single parent
     */

    private char[] makeChild(char[] parent) {
        char[] result = new char[phraseLength];
        for (int i = 0; i < phraseLength; i++) {
            result[i] = isMutate() ? getRandomChar() : parent[i];
        }
        return result;
    }

    /**
     * Case doesn't matter when checking if the characters are the same.
     */

    private int countMatches(char[] array1, char[] array2) {
        int result = 0;
        for (int i = 0; i < array1.length; i++) {
            if ((array1[i] == array2[i]) || (Math.abs(array1[i] - array2[i]) == 32)) {
                result++;
            }
        }
        return result;
    }


    /**
     * This method was created in VisualAge.
     *
     * @param newValue boolean
     */

    private void setShowEvolve(boolean newValue) {
        this.showEvolve = newValue;
    }

    private boolean changeParent() {
        return (myRand.nextDouble() < crossoverRate);
    }

    private boolean isMutate() {
        return (myRand.nextDouble() < mutationRate);
    }
}
