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
//-----------------------------------------------------------------------------
//~autogen autogen-header

//~autogen
//-----------------------------------------------------------------------------

package org.ev3dev.hardware.motors;

//-----------------------------------------------------------------------------

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.hardware.Device;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.io.Sysfs;

//-----------------------------------------------------------------------------

//~autogen generic-classes classes.motor>currentClass


//~autogen


/**
 * The motor class provides a uniform interface for using motors with positional and directional feedback such as the EV3 and NXT motors.
 *  This feedback allows for precise control of the motors. This is the most common type of motor, so we just call it motor.
 * @author Anthony
 *
 */
public class Motor extends Device{
	
	/**
	 * The Sysfs class's <code>address</code> property name
	 */
	public static final String SYSFS_PROPERTY_ADDRESS = "address";
	
	/**
	 * The Sysfs class's <code>command</code> property name
	 */
	public static final String SYSFS_PROPERTY_COMMAND = "command";
	
	/**
	 * The Sysfs class's <code>commands</code> property name
	 */
	public static final String SYSFS_PROPERTY_COMMANDS = "commands";
	
	/**
	 * The Sysfs class's <code>count_per_rot</code> property name
	 */
	public static final String SYSFS_PROPERTY_COUNT_PER_ROT = "count_per_rot";
	
	/**
	 * The Sysfs class's <code>driver_name</code> property name
	 */
	public static final String SYSFS_PROPERTY_DRIVER_NAME = "driver_name";
	
	/**
	 * The Sysfs class's <code>duty_cycle</code> property name
	 */
	public static final String SYSFS_PROPERTY_DUTY_CYCLE = "duty_cycle";
	
	/**
	 * The Sysfs class's <code>duty_cycle_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_DUTY_CYCLE_SP = "duty_cycle_sp";
	
	/**
	 * The Sysfs class's <code>polarity</code> property name
	 */
	public static final String SYSFS_PROPERTY_POLARITY = "polarity";
	
	/**
	 * The Sysfs class's <code>position</code> property name
	 */
	public static final String SYSFS_PROPERTY_POSITION = "position";
	
	/**
	 * The Sysfs class's <code>position_p</code> property name
	 */
	public static final String SYSFS_PROPERTY_POSITION_P = "hold_pid/Kp";
	
	/**
	 * The Sysfs class's <code>position_i</code> property name
	 */
	public static final String SYSFS_PROPERTY_POSITION_I = "hold_pid/Ki";
	
	/**
	 * The Sysfs class's <code>position_d</code> property name
	 */
	public static final String SYSFS_PROPERTY_POSITION_D = "hold_pid/Kd";
	
	/**
	 * The Sysfs class's <code>position_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_POSITION_SP = "position_sp";
	
	/**
	 * The Sysfs class's <code>speed</code> property name
	 */
	public static final String SYSFS_PROPERTY_SPEED = "speed";
	
	/**
	 * The Sysfs class's <code>speed_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_SPEED_SP = "speed_sp";
	
	/**
	 * The Sysfs class's <code>ramp_up_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_RAMP_UP_SP = "ramp_up_sp";
	
	/**
	 * The Sysfs class's <code>ramp_down_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_RAMP_DOWN_SP = "ramp_down_sp";
	
	/**
	 * The Sysfs class's <code>state</code> property name
	 */
	public static final String SYSFS_PROPERTY_STATE = "state";
	
	/**
	 * The Sysfs class's <code>stop_action</code> property name
	 */
	public static final String SYSFS_PROPERTY_STOP_ACTION = "stop_action";
	
	/**
	 * The Sysfs class's <code>stop_actions</code> property name
	 */
	public static final String SYSFS_PROPERTY_STOP_ACTIONS = "stop_actions";
	
	/**
	 * The Sysfs class's <code>time_sp</code> property name
	 */
	public static final String SYSFS_PROPERTY_TIME_SP = "time_sp";
	
