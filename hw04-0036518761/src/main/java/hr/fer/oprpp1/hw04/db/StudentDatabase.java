package hr.fer.oprpp1.hw04.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The class represents an implementation of a simple database.
 * 
 * @author Mislav Prce
 */
public class StudentDatabase {
	
	List<StudentRecord> records;
	Map<String, StudentRecord> index;
	
	/**
	 * The constructor instances a new database with a given list of records.
	 * 
	 * @param list the list of records to be put in the database
	 */
	public StudentDatabase(List<String> list) {
		this.records = new ArrayList<>(list.size());
		this.index =  new HashMap<>(list.size());
		parse(list);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException {
		
		File text = new File("C:\\javaproj\\hw04-0036518761\\src\\main\\java\\hr\\fer\\oprpp1\\hw04\\db\\database.txt");
		List<String> entries = new ArrayList<>();
		Scanner sc = new Scanner(text);
		
		while(sc.hasNext()) {
			String line = sc.nextLine();
			if(line != "") {
				entries.add(line);
			} else {
				break;
			}
		}
		sc.close();
		
		StudentDatabase db = new StudentDatabase(entries);
	}
	
	/**
	 * The method parses a list of strings into records and adds them to the database.
	 * 
	 * @param list the list of strings to be parsed and added into the database
	 */
	private void parse(List<String> list) {
		
		for(int i = 0, len = list.size(); i < len; i++) {
			
			String[] record = list.get(i).split("\\s+");
			StudentRecord student;
			
			if(record.length == 5) {
				student = new StudentRecord(record[0], record[1] + " " + record[2],
						record[3], Integer.parseInt(record[4]));
			} else {
				student = new StudentRecord(record[0], record[1], record[2], Integer.parseInt(record[3]));
			}
			
			if(records.contains(student) || student.getFinalGrade() < 1 || student.getFinalGrade() > 5) {
				throw new IllegalArgumentException("Invalid input for students! Invalid grade or duplicate input");
			}
			
			records.add(student);
			index.put(student.getJmbag(), student);
		}
	}
	
	/**
	 * The method retrieves the student record for the given jmbag in O(1) complexity.
	 * 
	 * @param jmbag the jmbag for which the appropriate student record is retrieved
	 * @return the student record with the given jmbag if it is present in the database;
	 * <code>null</code> otherwise
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * The method filters the records from the database using the provided <code>IFilter</code> object.
	 * The method returns a new list of student records which satisfy the given filter.
	 * 
	 * @param filter the object whose method <code>accepts</code> filters the records
	 * @return a new list consisting of records which satisfy the given filter
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> acceptedRecords = new ArrayList<>();
		
		for(int i = 0, len = records.size(); i < len; i++) {
			StudentRecord record = records.get(i);
			if(filter.accepts(record)) {
				acceptedRecords.add(record);
			}
		}
		return acceptedRecords;
	}

}
