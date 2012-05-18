package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.resultwindow.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.definetti.*;
import java.awt.event.*;
import edu.umn.ecology.populus.constants.*;
import java.lang.Thread;
import edu.umn.ecology.populus.core.PreferencesDialog;

/* The OutputPanel for 3D graphs.*/
public class BasicPlotDeFinettiPanel extends OutputPanel {
   DeFinettiGraph dfg;
   GridBagLayout gridBagLayout1 = new GridBagLayout();

   public BasicPlotDeFinettiPanel(BasicPlotInfo info){
      this();
      dfg = new DeFinettiGraph(info);
      updateData(info);
   }

   public BasicPlotDeFinettiPanel(){
      setType(OutputTypes.kDeFi);
      removeAll();
      super.setBackground(Color.white);
      setBackground(Color.white);
      this.setLayout(gridBagLayout1);
   }

   public void updateData(BasicPlotInfo bpi) {
      if(dfg == null){
         dfg = new DeFinettiGraph(bpi.getData());
      } else {
         dfg.setData(bpi.getData());
      }

      dfg.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

      this.add(dfg,new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,
         GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

      /*all we have to do is create the LiveGraph class, and it will handle the painting*/
      if(bpi.isLive()){
         new LiveGraph(dfg,10,(int) edu.umn.ecology.populus.core.PopPreferences.getDelayTime());
      } else
         repaint();
   }

   /** The popup menu on the output screen accesses this function to dynamically modify
    the graph. go to edu.umn.ecology.populus.core.ModelOutputFrame for modifying the actual menu*/
   public void showOptions(int whatOption){
      switch (whatOption) {
         case MenuOptions.kCourserGrid:
            if(dfg.getGridVisible())
               dfg.setGridFactor(dfg.getGridFactor()/2.0);
            else
               dfg.setGridVisible(true);
            break;
         case MenuOptions.kFinerGrid:
            if(dfg.getGridVisible())
               dfg.setGridFactor(dfg.getGridFactor()*2.0);
            else
               dfg.setGridVisible(true);
            break;
         case MenuOptions.kClearGrid:
            dfg.setGridVisible(false);
            break;
         case MenuOptions.kOptionScreen:
            break;
         case MenuOptions.kReset:
            dfg.resetGraph();
            break;
         case MenuOptions.kZoomIn:
            dfg.setZoomFactor(dfg.getZoomFactor()*1.1);
            break;
         case MenuOptions.kZoomOut:
            dfg.setZoomFactor(dfg.getZoomFactor()/1.1);
            break;
         case MenuOptions.kSqueezeLabels:
            break;
      }
      dfg.repaint();
   }
}
