package hr.fer.zemris.lsystems.impl;

import static java.lang.Math.*;

/**
 * The class is an implementation of a two-dimensional vector.
 * 
 * @author Mislav Prce
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * The constructor makes a new instance of a vector with the given values of x and y.
	 * 
	 * @param x the x-coordinate of the vector to be made
	 * @param y the y-coordinate of the vector to be made
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * The method returns the x-coordinate of the vector.
	 * 
	 * @return x-coordinate of the vector
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * The method returns the y-coordinate of the vector.
	 * 
	 * @return y-coordinate of the vector
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * The method adds the given vector to the current vector.
	 * 
	 * @param offset the vector to be added to the current vector
	 */
	public void add(Vector2D offset) {
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * The method returns a new vector which is a result of adding the current vector and 
	 * the offset vector.
	 * 
	 * @param offset the vector to be added with the current vector
	 * @return a new vector which is the result of adding the offset vector to the current vector
	 */
	public Vector2D added(Vector2D offset) {
		return new Vector2D(this.x + offset.x, this.y + offset.y);
	}
	
	/**
	 * The method rotates the current vector by an angle equal to the value of the
	 * parameter <code>angle</code>.
	 * 
	 * @param angle the angle by which the vector is rotated
	 */
	public void rotate(double angle) {
		double x2 = cos(angle) * this.x - sin(angle) * this.y;
		this.y = sin(angle) * this.x + cos(angle) * this.y;
		this.x = x2;
	}
	
	/**
	 * The method returns a new vector which is a result of rotating the current 
	 * vector by an angle equal to the value of the parameter <code>angle</code>.
	 * 
	 * @param angle the angle by which the current vector is rotated
	 * @return a new vector based on the rotation of the current vector
	 */
	public Vector2D rotated(double angle) {
		return new Vector2D(cos(angle) * this.x - sin(angle) * this.y, sin(angle) * this.x + cos(angle) * this.y);
	}
	
	/**
	 * The method scales the current vector with a multiplier of
	 * the given parameter <code>scaler</code>.
	 * 
	 * @param scaler the multiplier by which the vector is scaled
	 */
	public void scale(double scaler) {
		this.x *= scaler;
		this.y *= scaler;
	}
	
	/**
	 * The method returns a new vector which is the result of scaling the
	 * current vector with multiplier of <code>scaler</code>.
	 * 
	 * @param scaler the multiplier by which the vector is scaled
	 * @return a new vector which is the result of scaling the current vector
	 * by a multiplier of <code>scaler</code>
	 */
	public Vector2D scaled(double scaler) {
		return new Vector2D(this.x * scaler, this.y * scaler);
	}
	
	/**
	 * The method returns a new vector which is a copy of the current vector.
	 * 
	 * @return a new copy of the current vector
	 */
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(!(o instanceof Vector2D)) {
			return false;
		}
		Vector2D other = (Vector2D) o;
		return abs(this.x - other.x) < 1E-8 && abs(this.y - other.y) < 1E-8;
	}
}
