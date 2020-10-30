/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.model.lvpptl;
import edu.umn.ecology.populus.math.*;
import edu.umn.ecology.populus.plot.*;
import edu.umn.ecology.populus.constants.ColorScheme;
import edu.umn.ecology.populus.plot.plotshapes.PlotArrow;
import java.util.*;

public class LVPPTLParamInfo implements BasicPlot {
	Integrator ig = null;
	Derivative deriv = null;
	short thetaType;
	double r1, C, d2, g, K, D, h, a, Th;
	double n0, p0;
	double gens, type;
	boolean isDD;
	boolean isLV;
	boolean vsTime;
	boolean isType2; //for LV
	static ResourceBundle res = ResourceBundle.getBundle( "edu.umn.ecology.populus.model.lvpptl.Res" );
	private String xCap = res.getString( "Time_b_i_t_" );
	private String yCap = res.getString( "Population_" ) + ColorScheme.getColorString( 0 ) + "<b><i>P </i></b></FONT>, " + ColorScheme.getColorString( 1 ) + "<i><b>N </></FONT> )";
	private String n1Cap = res.getString( "PPopulation_b_i_" ) + ColorScheme.getColorString( 0 ) + "P</> )";
	private String mCapNvsT, mCapN2vsN1;
	private String n2Cap = res.getString( "NPopulation_b_i_" ) + ColorScheme.getColorString( 1 ) + "N</> )";
	private final String TLmCapNvsT = res.getString( "_u0398_Logistic1" );
	private final String LVmCapN2vsN1 = res.getString( "Lotka_Volterra2" );
	private final String TLmCapN2vsN1 = res.getString( "_u0398_Logistic2" );
	private final String LVmCapNvsT = res.getString( "Lotka_Volterra1" );

	/** Theta Logistic */

	/*new LVPPTLParamInfo(this.buttonPvN.isSelected(), this.runningTimePanel1.getTime(),
   this.typeComboBox.getSelectedIndex() + 1,
   this.paramR.getDouble(), this.paramK.getDouble(), this.paramTheta.getDouble(),
   this.paramC.getDouble(), this.paramh.getDouble(),
   this.paramD.getDouble(), this.paramS.getDouble(),
   this.paramTLN0.getDouble(), this.paramTLP0.getDouble()
	 */

	public LVPPTLParamInfo( boolean pvn, double gens, int type, double r, double k, double theta, double c, double h, double d, double s, double n, double p ) {
		this.vsTime = !pvn;
		this.gens = (int)( gens + 0.5 ); //why is this done? it prevents a s-s setting...
		this.n0 = n;
		this.p0 = p;
		this.r1 = r;

		//this.d2 = r2;
		this.C = c;

		//this.g = c2;
		this.K = k;
		this.D = d;
		this.h = h;
		thetaType = (short)type;
		ig = new Integrator( new TLDeriv( type, r, k, theta, c, h, d, s, n, p ) );
		this.isLV = false;
	}

