package hr.fer.oprpp1.custom.collections;

/**
 * This class is an implementation of a stack.
 * 
 * @author Mislav Prce
 */
public class ObjectStack<T> {
	
	private ArrayIndexedCollection<T> elements;
	
	/**
	 * The default constructor for the class.
	 */
	public ObjectStack() {
		elements = new ArrayIndexedCollection<>();
	}
	
	/**
	 * The method checks if the stack is empty.
	 * 
	 * @return <code>true</code> if the stack is empty; 
	 * <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	/**
	 * The method returns the number of currently stored objects on the stack.
	 * 
	 * @return the number of objects stored on the stack
	 */
	public int size() {
		return elements.size();
	}
	
	/**
	 * The method puts the given object on top of the stack.
	 * 
	 * @param value the object to be pushed to the stack
	 * @throws <code>NullPointerException</code> if the given object is null
	 */
	public void push(T value) {
		
		if(value == null) {
			throw new NullPointerException("Cannot push null reference to stack!");
		}
		
		elements.add(value);
	}
	
	/**
	 * The method removes an object from the stack and returns it.
	 * 
	 * @return the object which was taken from the top of the stack
	 */
	public T pop() {
		T temp = peek();
		elements.remove(size() - 1);
		return temp;
	}
	
	/**
	 * The method returns the object stored on top of the stack.
	 * 
	 * @return the object on top of the stack
	 */
	public T peek() {
		int size = elements.size();
		
		if(size == 0) {
			throw new EmptyStackException("No more elements on the stack!");
		}
		return elements.get(size - 1);
	}
	
	/**
	 * The method removes all objects from the stack.
	 */
	public void clear() {
		elements.clear();
	}
	
}
