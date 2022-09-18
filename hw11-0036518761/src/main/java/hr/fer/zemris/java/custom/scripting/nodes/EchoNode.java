package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

public class EchoNode extends Node {
	
	private Element[] elements;
	
	public EchoNode(Element[] elements) {
		if(elements == null) {
			throw new NullPointerException("Elements cannot be null!"); 
		}
		this.elements = elements;
	}
	
	public Element[] getElements() {
		return this.elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$");
		for(int i = 0; i < elements.length; i++) {
			Element el = elements[i];
			sb.append(el.asText()+" ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof EchoNode)) {
			return false;
		}
		EchoNode other = (EchoNode)obj;
		if(this.elements.length != other.elements.length) {
			return false;
		}
		for(int i = 0; i < elements.length; i++) {
			if(!this.elements[i].asText().equals(other.elements[i].asText())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
