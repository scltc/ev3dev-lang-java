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
package org.ev3dev.hardware.lcd.remote;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.ev3dev.hardware.lcd.LCD;

public class ServerSocketHandler extends Thread{
    
    public static final int LINE_LEN = 24;
    
    public static final int ROWS = 128;
    
    public static final int BUF_SIZE = LINE_LEN * ROWS;

    private Socket socket;
    
    private boolean running = false;
    
    public ServerSocketHandler(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run(){
        if (!running){
            running = true;
            
            try {
                socket.setSoTimeout(60000);
                long startTime = System.currentTimeMillis();
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " is awaiting commands...");
                
                byte[] bwdata = new byte[BUF_SIZE];
                
                byte[] bwarr = {(byte) 0xff, (byte) 0x00};
                IndexColorModel bwcm = new IndexColorModel(1, bwarr.length, bwarr, bwarr, bwarr);
                
                DataBuffer db = new DataBufferByte(bwdata, bwdata.length);
                WritableRaster wr = Raster.createPackedRaster(db, LCD.SCREEN_WIDTH, LCD.SCREEN_HEIGHT, 1, null);
                
                BufferedImage image = new BufferedImage(bwcm, wr, false, null);
                Graphics2D g2d = (Graphics2D) image.getGraphics();
                
                g2d.setPaint(Color.WHITE);
                g2d.setBackground(Color.WHITE);
                g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
                
                while ((line = reader.readLine()) != null){
                    if (line.startsWith("INVOKE")){
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " recevied invoke command since " + (System.currentTimeMillis() - startTime) + " ms");
                        
                        String[] splitData = line.split(" ");
                        if (splitData.length < 5){
                            System.out.println("Missing parameters " + splitData.length + " / 5");
                        }
                        String id = splitData[1];
                        String g2dClassStr = splitData[2];
                        String methodName = splitData[3];
                        String[] paraTypesStr = splitData[4].split(",");
                        String[] argsStr = splitData[5].split(",");
                        
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " at ID \"" + id + "\" is invoking G2D method \"" + methodName + "\" with " + argsStr.length + " arguments");
                        
                        Class<?> paraTypes[] = null;
                        
                        if (splitData[3].equals("none")){
                            paraTypes = new Class<?>[0];
                        } else {
                            paraTypes = new Class<?>[paraTypesStr.length];
                            for (int i = 0; i < paraTypes.length; i++){
                                try {
                                    Class<?> c = Class.forName(paraTypesStr[i]);
                                    paraTypes[i] = c;
                                } catch (ClassNotFoundException e) {
                                    System.out.println("WARNING: Class not found: " + e);
                                    continue;
                                }
                            }
                        }
                        
                        Object args[] = null;
                        if (splitData[4].equals("none")){
                            args = new Object[0];
                        } else {
                            args = new Object[argsStr.length];
                            for (int i = 0; i < args.length; i++){
                                ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(argsStr[i]));
                                ObjectInput objIn = new ObjectInputStream(in);
                                
                                try {
                                    args[i] = objIn.readObject();
                                    if (args[i] instanceof Integer){
                                        args[i] = (int) args[i];
                                    } else if (args[i] instanceof Float){
                                        args[i] = (float) args[i];
                                    }
                                } catch (ClassNotFoundException e) {
                                    System.out.println("WARNING: Class not found: " + e);
                                    continue;
                                }
                                
                                objIn.close();
                                in.close();
                            }
                        }
                        Class<?> g2dClass = null;
                        try {
                            g2dClass = Class.forName(g2dClassStr);
                        } catch (ClassNotFoundException e1) {
                            System.out.println("WARNING: Cannot find G2D class specified");
                            e1.printStackTrace();
                            continue;
                        }
                        
                        Method[] methods = g2dClass.getDeclaredMethods();
                        for (int i = 0; i < methods.length; i++){
                            System.out.println("[" + i + "] " + methods[i].getName());
                            for (int j = 0; j < methods[i].getParameterTypes().length; j++){
                                System.out.println("\t[" + i + "] containing [" + j + "] parameter: " + methods[i].getParameterTypes()[j].getName());
                            }
                        }
                        
                        Method method = null;
                        try {
                            method = g2dClass.getDeclaredMethod(methodName, paraTypes);
                        } catch (NoSuchMethodException e) {
                            System.out.println("WARNING: Method for G2D not found: " + e);
                            e.printStackTrace();
                            continue;
                        } catch (SecurityException e) {
                            System.out.println("WARNING: Cannot access to method of G2D: " + e);
                            e.printStackTrace();
                            continue;
                        }
                        
                        Object objReturn = null;
                        try {
                            objReturn = method.invoke(g2d, args);
                        } catch (IllegalAccessException e) {
                            System.out.println("WARNING: Unable to invoke method: " + e);
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            System.out.println("WARNING: Unable to invoke method: " + e);
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            System.out.println("WARNING: Unable to invoke method: " + e);
                            e.printStackTrace();
                        }
                        
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ObjectOutput objOut;
                        objOut = new ObjectOutputStream(out);
                        objOut.writeObject(objReturn);
                        objOut.flush();
                        writer.println("DONE " + id + " " + Base64.encodeBase64String(out.toByteArray()));
                        out.close();
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " at ID \"" + id + "\" successfully invoked G2D method \"" + methodName + "\" with " + argsStr.length + " arguments");
                        
                        
                    } else if (line.startsWith("GETIMAGE")){
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " recevied GETIMAGE command since " + (System.currentTimeMillis() - startTime) + " ms");
                    
