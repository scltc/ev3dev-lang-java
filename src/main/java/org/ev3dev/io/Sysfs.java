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
package org.ev3dev.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/***
 * A class for reading/writing to the EV3 driver system classes
 * @author Anthony
 *
 */
public class Sysfs {
	
	/**
	 * The default ev3dev Sysfs class path (/sys/class/)
	 */
	public static final String DEFAULT_SYSTEM_CLASS_PATH = "/sys/class/";
	
	private static String SYSTEM_CLASS_PATH = DEFAULT_SYSTEM_CLASS_PATH;
	
	private static boolean incompat_check = true;
	
	/**
	 * Sets the library incompatibility check to be enabled or not
	 * @param enabled Enabled
	 */
	public static void setIncompatibleCheckEnabled(boolean enabled){
		incompat_check = enabled;
	}
	
	/**
	 * Returns whether the library incompatibility check is enabled or not
	 * @param enabled Enabled
	 */
	public static boolean isIncompatibleCheckEnabled(){
		return incompat_check;
	}
	
	/**
	 * Get all sub-class files
	 * @param class_name Main Class Name
	 * @return File Array
	 */
	public static File[] getAllSubClass(String class_name){
		File file = new File(SYSTEM_CLASS_PATH + class_name);
		File[] files = file.listFiles();
		return files;
	}
	
	/**
	 * Returns the current ev3dev Sysfs path.
	 * @return the current Sysfs path
	 */
	public static String getSysfsPath(){
		return SYSTEM_CLASS_PATH;
	}
	
	/**
	 * Modify the current ev3dev Sysfs path in this library instance to be used. This is probably used for debugging purpose. Any invalid modification to the path will break the library.
	 * @param path The file-system path to be used
	 */
	public static void setSysfsPath(String path){
		SYSTEM_CLASS_PATH = path;
	}
	
	/**
	 * Sets the current ev3dev Sysfs path to default
	 */
	public static void resetSysfsPath(){
		SYSTEM_CLASS_PATH = DEFAULT_SYSTEM_CLASS_PATH;
	}
	
	/***
	 * Reads the property of the class specified.
	 * @param class_name The class name
	 * @param property The property name of the class.
	 * @return The value of the property
	 * @throws FileNotFoundException If the specified class isn't exist.
	 * @throws IOException If the API couldn't read the class's property
	 */
	public static String getAttribute(String class_name, String property) throws FileNotFoundException, IOException{
		File file = new File(SYSTEM_CLASS_PATH + class_name + "/" + property);
		class_name = class_name.toLowerCase();
		property = property.toLowerCase();
		FileInputStream in = new FileInputStream(file);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return sb.toString();
	}
	
	/***
	 * Reads the property of the class and subclass specified.
	 * @param class_name The class name.
	 * @param subclass The Sub-class name.
	 * @param property The property name of the class
	 * @return The value of the property
	 * @throws FileNotFoundException If the specified class isn't exist.
	 * @throws IOException If the API couldn't read the class's property
	 */
	public static String getAttribute(String class_name, String subclass, String property) throws FileNotFoundException, IOException{
		return getAttribute(class_name, subclass + "/" + property);
	}
	
	/***
	 * Writes the property of the class and subclass specified.
	 * @param class_name The class name.
	 * @param subclass The Sub-class name.
	 * @param property The property name of the class
	 * @param new_value The new value of the property
	 * @throws FileNotFoundException If the specified class isn't exist.
	 * @throws IOException If the API couldn't read the class's property
	 */
	public static void setAttribute(String class_name, String subclass, String property, String new_value) throws FileNotFoundException, IOException{
		setAttribute(class_name, subclass + "/" + property, new_value);
	}
	
	/***
	 * Writes the property of the class specified.
	 * @param class_name The class name.
	 * @param property The property name of the class
	 * @param new_value The new value of the property
	 * @throws FileNotFoundException If the specified class isn't exist.
	 * @throws IOException If the API couldn't read the class's property
	 */
	public static void setAttribute(String class_name, String property, String new_value) throws FileNotFoundException, IOException{
		PrintWriter out = new PrintWriter(SYSTEM_CLASS_PATH + class_name + "/" + property);
		out.write(new_value);
		out.flush();
		out.close();
	}
	
	/***
	 * A function to separate space from a spaced-array.
	 * @param space_array A string
	 * @return A array of the string/space-array
	 */
	public static String[] separateSpace(String space_array){
		int i;
		int j;
		String sep;
		List<String> list = new ArrayList<String>(50);
		for (i = 0; i < space_array.length(); i++){
			for (j = i; j < space_array.length(); j++){
				if (space_array.charAt(j) == ' '){
					break;
				}
			}
			if (j == space_array.length()){
				break;
			}
			sep = space_array.substring(i, j + 1);
			list.add(sep);
			i = j;
		}
		Object[] objarr = list.toArray();
		String[] strarr = new String[objarr.length];
		for (i = 0; i < strarr.length; i++){
			strarr[i] = (String) objarr[i];
		}
		return strarr;
	}
	
	private static String readFile(File file) throws IOException{
		FileInputStream in = new FileInputStream(file);
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * Search the full class name, using a class name, FS folder prefix and an address
	 * @param classname The class Name (e.g. lego-port, tacho-motor)
	 * @param fsFolderPrefix The FS folder prefix, without the value [N] (e.g. motor, sensor)
	 * @param address Port address (e.g. outA, in1)
	 * @return The full FS class folder name, with the same port address, if none, returns null
	 */
	public static String searchClassFullName(String classname, String fsFolderPrefix, String address){
		File[] sub = Sysfs.getAllSubClass(classname);
		File file;
		String data;
		for (File asub : sub){
			file = new File(asub.getAbsolutePath() + "/address");
			try {
				data = readFile(file);
				if(data.equals(address)){
					return asub.getName();
				}
			} catch (Exception ignore){}
		}
		return null;
	}
}
