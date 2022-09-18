package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {
	
	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String asText() {
		return this.name;
	}
}
