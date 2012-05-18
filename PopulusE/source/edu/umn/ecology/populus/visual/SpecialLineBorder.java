package edu.umn.ecology.populus.visual;

import javax.swing.border.LineBorder;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Component;

/**
 * this is needed in the model fds:ess because it draws some borders right next to each other
 * creating, in effect, a border thickness 2. this extension of the LineBorder class prevents that
 * by making as an option the drawing of the borders on each of the sides.
 * the whole "rounded border" thing was eliminated because i don't want to figure that out when it
 * probably isn't needed.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 5.2
 */
public class SpecialLineBorder extends LineBorder {
   private boolean drawTop    = true;
   private boolean drawBottom = true;
   private boolean drawLeft   = true;
   private boolean drawRight  = true;
   public boolean upperRight = false;
   public boolean upperLeft  = false;
   public boolean lowerRight = false;
   public boolean lowerLeft  = false;
   public double fraction = 0.2;

   public SpecialLineBorder(Color c){
      super(c);
   }
   public SpecialLineBorder(Color c, int thickness){
      super(c,thickness);
   }

   public SpecialLineBorder(Color c, boolean drawTop, boolean drawBottom, boolean drawLeft, boolean drawRight, int thickness){
      this(c,thickness);
      this.drawTop = drawTop;
      this.drawBottom = drawBottom;
      this.drawLeft = drawLeft;
      this.drawRight = drawRight;
   }

   public SpecialLineBorder(Color c, boolean drawTop, boolean drawBottom, boolean drawLeft, boolean drawRight){
      super(c);
      this.drawTop = drawTop;
      this.drawBottom = drawBottom;
      this.drawLeft = drawLeft;
      this.drawRight = drawRight;
   }

   public void setEdge(boolean drawTop, boolean drawBottom, boolean drawLeft, boolean drawRight){
      this.drawTop = drawTop;
      this.drawBottom = drawBottom;
      this.drawLeft = drawLeft;
      this.drawRight = drawRight;
   }

   public void setCorner(boolean upperLeft, boolean upperRight, boolean lowerLeft, boolean lowerRight){
      this.upperLeft = upperLeft;
      this.upperRight = upperRight;
      this.lowerRight = lowerRight;
      this.lowerLeft = lowerLeft;
   }

   public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
      Color oldColor = g.getColor();
      g.setColor(lineColor);
      for(int i = 0; i < thickness; i++){
         //g.drawRect(x+i, y+i, width-i-i-1, height-i-i-1);
         if(drawTop)    g.drawLine(x+i,y+i,x+width-i-1,y+i);
         if(drawBottom) g.drawLine(x+i,y+height-i-1,x+width-i-1,y+height-i-1);
         if(drawRight)  g.drawLine(x+width-i-1,y,x+width-i-1,y+height-1);
         if(drawLeft)   g.drawLine(x+i,y,x+i,y+height-1);

         //these are for drawing corners
         if(upperLeft)    g.drawLine(x+i,y+i,x+(int)(width*fraction+0.5)-1,y+i);
         if(upperRight)   g.drawLine(x+(int)(width*(1-fraction)+0.5),y+i,x+width-i-1,y+i);
         if(lowerLeft)    g.drawLine(x+i,y+height-i-1,x+(int)(width*fraction+0.5)-1,y+height-i-1);
         if(lowerRight)   g.drawLine(x+(int)(width*(1-fraction)),y+height-i-1,x+width-i-1,y+height-i-1);
      }
      g.setColor(oldColor);
   }
}
