package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public abstract class Node {
	
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
	
	public abstract void accept(INodeVisitor visitor);
}
