package edu.umn.ecology.populus.visual.stagegraph;

import java.awt.*;

/**
 * This interface controls what all the displayed elements in StageStructuredPane need to show and do
 *
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */
interface StageShape {
   /**if active, the color will change. essentially, i just lets the user know which element
    the mouse is on.*/
   public void setActive(boolean b);
   public void paint(Graphics g);
   /**is the point within the chosen bounds for the StageShape?*/
   public boolean contains(double x, double y);
   /**each StageShape has a Label associated with it. if the StageShape is a Label itself, <code>this</code> is returned*/
   public Label getLabel();
   /**this requests the value-dependent portion of the title. if true, then we want the %n's turned into stage indicies*/
   public String getName(boolean b);
   /**set the text including tags*/
   public void setName(String s);
   /**the value-independent portion of the title*/
   public String getNamePrefix();
   /**set the text including tags*/
   public void setNamePrefix(String s);
}
