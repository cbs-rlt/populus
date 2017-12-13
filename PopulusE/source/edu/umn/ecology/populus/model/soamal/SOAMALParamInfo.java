/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.soamal;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class SOAMALParamInfo implements BasicPlot {
	public static final int PvsT = 1;
	public static final int PvsP = 2;
	public static final int DeFi = 3;

	final int plotType, gens, dim;
	final double[][] wij;
	double[] freq;
	int[] plotted;
	int[] used;

	@Override
	public BasicPlotInfo getBasicPlotInfo() {
		BasicPlotInfo bpi = new BasicPlotInfo();
		double[][][] points = null;
		double[][] data = new double[gens+1][dim];
		double[] wi = new double[dim];
		double wbar=0;
		final String mCap1 = "Selection on a Multi-Allelic Locus";
		final String xCap1 = "Generations (<i>t</i>)";
		String yCap1 = "Allelic Frequency (  ";
		final String mCap2 = "Spatial Representation of Frequencies";
		final String xCap2 = "<b><i>P</i><sub>1</>";
		final String yCap2 = "<b><i>P</i><sub>2</>";
		final String zCap2 = "<b><i>P</i><sub>3</>";

		if(plotted.length == 0) return null;

		for(int i=0; i<=gens; i++){
			data[i] = freq.clone();
			wbar = 0.0d;

			for(int ii=0; ii<dim; ii++){
				wi[ii] = 0.0d;
				for(int jj=0; jj<dim; jj++){
					wi[ii] += freq[jj]*wij[ii][jj];
				}
				wbar += freq[ii]*wi[ii];
			}

			for(int ii=0; ii<dim; ii++)
				freq[ii] *= wi[ii]/wbar;
		}

		switch (plotType){
		case PvsT:
			points = new double[plotted.length][2][gens+1];
			int[] a;
			a = plottedTransform(plotted,used);
			for(int j=0; j<plotted.length; j++){
				for(int i=0; i<=gens; i++){
					points[j][0][i] = i;
					points[j][1][i] = data[i][a[j]];
				}
				yCap1 += ColorScheme.getColorString(j)+"<i>"+(plotted[j]+1)+"</>" + (j==plotted.length-1?"":", ");
			}
			yCap1 += " )";
			bpi = new BasicPlotInfo(points,mCap1,xCap1,yCap1);
			bpi.setIsFrequencies(true);
			break;
		case PvsP:
			if(plotted.length > 3){
				edu.umn.ecology.populus.fileio.Logging.log("Too many to graphs");
				break;
			}
			points = new double[1][plotted.length][gens+1];
			for(int i=0; i<=gens; i++){
				for(int j=0; j<plotted.length; j++)
					points[0][j][i] = data[i][plotted[j]];
			}
			bpi = new BasicPlotInfo(points,mCap2,xCap2,yCap2);
			if(plotted.length == 3){
				bpi.setZCaption(zCap2);
				bpi.setGraphType(BasicPlotInfo.k3D);
				bpi.setIsDiscrete(true);
			}
			//these additions don't seem to be much use, the ends are on the edge of the graph anyway...
			//PlotArrow.addArrow( bpi, 0 );
			//PlotArrow.addFletching( bpi, 0 );
			break;
		case DeFi:
			points = new double[1][3][gens+1];
			for(int i=0; i<=gens; i++){
				for(int j=0; j<plotted.length; j++)
					points[0][j][i] = data[i][plotted[j]];
			}
			bpi = new BasicPlotInfo(points,mCap1,xCap2,yCap2);
			bpi.setZCaption(zCap2);
			bpi.setGraphType(BasicPlotInfo.kDeFinetti);
			break;
		}
		return bpi;
	}

	int[] plottedTransform(int[] p, int[] u){
		int[] a = new int[p.length];
		int c=0;
		for(int i=0; c<a.length; i++){
			if(p[c] == u[i]){
				a[c] = i;
				c++;
			}
		}
		return a;
	}

	public SOAMALParamInfo(double[][] data, double[] initialFreq, int[] plotted, int gens, int[] used, int plotType) {
		this.wij = data;
		this.freq = initialFreq;
		this.plotted = plotted;
		this.used = used;
		this.gens = gens;
		this.plotType = plotType;
		this.dim = data.length;
	}
}