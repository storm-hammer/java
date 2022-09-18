package hr.fer.oprpp1.hw01;

import static java.lang.Math.*;

/**
 * The class is an implementation of an unmodifiable complex number.
 * 
 * @author Mislav Prce
 */
public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * The constructor makes a new object using the given parameters for real and
	 * imaginary parts of the number.
	 * 
	 * @param real real part of the complex number
	 * @param imaginary imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * The method makes a new complex number from the given real part of the number.
	 * 
	 * @param real real part of the complex number
	 * @return a new instance of a complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0); 
	}
	
	/**
	 * The method makes a new complex number from the given imaginary part of the number.
	 * 
	 * @param imaginary imaginary part of the complex number
	 * @return a new instance of a complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * The method makes a new complex number from the given magnitude
	 * and angle of the number.
	 * 
	 * @param magnitude magnitude of the complex number
	 * @param angle angle of the complex number
	 * @return a new instance of a complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}
	
	/**
	 * The method parses a string into a complex number.
	 * 
	 * @param s a string from which a complex number is parsed
	 * @return a new instance of a complex number
	 */
	public static ComplexNumber parse(String s) {
		
		if(s.contains("++") || s.contains("--") || s.contains("-+") || s.contains("+-")) {
			throw new IllegalArgumentException();
		}
		
		if(s.contains("i") && s.indexOf("i") != s.length() - 1) {
			throw new IllegalArgumentException();
		}
		
		if(!s.contains("i")) {
			return new ComplexNumber(Double.parseDouble(s), 0);
		}
		
		int indexOfPlus = s.lastIndexOf("+");
		int indexOfMinus = s.lastIndexOf("-");
		int indexOfSecondSign;
		
		if(indexOfMinus > indexOfPlus) {
			indexOfSecondSign = indexOfMinus;
		} else {
			indexOfSecondSign = indexOfPlus;
		}

		double real = Double.parseDouble(s.substring(0, indexOfSecondSign));
		double imaginary = Double.parseDouble(s.substring(indexOfSecondSign, s.length() - 1));
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * The method returns the real part of the current number.
	 * 
	 * @return the real part of the current number.
	 */
	public double getReal() {
		return this.real;
	}
	
	/**
	 * The method returns the imaginary part of the current number.
	 *  
	 * @return the imaginary part of the current number
	 */
	public double getImaginary() {
		return this.imaginary;
	}
	
	/**
	 * The method returns the magnitude of the current number.
	 * 
	 * @return magnitude of current number
	 */
	public double getMagnitude() {
		return sqrt(real * real + imaginary * imaginary);
	}
	
	/**
	 * The method returns the angle of the current number.
	 * 
	 * @return angle of current number
	 */
	public double getAngle() {
		return atan2(imaginary, real);
	}
	
	/**
	 * The method returns a new number made by adding the current number and the 
	 * given number.
	 * 
	 * @param c number to be added with current number
	 * @return a new instance of a complex number
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * The method returns a new number made by subtracting the given number from the 
	 * current number.
	 * 
	 * @param c the number to be subtracted from the current number
	 * @return a new instance of a complex number
	 */
	public ComplexNumber sub(ComplexNumber c) {
			return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * The method divides the current number by the given number and returns the result as
	 * a new complex number.
	 * 
	 * @param c the number by which we divide the current number
	 * @return a new instance of a complex number
	 */
	public ComplexNumber div(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() / c.getMagnitude(), this.getAngle() - c.getAngle());
	}
	
	/**
	 * The method multiplies the current number and the given number and returns
	 * the result as a new complex number.
	 * 
	 * @param c the number to be multiplied with the current number
	 * @return a new instance of a complex number
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return fromMagnitudeAndAngle(this.getMagnitude() * c.getMagnitude(), this.getAngle() + c.getAngle());
	}
	
	/**
	 * The method calculates the current number to the power of the parameter
	 * <code>n</code> and returns the result as a new number.
	 * 
	 * @param n the power of the complex number
	 * @return a new instance of a complex number
	 * @throws <code>IllegalArgumentException</code> in case of a negative argument
	 */
	public ComplexNumber power(int n) {
		
		if(n < 0) {
			throw new IllegalArgumentException();
		}
	
		return fromMagnitudeAndAngle(pow(getMagnitude(), n), getAngle() * n);
	}
	
	/**
	 * The method calculates the nth roots of the current number and returns 
	 * them as an array of complex numbers.
	 * 
	 * @param n the number of roots to be taken from the number
	 * @return a field of complex numbers
	 * @throws <code>IllegalArgumentException</code> in case of a negative argument
	 * or zero argument
	 */
	public ComplexNumber[] root(int n) {
		
		if(n <= 0) {
			throw new IllegalArgumentException();
		}
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		for(int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(pow(getMagnitude(), 1/(float)n), (getAngle() + 2 * PI * i) / n);
		}
		return roots;
	}
	
	/**
	 * The method defines the was in which the complex numbers are stored in the 
	 * form of a <code>String</code>.
	 * 
	 * @return the number in the form of a string
	 */
	public String toString() {
		char c = imaginary < 0 ? '-' : '+';
		return real + " " + c + " " + abs(imaginary) + "i";
	}
}
