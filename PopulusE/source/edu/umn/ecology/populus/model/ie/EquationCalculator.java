package edu.umn.ecology.populus.model.ie;
import java.util.*;
import java.util.Random;
import java.lang.Character;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.io.Serializable;
import edu.umn.ecology.populus.math.NumberMath;
import edu.umn.ecology.populus.math.Routines;

/**
  This class will take an array of Strings and make a solvable equation out of them.
  The utilizing program can then give it values for individual variables and then
  get answers. This class was written for Populus, so it will interact with Populus'
  Integration utility.

  The core of this class was written by Amos Anderson for a computer science class in high
  school, and has been spiffied up to handle functions and variables for Populus.

  Look at the comments in the individual methods for an idea how the class accomplishes it's
  task.

  Programming notes: this seems to work really really fast. After being initially set up,
  the time it takes to calculate the actual values is very fast. Because of this, it just
  may not be necessary to do too many optimizations. But if it is deemed necessary, then
  the strings and vectors could be gotten rid of after their usefulness is done.

  More functions could be added if deemed necessary, for example it would not be difficult to
  add arc-trig functions, an absolute function or even a random function and maybe modulo. If
  the idea of adding more functions gets really exciting, then perhaps even a
  "Define-A-Function"(t) component could be added to the program.
 */

