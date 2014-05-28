package edu.umn.ecology.populus.model.aids;

import java.awt.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class AIDSPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 8378090699643554212L;
PopulusParameterField v0PPF = new PopulusParameterField();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField uPPF = new PopulusParameterField();
   JPanel virusesPanel = new JPanel();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   PopulusParameterField vi0PPF = new PopulusParameterField();
   PopulusParameterField riPPF = new PopulusParameterField();
   PopulusParameterField ripPPF = new PopulusParameterField();
   PopulusParameterField QPPF = new PopulusParameterField();
   PopulusParameterField dPPF = new PopulusParameterField();
   PopulusParameterField y0PPF = new PopulusParameterField();
   JPanel cd4Panel = new JPanel();
   PopulusParameterField kPPF = new PopulusParameterField();
   PopulusParameterField kpPPF = new PopulusParameterField();
   PopulusParameterField KPPF = new PopulusParameterField();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   PopulusParameterField siPPF = new PopulusParameterField();
   PopulusParameterField piPPF = new PopulusParameterField();
   PopulusParameterField maxStrainsPPF = new PopulusParameterField();
   TitledBorder cd4Border;
   TitledBorder virusBorder;
   JPanel miscPanel = new JPanel();
   PopulusParameterField runtimePPF = new PopulusParameterField();
   PopulusParameterField dtPPF = new PopulusParameterField();
   GridBagLayout gridBagLayout4 = new GridBagLayout();
   TitledBorder titledBorder1;
   JCheckBox keepVvsTCB = new JCheckBox();
   JPanel plotTypeP = new JPanel();
   TitledBorder titledBorder2;
   JRadioButton ZXvsTRB = new StyledRadioButton();
   JRadioButton VvsTRB = new StyledRadioButton();
   JRadioButton VivsTRB = new StyledRadioButton();
   JRadioButton DvsTRB = new StyledRadioButton();
   JRadioButton RvsTRB = new StyledRadioButton();
   JRadioButton YVvsTRB = new StyledRadioButton();
   ButtonGroup bg = new ButtonGroup();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   JCheckBox keepSeedCB = new JCheckBox();

   long randomSeed = System.currentTimeMillis();
   Vector VvsTSaved = null;

   public BasicPlot getPlotParamInfo() {
      int plotType;
      if(ZXvsTRB.isSelected())
         plotType = AIDSParamInfo.ZXIvsT;
      else if(VvsTRB.isSelected())
         plotType = AIDSParamInfo.VvsT;
      else if(VivsTRB.isSelected())
         plotType = AIDSParamInfo.VIvsT;
      else if(DvsTRB.isSelected())
         plotType = AIDSParamInfo.DvsT;
      else if(RvsTRB.isSelected())
         plotType = AIDSParamInfo.RvsT;
      else
         plotType = AIDSParamInfo.YVvsT;

      if(!keepSeedCB.isSelected())
         randomSeed = System.currentTimeMillis();

      if(keepVvsTCB.isSelected()) {
         if (VvsTSaved == null)
            VvsTSaved = new Vector();
      } else
         VvsTSaved = null;

      return new AIDSParamInfo(v0PPF.getDouble(), vi0PPF.getDouble(), uPPF.getDouble(), riPPF.getDouble(),
             ripPPF.getDouble(), QPPF.getDouble(), y0PPF.getDouble(), KPPF.getDouble(), dPPF.getDouble(),
             kpPPF.getDouble(), kPPF.getDouble(), siPPF.getDouble(), piPPF.getDouble(), dtPPF.getDouble(),
             runtimePPF.getDouble(), maxStrainsPPF.getInt(), plotType, randomSeed, VvsTSaved);
   }

   public AIDSPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public String getOutputGraphName() {
      return "AIDS: Threshold Model";
   }

   private void jbInit() throws Exception {
      titledBorder2 = new TitledBorder("");
      this.setLayout(gridBagLayout1);
      virusesPanel.setLayout(gridBagLayout2);
      cd4Panel.setLayout(gridBagLayout3);
      miscPanel.setLayout(gridBagLayout4);
      cd4Border = new TitledBorder("");
      virusBorder = new TitledBorder("");
      titledBorder1 = new TitledBorder("");
      piPPF.setCurrentValue(20.0);
      piPPF.setHelpText("Strain-specific virus kill rate");
      piPPF.setIncrementAmount(0.5);
      piPPF.setMaxValue(100.0);
      KPPF.setCurrentValue(100.0);
      KPPF.setHelpText("Recruitment rate from thymus");
      KPPF.setIncrementAmount(0.5);
      KPPF.setMaxValue(100.0);
      dPPF.setCurrentValue(1.0);
      dPPF.setHelpText("Per capita death rate");
      dPPF.setIncrementAmount(0.5);
      dPPF.setMaxValue(100.0);
      kpPPF.setCurrentValue(0.1);
      kpPPF.setHelpText("Rate joining the cross-reactive population");
      kpPPF.setIncrementAmount(0.5);
      kpPPF.setMaxValue(100.0);
      kPPF.setCurrentValue(0.1);
      kPPF.setHelpText("Rate joining a strain specific population");
      kPPF.setIncrementAmount(0.5);
      kPPF.setMaxValue(100.0);
      siPPF.setCurrentValue(9.0);
      siPPF.setHelpText("Cross-reactive virus kill rate");
      siPPF.setIncrementAmount(0.5);
      siPPF.setMaxValue(100.0);
      y0PPF.setCurrentValue(100.0);
      y0PPF.setHelpText("Initial density");
      y0PPF.setIncrementAmount(0.5);
      y0PPF.setMaxValue(100.0);
      v0PPF.setCurrentValue(0.0010);
      v0PPF.setHelpText("Initial density");
      v0PPF.setIncrementAmount(0.5);
      v0PPF.setMaxValue(100.0);
      v0PPF.setToolTipText("");
      vi0PPF.setCurrentValue(0.0010);
      vi0PPF.setHelpText("Start density for new mutant");
      vi0PPF.setIncrementAmount(0.5);
      vi0PPF.setMaxValue(100.0);
      uPPF.setCurrentValue(1.0);
      uPPF.setHelpText("Kill rate of CD4+ cells");
      uPPF.setIncrementAmount(0.5);
      uPPF.setMaxValue(100.0);
      riPPF.setCurrentValue(0.05);
      riPPF.setHelpText("Average replication rate from CD4+ cells");
      riPPF.setIncrementAmount(0.5);
      riPPF.setMaxValue(100.0);
      ripPPF.setCurrentValue(3.0);
      ripPPF.setHelpText("Background replication rate");
      ripPPF.setIncrementAmount(0.5);
      ripPPF.setMaxValue(100.0);
      QPPF.setCurrentValue(4.0);
      QPPF.setHelpText("Probable new strain rate");
      QPPF.setIncrementAmount(0.5);
      QPPF.setMaxValue(100.0);
      cd4Panel.setBorder(cd4Border);
      virusesPanel.setBorder(virusBorder);
      virusBorder.setTitle("Viruses");
      cd4Border.setTitle("CD4+ cells");
      runtimePPF.setCurrentValue(100.0);
      runtimePPF.setIncrementAmount(10.0);
      runtimePPF.setMaxValue(1000.0);
      runtimePPF.setParameterName("Run Time");
      runtimePPF.setHelpText("Maximum number of time units for the model to run");
      dtPPF.setCurrentValue(0.05);
      dtPPF.setIncrementAmount(0.01);
      dtPPF.setMaxValue(20.0);
      dtPPF.setParameterName("Time Step");
      dtPPF.setHelpText("Time step for numerical integration");
      miscPanel.setBorder(titledBorder1);
      keepVvsTCB.setToolTipText("Remember \"v vs t\" data between runs");
      keepVvsTCB.setText("Keep \"v vs t\"");
      plotTypeP.setBorder(titledBorder2);
      plotTypeP.setLayout(gridBagLayout5);
      titledBorder2.setTitle("Plot Type");
      ZXvsTRB.setText("<i>z</i>, <i>x<sub>i</> vs <i>t</i>");
      VvsTRB.setText("<i>v</i> vs <i>t</i>");
      VivsTRB.setText("<i>v<sub>i</> vs <i>t</i>");
      DvsTRB.setText("1/<i>D</i> vs <i>t</i>");
      RvsTRB.setText("<i>r</i> vs <i>t</i>");
      YVvsTRB.setText("<i>y</i>, <i>v</i> vs <i>t</i>");
      keepSeedCB.setText("Keep Random Seed");
      keepSeedCB.setToolTipText("Use the same seed for the random number generator");
      titledBorder1.setTitle("Model");
      maxStrainsPPF.setCurrentValue(50.0);
      maxStrainsPPF.setDefaultValue(50.0);
      maxStrainsPPF.setMaxValue(200.0);
      maxStrainsPPF.setParameterName("Max Strains");
      maxStrainsPPF.setHelpText("The maximum number of mutant strains allowed to be created");
      maxStrainsPPF.setIncrementAmount(5.0);
      maxStrainsPPF.setIntegersOnly(true);
      virusesPanel.add(v0PPF,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(vi0PPF,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(uPPF,  new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(riPPF,  new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(ripPPF,  new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(QPPF,  new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      virusesPanel.add(maxStrainsPPF,  new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      this.setBackground(Color.lightGray);
      v0PPF.setParameterName("<i>v<sub>0</>");
      vi0PPF.setParameterName("<i>v<sub>i0</>");
      uPPF.setParameterName("<i>u</i>");
      riPPF.setParameterName("\\<<i>r<sub>i</sub></i>\\>");
      ripPPF.setParameterName("<i>r<sub>i</>\'/<i>r<sub>i</>");
      QPPF.setParameterName("<i>Q</i>");
      dPPF.setParameterName("<i>d</i>");
      y0PPF.setParameterName("<i>y<sub>0</>");
      kPPF.setParameterName("<i>k</i>");
      kpPPF.setParameterName("<i>k</i>\'");
      KPPF.setParameterName("<i>K</i>");
      siPPF.setParameterName("<i>s<sub>i</>/<i>r<sub>i</>");
      piPPF.setParameterName("<i>p<sub>i</>/<i>r<sub>i</>");
      this.add(virusesPanel,       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      this.add(cd4Panel,          new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      cd4Panel.add(piPPF,                          new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(siPPF,                         new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(kPPF,                         new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(kpPPF,                         new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(dPPF,                           new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(KPPF,                          new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      cd4Panel.add(y0PPF,                        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      this.add(miscPanel,        new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      miscPanel.add(dtPPF,         new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 0, 0));
      miscPanel.add(runtimePPF,           new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 2, 0));
      miscPanel.add(keepVvsTCB,    new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      miscPanel.add(keepSeedCB, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(plotTypeP,        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      bg.add(DvsTRB);
      bg.add(VivsTRB);
      bg.add(ZXvsTRB);
      bg.add(VvsTRB);
      bg.add(YVvsTRB);
      bg.add(RvsTRB);
      YVvsTRB.setSelected(true);
      plotTypeP.add(VvsTRB,      new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(ZXvsTRB,     new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(VivsTRB,     new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(DvsTRB,      new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
      plotTypeP.add(RvsTRB,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
      plotTypeP.add(YVvsTRB,      new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
      this.registerChildren( this );
   }
}
