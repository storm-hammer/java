package hr.fer.zemris.java.custom.scripting.elems;

public class ElementOperator extends Element {

	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return this.symbol;
	}
	
	@Override
	public String asText() {
		return this.symbol;
	}
}
