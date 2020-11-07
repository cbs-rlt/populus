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
import java.beans.*;

public class PopulusParameterFieldBeanInfo extends SimpleBeanInfo {
	String iconColor16x16Filename = "clr16pf.gif";
	String iconColor32x32Filename = "clr32pf.gif";
	String iconMono16x16Filename = "mono16pf.gif";
	Class<?> beanClass = PopulusParameterField.class;
	String iconMono32x32Filename = "mono32pf.gif";

    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            PropertyDescriptor _columns = new PropertyDescriptor("columns", beanClass, "getColumns", "setColumns");
            _columns.setDisplayName("columns");
            _columns.setShortDescription("number of columns");
            PropertyDescriptor _currentValue = new PropertyDescriptor("currentValue", beanClass, "getCurrentValue", "setCurrentValue");
            _currentValue.setDisplayName("currentValue");
            _currentValue.setShortDescription("current value");
            PropertyDescriptor _defaultValue = new PropertyDescriptor("defaultValue", beanClass, "getDefaultValue", "setDefaultValue");
            _defaultValue.setDisplayName("defaultValue");
            _defaultValue.setShortDescription("default value");
            PropertyDescriptor _double = new PropertyDescriptor("double", beanClass, "getDouble", null);
            PropertyDescriptor _enabled = new PropertyDescriptor("enabled", beanClass, "isEnabled", "setEnabled");
            _enabled.setDisplayName("enabled");
            _enabled.setShortDescription("enabled");
            PropertyDescriptor _helpText = new PropertyDescriptor("helpText", beanClass, "getHelpText", "setHelpText");
            _helpText.setDisplayName("helpText");
            _helpText.setShortDescription("Help Information About Parameter");
            PropertyDescriptor _incrementAmount = new PropertyDescriptor("incrementAmount", beanClass, "getIncrementAmount", "setIncrementAmount");
            _incrementAmount.setDisplayName("incrementAmount");
            _incrementAmount.setShortDescription("incrementAmount");
            PropertyDescriptor _integersOnly = new PropertyDescriptor("integersOnly", beanClass, "isIntegersOnly", "setIntegersOnly");
            _integersOnly.setDisplayName("integersOnly");
            _integersOnly.setShortDescription("'true' for integer only values");
            PropertyDescriptor _label = new PropertyDescriptor("label", beanClass, null, "setLabel");
            _label.setDisplayName("label");
            _label.setShortDescription("label");
            PropertyDescriptor _maxValue = new PropertyDescriptor("maxValue", beanClass, "getMaxValue", "setMaxValue");
            _maxValue.setDisplayName("maxValue");
            _maxValue.setShortDescription("maximum value");
            PropertyDescriptor _minValue = new PropertyDescriptor("minValue", beanClass, "getMinValue", "setMinValue");
            _minValue.setDisplayName("minValue");
            _minValue.setShortDescription("minimum value");
            PropertyDescriptor _parameterName = new PropertyDescriptor("parameterName", beanClass, "getParameterName", "setParameterName");
            _parameterName.setDisplayName("parameterName");
            _parameterName.setShortDescription("parameterName");
            PropertyDescriptor _unicodeName = new PropertyDescriptor("unicodeName", beanClass, "getUnicodeName", "setUnicodeName");
            _unicodeName.setDisplayName("unicodeName");
            _unicodeName.setShortDescription("unicodeName");
            PropertyDescriptor _useHTML = new PropertyDescriptor("useHTML", beanClass, "isUseHTML", "setUseHTML");
            _useHTML.setDisplayName("useHTML");
            _useHTML.setShortDescription("Use HTMLLabel as label");
            return new PropertyDescriptor[]{
                    _columns, _currentValue, _defaultValue, _double, _enabled,
                    _helpText, _incrementAmount, _integersOnly, _label,
                    _maxValue, _minValue, _parameterName, _unicodeName,
                    _useHTML,
            };
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

	@Override
	public java.awt.Image getIcon( int iconKind ) {
		switch( iconKind ) {
		case BeanInfo.ICON_COLOR_16x16:
			return iconColor16x16Filename != null ? loadImage( iconColor16x16Filename ) : null;

		case BeanInfo.ICON_COLOR_32x32:
			return iconColor32x32Filename != null ? loadImage( iconColor32x32Filename ) : null;

		case BeanInfo.ICON_MONO_16x16:
			return iconMono16x16Filename != null ? loadImage( iconMono16x16Filename ) : null;

		case BeanInfo.ICON_MONO_32x32:
			return iconMono32x32Filename != null ? loadImage( iconMono32x32Filename ) : null;
		}
		return null;
	}

    @Override
    public BeanInfo[] getAdditionalBeanInfo() {
        Class<?> superclass = beanClass.getSuperclass();
        try {
            BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);
            return new BeanInfo[]{
                    superBeanInfo
            };
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PopulusParameterFieldBeanInfo() {

    }
}
