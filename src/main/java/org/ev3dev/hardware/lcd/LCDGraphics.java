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

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Map;

/**
 * An Graphics2D implementation to draw to the ev3dev LCD
 * @author Anthony
 *
 */
public class LCDGraphics extends Graphics2D {
	
	public static final int LINE_LEN = 24;
	
	public static final int ROWS = 128;
	
	public static final int BUF_SIZE = LINE_LEN * ROWS;
	
	private LCD lcd;
	
	private BufferedImage image;
	
	private Graphics2D g2d;

	/**
	 * Creates an instance that uses the default ev3dev LCD
	 */
	public LCDGraphics(){
		this(new LCD());
	}
	
	/**
	 * Creates an instance with an external LCD instance
	 * @param lcd
	 */
	public LCDGraphics(LCD lcd) {
		this.lcd = lcd;
		
		byte[] data = new byte[BUF_SIZE];
		
		byte[] bwarr = {(byte) 0xff, (byte) 0x00};
		IndexColorModel bwcm = new IndexColorModel(1, bwarr.length, bwarr, bwarr, bwarr);
		
		DataBuffer db = new DataBufferByte(data, data.length);
		WritableRaster wr = Raster.createPackedRaster(db, LCD.SCREEN_WIDTH, LCD.SCREEN_HEIGHT, 1, null);
		
		this.image = new BufferedImage(bwcm, wr, false, null);
		this.g2d = (Graphics2D) image.getGraphics();
		
		g2d.setPaint(Color.WHITE);
		g2d.setBackground(Color.WHITE);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
	}
	
	/**
	 * Returns the LCD instance
	 * @return LCD
	 */
	public LCD getLcd(){
	    return lcd;
	}
	
	/**
	 * Returns the rendering BufferedImage instance
	 * @return BufferedImage
	 */
	public BufferedImage getImage(){
		return image;
	}
	
	/**
	 * Applies the Graphics context onto the ev3dev's LCD
	 */
	public void flush(){
		byte[] buf = new byte[BUF_SIZE];
		
		byte[] pixel = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
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
		
		
		lcd.draw(buf);
	}

	@Override
	public void draw(Shape s) {
		g2d.draw(s);
	}

	@Override
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		return g2d.drawImage(img, xform, obs);
	}

	@Override
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		g2d.drawImage(img, op, x, y);
	}

	@Override
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		g2d.drawRenderedImage(img, xform);
	}

	@Override
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		g2d.drawRenderableImage(img, xform);
	}

	@Override
	public void drawString(String str, int x, int y) {
		g2d.drawString(str, x, y);
	}

	@Override
	public void drawString(String str, float x, float y) {
		g2d.drawString(str, x, y);
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		g2d.drawString(iterator, x, y);
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, float x, float y) {
		g2d.drawString(iterator, x, y);
	}

	@Override
	public void drawGlyphVector(GlyphVector g, float x, float y) {
		g2d.drawGlyphVector(g, x, y);
	}

	@Override
	public void fill(Shape s) {
		g2d.fill(s);
	}

	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		return g2d.hit(rect, s, onStroke);
	}

	@Override
	public GraphicsConfiguration getDeviceConfiguration() {
		return g2d.getDeviceConfiguration();
	}

	@Override
	public void setComposite(Composite comp) {
		g2d.setComposite(comp);
	}

	@Override
	public void setPaint(Paint paint) {
		g2d.setPaint(paint);
	}

	@Override
	public void setStroke(Stroke s) {
		g2d.setStroke(s);
	}

	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		g2d.setRenderingHint(hintKey, hintValue);
	}

	@Override
	public Object getRenderingHint(Key hintKey) {
		return g2d.getRenderingHint(hintKey);
	}

	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		g2d.setRenderingHints(hints);
	}

	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		g2d.addRenderingHints(hints);
	}

	@Override
	public RenderingHints getRenderingHints() {
		return g2d.getRenderingHints();
	}

	@Override
	public void translate(int x, int y) {
		g2d.translate(x, y);
	}

	@Override
	public void translate(double tx, double ty) {
		g2d.translate(tx, ty);
	}

	@Override
	public void rotate(double theta) {
		g2d.rotate(theta);
	}

	@Override
	public void rotate(double theta, double x, double y) {
		g2d.rotate(theta, x, y);
	}

	@Override
	public void scale(double sx, double sy) {
		g2d.scale(sx, sy);
	}

	@Override
	public void shear(double shx, double shy) {
		g2d.shear(shx, shy);
	}

	@Override
	public void transform(AffineTransform Tx) {
		g2d.transform(Tx);
	}

	@Override
	public void setTransform(AffineTransform Tx) {
		g2d.setTransform(Tx);
	}

	@Override
	public AffineTransform getTransform() {
		return g2d.getTransform();
	}

	@Override
	public Paint getPaint() {
		return g2d.getPaint();
	}

	@Override
	public Composite getComposite() {
		return g2d.getComposite();
	}

	@Override
	public void setBackground(Color color) {
		g2d.setBackground(color);
	}

	@Override
	public Color getBackground() {
		return g2d.getBackground();
	}

	@Override
	public Stroke getStroke() {
		return g2d.getStroke();
	}

	@Override
	public void clip(Shape s) {
		g2d.clip(s);
	}

	@Override
	public FontRenderContext getFontRenderContext() {
		return g2d.getFontRenderContext();
	}

	@Override
	public Graphics create() {
		return g2d.create();
	}

	@Override
	public Color getColor() {
		return g2d.getColor();
	}

	@Override
	public void setColor(Color c) {
		g2d.setColor(c);
	}

	@Override
	public void setPaintMode() {
		g2d.setPaintMode();
	}

	@Override
	public void setXORMode(Color c1) {
		g2d.setXORMode(c1);
	}

	@Override
	public Font getFont() {
		return g2d.getFont();
	}

	@Override
	public void setFont(Font font) {
		g2d.setFont(font);
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		return g2d.getFontMetrics(f);
	}

	@Override
	public Rectangle getClipBounds() {
		return g2d.getClipBounds();
	}

	@Override
	public void clipRect(int x, int y, int width, int height) {
		g2d.clipRect(x, y, width, height);
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		g2d.setClip(x, y, width, height);
	}

	@Override
	public Shape getClip() {
		return g2d.getClip();
	}

	@Override
	public void setClip(Shape clip) {
		g2d.setClip(clip);
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		g2d.copyArea(x, y, width, height, dx, dy);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		g2d.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		g2d.fillRect(x, y, width, height);
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		g2d.clearRect(x, y, width, height);
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		g2d.drawOval(x, y, width, height);
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		g2d.fillOval(x, y, width, height);
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		g2d.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		g2d.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		g2d.drawPolyline(xPoints, yPoints, nPoints);
	}

	@Override
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		g2d.drawPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		g2d.fillPolygon(xPoints, yPoints, nPoints);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		return g2d.drawImage(img, x, y, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
		return g2d.drawImage(img, x, y, width, height, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
		return g2d.drawImage(img, x, y, bgcolor, observer);
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
		return g2d.drawImage(img, x, y, width, height, observer);
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			ImageObserver observer) {
		return g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
			Color bgcolor, ImageObserver observer) {
		return g2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
	}

	@Override
	public void dispose() {
		g2d.dispose();
		flush();
	}

}
