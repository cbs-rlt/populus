package edu.umn.ecology.populus.plot;

import edu.umn.ecology.populus.core.*;
import java.io.*;
import edu.umn.ecology.populus.math.NumberMath;
import java.util.*;

/**
 * this class contains the method <code>simpleUpdateOutput</code> that controls what
 * kind of output it is going to be.
 * <p>Title: Populus</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class BasicPlotModel extends Model {
   ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.plot.Res" );
   //BasicPlotOutputPanel panel = null;
   BasicPlotInfo data = null;

   public BasicPlotModel() {
   }

   /**
    * this method controls which OutputPanel extension should be used to do the visual.
    * the only place this method is overridden right now is when it is a cell or woozle panel, but
    * that might change...
    * for now, this method handles the output of all the BasicPlotInfo plots.
    */
   protected void simpleUpdateOutput() {
      BasicPlotInfo data = (BasicPlotInfo)getData();
      if(data == null) return;
      switch (data.outputType){
         case BasicPlotInfo.k2D:
            if( ( getModelOutput() != null ) && ( getModelOutput() instanceof BasicPlotOutputPanel ) ) {
               ( (BasicPlotOutputPanel)getModelOutput() ).getBPC().setBPI( data );
            } else {
               setModelOutput( new BasicPlotOutputPanel( data ) );
            }
            break;
         case BasicPlotInfo.k3D:
            if( ( getModelOutput() != null ) && ( getModelOutput() instanceof BasicPlotOutputPanel3D ) ) {
               ( (BasicPlotOutputPanel3D)getModelOutput() ).updateData( data );
            } else {
               setModelOutput( new BasicPlotOutputPanel3D( data ) );
            }
            break;
         case BasicPlotInfo.kDeFinetti:
            if( ( getModelOutput() != null ) && ( getModelOutput() instanceof BasicPlotDeFinettiPanel ) ) {
               ( (BasicPlotDeFinettiPanel)getModelOutput() ).updateData( data );
            } else {
               setModelOutput( new BasicPlotDeFinettiPanel( data ) );
            }
            break;
         case BasicPlotInfo.kEigenSystem:
            if( ( getModelOutput() != null ) && ( getModelOutput() instanceof EigenSystem ) ) {
               ( (EigenSystem)getModelOutput() ).updateData( data );
            } else {
               setModelOutput( new EigenSystem( data ) );
            }
            break;
         case BasicPlotInfo.kSpecial:
            if ((getModelOutput() != null) && (getModelOutput() instanceof TableOutput)) {
               ((TableOutput) getModelOutput()).updateData(data);
            } else {
               setModelOutput(new TableOutput(data));
            }
            break;
      }
   }

   /** This method handles the writing of the model's data to a text file.*/

   protected void saveOutputText( OutputStreamWriter osw ) throws IOException {
      int i, j, len;
      final int columnWidth = 20;
      double[][][] data;
      BasicPlotInfo bpi = (BasicPlotInfo)getData();
      data = bpi.getData();
      if( data == null ) {
         osw.write( res.getString( "No_text_data" ) );
         return ;
      }
      String mainCaption = LabToStr( bpi.getMainCaption(), 0, false );
      osw.write( (res.getString( "Text_output_for" ) + mainCaption) + ".\n\n" );
      for( i = 0;i < data.length;i++ ) {
         osw.write((res.getString( "Line_" ) + ( i + 1 )) + "\n");
         osw.write( LabToStr( bpi.getXCaption(), columnWidth, true ) );
         if( bpi.vsTimeChars == null ) {
            osw.write( LabToStr( bpi.getYCaption(), columnWidth, true ) );
         }
         else {
            osw.write( bpi.vsTimeChars[i] );//be careful if this is called...
         }
         if( data[i].length == 3 ) {
            osw.write( LabToStr( bpi.getZCaption(), columnWidth, true ) );
         }
         osw.write( "\n" );
         len = Math.min( data[i][0].length, data[i][1].length );
         for( j = 0;j < len;j++ ) {
            for( int k = 0;k < data[i].length;k++ ) {
               osw.write( NumToStr( data[i][k][j], columnWidth, true ) );
            }
            osw.write( "\n" );
         }
         osw.write( "\n\n\n" );
         osw.flush();
      }
   }

   /**
    * this is the method that calls the <code>getData</code> method in all the models
    * @return
    */
   protected ParamInfo getData() {
      BasicPlotInputPanel bip = (BasicPlotInputPanel)getModelInput();
      BasicPlot bp = (BasicPlot)bip.getPlotParamInfo();
      if( bp != null ) {
         outputFrame.setVisible( true ); //we don't want a "white" graph, so only make it visible when we have data
         return bp.getBasicPlotInfo();
      }
      else {
         return null;
      }
   }

   /** HTMLLabels have a lot of extraneous data for text output. This
     method strips that data off. Perhaps an output option could be made for
     creating an html file to keep the "decorations."*/

   private String LabToStr( String a, int len, boolean stripSpaces ) {

      //strip off the extraneous data
      StringBuffer sb = new StringBuffer( a );
      int i = 0;
      while( i < sb.length() ) {
         if( stripSpaces && sb.charAt( i ) == ' ' ) {
            sb.deleteCharAt( i );
         }
         if( sb.charAt( i ) == '<' ) {
            while( sb.charAt( i ) != '>' ) {
               sb.deleteCharAt( i );
            }
            sb.deleteCharAt( i );
         }
         else {
            i++;
         }
      }
      sb.setLength( i );
      if( sb.charAt( 0 ) == ' ' ) {
         sb.deleteCharAt( 0 );
      }
      if( len == 0 ) {
         len = sb.length();
      }
      return NewStringLength( sb.toString(), len, ' ', false );
   }

   /** Doubles are different lengths when printed to the screen. This function
     forces them to be a uniform length so that the columns of numbers look good.*/

   private String NewStringLength( String a, int len, char replaceWith, boolean addComma ) {

      //Preconditions: replaceWith is the character that will be used to fill up length added

      //		to the string.
      StringBuffer sb = new StringBuffer( a );
      if( len == 0 ) {
         len = sb.length();
      }
      int dif = sb.length() - len;
      if( dif > 0 ) {
         if( addComma ) {
            sb.setLength( len - 1 );
            sb.append( "," );
         }
         else {
            sb.setLength( len );
         }
      }
      else {
         if( dif < 0 ) {
            if( addComma ) {
               sb.append( "," );
            }
            while( sb.length() - len < 0 ) {
               //try {
               sb.append( "" + replaceWith ); //Lars - Adding "" because JDK 1.5 is stupid.
               //} catch (IOException ioe) { }
            }
         }
         else {
            if( addComma ) {
               sb.setCharAt( sb.length() - 1, ',' );
            }
         }
      }
      return sb.toString();
   }

   private String NumToStr( double num, int len, boolean addComma ) {
      num = NumberMath.roundSig( num, 10, 0 );
      return NewStringLength( String.valueOf( num ), len, ' ', true );
   }

//uncomment this method for an excel-type option

/*
protected int getChoice() {
return edu.umn.ecology.populus.core.SaveDialog.getSaveChoice(true);
}
protected void saveOutputExcel() {
String filename = "test";
try {
ObjectOutputStream oos;
filename = edu.umn.ecology.populus.fileio.IOUtility.getFileName(
"model1.txt", "Save Output in Excel-Readable Format", java.awt.FileDialog.SAVE);
if ((filename != null) && (data != null)) {
data.writeToTextFileXL(new FileOutputStream(filename));
}
} catch (IOException e) {
javax.swing.JOptionPane.showMessageDialog(DesktopWindow.defaultWindow,
"Could not write file", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
}
}
*/
}