	/**
	 * The Sysfs class's <code>run-forever</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_FOREVER = "run-forever";
	
	/**
	 * The Sysfs class's <code>run-to-abs-pos</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_TO_ABS_POS = "run-to-abs-pos";
	
	/**
	 * The Sysfs class's <code>run-to-rel-pos</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_TO_REL_POS = "run-to-rel-pos";
	
	/**
	 * The Sysfs class's <code>run-timed</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_TIMED = "run-timed";
	
	/**
	 * The Sysfs class's <code>run-direct</code> command
	 */
	public static final String SYSFS_COMMAND_RUN_DIRECT = "run-direct";
	
	/**
	 * The Sysfs class's <code>stop</code> command
	 */
	public static final String SYSFS_COMMAND_STOP = "stop";
	
	/**
	 * The Sysfs class's <code>reset</code> command
	 */
	public static final String SYSFS_COMMAND_RESET = "reset";
	
	/**
	 * This Sysfs's class name (e.g. <code>/sys/class/lego-sensor</code>, and <code>lego-sensor</code> is the class name)
	 */
	public static final String CLASS_NAME = "tacho-motor";
	
	/**
	 * This Sysfs's class name prefix (e.g. <code>/sys/class/lego-sensor/sensor0</code>, and <code>sensor</code> is the class name prefix without the [N] value.)
	 */
	public static final String CLASS_NAME_PREFIX = "motor";
	
//-----------------------------------------------------------------------------

	private String address;
	
//-----------------------------------------------------------------------------
	
	/***
	 * Creates a new motor object.
	 * @param portField a LegoPort field (e.g. LegoPort.INPUT_1)
	 * @throws EV3LibraryException If the LegoPort specified goes wrong
	 */
	public Motor(int portField) throws EV3LibraryException{
		this(new LegoPort(portField));
	}
	
	/***
	 * Creates a new motor object.
	 * @param port LegoPort
	 * @throws EV3LibraryException If the LegoPort specified goes wrong
	 */
	public Motor(LegoPort port) throws EV3LibraryException{
		this(port, CLASS_NAME_PREFIX);
	}
	
	/***
	 * Creates a new motor object.
	 * @param port LegoPort
	 * @param class_name_prefix Specify a class name prefix (e.g. motor[N], which "motor" is the prefix)
	 * @throws EV3LibraryException If the LegoPort specified goes wrong
	 */
	public Motor(LegoPort port, String class_name_prefix) throws EV3LibraryException{
		super(port, CLASS_NAME, CLASS_NAME_PREFIX);
		address = port.getAddress();
		
		//Verify is the LegoPort connecting a motor / is a output
		if (!address.contains("out")){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a output.");
		} else if (!port.getStatus().equals(CLASS_NAME)){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a motor (" + port.getStatus() + ")");
		}
	}

