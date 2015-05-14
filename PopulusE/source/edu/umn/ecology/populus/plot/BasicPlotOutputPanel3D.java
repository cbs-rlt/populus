package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.resultwindow.*;
import java.awt.*;
import edu.umn.ecology.populus.plot.threedgrapher.*;
import edu.umn.ecology.populus.constants.*;

/* The OutputPanel for 3D graphs.*/
public class BasicPlotOutputPanel3D extends OutputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8686546235450404726L;
	ThreeD td;
   GridBagLayout gridBagLayout1 = new GridBagLayout();

   public BasicPlotOutputPanel3D(BasicPlotInfo info){
      this();
      updateData(info);
   }

   public BasicPlotOutputPanel3D(){
      setType(OutputTypes.k3D);
      removeAll();
      super.setBackground(Color.white);
      setBackground(Color.white);
      this.setLayout(gridBagLayout1);
   }

   public void updateData(BasicPlotInfo bpi) {
      if(td == null){
         td = new ThreeD(bpi.getData());
      } else {
         td.setData(bpi.getData());
      }
      td.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
      td.setXLabel(bpi.getXCaption());
      td.setYLabel(bpi.getYCaption());
      td.setZLabel(bpi.getZCaption());
      td.setMainLabel(bpi.getMainCaption());
      td.setLabelT(bpi.isLabelsT());
      td.setIsDiscrete(bpi.isDiscrete(),0);
      td.setZIsDiscrete(bpi.isZDiscrete());
      td.setXIsDiscrete(bpi.isXDiscrete());

      if(bpi.xMaxSet)
         td.setXMax((float)bpi.getXMax());
      if(bpi.yMaxSet)
         td.setYMax((float)bpi.getYMax());
      if(bpi.zMaxSet)
         td.setZMax((float)bpi.getZMax());

      td.reset();
      td.run();
      this.add(td,new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,
         GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

      /*all we have to do is create the LiveGraph class, and it will handle the painting*/
      if(bpi.isLive()){
         td.setMultiColored(true);
         new LiveGraph(td,20,(int) edu.umn.ecology.populus.core.PopPreferences.getDelayTime());
      } else
         repaint();
   }

   /** The popup menu on the output screen accesses this function to dynamically modify
         the graph. go to edu.umn.ecology.populus.core.ModelOutputFrame for modifying the actual menu*/
   public void showOptions(int whatOption){
      switch (whatOption) {
         case MenuOptions.kCoarserGrid:
                     td.setGridVisible(true);
                     td.setNumGridLines(td.getNumGridLines()/2);
                     break;
         case MenuOptions.kFinerGrid:
                     td.setGridVisible(true);
                     td.setNumGridLines(td.getNumGridLines()*2);
                     break;
         case MenuOptions.kClearGrid:
                     td.setGridVisible(false);
                     break;
         case MenuOptions.kOptionScreen:
                     break;
         case MenuOptions.kReset:
                     td.reset();
                     break;
         case MenuOptions.kZoomIn:
                     td.setScaleFudge(td.getScaleFudge()*1.2f);
                     break;
         case MenuOptions.kZoomOut:
                     td.setScaleFudge(td.getScaleFudge()/1.2f);
                     break;
         case MenuOptions.kSqueezeLabels:
                     td.setSqueezeLabels(!td.getSqueezeLabels());
                     break;
      }
   }
}
