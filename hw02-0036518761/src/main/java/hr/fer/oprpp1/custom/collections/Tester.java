package hr.fer.oprpp1.custom.collections;

/**
 * The interface represents an object which has a testing method implemented.
 * 
 * @author Mislav Prce
 */
public interface Tester {
	
	/**
	 * The method tests if an object satisfies certain conditions which depend
	 * on the implementation.
	 * 
	 * @param obj the object to be tested
	 * @return <code>true</code> if the object satisfies the conditions; <code>false</code> otherwise
	 */
	boolean test(Object obj);
}
