package edu.umn.ecology.populus.model.aidst;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;

public class AIDSTPanel extends BasicPlotInputPanel {
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
   //StyledRadioButton xvsyvsvButton = new StyledRadioButton();
   VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
   PopulusParameterField paramB = new PopulusParameterField();
   JPanel modelParametersPanel = new JPanel();
   Border border6;
   Border border2;
   Border border7;
   TitledBorder titledBorder2;
   JPanel hostRatesPanel = new JPanel();
   GridBagLayout gridBagLayout5 = new GridBagLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField paramA = new PopulusParameterField();
   PopulusParameterField parambeta = new PopulusParameterField();
   PopulusParameterField paramC= new PopulusParameterField();
   PopulusParameterField paramQ = new PopulusParameterField();
   PopulusParameterField paramH = new PopulusParameterField();
   PopulusParameterField paramS = new PopulusParameterField();
   PopulusParameterField paramP = new PopulusParameterField();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   PopulusParameterField paramX = new PopulusParameterField();
   TitledBorder titledBorder6;

   PopulusParameterField paramY = new PopulusParameterField();
   PopulusParameterField paramW = new PopulusParameterField();
   PopulusParameterField paramZ = new PopulusParameterField();


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
   Border border10;
   TitledBorder titledBorder10;
   Border border11;
   TitledBorder titledBorder11;
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   private PopulusParameterField paramlambda = new PopulusParameterField();
   private PopulusParameterField paramd = new PopulusParameterField();
   private GridBagLayout gridBagLayout6 = new GridBagLayout();
   //private JPanel intervalPanel = new JPanel();
   private GridBagLayout gridBagLayout7 = new GridBagLayout();
   //private JPanel intervalPanel1 = new JPanel();
   //private PopulusParameterField paramt2 = new PopulusParameterField();
   //private PopulusParameterField paramt1 = new PopulusParameterField();
   //private GridBagLayout gridBagLayout8 = new GridBagLayout();
   //private JPanel intervalPanel2 = new JPanel();
   //private PopulusParameterField paramt4 = new PopulusParameterField();
  /// private PopulusParameterField paramt3 = new PopulusParameterField();
 //  private GridBagLayout gridBagLayout9 = new GridBagLayout();
 //  private JPanel intervalPanel3 = new JPanel();
 //  private PopulusParameterField paramt5 = new PopulusParameterField();
 //  private PopulusParameterField paramt6 = new PopulusParameterField();
   private AIDSTIntervalArray intervalArray = new AIDSTIntervalArray();



   public BasicPlot getPlotParamInfo() {
      double time= runningTimePanel1.getTime();
      double[][] intTimes = intervalArray.getIntervals();
      double s = paramS.getDouble();
      int plotType;
      if( xyvtButton.isSelected())
         plotType = AIDSTParamInfo.vsT;
      else if( vvstButton.isSelected() )
         plotType = AIDSTParamInfo.vvsT;
      else if(xyvstButton.isSelected())
         plotType = AIDSTParamInfo.xyvsT;
      else
        plotType = AIDSTParamInfo.xvsYvsV;


      if( plotType==AIDSTParamInfo.xvsYvsV ) {

         return new AIDST3DParamInfo( time, paramX.getDouble(), paramY.getDouble(),paramW.getDouble(),paramZ.getDouble(),
                                      paramlambda.getDouble(),paramd.getDouble(),paramB.getDouble(),  paramA.getDouble(),
                                      parambeta.getDouble(), paramC.getDouble(), paramQ.getDouble(), paramH.getDouble(),
                                      paramS.getDouble(), paramP.getDouble(), intTimes);
      }

      return new AIDSTParamInfo( plotType,time, paramX.getDouble(), paramY.getDouble(), paramW.getDouble(), paramZ.getDouble(),
                                 paramlambda.getDouble(),paramd.getDouble(),paramA.getDouble(),parambeta.getDouble(),
                                 paramB.getDouble(), paramC.getDouble(),paramQ.getDouble(), paramH.getDouble(), paramS.getDouble(),
                                 paramP.getDouble(), intTimes );
   }




