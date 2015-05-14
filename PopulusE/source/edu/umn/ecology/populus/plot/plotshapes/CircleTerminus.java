package edu.umn.ecology.populus.plot.plotshapes;

import java.awt.Graphics;


/**
 * Circle that goes at the beginning or end of a line.
 * 
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class CircleTerminus extends PlotTerminus {

   public CircleTerminus(boolean isStart) {
      super(isStart);
   }

   protected void resize(int size) {
      this.size = size;
   }

   public void draw(Graphics gc,
                    int xorg,
                    int yorg) {
      int xi, yi, d;
      xi = xorg - size;
      yi = yorg - size;
      d = size * 2;
      if (this.isStart())
         gc.drawOval(xi, yi, d, d);
      else
         gc.fillOval(xi, yi, d, d);
   }

   
   //TODO - what about the draw with floating point args? Can that ever be called?
   
   //TODO - does this ever really get called???
   protected float[] getArray(int size) {
      int steps = 20;
      float[] pc = new float[steps * 2];
      double s = (double) size;

      for (int i = 0; i < steps; i++) {
         double angle = (2.0 * Math.PI * i) / ((double) steps);
         pc[2*i] = (float) (Math.cos(angle) * s);
         pc[2*i + 1] = (float) (Math.sin(angle) * s);
      }
      return pc;
   }
}

