package edu.umn.ecology.populus.plot.plotshapes;

/**
 * <p>Title: Populus</p>
 * <p>Description: Arrow is the last point on a graph.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class Arrow extends DirectionalPlotTerminus {

   public Arrow( double dy, double dx) {
      super(true);
      this.setDeltas(dy, dx);
   }

   protected float[] getArray(int size){
      int numPoints = 2;
      float f = size / 1.0f;
      float[] pc = new float[2 * ( numPoints + 2 )];
      float[] pf = new float[2 * ( numPoints + 2 )];
      for( int i = 0;i < ( numPoints / 2 );i++ ) {
         pc[i] = ( -3 + i ) * f;
         pc[i + 1] = f;
         pc[i + numPoints] = ( -3 + i ) * f;
         pc[i + numPoints + 1] = -f;
      }
      pc[2 * numPoints] = 0;
      pc[2 * numPoints + 1] = -2;
      pc[2 * numPoints + 2] = 0;
      pc[2 * numPoints + 3] = 2;
      return pc;
   }
}
