package edu.umn.ecology.populus.model.ie;
import edu.umn.ecology.populus.math.Derivative;

public class IEDeriv extends Derivative {
   boolean[] plotted;
   private EquationCalculator calc;

   public void doDerivative( double time, double[] y, double[] dN ) {
      double[] a = calc.calculateValues( y, time );
      for( int i = 0;i < dN.length;i++ ) {
         dN[i] = a[i];
      }
   }

   public int getPlotCount() {
      int count = 0;
      for( int i = 0;i < plotted.length;i++ ) {
         if( plotted[i] ) {
            count++;
         }
      }
      if( count > 3 ) {
         return 3;
      }
      return count;
   }

   public int[] getPlottedList() {
      int[] list = new int[getPlotCount()];
      int ind = 0;
      for( int i = 0;i < plotted.length;i++ ) {
         if( plotted[i] ) {
            list[ind++] = i;
         }
      }
      return list;
   }

   public double[] postDerivative(double[] v, double t){ return v; }

   public IEDeriv( EquationCalculator ss, boolean[] plot ) {
      plotted = plot;
      calc = ss;
      this.numVariables = calc.numVariables(); //doesn't take unused equations into account
   }
}
