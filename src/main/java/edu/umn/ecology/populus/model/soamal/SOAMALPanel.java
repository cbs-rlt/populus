/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.soamal;

import edu.umn.ecology.populus.core.DesktopWindow;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.HTMLLabel;
import edu.umn.ecology.populus.visual.SpecialLineBorder;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class SOAMALPanel extends BasicPlotInputPanel {
    /**
     *
     */
    private static final long serialVersionUID = 8894866035921281037L;
    private final GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final JScrollPane dataTableSP = new JScrollPane();
    private JTable table;
    private final SOAMALTable st = new SOAMALTable();
    private final JPanel plotTypeP = new JPanel();
    private final JRadioButton deFinettiRB = new JRadioButton();
    private final JRadioButton PvsPRB = new StyledRadioButton();
    private final JRadioButton PvsTRB = new StyledRadioButton();
    private final GridBagLayout gridBagLayout2 = new GridBagLayout();
    private TitledBorder titledBorder1;
    private final ButtonGroup bg = new ButtonGroup();
    private final JPanel miscP = new JPanel();
    private final PopulusParameterField gensPPF = new PopulusParameterField();
    private final GridBagLayout gridBagLayout3 = new GridBagLayout();
    private final JButton addDimB = new JButton();
    //private JLabel tableLabel = new JLabel();
    private final HTMLLabel tableLabel = new HTMLLabel("Genotypic Fitness Matrix ( w<sub>xx</> )");

    @Override
    public BasicPlot getPlotParamInfo() {
        int plotType;

        if (table.getCellEditor() != null && !table.getCellEditor().stopCellEditing()) {
            return null;
        }

        double[][] data = st.getMatrix();
        double[] ifreq = st.getInitialFrequencies();
        int[] p = st.getPlotted();

        if (PvsTRB.isSelected())
            plotType = SOAMALParamInfo.PvsT;
        else if (PvsPRB.isSelected())
            plotType = SOAMALParamInfo.PvsP;
        else
            plotType = SOAMALParamInfo.DeFi;

        if (p.length > 3 && (plotType == SOAMALParamInfo.PvsP || plotType == SOAMALParamInfo.DeFi)) {
            JOptionPane.showMessageDialog(DesktopWindow.defaultWindow, "Too many graphs to plot", "Message", JOptionPane.PLAIN_MESSAGE);
            return null;
        }

        return new SOAMALParamInfo(data, ifreq, p, gensPPF.getInt(), st.getUsed(), plotType);
    }

    public SOAMALPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {//this is needed because w/o the title doesn't fit
        table = new JTable(st);
        table.moveColumn(0, 3);
        table.setDefaultRenderer(Integer.class, new ShadedTableCellRenderer());
        table.setDefaultRenderer(Double.class, new ShadedTableCellRenderer());
        table.setDefaultRenderer(Boolean.class, new ShadedBooleanRenderer());
        ((DefaultTableCellRenderer) table.getDefaultRenderer(Integer.class)).setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);


        /*figure out how to italisize some of the headers and also disallow column moving
         */
        titledBorder1 = new TitledBorder("");
        deFinettiRB.setText("De Finetti");
        PvsPRB.setText("<i>p</i> vs <i>p</i>");
        PvsTRB.setSelected(true);
        PvsTRB.setText("<i>p</i> vs <i>t</i>");
        plotTypeP.setLayout(gridBagLayout2);
        dataTableSP.setPreferredSize(new Dimension(100, 121));
        table.setSelectionBackground(Color.white);
        gensPPF.setParameterName("Generations");
        gensPPF.setHelpText("Number of generations to be simulated");
        gensPPF.setMinValue(1.0);
        gensPPF.setMaxValue(1000.0);
        gensPPF.setIntegersOnly(true);
        gensPPF.setIncrementAmount(10.0);
        gensPPF.setDefaultValue(100.0);
        gensPPF.setCurrentValue(100.0);
        miscP.setLayout(gridBagLayout3);
        addDimB.setText("Add Allele");
        addDimB.addActionListener(e -> addDimB_actionPerformed(e));
        bg.add(PvsTRB);
        bg.add(PvsPRB);
        bg.add(deFinettiRB);
        plotTypeP.setBorder(titledBorder1);
        titledBorder1.setTitle("Plot Type");
        this.setLayout(gridBagLayout1);
        this.add(dataTableSP, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        dataTableSP.getViewport().add(table, null);
        this.add(plotTypeP, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        plotTypeP.add(PvsTRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        plotTypeP.add(PvsPRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        plotTypeP.add(deFinettiRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
        this.add(miscP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        miscP.add(gensPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        miscP.add(addDimB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
        miscP.add(tableLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 5), 0, 0));
        this.registerChildren(this);
    }

    static class ShadedTableCellRenderer extends DefaultTableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = -4705486194689980035L;
        Color unselectedBackground;

        ShadedTableCellRenderer() {
            super();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {

            //the color for the allelic row index
            if (column == 3) {
                unselectedBackground = SystemColor.control;
            } else if (!table.isCellEditable(row, column))
                unselectedBackground = Color.lightGray;
            else {
                if (column < 4) {
                    unselectedBackground = Color.white;
                } else {
                    //color for the available matrix entries
                    unselectedBackground = Color.yellow;
                    table.setSelectionForeground(Color.black);
                }
            }
            super.setForeground(table.getSelectionForeground());
            super.setBackground(unselectedBackground);

            setFont(table.getFont());

            if (hasFocus) {
                setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                if (table.isCellEditable(row, column)) {
                    super.setForeground(UIManager.getColor("Table.focusCellForeground"));
                    super.setBackground(UIManager.getColor("Table.focusCellBackground"));
                }
            } else {
                setBorder(noFocusBorder);
            }

            /*this is supposed to be the column right next to the matrix*/
            //if(column == 3) setBorder(new SpecialLineBorder(Color.black,false,false,false,true,3));
            if (column == 3) setBorder(new SpecialLineBorder(Color.black, false, false, true, false, 3));
            setHorizontalAlignment(SwingConstants.CENTER);

            setValue(value);

            // ---- begin optimization to avoid painting background ----
            Color back = getBackground();
            boolean colorMatch = (back != null) && (back.equals(table.getBackground())) && table.isOpaque();
            setOpaque(!colorMatch);
            // ---- end optimization to aviod painting background ----

            return this;
        }
    }

    static class ShadedBooleanRenderer extends JCheckBox implements TableCellRenderer {
        /**
         *
         */
        private static final long serialVersionUID = 7466831463565916643L;
        Color unselectedBackground = Color.lightGray;

        public ShadedBooleanRenderer() {
            super();
            setHorizontalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (!table.isCellEditable(row, column)) {
                unselectedBackground = Color.lightGray;
            } else {
                unselectedBackground = Color.white;
            }
            setForeground(table.getForeground());
            setBackground(unselectedBackground);
            setSelected((value != null && (Boolean) value));
            return this;
        }
    }

    void addDimB_actionPerformed(ActionEvent e) {
        double[] mat = new double[st.getDataDimension() + 1];
        Arrays.fill(mat, 1.0d);
        st.addDimension(false, false, 0.0d, mat);
        table.moveColumn(0, 3);
    }
}