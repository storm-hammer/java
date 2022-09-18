package hr.fer.oprpp1.hw04.db;

/**
 * The interface is a strategy of comparing two <code>String</code> values
 * based on the concrete implementation.
 * 
 * @author Mislav Prce
 */
public interface IComparisonOperator {
	
	/**
	 * The method checks if the first string satisfies the conditions based on the 
	 * second string.
	 * 
	 * @param value1 the string being checked
	 * @param value2 the string defining the conditions the first string has to meet
	 * @return <code>true</code> if the conditions defined by the second string are met;
	 * <code>false</code> otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
