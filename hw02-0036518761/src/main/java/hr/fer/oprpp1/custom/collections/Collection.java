package hr.fer.oprpp1.custom.collections;

/**
 * The class <code>Collection</code> represents a general
 * collection of objects.
 * 
 * @author Mislav Prce
 */
public interface Collection {
	
	/**
	 * The method checks if the collection is empty or not.
	 * 
	 * @return <code>true</code> if the collection contains 
	 * no objects; <code>false</code> otherwise
	 */
	default boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * The method returns the number of currently stored objects
	 * in the current collection.
	 * 
	 * @return number of items in collection
	 */
	int size();
	
	/**
	 * The method adds the received object into
	 * the current collection.
	 * 
	 * @param value the object to be added into the collection
	 */
	void add(Object value);
	
	/**
	 * The method checks if the received object is in the current collection
	 * using the <code>equals</code> method.
	 * 
	 * @param value the object which we are checking is in the collection or not
	 * @return <code>true</code> if the collection contains the received object
	 */
	boolean contains(Object value);
	
	/**
	 * The method searches through the current collection and removes
	 * an occurrence of the received object.
	 * 
	 * @param value the object which an instance of will be removed
	 * from the current collection
	 * @return <code>true</code> if the collection contains the received object
	 */
	boolean remove(Object value);
	/**
	 * The method allocates a new array the same size as the current collection
	 * and then fills the array with the contents of the collection. 
	 * 
	 * @return array filled with the contents of the collection
	 */
	Object[] toArray();
	
	/**
	 * The method calls <code>processor.process()</code> for each 
	 * element of the collection.
	 * 
	 * @param processor the processor which will provide the 
	 * <code>process</code> method
	 */
	default void forEach(Processor p) {
		ElementsGetter getter = createElementsGetter();
		
		while(getter.hasNextElement() == true) {
			p.process(getter.getNextElement());
		}

	}
	
	/**
	 * The method adds all elements from the received collection
	 * into the current collection.
	 * 
	 * @param other the collection whose objects will be added into
	 * the current collection
	 */
	default void addAll(Collection other) {
		
		class AddToCollectionProcessor implements Processor {
			
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		
		other.forEach(new AddToCollectionProcessor());
	}
	
	/**
	 * The method removes all elements from the current collection.
	 */
	void clear();
	
	/**
	 * The method creates an instance of an <code>ElementsGetter</code>
	 * of the collection and returns it.
	 * 
	 * @return an instance of <code>ElementsGetter</code>
	 */
	ElementsGetter createElementsGetter();
	
	/**
	 * The method adds all elements for which the method <code>test</code>
	 * from the interface <code>Tester</code> returns <code>true</code> into the collection.
	 * 
	 * @param col the collection whose elements are tested and added into the current collection
	 * if they satisfy the conditions
	 * @param tester the interface which provides the <code>test</code> method
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		while(getter.hasNextElement() == true) {
			Object obj = getter.getNextElement();
			if(tester.test(obj) == true) {
				this.add(obj);
			}
		}
	}
}