public class EquationCalculator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1638244995998903324L;
	private String[] eqs;
	private Vector<Token>[] v;
	private Stack[] s;
	private double[] initial, calculated;
	private Hashtable constants = null;
	private boolean[] used;
	private int numEQ = 0;
	private boolean randomOK = true;
	private static Random eqRand = new Random( System.currentTimeMillis() );

	/**
     See EquationCalculator(String[] equations) for a description of
     the equations parameter.

     The hashtable contains the values of the constants
     (in constant name, constant value pairs)
	 */

	public EquationCalculator( String[] equations, Hashtable constants, boolean randomAllowed ) throws IEException {
		used = new boolean[equations.length];
		for( int i = 0;i < equations.length;i++ ) {
			used[i] = true; //default is that all equations will be used
		}
		this.constants = constants;
		this.randomOK = randomAllowed;
		SetUp( equations );
	}

	/**
     The added boolean array indicates which of the equations in the string array will
     be used.

     The purpose of this is that some people may want to easily switch "on" and "off"
     some of the equations. This provides the easiest way to do this.
	 */

	public EquationCalculator( String[] equations, boolean[] isUsed, Hashtable constants, boolean randomAllowed ) throws IEException {
		this.constants = constants;
		used = isUsed;
		randomOK = randomAllowed;
		SetUp( equations );
	}

	/**
    The array of equations contain the strings the user entered. These strings
    represent equations the user would like to see solved.

    Valid components in the string are:
    numbers (doubles)
    operators +-*^/
    functions sin,cos,tan,!,ln
    constants (a name of a constant, the value supplied from a hashtable)
    parameters (currently defined to be the initial populuations, and in the strings,
    are of the form, N1, N2, N3 etc depending on the number of equations
    time parameter (stored as N0)
	 */

	public EquationCalculator( String[] equations ) throws IEException {
		used = new boolean[equations.length];
		for( int i = 0;i < equations.length;i++ ) {
			used[i] = true; //default is that all equations will be used
		}
		SetUp( equations );
	}

	/*************************************************
	 *           Public Methods                       *
	 *************************************************/

	public double[] calculateValues( double[] newInitial, double newTime ) {
		initial[0] = newTime;
		for( int i = 0;i < numEQ;i++ ) {
			initial[i + 1] = newInitial[i];
		}
		Solve();

		//PrintAnswers();
		return calculated;
	}

	public int numVariables() {
		return numEQ;
	}

	public void refreshRandom( long seed ) {
		eqRand.setSeed( seed );
	}

	/**	This method will take the collection of strings and tokenize them into the kinds of
     tokens allowed here (see the class Token for valid tokens)
	 */

	void Tokenize() throws IEException {

		//Preconditions:
		//		an array of strings stored in the global variable
		//Postconditions:
		//		an array of vectors storing the tokens.
		//
		//		will modify the equation when numbers and functions get in same token
		StringTokenizer st;
		String temp = "";
		boolean tokenIdentified, lastTokenWasParam = false;
		for( int i = 0;i < numEQ;i++ ) {
			v[i] = new Vector<Token>();
			if( !used[i] ) {
				continue;
			}
			st = new StringTokenizer( eqs[i], " +-*/%^!()[]{}", true );
			lastTokenWasParam = false;
			while( st.hasMoreTokens() ) {
				tokenIdentified = false;
				temp = st.nextToken();
				if( Character.isDigit( temp.charAt( 0 ) ) ) {
					tokenIdentified = true;
					int k;
					for( k = 0;k < temp.length() && tokenIdentified == true;k++ ) {
						if( !Character.isDigit( temp.charAt( k ) ) ) {
							tokenIdentified = false;
						}
					}
					if( tokenIdentified ) {
						v[i].addElement( new Token( Token.kConstant, Double.valueOf( temp ).doubleValue() ) );
					}
					else {
						v[i].addElement( new Token( Token.kConstant, Double.valueOf( temp.substring( 0, k - 1 ) ).doubleValue() ) );
						temp = temp.substring( k - 1, temp.length() );
					}
				}
				if( tokenIdentified == false && temp.charAt( 0 ) == '.' ) {
					tokenIdentified = true;
					temp = "0" + temp;
					if( v[i].size() > 0 && v[i].elementAt( v[i].size() - 1 ).tokenType == Token.kConstant ) {
						( (Token)v[i].elementAt( v[i].size() - 1 ) ).value += Double.parseDouble( temp );
					}
					else {
						throw new IEException( "Invalid decimal" );
					}
				}
				if( tokenIdentified == false && ( temp.charAt( 0 ) >= 'a' && temp.charAt( 0 ) <= 'z' ) || ( temp.charAt( 0 ) >= 'A' && temp.charAt( 0 ) <= 'Z' ) ) {
					tokenIdentified = true;
					int type = 999;
					double value = 0;

					//functions
					if( temp.equalsIgnoreCase( "sin" ) ) {
						type = Token.kFunction;
						value = Token.kSine;
					}
					else if( temp.equalsIgnoreCase( "cos" ) ) {
						type = Token.kFunction;
						value = Token.kCosine;
					}
					else if( temp.equalsIgnoreCase( "tan" ) ) {
						type = Token.kFunction;
						value = Token.kTangent;
					}
					else if( temp.equalsIgnoreCase( "asin" ) ) {
						type = Token.kFunction;
						value = Token.kArcSine;
					}
					else if( temp.equalsIgnoreCase( "acos" ) ) {
						type = Token.kFunction;
						value = Token.kArcCosine;
					}
					else if( temp.equalsIgnoreCase( "atan" ) ) {
						type = Token.kFunction;
						value = Token.kArcTangent;
					}
					else if( temp.equalsIgnoreCase( "ln" ) ) {
						type = Token.kFunction;
						value = Token.kLn;
					}
					else if( temp.equalsIgnoreCase( "abs" ) ) {
						type = Token.kFunction;
						value = Token.kAbsolute;
					}
					else if( temp.equalsIgnoreCase( "ipart" ) ) {
						type = Token.kFunction;
						value = Token.kIntPart;
					}
					else if( temp.equalsIgnoreCase( "fpart" ) ) {
						type = Token.kFunction;
						value = Token.kFracPart;
					}
					//miscellaneous operators
					else if( temp.equalsIgnoreCase( "min" ) ) {
						type = Token.kOperator;
						value = Token.kMinimum;
					}
					else if( temp.equalsIgnoreCase( "max" ) ) {
						type = Token.kOperator;
						value = Token.kMaximum;
					}
					else if( temp.equalsIgnoreCase( "sigfig" ) ) {
						type = Token.kOperator;
						value = Token.kSigFig;
					}
					else if( temp.equalsIgnoreCase( "random" ) ) {
						if( !randomOK ) {
							throw new IEException( "\"random\" not defined for continuous equations." );
						}
						type = Token.kOperator;
						value = Token.kRandom;
					}
					else if( temp.startsWith( "N" ) ) {
						type = Token.kParameter;
						try {
							value = Integer.parseInt( temp.substring( 1 ) ); //i.e. N1, N2, N3, etc ("N0" is reserved for time)
						}
						catch( NumberFormatException nfe ) {
							throw new IEException( "Invalid index for N: \'" + temp.substring( 1 ) + "\'." );
						}
						if( value > numEQ || value < 1 ) {
							throw new IEException( "Parameter N index " + (int)value + " not defined." );
						}
						if( !used[(int)( value - 1 )] ) {
							throw new IEException( "Parameter N" + (int)value + " not used." );
						}
						lastTokenWasParam = true;
						temp = "";
					}
					else if( temp.startsWith( "t" ) && temp.length() == 1 ) {
						type = Token.kParameter;
						value = 0;
						lastTokenWasParam = true;
						temp = "";
					}

					if( type < 999 ) {

						//if appropriate, insert multiplication before the constant
						if( v[i].size() > 0 && type != Token.kOperator ) {
							if( v[i].elementAt( v[i].size() - 1 ).tokenType == Token.kConstant
									|| ( v[i].elementAt( v[i].size() - 1 ).tokenType == Token.kBracket
									&& v[i].elementAt( v[i].size() - 1 ).value == Token.kClose ) ) {
								v[i].addElement( new Token( Token.kOperator, Token.kMultiply ) );
							}
						}
						v[i].addElement( new Token( type, value ) );
					}
					else if( temp.equalsIgnoreCase( "e" ) && !constants.containsKey( temp ) ) {
						value = Math.E;
					} else if( temp.equalsIgnoreCase( "pi" ) ) {
						value = Math.PI;
					} else if( temp.equalsIgnoreCase( "rand" ) ) {
						value = eqRand.nextDouble();
						if( eqRand.nextBoolean() ) {
							value *= -1;
						}
						edu.umn.ecology.populus.fileio.Logging.log( "Rand value generated: " + value );
					} else if( temp.equalsIgnoreCase( "normal" ) ) {
						value = eqRand.nextGaussian();
						edu.umn.ecology.populus.fileio.Logging.log( "Normal value generated: " + value );
					} else if( constants != null ) {
						if( constants.containsKey( temp ) ) {
							try {
								value = ( (Double)constants.get( temp ) ).doubleValue();
							}
							catch( NumberFormatException nfe ) {
								throw new IEException( "Hashtable didn't give a number." );
							}
						}
						else {
							throw new IEException( "Constant \'" + temp + "\' is not defined." );
						}
					}
					else {
						throw new IEException( "Constants not provided." );
					}

					//if appropriate, insert multiplication before the constant
					if( v[i].size() > 0 ) {
						if( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType == Token.kConstant || ( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType == Token.kBracket && ( (Token)v[i].elementAt( v[i].size() - 1 ) ).value == Token.kClose ) ) {
							v[i].addElement( new Token( Token.kOperator, Token.kMultiply ) );
						}
					}
					v[i].addElement( new Token( Token.kConstant, value ) );
					lastTokenWasParam = true;
					temp = "";
				}
				if( !tokenIdentified ) {
					switch( temp.charAt( 0 ) ) {
					case '+':
						v[i].addElement( new Token( Token.kOperator, Token.kPlus ) );
						break;

					case '-':
						v[i].addElement( new Token( Token.kOperator, Token.kMinus ) );
						break;

					case '*':
						v[i].addElement( new Token( Token.kOperator, Token.kMultiply ) );
						break;

					case '/':
						v[i].addElement( new Token( Token.kOperator, Token.kDivide ) );
						break;

					case '%':
						v[i].addElement( new Token( Token.kOperator, Token.kModulo ) );
						break;

					case '^':
						v[i].addElement( new Token( Token.kOperator, Token.kExponent ) );
						break;

					case '!':
						v[i].addElement( new Token( Token.kFunction, Token.kFactorial ) );
						break;

					case '(':
						v[i].addElement( new Token( Token.kBracket, Token.kOpen ) );
						break;

					case ')':
						v[i].addElement( new Token( Token.kBracket, Token.kClose ) );
						break;

					case '{':
						v[i].addElement( new Token( Token.kBracket, Token.kOpen ) );
						break;

					case '}':
						v[i].addElement( new Token( Token.kBracket, Token.kClose ) );
						break;

					case '[':
						v[i].addElement( new Token( Token.kBracket, Token.kOpen ) );
						break;

					case ']':
						v[i].addElement( new Token( Token.kBracket, Token.kClose ) );
						break;

					case ' ':
						temp = "";
						break;

					default:
						throw new IEException( "Unknown token: '" + temp + "'." );
					} //end switch
				}
				if( lastTokenWasParam && temp != "" ) {
					lastTokenWasParam = false;

					//we will insert a * if it doesn't already have an operator and it is not the

					//end of a parenthetical statement
					if( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType != Token.kOperator && !( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType == Token.kBracket && ( (Token)v[i].elementAt( v[i].size() - 1 ) ).value == Token.kClose ) ) {
						v[i].insertElementAt( new Token( Token.kOperator, Token.kMultiply ), v[i].size() - 1 );
					}
				}
			} //end while
		} //end for
	} //end method

