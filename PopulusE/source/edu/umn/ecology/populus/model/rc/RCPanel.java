/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.rc;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;

public class RCPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 26613640355885885L;
Border border1;
   TitledBorder titledBorder1;
   Border border8;
   ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
   JPanel plotTypePanel = new JPanel();
   RunningTimePanel runningTimePanel = new RunningTimePanel();
   Border border9;
   GridBagLayout gridBagLayout7= new GridBagLayout();
   Border border6;
   Border border2;
   Border border7;
   TitledBorder titledBorder2;
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   TitledBorder titledBorder6;
   TitledBorder titledBorder7;
   Border border3;
   TitledBorder titledBorder3;
   TitledBorder titledBorder8;
   Border border4;
   TitledBorder titledBorder4;
   TitledBorder titledBorder9;
   Border border10;
   TitledBorder titledBorder10;
   Border border11;
   TitledBorder titledBorder11;
   Border border12;
   TitledBorder titledBorder12;
   Border border5;
   TitledBorder titledBorder5;//JRadioButton();
   private JRadioButton phivsr1Button = new StyledRadioButton();
   private JRadioButton n2vsn1Button = new StyledRadioButton();
   private GridBagLayout gridBagLayout12 = new GridBagLayout();
   private JRadioButton c2r2Button = new JRadioButton();
   private JPanel resourceTypePanel = new JPanel();
   private JRadioButton c2r1Button = new JRadioButton();
   GridBagLayout gridBagLayout13 = new GridBagLayout();
   JRadioButton seasonalModelButton = new JRadioButton();
   JPanel modelTypePanel = new JPanel();
   JRadioButton equableModelButton = new JRadioButton();
   JRadioButton c1r1Button = new JRadioButton();
   private JRadioButton phivsr2Button = new StyledRadioButton();
   private JRadioButton rvstButton = new StyledRadioButton();
   private JRadioButton nvstButton = new StyledRadioButton();
   private JRadioButton nandrvstButton = new StyledRadioButton();
   private PopulusParameterField paramb12 = new PopulusParameterField();
   private PopulusParameterField parame22 = new PopulusParameterField();
   private GridBagLayout gridBagLayout3 = new GridBagLayout();
   private PopulusParameterField paramb11 = new PopulusParameterField();
   private PopulusParameterField parame21 = new PopulusParameterField();
   private PopulusParameterField paramb22 = new PopulusParameterField();
   private PopulusParameterField paramC2 = new PopulusParameterField();
   private PopulusParameterField paramb21 = new PopulusParameterField();
   private PopulusParameterField paramC1 = new PopulusParameterField();
   private PopulusParameterField paramW = new PopulusParameterField();
   private PopulusParameterField paramV = new PopulusParameterField();
   private PopulusParameterField parama12 = new PopulusParameterField();
   private PopulusParameterField parama11 = new PopulusParameterField();
   private PopulusParameterField parame12 = new PopulusParameterField();
   private PopulusParameterField parame11 = new PopulusParameterField();
   private JPanel hostRatesPanel = new JPanel();
   private PopulusParameterField parama22 = new PopulusParameterField();
   private PopulusParameterField paramD = new PopulusParameterField();
   private PopulusParameterField parama21 = new PopulusParameterField();
   private PopulusParameterField paramn2 = new PopulusParameterField();
   private PopulusParameterField paramn1 = new PopulusParameterField();
   private GridBagLayout gridBagLayout2 = new GridBagLayout();
   private JPanel initialDensitiesPanel = new JPanel();

   public BasicPlot getPlotParamInfo() {
      double time = runningTimePanel.getTime();
      int plotType;

      if( nvstButton.isSelected())
         plotType = RCParamInfo.nvst;
      else if (rvstButton.isSelected())
         plotType = RCParamInfo.rvst;
      else if (nandrvstButton.isSelected())
         plotType = RCParamInfo.nandrvst;
      else if (phivsr1Button.isSelected())
         plotType = RCParamInfo.phivsR;
      else if (phivsr2Button.isSelected())
         plotType = RCParamInfo.phivsR2;
      else if (n2vsn1Button.isSelected())
         plotType = RCParamInfo.n2vsn1;
      else {
         //??
         edu.umn.ecology.populus.fileio.Logging.log("Unknown plot");
         plotType = 0;
      }


      //boolean vsTime = true;
      int modelType;
      if (equableModelButton.isSelected()) {
         if (c1r1Button.isSelected()) {
            modelType = RCParamInfo.equable_11;
         }
         else if( c2r1Button.isSelected() )
            modelType = RCParamInfo.equable_21;
         else
            modelType = RCParamInfo.equable_22;
      }
      else if (seasonalModelButton.isSelected()){
         if (c1r1Button.isSelected())
            modelType = RCParamInfo.seasonal_11;
         else if( c2r1Button.isSelected() )
            modelType = RCParamInfo.seasonal_21;
         else
            modelType = RCParamInfo.seasonal_22;
      }
      else {
         System.err.println( "Error in RCPanel: Unknown button selected" );
         modelType = -1;
      }
      return new RCParamInfo(modelType, plotType, time, paramn1.getDouble(), paramn2.getDouble(), paramV.getDouble(),paramW.getDouble(), paramC1.getDouble(), paramC2.getDouble(),parame12.getDouble(),parame11.getDouble(), parame21.getDouble(), parame22.getDouble(),parama11.getDouble(),parama12.getDouble(), parama21.getDouble(), parama22.getDouble(),paramb11.getDouble(),paramb12.getDouble(), paramb21.getDouble(), paramb22.getDouble(), paramD.getDouble(), 0 );
   }

   public RCPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      titledBorder1 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Plot Type");
      titledBorder8 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Consumers and Resources");
      titledBorder9 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Initial Conditions");
      plotTypePanel.setBorder(titledBorder1 );
      plotTypePanel.setLayout( gridBagLayout7 );
      this.setLayout( gridBagLayout1 );
      runningTimePanel.setDefaultTime( 10.0 );

      phivsr1Button.setToolTipText("");
      phivsr1Button.setActionCommand("<i>\u03C6</i> vs <i>r</i><sub>1</sub> ");
      phivsr1Button.setText("<i>\u03C6</i> vs <i>r</i><sub>1</sub> ");

      c2r2Button.setFocusPainted(false);
      c2r2Button.setText("2 consumers/ 2 resources");
      c2r2Button.setEnabled(true);
      c2r2Button.setToolTipText("2 consumers/ 2 resources");
      c2r2Button.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modelChangedActionPerformed(e);
         }
      });
      resourceTypePanel.setBorder(titledBorder8);
      resourceTypePanel.setLayout(gridBagLayout12);
      c2r1Button.setToolTipText("2 consumers/ 1 resource");
      c2r1Button.setText("2 consumers/ 1 resource");
      c2r1Button.setFocusPainted(false);
      c2r1Button.setSelected(true);
      c2r1Button.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modelChangedActionPerformed(e);
         }
      });
      seasonalModelButton.setToolTipText("Seasonal");
      seasonalModelButton.setText("Seasonal");
      seasonalModelButton.setFocusPainted(false);
      seasonalModelButton.setSelected(true);
      seasonalModelButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modelChangedActionPerformed(e);
         }
      });
      modelTypePanel.setLayout(gridBagLayout13);
      modelTypePanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Model Type"));
      equableModelButton.setToolTipText("Equable");
      equableModelButton.setText("Equable");
      equableModelButton.setFocusPainted(false);
      equableModelButton.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modelChangedActionPerformed(e);
         }
      });
      c1r1Button.setFocusPainted(false);
      c1r1Button.setText("1 consumer/ 1 resource");
      c1r1Button.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            modelChangedActionPerformed(e);
         }
      });
      c1r1Button.setToolTipText("1 consumer & 1 resource");
      phivsr2Button.setText("<i>\u03C6</i> vs <i>r</i><sub>2</sub> ");
      phivsr2Button.setActionCommand("<i>\u03C6</i> vs <i>r</i><sub>2</sub> ");
      phivsr2Button.setEnabled(false);
      phivsr2Button.setToolTipText("");
      n2vsn1Button.setText("<i>n<sub>2</sub></i> vs <i>n</i><sub>2</sub> ");
      n2vsn1Button.setEnabled(true);
      rvstButton.setFocusPainted(false);
      rvstButton.setText("<i>r</i> vs <i>t</i>");
      nvstButton.setText("<i>n</i> vs <i>t</i>");
      nandrvstButton.setText("<i>n</i>,<i>r</i> vs <i>t</i>");
      nvstButton.setSelected(true);
      paramb12.setCurrentValue(4.0);
      paramb12.setDefaultValue(4.0);
      paramb12.setEnabled(false);
      paramb12.setHelpText("Half saturation constant of 2nd consumer for this model\'s u");
      paramb12.setIncrementAmount(0.1);
      paramb12.setMaxValue(100.0);
      paramb12.setParameterName("<i>b</i><sub>12</sub>");
      parame22.setMaxValue(100.0);
      parame22.setParameterName("<i>e</i><sub>22</sub>");
      parame22.setDefaultValue(1.0);
      parame22.setEnabled(false);
      parame22.setHelpText("the amount of resource 2 required to make a single new individual " +
                           "of species 2");
      parame22.setCurrentValue(1.0);
      paramb11.setCurrentValue(5.5);
      paramb11.setDefaultValue(5.5);
      paramb11.setHelpText("Half saturation constant of 2nd consumer for this model\'s u");
      paramb11.setIncrementAmount(0.1);
      paramb11.setMaxValue(100.0);
      paramb11.setParameterName("<i>b</i><sub>11</sub>");
      parame21.setMaxValue(100.0);
      parame21.setParameterName("<i>e</i><sub>21</sub>");
      parame21.setVerifyInputWhenFocusTarget(true);
      parame21.setDefaultValue(1.0);
      parame21.setHelpText("the amount of resource 1 required to make a single new individual " +
                           "of species 2");
      parame21.setCurrentValue(1.0);
      paramb22.setCurrentValue(12.0);
      paramb22.setDefaultValue(12.0);
      paramb22.setEnabled(false);
      paramb22.setHelpText("Half saturation constant of 2nd consumer for this model\'s u");
      paramb22.setIncrementAmount(0.1);
      paramb22.setMaxValue(100.0);
      paramb22.setParameterName("<i>b</i><sub>22</sub>");
      paramC2.setParameterName("<i>c</i><sub>2</sub>");
      paramC2.setMaxValue(1000.0);
      paramC2.setCurrentValue(12.0);
      paramC2.setIncrementAmount(2.0);
      paramC2.setDefaultValue(12.0);
      paramC2.setEnabled(false);
      paramC2.setHelpText("Rate of turnover");
      paramb21.setCurrentValue(0.2);
      paramb21.setDefaultValue(0.2);
      paramb21.setHelpText("Half saturation constant of 2nd consumer for this model\'s u");
      paramb21.setIncrementAmount(0.1);
      paramb21.setMaxValue(100.0);
      paramb21.setParameterName("<i>b</i><sub>21</sub>");
      paramC1.setCurrentValue(2.5);
      paramC1.setDefaultValue(2.5);
      paramC1.setHelpText("Concentration of the limiting resource in the inflowing solution");
      paramC1.setIncrementAmount(2.0);
      paramC1.setMaxValue(100.0);
      paramC1.setParameterName("<i>c</i><sub>1</sub>");
      paramW.setHelpText("Flow rate of culture medium through the vessel");
      paramW.setDefaultValue(40.0);
      paramW.setIncrementAmount(5.0);
      paramW.setMinValue(1.0);
      paramW.setCurrentValue(40.0);
      paramW.setMaxValue(1000.0);
      paramW.setParameterName("<i>w</i>");
      paramV.setParameterName("<i>V</i>");
      paramV.setMinValue(0.1);
      paramV.setMaxValue(1000.0);
      paramV.setDefaultValue(2.0);
      paramV.setHelpText("Culture vessel volume");
      paramV.setCurrentValue(2.0);
      parama12.setCurrentValue(28.0);
      parama12.setDefaultValue(28.0);
      parama12.setEnabled(false);
      parama12.setHelpText("Maximal growth rate of consumers on unlimited resources for this " +
    "model uptake function");
      parama12.setMaxValue(1000.0);
      parama12.setParameterName("<i>a</i><sub>12</sub>");
      parama11.setParameterName("<i>a</i><sub>11</sub>");
      parama11.setMaxValue(100.0);
      parama11.setHelpText("Maximal growth rate of consumers on unlimited resources for this " +
                           "model uptake function");
      parama11.setDefaultValue(10.0);
      parama11.setCurrentValue(10.0);
      parame12.setCurrentValue(1.0);
      parame12.setHelpText("the amount of resource 2 required to make a single new individual " +
                           "of species 1");
      parame12.setDefaultValue(1.0);
      parame12.setEnabled(false);
      parame12.setMaxValue(100.0);
      parame12.setParameterName("<i>e</i><sub>12</sub>");
      parame11.setParameterName("<i>e</i><sub>11</sub>");
      parame11.setMaxValue(100.0);
      parame11.setDefaultValue(1.0);
      parame11.setHelpText("the amount of resource 1 required to make a single new individual " +
                           "of species 1");
      parame11.setCurrentValue(1.0);
      hostRatesPanel.setBorder(new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Model Parameters"));
      hostRatesPanel.setLayout(gridBagLayout3);
      parama22.setCurrentValue(20.0);
      parama22.setDefaultValue(20.0);
      parama22.setEnabled(false);
      parama22.setHelpText("Maximal growth rate of consumers on unlimited resources for this " +
    "model uptake function");
      parama22.setMaxValue(100.0);
      parama22.setParameterName("<i>a</i><sub>22</sub>");
      paramD.setParameterName("<i>d</i>");
      paramD.setMaxValue(1.0);
      paramD.setIncrementAmount(0.05);
      paramD.setDefaultValue(0.1);
      paramD.setHelpText("Dilution fraction");
      paramD.setCurrentValue(0.1);
      parama21.setCurrentValue(2.4);
      parama21.setDefaultValue(2.4);
      parama21.setHelpText("Maximal growth rate of 2nd consumer on unlimited resources for this " +
                           "model uptake function");
      parama21.setMaxValue(100.0);
      parama21.setParameterName("<i>a</i><sub>21</sub>");
      paramn2.setParameterName("<i>n</i><sub>2</sub>(0)");
      paramn2.setMaxValue(1000.0);
      paramn2.setDefaultValue(2.0);
      paramn2.setHelpText("Initial concentration of 2nd consumer species");
      paramn2.setCurrentValue(2.0);
      paramn1.setCurrentValue(2.0);
      paramn1.setHelpText("Initial concentration of 1st consumer species");
      paramn1.setDefaultValue(2.0);
      paramn1.setIncrementAmount(5.0);
      paramn1.setMaxValue(1000.0);
      paramn1.setParameterName("<i>n</i><sub>1</sub>(0)");
      initialDensitiesPanel.setLayout(gridBagLayout2);
      initialDensitiesPanel.setBorder(titledBorder9);
      initialDensitiesPanel.setToolTipText("Initial Population Sizes");
      hostRatesPanel.add(paramV, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramC1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parame11, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parame21, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parama11, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parama21, new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramb11, new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramb21, new GridBagConstraints(0, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramD, new GridBagConstraints(0, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramW, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramC2, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parame12, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parame22, new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parama12, new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(parama22, new GridBagConstraints(1, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramb12, new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      hostRatesPanel.add(paramb22, new GridBagConstraints(1, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      initialDensitiesPanel.add(paramn2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      initialDensitiesPanel.add(paramn1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0));
      modelTypePanel.add(equableModelButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      modelTypePanel.add(seasonalModelButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(nvstButton,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(rvstButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(nandrvstButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(phivsr1Button,  new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(phivsr2Button,  new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypePanel.add(n2vsn1Button,  new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      this.add(resourceTypePanel,  new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(initialDensitiesPanel,           new GridBagConstraints(1, 1, 1, 2, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(modelTypePanel,          new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bg1.add(c2r1Button);
      bg1.add(c2r2Button);
      bg1.add(c1r1Button);
      resourceTypePanel.add(c2r1Button,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(c2r2Button,      new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(c1r1Button,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      this.add(runningTimePanel,      new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(hostRatesPanel,   new GridBagConstraints(2, 0, 1, 4, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(plotTypePanel,          new GridBagConstraints(0, 2, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      bg3.add(phivsr1Button);
      bg3.add(phivsr2Button);
      bg3.add(rvstButton);
      bg3.add(nandrvstButton);
      bg3.add(nvstButton);
      bg3.add(n2vsn1Button);

      bg2.add(equableModelButton);
      bg2.add(seasonalModelButton);
      registerChildren(this);
   }

   /** This is called when we change the number of populations or seasonal/equable */
   private void modelChangedActionPerformed(ActionEvent e) {
      //update stuff related to equal/seasonal
      if (equableModelButton.isSelected()) {
         paramD.setEnabled(false);
      }
      else {
         paramD.setEnabled(true);
      }

      //update stuff related to number of populations
      if (this.c1r1Button.isSelected()) {
         phivsr2Button.setEnabled(false);
         if (phivsr2Button.isSelected())
            nvstButton.setSelected(true);
         n2vsn1Button.setEnabled(false);
         if (n2vsn1Button.isSelected())
            nvstButton.setSelected(true);
         parama12.setEnabled(false);
         parama21.setEnabled(false);
         parama22.setEnabled(false);
         paramb12.setEnabled(false);
         paramb21.setEnabled(false);
         paramb22.setEnabled(false);
         parame12.setEnabled(false);
         parame21.setEnabled(false);
         parame22.setEnabled(false);
         paramn2.setEnabled(false);
         paramC2.setEnabled(false);
      }
      else if (this.c2r1Button.isSelected()) {
         phivsr2Button.setEnabled(false);
         if (phivsr2Button.isSelected())
            nvstButton.setSelected(true);
         n2vsn1Button.setEnabled(true);
         parama12.setEnabled(false);
         parama21.setEnabled(true);
         parama22.setEnabled(false);
         paramb12.setEnabled(false);
         paramb21.setEnabled(true);
         paramb22.setEnabled(false);
         parame12.setEnabled(false);
         parame21.setEnabled(true);
         parame22.setEnabled(false);
         paramn2.setEnabled(true);
         paramC2.setEnabled(false);
      }
      else { //c2r2
         phivsr2Button.setEnabled(true);
         n2vsn1Button.setEnabled(true);
         parama12.setEnabled(true);
         parama21.setEnabled(true);
         parama22.setEnabled(true);
         paramb12.setEnabled(true);
         paramb21.setEnabled(true);
         paramb22.setEnabled(true);
         parame12.setEnabled(true);
         parame21.setEnabled(true);
         parame22.setEnabled(true);
         paramn2.setEnabled(true);
         paramC2.setEnabled(true);
      }
      //fireModelPanelEvent( this.CHANGE_PLOT );
   }

   void c1r1Button_actionPerformed(ActionEvent e) {

   }

}
/*
public int getTriggers() {
return 0;
}
*/
//}