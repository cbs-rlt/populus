package edu.umn.ecology.populus.constants;

import edu.umn.ecology.populus.plot.coloredcells.CellDefaults;
import java.io.Serializable;

/**
 * a container for saving cell settings
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class ValuesToSave implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 342451521705294304L;
int widthForCells;
   int heightForCells;

   public ValuesToSave(){
      heightForCells = CellDefaults.kHeight;
      widthForCells = CellDefaults.kWidth;
   }

   public void setValues(){
      CellDefaults.kHeight = heightForCells;
      CellDefaults.kWidth = widthForCells;
   }

   public static void setDefaults(){
      CellDefaults.kHeight = CellDefaults.kHeightDefault;
      CellDefaults.kWidth = CellDefaults.kWidthDefault;
   }
}