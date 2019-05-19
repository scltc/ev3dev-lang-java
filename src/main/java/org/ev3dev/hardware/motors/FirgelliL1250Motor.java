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
package org.ev3dev.hardware.motors;

//~autogen autogen-header

//~autogen

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.hardware.ports.LegoPort;

//~autogen generic-class-description classes.actuonix50Motor>currentClass

//~autogen

public class FirgelliL1250Motor extends Motor {
	
	/**
	 * The Sysfs class's <code>count_per_m</code> property name
	 */
	public static final String SYSFS_PROPERTY_COUNT_PER_M = "count_per_m";
	
	/**
	 * The Sysfs class's <code>full_travel_count</code> property name
	 */
	public static final String SYSFS_PROPERTY_FULL_TRAVEL_COUNT = "full_travel_count";
	
	/**
	 * This Sysfs's class name prefix (e.g. <code>/sys/class/lego-sensor/sensor0</code>, and <code>sensor</code> is the class name prefix without the [N] value.)
	 */
	public static final String LINEAR_MOTOR_CLASS_NAME_PREFIX = "linear";
	
	/**
	 * The driver name for the L12 EV3 50mm by Actuonix
	 */
	public static final String DRIVER_NAME_50MM = "act-l12-ev3-50";

	public FirgelliL1250Motor(int portField) throws EV3LibraryException {
		this(new LegoPort(portField));
	}

	public FirgelliL1250Motor(LegoPort port) throws EV3LibraryException {
		super(port, LINEAR_MOTOR_CLASS_NAME_PREFIX);
		if (!port.getDriverName().equals(DRIVER_NAME_50MM)){
			throw new InvalidPortException("The port does not have a Firgelli L12 50 Motor driver.");
		}
	}
	
	/**
	 * Do not use this on Firgelli L12 50/100 Motors (Linear motors).<br>
	 * <code>-1</code> will be returned instead, use <code>getCountPerMetre()</code>
	 */
	@Override
	public int getCountPerRot() throws EV3LibraryException{
		return -1;
	}
	
	/**
	 * Returns the number of tacho counts in one meter of travel of the motor. 
	 * Tacho counts are used by the position and speed attributes, so you can
	 *  use this value to convert from distance to tacho counts. (linear motors only)
	 * @return Counts per metre
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getCountPerMetre() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String countpermetre = this.getAttribute(SYSFS_PROPERTY_COUNT_PER_M);
		return Integer.parseInt(countpermetre);
	}
	
	/**
	 * Returns the number of tacho counts in the full travel of the motor. 
	 * When combined with the count_per_m attribute, you can use this value
	 *  to calculate the maximum travel distance of the motor. 
	 *  (linear motors only)
	 * @return Full Travel Count
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getFullTravelCount() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_FULL_TRAVEL_COUNT);
		return Integer.parseInt(str);
	}

}
