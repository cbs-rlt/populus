package edu.umn.ecology.populus.model.aspg;

import java.util.*;
import java.awt.Color;
import javax.swing.JComponent;
import edu.umn.ecology.populus.visual.HTMLLabel;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.resultwindow.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import Jama.*;
/**
 *
 */
public class ASPGParamInfo implements BasicPlot, TableInterface {
   static ResourceBundle res = ResourceBundle.getBundle("edu.umn.ecology.populus.model.aspg.Res");
   public static final String kLAMBDAVSTXCAPTION = "Time Interval (<i>t</i>)";
   public static final String kLAMBDAVSTYCAPTION = "\u03bb";
   public static final String kLAMBDAVSTMAINCAPTION = "\u03bb vs <i>t</>";
   public static final String kSIGMANXVSTXCAPTION = "Time Interval (<i>t</i>)";
   public static final String kSIGMANXVSTYCAPTION = "\u03a3<i>S<sub>x</sub></i>";
   public static final String kSIGMANXVSTMAINCAPTION = "\u03a3<i>S<sub>x</sub></i> vs <i>t</>";
   public static final String kNXSIGMANXVSTXCAPTION = "Time Interval (<i>t</i>)";
   //public static final String kNXSIGMANXVSTYCAPTION = "<i>S<sub>x</sub></i>/\u03a3<i>S<sub>x</sub></i>";
   public static final String kNXSIGMANXVSTYCAPTION = "Proportion of Populuation Aged";
   public static final String kNXSIGMANXVSTMAINCAPTION = "<i>S<sub>x</sub></i>/\u03a3<i>S<sub>x</sub></i> vs <i>t</i>";
   public static final String kVXVSXXCAPTION = res.getString("Age_Class_i_x_i_");
   public static final String kVXVSXYCAPTION = "Reproductive Value ( <i>V<sub>x</sub></i> )";
   public static final String kVXVSXMAINCAPTION = "<i>V<sub>x</sub></i> vs <i>x</i>";
   public static final String kNXSIGMANXVSXXCAPTION = res.getString("Age_Class_i_x_i_");
   //public static final String kNXSIGMANXVSXYCAPTION = "<i>S<sub>x</sub></i>/\u03a3<i>S<sub>x</sub></i>";
   public static final String kNXSIGMANXVSXYCAPTION = "Proportion of Populuation Aged";
   public static final String kNXSIGMANXVSXMAINCAPTION = "<i>S<sub>x</sub></i>/\u03a3<i>S<sub>x</sub></i> vs <i>x</i>";
   public static final String kMXVSXXCAPTION = res.getString("Age_Class_i_x_i_");
   public static final String kMXVSXYCAPTION = "<i>m<sub>x</sub></i>";
   public static final String kMXVSXMAINCAPTION = "<i>m<sub>x</sub></i> vs <i>x</i>";
   public static final String kLXVSXXCAPTION = res.getString("Age_Class_i_x_i_");
   public static final String kLXVSXYCAPTION = "<i>l<sub>x</sub></i>";
   public static final String kLXVSXMAINCAPTION = "<i>l<sub>x</sub></i> vs <i>x</i>";
   public static final String kMainCaption = res.getString("Age_Structured");

   public static final int kL = 0;
   public static final int kM = 1;
   public static final int kS = 2;
   public static final int kP = 3;
   public static final int kF = 4;

   public static final double kMAXR = 100.0;
   public static final double kRPREC = 1.0e-4;

   ASPGData data;
   double[][] lxmxTable;
   int outputType;
   int ageClassToView;
   double r0, g, r, va;
   String[] title = null;

   /**
    * Use -1 as ageClassToView to view all
    */
   public ASPGParamInfo(int outputType, ASPGData data, int ageClassToView, int inputType) throws BadUserException {
      this.data = data;
      this.outputType = outputType;
      this.ageClassToView = ageClassToView;
      lxmxTable = data.getLxMxTable();
      double temp = lxmxTable[kL][0];
      for (int i = 1; i < lxmxTable[kL].length; i++) {
         if(Double.isNaN(lxmxTable[kL][i]) || Double.isInfinite(lxmxTable[kL][i]))
            throw new BadUserException("Is there a zero in the Lx before the last class?");
         if( lxmxTable[kL][i] > temp && Math.abs(lxmxTable[kL][i]-temp) > 1e-12){
            if(inputType == ASPGPanel.kLXMX)
               throw new BadUserException(res.getString("lx_must_be_weakly"));
            else
               throw new BadUserException("The p values must be less than one.");
         }
         temp = lxmxTable[kL][i];
      }

   }

