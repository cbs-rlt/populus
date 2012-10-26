package edu.umn.ecology.populus.model.ie;
import java.io.Serializable;

/**
  If adding a new token type, remember to specify the operator precedence.
  */

class Token implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = 5744460858336585474L;
public int tokenType;
   public double value;
   public final static int kMaximum = 2;
   public final static int kRandom = 3;
   public final static int kPlus = 4;
   public final static int kArcCosine = 4;
   public final static int kLn = 6;
   public final static int kFracPart = 10;
   public final static int kSine = 0;
   public final static int kOperator = 0;
   public final static int kMinus = 5;
   public final static int kAbsolute = 8;
   public final static int kMultiply = 6;
   public final static int kMinimum = 1;
   public final static int kDivide = 7;
   public final static int kCosine = 1;
   public final static int kModulo = 8;
   public final static int kArcSine = 3;
   public final static int kExponent = 9;
   public final static int kArcTangent = 5;
   public final static int kConstant = 1;
   public final static int kFactorial = 7;
   public final static int kBracket = 2;
   public final static int kIntPart = 9;
   public final static int kOpen = 0;
   public final static int kParameter = 4;
   public final static int kSigFig = 0;
   public final static int kFunction = 3;
   public final static int kClose = 1;
   public final static int kTangent = 2;
   
   public void setToken( int newTokenType, double newValue ) {
      tokenType = newTokenType;
      value = newValue;
   }
   
   /**
     order of precedence (lower is higher precedence)
     0. parenthesis (not really considered)
     1. unary operators (sin,cos,tan,ln,!)
     2. ^
     3. *,/,%
     4. +,-
     5. everything else
     */
   
   public int getPrecedence() {
      if( tokenType == kBracket ) {
         return 0;
      }
      if( tokenType == kFunction ) {
         return 1;
      }
      if( tokenType == kOperator ) {
         switch( (int)value ) {
            case kExponent:
                return 2;
            
            case kMultiply:
                return 3;
            
            case kDivide:
                return 3;
            
            case kModulo:
                return 3;
            
            case kPlus:
                return 4;
            
            case kMinus:
                return 4;
            
            case kSigFig:
                return 4; //should these functions be level 4?
            
            case kMinimum:
                return 4;
            
            case kMaximum:
                return 4;
            
            case kRandom:
                return 4;
         }
      }
      return 5;
   }
   
   public String toString() {
      String temp = "Type: ";
      int switcher = (int)value;
      switch( tokenType ) {
         case kOperator:
             temp += "operator\nValue: ";
             switch( switcher ) {
                case kPlus:
                    temp += "+";
                    break;
                
                case kMinus:
                    temp += "-";
                    break;
                
                case kMultiply:
                    temp += "*";
                    break;
                
                case kDivide:
                    temp += "/";
                    break;
                
                case kExponent:
                    temp += "^";
                    break;
             }
             break;
         
         case kConstant:
             temp += "constant\nValue: ";
             temp += value;
             break;
         
         case kBracket:
             temp += "bracket\nValue: ";
             switch( switcher ) {
                case kOpen:
                    temp += "(";
                    break;
                
                case kClose:
                    temp += ")";
                    break;
             }
             break;
         
         case kFunction:
             temp += "function\nValue: ";
             switch( switcher ) {
                case kSine:
                    temp += "sin";
                    break;
                
                case kCosine:
                    temp += "cos";
                    break;
                
                case kTangent:
                    temp += "tan";
                    break;
                
                case kLn:
                    temp += "ln";
                    break;
                
                case kFactorial:
                    temp += "!";
                    break;
                
                case kAbsolute:
                    temp += "|";
                    break;
             }
             break;
         
         default:
             temp += "n/a";
             break;
      }
      temp += "\n";
      return temp;
   }
   
   public String shortString() {
      String temp = "";
      int switcher = (int)value;
      switch( tokenType ) {
         case kOperator:
             switch( switcher ) {
                case kMinimum:
                    temp += "min";
                    break;
                
                case kMaximum:
                    temp += "max";
                    break;
                
                case kSigFig:
                    temp += "sigfig";
                    break;
                
                case kRandom:
                    temp += "random";
                    break;
                
                case kPlus:
                    temp += "+";
                    break;
                
                case kMinus:
                    temp += "-";
                    break;
                
                case kMultiply:
                    temp += "*";
                    break;
                
                case kModulo:
                    temp += "%";
                    break;
                
                case kDivide:
                    temp += "/";
                    break;
                
                case kExponent:
                    temp += "^";
                    break;
             }
             break;
         
         case kConstant:
             temp += value;
             break;
         
         case kBracket:
             switch( switcher ) {
                case kOpen:
                    temp += "(";
                    break;
                
                case kClose:
                    temp += ")";
                    break;
             }
             break;
         
         case kFunction:
             switch( switcher ) {
                case kSine:
                    temp += "sin";
                    break;
                
                case kCosine:
                    temp += "cos";
                    break;
                
                case kTangent:
                    temp += "tan";
                    break;
                
                case kLn:
                    temp += "ln";
                    break;
                
                case kAbsolute:
                    temp += "|";
                    break;
                
                case kFactorial:
                    temp += "!";
                    break;
                
                case kArcSine:
                    temp += "asin";
                    break;
                
                case kArcCosine:
                    temp += "acos";
                    break;
                
                case kArcTangent:
                    temp += "atan";
                    break;
                
                case kIntPart:
                    temp += "ipart";
                    break;
                
                case kFracPart:
                    temp += "fpart";
                    break;
             }
             break;
         
         case kParameter:
             if( value > 0 ) {
                temp += "N" + (int)value;
             }
             else {
                temp += "t";
             }
             break;
         
         default:
             temp += "n/a";
             break;
      }
      temp += " ";
      return temp;
   }
   
   public Object clone() {
      return new Token( tokenType, value );
   }
   
   public Token( int tokenType, double value ) {
      this.tokenType = tokenType;
      this.value = value;
   }
}
