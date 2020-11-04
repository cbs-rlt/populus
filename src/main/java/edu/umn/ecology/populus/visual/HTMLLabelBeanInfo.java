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
package edu.umn.ecology.populus.visual;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class HTMLLabelBeanInfo extends SimpleBeanInfo {
    String iconColor16x16Filename = "clr16hl.gif";
    String iconColor32x32Filename = "clr32hl.gif";
    String iconMono16x16Filename = "mono16hl.gif";
    String iconMono32x32Filename = "mono32hl.gif";
    Class<HTMLLabel> beanClass = HTMLLabel.class;

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _defaultColor = new PropertyDescriptor("defaultColor", beanClass, "getDefaultColor", "setDefaultColor");
            _defaultColor.setDisplayName("Default Color");
            _defaultColor.setShortDescription("Default Color");
            PropertyDescriptor _defaultFont = new PropertyDescriptor("defaultFont", beanClass, "getDefaultFont", "setDefaultFont");
            _defaultFont.setDisplayName("Default Font");
            _defaultFont.setShortDescription("Default Font");
            PropertyDescriptor _direction = new PropertyDescriptor("direction", beanClass, "getDirection", "setDirection");
            _direction.setDisplayName("direction");
            _direction.setShortDescription("direction");
            _direction.setPropertyEditorClass(edu.umn.ecology.populus.visual.DirectionEditor.class);
            PropertyDescriptor _rotate = new PropertyDescriptor("rotate", beanClass, "isRotate", "setRotate");
            _rotate.setDisplayName("rotate");
            _rotate.setShortDescription("rotate");
            PropertyDescriptor _text = new PropertyDescriptor("text", beanClass, "getText", "setText");
            _text.setDisplayName("text");
            _text.setShortDescription("text");
            PropertyDescriptor[] pds = new PropertyDescriptor[]{
                    _defaultColor, _defaultFont, _direction, _rotate, _text,
            };
            return pds;
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
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

    public HTMLLabelBeanInfo() {

    }
}