   public BasicPlotInfo getBasicPlotInfo() {
      BasicPlotInfo returnValue = null;

      int shift = 0;
      if(data.getModelType() == ASPGPanel.kPREBREEDING)
         shift = 1;

      double[][][] points;
      double[][] tabularOutputMatrix = data.getTabularOutput();
      int numClasses = data.getNumClasses();
      int runTime = data.getRuntime();

      switch (outputType) {
         case ASPGPanel.kLESLIEMATRIX:
         case ASPGPanel.kTABULAROUTPUT:
            returnValue = new BasicPlotInfo(this);
            break;
         case ASPGPanel.kLAMBDAVST:
            points = new double[1][2][runTime];
            for (int i = 0; i < runTime; i++) {
               points[0][0][i] = i+1;
               points[0][1][i] = tabularOutputMatrix[i+1][numClasses] /tabularOutputMatrix[i][numClasses];
            }
            //returnValue = new BasicPlotInfo(data, kLAMBDAVSTMAINCAPTION, kLAMBDAVSTXCAPTION, kLAMBDAVSTYCAPTION);
            returnValue = new BasicPlotInfo(points, kMainCaption, kLAMBDAVSTXCAPTION, kLAMBDAVSTYCAPTION);
            //setting ymin doesn't do anything
            //returnValue.setYMin(0.0);
            break;
         case ASPGPanel.kSIGMANXVST:
            points = new double[1][2][runTime+1];
            for (int i = 0; i <= runTime; i++) {
               points[0][0][i] = i;
               points[0][1][i] = tabularOutputMatrix[i][numClasses];
            }
            //returnValue = new BasicPlotInfo(data, kSIGMANXVSTMAINCAPTION, kSIGMANXVSTXCAPTION, kSIGMANXVSTYCAPTION);
            returnValue = new BasicPlotInfo(points, kMainCaption, kSIGMANXVSTXCAPTION, kSIGMANXVSTYCAPTION);
            break;
         case ASPGPanel.kNXSIGMANXVST:
            String yCap = kNXSIGMANXVSTYCAPTION;
            if(ageClassToView >= 0){
               points = new double[1][2][runTime+1];
               for (int i = 0; i <= runTime; i++) {
                  points[0][0][i] = i;
                  points[0][1][i] = tabularOutputMatrix[i][ageClassToView] / tabularOutputMatrix[i][numClasses];
               }
               yCap += " ( " + ColorScheme.getColorString(0) + ageClassToView + "</>)  ";
            } else {
               points = new double[numClasses][2][runTime+1];
               yCap += " ( ";
               for(int j = 0; j < numClasses; j++){
                  for (int i = 0; i <= runTime; i++) {
                     points[j][0][i] = i;
                     points[j][1][i] = tabularOutputMatrix[i][j] / tabularOutputMatrix[i][numClasses];
                  }
                  yCap += "" + ColorScheme.getColorString(j%(ColorScheme.colors.length)) + j + "</>";
                  if(j<(numClasses-1))
                     yCap += ",";
               }
               yCap += ")  ";
            }
            //returnValue = new BasicPlotInfo(data, kNXSIGMANXVSTMAINCAPTION, kNXSIGMANXVSTXCAPTION, kNXSIGMANXVSTYCAPTION);
            returnValue = new BasicPlotInfo(points, kMainCaption, kNXSIGMANXVSTXCAPTION, yCap);
            break;
         case ASPGPanel.kNXSIGMANXVSX:
            String yNXCap = kNXSIGMANXVSXYCAPTION;
            yNXCap += " (vs  <i>t=</i>"+runTime+")";

            points = new double[1][2][numClasses];
            for (int i = 0; i < numClasses; i++) {
               points[0][0][i] = i+shift;
               points[0][1][i] = tabularOutputMatrix[runTime][i] / tabularOutputMatrix[runTime][numClasses];
            }
            //returnValue = new BasicPlotInfo(data, kNXSIGMANXVSXMAINCAPTION, kNXSIGMANXVSXXCAPTION, kNXSIGMANXVSXYCAPTION);
            returnValue = new BasicPlotInfo(points, kMainCaption, kNXSIGMANXVSXXCAPTION, yNXCap);

            break;
         case ASPGPanel.kVXVSX:
            points = new double[1][2][numClasses+1];
            double temp;
            findValues();

            for (int i = 0; i < numClasses; i++) {
               va = 0.0;
               for (int xi = i; xi < numClasses; xi++) {
                  va += Math.exp(-r * ((double) xi)) * lxmxTable[kL][xi] * lxmxTable[kM][xi];
               }
               //For some reason, the last number should be 0.0.
               //Should we just skip it, making it automatically 0?
               temp = (lxmxTable[kL][i] == 0) ? 0 : va * Math.exp(r * i) / lxmxTable[kL][i];
               points[0][0][i] = i+shift;
               points[0][1][i] = temp;
            }
            points[0][0][points[0][0].length-1] = points[0][0].length-1+shift;
            points[0][1][points[0][1].length-1] = 0;

            //returnValue = new BasicPlotInfo(data, kVXVSXMAINCAPTION, kVXVSXXCAPTION, kVXVSXYCAPTION);
            returnValue = new BasicPlotInfo(points, kMainCaption, kVXVSXXCAPTION, kVXVSXYCAPTION);
            break;
         case ASPGPanel.kLXVSX:
            points = new double[1][2][numClasses+1];
            for (int i = 0; i <= numClasses; i++) {
               points[0][0][i] = i+shift;
               if(i!=numClasses)
                  points[0][1][i] = lxmxTable[kL][i];
               else
                  points[0][1][i] = 0;
            }
            returnValue = new BasicPlotInfo(points, kMainCaption, kLXVSXXCAPTION, kLXVSXYCAPTION);
            break;
         case ASPGPanel.kMXVSX:
            points = new double[1][2][numClasses+1];
            for (int i = 0; i <= numClasses; i++) {
               points[0][0][i] = i+shift;
               if(i!=numClasses)
                  points[0][1][i] = lxmxTable[kM][i];
               else
                  points[0][1][i] = 0;
            }
            returnValue = new BasicPlotInfo(points, kMainCaption, kMXVSXXCAPTION, kMXVSXYCAPTION);
            break;
         case ASPGPanel.kXVSNXT://3d
		points = new double[numClasses][3][runTime+1];
            for(int j = 0; j < numClasses; j++){
               for(int i = 0; i <= runTime; i++){
                  points[j][0][i] = j+shift;
                  points[j][1][i] = tabularOutputMatrix[i][j];
                  points[j][2][i] = i;
               }
            }
            returnValue = new BasicPlotInfo(points,kMainCaption,"Age Class (x)","S<sub>x</sub>");
            returnValue.setZCaption("Time Interval (t)");
            returnValue.setGraphType(BasicPlotInfo.k3D);
            returnValue.setLabelsT(false);
            break;
         case ASPGPanel.kXVSNXSIGMANXT://3d
		points = new double[numClasses][3][runTime+1];
            for(int j = 0; j < numClasses; j++){
               for(int i = 0; i <= runTime; i++){
                  points[j][0][i] = j+shift;
                  points[j][1][i] = tabularOutputMatrix[i][j] / tabularOutputMatrix[i][numClasses];
                  points[j][2][i] = i;
               }
            }
            returnValue = new BasicPlotInfo(points,kMainCaption,"Age Class (x)","S<sub>x</sub>/\u03a3S<sub>x</sub>");
            returnValue.setZCaption("Time Interval (t)");
            returnValue.setGraphType(BasicPlotInfo.k3D);
            returnValue.setLabelsT(false);
            break;
         case ASPGPanel.kEIGEN:
            Matrix m = Matrix.constructWithCopy(data.getLeslieMatrix());
            EigenvalueDecomposition ed = m.eig();
            returnValue = new BasicPlotInfo(ed);
            break;
         default:
            //Unimplemented or unknown model
            System.err.println(res.getString("Unknown_model_type"));
         break;
      }
      //can't set the colors of a bpi that isn't a graph...
      if((outputType != ASPGPanel.kLESLIEMATRIX) && (outputType != ASPGPanel.kTABULAROUTPUT)){
         returnValue.setLineStyle(BasicPlotInfo.DASHED);
         returnValue.setLineColor(0,Color.black);
         returnValue.setSymbolStyle(0,BasicPlotInfo.DOTS);
      }
      returnValue.setIsDiscrete(true);
      return returnValue;
   }

