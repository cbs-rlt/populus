package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.constants.OutputTypes;
import edu.umn.ecology.populus.resultwindow.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class BasicPlotOutputPanel extends OutputPanel {

   //Package access for BasicPlotModel to access.
   BasicPlotCanvas bpc;

   public BasicPlotCanvas getBPC() {

      //used in simpleUpdateOutput, so we want to update the colors in the process
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