   public AIDSTPanel() {
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
      titledBorder6 = new TitledBorder( border6, "Therapy Intervals" );
      border7 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder7 = new TitledBorder( border7, "Host Rates" );
      border9 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder9 = new TitledBorder( border9, "Initial Densities" );
/*
      border8 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder8 = new TitledBorder( border8, "Treatment Interval 1" );
      border10 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder10 = new TitledBorder( border8, "Treatment Interval 2" );
      border11 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder11 = new TitledBorder( border8, "Treatment Interval 3" );
*/

      plotTypePanel.setBorder( titledBorder1 );
      plotTypePanel.setLayout( verticalFlowLayout1 );

      //x,y,w,z vs t
      //xyvtButton.setText( "<i>x, y, w, z</i>  vs <i>t</i>" );
      xyvtButton.setText( "<i>x, y, w, z</i>  vs <i>t</i>" );
      vvstButton.setText("<i>y, w</i>  vs <i>t</i>");
      xyvstButton.setText("<i>x, y</i>  vs <i>t</i>");
      modelParametersPanel.setBorder( titledBorder2 );
      modelParametersPanel.setLayout( gridBagLayout5 );
      hostRatesPanel.setBorder(titledBorder3 );
      hostRatesPanel.setLayout( gridBagLayout3 );
      parambeta.setCurrentValue(0.5 );
      parambeta.setDefaultValue(0.5 );
      parambeta.setHelpText( "Between-host transmission rate" );
      parambeta.setIncrementAmount(0.0010 );
      parambeta.setMaxValue( 1.0 );
      parambeta.setParameterName( "\u03b2" );
      paramX.setParameterName("<i>x</i>(0)" );
      paramX.setMaxValue( 100000.0 );
      paramX.setIncrementAmount( 5.0 );
      paramX.setDefaultValue(20.0 );
      paramX.setHelpText("Uninfected host cell density" );
      paramX.setCurrentValue(20.0 );
      paramY.setParameterName("<i>y</i>(0)" );
      paramY.setMaxValue( 100000.0 );
      paramY.setIncrementAmount( 5.0 );
      paramY.setDefaultValue(0.1 );
      paramY.setHelpText("Infected host cell density" );
      paramY.setCurrentValue(0.1 );
      paramW.setParameterName("<i>w</i>(0)" );
      paramW.setMaxValue( 100000.0 );
      paramW.setIncrementAmount(0.1 );
      paramW.setDefaultValue(0.0010 );
      paramW.setHelpText("CTL precursors, CTLp, density" );
      paramW.setCurrentValue(0.0010 );
      paramZ.setParameterName("<i>z</i>(0)" );
      paramZ.setMaxValue( 100000.0 );
      paramZ.setIncrementAmount( 5.0 );
      paramZ.setCurrentValue(1.0E-4);
      paramZ.setDefaultValue(1.0E-4);
      paramZ.setHelpText("CTL effector density" );
      paramS.setParameterName("s" );
      paramS.setMaxValue(1.0 );
      paramS.setIncrementAmount(0.01);
      paramS.setDefaultValue(0.0042);
      paramS.setHelpText("CTL precursors, CTLp, density" );
      paramS.setCurrentValue(0.0050 );
      paramP.setParameterName("p" );
      paramP.setMaxValue( 100000.0 );
      paramP.setIncrementAmount( 5.0 );
      paramP.setDefaultValue(1.0 );
      paramP.setHelpText("CTL precursors, CTLp, density" );
      paramP.setCurrentValue(1.0 );
      hostDensitiesPanel.setLayout( gridBagLayout2 );
      hostDensitiesPanel.setBorder(titledBorder5 );
      hostDensitiesPanel.setToolTipText( "Initial Host Densities" );
      this.setLayout( gridBagLayout1 );
      paramB.setParameterName("b" );
      paramB.setMaxValue(200 );
      paramB.setDefaultValue(0.01 );
      paramB.setHelpText("rate of free virions production by infected cells" );
      paramB.setIncrementAmount(0.1);
      paramB.setCurrentValue(0.01 );
      paramA.setCurrentValue(0.2 );
      paramA.setDefaultValue(0.2 );
      paramA.setHelpText("Infected cells death rate" );
      paramA.setIncrementAmount( 0.1 );
      paramA.setMaxValue( 1.0 );
      paramA.setParameterName("a" );
      titledBorder9.setTitle( "Host Densities" );
      runningTimePanel1.setDefaultTime(500.0 );
      paramlambda.setCurrentValue(1.0);
      paramlambda.setHelpText("rate of uninfected cell production");
      paramlambda.setDefaultValue(1.0);
      paramlambda.setIncrementAmount(0.1);
      paramlambda.setMaxValue(1.0);
      paramlambda.setParameterName("\u03BB");
      paramd.setCurrentValue(0.1);
      paramd.setHelpText("rate of unifected cell death");
      paramd.setDefaultValue(0.1);
      paramd.setIncrementAmount(0.1);
      paramd.setMaxValue(1.0);
      paramd.setParameterName("d");
      paramC.setCurrentValue(0.1 );
      paramC.setDefaultValue(0.1 );
      paramC.setIncrementAmount( 0.1 );
      paramC.setMaxValue( 1.0 );
      paramC.setParameterName("c" );
      paramQ.setCurrentValue(0.5 );
      paramQ.setDefaultValue(0.5 );
      paramQ.setIncrementAmount( 0.1 );
      paramQ.setMaxValue( 1.0 );
      paramQ.setParameterName("q" );
      paramH.setCurrentValue(0.1 );
      paramH.setDefaultValue(0.1 );
      paramH.setIncrementAmount( 0.1 );
      paramH.setMaxValue( 1.0 );
      paramH.setParameterName("h" );

      vvstButton.setSelected(true);

      this.add( modelParametersPanel,      new GridBagConstraints(0, 0, 2, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostDensitiesPanel.add( paramX,  new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostDensitiesPanel.add( paramY,  new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostDensitiesPanel.add( paramW,  new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostDensitiesPanel.add( paramZ,  new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );


      modelParametersPanel.add( hostRatesPanel,        new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      modelParametersPanel.add( hostRatesPanel,  new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( paramB,           new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( parambeta,            new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( paramP,             new GridBagConstraints(1, 4, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( paramA,           new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( paramQ,           new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      hostRatesPanel.add( paramS,           new GridBagConstraints(1, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0) );
      this.add( plotTypePanel,         new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      plotTypePanel.add(vvstButton, null);
      plotTypePanel.add( xyvtButton, null );
      plotTypePanel.add( xyvstButton, null );
      //plotTypePanel.add(xvsyvsvButton, null );
      this.add( runningTimePanel1,      new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
      this.add(intervalArray,        new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));
      bg2.add( this.xyvtButton );
      bg2.add( this.xyvstButton );
      bg2.add( this.vvstButton );
      //bg2.add(this.xvsyvsvButton );
      hostRatesPanel.add(paramlambda,        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      hostRatesPanel.add(paramd,        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      hostRatesPanel.add(paramC,        new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      hostRatesPanel.add(paramH,        new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
      modelParametersPanel.add(hostDensitiesPanel,              new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

      registerChildren( this );
   }



/*
public int getTriggers() {
return 0;
}
*/
}
