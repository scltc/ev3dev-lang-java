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

import java.awt.image.BufferedImage;
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
import java.net.Socket;
import java.security.SecureRandom;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class GraphicsProccessorClient extends Thread{

    public static final int DEFAULT_PORT = 6718;
    
    private final String hostname;
    
    private final int port;
    
    private Socket socket = null;
    
    public GraphicsProccessorClient(String hostname){
        this(hostname, DEFAULT_PORT);
    }
    
    public GraphicsProccessorClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }
    
    public void newSocket() throws IOException{
        if (socket != null){
            socket.close();
        }
        socket = new Socket(hostname, port);
    }
    
    public BufferedImage getImage() throws IOException{
        
        SecureRandom rand = new SecureRandom();
        int num = rand.nextInt();
        
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("GETIMAGE " + num);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        long startTime = System.currentTimeMillis();
        while ((line = reader.readLine()) != null){
            if (line.startsWith("DONE " + num + " ")){
                break;
            } else if (System.currentTimeMillis() - startTime >= 5000){
                socket.close();
                throw new IOException("Invoker does not respond in 5000 ms");
            }
        }
        
        String[] splitData = line.split(" ");
        
        ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(splitData[2]));
        ObjectInput objIn = new ObjectInputStream(in);
        BufferedImage obj;
        try {
            obj = (BufferedImage) objIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Returned object's cannot be found", e);
        }
        
        return obj;
    }
    
    public Object invokeG2dMethod(Class<?> g2dClass, String methodName, Object... args) throws IOException{
        if (socket == null){
            throw new IOException ("Socket not initialized");
        } else if (socket.isClosed()){
            throw new IOException("Socket is closed. Please renew it using newSocket()");
        }
        
        String paraTypeStr = "";
        for (int i = 0; i < args.length; i++){
            paraTypeStr += args[i].getClass().getName();
            if (i != args.length - 1){
                paraTypeStr += ",";
            }
        }
        
        if (paraTypeStr.isEmpty()){
            paraTypeStr = "none";
        }
        
        String valStr = "";
        for (int i = 0; i < args.length; i++){
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutput objOut = null;
            objOut = new ObjectOutputStream(out);
            objOut.writeObject(args[i]);
            objOut.flush();
            valStr += Base64.encodeBase64String(out.toByteArray());
            out.close();
        }
        
        if (valStr.isEmpty()){
            valStr = "none";
        }
        
        SecureRandom rand = new SecureRandom();
        int num = rand.nextInt();

        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("INVOKE " + num + " " + g2dClass.getName() + " " + methodName + " " + paraTypeStr + " " + valStr);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        long startTime = System.currentTimeMillis();
        while ((line = reader.readLine()) != null){
            if (line.startsWith("DONE " + num + " ")){
                break;
            } else if (System.currentTimeMillis() - startTime >= 5000){
                socket.close();
                throw new IOException("Invoker does not respond in 5000 ms");
            }
        }
        
        String[] splitData = line.split(" ");
        
        
        ByteArrayInputStream in = new ByteArrayInputStream(Base64.decodeBase64(splitData[2]));
        ObjectInput objIn = new ObjectInputStream(in);
        Object obj;
        try {
            obj = objIn.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Returned object's cannot be found", e);
        }
        
        return obj;
    }
    
    public byte[] getProcessed() throws IOException{
        if (socket == null){
            throw new IOException ("Socket not initialized");
        } else if (socket.isClosed()){
            throw new IOException("Socket is closed. Please renew it using newSocket()");
        }
        
        SecureRandom rand = new SecureRandom();
        int num = rand.nextInt();
        
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("GETPROCESSED " + num);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        long startTime = System.currentTimeMillis();
        while ((line = reader.readLine()) != null){
            if (line.startsWith("DONE " + num + " ")){
                break;
            } else if (System.currentTimeMillis() - startTime >= 5000){
                socket.close();
                throw new IOException("Remote image processing does not respond in 5000 ms");
            }
        }
        writer.close();
        reader.close();
        
        String[] splitData = line.split(" ");
        
        if (splitData.length < 3){
            socket.close();
            throw new IOException("Invalid response received. Array length is " + splitData.length + " / 3");
        }
        
        String data = splitData[2];
        
        return Base64.decodeBase64(data);
    }
    
    public byte[] process(BufferedImage image) throws IOException{
        Socket socket = new Socket(hostname, port);
        socket.setSoTimeout(10000);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        ImageIO.write(image, "png", out);
        
        SecureRandom rand = new SecureRandom();
        int num = rand.nextInt();
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        writer.println("PROCESS " + num + " " + Base64.encodeBase64String(out.toByteArray()));
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        long startTime = System.currentTimeMillis();
        while ((line = reader.readLine()) != null){
            if (line.startsWith("DONE " + num + " ")){
                break;
            } else if (System.currentTimeMillis() - startTime >= 5000){
                socket.close();
                throw new IOException("Remote image processing does not respond in 5000 ms");
            }
        }
        writer.close();
        reader.close();
        
        String[] splitData = line.split(" ");
        
        if (splitData.length < 3){
            socket.close();
            throw new IOException("Invalid response received. Array length is " + splitData.length + " / 3");
        }
        
        String data = splitData[2];
        
        socket.close();
        return Base64.decodeBase64(data);
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
