/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */

package edu.umn.ecology.populus.model.aspg;

import java.util.ArrayList;
import javax.swing.event.*;
import javax.swing.table.*;

import edu.umn.ecology.populus.fileio.Logging;

public class ASPGLxMxTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 4778650848890791005L;
	static final int kCOLUMNS = 5;
	int rows = 0;
	ArrayList<ArrayList<Number>> data;
	int type = ASPGPanel.kPOSTBREEDING;

	public ASPGLxMxTableModel(int rows) {
		int i;

		data = new ArrayList<ArrayList<Number>>(kCOLUMNS);
		for (i = 0; i < kCOLUMNS; i++) {
			data.add(new ArrayList<Number>());
		}
		setNumRows(rows);
		//initialize table to defaults if rows are 4
		if (rows == 4) {
			data.get(1).set(0, 1.0);
			data.get(1).set(1, 0.5);
			data.get(1).set(2, 0.25);
			data.get(2).set(0, 0.0);
			data.get(2).set(1, 1.0);
			data.get(2).set(2, 5.0);
			data.get(3).set(0, 1.0);
		}
		super.fireTableChanged(new TableModelEvent(this));
	}

	/**
	 *  Return the name for the column
	 */
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "<i>x</i>";
		case 1:
			return "<i>l</i><sub>x</sub>";
		case 2:
			return "<i>m</i><sub>x</sub>";
		case 3:
			return "Initial <i>S</i><sub>x</sub>(0)";
		default:
			return null;
		}
	}

	/**
	 *  Returns Object.class by default
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0) return Integer.class;
		return Double.class;
	}

	/**
	 *  Returns true for anything in columns 1-3
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(type != ASPGPanel.kPREBREEDING)
			return
					(columnIndex > 0) &&
					(columnIndex < kCOLUMNS) &&
					!((rowIndex == 0) && ((columnIndex == 1) || (columnIndex == 2))) &&
					(rowIndex != rows-1);
		else
			return
					(columnIndex > 0) &&
					(columnIndex < kCOLUMNS) &&
					(rowIndex != rows-1);
	}

	public void setTable(double[][] newLxMx, int type){
		int n = newLxMx[0].length - 1;
		setNumRows(n);
		for(int i=0; i<ASPGData.kP; i++){
			for(int j=0; j<n; j++){
				setValueAt(new Double(newLxMx[i][j]),j,i+1);
			}
		}
		setType(type, false, 0);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(aValue == null) aValue = new Double(0);
		data.get(columnIndex).set(rowIndex, (Number) aValue);
		if(rowIndex == 0 && columnIndex == 1){
			fireTableChanged(new TableModelEvent(this,rowIndex,rowIndex,columnIndex));
		}
	}

	public void setNumRows(int newSize) {
		if ((newSize < 0) || (newSize == getRowCount()))
			return;

		int i;
		int oldNumRows = getRowCount();
		if (newSize <= getRowCount()) {
			// newSize is smaller than our current size, so we can just
			// let Vector discard the extra rows
			for (i = 0; i < kCOLUMNS; i++) {
				data.get(i).ensureCapacity(newSize);
			}
			rows = newSize;
			// Generate notification
			fireTableRowsDeleted(getRowCount(), oldNumRows-1);
		} else {
			getColumnCount();
			// We are adding rows to the model
			while(getRowCount() < newSize) {
				for (i = 0; i < kCOLUMNS; i++) {
					if (i == 0) {
						data.get(i).add(rows+1+getShift());
					} else {
						data.get(i).add(0.0);
					}
				}
				rows++;
			}

			// Generate notification
			fireTableRowsInserted(oldNumRows, getRowCount()-1);
		}
		data.get(1).set(rows-1, 0.0);
		this.fireTableCellUpdated(rows-1, 1);
	}


	@Override
	public int getRowCount() {
		return rows;
	}
	@Override
	public int getColumnCount() {
		return kCOLUMNS-1;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if((row == (rows-1)) && (column == 3)) return null;
		if(type != ASPGPanel.kPREBREEDING){
			if (((row < rows-1) || (column < 2)) && !((row == 0) && (column == 2))) {
				return data.get(column).get(row);
			} else {
				return null;
			}
		} else {
			if ((row < rows-1) || (column < 2)) {
				return data.get(column).get(row);
			} else {
				return null;
			}
		}
	}
	//Returns lx, mx
	//Creates a new matrix, an array of lx, ms, Sx arrays plus two additional arrays,
	//All of length numClasses.
	public synchronized double[][] getData() {
		double[][] d = new double[kCOLUMNS][rows+1]; //gives more room than needed for later
		int i, j;
		Object obj;
		for (i = 0; i < rows; i++) {
			for (j = 1; j < kCOLUMNS-1; j++) {
				obj = data.get(j).get(i);
				if (obj instanceof Double) {
					d[j-1][i] = ((Double) obj).doubleValue();
				} else if (obj instanceof String) {
					//TODO - does this really happen???
					d[j-1][i] = Double.parseDouble((String) obj);
				}
			}
		}
		return d;
	}

	public void setData(Object o) {
		double[][] d;
		try {
			d = (double[][]) o;
			if (d.length != kCOLUMNS) {
				Logging.log("length doesn't match "+d.length, Logging.kWarn);
				return;
			}
			int nr = d[0].length;
			this.setNumRows(nr-1);
			for (int i = 0; i < kCOLUMNS; i++) {
				data.get(i).clear();
				data.get(i).ensureCapacity(d[i].length-1);
				for (int j = 0; j < d[i].length-1; j++) {
					double val = (i>0) ? d[i-1][j] : j;
					data.get(i).add(j, val);
				}
			}
		}
		catch (Exception e) {
			Logging.log(e);
		}
	}

	//change the method with the same name in ASPGPanel
	int getShift(){
		//      return 0;
		return type == ASPGPanel.kPREBREEDING?0:-1;
		//      return getModelType() == kCONTINUOUS?-1:0;
	}

	public void setType(int newType, boolean setPre, double firstL1){
		this.type = newType;

		data.get(0).clear();
		for(int i = 0; i < rows; i++){
			data.get(0).add(i+1+getShift());
		}
		if(setPre)
			if(type != ASPGPanel.kPREBREEDING)
				data.get(0).set(0, 1.0);
			else
				data.get(0).set(0,  firstL1);
		fireTableChanged(new TableModelEvent(this));
	}

	public int getType(){
		return type;
	}
}