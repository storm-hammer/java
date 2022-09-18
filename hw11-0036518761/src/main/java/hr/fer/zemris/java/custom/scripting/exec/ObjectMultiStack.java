package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.custom.stack.EmptyStackException;

public class ObjectMultistack {
	
	private Map<String, MultiStackEntry> multiStack;
	
	public ObjectMultistack() {
		this.multiStack = new HashMap<>();
	}
	
	public void push(String keyName, ValueWrapper valueWrapper) {
		MultiStackEntry head = multiStack.get(keyName);
		if(head == null) {
			multiStack.put(keyName, new MultiStackEntry(valueWrapper));
		} else {
			while(head.next != null) {
				head = head.next;
			}
			head.next = new MultiStackEntry(valueWrapper);
		}
	}
	
	public ValueWrapper pop(String keyName) {
		MultiStackEntry head = multiStack.get(keyName);
		MultiStackEntry previous = multiStack.get(keyName);
		if(head == null) {
			throw new EmptyStackException("The stack for this key is empty!");
		}
		if(head.next == null) {//only head on stack
			return multiStack.remove(keyName).value;
		}
		while(head.next != null) {
			previous = head;
			head = head.next;
		}
		previous.next = null;
		return head.value;
	}
	
	public ValueWrapper peek(String keyName) {
		MultiStackEntry head = multiStack.get(keyName);
		if(head == null) {
			throw new EmptyStackException("The stack for this key is empty!");
		}
		while(head.next != null) {
			head = head.next;
		}
		return head.value;
	}
	
	public boolean isEmpty(String keyName) {
		return multiStack.isEmpty();
	}
	
	static class MultiStackEntry {
		 private ValueWrapper value;
		 private MultiStackEntry next;
		 
		 public MultiStackEntry(ValueWrapper value) {
			this.value = value;
			this.next = null;
		}
	}
	
	public static void main(String[] args) {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		System.out.println("Current value for price: "
		+ multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		multistack.peek("year").setValue(
		((Integer)multistack.peek("year").getValue()).intValue() + 50
		);
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		multistack.pop("year");
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		multistack.peek("year").add("5");
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		multistack.peek("year").add(5);
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
		multistack.peek("year").add(5.0);
		System.out.println("Current value for year: "
		+ multistack.peek("year").getValue());
	}
}
