/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdtpr;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8027322160426817937L;
	static final Object[][] contents = new String[][] {
		{
			"Threshold_Predator", "Threshold Predator Reproduction"
		},  {
			"Discrete_Predator", "Discrete Predator-Prey (Threshold Predator Reproduction)"
		},  {
			"Model_Type", "Model Type"
		},  {
			"Model_Parameters", "Model Parameters"
		},  {
			"Initial_Conditions", "Initial Conditions"
		},  {
			"Axes", "Axes"
		},  {
			"Initial_Population", "Initial Population"
		},  {
			"Prey_Size_i_N_i_sub_0", "Prey Size (<i>N</i><sub>0</sub>)"
		},  {
			"K", "<i>K</i>"
		},  {
			"Number_of_generations", "Number of generations to plot"
		},  {
			"Generations_", "Generations:"
		},  {
			"Plot_Type", "Plot Type"
		},  {
			"Time_i_t_i_", "Time (<i> t </i>)"
		}
	};

	@Override
	public Object[][] getContents() {
		return contents;
	}
}
