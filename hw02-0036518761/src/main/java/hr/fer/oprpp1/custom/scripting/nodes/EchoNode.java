package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

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
			if(el != null) {//null pointer baca radi one velicine 10 u echonode
				sb.append(el.asText()+" ");
			}
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
			if(this.elements[i] == null || other.elements == null) {//also nullP radi velicine 10, moze se izbacit ako se promijeni parser
				break;
			}
			if(!this.elements[i].asText().equals(other.elements[i].asText())) {
				return false;
			}
		}
		return true;
	}
}