boolean Syntax() throws IEException {
	//Preconditions:
	//		a vector of tokens
	//Postconditions:
	//		for the rest of this class to work, this method must stop the rif-raf from getting
	//		by. it will check for possibilities that could "crash" other parts of the program.
	//
	//    will throw an IEException if:
	//		->	the brackets are unbalanced 					((1+2)
	//      ->	2 operators are next to each other 		1+*2
	//      ->	incorrect operators and brackets 			(*1-2) or (1-2*)
	//		->	operators on ends of equation					*(1+2) or (1+2)*
	//      ->	two adjacent number tokens         		1 2+3
	//      ->	operators incorrect order							)1+2(
	//      ->	number after close bracket						(1+2)3
	//		->	operator inside of close bracket			(1-)
	//      ->	operator inside function							sin * 8
	//
	//    will take the liberty to modify the vector when:
	//		->	if there is a number next to an open bracket, then multiplication will be assumed
	//		->	a constant is in front of a function, multiplication will be assumed
	//		->	move factorial function to front of its operand
	//		->	very first token is a '-', will change to -1*
	//		->	move !'s to same position as other functions i.e from 9! to !9
	//		->	minus signs are checked for negate. e.g. (-9) to (-1*9)
	int bracketcount;
	for( int i = 0;i < v.length;i++ ) {
		if( !used[i] ) {
			continue;
		}
		if( v[i].isEmpty() ) {
			continue;
		}
		bracketcount = 0;

		//if the first operator is '-', then assume it negates the 2nd token

		//needed because switch starts at 1.
		if( !v[i].isEmpty() ) {
			if( ( (Token)v[i].elementAt( 0 ) ).tokenType == Token.kOperator && ( (Token)v[i].elementAt( 0 ) ).value == Token.kMinus ) {
				v[i].removeElementAt( 0 );
				v[i].insertElementAt( new Token( Token.kOperator, Token.kMultiply ), 0 );
				v[i].insertElementAt( new Token( Token.kConstant, -1 ), 0 );
			}
		}

		//check for too many close brackets
		for( int j = 0;j < v[i].size();j++ ) {
			if( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kBracket ) {
				if( ( (Token)v[i].elementAt( j ) ).value == Token.kOpen ) {
					bracketcount++;
				}
				else {
					bracketcount--;
				}
			}
			if( bracketcount < 0 ) {
				throw new IEException( "Brackets incorrect order." );
			}
		}

		//check for unbalanced brackets
		if( bracketcount != 0 ) {
			throw new IEException( "Brackets unbalanced." );
		}

		//check for others
		for( int j = 0;j < v[i].size() - 1;j++ ) {
			switch( ( (Token)v[i].elementAt( j + 1 ) ).tokenType ) {
			case Token.kBracket:
				if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kOpen && ( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kConstant || /*5(*/ ( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kBracket && /*)(*/ ( (Token)v[i].elementAt( j ) ).value == Token.kClose ) ) ) {
					v[i].insertElementAt( new Token( Token.kOperator, Token.kMultiply ), j + 1 );
				}
				if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kOpen && ( ( ( (Token)v[i].elementAt( j + 2 ) ).tokenType == Token.kOperator && ( (Token)v[i].elementAt( j + 2 ) ).value != Token.kMinus ) || /*(**/ ( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kBracket && /*)(*/ ( (Token)v[i].elementAt( j ) ).value == Token.kClose ) ) ) {
					throw new IEException( "Invalid token inside of open bracket." );
				}
				if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kClose && ( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kOperator || /*-)*/ ( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kBracket && /*()*/ ( (Token)v[i].elementAt( j ) ).value == Token.kOpen ) ) ) {
					throw new IEException( "Invalid token inside of close bracket." );
				}
				if( v[i].size() > j + 2 ) {
					if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kClose && ( ( (Token)v[i].elementAt( j + 2 ) ).tokenType == Token.kConstant || /*)5*/ ( ( (Token)v[i].elementAt( j + 2 ) ).tokenType == Token.kFunction && /*)sin*/ ( (Token)v[i].elementAt( j + 2 ) ).value != Token.kFactorial ) ) ) {
						throw new IEException( "Invalid token outside of close bracket." );
					}
				}
				break;

			case Token.kConstant:
				if( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kConstant ) {
					throw new IEException( "Two adjacent numbers without operator." );
				}
				break;

			case Token.kOperator:
				if( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kOperator ) {
					throw new IEException( "Two Operators next to each other." );
				}
				if( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kFunction ) {
					throw new IEException( "Operator on inside of function." );
				}

				//If these conditions are true, then assume the "-" is negate, not minus
				if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kMinus ) {
					if( ( (Token)v[i].elementAt( j ) ).tokenType != Token.kConstant && ( (Token)v[i].elementAt( j ) ).tokenType != Token.kParameter && !( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kBracket && ( (Token)v[i].elementAt( j ) ).value == Token.kClose ) ) {
						v[i].removeElementAt( j + 1 );
						v[i].insertElementAt( new Token( Token.kOperator, Token.kMultiply ), j + 1 );
						v[i].insertElementAt( new Token( Token.kConstant, -1 ), j + 1 );
					}
				}
				break;

			case Token.kFunction:
				if( ( (Token)v[i].elementAt( j + 1 ) ).value == Token.kFactorial ) {
					int k = j, l = 0;
					do {
						if( ( (Token)v[i].elementAt( k ) ).tokenType == Token.kBracket ) {
							if( ( (Token)v[i].elementAt( k ) ).value == Token.kOpen ) {
								l++;
							}
							else {
								l--;
							}
						}
						k--;
					}while( l != 0 );
					v[i].removeElementAt( j + 1 );
					v[i].insertElementAt( new Token( Token.kFunction, Token.kFactorial ), k + 1 );
				}
				if( ( (Token)v[i].elementAt( j + 1 ) ).tokenType == Token.kFunction ) {
					if( ( (Token)v[i].elementAt( j ) ).tokenType == Token.kConstant ) {
						v[i].insertElementAt( new Token( Token.kOperator, Token.kMultiply ), j + 1 );
					}
				}
				break;
			}
		}

		//check first and last tokens for validity
		if( ( (Token)v[i].elementAt( 0 ) ).tokenType == Token.kOperator ) {
			throw new IEException( "Invalid first token." );
		}
		if( ( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType == Token.kOperator ) || ( ( (Token)v[i].elementAt( v[i].size() - 1 ) ).tokenType == Token.kFunction ) ) {
			throw new IEException( "Invalid last token." );
		}
	}
	return true;
}

