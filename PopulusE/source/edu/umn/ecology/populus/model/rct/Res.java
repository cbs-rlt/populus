package edu.umn.ecology.populus.model.rct;
import java.util.*;
import java.io.Serializable;
import edu.umn.ecology.populus.constants.ColorScheme;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][]{
      {"Resource_competition", "Resource Competition (Tilman) "},
      { "Resource", "Tilman" },
      {"Resource1", "Resource (r<sub>1</sub>)"},
      { "Time_b_i_t_", "Time ( <b><i>t</> )" },


      {"Concentration", "Concentration of Resource (<i>r</i><sub>1</sub>)"},
      {"Concentration2", "Concentration of Resource (<i>r</i><sub>2</sub>)"},
      {"frequency2", "Frequency of Species" + "<b><i>"+ColorScheme.getColorString( 0 ) +" #1 <b><i>" +ColorScheme.getColorString( 1 ) +"#2"},
      {"frequency1", "Frequency of Species" + "<b><i>"+ColorScheme.getColorString( 0 ) +" #1"},
      {"Rate_of_Uptake1" ,"Rate of Uptake ("+ "<b><i>"+ColorScheme.getColorString( 0 ) +" r<sub>11 </>" +")"},
      {"Rate_of_Uptake2" ,"Rate of Uptake ("+ "<b><i>"+ColorScheme.getColorString( 0 ) +" r<sub>11</sub>, "+ "<b><i>"+ColorScheme.getColorString( 1 ) +" r<sub>21</>"+ " )" },
      {"Rate_of_Uptake3" ,"Rate of Uptake ("+ "<b><i>"+ColorScheme.getColorString( 0 ) +" r<sub>12</sub>, "+ "<b><i>"+ColorScheme.getColorString(1 ) +" r<sub>22</>"+ " )" },

      { "N_t_", "Population Density" + "\n<b><i>"+ColorScheme.getColorString( 0 ) + "  n<sub>+</sub> </font></i></b>, <b><i>" + ColorScheme.getColorString( 1 ) + "n<sub>*</sub> </font></i></b>, <b><i>" + ColorScheme.getColorString( 2 ) + " n </font></i></b>, <b><i>" + ColorScheme.getColorString( 3 ) + "n<sub>+</sub> + n<sub>*</sub> + n </font></i></b>" },

      {"plasmid-free","Plasmid-Free Cells (<b><i>n</i></b>)"},
      {"Plasmid-Bearing", "Plasmid-Bearing Donor Cells     ( <b><i> n</><sub>+</sub>)"},
      {"nstar", "Pasmid-Bearing Transconjugant Cells       ( <b><i>n</><sub>*</sub>)"},
      { "Host", "Host" },
      { "Parasite", "Parasite" },
      { "Host_n_", "Host Density (<i>H</>)" },
      { "Parasite_P_", "Parasite Population(<i>P</> )" },
      { "Host_n_W", "Host Density ( <b><i>H</> )" },
      { "Parasite_P_W", "Parasite Population( <b><i>P</> )" },

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
      { "DAM_parasite", "Decoupled A & M" },

   	  { "CapSpecies1", "Species 1 (" + ColorScheme.getColorString( 0 ) + "<i>N<sub>1</sub></i></font>)"},
   	  { "CapSpecies2", "Species 2 (" + ColorScheme.getColorString( 1 ) + "<i>N<sub>2</sub></i></font>)"},
   	  { "CapSpecies3", "Species 3 (" + ColorScheme.getColorString( 2 ) + "<i>N<sub>3</sub></i></font>)"},
  	  { "CapResource1", "Resource 1 (" + ColorScheme.getColorString( 0 ) + "<i>R<sub>1</sub></i></font>)"},
   	  { "CapResource2", "Resource 2 (" + ColorScheme.getColorString( 1 ) + "<i>R<sub>2</sub></i></font>)"},
   	  { "CapResource3", "Resource 3 (" + ColorScheme.getColorString( 2 ) + "<i>R<sub>3</sub></i></font>)"}};


      public Object[][] getContents() {
         return contents;
      }
}