package edu.umn.ecology.populus.fileio;

public class CancelledSave extends Exception {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 6223522664382154521L;

public CancelledSave( String s ) {
      super( "Cancelled Save: " + s );
   }
   
   public CancelledSave() {
      super( "Cancelled Save" );
   }
}
