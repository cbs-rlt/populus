package edu.umn.ecology.populus.plot.coloredcells;

public interface CellFunction{
	/** Get the values to start up the CellPanel*/
	public double[][] initialF();

	/** Get the next iteration of values*/
	public double[][] f();

	/** Change type, get other values*/
	public double[][] changeType(String s);

	/** Get the desired dimension of the data (not used, dim comes from data)*/
	public int getDimension();

	/** Get the strings that will be used in the key*/
	public String[] getStrings();

	/** Get the values that separate the data into the different colors*/
	public double[] getDemarcations();

	/** Get the interval of iterations before a pause*/
	public int getBreakInterval();

	/** Get the output types*/
	public String[] getOutputTypes();

	/** Get current model type*/
	public String getCurrentType();

	/** Set one of the values in the matrix*/
	public void setValue(int species, int r, int c, double newValue);

	/** Get Color Palette*/
	public CellPalette getColorPalette();

	/** Get the current generation*/
	public int getGeneration();
}