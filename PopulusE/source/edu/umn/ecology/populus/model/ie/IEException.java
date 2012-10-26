package edu.umn.ecology.populus.model.ie;
import java.lang.Exception;

/** An exception especially "designed" for use with the EquationCalculator
  class.*/

public class IEException extends Exception {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 3719610138118809274L;

public IEException( String message ) {
      super( message );
   }
}
