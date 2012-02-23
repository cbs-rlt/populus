package edu.umn.ecology.populus.edwin;

public interface ModelPanelEventTypes {
   /** Signal to repack the internal frame window.  Not a direct user request. */
   public static final int REPACK = 32;

   /**for example, the parameterfield component, when incrementing and decrementing*/
   public static final int ADJUSTMENT = 2;

   /**
     *Rare. maybe for something like "show crossover".
     *Does not change actual data, just the look of the output
     */
   public static final int VISUAL = 4;

   /**Generally a change such as discrete to continuous*/
   public static final int CHANGE_PLOT = 8;
   public static final int TYPING = 16;

   /**For pressing ENTER, of course. Generally for ParameterFields*/
   public static final int ENTER = 1;
}
