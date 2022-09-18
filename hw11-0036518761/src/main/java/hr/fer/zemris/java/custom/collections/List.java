package hr.fer.zemris.java.custom.collections;

/**
 * The interface represents a list.
 * 
 * @author Mislav Prce
 */
public interface List extends Collection {
	
	/**
	 * The method returns the object from the collection at the given index.
	 * 
	 * @param index the index from which the object is returned
	 * @return the object at the given index
	 */
	Object get(int index);
	
	/**
	 * The method inserts the given object at the given index.
	 * 
	 * @param value the object to be inserted
	 * @param position the position at which the object will be inserted
	 */
	void insert(Object value, int position);
	
	/**
	 * The method returns the index of the given object.
	 * 
	 * @param value the object whose index is being searched for
	 * @return the index of the given object
	 */
	int indexOf(Object value);
	
	/**
	 * The method removes an object from the collection at the given index.
	 * 
	 * @param index the index of the object to be removed
	 */
	void remove(int index);

}
