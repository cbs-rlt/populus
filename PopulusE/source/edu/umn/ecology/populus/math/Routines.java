/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.math;

import java.util.Random;
/**
  Basic math routines

  These functions should probably be removed from the math package and placed in a utilities
  package with stuff from NumberMath

  @author Lars Roe
 */

public final class Routines {
	public static double randBinOldg, randBinNold, randBinPc, randBinPlog, randBinPclog, randBinPold;

	public static void quickSort( double a[][], int left, int right, int line ) {
		int leftIndex = left;
		int rightIndex = right;
		double partionElement;
		if( right > left ) {

			/* Arbitrarily establishing partition element as the midpoint of
			 * the array.
			 */
			partionElement = a[line][( left + right ) / 2];

			// loop through the array until indices cross
			while( leftIndex <= rightIndex ) {

				/* find the first element that is greater than or equal to
				 * the partionElement starting from the leftIndex.
				 */
				while( ( leftIndex < right ) && ( a[line][leftIndex] < partionElement ) ) {
					++leftIndex;
				}

				/* find an element that is smaller than or equal to
				 * the partionElement starting from the rightIndex.
				 */
				while( ( rightIndex > left ) && ( a[line][rightIndex] > partionElement ) ) {
					--rightIndex;
				}

				// if the indexes have not crossed, swap
				if( leftIndex <= rightIndex ) {
					swap( a, leftIndex, rightIndex );
					++leftIndex;
					--rightIndex;
				}
			}

			/* If the right index has not reached the left side of array
			 * must now sort the left partition.
			 */
			if( left < rightIndex ) {
				quickSort( a, left, rightIndex, line );
			}

			/* If the left index has not reached the right side of array
			 * must now sort the right partition.
			 */
			if( leftIndex < right ) {
				quickSort( a, leftIndex, right, line );
			}
		}
	}

	/*
   Quick Sort implementation snatched from Model3D.java
   by Sun
	 */

	public static void quickSort( int a[], int left, int right ) {
		int leftIndex = left;
		int rightIndex = right;
		int partionElement;
		if( right > left ) {

			/* Arbitrarily establishing partition element as the midpoint of
			 * the array.
			 */
			partionElement = a[( left + right ) / 2];

			// loop through the array until indices cross
			while( leftIndex <= rightIndex ) {

				/* find the first element that is greater than or equal to
				 * the partionElement starting from the leftIndex.
				 */
				while( ( leftIndex < right ) && ( a[leftIndex] < partionElement ) ) {
					++leftIndex;
				}

				/* find an element that is smaller than or equal to
				 * the partionElement starting from the rightIndex.
				 */
				while( ( rightIndex > left ) && ( a[rightIndex] > partionElement ) ) {
					--rightIndex;
				}

				// if the indexes have not crossed, swap
				if( leftIndex <= rightIndex ) {
					swap( a, leftIndex, rightIndex );
					++leftIndex;
					--rightIndex;
				}
			}

			/* If the right index has not reached the left side of array
			 * must now sort the left partition.
			 */
			if( left < rightIndex ) {
				quickSort( a, left, rightIndex );
			}

			/* If the left index has not reached the right side of array
			 * must now sort the right partition.
			 */
			if( leftIndex < right ) {
				quickSort( a, leftIndex, right );
			}
		}
	}

	public static final double getMaxValue( double[] nums ) {
		double bestValue = nums[0];
		for( int i = 1;i < nums.length;i++ ) {
			bestValue = Math.max(bestValue,nums[i]);
		}
		return bestValue;
	}

	/** in the case of a tie, it goes to the greater index */

	public static final int getMaxIndex( int[] nums ) {
		return getMaxIndex( nums, 0, nums.length - 1 );
	}

	/**
	 * Finds the greatest index in the range of start to end, inclusive
	 * in the case of a tie, it goes to the greater index
	 */

	public static final int getMaxIndex( int[] nums, int start, int end ) {
		int bestValue = nums[start];
		int bestIndex = 0;
		for( int i = start + 1;i <= end;i++ ) {
			if( nums[i] > bestValue ) {
				bestValue = nums[i];
				bestIndex = i;
			}
		}
		return bestIndex;
	}

	public static final int[] shrinkIntArray( int[] array, int finalSize ) {
		int[] temp;
		temp = new int[finalSize];
		System.arraycopy( array, 0, temp, 0, finalSize );
		return temp;
	}