   public double[][] getMatrix() {
      if (outputType == ASPGPanel.kLESLIEMATRIX) {
         return NumberMath.roundedMatrix(data.getLeslieMatrix(true));
      } else if (outputType == ASPGPanel.kTABULAROUTPUT) {
         return NumberMath.roundedMatrix(data.getTabularOutput());
      }
      return null;
   }

   public JComponent[] getMatrixLabels() {
      //if ((outputType != ASPGPanel.kTABULAROUTPUT) && (outputType != ASPGPanel.kLESLIEMATRIX)) return null;
      if (outputType != ASPGPanel.kTABULAROUTPUT) return null;
      final int i = getXEnd() - getXStart();
      JComponent[] temp = new JComponent[i];
      for (int j = 0; j < i; j++) {
         temp[j] = new HTMLLabel("S<sub>x</sub>("+j+") ");
      }
      return temp;
   }

   public String[] getTitle() {
      if (title == null) {
         String def = res.getString("Age_Structured");
         if (outputType == ASPGPanel.kLESLIEMATRIX) {
            title = new String[] {def, res.getString("Leslie_Projection")};
         } else if (outputType == ASPGPanel.kTABULAROUTPUT) {
            findValues();
            title = new String[] {def, res.getString("Tabular_Projections")+" R0 = "+
               NumberMath.roundSigScientific(r0,4,0)+", Tc = "+
               NumberMath.roundSigScientific(g,4,0)+", r = "+
               NumberMath.roundSigScientific(r,4,0)+", Ln(R0)/Tc = "+
               NumberMath.roundSigScientific(Math.log(r0)/g,4,0) };
         } else {
            title = new String[] {res.getString("INVALID_TITLE")};
         }
      }
      return title;
   }

