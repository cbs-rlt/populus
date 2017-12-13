package edu.umn.ecology.populus.plot.plotshapes;

import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.core.PopPreferencesStorage;
import edu.umn.ecology.populus.fileio.Logging;

//TODO - It seems like this should belong in BasicPlotInfo?

/**
 * Contains methods for adding arrows and/or fletching to BasicPlotInfo lines.
 * Should this be part of the BasicPlotInfo class?
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class PlotArrow {

	/**
	 * just call this method and it will add an arrow to the end of
	 * the line in the bpi indicated by <code>tolineNumber</code>.
	 *
	 * It does this by simply creating a new line that duplicates the end/beginning
	 * of the existing line and uses this for a symbol.
	 * @see edu.umn.ecology.populus.plot.plotshapes.Fletching
	 * @param bpi the BasicPlotInfo with the line
	 * @param toLineNumber which line to put the arrow on
	 */
	public static void addArrow( BasicPlotInfo bpi, int toLineNumber ) {
		double[][][] data = bpi.getData();
		double xf = 0, yf = 0, xi = 0, yi = 0;
		if( toLineNumber >= data.length ) {
			edu.umn.ecology.populus.fileio.Logging.log( "Error: no such line to add arrow.", Logging.kWarn );
			return;
		}

		xf = data[toLineNumber][0][data[toLineNumber][0].length - 1];
		yf = data[toLineNumber][1][data[toLineNumber][1].length - 1];
		if( data[toLineNumber][0].length > 1 ) {
			xi = data[toLineNumber][0][data[toLineNumber][0].length - 2];
			yi = data[toLineNumber][1][data[toLineNumber][1].length - 2];
		}

		double[][] newLine = new double[][] { {xf}, {yf} };

		PlotTerminus p;
		if (PopPreferencesStorage.getTerminusType() == PopPreferencesStorage.kARROWTERMINI)
			p = new Arrow(yf-yi,xf-xi);
		else
			p = new CircleTerminus(false);
		bpi.setTerminus(toLineNumber, newLine, p);
	}

	/**
	 * just call this method and it will add an arrow to the beginning of
	 * the line in the bpi indicated by <code>tolineNumber</code>
	 * @see edu.umn.ecology.populus.plot.plotshapes.Fletching
	 * @param bpi the BasicPlotInfo with the line
	 * @param toLineNumber which line to put the arrow on
	 */
	public static void addFletching( BasicPlotInfo bpi, int toLineNumber ) {
		double[][][] data = bpi.getData();
		double xf = 0, yf = 0, xi = 0, yi = 0;
		if( toLineNumber >= data.length ) {
			edu.umn.ecology.populus.fileio.Logging.log( "Error: no such line to add fletching." );
			return;
		}

		xf = data[toLineNumber][0][1];
		yf = data[toLineNumber][1][1];
		if( data[toLineNumber][0].length > 1 ) {
			xi = data[toLineNumber][0][0];
			yi = data[toLineNumber][1][0];
		}

		double[][] newLine = new double[][] { {xi}, {yi} };

		PlotTerminus p;
		if (PopPreferencesStorage.getTerminusType() == PopPreferencesStorage.kARROWTERMINI)
			p = new Fletching(yf-yi,xf-xi);
		else
			p = new CircleTerminus(true);
		bpi.setTerminus(toLineNumber, newLine, p);
	}
}