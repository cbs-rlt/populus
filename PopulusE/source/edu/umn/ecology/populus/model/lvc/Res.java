/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvc;
import java.io.Serializable;

/*
//English
public class Res extends java.util.ListResourceBundle {
static final Object[][] contents = new String[][]{
{ "Lotka_Volterra", "Lotka-Volterra Competition" },
{ "Time_b_i_t_", "Time ( <b><i>t</> )" },
{ "Population_", "Population ( " },
{ "Population_b_i_", "Population ( <b><i>" },
{ "Lotka_Volterra1", "Lotka-Volterra Competition: Time Trajectory" },
{ "Lotka_Volterra2", "Lotka-Volterra Competition: Phase Plane" }};

public Object[][] getContents() {
return contents;
}
}
/*/

//Spanish

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4231606753494024999L;
static final Object[][] contents = new String[][] {
       {
         "Lotka_Volterra", "Lotka-Volterra Competition"
      },  {
         "Time_b_i_t_", "Time ( <b><i>t</> )"
      },  {
         "Population_", "Population ( "
      },  {
         "Population_b_i_", "Population ( <b><i>"
      },  {
         "Lotka_Volterra1", "Lotka-Volterra Competition: Time Trajectory"
      },  {
         "Lotka_Volterra2", "Lotka-Volterra Competition: Phase Plane"
      }
   };
   
   public Object[][] getContents() {
      return contents;
   }
}
  //*/
