package hr.fer.oprpp1.custom.scripting.elems;

public class ElementString extends Element {
	
	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	@Override
	public String asText() {
		try {
			return "\"" + Integer.parseInt(this.value) + "\"";
		} catch(NumberFormatException e) {
		}
		try {
			return "\"" + Double.parseDouble(this.value) + "\"";
		} catch(NumberFormatException e) {
		}
		return this.value;
	}
}
