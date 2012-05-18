package edu.umn.ecology.populus.model.ppplot;
import edu.umn.ecology.populus.plot.*;

//import edu.umn.ecology.populus.math.*;

public abstract class PPPlotInfo extends BasicPlotInfo {
   
   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp = null;
      return bp;
   }
   
   public PPPlotInfo() {
      
   }
   
   /**
     * Returns the lines that make up the isoclines.
     * Usually should be double[2][2][2], but the first
     * 2 may vary
     */
   
   protected abstract double[][][] getIsoclines();
}
