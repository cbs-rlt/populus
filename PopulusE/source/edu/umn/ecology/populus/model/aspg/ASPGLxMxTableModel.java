package edu.umn.ecology.populus.model.aspg;

import java.util.Stack;

import javax.swing.event.*;
import javax.swing.table.*;
import edu.umn.ecology.populus.fileio.Logging;

/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */

public class ASPGLxMxTableModel extends AbstractTableModel {
   static final int kCOLUMNS = 5;
   int rows = 0;
   Stack[] data;
   int type = ASPGPanel.kPOSTBREEDING;

   public ASPGLxMxTableModel(int rows) {
      int i, j;

      data = new Stack[kCOLUMNS];
      for (i = 0; i < kCOLUMNS; i++) {
         data[i] = new Stack();
      }
      setNumRows(rows);
      //initialize table to defaults if rows are 4
      if (rows == 4) {
         data[1].setElementAt(new Double(1.0), 0);
         data[1].setElementAt(new Double(0.5), 1);
         data[1].setElementAt(new Double(0.25), 2);
         data[2].setElementAt(new Double(0.0), 0);
         data[2].setElementAt(new Double(1.0), 1);
         data[2].setElementAt(new Double(5.0), 2);
         data[3].setElementAt(new Double(1.0), 0);
      }
      super.fireTableChanged(new TableModelEvent(this));
   }

   /**
    *  Return the name for the column
    */
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
   public Class getColumnClass(int columnIndex) {
      if(columnIndex == 0) return Integer.class;
      return Double.class;
   }

   /**
    *  Returns true for anything in columns 1-3
    */
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


   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      if(aValue == null) aValue = new Double(0);
      data[columnIndex].setElementAt(aValue, rowIndex);
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
            data[i].setSize(newSize);
         }
         rows = newSize;
         // Generate notification
         fireTableRowsDeleted(getRowCount(), oldNumRows-1);
      } else {
         int columnCount = getColumnCount();
         // We are adding rows to the model
         while(getRowCount() < newSize) {
            for (i = 0; i < kCOLUMNS; i++) {
               if (i == 0) {
                  data[i].addElement(new Integer(rows+1+getShift()));
               } else {
                  data[i].addElement(new Double(0.0));
               }
            }
            rows++;
         }

         // Generate notification
         fireTableRowsInserted(oldNumRows, getRowCount()-1);
      }
      data[1].setElementAt(new Double(0), rows-1);
      this.fireTableCellUpdated(rows-1, 1);
   }


   public int getRowCount() {
      return rows;
   }
   public int getColumnCount() {
      return kCOLUMNS-1;
   }

   public Object getValueAt(int row, int column) {
      if((row == (rows-1)) && (column == 3)) return null;
      if(type != ASPGPanel.kPREBREEDING){
         if (((row < rows-1) || (column < 2)) && !((row == 0) && (column == 2))) {
            return data[column].elementAt(row);
         } else {
            return null;
         }
      } else {
         if ((row < rows-1) || (column < 2)) {
            return data[column].elementAt(row);
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
            obj = data[j].elementAt(i);
            if (obj instanceof Double) {
               d[j-1][i] = ((Double) obj).doubleValue();
            } else if (obj instanceof String) {
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
            Logging.log("length doesn't match "+d.length, Logging.kHuh);
            return;
         }
         int nr = d[0].length;
         this.setNumRows(nr-1);
         for (int i = 0; i < kCOLUMNS; i++) {
            data[i].clear();
            data[i].setSize(d[i].length-1);
            for (int j = 0; j < d[i].length-1; j++) {
               double val = (i>0) ? d[i-1][j] : j;
               data[i].add(j, new Double(val));
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

      data[0].removeAllElements();
      for(int i = 0; i < rows; i++){
         data[0].addElement(new Integer(i+1+getShift()));
      }
      if(setPre)
      if(type != ASPGPanel.kPREBREEDING)
         data[1].setElementAt(new Double(1.0), 0);
      else
         data[1].setElementAt(new Double(firstL1), 0);
      fireTableChanged(new TableModelEvent(this));
   }

   public int getType(){
      return type;
   }
}