	/***
	 * Get the address of this motor.
	 * @return LegoPort address described in String
	 * @throws EV3LibraryException If the motor doesn't exist or IO ERROR
	 */
	public String getAddress() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_ADDRESS);
	}
	
	/***
	 * Generic method to send commands to the motor controller.
	 * @param command Command that suits for the motor driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void sendCommand(String command) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_COMMAND, command);
	}
	
	/***
	 * Cause the motor to run until another command is sent
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runForever() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RUN_FOREVER);
	}
	
	/***
	 * Run to an absolute position specified by <b>position_sp</b>
	 *  and then stop using the command specified in <b>stop_command</b>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runToAbsPos() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RUN_TO_ABS_POS);
	}
	
	/***
	 * Run to a position relative to the current position value.
	 * The new position will be <b>current position</b> + <b>position_sp</b>.
	 * When the new position is reached, the motor will stop
	 *  using the command specified by <b>stop_command</b>.
	 * @throws EV3LibraryException If I/O goes wrong.
	 */
	public void runToRelPos() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RUN_TO_REL_POS);
	}
	
	/***
	 * Run the motor for the amount of time specified in <b>time_sp</b>
	 *  and then stop the motor using the command specified by
	 *  <b>stop_command</b>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runTimed() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RUN_TIMED);
	}
	
	/***
	 * Run the motor at the duty cycle specified by <b>duty_cycle_sp</b>.
	 *  Unlike other run commands, changing <b>duty_cycle_sp</b> while
	 *   running will take effect immediately
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void runDirect() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RUN_DIRECT);
	}
	
	/**
	 * Stop any of the run commands before they are complete using the command specified by <b>stop_command</b>.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void stop() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_STOP);
	}
	
	/**
	 * Reset all of the motor parameter attributes to their default value. This will also have the effect of stopping the motor.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void reset() throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(SYSFS_COMMAND_RESET);
	}
	
	/**
	 * Returns a list of commands that are supported by the motor controller.
	 *  Possible values are run-forever, run-to-abs-pos, run-to-rel-pos,
	 *   run-timed, run-direct, stop and reset. Not all commands may be supported.
	 * @return A String Arrays with all the supported commands
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getCommands() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_COMMANDS);
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Returns the number of tacho counts in one rotation of the motor.
	 *  Tacho counts are used by the position and speed attributes, so
	 *   you can use this value to convert rotations or degrees to tacho
	 *    counts. In the case of linear actuators, the units here will
	 *     be counts per centimeter.
	 * @return Counts per cm
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getCountPerRot() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String countperrot = this.getAttribute(SYSFS_PROPERTY_COUNT_PER_ROT);
		return Integer.parseInt(countperrot);
	}
	
	//getCountPerM() Linear Motor (Just for mark down)
	
	/**
	 * Returns the name of the driver that provides this tacho motor device.
	 * @return The name of the driver
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getDriverName() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_DRIVER_NAME);
	}
	
	
	/**
	 * Returns the current duty cycle of the motor. Units are percent. Values are -100 to 100.
	 * @return Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDutyCycle() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String dutycycle = this.getAttribute(SYSFS_PROPERTY_DUTY_CYCLE);
		return Integer.parseInt(dutycycle);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @return Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDutyCycleSP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String dutycyclesp = this.getAttribute(SYSFS_PROPERTY_DUTY_CYCLE_SP);
		return Integer.parseInt(dutycyclesp);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @param sp Percentage
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setDutyCycleSP(int sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_DUTY_CYCLE_SP, Integer.toString(sp));
	}
	
	//getFullTravelCount() Linear Motor Only (Mark down)
	
	/**
	 * Sets the polarity of the motor. With normal polarity, a positive duty cycle will cause the motor to rotate clockwise.
	 *  With inversed polarity, a positive duty cycle will cause the motor to rotate counter-clockwise. Valid values are normal and inversed.
	 * @return The polarity of the motor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getPolarity() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_POLARITY);
	}
	
	/**
	 * Sets the polarity of the motor. With normal polarity, a positive duty cycle will cause the motor to rotate clockwise. With inversed polarity,
	 *  a positive duty cycle will cause the motor to rotate counter-clockwise. Valid values are normal and inversed.
	 * @param polarity The polarity of the motor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPolarity(String polarity) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POLARITY, polarity);
	}
	
	/**
	 * Returns the current position of the motor in pulses of the rotary encoder. When the motor rotates clockwise, the position will increase. Likewise,
	 *  rotating counter-clockwise causes the position to decrease. Writing will set the position to that value.
	 * @return The current position
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPosition() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute("position_p");
		return Integer.parseInt(str);
	}
	
	/**
	 * Returns the current position of the motor in pulses of the rotary encoder. When the motor rotates clockwise, the position will increase.
	 *  Likewise, rotating counter-clockwise causes the position to decrease. Writing will set the position to that value.
	 * @param position The current position
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition(int position) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POSITION, Integer.toString(position));
	}
	
	/**
	 * The proportional constant for the position PID.
	 * @return The proportional constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPosition_P() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_POSITION_P);
		return Integer.parseInt(str);
	}
	
	/**
	 * The integral constant for the position PID.
	 * @return The integral constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPosition_I() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_POSITION_I);
		return Integer.parseInt(str);
	}
	
	/**
	 * The derivative constant for the position PID.
	 * @return The derivative constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getPosition_D() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_POSITION_D);
		return Integer.parseInt(str);
	}
	
	/**
	 * The proportional constant for the position PID.
	 * @param position_p The proportional constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition_P(int position_p) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POSITION_P, Integer.toString(position_p));
	}
	
	/**
	 * The integral constant for the position PID.
	 * @param position_i The integral constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition_I(int position_i) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POSITION_I, Integer.toString(position_i));
	}
	
	/**
	 * The derivative constant for the position PID.
	 * @param position_d The derivative constant for the position PID.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition_D(int position_d) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POSITION_D, Integer.toString(position_d));
	}
	
	/**
	 * Writing specifies the target position for the run-to-abs-pos and run-to-rel-pos commands. Reading returns the current value.
	 *  Units are in tacho counts. You can use the value returned by counts_per_rot to convert tacho counts to/from rotations or degrees.
	 * @return The target position
	 * @throws EV3LibraryException if I/O goes wrong
	 */
	public int getPosition_SP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_POSITION_SP);
		return Integer.parseInt(str);
	}

	/**
	 * Writing specifies the target position for the run-to-abs-pos and run-to-rel-pos commands. Reading returns the current value.
	 *  Units are in tacho counts. You can use the value returned by counts_per_rot to convert tacho counts to/from rotations or degrees.
	 * @param position_sp The target position
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setPosition_SP(int position_sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_POSITION_SP, Integer.toString(position_sp));
	}
	
	/**
	 * Returns the current motor speed in tacho counts per second. Note, this is not necessarily degrees
	 *  (although it is for LEGO motors). Use the count_per_rot attribute to convert this value to RPM or deg/sec.
	 * @return The current speed
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getSpeed() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_SPEED);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the target speed in tacho counts per second used when speed_regulation is on.
	 *  Reading returns the current value. Use the count_per_rot attribute to convert RPM or deg/sec to tacho counts per second.
	 * @return The target speed
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getSpeed_SP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_SPEED);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the target speed in tacho counts per second used when speed_regulation is on. Reading returns the current value.
	 *  Use the count_per_rot attribute to convert RPM or deg/sec to tacho counts per second.
	 * @param speed_sp The target speed
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setSpeed_SP(int speed_sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_SPEED_SP, Integer.toString(speed_sp));
	}
	
	/**
	 * Writing sets the ramp up setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 0 to 100% duty
	 *   cycle over the span of this setpoint when starting the motor. If the maximum duty cycle is
	 *    limited by duty_cycle_sp or speed regulation,
	 *  the actual ramp time duration will be less than the setpoint.
	 * @return The ramp-up set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getRamp_Up_SP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_RAMP_UP_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the ramp up setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 0 to 100% duty
	 *   cycle over the span of this setpoint when starting the motor. If the maximum duty cycle is
	 *    limited by duty_cycle_sp or speed regulation,
	 *  the actual ramp time duration will be less than the setpoint.
	 * @param ramp_up_sp The ramp-up set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setRamp_Up_SP(int ramp_up_sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_RAMP_UP_SP, Integer.toString(ramp_up_sp));
	}
	
	/**
	 * Writing sets the ramp down setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 100% duty cycle down
	 *   to 0 over the span of this setpoint when stopping the motor. If the starting
	 *  duty cycle is less than 100%, the ramp time duration will be less than the full span of the setpoint.
	 * @return The ramp-down set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getRamp_Down_SP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_RAMP_DOWN_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing sets the ramp down setpoint. Reading returns the current value. Units are in milliseconds.
	 *  When set to a value bigger than 0, the motor will ramp the power sent to the motor from 100% duty cycle down
	 *   to 0 over the span of this setpoint when stopping the motor. If the starting
	 *  duty cycle is less than 100%, the ramp time duration will be less than the full span of the setpoint.
	 * @param ramp_down_sp The ramp-down set-point
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setRamp_Down_SP(int ramp_down_sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_RAMP_DOWN_SP, Integer.toString(ramp_down_sp));
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
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_STATE);
	}
	
	/**
	 * Reading returns a list of state flags. Possible flags are running, ramping holding and stalled.
	 * @return A list(String array) of state flags.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getState() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		String str = getStateViaString();
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Reading returns the current stop command. Writing sets the stop command. The value determines the motors behavior when command is set to stop.
	 *  Also, it determines the motors behavior when a run command completes. See stop_commands for a list of possible values.
	 * @return A stop command that listed using <code>getStopCommands()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStopAction() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_STOP_ACTION);
	}
	
	/**
	 * Reading returns the current stop command. Writing sets the stop command. The value determines the motors behavior when command is set to stop.
	 *  Also, it determines the motors behavior when a run command completes. See stop_commands for a list of possible values.
	 * @param stop_action A stop command that listed using <code>getStopCommands()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setStopAction(String stop_action) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_STOP_ACTION, stop_action);
	}
	
	/**
	 * <b>This function returns a string that is likely a "spaced-array".</b><br>
	 * <b>Use this function to directly to return a String array:</b>
	 * <pre>
	 * getStopCommands()
	 * </pre>
	 * Returns a list of stop modes supported by the motor controller. Possible values are coast,
	 *  brake and hold. coast means that power will be removed from the motor and it will freely
	 *   coast to a stop. brake means that power will be removed from the motor and a passive
	 *    electrical load will be placed on the motor. This is usually done by shorting the motor
	 *     terminals together. This load will absorb the energy from the rotation of the motors
	 *      and cause the motor to stop more quickly than coasting. hold does not remove power from
	 *       the motor. Instead it actively try to hold the motor at the current position.
	 *  If an external force tries to turn the motor, the motor will 'push back' to maintain its position.
	 * @return A list of stop modes supported by the motor controller
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getStopCommandsViaString() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(SYSFS_PROPERTY_STOP_ACTIONS);
	}
	
	/**
	 * Returns a list of stop modes supported by the motor controller. Possible values are coast,
	 *  brake and hold. coast means that power will be removed from the motor and it will freely
	 *   coast to a stop. brake means that power will be removed from the motor and a passive
	 *    electrical load will be placed on the motor. This is usually done by shorting the motor
	 *     terminals together. This load will absorb the energy from the rotation of the motors
	 *      and cause the motor to stop more quickly than coasting. hold does not remove power from
	 *       the motor. Instead it actively try to hold the motor at the current position.
	 *  If an external force tries to turn the motor, the motor will 'push back' to maintain its position.
	 * @return A list of stop modes supported by the motor controller
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getStopCommands() throws EV3LibraryException{
		if (!this.isConnected()){
			return null;
		}
		String str = getStopCommandsViaString();
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Writing specifies the amount of time the motor will run when using the run-timed command. Reading returns the current value. Units are in milliseconds.
	 * @return Amount of time in ms
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getTime_SP() throws EV3LibraryException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(SYSFS_PROPERTY_TIME_SP);
		return Integer.parseInt(str);
	}
	
	/**
	 * Writing specifies the amount of time the motor will run when using the run-timed command. Reading returns the current value. Units are in milliseconds.
	 * @param time_sp Amount of time in ms
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setTime_SP(int time_sp) throws EV3LibraryException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(SYSFS_PROPERTY_TIME_SP, Integer.toString(time_sp));
	}
	
}