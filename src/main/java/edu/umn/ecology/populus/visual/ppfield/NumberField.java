/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual.ppfield;

import edu.umn.ecology.populus.math.NumberMath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Will soon be a more robust component
 * designed especially for numbers.
 * possibly have it only recognize numbers (and of course, e, ., +, and -)
 */

public class NumberField extends JTextField {
    /**
     *
     */
    private static final long serialVersionUID = -4708524697027027818L;
    public boolean integersOnly = false;

    public NumberField(double def) {
        this(def, 8);
    }

    public NumberField(double def, int columns) {
        this(NumberMath.formatNumber(def), columns);
    }

    public void setValue(double newValue) {
        setText(NumberMath.formatNumber(newValue));
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 30);
    }

    public NumberField() {
        this(0.0);
    }

    public boolean isIntegersOnly() {
        return integersOnly;
    }

    public boolean isValid(char c) {
        int ePosition = Math.max(getText().indexOf('e'), getText().indexOf('E'));
        StringBuffer text;

        //Can't add whitespace
        if (Character.isWhitespace(c)) {
            return false;
        }
        text = new StringBuffer(getText());
        if (getSelectedText() == null) /*something funny is going on here. if too many decimals, then doesn't really work*/ {
            return false;
        }
        if ((text.length() >= getColumns()) && (getSelectedText().length() == 0)) {
            return false;
        }
        if (c == '.' & isIntegersOnly()) {
            return false;
        }
        text.insert(getCaretPosition(), c);
        if (isIntegersOnly() && (ePosition < getCaretPosition())) {
            return false;
        }
        try {
            Double.valueOf(text.toString());
            return true;
        } catch (NumberFormatException e) {
            if (Character.toUpperCase(c) != 'E') {
                return false;
            }
            return (getCaretPosition() - text.length() == -1) && (getCaretPosition() != 0) && (ePosition == -1);
        }
    }

    public void setIntegersOnly(boolean newValue) {
        this.integersOnly = newValue;
    }

    public NumberField(String def, int columns) {

        //NOTE NEXT LINE: THE '-2'!!!!
        super(def, columns - 2);
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        addKeyListener(new NumbersOnly());
        setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    public double getDouble() throws NumberFormatException {
        return Double.valueOf(getText()).doubleValue();
    }

    public class NumbersOnly extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            char c = e.getKeyChar();
            if (Character.isISOControl(c)) {
                return;
            }
            if (!isValid(c)) {
                e.consume();
            }
        }
    }

	/*
private void writeObject(ObjectOutputStream oos) throws IOException {
javax.swing.plaf.TextUI tui = this.getUI();
this.setUI(null);
oos.defaultWriteObject();
edu.umn.ecology.populus.fileio.Logging.log("writeObject called in NF: " + this);
this.setUI(tui);
}

private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
ois.defaultReadObject();
edu.umn.ecology.populus.fileio.Logging.log("readObject called in NF: " + this);
//numberField.setText((String) ois.readObject());
}
	 */
}
