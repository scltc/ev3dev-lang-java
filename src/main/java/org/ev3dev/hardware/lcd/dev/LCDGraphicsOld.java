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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.hardware.lcd.LCD;

/**
 * This is a old, incomplete implementation of LCDGraphics.
 * This is already abandoned, just for keeping a moment.
 * @author Anthony
 *
 */
public class LCDGraphicsOld extends Graphics {
	
	public static final int LINE_LEN = 24;
	
	public static final int ROWS = 128;
	
	public static final int BUF_SIZE = LINE_LEN * ROWS;
	
	private LCD lcd;
	
	private boolean whiteColor = false;
	
	private byte[] buf;
	
	private int transx = 0;
	
	private int transy = 0;
	
	private Font font;

	public LCDGraphicsOld(LCD lcd) {
		this.lcd = lcd;
		buf = new byte[BUF_SIZE];
	}
	
	/**
	 * Applies the Graphics context onto the ev3dev's LCD
	 */
	public void flush(){
		lcd.draw(buf);
	}

	/**
	 * Not implemented
	 */
	@Override
	public Graphics create() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void translate(int x, int y) {
		this.transx = x;
		this.transy = y;
	}
	
	public void setWhiteColor(){
		whiteColor = true;
	}
	
	public void setBlackColor(){
		whiteColor = false;
	}

	@Override
	public Color getColor() {
		return whiteColor ? Color.WHITE : Color.BLACK;
	}

	@Override
	public void setColor(Color c) throws EV3LibraryException{
		if (c == null || !c.equals(Color.BLACK) || !c.equals(Color.WHITE)){
			throw new EV3LibraryException("The EV3 LCD only supports Color.BLACK and Color.WHITE");
		}
		whiteColor = c.equals(Color.WHITE);
	}

	/**
	 * The ev3 LCD does not support PaintMode
	 */
	@Override
	public void setPaintMode() {
		throw new EV3LibraryException("The EV3 LCD does not support PaintMode");
	}

	/**
	 * The ev3 LCD does not support XORMode
	 */
	@Override
	public void setXORMode(Color c1) {
		throw new EV3LibraryException("The EV3 LCD does not support XORMode");
	}
	
	/**
	 * Not implemented
	 */
	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFont(Font font) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Not implemented
	 */
	@Override
	public FontMetrics getFontMetrics(Font f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getClipBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clipRect(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shape getClip() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClip(Shape clip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		
	}
	
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) { //Uses Bresenham's line algorithm
		boolean steep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
		
		if (steep){
			int tmp = x1;
			x1 = y1;
			y1 = tmp;
			
			tmp = x2;
			x2 = y2;
			y2 = x2;
		}
		
		if (x1 > x2){
			int tmp = x1;
			x1 = x2;
			x2 = tmp;
			
			tmp = y1;
			y1 = y2;
			y2 = tmp;
		}
		
		double deltax = x2 - x1;
		double deltay = Math.abs(y2 - y1);
		
		double err = deltax / 2;
		
		int xlen = x2 - x1;
		int y = y1;
		
		int ystep;
		if (y1 < y2){
			ystep = 1;
		} else {
			ystep = -1;
		}
		
		for (int i = 0; i < xlen; i++){
			if (steep){
				plot(y,i);
			} else {
				plot(i,y);
			}
			
			err -= deltay;
			if (err < 0){
				y += ystep;
				err += deltax;
			}
		}
	}
	
	/**
	 * Plot the specified (x,y) position with the selected color (Color.BLACK or Color.WHITE)
	 * @param x Position x
	 * @param y Position y
	 */
	public void plot(int x, int y){
		buf[y * LINE_LEN + x / 8] = (byte) (whiteColor ? 0x00 : 0xff);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				buf[(((i+y) * LINE_LEN)) + (x + j) / 8] = (byte) (whiteColor ? 0x00 : 0xff);
			}
		}
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				buf[(((i+y) * LINE_LEN)) + (x + j) / 8] = (byte) 0x00;
			}
		}
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(String str, int x, int y) {
		System.out.println("Draw string");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		System.out.println("Draw string2");
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
