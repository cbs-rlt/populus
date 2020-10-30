package edu.umn.ecology.populus.plot.threedgrapher;

import java.awt.*;

/**
 * this class contains the information for drawing a line on a 3D graph. however, this could actually
 * be used to do more than just lines. essentially, it will hold a collection of points and verticies,
 * so this could represent other graphical objects as well.
 * <p>Title: Populus</p>
 * <p>Description: ecological models</p>
 * <p>Copyright: Copyright (c) 2002, 2015</p>
 * <p>Company: University of Minnesota</p>
 * @author Amos Anderson
 * @version 5.2
 */
public class Line3D {
	float vert[];
	int tvert[];
	int nvert, maxvert;
	int con[];
	int ncon, maxcon;
	boolean transformed;
	boolean isDiscrete = true;
	boolean labelT = true;
	boolean drawLeading = false;
	Matrix3D mat;

	Color lineColor = Color.black;
	Color dotColor = Color.white;

	/**
	 * i may need to set something up to ensure that mat never gets writen over
	 */
	Line3D(){
	}

	/**
	 * use this constructor if you have a data set that is organized into a double array
	 * where the first array is the coordinate index, and the second array is the coordinate, e.g.
	 * data[1][200] would be the 200th y-coordinate
	 * @param data
	 */
	Line3D(double[][] data){
		int vertIndex;
		for(int i = 0; i < data[0].length; i++){
			vertIndex = addVert((float)data[0][i],(float)data[1][i],(float)data[2][i]);
			if(i>0) add(vertIndex-1,vertIndex);
		}
	}

	public boolean isDrawable(){
		return vert != null && nvert > 0;
	}

	public void paint(Graphics g, Matrix3D mat, int numPointsToDraw){
		Graphics2D g2d = (Graphics2D)g;
		this.mat = mat;

		if (vert == null || nvert <= 0)
			return;

		transform();
		int lim = ncon;
		int c[] = con;
		int v[] = tvert;
		if (lim <= 0 || nvert <= 0)
			return;

		//drawLines(g2d,lim,c,v);
		drawLines(g2d,numPointsToDraw,c,v);
		if(isDiscrete)
			drawPoints(g2d,v);
		drawStartIndicator(g2d,v);
		if(drawLeading)
			drawLeadingMarks(g,numPointsToDraw);
	}

	/**
	 * the lim parameter now refers to the number of points to draw lines between,
	 * instead of the previous number of lines. as a consequence, the for loop needs
	 * one less index (i.e. the number of lines between N points is N-1)
	 * @param g
	 * @param lim
	 * @param c
	 * @param v
	 */
	void drawLines(Graphics g, int lim, int[] c, int[] v){
		g.setColor(lineColor);
		for (int i = 0; i < lim-1; i++) {
			int T = c[i];
			int p1 = ((T >> 16) & 0xFFFF) * 3;
			int p2 = (T & 0xFFFF) * 3;
			g.drawLine(v[p1],v[p1+1],v[p2],v[p2+1]);
		}
	}

	void drawPoints(Graphics g, int[] v){
		g.setColor(dotColor);
		int r = 2;

		for(int i = 0; i < nvert*3; i+=3)
			if(vert[i] >= 0 && vert[i+1] >=0 && vert[i+2] >= 0 ){
				g.fillOval(v[i]-r,v[i+1]-r,2*r,2*r);
			}
	}

	/**
	 * right now just draws a "t=0" at the starting point
	 */
	void drawStartIndicator(Graphics g, int[] v){
		int r = 2;

		if(!labelT) return;
		g.setColor(lineColor);
		g.setFont(new Font("Dialog",Font.BOLD,12));
		g.fillOval(v[0]-r,v[1]-r,2*r,2*r);
		g.drawString("t=0",v[0]-25,v[1]+10);
	}

	/**
	 * this draws the little circles that lead the data "trail" as it is plotted live. if the graph isn't live,
	 * then a circle will appear at the last point.
	 * @param g
	 */
	void drawLeadingMarks(Graphics g, int numPoints){
		int[] d = new int[] {8,6,4};
		int index;
		g.setColor(lineColor);
		index = 0;
		for(int j=0; j<3; j++){
			if(tvert.length > (3*numPoints-index) && (3*numPoints-index) >= 0){
				g.fillOval((int)(tvert[3*numPoints-index] - 0.5*d[j]), (int)(tvert[3*numPoints-index+1] - 0.5*d[j]), d[j], d[j]);
			}
			index += 30;
		}
	}

