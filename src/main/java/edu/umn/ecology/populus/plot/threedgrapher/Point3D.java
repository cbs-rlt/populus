/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.plot.threedgrapher;

/** Class to simplify the handling of 3D points.*/
class Point3D{
	/**The true value of the point*/
	final public float v[] = new float[3];
	/**To hold the transformation values*/
	public int tv[] = new int[3];

	Point3D(float x,float y, float z){
		v[0] = x;
		v[1] = y;
		v[2] = z;
	}
	float x(){
		return v[0];
	}
	float y(){
		return v[1];
	}
	float z(){
		return v[2];
	}
	int tx(){
		return tv[0];
	}
	int ty(){
		return tv[1];
	}
	int tz(){
		return tv[2];
	}
}