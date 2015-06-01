package edu.umn.ecology.populus.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

/**
 * Chart renderer that adds special code to deal with DirectionalPlotTerminus objects.
 * 
 * @author Lars
 */
public class ChartRendererWithOrientatedShapes extends XYLineAndShapeRenderer {
	private static final long serialVersionUID = 8217901543433031346L;
	
	public ChartRendererWithOrientatedShapes(BasicPlotInfo bpi) {
		super(true, false);
		this.setBpInfo(bpi);
	}
	
	private BasicPlotInfo bpInfo;

	public BasicPlotInfo getBpInfo() {
		return bpInfo;
	}

	public void setBpInfo(BasicPlotInfo bpInfo) {
		this.bpInfo = bpInfo;
	}

	@Override
	protected void drawSecondaryPass(Graphics2D g2, XYPlot plot, 
            XYDataset dataset, int pass, int series, int item,
            ValueAxis domainAxis, Rectangle2D dataArea, ValueAxis rangeAxis,
            CrosshairState crosshairState, EntityCollection entities) {
		
		//Orient the shapes first
		double graphicsRatio = dataArea.getHeight()/dataArea.getWidth();
		double dataRatio = plot.getRangeAxis().getRange().getLength() / plot.getDomainAxis().getRange().getLength();
		double ratio = graphicsRatio / dataRatio;
		bpInfo.updateDirectedSymbolsJFC(this, ratio);
		
		//Now call this to do the rest
		super.drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
    }
}
