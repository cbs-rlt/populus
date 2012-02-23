package edu.umn.ecology.populus.plot.plotshapes;

import java.awt.Graphics;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class Star extends PlotTerminus {

   public Star() {
      super();
      //resize(5); //Lars - Is it necessary to resize just to initialize x and y??
   }

   protected void resize( int size ) {
      int nPoints = 8;

      x = new int[nPoints];
      y = new int[nPoints];

      for (int i = 0; i < nPoints; i++) {
         double angle = (Math.PI * 2.0 * (i + 0.5)) / ((double) nPoints);
         x[i] = (int) Math.rint(Math.cos(angle) * size);
         y[i] = (int) Math.rint(Math.sin(angle) * size);
      }
   }

   /**
    * Draws the point at the specified coordinates
    */
   public void draw(Graphics gc, int xorg, int yorg) {
      for (int i = x.length-1; i>=0; i--) {
         gc.drawLine(xorg, yorg, xorg+x[i], yorg+y[i]);
      }
   }
}

