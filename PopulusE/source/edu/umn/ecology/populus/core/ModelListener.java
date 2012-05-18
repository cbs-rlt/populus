package edu.umn.ecology.populus.core;

/**
  * Listens to the Models. Created in 1.1 version
  */

public interface ModelListener {
   
   public void modelChanged( ModelEvent e ) throws CannotChangeModelException;
}
