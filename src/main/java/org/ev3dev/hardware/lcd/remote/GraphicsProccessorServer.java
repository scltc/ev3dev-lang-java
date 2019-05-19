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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GraphicsProccessorServer extends Thread{

    public static final int DEFAULT_MAX_THREADS = 50;
    
    public static final int DEFAULT_PORT = 6718;
    
    private final int port;
    
    private final int maxThreads;
    
    private boolean running = false;
    
    public GraphicsProccessorServer(){
        this(DEFAULT_PORT);
    }
    
    public GraphicsProccessorServer(int port){
        this(port, DEFAULT_MAX_THREADS);
    }
    
    public GraphicsProccessorServer(int port, int maxThreads){
        this.port = port;
        this.maxThreads = maxThreads;
    }
    
    @Override
    public void run(){
        if (!running){
            running = true;
            
            try {
                ServerSocket srvSock = new ServerSocket(port);
                Socket socket;
                System.out.println("Server started. Waiting for new connection...");
                while(running){
                    socket = srvSock.accept();
                    System.out.println("Connection incoming: " + socket.getInetAddress().getHostAddress() + " starting socket thread");
                    ServerSocketHandler handler = new ServerSocketHandler(socket);
                    handler.start();
                }
                srvSock.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            running = false;
        }
    }

    public int getPort() {
        return port;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public boolean isRunning() {
        return running;
    }
}