	/**
	 * Transforms {N0, N1, N2 ... NX}
	 * to {{N0, N1, ... N(X-1)}, {N1-N0, N2-N1, ... , NX - N(X-1)}}
	 * nt+1 - nt
	 * <BR> Assumes parameter <code>out</code> is ready to use.
	 */

	public static final void process1( double[] narr, double[][] out ) {
		int x = narr.length - 1;
		int i;
		for( i = 0;i < x;i++ ) {
			out[0][i] = narr[i];
			out[1][i] = narr[i + 1] - narr[i];
		}
	}

	public static final void lnArray( double[] array ) {
		for( int i = 0;i < array.length;i++ ) {
			array[i] = Math.log( array[i] );
		}
	}

	/**
     Found in Numerical Recipies pg 177.
     the natural log of the gamma function is used because the values
     can get quite large for even smaller x.
	 */

	public static final double gammln( double a ) {
		final double stp = 2.50662827465;
		double x, tmp, ser;
		x = a - 1.0;
		tmp = x + 5.5;
		tmp = ( x + 0.5 ) * Math.log( tmp ) - tmp;
		ser = 1 + 76.18009173 / ( x + 1d ) - 86.50532033 / ( x + 2d ) + 24.01409822 / ( x + 3d ) - 1.231739516 / ( x + 4d ) + 0.120858003e-2 / ( x + 5d ) - 0.536382e-5 / ( x + 6d );
		return tmp + Math.log( stp * ser );
	}

	/** in the case of a tie, it goes to the greater index */

	public static final int getMaxIndex( double[] nums ) {

		/*
      if (nums == null || nums.length == 0)
      return -1; //invalid
      double bestValue = nums[0];
      int bestIndex = 0;
      for (int i = 1; i < nums.length; i++) {
      if (nums[i] > bestValue) {
      bestValue = nums[i];
      bestIndex = i;
      }
      }
      return bestIndex;
		 */
		return getMaxIndex( nums, 0, nums.length - 1 );
	}

	/** End is exclusive, 0 included */

	public static final int getRandomInt( int length ) {
		return getRandomInt( 0, length - 1 );
	}

	/**
	 * Transforms {N0, N1, N2 ... NX}
	 * to {{N0, N1, ... N(X-1)}, {(N1-N0)/N0, (N2-N1)/N1, ... , (NX - N(X-1))/N(X-1)}}
	 * (nt+1 - nt)/nt
	 * was used in discrete density-dependent population growth, but has been replaced
	 * with process3
	 * <BR> Assumes parameter <code>out</code> is ready to use.
	 */

	public static final void process2( double[] narr, double[][] out ) {
		int x = narr.length - 1;
		int i;
		for( i = 0;i < x;i++ ) {
			out[0][i] = narr[i];
			out[1][i] = ( narr[i + 1] - narr[i] ) / narr[i];
		}
	}

	/**
	 * Transforms {N0, N1, N2 ... NX}
	 * ln(nt+1/nt) vs nt
	 * used in discrete density-dependent population growth
	 * <BR> Assumes parameter <code>out</code> is ready to use.
	 */

	public static final void process3( double[] narr, double[][] out ) {
		int x = narr.length - 1;
		int i;
		for( i = 0;i < x;i++ ) {
			out[0][i] = narr[i];
			out[1][i] = Math.log ( narr[i + 1] / narr[i] );
		}
	}

	/** Ends are inclusive */

	public static final int getRandomInt( int min, int max ) {
		int attempt = (int)( Math.random() * ( 1.0 + max - min ) );
		if( attempt >= min && attempt <= max ) {
			return attempt;
		}
		else {
			return getRandomInt( min, max );
		}
	}

