package hr.fer.oprpp1.custom.collections;

import static java.lang.Math.abs;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class is an implementation of a map which stores entries in an array of entries.
 * Every element stored in the array of entries is the head of a linked list of entries.
 * If an entry belongs to the same slot as the entry currently stored in that slot
 * (if they have the same hash value) the entry to be added into the map is appended to the 
 * list which belongs to the entry currently stored in the slot.
 * 
 * @author Mislav prce
 * @param <K> the type of the key of an entry
 * @param <V> the type of the value of an entry
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K,V>>{
	
	private static final int DEFAULT_CAPACITY = 16;
	private TableEntry<K, V>[] table;
	private int size;
	private int modificationCount;
	
	/**
	 * The default constructor of the map which instances a new map with the default
	 * capacity which is 16 slots.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_CAPACITY];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * The constructor instances a new map with a capacity of the next bigger power of 2 of the 
	 * desired capacity.
	 * 
	 * @param initialCapacity the desired capacity of the map
	 * @throws <code>IllegalArgumentException</code> if the given capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		super();
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Cannot create a table with capacity 0!");
		}
		int capacity = 1;
		while(capacity < initialCapacity) {
			capacity = capacity << 1;
		}
		
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * The class is an implementation of an iterator for the class
	 * <code>SimpleHashtable</code>.
	 * 
	 * @author Mislav Prce
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K,V>> {
		
		private TableEntry<K, V> current, previous;
		private int savedModificationCount;
		private SimpleHashtable<K, V> hashTable;
		private int index;
		private boolean canCallRemove;
		
		/**
		 * The constructor instances a new iterator over the given hash table.
		 * 
		 * @param hashTable the hash table over which a new iterator will be instanced
		 */
		public IteratorImpl(SimpleHashtable<K, V> hashTable) {
			super();
			this.savedModificationCount = modificationCount;
			this.hashTable = hashTable;
			this.index = 0;
			this.canCallRemove = false;
		}
		
		/**
		 * The method checks if the iterator has any elements remaining to return.
		 * 
		 * @return <code>true</code> if the iterator has elements remaining;
		 * <code>false</code> otherwise
		 * @throws <code>ConcurrentModificationException</code> if the table was 
		 * modified since the iterator was last used
		 */
		public boolean hasNext() {
			
			if(savedModificationCount != hashTable.modificationCount) {
				throw new ConcurrentModificationException("The hash table was modified!");
			}
		
			while(current == null && index < hashTable.table.length) {
				current = hashTable.table[index++];
			}
			
			return current != null;
		}
		
		/**
		 * The method returns the next element from the map.
		 * 
		 * @return the next entry from the map
		 * @throws <code>NoSuchElementException</code> if the iterator has no more elements 
		 * remaining to return
		 * @throws <code>ConcurrentModificationException</code> if the table was 
		 * modified since the iterator was last used
		 */
		@SuppressWarnings("rawtypes")
		public SimpleHashtable.TableEntry next() {
			
			if(savedModificationCount != hashTable.modificationCount) {
				throw new ConcurrentModificationException("The hash table was modified!");
			}
			
			while(current == null && index < hashTable.table.length) {
				current = hashTable.table[index++];
			}
			
			if(current != null) {
				previous = current;
				current = current.next;
				canCallRemove = true;
				return previous;
			}
			throw new NoSuchElementException("No elements remaining!");
		}	
		
		/**
		 * The method removes an element from the current table.
		 * 
		 * @throws <code>ConcurrentModificationException</code> if the table was 
		 * modified since the iterator was last used
		 * @throws <code>IllegalStateException</code> if the method was called more than once or 
		 * before any elements were removed
		 */
		public void remove() {
			if(savedModificationCount != hashTable.modificationCount) {
				throw new ConcurrentModificationException("The hash table was modified!");
			}
			if(canCallRemove) {
				hashTable.remove(previous.key);
				savedModificationCount++;
				canCallRemove = false;
			} else {
				throw new IllegalStateException("Cannot remove the previous entry more than once!");
			}
		}
	}
	
	/**
	 * The class is an implementation of a map entry.
	 * 
	 * @author Mislav Prce
	 * @param <K> the type of the key of an entry
	 * @param <V> the type of the value of an entry
	 */
	public static class TableEntry<K, V> {
		
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * The constructor instances a new entry with the given key as the key of the entry
		 * and null as the value of the entry.
		 * 
		 * @param key the key of the entry
		 * @throws <code>NullPointerException</code> if the given key is null
		 */
		public TableEntry(K key) {
			this(key, null);
		}
		
		/**
		 * The constructor instances a new entry with the given key as the key of the entry
		 * and the given value as the value of the entry.
		 * 
		 * @param key the key of the entry
		 * @param value the value of the entry
		 * @throws <code>NullPointerException</code> if the given key is null
		 */
		public TableEntry(K key, V value) {
			super();
			
			if(key == null) {
				throw new NullPointerException("The key of the table entry cannot be null!");
			}
			this.key = key;
			this.value = value;
			this.next = null;
		}
		
		/**
		 * The method returns the key of the entry.
		 * 
		 * @return the key of the entry.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * The method returns the value of the entry.
		 * 
		 * @return the value of the entry.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * The method sets the value of the entry to the given value.
		 * 
		 * @param value the new value of the current entry
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * The method returns a string representation of the current entry.
		 * 
		 * @return a string representation of the entry
		 */
		@Override
		public String toString() {
			return this.key + "=" + this.value;
		}
	}
	
	/**
	 * The method checks if the table is empty or not. 
	 * 
	 * @return <code>true</code> if the table is empty; <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * The method returns the size of the table.
	 * 
	 * @return the size of the table
	 */
	public int size() {
		return size;
	}
	
	/**
	 * The method adds the entry into the table in the slot where it belongs based on its hash value.
	 * If the method finds an entry with the same key, it updates the value of the entry already stored
	 * in the map. If the designated slot for the entry is full the method appends the entry
	 * to the end of the list stored at the designated slot.
	 * 
	 * @param key the key of the entry to be added into the table
	 * @param value the value of the entry to be added into the table
	 * @return the old value of the entry in the map whose value was replaced, if it was replaced;
	 * <code>null</code> otherwise
	 * @throws <code>NullPointerException</code> if the given key is null
	 */
	public V put (K key, V value) {
		
		if(size/(double)table.length >= 0.75) {
			reallocate();
		}
		
		if(key == null) {
			throw new NullPointerException("Cannot put an entry whose key is null into the map!");
		}
		
		int hash = getHash(key);
		
		if(table[hash] != null) {
			TableEntry<K, V> current = table[hash], previous = table[hash];
			
			while(current != null) {
				
				if(current.key.equals(key)) {//updated value in already present entry
					V oldValue = current.value;
					current.value = value;
					return oldValue;
				}
				previous = current;//advance in the list
				current = current.next;
			}
			
			previous.next = new TableEntry<>(key, value);//added element to the end of the list
			increase();
			return null;
		}
		
		table[hash] = new TableEntry<>(key, value);//added element as a slot list head
		increase();
		return null;
	}
	
	/**
	 * The method removes the entry with the given key if it is present in the map
	 * and returns its value; returns <code>null</code> otherwise.
	 * 
	 * @param key the key of the entry which is to be removed from the map
	 * @return the value of the removed entry if it is present in the map;
	 * <code>null</code> otherwise
	 */
	public V remove(Object key) {
		
		if(key == null) {
			return null;
		}
		
		int hash = getHash(key);
		TableEntry<K, V> current = table[hash], previous = table[hash];
		
		if(current == null) {//slot is empty
			return null;
		}
		
		if(current.next == null) {//slot only has one entry
			
			if(current.key.equals(key)) {
				table[hash] = null;
				reduce();
				return current.value;
			}
		}
		
		while(current != null) {
			
			if(current.key.equals(key)) {
				
				if(table[hash] == current) {//removed element from the start of the list, new head
					table[hash] = current.next;
					reduce();
					return current.value;
				}
				
				previous.next = current.next;//removed element from inside the list
				reduce();
				return current.value;
			}
			previous = current;
			current = current.next;
		}
		
		return null;//if we get to this line we have not found the matching entry
	}
	
	/**
	 * The method increases the size and modification count by 1.
	 */
	private void increase() {
		modificationCount++;
		size++;
	}
	
	/**
	 * The method reduces the size and increases the modification count by 1.
	 */
	private void reduce() {
		modificationCount++;
		size--;
	}
	
	/**
	 * The method calculates the hash value of the current entry based on the key of the
	 * entry and the table length.
	 * 
	 * @param key the key of the entry whose hash value is being calculated
	 * @return the hash value of the given key
	 */
	private int getHash(Object key) {
		return abs(key.hashCode()) % table.length;
	}
	
	/**
	 * The method checks if the map contains the given key.
	 * 
	 * @param key the key whose presence in the map is checked
	 * @return <code>true</code> if the key is present in the map; <code>false</code> otherwise
	 * @throws <code>NullPointerException</code> if the given key is null
	 */
	public boolean containsKey(Object key) {
		
		if(key == null) {
			throw new NullPointerException("Cannot check if an entry with a null key is in the map!");
		}
		int hash = getHash(key);
		TableEntry<K, V> entry = table[hash];
		
		return getValueofKey(entry, key) != null ? true : false;
	}
	
	/**
	 * The method returns the value of the given key if it is present in the map.
	 * 
	 * @param key the key whose value is to be returned if it is present in the map
	 * @return the value of the given key if it is present in the map;
	 * <code>null</code> otherwise
	 */
	public V get(K key) {
		
		if(key == null) {
			return null;
		}
		
		int hash = getHash(key);
		TableEntry<K, V> entry = table[hash];
		
		return getValueofKey(entry, key);
	}
	
	/**
	 * The method returns the value of the given key if it is present in the map,
	 * returns null otherwise.
	 * 
	 * @param entry the entry in whose list the entry with the given key should be stored
	 * @param key the key of the entry whose value will be returned
	 * @return the value of the entry with the given key if it is present;
	 * <code>null</code> otherwise
	 */
	private V getValueofKey(TableEntry<K, V> entry, Object key) {
		
		while(entry != null) {
			if(entry.key.equals(key)) {
				return entry.value;
			}
			entry = entry.next;
		}
		return null;
	}
	
	/**
	 * The method returns a string representation of the current map. The order of
	 * elements is top-down concerning the slots and left-right concerning the
	 * lists stored in the slots.
	 * 
	 * @return the string representation of the contents of the map if it is not empty;
	 * <code>null</code> otherwise
	 */
	@Override
	public String toString() {
		
		TableEntry<K, V>[] entries =  this.toArray();
		if(entries.length == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder("[");
		
		sb.append(entries[0].toString());
		for(int i = 1; i < size; i++) {
			sb.append(", " + entries[i].toString());
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	/**
	 * The method returns the contents of the map in the form of an array of entries.
	 * The entries from the beginning of the map are stored on the lowest indexes of the
	 * array and so on.
	 * 
	 * @return a new array of entries which contains the contents of the map.
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		
		TableEntry<K, V>[] contents = (TableEntry<K, V>[]) new TableEntry[size];
		
		if(size == 0) {
			return contents;
		}
		int i = 0;
		int j = 0;
		
		while(i < table.length) {
			
			if(table[i] != null) {
				
				contents[j] = table[i];
				TableEntry<K, V> tempEntry = table[i].next;
				i++;//index of current slot
				j++;//index of entry in contents
				
				while(tempEntry != null) {//adding the entries form the current slots list
					contents[j] = tempEntry;
					j++;
					tempEntry = tempEntry.next;
				}
				
			} else {//skip empty slot
				i++;
			}
		}
		return contents;
	}
	
	/**
	 * The method checks if the map contains the given value. The value can be null.
	 * 
	 * @param value the value whose presence in the map is being checked
	 * @return <code>true</code> if the value is present in the map; <code>false</code> otherwise
	 */
	public boolean containsValue(Object value) {
		
		TableEntry<K, V>[] entries = this.toArray();
		
		if(value == null) {//contains null value
			
			for(int i = 0; i < entries.length; i++) {
				if(entries[i].value == null) {
					return true;
				}
			}
			
		} else {//contains certain value
			
			for(int i = 0; i < entries.length; i++) {
				if(entries[i].value.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * The method removes all entries from the current map.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		modificationCount++;
		size = 0;
	}
	
	/**
	 * The method doubles the capacity of the map (number of slots) and fills the new
	 * map with the contents of the old map (calculates hash values again).
	 */
	@SuppressWarnings("unchecked")
	private void reallocate() {
		
		TableEntry<K, V>[] entries = this.toArray();
		size = 0;
		table = (TableEntry<K, V>[]) new TableEntry[table.length << 1];
		
		for(TableEntry<K, V> entry : entries) {
			this.put(entry.key, entry.value);
		}
		modificationCount++;
	}
	
	/**
	 * The method returns an instance of an iterator over the current hash table.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}
}
