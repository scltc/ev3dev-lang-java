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
package org.ev3dev.hardware;

//-----------------------------------------------------------------------------
//~autogen autogen-header

//~autogen
//-----------------------------------------------------------------------------

import java.io.File;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidLEDException;
import org.ev3dev.io.Sysfs;

//~autogen generic-class-description classes.led>currentClass

//~autogen

/***
 * Any device controlled by the generic LED driver. <br>
 * <br>
 * See <a href="https://www.kernel.org/doc/Documentation/leds/leds-class.txt">
 * https://www.kernel.org/doc/Documentation/leds/leds-class.txt</a> 
 * for more details.
 * @author Anthony
 *
 */
public class LED extends Device{
	
	/**
	 * The Sysfs class's <code>max_brightness</code> property name
	 */
	public static final String SYSFS_PROPERTY_MAX_BRIGHTNESS = "max_brightness";
	
	/**
	 * The Sysfs class's <code>brightness</code> property name
	 */
	public static final String SYSFS_PROPERTY_BRIGHTNESS = "brightness";
	
	/**
	 * The Sysfs class's <code>trigger</code> property name
	 */
	public static final String SYSFS_PROPERTY_TRIGGER = "trigger";
	
	/**
	 * The Sysfs class's <code>delay_on</code> property name
	 */
	public static final String SYSFS_PROPERTY_DELAY_ON = "delay_on";
	
	/**
	 * The Sysfs class's <code>delay_off</code> property name
	 */
	public static final String SYSFS_PROPERTY_DELAY_OFF = "delay_off";
	
	/**
	 * Left EV3 Button
	 */
	public static final int LEFT = 0;
	
	/**
	 * Right EV3 Button
	 */
	public static final int RIGHT = 1;
	
	/**
	 * Green color.
	 */
	public static final int GREEN = 0;
	
	/**
	 * Red color.
	 */
	public static final int RED = 1;
	
	/**
	 * This Sysfs's class name (e.g. <code>/sys/class/lego-sensor</code>, and <code>lego-sensor</code> is the class name)
	 */
	public static final String CLASS_NAME = "leds";
	
	/**
	 * Creates a new LED instance.
	 * @param leftRightField The integer field from <code>LED</code> class (e.g. <code>Button.LEFT</code>, <code>Button.RIGHT</code>)
	 * @param colorField The integer field from <code>LED</code> class (e.g. <code>Button.GREEN</code>, <code>Button.RED</code>)
	 * @throws InvalidLEDException If the specified LEFT RIGHT field or color field isn't valid.
	 */
	public LED(int leftRightField, int colorField) throws InvalidLEDException{
		super(CLASS_NAME);
		if (leftRightField != 0 && leftRightField != 1){
			throw new InvalidLEDException("You are not specifying a EV3_LEFT_LED or EV3_RIGHT_LED field!");
		}
		if (colorField != 0 && colorField != 1){
			throw new InvalidLEDException("You are not specifying a EV3_LED_GREEN or EV3_LED_RED field!");
		}
		String direction = leftRightField == 0 ? "left" : "right";
		String color = colorField == 0 ? "green" : "red";
		
		this.setClassFullname("ev3:" + direction + ":" + color + ":ev3dev");
	}
	
	/**
	 * <b>This function is for advanced users.</b><br>
	 * <b>Use this function for basic users:</b>
	 * <pre>
	 * LED led = new LED(Button.LEFT, Button.GREEN);
	 * </pre>
	 * Creates a new LED instance directly with a <code>ledName</code>
	 * @param ledName LED Name that exists in <code>/sys/class/leds</code>
	 * @throws InvalidLEDException If the specified <code>ledName</code> does not exist
	 */
	public LED(String ledName) throws InvalidLEDException{
		super(CLASS_NAME);
		File file = new File(Sysfs.getSysfsPath() + CLASS_NAME + "/" + ledName);
		if (!file.exists()){
			throw new InvalidLEDException("The specified LED does not exist");
		}
		this.setClassName(ledName);
	}
	
