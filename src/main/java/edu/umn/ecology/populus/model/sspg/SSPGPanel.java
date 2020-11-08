/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.sspg;

import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.plot.BasicPlot;
import edu.umn.ecology.populus.plot.BasicPlotInputPanel;
import edu.umn.ecology.populus.visual.StyledRadioButton;
import edu.umn.ecology.populus.visual.matrixtable.MatrixTableModel;
import edu.umn.ecology.populus.visual.matrixtable.MatrixTableRenderer;
import edu.umn.ecology.populus.visual.ppfield.ParameterFieldEvent;
import edu.umn.ecology.populus.visual.ppfield.PopulusParameterField;
import edu.umn.ecology.populus.visual.stagegraph.StageStructuredPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.Externalizable;

/**
 * Stage-Structured Population Growth
 */


public class SSPGPanel extends BasicPlotInputPanel implements Externalizable {
    public static final int kLambda = 0;
    public static final int kSNX = 1;
    public static final int kNXSNXT = 2;
    public static final int kNXSNXX = 3;
    public static final int kXSNXT = 4;
    public static final int kXNXT = 5;
    public static final int kTABOUT = 6;
    public static final int kEIGEN = 7;

    //Pure GUI
    private final transient JScrollPane projectionScroller = new JScrollPane();
    private final transient JScrollBar jsbh = new JScrollBar();
    private final transient JScrollBar jsbv = new JScrollBar();
    private transient Component comp;
    private final transient String defaultTitle = "";
    private final transient GridBagLayout gridBagLayout1 = new GridBagLayout();
    private final transient JTabbedPane inputPane = new JTabbedPane();
    private final transient JPanel graphTypePanel = new JPanel();
    private final transient JPanel parametersPanel = new JPanel();
    private transient TitledBorder titledBorder1;
    private transient TitledBorder titledBorder2;
    private final transient GridBagLayout gridBagLayout2 = new GridBagLayout();
    private final transient GridBagLayout gridBagLayout3 = new GridBagLayout();

    //Radio Buttons - need to remember plot type.
    private final StyledRadioButton lambdavstRB = new StyledRadioButton();
    private final StyledRadioButton snxRB = new StyledRadioButton();
    private final StyledRadioButton nxsnxvstRB = new StyledRadioButton();
    private final StyledRadioButton nxsnxvsxRB = new StyledRadioButton();
    private final StyledRadioButton xvsnxsnxtRB = new StyledRadioButton();
    private final StyledRadioButton xvsnxtRB = new StyledRadioButton();
    private final StyledRadioButton taboutRB = new StyledRadioButton();
    private final StyledRadioButton eigenRB = new StyledRadioButton();
    private final ButtonGroup bg = new ButtonGroup();

