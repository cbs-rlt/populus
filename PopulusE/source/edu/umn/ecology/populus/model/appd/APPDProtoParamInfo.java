package edu.umn.ecology.populus.model.appd;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;

/**
  * Subclasses must do the following in initializing:
  * Set the following variables: <UL>
  *   <LI> deriv </LI>
  *   <LI> gens </LI>
  *   <LI> plotType </LI>
  *   <LI> modelType </LI>
  *   <LI> initialConditions </LI>
  *   <LI> mainCaption </LI>
  *   <LI> xCaption </LI>
  *   <LI> yCaption </LI>
  *   </UL>
  *
  *	This class is provided as common ground for all
  *	discrete-pp models to extend.
  */

public class APPDProtoParamInfo implements BasicPlot {
   public static final int NvsT = 1;
   public static final int NvsN = 2;
   protected DiscreteProc discProc = null;
   protected double[] initialConditions = null;
   protected int plotType = 0;
   protected String mainCaption = null;
   protected String xCaption = null;
   protected String yCaption = null;
   protected int gens;

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp;
      Integrator ig;
      int equations = initialConditions.length;
      double[][][] points;
      double[] xlist;
      double[][] ylists;
      int i;
      ig = new Integrator( discProc );
      if( gens < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.integrate( initialConditions, 0.0, gens );
      xlist = ig.getX();
      ylists = ig.getY();
      if( ( plotType & NvsT ) != 0 ) {
         points = new double[equations][2][];
         for( i = 0;i < equations;i++ ) {
            points[i][0] = xlist;
            points[i][1] = ylists[i];
         }
         bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption );
         bp.setYMin( 0.0 );
         bp.setIsDiscrete(true);
      }
      else {
         if( ( plotType & NvsN ) != 0 ) {
            points = new double[1][][];
            points[0] = ylists;

            //had isoclines here, and points was new double[3][][]
            bp = new BasicPlotInfo( points, mainCaption, xCaption, yCaption );
            PlotArrow.addArrow( bp, 0 );
            PlotArrow.addFletching( bp, 0 );
         }
         else {
            //error!!!
            edu.umn.ecology.populus.fileio.Logging.log( "Invalid plot type: in APPDProtoParamInfo" );
            bp = new BasicPlotInfo();
         }
      }
      bp.vsTimeChars = new String[] {
         "N", "P"
      };
      return bp;
   }

   public APPDProtoParamInfo() {

   }
}
