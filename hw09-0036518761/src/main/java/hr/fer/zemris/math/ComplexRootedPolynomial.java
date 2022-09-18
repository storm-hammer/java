package hr.fer.zemris.math;

import static java.lang.Math.sqrt;

/**
 * 
 * @author User
 *
 */
public class ComplexRootedPolynomial {
	
	private Complex constant;
	private Complex[] roots;
	
	/**
	 * 
	 * @param constant
	 * @param roots
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * 
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex value = this.constant;
		
		for(Complex root : roots) {
			value = value.multiply(z.sub(root));
		}
		return value;
	}
	
	/**
	 * 
	 * @return
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		for(int i = 0; i < this.roots.length; i++) {
			result = result.multiply(new ComplexPolynomial(this.roots[i].negate(), Complex.ONE));
		}
		
 		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(String.format("(%s)", constant));
		for(Complex root : roots) {
			sb.append(String.format("*(z-(%s))", root));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param z
	 * @param treshold
	 * @return
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minDistance = treshold;
		
		for(int i = 0; i < roots.length; i++) {
			double distance = sqrt(roots[i].sub(z).module());
			if(distance < minDistance) {
				minDistance = distance;
				index = i;
			}
		}
		return index;
	}
	
}
