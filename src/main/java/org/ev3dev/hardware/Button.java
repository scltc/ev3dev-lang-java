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

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidButtonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//~autogen generic-class-description classes.button>currentClass

//~autogen

/***
 * Provides a generic button reading mechanism that can be adapted to platform specific implementations.
 *  Each platform's specific button capabilites are enumerated in the 'platforms' section of this specification.
 * @author Anthony
 *
 */
public class Button {
    
    private static final Logger logger = LoggerFactory.getLogger(Button.class);
    
    /**
     * Default ev3dev linux GPIO Keys Event Path
     */
	public static final String SYSTEM_EVENT_PATH = "/dev/input/by-path/platform-gpio-keys.0-event";
	
	/**
	 * Represents the EV3 up button
	 */
	public static final int BUTTON_UP = 103;

    /**
     * Represents the EV3 down button
     */
	public static final int BUTTON_DOWN = 108;

    /**
     * Represents the EV3 left button
     */
	public static final int BUTTON_LEFT = 105;

    /**
     * Represents the EV3 right button
     */
	public static final int BUTTON_RIGHT = 106;

    /**
     * Represents the EV3 enter button
     */
	public static final int BUTTON_ENTER = 28;

    /**
     * Represents the EV3 backspace button
     */
	public static final int BUTTON_BACKSPACE = 14;
	
	private int button;
	
	/**
	 * Creates a new Button instance with the Button specified.
	 * @param button The Integer field of the <code>Button</code> class
	 * @throws InvalidButtonException If the specified button isn't a valid button.
	 */
	public Button(int button) throws InvalidButtonException{
	    logger.trace("Button Constructor starts");
	    logger.debug("arg: int button=" + button);
		if (button != BUTTON_UP && button != BUTTON_DOWN && button != BUTTON_LEFT &&
				button != BUTTON_RIGHT && button != BUTTON_ENTER && button != BUTTON_ENTER &&
				button != BUTTON_BACKSPACE){
		    logger.error("Unknown button constant specified at constructor: " + button);
			throw new InvalidButtonException("The button that you specified does not exist. Better use the integer fields like Button.BUTTON_UP");
		}
		this.button = button;
		logger.trace("Button Constructor ends");
	}
	
	/**
	 * Returns whether the button is pressed.
	 * @return Boolean that the button is pressed.
	 */
	public boolean isPressed(){
	    logger.trace("Method Button.isPressed() starts");
		try {
		    logger.debug("Reading from event path \"" + SYSTEM_EVENT_PATH + "\"");
			DataInputStream in = new DataInputStream(new FileInputStream(SYSTEM_EVENT_PATH));
			byte[] val = new byte[16];
			in.readFully(val);
			in.close();
			logger.debug("Read end");
			
			boolean result = test_bit(button, val);
			logger.debug("Test bit result: " + result);
			
	        logger.trace("Method Button.isPressed() ends");
			return result;
		} catch (IOException e){
		    logger.error("IOException occurred when reading \"" + SYSTEM_EVENT_PATH + "\"" + e);
            throw new EV3LibraryException("IOException occurred", e);
		}
	}
	
	private static boolean test_bit(int bit, byte[] bytes){
	    logger.trace("Method Button.test_bit(int bit, byte[] bytes) starts");
	    
	    boolean result = ((bytes[bit / 8] & (1 << (bit % 8))) != 1);
	    logger.debug("expression \"bytes[bit/8]&(1<<bit%8)))!=1\" results " + result);
	    
	    logger.trace("Method Button.test_bit(int bit, byte[] bytes) ends");
	    return result;
	}
	
	/*
	private static int EVIOCGKEY(int length){
		return 2 << (14+8+8) | length << (8+8) | ((int) 'E') << 8 | 0x18;
	}
	*/
}
