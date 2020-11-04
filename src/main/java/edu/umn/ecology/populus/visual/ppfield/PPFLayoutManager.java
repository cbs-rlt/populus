/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/
package edu.umn.ecology.populus.visual.ppfield;

import java.awt.*;

/**
 * I have not finished this yet, perhaps in future I will...
 */

public class PPFLayoutManager extends FlowLayout {
    /**
     *
     */
    private static final long serialVersionUID = -886584094839158031L;
    final PopulusParameterField ref;

    @Override
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {


			/*
      Insets insets = target.getInsets();
      int maxwidth = target.width - (insets.left + insets.right + hgap*2);
      int nmembers = target.getComponentCount();
      int x = 0, y = insets.top + vgap;
      int rowh = 0, start = 0;

      boolean ltr = target.getComponentOrientation().isLeftToRight();

      for (int i = 0 ; i < nmembers ; i++) {
      Component m = target.getComponent(i);
      if (m.visible) {
      Dimension d = m.getPreferredSize();
      m.setSize(d.width, d.height);

      if ((x == 0) || ((x + d.width) <= maxwidth)) {
      if (x > 0) {
      x += hgap;
      }
      x += d.width;
      rowh = Math.max(rowh, d.height);
      } else {
      moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, i, ltr);
      x = d.width;
      y += vgap + rowh;
      rowh = d.height;
      start = i;
      }
      }
      }
      moveComponents(target, insets.left + hgap, y, maxwidth - x, rowh, start, nmembers, ltr);
			 */
        }
    }

    public PPFLayoutManager(PopulusParameterField ref) {
        super(CENTER, 0, 0);
        this.ref = ref;
    }

	/*
private PopulusParameterField castToPPF(Component comp) {
if (!(comp instanceof PopulusParameterField)) {
return null;
}
return (PopulusParameterField) comp;
}
	 */
}
