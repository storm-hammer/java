package hr.fer.oprpp1.custom.scripting.nodes;

public class TextNode extends Node {
	
	private String text;
	
	public TextNode(String text) {
		
		if(text == null) {
			throw new NullPointerException("Text cannot be null!");
		}
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}
	
	@Override
	public String toString() {//li'l weird but alright well leave it for now
		return getText();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TextNode)) {
			return false;
		}
		TextNode other = (TextNode) obj;
		if(this.getText().equals(other.getText())) {
			return true;
		} else {
			return false;
		}
	}
}
