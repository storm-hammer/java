package hr.fer.zemris.math;

/**
 * 
 * @author User
 *
 */
public class ComplexPolynomial {
	
	private Complex[] factors;
	
	/**
	 * 
	 * @param factors
	 */
	public ComplexPolynomial(Complex ... factors) {
		this.factors = factors;
	}
	
	/**
	 * 
	 * @return
	 */
	public short order() {
		return (short) (factors.length - 1);
	}
	
	/**
	 * 
	 * @param p
	 * @return
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
		int len1 = this.factors.length, len2 = p.factors.length;
		int size = len1 + len2 - 1;
		Complex[] newPolynomial = new Complex[size];
		
		for(int i = 0; i < size; i++) {
			newPolynomial[i] = new Complex(0, 0);
		}
		
		for(int i = 0; i < len1; i++) {
			for(int j = 0; j < len2; j++) {
				newPolynomial[i+j] = newPolynomial[i+j].add(this.factors[i].multiply(p.factors[j]));
			}
		}
		
		return new ComplexPolynomial(newPolynomial);
	}
	
	/**
	 * 
	 * @return
	 */
	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[this.factors.length-1];
		for(int i = factors.length - 1; i > 0; i--) {
			newFactors[i-1] = factors[i].multiply(new Complex(i, 0));//TO DO doc about method, indexes
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * 
	 * @param z
	 * @return
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex();
		for(int i = factors.length - 1; i > 0; i--) {//TO DO doc about method, indexes
			result = result.add(factors[i].multiply(z.power(i)));
		}
		return result.add(factors[0]);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = factors.length - 1; i > 0; i--) {//TO DO doc about method, indexes
			sb.append(String.format("(%s)*Z^%d+", factors[i], i));
		}
		sb.append(String.format("(%s)", factors[0]));
		return sb.toString();
	}
	
}