
//Title:        Populus

//Version:      

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//							with Populus
package edu.umn.ecology.populus.core;

public class CannotChangeModelException extends Exception {
   public static final String OTHER_MODEL_RUNNING = "Another Model is Already Processing and Has Not Stopped Yet";
   
   public CannotChangeModelException( String reason ) {
      super( reason );
   }
   
   public CannotChangeModelException() {
      this( OTHER_MODEL_RUNNING );
   }
}
