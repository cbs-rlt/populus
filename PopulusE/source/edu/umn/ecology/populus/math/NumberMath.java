package edu.umn.ecology.populus.math;
import edu.umn.ecology.populus.model.ie.TextOutput;
/**
  Primarily used for formatting i/o
  Perhaps some of these functions are no longer needed, java itself provides the utilities
  to do some of these... will keep them here for now.

  The other thing is that perhaps these methods should be extracted (from here and in
  other files like BasicPlotModel and ..visual.Utilities) and placed into their own package.
  */

public final class NumberMath extends Object {
   public static final double LOG_OF_TEN = Math.log( 10 ); //Log base e
   public static final int UP = 1, DOWN = -1, NORMAL = 0;

   /**
     *  {rounds n to sf significant figures}
     *  Rounds <code>n</code> to <code>sf</code> significant figures, in base 10
     *  direction can be NORMAL, UP, or DOWN
     */

   public static double roundSig( double n, int sf, int direction ) {
      double factor;
      double temp;
      double absN;
      if( n == 0.0 ) {
         return 0.0;
      }
      else {
         absN = Math.abs( n );
         factor = Math.pow( 10, sf - 1 - ( (int)( Math.floor( ( Math.log( absN ) / LOG_OF_TEN ) ) ) ) );
         temp = absN * factor;
         if( direction == UP ) {
            temp = Math.ceil( temp );
         }
         else {
            if( direction == NORMAL ) {
               temp = (double)Math.round( temp );
            }
            else {
               if( direction == DOWN ) {
                  temp = Math.floor( temp );
               }
               else {
                  edu.umn.ecology.populus.fileio.Logging.log( "Unknown direction " + direction + " in roundSig" );
               }
            }
         }
      }
      return sign( n ) * temp / factor;
   }

   public static String roundSigScientific(double n, int sf, int direction){
      double value = roundSig(n,sf,direction);
      int power = (int)(Math.log(value)/Math.log(10.0d))-1;
      if(power > -2) return ""+value;
      value *= Math.pow(10.0d,-1*power);
      String sValue = Double.toString(value);
      if(sValue.length() > (sf+1))
         sValue = sValue.substring(0,sf+1);
      //return ""+value+"x10^"+power;
      return sValue+"E"+power;
   }

   //All this does is take 10^power. Is that necessary?

   public static double aLogI( int n ) {
      double temp = 1;
      int i;
      if( n > 0 ) {
         for( i = 1;i <= n;i++ ) {
            temp *= 10;
         }
      }
      else {
         if( n < 0 ) {
            for( i = -1;i >= n;i++ ) {
               temp /= 10;
            }
         }
      }
      return temp;
   }

   public static double getLittleMod( double bigger ) {
      int base = NumberMath.getNonExp( bigger );
      switch( base ) {
         case 2:
             return 4.0; //was 2.0

         case 3:
         case 6:
         case 9:
             return 3.0;

         case 1:
         case 5:
             return 5.0;

         case 7:
             return 1.0;

         default:
             return 4.0;
      }
   }

   /**
     *  returns the sign of n
     */

   public static double sign( double n ) {
      return ( n < 0.0 ) ? -1.0 : ( ( n == 0.0 ) ? 0.0 : 1.0 );
   }

   public static double roundTypeA( double a ) {
      a = roundSig( a, 1, DOWN );
      switch( NumberMath.getNonExp( a ) ) {
         case 6:
             return a * ( 5.0 / 6.0 );

         case 7:
             return a * ( 8.0 / 7.0 );

         case 9:
             return a * ( 10.0 / 9.0 );

         default:
             return a;
      }
   }

   /**
     *  returns the sign of n
     */

   public static int sign( int n ) {
      return ( n < 0 ) ? -1 : ( ( n == 0 ) ? 0 : 1 );
   }

   public static final int getExponent( double number ) {
      int result = (int)Math.floor( Math.log( Math.abs( number ) ) / LOG_OF_TEN );
      if( number / Math.pow( 10, result ) >= 10.0 ) {
         return result + 1;
      }
      else {
         return result;
      }
   }

   public static final int getNonExp( double number ) {
      return (int)Math.floor( .5 + ( number / Math.pow( 10, getExponent( number ) ) ) );
   }

   public static final boolean charIn( char c, String s ) {
      for( int i = 0;i < s.length();i++ ) {
         if( s.charAt( i ) == c ) {
            return true;
         }
      }
      return false;
   }

   public static final String formatExpNotation( double number ) {
      int exp = getExponent( number );
      number += 0.005 * Math.pow( 10, exp );
      StringBuffer buffer = new StringBuffer( Double.toString( number / Math.pow( 10, exp ) ) );
      if( buffer.length() > 3 ) {
         buffer.setLength( 3 );
      }
      return buffer.toString() + "E" + Integer.toString( exp );
   }

   /**Formats a number to two-digit accuracy, except for (n>100 && n<1000)
     * A more complicated explanation is:
     * 1) if n>=1000 or n<0.01 use exp'l notation
     *  2) if n>= 10 round to an integer
     *   I replaced step 3 for now, since we only need two decimal accuracy.
     *   3) if needed, truncate string to 6 chars, then (if truncated):
     *      if there is a decimal point, make 3 loops, until right side is not ., 9, or 0:
     *      i) while 0 at right, delete all right hand zeros, then proceed to check '.', skipping 9 check
     *      ii) if there is a 9, check which is closer to the original number:
     *          a) what it is now,
     *          or b) that plus 0.0001 (that would round it up if truncated to 6 chars)
     *          Take which is better
     *      iii) if there is a . at end, delete it,
     */

   //I may want something for three digit accuracy.

