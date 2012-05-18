package edu.umn.ecology.populus.math;

abstract public class DiscreteProc {
   protected int numVariables = -1;
   
   /**
     * gen is current generation to get  <BR>
     * y is both input and output
     */
   
   public abstract void v( long gen, double[] y );
   
   /********************/
   
   /* ACCESSOR METHODS */
   
   /********************/
   
   public int getNumVariables() {
      return numVariables;
   }
   
   public DiscreteProc() {
      
   }
}
