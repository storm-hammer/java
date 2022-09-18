package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;

public class ConstraintTest {
	
	@Test
	public void firstComponentConstraintTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(1, 2)));
	}
	
	@Test
	public void constraintInBoundsTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l, new RCPosition(-1, 2)));
	}
	
	@Test
	public void multipleComponentOneConstraintTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("1");
		JLabel l2 = new JLabel("2");
		assertThrows(CalcLayoutException.class, () ->  {
			p.add(l1, new RCPosition(1, 1));
			p.add(l2, new RCPosition(1, 1));
		});
	}
	
	@Test
	public void preferredSizeTest() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
		p.add(l1, new RCPosition(2,2));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(dim, new Dimension(152, 158));
	}
	
	@Test
	public void preferredSizeTest2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
		p.add(l1, new RCPosition(1,1));
		p.add(l2, new RCPosition(3,3));
		Dimension dim = p.getPreferredSize();
		assertEquals(dim, new Dimension(152, 158));
	}
}
