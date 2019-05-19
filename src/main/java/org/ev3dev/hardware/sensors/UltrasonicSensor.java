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
import org.ev3dev.exception.InvalidModeException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;

public class UltrasonicSensor extends Sensor {
	
	/**
	 * Distance in cm required Sysfs mode
	 */
	public static final String SYSFS_CM_MODE = "US-DIST-CM";
	
	/**
	 * Distance in cm Sysfs value index
	 */
	public static final int SYSFS_CM_VALUE_INDEX = 0;
	
	/**
	 * Distance in inch required Sysfs mode
	 */
	public static final String SYSFS_IN_MODE = "US-DIST-IN";
	
	/**
	 * Distance in inch Sysfs value index
	 */
	public static final int SYSFS_IN_VALUE_INDEX = 0;
	
	/**
	 * Other present sensor detection required Sysfs mode
	 */
	public static final String SYSFS_OTHER_PRESENT_MODE = "US-LISTEN";
	
	/**
	 * Other present sensor detection Sysfs value index
	 */
	public static final int SYSFS_OTHER_PRESENT_VALUE_INDEX = 0;
	
	/**
	 * This device's default driver name (EV3 Ultrasonic Sensor)
	 */
	public static final String DRIVER_NAME_EV3 = "lego-ev3-us";
	
	/**
	 * This device's default driver name (NXT Ultrasonic Sensor)
	 */
	public static final String DRIVER_NAME_NXT = "lego-nxt-us"; 
	
	public boolean autoSwitchMode = true;

	/**
	 * Creates a new UltrasonicSensor instance.
	 * @param port LegoPort
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a UltrasonicSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public UltrasonicSensor(LegoPort port) throws EV3LibraryException, InvalidPortException, InvalidSensorException {
		super(port);
		String driverName = this.getDriverName();
		if (!driverName.equals(DRIVER_NAME_EV3) && 
				!driverName.equals(DRIVER_NAME_NXT)){
			throw new InvalidSensorException("Can't create a UltrasonicSensor instance if it is not a ultrasonic sensor! Yours: " + driverName);
		}
	}

	/**
	 * Measurement of the distance detected by the sensor, in centimeters.
	 * @return The distance in centimeters
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getDistanceCentimeters() throws EV3LibraryException, InvalidModeException{
		if (!this.getMode().equals(SYSFS_CM_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_CM_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_CM_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_CM_VALUE_INDEX);
		return Float.parseFloat(str);
	}
	
	/**
	 * Measurement of the distance detected by the sensor, in inches.
	 * @return The distance in inches.
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getDistanceInches() throws EV3LibraryException, InvalidModeException{
		if (!this.getMode().equals(SYSFS_IN_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_IN_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_IN_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_IN_VALUE_INDEX);
		return Float.parseFloat(str);
	}
	
	/**
	 * Value indicating whether another ultrasonic sensor could be heard nearby.
	 * @return Boolean
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public boolean isOtherSensorPresent() throws EV3LibraryException, InvalidModeException{
		if (!this.getMode().equals(SYSFS_OTHER_PRESENT_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_OTHER_PRESENT_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_OTHER_PRESENT_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_OTHER_PRESENT_VALUE_INDEX);
		return str.equals("1");
	}
	
	/**
	 * Set Auto Switch Mode to be enabled or disabled.<br>
	 * (Default: enabled)
	 * @param autoswitch A Boolean
	 */
	public void setAutoSwitchMode(boolean autoswitch){
		this.autoSwitchMode = autoswitch;
	}

	/**
	 * Get whether Auto Switch Mode is enabled or disabled.
	 * @return A Boolean
	 */
	public boolean isAutoSwitchMode(){
		return autoSwitchMode;
	}
}
