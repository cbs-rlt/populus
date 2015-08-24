/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sdal;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8689433770326039777L;
static final Object[][] contents = new String[][] {
       {
         "Autosomal_Selection", "Selection on a Diallelic Autosomal Locus"
      },  {
         "Plot_Options", "Plot Options"
      },  {
         "Fitness_or_Selection", "Fitness or Selection Coefficients"
      },  {
         "Initial_Conditions", "Initial Conditions"
      },  {
         "Genotypic_frequency", "Genotypic frequency vs <i>t</i>"
      },  {
         "One_Initial_Frequency", "One Initial Frequency"
      },  {
         "Initial_Frequency", "Initial Frequency"
      },  {
         "Six_Initial", "Six Initial Frequencies"
      },  {
         "Generations", "Generations"
      },  {
         "Fitness_Selection", "Fitness/Selection Coeffs."
      },  {
         "Fitness", "Fitness"
      },  {
         "Selection", "Selection"
      },  {
         "Generations_b_i_t_", "Generations ( <b><i>t</> )"
      },  {
         "Genotypic_Frequencies", "Genotypic Frequencies vs <b><i>t</>"
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
