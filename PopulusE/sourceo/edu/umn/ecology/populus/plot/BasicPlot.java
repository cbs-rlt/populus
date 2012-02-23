package edu.umn.ecology.populus.plot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

/**
  This interface is used for every model to provide the method used to get the
  data from the class that processes the input data.
  */

public interface BasicPlot {
   
   public BasicPlotInfo getBasicPlotInfo();
}
