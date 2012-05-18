package edu.umn.ecology.populus.eegg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LPuzzlePanel extends JPanel {
	LPuzzleModel model = null;
	int hoverPiece = -1;
	Color midColor = Color.black;
	boolean isWon = false;
	int animTime = 0;

	public LPuzzlePanel() {
		init();
		setModel(LPuzzleModel2.createNewModel());
	}
	private void init() {
		addMouseHandler();
	}
	public void setModel(LPuzzleModel model) {
		this.model = model;
	}
	public LPuzzleModel getModel() {
		return model;
	}

	//////////////
	// GRAPHICS //
	//////////////
	protected void paintComponent(Graphics g) {
		if (!isWon) {
			for (int i=0; i<8; i++)
				paintPiece(i, g);
			paintMiddle(g);
		} else {
			g.setColor(Color.white);
			g.fillRect(0, 0, sX(1.0), sY(1.0));
			g.setColor(Color.darkGray);
         int xpos = sX(0.5);
         int ypos = sY(0.5);
         String s = "You have earned my respect.";
         xpos -= g.getFontMetrics().stringWidth(s)/2;
         g.drawString(s, xpos, ypos);
		}
	}
	private void paintPiece(int where, Graphics g) {
		Color c = model.getColor(where);
		Polygon p = getPolygon(where);
		if (where == hoverPiece) {
			c = c.brighter();
		}
		g.setColor(c);
		g.fillPolygon(p);
	}
	private void paintMiddle(Graphics g) {
		//Clear middle
		g.setColor(Color.white);
		g.fillPolygon(getPolygon(LPuzzleDirections.kC));

		//Draw circle face
		int w = getSize().width/6;
		int h = getSize().height/6;
		g.setColor(midColor);
		g.drawOval(sX(.5)-w, sY(.5)-h, 2*w, 2*h);

		//Draw eyes
		g.fillOval(sX(5.0/12), sY(11.0/24), sX(1.0/24), sY(1.0/24));
		g.fillOval(sX(13.0/24), sY(11.0/24), sX(1.0/24), sY(1.0/24));

		//Draw smile
		switch (model.getScore()) {
			default:
				g.drawLine(sX(11.0/24), sY(7.0/12), sX(13.0/24), sY(7.0/12));
				break;
			case 1:
				g.drawLine(sX(10.0/24), sY(7.0/12), sX(14.0/24), sY(7.0/12));
				break;
			case 4:
				g.drawLine(sX(10.0/24), sY(13.0/24), sX(10.0/24), sY(7.0/12));
				g.drawLine(sX(14.0/24), sY(13.0/24), sX(14.0/24), sY(7.0/12));
			case 3:
				g.drawLine(sX(10.0/24), sY(7.0/12), sX(14.0/24), sY(7.0/12));
			case 2:
				g.drawLine(sX(10.0/24), sY(14.0/24), sX(11.0/24), sY(15.0/24));
				g.drawLine(sX(11.0/24), sY(15.0/24), sX(13.0/24), sY(15.0/24));
				g.drawLine(sX(13.0/24), sY(15.0/24), sX(14.0/24), sY(14.0/24));
				break;
		}
	}
	private int sc(double r, int scale) {
		int nr = (int) (scale * r);
		return nr;
	}
	private int[] sc(double[] r, int scale) {
		int[] nx = new int[r.length];
		for (int i=r.length-1; i>=0; i--)
			nx[i] = sc(r[i], scale);
		return nx;
	}
	private int sX(double x) {
		return sc(x, getSize().width);
	}
	private int sY(double y) {
		return sc(y, getSize().height);
	}
	private int[] sX(double[] x) {
		return sc(x, getSize().width);
	}
	private int[] sY(double[] y) {
		return sc(y, getSize().height);
	}
	static double[][][] kPoints = {
			{{1.0,0.0,1.0/6,5.0/6},{0.0,0.0,1.0/6,1.0/6}},//N
			{{0.0,0.0,1.0/6,1.0/6},{0.0,1.0,5.0/6,1.0/6}},//W
			{{0.0,1.0,5.0/6,1.0/6},{1.0,1.0,5.0/6,5.0/6}},//S
			{{1.0,1.0,5.0/6,5.0/6},{1.0,0.0,1.0/6,5.0/6}},//E

			{{1.0/2,1.0/6,1.0/6},{1.0/6,1.0/6,1.0/2}},//NW
			{{1.0/6,1.0/6,1.0/2},{1.0/2,5.0/6,5.0/6}},//SW
			{{1.0/2,5.0/6,5.0/6},{5.0/6,5.0/6,1.0/2}},//SE
			{{5.0/6,5.0/6,1.0/2},{1.0/2,1.0/6,1.0/6}},//NE

			{{5.0/6,.5,1.0/6,.5},{.5,1.0/6,.5,5.0/6}}};//C

	private Polygon getPolygon(int where) {
		int[] xp = sX(kPoints[where][0]);
		int[] yp = sY(kPoints[where][1]);
		/*
		edu.umn.ecology.populus.fileio.Logging.log("Where is " + where);
		edu.umn.ecology.populus.fileio.Logging.log("x is " + kPoints[where][0]);
		edu.umn.ecology.populus.fileio.Logging.log("y is " + kPoints[where][1]);
		edu.umn.ecology.populus.fileio.Logging.log("xp is " + xp.length);
		edu.umn.ecology.populus.fileio.Logging.log("yp is " + yp.length);
		*/
		return new Polygon(xp, yp, xp.length);
	}

	//////////////////////
	// USER INTERACTION //
	//////////////////////
	private int getWhere(int x, int y) {
		for (int i=0; i<8; i++) {
			if (getPolygon(i).contains(x, y))
				return i;
		}
		return -1;
	}
	int whereLast=-1;
	private void addMouseHandler() {
		this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					userClicked(getWhere(e.getX(), e.getY()));
				}
				public void mouseExited(MouseEvent e) {
					whereLast=-1;
					userHovered(-1);
				}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e) {
					int whereNow = getWhere(e.getX(), e.getY());
					if (whereNow != whereLast) {
						whereLast = whereNow;
						userHovered(whereNow);
					}
				}
		});
	}
	private void removeMouseHandler() {
		MouseListener[] l = this.getMouseListeners();
		for (int i=0; i<l.length; i++)
			this.removeMouseListener(l[i]);
		MouseMotionListener[] ml = this.getMouseMotionListeners();
		for (int i=0; i<ml.length; i++)
			this.removeMouseMotionListener(ml[i]);
	}
	private void userClicked(int where) {
		if (where == -1)
			return; //invalid click
		int correct = model.guess(where);
		if (correct == where) {
			midColor = Color.green;
			if (model.getScore() >= 5) {
				isWon = true;
				//We need to set a timer that does something cool here...
				/*
				if (animTime < 100) {
				animTime++;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) { }
				repaint();
				*/
			}

		} else {
			midColor = Color.red;
		}
		repaint();
	}
	/** Called when users move their mouse over a new area */
	private void userHovered(int where) {
		hoverPiece = where;
		repaint();
	}
}
