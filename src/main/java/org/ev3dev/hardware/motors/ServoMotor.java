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
import org.ev3dev.exception.InvalidMotorException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.hardware.Device;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.io.Sysfs;

//~autogen generic-class-description classes.servoMotor>currentClass

//~autogen

/**
 * The servo motor class provides a uniform interface for using hobby type servo motors.
 * @author Anthony
 *
 */
public class ServoMotor extends Device{
	
	/**
	 * The Sysfs class's <code>address</code> property name
	 */
	private static final String SYSFS_PROPERTY_ADDRESS = "address";
	
	/**
	 * The Sysfs class's <code>command</code> property name
	 */
	private static final String SYSFS_PROPERTY_COMMAND = "command";
	
	/**
	 * The Sysfs class's <code>driver_name</code> property name
	 */
	private static final String SYSFS_PROPERTY_DRIVER_NAME = "driver_name";
	
	/**
	 * The Sysfs class's <code>polarity</code> property name
	 */
	private static final String SYSFS_PROPERTY_POLARITY = "polarity";
	
	/**
	 * The Sysfs class's <code>position_sp</code> property name
	 */
	private static final String SYSFS_PROPERTY_POSITION_SP = "position_sp";
	
	/**
	 * The Sysfs class's <code>state</code> property name
	 */
	private static final String SYSFS_PROPERTY_STATE = "state";
	
	/**
	 * The Sysfs class's <code>max_pulse_sp</code> property name
	 */
	private static final String SYSFS_PROPERTY_MAX_PULSE_SP = "max_pulse_sp";
	
	/**
	 * The Sysfs class's <code>mid_pulse_sp</code> property name
	 */
	private static final String SYSFS_PROPERTY_MID_PULSE_SP = "mid_pulse_sp";
	
	/**
	 * The Sysfs class's <code>min_pulse_sp</code> property name
	 */
	private static final String SYSFS_PROPERTY_MIN_PULSE_SP = "min_pulse_sp";
	
	/**
	 * The Sysfs class's <code>rate_sp</code> property name
	 */
	private static final String SYSFS_PROPERTY_RATE_SP = "rate_sp";
	
	/**
	 * The Sysfs class's <code>run</code> command
	 */
	public static final String SYSFS_COMMAND_RUN = "run";

	/**
	 * The Sysfs class's <code>float</code> command
	 */
	public static final String SYSFS_COMMAND_FLOAT = "float";
	
	/**
	 * This Sysfs's class name (e.g. <code>/sys/class/lego-sensor</code>, and <code>lego-sensor</code> is the class name)
	 */
	public static final String CLASS_NAME = "servo-motor";
	
	/**
	 * This Sysfs's class name prefix (e.g. <code>/sys/class/lego-sensor/sensor0</code>, and <code>sensor</code> is the class name prefix without the [N] value.)
	 */
	public static final String CLASS_NAME_PREFIX = "motor";

	private String address;
	
	/***
	 * Creates a new motor object.
	 * @param port LegoPort
	 * @throws InvalidPortException If the LegoPort isn't a OUTPUT, invalid or a tacho-motor.
	 * @throws EV3LibraryException If the LegoPort specified goes wrong
	 * @throws InvalidMotorException The specified motor wasn't a motor
	 */
	public ServoMotor(LegoPort port) throws EV3LibraryException{
		super(port, CLASS_NAME, CLASS_NAME_PREFIX);
		address = port.getAddress();
		
		//Verify is the LegoPort connecting a motor / is a output
		if (!address.contains("out")){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a output.");
		} else if (!port.getStatus().equals(CLASS_NAME)){
			throw new InvalidMotorException("The specified port (" + port.getAddress() + ") isn't a motor (" + port.getStatus() + ")");
		}
	}
	
