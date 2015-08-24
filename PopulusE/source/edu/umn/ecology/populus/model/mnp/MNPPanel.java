/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.mnp;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.plot.*;
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class MNPPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -3575124733108402223L;
private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private JScrollPane dataTableSP = new JScrollPane();
   private JTable table;
   private MNPTable st = new MNPTable();
   private JPanel plotTypeP = new JPanel();
   private JRadioButton wbarvspRB = new StyledRadioButton();
   private JRadioButton dPvsPRB = new StyledRadioButton();
   private JRadioButton PvsTRB = new StyledRadioButton();
   private GridBagLayout gridBagLayout2 = new GridBagLayout();
   private TitledBorder titledBorder1;
   private ButtonGroup bg = new ButtonGroup();
   private JPanel miscP = new JPanel();
   private PopulusParameterField gensPPF = new PopulusParameterField();
   private GridBagLayout gridBagLayout3 = new GridBagLayout();
   private JButton addRowB = new JButton();
   private JRadioButton GFvstRB = new StyledRadioButton();
   private JRadioButton sixFreqRB = new JRadioButton();
   private JRadioButton singleFreqRB = new JRadioButton();
   private JButton removeRowB = new JButton();
   private PopulusParameterField freqPPF = new PopulusParameterField();
   private JRadioButton levModelRB = new JRadioButton();
   private JRadioButton dempModelRB = new JRadioButton();
   private ButtonGroup modelTypeBG = new ButtonGroup();
   private ButtonGroup freqTypeBG = new ButtonGroup();

   public BasicPlot getPlotParamInfo() {
      int plotType;
      double[] freqs;

      if(PvsTRB.isSelected())
         plotType = MNPParamInfo.PvsT;
      else if(GFvstRB.isSelected())
         plotType = MNPParamInfo.GFvsT;
      else if(dPvsPRB.isSelected())
         plotType = MNPParamInfo.dPvsP;
      else
         plotType = MNPParamInfo.WBARvsP;

      if(singleFreqRB.isSelected())
         freqs = new double[] {freqPPF.getDouble()};
      else
         freqs = new double[] {0.02, 0.212, 0.404, 0.596, 0.788, 0.98};

      return new MNPParamInfo(st.getMatrix(),freqs,dempModelRB.isSelected(),gensPPF.getInt(),plotType);
   }

   public MNPPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {//this is needed because w/o the title doesn't fit
      table = new JTable(st);
      titledBorder1 = new TitledBorder("");
      wbarvspRB.setText("<i>w\u0305</i> vs <i>p</i>");
      dPvsPRB.setText("<i>\u0394p</i> vs <i>p</i>");
      PvsTRB.setSelected(true);
      PvsTRB.setText("<i>p</i> vs <i>t</i>");
      plotTypeP.setLayout(gridBagLayout2);
      dataTableSP.setPreferredSize(new Dimension(100, 121));
      table.setSelectionBackground(Color.white);
      gensPPF.setParameterName("Generations");
      gensPPF.setMinValue(1.0);
      gensPPF.setMaxValue(1000.0);
      gensPPF.setIntegersOnly(true);
      gensPPF.setIncrementAmount(10.0);
      gensPPF.setDefaultValue(50.0);
      gensPPF.setCurrentValue(50.0);
      miscP.setLayout(gridBagLayout3);
      addRowB.setText("Add Habitat");
      addRowB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            st.addRow(0.0,new double[] {1.0,1.0,1.0});
         }
      });
      GFvstRB.setText("<i>p</i><sup>2</sup>, 2<i>pq</i>, <i>q</i><sup>2</sup> vs <i>t</i>");
      sixFreqRB.setText("Six Frequencies");
      singleFreqRB.setSelected(true);
      singleFreqRB.setText("Single Frequency");
      singleFreqRB.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(ChangeEvent e) {
            freqPPF.setEnabled(singleFreqRB.isSelected());
            GFvstRB.setEnabled(singleFreqRB.isSelected());
            if(GFvstRB.isSelected() && !GFvstRB.isEnabled())
               PvsTRB.setSelected(true);
         }
      });
      removeRowB.setText("Remove Habitat");
      removeRowB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            st.removeRow();
         }
      });

      table.getColumnModel().getColumn(1).setHeaderRenderer(new TableHeaderRenderer());
      table.getColumnModel().getColumn(2).setHeaderRenderer(new TableHeaderRenderer());
      table.getColumnModel().getColumn(3).setHeaderRenderer(new TableHeaderRenderer());
      table.getColumnModel().getColumn(4).setHeaderRenderer(new TableHeaderRenderer());

      freqPPF.setCurrentValue(0.1);
      freqPPF.setDefaultValue(0.1);
      freqPPF.setIncrementAmount(10.0);
      freqPPF.setMaxValue(1.0);
      freqPPF.setParameterName("Initial Frequency");
      levModelRB.setText("Levine");
      dempModelRB.setSelected(true);
      dempModelRB.setText("Dempster");
      bg.add(PvsTRB);
      bg.add(dPvsPRB);
      bg.add(wbarvspRB);
      bg.add(GFvstRB);
      plotTypeP.setBorder(titledBorder1);
      titledBorder1.setTitle("Plot Type");
      this.setLayout(gridBagLayout1);
      this.add(dataTableSP,               new GridBagConstraints(0, 2, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      dataTableSP.getViewport().add(table, null);
      this.add(plotTypeP,        new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(PvsTRB,         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
      plotTypeP.add(dPvsPRB,           new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      plotTypeP.add(wbarvspRB,           new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      this.add(miscP,      new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(GFvstRB,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(dempModelRB,        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(levModelRB,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(gensPPF,     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(removeRowB,    new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      miscP.add(addRowB,   new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      miscP.add(singleFreqRB,   new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(sixFreqRB,   new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      miscP.add(freqPPF,   new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
      this.registerChildren( this );
      modelTypeBG.add(dempModelRB);
      modelTypeBG.add(levModelRB);
      freqTypeBG.add(singleFreqRB);
      freqTypeBG.add(sixFreqRB);
   }

   static class TableHeaderRenderer extends HTMLLabel implements TableCellRenderer {
      /**
	 * 
	 */
	private static final long serialVersionUID = -3007138809051313770L;
	public TableHeaderRenderer() {
         super();
      }
      public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
         setBorder(BorderFactory.createRaisedBevelBorder());
         setText((String)value);
         return this;
      }
   }
}