	/**
	 * Returns the maximum allowable brightness value.
	 * @return The maximum allowable brightness value.
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getMaxBrightness() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_MAX_BRIGHTNESS);
		return Integer.parseInt(str);
	}
	
	/**
	 * Gets the brightness level. Possible values are from 0 to max_brightness.
	 * @return The brightness level
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getBrightness() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_BRIGHTNESS);
		return Integer.parseInt(str);
	}
	
	/**
	 * Sets the brightness level. Possible values are from 0 to max_brightness.
	 * @param brightness The brightness level
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setBrightness(int brightness) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_BRIGHTNESS, Integer.toString(brightness));
	}
	
	/**
	 * <b>This function only returns a String, a spaced-array String.</b><br>
	 * <b>Use this function to return a String Array directly:</b>
	 * <pre>
	 * getTriggers()
	 * </pre>
	 * Returns a list of available triggers.
	 * @return A spaced-array String
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getTriggersViaString() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_TRIGGER);
	}
	
	/**
	 * Returns a list of available triggers.
	 * @return A String Array with a list of available triggers
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String[] getTriggers() throws EV3LibraryException{
		String str = getTriggersViaString();
		return Sysfs.separateSpace(str);
	}
	
	/**
	 * Gets the led trigger. A trigger is a kernel based source of led events. Triggers can either be simple or complex.
	 *  A simple trigger isn't configurable and is designed to slot into existing subsystems with minimal additional code.
	 *   Examples are the ide-disk and nand-disk triggers.<br>
	 *   <br>
	 *   Complex triggers whilst available to all LEDs have LED specific parameters and work on a per LED basis. The timer
	 *    trigger is an example. The timer trigger will periodically change the LED brightness between 0 and the current
	 *     brightness setting. The on and off time can be specified via delay_{on,off} attributes in milliseconds. You can 
	 *     change the brightness value of a LED independently of the timer trigger. However, if you set the brightness value
	 *      to 0 it will also disable the timer trigger.
	 * @return The LED trigger
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public String getTrigger() throws EV3LibraryException{
		return this.getAttribute(SYSFS_PROPERTY_TRIGGER);
	}
	
	/**
	 * Sets the led trigger. A trigger is a kernel based source of led events. Triggers can either be simple or complex.
	 *  A simple trigger isn't configurable and is designed to slot into existing subsystems with minimal additional code.
	 *   Examples are the ide-disk and nand-disk triggers.<br>
	 *   <br>
	 *   Complex triggers whilst available to all LEDs have LED specific parameters and work on a per LED basis. The timer
	 *    trigger is an example. The timer trigger will periodically change the LED brightness between 0 and the current
	 *     brightness setting. The on and off time can be specified via delay_{on,off} attributes in milliseconds. You can 
	 *     change the brightness value of a LED independently of the timer trigger. However, if you set the brightness value
	 *      to 0 it will also disable the timer trigger.
	 * @param selector The LED trigger that listed using <code>getTriggers()</code>
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setTrigger(String selector) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_TRIGGER, selector);
	}
	
	/**
	 * The timer trigger will periodically change the LED brightness between 0 and the current brightness setting.
	 *  The on time can be specified via delay_on attribute in milliseconds.
	 * @return The Delay_On Value in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDelay_On() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_DELAY_ON);
		return Integer.parseInt(str);
	}
	
	/**
	 * The timer trigger will periodically change the LED brightness between 0 and the current brightness setting.
	 *  The off time can be specified via delay_off attribute in milliseconds.
	 * @return The Delay_Off Value in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public int getDelay_Off() throws EV3LibraryException{
		String str = this.getAttribute(SYSFS_PROPERTY_DELAY_OFF);
		return Integer.parseInt(str);
	}
	
	/**
	 * The timer trigger will periodically change the LED brightness between 0 and the current brightness setting.
	 *  The on time can be specified via delay_on attribute in milliseconds.
	 * @param delay_on The Delay_On Value in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setDelay_On(int delay_on) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_DELAY_ON, Integer.toString(delay_on));
	}
	
	/**
	 * The timer trigger will periodically change the LED brightness between 0 and the current brightness setting.
	 *  The off time can be specified via delay_off attribute in milliseconds.
	 * @param delay_off The Delay_Off Value in milliseconds
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public void setDelay_Off(int delay_off) throws EV3LibraryException{
		this.setAttribute(SYSFS_PROPERTY_DELAY_OFF, Integer.toString(delay_off));
	}

	@Override
	public String getAddress() throws EV3LibraryException {
		return null;
	}

	@Override
	public String getDriverName() throws EV3LibraryException {
		return null;
	}
}