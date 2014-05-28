package edu.umn.ecology.populus.model.rct;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.fileio.Logging;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;

public class RCTPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -4719308612366020192L;
int numVars;
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
   private JRadioButton nvstButton = new StyledRadioButton();
   private GridBagLayout gridBagLayout8 = new GridBagLayout();
   private JPanel DensitiesPanel = new JPanel();
   private GridBagLayout gridBagLayout9 = new GridBagLayout();
   private JPanel DensitiesPanel2 = new JPanel();
   private JPanel hostGrowthPanel2 = new JPanel();
   private GridBagLayout gridBagLayout10 = new GridBagLayout();
   private GridBagLayout gridBagLayout12 = new GridBagLayout();
   private JPanel speciesTypePanel = new JPanel();
   GridBagLayout gridBagLayout13 = new GridBagLayout();
   JRadioButton switchingModelButton = new JRadioButton();
   JPanel modelTypePanel = new JPanel();
   JRadioButton essentialModelButton = new JRadioButton();
   private JRadioButton rvstButton = new StyledRadioButton();
   private JRadioButton nvsnButton = new StyledRadioButton();
   private JRadioButton rvsrButton = new StyledRadioButton();
   private PopulusParameterField paramK21 = new PopulusParameterField();
   private PopulusParameterField paramK31 = new PopulusParameterField();
   private PopulusParameterField paramC21 = new PopulusParameterField();
   private PopulusParameterField paramC11 = new PopulusParameterField();
   private PopulusParameterField paramC31 = new PopulusParameterField();
   private PopulusParameterField paramC9 = new PopulusParameterField();
   private PopulusParameterField paramC10 = new PopulusParameterField();

   private PopulusParameterField paramN01 = new PopulusParameterField();
   private PopulusParameterField paramr1 = new PopulusParameterField();
   private PopulusParameterField paramm1 = new PopulusParameterField();
   private PopulusParameterField paramK11 = new PopulusParameterField();

   private PopulusParameterField paramK22 = new PopulusParameterField();
   private PopulusParameterField paramK32 = new PopulusParameterField();
   private PopulusParameterField paramC22 = new PopulusParameterField();
   private PopulusParameterField paramC12 = new PopulusParameterField();
   private PopulusParameterField paramC32 = new PopulusParameterField();

   private PopulusParameterField paramN02 = new PopulusParameterField();
   private PopulusParameterField paramr2 = new PopulusParameterField();
   private PopulusParameterField paramm2 = new PopulusParameterField();
   private PopulusParameterField paramK12 = new PopulusParameterField();

   private PopulusParameterField paramK13 = new PopulusParameterField();
   private PopulusParameterField paramK23 = new PopulusParameterField();
   private PopulusParameterField paramK33 = new PopulusParameterField();
   private PopulusParameterField paramC23 = new PopulusParameterField();
   private PopulusParameterField paramC13 = new PopulusParameterField();
   private PopulusParameterField paramC33 = new PopulusParameterField();

   private PopulusParameterField paramN03 = new PopulusParameterField();
   private PopulusParameterField paramr3 = new PopulusParameterField();
   private PopulusParameterField paramm3 = new PopulusParameterField();
   private GridBagLayout gridBagLayout15 = new GridBagLayout();
   private PopulusParameterField paramS3 = new PopulusParameterField();
   private PopulusParameterField paramS2 = new PopulusParameterField();
   private PopulusParameterField paramS1 = new PopulusParameterField();
   private PopulusParameterField paramR30 = new PopulusParameterField();
   private PopulusParameterField paramR20 = new PopulusParameterField();
   private PopulusParameterField paramR10 = new PopulusParameterField();
   private JPanel resourceTypePanel = new JPanel();
   private PopulusParameterField paramA1 = new PopulusParameterField();
   private PopulusParameterField paramA2 = new PopulusParameterField();
   private PopulusParameterField paramA3 = new PopulusParameterField();
   public int newindex;
   private JLabel jLabel1 = new JLabel();
   private JLabel jLabel2 = new JLabel();
   private JLabel jLabel3 = new JLabel();
   private JLabel resource1label = new JLabel();
   private JLabel resource2label = new JLabel();
   private JLabel resource3 = new JLabel();
   private PopulusParameterField paramRNum = new PopulusParameterField();
   private PopulusParameterField paramSpeciesNum = new PopulusParameterField();
   private JPanel resourceTypePanel1 = new JPanel();
   private GridBagLayout gridBagLayout16 = new GridBagLayout();

   public BasicPlot getPlotParamInfo() {
      double time = runningTimePanel.getTime();
      int plotType;
      if( nvstButton.isSelected())
         plotType = RCTParamInfo.nvst;
      else {
         if (rvstButton.isSelected())
            plotType = RCTParamInfo.rvst;
         else{
            if(nvsnButton.isSelected())
               plotType = RCTParamInfo.nvsn;
            else
               plotType = RCTParamInfo.rvsr;
         }
      }


      //boolean vsTime = true;
      int modelType;
      if (essentialModelButton.isSelected())
         modelType = RCTParamInfo.ESSENTIAL;
      else{
         if (switchingModelButton.isSelected())
            modelType = RCTParamInfo.SWITCHING;
         else {
            System.err.println( "Error in RCTPanel: Unknown button selected" );
            modelType = -1;
         }
      }
      double[][] K = new double[3][3];
      double[][] C = new double[3][3];
      double[] r = new double[3];
      double[] m = new double[3];
      double[] s = new double[3];
      double[] a = new double[3];
      r[0] = paramr1.getDouble();
      r[1] = paramr2.getDouble();
      r[2] = paramr3.getDouble();
      m[0] = paramm1.getDouble();
      m[1] = paramm2.getDouble();
      m[2] = paramm3.getDouble();
      s[0] = paramS1.getDouble();
      s[1] = paramS2.getDouble();
      s[2] = paramS3.getDouble();
      a[0] = paramA1.getDouble();
      a[1] = paramA2.getDouble();
      a[2] = paramA3.getDouble();


      K[0][0] = paramK11.getDouble();
      K[1][0] = paramK12.getDouble();
      K[2][0] = paramK13.getDouble();
      K[0][1] = paramK21.getDouble();
      K[1][1] = paramK22.getDouble();
      K[2][1] = paramK23.getDouble();
      K[0][2] = paramK31.getDouble();
      K[1][2] = paramK32.getDouble();
      K[2][2] = paramK33.getDouble();

      C[0][0] = paramC11.getDouble();
      C[1][0] = paramC12.getDouble();
      C[2][0] = paramC13.getDouble();
      C[0][1] = paramC21.getDouble();
      C[1][1] = paramC22.getDouble();
      C[2][1] = paramC23.getDouble();
      C[0][2] = paramC31.getDouble();
      C[1][2] = paramC32.getDouble();
      C[2][2] = paramC33.getDouble();
      return new RCTParamInfo( modelType, plotType, time, paramSpeciesNum.getInt(), paramRNum.getInt(), paramN01.getDouble(), paramN02.getDouble(), paramN03.getDouble(), a, r, paramR10.getDouble(), paramR20.getDouble(), paramR30.getDouble(), m, K, C, s );
   }

   public RCTPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, "Plot Type" );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, "Model Parameters" );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, "Host Rates" );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border4, "Termination Conditions" );
      border5 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder5 = new TitledBorder( border5, "Host Densities" );
      border6 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder6 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Resources & Species");
      border7 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder7 = new TitledBorder( border7, "Host Rates" );
      border8 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder8 = new TitledBorder( border8, "Model Version" );
      border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder9 = new TitledBorder(BorderFactory.createLineBorder(SystemColor.controlText,1),"Species");
      border10 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder10 = new TitledBorder( border10, "Resources" );
      border11 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder11 = new TitledBorder( border11, "Plasmid-Free" );
      plotTypePanel.setBorder( titledBorder1 );
      plotTypePanel.setLayout( gridBagLayout7 );
      this.setLayout( gridBagLayout1 );


      nvstButton.setToolTipText("");
      nvstButton.setActionCommand("<i>N</i> vs <i>t</>");
      nvstButton.setText("<i>N</i> vs <i>t</>");

      speciesTypePanel.setBorder(titledBorder9);
      speciesTypePanel.setLayout(gridBagLayout12);
      switchingModelButton.setToolTipText("Switching");
      switchingModelButton.setActionCommand("Switching");
      switchingModelButton.setText("Switching");
      switchingModelButton.setFocusPainted(false);
      switchingModelButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            switchingModelButton_actionPerformed( e );
         }
      });
      modelTypePanel.setLayout(gridBagLayout13);
      modelTypePanel.setBorder(titledBorder8);
      essentialModelButton.setToolTipText("Essential");
      essentialModelButton.setActionCommand("Essential");
      essentialModelButton.setText("Essential");
      essentialModelButton.setSelected(true);
      essentialModelButton.addActionListener(new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            essentialModelButton_actionPerformed( e );
         }
      });
      rvstButton.setText("<i>R </i>vs <i>t</i>");
      rvstButton.setToolTipText("");
      nvsnButton.setFocusPainted(false);
      nvsnButton.setText("<i>N</i> vs <i>N</i>");
      rvsrButton.setText("<i>R</i> vs <i>R</i>");
      rvsrButton.setFocusPainted(false);
      rvsrButton.setSelected(true);

      paramK21.setCurrentValue(4.0);
      paramK21.setDefaultValue(4.0);
      paramK21.setHelpText("Half-saturation constant for growth on R2");
      paramK21.setIncrementAmount(2.0);
      paramK21.setMaxValue(100.0);
      paramK21.setParameterName("<i>k</i><sub>12</sub>");
      paramK31.setParameterName("<i>k</i><sub>13</sub>");
      paramK31.setMaxValue(100.0);
      paramK31.setIncrementAmount(2.0);
      paramK31.setHelpText("Half-saturation constant for growth on R3");
      paramK31.setDefaultValue(11.0);
      paramK31.setCurrentValue(11.0);
      paramC21.setCurrentValue(0.08);
      paramC21.setDefaultValue(0.08);
      paramC21.setHelpText("Consumption rate of R2");
      paramC21.setIncrementAmount(0.1);
      paramC21.setMaxValue(100.0);
      paramC21.setParameterName("<i>c</i><sub>12</sub>");
      paramC11.setCurrentValue(0.25);
      paramC11.setDefaultValue(0.25);
      paramC11.setHelpText("Consumption rate of R1");
      paramC11.setIncrementAmount(0.1);
      paramC11.setMaxValue(100.0);
      paramC11.setParameterName("<i>c</i><sub>11</sub>");
      paramC31.setCurrentValue(0.16);
      paramC31.setDefaultValue(0.16);
      paramC31.setHelpText("Consumption rate of R3");
      paramC31.setIncrementAmount(2.0);
      paramC31.setMaxValue(100.0);
      paramC31.setParameterName("<i>c</i><sub>13</sub>");


      paramN01.setParameterName("<i>N</i>(0)");
      paramN01.setMaxValue(1000.0);
      paramN01.setIncrementAmount(2.0);
      paramN01.setHelpText("Initial population density of species 1");
      paramN01.setDefaultValue(10.0);
      paramN01.setCurrentValue(10.0);

      paramN02.setParameterName("<i>N</i>(0)");
      paramN02.setMaxValue(1000.0);
      paramN02.setIncrementAmount(2.0);
      paramN02.setHelpText("Initial population density of species 2");
      paramN02.setDefaultValue(10.0);
      paramN02.setCurrentValue(10.0);

      paramN03.setParameterName("<i>N</i>(0)");
      paramN03.setMaxValue(1000.0);
      paramN03.setIncrementAmount(2.0);
      paramN03.setHelpText("Initial population density of species 3");
      paramN03.setDefaultValue(10.0);
      paramN03.setCurrentValue(10.0);
      paramN03.setEnabled(false);


      paramr1.setParameterName("<i>r</i><sub>1</sub>");
      paramr1.setMaxValue(100.0);
      paramr1.setIncrementAmount(0.1);
      paramr1.setHelpText("Maximal growth rate");
      paramr1.setDefaultValue(1.6);
      paramr1.setCurrentValue(1.6);
      paramm1.setParameterName("<i>m</i><sub>1</sub>");
      paramm1.setMaxValue(100.0);
      paramm1.setIncrementAmount(0.5);
      paramm1.setHelpText("Mortality rate of species 1");
      paramm1.setDefaultValue(0.2);
      paramm1.setCurrentValue(0.2);
      paramK11.setCurrentValue(18.0);
      paramK11.setDefaultValue(18.0);
      paramK11.setHelpText("Half-saturation constant for growth on R1");
      paramK11.setIncrementAmount(2.0);
      paramK11.setMaxValue(100.0);
      paramK11.setParameterName("<i>k</i><sub>11</sub>");

      paramr2.setParameterName("<i>r</i><sub>2</sub>");
      paramr2.setMaxValue(100.0);
      paramr2.setIncrementAmount(0.1);
      paramr2.setHelpText("Maximal growth rate");
      paramr2.setDefaultValue(1.0);
      paramr2.setCurrentValue(1.0);
      paramm2.setParameterName("<i>m</i><sub>2</sub>");
      paramm2.setMaxValue(100.0);
      paramm2.setIncrementAmount(0.5);
      paramm2.setHelpText("Mortality rate of species 2");
      paramm2.setDefaultValue(0.2);
      paramm2.setCurrentValue(0.2);
      paramK12.setCurrentValue(2.0);
      paramK12.setDefaultValue(2.0);
      paramK12.setHelpText("Half-saturation constant for growth on R1");
      paramK12.setIncrementAmount(2.0);
      paramK12.setMaxValue(100.0);
      paramK12.setParameterName("<i>k</i><sub>21</sub>");
      paramK22.setCurrentValue(14.0);
      paramK22.setDefaultValue(14.0);
      paramK22.setHelpText("Half-saturation constant for growth on R2");
      paramK22.setIncrementAmount(2.0);
      paramK22.setMaxValue(100.0);
      paramK22.setParameterName("<i>k</i><sub>22</sub>");
      paramK32.setParameterName("<i>k</i><sub>23</sub>");
      paramK32.setMaxValue(100.0);
      paramK32.setIncrementAmount(2.0);
      paramK32.setHelpText("Half-saturation constant for growth on R3");
      paramK32.setDefaultValue(8.0);
      paramK32.setCurrentValue(8.0);
      paramC22.setCurrentValue(0.2);
      paramC22.setDefaultValue(0.2);
      paramC22.setHelpText("Consumption rate of R2");
      paramC22.setIncrementAmount(0.1);
      paramC22.setMaxValue(100.0);
      paramC22.setParameterName("<i>c</i><sub>22</sub>");
      paramC12.setCurrentValue(0.1);
      paramC12.setDefaultValue(0.1);
      paramC12.setHelpText("Consumption rate of R1");
      paramC12.setIncrementAmount(0.1);
      paramC12.setMaxValue(100.0);
      paramC12.setParameterName("<i>c</i><sub>21</sub>");
      paramC32.setCurrentValue(0.15);
      paramC32.setDefaultValue(0.15);
      paramC32.setHelpText("Consumption rate of R3");
      paramC32.setIncrementAmount(2.0);
      paramC32.setMaxValue(100.0);
      paramC32.setParameterName("<i>c</i><sub>23</sub>");

      paramr3.setParameterName("<i>r</i><sub>3</sub>");
      paramr3.setMaxValue(100.0);
      paramr3.setIncrementAmount(0.1);
      paramr3.setHelpText("Maximal growth rate");
      paramr3.setDefaultValue(1.3);
      paramr3.setCurrentValue(1.3);
      paramr3.setEnabled(false);
      paramm3.setParameterName("<i>m</i><sub>3</sub>");
      paramm3.setMaxValue(100.0);
      paramm3.setIncrementAmount(0.5);
      paramm3.setHelpText("Mortality rate of species 3");
      paramm3.setDefaultValue(0.2);
      paramm3.setCurrentValue(0.2);
      paramm3.setEnabled(false);
      paramK13.setCurrentValue(10.0);
      paramK13.setDefaultValue(10.0);
      paramK13.setHelpText("Half-saturation constant for growth on R1");
      paramK13.setIncrementAmount(2.0);
      paramK13.setMaxValue(100.0);
      paramK13.setParameterName("<i>k</i><sub>31</sub>");
      paramK13.setEnabled(false);
      paramK23.setCurrentValue(9.0);
      paramK23.setDefaultValue(9.0);
      paramK23.setHelpText("Half-saturation constant for growth on R2");
      paramK23.setIncrementAmount(2.0);
      paramK23.setMaxValue(100.0);
      paramK23.setParameterName("<i>k</i><sub>32</sub>");
      paramK33.setParameterName("<i>k</i><sub>33</sub>");
      paramK33.setMaxValue(100.0);
      paramK33.setIncrementAmount(2.0);
      paramK33.setHelpText("Half-saturation constant for growth on R3");
      paramK33.setDefaultValue(9.5);
      paramK33.setCurrentValue(9.5);
      paramC23.setCurrentValue(0.14);
      paramC23.setDefaultValue(0.14);
      paramC23.setHelpText("Consumption rate of R2");
      paramC23.setIncrementAmount(0.1);
      paramC23.setMaxValue(100.0);
      paramC23.setParameterName("<i>c</i><sub>32</sub>");
      paramC13.setCurrentValue(0.175);
      paramC13.setDefaultValue(0.175);
      paramC13.setHelpText("Consumption rate of R1");
      paramC13.setIncrementAmount(0.1);
      paramC13.setMaxValue(100.0);
      paramC13.setParameterName("<i>c</i><sub>31</sub>");
      paramC13.setEnabled(false);
      paramC33.setCurrentValue(0.15);
      paramC33.setDefaultValue(0.15);
      paramC33.setHelpText("Consumption rate of R3");
      paramC33.setIncrementAmount(2.0);
      paramC33.setMaxValue(100.0);
      paramC33.setParameterName("<i>c</i><sub>33</sub>");
      paramK31.setEnabled(false);
      paramK32.setEnabled(false);
      paramK23.setEnabled(false);
      paramK33.setEnabled(false);
      paramC31.setEnabled(false);
      paramC32.setEnabled(false);
      paramC23.setEnabled(false);
      paramC33.setEnabled(false);
      runningTimePanel.setDefaultTime(100.0);

      paramS3.setCurrentValue(12.0);
      paramS3.setDefaultValue(12.0);
      paramS3.setHelpText("Maximum amount of resource 3");
      paramS3.setIncrementAmount(2.0);
      paramS3.setMaxValue(1000.0);
      paramS3.setParameterName("<i>S<sub>3</sub></i>");
      paramS3.setEnabled(false);
      paramS2.setCurrentValue(12.0);
      paramS2.setDefaultValue(12.0);
      paramS2.setHelpText("Maximum amount of resource 2");
      paramS2.setIncrementAmount(2.0);
      paramS2.setMaxValue(1000.0);
      paramS2.setParameterName("<i>S<sub>2</sub></i>");
      paramS1.setCurrentValue(12.0);
      paramS1.setDefaultValue(12.0);
      paramS1.setHelpText("Maximum amount of resource 1");
      paramS1.setIncrementAmount(2.0);
      paramS1.setMaxValue(1000.0);
      paramS1.setParameterName("<i>S<sub>1</sub></i>");
      paramR30.setCurrentValue(12.0);
      paramR30.setDefaultValue(12.0);
      paramR30.setHelpText("Initial concentration of R3 in the habitat");
      paramR30.setIncrementAmount(2.0);
      paramR30.setMaxValue(1000.0);
      paramR30.setParameterName("<i>R<sub>3</sub></i>(0)");
      paramR30.setEnabled(false);
      paramR20.setCurrentValue(12.0);
      paramR20.setDefaultValue(12.0);
      paramR20.setHelpText("Initial concentration of R2 in the habitat");
      paramR20.setIncrementAmount(2.0);
      paramR20.setMaxValue(1000.0);
      paramR20.setParameterName("<i>R<sub>2</sub></i>(0)");
      paramR10.setCurrentValue(12.0);
      paramR10.setDefaultValue(12.0);
      paramR10.setHelpText("Initial concentration of R1 in the habitat");
      paramR10.setIncrementAmount(2.0);
      paramR10.setMaxValue(1000.0);
      paramR10.setParameterName("<i>R<sub>1</sub></i>(0)");
      resourceTypePanel.setLayout(gridBagLayout15);
      resourceTypePanel.setBorder(titledBorder10);
      paramA1.setParameterName("<i>a</i><sub>1</sub>");
      paramA1.setMaxValue(100.0);
      paramA1.setIncrementAmount(0.1);
      paramA1.setHelpText("Rate constant determining resource recycling time for resource 1");
      paramA1.setDefaultValue(0.5);
      paramA1.setCurrentValue(0.5);
      paramA2.setParameterName("<i>a</i><sub>2</sub>");
      paramA2.setMaxValue(100.0);
      paramA2.setIncrementAmount(0.1);
      paramA2.setHelpText("Rate constant determining resource recycling time for resource 1");
      paramA2.setDefaultValue(0.5);
      paramA2.setCurrentValue(0.5);
      paramA3.setParameterName("<i>a</i><sub>3</sub>");
      paramA3.setMaxValue(100.0);
      paramA3.setIncrementAmount(0.1);
      paramA3.setHelpText("Rate constant determining resource recycling time for resource 1");
      paramA3.setDefaultValue(0.5);
      paramA3.setCurrentValue(0.5);
      paramA3.setEnabled(false);
      jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
      jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel1.setText("Species 1");
      jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
      jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel2.setText("Species 2");
      jLabel3.setFont(new java.awt.Font("Dialog", 1, 12));
      jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
      jLabel3.setText("Species 3");
      resource1label.setFont(new java.awt.Font("Dialog", 1, 12));
      resource1label.setHorizontalAlignment(SwingConstants.CENTER);
      resource1label.setText("Resource 1");
      resource2label.setFont(new java.awt.Font("Dialog", 1, 12));
      resource2label.setHorizontalAlignment(SwingConstants.CENTER);
      resource2label.setText("Resource 2");
      resource3.setFont(new java.awt.Font("Dialog", 1, 12));
      resource3.setHorizontalAlignment(SwingConstants.CENTER);
      resource3.setText("Resource 3");
      paramRNum.setParameterName("<i> # of Resources</i>");
      paramRNum.setMinValue(1.0);
      paramRNum.setHelpText("Number of resources");
      paramRNum.setMaxValue(3.0);
      paramRNum.setDefaultValue(2.0);
      paramRNum.setCurrentValue(2.0);
      paramRNum.setIntegersOnly(true);
      paramRNum.addParameterFieldListener(new ResourceIncrementChecker());
      paramSpeciesNum.setParameterName("<i># of Species</i>");
      paramSpeciesNum.setMaxValue(3.0);
      paramSpeciesNum.setMinValue(1.0);
      paramSpeciesNum.setHelpText("Number of Species");
      paramSpeciesNum.setDefaultValue(2.0);
      paramSpeciesNum.setCurrentValue(2.0);
      paramSpeciesNum.addParameterFieldListener(new SpeciesIncrementChecker());
      paramSpeciesNum.setIntegersOnly(true);
      resourceTypePanel1.setBorder(titledBorder6);
      resourceTypePanel1.setLayout(gridBagLayout16);
      plotTypePanel.add(nvstButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(rvstButton, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(nvsnButton, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      plotTypePanel.add(rvsrButton, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      this.add(resourceTypePanel,           new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      resourceTypePanel.add(paramR10,           new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramS1,           new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramR20,           new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramS2,           new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramR30,           new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramS3,           new GridBagConstraints(2, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramA1,            new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramA2,            new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(paramA3,            new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(resource1label,      new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(resource2label,      new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel.add(resource3,      new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      this.add(speciesTypePanel,               new GridBagConstraints(1, 0, 1, 3, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(runningTimePanel,           new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.add(modelTypePanel,                new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      modelTypePanel.add(essentialModelButton,     new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      modelTypePanel.add(switchingModelButton,     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      registerChildren( this );
      bg3.add(nvstButton);
      bg3.add(rvstButton);
      bg3.add(nvsnButton);
      bg3.add(rvsrButton);



      bg2.add(essentialModelButton);
      bg2.add(switchingModelButton);
      speciesTypePanel.add(paramN01,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramr1,   new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramm1,   new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK11,     new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK21,     new GridBagConstraints(0, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK31,     new GridBagConstraints(0, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC11,     new GridBagConstraints(0, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC21,     new GridBagConstraints(0, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC31,     new GridBagConstraints(0, 9, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

      speciesTypePanel.add(paramN02,   new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramr2,   new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramm2,   new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK12,     new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK22,     new GridBagConstraints(1, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK32,     new GridBagConstraints(1, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC12,     new GridBagConstraints(1, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC22,     new GridBagConstraints(1, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC32,     new GridBagConstraints(1, 9, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

      speciesTypePanel.add(paramN03,   new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramr3,   new GridBagConstraints(2, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramm3,   new GridBagConstraints(2, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK13,     new GridBagConstraints(2, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK23,     new GridBagConstraints(2, 5, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramK33,     new GridBagConstraints(2, 6, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC13,     new GridBagConstraints(2, 7, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC23,     new GridBagConstraints(2, 8, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(paramC33,     new GridBagConstraints(2, 9, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(jLabel1,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(jLabel2,  new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
      speciesTypePanel.add(jLabel3,  new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));



      this.add(plotTypePanel,               new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      resourceTypePanel1.add(paramSpeciesNum,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
      resourceTypePanel1.add(paramRNum,      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
      this.add(resourceTypePanel1,        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      this.registerChildren(this);
   }
   class SpeciesIncrementChecker implements ParameterFieldListener {
      public void parameterFieldChanged( ParameterFieldEvent e ) {
         switch (paramSpeciesNum.getInt()) {
            case 1:
               paramN02.setEnabled(false);
               paramN03.setEnabled(false);
               paramr2.setEnabled(false);
               paramr3.setEnabled(false);
               paramm2.setEnabled(false);
               paramm3.setEnabled(false);
               nvsnButton.setEnabled(false);
               if (nvsnButton.isSelected())
                  nvstButton.setSelected(true);
               break;
            case 2:
               paramN02.setEnabled(true);
               paramN03.setEnabled(false);
               paramr2.setEnabled(true);
               paramr3.setEnabled(false);
               paramm2.setEnabled(true);
               paramm3.setEnabled(false);
               nvsnButton.setEnabled(true);
               break;
            default:
               Logging.log("Invalid speciesNum " + paramSpeciesNum.getInt(), Logging.kHuh);
            case 3:
               paramN02.setEnabled(true);
               paramN03.setEnabled(true);
               paramr2.setEnabled(true);
               paramr3.setEnabled(true);
               paramm2.setEnabled(true);
               paramm3.setEnabled(true);
               nvsnButton.setEnabled(true);
               break;
         }
         checkCKVars();
      }
   }
   class ResourceIncrementChecker implements ParameterFieldListener {
      public void parameterFieldChanged( ParameterFieldEvent e ) {
         switch (paramRNum.getInt()) {
            case 1:
               paramA2.setEnabled(false);
               paramA3.setEnabled(false);
               paramR20.setEnabled(false);
               paramR30.setEnabled(false);
               paramS2.setEnabled(false);
               paramS3.setEnabled(false);
               rvsrButton.setEnabled(false);
               if (rvsrButton.isSelected())
                  rvstButton.setSelected(true);
               break;
            case 2:
               paramA2.setEnabled(true);
               paramA3.setEnabled(false);
               paramR20.setEnabled(true);
               paramR30.setEnabled(false);
               paramS2.setEnabled(true);
               paramS3.setEnabled(false);
               rvsrButton.setEnabled(true);
               break;
            default:
               Logging.log("Invalid paramRNum " + paramRNum.getInt(), Logging.kHuh);
            case 3:
               paramA2.setEnabled(true);
               paramA3.setEnabled(true);
               paramR20.setEnabled(true);
               paramR30.setEnabled(true);
               paramS2.setEnabled(true);
               paramS3.setEnabled(true);
               rvsrButton.setEnabled(true);
               break;
         }
         checkCKVars();
      }
   }
   void checkCKVars() {
      int s = paramSpeciesNum.getInt();
      int r = paramRNum.getInt();
      //param[CK]AB, where A=resources, B=species
      paramK21.setEnabled(r>1 && s>0);
      paramK31.setEnabled(r>2 && s>0);
      paramK12.setEnabled(r>0 && s>1);
      paramK22.setEnabled(r>1 && s>1);
      paramK32.setEnabled(r>2 && s>1);
      paramK13.setEnabled(r>0 && s>2);
      paramK23.setEnabled(r>1 && s>2);
      paramK33.setEnabled(r>2 && s>2);
      paramC21.setEnabled(r>1 && s>0);
      paramC31.setEnabled(r>2 && s>0);
      paramC12.setEnabled(r>0 && s>1);
      paramC22.setEnabled(r>1 && s>1);
      paramC32.setEnabled(r>2 && s>1);
      paramC13.setEnabled(r>0 && s>2);
      paramC23.setEnabled(r>1 && s>2);
      paramC33.setEnabled(r>2 && s>2);
   }

   void switchingModelButton_actionPerformed(ActionEvent e) {
      //Yes, we have to set max first... long story
      paramRNum.setMaxValue(3, true);
      paramRNum.setMinValue(2, true);
   }
   void essentialModelButton_actionPerformed(ActionEvent e) {
      paramRNum.setMinValue(1, true);
      paramRNum.setMaxValue(3, true);
   }
}
