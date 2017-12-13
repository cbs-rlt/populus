package edu.umn.ecology.populus.model.rct;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class RCTVariableIndex {
	private int numSpecies;
	private int numResources;

	public RCTVariableIndex(int numSpecies, int numResources) {
		this.numSpecies = numSpecies;
		this.numResources = numResources;
	}
	/** 0-based */
	public int getSpecIdx(int species) {
		return species;
	}
	/** 0-based */
	public int getResIdx(int resource) {
		return numSpecies + resource;
	}
	public int getResNum() {
		return numResources;
	}
	public int getSpecNum() {
		return numSpecies;
	}
	public int getTotalNum() {
		return numSpecies + numResources;
	}
}