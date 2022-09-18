package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Vector2DTester {
	
	Vector2D vector;
	
	@Test
	public void testAdd() {
		vector = new Vector2D(1, 2);
		vector.add(new Vector2D(1, 1));
		assertEquals(new Vector2D(2, 3), vector);
	}
	
	@Test
	public void testAdded() {
		vector = new Vector2D(1, 2);
		assertEquals(new Vector2D(2, 3), vector.added(new Vector2D(1, 1)));
	}
	
	@Test
	public void testRotate() {
		vector = new Vector2D(1, 1);
		vector.rotate(0.25 * Math.PI);
		assertEquals(new Vector2D(0, 1), vector);
	}
	
	@Test
	public void testRotated() {
		vector = new Vector2D(1, 1);
		assertEquals(new Vector2D(0, 1), vector.rotated(0.25 * Math.PI));
	}
	
	@Test
	public void testScale() {
		vector = new Vector2D(1, 1);
		vector.scale(2);
		assertEquals(new Vector2D(2, 2), vector);
	}
	
	@Test
	public void testScaled() {
		vector = new Vector2D(1, 1);
		assertEquals(new Vector2D(2, 2), vector.scaled(2));
	}
	
	@Test
	public void testCopy() {
		vector = new Vector2D(1, 1);
		assertEquals(vector, vector.copy());
	}
}
