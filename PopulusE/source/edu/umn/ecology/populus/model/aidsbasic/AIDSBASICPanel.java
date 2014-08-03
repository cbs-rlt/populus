package edu.umn.ecology.populus.model.aidsbasic;
import java.awt.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import javax.swing.*;
import javax.swing.border.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;

public class AIDSBASICPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -4342213882379052054L;
	Border border1;
   TitledBorder titledBorder1;
   StyledRadioButton xyvtButton = new StyledRadioButton();
   Border border8;
   ButtonGroup bg1 = new ButtonGroup(), bg2 = new ButtonGroup(), bg3 = new ButtonGroup();
   JPanel plotTypePanel = new JPanel();
   RunningTimePanel runningTimePanel1 = new RunningTimePanel();
   StyledRadioButton xyvstButton = new StyledRadioButton();
   Border border9;
   StyledRadioButton vvstButton = new StyledRadioButton();
   StyledRadioButton xvsyvsvButton = new StyledRadioButton();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   PopulusParameterField paramK = new PopulusParameterField();
   JPanel modelParametersPanel = new JPanel();
   Border border6;
   Border border2;
   Border border7;
   TitledBorder titledBorder2;
   JPanel hostRatesPanel = new JPanel();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField paramA = new PopulusParameterField();
   PopulusParameterField paramU = new PopulusParameterField();
   PopulusParameterField parambeta = new PopulusParameterField();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   PopulusParameterField paramX = new PopulusParameterField();
   TitledBorder titledBorder6;
   PopulusParameterField paramY = new PopulusParameterField();
   PopulusParameterField paramV = new PopulusParameterField();
   TitledBorder titledBorder7;
   JPanel hostDensitiesPanel = new JPanel();
   Border border3;
   TitledBorder titledBorder3;
   TitledBorder titledBorder8;
   Border border4;
   TitledBorder titledBorder4;
   TitledBorder titledBorder9;
   Border border5;
   TitledBorder titledBorder5;
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   private PopulusParameterField paramlambda = new PopulusParameterField();
   private PopulusParameterField paramd = new PopulusParameterField();



   public BasicPlot getPlotParamInfo() {
      double time = runningTimePanel1.getTime();
      int plotType;
      if( xyvtButton.isSelected())
         plotType = AIDSBASICParamInfo.vsT;
      else if( vvstButton.isSelected() )
         plotType = AIDSBASICParamInfo.vvsT;
      else if(xyvstButton.isSelected())
         plotType = AIDSBASICParamInfo.xyvsT;
      else
        plotType = AIDSBASICParamInfo.xvsYvsV;

   if( plotType==AIDSBASICParamInfo.xvsYvsV ) {
       return new AIDSBASIC3DParamInfo( time, paramX.getDouble(), paramY.getDouble(), paramV.getDouble(), paramlambda.getDouble(),paramd.getDouble(),paramK.getDouble(),  paramA.getDouble(), parambeta.getDouble(),paramU.getDouble());
     }
     return new AIDSBASICParamInfo( plotType,time, paramX.getDouble(), paramY.getDouble(), paramV.getDouble(), paramlambda.getDouble(),paramd.getDouble(),paramK.getDouble(),  paramA.getDouble(), parambeta.getDouble(),paramU.getDouble());
   }




   public AIDSBASICPanel() {
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
      titledBorder3 = new TitledBorder( border3, "Rate Parameters" );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border4, "Termination Conditions" );
      border5 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder5 = new TitledBorder( border5, "Initial Densities" );
      border6 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder6 = new TitledBorder( border6, "Include R" );
      border7 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder7 = new TitledBorder( border7, "Host Rates" );
      border8 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder8 = new TitledBorder( border8, "Model Version" );
      border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder9 = new TitledBorder( border9, "Initial Densities" );
      plotTypePanel.setBorder( titledBorder1 );
      plotTypePanel.setLayout( simpleVFlowLayout1 );
      xyvtButton.setSelected( true );
      //x,y,v vs t
      xyvtButton.setText( "<i>x, y, v</i>  vs <i>t</i>" );
      vvstButton.setText("<i>v</i>  vs <i>t</i>");
      xyvstButton.setText("<i>x, y</i>  vs <i>t</i>");
      xvsyvsvButton.setText("<i>x</i>  vs <i>y</i> vs <i>v</i>");
      modelParametersPanel.setBorder( titledBorder2 );
      modelParametersPanel.setLayout( gridBagLayout5 );
      hostRatesPanel.setBorder( titledBorder3 );
      hostRatesPanel.setLayout( gridBagLayout3 );
      parambeta.setCurrentValue(0.0010 );
      parambeta.setDefaultValue(0.0010 );
      parambeta.setHelpText( "Between-host transmission rate" );
      parambeta.setIncrementAmount(0.0010 );
      parambeta.setMaxValue( 1.0 );
      parambeta.setParameterName( "\u03b2" );
      paramX.setParameterName("<i>x</i>(0)" );
      paramX.setMaxValue( 100000.0 );
      paramX.setIncrementAmount( 5.0 );
      paramX.setDefaultValue(20.0 );
      paramX.setHelpText("Uninfected Host Cell density" );
      paramX.setCurrentValue(20.0 );
      paramY.setParameterName("<i>y</i>(0)" );
      paramY.setMaxValue( 100000.0 );
      paramY.setIncrementAmount( 5.0 );
      paramY.setDefaultValue(1.0 );
      paramY.setHelpText("Infected host cell density" );
      paramY.setCurrentValue(1.0 );
      paramV.setParameterName("<i>v</i>(0)" );
      paramV.setMaxValue( 100000.0 );
      paramV.setCurrentValue(1.0);
      paramV.setDefaultValue(1.0);
      paramV.setHelpText("Free virus particles density" );
      hostDensitiesPanel.setLayout( gridBagLayout2 );
      hostDensitiesPanel.setBorder(titledBorder5 );
      hostDensitiesPanel.setToolTipText( "Initial Host Densities" );
      this.setLayout( gridBagLayout1 );
      paramK.setParameterName("k" );
      paramK.setMaxValue(200 );
      paramK.setDefaultValue(10.0 );
      paramK.setHelpText("rate of free virions production by infected cells" );
      paramK.setCurrentValue(10.0 );
      paramU.setParameterName("u" );
      paramU.setMaxValue( 1.0 );
      paramU.setIncrementAmount( 0.1 );
      paramU.setHelpText("Free virus death rate" );
      paramU.setDefaultValue(0.5 );
      paramU.setCurrentValue(0.5 );
      paramA.setCurrentValue(0.1 );
      paramA.setDefaultValue(0.1 );
      paramA.setHelpText("Infected cells death rate" );
      paramA.setIncrementAmount( 0.1 );
      paramA.setMaxValue( 1.0 );
      paramA.setParameterName("a" );
      titledBorder9.setTitle( "Host Densities" );
      runningTimePanel1.setDefaultTime(200.0 );
      paramlambda.setCurrentValue(0.2);
      paramlambda.setHelpText("rate of uninfected cell production");
      paramlambda.setDefaultValue(0.4);
      paramlambda.setIncrementAmount(0.1);
      paramlambda.setMaxValue(1.0);
      paramlambda.setParameterName("\u03BB");
      paramd.setCurrentValue(0.01);
      paramd.setHelpText("rate of unifected cell death");
      paramd.setDefaultValue(0.01);
      paramd.setIncrementAmount(0.1);
      paramd.setMaxValue(1.0);
      paramd.setParameterName("d");
      xyvtButton.setSelected(true);




      this.add( modelParametersPanel,  new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 50, 0) );
      hostDensitiesPanel.add( paramX, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostDensitiesPanel.add( paramY, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 0, 0 ) );
      hostDensitiesPanel.add( paramV, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 5, 5, 5, 5 ), 1, 0 ) );
      modelParametersPanel.add( hostRatesPanel,    new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      hostRatesPanel.add( paramK,         new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( parambeta,          new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 1, 0) );
      hostRatesPanel.add( paramU,          new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 3, 0) );
      hostRatesPanel.add( paramA,         new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 1, 0) );
      this.add( plotTypePanel,     new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      plotTypePanel.add( xyvtButton, null );
      plotTypePanel.add( xyvstButton, null );
      plotTypePanel.add(vvstButton, null);
      plotTypePanel.add(xvsyvsvButton, null );
      this.add( runningTimePanel1,   new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      bg2.add( this.xyvtButton );
      bg2.add( this.xyvstButton );
      bg2.add( this.vvstButton );
      bg2.add(this.xvsyvsvButton );
      hostRatesPanel.add(paramlambda,      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      hostRatesPanel.add(paramd,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      modelParametersPanel.add(hostDensitiesPanel,         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

      registerChildren( this );
   }



/*
public int getTriggers() {
return 0;
}
*/
}
