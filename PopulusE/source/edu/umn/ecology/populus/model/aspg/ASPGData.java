package edu.umn.ecology.populus.model.aspg;

/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author unascribed
 * @version 1.0
 */

public class ASPGData {
	public static final int kL = 0;
	public static final int kM = 1;
	public static final int kS = 2;
	public static final int kP = 3;
	public static final int kF = 4;

	double preL1;
	/**
	 * May be either:
	 * <UL>
	 * <LI>post-reproductive: Classes age for a year, then babies are created</LI> or
	 * <LI>pre-reproductive: Babies are created, then everyone ages for a year.
	 * </UL>
	 * This variable is only relevant if birthType is pulse.
	 */
	int modelType;
	int runTime;
	/**
	 * This is a matrix of 5 arrays: l, m, S, p, f  <br>
	 * The first three (l, m, S) are given as input, whereas
	 *  the last two (p, f) are calculated during calculateLeslieMatrix() <br>
	 * Each array has length <code>numClasses</code>.
	 */
	double[][] matrix;
	double[] initPops;

	public ASPGData() {
	}

	public void setLxMxTable(double[][] newLxMxTable, int modelType, int runTime){
		initPops = new double[newLxMxTable[kS].length-2];
		System.arraycopy(newLxMxTable[kS],0,initPops,0,initPops.length);
		this.runTime = runTime;
		this.modelType = modelType;
		matrix = calculateLeslieMatrix(newLxMxTable);
	}

	public void setLeslieMatrix(double[][] lesMatrix, double[] pops, int modelType, int runTime){
		initPops = new double[pops.length];
		System.arraycopy(pops,0,initPops,0,pops.length);
		this.runTime = runTime;
		this.modelType = modelType;
		matrix = lesMatrix;
	}

	private double[][] calculateLeslieMatrix(double[][] lxmx) {
		int n = getNumClasses();
		double[][] m = new double[n][n];
		int start = 1;
		if(modelType == ASPGPanel.kPREBREEDING)    start = 0;
		//Calculate fi's and pi's for the matrix
		for (int i = start; i < n; i++) {
			switch(modelType){
			case ASPGPanel.kPOSTBREEDING:
				//pi = li/l(i-1)
				//fi = mi*pi
				if (lxmx[kL][i] == 0) lxmx[kP][i] = 0;
				else                  lxmx[kP][i] = lxmx[kL][i] / lxmx[kL][i-1];
				lxmx[kF][i] =         lxmx[kM][i] * lxmx[kP][i];
				break;
			case ASPGPanel.kPREBREEDING:
				//pi = l(i+1)/li
				//fi = l1*mi
				if(lxmx[kL][i] == 0)  lxmx[kP][i+1] = 0;
				else                  lxmx[kP][i+1] = lxmx[kL][i+1] / lxmx[kL][i];
				lxmx[kF][i+1] =       lxmx[kL][0] * lxmx[kM][i];
				break;
			case ASPGPanel.kCONTINUOUS:
				//pi = (li + l(i+1))/(l(i-1) + li)
				//fi = ((1 + l1) / 4) * (mi + pi * m(i+1))
				if (lxmx[kL][i] + lxmx[kL][i+1] == 0) lxmx[kP][i] = 0;
				else  lxmx[kP][i] = (lxmx[kL][i] + lxmx[kL][i+1]) / (lxmx[kL][i] + lxmx[kL][i-1]);
				lxmx[kF][i] = (1.0 + lxmx[kL][1]) * (lxmx[kM][i] + lxmx[kP][i] * lxmx[kM][i+1]) / 4.0;
				break;
			}
		}

		for (int i = 0; i < n; i++) {
			if((i+1)<n)
				m[i+1][i] = lxmx[kP][i+1];
			m[0][i] = lxmx[kF][i+1];
		}
		return m;
	}

	String mapModel(int i){
		if(i == ASPGPanel.kPREBREEDING) return "Prebreeding";
		if(i == ASPGPanel.kCONTINUOUS) return "Continuous";
		return "Postbreeding";
	}

	public double[][] getLxMxTable(){
		int n = getNumClasses();
		double[][] lxmx = new double[5][n+2];
		double[] p = new double[n-1];
		double PIpi = 0;

		int shift = modelType == ASPGPanel.kPREBREEDING ? 0 : 1;
		for(int i=0; i<n; i++){
			if((i+1)<n)
				lxmx[kP][i+shift] = matrix[i+1][i];
			lxmx[kF][i+shift] = matrix[0][i];
		}

		int start = 1;
		if(modelType == ASPGPanel.kPREBREEDING){
			lxmx[kL][0] = preL1;
			lxmx[kM][0] = lxmx[kF][0]/lxmx[kL][0];
		} else {
			lxmx[kL][0] = 1;
		}
		if(modelType == ASPGPanel.kCONTINUOUS){
			System.arraycopy(lxmx[kP],1,p,0,n-1);
			PIpi = getP0(p);
			lxmx[kP][0] = PIpi;
			lxmx[kF][0] = getF0(lxmx[kP], lxmx[kF]);
		}

		System.arraycopy(initPops,0,lxmx[kS],0,n);
		//Calculate fi's and pi's for the matrix
		for (int i = start; i < n; i++) {
			switch(modelType){
			case ASPGPanel.kPOSTBREEDING:
				lxmx[kL][i] = lxmx[kP][i]*lxmx[kL][i-1];
				if (lxmx[kP][i] == 0) lxmx[kM][i] = 0;
				else                  lxmx[kM][i] = lxmx[kF][i] / lxmx[kP][i];
				break;
			case ASPGPanel.kPREBREEDING:
				lxmx[kM][i] = lxmx[kF][i]/lxmx[kL][0];
				lxmx[kL][i] = lxmx[kP][i-1]*lxmx[kL][i-1];
				break;
			case ASPGPanel.kCONTINUOUS:
				lxmx[kL][i] = PIpi - lxmx[kL][i-1];
				PIpi *= lxmx[kP][i];
				lxmx[kM][i] = 4*lxmx[kF][i-1]/(lxmx[kP][0]*lxmx[kP][i-1]) - lxmx[kM][i-1]/lxmx[kP][i-1];
				//lxmx[kM][i] = Math.abs(lxmx[kM][i]);
				break;
			}
		}
		if(modelType == ASPGPanel.kCONTINUOUS){
			//NumberMath.printMatrix(lxmx,"LxMx table for type "+mapModel(modelType), false);
			//NumberMath.printMatrix(calculateLeslieMatrix(lxmx),"do we get the same matrix back?",true);
		}
		return lxmx;
	}

