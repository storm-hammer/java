package hr.fer.oprpp1.custom.collections;

/**
 * The class is an implementation of a simple dictionary consisting of entries
 * with a key and value.
 * 
 * @author Mislav Prce
 *
 * @param <K> the type of the keys of entries
 * @param <V> the type of the values of entries
 */
public class Dictionary<K, V> {
	
	private ArrayIndexedCollection<Entry<K, V>> entries;
	
	/**
	 * 
	 * @author Mislav Prce
	 *
	 * @param <K> the type of the keys of entries
	 * @param <V> the type of the values of entries
	 */
	private static class Entry<K, V> {
		
		private K key;
		private V value;
		
		/**
		 * The constructor makes a new instance of an entry with the given key and null as value.
		 * 
		 * @param key the key of the entry
		 */
		@SuppressWarnings("unused")
		public Entry(K key) {
			this(key, null);
		}
		
		/**
		 * The constructor makes a new instance of an entry with the given key and value.
		 * 
		 * @param key the key of the entry
		 * @param value the value of the entry
		 * @throws <code>NullPointerException</code> if the given key is null
		 */
		public Entry(K key, V value) {
			if(key == null) {
				throw new NullPointerException("The key of the entry cannot be null!");
			}
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * The default constructor for the dictionary.
	 */
	public Dictionary() {
		this.entries = new ArrayIndexedCollection<>();
	}
	
	/**
	 * The constructor makes a new instance of the dictionary and c???
	 * 
	 * @param other
	 */
	public Dictionary(Dictionary<K, V> other) {
		super();
		if(other == null) {
			throw new NullPointerException("The other dictionary cannot be null!");
		}
		this.entries = new ArrayIndexedCollection<>(other.entries);
	}
	
	/**
	 * The method checks if the dictionary is empty.
	 * 
	 * @return <code>true</code> if the collection is empty; <code>false</code> otherwise
	 */
	boolean isEmpty() {
		return entries.size() == 0;
	}
	
	/**
	 * The method returns the size of the dictionary.
	 * 
	 * @return size of the dictionary
	 */
	int size() {
		return entries.size();
	}
	
	/**
	 * The method removes all entries from the dictionary.
	 */
	void clear() {
		entries.clear();
	}
	
	/**
	 * The method adds the new entry into the dictionary. If there is an entry with the same key
	 * as the entry to be added into the dictionary the method doesn't add a new entry but
	 * sets the value of the entry that is already in the dictionary to the value of the entry to be
	 * added.
	 * 
	 * @param key the key of the entry to be added
	 * @param value the old value of the entry with the given key if that entry was already present 
	 * in the dictionary; <code>null</code> otherwise
	 * @return the value of the added entry
	 */
	V put(K key, V value) {
		
		for(int i = 0, size = entries.size(); i < size; i++) {
			Entry<K, V> entry = entries.get(i);
			
			if(entry.key.equals(key)) {
				V temp = entry.value;
				entry.value = value;
				return temp;
			}
		}
		
		entries.add(new Entry<>(key, value));
		return null;
	}
	
	/**
	 * The method returns the value of an entry that has the same key as the given key.
	 * 
	 * @param key the key of the entry whose value is wanted
	 * @return the value of the entry whose key was received if the key is present in the dictionary;
	 * <code>null</code> otherwise
	 */
	V get(Object key) {
		
		for(int i = 0, size = entries.size(); i < size; i++) {
			Entry<K, V> entry = entries.get(i);
			
			if(entry.key.equals(key)) {
				return entry.value;
			}
		}
		return null;
	}
	
	/**
	 * The method removes an entry with the given key if that entry is present in the dictionary and
	 * returns the value of the removed entry.
	 * 
	 * @param key the key of the entry to be removed from the dictionary
	 * @return the value of the removed entry if it was found; <code>null</code> otherwise
	 */
	V remove(K key) {
		
		for(int i = 0, size = entries.size(); i < size; i++) {
			Entry<K, V> entry = entries.get(i);
			
			if(entry.key.equals(key)) {
				entries.remove(i);
				return entry.value;
			}
		}
		return null;
	}

}