	@Override
	public BasicPlotInfo getBasicPlotInfo() {
		if( this.isLV ) {
			mCapNvsT = LVmCapNvsT;
			mCapN2vsN1 = LVmCapN2vsN1;
		}
		else {
			mCapNvsT = TLmCapNvsT;
			mCapN2vsN1 = TLmCapN2vsN1;
		}
		BasicPlotInfo bp;

		double[][][] points;
		double[] xlist;
		double[][] ylists;
		double[] initialConditions = new double[2];
		initialConditions[0] = n0;
		initialConditions[1] = p0;
		if( gens < 0 ) {
			ig.record.ss = true;
			ig.record.interval = false;
		}
		else {
			ig.record.tf = gens;
		}
		//      ig.record.mode = RungeKuttaRec.EULER;
		ig.doIntegration( initialConditions, 0.0, gens );
		xlist = ig.getX();
		ylists = ig.getY();
		double maxY = 0, maxX = 0;
		for( int i = 0;i < ylists[1].length;i++ ) {
			if( ylists[1][i] > maxY ) {
				maxY = ylists[1][i];
			}
		}
		for( int i = 0;i < ylists[0].length;i++ ) {
			if( ylists[0][i] > maxX ) {
				maxX = ylists[0][i];
			}
		}
		final double exCoeff = 1.2; //this is for how far beyond the max point the isoclines should extend
		double N = 0;
		if( vsTime ) {
			points = new double[2][2][];
			points[0][0] = xlist;
			points[0][1] = ylists[1];
			points[1][0] = xlist;
			points[1][1] = ylists[0];
			bp = new BasicPlotInfo( points, mCapNvsT, xCap, yCap );
			bp.vsTimeChars = new String[] {
					"N", "P"
			};
		} else {
			double[][] isocline1, isocline2;
			points = new double[3][][];

			if( this.isLV ) {
				if (isType2) {
					if( !isDD ) {
						isocline2 = new double[][] {
							{
								0, maxX * exCoeff
							},  {
								r1 / a, (r1 / a) + r1 * Th * maxX * exCoeff
							}
						};
					} else {
						int nsteps = 100;
						isocline2 = new double[2][nsteps];
						double currx = 0.0, stepsize = maxX * exCoeff / nsteps;
						for (int i=0; i<nsteps; i++) {
							isocline2[0][i] = currx;
							isocline2[1][i] = r1/a
									+ currx * (r1*Th - r1/(a*K))
									- currx*currx * (r1*Th/K);
							currx += stepsize;
						}
					}
					double predVal = d2 / (a * (g - d2*Th));
					isocline1 = new double[][] {
						{
							predVal, predVal
						},  {
							0, exCoeff * maxY
						}
					};
				} else {
					if( isDD ) {
						isocline2 = new double[][] {
							{
								0, maxX * exCoeff
							},  {
								r1 / C, r1 / C - r1 * maxX * exCoeff / ( C * K )
							}
						};
					}
					else {
						isocline2 = new double[][] {
							{
								0, maxX * exCoeff
							},  {
								r1 / C, r1 / C
							}
						};
					}
					isocline1 = new double[][] {
						{
							d2 / ( g * C ), d2 / ( g * C )
						},  {
							0, exCoeff * maxY
						}
					};
				}
			}
			else {

				//this is theta-logistic

				/*
            dN/dt = r1N(1-N/K) - f(N)P
            dP/dt = gP(f(N)-D)
            f(N) =
            type 1 = CN
            type 2 = CN/(1+hCN)
            type 3 = CN^2/(1+hCN^2)
				 */
				switch( thetaType ) {
				case 1:
					isocline1 = new double[][] {
						{
							D / C, D / C
						},  {
							0, maxY * exCoeff
						}
					};
					isocline2 = new double[][] {
						{
							0, maxX * exCoeff
						},  {
							r1 / C, ( -maxX * exCoeff / K + 1 ) * r1 / C
						}
					};
					break;

				case 2:
					isocline1 = new double[][] {
						{
							D / ( C * ( 1 - D * h ) ), D / ( C * ( 1 - D * h ) )
						},  {
							0, maxY * exCoeff
						}
					}; //P = r1/C-r1N/(CK)
					isocline2 = new double[2][100];
					for( int i = 0;i < 100;i++ ) {
						N = ( maxX * exCoeff / 100 ) * i;
						isocline2[0][i] = N;
						isocline2[1][i] = -h * N * N * r1 / K + ( h * r1 - r1 / ( K * C ) ) * N + r1 / C;
					}
					break;

				case 3:
					isocline1 = new double[][] {
						{
							Math.sqrt( D / ( C * ( 1 - D * h ) ) ), Math.sqrt( D / ( C * ( 1 - D * h ) ) )
						},  {
							0, maxY * exCoeff
						}
					}; //P = r1/C-r1N/(CK)
					isocline2 = new double[2][100];
					for( double i = 0.4;i < 100;i++ ) {
						N = ( maxX * exCoeff / 100 ) * i;
						isocline2[0][(int)i] = N;
						isocline2[1][(int)i] = r1 * ( 1 - N / K ) * ( 1 + h * C * N * N ) / ( C * N );
					}
					break;

				default:
					System.err.print( res.getString( "Not_a_valid_theta" ) );
					isocline1 = new double[][] {
						{
							0, 0
						},  {
							0, 0
						}
					}; //P = r1/C-r1N/(CK)
					isocline2 = new double[][] {
						{
							0, 0
						},  {
							0, 0
						}
					};
					break;
				}
			}
			points[0] = isocline1;
			points[1] = isocline2;
			points[2] = ylists;
			bp = new BasicPlotInfo( points, mCapN2vsN1, n2Cap, n1Cap );
			PlotArrow.addArrow( bp, 2 );
			PlotArrow.addFletching( bp, 2 );
		}
		bp.setYMin( 0.0 );
		return bp;
	}

	/** Lotka-Volterra */

	/*
   new LVPPTLParamInfo(this.buttonPvN.isSelected(), this.runningTimePanel1.getTime(),
   this.ddPreyBox.isSelected(), this.paramPreyK.getDouble(),
   this.paramLVN0.getDouble(), this.paramr1.getDouble(), this.paramC1.getDouble(),
   this.paramLVP0.getDouble(), this.paramr2.getDouble(), this.paramC2.getDouble()
	 */

	public LVPPTLParamInfo( boolean pvn, double gens, boolean dd, boolean isType2, double k, double n0, double r1, double c1, double p0, double r2, double c2, double a, double th ) {
		this.vsTime = !pvn;
		this.gens = gens;

		//values for isoclines:
		this.r1 = r1;
		this.d2 = r2;
		this.C = c1;
		this.g = c2;
		this.K = k;
		this.a = a;
		this.Th = th;

		//do i need to record these variables???
		this.n0 = n0;
		this.p0 = p0;
		this.isDD = dd;
		ig = new Integrator( new LVDeriv( r1, c1, r2, c2, isDD, k, isType2, a, th ) );
		this.isType2 = isType2;
		this.isLV = true;
	}
}
