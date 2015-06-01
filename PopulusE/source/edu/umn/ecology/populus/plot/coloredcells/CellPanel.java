package edu.umn.ecology.populus.plot.coloredcells;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class CellPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4834704545375034048L;
	int numDivisions;
	String[] labels;
	int[] blockStarts;
	double[] demarcations;
	CellController cc;
	Cell[][] cells;
	CellPalette cellColors;
	BufferedImage bi;
	int iw, ih;
	final int legH = 40;
	Rectangle r = new Rectangle();

	CellPanel(){
		super();
		try  {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public CellPanel(CellController ref,double[][] values, String[] strings, double[] demarcations, CellPalette colors){
		this();
		cc = ref;
		cellColors = colors;
		numDivisions = Math.min(demarcations.length+1,strings.length);
		cells = new Cell[values.length][values[0].length];

		iw = CellDefaults.kWidth*cells.length;
		ih = CellDefaults.kHeight*cells[0].length+legH;
		bi = new BufferedImage(iw,ih,BufferedImage.TYPE_INT_RGB);

		this.demarcations = demarcations;
		setValues(values,strings,demarcations);
		setDivisions();
	}

	public void setValues(double[][] values){
		if(values.length != cells.length || values[0].length != cells[0].length)
			cells = new Cell[values.length][values[0].length];

		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[0].length; j++)
				if(cells[i][j] == null)
					cells[i][j] = new Cell(values[i][j], getColor(values[i][j]));
				else
					cells[i][j].setCell(values[i][j], getColor(values[i][j]));

		repaint();
	}

	public void setValues(double[][] values, String[] strings, double[] demarcations){
		this.demarcations = demarcations;
		labels = strings;
		setValues(values);
	}

	public double getValue(int r, int c){
		if(r>=cells.length||c>cells[0].length||r<0||c<0) return -1d;
		return cells[r][c].getValue();
	}
	
	void updateImageSize(){
		iw = CellDefaults.kWidth*cells.length;
		ih = CellDefaults.kHeight*cells[0].length+legH;
		bi = new BufferedImage(iw,ih,BufferedImage.TYPE_INT_RGB);
	}

	void setDivisions(){
		blockStarts = new int[numDivisions+1];
		double tempW = CellDefaults.kWidth*cells.length;
		double blockW = tempW/((double)numDivisions);
		for(double i = 0; i < numDivisions; i++)
			blockStarts[(int)i] = (int)(i*blockW+0.5);
		blockStarts[numDivisions] = (int)tempW;
	}

	public void prepNextImage(){
		final int kW = CellDefaults.kWidth;
		final int kH = CellDefaults.kHeight;
		int w = kW*cells.length;
		int h = kH*cells[0].length;
		int shift=0;

		if(w != iw || (h+legH)!=ih){
			updateImageSize();
			setDivisions();
		}
		Graphics2D g = bi.createGraphics();
		g.setFont(new Font("Dialog",Font.PLAIN,10));

		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[0].length; j++){
				g.setColor(cells[i][j].getColor());
				g.fillRect(i*kW,j*kH,kW,kH);
			}

		for(int i = 0; i < blockStarts.length; i++){
			if((i+1) < blockStarts.length){
				g.setColor(cellColors.colors[i]);
				g.fillRect(blockStarts[i],h,blockStarts[i+1]-blockStarts[i],legH-1);
				g.setColor(cellColors.textColor);

				if(cellColors.textFormat==CellPalette.kCenter){
					shift = blockStarts[i+1]-blockStarts[i]-g.getFontMetrics().stringWidth(labels[i]);
					shift = (int)(((double)shift)/2+0.5);
				} else
					shift = 5;
				g.drawString(labels[i],blockStarts[i]+shift,h+25);
			}
			g.setColor(cellColors.outline);
			if(i<(blockStarts.length-1))
				g.drawLine(blockStarts[i],h,blockStarts[i],h+legH-1);
			else
				g.drawLine(blockStarts[i]-1,h,blockStarts[i]-1,h+legH-1);
		}
		g.drawLine(0,0,w-1,0);
		g.drawLine(w-1,0,w-1,h);
		g.drawLine(0,0,0,h);
		g.drawLine(0,h,blockStarts[numDivisions],h);
		g.drawLine(blockStarts[numDivisions],h+legH-1,0,h+legH-1);
	}

	public void paint(Graphics gr){
	//the r,b edges surrender one pixel width to the outline so that the total
	//width and hight are kWidth*cells.length, and kHeight*cells[0].length
		//setDivisions();
		prepNextImage();
		//BufferedImage bic = bi.getSubimage(0,0,bi.getWidth(),bi.getHeight());
		gr.setColor(cellColors.background);
		r.setBounds(gr.getClipBounds());
		gr.fillRect(r.x,r.y,r.width,r.height);
		int w = CellDefaults.kWidth*cells.length;
		int dw = getWidth();
		dw -= w;
		dw /= 2;
		gr.drawImage(bi,dw,0,this);
	}

	Color getColor(double v){
		int i;
		for(i=0; i<cellColors.colors.length && i<numDivisions-1; i++){
			if(v<=demarcations[i])
				return cellColors.colors[i];
		}
		return cellColors.colors[i];
	}

	private void jbInit() throws Exception {
		this.addMouseListener(new java.awt.event.MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				this_mouseClicked(e);
			}
		});
	}

	void this_mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int dw = getWidth();
		dw -= CellDefaults.kWidth*cells.length;
		dw /= 2;
		x -= dw;
		int r = x/CellDefaults.kWidth;
		int c = y/CellDefaults.kHeight;

		if(e.getClickCount()==2){
			CellEditPanel cep = new CellEditPanel(this,r,c,getValue(r,c));
			cep.setVisible(true);
		} else {
			Graphics g = this.getGraphics();
			g.setColor(Color.white);
			double d = getValue(r,c);
			if(d==-1d) return;
			g.drawString(dToS(d),e.getX(),e.getY());
  	}
	}

	public void modifyValue(int r, int c, double newValue){
		cells[r][c] = new Cell(newValue, getColor(newValue));
		cc.modifyValue(r,c,newValue);
		repaint();
	}

	String dToS(double v){
		int temp;
		if(v<100d){
			if(v==0) return "Zero";
			if(v<1e-7) return "Very Small";
			if(v<1e-2) return "Small";
			temp = (int)(v*100);
			v = temp;
			v /= 100;
			return Double.toString(v);
		} else {
			if(v >= Integer.MAX_VALUE) return "Very Large";
			return Integer.toString((int)v);
		}
	}
}