	public float[] matchPoint(int x, int y){
		float[] highestZ = new float[5];
		for(int i = 0; i < nvert*3; i+=3)
			if(distance(tvert[i],tvert[i+1],x,y) <= 3)
				if((highestZ[4] == 0) || (vert[i+2] > highestZ[2])){
					return new float[] {vert[i],vert[i+1],vert[i+2],tvert[i]+5,tvert[i+1]+5};
				}
		return null;
	}

	public float[] findBB(){
		if (nvert <= 0)
			return null;
		float v[] = vert;
		float xmin = 0; float xmax = 0; float ymin = 0; float ymax = 0; float zmin = 0; float zmax = 0;
		for (int i = nvert * 3; (i -= 3) >= 0;) {
			Float x = new Float(v[i]);
			if ((x.floatValue() < xmin) && !x.isInfinite())
				xmin = x.floatValue();
			if ((x.floatValue() > xmax) && !x.isInfinite())
				xmax = x.floatValue();
			Float y = new Float(v[i + 1]);
			if ((y.floatValue() < ymin) && !y.isInfinite())
				ymin = y.floatValue();
			if ((y.floatValue() > ymax) && !y.isInfinite())
				ymax = y.floatValue();
			Float z = new Float(v[i + 2]);
			if ((z.floatValue() < zmin) && !z.isInfinite())
				zmin = z.floatValue();
			if ((z.floatValue() > zmax) && !z.isInfinite())
				zmax = z.floatValue();
		}
		return new float[] {xmax,xmin,ymax,ymin,zmax,zmin};
	}

	public void addPoint(Point3D p){
		int vertIndex;
		vertIndex = addVert(p.x(),p.y(),p.z());
		if(vertIndex > 0)
			add(vertIndex-1,vertIndex);
	}

	/** Add a vertex to this model */
	public int addVert(float x, float y, float z) {
		int i = nvert;
		if (i >= maxvert)
			if (vert == null) {
				maxvert = 100;
				vert = new float[maxvert * 3];
			} else {
				maxvert *= 2;
				float nv[] = new float[maxvert * 3];
				System.arraycopy(vert, 0, nv, 0, vert.length);
				vert = nv;
			}
		i *= 3;
		vert[i] = x;
		vert[i + 1] = y;
		vert[i + 2] = z;
		return nvert++;
	}

	/** Add a line from vertex p1 to vertex p2 */
	public void add(int p1, int p2) {
		int i = ncon;
		if (p1 >= nvert || p2 >= nvert)
			return;
		if (i >= maxcon)
			if (con == null) {
				maxcon = 100;
				con = new int[maxcon];
			} else {
				maxcon *= 2;
				int nv[] = new int[maxcon];
				System.arraycopy(con, 0, nv, 0, con.length);
				con = nv;
			}
		if (p1 > p2) {
			int t = p1;
			p1 = p2;
			p2 = t;
		}
		con[i] = (p1 << 16) | p2;
		ncon = i + 1;
	}

	/** Transform all the points in this model */
	void transform() {
		if (transformed || nvert <= 0)
			return;
		if (tvert == null || tvert.length < nvert * 3)
			tvert = new int[nvert*3];
		mat.transform(vert, tvert, nvert);
		transformed = true;
	}


	/** eliminate duplicate lines
	 * we're never going to have duplicate lines in Populus... kept here for "posterity"*/
	void compress() {
		int limit = ncon;
		int c[] = con;
		edu.umn.ecology.populus.math.Routines.quickSort(con, 0, ncon - 1);

		int d = 0;
		int pp1 = -1;
		for (int i = 0; i < limit; i++) {
			int p1 = c[i];
			if (pp1 != p1) {
				c[d] = p1;
				d++;
			}
			pp1 = p1;
		}
		ncon = d;
	}

	/** Distance formula */
	double distance(int x1,int y1,int x2,int y2){
		return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}

	public void setColors(Color line, Color dots){
		this.lineColor = line;
		this.dotColor = dots;
	}

	public void setLabelT(boolean b){
		labelT = b;
	}

	public void setIsDiscrete(boolean b){
		isDiscrete = b;
	}

	public void setDrawLeading(boolean b){
		drawLeading = b;
	}
}
