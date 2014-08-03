package edu.umn.ecology.populus.visual;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;


/** Simple Vertical Layout with no frills.
 * 
 * @author Lars
 * 
 * TODO: add alignment?
 */
public class SimpleVFlowLayout implements LayoutManager, java.io.Serializable {
	private static final long serialVersionUID = 1633523923167242992L;
	private enum SizeMethod {MIN, PREF, MAX};
	
	private int hgap, vgap;
	private boolean hfill;

	public SimpleVFlowLayout(int hgap, int vgap, boolean hfill) {
		this.hgap = hgap;
		this.vgap = vgap;
		this.hfill = hfill;
	}

	public SimpleVFlowLayout() {
		this(5, 5, true);
	}
	
	public int getHgap() {
		return hgap;
	}

	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	public int getVgap() {
		return vgap;
	}

	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		//Unused
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		//Unused
	}

	private Dimension getLayoutSizeMulti(Container parent, SizeMethod sizeMethod) {
		synchronized (parent.getTreeLock()) {
			Dimension d = new Dimension();
			
			//Add in components
			for (Component c : parent.getComponents()) {
				Dimension cd;
				switch (sizeMethod) {
				case MIN:
					cd = c.getMinimumSize();
					break;
				case MAX:
					cd = c.getMaximumSize();
					break;
				case PREF: default:
					cd = c.getPreferredSize();
				}
				d.width = Math.max(d.width, cd.width);
				d.height += cd.height + getVgap();
			}
			
			//Add in insets and gaps
			Insets insets = parent.getInsets();
	        d.width += insets.left + insets.right + getHgap()*2;
	        d.height += insets.top + insets.bottom + getVgap()*2;
			return d;
		}
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSizeMulti(parent, SizeMethod.PREF);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSizeMulti(parent, SizeMethod.MIN);
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			Insets insets = parent.getInsets();
			int y = insets.top + getVgap();
			int x = insets.left + getHgap();
			int fillwidth = parent.getWidth() - (x + insets.right + this.hgap);
			for (Component c : parent.getComponents()) {
				Dimension cd = c.getPreferredSize();
				if (hfill) cd.width = fillwidth;
				c.setSize(cd);
				c.setLocation(x, y);
				y += cd.height + getHgap();
			}
		}
	}

}
