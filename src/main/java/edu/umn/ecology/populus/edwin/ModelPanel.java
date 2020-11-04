/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.edwin;

import edu.umn.ecology.populus.plot.ParamInfo;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldListener;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * ModelPanel serves as a root for all other models in the 'edwin' directory
 * Changes to this will affect all descendents.
 * <p>
 * All Populus models have a class that extends this one to provide the specific information
 * for the model including model names, help file names, and the simpleUpdateOutput() method
 * to provide the information indicating who should get the data for the output screen
 * (i.e. BasicPlotOutputPanel3D, BasicPlotOutputPanel, WoozleWindow, TableOutput etc).
 * Most models with multiple graphs will override the simpleUpdateOutput() method with
 * something like this from Age-Structured Population Growth.
 * <p>
 * <p>
 * <p>
 * This class is also the parent of BasicPlotInputPanel
 */

public class ModelPanel extends JPanel implements ModelPanelEventTypes, ParameterFieldListener, ActionListener, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7758198745066741486L;
    protected transient Vector<ModelPanelListener> listeners;

    public ParamInfo getParamInfo() {
        return null;
    }

    public String getOutputGraphName() {
        return null;
    }

    public synchronized void addModelPanelListener(ModelPanelListener listener) {
        listeners.addElement(listener);
    }

    //////////
    // EVENT
    // METHODS
    //////////

    @Override
    public void parameterFieldChanged(ParameterFieldEvent e) {
        processEvent(e);
    }

    public int getTriggers() {
        return ModelPanelEventTypes.ADJUSTMENT + ModelPanelEventTypes.CHANGE_PLOT + ModelPanelEventTypes.VISUAL;
    }

    public ModelPanel() {
        super();
        basicInitializer();
    }

    public synchronized void removeModelPanelListener(ModelPanelListener listener) {
        listeners.removeElement(listener);
    }

    public void destroy() {

        //remove listeners
        listeners.removeAllElements();

        //give panel a chance to clean up

        //Make sure all paramfields have lost their connections to any frames

        //so that they can be lost, too
    }

    /**
     * if the model is supposed to iterate the calculations as opposed to creating
     * an entirely new graph, the input panels derived from ModelPanel should override
     * this method so that they can prepare for the iterate command.
     *
     * @param isIterate
     */
    public void willIterate(boolean isIterate) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireModelPanelEvent(ModelPanelEventTypes.CHANGE_PLOT);
    }

    public void registerChildren(Component c) {
        if (c instanceof PopulusParameterField) {
            ((PopulusParameterField) c).addParameterFieldListener(this);
        } else if (c instanceof JButton) {
            ((JButton) c).addActionListener(this);
        } else if (c instanceof JRadioButton) {
            ((JRadioButton) c).addActionListener(this);
        } else if (c instanceof Container) {
            int i;
            Container cont;
            Component[] temp;
            cont = (Container) c;
            temp = cont.getComponents();
            for (i = 0; i < temp.length; i++) {
                registerChildren(temp[i]);
            }
        }
    }

    public void unregisterChildren(Component c) {
        if (c instanceof PopulusParameterField) {
            ((PopulusParameterField) c).removeParameterFieldListener(this);
        } else {
            if (c instanceof JButton) {
                ((JButton) c).removeActionListener(this);
            } else {
                if (c instanceof JRadioButton) {
                    ((JRadioButton) c).removeActionListener(this);
                } else {
                    if (c instanceof Container) {
                        int i;
                        Container cont;
                        Component[] temp;
                        cont = (Container) c;
                        temp = cont.getComponents();
                        for (i = 0; i < temp.length; i++) {
                            registerChildren(temp[i]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Call this to easily notify a parameterfield event
     */

    protected void processEvent(ParameterFieldEvent pfEvent) {
        ModelPanelEvent mpEvent = new ModelPanelEvent(this, pfEvent);
        Enumeration<ModelPanelListener> enm = listeners.elements();
        while (enm.hasMoreElements()) {
            enm.nextElement().modelPanelChanged(mpEvent);
        }
    }

    /**
     * Call this to easily notify an event
     */

    protected void fireModelPanelEvent(int type) {
        ModelPanelEvent event = new ModelPanelEvent(this, type);
        Enumeration<ModelPanelListener> e = listeners.elements();
        while (e.hasMoreElements()) {
            e.nextElement().modelPanelChanged(event);
        }
    }

    private void basicInitializer() {
        listeners = new Vector<>(1, 1);

        //add all listeners to model panel.  This should be done by subclasses.
        //registerChildren( this );
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        basicInitializer();
    }

    /**
     * Call this when the size of the panel may have resized
     */
    protected void repack() {
        fireModelPanelEvent(ModelPanelEventTypes.REPACK);
    }
}
