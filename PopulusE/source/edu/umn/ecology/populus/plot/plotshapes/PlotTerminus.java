package edu.umn.ecology.populus.plot.plotshapes;

import java.awt.Polygon;

import com.klg.jclass.chart.*;


/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

//TODO - do not extend from JCShape, but rather just use the getJCShape function

abstract public class PlotTerminus extends JCShape {
   private boolean isStart;
   protected double adjustment = 1.0;

   /**
    * @param newAdjustment ratio of the data's aspect ratio to the chart area's aspect ratio
    * @return true if the value has changed
    */
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
   
   //For JFreeChart
   //  TODO - what about fill? Fill is handled in the class here by JClass, but not JFreeChart.
   public java.awt.Shape getShape() {
	   return new Polygon(x, y, x.length);
   }
   
   public JCShape getJCShape() {
	   return this;
   }
   
}



