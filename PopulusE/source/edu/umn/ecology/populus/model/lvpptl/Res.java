/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4693535159377470572L;
	static final Object[][] contents = new String[][]{
		{ "Continuous_Predator", "Continuous Predator-Prey Models" },
		{ "Type_1", "Type 1" },
		{ "Type_2", "Type 2" },
		{ "Type_3", "Type 3" },
		{ "Predator", "Predator" },
		{ "Prey", "Prey" },
		{ "Model_Type", "Model Type" },
		{ "Model_Parameters", "Model Parameters" },
		{ "Initial_Conditions", "Initial Conditions" },
		{ "Prey_Growth", "Prey Growth" },
		{ "Predator_Growth", "Predator Growth" },
		{ "Initial_Densities", "Initial Densities" },
		{ "Func_Response_Params", "Func. Response Params" },
		{ "Initial_Population", "Initial Population" },
		{ "Rate_of_population", "Rate of population growth.\nr is such that at any time p,\nPopulation " +
		"is e^(r * t) * initial population" },
		{ "Rate_of_population1", "Rate of population growth.\nr is such that at any time p,\nPopulation " +
		"is e^(r * t) * initial population" },
		{ "Graph_Type", "Graph Type" },
		{ "Lotka_Volterra", "Lotka-Volterra" },
		{ "Initial_Predator_Density", "Initial Predator Density" },
		{ "Initial_Prey_Density", "Initial Prey Density" },
		{ "Functional_Response", "Functional Response Parameters" },
		{ "Density_Dependent", "Density-Dependent Prey" },
		{ "DD_Prey", "D-D Prey" },
		{ "Init_Densities", "Init. Densities" },
		{ "Functional_Response1", "Functional Response" },
		{ "Running_Conditions", "Running Conditions" },
		{ "_u0398_Logistic", "\u0398 - Logistic" },
		{ "Time_b_i_t_", "Time ( <b><i>t</> )" },
		{ "Population_", "Population ( " },
		//LROE TODO: switch Predator and Prey here.
		//{ "NPopulation_b_i_", "Predator Population ( <b><i>" },
		//{ "PPopulation_b_i_", "Prey Population ( <b><i>" },
		{ "NPopulation_b_i_", "Prey Population ( <b><i>" },
		{ "PPopulation_b_i_", "Predator Population ( <b><i>" },
		{ "Lotka_Volterra1", "Lotka-Volterra Predator-Prey: Time Trajectory" },
		{ "_u0398_Logistic1", "\u0398-Logistic Predator-Prey: Time Trajectory" },
		{ "Lotka_Volterra2", "Lotka-Volterra Predator-Prey: Phase Plane" },
		{ "_u0398_Logistic2", "\u0398-Logistic Predator-Prey: Phase Plane" },
		{ "Not_a_valid_theta", "Not a valid theta type" }};

		@Override
		public Object[][] getContents() {
			return contents;
		}
}