   public int getXStart() {
      return 0;
   }

   public int getYStart() {
      return 0;
   }

   public int getXEnd() {
      return getMatrix().length;
   }

   public int getYEnd() {
      if (outputType == ASPGPanel.kLESLIEMATRIX) {
         return getMatrix()[0].length;
      } else if (outputType == ASPGPanel.kTABULAROUTPUT) {
         return getMatrix()[0].length-1;
      }
      return -1;
   }

   /**
    * sets r0, g, r
    */
   void findValues(){
      //Braces are used to section this off so that variables are more local
      double temp; //r0=0, r=0, g=0,va ;
      double rhigh, rlow, rlast;
      int xi;
      int numClasses = data.getNumClasses();
      //refer to lines 343-433 of ASPG.pas
      //NOTE: qm1 = numClasses in NEW VERSION ONLY!!!

      for (xi = 0; xi < numClasses; xi++) {
         temp = lxmxTable[kL][xi] * lxmxTable[kM][xi];
         r0 += temp;//R0
         g += temp * xi;//the same as Tc
      }
      //returnValue.setMainCaption(kMainCaption +", <i>R0</i> = " + r0);
      g = (r0 != 0) ? g / r0: 0;
      r = ((g != 0) && (r0 != 0)) ? Math.log(r0) / g : 0;
      rhigh = r + kMAXR;
      rlow = r - kMAXR;
      do {
         temp = 0;
         for (xi = 1; xi < numClasses; xi++) {
            temp += Math.exp(-r*xi) * lxmxTable[kL][xi] * lxmxTable[kM][xi];
         }
         rlast = r;
         if (temp > 1.0) {
            rlow = r;
            r = (r + rhigh)/2;
         } else if (temp < 1.0) {
            rhigh = r;
            r = (r + rlow)/2;
         }

      }while (((Math.abs(temp-1) > kRPREC) || (Math.abs(r-rlast) > kRPREC)) && (temp != 0.0));
   }
}
