package hr.fer.oprpp1.hw04.db;

/**
 * The class is an implementation of several comparison strategies.
 * 
 * @author Mislav Prce
 */
public class ComparisonOperators {
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has smaller lexicographical value than second string; <code>false</code> otherwise.
	 */
	public static final IComparisonOperator LESS = (value1, value2) -> {
		return value1.compareTo(value2) < 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has smaller or the same lexicographical value as the second string; <code>false</code> otherwise.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) <= 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has greater lexicographical value than the second string; <code>false</code> otherwise.
	 */
	public static final IComparisonOperator GREATER = (value1, value2) -> {
		return value1.compareTo(value2) > 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has greater or the same lexicographical value as the second string;
	 * <code>false</code> otherwise.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) >= 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has the same lexicographical value as the second string;
	 * <code>false</code> otherwise.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) == 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives has different lexicographical value to the second string;
	 * <code>false</code> otherwise.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> {
		return value1.compareTo(value2) != 0;
	};
	
	/**
	 * A comparison operator which returns <code>true</code> if the first string
	 * it receives could be made by inserting any characters instead of the wildcard character
	 * in the second string; <code>false</code> otherwise.
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		
		String[] fields = value2.split("\\*");
		
		if(fields.length > 2)
			throw new IllegalArgumentException("Too many wildcard characters!");
		
		if(fields.length == 1) {
			
			int index = value2.lastIndexOf('*');
			
			if(index == value2.length() - 1) {
				return value1.startsWith(value2);
			}
			if(index == 0) {
				return value1.endsWith(value2);
			}
			if(index == -1) {
				return value1.equals(value2);
			}
		}
		
		if(fields[0].length() + fields[1].length() > value1.length()) {
			return false;
		}
		
		return value1.startsWith(fields[0]) && value1.endsWith(fields[1]);
	};
}
