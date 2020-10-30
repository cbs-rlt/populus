/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import edu.umn.ecology.populus.visual.HTMLLabel;

public class Equation extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -6600287433471621366L;
	JCheckBox isPlottedCB = new JCheckBox();
	JTextField equationTF = new JTextField();
	HTMLLabel dNdT = new HTMLLabel();
	HTMLLabel N0TF = new HTMLLabel();
	int index;
	JTextField initialValue = new JTextField();
	JCheckBox isUsedCB = new JCheckBox();
	FlowLayout flowLayout1 = new FlowLayout();
	String parameterName;
	boolean isDiscrete;

	public Equation() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public Equation( boolean used, boolean plotted, boolean isDiscrete, int eqIndex, double initial, String eq ) {
		this();
		this.index = eqIndex;
		parameterName = "N"+eqIndex;
		this.isDiscrete = isDiscrete;
		setType();
		isUsedCB.setSelected( used );
		isPlottedCB.setSelected( used );
		equationTF.setText( eq );
		initialValue.setToolTipText( "The initial value for N" + index );
		if( eqIndex <= 3 ) {
			isPlottedCB.setSelected( true );
		}
		initialValue.setText( String.valueOf( (int)initial ) );
	}

	public void setParameterName(String newName){
		parameterName = newName;
		setType();
	}

	public String getParameterName(){
		return parameterName;
	}

	public void setEquation(String newEquation){
		equationTF.setText(newEquation);
	}

	public double getInitialValue() {
		double v;
		try {
			v = Double.parseDouble( initialValue.getText() );
		}
		catch( NumberFormatException nfe ) {
			edu.umn.ecology.populus.fileio.Logging.log( "Initial value not a number." );
			return 0;
		}
		return v;
	}

	public boolean isUsed() {
		return isUsedCB.isSelected();
	}

	public boolean isPlotted() {
		return isPlottedCB.isSelected();
	}

	public String getEquation() {
		return equationTF.getText();
	}

	public void setType(boolean isDiscrete){
		this.isDiscrete = isDiscrete;
		setType();
	}

	public void setType() {
		if( isDiscrete ) {
			N0TF.setText( "<i>" + parameterName + " </i><sub>0</>" );
			dNdT.setText( "<i>" + parameterName + " </i><sub>t+1</> =" );
		}
		else {
			N0TF.setText( "<i>" + parameterName + "</i>(0)" );
			dNdT.setText( "<i>d" + parameterName + "/dt</i> =" );
		}
	}

	void isPlottedCB_stateChanged( ChangeEvent e ) {
		if( isPlottedCB.isSelected() ) {
			isUsedCB.setSelected( true );
			isUsedCB.setEnabled( false );
		}
		else {
			isUsedCB.setEnabled( true );
		}
	}

	private void jbInit() throws Exception {
		this.setLayout( flowLayout1 );
		equationTF.setPreferredSize( new Dimension( 200, 21 ) );
		isPlottedCB.addChangeListener( new javax.swing.event.ChangeListener()  {

			@Override
			public void stateChanged( ChangeEvent e ) {
				isPlottedCB_stateChanged( e );
			}
		} );
		initialValue.setPreferredSize( new Dimension( 30, 21 ) );
		initialValue.setToolTipText( "Enter the number of equations in the table" );
		initialValue.setHorizontalAlignment(SwingConstants.CENTER);
		isUsedCB.setToolTipText( "Use this equation" );
		isPlottedCB.setToolTipText( "Plot this equation" );
		isUsedCB.setSelected( true );
		this.add( isUsedCB, null );
		this.add( isPlottedCB, null );
		this.add( N0TF, null );
		this.add( initialValue, null );
		this.add( dNdT, null );
		this.add( equationTF, null );
	}
}
