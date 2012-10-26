package edu.umn.ecology.populus.plot;
import java.io.Serializable;

public class Res extends java.util.ListResourceBundle implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 833561965141554797L;
static final Object[][] contents = new String[][] {
       {
         "Output_of", "Output of "
      },  {
         "vs_", " vs "
      },  {
         "Line_", "Line #"
      },  {
         "No_text_data", "No text data available."
      },  {
         "Text_output_for", "Text output for "
      }
   };

   public Object[][] getContents() {
      return contents;
   }
}
