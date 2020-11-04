package edu.umn.ecology.populus.plot;

import Jama.EigenvalueDecomposition;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.math.Routines;
import edu.umn.ecology.populus.resultwindow.OutputPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;

/**
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 *
 * @author unascribed
 * @version 1.0
 */

public class EigenSystem extends OutputPanel {
    /**
     *
     */
    private static final long serialVersionUID = -7689433644030491113L;
    double[][] eigenvectors;
    double[] re, ie;
    String[] text;
    private JList eigenValueList = new JList();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JScrollPane eigenVectorPane = new JScrollPane();
    private JPanel eigenVectorPanel = new JPanel();
    private JLabel valueTL = new JLabel();
    private JLabel valueL = new JLabel();
    private JLabel vectorTL = new JLabel();
    private JLabel[] vector;
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private int dominantIndex;

    public EigenSystem(BasicPlotInfo bpi) {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateData(bpi);
    }

    double formatNumber(double d) {
        if (Math.abs(d) < 1e-10) return 0.0;
        return NumberMath.roundSig(d, 5, 0);
    }

    public void updateData(BasicPlotInfo bpi) {
        EigenvalueDecomposition ed = (EigenvalueDecomposition) bpi.getSpecial();
        this.ie = ed.getImagEigenvalues();
        this.re = ed.getRealEigenvalues();
        double[][] eigenMatrix = ed.getV().getArray();
        vector = new JLabel[re.length];
        eigenvectors = new double[re.length][re.length];
        for (int i = 0; i < re.length; i++)
            for (int j = 0; j < re.length; j++)
                eigenvectors[i][j] = eigenMatrix[j][i];
        dominantIndex = 0;
        for (int i = 0; i < re.length; i++) {
            ie[i] = formatNumber(ie[i]);
            re[i] = formatNumber(re[i]);
            eigenvectors[i] = Routines.normalize(eigenvectors[i]);
            for (int j = 0; j < eigenvectors[i].length; j++)
                eigenvectors[i][j] = formatNumber(eigenvectors[i][j]);
            if (ie[i] == 0 && re[i] > re[dominantIndex]) dominantIndex = i;
        }
        createEigenValueList();
        createVectorList(dominantIndex);
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);

        eigenValueList.addListSelectionListener(e -> eigenValueList_valueChanged(e));

        valueTL.setText("Eigenvalue: ");
        vectorTL.setText("Eigenvector: ");
        eigenVectorPanel.setLayout(gridBagLayout2);

        valueL.setPreferredSize(new Dimension(100, 20));

        this.add(eigenValueList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(eigenVectorPane, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        eigenVectorPane.getViewport().add(eigenVectorPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    void createEigenValueList() {
        text = new String[re.length];
        for (int i = 0; i < re.length; i++) {
            text[i] = "";
            if (re[i] != 0) {
                text[i] = "" + re[i] + " ";
                if (ie[i] > 0) text[i] += "+ ";
            }
            if (ie[i] != 0) text[i] += "" + ie[i] + "i";
            if (text[i].length() == 0) text[i] = "0.0";
            if (i == dominantIndex) text[i] += " (dominant)";
        }
        eigenValueList.setListData(text);
        eigenValueList.setSelectedIndex(dominantIndex);
    }

    void createVectorList(int index) {
        if (index < 0) return;
        eigenVectorPanel.removeAll();
        valueL.setText(text[index]);
        eigenVectorPanel.add(valueTL, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        eigenVectorPanel.add(valueL, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        eigenVectorPanel.add(vectorTL, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        for (int i = 0; i < vector.length; i++) {
            vector[i] = new JLabel();
            vector[i].setPreferredSize(new Dimension(100, 20));
            vector[i].setText("" + (i + 1) + ": " + eigenvectors[index][i]);
            eigenVectorPanel.add(vector[i], new GridBagConstraints(0, i + 2, 2, 1, 0.0, 0.0
                    , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        eigenVectorPanel.revalidate();
        repaint();
    }

    void eigenValueList_valueChanged(ListSelectionEvent e) {
        createVectorList(eigenValueList.getSelectedIndex());
    }
}
