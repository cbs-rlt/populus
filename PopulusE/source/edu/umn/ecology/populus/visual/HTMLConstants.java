package edu.umn.ecology.populus.visual;

public interface HTMLConstants {
   //in the process of code beautifying, the order of these was changed, that's why the values don't
   //seem to go in any order
   public static final int NORMAL            = 0;
   public static final int SIDEWAYS          = 1;
   public static final int STACKED           = 2;
   public static final int UP_TO_DOWN        = SIDEWAYS;
   public static final int UPSIDEDOWN        = STACKED;
   public static final int DOWN_TO_UP        = SIDEWAYS | STACKED;

   public static final int BAR_BEGIN         = 10;
   public static final int ALL_END           = 12;
   public static final int COLOR_BEGIN       = 4;
   public static final int ITALIC_BEGIN      = 6;
   public static final int BOLD_BEGIN        = 8;
   public static final float SCRIPT_SHRINKAGE = 0.8f;
   public static final int COLOR_END         = 5;
   public static final char TAG_BEGIN        = '<';
   public static final int ITALIC_END        = 7;
   public static final char TAG_END          = '>';
   public static final int BOLD_END          = 9;
   public static final int SUP_BEGIN         = 0;
   public static final int BAR_END           = 11;
   public static final int SUP_END           = 1;
   public static final int INVALID           = -1;
   public static final int SUB_BEGIN         = 2;
   public static final int SUB_END           = 3;
   public static final int LESS_THAN         = 13;
   public static final int GREATER_THAN      = 14;
   public static final int SPECIAL_CHAR      = 15;
   //public static final int NEW_LINE          = 16; not implemented
}
