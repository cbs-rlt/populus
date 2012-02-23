package edu.umn.ecology.populus.model.appdnrs;
import java.util.*;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][]{
	{ "Non_Random_Searching", "Non-Random Searching" },
	{ "Discrete_Predator", "Discrete Predator-Prey (Non-Random Searching)" },
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
	{ "Generations_", "Generations:" },
	{ "Time_i_t_i_", "Time (<i> t </i>)" }};
   
   public Object[][] getContents() {
      return contents;
   }
}
