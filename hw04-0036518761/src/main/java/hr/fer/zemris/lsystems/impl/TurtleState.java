package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

/**
 * The class is an implementation of an object which can be used to 
 * draw complex drawings on the graphical interface.
 * 
 * @author Mislav Prce
 */
public class TurtleState {
	
	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double effectiveLength;
	
	/**
	 * The constructor instances a new object with the given parameters.
	 * 
	 * @param position the initial position of the turtle
	 * @param direction the initial direction of the turtle
	 * @param color the initial color of the turtle
	 * @param effectiveLength the initial effectiveLength of the turtle
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double effectiveLength) {
		super();
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.effectiveLength = effectiveLength;
	}
	
	/**
	 * The method returns a new instance of the class with the current parameters.
	 * 
	 * @return a copy of the current object
	 */
	public TurtleState copy() {
		return new TurtleState(this.position, this.direction, this.color, this.effectiveLength);
	}
	
	/**
	 * The method sets the position of the object.
	 * 
	 * @param position the new position of the object
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}
	
	/**
	 * The method sets the direction of the object.
	 * 
	 * @param direction the new direction of the object
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}
	
	/**
	 * The method sets the color of the object.
	 * 
	 * @param color the new color of the object
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * The method sets the effective length of the object.
	 * 
	 * @param effectiveLength the new effective length of the object
	 */
	public void setEffectiveLength(double effectiveLength) {
		this.effectiveLength = effectiveLength;
	}
	
	/**
	 * The method returns the position of the object.
	 * 
	 * @return the position of the object
	 */
	public Vector2D getPosition() {
		return position;
	}
	
	/**
	 * The method returns the direction of the object.
	 * 
	 * @return the direction of the object
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * The method returns the color of the object.
	 * 
	 * @return the color of the object
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * The method returns the effective length of the object.
	 * 
	 * @return the effective length of the object
	 */
	public double getEffectiveLength() {
		return effectiveLength;
	}
}
