package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.LinkedHashMap;
import java.util.Map;
import hr.fer.zemris.java.gui.layouts.generators.*;

public class CalcLayout implements LayoutManager2 {
	
	private int gap;
	private Map<Component, RCPosition> constraints;
	
	public CalcLayout(int gap) {
		super();
		this.gap = gap;
		this.constraints = new LinkedHashMap<Component, RCPosition>();
	}
	
	public CalcLayout() {
		super();
		this.gap = 0;
		this.constraints = new LinkedHashMap<Component, RCPosition>();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Ne smijete pozivatu ovu metodu!");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		this.constraints.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return Utility.calculateSize(parent, constraints, new PreferredSize());
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return Utility.calculateSize(target, constraints, new MaximumSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return Utility.calculateSize(parent, constraints, new MinimumSize());
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets ins = parent.getInsets();
		double h = (parent.getHeight() - ins.top - ins.bottom) /(double) 5;
		double w = (parent.getWidth() - ins.left - ins.right) /(double) 7;
		
		for(Component c : parent.getComponents()) {
			RCPosition cons = constraints.get(c);
			if(cons != null) {
				if(cons.getColumn() == 1 && cons.getRow() == 1) {//first component, different than others
					c.setBounds(ins.left, ins.top, (int) (w * 5 + gap * 4),(int) h);
				} else {
					double x = ins.left + (cons.getColumn() - 1) * (gap + w);
					double y = ins.top + (cons.getRow() - 1) * (gap + h);
					c.setBounds((int) x,(int) y,(int) w,(int) h);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		
		if(comp == null || constraints == null) {
			throw new NullPointerException("Neither the component or the constraint can be null!");
		}
		
		if(this.constraints.get(comp) != null) {
			if(this.constraints.get(comp).equals(constraints)) {
				throw new CalcLayoutException("Ne možete dodati dvije komponente s istim ograničenjem!");
			}
		}
		
		RCPosition cons;
		if(constraints instanceof RCPosition) {
			cons = (RCPosition) constraints;
		} else if(constraints instanceof String) {
			cons = RCPosition.parse((String)constraints);
		} else {
			throw new IllegalArgumentException("Komponenta nema pravilan oblik ograničenja!");
		}
		
		int row = cons.getRow(), column = cons.getColumn();
		
		if(row < 1 || row > 5 || column < 1 || column > 7) {
			throw new CalcLayoutException("Komponenta izvan granica!");
		}
		
		if(row == 1 && column > 1 && column < 6) {
			throw new CalcLayoutException("Komponenta na ilegalnom mjestu!");
		}
		
		this.constraints.put(comp, cons);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
}
