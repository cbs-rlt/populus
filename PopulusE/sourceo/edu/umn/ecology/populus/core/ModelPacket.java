package edu.umn.ecology.populus.core;
import java.lang.reflect.*;

public final class ModelPacket implements java.io.Serializable {
   private String modelName;
   private Class modelClass;
   
   public Class getModelClass() {
      return modelClass;
   }
   
   public String getModelName() {
      return modelName;
   }
   
   public ModelPacket( Class modelClass ) {
      this.modelClass = modelClass;
      
      //Extract model name using class
      try {
         Method m = modelClass.getMethod( "getModelName", new Class[0] );
         modelName = (String)m.invoke( null, null );
      }
      catch( Exception e ) {
         this.modelName = "Error Finding Model Name";
         System.err.println( "Error finding model name for " + modelClass );
         System.err.println( e );
      }
   }
}
