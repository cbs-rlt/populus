package edu.umn.ecology.populus.model.aspg;

import edu.umn.ecology.populus.plot.*;
import java.util.*;

/**
 * <b>Demography (or ASPG=Age-Structured Population Growth)</b>
 * <p>This breaks up the population into different age classes.
 * Actually, we only study the female population, but we can pretend it is
 * just the population as a whole, as in Density-independent growth.
 * lx = age-specific survivorship (make it to next age class)
 * mx = age-specific fertility (
 */
public class ASPGModel extends BasicPlotModel {

	public ASPGModel() {
      this.setModelInput(new ASPGPanel());
	}

	public static String getModelName() {
		return res.getString("Age_Structured");
	}

	public Object getModelHelpText() {
      return "ASPGHELP";
	}

	protected String getHelpId() {
      return "aspg.overview";
	}

	static ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.aspg.Res");

}
