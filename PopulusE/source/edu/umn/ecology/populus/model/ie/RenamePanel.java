package edu.umn.ecology.populus.model.ie;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import edu.umn.ecology.populus.core.DesktopWindow;

public class RenamePanel extends JDialog {
   private JButton okB = new JButton();
   private ConversionPanel[] cpA;
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private JButton cancelB = new JButton();
   private boolean changeGood = false;

   class ConversionPanel extends JPanel{
      private JLabel fromL = new JLabel();
      private JLabel descL = new JLabel();
      private JTextField toTF = new JTextField();
      private GridBagLayout gbl = new GridBagLayout();

      ConversionPanel(String from){
         setLayout(gbl);
         fromL.setText(from);
         descL.setText("becomes");
         toTF.setText(from);
         add(fromL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
         add(descL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
         add(toTF, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
         toTF.setToolTipText("Select a new name for variable "+from);
         toTF.setPreferredSize(new Dimension(50,21));
      }

      String getTo(){
         return toTF.getText();
      }

      String getFrom(){
         return fromL.getText();
      }
   }

   public String[] getMappings(){
      if(!changeGood) return null;
      String[] sA = new String[cpA.length];
      String name;
      for(int i=0; i<cpA.length; i++){
         name = cpA[i].getTo().toLowerCase();
         if(name.length() == 0 || name.charAt(0) < 'a' || name.charAt(0) > 'z')
            sA[i] = cpA[i].getFrom();
         else
            sA[i] = cpA[i].getTo();
      }
      return sA;
   }

   public RenamePanel(String[] paramArray) {
      super(DesktopWindow.defaultWindow,"Rename",true);

      cpA = new ConversionPanel[paramArray.length];
      for(int i=0; i<cpA.length; i++){
         cpA[i] = new ConversionPanel(paramArray[i]);
      }

      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      setVisible(true);
   }

   private void jbInit() throws Exception {
      okB.setText("OK");
      okB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            finalizeB_actionPerformed(e);
         }
      });
      this.getContentPane().setLayout(gridBagLayout1);
      int i=0;
      for(; i<cpA.length; i++)
         this.getContentPane().add(cpA[i], new GridBagConstraints(0, i, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      cancelB.setText("Cancel");
      cancelB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            cancelB_actionPerformed(e);
         }
      });
      this.getContentPane().add(okB, new GridBagConstraints(0, ++i, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      this.getContentPane().add(cancelB, new GridBagConstraints(1, i, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

      pack();

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = getSize();
      if (frameSize.height > screenSize.height) {
         frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
         frameSize.width = screenSize.width;
      }
      setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
   }

   void finalizeB_actionPerformed(ActionEvent e) {
      changeGood = true;
      dispose();
   }

   void cancelB_actionPerformed(ActionEvent e) {
      dispose();
   }
}