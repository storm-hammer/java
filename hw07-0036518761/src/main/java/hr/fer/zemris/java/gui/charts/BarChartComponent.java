package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

@SuppressWarnings("unused")
public class BarChartComponent extends JComponent {
	
	private static final long serialVersionUID = 1L;
	private BarChart chart;
	
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = new AffineTransform();
		FontMetrics fm = g.getFontMetrics();
		
		int arrowWidth = 5, arrowHeight = 5;
		int yDescLength = 6 * chart.getyDesc().length();
		int xDescLength = 3 * chart.getxDesc().length();
		
		int lenDesc = 15;
		int lenNumbers = lenDesc + fm.getHeight();
		int lenAxis = lenNumbers + lenDesc;
		
		int y1 = dim.height - lenAxis;
		int x1 = dim.width - lenAxis;
		
		drawArrowLine(g, lenAxis, y1, x1, y1, arrowWidth, arrowHeight);//x os
		drawArrowLine(g, lenAxis, y1, lenAxis, 0, arrowWidth, arrowHeight);//y os
		
		List<XYValue> list = chart.getList();
		int listSize = list.size();
		int columnWidth = (dim.width - lenAxis * 2) / listSize;
		int maxY = chart.getMaxY();
		
		for(int i = 0; i < maxY; i++) {
			double y = (maxY - i) /(double)maxY * y1;
			
			g.setColor(Color.ORANGE);
			g.drawLine(lenAxis - 5, (int)y, x1, (int)y);
			
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(i), lenAxis - 20, (int)y);
		}
		
		for(int i = 0; i < listSize; i++) {
			XYValue value = list.get(i);
			double y = (maxY - value.getY()) / (double)(maxY) * y1;
			int x = lenAxis + columnWidth * i + 1;
			double columnHeight = (value.getY() / (double)maxY) * y1;
			
			g.setColor(Color.RED);
			g.fill3DRect(x, (int)y, columnWidth - 1 ,(int) columnHeight + 1, true);
			
			g.setColor(Color.ORANGE);
			g.drawLine(x-1, 0, x-1, y1 + 5);
			
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(i), x + columnWidth/2, dim.height - lenNumbers);
		}
		
		g.setColor(Color.BLACK);
		g.drawString(chart.getxDesc(), dim.width/2 - xDescLength, dim.height - 15);
		
		at.rotate(-Math.PI / 2);	 
		g2.setTransform(at);
		g2.drawString(chart.getyDesc(), -dim.height/2 - yDescLength, lenDesc);		
		
	}
	
	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g.drawLine(x1, y1, x2, y2);
	    g.fillPolygon(xpoints, ypoints, 3);
	}
}
