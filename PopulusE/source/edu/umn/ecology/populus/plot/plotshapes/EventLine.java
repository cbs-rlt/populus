package edu.umn.ecology.populus.plot.plotshapes;

import edu.umn.ecology.populus.plot.*;

/**
 * <p>Title: Makes a mark in the graph where a specific event occurs.</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class EventLine {

   public EventLine() {
   }

   public static void addEventLine(BasicPlotInfo bpi,
                                   //int lineToAddTo, /* ignored ? */
                                   double x
                                   //double yi, double yf
                                   //Color c
                                   )
   {
      bpi.findBounds();
      double ymin = bpi.getMinYVal();
      double ymax = bpi.getMaxYVal();
      double[][] newLine = new double[][] { {x, x}, {ymin, ymax} };

      bpi.addData(newLine);
   }
   public static void addEventMark(BasicPlotInfo bpi,
                                   int lineToAddTo, /* ignored ? */
                                   double x,
                                   double y
                                   //Color c
                                   )
   {

      double[][] termSpot = new double[][] { {x}, {y} };
      PlotTerminus p = new Star();
      bpi.setTerminus(lineToAddTo, termSpot, p);
   }
}

