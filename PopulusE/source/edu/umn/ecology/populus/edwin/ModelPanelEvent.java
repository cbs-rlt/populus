package edu.umn.ecology.populus.edwin;

//Title:        Populus

//Version:      

//Copyright:    Copyright (c) 1998

//Author:       Lars Roe

//Company:      University of Minnesota

import edu.umn.ecology.populus.visual.ppfield.*;

/**
  *
  */

public class ModelPanelEvent extends java.util.EventObject implements ModelPanelEventTypes {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8802443706867959580L;
private ParameterFieldEvent parameterFieldEvent = null;
   private int type;
   
   public ModelPanelEvent( Object source, int type, ParameterFieldEvent e ) {
      super( source );
      setParameterFieldEvent( e );
      this.type = type;
   }
   
   public ModelPanelEvent( Object source, ParameterFieldEvent e ) {
      super( source );
      setParameterFieldEvent( e );
      this.type = 0;
      if( e.isIncDec() || e.isMenu() ) {
         this.type |= ADJUSTMENT;
      }
      if( e.isEnter() ) {
         this.type |= ENTER;
      }
      if( e.isText() ) {
         this.type |= TYPING;
      }
   }
   
   public ModelPanelEvent( Object source, int type ) {
      super( source );
      this.type = type;
   }
   
   public int getType() {
      return type;
   }
   
   public boolean isMandatory() {
      if( !isParameterFieldEvent() ) {
         return ( type == ENTER );
      }
      else {
         return getParameterFieldEvent().isMandatory();
      }
   }
   
   public boolean isParameterFieldEvent() {
      return ( parameterFieldEvent != null );
   }
   
   /**
     * @return edu.umn.ecology.populus.visual.paramfield.ParameterFieldEvent
     */
   
   public ParameterFieldEvent getParameterFieldEvent() {
      return parameterFieldEvent;
   }
   
   protected void setParameterFieldEvent( ParameterFieldEvent newValue ) {
      this.parameterFieldEvent = newValue;
   }
}
