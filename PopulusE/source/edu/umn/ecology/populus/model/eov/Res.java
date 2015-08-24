/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.eov;
import java.io.Serializable;
import edu.umn.ecology.populus.constants.ColorScheme;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1976015012698126071L;
static final Object[][] contents = new String[][] {
      {
      "Infectious", "Evolution of Disease Virulence"
      },
      {
      "Susceptable_Host_S_", "Susceptable Host (<b><i>"+ColorScheme.getColorString(0)+"H</>)"
      },  {
     "Virulence_", "Virulence (<b><i>"+ColorScheme.getColorString(0)+"e</> )" + ", Transmission (<b><i>"+ColorScheme.getColorString(1)+" b</>"+")"
      },  {
      "hstar_", "Equilibrium Density" +" (<b><i>"+ColorScheme.getColorString(0)+" H*</>), (<b><i>" + ColorScheme.getColorString(1)+" I*</>), (<b><i>" +ColorScheme.getColorString(2)+"H* + I*</>)"
      },{
      "Infected_Host_I_", "Infected Host (<b><i>"+ColorScheme.getColorString(1)+"I</> )"
      },  {
      "Susceptable_Host_S_R", "Susceptable Host ( <b><i>"+ColorScheme.getColorString(0)+"S</> )"
      },  {
         "Infected_Host_I_R", "Infected Host ( <b><i>"+ColorScheme.getColorString(0)+"I</> )"
      },{
         "Time_b_i_t_", "Time ( <b><i>t</> )"
      },  {
         "Host_Population", "Host Population Density (   "
      },  {
         "totHostDens", "Total Host Density ( <b><i>"+ColorScheme.getColorString(0)+"N</> )"
      },  {
         "Infectious1", "Evolution of Disease Virulence"
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
