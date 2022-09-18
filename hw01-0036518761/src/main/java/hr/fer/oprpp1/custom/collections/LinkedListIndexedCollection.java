package hr.fer.oprpp1.custom.collections;

/**
 * The class is an implementation of a linked list.
 * 
 * @author Mislav Prce
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * The class represents a list node in the linked list.
	 * 
	 * @author Mislav Prce
	 */
	private static class ListNode {
		ListNode next;
		ListNode previous;
		Object value;
		
		/**
		 * The constructor makes a new instance of a <code>ListNode</code> with the
		 * <code>value</code> parameter setting the value of the node.
		 * 
		 * @param value the value to which the value of the node will be set.
		 */
		public ListNode(Object value) {
			this.value = value;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * The default constructor of the linked list.
	 */
	public LinkedListIndexedCollection() {
		super();
	}
	
	/**
	 * The constructor makes a new instance of the linked list 
	 * and copies all items from the given collection into the 
	 * current collection.
	 * 
	 * @param other the collection whose elements are copied into the
	 * new linked list
	 */
	public LinkedListIndexedCollection(Collection other) {
		
		if(other == null) {
			throw new NullPointerException("The other collection from which we are supposed to copy cannot be null!");
		}
		addAll(other);
	}
	
	/**
	 * The method returns the size of the linked list.
	 * 
	 * @return the number of objects currently stored in the linked list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * The method appends the given value at the end of the list
	 * using auxiliary methods.
	 * 
	 * @throws <code>NullPointerException</code> if the given value is 
	 * <code>null</code>
	 * @param value the object that will be appended to the list.
	 */
	@Override
	public void add(Object value) {
		
		if(value == null) {
			throw new NullPointerException();
		}
		
		ListNode temp = new LinkedListIndexedCollection.ListNode(value);
		
		if(first == null) {//empty list
			connectOnly(temp);
		} else {
			connectLast(temp);//appending to the end
		}
	}
	
	/**
	 * The method checks if the given object is contained in the list.
	 * 
	 * @param value the object whose presence in the list is being checked
	 * @return <code>true</code> if the given object is in the list;
	 * <code>false</code> otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}
	
	/**
	 * The method removes the first occurrence of the given object from the list.
	 * 
	 * @param value the object whose instance will be removed from the list
	 * if found
	 * @throws <code>NullPointerException</code> if the given object is null
	 * @return <code>true</code> if the method found and removed an occurrence of 
	 * the given object; <code>false</code> otherwise
	 */
	@Override
	public boolean remove(Object value) {
		
		if(value == null) {
			throw new NullPointerException("Cannot remove null from collection!");
		}
		
		ListNode n = first;
		
		while(n != null) {//a loop that can deal with an empty list, just skips checking completely
			if(n.value.equals(value)) {
				disconnectNode(n);
				return true;
			}
			n = n.next;
		}
		
		return false;//if we get to here we didn't find the element to remove
	}
	
	/**
	 * The method adds the given object into the empty list.
	 * 
	 * @param node the object to be added into the list
	 */
	public void connectOnly(ListNode node) {
		first = node;
		last = node;
		size++;
	}
	
	/**
	 * The method appends the node at the end of the list.
	 * 
	 * @param node the object to be added into the list
	 */
	public void connectLast(ListNode node) {
		node.previous = last;
		last.next = node;
		last = node;
		size++;
	}
	
	/**
	 * The method that adds the given node at the start of the list.
	 * 
	 * @param node the object to be added into the list.
	 */
	public void connectFirst(ListNode node) {
		node.next = first;
		first.previous = node;
		first = node;
		size++;
	}
	
	/**
	 * The method inserts the given object into the list at the given
	 * position and shifts the rest of the elements one slot forward.
	 * 
	 * @param node the object to be inserted into the list
	 * @param position the desired index of the object to be added into the list
	 */
	public void connectAtPosition(ListNode node, int position) {
		
		if(position == 0) {
			connectFirst(node);//inserting in the first place, at index 0
			return;
		}
		
		ListNode newNext = nodeAtIndex(position);
		ListNode newPrevious = newNext.previous;
		//if we get to this line at all that means the node is surrounded by other elements
		node.next = newNext;
		node.previous = newPrevious;
		newPrevious.next = node;
		newNext.previous = node;
		
		size++;
	}
	

	/**
	 * The method disconnects the given node from the list
	 * using auxiliary methods.
	 * 
	 * @param node the node to be disconnected from the list
	 */
	public void disconnectNode(ListNode node) {
		
		if(node.previous == null) {
			
			if(node.next == null) {
				disconnectOnly();//empty list
			} else {
				disconnectFirst(node);//first item in the list
			}
			
		} else {
			
			if(node.next == null) {
				disconnectLast(node);//last item in the list
			} else {
				disconnectMiddle(node);//somewhere in the list, surrounded by nodes
			}
		}
		
		node.value = null;
		size--;
	}
	
	/**
	 * The method disconnects the first element from the list.
	 * Auxiliary method for use in other methods.
	 * 
	 * @param node the element to be disconnected from the list.
	 */
	public void disconnectFirst(ListNode node) {
		first = node.next;
		node.next = null;
	}
	
	/**
	 * The method disconnects the only node in the list.
	 * Auxiliary method for use in other methods.
	 * 
	 */
	public void disconnectOnly() {
		last = null;
		first = null;
	}
	
	/**
	 * The method disconnects the last node in the list.
	 * Auxiliary method for use in other methods.
	 * 
	 * @param node the node to be disconnected from the list
	 */
	public void disconnectLast(ListNode node) {
		last = node.previous;
		node.previous = null;
	}
	
	/**
	 * The method disconnects the given node which is located in between
	 * two other nodes in the list. Auxiliary method for use in other methods.
	 * 
	 * @param node the node to be disconnected from the list
	 */
	public void disconnectMiddle(ListNode node) {
		node.previous.next = node.next;
		node.next.previous = node.previous;
		node.next = null;
		node.previous = null;
	}
	
	/**
	 * The method returns the contents of the list in the form
	 * of a newly allocated array.
	 * 
	 * @return array filled with list contents
	 */
	@Override
	public Object[] toArray() {
		
		/**
		 * An array used to store the elements of the list in the form of an array.
		 */
		Object[] values = new Object[size];
		ListNode n = first;
		int i = 0;
		
		while(i < size) {
			values[i++] = n.value;
			n = n.next;
		}
		
		return values;
	}
	
	/**
	 * The method which applies <code>processor.process</code> on each
	 * element of the list.
	 * 
	 * @param processor the processor which provides the <code>process</code>
	 * method
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode n = first;
		
		while(n != null) {
			processor.process(n.value);
			n = n.next;
		}
	}
	
	/**
	 * The method empties the list.
	 * 
	 */
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * The method finds the element in the list at the given index.
	 * 
	 * @param index the index from which the element is returned
	 * @return the value at the given index
	 * @throws <code>IndexOutOfBoundsException</code> in case of invalid index
	 */
	public Object get(int index) {
		
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index, cannot get element from that index!");
		}
		
		return nodeAtIndex(index).value;
	}
	
	/**
	 * The method returns the node from the given index in the list.
	 * 
	 * @param index the index of the desired element
	 * @return the node which has the given index
	 */
	public ListNode nodeAtIndex(int index) {
		
		int position;
		ListNode n;
		
		if(index < (size - 1) /2) {
			position = 0;
			n = first;
			while(position != index) {
				n = n.next;
				position++;
			}
		} else {
			position = size - 1;
			n = last;
			while(position != index) {
				n = n.previous;
				position--;
			}	
		}
		return n;
	}
	
	/**
	 * The method inserts the given value into the list at the desired position
	 * and shifts the other elements one spot forward.
	 * 
	 * @param value the object to be inserted into the list
	 * @param position the position where the object will be inserted
	 * @throws <code>IllegalArgumentException</code> in case of invalid argument
	 * @throws <code>NullPointerException</code> if the parameter <code>value</code>
	 * is null
	 */
	public void insert(Object value, int position) {
		
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Illegal position for insertion of the element!");
		}
		
		if(value == null) {
			throw new NullPointerException("Cannot insert null reference!");
		}
		
		ListNode newNode = new LinkedListIndexedCollection.ListNode(value);
		
		if(first == null) {
			connectOnly(newNode);
		}
		
		connectAtPosition(newNode, position);
	}
	
	/**
	 * The method returns the index of the given object in the list if it is found.
	 * 
	 * @param value the object whose index is searched for
	 * @return the index of the given object; -1 otherwise
	 */
	public int indexOf(Object value) {
		
		if(value == null) {
			return -1;
		}
		
		int i = 0;
		ListNode n = first;
		
		while(i < size) {
			if(n.value.equals(value)) {
				return i;
			}
			n = n.next;
			i++;
		}
		
		return -1;
	}
	
	/**
	 * The method removes the object at the given index from the list.
	 * 
	 * @param index the index from where an object will be removed
	 * @throws <code>IndexOutOfBoundsException</code> in case of invalid index
	 */
	public void remove(int index) {
		
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index for element removal!");
		}
		
		disconnectNode(nodeAtIndex(index));
	}
}
