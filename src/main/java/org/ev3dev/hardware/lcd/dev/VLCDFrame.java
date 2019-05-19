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
package org.ev3dev.hardware.lcd.dev;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.ev3dev.hardware.lcd.dev.VirtualLCD;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class VLCDFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6540608026156477027L;
	private JPanel contentPane;
	private VirtualLCD lcd;
	private Thread thread;
	private JLabel lblImg;

	private boolean running = false;
	/**
	 * Create the frame.
	 */
	public VLCDFrame(VirtualLCD vlcd) {
		setTitle("ev3dev-lang-java VirtualLCD");
		this.lcd = vlcd;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 433, 318);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblImg = new JLabel("");
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblImg, BorderLayout.CENTER);
		
		thread = new Thread(new Runnable(){

			public void run() {
				if (!running){
					running = true;
					while(running){
						lblImg.setIcon(new ImageIcon(lcd.getImage()));
					}
					running = false;
				}
			}
			
		});
		thread.start();
	}

}
