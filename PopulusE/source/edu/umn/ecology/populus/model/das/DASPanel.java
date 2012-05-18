package edu.umn.ecology.populus.model.das;

import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.visual.*;
import javax.swing.*;
import edu.umn.ecology.populus.constants.ColorScheme;

public class DASPanel extends BasicPlotInputPanel {
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField populationPPF = new PopulusParameterField();
   PopulusParameterField frequencyPPF = new PopulusParameterField();
   PopulusParameterField gensPPF = new PopulusParameterField();
   PopulusParameterField wAAPPF = new PopulusParameterField();
   PopulusParameterField wAaPPF = new PopulusParameterField();
   PopulusParameterField waaPPF = new PopulusParameterField();

   public DASPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public void updateLabels() {

   }

   public BasicPlot getPlotParamInfo() {
      return new DASParamInfo(populationPPF.getInt(),frequencyPPF.getDouble(), gensPPF.getInt(),
             wAAPPF.getDouble(),wAaPPF.getDouble(),waaPPF.getDouble());
   }

   private void jbInit() throws Exception {
      this.setLayout( gridBagLayout1 );
      populationPPF.setIncrementAmount(5.0);
      populationPPF.setIntegersOnly(true);
      populationPPF.setMaxValue(500.0);
      populationPPF.setMinValue(1.0);
      populationPPF.setCurrentValue(50.0);
      populationPPF.setDefaultValue(50.0);
      populationPPF.setParameterName("Population Size (<i>N</i>)");
      frequencyPPF.setParameterName("Initial Allelic Frequency (<i>p</i>)");
      frequencyPPF.setMaxValue(1.0);
      frequencyPPF.setIntegersOnly(true);
      frequencyPPF.setCurrentValue(0.5);
      frequencyPPF.setDefaultValue(0.5);
      frequencyPPF.setIncrementAmount(0.1);
      gensPPF.setParameterName("Runtime (generations)");
      gensPPF.setMinValue(1.0);
      gensPPF.setMaxValue(1000.0);
      gensPPF.setDefaultValue(500.0);
      gensPPF.setCurrentValue(500.0);
      gensPPF.setIntegersOnly(true);
      gensPPF.setIncrementAmount(20.0);
      wAAPPF.setIncrementAmount(0.1);
      wAAPPF.setDefaultValue(0.8);
      wAAPPF.setCurrentValue(0.8);
      wAAPPF.setIntegersOnly(true);
      wAAPPF.setMaxValue(1.0);
      wAAPPF.setParameterName("<i>w<sub>AA</sub></i>");
      wAAPPF.setHelpText("Fitness of the \"AA\" genotype");
      wAaPPF.setIncrementAmount(0.1);
      wAaPPF.setDefaultValue(1.0);
      wAaPPF.setCurrentValue(1.0);
      wAaPPF.setIntegersOnly(true);
      wAaPPF.setMaxValue(1.0);
      wAaPPF.setParameterName("<i>w<sub>Aa</sub></i>");
      wAaPPF.setHelpText("Fitness of the \"Aa\" genotype");
      waaPPF.setIncrementAmount(0.1);
      waaPPF.setDefaultValue(0.9);
      waaPPF.setCurrentValue(0.9);
      waaPPF.setIntegersOnly(true);
      waaPPF.setMaxValue(1.0);
      waaPPF.setParameterName("<i>w<sub>aa</sub></i>");
      waaPPF.setHelpText("Fitness of the \"aa\" genotype");
      this.add(frequencyPPF,                new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 10), 0, 0));
      this.add(gensPPF,                 new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 10, 10), 0, 0));
      this.add(populationPPF,           new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 5, 10), 0, 0));
      this.add(wAAPPF,       new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      this.add(wAaPPF,      new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      this.add(waaPPF,     new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
      registerChildren( this );
   }
}
