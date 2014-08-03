package edu.umn.ecology.populus.model.pp;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import javax.swing.border.*;
import java.util.*;

public class PPPanel extends BasicPlotInputPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 4286136553156042563L;
ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.pp.Res" );
   JPanel initialConditionsPanel = new JPanel();
   Border border1;
   PopulusParameterField mPF = new PopulusParameterField();
   PopulusParameterField p0PF = new PopulusParameterField();
   PopulusParameterField lambdaPPF = new PopulusParameterField();
   JRadioButton xypvstButton = new StyledRadioButton();
   PopulusParameterField alphaPF = new PopulusParameterField();
   TitledBorder titledBorder1;
   PopulusParameterField aYPF = new PopulusParameterField();
   SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
   GridBagLayout gridBagLayout2 = new GridBagLayout();
   Border border2;
   PopulusParameterField gPPF = new PopulusParameterField();
   TitledBorder titledBorder2;
   PopulusParameterField x0PF = new PopulusParameterField();
   Border border3;
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   TitledBorder titledBorder3;
   JPanel modelTypePanel = new JPanel();
   Border border4;
   PopulusParameterField gensPF = new PopulusParameterField();
   TitledBorder titledBorder4;
   JPanel jPanel1 = new JPanel();
   PopulusParameterField y0PF = new PopulusParameterField();
   JPanel plotTypePanel = new JPanel();
   PopulusParameterField gPF = new PopulusParameterField();
   JRadioButton vsButton = new StyledRadioButton();
   PopulusParameterField lambdaPF = new PopulusParameterField();
   PopulusParameterField betaPF = new PopulusParameterField();
   JRadioButton randButton = new JRadioButton();
   GridBagLayout gridBagLayout3 = new GridBagLayout();
   JRadioButton switchButton = new JRadioButton();
   PopulusParameterField sPF = new PopulusParameterField();
   ButtonGroup bg = new ButtonGroup();
   ButtonGroup vs = new ButtonGroup();
   PopulusParameterField aXPF = new PopulusParameterField();

   public BasicPlot getPlotParamInfo() {
      if( vsButton.isSelected() ) {
         return new PP3DParamInfo( x0PF.getDouble(), y0PF.getDouble(), p0PF.getDouble(), switchButton.isSelected(), new double[] {
            lambdaPF.getDouble(), lambdaPPF.getDouble()
         }, new double[] {
            aXPF.getDouble(), aYPF.getDouble()
         }, new double[] {
            gPF.getDouble(), gPPF.getDouble()
         }, new double[] {
            alphaPF.getDouble(), betaPF.getDouble()
         }, mPF.getDouble(), sPF.getDouble(), gensPF.getInt(), xypvstButton.isSelected() );
      }
      return new PPParamInfo( x0PF.getDouble(), y0PF.getDouble(), p0PF.getDouble(), switchButton.isSelected(), new double[] {
         lambdaPF.getDouble(), lambdaPPF.getDouble()
      }, new double[] {
         aXPF.getDouble(), aYPF.getDouble()
      }, new double[] {
         gPF.getDouble(), gPPF.getDouble()
      }, new double[] {
         alphaPF.getDouble(), betaPF.getDouble()
      }, mPF.getDouble(), sPF.getDouble(), gensPF.getInt(), xypvstButton.isSelected() );
   }

   public PPPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public String getOutputGraphName() {

      /*
      if (clinearButton.isSelected()) {
      return "Continuous Exponential Growth r=" + bPF.getDouble();
      } else {
      return "Discrete Geometric Growth \u03BB=" + lambdaPF.getDouble();
      }
      */
      return res.getString( "Discrete_Predator" );
   }

   /** Changes enablement of components */

   public void actionPerformed( ActionEvent e ) {
      if( e.getSource() instanceof JRadioButton ) {
         sPF.setVisible( switchButton.isSelected() );
      }
      this.fireModelPanelEvent( ModelPanelEvent.CHANGE_PLOT );
   }

   protected boolean isContinuous() {
      return randButton.isSelected();
   }

   private void jbInit() throws Exception {
      border1 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder1 = new TitledBorder( border1, res.getString( "Model_Type" ) );
      border2 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder2 = new TitledBorder( border2, res.getString( "Model_Parameters" ) );
      border3 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder3 = new TitledBorder( border3, res.getString( "Initial_Conditions" ) );
      border4 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
      titledBorder4 = new TitledBorder( border4, res.getString( "Plot_Type" ) );
      modelTypePanel.setBorder( titledBorder1 );
      modelTypePanel.setLayout( simpleVFlowLayout1 );
      initialConditionsPanel.setBorder( titledBorder3 );
      initialConditionsPanel.setLayout( gridBagLayout2 );
      y0PF.setCurrentValue( 25.0 );
      y0PF.setDefaultValue( 25.0 );
      y0PF.setHelpText( res.getString( "Initial_Population" ) );
      y0PF.setIncrementAmount( 1.0 );
      y0PF.setMaxValue( 999.0 );
      y0PF.setMinValue( 1.0 );
      y0PF.setParameterName( res.getString( "Prey_2_Size_i_Y_i_sub" ) );
      randButton.setSelected( true );
      randButton.setText( res.getString( "Random" ) );
      randButton.setFocusPainted( false );
      randButton.setActionCommand( res.getString( "continuous" ) );
      switchButton.setText( res.getString( "Switching" ) );
      switchButton.setFocusPainted( false );
      switchButton.setActionCommand( res.getString( "discrete" ) );
      lambdaPF.setCurrentValue( 3.0 );
      lambdaPF.setDefaultValue( 3.0 );
      lambdaPF.setIncrementAmount( 0.1 );
      lambdaPF.setMaxValue( 5.0 );
      lambdaPF.setParameterName( "\u03BB" ); //note </> since italic lambda is usually undefined.
      lambdaPF.setHelpText("The growth rate of prey X.");
      gPF.setCurrentValue( 0.01 );
      gPF.setDefaultValue( 0.01 );
      gPF.setIncrementAmount( 0.1 );
      gPF.setMaxValue( 1.0 );
      gPF.setMinValue( 0 );
      gPF.setParameterName( "<i>g</i>" );
      gPF.setHelpText("g modulates the overall intensity of density-dependent feedback.");
      this.setLayout( gridBagLayout1 );
      p0PF.setParameterName( res.getString( "Predator_Size_i_P_i" ) );
      p0PF.setMinValue( 1.0 );
      p0PF.setMaxValue( 999.0 );
      p0PF.setIncrementAmount( 1.0 );
      p0PF.setHelpText( res.getString( "Initial_Population" ) );
      p0PF.setDefaultValue( 10.0 );
      p0PF.setCurrentValue( 10.0 );
      aXPF.setCurrentValue( 0.05 );
      aXPF.setDefaultValue( 0.05 );
      aXPF.setIncrementAmount( 0.1 );
      aXPF.setMaxValue( 1.0 );
      aXPF.setParameterName( "<i>aX</i>" );
      aXPF.setHelpText("The area of discovery for prey X.");
      aYPF.setCurrentValue( 0.08 );
      aYPF.setDefaultValue( 0.08 );
      aYPF.setIncrementAmount( 0.1 );
      aYPF.setMaxValue( 1.0 );
      aYPF.setParameterName( "<i>aY</i>" );
      aYPF.setHelpText("The area of discovery for prey Y.");
      gensPF.setCurrentValue( 25.0 );
      gensPF.setDefaultValue( 25.0 );
      gensPF.setHelpText( res.getString( "Number_of_generations" ) );
      gensPF.setIncrementAmount( 5.0 );
      gensPF.setMaxValue( 999.0 );
      gensPF.setMinValue( 1.0 );
      gensPF.setParameterName( res.getString( "Generations_" ) );
      lambdaPPF.setCurrentValue( 4.0 );
      lambdaPPF.setDefaultValue( 4.0 );
      lambdaPPF.setIncrementAmount( 0.1 );
      lambdaPPF.setMaxValue( 5.0 );
      lambdaPPF.setParameterName( "\u03BB'" );
      lambdaPPF.setHelpText("The growth rate of prey Y.");
      jPanel1.setLayout( gridBagLayout3 );
      vsButton.setToolTipText( "" );
      vsButton.setText( "<i>P</i>  vs <i>X</i>  vs <i>Y</i>" );
      vsButton.setFocusPainted( false );
      xypvstButton.setSelected( true );
      xypvstButton.setText( "<i>X</i>, <i>Y</i>, <i>P</i>  vs <i>t</i>" );
      xypvstButton.setFocusPainted( false );
      plotTypePanel.setBorder( titledBorder4 );
      gPPF.setParameterName( "<i>g\'</i>" );
      gPPF.setMinValue( 0.0 );
      gPPF.setMaxValue( 1.0 );
      gPPF.setIncrementAmount( 0.1 );
      gPPF.setDefaultValue( 0.01 );
      gPPF.setCurrentValue( 0.01 );
      gPPF.setHelpText("g' modulates the overall intensity of density-dependent feedback.");
      betaPF.setParameterName( "\u03b2" );
      betaPF.setMinValue( 0.0 );
      betaPF.setMaxValue( 100.0 );
      betaPF.setIncrementAmount( 0.1 );
      betaPF.setDefaultValue( 0.7 );
      betaPF.setCurrentValue( 0.7 );
      betaPF.setHelpText("The competition coefficient that scales the density-dependent interspecific competitive feedback.");
      alphaPF.setParameterName( "\u03b1" );
      alphaPF.setMinValue( 0.0 );
      alphaPF.setMaxValue( 100.0 );
      alphaPF.setDefaultValue( 0.6 );
      alphaPF.setCurrentValue( 0.6 );
      alphaPF.setHelpText("The competition coefficient that scales the density-dependent interspecific competitive feedback.");
      x0PF.setParameterName( res.getString( "Prey_1_Size_i_X_i_sub" ) );
      x0PF.setMinValue( 1.0 );
      x0PF.setMaxValue( 999.0 );
      x0PF.setIncrementAmount( 1.0 );
      x0PF.setHelpText( res.getString( "Initial_Population" ) );
      x0PF.setDefaultValue( 25.0 );
      x0PF.setCurrentValue( 25.0 );
      sPF.setCurrentValue( 1.0 );
      sPF.setDefaultValue( 1.0 );
      sPF.setIncrementAmount( 0.1 );
      sPF.setMaxValue( 1.0 );
      sPF.setMinValue( 0.0 );
      sPF.setParameterName( "<i>s</i>" );
      sPF.setHelpText("The switching constant.");
      sPF.setVisible( false );
      mPF.setCurrentValue( 0.2 );
      mPF.setDefaultValue( 0.2 );
      mPF.setIncrementAmount( 0.1 );
      mPF.setMaxValue( 1.0 );
      mPF.setMinValue( 0.0 );
      mPF.setParameterName( "<i>m</i>" );
      mPF.setHelpText("The predator interference constant");
      this.add( modelTypePanel, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      modelTypePanel.add( randButton, null );
      modelTypePanel.add( switchButton, null );
      this.add( initialConditionsPanel, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 0, 0 ), 0, 0 ) );
      initialConditionsPanel.add( y0PF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      initialConditionsPanel.add( p0PF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 2, 0 ), 0, 0 ) );
      initialConditionsPanel.add( x0PF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      this.add( gensPF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      this.add( jPanel1, new GridBagConstraints( 1, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets( 10, 0, 0, 0 ), 0, 0 ) );
      jPanel1.add( lambdaPF, new GridBagConstraints( 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      jPanel1.add( lambdaPPF, new GridBagConstraints( 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 10, 5, 0 ), 0, 0 ) );
      jPanel1.add( aXPF, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      jPanel1.add( aYPF, new GridBagConstraints( 1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      jPanel1.add( gPF, new GridBagConstraints( 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      jPanel1.add( gPPF, new GridBagConstraints( 1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 5, 0 ), 0, 0 ) );
      jPanel1.add( alphaPF, new GridBagConstraints( 0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      jPanel1.add( betaPF, new GridBagConstraints( 1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      jPanel1.add( sPF, new GridBagConstraints( 1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 20, 0, 0, 0 ), 0, 0 ) );
      jPanel1.add( mPF, new GridBagConstraints( 0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 20, 0, 0, 0 ), 0, 0 ) );
      this.add( plotTypePanel, new GridBagConstraints( 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets( 0, 30, 10, 0 ), 0, 0 ) );
      plotTypePanel.add( xypvstButton, null );
      plotTypePanel.add( vsButton, null );
      bg.add( switchButton );
      bg.add( randButton );
      vs.add( xypvstButton );
      vs.add( vsButton );
      this.registerChildren( this );
   }

/*
public int getTriggers() {
return 0;
}
//*/
}