	/**
	 * yay, this works! :-) i went from thinking this problem unsolvable to figuring it out. this math is probably hard
	 * to understand without looking at the formulas. essentially, the getF0 solves the equation for mx where x is one higher
	 * than the last class. e.g. mx = FUNCTION(p[], f[]) = 0
	 */
	private double getF0(double[] p, double[] f){
		int n = f.length - 3;
		double f0 = f[n]*Math.pow(-1,n+1);
		for(int i=n-1; i>0; i--)
			f0 = p[i]*f0 + f[i]*Math.pow(-1,i+1);
		return f0*p[0];
	}

	private double getP0(double[] p){
		double sum=0;
		int n = p.length+1;
		double[] denominator = new double[n];
		for(int i=0; i<n; i++)
			denominator[i] = 1;

		for(int i=0; i<n-1; i++){
			denominator[i] *= Math.pow(-1,n-i+1);
			for(int j=0; j<=i; j++)
				denominator[i+1] *= p[j];
			sum += denominator[i];
		}
		sum += denominator[n-1];
		//      return Math.abs(Math.pow(-1,n+1)/sum);
		return Math.pow(-1,n+1)/sum;
	}

	/**
	 * <code>tabularOutputMatrix[i][j]</code> has number of individuals in time <code>i</code>
	 *  that are in cohort <code>j</code>. <code>tabularOutputMatrix[i][numClasses-1]</code>
	 *  contain the number of individuals at time <code>i</code>.  <br>
	 *  The last element in each tabularOutputMatrix[i] is the total Sigma(Sx)
	 */
	public double[][] getTabularOutput() {
		int numClasses = getNumClasses();
		double[][] tabularOutputMatrix = new double[runTime+1][numClasses+1];
		double temp, tempsum;
		int i, j, k;

		//initialize first column
		System.arraycopy(initPops, 0, tabularOutputMatrix[0], 0, numClasses);
		temp = 0.0;
		for (i = 0; i < numClasses; i++) {
			temp += tabularOutputMatrix[0][i];
		}
		tabularOutputMatrix[0][numClasses] = temp;
		//Now fill rest of array. Note that temp and i are reused.
		for (i = 0; i < runTime; i++) {
			tempsum = 0.0;
			for (j = 0; j < numClasses; j++) {
				temp = 0.0;
				for (k = 0; k < numClasses; k++) {
					temp += matrix[j][k] * tabularOutputMatrix[i][k];
				}
				tabularOutputMatrix[i+1][j] = temp;
				tempsum += temp;
			}
			tabularOutputMatrix[i+1][numClasses] = tempsum;
		}
		return tabularOutputMatrix;
	}

	public double[][] getLeslieMatrix(){
		return getLeslieMatrix(false);
	}

	public double[][] getLeslieMatrix(boolean flip){
		if(!flip)
			return matrix;
		double[][] mat = new double[matrix.length][matrix[0].length];
		for(int i=0; i<mat.length; i++)
			for(int j=0; j<mat[i].length; j++)
				mat[i][j] = matrix[j][i];
		return mat;
	}

	public double[] getInitPops(){
		return initPops;
	}

	public int getNumClasses(){
		return initPops.length;
	}

	public int getModelType(){
		return modelType;
	}

	public void setModelType(int newtype){
		/*double[][] lxmx = getLxMxTable();
      edu.umn.ecology.populus.math.NumberMath.printMatrix(lxmx,"LxMx table for type "+modelType);
      if(newtype != ASPGPanel.kPREBREEDING){
         lxmx[kL][0] = 1;
         lxmx[kM][0] = 0;
      } else {
         lxmx[kL][0] = 0.9;
         lxmx[kM][0] = 0;
      }
      setLxMxTable(lxmx,newtype,runTime);
      lxmx = getLxMxTable();
      edu.umn.ecology.populus.math.NumberMath.printMatrix(lxmx,"LxMx table for type "+modelType);
		 */
		if(newtype == modelType) return;
		int n = getNumClasses();
		if(modelType == ASPGPanel.kPREBREEDING){
			System.arraycopy(matrix[0],1,matrix[0],0,n-1);
			matrix[0][n-1] = 0;
		} else if(newtype == ASPGPanel.kPREBREEDING){
			System.arraycopy(matrix[0],0,matrix[0],1,n-1);
			matrix[0][0] = 0;
		}
	}

	public void setPrebreedingParameter(double d){
		preL1 = d;
	}

	public int getRuntime(){
		return runTime;
	}
}
