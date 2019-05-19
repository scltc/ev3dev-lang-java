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

import java.io.IOException;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.io.Sysfs;

/***
 * The lego-port class provides an interface for working with input and output ports that are compatible with LEGO MINDSTORMS RCX/NXT/EV3,
 *  LEGO WeDo and LEGO Power Functions sensors and motors. Supported devices include the LEGO MINDSTORMS EV3 Intelligent Brick, the LEGO
 *   WeDo USB hub and various sensor multiplexers from 3rd party manufacturers.<br>
 * <br>
 * Some types of ports may have multiple modes of operation. For example, the input ports on the EV3 brick can communicate
 *  with sensors using UART, I2C or analog validate signals - but not all at the same time. Therefore there are multiple modes
 *   available to connect to the different types of sensors.<br>
 * <br>
 * In most cases, ports are able to automatically detect what type of sensor or motor is connected. In some cases though, this
 *  must be manually specified using the mode and set_device attributes. The mode attribute affects how the port communicates
 *   with the connected device. For example the input ports on the EV3 brick can communicate using UART, I2C or analog voltages,
 *    but not all at the same time, so the mode must be set to the one that is appropriate for the connected sensor. The set_device
 *     attribute is used to specify the exact type of sensor that is connected. Note: the mode must be correctly set before setting
 *      the sensor type.<br>
 * <br>
 * Ports can be found at /sys/class/lego-port/port[N] where [N] is incremented each time a new port is registered. Note: The number is not related to the actual port at all - use the address attribute to find a specific port.
 * @author Anthony
 *
 */
public class LegoPort{
	
	private int port = 0;
	
	/**
	 * The sysfs class name of LegoPort
	 */
	public static final String CLASS_NAME = "lego-port";
	
	/**
	 * Sensor Port 1 on the EV3
	 */
	public static final int INPUT_1 = 0;
	
	/**
	 * Sensor Port 2 on the EV3
	 */
	public static final int INPUT_2 = 1;
	
	/**
	 * Sensor Port 3 on the EV3
	 */
	public static final int INPUT_3 = 2;
	
	/**
	 * Sensor Port 4 on the EV3
	 */
	public static final int INPUT_4 = 3;
	
	/**
	 * Motor Port A on the EV3
	 */
	public static final int OUTPUT_A = 4;
	
	/**
	 * Motor Port B on the EV3
	 */
	public static final int OUTPUT_B = 5;
	
	/**
	 * Motor Port C on the EV3
	 */
	public static final int OUTPUT_C = 6;
	
	/**
	 * Motor Port D on the EV3
	 */
	public static final int OUTPUT_D = 7;
	
	/**
	 * Creates a new LegoPort object.
	 * @param port A INPUT/OUTPUT Integer field from LegoPort class.
	 * @throws InvalidPortException If the specified port is lower than INPUT_1, higher than OUTPUT_D
	 */
	public LegoPort(int port) throws InvalidPortException{
		if (port < INPUT_1){
			throw new InvalidPortException("Port is lower than " + INPUT_1 + ", Port: " + port);
		} else if (port > OUTPUT_D){
			throw new InvalidPortException("Port is higher than " + OUTPUT_D + ", Port: " + port);
		}
		this.port = port;
	}
	
	/**
	 * Returns the name of the port. See individual driver documentation for the name that will be returned.
	 * @return Address (e.g. in1, outA)
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getAddress() throws EV3LibraryException{
		String address;
		try {
			address = Sysfs.getAttribute(CLASS_NAME, "port" + port, "address");
		} catch (IOException e) {
			throw new EV3LibraryException("Get address attribute failed", e);
		}
		return address;
	}
	
	/**
	 * Returns the name of the driver that loaded this device. You can find the complete list of drivers in the [list of port drivers].
	 * @return Driver Name of this port
	 * @throws EV3LibraryException if I/O goes wrong
	 */
	public String getDriverName() throws EV3LibraryException{
		String drivername;
		try {
			drivername = Sysfs.getAttribute(CLASS_NAME, "port" + port, "driver_name");
		} catch (IOException e) {
			throw new EV3LibraryException("Get driver name attribute failed", e);
		}
		return drivername;
	}
	
	/**
	 * Returns a list of the available modes of the port.
	 * @return A String Array with a list of available modes
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getModes() throws EV3LibraryException{
		String modesstr;
		try {
			modesstr = Sysfs.getAttribute(CLASS_NAME, "port" + port, "modes");
		} catch (IOException e) {
			throw new EV3LibraryException("Get modes attribute failed", e);
		}
		return Sysfs.separateSpace(modesstr);
	}
	
	/**
	 * Reading returns the currently selected mode. Writing sets the mode. Generally
	 *  speaking when the mode changes any sensor or motor devices associated with
	 *   the port will be removed new ones loaded,
	 *  however this this will depend on the individual driver implementing this class.
	 * @return The currently selected mode
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getMode() throws EV3LibraryException{
		String mode;
		try {
			mode = Sysfs.getAttribute(CLASS_NAME, "port" + port, "mode");
		} catch (IOException e) {
			throw new EV3LibraryException("Get mode attribute failed", e);
		}
		return mode;
	}
	
	/**
	 * Reading returns the currently selected mode. Writing sets the mode. Generally
	 *  speaking when the mode changes any sensor or motor devices associated with
	 *   the port will be removed new ones loaded,
	 *  however this this will depend on the individual driver implementing this class.
	 * @param mode A available mode listed using <code>getModes()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setMode(String mode) throws EV3LibraryException{
		try {
			Sysfs.setAttribute(CLASS_NAME, "port" + port, "mode", mode);
		} catch (IOException e) {
			throw new EV3LibraryException("Set mode attribute failed", e);
		}
	}
	
	/**
	 * For modes that support it, writing the name of a driver will cause a new device to be registered for that driver and
	 *  attached to this port. For example, since NXT/Analog sensors cannot be auto-detected, you must use this attribute to
	 *   load the correct driver. Returns -EOPNOTSUPP if setting a device is not supported.
	 * @param driver A generic driver name
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setDevice(String driver) throws EV3LibraryException{
		try {
			Sysfs.setAttribute(CLASS_NAME, "port" + port, "set_device", driver);
		} catch (IOException e) {
			throw new EV3LibraryException("Set device attribute failed", e);
		}
	}
	
	/**
	 * In most cases, reading status will return the same value as mode. In cases where there is an
	 *  auto mode additional values may be returned, such as no-device or error.
	 *  See individual port driver documentation for the full list of possible values.
	 * @return The same as mode or some errors like: no-device or error.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStatus() throws EV3LibraryException{
		String status;
		try {
			status = Sysfs.getAttribute(CLASS_NAME, "port" + port, "status");
		} catch (IOException e) {
			throw new EV3LibraryException("Get status attribute failed", e);
		}
		return status;
	}
}
