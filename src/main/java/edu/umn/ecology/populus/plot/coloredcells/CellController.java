/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.coloredcells;

import edu.umn.ecology.populus.constants.MenuOptions;
import edu.umn.ecology.populus.constants.OutputTypes;
import edu.umn.ecology.populus.resultwindow.OutputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class will display a picture of tiles
 */
public class CellController extends OutputPanel implements Runnable, KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 3862996446929889045L;
    final Thread cellUpdater;
    CellPanel cellPanel;
    boolean isDone = false, isSuspended = false;
    CellFunction cf;
    final GridBagLayout gbl1 = new GridBagLayout();
    private long pauseTime = 100;
    final JToggleButton pauseButton = new JToggleButton();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JPanel jPanel1 = new JPanel();
    final JComboBox<String> typeChangeCB = new JComboBox<String>();
    final GridBagLayout gridBagLayout2 = new GridBagLayout();
    final JLabel gensL = new JLabel();
    int numRuns;

    public CellController(CellFunction evaluator) {
        setType(OutputTypes.kCell);
        setBackground(Color.black);
        this.setLayout(gbl1);
        cf = evaluator;
        cellPanel = new CellPanel(this, cf.initialF(), cf.getStrings(), cf.getDemarcations(), cf.getColorPalette());
        cellPanel.setBackground(Color.black);
        if (cf.getBreakInterval() == 1) setPaused(true);
        try {
            jbInit();
            addKeyListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        numRuns = evaluator.getGeneration();
        cellUpdater = new Thread(this);
        cellUpdater.start();
    }

    public void changeCellFunction(CellFunction evaluator) {
        if (cellPanel != null) remove(cellPanel);
        cf = evaluator;
        numRuns = 0;
        cellPanel = new CellPanel(this, cf.initialF(), cf.getStrings(), cf.getDemarcations(), cf.getColorPalette());
        typeChangeCB.setSelectedItem(cf.getCurrentType());
        add(cellPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        isSuspended = isDone = false;
        numRuns = evaluator.getGeneration();
        gensL.setText("" + numRuns);
        if (cf.getBreakInterval() == 1) setPaused(true);
        else setPaused(false);
        updateUI();
    }

    @Override
    public void run() {
        double[][] calc = new double[1][1];
        boolean valuesSet = false;
        long saveTime = 0;
        requestFocus();
        while (!isDone) {
            try {
                if (saveTime < pauseTime)
                    Thread.sleep(pauseTime - saveTime);
                else
                    Thread.sleep(0);
            } catch (InterruptedException e) {
            }
            saveTime = System.currentTimeMillis();
            if (!valuesSet && !isSuspended) {
                valuesSet = true;
                calc = cf.f();
            }
            saveTime = System.currentTimeMillis() - saveTime;
            if (!isSuspended) {
                //edu.umn.ecology.populus.fileio.Logging.log("Free memory at "+numRuns+":\t"+Runtime.getRuntime().freeMemory());
                cellPanel.setValues(calc, cf.getStrings(), cf.getDemarcations());
                valuesSet = false;
                numRuns++;
                gensL.setText("" + numRuns);
                if (numRuns % cf.getBreakInterval() == 0) setPaused(true);
            }
        }
        try {
            cellUpdater.join(1000);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void destroy() {
        isDone = isSuspended = true;
    }

    public void setPauseTime(long newTime) {
        pauseTime = newTime;
    }

    private void jbInit() throws Exception {
        String[] a = cf.getOutputTypes();
        if (a != null) {
            for (String s : a) {
                typeChangeCB.addItem(s);
            }
            typeChangeCB.setSelectedItem(cf.getCurrentType());
        }
        pauseButton.addActionListener(e -> pauseButtonChange(e));
        setLayout(gridBagLayout1);
        pauseButton.setPreferredSize(new Dimension(90, 25));
        pauseButton.setText("Pause");
        jPanel1.setLayout(gridBagLayout2);
        typeChangeCB.addActionListener(e -> typeChangeCB_actionPerformed(e));
        gensL.setText("" + cf.getGeneration());
        gensL.setBackground(Color.black);
        gensL.setForeground(Color.white);
        gensL.setPreferredSize(new Dimension(40, 17));
        jPanel1.setBackground(Color.black);
        add(cellPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        if (a != null && a.length > 0)
            jPanel1.add(typeChangeCB, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel1.add(pauseButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
        jPanel1.add(gensL, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    }

    public void setPaused(boolean b) {
        isSuspended = b;
        pauseButton.setSelected(b);
        if (b) {
            pauseButton.setText("Resume");
        } else {
            pauseButton.setText("Pause");
        }
        requestFocus();
    }

    void pauseButtonChange(ActionEvent e) {
        //edu.umn.ecology.populus.fileio.Logging.log("total memory: "+Runtime.getRuntime().totalMemory());
        if (pauseButton.isSelected()) {
            isSuspended = true;
            pauseButton.setText("Resume");
        } else {
            isSuspended = false;
            pauseButton.setText("Pause");
            /*a call to the garbage collector should happen somewhere. is this a good place?*/
            System.gc();
        }
    }

    void typeChangeCB_actionPerformed(ActionEvent e) {
        //Get value from backing model, not current (and unchecked) input
        double[][] v = null;
        try {
            cf.changeType(typeChangeCB.getItemAt(typeChangeCB.getSelectedIndex()));
        } catch (Exception ex) {
            ;//TODO
        }
        if (v == null) return;
        cellPanel.setValues(v, cf.getStrings(), cf.getDemarcations());
    }

    void modifyValue(int r, int c, double newValue) {
        cf.setValue(typeChangeCB.getSelectedIndex(), r, c, newValue);
    }

    void zoomIn() {
        CellDefaults.kHeight++;
        CellDefaults.kWidth++;
        cellPanel.repaint();
    }

    void zoomOut() {
        if (CellDefaults.kHeight <= 1 || CellDefaults.kWidth <= 1)
            return;
        CellDefaults.kHeight--;
        CellDefaults.kWidth--;
        cellPanel.repaint();
    }

    void reset() {
        CellDefaults.kHeight = CellDefaults.kHeightDefault;
        CellDefaults.kWidth = CellDefaults.kWidthDefault;
        cellPanel.repaint();
    }

    @Override
    public void showOptions(int whatOption) {
        switch (whatOption) {
            case MenuOptions.kCoarserGrid:
                break;
            case MenuOptions.kFinerGrid:
                break;
            case MenuOptions.kClearGrid:
                break;
            case MenuOptions.kOptionScreen:
                break;
            case MenuOptions.kReset:
                reset();
                break;
            case MenuOptions.kZoomIn:
                zoomIn();
                break;
            case MenuOptions.kZoomOut:
                zoomOut();
                break;
        }
    }

    /**
     * the help file accessed through the KeyListener javadoc says that the isFocusTraversable
     * method should be overridden from the parent component and set to return true every time.
     *
     * @return always will be true
     */
    @Override
    public boolean isFocusable() {
        return true;
    }

    /**
     * not useful right now, all my needs are covered with the other two methods from KeyListener
     *
     * @param ke
     */
    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    /**
     * this method picks up the zoom keys, which are set to be the buttons with + or - on them.
     *
     * @param ke
     */
    @Override
    public void keyTyped(KeyEvent ke) {
        char c = ke.getKeyChar();

        switch (c) {
            case '=', '+' -> zoomIn();
            case '-', '_' -> zoomOut();
            case 'r', 'R' -> reset();
        }
        ke.consume();
    }
}

/* debugging code:

long time = System.currentTimeMillis();
System.gc();
time = System.currentTimeMillis()-time;
edu.umn.ecology.populus.fileio.Logging.log("gc took "+((double)time)/1000+" seconds."); //usually about 1/4th sec
 */

