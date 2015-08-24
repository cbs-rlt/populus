/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot;
import edu.umn.ecology.populus.plot.BasicPlotInfo;

/**
  This interface is used for every model to provide the method used to get the
  data from the class that processes the input data.
  */

public interface BasicPlot {
   
   public BasicPlotInfo getBasicPlotInfo();
}