   /* OLD CODE:
   public static final String formatNumber(double n) {
   int maxLength;
   String value;
   StringBuffer buffer;
   boolean negative = false;

   if (n < 0) {
   n *= -1;
   negative = true;
   }
   if (n < 1e-10) return "0"; //Otherwise, you get NaN!!!
   if ((n >= 1000.0) || (n <0.01))
   value = formatExpNotation(n);
   else if (n >= 10.0)
   value = Integer.toString((int) (n + 0.5));
   else {
   if (n >= 1.0) {
   n += 0.05;
   maxLength = 3;
   }
   else if (n >= 0.1) {
   n += 0.005;
   maxLength = 4;
   }
   else {
   n += 0.0005;
   maxLength = 5;
   }
   value = Double.toString(n);
   if (value.length() > maxLength) {
   buffer = new StringBuffer(value);
   buffer.setLength(maxLength);
   if (buffer.charAt(maxLength - 1) == '0') {
   buffer.setLength(maxLength - 1);
   if (buffer.charAt(maxLength - 2) == '.')
   buffer.setLength(maxLength - 2);
   }
   value = new String(buffer);
   }
   }
   if (negative)
   value = "-" + value;
   return value;
   }
   */

   public static final String formatNumber( double n ) {
      int maxLength;
      String value;
      boolean negative = false;
      if( n < 0 ) {
         n *= -1;
         negative = true;
      }
      if( n < 1e-300 ) {
         return "0"; //Otherwise, you get NaN!!!
      }
      if( ( n >= 1000.0 ) || ( n < 0.001 ) ) {
         value = formatExpNotation3( n, 3 );
      }
      else {
         if( n >= 100.0 ) {
            value = Integer.toString( (int)( n + 0.5 ) );
         }

         // Although this seems silly to break up each case, at least it works,

         //  and can't be too slow - no use of log
         else {
            if( n >= 10.0 ) {
               n += 0.05;
               maxLength = 4;
            }
            if( n >= 1.0 ) {
               n += 0.005;
               maxLength = 4;
            }
            else {
               if( n >= 0.1 ) {
                  n += 0.0005;
                  maxLength = 5;
               }
               else {
                  n += 0.00005;
                  maxLength = 6;
               }
            }
            value = trimDown( Double.toString( n ), maxLength );
         }
      }
      if( negative ) {
         value = "-" + value;
      }
      return value;
   }

   /* I use this for Graphing purposes, so that 0 shows up as 0, not something crazy */

   public static final String formatNumber( double n, double bigMod ) {
      if( Math.abs( n * 10.0 ) < bigMod ) {
         return "0";
      }
      return formatNumber( n );
   }

   /** Formats a number, but with more complexity. Soon to be worked on */

   public static final String formatNumber( double number, int l ) {
      if(number > 1000) return formatExpNotation3(number,l);
      int temp = (int)(number*Math.pow(10,l)+0.5);
      return ""+(double)temp/Math.pow(10,l);
   }

   /** meant only for with decimal point, assumed to be already fudged up (half of one least sig fig) */

   static String trimDown( String s, int size ) {
      if( s.length() <= size ) {
         return s;
      }
      char c;
      StringBuffer buffer = new StringBuffer( s );
      buffer.setLength( size );
      c = buffer.charAt( --size );
      while( c == '0' ) {
         buffer.setLength( size );
         c = buffer.charAt( --size );
      }
      if( c == '.' ) {
         buffer.setLength( size );
      }
      return buffer.toString();
   }

   /** meant only for positive numbers */

   static final String formatExpNotation3( double number, int numDigits ) {
      int exp = getExponent( number );
      number += 0.0005 * Math.pow( 10, exp );
      return trimDown( Double.toString( number / Math.pow( 10, exp ) ), exp>9?numDigits:numDigits+1 ) + "E" + Integer.toString( exp );
   }

   public static void printMatrix(double[][] a, String name, boolean reverse){
      edu.umn.ecology.populus.fileio.Logging.log("\n"+name+": ");
      if(reverse){
         for(int i=0; i<a.length; i++){
            for(int j=0; j<a[i].length; j++){
               System.out.print(TextOutput.NumToStr(a[i][j],20,10,false));
            }
            System.out.print("\n");
         }
      } else {
         for(int i=0; i<a[0].length; i++){
            for(int j=0; j<a.length; j++){
               System.out.print(TextOutput.NumToStr(a[j][i],20,10,false));
            }
            System.out.print("\n");
         }
      }
      //edu.umn.ecology.populus.fileio.Logging.log("sum: "+sum);
   }

   static void normalize(double[][] a){
      double sum=0;
      for(int i=0; i<a.length; i++)
         for(int j=0; j<a[i].length; j++)
            sum += a[i][j];
      for(int i=0; i<a.length; i++)
         for(int j=0; j<a[i].length; j++)
            a[i][j] /= sum;
   }

   public static double[][] roundedMatrix(double[][] mat){
//postconditions: because the data in the matrix isn't useful after the 3rd
//		decimal place, this method will round each entry for output while leaving
//		the original in place for later use if for some reason used again
      double[][] newMatrix;
      double tempNum;
      int tempTrunked;

      newMatrix = new double[mat.length][mat[0].length];
      for(int i=0; i<mat.length; i++){
         for(int j=0; j<mat[0].length; j++){
            tempNum = mat[i][j];
            if(tempNum < Math.pow(2,32)/2500){
               tempTrunked = (int)(tempNum*1000);
               tempNum = (double)tempTrunked;
               tempNum /= 1000;
               if((mat[i][j] - tempNum) >= 0.0005) tempNum += 0.001;
            }
            newMatrix[i][j] = tempNum;
         }
      }
      return newMatrix;
   }
}