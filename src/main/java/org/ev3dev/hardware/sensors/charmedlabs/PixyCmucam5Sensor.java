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
package org.ev3dev.hardware.sensors.charmedlabs;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.hardware.ports.LegoPort;
import org.ev3dev.hardware.sensors.I2CSensor;

/**
 * Pixy Cmucam 5 for Lego
 * @author Anthony
 *
 */
public class PixyCmucam5Sensor extends I2CSensor {
	
	/**
	 * <code>ALL</code> Mode - All
	 */
	public static final String MODE_ALL = "ALL";
	
	/**
	 * <code>ALL</code> Mode - Signature low byte Sysfs value index
	 */
	public static final int MODE_ALL_SIG_LOW_BYTE_VALUE_INDEX = 0;
	
	/**
	 * <code>ALL</code> Mode - Signature high byte Sysfs value index 
	 */
	public static final int MODE_ALL_SIG_HIGH_BYTE_VALUE_INDEX = 1;
	
	/**
	 * <code>ALL</code> Mode - X Sysfs value index
	 */
	public static final int MODE_ALL_X_VALUE_INDEX = 2;
	
	/**
	 * <code>ALL</code> Mode - Y Sysfs value index
	 */
	public static final int MODE_ALL_Y_VALUE_INDEX = 3;
	
	/**
	 * <code>ALL</code> Mode - Width Sysfs value index
	 */
	public static final int MODE_ALL_WIDTH_VALUE_INDEX = 4;
	
	/**
	 * <code>ALL</code> Mode - Height Sysfs value index
	 */
	public static final int MODE_ALL_HEIGHT_VALUE_INDEX = 5;
	
	/**
	 * <code>SIG[N]</code> Mode (All signature modes) - Count Sysfs value index
	 */
	public static final int MODE_SIG_COUNT_VALUE_INDEX = 0;
	
	/**
	 * <code>SIG[N]</code> Mode (All signature modes) - X Sysfs value index
	 */
	public static final int MODE_SIG_X_VALUE_INDEX = 1;
	
	/**
	 * <code>SIG[N]</code> Mode (All signature modes) - Y Sysfs value index
	 */
	public static final int MODE_SIG_Y_VALUE_INDEX = 2;
	
	/**
	 * <code>SIG[N]</code> Mode (All signature modes) - Width Sysfs value index
	 */
	public static final int MODE_SIG_WIDTH_VALUE_INDEX = 3;
	
	/**
	 * <code>SIG[N]</code> Mode (All signature modes) - Height Sysfs value index
	 */
	public static final int MODE_SIG_HEIGHT_VALUE_INDEX = 4;
	
	/**
	 * The prefix of modes <code>SIG[N]</code>, where <code>SIG</code> is the prefix.
	 */
	public static final String PREFIX_MODE_SIG = "SIG";
	
	/**
	 * <code>SIG1</code> Mode - Signature #1
	 */
	public static final String MODE_SIG1 = "SIG1";
	
	/**
	 * <code>SIG2</code> Mode - Signature #2
	 */
	public static final String MODE_SIG2 = "SIG2";
	
	/**
	 * <code>SIG3</code> Mode - Signature #3
	 */
	public static final String MODE_SIG3 = "SIG3";
	
	/**
	 * <code>SIG4</code> Mode - Signature #4
	 */
	public static final String MODE_SIG4 = "SIG4";
	
	/**
	 * <code>SIG5</code> Mode - Signature #5
	 */
	public static final String MODE_SIG5 = "SIG5";
	
	/**
	 * <code>SIG6</code> Mode - Signature #6
	 */
	public static final String MODE_SIG6 = "SIG6";
	
	/**
	 * <code>SIG7</code> Mode - Signature #7
	 */
	public static final String MODE_SIG7 = "SIG7";
	
	/**
	 * Vendor ID
	 */
	public static final String VENDOR_ID = "Pixy";
	
	/**
	 * Product ID
	 */
	public static final String PRODUCT_ID = "Pixy";
	
	/**
	 * Pixy Cmucam 5 Sensor driver name
	 */
	public static final String DRIVER_NAME = "pixy-lego";
	
	/**
	 * Address
	 */
	public static final byte ADDRESS = 0x01;
	
	/**
	 * Creates a new Pixy Cmucam 5 instnace
	 * @param port The LegoPort instance
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public PixyCmucam5Sensor(LegoPort port) throws EV3LibraryException {
		super(port, DRIVER_NAME);
	}
	
	/**
	 * Returns the vendor id
	 * @return Vendor Id
	 */
	public String getVendorId(){
		return VENDOR_ID;
	}
	
	/**
	 * Returns the product id
	 * @return Product Id
	 */
	public String getProductId(){
		return PRODUCT_ID;
	}
	
	/**
	 * Set mode to <code>ALL</code>.<br>
	 * <br>
	 * And returns a <code>ModeAll</code> instance for communication.
	 * @return ModeAll instance
	 */
	public ModeAll modeAll(){
		setMode(MODE_ALL);
		return new ModeAll(MODE_ALL);
	}
	
	/**
	 * Set mode to <code>SIG[N]</code>, where <code>[N]</code> is the integer parameter specified<br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @param sigcount The value [N] of mode <code>SIG[N]</code>. (e.g. <code>SIG1</code> specify 1)
	 * @return ModeSig instance
	 */
	public ModeSig modeSig(int sigcount){
		setMode(PREFIX_MODE_SIG + sigcount);
		return new ModeSig(PREFIX_MODE_SIG + sigcount);
	}
	
