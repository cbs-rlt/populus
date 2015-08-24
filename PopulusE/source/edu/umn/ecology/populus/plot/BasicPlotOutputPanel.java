/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.constants.OutputTypes;
import edu.umn.ecology.populus.resultwindow.*;

import java.awt.*;

public class BasicPlotOutputPanel extends OutputPanel {

   /**
	 * 
	 */
	private static final long serialVersionUID = -1467334882548355207L;
//Package access for BasicPlotModel to access.
   BasicPlotCanvas bpc;

   public BasicPlotCanvas getBPC() {
      //used in simpleUpdateOutput, so we want to update the colors in the process
      //This is also required since it refreshes the captions
      bpc.setBackground( ColorScheme.bG );
      this.setBackground( ColorScheme.bG );
      return bpc;
   }

   /** Functionality of this method is passed to the BasicPlotCanvas which holds the
     chart to be modified.*/

   public void showOptions( int whatOption ) {
      bpc.displayChartOptionScreen( whatOption );
   }

   public BasicPlotOutputPanel( BasicPlotInfo bpi ) {
      this.setBackground( ColorScheme.bG );
      setType(OutputTypes.k2D);
      //this.setDoubleBuffered(true);
      setLayout( new GridLayout( 1, 1 ) );
      this.removeAll();
      bpc = new BasicPlotCanvas( bpi );
      bpc.setBackground( ColorScheme.bG );
      this.add( bpc );
   }
}
