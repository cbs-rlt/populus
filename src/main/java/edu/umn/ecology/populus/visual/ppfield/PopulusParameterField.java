/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual.ppfield;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.visual.HTMLLabel;
import edu.umn.ecology.populus.visual.MultiLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.StringTokenizer;
import java.util.Vector;

public class PopulusParameterField extends JPanel implements Externalizable {
    final NumberField numberField = new NumberField();

    //JLabel plainLabel; // = new JLabel();
    final HTMLLabel fancyLabel = new HTMLLabel();
    final JMenuItem setIncrementAmount = new JMenuItem();
    final UpDownArrowSet arrowButtonSet = new UpDownArrowSet();
    final JPopupMenu popupMenu = new JPopupMenu();
    final JMenuItem setToMaximumMenuItem = new JMenuItem();
    final JMenuItem setToMinimumMenuItem = new JMenuItem();
    final JMenuItem setToDefaultMenuItem = new JMenuItem();
    final JMenuItem aboutParameterMenuItem = new JMenuItem();
    final JMenuItem cancelMenuItem = new JMenuItem();
    private double defaultValue;
    private double minValue;
    private double maxValue;
    private boolean autoIncrement = false;
    private boolean keepValueAnInteger = false;

    //private double currentValue;
    private String helpText;
    private String parameterName;
    private transient Vector parameterFieldListeners;
    private double incrementAmount = 1.0;

    //for willAutoUpdate

    //true: the parameter field will update the output automatically

