package edu.umn.ecology.populus.resultwindow;

/**
  * Used to communicate between an output panel and its creator(s)
  * 
  * @author Lars Roe
  */

public interface OutputPanelListener {
   
   public void outputChangeRequested( OutputPanelEvent e );
}
