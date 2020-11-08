/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.util.StringTokenizer;

public class EquationPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = -5235310299666499109L;
    final JPanel labelHolder = new JPanel();
    final JScrollPane scroller = new JScrollPane();
    final JLabel useL = new JLabel();
    final JLabel numEqL = new JLabel();
    final GridBagLayout gridBagLayout1 = new GridBagLayout();
    int numEqs;
    final SimpleVFlowLayout simpleVFlowLayout2 = new SimpleVFlowLayout();
    final JTextField numEQTF = new JTextField();
    final JLabel plotL = new JLabel();
    final JPanel eqHolder = new JPanel();
    boolean isDiscrete = false;
    String[] paramNames;
    private final JButton renameB = new JButton();
    private final JSeparator separator = new JSeparator();
    private final JSeparator separator_1 = new JSeparator();
    private final JSeparator separator_2 = new JSeparator();

    public EquationPanel(int num, boolean isdis) {
        this();
        this.isDiscrete = isdis;

        //dos defaults
        //*
        eqHolder.add(new Equation(false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N1(r1-a11*N1-a12*N2-c1*N3)"), null);
        eqHolder.add(new Equation(false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N2(r2-a21*N1-a22*N2-c2*N3)"), null);
        eqHolder.add(new Equation(false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N3(b(c1*N1+c2*N2)-d)"), null);
		/*/
      //cpp defaults
      eqHolder.add(new Equation(false,false,isDiscrete,eqHolder.getComponentCount()+1,20,"r1*N1(1-N1/K)-C*N1*N2"),null);
      eqHolder.add(new Equation(false,false,isDiscrete,eqHolder.getComponentCount()+1,20,"-d2*N2+g*C*N1*N2"),null);
      //*/
        numEqs = eqHolder.getComponentCount();
        numEQTF.setText("" + numEqs);

        paramNames = new String[numEqs];
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = "N" + (i + 1);
    }

    public boolean[] getPlotted() {
        boolean[] b = new boolean[numEqs];
        for (int i = 0; i < numEqs; i++) {
            b[i] = ((Equation) eqHolder.getComponent(i)).isPlotted();
        }
        return b;
    }

    public boolean[] getUsed() {
        boolean[] b = new boolean[numEqs];
        for (int i = 0; i < numEqs; i++) {
            b[i] = ((Equation) eqHolder.getComponent(i)).isUsed();
        }
        return b;
    }

    /*
   the inStandard boolean queries whether the equations are requested in terms of
   N1, N2, N3 etc or in terms of user defined variables
     */
    public String[] getStrings(boolean inStandard) {
        String[] eqsStrings = new String[numEqs];
        for (int i = 0; i < numEqs; i++) {
            eqsStrings[i] = ((Equation) eqHolder.getComponent(i)).getEquation();
        }
        if (inStandard) {
            return mapEquations(eqsStrings, getParamNames(false), getParamNames(true));
        } else {
            return eqsStrings;
        }
    }

    /*
   whether we want the names to be the variables N1,N2,N3 etc, or we want them with the names
   user defined
     */
    public String[] getParamNames(boolean inStandard) {
        String[] paramNames = new String[numEqs];
        if (inStandard) {
            for (int i = 0; i < numEqs; i++) {
                paramNames[i] = "N" + (i + 1);
            }
            return paramNames;
        } else {
            for (int i = 0; i < numEqs; i++) {
                paramNames[i] = ((Equation) eqHolder.getComponent(i)).getParameterName();
            }
            return paramNames;
        }
    }

    public EquationPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double[] getInitialValues() {
        double[] iv = new double[numEqs];
        for (int i = 0; i < numEqs; i++) {
            iv[i] = ((Equation) eqHolder.getComponent(i)).getInitialValue();
        }
        return iv;
    }

    void jsb_adjustmentValueChanged(AdjustmentEvent e) {
        scroller.revalidate();
        scroller.repaint();
    }

    void setType(boolean isDiscrete) {
        this.isDiscrete = isDiscrete;
        for (int i = 0; i < numEqs; i++) {
            ((Equation) eqHolder.getComponent(i)).setType(isDiscrete);
        }
    }

    void numEQTF_actionPerformed(ActionEvent e) {
        int newNumEQ = 0;
        try {
            newNumEQ = Integer.parseInt(numEQTF.getText());
        } catch (NumberFormatException nfe) {
            return;
        }
        setUpEquations(newNumEQ);
    }

    void setUpEquations(int newNum) {
        int i = 0;
        this.numEqs = newNum;
        while (numEqs < eqHolder.getComponentCount()) {
            eqHolder.remove(eqHolder.getComponentCount() - 1);
        }
        while (numEqs > eqHolder.getComponentCount()) {
            i = eqHolder.getComponentCount() + 1;
            eqHolder.add(new Equation(false, false, isDiscrete, i, 10, "N" + i), null);
        }
        eqHolder.revalidate();
        eqHolder.repaint();
    }

    private void jbInit() throws Exception {
        this.setLayout(gridBagLayout1);
        JScrollBar jsb = scroller.getVerticalScrollBar();
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setBorder(BorderFactory.createLineBorder(Color.black));
        eqHolder.setLayout(simpleVFlowLayout2);
        jsb.addAdjustmentListener(this::jsb_adjustmentValueChanged);
        jsb.setUnitIncrement(10);
        this.add(labelHolder, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 0), 0, 0));
        GridBagLayout gbl_labelHolder = new GridBagLayout();
        gbl_labelHolder.rowHeights = new int[]{23, 0};
        gbl_labelHolder.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        gbl_labelHolder.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        labelHolder.setLayout(gbl_labelHolder);
        useL.setText("Use");
        GridBagConstraints gbc_useL = new GridBagConstraints();
        gbc_useL.insets = new Insets(5, 5, 0, 5);
        gbc_useL.fill = GridBagConstraints.HORIZONTAL;
        gbc_useL.anchor = GridBagConstraints.NORTHWEST;
        gbc_useL.gridx = 0;
        gbc_useL.gridy = 0;
        labelHolder.add(useL, gbc_useL);

        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.insets = new Insets(0, 0, 0, 5);
        gbc_separator.gridx = 1;
        gbc_separator.gridy = 0;
        labelHolder.add(separator, gbc_separator);
        plotL.setText("Plot");
        GridBagConstraints gbc_plotL = new GridBagConstraints();
        gbc_plotL.insets = new Insets(5, 5, 0, 5);
        gbc_plotL.fill = GridBagConstraints.HORIZONTAL;
        gbc_plotL.anchor = GridBagConstraints.NORTHWEST;
        gbc_plotL.gridx = 2;
        gbc_plotL.gridy = 0;
        labelHolder.add(plotL, gbc_plotL);

        GridBagConstraints gbc_separator_1 = new GridBagConstraints();
        gbc_separator_1.insets = new Insets(0, 0, 0, 5);
        gbc_separator_1.gridx = 3;
        gbc_separator_1.gridy = 0;
        labelHolder.add(separator_1, gbc_separator_1);
        numEqL.setText("Number of Equations:");
        GridBagConstraints gbc_numEqL = new GridBagConstraints();
        gbc_numEqL.insets = new Insets(5, 5, 0, 5);
        gbc_numEqL.fill = GridBagConstraints.HORIZONTAL;
        gbc_numEqL.anchor = GridBagConstraints.NORTHWEST;
        gbc_numEqL.gridx = 4;
        gbc_numEqL.gridy = 0;
        labelHolder.add(numEqL, gbc_numEqL);
        renameB.setText("Rename");
        renameB.addActionListener(this::renameB_actionPerformed);
        numEQTF.setMaximumSize(new Dimension(30, 30));
        numEQTF.setPreferredSize(new Dimension(25, 21));
        numEQTF.setToolTipText("Enter the number of equations you want in the table.");
        numEQTF.setHorizontalAlignment(SwingConstants.RIGHT);
        numEQTF.addActionListener(this::numEQTF_actionPerformed);
        GridBagConstraints gbc_numEQTF = new GridBagConstraints();
        gbc_numEQTF.insets = new Insets(5, 5, 0, 5);
        gbc_numEQTF.fill = GridBagConstraints.HORIZONTAL;
        gbc_numEQTF.anchor = GridBagConstraints.NORTHWEST;
        gbc_numEQTF.gridx = 5;
        gbc_numEQTF.gridy = 0;
        labelHolder.add(numEQTF, gbc_numEQTF);

        GridBagConstraints gbc_separator_2 = new GridBagConstraints();
        gbc_separator_2.insets = new Insets(0, 0, 0, 5);
        gbc_separator_2.gridx = 6;
        gbc_separator_2.gridy = 0;
        labelHolder.add(separator_2, gbc_separator_2);
        GridBagConstraints gbc_renameB = new GridBagConstraints();
        gbc_renameB.insets = new Insets(5, 5, 5, 5);
        gbc_renameB.fill = GridBagConstraints.HORIZONTAL;
        gbc_renameB.anchor = GridBagConstraints.NORTHWEST;
        gbc_renameB.gridx = 7;
        gbc_renameB.gridy = 0;
        labelHolder.add(renameB, gbc_renameB);
        this.add(scroller, new GridBagConstraints(0, 1, 2, 1, 1.0, 5.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        scroller.setViewportView(eqHolder);

        //jsb.setUnitIncrement(36);
    }

    void renameB_actionPerformed(ActionEvent e) {
        String[] paramNames = getParamNames(false);
        RenamePanel RP = new RenamePanel(paramNames);
        String[] ss = RP.getMappings();
        if (ss == null) return;
        String[] ts = mapEquations(getStrings(false), paramNames, ss);

        for (int i = 0; i < eqHolder.getComponentCount(); i++) {
            ((Equation) eqHolder.getComponent(i)).setParameterName(ss[i]);
            ((Equation) eqHolder.getComponent(i)).setEquation(ts[i]);
        }
    }

    String[] mapEquations(String[] eqs, String[] from, String[] to) {
        int length = Math.min(from.length, to.length);
        String[] toReturn = new String[eqs.length];
        StringTokenizer[] newEqs = new StringTokenizer[eqs.length];
        for (int i = 0; i < newEqs.length; i++) {
            newEqs[i] = new StringTokenizer(eqs[i], " +-*/%^!()[]{}", true);
            toReturn[i] = "";
        }

        for (int i = 0; i < eqs.length; i++) {
            while (newEqs[i].hasMoreTokens()) {
                String token = newEqs[i].nextToken();
                for (int j = 0; j < length; j++)
                    if (token.equalsIgnoreCase(from[j]))
                        token = to[j];
                toReturn[i] += token;
            }
        }
        return toReturn;
    }
}
