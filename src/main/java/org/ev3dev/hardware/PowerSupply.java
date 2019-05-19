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
package org.ev3dev.hardware;

//~autogen autogen-header

//~autogen

import java.io.FileNotFoundException;
import java.io.IOException;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.io.Sysfs;

//~autogen generic-class-description classes.powerSupply>currentClass

//~autogen

/***
 * A generic interface to read data from the system's power_supply class. Uses the built-in legoev3-battery if none is specified.
 * @author Anthony
 *
 */
public class PowerSupply{
	
	/**
	 * The Sysfs class's <code>measured_current</code> property name
	 */
	public static final String SYSFS_MEASURED_CURRENT = "measured_current";
	
	/**
	 * The Sysfs class's <code>measured_voltage</code> property name
	 */
	public static final String SYSFS_MEASURED_VOLTAGE = "measured_voltage";
	
	/**
	 * The Sysfs class's <code>max_voltage</code> property name
	 */
	public static final String SYSFS_MAX_VOLTAGE = "max_voltage";
	
	/**
	 * The Sysfs class's <code>min_voltage</code> property name
	 */
	public static final String SYSFS_MIN_VOLTAGE = "min_voltage";
	
	/**
	 * The Sysfs class's <code>technology</code> property name
	 */
	public static final String SYSFS_TECHNOLOGY = "technology";
	
	/**
	 * The Sysfs class's <code>type</code> property name
	 */
	public static final String SYSFS_TYPE = "type";
	
	/**
	 * This Sysfs's class name (e.g. <code>/sys/class/lego-sensor</code>, and <code>lego-sensor</code> is the class name)
	 */
	public static final String POWER_SUPPLY_CLASS_NAME = "power_supply";
	
	/***
	 * The measured current that the battery is supplying (in microamps)
	 * @return Measured Current
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static int getMeasuredCurrent() throws EV3LibraryException{
		String str;
		try {
			str = Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_MEASURED_CURRENT);
		} catch (IOException e) {
			throw new EV3LibraryException("Get measured current attribute failed", e);
		}
		return Integer.parseInt(str);
	}
	
	/***
	 * The measured voltage that the battery is supplying (in microvolts)
	 * @return Measured Voltage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static int getMeasuredVoltage() throws EV3LibraryException{
		String str;
		try {
			str = Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_MEASURED_VOLTAGE);
		} catch (IOException e) {
			throw new EV3LibraryException("Get measured voltage attribute failed", e);
		}
		return Integer.parseInt(str);
	}
	
	/***
	 * Get the maximum voltage
	 * @return Maximum Voltage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static int getMaxVoltage() throws EV3LibraryException{
		String str;
		try {
			str = Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_MAX_VOLTAGE);
		} catch (IOException e) {
			throw new EV3LibraryException("Get max voltage attribute failed", e);
		}
		return Integer.parseInt(str);
	}
	
	/***
	 * Get the minimum voltage
	 * @return Minimum Voltage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static int getMinVoltage() throws EV3LibraryException{
		String str;
		try {
			str = Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_MIN_VOLTAGE);
		} catch (IOException e) {
			throw new EV3LibraryException("Get min voltage attribute failed", e);
		}
		return Integer.parseInt(str);
	}
	
	/***
	 * Get the technology of this power supply
	 * @return String
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static String getTechnology() throws EV3LibraryException{
		try {
			return Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_TECHNOLOGY);
		} catch (IOException e) {
			throw new EV3LibraryException("Get technology attribute failed", e);
		}
	}
	
	/***
	 * Get the type of this power supply
	 * @return String
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public static String getType() throws EV3LibraryException{
		try {
			return Sysfs.getAttribute(POWER_SUPPLY_CLASS_NAME, SYSFS_TYPE);
		} catch (IOException e) {
			throw new EV3LibraryException("Get type attribute failed", e);
		}
	}
}
