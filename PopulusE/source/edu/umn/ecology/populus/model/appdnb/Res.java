/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.appdnb;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 7301279375938620473L;
static final Object[][] contents = new String[][]{
	{ "Nicholson_Bailey", "Nicholson-Bailey" },
	{ "Discrete_Predator", "Discrete Predator-Prey (Nicholson-Bailey)" },
	{ "Model_Type", "Model Type" },
	{ "Model_Parameters", "Model Parameters" },
	{ "Initial_Conditions", "Initial Conditions" },
	{ "Plot_Type", "Plot Type" },
	{ "Initial_Population", "Initial Population" },
	{ "Prey_Size_i_N_i_sub_0", "Prey Size (<i>N</i><sub>0</sub>)" },
	{ "Density_Dependent", "Density-Dependent Prey" },
	{ "continuous", "continuous" },
	{ "Density_Independent", "Density-Independent Prey" },
	{ "discrete", "discrete" },
	{ "Predator_Size_i_P_i", "Predator Size (<i>P</i><sub>0</sub>)" },
	{ "Number_of_generations", "Number of generations to plot" },
	{ "Run_Time_", "Run Time:" },
	{ "Time_i_t_i_", "Time (<i> t </i>)" }};
   
   public Object[][] getContents() {
      return contents;
   }
}
