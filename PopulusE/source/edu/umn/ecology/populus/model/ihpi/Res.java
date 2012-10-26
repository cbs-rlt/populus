package edu.umn.ecology.populus.model.ihpi;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 2907224542515811379L;
static final Object[][] contents = new String[][]{
	{ "Host_Parasitoid_with", "Host-Parasitoid with Insecticide" },
	{ "Discrete_Predator", "Discrete Predator-Prey (Host-Parasitoid with Insecticide)" },
	{ "Model_Type", "Model Type" },
	{ "Model_Parameters", "Model Parameters" },
	{ "Initial_Conditions", "Initial Conditions" },
	{ "Axes", "Axes" },
	{ "Initial_Population", "Initial Population" },
	{ "Prey_Size_i_N_i_sub_0", "Prey Size (<i>N</i><sub>0</sub>)" },
	{ "Type_III", "Type III" },
	{ "Type_I", "Type I" },
	{ "Predator_Size_i_P_i", "Predator Size (<i>P</i><sub>0</sub>)" },
	{ "Number_of_generations", "Number of generations to plot" },
	{ "Generations_", "Generations:" },
	{ "Type_II", "Type II" },
	{ "Plot_Type", "Plot Type" },
	{ "Time_i_t_i_", "Time (<i> t </i>)" }};
   
   public Object[][] getContents() {
      return contents;
   }
}
