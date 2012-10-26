package edu.umn.ecology.populus.resultwindow;
import java.util.*;
import java.awt.*;

/**
  * Used to communicate between an output window and its creator(s)
  *  
  * @author Lars Roe
  */

public class OutputPanelEvent extends EventObject {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7682406453406166585L;
public static final int RESIZED = 2;
   public static final int SWITCH_OUTPUT = 4;
   public static final int COPY_WINDOW = 8;
   public static final int KILL = 16;
   public static final int ACTIVATED = 32;
   public static final int ZOOM = 1;
   public static final int EXPORT = 128;
   public static final int EXPORT_SIMPLE = 256;
   public static final int REFRESH = 64;
   boolean zoom = false;
   double xMin = Double.NaN, xMax = Double.NaN, yMin = Double.NaN, yMax = Double.NaN;
   boolean resized = false;
   boolean switchOutput = false;
   boolean refresh = false;
   private int type;
   
   /**
     * Informative Constructor
     */
   
   public OutputPanelEvent( Component c, int type, Object additional ) {
      super( c );
      this.type = type;
      if( ( type & ZOOM ) != 0 ) {
         zoom = true;
      }
      if( ( type & RESIZED ) != 0 ) {
         resized = true;
      }
      if( ( type & SWITCH_OUTPUT ) != 0 ) {
         switchOutput = true;
      }
      if( ( type & REFRESH ) != 0 ) {
         refresh = true;
      }
   }
   
   /**
     * Plain Constructor
     */
   
   public OutputPanelEvent( Component c, int id ) {
      this( c, id, null );
   }
   
   public int getType() {
      return this.type;
   }
   
   /**
     * Resized
     */
   
   boolean isResizedEvent() {
      return resized;
   }
   
   /** returns panel in which the event occurred */
   
   Component getComponent() {
      return (Component)this.getSource();
   }
   
   /** If not initialized, or not a zoom event, returns NaN */
   
   double getYMax() {
      return yMax;
   }
   
   /** If not initialized, or not a zoom event, returns NaN */
   
   double getYMin() {
      return yMin;
   }
   
   /**
     * Switch output type methods
     */
   
   boolean isSwitchOutputEvent() {
      return switchOutput;
   }
   
   /**
     * Zoom event methods
     */
   
   boolean isZoomEvent() {
      return zoom;
   }
   
   /** If not initialized, or not a zoom event, returns NaN */
   
   double getXMax() {
      return xMax;
   }
   
   /** If not initialized, or not a zoom event, returns NaN */
   
   double getXMin() {
      return xMin;
   }
}
