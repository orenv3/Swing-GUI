package com.swing2script.hub.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpinnerPanel extends JPanel{
	
	private final Graphics graphics = new Graphics() {
		
		@Override
		public void translate(int x, int y) {
		}
		
		@Override
		public void setXORMode(Color c1) {
		}
		
		@Override
		public void setPaintMode() {
		}
		
		@Override
		public void setFont(Font font) {
		}
		
		@Override
		public void setColor(Color c) {
		}
		
		@Override
		public void setClip(int x, int y, int width, int height) {
		}
		
		@Override
		public void setClip(Shape clip) {
		}
		
		@Override
		public FontMetrics getFontMetrics(Font f) {
			return null;
		}
		
		@Override
		public Font getFont() {
			return null;
		}
		
		@Override
		public Color getColor() {
			return null;
		}
		
		@Override
		public Rectangle getClipBounds() {
			return null;
		}
		
		@Override
		public Shape getClip() {
			return null;
		}
		
		@Override
		public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		}
		
		@Override
		public void fillRect(int x, int y, int width, int height) {
		}
		
		@Override
		public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		}
		
		@Override
		public void fillOval(int x, int y, int width, int height) {
		}
		
		@Override
		public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		}
		
		@Override
		public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		}
		
		@Override
		public void drawString(String str, int x, int y) {
		}
		
		@Override
		public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		}
		
		@Override
		public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		}
		
		@Override
		public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		}
		
		@Override
		public void drawOval(int x, int y, int width, int height) {
		}
		
		@Override
		public void drawLine(int x1, int y1, int x2, int y2) {
		}
		
		@Override
		public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
				Color bgcolor, ImageObserver observer) {
			return false;
		}
		
		@Override
		public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
				ImageObserver observer) {
			return false;
		}
		
		@Override
		public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
			return false;
		}
		
		@Override
		public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
			return false;
		}
		
		@Override
		public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
			return false;
		}
		
		@Override
		public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
			return false;
		}
		
		@Override
		public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
		}
		
		@Override
		public void dispose() {
		}
		
		@Override
		public java.awt.Graphics create() {
			return null;
		}
		
		@Override
		public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		}
		
		@Override
		public void clipRect(int x, int y, int width, int height) {
		}
		
		@Override
		public void clearRect(int x, int y, int width, int height) {
		}
	};
	
	private static SpinnerPanel instance = null;
	
	private SpinnerPanel() {
		this.setOpaque(false);
		this.setBackground(new Color(0,0,0,100));
		this.setSize(getWidth(),getHeight());
		paintComponent(graphics);
		// Set it non-opaque
		
		// Set layout to JPanel
		this.setLayout(new GridBagLayout());
		
		// Add the JLabel with the image icon
		ImageIcon gif = new ImageIcon("spinner.gif");
		Image image = gif.getImage(); // transform it 
		Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT); // scale it the smooth way  java.awt.Image.SCALE_SMOOTH
		ImageIcon gif2 = new ImageIcon(newimg);
		JLabel l = new JLabel(gif2);
		this.add(l);
	}

	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(0,0,0,140));
		g.fillRect(0,0,getWidth(),getHeight());
	}
	
	public static SpinnerPanel getInstance() {
		if(instance ==null)
			instance = new SpinnerPanel();
		return instance;
	}
	
}
