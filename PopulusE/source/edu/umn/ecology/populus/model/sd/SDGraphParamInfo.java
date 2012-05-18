package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class SDGraphParamInfo extends ParamInfo implements BasicPlot{
	SDCellParamInfo cpi;
	public static final String kMainCap = "Spatial Dilemmas Frequency Time Trajectory";
	public static final String kXCap = "Generation";
	String kYCap = "Frequency ( "+ColorScheme.getColorString(0)+"<i>C</>, "
				+ColorScheme.getColorString(1)+"<i>D</> ) ";
	boolean isup;

	public SDGraphParamInfo(SDCellParamInfo pi, boolean isUpdate){
		cpi = pi;
		isup = isUpdate;
	}

	public BasicPlotInfo getBasicPlotInfo(){
		int gens = cpi.runInterval;
		if(!isup){
			cpi.initialF();
			for(int i=0; i<gens; i++)
				cpi.f();
		}
		BasicPlotInfo bpi = new BasicPlotInfo(cpi.getFrequencies(),kMainCap,kXCap,kYCap);
		return bpi;
	}
}