    //false: the parameter field will not update the output automatically
    private boolean willAutoUpdate = true;
    private String unicodeName;
    private boolean useHTML = true;
    private double maxAutoUpdateValue = 1000.0;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();//essentially unlimited to start

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 30);
    }

    public boolean isIntegersOnly() {
        return this.numberField.isIntegersOnly();
    }

    public String getHelpText() {
        return helpText;
    }

    public int getColumns() {
        return numberField.getColumns();
    }

    public void setDefaultValue(double newDefaultValue) {
        defaultValue = newDefaultValue;
    }

    public void setCurrentValue(double newCurrentValue) {
        setCurrentValue(newCurrentValue, false);
    }

    /**
     * @param fire If true, sends an event
     */
    public void setCurrentValue(double newCurrentValue, boolean fire) {
        this.numberField.setValue(newCurrentValue);
        if (fire)
            fireParameterFieldChanged(new ParameterFieldEvent(this, ParameterFieldEvent.PROG));
    }

    public void setMaxValue(double newMaxValue) {
        setMaxValue(newMaxValue, false);
    }

    /**
     * @param fire If true, sends an event if the current value changes
     */
    public void setMaxValue(double newMaxValue, boolean fire) {
        if (fire) {
            double val = this.getCurrentValue();
            maxValue = newMaxValue;
            if (maxValue < val) {
                setCurrentValue(maxValue, fire);
            }
        } else {
            maxValue = newMaxValue;
            if (maxValue < getCurrentValue()) {
                setCurrentValue(maxValue, fire);
            }
        }
    }

    public void setMinValue(double newMinValue) {
        setMinValue(newMinValue, false);
    }

    /**
     * @param fire If true, sends an event if the current value changes
     */
    public void setMinValue(double newMinValue, boolean fire) {
        if (fire) {
            double val = this.getCurrentValue();
            minValue = newMinValue;
            if (minValue > val) {
                setCurrentValue(minValue, fire);
            }
        } else {
            minValue = newMinValue;
            if (minValue > getCurrentValue()) {
                setCurrentValue(minValue, fire);
            }
        }
    }

    public void setAutoIncrement(boolean yesAuto, boolean yesKeepInteger) {
        if (yesAuto) {
            autoIncrement = true;
        } else {
            autoIncrement = false;
        }
        if (yesKeepInteger) {
            keepValueAnInteger = true;
        } else {
            keepValueAnInteger = false;
        }
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    public int getInt() {
        return ((int) (getCurrentValue() + 0.5));
    }

    public void setUnicodeName(String newUnicodeName) {
        unicodeName = newUnicodeName;
    }

    public double getDouble() {
        return getCurrentValue();
    }

    /**
     * not sure if this deletes old component
     */

    public void setUseHTML(boolean newUseHTML) {
        useHTML = newUseHTML;
        fancyLabel.setHasHTMLLabels(false);
        //setUseHTML(newUseHTML, true);
    }

    public double getMaxValue() {
        return maxValue;
    }

    public boolean isUseHTML() {
        return useHTML;
    }

    public double getMinValue() {
        return minValue;
    }

    /**
     * NOT IMPLEMENTED YET!!
     */

    public String getPlainName() {
        //StringBuffer sb = new StringBuffer(parameterName.length());
        return parameterName;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setIncrement(double newIncrement) {
        setIncrementAmount(newIncrement);
    }

    public double checkAndSetCurrentValue(double newCurrentValue) {
        if (newCurrentValue < minValue) {
            newCurrentValue = minValue;

			/*
      if (getFrame() != null)
      new OKDialog(this.getFrame(), "Value has been", "set to minimum.", this.getLocationOnScreen());
			 */
        } else {
            if (newCurrentValue > maxValue) {
                newCurrentValue = maxValue;

				/*
         if (getFrame() != null)
         new OKDialog(this.getFrame(), "Value has been", "set to maximum.", this.getLocationOnScreen());
         }
				 */
            }
        }
        this.numberField.setValue(newCurrentValue);
        return newCurrentValue;
    }

    public void setIncrementAmount(double newIncrementAmount) {
        incrementAmount = newIncrementAmount;
    }

    public double getCurrentValue() {
        return getCurrentValue(false);
    }

    public double getCurrentValue(boolean fire) {
        double result;
        try {
            result = this.numberField.getDouble();
            result = checkAndSetCurrentValue(result);
        } catch (NumberFormatException e) {
            result = getDefaultValue();

            //if (getFrame() != null)

            //new OKDialog(this.getFrame(), "Bad input. Value has", "been reset to " + result, this.getLocationOnScreen());
            setCurrentValue(result, fire);
        }
        return result;
    }

    @Override
    public void writeExternal(ObjectOutput oo) throws IOException {
        oo.writeObject((parameterName != null) ? parameterName : "");
        oo.writeObject((helpText != null) ? helpText : "");
        oo.writeObject(numberField.getText());
        oo.writeObject((unicodeName != null) ? unicodeName : "");
        oo.writeDouble(this.minValue);
        oo.writeDouble(this.maxValue);
        oo.writeDouble(this.defaultValue);
        oo.writeDouble(this.incrementAmount);
        oo.writeInt(this.getColumns());
        oo.writeBoolean(this.useHTML);
        oo.writeBoolean(this.isEnabled());
    }

    public void setIntegersOnly(boolean newIntegersOnly) {
        this.numberField.setIntegersOnly(newIntegersOnly);
    }

    public PopulusParameterField() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setHelpText(String newHelpText) {
        helpText = newHelpText;
    }

    public void setAutoUpdateStopValue(double maxValue) {
        maxAutoUpdateValue = maxValue;
    }

    public void setColumns(int newColumns) {
        numberField.setColumns(newColumns);
    }

    public synchronized void removeParameterFieldListener(ParameterFieldListener l) {
        if (parameterFieldListeners != null && parameterFieldListeners.contains(l)) {
            Vector v = (Vector) parameterFieldListeners.clone();
            v.removeElement(l);
            parameterFieldListeners = v;
        }
    }

    @Override
    public void setEnabled(boolean newEnabled) {
        if (isEnabled() == newEnabled) {
            return;
        }
        super.setEnabled(newEnabled);
        numberField.setEnabled(newEnabled);
        getLabel().setEnabled(newEnabled);
        arrowButtonSet.setEnabled(newEnabled);
        setToMaximumMenuItem.setEnabled(newEnabled);
        setToMinimumMenuItem.setEnabled(newEnabled);
        setToDefaultMenuItem.setEnabled(newEnabled);
    }

    public synchronized void addParameterFieldListener(ParameterFieldListener l) {
        Vector v = parameterFieldListeners == null ? new Vector(2) : (Vector) parameterFieldListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            parameterFieldListeners = v;
        }
    }

    public void setLabel(java.awt.Component newLabel) {
        this.removeAll();
        this.add(newLabel, null);
        this.add(numberField, null);
        this.add(arrowButtonSet, null);
    }

    public boolean getAutoUpdate() {
        if (numberField.getDouble() > maxAutoUpdateValue) {
            willAutoUpdate = false;
        } else {
            willAutoUpdate = true;
        }
        return willAutoUpdate;
    }

    public String getUnicodeName() {
        return unicodeName;
    }

    @Override
    public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
        this.setParameterName((String) oi.readObject());
        this.setHelpText((String) oi.readObject());
        this.numberField.setText((String) oi.readObject());
        this.setUnicodeName((String) oi.readObject());
        this.minValue = oi.readDouble();
        this.maxValue = oi.readDouble();
        this.defaultValue = oi.readDouble();
        this.incrementAmount = oi.readDouble();
        this.numberField.setColumns(oi.readInt());
        this.useHTML = oi.readBoolean();
        this.setEnabled(oi.readBoolean());
    }

    /**
     * not sure if this deletes old component
     */

    public void setUseHTML(boolean newUseHTML, boolean check) {

        //if (check && (newUseHTML == useHTML)) return;
        useHTML = newUseHTML;
        if (useHTML) {
            setLabel(new HTMLLabel());
        } else {


            //setLabel(new JLabel());
        }
        this.setParameterName(this.getParameterName());
    }

    public void setParameterName(String newParameterName) {
        parameterName = newParameterName;
        //String text = "<i>" + parameterName + "</i> = ";
        fancyLabel.setText(parameterName + " = ");
    }

    public HTMLLabel getLabel() {
        return fancyLabel;
    }

    public String getParameterName() {
        return parameterName;
    }

    public double getIncrementAmount() {
        return incrementAmount;
    }

    /**
     * Makes a hand appear to indicate Parameter Popup Window possible
     * Also triggers Popups in other areas.
     */


    protected void fireParameterFieldChanged(ParameterFieldEvent e) {

		/*
      the autoupdate could be stopped here
		 */
        if (parameterFieldListeners != null && getAutoUpdate()) {
            Vector listeners = parameterFieldListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ParameterFieldListener) listeners.elementAt(i)).parameterFieldChanged(e);
            }
        }
    }


    void setToDefaultMenuItem_actionPerformed(ActionEvent e) {
        setCurrentValue(getDefaultValue());
        fireParameterFieldChanged(new ParameterFieldEvent(this, ParameterFieldEvent.MENU));
    }

    void setToMinimumMenuItem_actionPerformed(ActionEvent e) {
        setCurrentValue(getMinValue());
        fireParameterFieldChanged(new ParameterFieldEvent(this, ParameterFieldEvent.MENU));
    }

    void setToMaximumMenuItem_actionPerformed(ActionEvent e) {
        setCurrentValue(getMaxValue());
        fireParameterFieldChanged(new ParameterFieldEvent(this, ParameterFieldEvent.MENU));
    }

    void setIncrementAmount_actionPerformed(ActionEvent e) {

    }

    void aboutParameterMenuItem_actionPerformed(ActionEvent e) {
        MultiLabel ml;
        if (this.getHelpText() != null) {
            StringTokenizer st = new StringTokenizer(this.getHelpText(), "\n");
            Object[] stringLines;
            int i;
            stringLines = new Object[3 + st.countTokens()];
            stringLines[0] = "Parameter Name:";
            HTMLLabel label = new HTMLLabel(getParameterName());
            Font f = label.getDefaultFont();
            f = f.deriveFont(f.getSize2D() * 2.0f);
            label.setDefaultFont(f);
            stringLines[1] = label;
            stringLines[2] = "Parameter Description:";
            for (i = 3; st.hasMoreTokens(); i++) {
                stringLines[i] = st.nextToken();
            }
            ml = new MultiLabel(stringLines, null, 4, 0, SwingConstants.LEFT);
        } else {
            ml = new MultiLabel(new String[]{
                    "*NO INFORMATION AVAILABLE*"
            }, null, 4, 0, SwingConstants.LEFT);
        }
        JOptionPane.showMessageDialog(DesktopWindow.defaultWindow, ml, "About Parameter", JOptionPane.INFORMATION_MESSAGE);
    }

    private void increment(boolean up) {
        double change;
        int type;
        if (autoIncrement) {
            setAutoIncrementAmount();
        }
        if (up) {
            type = ParameterFieldEvent.INC;
            change = incrementAmount;
        } else {
            type = ParameterFieldEvent.DEC;
            change = -incrementAmount;
        }
        checkAndSetCurrentValue(change + getCurrentValue());
        fireParameterFieldChanged(new ParameterFieldEvent(this, type));
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        arrowButtonSet.addActionListener(e -> {
            if (e.getActionCommand() == UpDownArrowSet.UP) {
                increment(true);
            } else {
                increment(false);
            }
        });
        cancelMenuItem.setText("Cancel");
        setToDefaultMenuItem.setText("Set to Default");
        setToDefaultMenuItem.addActionListener(this::setToDefaultMenuItem_actionPerformed);
        setToMinimumMenuItem.setText("Set to Minimum");
        setToMinimumMenuItem.addActionListener(this::setToMinimumMenuItem_actionPerformed);
        setToMaximumMenuItem.setText("Set to Maximum");
        setToMaximumMenuItem.addActionListener(this::setToMaximumMenuItem_actionPerformed);
        aboutParameterMenuItem.setText("About Parameter");
        aboutParameterMenuItem.addActionListener(this::aboutParameterMenuItem_actionPerformed);
        numberField.addKeyListener(new EnterListener());
        this.setEnabled(true);
        this.setDoubleBuffered(false);
        setIncrementAmount.addActionListener(this::setIncrementAmount_actionPerformed);
        setIncrementAmount.setText("Set Increment Amount");
        this.add(fancyLabel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(numberField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(arrowButtonSet, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        popupMenu.add(cancelMenuItem);
        popupMenu.add(setToDefaultMenuItem);
        popupMenu.add(setToMinimumMenuItem);
        popupMenu.add(setToMaximumMenuItem);

        //Do not implement yet!!

        //popupMenu.add(setIncrementAmount);
        popupMenu.add(aboutParameterMenuItem);
        fancyLabel.addMouseListener(new PopupListener());
        fancyLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void setAutoIncrementAmount() {
        double value = getCurrentValue();
        if (value >= (maxValue / 2)) {
            incrementAmount = maxValue / 10;
        } else {
            if (value >= (maxValue / 5)) {
                incrementAmount = maxValue / 20;
            } else {
                if (value >= (maxValue / 10)) {
                    incrementAmount = maxValue / 50;
                } else {
                    incrementAmount = maxValue / 100;
                }
            }
        }
        if (keepValueAnInteger) {
            int temp;
            temp = (int) incrementAmount;
            incrementAmount = temp;
        }
    }

    class EnterListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                fireParameterFieldChanged(new ParameterFieldEvent(this, ParameterFieldEvent.ENTER));
            }
        }
    }

    class PopupListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if ((e.isPopupTrigger() || e.getSource() == getLabel()) && e.getID() != 502) {

                //show menu
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
