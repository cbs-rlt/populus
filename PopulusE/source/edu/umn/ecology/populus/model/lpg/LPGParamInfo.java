
//This is still trying to copy Chris's code in the lagged model, which I am confused by a little.

//I would like to some time look at it closer and clean it up.
package edu.umn.ecology.populus.model.lpg;
import java.util.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.model.lpg.LPGData;

/**
  *
  */

public class LPGParamInfo implements BasicPlot {
   public static final int LAGGED = 1;
   public static final int DISCRETE = 2;
   public static final int CONTINUOUS = 4;

   //private static final int LAG_ITERS = 50; //number of generations?

   //private static final int GEN = 50; //number of generations
   public static final long NUM_ITERS = 1; //Is this correct?
   public static final double U_LIMIT = 1e30;
   public static final int MAX_ITERS = 100000;
   String lnyCap = res.getString( "ln_Population_ln_i_b" );
   String dndtCap = "d<i><b>N</>/d<i><b>t</>";
   String dnndtCap = "d<i><b>N</>/<i><b>N</>d<b><i>t</>";
   double r; //growth rate
   int lagLagIters;
   String timeCap = res.getString( "Time_b_i_t_" );
   String xCap = timeCap;
   double n0; //n0 = pop0
   String nsubtCap = "<i>N <sub>t</>";

   /** May be kNVST, kLNNVST, kDNDTVSN, kDNNDTVSN, or kLNNTP1VSLNNT */
   int plotType;
   String disc1Cap = "<i>N<sub>t</i>+1</>-<i>N<sub> t</>";
   double kNumPoints = 2000.0; //number of points on each graph. be sure it is less than MAX_ITERS
   //String disc2Cap = "(<i>N<sub>t</i>+1</sub>-<i>N<sub>t</>)/<i>N<sub>t</>"; //<- this is for "process2"
   String disc2Cap = "ln ( <i>N<sub>t</i>+1</sub> / <i>N<sub>t</> )";
   int generations; //gens
   String lnntp1Cap = "ln <i>N<sub>t</i>+1</>";
   String nCap = res.getString( "Population_Size_b_i_N" );
   String lnntCap = "ln <i>N <sub>t</>";
   int lag; //need this be int, or can it be double?
   String mCapContinuous = res.getString( "Continuous_Logistic1" );

   /** May be LAGGED, DISCRETE, or CONTINUOUS */
   int modelType;
   String mCapLagged = res.getString( "Lagged_Logistic1" );
   String yCap = nCap;
   String mCapDiscrete = res.getString( "Discrete_Logistic1" );
   int k; //k = max size
   static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lpg.Res" );

   /*
   private variables pulled out so the switch could be expanded
   */
   private double[][][] points;
   private static BasicPlotInfo returnValue = null;
   private static double tempt, tempN, maxy, temp;

   public LPGParamInfo( int modelType, double n0, double k, double r, double t, int time, int plotType ) {
      lag = (int)t;
      generations = time;
      this.k = (int)k;
      this.r = r;
      this.n0 = n0;
      if( modelType == LPGParamInfo.LAGGED ) {
         if( lag == 0 ) {
            modelType = CONTINUOUS;
         }
         else {
            lagLagIters = lag * time;
         }
      }
      this.modelType = modelType;
      this.plotType = plotType;
   }

   public BasicPlotInfo getBasicPlotInfo() {
      return returnValue;
   }

   public BasicPlotInfo getResults() {
      yCap = nCap;
      xCap = timeCap;
      if( plotType == LPGPanel.kLNNVST ) {
         yCap = lnyCap;
         xCap = timeCap;
      } else {
         if( plotType == LPGPanel.kDNDTVSN ) {
            if( modelType == DISCRETE ) {
               yCap = disc1Cap;
               xCap = nsubtCap;
            } else {
               yCap = dndtCap;
               xCap = nCap;
            }
         } else {
            if( plotType == LPGPanel.kDNNDTVSN ) {
               if( modelType == DISCRETE ) {
                  yCap = disc2Cap;
                  xCap = nsubtCap;
               } else {
                  yCap = dnndtCap;
                  xCap = nCap;
               }
            } else {
               if( plotType == LPGPanel.kLNNTP1VSLNNT ) {

                  //We assume it has to be discrete
                  yCap = lnntp1Cap;
                  xCap = lnntCap;
               } else {
                  if( plotType == LPGPanel.kNVST ) {
                     xCap = timeCap;
                     yCap = nCap;
                  }
               }
            }
         }
      }
      switch( modelType ) {
         case CONTINUOUS:
             ContinuousGraph();
             break;
         case DISCRETE:
             DiscreteGraph();
             break;
         case LAGGED:
             LaggedGraph();
             break;
      }
      return returnValue;
   }

   public LPGParamInfo( LPGData[] myData, int numGraphs, int modelType, int plotType, int time ) {
      BasicPlotInfo temp = null;

      /*
      we need toKeep to store the line information since
      returnValue is overwritten every loop.
      */
      BasicPlotInfo toKeep = new BasicPlotInfo();
      double[][][] keepDoub = null;
      if( numGraphs == 0 ) {
         return ;
      }
      int j = 0;
      while( myData[j] == null ) {
         j++;
      }
      this.modelType = modelType;
      this.plotType = plotType;
      this.generations = time;
      j = 0;
      returnValue = new BasicPlotInfo();
      keepDoub = new double[numGraphs][][];
      for( int i = 0;i < numGraphs;i++ ) {
         while( myData[j] == null ) j++;
         lag = (int)myData[j].T;
         this.k = (int)myData[j].K;
         this.r = myData[j].r;
         this.n0 = myData[j].N;
         temp = getResults();
         keepDoub[i] = temp.getData()[0];
         if( modelType == DISCRETE)
            toKeep.setSymbolColor(i,ColorScheme.colors[j]);
         else
            toKeep.setLineColor(i,ColorScheme.colors[j]);
         j++;
      }
      toKeep.setData( keepDoub );
      if( modelType == DISCRETE ) toKeep.setIsDiscrete(true);
      toKeep.setLineWidth( 0, 3 );
      toKeep.setMainCaption( temp.getMainCaption() );
      toKeep.setXCaption( temp.getXCaption() );
      toKeep.setYCaption( temp.getYCaption() );
      returnValue = toKeep;
   }