	/**
	 * i don't understand why this was put in here. is this method better than
	 * the ones provided in java.lang.Random?
	 * @param pp
	 * @param n
	 * @return
	 */
	public static final int randBin( double pp, int n ) {
		int bnl, j;
		double np, em, g, angle, p, sq, t, y;
		p = ( pp <= 0.5 ) ? pp : 1.0 - pp;
		np = n * p;
		if( n < 25 ) { //regular method
			bnl = 0;
			for( j = 0;j < n;j++ ) {
				if( Math.random() < p ) {
					bnl++;
				}
			}
		}
		else {
			if( np < 1.0 ) { //do Poisson
				g = Math.exp( -np );
				t = 1;
				j = -1;
				do {
					j++;
					t *= Math.random();
				}while( !( ( t < g ) || ( j == n ) ) );
				bnl = j;
			}
			else {
				if( n != randBinNold ) {
					randBinOldg = gammln( n + 1 );
					randBinNold = n;
				}
				if( p != randBinPold ) {
					randBinPc = 1 - p;
					randBinPlog = Math.log( p );
					randBinPclog = Math.log( randBinPc );
					randBinPold = p;
				}
				sq = Math.sqrt( 2.0 * np * randBinPc );
				do {
					do {
						do {
							angle = Math.PI * Math.random();
						}while( angle == Math.PI / 2 );
						y = Math.sin( angle ) / Math.cos( angle );
						em = sq * y + np;
					}while( ( em < 0 ) || ( em >= n + 1 ) );
					em = ( (int)em );
					t = 1.2 * sq * ( 1 + y * y ) * Math.exp( randBinOldg - gammln( em + 1 ) - gammln( n - em + 1 ) + em * randBinPlog + ( n - em ) * randBinPclog );
				}while( Math.random() > t );
				bnl = (int)em;
			}
			if( p != pp ) {
				bnl = n - bnl;
			}
		}
		return bnl;
	}

	/**
	 *
	 * @param number, an integer to factorize
	 * @return int[] prime factors, in ascending order.  A prime p with multiplicity m will repeat p m times.
	 */
	public static final int[] primeFactorize( int number ) {
		int sqrnum = (int)Math.sqrt( number );
		int a;
		int[] retVal;
		java.util.Vector<Integer> nums = new java.util.Vector<Integer>();
		for( a = 2;a <= sqrnum;a++ ) {
			if( number % a == 0 ) {
				number /= a;
				sqrnum = (int)Math.sqrt( number );
				nums.addElement( new Integer( a ) );
				a--;
			}
		}
		if( number > 1 ) {
			nums.addElement( new Integer( number ) );
		}
		retVal = new int[nums.size()];
		for( a = 0;a < retVal.length;a++ ) {
			retVal[a] = nums.elementAt( a );
		}
		return retVal;
	}

	public static final double getMinValue( double[] nums ) {
		double bestValue = nums[0];
		for( int i = 1;i < nums.length;i++ ) {
			bestValue = Math.min(bestValue,nums[i]);
		}
		return bestValue;
	}

	/**
	 * Finds the greatest index in the range of start to end, inclusive
	 * in the case of a tie, it goes to the greater index
	 */

	public static final int getMaxIndex( double[] nums, int start, int end ) {
		double bestValue = nums[start];
		int bestIndex = 0;
		for( int i = start + 1;i <= end;i++ ) {
			if( nums[i] > bestValue ) {
				bestValue = nums[i];
				bestIndex = i;
			}
		}
		return bestIndex;
	}

	public static final double getVariance( double[] y ) {
		double sumy2 = 0.0, sumy = 0.0;
		int n = y.length;
		if( n < 2 )
			return 0.0;

		for(int k = 0;k <= n - 1;k++ ) {
			sumy2 += y[k] * y[k];
			sumy += y[k];
		}
		return sumy2 / n - ( sumy * sumy ) / ( n * n );
	}

	/** Returns aCb (combinatorial) */
	public static final double comb( long n, long k ) {
		double temp = 1.0;
		int i;
		for( i = 1;i <= k;i++ ) {
			temp *= n / k;
			n--;
			k--;
		}
		return temp;
	}

	public static final double[] shrinkDoubleArray( double[] array, int finalSize ) {
		double[] temp;
		temp = new double[finalSize];
		System.arraycopy( array, 0, temp, 0, finalSize );
		return temp;
	}

	/**
	 * swap the ith and jth element in <code>a</code>
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void swap( int a[], int i, int j ) {
		int T;
		T = a[i];
		a[i] = a[j];
		a[j] = T;
	}

	/**
	 * swaps all the ith and jth elements for every array
	 * @param a
	 * @param i
	 * @param j
	 */
	private static void swap( double a[][], int i, int j ) {
		double T;
		for( int index = 0;index < a.length;index++ ) {
			T = a[index][i];
			a[index][i] = a[index][j];
			a[index][j] = T;
		}
	}

