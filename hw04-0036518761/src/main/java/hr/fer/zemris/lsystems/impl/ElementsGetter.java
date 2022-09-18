package hr.fer.zemris.lsystems.impl;

/**
 * 
 * @author User
 *
 */
public interface ElementsGetter<T> {
	
	/**
	 * The method checks if the getter has any elements
	 * left to return.
	 * 
	 * @return <code>true</code> if the getter has any elements
	 * remaining; <code>false</code> otherwise
	 */
	boolean hasNextElement();
	
	/**
	 * The method returns an object from the collection.
	 * 
	 * @return the next object from the collection
	 */
	T getNextElement();
	
	/**
	 * 
	 * @param p
	 */
	default void processRemaining(Processor<T> p) {
		
		while(hasNextElement() == true) {
			p.process(getNextElement());
		}
		
	}
}
