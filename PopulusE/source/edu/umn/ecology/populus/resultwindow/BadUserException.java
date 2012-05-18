package edu.umn.ecology.populus.resultwindow;

/** This is the exception class for sending an error to the output screen.*/

public class BadUserException extends Error {
   
   /**
     * BadUserException constructor comment.
     * @param s java.lang.String
     */
   
   public BadUserException( String s ) {
      super( s );
   }
   
   /**
     * BadUserException constructor comment.
     */
   
   public BadUserException() {
      super();
   }
}
