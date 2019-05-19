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

import java.io.IOException;

import org.ev3dev.exception.EV3LibraryException;

import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.io.Sysfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the base class that handles control tasks for a single port or index. The class must chose one device out of the available ports to control. Given an IO port (in the constructor), an implementation should:<br>
<br>
- If the specified port is blank or unspecified/undefined/null, the available devices should be enumerated until a suitable device is found. Any device is suitable when it's type is known to be compatible with the controlling class, and it meets any other requirements specified by the caller.<br>
<br>
- If the specified port name is not blank, the available devices should be enumerated until a device is found that is plugged in to the specified port. The supplied port name should be compared directly to the value from the file, so that advanced port strings will match, such as in1:mux3.<br>
<br>
- If an error occurs after the initial connection, an exception should be thrown by the binding informing the caller of what went wrong. Unless the error is fatal to the application, no other actions should be taken.<br>
<br>
 * @author Anthony
 *
 */
public abstract class Device {
    
    private static final Logger logger = LoggerFactory.getLogger(Device.class);
	
	private String className;
	
	private String classNamePrefix = null;
	
	private String address;
	
	private String classFullName = null;
	
	private LegoPort port;
	
	private boolean connected = false;
	
	/***
	 * Generic way to create a device
	 * @param className The Sysfs Class name
	 */
	public Device(String className){
	    logger.trace("Device Constructor starts - generic");
	    logger.debug("className="+ className);
		this.port = null;
		this.className = className;
		logger.trace("Device Constructor ends - generic");
	}

	/**
	 * Create a new device with a <b>LegoPort</b>, <b>ClassName</b>, <b>classNamePrefix</b>
	 * @param port A LegoPort delared before.
	 * @param className Sysfs class name
	 * @param classNamePrefix The filename prefix inside the "Sysfs class" (e.g. motor[n], which "motor" is the prefix)
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public Device(LegoPort port, String className, String classNamePrefix) throws EV3LibraryException{
	    logger.trace("Device Constructor starts");
		this.port = port;
		this.className = className;
		this.classNamePrefix = classNamePrefix;
		
		address = port.getAddress();
        
        logger.debug("port.getAddress()=" + address);
        logger.debug("c");
		
		connected = checkIsConnected();
		if (!connected){
			logger.info(className + "-" + this.hashCode() + ": No port connected. Searching until port \"" + address + "\" connected...");
			
			while (!connected){
				connected = checkIsConnected();
			}

			logger.info(className + "-" + this.hashCode() + ": Connected to " + address);
		}

        logger.trace("Device Constructor ends");
	}
	
	public abstract String getAddress() throws EV3LibraryException;
	
	public abstract String getDriverName() throws EV3LibraryException;
	
	/**
	 * Set the Sysfs class name (location) of this Device
	 * @param className The Sysfs class name located in <b>/sys/class</b>
	 */
	public void setClassName(String className){
		this.className = className;
	}
	
	/**
	 * Set the Sysfs class full name (including prefix if any)
	 * @param classFullName The Sysfs class name located in <b>/sys/class/[className]</b>
	 */
	public void setClassFullname(String classFullName){
		this.classFullName = classFullName;
	}
	
	/**
	 * Set the filename prefix inside the Sysfs class (prefix (e.g. motor)) of this Device
	 * @param classNamePrefix The filename inside the Sysfs class (e.g. "/sys/class/motor/motor0" <b>motor</b> is a prefix)
	 */
	public void setClassNamePrefix(String classNamePrefix){
		this.classNamePrefix = classNamePrefix;
	}
	
	/**
	 * If a valid device is found while enumerating the ports, the <b>connected</b> variable is set to <b>true</b> (by default, it should be <b>false</b>). If <b>connected</b> is <b>false</b> when an attempt is made to read from or write to a property file, an error should be thrown (except while in the consructor).
	 * @return Whether the device is ready.
	 */
	public boolean isConnected(){
		return connected;
	}
	
	/**
	 * Returns the LegoPort connected with this Device
	 * @return LegoPort object
	 */
	public LegoPort getPort(){
		return port;
	}
	
	/**
	 * Get the filename prefix inside the Sysfs class (prefix (e.g. motor)) of this Device
	 * @return The filename inside the Sysfs class (e.g. "/sys/class/motor/motor0" <b>motor</b> is a prefix)
	 */
	public String getClassNamePrefix(){
		return classNamePrefix;
	}
	
	/**
	 * Get the full filename (not prefix) inside the Sysfs class of this Device. This must be already searched by the <code>checkIsConnected()</code> method
	 * @return The filename inside the Sysfs class (e.g. "/sys/class/motor/motor0" <b>motor0</b> is the full name)
	 */
	public String getClassFullName(){
		return classNamePrefix;
	}
	
	/***
	 * Reads the property specified.
	 * @param property The property name
	 * @return The value of the property
	 */
	public final String getAttribute(String property) throws EV3LibraryException{
		try {
			String str = Sysfs.getAttribute(className, classFullName, property);
			connected = true;
			return str;
		} catch (IOException e){
			connected = false;
			throw new EV3LibraryException("Get device attribute failed: " + property, e);
		}
	}
	
	/***
	 * Writes the property specified.
	 * @param property The property name
	 * @param new_value The new value of the property
	 */
	public final void setAttribute(String property, String new_value) throws EV3LibraryException{
		try {
			Sysfs.setAttribute(className, classFullName, property, new_value);
			connected = true;
		} catch (IOException e){
			connected = false;
			throw new EV3LibraryException("Set device attribute failed: " + property, e);
		}
	}
	
	private boolean checkIsConnected(){
		try {
			classFullName = Sysfs.searchClassFullName(className, classNamePrefix, address);
		} catch (Exception ignore){
			classFullName = null;
			return false;
		}
		return classFullName != null;
	}
}