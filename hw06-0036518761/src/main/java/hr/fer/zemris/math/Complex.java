package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * 
 * @author User
 *
 */
public class Complex {
	
	private double real, imaginary;
	
	public static final Complex ZERO = new Complex(0,0);
	public static final Complex ONE = new Complex(1,0);
	public static final Complex ONE_NEG = new Complex(-1,0);
	public static final Complex IM = new Complex(0,1);
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * 
	 */
	public Complex() {
		super();
		this.real = 0;
		this.imaginary = 0;
	}
	
	/**
	 * 
	 * @param re
	 * @param im
	 */
	public Complex(double re, double im) {
		super();
		this.real = re;
		this.imaginary = im;
	}
	
	/**
	 * 
	 * @return
	 */
	public double module() {
		return sqrt(this.real * this.real + this.imaginary * this.imaginary);
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Complex multiply(Complex c) {
		return fromMagnitudeAndAngle(this.module() * c.module(), this.getAngle() + c.getAngle());
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Complex divide(Complex c) {
		return fromMagnitudeAndAngle(this.module() / c.module(), this.getAngle() - c.getAngle());
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Complex add(Complex c) {
		return new Complex(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public Complex sub(Complex c) {
		return new Complex(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * 
	 * @return
	 */
	public Complex negate() {
		return new Complex(-1 * this.real, -1 * this.imaginary);
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("The power cannot be less than zero!");
		}
	
		return fromMagnitudeAndAngle(pow(module(), n), getAngle() * n);
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("The root cannot be less than or equal to zero!");
		}
		
		List<Complex> roots = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			roots.add(fromMagnitudeAndAngle(pow(module(), 1/(float)n), (getAngle() + 2 * PI * i) / n));
		}
		return roots;
	}
	
	@Override
	public String toString() {
		char c = imaginary < 0 ? '-' : '+';
		return Double.toString(real) + c + "i" + abs(imaginary);
	}
	
	/**
	 * 
	 * @param magnitude
	 * @param angle
	 * @return
	 */
	private static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
		return new Complex(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * 
	 * @return
	 */
	private double getAngle() {
		return atan2(imaginary, real);
	}
	
}