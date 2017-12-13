/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.aspg;

import java.io.Serializable;

public class Res extends java.util.ListResourceBundle
implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1583074720739231999L;
	static final Object[][] contents = new String[][]{
		{ "Age_Structured", "Age-Structured Growth" },
		{ "Output_Type", "Output Type" },
		{ "Output_Parameters", "Output Parameters" },
		{ "Birthing", "Birthing" },
		{ "Birth_Pattern", "Birth Pattern" },
		{ "Census_Timing", "Census Timing" },
		{ "Total_number_of_age", "Total number of age classes" },
		{ "_i_classes_i_", "<i># classes </i>" },
		{ "Tabular_Projection", "Tabular Projection" },
		{ "When_graphing_Sx", "When graphing Sx/\u03A3Sx vs t, this variable selects which age class " },
		{ "to_view_Acceptable", "to view." },
		{ "_i_Age_Class_To_View", "<i>Age Class To View </i>" },
		{ "The_number_of_time", "The number of time intervals to run" },
		{ "_i_Run_Time_i_", "Run Time" },
		{ "View_All_Age_Classes", "View All Age Classes" },
		{ "Birth_Flow", "Birth-Flow" },
		{ "Birth_Pulse", "Birth-Pulse" },
		{ "Prebreeding", "Prebreeding" },
		{ "Postbreeding", "Postbreeding" },
		{ "_Discrete_", "(Discrete)" },
		{ "_Continuous_", "(Continuous)" },
		{ "Leslie_Matrix", "Leslie Matrix" },
		{ "Age_Class_i_x_i_", "Age Class (<i>x</i>)" },
		{ "lx_must_be_weakly", "Parameter lx must be non-increasing." },
		{ "Unknown_model_type", "Unknown model type" },
		{ "Leslie_Projection", "Leslie Projection Matrix" },
		{ "Tabular_Projections", "Tabular Projections" },
		{ "INVALID_TITLE", "INVALID TITLE" }};

		//  public static double getR0() {
		// ASPGParamInfo.calculateRTrLn();
		//}
		@Override
		public Object[][] getContents() {
			return contents;
		}
}