void InfixToPrefix( Stack cs, int index, int left, int right ) {

	//A recursive method which creates a prefix stack based on the vector

	//at index i.

	//Preconditions:

	//		a vector of tokens

	//Postconditions:

	//		a prefix stack

	//		examples:

	//			1 + 2 -> + 1 2

	//			1 + 2 * 3	-> + * 2 3 1

	//			(1+2)*3 ->	* 3 + 1 2
	if( right < left ) {
		return ;
	}
	int symbol, op1, op2, bracketcount = 0;
	boolean foundSymbol = false;
	boolean pok = true, checked = false; //(p)arenthesis (ok), checked -> we have checked for brackets
	if( v[index].size() == 0 ) {
		return ;
	}

	//strip off outermost parenthesis
	while( !checked && ( (Token)v[index].elementAt( left ) ).tokenType == Token.kBracket && ( (Token)v[index].elementAt( right ) ).tokenType == Token.kBracket ) {
		checked = true;

		//we need to check if first parenthesis has a pair other than the very last
		for( int j = left;j < right;j++ ) {
			if( ( (Token)v[index].elementAt( j ) ).tokenType == Token.kBracket ) {
				if( ( (Token)v[index].elementAt( j ) ).value == Token.kOpen ) {
					bracketcount++;
				}
				else {
					bracketcount--;
				}
			}
			if( bracketcount == 0 ) {
				pok = false;
			}
		}
		if( pok ) {
			checked = false;
			pok = true;
			bracketcount = 0;
			left++;
			right--;
		}
	}

	//edu.umn.ecology.populus.fileio.Logging.log("Progress in infix: bounds are "+left+" and "+right);

	//PrintVector(left,right);

	//We loop through the different operator precedences (as defined in the Token class)

	//looking for the lowest precedence first. The inner loop goes from the end of the

	//segment to the beginning (according to the rules of math)

	//

	//The initial business with the brackets is because we want to skip all of the stuff

	//in the brackets. That is, we will recurse back to this function the insides of

	//those brackets.
	symbol = op1 = op2 = bracketcount = 0;
	breakthenestedforloop:for( int j = 4;j >= 1;j-- ) {
		for( int i = right;i >= left;i-- ) {
			if( ( (Token)v[index].elementAt( i ) ).tokenType == Token.kBracket ) {
				do {
					if( ( (Token)v[index].elementAt( i ) ).tokenType == Token.kBracket ) {
						if( ( (Token)v[index].elementAt( i ) ).value == Token.kOpen ) {
							bracketcount++;
						}
						else {
							bracketcount--;
						}
					}
					i--;
				}while( bracketcount != 0 );
				i++;
			}
			if( ( (Token)v[index].elementAt( i ) ).getPrecedence() == j ) {
				symbol = i;
				op1 = i - 1;
				op2 = i + 1;
				foundSymbol = true;
				break breakthenestedforloop;
			}
		}
	}

	//The segment has been divided into 2 parts, the remaining code in this method will

	//send the 2 halves back to this function.
	if( !foundSymbol ) {
		cs.push( new Integer( right ) );
	}
	else {
		InfixToPrefix( cs, index, op2, right );
		InfixToPrefix( cs, index, left, op1 );
		cs.push( new Integer( symbol ) );
	}
}

