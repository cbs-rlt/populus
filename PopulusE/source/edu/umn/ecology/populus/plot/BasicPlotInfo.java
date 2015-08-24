/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;
import com.klg.jclass.chart.*;

import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.plotshapes.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.fileio.Logging;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.*;

import org.jfree.chart.ChartTheme;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import Jama.EigenvalueDecomposition;

/**
  * this class is meant to hold all the data necessary for graphing a data set. the vector <code>lines</code>
  * is meant to hold all the stylization information like line color, line style (e.g. dashed), symbol color, etc.
  * this class also hold information like on whether the chart should be a log-plot or not. one of the more
  * important parts of this class is the caption information and the viewing bounds information.
  * as far as the bounds go, JCChart actually does a pretty good job of coming up with default bounds, so it is
  * usually best to let it just handle the bounds. however some plots, like frequency plots, have bounds that should
  * be enforced.
  */
public class BasicPlotInfo extends ParamInfo
	implements ChartDataModel, //getNumSeries, getYSeries, getXSeries
	JCChartListener  //changeChart, paintChart
{
	private static final long serialVersionUID = -882044708806321402L;

	ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.plot.Res" );

   public static final int k2D = 0;
   public static final int k3D = 1;
   public static final int kDeFinetti = 2;
   public static final int kSpecial = 3;
   public static final int kEigenSystem = 4;

   /**
    * it is assumed that a graph will be a 2D plot be default. if this needs to be changed however,
    * the different types available are listed above.
    */
   public int outputType = k2D;

   /* only stick these in if they will be used, the check is if this is null. if these are set, then the
      text output behaves a different way than otherwise.*/
   public String[] vsTimeChars = null;

   /*Symbol Styles*/
   public static final int NONE      = JCSymbolStyle.NONE;
   //public static final int SQUARE    = JCSymbolStyle.BOX;
   public static final int DOTS      = -1;
   public static final int ARROW     = -2;
   public static final int FLETCHING = -3;

   /*these objects are for turning a plot line into an arrow*/
   protected Vector<PlotTerminus> plotTerminusList = new Vector<PlotTerminus>();

   /*Line Styles*/
   public static final int CONTINUOUS  = JCLineStyle.SOLID;        // 1
   public static final int DISCRETE    = JCLineStyle.SHORT_DASH;   // 3        5x 10o 
   public static final int DASHED      = JCLineStyle.LONG_DASH;    // 2       10x 10o
   public static final int LSL_DASH    = JCLineStyle.LSL_DASH;     // 4       10x 10o  5x 10o
   public static final int DASH_DOT    = JCLineStyle.DASH_DOT;     // 5       10x 10o  1x 10o
   public static final int NO_LINE     = JCLineStyle.NONE;         // 0

   /*Usage: data[lineNo][0 for x, 1 for y, 2 for z][point number]*/
   double[][][] data;

   /** 
    * lines give styling info about the same lines in data.
    * 
    * TODO - if we want to remove dependencies to JCChart, we will need to create a class that
    *  functions as JCChartStyle here, and contains:
    *      LineStyle  (e.g., CONTINUOUS)
    *      LineWidth
    *      LineColor
    *      SymbolShape
    *      SymbolSize
    *      SymbolColor
    *      
    */
   private Vector<JCChartStyle> lines = new Vector<JCChartStyle>();

   /*these are a collection of flags*/
   private boolean hasIso         = false;
   private boolean isDiscrete     = false;
   private boolean labelT         = true;
   private boolean startGridded   = false;
   private boolean zIsDiscrete    = false;
   private boolean xIsDiscrete    = false;
   public boolean  isBarChart     = false;
   public boolean  isFrequencies  = false;
   public boolean  isLogPlot      = false;
   private boolean isLive         = false;
   private boolean axisReset      = false;

   /*this is used in the aspg matrix output*/
   private Object special = null;

   private String[] xCaption, yCaption, mainCaption;
   private String zCaption; //Still need to make Free MultiLine Label
   /* List of InnerLabel objects for inside labels */
   private Vector<InnerLabel> innerLabels = new Vector<InnerLabel>();
   private double xMin, xMax;
   private double yMin, yMax;
   private double zMin, zMax;
   private double xAxisMin, xAxisMax;
   private double yAxisMin, yAxisMax;
   private double zAxisMin, zAxisMax;
   public boolean xMinSet = false;
   public boolean yMinSet = false;
   public boolean zMinSet = false;
   public boolean xMaxSet = false;
   public boolean yMaxSet = false;
   public boolean zMaxSet = false;

   /*these flags control whether or not to just let JCChart to determine the viewing bounds
   regardless of what xMin, xMax have been set to*/
   //public boolean hasXMax = false, hasYMax = false, hasZMax = false;

   
   	private class JFCXYAdapter extends AbstractXYDataset implements IntervalXYDataset {
	    private static final long serialVersionUID = -1226686444066513897L;
	    private double barWidth = 0.1;
	    
	    public JFCXYAdapter() {
	    	updateBarWidth(0);
	    }

	    public void updateBarWidth(int series) {
	    	if (data[series][0].length > 1) {
	    		double dx = data[series][0][1] - data[series][0][0];
	    		this.barWidth = 3.0 * dx / 4.0;
	    	}
	    }
	    
		@Override
		public int getItemCount(int series) {
			//Logging.log("In getItemCount " + series);
			return data[series][0].length;
		}
	
		@Override
		public Number getX(int series, int item) {
			//Logging.log("In getX " + series + ", " + item + " = " + data[series][0][item]);
			return data[series][0][item];
		}
	
		@Override
		public Number getY(int series, int item) {
			//Logging.log("In getY " + series + ", " + item + " = " + data[series][1][item]);
			return data[series][1][item];
		}
	
		@Override
		public int getSeriesCount() {
			//Logging.log("In getSeriesCount " + data.length);
			return data.length;
		}

		@Override
		public String getSeriesKey(int arg0) {
			//TODO - I think this is the name of the series, will this implementation suffice?
			return "" + arg0;
		}

		@Override
		public Number getStartX(int series, int item) {
			return getXValue(series, item) - barWidth / 2.0;
		}

		@Override
		public double getStartXValue(int series, int item) {
			return getStartX(series, item).doubleValue();
		}

		@Override
		public Number getEndX(int series, int item) {
			return getXValue(series, item) + barWidth / 2.0;
		}

		@Override
		public double getEndXValue(int series, int item) {
			return getEndX(series, item).doubleValue();
		}

		@Override
		public Number getStartY(int series, int item) {
			return getY(series, item);
		}

		@Override
		public double getStartYValue(int series, int item) {
			return getY(series, item).doubleValue();
		}

		@Override
		public Number getEndY(int series, int item) {
			return getY(series, item);
		}

		@Override
		public double getEndYValue(int series, int item) {
			return getY(series, item).doubleValue();
		}
   }
   	
   
   /***************
   * CONSTRUCTORS *
   ***************/
   public BasicPlotInfo() {}

   /**
    * this is used in ASPGParamInfo for the matrix style output
    * @param special
    */
   public BasicPlotInfo( Object special ) {
      if(special instanceof EigenvalueDecomposition){
         outputType = kEigenSystem;
      } else {
         outputType = kSpecial;
      }
      this.special = special;
   }

   /**
    * use this method if you want to set the data in one place, but will
    * add the captions later.
    */
   public BasicPlotInfo( double[][][] dataPoints ) {
      setData( dataPoints );
   }

   /**
    * Constructor for 2D graph, giving data and captions.
    * This constructor is probably the most used of all of them.
    */
   public BasicPlotInfo( double[][][] dataPoints, String mC, String xC, String yC ) {
      this.xCaption = new String[] {xC};
      this.yCaption = new String[] {yC};
      this.mainCaption = new String[] {mC};
      setData( dataPoints );
   }

   /**
    * if this constructor is used, then nothing else needs to happen to produce a 3D graph.
    */
   public BasicPlotInfo( double[][][] dataPoints, String mC, String xC, String yC, String zC ) {
      this.xCaption = new String[] {xC};
      this.yCaption = new String[] {yC};
      this.zCaption = zC;
      this.mainCaption = new String[] {mC};
      outputType = k3D;
      setData( dataPoints );
   }
   
   private static float[] getDashArray(int type) {
	   final float[][] ttable = {
		   { 0.0f,  1.0f},   			//NONE   0
		   null,           				//SOLID  1
		   {10.0f, 10.0f},				//LONG   2
		   { 5.0f, 10.0f},				//SHORT  3
		   {10.0f, 10.0f, 5.0f, 10.0f},	//LSL    4
		   {10.0f, 10.0f, 1.0f, 10.0f}, //DASH.  5
	   };
	   
	   return ttable[type];
   }

   public ChartTheme getJFreeChartTheme() {
	   class PopChartTheme implements ChartTheme {
		   private BasicPlotInfo bpiRef;
		   public PopChartTheme(BasicPlotInfo bpi) {
			   this.bpiRef = bpi;
		   }
		   public void apply(JFreeChart chart) {
			   JFCXYAdapter jfca = new JFCXYAdapter();
			   XYPlot plot = chart.getXYPlot();
			   plot.setDataset(jfca);

			   if (isLogPlot) {
				   plot.setRangeAxis(new LogarithmicAxis(""));
			   }
			   if (isFrequencies) {
				   ValueAxis va = plot.getRangeAxis();
				   if (va instanceof NumberAxis) {
					   NumberAxis na = (NumberAxis) va;
					   na.setTickUnit(new NumberTickUnit(0.1));
				   } else {
					   Logging.log("Range Axis is not NumberAxis, why?", Logging.kWarn);
				   }
			   }

			   if(xMinSet) plot.getDomainAxis().setLowerBound(xAxisMin);
			   if(xMaxSet) plot.getDomainAxis().setUpperBound(xAxisMax);
			   if(yMinSet) plot.getRangeAxis().setLowerBound(yAxisMin);
			   if(yMaxSet) plot.getRangeAxis().setUpperBound(yAxisMax);

			   //TODO - just use this renderer
			   plot.setRenderer(new ChartRendererWithOrientatedShapes(bpiRef));
			   
			   XYItemRenderer r = plot.getRenderer();
			   //		   AbstractXYItemRenderer r = plot.getRenderer();
			   if (r instanceof XYLineAndShapeRenderer) {
				   XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
				   for( int i = 0;i < getNumSeries();i++ ) {
					   //Set Line
					   renderer.setSeriesPaint(i, getLineColor(i));
					   renderer.setSeriesStroke(i, getLineStroke(i));

					   //Set Symbol
					   renderer.setSeriesFillPaint(i, getSymbolColor(i));
					   renderer.setSeriesOutlinePaint(i, getSymbolColor(i));

					   Shape shape = getSymbolShape(i);
					   if (shape != null) {
						   renderer.setSeriesShape(i, shape);
						   renderer.setSeriesShapesFilled(i, isSymbolOpaque(i));
						   renderer.setUseFillPaint(true);
						   renderer.setUseOutlinePaint(true);
						   renderer.setSeriesShapesVisible(i, true);
					   }
				   }
			   } else if (r instanceof XYBarRenderer) {
				   XYBarRenderer barRenderer = (XYBarRenderer) r;
				   barRenderer.setBarPainter(new StandardXYBarPainter());
			   } else {
				   Logging.log("Unknown renderer type: " + r.getClass(), Logging.kWarn);
			   }

			   //inner labels, used in AIDS: Therapy
			   plot.clearAnnotations();
			   Enumeration<InnerLabel> e = innerLabels.elements();
			   while (e.hasMoreElements()) {
				   InnerLabel lab = (InnerLabel) e.nextElement();
				   Logging.log("Adding " + lab.caption + " at " + lab.x + ", " + lab.y);

				   XYTextAnnotation annotation = new XYTextAnnotation(lab.caption, lab.x, lab.y);
				   annotation.setTextAnchor(TextAnchor.BOTTOM_CENTER);
				   annotation.setOutlineVisible(true);
				   plot.addAnnotation(annotation);


				   //I actually think the annotation above is ugly.  We can use one of these instead in the future, maybe...
				   /*PointerAnnotation may look cool...
				    * That 2.0 is the angle, randomly picked
				    * XYPointerAnnotation annotation = new XYPointerAnnotation(lab.caption, lab.x, lab.y, 2.0); 
				    */

				   /*
				   ValueMarker marker = new ValueMarker(lab.x);
				   marker.setLabel(lab.caption);
				   marker.setLabelAnchor(RectangleAnchor.BOTTOM_LEFT);
				   plot.addDomainMarker(marker);
				    */

			   }

			   //This is set for GD: AMCM
			   if (startGridded) {
				   plot.setDomainGridlinesVisible(true);
				   plot.setDomainGridlinePaint(Color.BLACK);
				   plot.setRangeGridlinesVisible(true);
				   plot.setRangeGridlinePaint(Color.BLACK);
			   }
			   
		   }
	   }
	   return new PopChartTheme(this);
   }
   
   public void styleJFree( JFreeChart chart ) {
	   getJFreeChartTheme().apply(chart);
   }
   
   /************************
   * JCChart Communicators *
   ************************/
   /** This modifies the chart argument for the look and data wanted */
   public void styleJC( JCChart chart ) {
      findBounds(); //compute xMin, xMax, yMin, yMax, zMin, zMax
      for( int i = 0;i < getNumSeries();i++ )
         chart.getDataView( 0 ).setChartStyle( i, getChartStyle(i) );

      chart.getDataView( 0 ).setDataSource( this );
      JCAxis h = chart.getChartArea().getHorizActionAxis();
      setAxis(chart);
      if(this.isBarChart){
        chart.getDataView(0).setChartType(JCChart.BAR);
        h.setAnnotationMethod(JCAxis.VALUE_LABELS);
        JCValueLabel[] labels = new JCValueLabel[data[0][0].length];
        for(int i=0; i<labels.length; i++){
           labels[i] = new JCValueLabel(data[0][0][i],""+NumberMath.roundSig(data[0][0][i],4,NumberMath.NORMAL));
        }
        h.setValueLabels(labels);
      } else {
         chart.getDataView(0).setChartType(JCChart.PLOT);
         h.setAnnotationMethod(JCAxis.VALUE);
         h.setValueLabels(null);
      }

      chart.getChartLabelManager().removeAllChartLabels();
      Enumeration<InnerLabel> e = innerLabels.elements();
      while (e.hasMoreElements()) {
         InnerLabel lab = (InnerLabel) e.nextElement();
         JCChartLabel cl = new JCChartLabel(lab.caption);
         cl.setDataCoord( new JCDataCoord( lab.x, lab.y ) );
         cl.setAnchor( JCChartLabel.NORTH );
         cl.setAttachMethod( JCChartLabel.ATTACH_DATACOORD );
         cl.getComponent().setBorder( javax.swing.BorderFactory.createLineBorder(Color.black, 2) );
         chart.getChartLabelManager().addChartLabel(cl);
      }

      if(startGridded){
         JCAxis v = chart.getChartArea().getVertActionAxis();
         h.setGridSpacingIsDefault( true );
         v.setGridSpacingIsDefault( true );
         h.setGridVisible( true );
         v.setGridVisible( true );
      }

      chart.update();
      chart.addChartListener(this);
   }

   /**
    * this method is also called in BasicPlotCanvas so that it can fix the axes
    * when the graph is reset
    * @param chart
    */
   public void setAxis( JCChart chart){
      axisReset = true;
      JCAxis v = chart.getChartArea().getVertActionAxis();
      JCAxis h = chart.getChartArea().getHorizActionAxis();

      if(xMinSet) h.setMin(xAxisMin);
      else        h.setMinIsDefault(true);
      if(xMaxSet) h.setMax(xAxisMax);
      else        h.setMaxIsDefault(true);
      if(yMinSet) v.setMin(yAxisMin);
      else        v.setMinIsDefault(true);
      if(yMaxSet) v.setMax(yAxisMax);
      else        v.setMaxIsDefault(true);

      if(isFrequencies){
         v.setNumSpacing(0.1);
      }
      v.setLogarithmic(isLogPlot);
   }

   /**
    * this method will create more elements in the lines vector if the requested index doesn't exist.
    * the reason it does this is so that the graphing styles of lines can be set before the lines themselves
    * are added.
    * @param n
    * @return
    */
   private JCChartStyle getChartStyle( int n ) {
      while(lines.size() <= n) lines.add( getDefaultStyle() );
      return ( (JCChartStyle)lines.elementAt( n ) );
   }
      
   
   private Stroke getLineStroke(int n) {
	   JCChartStyle jccs = getChartStyle(n);
	   return new BasicStroke((float) jccs.getLineWidth(),
			   BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
			   getDashArray(jccs.getLinePattern()), 0.0f);
   }
   private Color getLineColor(int n) {
	   return getChartStyle(n).getLineColor();
   }
   /**
    * 
    * @param n Line index
    * @return shape of the symbol on the line.  May be null if no symbol.
    */
   private Shape getSymbolShape(int n) {
	   JCSymbolStyle jcss = getChartStyle(n).getSymbolStyle();
	   if(jcss.getCustomShape() != null) {
		   //TODO -- this should ALWAYS be a PlotTerminus if it's customized.  We should force it in the compile.
		   try {
			   PlotTerminus pt = (PlotTerminus) jcss.getCustomShape();
			   return pt.getShape();
		   } catch (ClassCastException e) {
			   Logging.log("Why is custom shape not PlotTerminus??", Logging.kWarn);
			   Logging.log(e);
			   return null;
		   }
	   }
	   switch (jcss.getShape()) {
	   case DOTS:
		   double sz = 6.0; //TODO ??
		   return new Ellipse2D.Double(sz/2.0, sz/2.0, sz, sz);
	   default:
	   case NONE:
		   return null;
	   }
   }
   private boolean isSymbolOpaque(int n) {
	   JCSymbolStyle jcss = getChartStyle(n).getSymbolStyle();
	   if(jcss.getCustomShape() != null) {
		   //TODO -- this should ALWAYS be a PlotTerminus if it's customized.  We should force it in the compile.
		   try {
			   PlotTerminus pt = (PlotTerminus) jcss.getCustomShape();
			   return pt.isOpaque();
		   } catch (ClassCastException e) {
			   Logging.log("Why is custom shape not PlotTerminus??", Logging.kWarn);
			   Logging.log(e);
			   return true;
		   }
	   }
	   //default is true
	   return true;
   }
   private Color getSymbolColor(int n) {
	   return getChartStyle(n).getSymbolColor();
   }

   /***********************
   * Manipulation of data *
   ***********************/
   public double[][][] getData() {
      return data;
   }

   public void setData( double[][][] points ) {
      data = null;
      for( int i = 0; i < points.length; i++ )
         addData(points[i]);
   }

   /** Swaps chart data of two indices */
   public void swapData( int index1, int index2 ){
      int nSeries = getNumSeries();
      if(index1 >= nSeries || index2 >= nSeries) {
    	  Logging.log("Invalid indices passed into swapData");
          return;
      }

      double[][] tempData = data[index1];
      data[index1] = data[index2];
      data[index2] = tempData;
   }
   /**Similar to swapData, but move one index to the front, and
    * shifts everything else back.
    */
   public void moveDataToFront( int index ) {
	   int nSeries = getNumSeries();
	   if(index >= nSeries || index < 0) {
		   Logging.log("Invalid index (" + index + "to moveDataToFront");
		   return;
	   }
	   
	   double[][] tempData = data[index];
	   for (int i=index; i>0; --i) {
		   data[i] = data[i-1];
	   }
	   data[0] = tempData;
   }
   
   /**Similar to swapData, but move one index to the back, and
    * shifts everything else forward.
    */
   public void moveDataToBack( int index ) {
	   int nSeries = getNumSeries();
	   if(index >= nSeries || index < 0) {
		   Logging.log("Invalid index (" + index + "to moveDataToFront");
		   return;
	   }
	   
	   double[][] tempData = data[index];
	   for (int i=index; i<data.length-1; ++i) {
		   Logging.log("data[" + index + "] = data[" + (index+1) + "]");
		   data[i] = data[i+1];
	   }
	   data[data.length-1] = tempData;
	   Logging.log("data[" + (data.length-1) + "] = data[" + index + "]");
   }

   public int addData( double[][] newLine ){
      int nSeries = getNumSeries();
      double[][][] newData = new double[nSeries + 1][][];
      for( int i = 0; i < nSeries; i++ )
         newData[i] = data[i];

      newData[newData.length-1] = newLine;
      if(nSeries == lines.size())
         lines.add( getDefaultStyle() );
      data = newData;
      return nSeries;
   }

   /**
    * to change what a line would look like by default, change the settings in this method
    * 
    * @return
    */
   private JCChartStyle getDefaultStyle(){
      JCChartStyle jccs = new JCChartStyle();
      jccs.getLineStyle().setPattern(CONTINUOUS);
      jccs.getSymbolStyle().setShape(NONE);
      jccs.setLineWidth(2);
      jccs.setSymbolSize(6);
      jccs.setLineColor(ColorScheme.colors[getNumSeries()%ColorScheme.colors.length]);
      jccs.setSymbolColor(ColorScheme.colors[getNumSeries()%ColorScheme.colors.length]);
      return jccs;
   }

   /**
    * Required for JCChartListener, called after a paint event.
    * 
    * One of the methods that make this class a JCChartListener. This method is called when the
    * chart is finally drawn so that the aspect of the plot window can be taken into account when
    * calculating the angle of the plot arrow and fletching.
    * 
    *  TODO - will we need to dynamically check for JFreeChart when resized, or will it automagically work out?
    * @param jc
    */
   public void paintChart(JCChart jc){
      if(plotTerminusList.isEmpty()) return;
      JCAxis h = jc.getChartArea().getHorizActionAxis();
      JCAxis v = jc.getChartArea().getVertActionAxis();
      double chartMod = (double)jc.getChartArea().getHeight()/(double)jc.getChartArea().getWidth();
      double dataMod = (v.getMax()-v.getMin())/(h.getMax()-h.getMin());
      boolean needUpdate = updateDirectedSymbols(chartMod/dataMod);
      if(needUpdate)
         jc.update();
   }
   
   /**
    * This will tell the directed shapes (PlotTerminus) that they need to update their direction
    * 
    * @param ratio  (comp height/comp width) / (y value range / x value range)
    * @return true if values changed
    */
   public boolean updateDirectedSymbols(double ratio) {
      boolean needUpdate = false;
      Enumeration<PlotTerminus> e = plotTerminusList.elements();
      while (e.hasMoreElements()) {
         PlotTerminus term = e.nextElement();
         needUpdate |= term.updateAdjustment(ratio);
      }
      return needUpdate;
   }
   
   public void updateDirectedSymbolsJFC(ChartRendererWithOrientatedShapes r, double ratio) {
      boolean needUpdate = updateDirectedSymbols(ratio);
      if (needUpdate) {
    	  for (int i=0; i<getNumSeries(); i++) {
    		  r.setSeriesShape(i, getSymbolShape(i));
    	  }
      }
   }

   /**
    * Required for JCChartListener
    * 
    * This method is called when one of the axes is changed, e.g. when a zoom is performed. if the graph
    * is a frequency graph, then we want to force the y-axis to have a spacing of 0.1, but if zoomed in, then
    * the 0.1 spacing isn't any good anymore, and we should let the axis choose its own spacing.
    * @param jce
    */
   public void changeChart(JCChartEvent jce){
      JCAxis a = jce.getModifiedAxis();
      if(!a.isVertical()) return;

      if(!axisReset){
         a.setNumSpacingIsDefault(true);
      }
      axisReset = false;
   }

   public int getNumSeries() {
      if(data == null) return 0;
      return data.length;
   }

   public double[][] getPoints( int n ) {
      return data[n];
   }

   public double[] getXSeries( int p0 ) {
      return this.data[p0][0];
   }

   public double[] getYSeries( int p0 ) {
      return this.data[p0][1];
   }

   /************************
   *  Style Manipulations  *
   ************************/
   public void setLineWidth( int whichLine, int width){
      getChartStyle(whichLine).setLineWidth(width);
   }

   public void setLineColor( int whichLine, Color newColor ) {
      getChartStyle( whichLine ).setLineColor( newColor );
   }

   public void setLineStyle( int style ) {
      for( int i = 0;i < this.getNumSeries();i++ ) {
         getChartStyle(i).getLineStyle().setPattern(style);
      }
   }

   public void setLineStyle( int whichLine, int style ) {
      getChartStyle(whichLine).getLineStyle().setPattern(style);
   }

   public void setDiscrete(int whichLine){
      setLineStyle( whichLine, BasicPlotInfo.DASHED );
      setLineColor( whichLine, Color.black );
      setSymbolStyle( whichLine, BasicPlotInfo.DOTS);
   }

   public void setSymbolColor( int whichLine, Color newColor ) {
      getChartStyle( whichLine ).setSymbolColor( newColor );
   }

   public void setSymbolSize( int whichLine, int size) {
      getChartStyle(whichLine).setSymbolSize(size);
   }

   /** Like setSymbolStyle, but for the beginning or ending of a line
    * todo:  Ability to remove...
    */
   public void setTerminus(int whichLine, double[][] newLine, PlotTerminus term) {
      int terminusLine = getData().length;
      addData(newLine);
      getChartStyle(terminusLine).getSymbolStyle().setCustomShape(term.getJCShape());
      Color c = ColorScheme.colors[whichLine%ColorScheme.colors.length];
      setColor(c, terminusLine);
      plotTerminusList.add(term);
   }

   public void setSymbolStyle( int whichLine, int style) {
      if(style >= 0){
    	 if(style>0){
    		 edu.umn.ecology.populus.fileio.Logging.log("Why set symbolstyle to " + style, Logging.kWarn);
    	 }
         getChartStyle(whichLine).getSymbolStyle().setShape(style);
      } else {
         switch(style){
            case DOTS:
               getChartStyle(whichLine).getSymbolStyle().setCustomShape( new CircleTerminus(false).getJCShape() );
               break;
            case ARROW:
            case FLETCHING:
               edu.umn.ecology.populus.fileio.Logging.log("Should use setTerminus", Logging.kWarn);
               break;
         }
      }
   }

   public void setSymbolStyle( int style ) {
      for( int i = 0;i < getNumSeries();i++ ) {
         setSymbolStyle(i,style);
      }
   }

   public void setSymbolSize( int size) {
      for( int i = 0;i < getNumSeries();i++ ) {
         setSymbolSize(i,size);
      }
   }

   public void setColors( java.awt.Color[] newColors ) {
      int numLines = getNumSeries();
      for( int i = 0;i < numLines;i++ ) {
         setColor(newColors[i%newColors.length], i);
      }
   }
   public void setColor(java.awt.Color newColor, int line) {
      //newColor = Color.black;
      //getChartStyle(line).getSymbolStyle().setColor(newColor); //Lars - redundant???
      getChartStyle(line).setSymbolColor(newColor);
      getChartStyle(line).setLineColor(newColor);
   }

   /******************************
   * Viewing Bound Manipulations *
   ******************************/
   public double getXMin() { return xAxisMin; }
   public double getXMax() { return xAxisMax; }
   public double getYMin() { return yAxisMin; }
   public double getYMax() { return yAxisMax; }
   public double getZMin() { return zAxisMin; }
   public double getZMax() { return zAxisMax; }

   /** Get min Y value (not view window).  MUST call findbounds() first */
   public double getMinYVal() { return yMin; }
   /** Get max Y value (not view window).  MUST call findbounds() first */
   public double getMaxYVal() { return yMax; }
   /** Get min X value (not view window).  MUST call findbounds() first */
   public double getMinXVal() { return xMin; }
   /** Get max X value (not view window).  MUST call findbounds() first */
   public double getMaxXVal() { return xMax; }

   public void setXMin( double newXMin ) {
      xAxisMin = newXMin;
      xMinSet = true;
   }
   public void setXMax( double newXMax ) {
      xAxisMax = newXMax;
      xMaxSet = true;
   }
   public void setYMin( double newYMin ) {
      yAxisMin = newYMin;
      yMinSet = true;
   }
   public void setYMax( double newYMax ) {
      yAxisMax = newYMax;
      yMaxSet = true;
   }
   public void setZMin( double newZMin ) {
      zAxisMin = newZMin;
      zMinSet = true;
   }
   public void setZMax( double newZMax ) {
      zAxisMax = newZMax;
      zMaxSet = true;
   }

   public void setWindow( double xmin, double xmax, double ymin, double ymax ) {
      setXMin( xmin );
      setXMax( xmax );
      setYMin( ymin );
      setYMax( ymax );
   }

   /**
    * don't arbitrarily delete this method, the x and y max values are used to help
    * figure out plot arrow and fletching angles.
    */
   public void findBounds() {
      double currentX, currentY, currentZ;
      currentX = data[0][0][0];
      currentY = data[0][1][0];
      xMin = currentX;
      xMax = currentX;
      yMin = currentY;
      yMax = currentY;
      for(int i = 0; i < data.length; i++) {
         for(int j = 0; j < data[i][0].length; j++) {
            currentX = data[i][0][j];
            currentY = data[i][1][j];
            xMin = Math.min( xMin, currentX );
            xMax = Math.max( xMax, currentX );
            yMin = Math.min( yMin, currentY );
            yMax = Math.max( yMax, currentY );
         }
      }
      if(data[0].length == 3){
         currentZ = data[0][2][0];
         zMin = currentZ;
         zMax = currentZ;
         for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i][2].length; j++) {
               currentZ = data[i][2][j];
               zMin = Math.min( zMin, currentZ );
               zMax = Math.max( zMax, currentZ );
            }
         }
      }
   }

   /***********
   * Captions *
   ***********/
   public void setCaptions(String mainCaption, String xCaption, String yCaption, String zCaption){
      this.mainCaption = new String[] {mainCaption};
      this.xCaption = new String[] {xCaption};
      this.yCaption = new String[] {yCaption};
      this.zCaption = zCaption;
   }

   public void setMainCaption( String cap ) { mainCaption = new String[] {cap}; }
   public void setXCaption( String newXCaption ) { xCaption = new String[] {newXCaption}; }
   public void setYCaption( String newYCaption ) { yCaption = new String[] {newYCaption}; }
   public void setZCaption( String newZCaption ) { zCaption = newZCaption; }
   public void setMainCaptions( String[] cap ) { mainCaption = cap; }
   public void setXCaptions( String[] newXCaption ) { xCaption = newXCaption; }
   public void setYCaptions( String[] newYCaption ) { yCaption = newYCaption; }

   public String getMainCaption() { return mainCaption[0]; }
   public String getXCaption() { return xCaption[0]; }
   public String getYCaption() { return yCaption[0]; }
   public String getZCaption() { return zCaption; }
   public String[] getMainCaptions() { return mainCaption; }
   public String[] getXCaptions() { return xCaption; }
   public String[] getYCaptions() { return yCaption; }

   public void addInnerCaption(String caption, double x, double y) {
      InnerLabel label = new InnerLabel(caption, x, y);
      innerLabels.add(label);
   }
   public void clearInnerCaptions() {
      innerLabels.clear();
   }

   /***************************
   * Graph type manipulations *
   ***************************/
   public int getGraphType(){ return outputType; }
   public void setGraphType(int newValue){ outputType = newValue; }
   public Object getSpecial() { return special; }

   public void setIsFrequencies( boolean isFreq ) {
      this.isFrequencies = isFreq;
      if(isFreq){
         setYMax(1.0);
         setYMin(0.0);
      }
   }

   /********
   * Flags *
   ********/
   public void set3DIsDiscrete( boolean z, boolean x ) {
         this.zIsDiscrete = z;
         this.xIsDiscrete = x;
   }

   public boolean isZDiscrete() { return zIsDiscrete; }
   public boolean isXDiscrete() { return xIsDiscrete; }
   public void setDefaultAxis( boolean yes ) {
      xMinSet = false;
      xMaxSet = false;
      yMinSet = false;
      yMaxSet = false;
      zMinSet = false;
      zMaxSet = false;
   }
   public boolean isHasIsoclines() { return getGraphType()==k3D && hasIso; }
   public void setHasIsoclines( boolean b ) { this.hasIso = b; }
   public boolean isLabelsT() { return labelT; }
   public void setLabelsT( boolean b ) { labelT = b; }
   public boolean isDiscrete(){ return isDiscrete; }
   public void setIsLive(boolean b){ isLive = b; }
   public boolean isLive(){ return (getGraphType() == k3D || getGraphType() == kDeFinetti) && isLive; }
   public boolean isStartGridded() { return startGridded; }
   public void setStartGridded( boolean b ) { startGridded = b; }

   /**
    * this is the look that i've chosen for a discrete graph. the only thing that i've left out of this
    * method is the setting of the symbol color because i will assume that would be set elsewhere if it
    * needs to be set.
    * @param b
    */
   public void setIsDiscrete(boolean b){
      isDiscrete = b;
      if(b){
         setLineStyle( BasicPlotInfo.DASHED );
         for( int j = 0;j < getNumSeries();j++ ) {
            setLineColor( j, Color.black ); //TODO = why set to black?
            setSymbolStyle( j, BasicPlotInfo.DOTS);
         }
      } else {
         setSymbolStyle( BasicPlotInfo.NONE );
         setLineStyle( BasicPlotInfo.CONTINUOUS );
      }
   }
   

   /***************************************
   * Serialization and data storage stuff *
   ***************************************/
   public void dump( PrintWriter pw ) {
      java.util.Enumeration<JCChartStyle> e;
      int inc = 1;
      pw.println( res.getString( "Output_of" ) + mainCaption );
      pw.println( xCaption + res.getString( "vs_" ) + yCaption );
      e = lines.elements();
      while( e.hasMoreElements() ) {
         pw.println( res.getString( "Line_" ) + inc++ );
      }
   }


}

class InnerLabel {
   String caption;
   double x, y;

   InnerLabel(String c, double x, double y) {
      this.caption = c;
      this.x = x;
      this.y = y;
   }
}



