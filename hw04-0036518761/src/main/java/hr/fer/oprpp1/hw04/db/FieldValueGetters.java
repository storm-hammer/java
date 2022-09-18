package hr.fer.oprpp1.hw04.db;

/**
 * The class contains concrete implementations of the <code>IFieldValueGetters</code> strategy.
 * 
 * @author Mislav Prce
 */
public class FieldValueGetters {
	
	/*
	 * The constant is a concrete strategy which returns the value 
	 * of the first name field of the given record.
	 */
	public static final IFieldValueGetters FIRST_NAME = (record) -> {
		return record.getFirstName();
	};
	
	/*
	 * The constant is a concrete strategy which returns the value 
	 * of the last name field of the given record.
	 */
	public static final IFieldValueGetters LAST_NAME = (record) -> {
		return record.getLastName();
	};
	
	/*
	 * The constant is a concrete strategy which returns the value 
	 * of the jmbag field of the given record.
	 */
	public static final IFieldValueGetters JMBAG = (record) -> {
		return record.getJmbag();
	};
}