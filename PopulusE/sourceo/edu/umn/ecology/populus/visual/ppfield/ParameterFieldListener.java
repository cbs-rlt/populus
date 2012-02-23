
//Title:        Populus

//Version:      

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual.ppfield;
import java.util.*;

public interface ParameterFieldListener extends EventListener {
   
   public void parameterFieldChanged( ParameterFieldEvent e );
}
