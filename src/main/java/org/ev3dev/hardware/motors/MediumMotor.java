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

import java.io.IOException;

import org.ev3dev.exception.InvalidMotorException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.hardware.ports.LegoPort;

//~autogen generic-class-description classes.medium>currentClass

//~autogen

public class MediumMotor extends Motor {
	
	/**
	 * This device's default driver name
	 */
	public static final String DRIVER_NAME = "lego-ev3-m-motor";
	
	public MediumMotor(int portField) throws InvalidPortException, IOException {
		this(new LegoPort(portField));
	}

	public MediumMotor(LegoPort port) throws InvalidPortException, IOException, InvalidMotorException {
		super(port);
		String drivername = this.getDriverName();
		if (!drivername.equals(DRIVER_NAME)){
			throw new InvalidMotorException("The port is not connected to a LargeMotor (" + DRIVER_NAME + "): " + drivername);
		}
	}

}
