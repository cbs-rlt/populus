package edu.umn.ecology.populus.plot;

/**
 * if a graph needs to be a "live" graph, meaning that it draws it's data
 * little at a time, then the graph drawing class should implement this
 * interface. A class that implements this interface can then be passed
 * to a LiveGraph class, which can draw the data a little at a time.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public interface Stepper{

	/**
	 * this is to give the implementing class a chance to set up for
	 * being "alive"
	 * @param increment the number of steps the graph should take each <code>stepGraph</code>
	 * call
	 */
	public void setUpLive(int increment);

	/**
	 * the implementation of this method should increment some sort
	 * of counter for how many of the data points should be drawn.
	 * this method will be called by LiveGraph for each increment of
	 * drawing.
	 * @return the boolean returned should be <code>false</code> if there are
	 * no more increments available, and <code>true</code> if we aren't done graphing
	 * yet.
	 */
	public boolean stepGraph();

	/**
	 * this method is here so that implementing classes will be forced
	 * to include this method, which strongly encouranges the use of
	 * "offscreen graphics drawing" or blit-ing so that only the latest
	 * increment needs to be drawn, and not the entire graph each time.
	 */
	//public void prepNextImage();
}
