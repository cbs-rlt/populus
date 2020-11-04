/*******************************************************************************
 * Copyright (c) 2015 Regents of the University of Minnesota.
 *
 * This software is released under GNU General Public License 2.0
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 *******************************************************************************/

//Title:        Populus

//Version:

//Copyright:    Copyright (c) 1999

//Author:       Lars Roe, under Don Alstad

//Company:      University of Minnesota

//Description:  First Attempt at using Java 1.2

//with Populus
package edu.umn.ecology.populus.visual.ppfield;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class UpDownArrowSetBeanInfo extends SimpleBeanInfo {
    String iconColor16x16Filename;
    String iconColor32x32Filename = "updown.gif";
    String iconMono16x16Filename;
    String iconMono32x32Filename;
    Class beanClass = UpDownArrowSet.class;

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        PropertyDescriptor[] pds = new PropertyDescriptor[]{};
        return pds;
    }

    @Override
    public java.awt.Image getIcon(int iconKind) {
        return switch (iconKind) {
            case BeanInfo.ICON_COLOR_16x16 -> iconColor16x16Filename != null ? loadImage(iconColor16x16Filename) : null;
            case BeanInfo.ICON_COLOR_32x32 -> iconColor32x32Filename != null ? loadImage(iconColor32x32Filename) : null;
            case BeanInfo.ICON_MONO_16x16 -> iconMono16x16Filename != null ? loadImage(iconMono16x16Filename) : null;
            case BeanInfo.ICON_MONO_32x32 -> iconMono32x32Filename != null ? loadImage(iconMono32x32Filename) : null;
            default -> null;
        };
    }

    public UpDownArrowSetBeanInfo() {

    }
}
