package edu.umn.ecology.populus.model.prm;
import java.util.*;
import java.io.Serializable;
import edu.umn.ecology.populus.constants.ColorScheme;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][]{
	{ "PRM", "Insect Resistance Management" },
        { "One", "<b><i>" +ColorScheme.getColorString(0) + "x </font></i></b>, <b><i>" +
        ColorScheme.getColorString(1) + "y/G </font></i></b>, <b><i>"+ColorScheme.getColorString(2) + "p </font></i></b>, <b><i>" +
       ColorScheme.getColorString(3) + "w </font></i></b>" },
	{ "Generations_b_i_t_", "Generations ( <b><i>t</> )" },
	{ "Two", "<b><i> " +ColorScheme.getColorString(0) +"x * p</font></i></b>, <b><i>"+ColorScheme.getColorString(1) +
        "y/G * w </font></i></b>" },
	{ "Three", "<b><i> x + y </i></b>" },
	{ "Four", "<b><i>(x * p) + (y * w)</i></b> " },
	{ "Time_b_i_t_", "<b><i>Time ( <b><i>t</> ) </i></b>" },
	{ "Host_Population", "Population (   " },
	{ "Model_Type", "Model Type" },
	{ "Model_Parameters", "Model Parameters" },
	{ "Initial_Conditions", "Initial Conditions" },
	{ "Graph_Type", "Graph Type" },
	{ "Init_Population", "" }};


   public Object[][] getContents() {
      return contents;
   }
}