double getRandom( double low, double high ) {
	if( high < low ) {
		double temp = low;
		low = high;
		high = temp;
	}
	if( high == low ) {
		return low;
	}
	return ( high - low ) * eqRand.nextDouble() + low;
}

/*************************************************
 *									Debugger Methods							 *
 *************************************************/

void PrintVectors() {
	edu.umn.ecology.populus.fileio.Logging.log( "The vectors are:" );
	for( int i = 0;i < v.length;i++ ) {
		if( v[i] != null ) {
			System.out.print( i + ").\t" );
			for( int j = 0;j < v[i].size();j++ ) {
				System.out.print( ( (Token)v[i].elementAt( j ) ).shortString() );
			}
		}
		edu.umn.ecology.populus.fileio.Logging.log();
	}
}

void PrintAnswers() {
	edu.umn.ecology.populus.fileio.Logging.log( "The answers are:" );
	for( int i = 0;i < numEQ;i++ ) {
		edu.umn.ecology.populus.fileio.Logging.log( i + "\t" + calculated[i] );
	}
}

/*************************************************
 *									Core Methods									 *
 *************************************************/

void SetUp( String[] equations ) throws IEException {
	numEQ = equations.length;
	eqs = equations;
	v = new Vector[numEQ];
	s = new Stack[numEQ];
	calculated = new double[numEQ];
	initial = new double[numEQ + 1];
	try {
		Tokenize();
		Syntax();
		for( int i = 0;i < numEQ;i++ ) {
			s[i] = new Stack();
			if( !used[i] ) {
				continue;
			}
			InfixToPrefix( s[i], i, 0, v[i].size() - 1 );
		}

		//Solve();
	}
	catch( IEException iee ) {
		throw iee;
	}
	catch( NumberFormatException nfe ) {
		throw new IEException( "Unknown token used." );
	}
	catch( Exception e ) {
		e.printStackTrace();
		throw new IEException( "Something bad happened..." );
	}

	/*Printing section
   edu.umn.ecology.populus.fileio.Logging.log();
   PrintVectors();
   PrintStacks();
   //*/
}

