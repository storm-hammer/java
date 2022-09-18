package hr.fer.oprpp1.hw04.db;

/**
 * The interface represents an object with only one method which checks
 * if the student record satisfies certain conditions.
 * 
 * @author Mislav Prce
 */
public interface IFilter {
	
	/**
	 * The method checks if the given record satisfies certain conditions.
	 * 
	 * @param record the record to be checked
	 * @return <code>true</code> if the record satisfies the conditions;
	 * <code>false</code> otherwise
	 */
	public boolean accepts(StudentRecord record);
}
