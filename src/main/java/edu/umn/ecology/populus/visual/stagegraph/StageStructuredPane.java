/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual.stagegraph;

import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Vector;

public class StageStructuredPane extends JPanel implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7507895596014905385L;
    DrawingPane drawingPanel;
    JPanel controlPanel = new JPanel();
    JToggleButton addStage = new JToggleButton();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    JToggleButton addTransition = new JToggleButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JToggleButton removeElement = new JToggleButton();

    public static final int kSSPG = 0;
    public static final int kASPG = 1;
    public static double scale;

    static {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        if (d.getHeight() >= 1024) {
            scale = 1.0;
        } else {
            scale = (d.getHeight() - 350) / (1024 - 350);
            //scale = 0.7;
        }
    }

    public StageStructuredPane(int type) {
        setLayout(gridBagLayout1);
        setPreferredSize(new Dimension((int) (600 * scale), (int) (600 * scale)));
        drawingPanel = new DrawingPane(type, scale);
        addStage.setText("Add Stage");
        addStage.addActionListener(e -> addStage_actionPerformed(e));
        drawingPanel.setToggleButtons(addStage, addTransition, removeElement);
        controlPanel.setLayout(gridBagLayout2);
        addTransition.setText("Add Transition");
        addTransition.addActionListener(e -> addTransition_actionPerformed(e));
        removeElement.setText("Remove Element");
        removeElement.addActionListener(e -> removeElement_actionPerformed(e));
        add(drawingPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        add(controlPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        controlPanel.add(addStage, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        controlPanel.add(addTransition, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        controlPanel.add(removeElement, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }

    void addStage_actionPerformed(ActionEvent e) {
        if (addStage.isSelected()) {
            removeElement.setSelected(false);
            addTransition.setSelected(false);
        }
        drawingPanel.repaint();
    }

    void addTransition_actionPerformed(ActionEvent e) {
        if (addTransition.isSelected()) {
            addStage.setSelected(false);
            removeElement.setSelected(false);
        }
        drawingPanel.repaint();
    }

    void removeElement_actionPerformed(ActionEvent e) {
        if (removeElement.isSelected()) {
            addStage.setSelected(false);
            addTransition.setSelected(false);
        }
        drawingPanel.repaint();
    }

    /**
     * Keep a reference to the number of classes and which to view.  Call this before setData.
     * (It's not really a listener.)
     */
    public void numStagesListener(PopulusParameterField numberClasses, PopulusParameterField whichToView) {
        drawingPanel.numPPF = numberClasses;
        drawingPanel.whichPPF = whichToView;
    }

    public void startTimer() {
        drawingPanel.timeIndex = 0;
        drawingPanel.t.start();
    }

    public void stopTimer() {
        drawingPanel.t.stop();
    }

    public void setDefaultTitle(String s) {
        drawingPanel.defaultTitle = s;
    }

    public double[][] getMatrix() {
        return drawingPanel.getMatrixRepresentation();
    }

    public double[] getPopulations() {
        return drawingPanel.getPopulations();
    }

    public String[] getTitles() {
        return drawingPanel.getTitles();
    }

    public void setData(double[][] mat, double[] pops, String[] titles, int indexShift, boolean lastCanHaveStarts) {
        int newDim = pops.length;
        if (titles == null) {
            titles = new String[newDim];
            for (int i = 0; i < titles.length; i++) titles[i] = "";
        }
        if (mat.length != newDim || mat[0].length != newDim || titles.length != newDim) {
            edu.umn.ecology.populus.fileio.Logging.log("New stages dimensions don't match");
            return;
        }

        drawingPanel.clearQueue();
        int oldDim = drawingPanel.getNumStages();
        drawingPanel.indexShift = indexShift;
        drawingPanel.lastCanHaveStarts = lastCanHaveStarts;

        if (newDim > oldDim) {
            for (int i = oldDim; i < newDim; i++)
                drawingPanel.addStage(pops[i]).setName(titles[i]);
        } else if (newDim < oldDim) {
            for (int i = oldDim - 1; i >= newDim; i--) {
                drawingPanel.removeStage(drawingPanel.getStage(i + 1));
            }
        }

        for (int i = 0; i < Math.min(newDim, oldDim); i++) {
            Stage s = drawingPanel.getStage(i + 1);
            s.setName(titles[i]);
            s.getLabel().v = pops[i];
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != 0) {
                    drawingPanel.getTransition(j + 1, i + 1, true).l.v = mat[i][j];
                } else {
                    drawingPanel.removeTransition(drawingPanel.getTransition(j + 1, i + 1, false));
                }
            }
        }
    }


    class DrawingPane extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
        /**
         *
         */
        private static final long serialVersionUID = 2149768979816767741L;
        //Color background = Color.green;
        Color background = new Color(170, 170, 170);
        JToggleButton stageTB = null;
        JToggleButton transTB = null;
        JToggleButton removeTB = null;
        Vector<Stage> stages = new Vector<>(0);
        Vector<Stage> queue = new Vector<>(0);
        Vector<Transition> trans = new Vector<>(0);
        int prevx, prevy;
        Stage firstTrans = null;
        int timeIndex = 0;
        int type;
        boolean lastCanHaveStarts;
        int indexShift = 0;
        final double scale;
        StageShape currentSelection;
        public PopulusParameterField numPPF, whichPPF;
        public javax.swing.Timer t = new javax.swing.Timer(50, this);
        public String defaultTitle = "";

        public DrawingPane(int type, double scale) {
            this.type = type;
            this.scale = scale;
            setUpGraph();
            addMouseListener(this);
            addMouseMotionListener(this);
            //t.start();
            try {
                jbInit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            timeIndex += 20;
            repaint();
        }

        void setUpGraph() {

            addStage(45, 200, 1);
            addStage(250, 25, 0);
            addStage(250, 450, 0);
            addStage(500, 200, 0);
            addStage(250, 200, 0);
            //addStage(450,450,0);

			/*
         setUpTransition(1,2);
         setUpTransition(2,4);
         setUpTransition(4,3);
         setUpTransition(3,4);
         setUpTransition(1,3);
         setUpTransition(4,5);
         setUpTransition(5,1);
         /
         for(int i=0; i<trans.size(); i++)
            ((Transition)trans.get(i)).setLabelPosition();
			 */
        }

        public double[][] getMatrixRepresentation() {
            int dim = getNumStages();
            Transition t;
            int[] con;
            double[][] mat = new double[dim][dim];
            for (Transition tran : trans) {
                t = tran;
                con = t.getConnectionIndicies();
                mat[con[1] - 1][con[0] - 1] = t.getValue();
            }
            return mat;
        }

        public double[] getPopulations() {
            double[] pops = new double[getNumStages()];
            for (int i = 0; i < pops.length; i++)
                pops[i] = getStage(i + 1).getValue();
            return pops;
        }

        public String[] getTitles() {
            String[] titles = new String[getNumStages()];
            for (int i = 0; i < titles.length; i++)
                titles[i] = getStage(i + 1).getName(false);
            return titles;
        }

        public int getNumStages() {
            return stages.size() + queue.size();
        }

        public Stage getStage(int index) {
            return index <= stages.size() ? (Stage) stages.get(index - 1) : (Stage) queue.get(index - stages.size() - 1);
        }

        public int getStageIndex(Stage s) {
            if (stages.contains(s)) return stages.indexOf(s) + 1;
            if (queue.contains(s)) return queue.indexOf(s) + stages.size() + 1;
            return -1;
        }

        /* this method is provided so that if elements were previously placed in the queue, but were never
      actually added, they can be forgotten
         */
        public void clearQueue() {
            Stage s;
            for (Stage stage : queue) {
                s = stage;
                s.removeStage(trans);
            }
            queue.removeAllElements();

            stageTB.setSelected(false);
            removeTB.setSelected(false);
        }

        //normal stage addition
        Stage addStage(int x, int y, double n0) {
            Stage s = new Stage((int) (x * scale), (int) (y * scale), n0, type, stages, queue, this);
            stages.add(s);
            return s;
        }

        //add stage to the queue
        Stage addStage(double n0) {
            Stage s = new Stage(-99999, -99999, n0, type, stages, queue, this);
            if (type == StageStructuredPane.kASPG) {
                stages.add(s);
            } else {
                queue.add(s);
            }
            return s;
        }

        Transition setUpTransition(int stageStart, int stageStop) {
            int[] c;
            if (stageStart > getNumStages() || stageStop > getNumStages()) return null;

            if (type == StageStructuredPane.kASPG) {
                int diff = stageStop - stageStart;
                if (stageStart == getNumStages() && !lastCanHaveStarts)
                    return null;
                if (diff > 0 && diff != 1)
                    return null;
                if (diff < 0 && stageStop != 1)
                    return null;
            }

            Stage from = getStage(stageStart);
            Stage to = getStage(stageStop);

            Transition t = new Transition(1, from, to, type);

            from.registerTransition(t, true);
            to.registerTransition(t, false);

            for (Transition tran : trans) {
                c = tran.getConnectionIndicies();
                if (c[0] == stageStop && c[1] == stageStart) {
                    t.setStraight(false);
                    tran.setStraight(false);
                }
                if (c[0] == stageStart && c[1] == stageStop) {
                    return null;
                }
            }
            trans.add(t);
            return t;
        }

        Transition getTransition(int from, int to, boolean createNew) {
            return getTransition(new int[]{from, to}, createNew);
        }

        Transition getTransition(int[] con, boolean createNew) {
            int[] c;
            for (Transition tran : trans) {
                c = tran.getConnectionIndicies();
                if (c[0] == con[0] && c[1] == con[1]) {
                    return tran;
                }
            }
            if (createNew) return setUpTransition(con[0], con[1]);
            return null;
        }

        @Override
        public void paint(Graphics g) {
            String stageMessage = "Click to place new stage";
            if (!queue.isEmpty()) {
                stageTB.setSelected(true);
                removeTB.setSelected(false);
                transTB.setSelected(false);
                firstTrans = null;
                stageMessage = "Select location for the added stage";
            }

            setStageDefaultPositions();

            Rectangle r = getBounds();
            g.setColor(background);
            g.fillRect(r.x, r.y, r.width, r.height);

            for (Transition transition : trans) {
                transition.setTimeIndex(timeIndex);
                transition.paint(g);
            }
            for (Stage s : stages) {
                s.setIndexShift(indexShift);
                s.paint(g);
            }
            for (Transition tran : trans) tran.paintLabel(g);
            for (Stage stage : stages) stage.paintLabel(g);

            if (stageTB != null && stageTB.isSelected())
                g.drawString(stageMessage, 10, 20);

            if (transTB != null && transTB.isSelected()) {
                if (firstTrans == null) {
                    g.drawString("Select source stage for transition", 10, 20);
                } else {
                    g.drawString("Select destination for transition from " + firstTrans.getShiftedIndex(), 10, 20);
                }
            }

            if (removeTB != null && removeTB.isSelected()) {
                g.drawString("Select element to remove", 10, 20);
            }
        }

        public StageShape getSelection(Point p) {
            for (Transition transition : trans)
                if (transition.getLabel().contains(p))
                    return transition.getLabel();
            for (Stage value : stages)
                if (value.getLabel().contains(p))
                    return value.getLabel();
            for (Stage stage : stages)
                if (stage.contains(p))
                    return stage;
            for (Transition tran : trans)
                if (tran.contains(p))
                    return tran;
            return null;
        }

        public void setToggleButtons(JToggleButton stageTB, JToggleButton transTB, JToggleButton removeTB) {
            this.stageTB = stageTB;
            this.transTB = transTB;
            this.removeTB = removeTB;
        }

        public void removeStage(Stage s) {
            if (stages.size() <= 1) return;
            if (stages.contains(s)) {
                stages.remove(s);
            } else if (queue.contains(s)) {
                queue.remove(s);
            } else {
                edu.umn.ecology.populus.fileio.Logging.log("Stage not available to remove...");
            }
            if (type == StageStructuredPane.kASPG && !lastCanHaveStarts)
                getStage(getNumStages()).removeStarts(trans);
            s.removeStage(trans);
            numPPF.setCurrentValue(getNumStages());
            whichPPF.setMaxValue(getNumStages());
        }

        public void removeTransition(Transition t) {
            if (t == null) return;
            Stage[] c = t.getConnectionStages();
            trans.remove(t);
            c[0].removeTransition(t);
            c[1].removeTransition(t);
            int[] temp = t.getConnectionIndicies();
            Transition t2 = getTransition(temp[1], temp[0], false);
            if (t2 != null) {
                t2.setStraight(true);
                t2.setLabelPosition();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            currentSelection = getSelection(e.getPoint());
            if (stageTB != null && stageTB.isSelected()) {
                if (!queue.isEmpty()) {
                    /*      queue has a new stage to be located    */
                    Stage s = queue.firstElement();
                    s.setPosition(e.getPoint(), getBounds());
                    queue.remove(s);
                    stages.add(s);
                } else {
                    /*      create a new stage      */
                    Stage s = addStage(e.getX(), e.getY(), 0);
                    s.setIndexShift(indexShift);
                    s.setName(defaultTitle);
                    ValueModifyFrame vmf = new ValueModifyFrame(this, s.getLabel(), type);
                    numPPF.setCurrentValue(getNumStages());
                    whichPPF.setMaxValue(getNumStages());
                    vmf.setVisible(true);
                }
                stageTB.setSelected(false);
            } else if (removeTB != null && removeTB.isSelected()) {
                /*     remove an element     */
                if (currentSelection == null) {
                    removeTB.setSelected(false);
                    return;
                }
                if (currentSelection instanceof Stage) {
                    /*    remove a Stage     */
                    if (type != StageStructuredPane.kASPG || getNumStages() > 2) {
                        removeStage((Stage) currentSelection);
                    }
                } else if (currentSelection instanceof Transition) {
                    /*    remove a Transition    */
                    removeTransition((Transition) currentSelection);
                }
                removeTB.setSelected(false);
            } else if (transTB != null && transTB.isSelected()) {
                /*      add a transition       */
                if (currentSelection == null) {
                    firstTrans = null;
                    transTB.setSelected(false);
                }
                if (!(currentSelection instanceof Stage)) return;
                if (firstTrans == null) {
                    /*   get transition source   */
                    firstTrans = (Stage) currentSelection;
                } else {
                    /*   get transition destination    */
                    Stage secTrans = (Stage) currentSelection;
                    Transition t = setUpTransition(firstTrans.getIndex(), secTrans.getIndex());
                    if (t == null) {
                        firstTrans = null;
                        transTB.setSelected(false);
                        return;
                    }
                    ValueModifyFrame vmf = new ValueModifyFrame(this, t.getLabel(), type);
                    t.setLabelPosition();

                    Transition t2 = getTransition(secTrans.getIndex(), firstTrans.getIndex(), false);
                    if (t2 != null) t2.setLabelPosition();
                    vmf.setVisible(true);
                    firstTrans = null;
                    transTB.setSelected(false);
                }
            } else {
                /*   fill stage or modify value for currentSelection    */
                if (e.getClickCount() == 1) {

                } else {
                    if (currentSelection == null) return;
                    ValueModifyFrame vmf = new ValueModifyFrame(this, currentSelection.getLabel(), type);
                    vmf.setVisible(true);
                }
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            currentSelection = getSelection(e.getPoint());
            prevx = e.getX();
            prevy = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            StageShape rs = getSelection(e.getPoint());
            for (Stage stage : stages) {
                stage.setActive(false);
                stage.l.setActive(false);
            }
            for (Transition tran : trans) {
                tran.setActive(false);
                tran.l.setActive(false);
            }
            if (rs != null) rs.setActive(true);

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentSelection == null) return;
            if (currentSelection instanceof Stage) {
                ((Stage) currentSelection).setPosition(e.getPoint(), this.getBounds());
            } else if (currentSelection instanceof Label && ((Label) currentSelection).getOwner() instanceof Stage) {
                ((Stage) ((Label) currentSelection).getOwner()).rotateLabel(e.getPoint());
            } else if (currentSelection instanceof Transition) {
                ((Transition) currentSelection).setShift(e.getPoint());
            } else if (currentSelection instanceof Label && ((Label) currentSelection).getOwner() instanceof Transition) {
                currentSelection.getLabel().setPosition(new PointD(e.getX(), e.getY()));
            }
            repaint();
        }

        private void jbInit() throws Exception {
            this.addComponentListener(new java.awt.event.ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    this_componentResized(e);
                }
            });
        }

        public void setStageDefaultPositions() {
            if (type == StageStructuredPane.kASPG) {
                Rectangle r = getBounds();
                double cushion = 100;
                double ydistance = (r.height - 2 * cushion) / (stages.size() - 1);
                double xdistance = (r.width - 2 * cushion) / (stages.size() - 1);
                for (int i = 0; i < stages.size(); i++) {
                    stages.get(i).setPosition(new Point((int) (cushion + i * xdistance), (int) (cushion + i * ydistance)), r);
                }
            }
        }

        void this_componentResized(ComponentEvent e) {
			/* this method is to ensure that the labels on the stages have the correct
         default positions*/
            for (Stage stage : stages) stage.setLabelPosition(getBounds());
        }
    }

	/*
   private void writeObject(java.io.ObjectOutputStream out)
       throws IOException
   {
   }

   private void readObject(java.io.ObjectInputStream in)
       throws IOException, ClassNotFoundException
   {
   }
	 */
}











