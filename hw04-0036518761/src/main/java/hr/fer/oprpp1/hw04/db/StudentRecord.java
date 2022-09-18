package hr.fer.oprpp1.hw04.db;

/**
 * The class stores information about a certain student.
 * 
 * @author Mislav Prce
 */
public class StudentRecord {
	
	private String jmbag;
	private String lastName;
	private String firstName;
	private int finalGrade;
	
	/**
	 * The constructor instances a new object of the class with the given parameters.
	 * 
	 * @param jmbag the jmbag of the student
	 * @param lastName the last name of the student
	 * @param firstName the first name of the student
	 * @param finalGrade the final grade of the student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setFinalGrade(int finalGrade) {
		this.finalGrade = finalGrade;
	}
	
	public String getJmbag() {
		return jmbag;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * The method calculates the hash code of the object based on the value of the "jmbag" field.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	/**
	 * The method returns <code>true</code> if the values of the "jmbag" fields match;
	 * <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}	
	
}
