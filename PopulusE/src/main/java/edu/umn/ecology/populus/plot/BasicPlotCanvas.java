/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;
import com.klg.jclass.chart.*;

import edu.umn.ecology.populus.constants.*;
import edu.umn.ecology.populus.core.PopPreferencesStorage;
import edu.umn.ecology.populus.visual.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.AbstractXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

/**
 * A GUI Panel that displays a chart. Currently, it displays a JCChart in the Center,
 * with <code>HTMLLabel</code>s representing the axis names on the sides.
 * The Panel is worthless without setting the BasicPlotInfo needed to be rendered.
 * This can be done by either the constructor or the the setBPI method. <BR>
 *
 * The analagous 3D class to this one is ThreeD.
 */

public class BasicPlotCanvas extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -9029148726700689019L;

	//HTMLLabel mainCaption, xCaption, yCaption;
	HTMLMultiLabel mainCaption, xCaption, yCaption;

	//double xScale, yScale;
	//double bigMod;
	//double littleMod;
	//int pWidth, pHeight, pPTop, pPBottom, pPLeft, pPRight;
	MacroLayout borderLayout1 = new MacroLayout();

	//BorderLayout borderLayout1 = new BorderLayout();
	/**chart used to be any component to use the previous version, but not anymore*/
	JCChart chart;
	ChartPanel jfchartpanel;
	BasicPlotInfo info = null;
	Color bgcolor = null;

	public void setBPI( BasicPlotInfo newInfo ) {
		if( newInfo != null ) {
			info = newInfo;
			if (PopPreferencesStorage.isUseJFreeClass()) {
				info.styleJFree(jfchartpanel.getChart());
			} else {
				info.styleJC( chart );
			}
			mainCaption.setText( info.getMainCaption() );
			xCaption.setText( info.getXCaptions() );
			yCaption.setText( info.getYCaptions() );
		}
	}

	public BasicPlotCanvas( BasicPlotInfo info ) {
		this.info = info;
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void setBackground( Color c ) {
		bgcolor = c;
		setBGColor();
	}

	/** The popup menu on the output screen accesses this function to dynamically modify
     the graph. When "optimized," the case values should be turned into constants somewhere
     for easier modification.*/

	public void displayChartOptionScreen( int whatOption ) {
		if (PopPreferencesStorage.isUseJFreeClass()) {
			//TODO

		} else {
			JCAxis h = chart.getChartArea().getHorizActionAxis();
			JCAxis v = chart.getChartArea().getVertActionAxis();
			switch( whatOption ) {
			case MenuOptions.kCoarserGrid:
				coarserGrid();
				break;

			case MenuOptions.kFinerGrid:
				finerGrid();
				break;

			case MenuOptions.kClearGrid:
				h.setGridVisible( false );
				v.setGridVisible( false );
				break;

			case MenuOptions.kOptionScreen:
				/*this is kind of a "hacked" way of bringing up the customizer screen because unfortunately, the
	            JClass people didn't provide an easier way to do this. but this works, so whatever.*/
				chart.mousePressed(new MouseEvent(this,MouseEvent.MOUSE_PRESSED,System.currentTimeMillis(),InputEvent.SHIFT_MASK,0,0,1,false));
				break;

			case MenuOptions.kReset:
				h.setGridVisible( false );
				v.setGridVisible( false );
				chart.reset();
				break;
			}
		}
	}

	void finerGrid(){
		JCAxis h = chart.getChartArea().getHorizActionAxis();
		JCAxis v = chart.getChartArea().getVertActionAxis();

		if( !h.isGridVisible() ) {
			h.setGridSpacingIsDefault( true );
			v.setGridSpacingIsDefault( true );
			h.setGridVisible( true );
			v.setGridVisible( true );
		} else {
			h.setGridSpacing( h.getGridSpacing() / 2 );
			v.setGridSpacing( v.getGridSpacing() / 2 );
		}
	}

	void coarserGrid(){
		JCAxis h = chart.getChartArea().getHorizActionAxis();
		JCAxis v = chart.getChartArea().getVertActionAxis();

		if( !h.isGridVisible() ) {
			h.setGridSpacingIsDefault( true );
			v.setGridSpacingIsDefault( true );
			h.setGridVisible( true );
			v.setGridVisible( true );
		}
		else {
			h.setGridSpacing( h.getGridSpacing() * 2 );
			v.setGridSpacing( v.getGridSpacing() * 2 );
		}
	}

	public BasicPlotCanvas() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private void setBGColor() {
		int trans;
		int r, b, g;
		if( bgcolor == null ) {
			return ;
		}

		//if the background is too dark, then we need a new color for the
		//text so it can be visible.
		trans = bgcolor.getRGB();
		r = bgcolor.getRed();
		b = bgcolor.getBlue();
		g = bgcolor.getGreen();
		if( r < 80 && b < 80 && g < 80 ) {
			trans += 256 * 256 * 256 + 1;
			trans *= -1;
		}
		else {
			trans = -256 * 256 * 256;
		}
		Color transColor = new Color( trans );
		if( chart != null ) {
			chart.setBackground( bgcolor );
			chart.setForeground( new Color( trans ) );
		}
		if (jfchartpanel != null) {
			jfchartpanel.setBackground( bgcolor );
			//TODO: Need to understand this versus chart bg color
			jfchartpanel.getChart().setBackgroundPaint(bgcolor);
		}
		if( mainCaption != null ) {
			mainCaption.setBackground( bgcolor );
			mainCaption.setDefaultColor( transColor );
		}
		if( xCaption != null ) {
			xCaption.setBackground( bgcolor );
			xCaption.setDefaultColor( new Color( trans ) );
		}
		if( yCaption != null ) {
			yCaption.setBackground( bgcolor );
			yCaption.setDefaultColor( new Color( trans ) );
		}
	}

	private void jbInit() throws Exception {
		/*
      if( info != null ) {
         mainCaption = new HTMLLabel( info.getMainCaption() );
         xCaption = new HTMLLabel( info.getXCaptions()[0] );
         yCaption = new HTMLLabel( info.getYCaptions()[0] );
         yCaption.setDirection( HTMLLabel.DOWN_TO_UP );
      } else {
         mainCaption = new HTMLLabel( "Main Caption" );
         xCaption = new HTMLLabel( "X Caption" );
         yCaption = new HTMLLabel( "Y Caption" );
      }
		 */
		//*
		if( info != null ) {
			mainCaption = new HTMLMultiLabel( info.getMainCaption() );
			xCaption = new HTMLMultiLabel( info.getXCaptions() );
			yCaption = new HTMLMultiLabel( info.getYCaptions() );
			yCaption.setDirection( HTMLConstants.DOWN_TO_UP );
		} else {
			mainCaption = new HTMLMultiLabel( "Main Caption" );
			xCaption = new HTMLMultiLabel( "X Caption" );
			yCaption = new HTMLMultiLabel( "Y Caption" );
			yCaption.setDirection( HTMLConstants.DOWN_TO_UP );
		}
		setLayout( borderLayout1 );
		if (PopPreferencesStorage.isUseJFreeClass()) {
			NumberAxis yAxis = new NumberAxis(null);
			NumberAxis xAxis = new NumberAxis(null);
			xAxis.setAutoRangeIncludesZero(false);

			AbstractXYItemRenderer renderer = null;
			if (info.isBarChart) {
				renderer = new XYBarRenderer();
			} else {
				renderer = new XYLineAndShapeRenderer(true, false);
			}

			XYPlot plot = new XYPlot(null, xAxis, yAxis, renderer);
			plot.setOrientation(PlotOrientation.VERTICAL);

			JFreeChart jfchart = new JFreeChart(null, null, plot, false);
			jfchartpanel = new ChartPanel(jfchart);
			plot.setBackgroundPaint(ColorScheme.bG);

			info.styleJFree(jfchart);
			add( jfchartpanel, BorderLayout.CENTER );
		} else {
			chart = new JCChart( JCChart.PLOT );
			info.styleJC( chart );
			chart.setBackground( ColorScheme.bG );
			chart.setAllowUserChanges( true );
			chart.setTrigger( 0, new EventTrigger( InputEvent.SHIFT_MASK, EventTrigger.CUSTOMIZE ) );
			chart.setTrigger( 1, new EventTrigger( InputEvent.BUTTON1_MASK, EventTrigger.ZOOM ) );
			chart.setResetKey( 'r' );
			chart.addMouseListener( new java.awt.event.MouseAdapter()  {

				@Override
				public void mouseClicked( java.awt.event.MouseEvent e ) {
					if( ( e.getModifiers() & InputEvent.META_MASK ) != 0 ) {
						info.setAxis(chart);
						chart.reset();
					}
				}
			} );
			chart.setCursor( new Cursor( Cursor.CROSSHAIR_CURSOR ) );
			chart.setWarningDialog(false);
			add( chart, BorderLayout.CENTER );
		}
		setBGColor();
		add( mainCaption, BorderLayout.NORTH );
		add( xCaption, BorderLayout.SOUTH );
		add( yCaption, BorderLayout.WEST );
	}
}
