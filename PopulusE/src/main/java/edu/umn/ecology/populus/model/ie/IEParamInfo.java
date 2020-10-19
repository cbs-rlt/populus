/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;


public class IEParamInfo implements BasicPlot {

	protected int plotType = 0;
	public static final int NvsT = 1;
	public static final int NvsN = 2;

	protected double[] initial = null;
	private boolean[] plotted;
	protected double runTime;
	protected String mainCaption = null;
	protected String xCaption = null;
	protected String yCaption = null;
	private boolean isDiscrete;
	private Integrator ig;
	private String[] eqs;
	private String[] names;
	private boolean isText;
	public IEParamInfo() {
	}

	public IEParamInfo(EquationCalculator ss, double[] N0, boolean[] used, boolean[] plotted,
			double runTime, boolean isDiscrete, boolean vsTime, boolean textOutput, String[] eqs,
			String[] names) throws IEException{
		this.isDiscrete = isDiscrete;
		this.runTime = runTime;
		this.plotted = plotted;
		this.isText = textOutput;
		this.eqs = eqs;
		this.names = names;
		plotType = vsTime ? NvsT : NvsN;
		if(getPlotCount() == 1 && plotType == NvsN)
			throw new IEException("N vs N must plot more than 1 equation.");
		this.initial = N0;
		mainCaption = "Interaction Engine Graph";
		if(vsTime){
			xCaption = "Time ( <i>t</i> )";
			yCaption = "";
			int ind=0;
			for(int i=0; i<N0.length; i++)
				if(plotted[i])
					yCaption += ColorScheme.getColorString(ind++)+" <i>"+names[i]+"</i></font>,";
			//remove the last comma
			if(yCaption.length() > 0);
			yCaption = yCaption.substring(0,yCaption.length()-1);
		}
		if(isDiscrete)
			ig = new Integrator(new IEDiscreteProc(ss));
		else
			ig = new Integrator(new IEDeriv(ss,plotted));
	}

	@Override
	public BasicPlotInfo getBasicPlotInfo() {
		BasicPlotInfo bp;
		int equations = initial.length;
		double[][][] points;
		double[] xlist;
		double[][] ylists;
		if (runTime < 0) {
			ig.record.ss = true;
			ig.record.interval = false;
		} else {
			ig.record.tf = runTime;
		}
		ig.integrate(initial, 0.0, runTime);
		xlist = ig.getX();
		ylists = ig.getY();

		if(isText){//modify this for which equations are used?
			String[] labels = new String[ylists.length+1];
			labels[0] = "t";
			for(int i=1; i<labels.length; i++)
				labels[i] = names[i-1];

			String[] headers = new String[ylists.length+1];
			if(isDiscrete)
				headers[0] = "Discrete equation text output.";
			else
				headers[0] = "Continuous equation text output.";
			for(int i=1; i<headers.length; i++)
				headers[i] = eqs[i-1];

			double[][] dataToWrite = new double[ylists.length+1][];
			dataToWrite[0] = xlist;
			for(int i=1; i<dataToWrite.length; i++)
				dataToWrite[i] = ylists[i-1];

			TextOutput.saveInteractionEngineText(dataToWrite,headers,labels,isDiscrete);
		}

		if(plotType == NvsT) {
			points = new double[getPlotCount()][2][];
			int lc=0;
			for(int j = 0; j<equations; j++)
				if(plotted[j]){
					points[lc][0] = xlist;
					points[lc][1] = ylists[j];
					lc++;
				}
			bp = new BasicPlotInfo(points, mainCaption, xCaption, yCaption);
			bp.setYMin(0.0);

			if(isDiscrete) bp.setIsDiscrete(true);
		} else if(plotType == NvsN) {
			points = new double[1][getPlotCount()][];
			String[] captions = new String[points[0].length];
			int lc=0;
			for(int j = 0; j<equations && lc<getPlotCount(); j++){
				if(plotted[j]){
					points[0][lc] = ylists[j];
					//captions[lc] = "<i>N" + (j+1)+"</i>";
					captions[lc] = "<i>"+names[j]+"</i>";
					lc++;
				}
			}

			double[][][] p = new double[1][getPlotCount()][];
			int ind=0;
			for(int i=0; i<ylists.length && ind<getPlotCount(); i++){
				if(plotted[i]) p[0][ind++] = ylists[i];
			}

			if(!isDiscrete && getPlotCount()<3){
				bp = new BasicPlotInfo(IsoclineAnalysis.addIsoclines(p,xlist,ig.getDerivative(),true));
				bp.setHasIsoclines(true);
			} else {
				bp = new BasicPlotInfo(p);
			}

			if(points[0].length == 3){
				bp.setMainCaption("Interaction Engine");
				bp.setXCaption(captions[0]);
				bp.setYCaption(captions[1]);
				bp.setZCaption(captions[2]);
				//bp.vsTimeChars = new String[] {captions[0],captions[1],captions[2]};//not sure why this is here at all... it will screw up the text output if used....
				bp.setGraphType(BasicPlotInfo.k3D);
				bp.setLabelsT(true);
			} else {
				bp.setMainCaption("Interaction Engine");
				bp.setXCaption(ColorScheme.getColorString(1)+captions[0]+"</font>");
				bp.setYCaption(ColorScheme.getColorString(0)+captions[1]+"</font>");

				if(!isDiscrete){
					PlotArrow.addArrow(bp,2);
					PlotArrow.addFletching(bp,2);
				}
			}
			if(isDiscrete) bp.setIsDiscrete(true);
		} else {
			bp = new BasicPlotInfo();
		}
		return bp;
	}

	int getPlotCount(){
		int count=0;
		for(int i=0; i<plotted.length; i++)
			if(plotted[i]) count++;
		if(count>3) return 3;
		return count;
	}
}

class IEDiscreteProc extends DiscreteProc {
	private EquationCalculator calc;

	public IEDiscreteProc(EquationCalculator ss) {
		calc = ss;
		this.numVariables = calc.numVariables(); //doesn't take equations unused into account
	}

	@Override
	public void v(long time, double[] y) {
		double[] initial;
		initial = calc.calculateValues(y,time);
		for(int i=0; i<y.length; i++)
			y[i] = initial[i];
	}
}