                        String[] splitData = line.split(" ");
                        if (splitData.length < 2){
                            System.out.println("Missing parameters " + splitData.length + " / 2");
                        }
                        String id = splitData[1];
                        
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ObjectOutput objOut;
                        objOut = new ObjectOutputStream(out);
                        objOut.writeObject(image);
                        objOut.flush();
                        
                        writer.println("DONE " + id + " " + Base64.encodeBase64String(out.toByteArray()));
                        out.close();
                    } else if (line.startsWith("GETPROCESSED")){
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " recevied GETPROCESSED command since " + (System.currentTimeMillis() - startTime) + " ms");
                    
                        String[] splitData = line.split(" ");
                        if (splitData.length < 2){
                            System.out.println("Missing parameters " + splitData.length + " / 2");
                        }
                        String id = splitData[1];
                        
                        writer.println("DONE " + id + " " + Base64.encodeBase64String(processImage(image)));
                    } else if (line.startsWith("PROCESS")){
                        System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " recevied process command since " + (System.currentTimeMillis() - startTime) + " ms");
                        String[] splitData = line.split(" ");
                        
                        if (splitData.length < 3){
                            System.out.println("Error processing... missing parameters: " + splitData.length);
                            continue;
                        }
                        
                        String id = splitData[1];
                        String data = splitData[2];
                        
                        System.out.println("Processing data with ID: " + id + " DATA: " + data);
                        
                        ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(data));
                        BufferedImage inImage = ImageIO.read(in);
                        
                        byte[] buf = processImage(inImage);
                        
                        System.out.println("Processing Done for ID: " + id);
                        writer.println("DONE " + id + " " + Base64.encodeBase64String(buf));
                    }
                }
                System.out.println("Connection of " + socket.getInetAddress().getHostAddress() + " closed. Total connection time: " + (System.currentTimeMillis() - startTime) + " ms");
                writer.close();
                reader.close();
                socket.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            running = false;
        }
    }
    
    public static byte[] processImage(BufferedImage image){
        byte[] buf = new byte[BUF_SIZE];
        
        //byte[] pixel = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        //System.out.println("pixel[] len: " + pixel.length);
        //System.out.println("content: " + Arrays.toString(pixel));
        
        int bitPos;
        for (int i = 0; i < LCD.SCREEN_HEIGHT; i++){
            //System.out.println("rendering row " + i + "...");
            //long t = System.currentTimeMillis();
            bitPos = 0;
            for (int j = 0; j < LCD.SCREEN_WIDTH; j++){
                //int val = image.getRGB(j, i);
                //int cmb = val & 0xff + (val & 0xff00) >> 8 + (val & 0xff0000) >> 16;
                
                if (bitPos > 7){
                    //System.out.println("Overload");
                    bitPos = 0;
                }
                
                
                //TODO: Rewrite not to use getRGB()! It results in low performance
                Color color = new Color(image.getRGB(j, i));
                
                int y = (int) (0.2126 * color.getRed() + 0.7152 * color.getBlue() + 0.0722 * color.getGreen()); //Combine all colours together 255+255+255 = 765
                //System.out.println("(" + j + ", " + i + "): " + cmb);
                
                
                //byte pixelBit = pixel[i * LINE_LEN + j / 8];
                
                //System.out.println(Integer.toHexString(pixelBit));
                
                //System.out.println("Pixel: " + (i * LINE_LEN + j / 8) + " bit " + bitPos + ": " + y + " fill? " + (y < 128));
                if (y < 128){
                    //System.out.println("pixel black: " + (i * LINE_LEN + j / 8 + "@bit" + bitPos) + "y: " + y + " r:" + color.getRed() + "g:" + color.getGreen() + "b:" + color.getBlue());
                    //System.out.println("Black: " + (pixelBit & 0xff));
                    buf[i * LINE_LEN + j / 8] |= (1 << bitPos);
                } else {
                    //System.out.println("White");
                    buf[i * LINE_LEN + j / 8] &= ~(1 << bitPos);
                }
                
                bitPos++;
            }
            //System.out.println("row (" + (i) + ") getRGB used: " + (System.currentTimeMillis() - t) + " ms");
        }
        return buf;
    }
    
}
