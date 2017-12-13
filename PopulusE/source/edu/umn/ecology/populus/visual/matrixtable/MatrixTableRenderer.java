package edu.umn.ecology.populus.visual.matrixtable;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import edu.umn.ecology.populus.core.PopPreferencesStorage;
import edu.umn.ecology.populus.visual.SpecialLineBorder;

/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */

public class MatrixTableRenderer extends DefaultTableCellRenderer {
	/**
	 *
	 */
	private static final long serialVersionUID = -5435376239339922955L;

	public MatrixTableRenderer(){
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		TableModel model = table.getModel();
		if (model.isCellEditable(row, column))
			setBackground(PopPreferencesStorage.getTableEditColor());
		else
			setBackground(PopPreferencesStorage.getTableUneditColor());
		setFont(table.getFont());

		if (hasFocus) {
			setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
			if (table.isCellEditable(row, column)) {
				super.setForeground( UIManager.getColor("Table.focusCellForeground") );
				super.setBackground( UIManager.getColor("Table.focusCellBackground") );
			}
		} else {
			setBorder(noFocusBorder);
		}

		SpecialLineBorder slb = new SpecialLineBorder(Color.black,3);
		if(column == 0){
			slb.setEdge(false,false,true,false);
			if(row == 0) slb.setCorner(true,false,false,false);
			if(row == table.getColumnCount()-2) slb.setCorner(false,false,true,false);
			setBorder(slb);
		}
		if(column == table.getColumnCount()-2){
			slb.setEdge(false,false,false,true);
			if(row == 0) slb.setCorner(false,true,false,false);
			if(row == table.getColumnCount()-2) slb.setCorner(false,false,false,true);
			setBorder(slb);
		}
		if(column == table.getColumnCount()-1){
			slb.setEdge(false,false,true,true);
			if(row == 0) slb.setCorner(true,true,false,false);
			if(row == table.getColumnCount()-2) slb.setCorner(false,false,true,true);
			setBorder(slb);
		}
		setHorizontalAlignment(SwingConstants.CENTER);

		setValue(value);

		// ---- begin optimization to avoid painting background ----
		Color back = getBackground();
		boolean colorMatch = (back != null) && ( back.equals(table.getBackground()) ) && table.isOpaque();
		setOpaque(!colorMatch);
		// ---- end optimization to aviod painting background ----

		return this;
	}
	/*
   private void writeObject(java.io.ObjectOutputStream out)
       throws java.io.IOException
   {
   }

   private void readObject(java.io.ObjectInputStream in)
       throws java.io.IOException, ClassNotFoundException
   {
   }
	 */
}