package edu.umn.ecology.populus.model.interdgs;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.plot.*;
import javax.swing.*;
import javax.swing.border.*;

public class INTERDGSPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -465299890956646946L;
JPanel indCoefsP = new JPanel();
   PopulusParameterField sEEPPF = new PopulusParameterField();
   private JPanel plotTypeP = new JPanel();
   private JRadioButton svstRB = new StyledRadioButton();
   private JRadioButton AvstRB = new StyledRadioButton();
   private JPanel modelP = new JPanel();
   private PopulusParameterField gensPPF = new PopulusParameterField();
   private PopulusParameterField sAEPPF = new PopulusParameterField();
   private PopulusParameterField sAAPPF = new PopulusParameterField();
   private PopulusParameterField cPPF = new PopulusParameterField();
   private JPanel groupCoefsP = new JPanel();
   private PopulusParameterField aPPF = new PopulusParameterField();
   private PopulusParameterField bPPF = new PopulusParameterField();
   private PopulusParameterField runsPPF = new PopulusParameterField();
   private PopulusParameterField AfreqPPF = new PopulusParameterField();
   private PopulusParameterField mPPF = new PopulusParameterField();
   private JCheckBox singleMutationCB = new JCheckBox();
   private PopulusParameterField demeSizePPF = new PopulusParameterField();
   private TitledBorder titledBorder1;
   private TitledBorder titledBorder2;
   private TitledBorder titledBorder3;
   private TitledBorder titledBorder4;
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private GridBagLayout gridBagLayout2 = new GridBagLayout();
   private GridBagLayout gridBagLayout3 = new GridBagLayout();
   private GridBagLayout gridBagLayout4 = new GridBagLayout();
   private GridBagLayout gridBagLayout5 = new GridBagLayout();
   private ButtonGroup plotTypeBG = new ButtonGroup();
   private final int numDemes = 10;

   public BasicPlot getPlotParamInfo() {
      int plotType;
      double Afreq = AfreqPPF.getDouble();
      double k, n2 = 2*demeSizePPF.getInt();
      double[] freqs = new double[numDemes];

      if(singleMutationCB.isSelected()){
         freqs[0] = 1.0/n2;
      } else {
         for(int i=0; i<numDemes; i++){
            k = 0;
            for(int j=0; j<n2; j++)
               if(Math.random() < Afreq) k++;
            freqs[i] = k/n2;
         }
      }

      if(AvstRB.isSelected())
         plotType = INTERDGSParamInfo.AvsT;
      else
         plotType = INTERDGSParamInfo.SvsT;

      return new INTERDGSParamInfo(aPPF.getDouble(),bPPF.getDouble(),cPPF.getDouble(),sAAPPF.getDouble(),
                                   sAEPPF.getDouble(),sEEPPF.getDouble(),demeSizePPF.getInt(),mPPF.getDouble(),
                                   freqs,runsPPF.getInt(),gensPPF.getInt(),plotType);
   }

   public INTERDGSPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   private void jbInit() throws Exception {
      titledBorder1 = new TitledBorder("Group Coefficients");
      titledBorder2 = new TitledBorder("Individual Coefficients");
      titledBorder3 = new TitledBorder("Plot Type");
      titledBorder4 = new TitledBorder("Model Parameters");
      groupCoefsP.setBorder(titledBorder1);
      indCoefsP.setBorder(titledBorder2);
      plotTypeP.setBorder(titledBorder3);
      modelP.setBorder(titledBorder4);
      indCoefsP.setLayout(gridBagLayout3 );
      sEEPPF.setIncrementAmount( 0.05 );
      sEEPPF.setMaxValue( 1.0 );
      sEEPPF.setParameterName("<i>s<sub>EE</>" );
      sEEPPF.setHelpText("This is a selection coefficient.");
      svstRB.setText("<i>\u03c3<sup>2</> vs <i>t</i>");
      AvstRB.setSelected(true);
      AvstRB.setText("<i>A</i> vs <i>t</i>");
      gensPPF.setCurrentValue(50.0);
      gensPPF.setDefaultValue(50.0);
      gensPPF.setIncrementAmount(10.0);
      gensPPF.setIntegersOnly(true);
      gensPPF.setMaxValue(1000.0);
      gensPPF.setMinValue(1.0);
      gensPPF.setParameterName("Generations");
      gensPPF.setHelpText("Number of generations to be simulated.");
      sAEPPF.setParameterName("<i>s<sub>AE</>");
      sAEPPF.setMaxValue(1.0);
      sAEPPF.setIncrementAmount(0.05);
      sAEPPF.setHelpText("This is a selection coefficient.");
      sAAPPF.setParameterName("<i>s<sub>AA</>");
      sAAPPF.setMaxValue(1.0);
      sAAPPF.setIncrementAmount(0.05);
      sAAPPF.setDefaultValue(0.1);
      sAAPPF.setCurrentValue(0.1);
      sAAPPF.setHelpText("This is a selection coefficient.");
      plotTypeP.setLayout(gridBagLayout4);
      cPPF.setParameterName("<i>c</i>");
      cPPF.setMaxValue(5.0);
      cPPF.setIncrementAmount(0.1);
      cPPF.setDefaultValue(1.0);
      cPPF.setCurrentValue(1.0);
      cPPF.setHelpText("This a deme survival coefficient.");
      groupCoefsP.setLayout(gridBagLayout2);
      aPPF.setParameterName("<i>a</i>");
      aPPF.setMaxValue(5.0);
      aPPF.setIncrementAmount(0.1);
      aPPF.setDefaultValue(0.5);
      aPPF.setCurrentValue(0.5);
      aPPF.setHelpText("This is a deme survival coefficient." );
      bPPF.setParameterName("<i>b</i>");
      bPPF.setMaxValue(5.0);
      bPPF.setIncrementAmount(0.1);
      bPPF.setDefaultValue(0.5);
      bPPF.setCurrentValue(0.5);
      bPPF.setHelpText("This is a deme survival coefficient.");
      runsPPF.setCurrentValue(5.0);
      runsPPF.setDefaultValue(5.0);
      runsPPF.setIntegersOnly(true);
      runsPPF.setMaxValue(100.0);
      runsPPF.setParameterName("Runs");
      runsPPF.setHelpText("The number of runs to be averaged over.");
      runsPPF.setToolTipText("");
      AfreqPPF.setCurrentValue(0.5);
      AfreqPPF.setDefaultValue(0.5);
      AfreqPPF.setIncrementAmount(0.05);
      AfreqPPF.setMaxValue(1.0);
      AfreqPPF.setParameterName("\"<i>A</i>\" starting frequency");
      mPPF.setCurrentValue(0.05);
      mPPF.setDefaultValue(0.05);
      mPPF.setIncrementAmount(0.05);
      mPPF.setMaxValue(5.0);
      mPPF.setParameterName("Migration Rate");
      mPPF.setHelpText("Migration rate");
      singleMutationCB.setText("Single Mutation?");
      singleMutationCB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            singleMutationCB_actionPerformed(e);
         }
      });
      demeSizePPF.setParameterName("Deme Size");
      demeSizePPF.setMaxValue(100.0);
      demeSizePPF.setIncrementAmount(5.0);
      demeSizePPF.setIntegersOnly(true);
      demeSizePPF.setDefaultValue(10.0);
      demeSizePPF.setCurrentValue(10.0);
      modelP.setLayout(gridBagLayout5);
      this.setLayout(gridBagLayout1);
      this.add( indCoefsP,   new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0) );
      indCoefsP.add(sAAPPF,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      indCoefsP.add(sAEPPF,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      indCoefsP.add( sEEPPF,   new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0) );
      this.add(modelP,    new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      modelP.add(demeSizePPF,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      modelP.add(singleMutationCB,     new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
      modelP.add(mPPF,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      modelP.add(AfreqPPF,    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 5, 5, 5), 0, 0));
      modelP.add(runsPPF,     new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      modelP.add(gensPPF,      new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      this.add(plotTypeP,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      plotTypeP.add(AvstRB,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
      plotTypeP.add(svstRB,    new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 5, 0), 0, 0));
      this.add(groupCoefsP,     new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
      groupCoefsP.add(aPPF,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));
      groupCoefsP.add(bPPF,   new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      groupCoefsP.add(cPPF,   new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 5, 5), 0, 0));
      plotTypeBG.add(AvstRB);
      plotTypeBG.add(svstRB);
      this.registerChildren( this );
   }

   void singleMutationCB_actionPerformed(ActionEvent e) {
      AfreqPPF.setEnabled(!singleMutationCB.isSelected());
   }
}
