package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;

/**
 * The class is an implementation of a resizable collection
 * of objects which it stores in an array.
 * 
 * @author Mislav Prce
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	
	/**
	 * An array which is used to store elements of the collection.
	 */
	private Object[] elements;
	
	static final int MIN_CAPACITY = 16;

	/**
	 * The default constructor for this class. Sets the capacity of
	 * the collection to 16.
	 */
	public ArrayIndexedCollection() {
		this(MIN_CAPACITY);
	}
	
	/**
	 * A constructor which receives the capacity as an argument.
	 * 
	 * @param capacity the length of the <code>elements</code> array
	 * @throws <code>IllegalArgumentException</code> if the <code>initialCapacity</code>
	 * is less than 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("The initial capacity of the collection cannot be negative!");
		}
		
		elements = new Object[initialCapacity];
		size = 0;
	}
	
	/**
	 * A constructor which receives a reference to another collection.
	 * The constructor delegates the work of initializing the array 
	 * and capacity to the more complex constructor.
	 * 
	 * @param other the reference to a collection whose
	 * items will be copied into the newly made collection.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, MIN_CAPACITY);
	}
	
	/**
	 * A constructor which receives a reference to another collection
	 * and an integer which represents the wanted initial capacity.
	 * The constructor preallocates an <code>Object</code> array
	 * of the minimum capacity possible (either the size of the collection
	 * or the <code>initialCapacity</code>).
	 * 
	 * @param other reference to a collection whose items will be copied
	 * into the newly made collection.
	 * @param initialCapacity the capacity of the collection to be 
	 * constructed
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		
		if(other == null) {
			throw new NullPointerException("The initial capacity of the collection cannot be negative!");
		}
		
		int determinedCapacity;
		determinedCapacity = initialCapacity > other.size() ? initialCapacity : other.size();
		
		elements = new Object[determinedCapacity];
		
		addAll(other);
	}
	
	/**
	 * The method returns the number of currently stored objects
	 * in the current collection.
	 * 
	 * @return the size of the collection (number of stored objects).
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The method adds the received object into the current collection.
	 * The method doubles the capacity of the backing array if the 
	 * array is full. The method does not accept null references.
	 * 
	 * @throws <code>NullPointerException</code> if given a null reference as
	 * an argument
	 * @param value the object to be added into the collection
	 */
	@Override
	public void add(Object value) {
		
		if(value == null) {
			throw new NullPointerException("A null reference cannot be added into the collection!");
		}
		
		if(size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length << 1);
		}

		elements[size] = value;
		size++;
	}
	
	/**
	 * The method checks if the received object is in the current collection
	 * using the <code>equals</code> method.
	 * 
	 * @param value the object which we are checking is in the collection or not
	 * @return <code>true</code> if the collection contains the received object;
	 * <code>false</code> otherwise
	 */
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * The method allocates a new array the same size as the current collection
	 * and then fills the array with the contents of the collection. 
	 * 
	 * @return array filled with the contents of the collection
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}
	
	/**
	 * The method calls <code>processor.process</code> for each 
	 * element of the collection. 
	 * 
	 * @param processor the processor which will provide the 
	 * <code>process</code> method
	 */
	@Override
	public void forEach(Processor processor) {
		if(size != 0) {
			for(int i = 0; i < size; i++) {
				processor.process(elements[i]);
			}
		}
	}
	
	/**
	 * The method removes all elements from the current collection and
	 * leaves the current capacity of the array.
	 */
	@Override
	public void clear() {
		
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
			
		size = 0;
	}
	
	/**
	 * The method returns the object from the current collection
	 * stored at the position given by the parameter.
	 * 
	 * @param index the position of the object that will be returned in the 
	 * current collection
	 * @return object from the collection that is located at the received index
	 * @throws IndexOutOfBoundsException if the index parameter is invalid
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index!");
		}
		
		return elements[index];
	}
	
	/**
	 * The method inserts the given object into the collection
	 * at the given position.
	 * 
	 * @throws <code>NullPointerException</code> if the <code>value</code> parameter
	 * is null
	 * @throws <code>IndexOutOfBoundsException</code> if the <code>position</code>
	 * parameter is invalid
	 * @param value the object that will be inserted into the collection
	 * @param position the position at which the object will be inserted
	 */
	public void insert(Object value, int position) {
		
		if(value == null) {
			throw new NullPointerException("Cannot insert null value into collection!");
		}
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Invalid index for insertion!");
		}
		
		if(elements.length == size) {
			elements = Arrays.copyOf(elements, elements.length << 1);
		}
		
		int i = size - 1;
		
		if(i >= position) {
			while(i >= position) {
				elements[i+1] = elements[i];
				i--;
			}
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * The method finds the index of the first occurrence of the given object.
	 * 
	 * @param value the object whose index we are searching for
	 * @return index of the given object in the current collection if 
	 * it is found; -1 otherwise
	 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		for(int i = 0; i < size; i++) {
			if(value.equals(elements[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * The method removes the first occurrence of the given object
	 * in the current collection. The method also repositions
	 * the rest of the elements in the array.
	 * 
	 * @param value the object that an occurrence of will be removed
	 * from the current collection
	 * @throws <code>NullPointerException</code> if the <code>value</code>
	 * parameter is null
	 * @return <code>true</code> if an occurrence of the given object
	 * was found and removed from the collection; <code>false</code> otherwise
	 */
	@Override
	public boolean remove(Object value) {
		
		if(value == null) {
			throw new NullPointerException("Cannot remove null reference from collection!");
		}
		
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		
		remove(index);
		
		return true;
	}
	
	/**
	 * The method removes the object at the given index
	 * in the current collection. The method also repositions
	 * the rest of the elements in the array.
	 * 
	 * @throws <code>IndexOutOfBoundsException</code> if the <code>index</code>
	 * parameter is invalid
	 * @param index the position of the object that will be removed
	 */
	public void remove(int index) {
		
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index for item removal!");
		}
		
		int i = index;

		if(i < size - 1) {			
			while(i < size - 1) {
				elements[i] = elements[i+1];
				i++;
			}
		}
		
		elements[size-1] = null;
		size--;
	}
	
}
