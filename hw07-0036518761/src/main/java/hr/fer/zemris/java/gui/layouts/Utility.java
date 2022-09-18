package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Map;

public class Utility {
	
	public static Dimension calculateSize(Container parent, Map<Component, RCPosition> constraints, Size g) {
		
		Dimension result = new Dimension(0, 0);
		
		for(Component c : parent.getComponents()) {
			if(constraints.get(c) != null) {
				Dimension currentSize = g.generate(c);
				
				if(currentSize == null) {
					continue;
				}
				
				int height = (int) (result.getHeight() + currentSize.getHeight());
				int width = (int) (result.getWidth() + currentSize.getWidth());
				result = new Dimension(width, height);
				
//				if(currentSize.height > result.height) {
//					result.height = currentSize.height;
//				}
//				
//				if(currentSize.width > result.width) {
//					result.width = currentSize.width;
//				}
			}
		}
		return result;
	}
}
