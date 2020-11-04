package edu.umn.ecology.populus.core;

import edu.umn.ecology.populus.visual.RotatablePanel;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: Populus</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 *
 * <p>Company: University of Minnesota</p>
 *
 * @author Lars Roe
 * @version 1.0
 */
public class SuperFun extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 5823526538834551269L;
    JPanel panel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    RotatablePanel rp = new RotatablePanel();
    JLabel label = new JLabel();
    JPanel jPanel1 = new JPanel();
    JButton jButton1 = new JButton();
    JRadioButton jRadioButton1 = new JRadioButton() {
        /**
         *
         */
        private static final long serialVersionUID = 6902665182242017479L;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    };
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel1 = new JLabel();

    public SuperFun(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public SuperFun() {
        this(new Frame(), "SuperFun", false);
        this.setVisible(true);
    }

    private void jbInit() throws Exception {
        jButton1.setName("JBUTTON1");
        jPanel1.setName("JPANEL1");
        jPanel1.setBounds(new Rectangle(0, 0, 395, 177));
        jRadioButton1.setName("JRADIOBUTTON1");
        rp.setName("RP");
        this.setName("THIS");

        panel1.setLayout(borderLayout1);
        jButton1.setText("jButton1");
        jRadioButton1.setText("jRadioButton1");
        jPanel2.setLayout(borderLayout2);
        jLabel1.setText("jLabel1");
        jLabel1.setBounds(new Rectangle(0, 0, 34, 15));
        getContentPane().add(panel1);
        panel1.add(jPanel2, java.awt.BorderLayout.NORTH);
        //rp.add(jPanel1);
        rp.add(jLabel1);
        label.setText("<html><font color=red>RED</font></html>");
        rp.setContent(jPanel1);
        jPanel1.add(jRadioButton1);
        jPanel1.add(jButton1);
        jPanel2.add(rp, java.awt.BorderLayout.CENTER);
    }
}
