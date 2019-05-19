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
 * Generic EV3 Analog Sensor driver
 * @author Anthony
 *
 */
public class EV3AnalogSensor extends Sensor {
	
	/**
	 * <code>ANALOG</code> Mode - Raw analog value
	 */
	public static final String MODE_ANALOG = "ANALOG";
	
	/**
	 * The Sysfs value index
	 */
	public static final int VALUE_INDEX = 0;

	/**
	 * The EV3 Analog sensor driver name
	 */
	public static final String DRIVER_NAME = "ev3-analog";
	
	/**
	 * Creates a new EV3 analog sensor
	 * @param port The LegoPort instance
	 * @param typeId The sensor type ID, see <a href="http://www.ev3dev.org/docs/sensors">here</a> for more details.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public EV3AnalogSensor(LegoPort port, String typeId) throws EV3LibraryException{
		super(port);
		String drivername = port.getDriverName();
		if (!drivername.equals(DRIVER_NAME + "-" + typeId)){
			throw new EV3LibraryException("The port is not connected to a EV3 analog sensor with type id \"" + typeId + "\": " + drivername);
		}
	}
	
	/**
	 * Set mode as <code>ANALOG</code> Mode - Raw analog value
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setModeAnalog() throws EV3LibraryException{
		setMode(MODE_ANALOG);
	}
	
	/**
	 * Returns the raw analog voltage / value (0-5000).<br>
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
