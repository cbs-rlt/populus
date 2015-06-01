package edu.umn.ecology.populus.visual.stagegraph;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import edu.umn.ecology.populus.core.DesktopWindow;
/**
 * <p>Title: Stage Structured Growth</p>
 * <p>Description: a program for designing the stage structured growth for Populus</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Amos Anderson
 * @version 1.0
 */

public class ValueModifyFrame extends JDialog {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3663970312045684506L;
	private JLabel nameL = new JLabel();
   private JLabel valueL = new JLabel();
   private JTextField valueTF = new JTextField();
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   Label l;
   private JTextField nameTF = new JTextField();
   private JLabel nameConstL = new JLabel();
   private JButton okB = new JButton();
   private JLabel stageL = new JLabel();
   private JTextField stageTF = new JTextField();
   int type;

   public ValueModifyFrame(JPanel reference, Label l, int type) {
      super(DesktopWindow.defaultWindow);
      this.l = l;
      this.type = type;
      try {

         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      nameL.setText("Name:");
      if(l.getOwner() instanceof Transition){
         setTitle("Transition");
         valueL.setText("Transition probability:");
         nameConstL.setText(l.getName(true));
      } else {
         setTitle("Stage");
         valueL.setText("Initial Population:");
         nameTF.setText(l.getName(false));
      }

      valueTF.setText(""+l.v);
      if(l.getOwner() instanceof Stage){
         stageL.setText("Stage index:");
         stageTF.setText(""+((Stage)l.getOwner()).getShiftedIndex());
         if(type == StageStructuredPane.kASPG)
            stageTF.setEditable(false);
      }
      Point p = reference.getLocationOnScreen();
      setLocation((int)(p.getX()+reference.getWidth()/2-this.getWidth()/4),
                  (int)(p.getY()+reference.getHeight()/2-this.getHeight()/4));
      this.setModal(true);
      this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      pack();
      validate();
   }

   public ValueModifyFrame() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      this.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowDeactivated(WindowEvent e) {
            this_windowDeactivated(e);
         }
      });
      this.getContentPane().setLayout(gridBagLayout1);
      okB.setText("OK");
      okB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            okB_actionPerformed(e);
         }
      });
      if(type != StageStructuredPane.kASPG)
      this.getContentPane().add(nameL,     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
      this.getContentPane().add(valueL,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
      this.getContentPane().add(valueTF,     new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
      if(l.getOwner() instanceof Transition){
         this.getContentPane().add(nameConstL,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
      } else {
         if(type != StageStructuredPane.kASPG)
         this.getContentPane().add(nameTF,    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
         this.getContentPane().add(stageL,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
         this.getContentPane().add(stageTF,    new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
               ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
      }
      int s = type == StageStructuredPane.kASPG ? 2 : 0;
      this.getContentPane().add(okB,  new GridBagConstraints(0, getContentPane().getComponentCount()+s, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0));
      setSize(new Dimension(300, 240));
   }

   void this_windowDeactivated(WindowEvent e) {
      setVisible(false);
      super.remove(this);
   }

   void okB_actionPerformed(ActionEvent e) {
      double d=0;
      int i = 0;
      boolean dOk = true;
      try{
         d = Double.parseDouble(valueTF.getText());
      } catch (Exception ex){
         dOk = false;
      }
      setVisible(false);
      if(dOk) l.v = d;
      dOk = true;
      if(l.getOwner() instanceof Stage){
         try{
            i = Integer.parseInt(stageTF.getText());
         } catch (Exception ex){
            dOk = false;
         }
         if(dOk && type != StageStructuredPane.kASPG) ((Stage)l.getOwner()).setIndex(i-1);
         l.setName(nameTF.getText());
      }
   }
}