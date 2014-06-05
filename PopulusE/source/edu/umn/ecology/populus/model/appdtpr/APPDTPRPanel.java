package edu.umn.ecology.populus.model.appdtpr;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import javax.swing.border.*;
import java.util.*;

public class APPDTPRPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 6188249316261413210L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.appdtpr.Res" );
   Border border1;
   TitledBorder titledBorder1;
   PopulusParameterField aPF = new PopulusParameterField();
   JPanel plotTypePanel = new JPanel();
   JRadioButton npvstButton = new StyledRadioButton();
   PopulusParameterField gensPF = new PopulusParameterField();
   JPanel initialConditionsPanel = new JPanel();
   Border border2;
   TitledBorder titledBorder4;
   TitledBorder titledBorder2;
   VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
   Border border3;
   JPanel paramsPanel = new JPanel();
   TitledBorder titledBorder3;
   PopulusParameterField cPF = new PopulusParameterField();
   PopulusParameterField n0PF = new PopulusParameterField();
   PopulusParameterField rPF = new PopulusParameterField();
   PopulusParameterField KPF = new PopulusParameterField();
   Border border4;
   ButtonGroup bg = new ButtonGroup();
   JRadioButton pvsnButton = new StyledRadioButton();
   ButtonGroup plotGroup = new ButtonGroup();
   VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   PopulusParameterField p0PF = new PopulusParameterField();
   PopulusParameterField bPF = new PopulusParameterField();

   public BasicPlot getPlotParamInfo() {
      return new APPDTPRParamInfo( n0PF.getDouble(), p0PF.getDouble(), KPF.getDouble(), rPF.getDouble(), aPF.getDouble(), cPF.getDouble(), bPF.getDouble(), gensPF.getInt(), npvstButton.isSelected() );
   }

   public APPDTPRPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public String getOutputGraphName() {

      //return "Threshold Predator Reproduction";
      return res.getString( "Discrete_Predator" );
   }

   public void actionPerformed( ActionEvent e ) {
      this.fireModelPanelEvent( ModelPanelEvent.CHANGE_PLOT );
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border4, res.getString( "Axes" ) );
      initialConditionsPanel.setBorder( titledBorder3 );
      initialConditionsPanel.setLayout( verticalFlowLayout2 );
      n0PF.setCurrentValue( 25.0 );
      n0PF.setDefaultValue( 25.0 );
      n0PF.setHelpText( res.getString( "Initial_Population" ) );
      n0PF.setIncrementAmount( 1.0 );
      n0PF.setMaxValue( 999.0 );
      n0PF.setMinValue( 1.0 );
      n0PF.setParameterName( res.getString( "Prey_Size_i_N_i_sub_0" ) );
      KPF.setCurrentValue( 50.0 );
      KPF.setDefaultValue( 50.0 );

      //lambdaPF.setHelpText("");
      KPF.setIncrementAmount( 0.1 );
      KPF.setMaxValue( 999.0 );
      KPF.setParameterName( res.getString( "K" ) );
      KPF.setHelpText("The prey carrying capacity");
      //aDIPF.setHelpText("");
      this.setLayout( gridBagLayout1 );
      p0PF.setParameterName("Predator Size (<i>P</i><sub>0</sub>)" );
      p0PF.setMinValue( 1.0 );
      p0PF.setMaxValue( 999.0 );
      p0PF.setIncrementAmount( 1.0 );
      p0PF.setHelpText( res.getString( "Initial_Population" ) );
      p0PF.setDefaultValue( 10.0 );
      p0PF.setCurrentValue( 10.0 );

      //aDDPF.setHelpText("");
      gensPF.setCurrentValue( 25.0 );
      gensPF.setDefaultValue( 25.0 );
      gensPF.setHelpText( res.getString( "Number_of_generations" ) );
      gensPF.setIncrementAmount( 5.0 );
      gensPF.setMaxValue( 999.0 );
      gensPF.setMinValue( 1.0 );
      gensPF.setParameterName( res.getString( "Generations_" ) );
      paramsPanel.setLayout( verticalFlowLayout1 );
      cPF.setCurrentValue( 1.0 );
      cPF.setDefaultValue( 1.0 );
      cPF.setMaxValue( 1000.0 );
      cPF.setParameterName( "<i>c</i>" );
      cPF.setHelpText("The parasitoid's \"numerical response.\"");
      bPF.setCurrentValue( 0.3 );
      bPF.setDefaultValue( 0.3 );
      bPF.setIncrementAmount( 0.1 );
      bPF.setMaxValue( 5.0 );
      bPF.setParameterName( "\u03b2" );
      bPF.setHelpText("The threshold prey consumption required before predators begin to reproduce.");
      rPF.setCurrentValue( 0.693 );
      rPF.setDefaultValue( 0.693 );
      rPF.setIncrementAmount( 0.1 );
      rPF.setMaxValue( 5.0 );
      rPF.setParameterName( "<i>r</i>" );
      rPF.setHelpText("The instantaneous growth rate for a prey.");
      aPF.setCurrentValue( 0.06 );
      aPF.setDefaultValue( 0.06 );
      aPF.setIncrementAmount( 0.1 );
      aPF.setMaxValue( 1.0 );
      aPF.setParameterName( "<i>a</i>" );
      aPF.setHelpText("The \"area of discovery\" of parasitoids.");
      plotTypePanel.setBorder( titledBorder4 );
      pvsnButton.setText( "<i>P</i>  vs <i>N</i>" );
      pvsnButton.setFocusPainted( false );
      npvstButton.setSelected( true );
      npvstButton.setText( "<i>N</i>, <i>P</i>  vs <i>t</i>" );
      npvstButton.setFocusPainted( false );
      titledBorder4.setTitle( res.getString( "Plot_Type" ) );
      this.setMinimumSize( new Dimension( 360, 250 ) );
      this.add( initialConditionsPanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 20, 0 ), 0, 0 ) );
      initialConditionsPanel.add( n0PF, null );
      initialConditionsPanel.add( p0PF, null );
      this.add( paramsPanel, new GridBagConstraints( 1, 0, 1, 3, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      paramsPanel.add( KPF, null );
      paramsPanel.add( rPF, null );
      paramsPanel.add( aPF, null );
      paramsPanel.add( cPF, null );
      paramsPanel.add( bPF, null );
      this.add( plotTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 20, 0 ), 0, 0 ) );
      plotTypePanel.add( npvstButton, null );
      plotTypePanel.add( pvsnButton, null );
      this.add( gensPF, new GridBagConstraints( 0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      plotGroup.add( this.npvstButton );
      plotGroup.add( this.pvsnButton );
      this.registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
*/
}
