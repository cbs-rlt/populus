package edu.umn.ecology.populus.model.aidst;

import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.edwin.ModelPanel;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import com.borland.jbcl.layout.VerticalFlowLayout;


/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: University of Minnesota</p>
 * @author Don Alstad
 * @version 5.4
 */

public class AIDSTIntervalArray extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3245040664068263100L;
private Border border1;
   private TitledBorder titledBorder1;
   private JScrollPane scrollPane = new JScrollPane();
   private JButton delButton = new JButton();
   private JPanel mainPanel = new JPanel();
   private JPanel intervalsPane = new JPanel();
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private JButton addButton = new JButton();
   private Stack intervals = new Stack();
   private int enabledIntervals = 0;
   public static final int kMaxIntervals = 3;
   public static final int kMinIntervals = 0;
   private GridLayout gridLayout2 = new GridLayout();
   private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

   public AIDSTIntervalArray() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder(SystemColor.controlText,1);
      titledBorder1 = new TitledBorder(border1,"Therapy Intervals");
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      scrollPane.setBorder(titledBorder1);
      delButton.setText("delete");
      delButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            delButton_actionPerformed(e);
         }
      });
      mainPanel.setLayout(gridBagLayout1);
      addButton.setText("add");
      addButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            addButton_actionPerformed(e);
         }
      });
      intervalsPane.setLayout(verticalFlowLayout1);
      this.setLayout(gridLayout2);
      gridLayout2.setColumns(1);
      this.add(scrollPane, null);
      scrollPane.getViewport().add(mainPanel, null);
      mainPanel.add(intervalsPane,    new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      mainPanel.add(delButton,      new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      mainPanel.add(addButton,     new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
      addInterval(50.0, 200.0);
      addInterval(220.0, 250.0);
      addInterval(280.0, 320.0);
      this.revalidate();
   }

   void addButton_actionPerformed(ActionEvent e) {
      double start, finish;
      if (!intervals.isEmpty()) {
         AIDSTInterval interval = (AIDSTInterval) intervals.lastElement();
         start = interval.getValues()[1] + 20.0;
         finish = start + 20.0;
      }
      else {
         start = 50.0;
         finish = 200.0;
      }
      addInterval(start, finish);
   }

   void addInterval(double start, double end) {
      AIDSTInterval interval = new AIDSTInterval();
      interval.setValues(start, end);
      intervals.push(interval);
      intervalsPane.add(interval);
      enabledIntervals++;
      this.delButton.setEnabled(true);
      if (enabledIntervals >= kMaxIntervals)
         this.addButton.setEnabled(false);
      this.revalidate();
   }

/*
   void enableInterval() {
      AIDSTInterval interval = (AIDSTInterval) intervals.elementAt(++lastInterval);
      interval.enable();
   }
*/

   void removeInterval() {
      AIDSTInterval interval = (AIDSTInterval) intervals.pop();
      intervalsPane.remove(interval);
      enabledIntervals--;
      this.addButton.setEnabled(true);
      if (enabledIntervals <= kMinIntervals)
         this.delButton.setEnabled(false);
      this.revalidate();
   }

   void delButton_actionPerformed(ActionEvent e) {
      removeInterval();
   }


   public double[][] getIntervals() {
      int size = intervals.size();
      double[][] vals = new double[intervals.size()][];
      for (int cnt = 0; cnt < size; cnt++) {
         AIDSTInterval interval = (AIDSTInterval) intervals.elementAt(cnt);
         vals[cnt] = interval.getValues();
      }
      return vals;
   }

   void setModelPanel(ModelPanel c) {
   }

}