package edu.umn.ecology.populus.visual;

import java.awt.*;
import javax.swing.border.*;
import edu.umn.ecology.populus.core.PopPreferences;


public class OwnershipBorder implements Border { //, java.io.Serializable {
	private Color color = Color.black;
	private int thickness;

	public OwnershipBorder(Color borderColor) {
		this.color = borderColor;
                thickness = PopPreferences.getOwnershipBorderThickness();
	}
	public OwnershipBorder(Color borderColor, int thickness) {
		this.color = borderColor;
		this.thickness = thickness;
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Color oldColor = g.getColor();
		g.translate(x, y);
		g.setColor(color);
		for (int i = 0; i < thickness; i++) {
			g.drawLine(0, i, w-1, i);
		}
		g.translate(-x, -y);
		g.setColor(oldColor);
	}

	public Insets getBorderInsets(Component c) {
		return new Insets(thickness, 0, 0, 0);
	}

	public boolean isBorderOpaque() {
		return true;
	}



	public int getThickness() {

		return thickness;

	}

}
