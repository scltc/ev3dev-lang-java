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
package org.ev3dev.hardware.sensors;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;

/**
 * A generic interface to control I2C-type EV3 sensors.
 * @author Anthony
 *
 */
public class I2CSensor extends Sensor {
	
	/**
	 * The Sysfs class's <code>fw_version</code> property name
	 */
	public static final String SYSFS_PROPERTY_FIRMWARE_VERSION = "fw_version";
	
	/**
	 * The Sysfs class's <code>poll_ms</code> property name
	 */
	public static final String SYSFS_PROPERTY_POLL_MS = "poll_ms";
	
	/**
	 * This device's default driver name
	 */
	public static final String DRIVER_NAME = "nxt-i2c-sensor";

	/**
	 * Creates a new I2CSensor instance.
	 * @param port LegoPort
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a I2CSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public I2CSensor(LegoPort port) throws InvalidPortException, InvalidSensorException, EV3LibraryException {
		this(port, DRIVER_NAME);
	}
	
	/**
	 * Creates a new I2CSensor instance, and an alternative driver name can be specified.
	 * @param port LegoPort
	 * @param target_driver_name The target driver name to be checked.
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a I2CSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public I2CSensor(LegoPort port, String target_driver_name) throws InvalidPortException, InvalidSensorException, EV3LibraryException {
		super(port);
		if (!this.getDriverName().equals(target_driver_name)){
			throw new InvalidSensorException("The specified port is not a I2C sensor.");
		}
	}
	
	/**
	 * Returns the firmware version of the sensor if available. Currently only I2C/NXT sensors support this.
	 * @return The firmware version
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getFirmwareVersion() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_FIRMWARE_VERSION);
	}
	
	/**
	 * Returns the polling period of the sensor in milliseconds. Writing sets the polling period. Setting to 0 disables polling. 
	 * Minimum value is hard coded as 50 msec. Returns -EOPNOTSUPP if changing polling is not supported.
	 *  Currently only I2C/NXT sensors support changing the polling period.
	 * @return The polling period in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPollMs() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_POLL_MS);
		return Integer.parseInt(str);
	}
	
	/**
	 * Sets the polling period of the sensor in milliseconds. Writing sets the polling period. Setting to 0 disables polling. 
	 * Minimum value is hard coded as 50 msec. Returns -EOPNOTSUPP if changing polling is not supported.
	 *  Currently only I2C/NXT sensors support changing the polling period.
	 * @param ms The polling period in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPollMs(int ms) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_POLL_MS, Integer.toString(ms));
	}
}