	/**
	 * Set mode to <code>SIG1</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig1(){
		return modeSig(1);
	}
	
	/**
	 * Set mode to <code>SIG2</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig2(){
		return modeSig(2);
	}
	
	/**
	 * Set mode to <code>SIG3</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig3(){
		return modeSig(3);
	}
	
	/**
	 * Set mode to <code>SIG4</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig4(){
		return modeSig(4);
	}
	
	/**
	 * Set mode to <code>SIG5</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig5(){
		return modeSig(5);
	}
	
	/**
	 * Set mode to <code>SIG6</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig6(){
		return modeSig(6);
	}
	
	/**
	 * Set mode to <code>SIG7</code><br>
	 * <br>
	 * And returns a <code>ModeSig</code> instance for communication.
	 * @return ModeSig instance
	 */
	public ModeSig modeSig7(){
		return modeSig(7);
	}
	
	/**
	 * A class to communicate with Pixy Cmucam 5 using mode ALL
	 * @author Anthony
	 *
	 */
	public class ModeAll {
		
		private final String mode;
		
		private boolean autoSwitchMode = true;
		
		private ModeAll(String mode){
			this.mode = mode;
		}
		
		/**
		 * Check mode and switch automatically if <code>autoSwitchMode</code> is <code>false</code><br>
		 * Otherwise, <code>EV3LibraryException will be thrown<code>.<br>
		 * <br>
		 * Formerly called Fix Mode.
		 * @throws EV3LibraryException
		 */
		private void fixMode() throws EV3LibraryException{
			String currMode = getMode();
			if (!currMode.equals(mode)){
				if (autoSwitchMode){
					setMode(mode);
				} else {
					throw new EV3LibraryException("[Auto Switch Mode Off] You cannot use this function of mode " + mode + " with the current mode: " + currMode);
				}
			}
		}
		
		/**
		 * Sets whether it should switch mode automatically if the mode is invalid.<br>
		 * By default, it will auto switch mode.
		 * @param autoSwitchMode Switch or not
		 */
		public void setAutoSwitchMode(boolean autoSwitchMode){
			this.autoSwitchMode = autoSwitchMode;
		}
		
		/**
		 * Returns whether it will auto switch mode or not.
		 * @return Boolean
		 */
		public boolean isAutoSwitchMode(){
			return autoSwitchMode;
		}
		
		/**
		 * Get the signature low byte
		 * @return A byte representing the signature low byte
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public byte getSignatureLowByte() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_SIG_LOW_BYTE_VALUE_INDEX);
			return Byte.parseByte(str);
		}
		
		/**
		 * Get the signature high byte
		 * @return A byte representing the signature high byte
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public byte getSignatureHighByte() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_SIG_HIGH_BYTE_VALUE_INDEX);
			return Byte.parseByte(str);
		}
		
		/**
		 * Get the X point
		 * @return X point
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getX() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_X_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the Y point
		 * @return Y point
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getY() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_Y_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the width
		 * @return Width
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getWidth() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_WIDTH_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the height
		 * @return Height
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getHeight() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_ALL_HEIGHT_VALUE_INDEX);
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * A class to communicate with Pixy Cmucam 5 using mode SIG[N]
	 * @author Anthony
	 *
	 */
	public class ModeSig {
		
		private final String mode;
		
		private boolean autoSwitchMode = true;
		
		private ModeSig(String mode){
			this.mode = mode;
		}
		
		/**
		 * Check mode and switch automatically if <code>autoSwitchMode</code> is <code>false</code><br>
		 * Otherwise, <code>EV3LibraryException will be thrown<code>.<br>
		 * <br>
		 * Formerly called Fix Mode.
		 * @throws EV3LibraryException
		 */
		private void fixMode() throws EV3LibraryException{
			String currMode = getMode();
			if (!currMode.equals(mode)){
				if (autoSwitchMode){
					setMode(mode);
				} else {
					throw new EV3LibraryException("[Auto Switch Mode Off] You cannot use this function of mode " + mode + " with the current mode: " + currMode);
				}
			}
		}
		
		/**
		 * Sets whether it should switch mode automatically if the mode is invalid.<br>
		 * By default, it will auto switch mode.
		 * @param autoSwitchMode Switch or not
		 */
		public void setAutoSwitchMode(boolean autoSwitchMode){
			this.autoSwitchMode = autoSwitchMode;
		}
		
		/**
		 * Returns whether it will auto switch mode or not.
		 * @return Boolean
		 */
		public boolean isAutoSwitchMode(){
			return autoSwitchMode;
		}
		
		/**
		 * Get the count
		 * @return Count
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public byte getCount() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_SIG_COUNT_VALUE_INDEX);
			return Byte.parseByte(str);
		}
		
		/**
		 * Get the X point
		 * @return X point
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getX() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_SIG_X_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the Y point
		 * @return Y point
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getY() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_SIG_Y_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the width
		 * @return Width
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getWidth() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_SIG_WIDTH_VALUE_INDEX);
			return Integer.parseInt(str);
		}
		
		/**
		 * Get the height
		 * @return Height
		 * @throws EV3LibraryException If I/O goes wrong
		 */
		public int getHeight() throws EV3LibraryException{
			fixMode();
			String str = getAttribute("value" + MODE_SIG_HEIGHT_VALUE_INDEX);
			return Integer.parseInt(str);
		}
	}

}
