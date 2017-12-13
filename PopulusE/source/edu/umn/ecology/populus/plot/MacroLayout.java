/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;

import java.awt.*;

/**
 * This Layout is used to layout a chart along with it's main title and x/y titles.
 * Assumes that the container to lay out is a BasicPlotCanvas component.
 *
 * This adds onto BorderLayout and changes the fonts of the captions dynamically.
 * We really need to be smarter about using the SOUTH, NORTH, WEST, CENTER, etc.
 */
public class MacroLayout extends BorderLayout {

	/**
	 *
	 */
	private static final long serialVersionUID = 673990963046959401L;

	public MacroLayout() {
	}

	@Override
	public void layoutContainer(Container c) {
		edu.umn.ecology.populus.plot.BasicPlotCanvas chart;
		if (!(c instanceof edu.umn.ecology.populus.plot.BasicPlotCanvas)) {
			throw new Error("Can't layout non-BasicPlotCanvas object in MacroLayout!");
		}
		chart = (edu.umn.ecology.populus.plot.BasicPlotCanvas) c;
		int yy, ymargins, fontsize;
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
		try {
			if(System.getProperty("os.name").startsWith("Mac")){
				fontString = "SansSerif";
			}
		} catch (Exception e) {
			//TODO
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
			if ((comp=chart.yCaption) != null) {
				//needed prior bottom and top calculations, hence not in the prior structure.
				chart.yCaption.setBounds(left - leftIndent, top, chart.yCaption.getPreferredSize().width, bottom - top);
			}
			if ((comp=getLayoutComponent(c, CENTER)) != null) {
				comp.setBounds(left, top, right - left, bottom - top);
			}
		}
	}
}