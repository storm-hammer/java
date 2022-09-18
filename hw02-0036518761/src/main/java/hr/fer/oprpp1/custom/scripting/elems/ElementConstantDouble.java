package hr.fer.oprpp1.custom.scripting.elems;

public class ElementConstantDouble extends Element {
	
	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
