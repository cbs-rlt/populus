package edu.umn.ecology.populus.model.dig;
import java.awt.*;
import java.awt.event.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.visual.*;
import edu.umn.ecology.populus.visual.ppfield.*;
import edu.umn.ecology.populus.edwin.*;
import javax.swing.*;
import edu.umn.ecology.populus.visual.SimpleVFlowLayout;
import javax.swing.border.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import java.util.*;

/**
 * Density independent growth panel
 * If Unicode is supported use "\u03BB" instead of "lambda"
 *
 *
 */

public class DIGPanel extends BasicPlotInputPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4120490983905219058L;
	public static final int kDNDTVSN = 2;
	public static final int kLNNVST = 1;
	public static final int kNVST = 0;
	public static final int kDNNDTVSN = 3;
	GridBagLayout gridBagLayout5 = new GridBagLayout();
	GridBagLayout gridBagLayout4 = new GridBagLayout();
	Box box18;
	JCheckBox three = new JCheckBox();
	PopulusParameterField rPF3 = new PopulusParameterField();
	SimpleVFlowLayout simpleVFlowLayout7 = new SimpleVFlowLayout();
	PopulusParameterField n0PF2 = new PopulusParameterField();
	JPanel graph3 = new JPanel();
	Box box17;
	Box box6;
	GridBagLayout gridBagLayout7 = new GridBagLayout();
	PopulusParameterField n0PF3 = new PopulusParameterField();
	JPanel jPanel1 = new JPanel();
	PopulusParameterField n0PF1 = new PopulusParameterField();
	JCheckBox one = new JCheckBox();
	Box box4;
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	PopulusParameterField lambdaPF2 = new PopulusParameterField();
	GridBagLayout gridBagLayout6 = new GridBagLayout();
	JPanel modelParametersPanel2 = new JPanel();
	SimpleVFlowLayout simpleVFlowLayout5 = new SimpleVFlowLayout();
	DIGData[] myData;
	PopulusParameterField rPF1 = new PopulusParameterField();
	JPanel modelTypePanel = new JPanel();
	PopulusParameterField lambdaPF = new PopulusParameterField();
	Border border1;
	JPanel modelParametersPanel4 = new JPanel();
	TitledBorder titledBorder1;
	Box box12;
	SimpleVFlowLayout simpleVFlowLayout1 = new SimpleVFlowLayout();
	Box box14;
	Border border2;
	Box box16;
	TitledBorder titledBorder2;
	JPanel graph4 = new JPanel();
	Border border3;
	JCheckBox two = new JCheckBox();
	TitledBorder titledBorder3;
	JCheckBox four = new JCheckBox();
	PopulusParameterField gensPF = new PopulusParameterField();
	PopulusParameterField n0PF = new PopulusParameterField();
	JRadioButton continuousButton = new JRadioButton();
	SimpleVFlowLayout simpleVFlowLayout4 = new SimpleVFlowLayout();
	JRadioButton discreteButton = new JRadioButton();
	JPanel modelParametersPanel1 = new JPanel();
	ButtonGroup bg = new ButtonGroup();
	PopulusParameterField rPF2 = new PopulusParameterField();
	ButtonGroup bgPlot = new ButtonGroup();
	Box box5;
	JPanel plotTypePanel = new JPanel();
	PopulusParameterField lambdaPF1 = new PopulusParameterField();
	SimpleVFlowLayout simpleVFlowLayout3 = new SimpleVFlowLayout();
	SimpleVFlowLayout simpleVFlowLayout6 = new SimpleVFlowLayout();
	Border border4;
	JPanel modelParametersPanel3 = new JPanel();
	Border border5;
	PopulusParameterField rPF = new PopulusParameterField();
	TitledBorder titledBorder4;
	Box box7;
	TitledBorder titledBorder5;
	JPanel initialConditionsPanel2 = new JPanel();
	StyledRadioButton dNNdtButton = new StyledRadioButton();
	Box box11;
	StyledRadioButton nvstButton = new StyledRadioButton();
	JPanel initialConditionsPanel3 = new JPanel();
	StyledRadioButton dNdtButton = new StyledRadioButton();
	Box box13;
	StyledRadioButton lnnvstButton = new StyledRadioButton();
	JPanel initialConditionsPanel4 = new JPanel();
	JTabbedPane graphs = new JTabbedPane();
	Box box15;
	JPanel graph1 = new JPanel();
	JPanel initialConditionsPanel5 = new JPanel();
	JPanel graph2 = new JPanel();
	PopulusParameterField lambdaPF3 = new PopulusParameterField();
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.dig.Res" );
	static final String CONTINUOUS_N0 = res.getString( "Pop_Size_i_N_i_0_" );

	static final String str24 = "d<i>N</i>/<i>N</i>d<i>t</i> vs <i>N</i>";
	static final String str23 = "d<i>N</i>/d<i>t</i> vs <i>N</i>";
	//static final String str14 = "(<i>N<sub>t</i> +1</sub> - <i>N<sub>t</>)/<i>N<sub>t</> vs <i>N<sub>t</>";
	static final String str14 = "ln (<i>N</i><sub><i>t</i>+1</sub> / <i>N<sub>t</sub></i>) vs <i>N<sub>t</sub></i>";
	static final String str13 = "<i>N</i><sub><i>t</i>+1</sub> - <i>N<sub>t</sub></i> vs <i>N<sub>t</sub></i>";
	static final String DISCRETE_N0 = res.getString( "Pop_Size_i_N_i_sub_0" );
	static final String str1 = "<i>N</i> vs <i>t</i>";
	static final String str2 = "ln (<i>N</i>) vs <i>t</i>";
	private GridBagLayout gridBagLayout2 = new GridBagLayout();


	public BasicPlot getPlotParamInfo() {
		int selection = nvstButton.isSelected() ? kNVST : ( lnnvstButton.isSelected() ? kLNNVST : ( dNdtButton.isSelected() ? kDNDTVSN : kDNNDTVSN ) );

		/* this has uses for examples as i make other multiple graphs
      return new DIGParamInfo(continuousButton.isSelected(), selection,
      gensPF.getCurrentValue(),
      lambdaPF.getCurrentValue(), n0PF.getCurrentValue(), rPF.getCurrentValue());


      myData[1] = new DIGData(continuousButton.isSelected(), selection, 30, 0.7,10, 0.15);
      myData[2] = new DIGData(continuousButton.isSelected(), selection, 30, 0.4,20, 0.22);
		 */
		myData = new DIGData[4];
		int numToGraph = 0;
		if( one.isSelected() ) {
			myData[0] = new DIGData( continuousButton.isSelected(), selection, gensPF.getCurrentValue(), lambdaPF.getCurrentValue(), n0PF.getCurrentValue(), rPF.getCurrentValue() );
			numToGraph++;
		} else {
			myData[0] = null;
		}
		if( two.isSelected() ) {
			myData[1] = new DIGData( continuousButton.isSelected(), selection, gensPF.getCurrentValue(), lambdaPF1.getCurrentValue(), n0PF1.getCurrentValue(), rPF1.getCurrentValue() );
			numToGraph++;
		} else {
			myData[1] = null;
		}
		if( three.isSelected() ) {
			myData[2] = new DIGData( continuousButton.isSelected(), selection, gensPF.getCurrentValue(), lambdaPF2.getCurrentValue(), n0PF2.getCurrentValue(), rPF2.getCurrentValue() );
			numToGraph++;
		} else {
			myData[2] = null;
		}
		if( four.isSelected() ) {
			myData[3] = new DIGData( continuousButton.isSelected(), selection, gensPF.getCurrentValue(), lambdaPF3.getCurrentValue(), n0PF3.getCurrentValue(), rPF3.getCurrentValue() );
			numToGraph++;
		} else {
			myData[3] = null;
		}
		return new DIGParamInfo( myData, numToGraph );

		//*/
	}

	public DIGPanel() {
		try {
			jbInit();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public String getOutputGraphName() {
		return res.getString( "Density_Independent" );

		/*
   if (continuousButton.isSelected()) {
   return "Continuous Exponential Growth r=" + rPF.getDouble();
   } else {
   return "Discrete Geometric Growth \u03BB=" + lambdaPF.getDouble();
   }
		 */
	}

	/*
   protected boolean isContinuous() {
   return continuousButton.isSelected();
   }
	 */

	/*
   public int getTriggers() {
   return 0;
   }
	 */

	public void updateLabels() {
		if( continuousButton.isSelected() ) {
			graphs.setForegroundAt( 0, ColorScheme.colors[0] );
			graphs.setForegroundAt( 1, ColorScheme.colors[1] );
			graphs.setForegroundAt( 2, ColorScheme.colors[2] );
			graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		}
		else {
			graphs.setForegroundAt( 0, ColorScheme.colors[0] );
			graphs.setForegroundAt( 1, ColorScheme.colors[1] );
			graphs.setForegroundAt( 2, ColorScheme.colors[2] );
			graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		}
	}

	/** Changes enablement of components */

	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() instanceof JRadioButton ) {
			if( e.getSource() == continuousButton || e.getSource() == discreteButton ) {
				boolean cE = continuousButton.isSelected();
				rPF3.setEnabled( cE );
				lambdaPF3.setEnabled( !cE );
				rPF2.setEnabled( cE );
				lambdaPF2.setEnabled( !cE );
				rPF1.setEnabled( cE );
				lambdaPF1.setEnabled( !cE );
				rPF.setEnabled( cE );
				lambdaPF.setEnabled( !cE );
				if( cE ) {
					rPF3.setCurrentValue( Math.log( lambdaPF3.getCurrentValue() ) );
					rPF2.setCurrentValue( Math.log( lambdaPF2.getCurrentValue() ) );
					rPF1.setCurrentValue( Math.log( lambdaPF1.getCurrentValue() ) );
					rPF.setCurrentValue( Math.log( lambdaPF.getCurrentValue() ) );
					dNdtButton.setText( str23 );
					dNNdtButton.setText( str24 );
					n0PF3.setParameterName( CONTINUOUS_N0 );
					n0PF2.setParameterName( CONTINUOUS_N0 );
					n0PF1.setParameterName( CONTINUOUS_N0 );
					n0PF.setParameterName( CONTINUOUS_N0 );
				}
				else {
					lambdaPF3.setCurrentValue( Math.exp( rPF3.getCurrentValue() ) );
					lambdaPF2.setCurrentValue( Math.exp( rPF2.getCurrentValue() ) );
					lambdaPF1.setCurrentValue( Math.exp( rPF1.getCurrentValue() ) );
					lambdaPF.setCurrentValue( Math.exp( rPF.getCurrentValue() ) );
					dNdtButton.setText( str13 );
					dNNdtButton.setText( str14 );
					n0PF3.setParameterName( DISCRETE_N0 );
					n0PF2.setParameterName( DISCRETE_N0 );
					n0PF1.setParameterName( DISCRETE_N0 );
					n0PF.setParameterName( DISCRETE_N0 );
				}
				this.repack();
			}
		}
		updateLabels();
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
		titledBorder4 = new TitledBorder( border4, res.getString( "Plot_Type" ) );
		border5 = BorderFactory.createLineBorder( SystemColor.controlText, 1 );
		titledBorder5 = new TitledBorder( border4, res.getString( "Which_to_Graph" ) );
		box4 = Box.createVerticalBox();
		box5 = Box.createVerticalBox();
		box6 = Box.createVerticalBox();
		box7 = Box.createVerticalBox();
		box11 = Box.createVerticalBox();
		box12 = Box.createHorizontalBox();
		box13 = Box.createVerticalBox();
		box14 = Box.createHorizontalBox();
		box15 = Box.createVerticalBox();
		box16 = Box.createHorizontalBox();
		box17 = Box.createVerticalBox();
		box18 = Box.createHorizontalBox();
		modelTypePanel.setBorder( titledBorder1 );
		modelTypePanel.setLayout( simpleVFlowLayout1 );
		continuousButton.setSelected( true );
		continuousButton.setText( res.getString( "Continuous" ) );
		continuousButton.setFocusPainted( false );
		discreteButton.setText( res.getString( "Discrete" ) );
		discreteButton.setFocusPainted( false );

		//note </> since italic lambda is usually undefined.
		gensPF.setCurrentValue( 20.0 );
		gensPF.setDefaultValue( 20.0 );
		gensPF.setHelpText( res.getString( "Number_of_generations" ) );
		gensPF.setIncrementAmount( 5.0 );
		gensPF.setMaxValue( 999.0 );
		gensPF.setMinValue( 1.0 );
		gensPF.setParameterName( res.getString( "Run_Time" ) );
		plotTypePanel.setLayout( simpleVFlowLayout3 );
		plotTypePanel.setBorder( titledBorder4 );
		dNNdtButton.setText( str24 );
		nvstButton.setSelected( true );
		nvstButton.setText( str1 );
		dNdtButton.setText( str23 );
		lnnvstButton.setText( str2 );
		graphs.setMinimumSize( new Dimension( 20, 20 ) );
		this.setLayout( gridBagLayout1 );
		lambdaPF3.setParameterName( "\u03BB" );
		lambdaPF3.setMaxValue( 10.0 );
		lambdaPF3.setIncrementAmount( 0.1 );
		lambdaPF3.setHelpText( res.getString( "Rate_of_population" ) + res.getString( "the_npopulation_at" ) );
		lambdaPF3.setEnabled( false );
		lambdaPF3.setDefaultValue( 1.1 );
		lambdaPF3.setCurrentValue( 1.1 );
		rPF3.setParameterName( "<i>r </i>" );
		rPF3.setMinValue( -1.0 );
		rPF3.setMaxValue( 1.0 );
		rPF3.setIncrementAmount( 0.05 );
		rPF3.setHelpText( res.getString( "Rate_of_population1" ) + res.getString( "is_e_r_t_initial" ) );
		rPF3.setDefaultValue( 0.1 );
		rPF3.setCurrentValue( 0.1 );
		modelParametersPanel1.setLayout( simpleVFlowLayout4 );
		modelParametersPanel1.setBorder( titledBorder2 );
		lambdaPF2.setParameterName( "\u03BB" );
		lambdaPF2.setMaxValue( 10.0 );
		lambdaPF2.setIncrementAmount( 0.1 );
		lambdaPF2.setHelpText( res.getString( "Rate_of_population2" ) + res.getString( "the_npopulation_at1" ) );
		lambdaPF2.setEnabled( false );
		lambdaPF2.setDefaultValue( 1.1 );
		lambdaPF2.setCurrentValue( 1.1 );
		rPF2.setParameterName( "<i>r </i>" );
		rPF2.setMinValue( -1.0 );
		rPF2.setMaxValue( 1.0 );
		rPF2.setIncrementAmount( 0.05 );
		rPF2.setHelpText( res.getString( "Rate_of_population3" ) + res.getString( "is_e_r_t_initial" ) );
		rPF2.setDefaultValue( 0.1 );
		rPF2.setCurrentValue( 0.1 );
		modelParametersPanel2.setLayout( simpleVFlowLayout5 );
		modelParametersPanel2.setBorder( titledBorder2 );
		lambdaPF1.setParameterName( "\u03BB" );
		lambdaPF1.setMaxValue( 10.0 );
		lambdaPF1.setIncrementAmount( 0.1 );
		lambdaPF1.setHelpText( res.getString( "Rate_of_population4" ) + res.getString( "the_npopulation_at2" ) );
		lambdaPF1.setEnabled( false );
		lambdaPF1.setDefaultValue( 1.1 );
		lambdaPF1.setCurrentValue( 1.1 );
		rPF1.setParameterName( "<i>r </i>" );
		rPF1.setMinValue( -1.0 );
		rPF1.setMaxValue( 1.0 );
		rPF1.setIncrementAmount( 0.05 );
		rPF1.setHelpText( res.getString( "Rate_of_population5" ) + res.getString( "is_e_r_t_initial" ) );
		rPF1.setDefaultValue( 0.1 );
		rPF1.setCurrentValue( 0.1 );
		modelParametersPanel3.setLayout( simpleVFlowLayout6 );
		modelParametersPanel3.setBorder( titledBorder2 );
		lambdaPF.setParameterName( "\u03BB" );
		lambdaPF.setMaxValue( 10.0 );
		lambdaPF.setIncrementAmount( 0.1 );
		lambdaPF.setHelpText( res.getString( "Rate_of_population6" ) + res.getString( "the_npopulation_at3" ) );
		lambdaPF.setEnabled( false );
		lambdaPF.setDefaultValue( 1.1 );
		lambdaPF.setCurrentValue( 1.1 );
		rPF.setParameterName( "<i>r </i>" );
		rPF.setMinValue( -5.0 );
		rPF.setMaxValue( 5.0 );
		rPF.setIncrementAmount( 0.05 );
		rPF.setHelpText( res.getString( "Rate_of_population7" ) + "is e^(r * t) * initial population" );
		rPF.setDefaultValue( 0.1 );
		rPF.setCurrentValue( 0.1 );
		modelParametersPanel4.setLayout( simpleVFlowLayout7 );
		modelParametersPanel4.setBorder( titledBorder2 );
		initialConditionsPanel2.setBorder( titledBorder3 );
		n0PF3.setCurrentValue( 10.0 );
		n0PF3.setDefaultValue( 10.0 );
		n0PF3.setHelpText( res.getString( "Initial_Population" ) );
		n0PF3.setIncrementAmount( 1.0 );
		n0PF3.setMaxValue( 999.0 );
		n0PF3.setMinValue( 1.0 );
		n0PF3.setParameterName( CONTINUOUS_N0 );
		initialConditionsPanel3.setBorder( titledBorder3 );
		n0PF2.setCurrentValue( 10.0 );
		n0PF2.setDefaultValue( 10.0 );
		n0PF2.setHelpText( res.getString( "Initial_Population" ) );
		n0PF2.setIncrementAmount( 1.0 );
		n0PF2.setMaxValue( 999.0 );
		n0PF2.setMinValue( 1.0 );
		n0PF2.setParameterName( CONTINUOUS_N0 );
		initialConditionsPanel4.setBorder( titledBorder3 );
		n0PF1.setCurrentValue( 10.0 );
		n0PF1.setDefaultValue( 10.0 );
		n0PF1.setHelpText( res.getString( "Initial_Population" ) );
		n0PF1.setIncrementAmount( 1.0 );
		n0PF1.setMaxValue( 999.0 );
		n0PF1.setMinValue( 1.0 );
		n0PF1.setParameterName( CONTINUOUS_N0 );
		initialConditionsPanel5.setBorder( titledBorder3 );
		n0PF.setCurrentValue( 10.0 );
		n0PF.setDefaultValue( 10.0 );
		n0PF.setHelpText( res.getString( "Initial_Population" ) );
		n0PF.setIncrementAmount( 1.0 );
		n0PF.setMaxValue(1000000.0 );
		n0PF.setMinValue( 1.0 );
		n0PF.setParameterName( CONTINUOUS_N0 );
		graph1.setLayout( gridBagLayout4 );
		graph2.setLayout( gridBagLayout5 );
		graph3.setLayout( gridBagLayout6 );
		graph4.setLayout( gridBagLayout7 );
		one.setToolTipText( res.getString( "Graph_Model_A" ) );
		one.setSelected( true );
		two.setToolTipText( res.getString( "Overlay_Model_B" ) );
		three.setToolTipText( res.getString( "Overlay_Model_C" ) );
		four.setToolTipText( res.getString( "Overlay_Model_D" ) );
		jPanel1.setLayout(gridBagLayout2 );
		this.add( graphs,             new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
		graph1.setForeground( Color.blue );
		graphs.add( graph1, "  A  " );
		graph1.add( box18, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box18.add( box17, null );
		box17.add( initialConditionsPanel5, null );
		initialConditionsPanel5.add( n0PF, null );
		graph1.add( box7, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph1.add( one, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box7.add( modelParametersPanel4, null );
		modelParametersPanel4.add( lambdaPF, null );
		modelParametersPanel4.add( rPF, null );
		graphs.add( graph2, "  B  " );
		graph2.add( box16, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box16.add( box15, null );
		box15.add( initialConditionsPanel4, null );
		initialConditionsPanel4.add( n0PF1, null );
		graph2.add( box6, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph2.add( two, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box6.add( modelParametersPanel3, null );
		modelParametersPanel3.add( lambdaPF1, null );
		modelParametersPanel3.add( rPF1, null );
		graphs.add( graph3, "  C  " );
		graph3.add( box14, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box14.add( box13, null );
		box13.add( initialConditionsPanel3, null );
		initialConditionsPanel3.add( n0PF2, null );
		graph3.add( box5, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph3.add( three, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box5.add( modelParametersPanel2, null );
		modelParametersPanel2.add( lambdaPF2, null );
		modelParametersPanel2.add( rPF2, null );
		graphs.add( graph4, "  D  " );
		graph4.add( box12, new GridBagConstraints( 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		box12.add( box11, null );
		box11.add( initialConditionsPanel2, null );
		initialConditionsPanel2.add( n0PF3, null );
		graph4.add( box4, new GridBagConstraints( 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graph4.add( four, new GridBagConstraints( 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
		graphs.setForegroundAt( 0, ColorScheme.colors[0] );
		graphs.setForegroundAt( 1, ColorScheme.colors[1] );
		graphs.setForegroundAt( 2, ColorScheme.colors[2] );
		graphs.setForegroundAt( 3, ColorScheme.colors[3] );
		box4.add( modelParametersPanel1, null );
		modelParametersPanel1.add( lambdaPF3, null );
		modelParametersPanel1.add( rPF3, null );
		this.add( gensPF,    new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0) );
		this.add( jPanel1,         new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0) );
		jPanel1.add( modelTypePanel,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0) );
		modelTypePanel.add( continuousButton, null );
		modelTypePanel.add( discreteButton, null );
		jPanel1.add( plotTypePanel,     new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
				,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0) );
		plotTypePanel.add( nvstButton, null );
		plotTypePanel.add( lnnvstButton, null );
		plotTypePanel.add( dNdtButton, null );
		plotTypePanel.add( dNNdtButton, null );
		bg.add( discreteButton );
		bg.add( continuousButton );
		bgPlot.add( nvstButton );
		bgPlot.add( lnnvstButton );
		bgPlot.add( dNdtButton );
		bgPlot.add( dNNdtButton );
		this.registerChildren( this );

		//pack?
	}
}