double DoFactorial( double op ) {

	//The definition for the factorial of non-negative integers is

	//easy enough. otherwise, the gamma function is used

	//(see http://hissa.nist.gov/dads/HTML/gammafunctn.html)

	//

	//the definition found there is:

	//gamma(n) = integral from 0 to infinity of e^x*x^(n-1) dx

	//where gamma(n+1) = n!
	if( Double.isInfinite( op ) ) {
		return op;
	}
	if( (int)op == op ) {
		if( op <= 0 ) {
			return 1;
		}
		return DoFactorial( op - 1 ) * op;
	}
	else {
		return Math.exp( Routines.gammln( op + 1 ) );
	}
}

void Solve() {

	//This method will solve the stack array and leave the answers in the "answers" array

	//Preconditions:

	//		the stacks are in prefix order (see InfixToPrefix) without errors (see Syntax)

	//

	//Postconditions:

	//		the stacks are as they were (they are not destroyed) and the results are in the

	//		array "answers." if the string entered at the very beginning made it to this method,

	//		then this method will work as intended (assuming no bugs)

	//

	//		When a valid stack has been created (see InfixToPrefix), it can be evaluated as follows:

	//

	//		if we have a stack like * 3 + 1 2, the order of operations will be:

	//		1)find an operator followed by 2 numbers and do the operation

	//			eg. + 1 2 -> 3

	//		2)replace the tokens used in step 1 with the result in the stack

	//			eg. the stack becomes * 3 3

	//		3)if there are operators remaining, go back to step 1, if not, then all that should

	//			remain is a number (the answer) and you're done

	//			eg. this stack ends with "9"

	//

	//		for this particular case, the stack contains the indices to positions in the vector

	//		and the intermediate answers are stored back in the same vector. (a clone, the original

	//		is kept for later use of course)

	//		this method uses two stacks. as a part of the process of searching for tokens to operate

	//		on, it will pop off objects and push them into a storage stack for later retrieval.

	//

	//		The code in c++ looks a lot cleaner than this because java requires me to cast every

	//		single object before i can access it's methods. this is annoying. oh well.
	int sym, op1, op2;
	Stack bf;
	for( int i = 0;i < numEQ;i++ ) {
		if( !used[i] ) {
			continue;
		}
		Stack ix = (Stack)s[i].clone();
		if( ix.empty() ) {
			break;
		}
		Vector a = GetVector( i );
		bf = new Stack();
		while( ( (Token)a.elementAt( ( (Integer)ix.peek() ).intValue() ) ).tokenType != Token.kConstant || !bf.empty() ) {
			while( ( (Token)a.elementAt( ( (Integer)ix.peek() ).intValue() ) ).tokenType != Token.kConstant ) {
				bf.push( ix.pop() );
			}
			if( ( (Token)a.elementAt( ( (Integer)bf.peek() ).intValue() ) ).tokenType == Token.kFunction ) {
				sym = ( (Integer)bf.pop() ).intValue();
				op1 = ( (Integer)ix.peek() ).intValue();
				( (Token)a.elementAt( op1 ) ).value = PerformFunction( (int)( (Token)a.elementAt( sym ) ).value, ( (Token)a.elementAt( op1 ) ).value );
				while( !bf.empty() ) {
					ix.push( bf.pop() );
				}
				continue;
			}
			bf.push( ix.pop() );
			if( ( (Token)a.elementAt( ( (Integer)ix.peek() ).intValue() ) ).tokenType == Token.kConstant ) {
				op1 = ( (Integer)bf.pop() ).intValue();
				sym = ( (Integer)bf.pop() ).intValue();
				op2 = ( (Integer)ix.peek() ).intValue();
				( (Token)a.elementAt( op2 ) ).value = PerformOperator( (int)( (Token)a.elementAt( sym ) ).value, ( (Token)a.elementAt( op1 ) ).value, ( (Token)a.elementAt( op2 ) ).value );
				while( !bf.empty() ) {
					ix.push( bf.pop() );
				}
			}
			else {
				bf.push( ix.pop() );
			}
		} //end while
			if( a.size() > 0 && used[i] ) {
				calculated[i] = ( (Token)a.elementAt( ( (Integer)ix.peek() ).intValue() ) ).value;
			}
	} //end for
}

