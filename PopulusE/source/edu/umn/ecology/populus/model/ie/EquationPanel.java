package edu.umn.ecology.populus.model.ie;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;

import java.util.StringTokenizer;

public class EquationPanel extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = -5235310299666499109L;
	JPanel labelHolder = new JPanel();
   JScrollPane scroller = new JScrollPane();
   JLabel useL = new JLabel();
   JLabel numEqL = new JLabel();
   GridBagLayout gridBagLayout1 = new GridBagLayout();
   int numEqs;
   VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
   JTextField numEQTF = new JTextField();
   JLabel plotL = new JLabel();
   JPanel eqHolder = new JPanel();
   XYLayout xYLayout1 = new XYLayout();
   boolean isDiscrete = false;
   String[] paramNames;
   private JButton renameB = new JButton();

   public EquationPanel( int num, boolean isdis ) {
      this();
      this.isDiscrete = isdis;

      //dos defaults
      //*
      eqHolder.add( new Equation( false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N1(r1-a11*N1-a12*N2-c1*N3)" ), null );
      eqHolder.add( new Equation( false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N2(r2-a21*N1-a22*N2-c2*N3)" ), null );
      eqHolder.add( new Equation( false, false, isDiscrete, eqHolder.getComponentCount() + 1, 50, "N3(b(c1*N1+c2*N2)-d)" ), null );
      /*/
      //cpp defaults
      eqHolder.add(new Equation(false,false,isDiscrete,eqHolder.getComponentCount()+1,20,"r1*N1(1-N1/K)-C*N1*N2"),null);
      eqHolder.add(new Equation(false,false,isDiscrete,eqHolder.getComponentCount()+1,20,"-d2*N2+g*C*N1*N2"),null);
      //*/
      numEqs = eqHolder.getComponentCount();
      numEQTF.setText( "" + numEqs );

      paramNames = new String[numEqs];
      for(int i=0; i<paramNames.length; i++)
         paramNames[i] = "N"+(i+1);
   }

   public boolean[] getPlotted() {
      boolean[] b = new boolean[numEqs];
      for( int i = 0;i < numEqs;i++ ) {
         b[i] = ( (Equation)eqHolder.getComponent( i ) ).isPlotted();
      }
      return b;
   }

   public boolean[] getUsed() {
      boolean[] b = new boolean[numEqs];
      for( int i = 0;i < numEqs;i++ ) {
         b[i] = ( (Equation)eqHolder.getComponent( i ) ).isUsed();
      }
      return b;
   }

   /*
   the inStandard boolean queries whether the equations are requested in terms of
   N1, N2, N3 etc or in terms of user defined variables
   */
   public String[] getStrings(boolean inStandard) {
      String[] eqsStrings = new String[numEqs];
      for( int i = 0;i < numEqs;i++ ) {
         eqsStrings[i] = ( (Equation)eqHolder.getComponent( i ) ).getEquation();
      }
      if(inStandard){
         return mapEquations(eqsStrings,getParamNames(false),getParamNames(true));
      } else {
         return eqsStrings;
      }
   }

   /*
   whether we want the names to be the variables N1,N2,N3 etc, or we want them with the names
   user defined
   */
   public String[] getParamNames(boolean inStandard) {
      String[] paramNames = new String[numEqs];
      if(inStandard){
         for( int i = 0;i < numEqs;i++ ) {
            paramNames[i] = "N"+(i+1);
         }
         return paramNames;
      } else {
         for( int i = 0;i < numEqs;i++ ) {
            paramNames[i] = ( (Equation)eqHolder.getComponent( i ) ).getParameterName();
         }
         return paramNames;
      }
   }

   public EquationPanel() {
      try {
         jbInit();
      }
      catch( Exception e ) {
         e.printStackTrace();
      }
   }

   public double[] getInitialValues() {
      double[] iv = new double[numEqs];
      for( int i = 0;i < numEqs;i++ ) {
         iv[i] = ( (Equation)eqHolder.getComponent( i ) ).getInitialValue();
      }
      return iv;
   }

   void jsb_adjustmentValueChanged( AdjustmentEvent e ) {
      scroller.revalidate();
      scroller.repaint();
   }

   void setType( boolean isDiscrete ) {
      this.isDiscrete = isDiscrete;
      for( int i = 0;i < numEqs;i++ ) {
         ( (Equation)eqHolder.getComponent( i ) ).setType( isDiscrete );
      }
   }

   void numEQTF_actionPerformed( ActionEvent e ) {
      int newNumEQ = 0;
      try {
         newNumEQ = Integer.parseInt( numEQTF.getText() );
      }
      catch( NumberFormatException nfe ) {
         return ;
      }
      setUpEquations( newNumEQ );
   }

   void setUpEquations( int newNum ) {
      int i = 0;
      this.numEqs = newNum;
      while( numEqs < eqHolder.getComponentCount() ) {
         eqHolder.remove( eqHolder.getComponentCount() - 1 );
      }
      while( numEqs > eqHolder.getComponentCount() ) {
         i = eqHolder.getComponentCount() + 1;
         eqHolder.add( new Equation( false, false, isDiscrete, i, 10, "N" + i ), null );
      }
      eqHolder.revalidate();
      eqHolder.repaint();
   }

   private void jbInit() throws Exception {
      this.setLayout( gridBagLayout1 );
      labelHolder.setLayout( xYLayout1 );
      useL.setText( "Use" );
      plotL.setText( "Plot" );
      JScrollBar jsb = scroller.getVerticalScrollBar();
      scroller.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
      scroller.setBorder( BorderFactory.createLineBorder( Color.black ) );
      eqHolder.setLayout( verticalFlowLayout2 );
      numEQTF.setMaximumSize( new Dimension( 30, 30 ) );
      numEQTF.setPreferredSize( new Dimension( 25, 21 ) );
      numEQTF.setToolTipText( "Enter the number of equations you want in the table." );
      numEqL.setText( "Number of Equations:" );
      numEQTF.setHorizontalAlignment( JTextField.RIGHT );
      numEQTF.addActionListener( new java.awt.event.ActionListener()  {

         public void actionPerformed( ActionEvent e ) {
            numEQTF_actionPerformed( e );
         }
      } );
      jsb.addAdjustmentListener( new java.awt.event.AdjustmentListener()  {

         public void adjustmentValueChanged( AdjustmentEvent e ) {
            jsb_adjustmentValueChanged( e );
         }
      } );
      jsb.setUnitIncrement( 10 );
      renameB.setText("Rename");
      renameB.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            renameB_actionPerformed(e);
         }
      });
      this.add( labelHolder, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 5, 0, 5, 0 ), 0, 0 ) );
      labelHolder.add( useL, new XYConstraints( 5, 0, -1, -1 ) );
      labelHolder.add( plotL, new XYConstraints( 35, 0, -1, -1 ) );
      labelHolder.add( numEQTF, new XYConstraints( 230, 0, -1, -1 ) );
      labelHolder.add( numEqL, new XYConstraints( 100, 0, -1, -1 ) );
      labelHolder.add( renameB,  new XYConstraints(300, 0, -1, -1));
      this.add( scroller, new GridBagConstraints( 0, 1, 2, 1, 1.0, 5.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets( 0, 0, 0, 0 ), 0, 0 ) );
      scroller.getViewport().add( eqHolder, null );

      //jsb.setUnitIncrement(36);
   }

   void renameB_actionPerformed(ActionEvent e) {
      String[] paramNames = getParamNames(false);
      RenamePanel RP = new RenamePanel(paramNames);
      String[] ss = RP.getMappings();
      if(ss == null) return;
      String[] ts = mapEquations(getStrings(false),paramNames,ss);

      for(int i=0; i<eqHolder.getComponentCount(); i++){
         ( (Equation)eqHolder.getComponent( i ) ).setParameterName(ss[i]);
         ( (Equation)eqHolder.getComponent( i ) ).setEquation(ts[i]);
      }
   }

   String[] mapEquations(String[] eqs, String[] from, String[] to){
      int length = Math.min(from.length, to.length);
      String[] toReturn = new String[eqs.length];
      StringTokenizer[] newEqs = new StringTokenizer[eqs.length];
      for(int i=0; i<newEqs.length; i++){
         newEqs[i] = new StringTokenizer( eqs[i], " +-*/%^!()[]{}", true );
         toReturn[i] = "";
      }

      for(int i=0; i<eqs.length; i++){
         while(newEqs[i].hasMoreTokens()){
            String token = newEqs[i].nextToken();
            for(int j=0; j<length; j++)
               if(token.equalsIgnoreCase(from[j]))
                  token = to[j];
            toReturn[i] += token;
         }
      }
      return toReturn;
   }
}