   private void DiscreteGraph() {
      double oldN;
      maxy = k;
      if( plotType == LPGPanel.kLNNTP1VSLNNT ) {
         points = new double[1][2][generations];
      }
      else {
         points = new double[1][2][generations + 1];
      }
      points[0][0][0] = 0.0;
      points[0][1][0] = n0;
      tempN = n0;
      oldN = n0;
      for( int i = 1; i <= generations; i++ ) {
         tempN *= Math.exp( r * ( 1 - tempN / k ) );
         maxy = Math.max( maxy, tempN );
         if( plotType == LPGPanel.kLNNTP1VSLNNT ) {
            points[0][0][i - 1] = Math.log( oldN );
            points[0][1][i - 1] = Math.log( tempN );
            oldN = tempN;
         }
         else {
            points[0][0][i] = (double)i;
            points[0][1][i] = tempN;
         }
      }
      if( plotType == LPGPanel.kDNDTVSN ) {
         double[][] xformed = new double[2][generations];
         edu.umn.ecology.populus.math.Routines.process1( points[0][1], xformed );
         points[0] = xformed;
      }
      else {
         if( plotType == LPGPanel.kDNNDTVSN ) {
            double[][] xformed = new double[2][generations];
            //edu.umn.ecology.populus.math.Routines.process2( points[0][1], xformed );
            edu.umn.ecology.populus.math.Routines.process3( points[0][1], xformed );
            points[0] = xformed;
         }
         else {
            if( plotType == LPGPanel.kLNNVST ) {
               edu.umn.ecology.populus.math.Routines.lnArray( points[0][1] );
            }
         }
      }
      returnValue = new BasicPlotInfo( points, mCapDiscrete, xCap, yCap );

      double[][][] newPoints = new double[2][2][points[0][1].length];
      newPoints[0] = points[0];
      newPoints[1] = points[0];
      returnValue.setData( newPoints );
      returnValue.setIsDiscrete(true);
   }

   /**
      This algorithm has been improved upon again with something even simpler and gives better results.
      It makes some assumptions, namely that populations bigger than 1e30 or less than 1e-30 aren't useful
      to the Populus user or have any physical meaning whatsoever, so if these populations have been
      achieved, then they will be controlled. Being a differential equation, this method is only a crude
      approximation, hopefully it isn't too much of an approximation for general use...
   */
   private void LaggedGraph() {
      kNumPoints = generations*20;
      double dt = generations / kNumPoints;
      double kcounter = 0.0;
      Vector<Double> lagVector = new Vector<Double>( lagLagIters + 1 );
      double[][][] points = new double[1][2][(int)kNumPoints];
      double nx = 0.0, y = n0;
      maxy = n0;
      int i = 0;

      do {
         points[0][0][i] = i*dt;
         if( y < 1.0d/U_LIMIT ){
            points[0][1][i] = y = 0.0;
         } else if( y > U_LIMIT) {
            points[0][1][i] = y = U_LIMIT;
         } else {
            points[0][1][i] = y;
         }

         y += r * y * ( 1 - nx / k ) * dt;
         lagVector.addElement( new Double( y ) );

         if( kcounter >= lag ) {
            nx = ( (Double)lagVector.elementAt( 0 ) ).doubleValue();
            lagVector.removeElementAt( 0 );
         } else {
            kcounter += dt;
         }
         maxy = Math.max( y, maxy );
         i++;
      }while( i < kNumPoints );

      if( plotType == LPGPanel.kLNNVST ) {
         edu.umn.ecology.populus.math.Routines.lnArray( points[0][1] );
      }

      returnValue = new BasicPlotInfo( points, mCapLagged, xCap, yCap );
   }

   private void ContinuousGraph() {
      int i;
      if( ( plotType == LPGPanel.kDNDTVSN ) || ( plotType == LPGPanel.kDNNDTVSN ) ) {
         points = new double[1][2][generations];
      }
      else {
         points = new double[1][2][generations + 1];
      }
      points[0][0][0] = 0.0;
      points[0][1][0] = n0;
      temp = ( k - n0 ) / n0;
      for( i = 1;i <= generations;i++ ) {
         tempt = (double)i;
         tempN = k / ( 1 + temp * Math.exp( -r * ( (double)i ) ) );
         if( ( plotType == LPGPanel.kNVST ) || ( plotType == LPGPanel.kLNNVST ) ) {
            points[0][0][i] = tempt;
            points[0][1][i] = tempN;
         }
         else {
            if( plotType == LPGPanel.kDNDTVSN ) {
               points[0][0][i - 1] = tempN;
               points[0][1][i - 1] = r * tempN * ( k - tempN ) / k;
            }
            else {
               if( plotType == LPGPanel.kDNNDTVSN ) {
                  points[0][0][i - 1] = tempN;
                  points[0][1][i - 1] = r * ( k - tempN ) / k;
               } //Shouldn't be any other case. Otherwise could be null
            }
         }
      }
      if( plotType == LPGPanel.kLNNVST ) {
         edu.umn.ecology.populus.math.Routines.lnArray( points[0][1] );
      }
      returnValue = new BasicPlotInfo( points, mCapContinuous, xCap, yCap );
   }
}