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
 * MotorPort class for getting data from a specified motor port.<br>
 * <br>
 * You can use a LegoPort to use all the ports.
 * @author Anthony
 *
 */
public class MotorPort extends LegoPort {
	
	public static final int MOTOR_A = 4;
	public static final int MOTOR_B = 5;
	public static final int MOTOR_C = 6;
	public static final int MOTOR_D = 7;

	/***
	 * Creates a new motor port object.<br>
	 * <br>
	 * <b>NOTE:</b> MotorPort only accepts motors ports. Use LegoPort for all ports.
	 * @param port A final field from the <b>MotorPort</b> class. e.g.: <b>MotorPort.MOTOR_A</b>
	 * @throws InvalidPortException If the specified port does not exist
	 */
	public MotorPort(int port) throws InvalidPortException {
		super(port);
		if (port < 0){
			throw new InvalidPortException("The port you specified was invaild: 0 > " + port);
		} else if (port < MOTOR_A){
			throw new InvalidPortException("The port you specified isn't a motor: " + MOTOR_A + " > " + port);
		} else if (port > MOTOR_D){
			throw new InvalidPortException("The port you specified was invaild: " + MOTOR_D + " > " + port);
		}
	}

}
