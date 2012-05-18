package edu.umn.ecology.populus.fileio;

public class CancelledSave extends Exception {
   
   public CancelledSave( String s ) {
      super( "Cancelled Save: " + s );
   }
   
   public CancelledSave() {
      super( "Cancelled Save" );
   }
}
