package edu.umn.ecology.populus.model.mnp;
import java.util.*;

import javax.swing.table.*;
import javax.swing.event.*;

import edu.umn.ecology.populus.math.*;

public class MNPTable extends AbstractTableModel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -4666061719715462195L;

	int numRows=0;

   /**
    * each vector represents a row, each array index represents
    * the column
    */
   @SuppressWarnings("unchecked")
   Vector<Object>[] table = new Vector[0];

   public MNPTable() {

      addRow(0.75,new double[] {0.6, 1.0, 1.0});
      addRow(0.25,new double[] {1.0, 1.0, 0.4});

      super.fireTableChanged(new TableModelEvent(this));
   }

   public void addRow(double percent, double[] w){
      numRows++;
      Vector<Object> newRow = new Vector<Object>();
      newRow.add(new Integer(numRows));
      newRow.add(new Double(percent));
      newRow.add(new Double(w[0]));
      newRow.add(new Double(w[1]));
      newRow.add(new Double(w[2]));

      @SuppressWarnings("unchecked")
	  Vector<Object>[] temp = new Vector[numRows];
      for(int i=0; i<table.length; i++)
         temp[i] = table[i];
      temp[table.length] = newRow;
      table = temp;
      this.fireTableStructureChanged();
   }

   public void removeRow(){
      if(numRows == 0) return;
      numRows--;
      Vector[] temp = new Vector[numRows];
      for(int i=0; i<temp.length; i++)
         temp[i] = table[i];
      table = temp;
      this.fireTableStructureChanged();
   }

   public String getColumnName(int column) {
      switch (column) {
         case 0:
            return "Habitat";
         case 1:
            return "<i>c</i>";
         case 2:
            return "<i>w<sub>AA</sub></i>";
         case 3:
            return "<i>w<sub>Aa</sub></i>";
         case 4:
            return "<i>w<sub>aa</sub></i>";
         default:
            return null;
      }
   }

   public Class<?> getColumnClass(int c) {
      return getValueAt(0, c).getClass();
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      if(columnIndex==0) return false;
      return true;
   }

   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      table[rowIndex].setElementAt(aValue,columnIndex);
      fireTableDataChanged();
      if(columnIndex == 1){
         double[] a = new double[getRowCount()];
         for(int i=0; i<a.length; i++)
            a[i] = ((Double)getValueAt(i,columnIndex)).doubleValue();
         a = Routines.balanceArray(a,1.0,rowIndex);
         for(int i=0; i<a.length; i++)
            table[i].setElementAt(new Double(a[i]),columnIndex);
      }
   }

   public int getRowCount() {
      return numRows;
   }
   public int getColumnCount() {
      return 5;
   }

   public Object getValueAt(int row, int column) {
      return table[row].get(column);
   }

   synchronized double[][] getMatrix() {
      double[][] data = new double[numRows][4];
      for(int i=0; i<data.length; i++)
         for(int j=0; j<data[i].length; j++)
            data[i][j] = ((Double)getValueAt(i,j+1)).doubleValue();
      return data;
   }
}