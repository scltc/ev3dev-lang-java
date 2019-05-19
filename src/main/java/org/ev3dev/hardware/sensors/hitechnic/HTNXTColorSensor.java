/*******************************************************************************
 * Any modification, copies of sections of this file must be attached with this
 * license and shown clearly in the developer's project. The code can be used
 * as long as you state clearly you do not own it. Any violation might result in
 *  a take-down.
 *
 * MIT License
 *
 * Copyright (c) 2016, 2017 Anthony Law
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package org.ev3dev.hardware.sensors.hitechnic;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.hardware.sensors.I2CSensor;

public class HTNXTColorSensor extends I2CSensor {
	
	/**
	 * HiTechnic NXT Color sensor
	 */
	public static final String DRIVER_NAME = "ht-nxt-color";
	
	/**
	 * The vendor id of this sensor
	 */
	public static final String VENDOR_ID = "HiTechnic";
	
	/**
	 * The product id of this sensor
	 */
	public static final String PRODUCT_ID = "Color";
	
	/**
	 * Mode "Color"
	 */
	public static final String MODE_COLOR = "COLOR";
	
	/**
	 * Mode "Red component"
	 */
	public static final String MODE_RED = "RED";
	
	/**
	 * Mode "Green component"
	 */
	public static final String MODE_GREEN = "GREEN";
	
	/**
	 * Mode "Blue component"
	 */
	public static final String MODE_BLUE = "BLUE";
	
	/**
	 * Mode "Raw values"
	 */
	public static final String MODE_RAW = "RAW";
	
	/**
	 * Mode "Normalized values"
	 */
	public static final String MODE_NORM = "NORM";
	
	/**
	 * Mode "All values"
	 */
	public static final String MODE_ALL = "ALL";
	
	/**
	 * Color (0 to 17) Sysfs value index of mode "Color"
	 */
	public static final int INDEX_MODE_COLOR_COLOR = 0;
	
	/**
	 * Reflected light intensity (0 to 255) Sysfs value index of mode "Red","Green","Blue"
	 */
	public static final int INDEX_REFLECTED = 0;
	
	/**
	 * Red component color value (0 to 255) Sysfs value index of mode "Raw","Normalized values"
	 */
	public static final int INDEX_RED_COMP = 0;
	
	/**
	 * Green component color value (0 to 255) Sysfs value index of mode "Raw","Normalized values"
	 */
	public static final int INDEX_GREEN_COMP = 1;
	
	/**
	 * Blue component color value (0 to 255) Sysfs value index of mode "Raw","Normalized values"
	 */
	public static final int INDEX_BLUE_COMP = 2;
	
	/**
	 * ??? (Unknown) component color value (0 to 255) Sysfs value index of mode "Normalized values"
	 */
	public static final int INDEX_MODE_NORM_UNKNOWN = 3;
	
	/**
	 * Color value (0 to 17) Sysfs value index of mode "All"
	 */
	public static final int INDEX_MODE_ALL_COLOR = 0;
	
	/**
	 * Red component color value (0 to 255) Sysfs value index of mode "All"
	 */
	public static final int INDEX_MODE_ALL_RED = 1;
	
	/**
	 * Green component color value (0 to 255) Sysfs value index of mode "All"
	 */
	public static final int INDEX_MODE_ALL_GREEN = 2;
	
	/**
	 * Blue component color value (0 to 255) Sysfs value index of mode "All"
	 */
	public static final int INDEX_MODE_ALL_BLUE = 3;
	
	/**
	 * The attribute prefix before the value index
	 */
	public static final String VALUE_PREFIX = "value";
	
	/**
	 * I2C Address
	 */
	public static final byte address = 0x01;

	/**
	 * Creates a HTNXTColorSensor instance.
	 * @param port The LegoPort instance
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public HTNXTColorSensor(LegoPort port) throws EV3LibraryException {
		super(port, DRIVER_NAME);
	}
	
	/**
	 * Set mode as mode "Color"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsColor() throws EV3LibraryException {
		setMode(MODE_COLOR);
	}
	
	/**
	 * Set mode as mode "Red"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsRed() throws EV3LibraryException {
		setMode(MODE_RED);
	}

	/**
	 * Set mode as mode "Green"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsGreen() throws EV3LibraryException{
		setMode(MODE_GREEN);
	}
	
	/**
	 * Set mode as mode "Blue"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsBlue() throws EV3LibraryException{
		setMode(MODE_BLUE);
	}
	
	/**
	 * Set mode as mode "Raw"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsRaw() throws EV3LibraryException{
		setMode(MODE_RAW);
	}
	
	/**
	 * Set mode as mode "Normalized values"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsNorm() throws EV3LibraryException{
		setMode(MODE_NORM);
	}
	
	/**
	 * Set mode as mode "All"
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAsAll() throws EV3LibraryException{
		setMode(MODE_ALL);
	}
	
	/**
	 * <b>This function requires mode <code>COLOR</code> or <code>ALL</code>.</b><br>
	 * <br>
	 * Get the color value from 0 to 17. Color values chart can be found at <a href="http://www.ev3dev.org/docs/sensors/hitechnic-nxt-color-sensor/">http://www.ev3dev.org/docs/sensors/hitechnic-nxt-color-sensor/</a>
	 * @return a integer from 0 to 17
	 */
	public int getColor(){
		String str = getAttribute(VALUE_PREFIX + INDEX_MODE_COLOR_COLOR);
		return Integer.parseInt(str);
	}
	
	/**
	 * <b>This function requires mode <code>RED</code>, <code>GREEN</code> or <code>BLUE</code>.</b><br>
	 * <br>
	 * Get the reflected color intensity.
	 * @return a integer from 0 to 255
	 */
	public int getReflectedLightIntensity(){
		String str = getAttribute(VALUE_PREFIX + INDEX_REFLECTED);
		return Integer.parseInt(str);
	}
	
	/**
	 * <b>This function requires mode <code>RAW</code>, <code>NORM</code> or <code>ALL</code>.</b><br>
	 * <br>
	 * Get the red component
	 * @return
	 */
	public int getRedComponent(){
		String mode = getMode();
		String str;
		if (mode.equals(MODE_ALL)){
			str = getAttribute(VALUE_PREFIX + INDEX_MODE_ALL_RED);
		} else if (mode.equals(MODE_RAW) || mode.equals(MODE_NORM)){
			str = getAttribute(VALUE_PREFIX + INDEX_RED_COMP);
		} else {
			throw new EV3LibraryException("The function does not support with the current mode: " + mode);
		}
		return Integer.parseInt(str);
	}
}
