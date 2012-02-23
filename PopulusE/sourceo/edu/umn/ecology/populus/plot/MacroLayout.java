package edu.umn.ecology.populus.plot;

import java.awt.*;

/**
 * This Layout is used to layout a chart along with it's main title and x/y titles.
 * Assumes that the container to lay out is a BasicPlotCanvas component. 
 */
public class MacroLayout extends BorderLayout {

	public MacroLayout() {
	}

	public void layoutContainer(Container c) {
		edu.umn.ecology.populus.plot.BasicPlotCanvas chart;
		if (!(c instanceof edu.umn.ecology.populus.plot.BasicPlotCanvas)) {
			throw new Error("Can't layout non-BasicPlotCanvas object in MacroLayout!");
		}
		chart = (edu.umn.ecology.populus.plot.BasicPlotCanvas) c;
		int yy, ymargins, fontsize;
		double xscale, xscale2, xstep, x, yscale, ystep, y, v, sv, yspan;
		Font smallFont, bigFont;
		if (chart == null || chart.info == null)
			return;
		yy = c.getSize().height;
		ymargins = yy / 15 + 10;
		if (ymargins < 30) {
			ymargins = 30;
		}
		fontsize = ymargins / 2;
		if (fontsize < 7) {
			fontsize = 7;
		}
		//Set up font size - Can't use BOLD, since it ruins the unicode look
		String fontString = "Serif";
		if(System.getProperty("os.name").startsWith("Mac")){
			fontString = "SansSerif";
		}
		
		smallFont = new Font(fontString, Font.PLAIN, fontsize);
		bigFont = new Font(fontString, Font.PLAIN, fontsize * 3 / 2);
		chart.xCaption.setDefaultFont(smallFont);
		chart.yCaption.setDefaultFont(smallFont);
		chart.mainCaption.setDefaultFont(bigFont);
		synchronized (c.getTreeLock()) {
			Insets insets = c.getInsets();
			int top = insets.top;
			int bottom = c.getHeight() - insets.bottom;
			int left = insets.left;
			int right = c.getWidth() - insets.right;
			int leftIndent = 0;

			boolean ltr = c.getComponentOrientation().isLeftToRight();
			Component comp = null;

			if ((comp=chart.yCaption) != null) {
				comp.setSize(comp.getWidth(), bottom - top);
				Dimension d = comp.getPreferredSize();
				leftIndent = d.width;
				left += leftIndent;
			}
			if ((comp=chart.mainCaption) != null) {
				comp.setSize(right - left, comp.getHeight());
				Dimension d = comp.getPreferredSize();
				comp.setBounds(left, top, right - left, d.height);
				top += d.height;
			}
			if ((comp=chart.xCaption) != null) {
				comp.setSize(right - left, comp.getHeight());
				Dimension d = comp.getPreferredSize();
				comp.setBounds(left, bottom - d.height, right - left, d.height);
				bottom -= d.height;
			}
			chart.yCaption.setBounds(left - leftIndent, top, chart.yCaption.getPreferredSize().width, bottom - top);
			if ((comp=chart.chart) != null) {
				comp.setBounds(left, top, right - left, bottom - top);
			}
		}
	}
}