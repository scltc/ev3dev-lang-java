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
package org.ev3dev.hardware.ports;

import org.ev3dev.exception.InvalidPortException;

/***
 * SensorPort class for getting data from a specified sensor port.<br>
 * <br>
 * You can use a LegoPort to use all the ports.
 * @author Anthony
 *
 */
public class SensorPort extends LegoPort{
	
	public static final int SENSOR_1 = 0;
	public static final int SENSOR_2 = 1;
	public static final int SENSOR_3 = 2;
	public static final int SENSOR_4 = 3;

	/***
	 * Creates a new sensor port object.<br>
	 * <br>
	 * <b>NOTE:</b> SensorPort only accepts sensor ports. Use LegoPort for all ports.
	 * @param port A final field from the <b>SensorPort</b> class. e.g.: <b>SensorPort.SENSOR_1</b>
	 * @throws InvalidPortException If the specified port does not exist
	 */
	public SensorPort(int port) throws InvalidPortException {
		super(port);
		if (port < 0){
			throw new InvalidPortException("The port you specified was invaild: 0 > " + port);
		} else if (port > SENSOR_4){
			throw new InvalidPortException("The port you specified isn't a sensor: " + SENSOR_3 + " > " + port);
		}
	}
	
}
