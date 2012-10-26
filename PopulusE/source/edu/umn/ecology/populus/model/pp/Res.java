package edu.umn.ecology.populus.model.pp;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 729482635029562574L;
static final Object[][] contents = new String[][]{
	{ "Discrete_Predator", "Discrete Predator-Prey (Polyphagous Predators)" },
	{ "Polyphagous_Predators", "Polyphagous Predators" },
	{ "Model_Type", "Model Type" },
	{ "Model_Parameters", "Model Parameters" },
	{ "Initial_Conditions", "Initial Conditions" },
	{ "Plot_Type", "Plot Type" },
	{ "Initial_Population", "Initial Population" },
	{ "Prey_2_Size_i_Y_i_sub", "Prey 2 Size (<i>Y</i><sub>0</sub>)" },
	{ "Random", "Random" },
	{ "continuous", "continuous" },
	{ "Switching", "Switching" },
	{ "discrete", "discrete" },
	{ "Predator_Size_i_P_i", "Predator Size (<i>P</i><sub>0</sub>)" },
	{ "Number_of_generations", "Number of generations to plot" },
	{ "Generations_", "Generations:" },
	{ "Prey_1_Size_i_X_i_sub", "Prey 1 Size (<i>X</i><sub>0</sub>)" },
	{ "Time_i_t_i_", "Time (<i> t </i>)" }};
   
   public Object[][] getContents() {
      return contents;
   }
}
