package edu.umn.ecology.populus.model.dig;
import java.util.*;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][] {
       {
         "Density_Independent", "Density-Independent Growth"
      },  {
         "Pop_Size_i_N_i_0_", "Pop Size (<i>N</i>(0))"
      },  {
         "Pop_Size_i_N_i_sub_0", "Pop Size (<i>N</i><sub>0</sub>)"
      },  {
         "Model_Type", "Model Type"
      },  {
         "Model_Parameters", "Model Parameters"
      },  {
         "Initial_Conditions", "Initial Conditions"
      },  {
         "Plot_Type", "Plot Type"
      },  {
         "Which_to_Graph", "Which to Graph"
      },  {
         "Continuous", "Continuous"
      },  {
         "Discrete", "Discrete"
      },  {
         "Number_of_generations", "Number of generations to plot"
      },  {
         "Run_Time", "Run Time"
      },  {
         "Rate_of_population", "Rate of population growth.\nPopulation at time t + 1 is equal to "
      },  {
         "the_npopulation_at", "the\npopulation at time t times this parameter"
      },  {
         "Rate_of_population1", "Rate of population growth.\nr is such that at any time p,\nPopulation "
      },  {
         "is_e_r_t_initial", "is e^(r * t) * initial population"
      },  {
         "Time_b_i_t_", "Time ( <b><i>t</> )"
      },  {
         "Population_b_i_N_", "Population ( <b><i>N</> )"
      },  {
         "Population_b_i_N_sub", "Population ( <b><i>N<sub>t</sub></> )"
      },  {
         "ln_Population_ln_i_b", "ln Population ( ln {<i><b>N</b></>} )"
      },  {
         "Continuous1", "Continuous Exponential Growth"
      },  {
         "Discrete_Geometric", "Discrete Geometric Growth"
      },  {
         "Rate_of_population2", "Rate of population growth.\nPopulation at time t + 1 is equal to "
      },  {
         "the_npopulation_at1", "the\npopulation at time t times this parameter"
      },  {
         "Rate_of_population3", "Rate of population growth.\nr is such that at any time p,\nPopulation "
      },  {
         "Rate_of_population4", "Rate of population growth.\nPopulation at time t + 1 is equal to "
      },  {
         "the_npopulation_at2", "the\npopulation at time t times this parameter"
      },  {
         "Rate_of_population5", "Rate of population growth.\nr is such that at any time p,\nPopulation "
      },  {
         "Rate_of_population6", "Rate of population growth.\nPopulation at time t + 1 is equal to "
      },  {
         "the_npopulation_at3", "the\npopulation at time t times this parameter"
      },  {
         "Rate_of_population7", "Rate of population growth.\nr is such that at any time p,\nPopulation "
      },  {
         "Initial_Population", "Initial Population"
      },  {
         "Graph_Model_A", "Graph Model A"
      },  {
         "Overlay_Model_B", "Overlay Model B"
      },  {
         "Overlay_Model_C", "Overlay Model C"
      },  {
         "Overlay_Model_D", "Overlay Model D"
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
