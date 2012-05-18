package edu.umn.ecology.populus.plot.coloredcells;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

class CellEditPanel extends JFrame{
	JLabel curL = new JLabel();
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	JLabel curvalL = new JLabel();
	JLabel newL = new JLabel();
	JLabel locL = new JLabel();
	JLabel locvalL = new JLabel();
	JTextField newvalTF = new JTextField();
	String loc,curval;
	CellPanel cp;
	int r,c;

	public CellEditPanel(CellPanel reference,int r, int c, double curVal) {
		ImageIcon ii = new ImageIcon("picon.gif");
		setIconImage(ii.getImage());
		setTitle("Edit Cell:");
		this.r = r;
		this.c = c;
		loc = r+","+c;
		curval = String.valueOf(curVal);
		this.cp = reference;
		try  {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
      Point p = reference.getLocationOnScreen();
      setLocation((int)(p.getX()+reference.getWidth()/2-this.getWidth()/2),
                  (int)(p.getY()+reference.getHeight()/2-this.getHeight()/2));
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(gridBagLayout1);
		curL.setText("Current Value:");
		newL.setText("New Value:");
		locL.setText("Location:");
		curvalL.setText(curval);
		locvalL.setText(loc);
		newvalTF.setPreferredSize(new Dimension(70, 21));
		newvalTF.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				newvalTF_actionPerformed(e);
			}
		});
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowDeactivated(WindowEvent e) {
				this_windowDeactivated(e);
			}
		});
		this.getContentPane().add(curL, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		this.getContentPane().add(curvalL, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(newL, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		this.getContentPane().add(locL, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
		this.getContentPane().add(locvalL, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.getContentPane().add(newvalTF, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
						,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		this.setResizable(false);
		validate();
		pack();
	}

	void newvalTF_actionPerformed(ActionEvent e) {
		double d=0;
		try{
			d = Double.parseDouble(newvalTF.getText());
		} catch (Exception ex){
			setVisible(false);
			super.remove(this);
		}
		setVisible(false);
		cp.modifyValue(r,c,d);
	}

	void this_windowDeactivated(WindowEvent e) {
      setVisible(false);
		super.remove(this);
	}
}
