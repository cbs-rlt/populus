package edu.umn.ecology.populus.plot;

import javax.swing.*;
/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */

public interface TableInterface {
	public double[][] getMatrix();
	public JComponent[] getMatrixLabels();
	public int getXStart();
	public int getYStart();
	public int getXEnd();
	public int getYEnd();
	public String[] getTitle();

	/*   matrix = info.getMatrix();
matrixPanel.setTopLabels(info.getMatrixLabels());
matrixPanel.setMatrixData(matrix, info.getXStart(), info.getXEnd(), info.getYStart(), info.getYEnd());
//matrixPanel.setTopLabelsVisible(false);
gridLayout1.setColumns(1);
		gridLayout1.setRows(info.getTitle().length);
	 */
}