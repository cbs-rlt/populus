package edu.umn.ecology.populus.model.aidst;

import javax.swing.*;
import java.awt.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.border.*;

/**
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2005, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Lars Roe
 * @version 5.4
 */

public class AIDSTInterval extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -9182934658867998210L;
	private GridBagLayout gridBagLayout7 = new GridBagLayout();
	private PopulusParameterField paramt2 = new PopulusParameterField();
	private PopulusParameterField paramt1 = new PopulusParameterField();
	private Border border2;
	private TitledBorder titledBorder2;
	String borderString = "Treatment Interval ";

	public AIDSTInterval() {
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		border2 = BorderFactory.createLineBorder(SystemColor.controlText,2);
		titledBorder2 = new TitledBorder(border2,borderString);
		this.setLayout(gridBagLayout7);
		paramt2.setCurrentValue(200.0);
		paramt2.setDefaultValue(200.0);
		paramt2.setMaxValue(400.0);
		paramt2.setParameterName("End ");
		paramt1.setCurrentValue(50.0);
		paramt1.setDefaultValue(50.0);
		paramt1.setMaxValue(400.0);
		paramt1.setParameterName("Begin ");
		this.setBorder(titledBorder2);
		this.add(paramt1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		this.add(paramt2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
	}

	public void setNumber(int n) {
		titledBorder2.setTitle(borderString + n);
	}

	public void setValues(double t1, double t2) {
		paramt1.setCurrentValue(t1);
		paramt2.setCurrentValue(t2);
	}

	public double[] getValues() {
		double[] pair = new double[2];
		pair[0] = paramt1.getCurrentValue();
		pair[1] = paramt2.getCurrentValue();
		return pair;
	}

}