    //These parameters need to be saved.  Oh yes they do.
    private final PopulusParameterField numStagesPPF = new PopulusParameterField();
    private final PopulusParameterField intervalsPPF = new PopulusParameterField();
    private final PopulusParameterField whichStagePPF = new PopulusParameterField();
    private transient StageStructuredPane sp = new StageStructuredPane(StageStructuredPane.kSSPG);
    private MatrixTableModel tableModel = new MatrixTableModel(false);
    private final JTable projectionTable = new JTable() {
        /**
         *
         */
        private static final long serialVersionUID = -405372220740247365L;

        // Override this method so that it returns the preferred
        // size of the JTable instead of the default fixed size
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }
    };

	/*
   String defaultTitle = "Stage #%n";
      double[][] initialMatrix = new double[][] {
         {0,     0,    0,     0,     0,     322.3 },
         {0.966, 0,    0,     0,     0,     0     },
         {0.013, 0.01, 0.125, 0,     0,     3.448 },
         {0.007, 0,    0.125, 0.238, 0,     30.17 },
         {0.008, 0,    0,     0.245, 0.167, 0.862 },
         {0,     0,    0,     0.023, 0.75,  0     }};
	 */

    final double[][] initialMatrix = new double[][]{
            {0, 0, 0, 0, 9},
            {0.75, 0, 0, 0, 0},
            {0.75, 0, 0, 3, 0},
            {0, 0.75, 0.75, 0, 0},
            {0, 0, 0, 0.75, 0}};

    final double[] initialPops = new double[]{
            500, 0, 0, 0, 0
    };

    final String[] initialTitles = new String[]{
            defaultTitle, defaultTitle, defaultTitle, defaultTitle, defaultTitle
    };

    public SSPGPanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sp.startTimer();
    }

    @Override
    public BasicPlot getPlotParamInfo() {
        int type;
        if (projectionTable.getCellEditor() != null && !projectionTable.getCellEditor().stopCellEditing()) {
            return null;
        }

        type = getPlotType();

        updateOtherInputs(comp);

        return new SSPGParamInfo(tableModel.getMatrix(), tableModel.getPopulations(), type, intervalsPPF.getInt(), whichStagePPF.getInt());
    }

    private int getPlotType() {
        int type;

        if (lambdavstRB.isSelected()) {
            type = SSPGPanel.kLambda;
        } else if (snxRB.isSelected()) {
            type = SSPGPanel.kSNX;
        } else if (nxsnxvstRB.isSelected()) {
            type = SSPGPanel.kNXSNXT;
        } else if (nxsnxvsxRB.isSelected()) {
            type = SSPGPanel.kNXSNXX;
        } else if (xvsnxsnxtRB.isSelected()) {
            type = SSPGPanel.kXSNXT;
        } else if (xvsnxtRB.isSelected()) {
            type = SSPGPanel.kXNXT;
        } else if (eigenRB.isSelected()) {
            type = SSPGPanel.kEIGEN;
        } else {
            type = SSPGPanel.kTABOUT;
        }

        return type;
    }

		private void setPlotType(int type) {
			switch (type) {
			case kLambda:
				this.lambdavstRB.setSelected(true);
				break;
			case kSNX:
				this.snxRB.setSelected(true);
				break;
			case kNXSNXT:
				this.nxsnxvstRB.setSelected(true);
				break;
			case kNXSNXX:
				this.nxsnxvsxRB.setSelected(true);
				break;
			case kXSNXT:
				this.xvsnxsnxtRB.setSelected(true);
				break;
			case kXNXT:
				this.xvsnxtRB.setSelected(true);
				break;
			case kEIGEN:
				this.eigenRB.setSelected(true);
				break;
			case kTABOUT:
			default:
				this.taboutRB.setSelected(true);
				break;
			}
		}

    @Override
    public String getOutputGraphName() {
        return "Stage-Structured Output";
    }

    private void jbInit() throws Exception {
        projectionTable.setModel(tableModel);
        titledBorder1 = new TitledBorder("Graph Type");
        titledBorder2 = new TitledBorder("Parameters");
        this.setLayout(gridBagLayout1);
        graphTypePanel.setBorder(titledBorder1);
        graphTypePanel.setLayout(gridBagLayout2);
        parametersPanel.setBorder(titledBorder2);
        parametersPanel.setLayout(gridBagLayout3);
        lambdavstRB.setText("\u03bb vs <i>t</i>");
        snxRB.setText("\u03a3<i>Nx</i> vs <i>t</i>");
        nxsnxvstRB.setText("<i>Nx/</i>\u03a3<i>Nx</i> vs <i>t</i>");
        nxsnxvstRB.addChangeListener(this::nxsnxvstRB_stateChanged);
        nxsnxvsxRB.setText("<i>Nx/</i>\u03a3<i>Nx</i> vs <i>x</i>");
        xvsnxsnxtRB.setText("<i>x</i> vs <i>Nx/</i>\u03a3<i>Nx</i>, <i>t</i>");
        xvsnxtRB.setText("<i>x</i> vs <i>Nx</i>, <i>t</i>");
        taboutRB.setText("Tabular Output");
        numStagesPPF.setCurrentValue(sp.getPopulations().length);
        numStagesPPF.setMaxValue(20.0);
        numStagesPPF.setMinValue(1.0);
        numStagesPPF.addParameterFieldListener(this::numStagesPPF_parameterFieldChanged);
        intervalsPPF.setCurrentValue(6.0);
        intervalsPPF.setMaxValue(1000.0);
        intervalsPPF.setMinValue(1.0);
        whichStagePPF.setEnabled(false);
        whichStagePPF.setMaxValue(sp.getPopulations().length);
        whichStagePPF.setMinValue(1.0);
        inputPane.addChangeListener(this::inputPane_stateChanged);
        eigenRB.setText("Eigen System");
        bg.add(lambdavstRB);
        bg.add(snxRB);
        bg.add(nxsnxvstRB);
        bg.add(nxsnxvsxRB);
        bg.add(xvsnxsnxtRB);
        bg.add(xvsnxtRB);
        bg.add(taboutRB);
        bg.add(eigenRB);
        lambdavstRB.setSelected(true);
        numStagesPPF.setParameterName("Number of Stages");
        intervalsPPF.setParameterName("Number of Time Intervals");
        whichStagePPF.setParameterName(" Which stage to view");


        tableModel.setTable(projectionTable);
        sp.numStagesListener(numStagesPPF, whichStagePPF);
        sp.setData(initialMatrix, initialPops, initialTitles, 0, true);

        projectionTable.setColumnSelectionAllowed(false);
        projectionTable.setRowSelectionAllowed(false);
        ((DefaultTableCellRenderer) projectionTable.getDefaultRenderer(Double.class)).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer) projectionTable.getDefaultRenderer(Integer.class)).setHorizontalAlignment(SwingConstants.CENTER);

        projectionTable.setRowSelectionAllowed(false);
        projectionTable.setTableHeader(null);
        projectionTable.setIntercellSpacing(new Dimension(3, 0));
        projectionTable.setBackground(Color.white);
        projectionScroller.getViewport().add(projectionTable);
        projectionTable.setDefaultRenderer(Double.class, new MatrixTableRenderer()); //Lars !!!!
        inputPane.add("Life-Cycle Graph", sp);
        inputPane.add("Projection Matrix", projectionScroller);

        this.add(graphTypePanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 0, 0));
        graphTypePanel.add(lambdavstRB, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(snxRB, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(nxsnxvstRB, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(nxsnxvsxRB, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(xvsnxsnxtRB, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(xvsnxtRB, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(taboutRB, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        graphTypePanel.add(eigenRB, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
        this.add(inputPane, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.add(parametersPanel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 10, 0), 50, 50));
        parametersPanel.add(numStagesPPF, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        parametersPanel.add(intervalsPPF, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        parametersPanel.add(whichStagePPF, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        projectionTable.setShowGrid(false);
        projectionScroller.getViewport().setLayout(gbl);
        projectionScroller.getViewport().setBackground(Color.white);

        jsbh.setOrientation(Adjustable.HORIZONTAL);
        projectionScroller.setHorizontalScrollBar(jsbh);
        projectionScroller.setVerticalScrollBar(jsbv);
        projectionScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        projectionScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        projectionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        projectionTable.setPreferredScrollableViewportSize(inputPane.getSize());

        projectionScroller.getViewport().add(projectionTable, gbc);
        registerChildren(this);
    }

    void numStagesPPF_parameterFieldChanged(ParameterFieldEvent e) {
        int dim = numStagesPPF.getInt();
        tableModel.setNumRows(dim);

        whichStagePPF.setMaxValue(dim);
        projectionScroller.getViewport().setExtentSize(new Dimension(1000, 1000));
        for (int i = 0; i < projectionTable.getColumnCount(); i++) {
            projectionTable.getColumnModel().getColumn(i).setMinWidth(40);
            projectionTable.getColumnModel().getColumn(i).setPreferredWidth(50);
        }

			/* this still doesn't handle the size of the matrix perfectly... it seems that when the matrix
      is around 20, it starts to become larger than the viewable area... i'm not sure what's wrong,
      but it's probably not too important*/
        projectionScroller.getViewport().setMinimumSize(new Dimension(1000, 1000));
    }

    void updateOtherInputs(Component newData) {
        if (newData instanceof JScrollPane) {
            //moved from table
            sp.setData(tableModel.getMatrix(), tableModel.getPopulations(), null, 0, true);
        } else {
            //moved from graph
            tableModel.setData(sp.getMatrix(), sp.getPopulations(), 0, true);
            int numStages = sp.getPopulations().length;
            numStagesPPF.setCurrentValue(numStages);
        }
        projectionScroller.getViewport().setExtentSize(new Dimension(1000, 1000));
        for (int i = 0; i < projectionTable.getColumnCount(); i++) {
            projectionTable.getColumnModel().getColumn(i).setMinWidth(40);
            projectionTable.getColumnModel().getColumn(i).setPreferredWidth(50);
        }
    }

    void inputPane_stateChanged(ChangeEvent e) {
        if (inputPane.getSelectedComponent() instanceof JScrollPane) {
            numStagesPPF.setEnabled(true);
        } else {
            numStagesPPF.setEnabled(false);
        }
        if (comp == null) {
            comp = inputPane.getSelectedComponent();
            return;
        }
        if (comp.equals(inputPane.getSelectedComponent())) return;
        updateOtherInputs(comp);
        comp = inputPane.getSelectedComponent();
    }

    void nxsnxvstRB_stateChanged(ChangeEvent e) {
        whichStagePPF.setEnabled(nxsnxvstRB.isSelected());
    }


    /**
     * We will save this panel specially, because I'm getting errors if
     * I do not.
     * Also, we need to start the timer anyway!
     *
     * @param out
     * @throws java.io.IOException
     */
    @Override
    public void writeExternal(java.io.ObjectOutput out)
            throws java.io.IOException {
        out.writeObject(tableModel);
        out.writeDouble(numStagesPPF.getCurrentValue());
        out.writeDouble(intervalsPPF.getCurrentValue());
        out.writeDouble(whichStagePPF.getCurrentValue());
        out.writeObject(sp.getMatrix());
        out.writeObject(sp.getPopulations());
        out.writeObject(sp.getTitles());
        out.writeInt(this.getPlotType());
    }

    @Override
    public void readExternal(java.io.ObjectInput in) {
        try {
            tableModel = (MatrixTableModel) in.readObject();
            numStagesPPF.setCurrentValue(in.readDouble());
            intervalsPPF.setCurrentValue(in.readDouble());
            whichStagePPF.setCurrentValue(in.readDouble());
            sp = new StageStructuredPane(StageStructuredPane.kSSPG);
            sp.setData((double[][]) in.readObject(), (double[]) in.readObject(), (String[]) in.readObject(), 0, true);
            this.setPlotType(in.readInt());
        } catch (Exception e) {
            Logging.log(e);
        }
        sp.startTimer();
    }


}


