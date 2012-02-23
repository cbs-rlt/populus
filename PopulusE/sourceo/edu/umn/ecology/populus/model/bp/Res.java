package edu.umn.ecology.populus.model.bp;
import java.util.*;
import java.io.Serializable;
import edu.umn.ecology.populus.constants.ColorScheme;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][]{
      { "Bacterial", "Population Biology of Bacterial Plasmids" },
      { "N_t_", "Population Density" + "\n<b><i>"+ColorScheme.getColorString( 0 ) + "  n<sub>+</sub> </font></i></b>, <b><i>" + ColorScheme.getColorString( 1 ) + "n<sub>*</sub> </font></i></b>, <b><i>" + ColorScheme.getColorString( 2 ) + " n </font></i></b>, <b><i>" + ColorScheme.getColorString( 3 ) + "n<sub>+</sub> + n<sub>*</sub> + n </font></i></b>" },
      {"frequency", "Frequency of Plasmid" + "<b><i>"+ColorScheme.getColorString( 0 ) +" (n<sub>+</sub> / N) <b><i>" +ColorScheme.getColorString( 1 ) +" (n<sub>*</sub> / N)"},
      {"plasmid-free","Plasmid-Free Cells (<b><i>n</i></b>)"},
      {"Plasmid-Bearing", "Plasmid-Bearing Donor Cells     ( <b><i> n</><sub>+</sub>)"},
      {"nstar", "Pasmid-Bearing Transconjugant Cells       ( <b><i>n</><sub>*</sub>)"},
	{ "Host", "Host" },
	{ "Parasite", "Parasite" },
	{ "Host_n_", "Host Density (<i>H</>)" },
	{ "Parasite_P_", "Parasite Population(<i>P</> )" },
	{ "Host_n_W", "Host Density ( <b><i>H</> )" },
	{ "Parasite_P_W", "Parasite Population( <b><i>P</> )" },
	{ "Time_b_i_t_", "Time ( <b><i>t</> )" },
	{ "Host_Population", "Population (   " },
	{ "totHostDens", "Host Density ( <b><i>n</> )" },
	{ "Infectious1", "Macroparasitic Diseases" },
	{ "Rate_of_population", "Instantaneous death rate of host due to the parasite" },
	{ "Anderson_May", "Equable" },
	{ "Parasite_Rate", "Parasite Rates" },
	{ "Host_Rate", "Host Rates" },
	{ "Model_Type", "Model Type" },
	{ "Model_Parameters", "Model Parameters" },
	{ "Initial_Conditions", "Initial Conditions" },
	{ "Initial_Densities", "Initial Densities" },
	{ "Graph_Type", "Graph Type" },
	{ "Init_Population", "Initial Populations" },
	{ "Parasite_Growth", "Parasite Growth" },
	{ "Host_Growth", "Host Growth" },
	{ "DAM", "Decoupled Anderson & May" },
	{ "DAM_parasite", "Decoupled A & M" }};


   public Object[][] getContents() {
      return contents;
   }
}
