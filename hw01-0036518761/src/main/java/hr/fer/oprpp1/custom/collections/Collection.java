package hr.fer.oprpp1.custom.collections;

/**
 * The class <code>Collection</code> represents a general
 * collection of objects.
 * 
 * @author Mislav Prce
 */
public class Collection {
	
	/**
	 * The method checks if the collection is empty or not.
	 * 
	 * @return <code>true</code> if the collection contains 
	 * no objects; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * The method returns the number of currently stored objects
	 * in the current collection. In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @return number of items in collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * The method adds the received object into
	 * the current collection. In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @param value the object to be added into the collection
	 */
	public void add(Object value) {
	}
	
	/**
	 * The method checks if the received object is in the current collection
	 * using the <code>equals</code> method. In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @param value the object which we are checking is in the collection or not
	 * @return <code>true</code> if the collection contains the received object
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * The method searches through the current collection and removes
	 * an occurrence of the received object. In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @param value the object which an instance of will be removed
	 * from the current collection
	 * @return <code>true</code> if the collection contains the received object
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * The method allocates a new array the same size as the current collection
	 * and then fills the array with the contents of the collection. 
	 * In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @return array filled with the contents of the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * The method calls <code>processor.process()</code> for each 
	 * element of the collection. In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 * 
	 * @param processor the processor which will provide the 
	 * <code>process</code> method
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * The method adds all elements from the received collection
	 * into the current collection.
	 * 
	 * @param other the collection whose objects will be added into
	 * the current collection
	 */
	public void addAll(Collection other) {
		
		class AddToCollectionProcessor extends Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddToCollectionProcessor());
	}
	
	/**
	 * The method removes all elements from the current collection.
	 * In the class <code>Collection</code> this
	 * method is not implemented correctly and it is expected that the
	 * derived classes implement it correctly.
	 */
	public void clear() {
	}
}