	/**
      This method will remove infinities from the data set by simply truncating the
      data at the NaN.
	 */
	public static void checkAndRemoveInfinities(double[][][] data) {
		for( int i = 0;i < data.length;i++ ) {
			checkSeriesLoop:for( int j = 0;j < data[i][0].length;j++ ) {
				if( Double.isInfinite( data[i][0][j] ) || Double.isInfinite( data[i][1][j] ) ) {
					if( j <= 0 ) {
						int k;
						double[][][] newData = new double[data.length - 1][][];
						for( k = 0;k < i;k++ ) {
							newData[k] = data[k];
						}
						for( k = i + 1;k < data.length;k++ ) {
							newData[k - 1] = data[k];
						}
						data = newData;
					} else {
						//Logging.log("The value of j: "+j+"\n");
						//Truncate the series up to the infinite value
						double[][] newDataSeries = new double[2][j];
						System.arraycopy( data[i][0], 0, newDataSeries[0], 0, j );
						System.arraycopy( data[i][1], 0, newDataSeries[1], 0, j );
						data[i] = newDataSeries;
					}

					//Done checking this series
					break checkSeriesLoop;
				}
			}
		}
	}

	/**
	 * this method removes zeros from a data set to make the data set smaller.
	 * @param points
	 * @param keepOneBefore the point at which the data is truncated is found by looking
	 * for non-zero values. if the zero value before the first non-zero should be included
	 * in the new data set, then set this to true.
	 * @return
	 */
	public static double[][] removeInitialZeros(double[][] points, boolean keepOneBefore){
		int i;
		gotPoint:for(i=0; i<points[0].length; i++)
			for(int j=1; j<points.length; j++)
				if(points[j][i] != 0.0) break gotPoint;
		if(keepOneBefore && i>0) i--;
		double[][] newArray = new double[points.length][points[0].length-i];
		for(int j=0; j<points.length; j++)
			System.arraycopy(points[j],i,newArray[j],0,newArray[j].length);
		return newArray;
	}

	/**
	 * the data sets produced by the integrator can potentially be very large (e.g. the AIDS
	 * model). for the data to then be graphed in a decent amount of time, the data set needs to
	 * be pruned down to a more manageable size. it will first remove the initial zeros, then
	 * make the new data set to be ~1/factor the size it was before.
	 * @param points
	 * @param factor
	 * @param keepOneBefore
	 * @return
	 */
	public static double[][] pruneArray(double[][] points, int factor, boolean keepOneBefore){
		points = removeInitialZeros(points, keepOneBefore);
		int length =(int)((double)points[0].length/(double)factor+0.5);
		double[][] newArray = new double[points.length][length];
		for(int i=0; i<points.length; i++)
			for(int j=0; j<newArray[i].length; j++)
				newArray[i][j] = points[i][j*factor];

		/*ensure that the last point gets in the new array*/
		for(int i=0; i<newArray.length; i++){
			newArray[i][newArray[i].length-1] = points[i][points[i].length-1];
		}
		return newArray;
	}

	/**
	 * matrix multiplication. mXn * iXj = mXj iff n=i
	 * @param lhs
	 * @param rhs
	 * @return lhs*rhs
	 */
	public static double[][] multiplyMatricies(double[][] lhs, double[][] rhs){
		double[][] result;
		if(lhs.length == 0 || lhs[0].length == 0 || rhs.length == 0 || rhs[0].length == 0)
			return null;

		//mXn * iXj = mXj iff n=i
		if(lhs[0].length != rhs.length)
			return null;

		//mXn * iXj = mXj
		result = new double[lhs.length][rhs[0].length];

		//loop the resultant matrix for each entry
		for(int i=0; i<result.length; i++)
			for(int j=0; j<result[0].length; j++)

				//loop for each entry
				for(int k=0; k<lhs[0].length; k++)
					result[i][j] += lhs[i][k]*rhs[k][j];

		return result;
	}

	public static double[] multiplyMatricies(double[] lhs, double[][] rhs){
		double[][] a = new double[1][];
		a[0] = lhs;

		double[][] r = multiplyMatricies(a,rhs);
		return r[0];
	}

	public static double[] multiplyMatricies(double[][] lhs, double[] rhs){
		double[] result = new double[rhs.length];

		for(int i=0; i<lhs.length; i++)
			for(int j=0; j<lhs[0].length; j++)
				result[i] += lhs[i][j]*rhs[j];

		return result;
	}