void PrintStacks() {
	System.out.print( "The stacks are:" );
	Stack temp;
	int k, l;
	for( int i = 0;i < numEQ;i++ ) {
		if( s[i] == null ) {
			break;
		}
		temp = (Stack)s[i].clone();
		l = temp.size();
		System.out.print( "\n" + i + ").\t" );
		System.out.print( l + "\t" );
		for( int j = 0;j < l;j++ ) {
			k = ( (Integer)temp.pop() ).intValue();
			System.out.print( ( (Token)v[i].elementAt( k ) ).shortString() );
		}
	}
	edu.umn.ecology.populus.fileio.Logging.log();
}

/*************************************************
 *									Helper Methods  							 *
 *************************************************/

Vector GetVector( int whichVector ) {
	Vector a = new Vector();
	for( int i = 0;i < v[whichVector].size();i++ ) {
		a.addElement( ( (Token)v[whichVector].elementAt( i ) ).clone() );
		if( ( (Token)a.elementAt( i ) ).tokenType == Token.kParameter ) {
			( (Token)a.elementAt( i ) ).setToken( Token.kConstant, initial[(int)( (Token)a.elementAt( i ) ).value] );
		}
	}
	return a;
}

void PrintVectors( int left, int right ) {
	if( left < 0 ) {
		return ;
	}
	for( int i = 0;i < v.length;i++ ) {
		if( v[i] != null ) {
			System.out.print( i + ").\t" );
			if( right > v[i].size() ) {
				break;
			}
			for( int j = left;j <= right;j++ ) {
				System.out.print( ( (Token)v[i].elementAt( j ) ).shortString() );
			}
		}
		edu.umn.ecology.populus.fileio.Logging.log();
	}
	edu.umn.ecology.populus.fileio.Logging.log();
}

