/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual.matrixtable;

import edu.umn.ecology.populus.fileio.Logging;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class MatrixTableModel extends AbstractTableModel implements java.io.Externalizable {
    int dimension = 0;
    int indexShift = -1;
    boolean lastCanHaveStarts = false;
    /*each element of the array refers to a column, each element in the vector
   refers to the row values*/
    Vector[] table;
    transient JTable jt; //Lars - What is this???
    boolean isASPG;

    public MatrixTableModel() {
        this(false);
    }

    public MatrixTableModel(boolean isASPG) {
        this.isASPG = isASPG;
        table = new Vector[0];
        super.fireTableChanged(new TableModelEvent(this));
    }

    /**
     * Lars - what do we need this function for??
     */
    public void setTable(JTable t) {
        jt = t;
    }

    public void clearData() {
        table = new Vector[1];
        table[0] = new Vector();
        dimension = 0;
        super.fireTableChanged(new TableModelEvent(this));
    }

    public void addDimension(double initPop) {
        dimension++;

        Vector[] temp = new Vector[dimension + 1];
        for (int i = 0; i < dimension - 1; i++)
            temp[i] = table[i];
        temp[dimension] = table[dimension - 1];
        temp[dimension - 1] = new Vector(0);
        table = temp;

        table[dimension].add(initPop);

        for (int i = 0; i < dimension - 1; i++)
            table[dimension - 1].add(0.0);
        for (int i = 0; i < dimension; i++)
            table[i].add(0.0);
    }

    public void removeDimension() {
        dimension--;

        Vector[] temp = new Vector[dimension + 1];
        for (int i = 0; i < dimension; i++) {
            temp[i] = table[i];
            temp[i].removeElementAt(dimension);
        }
        temp[dimension] = table[dimension + 1];
        temp[dimension].removeElementAt(dimension);
        table = temp;

        if (!lastCanHaveStarts) {
            setValueAt(0.0, 0, dimension - 1);
        }
        fireTableStructureChanged();
    }

    @Override
    public String getColumnName(int column) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (!isASPG) return true;
        if (!lastCanHaveStarts && columnIndex == dimension - 1) return false;
        if (rowIndex == 0 || columnIndex == dimension) return true;
        if (rowIndex - 1 == columnIndex) return true;
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue == null) aValue = 0.0;
        table[columnIndex].setElementAt(aValue, rowIndex);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dimension;
    }

    @Override
    public int getColumnCount() {
        return dimension + 1;
    }

    public int getDataDimension() {
        return dimension;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return table[column].get(row);
    }

    public void setNumRows(int newSize) {
        int oldSize = getRowCount();
        if ((newSize < 0) || (newSize == oldSize))
            return;
        if (newSize < oldSize) {
            while (newSize != getRowCount()) removeDimension();
            fireTableStructureChanged();
            return;
        } else {
            while (newSize != getRowCount()) addDimension(0);
            fireTableStructureChanged();
            return;
        }
    }

    public synchronized double[][] getMatrix() {
        double[][] data = new double[dimension][dimension];
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                data[i][j] = (Double) getValueAt(i, j);
        return data;
    }

    public synchronized double[] getPopulations() {
        double[] data = new double[dimension];
        for (int i = 0; i < data.length; i++)
            data[i] = (Double) getValueAt(i, dimension);
        return data;
    }

    public synchronized void setData(double[][] mat, double[] pops, int indexShift, boolean lastCanHaveStarts) {
        clearData();
        this.lastCanHaveStarts = lastCanHaveStarts;
        if (mat.length != mat[0].length) return;
        if (mat.length > dimension) {
            for (int i = dimension; i < mat.length; i++)
                this.addDimension(0);
        }
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat.length; j++)
                this.setValueAt(mat[i][j], i, j);

        Vector p = new Vector();
        for (double pop : pops) p.add(pop);
        table[dimension] = p;

        fireTableStructureChanged();
    }

    public void setModel(boolean lastCanHaveStarts) {
        this.lastCanHaveStarts = lastCanHaveStarts;
    }

    @Override
    public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        out.writeInt(dimension);
        out.writeInt(indexShift);
        out.writeBoolean(lastCanHaveStarts);
        out.writeBoolean(isASPG);
        out.writeObject(table);
    }

    @Override
    public void readExternal(java.io.ObjectInput in) {
        try {
            dimension = in.readInt();
            indexShift = in.readInt();
            lastCanHaveStarts = in.readBoolean();
            isASPG = in.readBoolean();
            table = (Vector[]) in.readObject();
        } catch (Exception e) {
            Logging.log(e);
        }
    }


}
