package edu.umn.ecology.populus.model.lpg;
import java.io.Serializable;

class LPGData extends Object implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8248674235327098587L;
public double r;
   public double K;
   
   /*
   protected double N;
   protected double K;
   protected double r;
   protected double T;
   */
   public double N;
   public double T;
   public static int time;
   public static int modelType;
   
   //gensPF.getCurrentValue(),lambdaPF.getCurrentValue(), n0PF.getCurrentValue(), rPF.getCurrentValue()
   
   /*
   return new LPGParamInfo(modelType, paramN0.getCurrentValue(), paramK.getCurrentValue(),
   paramr.getCurrentValue(), paramT.getCurrentValue(),
   paramTime.getInt(), plotType);
   
   */
   public static int plotType;
   
   LPGData( int modelType, double paramNO, double paramK, double paramR, double paramT, int paramTime, int plotType ) {
      LPGData.modelType = modelType;
      LPGData.plotType = plotType;
      LPGData.time = paramTime;
      this.N = paramNO;
      this.K = paramK;
      this.r = paramR;
      this.T = paramT;
   }
}