	/**
	 * this method will "balance" the input <code>array</code> meaning that the
	 * returned array will sum to the <code>sum</code>
	 * keepIndex will be the index in the array that needs to remain the same.
	 */
	public static double[] balanceArray(double[] array, double sum, int keepIndex){
		double[] a = array.clone();
		double dF=0;
		for(int k=0; k<a.length; k++)
			dF += a[k];

		dF = sum - dF;
		int i = keepIndex;
		while(dF != 0.0){
			i = (i+1)%a.length;
			a[i] += dF;
			if(a[i] > sum){
				a[i] = sum;
				dF = 0.0;
				for(int j=0; j<a.length; j++)
					if(j != i) a[j] = 0.0d;
			} else if(a[i] < 0.0){
				dF = a[i];
				a[i] = 0.0;
			} else dF = 0.0;
		}
		return a;
	}

	/**
	 * this method will return the gaussian distribution value at <code>x</code>
	 * @param x where
	 * @param m mean
	 * @param v variance
	 * @return gaussian at x
	 */
	public static double getGaussian(double x, double m, double v){
		if(v==0)
			if(x!=m) return 0.0;
			else     return Double.MAX_VALUE;

		double arg = (x - m)*(x - m)/(2.0*v);
		if( (v>Double.MIN_VALUE) && (arg<0.9*Math.log(Double.MAX_VALUE)) )
			return Math.exp(-arg)/Math.sqrt(2.0*Math.PI*v);
		else
			if(x != m) return 0.0;
			else       return Double.MAX_VALUE;
	}

	/**
	 * this method creates a Gaussian curve (with mean <code>m</code>, variance <code>v</code>)
	 * which goes from 0 to scale.
	 * @param m mean
	 * @param v variance
	 * @param scale the max y value on the line
	 * @return
	 */
	public static double[][] buildGaussianCurve(double m, double v, double scale, double maxx){
		final int NUM_POINTS = 100;
		double[][] curve = new double[2][NUM_POINTS+2];
		double t = Math.sqrt(2.0*Math.PI*v);
		double x;
		boolean haveAve = false;//to make sure the mean point gets in
		for(double i=0; i<(NUM_POINTS+2); i++){
			x = i*maxx/NUM_POINTS;
			if(!haveAve && x>m){
				curve[0][(int)i] = m;
				curve[1][(int)i] = scale*t*getGaussian(m,m,v);
				haveAve = true;
				i++;
			}
			curve[0][(int)i] = x;
			curve[1][(int)i] = scale*t*getGaussian(x,m,v);
		}
		return curve;
	}

	/**
	 * this method will do a linear regression (unweighted, i.e. all points have an
	 * error of 1). the x and y arrays need to be the same length of course...
	 * @param x an array of x-values
	 * @param y the corresponding array of y-values
	 * @param min bounds on the line
	 * @param max
	 * @return a line with 2 points, one where x = min, and the other where x = max, unless
	 * it is a vertical line, in which case min and max are for y
	 */
	public static double[][] buildLinearRegression(double[] x, double[] y, double min, double max){
		double[][] line = new double[2][2];
		double xav=0, yav=0, ncov=0, nvar=0, diff=0, a, b;
		for(int i=0; i<x.length; i++){
			xav += x[i];
			yav += y[i];
		}
		xav /= x.length;
		yav /= y.length;

		for(int i=0; i<x.length; i++){
			diff = x[i] - xav;
			ncov += diff*(y[i] - yav);
			nvar += diff*diff;
		}

		/* y = a + bx */
				if(nvar != 0){
					b = ncov/nvar;
					a = yav - b*xav;
					line[0][0] = min;
					line[0][1] = max;
					line[1][0] = b*min + a;
					line[1][1] = b*max + a;
				} else {
					line[0][0] = xav;
					line[0][1] = xav;
					line[1][0] = min;
					line[1][1] = max;
				}
				return line;
	}

	/**
	 * this method will normalize an array
	 * @param a an array
	 * @return an array where the sum of the elements is 1.0
	 */
	public static double[] normalize(double[] a){
		double sum=0;
		boolean allnegative = true;
		for(int i=0; i<a.length; i++){
			if(a[i] > 0) allnegative = false;
			sum += Math.abs(a[i]);
		}
		if(sum == 0) sum = 1;
		if(allnegative) sum *= -1;
		for(int i=0; i<a.length; i++)
			a[i] /= sum;
		return a;
	}

