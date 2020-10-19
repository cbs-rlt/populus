/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.threedgrapher;

import java.awt.*;
import edu.umn.ecology.populus.math.NumberMath;


public class Plane3D {
	public static final int NONE = 0;
	public static final int isXY = 1;
	public static final int isXZ = 2;
	public static final int isYZ = 4;

	Point3D[][] grid;
	int last;
	Matrix3D mat;
	Color planeColor;
	Color backgroundColor;

	/**
	 * right now, <code>special</code> is used to indicate whether the graph is one of the
	 * grid panes and which one it is or not, because the 3 are handled differently.
	 */
	int special = 0;


	boolean zIsDiscrete = false;
	boolean xIsDiscrete = false;

	/* don't draw overlapping number labels or their lines*/
	boolean dontSqueezeNumbers = true;


	public Plane3D(Point3D[][] g, int special){
		grid = g;
		last = grid.length - 1;
		this.special = special;
	}

	void addLine(Line3D l){
	}

	public void transform(Matrix3D mat){
		mat.transform(grid);
	}

	void paint(Graphics2D g, Matrix3D mat){
		if(grid == null) return;
		int numLines = grid.length-1;
		transform(mat);
		FontMetrics fm = g.getFontMetrics();
		final int dy = g.getFont().getSize()-2;
		String s = "";
		int dx=0;
		int xi=0;
		int yi=0;

		drawFacets(g);
		drawGrid(g);

		g.setColor(Color.black);
		g.setFont(new Font("Dialog",Font.PLAIN,12));
		if(special != NONE)
			for(int i = grid.length-1 ; i >= 0 ; i--){//descending so that the last is ensured to be drawn
				switch(special){
				case isXY:
					if( Math.abs(grid[i][numLines].ty() - yi) < dy &&
							Math.abs(grid[i][numLines].tx() - xi) < dx){
						if(dontSqueezeNumbers) continue;
					}

					if(!xIsDiscrete)
						s = dToS(grid[i][0].x());
					else
						s = ""+(int)(grid[i][0].x()+0.5);
					g.drawString(s,grid[i][numLines].tx(),grid[i][numLines].ty()-3);
					break;
				case isXZ:
					if( Math.abs(grid[numLines][i].ty() - yi) < dy &&
							Math.abs(grid[numLines][i].tx() - xi) < dx){
						if(dontSqueezeNumbers) continue;
					}

					//this little if statement causes the axis labels to be integers (instead of doubles) for discrete
					if(!zIsDiscrete)
						s = dToS(grid[0][i].z());
					else
						s = ""+(int)(grid[0][i].z()+0.5);
					g.drawString(s,grid[numLines][i].tx()+5,grid[numLines][i].ty()+10);
					break;
				case isYZ:
					if( Math.abs(grid[i][numLines].ty() - yi) < dy &&
							Math.abs(grid[i][numLines].tx() - xi) < dx){
						if(dontSqueezeNumbers) continue;
					}
					s = dToS(grid[i][0].y());
					dx = fm.charsWidth(s.toCharArray(),0,s.length());
					g.drawString(s,grid[i][numLines].tx()-dx-5,grid[i][numLines].ty()+5);
					break;
				}

				if(special != isXZ){
					yi = grid[i][numLines].ty();
					xi = grid[i][numLines].tx();
				} else {
					yi = grid[numLines][i].ty();
					xi = grid[numLines][i].tx();
				}
				dx = fm.charsWidth(s.toCharArray(),0,s.length());
			}
	}

	void drawFacets(Graphics2D g){
		int[] x,y;
		int numLines = grid.length-1;
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		/*alpha = 1.0 means opaque, alpha = 0.0 means transparent*/
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.15f));
		g.setColor(Color.darkGray);
		/*
      for(int i = 0; i < numLines; i++)
         for(int j = 0; j < numLines; j++){
            x = new int[] {grid[i][j].tx(),grid[i+1][j].tx(),grid[i+1][j+1].tx(),grid[i][j+1].tx()};
            y = new int[] {grid[i][j].ty(),grid[i+1][j].ty(),grid[i+1][j+1].ty(),grid[i][j+1].ty()};
            //GradientPaint or TexturePaint
            //GradientPaint gp = new GradientPaint(x[0],y[0],Color.blue,x[3],y[3],Color.lightGray);
            //g.setPaint(gp);
            g.fillPolygon(x,y,4);
         }
		 */
		x = new int[] {grid[0][0].tx(),grid[numLines][0].tx(),grid[numLines][numLines].tx(),grid[0][numLines].tx()};
		y = new int[] {grid[0][0].ty(),grid[numLines][0].ty(),grid[numLines][numLines].ty(),grid[0][numLines].ty()};
		g.fillPolygon(x,y,4);
	}

	void drawGrid(Graphics2D g){
		int[] x,y;
		int numLines = grid.length-1;

		g.setComposite(AlphaComposite.SrcOver);
		g.setColor(planeColor);
		for(int i = 0; i < numLines; i++)
			for(int j = 0; j < numLines; j++){
				x = new int[] {grid[i][j].tx(),grid[i+1][j].tx(),grid[i+1][j+1].tx(),grid[i][j+1].tx()};
				y = new int[] {grid[i][j].ty(),grid[i+1][j].ty(),grid[i+1][j+1].ty(),grid[i][j+1].ty()};
				g.drawPolygon(x,y,4);
			}
	}

	/** Method to intercept the output of a double to the screen. This formats it
      for a better fit or for more information accordingly.*/
	String dToS(double v){
		int temp;
		if(v < 0.01){
			return NumberMath.roundSigScientific(v,3,0);
		} else if(v<10d){
			temp = (int)(v*100d+0.5d);
			v = temp;
			v /= 100;
			return Double.toString(v);
		} else {
			return Integer.toString((int)(v+0.5d));
		}
	}

	public Point3D getPoint(int i, int j){
		return grid[i][j];
	}

	public void setPlaneColor(Color c){
		this.planeColor = c;
	}

	public void setBackground(Color c){
		this.backgroundColor = c;
	}

	public void setDontSqueeze(boolean b){
		this.dontSqueezeNumbers = b;
	}

	public void setZIsDiscrete(boolean b){
		this.zIsDiscrete = b;
	}

	public void setXIsDiscrete(boolean b){
		this.xIsDiscrete = b;
	}
}