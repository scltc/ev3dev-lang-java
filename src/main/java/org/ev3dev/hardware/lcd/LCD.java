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
package org.ev3dev.hardware.lcd;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.ev3dev.exception.EV3LibraryException;

/**
 * Provides an interface to draw to the EV3's LCD
 * @author Anthony
 *
 */
public class LCD {
	
	public static final String FB_PATH = "/dev/fb0";
	
	//This should not be hard-coded, however just for testing
	public static final int SCREEN_WIDTH = 178;
	
	public static final int SCREEN_HEIGHT = 128;

	public LCD() {
		
	}
	
	/**
	 * Draws a byte array into the EV3 framebuffer
	 * @param data Byte array to be drawn (128 (height) * 178 / 8 (length) = 3072 bytes)
	 * @throws EV3LibraryException
	 */
	public void draw(byte[] data) throws EV3LibraryException{
		File file = new File(FB_PATH);
		if (!file.exists()){
			throw new EV3LibraryException("The framebuffer device does not exist! Are you using a EV3?");
		}
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.write(data);
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new EV3LibraryException("Unable to draw the LCD", e);
		}
	}

}
