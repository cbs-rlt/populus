package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.plot.coloredcells.*;
import edu.umn.ecology.populus.plot.ParamInfo;
import java.util.Random;

class SDCellParamInfo extends ParamInfo implements CellFunction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9038939798762684050L;
	static final int kC 	= 0;
	static final int kCD 	= 1;
	static final int kD 	=	2;
	static final int kDC 	= 3;

	int size, type, border, runInterval, currentGeneration=0;
	boolean is8, intwself, isStable;
	double[][][] freqs;
	double[][] olddata;
	double p,t,s,r;


	public SDCellParamInfo(int type, int border, int inittype,double initfreq,int patchsize,
			boolean isEdge,	boolean is8Neighbors, boolean diseach, boolean interactwself,
			int runinterval, int laticesize, double p, double t, double s, double r){
		runInterval = runinterval;
		this.type = type;
		this.border = border;
		this.intwself = interactwself;
		this.size = laticesize;
		this.is8 = is8Neighbors;
		if(diseach) runInterval = 1;
		olddata = new double[size][size];
		this.p = p;
		this.t = t;
		this.s = s;
		this.r = r;
      initialize(inittype,initfreq,patchsize,isEdge);
	}

	void initialize(int inittype, double initfreq, int patchsize, boolean isEdge){
		Random r = new Random(System.currentTimeMillis());
		switch (inittype){
			case SDPanel.kRandom:
				for(int i=0; i<size; i++)
					for(int j=0; j<size; j++)
						if(r.nextDouble()<initfreq) olddata[i][j] = kC;
						else olddata[i][j] = kD;
				break;
			case SDPanel.kOneCD:
				for(int i=0; i<size; i++)
					for(int j=0; j<size; j++)
						olddata[i][j] = kD;
				if(!isEdge)
					for(int i=(size/2-patchsize/2); i<(size/2+(patchsize/2+0.5)); i++)
						for(int j=(size/2-patchsize/2); j<(size/2+(patchsize/2+0.5)); j++)
							olddata[i][j] = kC;
				else
					for(int i=0; i<patchsize; i++)
						for(int j=0; j<patchsize; j++)
							olddata[i][j] = kC;
				break;
			case SDPanel.kOneDH:
				for(int i=0; i<size; i++)
					for(int j=0; j<size; j++)
						olddata[i][j] = kC;
				if(!isEdge)
					for(int i=(size/2-patchsize/2); i<(size/2+(patchsize/2+0.5)); i++)
						for(int j=(size/2-patchsize/2); j<(size/2+(patchsize/2+0.5)); j++)
							olddata[i][j] = kD;
				else
					for(int i=0; i<patchsize; i++)
						for(int j=0; j<patchsize; j++)
							olddata[i][j] = kD;
				break;
		}
	}

	/** Get the values to start up the CellPanel*/
	public double[][] initialF(){
		updateFreqs();
		return olddata;
	}
	/** Get the next iteration of values*/
	public synchronized double[][] f(){
		double[][] values = new double[size][size];
		for(int i=0; i<values.length; i++)
			for(int j=0; j<values[0].length; j++)

				for(int m=i-1; m<=i+1; m++)
					for(int n=j-1; n<= j+1; n++){
						if(!intwself && m==i && n==j)
							continue;
						if(!is8 && m!=i && n!=j)
							continue;
						int tM=m, tN=n;
						double[] v;
						switch(border){
							case SDPanel.kAbsorbing:
								if(m<0 || n<0 || m>=values.length || n>=values[0].length) break;

								v = getResults(i,j,tM,tN);
								values[i][j] += v[0];
								values[tM][tN] += v[1];
								break;
							case SDPanel.kPeriodic:
								if(m<0) 	tM = values.length-1;
								if(m>=values.length) tM = 0;
								if(n<0) 	tN = values[0].length-1;
								if(n>=values[0].length) tN = 0;

								v = getResults(i,j,tM,tN);
								values[i][j] += v[0];
								values[tM][tN] += v[1];
								break;
							case SDPanel.kReflexive:
								if(m<0) 	tM = 0;
								if(m>=values.length) tM = values.length-1;
								if(n<0) 	tN = 0;
								if(n>=values[0].length) tN = values[0].length-1;

								v = getResults(i,j,tM,tN);
								values[i][j] += v[0];
								values[tM][tN] += v[1];
								break;
						}
					}

		double high=0;
		double[][] newdata = new double[olddata.length][olddata[0].length];
		int iind=-1, jind=-1;
		for(int i=0; i<values.length; i++)
			for(int j=0; j<values[0].length; j++){
        high=0;
				for(int m=i-1; m<=i+1; m++)
					for(int n=j-1; n<= j+1; n++){
						if(!intwself && m==i && n==j)
							continue;
						if(!is8 && m!=i && n!=j)
							continue;
						int tM=m, tN=n;
						switch(border){
							case SDPanel.kAbsorbing:
								if(m<0 || n<0 || m>=values.length || n>=values[0].length) break;

								if(values[tM][tN] >= high){
									high = values[tM][tN];
									iind = tM;
									jind = tN;
								}
								break;
							case SDPanel.kPeriodic:
								if(m<0) 	tM = values.length-1;
								if(m>=values.length) tM = 0;
								if(n<0) 	tN = values[0].length-1;
								if(n>=values[0].length) tN = 0;

								if(values[tM][tN] >= high){
									high = values[tM][tN];
									iind = tM;
									jind = tN;
								}
								break;
							case SDPanel.kReflexive:
								if(m<0) 	tM = 0;
								if(m>=values.length) tM = values.length-1;
								if(n<0) 	tN = 0;
								if(n>=values[0].length) tN = values[0].length-1;

								if(values[tM][tN] >= high){
									high = values[tM][tN];
									iind = tM;
									jind = tN;
								}
								break;
						}
					}

				if(olddata[iind][jind] == kC || olddata[iind][jind] == kDC)
					if(olddata[i][j] == kC || olddata[i][j] == kDC)
						newdata[i][j] = kC;
					else
						newdata[i][j] = kDC;
				else
					if(olddata[i][j] == kD || olddata[i][j] == kCD)
						newdata[i][j] = kD;
					else
						newdata[i][j] = kCD;
			}
		isStable = compareArrays(newdata, olddata);
		olddata = newdata;
		currentGeneration++;
		updateFreqs();
		return olddata;
	}

	boolean compareArrays(double[][] rhs, double[][] lhs){
		if(rhs.length != lhs.length || rhs[0].length != lhs[0].length)
			return false;
		for(int i=0; i<rhs.length; i++)
			for(int j=0; j<rhs[0].length; j++)
				if(rhs[i][j] != lhs[i][j])
					return false;
		return true;
	}

	double[] getResults(int hi, int hj, int ni, int nj){
		boolean homeC =     (olddata[hi][hj]==kC || olddata[hi][hj]==kDC);
		boolean neighborC = (olddata[ni][nj]==kC || olddata[ni][nj]==kDC);
		double home, neighbor;
		if(homeC && neighborC){
			home = neighbor = r;
		} else if(!homeC && !neighborC) {
			home = neighbor = p;
		} else if(!homeC && neighborC){
			home = t;
			neighbor = s;
		} else {
			home = s;
			neighbor = t;
		}
		return new double[] {home, neighbor};
	}

	double[][] cloneArray(double[][] d){
		double[][] r = new double[d.length][d[0].length];
		for(int i=0; i<r.length; i++)
			for(int j=0; j<r[0].length; j++)
				r[i][j] = d[i][j];
		return r;
	}
	void updateFreqs(){
		if(freqs==null) freqs = new double[2][2][currentGeneration+1];
		if(freqs[0][0].length>=currentGeneration){
			double[][][] points = new double[2][2][freqs[0][0].length+50];
			for(int i=0; i<freqs.length; i++)
				for(int j=0; j<freqs[0].length; j++)
					for(int k=0; k<freqs[0][0].length; k++)
						points[i][j][k] = freqs[i][j][k];
			freqs = points;
		}
		freqs[0][0][currentGeneration] = currentGeneration;
		freqs[1][0][currentGeneration] = currentGeneration;
		freqs[0][1][currentGeneration] = getFreqC();
		freqs[1][1][currentGeneration] = 1-getFreqC();
	}

	double getFreqC(){
		double sum=0;
		for(int i=0; i<olddata.length; i++)
			for(int j=0; j<olddata[0].length; j++)
				if(olddata[i][j]==kC || olddata[i][j]==kDC)
					sum+=1;
		return sum/(size*size);
	}

	double[][][] getFrequencies(){
      double[][][] v = new double[2][2][currentGeneration+1];
      for(int i=0; i<freqs.length; i++)
         for(int j=0; j<freqs[0].length; j++)
            for(int k=0; k<v[0][0].length; k++)
               v[i][j][k] = freqs[i][j][k];

		//if the generation of the last data point is zero, then that means the
		//the generations got out of sync.
		if(v[0][0][v[0][0].length-1] == 0){
			double[][][] temp = new double[2][2][v[0][0].length-1];
			for(int i=0; i<temp.length; i++)
				for(int j=0; j<temp[0].length; j++)
					for(int k=0; k<temp[0][0].length; k++)
						temp[i][j][k] = v[i][j][k];
			v = temp;
		}

		return v;
	}
	/** Change type, get other values*/
	public double[][] changeType(String s){
		return null;
	}
	/** Get the desired dimension of the data (not used, dim comes from data)*/
	public int getDimension(){
		return size;
	}
	/** Get the strings that will be used in the key*/
	public String[] getStrings(){
		return new String[] {"C","C to D","D","D to C"};
	}
	/** Get the values that separate the data into the different colors*/
	public double[] getDemarcations(){
		return new double[] {0d,1d,2d,3d};
	}
	/** Get the interval of iterations before a pause*/
	public int getBreakInterval(){
		return runInterval;
	}
	/** Get the output types*/
	public String[] getOutputTypes(){
		return null;
	}
	/** Get current model type*/
	public String getCurrentType(){
		return null;
	}
	/** Set one of the values in the matrix*/
	public void setValue(int species, int r, int c, double newValue){
      olddata[r][c] = newValue;
	}
	public CellPalette getColorPalette(){
		return new SDColorPalette();
	}
	public int getGeneration(){
		return currentGeneration;
	}
}
