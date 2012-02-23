package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.core.Model;
import edu.umn.ecology.populus.plot.coloredcells.CellController;
import java.util.*;

public class SDModel extends Model {
	SDPanel sdp = new SDPanel();
	public SDModel() {
		this.setModelInput(sdp);
	}
	protected void simpleUpdateOutput() {
		ParamInfo pi = getData();

		if(pi instanceof BasicPlot){//it is a 2d plot
			BasicPlotInfo data = ((BasicPlot)pi).getBasicPlotInfo();
			if ((getModelOutput() != null) && (getModelOutput() instanceof BasicPlotOutputPanel)) {
				((BasicPlotOutputPanel) getModelOutput()).getBPC().setBPI(data);
			} else {
				setModelOutput(new BasicPlotOutputPanel(data));
			}
		} else {//it is a cell panel
			SDCellParamInfo nbsspi = (SDCellParamInfo) pi;
			if ((getModelOutput() != null) && (getModelOutput() instanceof CellController)) {
				((CellController) getModelOutput()).changeCellFunction(nbsspi);
			} else {
				setModelOutput(new CellController(nbsspi));
			}
		}
	}
	protected ParamInfo getData() {
		outputFrame.setVisible(true);
		return getModelInput().getParamInfo();
	}
	public static String getModelName() {
		return "Spatial Dilemmas";
	}
	public Object getModelHelpText() {
		return "SDHELP";
	}
	protected String getHelpId() {
		return "sd.overview";
	}
	protected boolean isSwitchable(){
		return true;
	}
	protected void switchOutput(){
		sdp.switchOutputType();
		updateOutput();
		if(getModelOutput() instanceof CellController)
			((CellController) getModelOutput()).setPaused(true);
	}
}