	/**
	 * this method will build a "histogram" of the data in the <code>a</code> array.
	 * this is only used in Heritability right now. for the scaling in the distribution
	 * to match the distribution on a gaussian curve, the <code>scale</code> needs to look
	 * something like: <code>s*Math.sqrt(2.0*Math.PI*v)</code> where v is the variance on the gaussian,
	 * and s is the scaling on the gaussian (it's maximum value).
	 * @param a collection of data
	 * @param scale <code>s*Math.sqrt(2.0*Math.PI*v)</code>
	 * @return a line to be used with BasicPlotInfo
	 */
	public static double[][] buildDistributionCurve(double[] a, double scale){
		int max = (int)(getMaxValue(a)+0.5)+1;//addition of one to ensure that the line will go to zero
		int min = (int)(getMinValue(a)+0.5)-1;
		double[][] line = new double[2][max-min+1];

		for(int i=0; i<line[0].length; i++)
			line[0][i] = i+min;

		for(int i=0; i<a.length; i++)
			line[1][(int)(a[i] + 0.5)-min]++;

		line[1] = normalize(line[1]);
		for(int i=0; i<line[1].length; i++)
			line[1][i] *= scale;

		return line;
	}

	/**
	 * this method will return an array with frequency*size 1's in it,
	 * evenly distributed.
	 * @param size size of the array requested
	 * @param frequency frequency of 1's in the array
	 * @param rand a Random object to shuffle the 1's. it should be passed in so the
	 * caller can control the random seed. if null is passed in, this method will make a Random
	 * object for itself.
	 * @return
	 */
	public static int[] getFrequencyArray(int size, double frequency, Random rand){
		if(rand == null) rand = new Random(System.currentTimeMillis());
		int[] a = new int[size];
		int t1, t2;
		for(int i=0; i<frequency*size; i++)
			a[i] = 1;

		for(int i=0; i<size; i++){
			t1 = rand.nextInt(size);
			t2 = rand.nextInt(size);
			swap(a,t1,t2);
		}

		return a;
	}

	/**
	 * the method this was copied from, the one in java.lang.Random,
	 * didn't do anything with variance -- so this method will do it.
	 * should probably move this somehow into Routines... right now,
	 * this is only used in Heritability.
	 * @param m mean
	 * @param v variance
	 * @param rand if rand is null, one will be created
	 * @return
	 */
	public static double nextGaussian(double m, double v, Random rand) {
		// See Knuth, ACP, Section 3.4.1 Algorithm C.
		if(rand == null) rand = new Random(System.currentTimeMillis());
		double v1, v2, s;
		do {
			v1 = 2 * rand.nextDouble() - 1; // between -1 and 1
			v2 = 2 * rand.nextDouble() - 1; // between -1 and 1
			s = v1 * v1 + v2 * v2;
		} while (s >= 1 || s == 0);
		double multiplier = Math.sqrt(-2 * v * Math.log(s)/s);
		return m + v1 * multiplier;
	}

	/**
	 * this method will add the array a to the double array aa at pos.
	 * @param aa the arrays to be extended
	 * @param a the array to add
	 * @param pos where
	 * @return new array
	 */
	public static double[][] addArray(double[][] aa, double[] a, int pos){
		if(aa.length > pos){
			aa[pos] = a;
			return aa;
		} else {
			double[][] naa = new double[aa.length*2][];
			System.arraycopy(aa,0,naa,0,aa.length);
			naa[pos] = a;
			return naa;
		}
	}

	public static double[] addDouble(double[] aa, double a, int pos){
		if(aa.length > pos){
			aa[pos] = a;
			return aa;
		} else {
			double[] naa = new double[aa.length*2];
			System.arraycopy(aa,0,naa,0,aa.length);
			naa[pos] = a;
			return naa;
		}
	}

	/**
	 * Returns the index of the first value in A that is at least as large as n
	 * Assumes array is ascending order, and will always return a valid index.
	 */
	public static int binarySearchD(double[] A, double n) {
		int lowestPossibleLoc = 0;
		int highestPossibleLoc = A.length - 1;

		if (n < A[0])
			return 0;

		while (highestPossibleLoc > lowestPossibleLoc) {
			int middle = (lowestPossibleLoc + highestPossibleLoc) / 2;
			if (A[middle] < n) {
				lowestPossibleLoc = middle + 1;
			}
			else if (A[middle] == n) {
				return middle;
			}
			else {
				highestPossibleLoc = middle;
			}
		}
		//highest should be the same as lowest
		return highestPossibleLoc;
	}


}


















