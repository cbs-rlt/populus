package edu.umn.ecology.populus.constants;

import edu.umn.ecology.populus.math.RungeKuttaRec;

public class RungeKuttaDefaults {
   public static int dMaxiter = 2000;
   public static double dSSERR = 1e-5; //a setting of 0 just means that it will do dMaxiter points
   public static double dSSMinDur = 10.0;
   public static double dEPS = 1e-6;
   public static int mode = RungeKuttaRec.RK4QC;
   public static double h = 0.2;
}
