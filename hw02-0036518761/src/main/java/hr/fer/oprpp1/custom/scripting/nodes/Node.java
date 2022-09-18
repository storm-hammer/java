package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

public class Node {
	
	private ArrayIndexedCollection col;
	
	public void addChildNode(Node child) {
		if(child == null) {
			throw new NullPointerException("Child node cannot be null");
		}
		if(col == null) {
			col = new ArrayIndexedCollection();
		}
		col.add(child);
	}
	
	public int numberOfChildren() {
		return col.size();
	}
	
	public Node getChild(int index) {
		return (Node) col.get(index);
	}

}