	/***
	 * Get the address of this motor.
	 * @return LegoPort address described in String
	 * @throws EV3LibraryException If the motor doesn't exist or IO ERROR
	 */
	public String getAddress() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_ADDRESS);
	}
	
	/***
	 * Generic method to send commands to the motor controller.
	 * @param command Command that suits for the motor driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void sendCommand(String command) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_COMMAND, command);
	}
	
	/***
	 * Setting to run will cause the servo to be driven to the <b>position_sp</b> set in the <b>position_sp</b> attribute. 
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void run() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_RUN);
	}
	
	/***
	 * Run to an absolute position specified by <b>position_sp</b>
	 *  and then stop using the command specified in <b>stop_command</b>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void Float() throws EV3LibraryException{
		sendCommand(SYSFS_COMMAND_FLOAT);
	}
	
	/**
	 * Returns the name of the driver that provides this tacho motor device.
	 * @return The name of the driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getDriverName() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_DRIVER_NAME);
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the maximum (clockwise) position_sp. Default value is 2400.
	 *  Valid values are 2300 to 2700. You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @return The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getMaxPulse_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_MAX_PULSE_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the maximum (clockwise) position_sp. Default value is 2400.
	 *  Valid values are 2300 to 2700. You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @param max_pulse_sp The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setMaxPulse_SP(int max_pulse_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_MAX_PULSE_SP, Integer.toString(max_pulse_sp));
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the mid position_sp.
	 *  Default value is 1500. Valid values are 1300 to 1700. For example, on a 180 degree servo, this would be 90 degrees.
	 *   On continuous rotation servo, this is the 'neutral' position_sp where the motor does not turn.
	 *  You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @return The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getMidPulse_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_MID_PULSE_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the mid position_sp.
	 *  Default value is 1500. Valid values are 1300 to 1700. For example, on a 180 degree servo, this would be 90 degrees.
	 *   On continuous rotation servo, this is the 'neutral' position_sp where the motor does not turn.
	 *  You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @param mid_pulse_sp The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setMidPulse_SP(int mid_pulse_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_MID_PULSE_SP, Integer.toString(mid_pulse_sp));
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the
	 *  minimum (counter-clockwise) position_sp. Default value is 600. Valid values are 300 to 700.
	 *  You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @return The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getMinPulse_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_MIN_PULSE_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Used to set the pulse size in milliseconds for the signal that tells the servo to drive to the
	 *  minimum (counter-clockwise) position_sp. Default value is 600. Valid values are 300 to 700.
	 *  You must write to the position_sp attribute for changes to this attribute to take effect.
	 * @param min_pulse_sp The pulse size in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setMinPulse_SP(int min_pulse_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_MIN_PULSE_SP, Integer.toString(min_pulse_sp));
	}
	
	/**
	 * Sets the polarity of the servo. Valid values are normal and inversed. Setting the value to inversed will cause the position_sp value to be inversed.
	 *  i.e -100 will correspond to max_pulse_sp, and 100 will correspond to min_pulse_sp.
	 * @return The polarity of the servo
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getPolarity() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_POLARITY);
	}
	
	/**
	 * Sets the polarity of the servo. Valid values are normal and inversed. Setting the value to inversed will cause the position_sp value to be inversed.
	 *  i.e -100 will correspond to max_pulse_sp, and 100 will correspond to min_pulse_sp.
	 * @param polarity The polarity of the servo
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPolarity(String polarity) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_POLARITY, polarity);
	}
	
	/**
	 * Reading returns the current position_sp of the servo. Writing instructs the servo to move to the specified position_sp.
	 *  Units are percent. Valid values are -100 to 100 (-100% to 100%)
	 *  where -100 corresponds to min_pulse_sp, 0 corresponds to mid_pulse_sp and 100 corresponds to max_pulse_sp.
	 * @return The current position_sp of the servo
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPosition_SP() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_POSITION_SP);
		return Integer.parseInt(str);
	}

	/**
	 * Reading returns the current position_sp of the servo. Writing instructs the servo to move to the specified position_sp.
	 *  Units are percent. Valid values are -100 to 100 (-100% to 100%)
	 *  where -100 corresponds to min_pulse_sp, 0 corresponds to mid_pulse_sp and 100 corresponds to max_pulse_sp.
	 * @param position_sp The current position_sp of the servo
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition_SP(int position_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_POSITION_SP, Integer.toString(position_sp));
	}
	
	/**
	 * Sets the rate_sp at which the servo travels from 0 to 100.0% (half of the full range of the servo).
	 *  Units are in milliseconds. Example: Setting the rate_sp to 1000 means that it will take a 180 degree
	 *   servo 2 second to move from 0 to 180 degrees. Note: Some servo controllers may not support this
	 *    in which case reading and writing will fail with -EOPNOTSUPP.
	 *  In continuous rotation servos, this value will affect the rate_sp at which the speed ramps up or down.
	 * @param rate_sp The rate_sp value
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setRate_SP(int rate_sp) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_RATE_SP, Integer.toString(rate_sp));
	}
	
	/**
	 * <b>This function returns a string that is likely a "spaced-array".</b><br>
	 * <b>Use this function to directly to return a String array:</b>
	 * <pre>
	 * getState()
	 * </pre>
	 * Reading returns a list of state flags. Possible flags are running, ramping holding and stalled.
	 * @return A list of state flags. String spaced-array
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStateViaString() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_STATE);
	}

	/**
	 * Reading returns a list of state flags. Possible flags are running, ramping holding and stalled.
	 * @return A list(String array) of state flags.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getState() throws EV3LibraryException{
		String str = getStateViaString();
		return Sysfs.separateSpace(str);
	}
}
