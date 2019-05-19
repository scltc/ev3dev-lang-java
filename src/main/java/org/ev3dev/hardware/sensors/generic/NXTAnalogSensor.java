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
package org.ev3dev.hardware.sensors.generic;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.hardware.sensors.Sensor;

/**
 * Generic NXT Analog Sensor driver
 * @author Anthony
 *
 */
public class NXTAnalogSensor extends Sensor {

	/**
	 * <code>ANALOG-0</code> Mode - Raw analog value
	 */
	public static final String MODE_ANALOG_0 = "ANALOG-0";
	
	/**
	 * <code>ANALOG-1</code> Mode - Raw analog value, Pin 5 high
	 */
	public static final String MODE_ANALOG_1 = "ANALOG-1";
	
	/**
	 * The Sysfs value index
	 */
	public static final int VALUE_INDEX = 0;
	
	/**
	 * The NXT Analog sensor driver name
	 */
	public static final String DRIVER_NAME = "nxt-analog";
	
	/**
	 * Creates a new NXT analog sensor.
	 * @param port The LegoPort instance
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public NXTAnalogSensor(LegoPort port) throws EV3LibraryException{
		this(port, DRIVER_NAME);
	}
	
	/**
	 * Creates a new NXT analog sensor, and an alternative driver name can be specified.
	 * @param port The LegoPort instance
	 * @param target_driver_name The target driver name to be checked.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public NXTAnalogSensor(LegoPort port, String target_driver_name) throws EV3LibraryException{
		super(port);
		String drivername = port.getDriverName();
		if (!drivername.equals(target_driver_name)){
			throw new EV3LibraryException("The port is not connected to a NXT analog sensor: " + drivername);
		}
	}
	
	/**
	 * Set mode as <code>ANALOG-0</code> Mode - Raw analog value
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAnalog0() throws EV3LibraryException{
		setMode(MODE_ANALOG_0);
	}
	
	/**
	 * Set mode as <code>ANALOG-1</code> Mode - Raw analog value, Pin 5 high
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAnalog1() throws EV3LibraryException{
		setMode(MODE_ANALOG_1);
	}
	
	/**
	 * Returns the raw analog voltage / value (0-5000).<br>
	 * Both mode uses the same value index (value0)<br>
	 * <br>
	 * This function does not calculate decimal places.
	 * @throws EV3LibraryException If I/O goes wrong
	 * @return The voltage
	 */
	public int getRawValue() throws EV3LibraryException{
		String str = getAttribute("value" + VALUE_INDEX);
		return Integer.parseInt(str);
	}
	
	/**
	 * Returns the raw analog voltage / value (0-5000), and with decimal places<br>
	 * Both mode uses the same value index (value0)<br>
	 * @throws EV3LibraryException If I/O goes wrong
	 * @return The voltage
	 */
	public float getValue() throws EV3LibraryException{
		float out = getRawValue();
		
		int dec = getDecimals();
		for (int i = 1; i <= dec; i++){
			out /= 10;
		}
		
		return out;
	}

}
