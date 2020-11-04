/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual;

import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.model.ie.TextOutput;

import javax.swing.*;
import java.awt.*;

/**
 * Use this class for displaying matricies. Used by ASPG and SSPG
 */

public class Matrix extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 2911537167756373181L;
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    final JPanel matrixPanel = new JPanel();
    final JPanel topLabelsPanel = new JPanel();
    final Bracket leftBracket = new Bracket(SwingConstants.LEFT);
    final Bracket rightBracket = new Bracket(SwingConstants.RIGHT);
    //GridLayout labelGridLayout = new GridLayout();
    JComponent[] topLabels = null;
    boolean showBrackets = true;
    private double[][] matrixData;
    private final GridBagLayout labelsGBL = new GridBagLayout();
    private final GridBagLayout matrixGBL = new GridBagLayout();

    public Matrix() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        matrixPanel.setLayout(matrixGBL);
        this.setBackground(Color.white);
        matrixPanel.setBackground(Color.white);
        leftBracket.setBackground(Color.white);
        rightBracket.setBackground(Color.white);
        topLabelsPanel.setLayout(labelsGBL);
        this.add(matrixPanel, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(topLabelsPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void setMatrixData(double[][] newData) {
        setMatrixData(newData, 0, newData.length, 0, newData[0].length);
    }

    /**
     * From xstart to xend and ystart to yend (including start, but not end)
     * where xstart, xend, ystart, and yend refer to the bounds in the indicies. notice that
     * the slow loop is y, and the fast loop is j
     */

    public void setMatrixData(double[][] newData, int xstart, int xend, int ystart, int yend) {
        JLabel jl;
        matrixPanel.removeAll();
        matrixData = newData;
        Dimension d = new Dimension(60, 20);
        //matrixGridLayout.setColumns( xend - xstart );
        //matrixGridLayout.setRows( yend - ystart );
        for (int i = ystart; i < yend; i++) {
            for (int j = xstart; j < xend; j++) {
                //jl = new JLabel( TextOutput.NewStringLength(""+matrixData[j][i],5,' ',false));
                jl = new JLabel(TextOutput.NewStringLength(" " + NumberMath.formatNumber(matrixData[j][i], 3), 7, ' ', false));
                jl.setFont(new Font("Courier", Font.PLAIN, 12));
                jl.setPreferredSize(d);
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                matrixPanel.add(jl, new GridBagConstraints(j, i, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            }
        }
        //We trust that topLabels.length is xend-xstart!!!
		/*
      a quick hack is employed here, that for now, is probably ok --
      if there are top labels, then it is assumed the Matrix is a Tabular Projection
      and that we don't want brackets, if there aren't top labels, then we do want
      brackets...
		 */
        topLabelsPanel.removeAll();
        if (topLabels != null) {
            setTopLabelsVisible(true);
            setBracketsVisible(false);
            //labelGridLayout = new GridLayout( 1, xend - xstart );
            //topLabelsPanel.setLayout( labelGridLayout );
            for (int i = 0; i < topLabels.length; i++) {
                topLabels[i].setPreferredSize(d);
                topLabelsPanel.add(topLabels[i],
                        new GridBagConstraints(i, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
            }
        } else {
            setTopLabelsVisible(false);
            setBracketsVisible(true);
        }
        matrixPanel.revalidate();
    }

    /**
     * Call this before setMatrixData!
     */

    public void setTopLabels(JComponent[] list) {
        topLabels = list;
    }

    public void setTopLabelsVisible(boolean vis) {
        topLabelsPanel.setVisible(vis);
    }

    public void setBracketsVisible(boolean yes) {
        if (showBrackets == yes) {
            return;
        }
        showBrackets = yes;
        if (!showBrackets) {
            this.remove(leftBracket);
            this.remove(rightBracket);
        } else {
            this.add(leftBracket, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
            this.add(rightBracket, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
        }
    }
}
