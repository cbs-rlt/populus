package edu.umn.ecology.populus.model.nbss;

import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class NBSSGraphParamInfo extends ParamInfo implements BasicPlot{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6979872204518008120L;
	NBSSCellParamInfo cpi;
	boolean avg, isup;
	public static final String kMainCap = "Spatial-Structure Populations";
	public static final String kXCap = "Generation";
	String kYCap = "Population ( "+ColorScheme.getColorString(0)+"<i>N</>, "
				+ColorScheme.getColorString(1)+"<i>P</> ) ";

	public NBSSGraphParamInfo(NBSSCellParamInfo pi, boolean plotAverage, boolean isUpdate){
		cpi = pi;
		avg = plotAverage;
		isup = isUpdate;
		if(avg) kYCap = "Average "+kYCap;
		else kYCap = "Total "+kYCap;
	}

	public BasicPlotInfo getBasicPlotInfo(){
		int gens = cpi.runInterval;
		if(!isup){
			cpi.initialF();
			for(int i=0; i<gens; i++)
				cpi.f();
		}
		BasicPlotInfo bpi = new BasicPlotInfo(cpi.getTotals(avg),kMainCap,kXCap,kYCap);
		return bpi;
	}
}
