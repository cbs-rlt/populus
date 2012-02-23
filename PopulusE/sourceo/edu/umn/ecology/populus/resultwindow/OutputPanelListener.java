package edu.umn.ecology.populus.resultwindow;
import edu.umn.ecology.populus.plot.*;
import java.util.*;
import java.awt.*;

/**
  * Used to communicate between an output panel and its creator(s)
  * 
  * @author Lars Roe
  */

public interface OutputPanelListener {
   
   public void outputChangeRequested( OutputPanelEvent e );
}
