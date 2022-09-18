package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTester {
	
	Dictionary<Integer, String> dict;
	
	@Test
	public void testDictionaryConstructor() {
		assertThrows(NullPointerException.class, () -> dict = new Dictionary<>(null));
	}
	
	@Test
	public void testIsEmptyOnEmptyDictionary1() {
		dict = new Dictionary<>();
		dict.put(1, "ja");
		assertEquals(false, dict.isEmpty());
	}
	
	@Test
	public void testIsEmptyOnEmptyDictionary2() {
		dict = new Dictionary<>();
		assertEquals(true, dict.isEmpty());
	}
	
	@Test
	public void testSize1() {
		dict = new Dictionary<>();
		assertEquals(0, dict.size());
	}
	
	@Test
	public void testSize2() {
		dict = new Dictionary<>();
		dict.put(1, "ja");
		assertEquals(1, dict.size());
	}
	
	@Test
	public void testClear() {
		dict = new Dictionary<>();
		dict.put(1, "ja");
		dict.put(2, "nesto");
		dict.clear();
		assertEquals(0, dict.size());
	}
	
	@Test
	public void testPutReplacesOldValue() {
		dict = new Dictionary<>();
		dict.put(1, "ja");
		dict.put(2, "nesto");
		dict.put(2, "blabla");
		assertEquals("blabla", dict.get(2));
	}
	
	@Test
	public void testRemove() {
		dict = new Dictionary<>();
		dict.put(1, "nesto");
		dict.put(2, "blabla");
		dict.remove(2);
		assertNull(dict.get(2));
	}
}
