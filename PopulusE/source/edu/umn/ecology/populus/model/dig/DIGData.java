package edu.umn.ecology.populus.model.dig;
import java.io.Serializable;

class DIGData extends Object implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 2469088803649153178L;
public static int selection;
   public static double gensPF;
   public static boolean isContinuous;
   protected double rPF;
   protected double nOPF;
   
   //gensPF.getCurrentValue(),lambdaPF.getCurrentValue(), n0PF.getCurrentValue(), rPF.getCurrentValue()
   protected double lambdaPF;
   
   DIGData( boolean a, int b, double c, double d, double e, double f ) {
      isContinuous = a;
      selection = b;
      gensPF = c;
      lambdaPF = d;
      nOPF = e;
      rPF = f;
   }
}
