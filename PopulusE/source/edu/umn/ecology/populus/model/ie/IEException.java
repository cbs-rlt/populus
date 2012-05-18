package edu.umn.ecology.populus.model.ie;
import java.lang.Exception;

/** An exception especially "designed" for use with the EquationCalculator
  class.*/

public class IEException extends Exception {
   
   public IEException( String message ) {
      super( message );
   }
}
