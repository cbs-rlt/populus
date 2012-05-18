package edu.umn.ecology.populus.model.md;
import java.awt.*;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;
import java.util.*;

public class MDParamInfo implements BasicPlot {
   public static final int HPDD = 9;
   public static final int HPWDD = 10;
   public static final int HPADD = 11;
   public static final int vsT = 0;//N,P,w vs T or P,N vs T
   public static final int nvsp = 1;//N vs P
   public static final int phase = 2;//N vs P vs W

   String mCapNvsT = res.getString( "Infectious1" );
   int plotType;
   int numVars, aorW;
   double x, y, z, time;
   Integrator ig;
   String mCapN2vsN1 = mCapNvsT;
   String xCap = res.getString( "Time_b_i_t_" );
   String yCap2 = res.getString( "Host_Population" ) +"<b><i>" + ColorScheme.getColorString( 0 ) + "H </font></i></b>, <b><i>" + ColorScheme.getColorString( 1 ) + "P </font>"+ "</i></b> )";
   String yCap3 = res.getString( "Host_Population" ) +"<b><i>" + ColorScheme.getColorString( 0 ) + "H </font></i></b>, <b><i>" + ColorScheme.getColorString( 1 ) + "P </font>" + "</i></b>, <b><i>" + ColorScheme.getColorString( 2 ) + "W </font></i></b>" + "</i></b> )";
   String yCap4 = res.getString( "Host_Population" ) +"<b><i>" + ColorScheme.getColorString( 0 ) + "H </font></i></b>, <b><i>" + ColorScheme.getColorString( 1 ) + "P </font>" + "</i></b>, <b><i>" + ColorScheme.getColorString( 2 ) + "A </font></i></b>" + "</i></b> )";
   String nCaption = res.getString( "Host_N_W" );
   String pCaption = res.getString( "Parasite_P_W" );
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.md.Res" );


   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo bp = null;
      double[][][] points;
      double[] xlist;
      //double[] totalPop;
      double[][] ylists;
      double[] initialConditions = new double[numVars];
      int size;
      initialConditions[0] = x;
      initialConditions[1] = y;
      if( numVars == 3 ) {
         initialConditions[2] = z;
      }
      if( time < 0 ) {
         ig.record.ss = true;
         ig.record.interval = false;
      }
      ig.record.nonNegOnly = true;

      ig.doIntegration( initialConditions, 0.0, time );
      xlist = ig.getX();
      ylists = ig.getY();
      size = xlist.length;
      /*totalPop = new double[size];
      for(int i=0; i<totalPop.length; i++)
         for(int j=0; j<ylists.length; j++)
            totalPop[i] += ylists[j][i];
*/
      String caption;
      switch(plotType){
         case vsT:
            points = new double[numVars][2][];
            points[0][0] = xlist;
            points[0][1] = ylists[0];//H
            points[1][0] = xlist;
            points[1][1] = ylists[1];//P
            if( numVars == 3 ) {
               points[2][0] = xlist;
               points[2][1] = ylists[2];//W
            }
            if (numVars == 3){
               if (aorW == 3)
                  caption = yCap3;
               else
                  caption = yCap4;
            }
            else caption = yCap2;
            bp = new BasicPlotInfo( points, mCapNvsT, xCap, caption );
            //bp.setYMin( 0.0 );
            bp.vsTimeChars = new String[] {
               "H", "P", "W"
            };
            bp.isLogPlot = true;
            break;
         case nvsp:
            points = new double[1][2][];
            points[0][1] = ylists[1];
            points[0][0] = ylists[0];
            bp = new BasicPlotInfo( points, mCapN2vsN1,  nCaption, pCaption );
            PlotArrow.addArrow( bp, 0 );
            //PlotArrow.addFletching( bp, 0 );
            break;
         case phase:
            points = new double[1][][];
            points[0] = ylists;
            bp = new BasicPlotInfo( points, mCapN2vsN1, nCaption, pCaption );
            PlotArrow.addArrow( bp, 0 );
            //PlotArrow.addFletching( bp, 0 );
            break;
      }
      return bp;
   }

   public MDParamInfo( int modelType, int plotType, double time, double X, double Y, double Z,
                       double a, double b, double alpha, double beta, double muA, double muP,
                       double lambda, double k, double gamma, double sigma, double theta ) {
      ig = new Integrator( new MDDeriv( modelType, a, b, alpha, beta, muA, muP, lambda, k, gamma, sigma, theta) );
      this.x = X;
      this.y = Y;
      this.z = Z;
      this.time = time;
      this.plotType = plotType;
      numVars = (modelType == HPWDD || modelType == HPADD) ? 3 : 2;
      aorW = (modelType == HPWDD) ? 3 : 2;
   }
}
