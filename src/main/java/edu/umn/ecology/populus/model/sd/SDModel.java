/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sd;

import edu.umn.ecology.populus.core.Model;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;
import edu.umn.ecology.populus.plot.BasicPlotOutputPanel;
import edu.umn.ecology.populus.plot.ParamInfo;
import edu.umn.ecology.populus.plot.coloredcells.CellController;

public class SDModel extends Model {
    final SDPanel sdp = new SDPanel();

    public SDModel() {
        this.setModelInput(sdp);
    }

    @Override
    protected void simpleUpdateOutput() {
        ParamInfo pi = getData();

        if (pi instanceof BasicPlot) {//it is a 2d plot
            BasicPlotInfo data = ((BasicPlot) pi).getBasicPlotInfo();
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

    @Override
    protected ParamInfo getData() {
        outputFrame.setVisible(true);
        return getModelInput().getParamInfo();
    }

    public static String getModelName() {
        return "Spatial Dilemmas";
    }

    @Override
    public Object getModelHelpText() {
        return "SDHELP";
    }

    @Override
    protected String getHelpId() {
        return "sd.overview";
    }

    @Override
    protected boolean isSwitchable() {
        return true;
    }

    @Override
    protected void switchOutput() {
        sdp.switchOutputType();
        updateOutput();
        if (getModelOutput() instanceof CellController)
            ((CellController) getModelOutput()).setPaused(true);
    }
}