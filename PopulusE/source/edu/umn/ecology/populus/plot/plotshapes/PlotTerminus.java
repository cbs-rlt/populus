package edu.umn.ecology.populus.plot.plotshapes;

import com.klg.jclass.chart.*;


/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

abstract public class PlotTerminus extends JCShape {
   private boolean isStart;
   protected double adjustment = 1.0;

   public boolean updateAdjustment( double newAdjustment ){
      if(newAdjustment != adjustment){
         this.adjustment = newAdjustment;
         return true;
      } else
         return false;
   }

   public PlotTerminus() {
      this(true);
   }
   public PlotTerminus(boolean isStart) {
      this.isStart = isStart;
   }
   public boolean isStart() {
      return isStart;
   }
}