double PerformOperator( int sym, double op1, double op2 ) {
	switch( sym ) {
	case Token.kExponent:
		return Math.pow( op1, op2 );

	case Token.kMultiply:
		return op1 * op2;

	case Token.kDivide:
		return op1 / op2;

	case Token.kModulo:
		return op1 % op2;

	case Token.kPlus:
		return op1 + op2;

	case Token.kMinus:
		return op1 - op2;

	case Token.kMaximum:
		return Math.max( op1, op2 );

	case Token.kMinimum:
		return Math.min( op1, op2 );

	case Token.kSigFig:
		return NumberMath.roundSig( op1, (int)op2, NumberMath.NORMAL );

	case Token.kRandom:
		double r = getRandom( op1, op2 );
		edu.umn.ecology.populus.fileio.Logging.log( initial[0] + ":\t" + r );
		return r;

	default:
		return 0d;
	}
}

double PerformFunction( int sym, double op ) {
	switch( sym ) {
	case Token.kSine:
		return Math.sin( op );

	case Token.kCosine:
		return Math.cos( op );

	case Token.kTangent:
		return Math.tan( op );

	case Token.kArcSine:
		return Math.asin( op );

	case Token.kArcCosine:
		return Math.acos( op );

	case Token.kArcTangent:
		return Math.atan( op );

	case Token.kLn:
		return Math.log( op );

	case Token.kFactorial:
		return DoFactorial( Math.abs( op ) );

	case Token.kAbsolute:
		return Math.abs( op );

	case Token.kIntPart:
		return (int)op;

	case Token.kFracPart:
		return op - (int)op;

	default:
		return 0d;
	}
}
}
