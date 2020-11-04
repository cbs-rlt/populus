/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.ie;

import java.io.Serializable;

/**
 * If adding a new token type, remember to specify the operator precedence.
 */

class Token implements Serializable {
    private static final long serialVersionUID = 5744460858336585474L;

    public enum TokenEnum {
        kOperator(0),
        kConstant(1),
        kBracket(2),
        kFunction(3),
        kParameter(4),
        kInvalidTokenType(999);

        private int value;

        private TokenEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    public TokenEnum tokenType;
    public double value;

    //For kBracket
    public final static int kOpen = 0;
    public final static int kClose = 1;

    //For kOperator
    public final static int kSigFig = 0;
    public final static int kMinimum = 1;
    public final static int kMaximum = 2;
    public final static int kRandom = 3;
    public final static int kPlus = 4;
    public final static int kMinus = 5;
    public final static int kMultiply = 6;
    public final static int kDivide = 7;
    public final static int kModulo = 8;
    public final static int kExponent = 9;


    //For kFunction
    public final static int kSine = 0;
    public final static int kCosine = 1;
    public final static int kTangent = 2;
    public final static int kArcSine = 3;
    public final static int kArcCosine = 4;
    public final static int kArcTangent = 5;
    public final static int kLn = 6;
    public final static int kFactorial = 7;
    public final static int kAbsolute = 8;
    public final static int kIntPart = 9;
    public final static int kFracPart = 10;


    public void setToken(TokenEnum newTokenType, double newValue) {
        tokenType = newTokenType;
        value = newValue;
    }

    /**
     * order of precedence (lower is higher precedence)
     * 0. parentheses (not really considered)
     * 1. unary operators (sin,cos,tan,ln,!)
     * 2. ^
     * 3. *,/,%
     * 4. +,-
     * 5. everything else
     */

    public int getPrecedence() {
        if (tokenType == TokenEnum.kBracket) {
            return 0;
        }
        if (tokenType == TokenEnum.kFunction) {
            return 1;
        }
        if (tokenType == TokenEnum.kOperator) {
            switch ((int) value) {
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

    @Override
    public String toString() {
        String temp = "Type: ";
        int switcher = (int) value;
        switch (tokenType) {
            case kOperator -> {
                temp += "operator\nValue: ";
                switch (switcher) {
                    case kPlus -> temp += "+";
                    case kMinus -> temp += "-";
                    case kMultiply -> temp += "*";
                    case kDivide -> temp += "/";
                    case kExponent -> temp += "^";
                }
            }
            case kConstant -> {
                temp += "constant\nValue: ";
                temp += value;
            }
            case kBracket -> {
                temp += "bracket\nValue: ";
                switch (switcher) {
                    case kOpen -> temp += "(";
                    case kClose -> temp += ")";
                }
            }
            case kFunction -> {
                temp += "function\nValue: ";
                switch (switcher) {
                    case kSine -> temp += "sin";
                    case kCosine -> temp += "cos";
                    case kTangent -> temp += "tan";
                    case kLn -> temp += "ln";
                    case kFactorial -> temp += "!";
                    case kAbsolute -> temp += "|";
                }
            }
            default -> temp += "n/a";
        }
        temp += "\n";
        return temp;
    }

    public String shortString() {
        String temp = "";
        int switcher = (int) value;
        switch (tokenType) {
            case kOperator:
                switch (switcher) {
                    case kMinimum -> temp += "min";
                    case kMaximum -> temp += "max";
                    case kSigFig -> temp += "sigfig";
                    case kRandom -> temp += "random";
                    case kPlus -> temp += "+";
                    case kMinus -> temp += "-";
                    case kMultiply -> temp += "*";
                    case kModulo -> temp += "%";
                    case kDivide -> temp += "/";
                    case kExponent -> temp += "^";
                }
                break;

            case kConstant:
                temp += value;
                break;

            case kBracket:
                switch (switcher) {
                    case kOpen -> temp += "(";
                    case kClose -> temp += ")";
                }
                break;

            case kFunction:
                switch (switcher) {
                    case kSine -> temp += "sin";
                    case kCosine -> temp += "cos";
                    case kTangent -> temp += "tan";
                    case kLn -> temp += "ln";
                    case kAbsolute -> temp += "|";
                    case kFactorial -> temp += "!";
                    case kArcSine -> temp += "asin";
                    case kArcCosine -> temp += "acos";
                    case kArcTangent -> temp += "atan";
                    case kIntPart -> temp += "ipart";
                    case kFracPart -> temp += "fpart";
                }
                break;

            case kParameter:
                if (value > 0) {
                    temp += "N" + (int) value;
                } else {
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

    @Override
    public Object clone() {
        return new Token(tokenType, value);
    }

    public Token(TokenEnum tokenType, double value) {
        this.tokenType = tokenType;
        this.value = value;
    }
}
