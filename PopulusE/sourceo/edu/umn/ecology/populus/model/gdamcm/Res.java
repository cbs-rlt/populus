package edu.umn.ecology.populus.model.gdamcm;
import java.util.*;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   static final Object[][] contents = new String[][] {
       {
         "Genetic_Drift", "Genetic Drift (Monte Carlo)"
      },  {
         "Breeding_Parameters", "Breeding Parameters"
      },  {
         "Initial_Conditions", "Initial Conditions"
      },  {
         "Runtime", "Runtime"
      },  {
         "3i_N_i_Generations", "3<i>N</i> Generations"
      },  {
         "Other_", "Other:"
      },  {
         "Number_of_generations", "Number of generations to plot"
      },  {
         "Number_of_individuals", "Number of individuals in population"
      },  {
         "Number_of_gene_loci", "Number of gene loci in each individual"
      },  {
         "Initial_frequency_of", "Initial frequency of each loci (from 0 to 1) "
      },  {
         "Permit_Selfing_", "Permit Selfing?"
      },  {
         "Generations", "Generations"
      },  {
         "Population_Size_i_N_i", "Population Size (<i>N</i>)"
      },  {
         "Number_of_Loci", "Number of Loci"
      },  {
         "set_frequencies_all", "set frequencies collectively"
      },  {
         "set_frequencies", "set frequencies individually"
      },  {
         "Initial_Frequency", "Initial Frequency"
      },  {
         "Locus_", "Locus #"
      },  {
         "Generations_b_i_t_", "Generations ( <b><i>t</> )"
      },  {
         "Allelic_Frequency_b_i", "Allelic Frequency (  <b><i>p</> )"
      },  {
         "Loci_1", "Loci #1"
      },  {
         "Loci_2", "Loci #2"
      },  {
         "Loci_3", "Loci #3"
      },  {
         "Loci_4", "Loci #4"
      },  {
         "Loci_5", "Loci #5"
      },  {
         "Loci_6", "Loci #6"
      },  {
         "Loci_7", "Loci #7"
      },  {
         "Loci_8", "Loci #8"
      },  {
         "Loci_9", "Loci #9"
      },  {
         "Loci_10", "Loci #10"
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
