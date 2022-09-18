package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {
	
	private List<XYValue> list;
	private String xDesc, yDesc;
	private int minY, maxY, x;
	
	public BarChart(List<XYValue> list, String yDesc, String xDesc, int minY, int maxY, int x) {
		if(minY < 0) {
			throw new IllegalArgumentException("Minimum height cannot be negative!");
		}
		if(maxY < minY) {
			throw new IllegalArgumentException("Maximum height cannot be less than minimum height!");
		}
		if((maxY - minY) % x != 0) {
			while((maxY - minY) % x != 0)
				x++;
			this.x = x;
		} else {
			this.x = x;
		}
		for(XYValue value : list) {
			if(value.getY() < minY) {
				throw new IllegalArgumentException("No bar can have height smaller than the minimum height");
			}
		}
		this.list = list;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		this.maxY = maxY;
		this.minY = minY;
	}

	public List<XYValue> getList() {
		return list;
	}

	public String getxDesc() {
		return xDesc;
	}

	public String getyDesc() {
		return yDesc;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public int getX() {
		return x;
	}
}
