package hr.fer.oprpp1.hw04.db;

/**
 * The interface is a strategy for retrieving values of 
 * field names of the given records based on the concrete implementation.
 * 
 * @author Mislav Prce
 */
public interface IFieldValueGetters {
	
	/**
	 * The method retrieves the value of a certain field name based on the concrete implementation.
	 * 
	 * @param record the records whose value of the wanted field name will be returned
	 * @return the value of the wanted field name of the given record
	 */
	public String get(StudentRecord record);
}
