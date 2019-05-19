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
package org.ev3dev.hardware.sensors.di;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.hardware.sensors.generic.NXTAnalogSensor;

public class DflexSensor extends NXTAnalogSensor {
	
	/**
	 * <code>FLEX</code> Mode - Flex
	 */
	public static final String MODE_FLEX = "FLEX";
	
	/**
	 * <code>FLEX</code> Mode - Flex Sysfs value index
	 */
	public static final int MODE_FLEX_VALUE_INDEX = 0;
	
	/**
	 * dFlex Sensor driver name
	 */
	public static final String DRIVER_NAME = "di-dflex";

	/**
	 * Creates a DflexSensor instance.
	 * @param port The LegoPort instance
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public DflexSensor(LegoPort port) throws EV3LibraryException {
		super(port, DRIVER_NAME);
	}
	
	/**
	 * Flex
	 * @return an integer from 0-100
	 */
	public int getFlex(){
		return Integer.parseInt(this.getAttribute("value" + MODE_FLEX_VALUE_INDEX));
	}

}
