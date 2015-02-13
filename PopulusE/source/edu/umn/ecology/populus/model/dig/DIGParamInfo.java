package edu.umn.ecology.populus.model.dig;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

public class DIGParamInfo implements BasicPlot {
   public static final int kNUM_DIVISIONS = 100;
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.dig.Res" );

   String yCap = res.getString( "Population_b_i_N_" );
   String nSubTCap = res.getString( "Population_b_i_N_sub" );
   String lnyCap = res.getString( "ln_Population_ln_i_b" );
   String kDDNNDTSTRING = "ln (<i><b>N<sub>t</i> +1</> / <i><b>N<sub>t</>)";
   String xCap = res.getString( "Time_b_i_t_" );
   String kCDNDTSTRING = "d<b><i>N</>/d<i><b>t</>";
   String kCDNNDTSTRING = "d<b><i>N</i>/<i>N</>d<i><b>t</>";
   String kDDNDTSTRING = "<i><b>N<sub>t</i>+1</> - <i><b>N<sub>t</>";
   String mCCap = res.getString( "Continuous1" );
   BasicPlotInfo thisInfo = null;
   String mDCap = res.getString( "Discrete_Geometric" );
   private int plottype;
   private double p0, gens, lambda, r;
   private boolean continuous;

   public DIGParamInfo( DIGData[] myData, int numGraphs ) {
      BasicPlotInfo temp = null;
      thisInfo = new BasicPlotInfo();
      double[][][] keepDoub;
      double maxR=0, minR=0;
      if( numGraphs == 0 ) return;
      int j = 0;
      while( myData[j] == null )
         j++;

      this.continuous = DIGData.isContinuous;
      if( continuous ) {
         if (numGraphs == 1)
            thisInfo.setMainCaption( mCCap + ", <i>r</i> = " + myData[j].rPF );
         else
            thisInfo.setMainCaption(mCCap);
      } else {
         if (numGraphs == 1)
            thisInfo.setMainCaption( mDCap + ", \u03BB = " + myData[j].lambdaPF );
         else
            thisInfo.setMainCaption(mDCap);
      }
      j = 0;
      keepDoub = new double[numGraphs][][];
      for( int i = 0;i < numGraphs;i++ ) {
         while( myData[j] == null ) j++;
         this.plottype = DIGData.selection;
         this.continuous = DIGData.isContinuous;
         this.gens = DIGData.gensPF;
         this.p0 = myData[j].nOPF;
         this.lambda = myData[j].lambdaPF;
         this.r = myData[j].rPF;
         temp = getResults();
         keepDoub[i] = temp.getData()[0];
         if(i==0){
            maxR = minR = keepDoub[i][1][0];
         } else {
            maxR = Math.max(maxR, keepDoub[i][1][0]);
            minR = Math.min(minR, keepDoub[i][1][0]);
         }
         if( !continuous )
            thisInfo.setSymbolColor(i,ColorScheme.colors[j]);
         else
            thisInfo.setLineColor(i,ColorScheme.colors[j]);
         j++;
      }
      thisInfo.setData( keepDoub );
      if( !continuous ) thisInfo.setIsDiscrete(true);
      thisInfo.setLineWidth( 0, 3 );
      thisInfo.setXCaption( temp.getXCaption() );
      thisInfo.setYCaption( temp.getYCaption() );

      if(plottype == DIGPanel.kDNNDTVSN){
      //this is a fix to try and get the line(s) for this plot type to go through
      //the middle of the graph and not lay on the x-axis
         thisInfo.setYMax(maxR+0.1*maxR);
         thisInfo.setYMin(minR-0.1*minR);
      }
   }

   public BasicPlotInfo getResults() {
      BasicPlotInfo bpi = new BasicPlotInfo();
      double[][][] points = null;
      int i = 0;
      if( continuous && ( ( plottype == DIGPanel.kDNDTVSN ) || ( plottype == DIGPanel.kDNNDTVSN ) ) ) {
         points = new double[1][2][kNUM_DIVISIONS + 1];
         double x = 0.0, y, inc = ( (double)gens ) / ( (double)kNUM_DIVISIONS );
         i = 0;
         while( i <= kNUM_DIVISIONS ) {
            y = Math.exp( r * x ) * p0;
            points[0][0][i] = y;
            points[0][1][i] = ( plottype == DIGPanel.kDNDTVSN ) ? y * r : r;
            x += inc;
            i++;
         }
         bpi.setData( points );
         bpi.setXCaption( yCap );
         bpi.setYCaption( ( plottype == DIGPanel.kDNDTVSN ) ? kCDNDTSTRING : kCDNNDTSTRING );
      } else {
         if( continuous ) { // the graph is continuous
            points = new double[1][2][kNUM_DIVISIONS + 1];
            double x = 0.0, inc = ( (double)gens ) / ( (double)kNUM_DIVISIONS );
            i = 0;
            while( i <= kNUM_DIVISIONS ) {
               points[0][0][i] = x;
               points[0][1][i] = Math.exp( ( r * x ) ) * p0;
               x += inc;
               i++;
            }
         } else { // the graph is discrete
            points = new double[1][2][(int)gens + 1];
            double x = p0;
            while( i <= gens ) {
               points[0][1][i] = x;
               x *= lambda;
               i++;
            }
            if( plottype == DIGPanel.kDNDTVSN ) {
               double[][] xformed = new double[2][(int)gens];
               edu.umn.ecology.populus.math.Routines.process1( points[0][1], xformed );
               points[0] = xformed;
            } else {
               if( plottype == DIGPanel.kDNNDTVSN ) {
                  double[][] xformed = new double[2][(int)gens];
                  //edu.umn.ecology.populus.math.Routines.process2( points[0][1], xformed );
                  edu.umn.ecology.populus.math.Routines.process3( points[0][1], xformed );
                  points[0] = xformed;
               } else {

                  //place the correct x variables
                  i = 0;
                  while( i <= gens ) {
                     points[0][0][i] = (double)i;
                     i++;
                  }
               }
            }
         }
         if( plottype == DIGPanel.kLNNVST ) {
            edu.umn.ecology.populus.math.Routines.lnArray( points[0][1] );
         }
         bpi.setData( points );

         if( !continuous && ( ( plottype == DIGPanel.kDNDTVSN ) || ( plottype == DIGPanel.kDNNDTVSN ) ) ) {
            bpi.setXCaption( nSubTCap );
            bpi.setYCaption( ( plottype == DIGPanel.kDNDTVSN ) ? kDDNDTSTRING : kDDNNDTSTRING );
         } else {
            bpi.setXCaption( xCap );
            bpi.setYCaption( ( plottype == DIGPanel.kLNNVST ) ? lnyCap : yCap );
         }
      }
      i = points[0][0].length;

      if( !continuous ){
         double[][][] newPoints = new double[2][2][points[0][1].length];
         newPoints[0] = points[0];
         newPoints[1] = points[0];
         bpi.setData( newPoints );
      }
      return bpi;
   }

   public BasicPlotInfo getBasicPlotInfo() {
      return thisInfo;
   }

   public DIGParamInfo( boolean continuous, int plottype, double g, double l, double p, double r ) {
      this.continuous = continuous;
      this.plottype = plottype;
      this.p0 = p;
      this.gens = g;
      this.lambda = l;
      this.r = r;
      thisInfo = getResults();
   }

}
